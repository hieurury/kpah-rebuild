/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import config.Config;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public class ModController {

	public static final String VERSION = "2.2.5";

	public static Config globalConfig = Config.loadConfig();

	protected static boolean isActiveAutoChat;

	public static long refeshTime = 0;

	static {
		try {
			// Tự động xóa cache ảnh thú cưỡi cũ bị sai lệch thứ tự để tải lại đúng chuẩn từ server
			javax.microedition.rms.RecordStore checkRs = null;
			boolean needClean = false;
			try {
				checkRs = javax.microedition.rms.RecordStore.openRecordStore("horse_img_clean_v1", true);
				if (checkRs.getNumRecords() == 0) {
					needClean = true;
					byte[] dummy = new byte[]{1};
					checkRs.addRecord(dummy, 0, dummy.length);
				}
			} finally {
				if (checkRs != null) {
					try {
						checkRs.closeRecordStore();
					} catch (Exception ignored) {}
				}
			}
			if (needClean) {
				try {
					javax.microedition.rms.RecordStore.deleteRecordStore("nqshImgPotionNew");
				} catch (Exception ignored) {}
			}
		} catch (Exception ignored) {}
	}

	// Đóng băng auto khi người chơi thao tác thủ công (bấm phím di chuyển, click chuột):
	public static long manualFreezeUntil = 0; // Timestamp ms kết thúc đóng băng 5s
	public static boolean autoWasActiveBeforeManual = false; // Ghi nhớ cờ auto để tự kích hoạt lại sau 5s

	public static boolean isNpc(Object target) {
		if (target == null) {
			return false;
		}
		if (target instanceof class_vh) {
			class_vh vh = (class_vh) target;
			if (vh.cF == 2 || vh.M() || vh.d_() || vh instanceof class_gn) {
				return true;
			}
		}
		return false;
	}

	public static void disableAutoCompletely() {
		class_abj.au = false;
		class_abj.av = false;
		autoCombatKeepActive = false;
		autoWasActiveBeforeManual = false;
		manualFreezeUntil = 0;
		isRegulating = false;
		autoAnchorMapId = -1;
		autoAnchorX = -1;
		autoAnchorY = -1;
		lastAttackMobId = -1;
		lastMobHp = -1;
		mobAttackStartTime = 0;

		class_abj gameScreen = class_acv.s;
		if (gameScreen != null && gameScreen.q != null) {
			((class_sc) gameScreen.q).s = null; // Huỷ ngay đường đi tự động
		}
		// Nhả toàn bộ phím đánh tự động
		class_acv.c[1] = false;
		class_acv.c[3] = false;
		class_acv.c[5] = false;
		class_acv.c[7] = false;
		class_acv.c[9] = false;
	}

	public static void update() {
		class_abj gameScreen = class_acv.s;
		long now = System.currentTimeMillis();

		// 0. Tự động hồi sinh khi hy sinh (hoạt động liên tục cả khi bật hay tắt auto)
		if (gameScreen != null && gameScreen.q != null) {
			class_hw player = gameScreen.q;
			if (player.cV == 3) {
				handleAutoRevive(gameScreen, player);
				return;
			} else {
				resetAutoRevive();
			}
		}

		// Tự động bán trang bị cấp thấp hơn bản thân
		handleAutoSellLowEquip();

		// 1. Kiểm tra nếu người chơi chủ động tắt Auto trong cài đặt hoặc chết:
		// Khi cả 2 cờ au (auto đánh) và av (cờ cấu hình auto) đều tắt,
		// nghĩa là người chơi đã chủ động TẮT AUTO!
		if (!class_abj.au && !class_abj.av) {
			if (autoCombatKeepActive || autoWasActiveBeforeManual || isRegulating) {
				disableAutoCompletely();
			}
			return;
		}

		// 2. Kiểm tra nếu người dùng đang chủ động nhấn phím di chuyển (phím 2, 4, 6, 8)
		if (class_acv.e[2] || class_acv.e[4] || class_acv.e[6] || class_acv.e[8]) {
			onUserManualMove();
			return;
		}

		// 3. Kiểm tra nếu đang trong thời gian đóng băng 5s do người chơi thao tác thủ công
		if (now < manualFreezeUntil) {
			// Đang bị đóng băng: Tuyệt đối không can thiệp, để người chơi tự do điều khiển.
			// TUYỆT ĐỐI KHÔNG xóa gameScreen.r để người chơi có thể tự do target NPC hoặc quái!
			return;
		}

		// 4. Đã hết thời gian đóng băng 5s (người chơi ngừng thao tác đủ 5 giây):
		// Tự động kích hoạt lại auto cho người chơi nếu trước đó auto đang chạy!
		if (autoWasActiveBeforeManual) {
			autoWasActiveBeforeManual = false;
			class_abj.au = true;
			class_abj.av = true;
			autoCombatKeepActive = true;

			if (gameScreen != null && gameScreen.q != null) {
				class_hw player = gameScreen.q;
				autoAnchorMapId = gameScreen.aG;
				autoAnchorX = player.cK;
				autoAnchorY = player.cL;
				player.ag = autoAnchorX;
				player.ah = autoAnchorY;
				lastAttackMobId = -1;
				lastMobHp = -1;
				mobAttackStartTime = 0;
				isRegulating = false;
				class_acv.a("Auto đã tự kích hoạt lại.");
			}
		}

		if (gameScreen != null && gameScreen.q != null) {
			class_hw player = gameScreen.q;
			// Chỉ khôi phục au nếu av đang bật (tức là người chơi chưa tắt trong cài đặt)
			if (class_abj.av && autoCombatKeepActive) {
				if (!player.cW && player.cV != 3 && !class_abj.au) {
					class_abj.au = true;
				}
			}
			// Nếu đang trong trạng thái điều tiết về tâm: chỉ xóa target quái vật/item, không xóa NPC
			if (isRegulating && gameScreen.r != null && !isNpc(gameScreen.r)) {
				gameScreen.r = null;
			}
		}

		handleAutoSupportSkills(gameScreen, gameScreen != null ? gameScreen.q : null);
		doAutoGame();
		handleAutoCombatRoaming();
	}

	private static long lastSupportBuffCheck = 0;

	/**
	 * Tự động kích hoạt và duy trì tất cả các kỹ năng hỗ trợ/buff được gán ở trang Hỗ Trợ (page 1).
	 * Hỗ trợ dùng đồng thời nhiều kỹ năng buff chủ động (ví dụ: Pháp Sư vừa bật Hồi công lực đan vừa bật Song hộ công thủ).
	 */
	private static void handleAutoSupportSkills(class_abj gameScreen, class_hw player) {
		if (player == null || player.cV == 3 || player.cW) {
			return;
		}
		long now = System.currentTimeMillis();
		if (now - lastSupportBuffCheck < 400) {
			return; // Quét mỗi 400ms
		}
		lastSupportBuffCheck = now;

		// Chỉ tự động duy trì buff khi auto đang chạy (au hoặc av)
		if (!class_abj.au && !class_abj.av) {
			return;
		}

		if (class_sc.a != null && class_sc.a.length > 1 && class_sc.a[1] != null) {
			class_gd[] supportSlots = class_sc.a[1];
			for (int i = 0; i < supportSlots.length; i++) {
				class_gd gd = supportSlots[i];
				if (gd == null) continue;
				byte skillId = gd.b();
				if (skillId < 4 || skillId > 7) continue;

				if (class_hw.aS == null || skillId >= class_hw.aS.length || class_hw.aS[skillId] <= 0) {
					continue;
				}

				if (class_qz.c == null || player.aO < 0 || player.aO >= class_qz.c.length) continue;
				byte[] effArray = class_qz.c[player.aO];
				int effIdx = skillId - 4;
				if (effIdx < 0 || effIdx >= effArray.length) continue;
				byte effId = effArray[effIdx];

				// Chỉ áp dụng cho self-buff chủ động (class_qz.d[aO][effIdx] == 0)
				if (class_qz.d != null && player.aO < class_qz.d.length && effIdx < class_qz.d[player.aO].length) {
					if (class_qz.d[player.aO][effIdx] != 0) {
						continue;
					}
				}

				// Nếu buff đã active trên người thì không buff lại
				if (player.e((int) effId)) {
					continue;
				}

				// Kiểm tra hồi chiêu
				if (player.aq != null && player.at != null && skillId < player.aq.length && skillId < player.at.length) {
					if (now - player.aq[skillId] <= player.at[skillId]) {
						continue;
					}
				}

				// Kiểm tra MP
				int mpCost = class_qz.b(skillId, (int) class_hw.aS[skillId]);
				if (player.bz < mpCost) {
					continue;
				}

				// Kích hoạt buff: gửi packet 51 lên server
				class_go.a().a(player.cG, (byte) 0, effId, (short) 0);
				player.bz -= mpCost;
				if (player.at != null && skillId < player.at.length) {
					player.at[skillId] = class_qz.a((byte) skillId, (int) class_hw.aS[skillId]);
				}
				if (player.aq != null && skillId < player.aq.length) {
					player.aq[skillId] = now;
				}
				break;
			}
		}
	}

	private static void doAutoGame() {
		if (ModHelpers.leftTime(refeshTime) > 180) {
			refeshTime = ModHelpers.currentTime();
			if (globalConfig.isAutoCayThan) {
				for (int i = 0; i < 8; i++) {
					class_go.a().n(i, 2);
					class_go.a().n(i, 3);
				}
			}
			if (globalConfig.isAutoRemoveDa) {
				doRemoveStone();
			}
			if (globalConfig.isAutoRemoveTranh) {
				doRemoveTranh();
			}
		}
	}

	/*
	 * Bỏ Đá
	 */
	private static void doRemoveStone() {
		for (int i = 159; i <= 174; i++) {
			class_go.a().a((short) i, 0, (byte) 0);
		}
		for (int i = 179; i <= 226; i++) {
			class_go.a().a((short) i, 0, (byte) 0);
		}
	}

	private static void doRemoveTranh() {
		for (int i = 15; i <= 23; i++) {
			class_go.a().a((short) i, 0, (byte) 0);
		}
		for (int i = 28; i <= 45; i++) {
			class_go.a().a((short) i, 0, (byte) 0);
		}
		class_go.a().a((short) 12, 0, (byte) 0);
		class_go.a().a((short) 13, 0, (byte) 0);
		class_go.a().a((short) 58, 0, (byte) 0);
		class_go.a().a((short) 59, 0, (byte) 0);
	}

	public void calCharactorLevelUp() {

	}

	public static void xaPhu() {
		new class_om(class_acv.s).perform();
	}

	public static void doDownHorse() {
		new class_qo(class_acv.s).perform();
	}

	public static void setNoti(String s) {
		class_acv.a(s);
	}

	public static void doSaveCurrentLocation() {
		// Lưu toạ độ đang đứng vào record
		String currentLocation = ModHelpers.getMapNameAndPosition();
		ModController.globalConfig.savedPostion = currentLocation;
		ModController.globalConfig.saveConfig();
		class_acv.a("Đã lưu vị trí " + currentLocation + " vào ổ lưu trữ!");
	}

	public static void logOut() {
		class_acv.g();
		class_acv.a.c();
		class_aco.a().c();
		class_ls.a(0, null);
		class_yv.a().d();
	}

	public static void onStartGame() {
		AccountManager.getInstance().loadByRecord();
	}

	/**
	 * Được gọi từ bytecode patch trong class_abj.z() để ưu tiên nhặt đồ.
	 * - Vật phẩm rơi (class_ba): khoảng cách chia 4 => luôn được chọn trước.
	 * - Quái vật (cF==1): khoảng cách nhân 4 => chỉ được chọn nếu không có đồ.
	 *
	 * @param context  đối tượng đang tính khoảng cách (class_vh hoặc subclass)
	 * @param orig     khoảng cách gốc do class_yg.d() tính ra
	 */
	public static int priorityDistance(Object context, int orig) {
		if (context instanceof class_ba) {
			return globalConfig.isAutoPickup ? orig / 4 : orig;
		}
		if (context instanceof class_bb) {
			class_bb mob = (class_bb) context;
			if (globalConfig.isPrioritizeElite && mob.isElite) {
				return 0;   // quái Tinh Anh: khoảng cách bằng 0 => ưu tiên tối cao tuyệt đối
			}
			if (globalConfig.isAutoPickup) {
				return orig * 4;   // quái vật: tăng khoảng cách => giảm ưu tiên so với nhặt đồ
			}
		} else if (context instanceof class_vh && ((class_vh) context).cF == 1) {
			if (globalConfig.isAutoPickup) {
				return orig * 4;
			}
		}
		return orig;
	}

	/**
	 * Được gọi từ bytecode hook movePlayer_(int, int) khi người dùng click chuột hoặc chạm đất di chuyển.
	 * Nhận tọa độ click chuột pixel trong thế giới (clickX, clickY).
	 * Nếu click vào NPC: LẬP TỨC khóa target vào NPC và kích hoạt giao tiếp nếu đứng gần!
	 */
	public static void onUserManualClick(int clickX, int clickY) {
		onUserManualMove();

		class_abj gameScreen = class_acv.s;
		if (gameScreen != null && gameScreen.l != null) {
			int size = gameScreen.l.size();
			for (int i = 0; i < size; i++) {
				Object obj = gameScreen.l.elementAt(i);
				if (obj instanceof class_vh) {
					class_vh vh = (class_vh) obj;
					if (isNpc(vh)) {
						if (class_yg.d(vh.cK - clickX) < 22 && class_yg.d(vh.cL - 20 - clickY) < 42) {
							gameScreen.r = vh; // Khóa target vào NPC ngay lập tức!
							class_acv.g = false;
							if (gameScreen.q != null) {
								int dist = class_yg.a((int) gameScreen.q.cK, (int) gameScreen.q.cL, (int) vh.cK, (int) vh.cL);
								if (dist <= 40) {
									class_acv.c[5] = true;
								}
							}
							return;
						}
					}
				}
			}
		}
	}

	/**
	 * Được gọi khi người dùng chủ động di chuyển (bấm phím điều hướng 2,4,6,8 / mũi tên
	 * hoặc click chuột/chạm màn hình để di chuyển).
	 * Đóng băng auto trong 5 giây, sau khi người chơi ngừng thao tác thì sau 5s tự động bật lại.
	 */
	public static void onUserManualMove() {
		class_abj gameScreen = class_acv.s;
		if (gameScreen != null && gameScreen.q != null) {
			// Nếu nhân vật đang bị choáng (cW), không tính là thao tác thủ công
			if (gameScreen.q.cW) {
				return;
			}
		}

		// Nếu auto đang bật hoặc trước đó đã kích hoạt: ghi nhớ để sau 5s tự động kích hoạt lại
		if (class_abj.au || class_abj.av || autoCombatKeepActive) {
			autoWasActiveBeforeManual = true;
		}

		// Tạm dừng auto và kéo dài thời gian đóng băng: 5 giây tính từ thao tác cuối cùng
		long now = System.currentTimeMillis();
		manualFreezeUntil = now + 5000L;

		// Tạm thời tắt cờ au của client để không tranh chấp quyền điều khiển với người chơi
		class_abj.au = false;
		isRegulating = false;
		if (gameScreen != null && gameScreen.r != null) {
			// Chỉ bỏ target quái vật cũ, TUYỆT ĐỐI KHÔNG bỏ target nếu đang chọn NPC
			if (!isNpc(gameScreen.r)) {
				gameScreen.r = null;
			}
		}
	}

	public static boolean isAutoRunning() {
		if (!class_abj.au && !class_abj.av) {
			return false;
		}
		if (System.currentTimeMillis() < manualFreezeUntil) {
			return false;
		}
		return class_abj.au || class_abj.av;
	}

	/**
	 * Được gọi từ bytecode patch trong class_abj.b(class_vh, int)
	 * Cho phép nhân vật tự động di chuyển đuổi theo mục tiêu khi auto đánh.
	 * TUYỆT ĐỐI KHÔNG đuổi theo ra ngoài vùng 4 góc của bãi treo máy,
	 * và KHÔNG đuổi theo bất cứ mục tiêu nào khi đang trong trạng thái điều tiết về trung tâm.
	 */
	public static boolean shouldChase(Object target) {
		if (!isAutoRunning()) return false;
		if (target == null) return false;

		// Quái Tinh Anh: Nếu đã là mục tiêu đang đánh thì dù có chạy khỏi bãi cũng đuổi theo!
		if (globalConfig.isPrioritizeElite && (target instanceof class_bb) && ((class_bb) target).isElite) {
			class_abj gameScreen = class_acv.s;
			if (gameScreen != null && gameScreen.r == target) {
				return true;
			}
		}

		// Nếu đang trong trạng thái điều tiết về trung tâm -> không đuổi theo bất kỳ ai
		if (isRegulating) return false;

		if (target instanceof class_vh) {
			class_vh vh = (class_vh) target;
			class_abj gameScreen = class_acv.s;
			if (gameScreen != null && gameScreen.q != null) {
				// Với vật phẩm rơi: kiểm tra phạm vi mở rộng có vùng đệm loot
				if (vh instanceof class_ba) {
					if (!isInsideLootZone((int) vh.cK, (int) vh.cL)) {
						return false;
					}
				} else {
					// Quái vật BẮT BUỘC phải nằm bên trong phạm vi 4 góc bãi treo máy
					if (!isInsideZone((int) vh.cK, (int) vh.cL)) {
						return false;
					}
				}
				int dist = class_yg.a((int) gameScreen.q.cK, (int) gameScreen.q.cL, (int) vh.cK, (int) vh.cL);
				if (dist > (MAX_TARGET_DISTANCE + LOOT_BUFFER)) {
					return false; // Quá xa mục tiêu -> không kích hoạt đuổi
				}
			}
			if (vh.cF == 1 || vh.cY || vh instanceof class_ba) {
				return true;
			}
		}
		return false;
	}

	// =========================================================================
	// KHOANH VÙNG 4 GÓC BÃI TREO MÁY & ĐIỀU TIẾT VỀ TRUNG TÂM
	// =========================================================================
	public static short autoAnchorMapId = -1;
	public static int autoAnchorX = -1;
	public static int autoAnchorY = -1;
	public static final int ZONE_BOX_RADIUS_X = 130;  // Nửa chiều rộng bãi train (pixel)
	public static final int ZONE_BOX_RADIUS_Y = 110;  // Nửa chiều cao bãi train (pixel)
	public static final int MAX_TARGET_DISTANCE = 140; // Cự ly tối đa để phát hiện/tiếp cận mục tiêu trong bãi
	public static final int LOOT_BUFFER = 35;         // Vùng đệm nhặt đồ xung quanh (+35px) tránh bỏ sót item rơi ngoài rìa

	// Bán kính và bộ đếm thời gian cho cơ chế tuần tra qua lại tìm quái trong bãi
	public static final int PATROL_RADIUS_X = 65;
	public static final int PATROL_RADIUS_Y = 55;
	private static long lastPatrolTime = 0;
	private static int nextPatrolDelay = 2500;

	// Tham chiếu mảng phím tắt từ class_abj (được cập nhật mỗi tick từ Patch 0)
	public static int[] currentShortcutSlots = null;

	// Trạng thái điều tiết về trung tâm bãi
	public static boolean isRegulating = false;
	private static long lastRegulateMoveTime = 0;
	private static long lastBrokenWeaponTryTime = 0;

	/**
	 * Kiểm tra xem tọa độ (x, y) có nằm trọn vẹn trong vùng 4 góc của bãi treo máy hay không.
	 */
	public static boolean isInsideZone(int x, int y) {
		if (autoAnchorX == -1 || autoAnchorY == -1) {
			return true;
		}
		int minX = autoAnchorX - ZONE_BOX_RADIUS_X;
		int maxX = autoAnchorX + ZONE_BOX_RADIUS_X;
		int minY = autoAnchorY - ZONE_BOX_RADIUS_Y;
		int maxY = autoAnchorY + ZONE_BOX_RADIUS_Y;
		return (x >= minX && x <= maxX && y >= minY && y <= maxY);
	}

	/**
	 * Kiểm tra xem tọa độ vật phẩm rơi có nằm trong vùng bãi train mở rộng (kèm đệm LOOT_BUFFER) hay không.
	 */
	public static boolean isInsideLootZone(int x, int y) {
		if (autoAnchorX == -1 || autoAnchorY == -1) {
			return true;
		}
		int minX = autoAnchorX - (ZONE_BOX_RADIUS_X + LOOT_BUFFER);
		int maxX = autoAnchorX + (ZONE_BOX_RADIUS_X + LOOT_BUFFER);
		int minY = autoAnchorY - (ZONE_BOX_RADIUS_Y + LOOT_BUFFER);
		int maxY = autoAnchorY + (ZONE_BOX_RADIUS_Y + LOOT_BUFFER);
		return (x >= minX && x <= maxX && y >= minY && y <= maxY);
	}

	/**
	 * Kích hoạt chiêu thức thông minh theo thứ tự ưu tiên:
	 * Ưu tiên chiêu ô số 5 -> ô số 3 -> các chiêu khác (nếu có trên ô 7, 9)
	 * Khi các chiêu đặc biệt đang hồi chiêu hoặc không đủ mana, sử dụng chiêu thường (ô số 1) để giữ nhịp đánh.
	 * Đảm bảo chỉ kích hoạt duy nhất 1 chiêu mỗi tick để không bị chiêu thường độc chiếm vòng lặp phím.
	 */
	public static void triggerAutoAttack(class_abj gameScreen) {
		if (gameScreen == null || gameScreen.q == null) {
			class_acv.c[1] = true;
			return;
		}
		// Nếu vũ khí đã hỏng hoàn toàn (độ bền = 0):
		// Thử gõ 1 hit mỗi 2.5s để Server có cơ hội tự sửa bằng Thẻ Mua Bán (nếu có trong túi).
		// Tránh spam phím 30 FPS gây flood packet và spam chat khi hết xu/không có thẻ.
		if (MainCharInfo.getDoBen() <= 0) {
			long now = System.currentTimeMillis();
			if (now - lastBrokenWeaponTryTime >= 2500L) {
				lastBrokenWeaponTryTime = now;
				class_acv.c[1] = true;
			}
			return;
		}
		class_hw player = gameScreen.q;
		long now = System.currentTimeMillis();

		int[] priorityKeys = new int[]{5, 3, 7, 9};
		int page = class_abj.V;
		if (currentShortcutSlots != null && class_sc.a != null && page >= 0 && page < class_sc.a.length && class_sc.a[page] != null) {
			for (int i = 0; i < priorityKeys.length; i++) {
				int key = priorityKeys[i];
				if (key >= 0 && key < currentShortcutSlots.length) {
					int slotIdx = currentShortcutSlots[key];
					if (slotIdx >= 0 && slotIdx < class_sc.a[page].length) {
						class_gd gd = class_sc.a[page][slotIdx];
						if (gd != null && gd.a == 1) { // Là chiêu thức
							byte skillId = gd.b();
							if (skillId >= 0 && skillId < class_hw.aS.length && class_hw.aS[skillId] > 0) {
								if (player.aq != null && player.at != null && skillId < player.aq.length && skillId < player.at.length) {
									if (now - player.aq[skillId] > player.at[skillId]) {
										try {
											int mpCost = class_qz.b(skillId, (int) class_hw.aS[skillId]);
											if (player.bz < mpCost) {
												continue; // Không đủ mana, xét chiêu kế tiếp
											}
										} catch (Exception e) {}

										// Chiêu hợp lệ, đã hồi xong và đủ mana: Kích hoạt DUY NHẤT chiêu này!
										class_acv.c[1] = false;
										class_acv.c[3] = false;
										class_acv.c[5] = false;
										class_acv.c[key] = true;
										return;
									}
								}
							}
						}
					}
				}
			}
		}

		// Nếu tất cả các chiêu đặc biệt đều đang hồi chiêu hoặc hết mana: Đánh thường (ô số 1)
		class_acv.c[3] = false;
		class_acv.c[5] = false;
		class_acv.c[1] = true;
	}

	// Trạng thái tuần tra và duy trì auto
	public static int slot5Range = -1;
	public static boolean autoCombatKeepActive = false;
	private static long lastChaseTime = 0;
	private static boolean lastAuState = false;

	// Cơ chế chống kẹt đánh quái (anti-stuck)
	private static short lastAttackMobId = -1;
	private static int lastMobHp = -1;
	private static long mobAttackStartTime = 0;
	private static final java.util.Hashtable ignoredMobs = new java.util.Hashtable();

	// Trạng thái tự động nhặt vật phẩm ưu tiên số 1
	private static long lastItemMoveTime = 0;
	private static long lastPickupTime = 0;
	private static short lastAttemptItemId = -1;
	private static int pickupAttempts = 0;
	private static short lastMoveItemId = -1;
	private static int moveStuckCount = 0;
	private static int lastPlayerX = -1;
	private static int lastPlayerY = -1;
	private static final java.util.Hashtable ignoredItems = new java.util.Hashtable();

	/**
	 * Quét tìm vật phẩm rơi trên mặt đất (class_ba) gần nhất TRONG PHẠM VI KHOANH VÙNG BÃI TRAIN (kèm LOOT_BUFFER).
	 */
	private static class_ba findNearestDroppedItem(class_abj gameScreen, int originX, int originY, class_hw player) {
		Vector entityList = gameScreen.l;
		if (entityList == null) return null;

		class_ba nearest = null;
		int minDistance = Integer.MAX_VALUE;
		long now = System.currentTimeMillis();

		int size = entityList.size();
		for (int i = 0; i < size; i++) {
			Object obj = entityList.elementAt(i);
			if (obj instanceof class_ba) {
				class_ba item = (class_ba) obj;
				// Bỏ qua vật phẩm đã biến mất / đã bị nhặt
				if (item.cE) {
					continue;
				}
				// Kiểm tra danh sách tạm bỏ qua
				Long expire = (Long) ignoredItems.get(new Short(item.cG));
				if (expire != null) {
					if (now < expire.longValue()) {
						continue;
					} else {
						ignoredItems.remove(new Short(item.cG));
					}
				}
				// Bỏ qua trang bị nếu hành trang đã đầy
				if (item.cF == 3 && player.r()) {
					continue;
				}
				// Bỏ qua tiền nếu đã đạt giới hạn (> 100tr)
				if (item.cF == 4 && item.c == 0 && player.br > 100000000L) {
					continue;
				}

				// Vật phẩm BẮT BUỘC phải nằm trong phạm vi mở rộng của bãi train (kèm LOOT_BUFFER)
				if (!isInsideLootZone((int) item.cK, (int) item.cL)) {
					continue;
				}

				int distToPlayer = class_yg.a((int) item.cK, (int) item.cL, (int) player.cK, (int) player.cL);
				if (distToPlayer <= (MAX_TARGET_DISTANCE + LOOT_BUFFER) && distToPlayer < minDistance) {
					minDistance = distToPlayer;
					nearest = item;
				}
			}
		}
		return nearest;
	}

	// =========================================================================
	// CÁC HÀM HỖ TRỢ: QUÁI TINH ANH, TỰ ĐỘNG HỒI SINH, TỰ ĐỘNG BÁN ĐỒ CẤP THẤP
	// =========================================================================
	public static class_bb getEliteTarget(class_abj gameScreen) {
		if (!globalConfig.isPrioritizeElite || gameScreen == null) {
			return null;
		}
		// 1. NẾU ĐÃ TARGET QUÁI TINH ANH RỒI:
		// Dù quái có chạy ra khỏi bãi train thì VẪN ĐUỔI THEO TIÊU DIỆT CHO BẰNG ĐƯỢC!
		if (gameScreen.r instanceof class_bb) {
			class_bb currentMob = (class_bb) gameScreen.r;
			if (currentMob.isElite && currentMob.cV != 5 && currentMob.v > 0) {
				return currentMob;
			}
		}
		// 2. NẾU CHƯA TARGET: CHỈ quét tìm quái Tinh Anh XUẤT HIỆN TRONG BÃI TRAIN (tránh chạy loạn xạ)
		class_hw player = gameScreen.q;
		if (player == null) {
			return null;
		}
		return findNearestEliteMob(gameScreen, autoAnchorX, autoAnchorY, player);
	}

	public static class_bb findNearestEliteMob(class_abj gameScreen, int originX, int originY, class_hw player) {
		Vector entityList = gameScreen.l;
		if (entityList == null) return null;
		class_bb nearest = null;
		int minDistance = Integer.MAX_VALUE;
		int size = entityList.size();
		for (int i = 0; i < size; i++) {
			Object obj = entityList.elementAt(i);
			if (obj instanceof class_bb) {
				class_bb mob = (class_bb) obj;
				// CHỈ QUÉT QUÁI TINH ANH TRONG BÃI TRAIN (tránh nhân vật chạy loạn xạ khắp bản đồ)
				if (!mob.isElite || mob.cV == 5 || mob.v <= 0) {
					continue;
				}
				if (!isInsideZone((int) mob.cK, (int) mob.cL)) {
					continue;
				}
				int distToPlayer = class_yg.a((int) mob.cK, (int) mob.cL, (int) player.cK, (int) player.cL);
				if (distToPlayer <= MAX_TARGET_DISTANCE && distToPlayer < minDistance) {
					minDistance = distToPlayer;
					nearest = mob;
				}
			}
		}
		return nearest;
	}

	private static long playerDeadStartTime = 0;
	private static boolean spotReviveRequested = false;
	private static boolean homeReviveRequested = false;

	public static void resetAutoRevive() {
		playerDeadStartTime = 0;
		spotReviveRequested = false;
		homeReviveRequested = false;
	}

	public static void handleAutoRevive(class_abj gameScreen, class_hw player) {
		if (!globalConfig.isAutoRevive) {
			return;
		}
		long now = System.currentTimeMillis();
		if (playerDeadStartTime == 0) {
			playerDeadStartTime = now;
			spotReviveRequested = false;
			homeReviveRequested = false;
		}

		long deadDuration = now - playerDeadStartTime;
		// 1. Sau 1.5 giây kể từ lúc chết: thử hồi sinh tại chỗ bằng xu
		if (deadDuration >= 1500L && !spotReviveRequested) {
			spotReviveRequested = true;
			class_go.a().a((int) player.cG, (byte) 0, "", 1);
			class_acv.w = null; // Đóng bảng popup chết
		}

		// 2. Nếu sau 4.5 giây vẫn chưa sống lại (hết xu hoặc lỗi popup): tự động về làng hồi phục
		if (deadDuration >= 4500L && !homeReviveRequested) {
			homeReviveRequested = true;
			class_go.a().j(); // Về làng
			class_acv.w = null;
		}
	}

	private static long lastAutoSellTime = 0;

	public static void handleAutoSellLowEquip() {
		if (!globalConfig.isAutoSellLowEquip) {
			return;
		}
		long now = System.currentTimeMillis();
		if (now - lastAutoSellTime < 1500L) {
			return;
		}
		class_abj gameScreen = class_acv.s;
		if (gameScreen == null || gameScreen.q == null) return;
		class_hw player = gameScreen.q;
		int playerLv = player.N; // Level nhân vật
		if (playerLv <= 1) return;

		Vector bag = class_hw.bu; // Danh sách trang bị trong hành trang
		if (bag == null || bag.size() == 0) return;

		for (int i = 0; i < bag.size(); i++) {
			Object obj = bag.elementAt(i);
			if (obj instanceof class_ql) {
				class_ql ql = (class_ql) obj;
				class_yc tmpl = class_yi.b((int) ql.r);
				int equipLv = (ql.y != -1) ? ql.y : (tmpl != null ? tmpl.f : 0);

				// BỘ LỌC AN TOÀN BẢO VỆ TÀI SẢN:
				// 1. Chỉ bán trang bị có cấp độ thấp hơn nhân vật
				if (equipLv >= playerLv) continue;
				// 2. Tuyệt đối không bán đồ đã cường hóa (+1, +2, ...)
				if (ql.s > 0) continue;
				// 3. Tuyệt đối không bán đồ đã khảm ngọc
				if (ql.I > 0) continue;
				// 4. Tuyệt đối không bán đồ thuê / đồ có hạn ngày (ql.w là dayUse)
				if (ql.w > 0 || (tmpl != null && tmpl.h > 0)) continue;

				// Hợp lệ: Gửi yêu cầu bán trang bị lên server (Server tự cộng xu và trừ item)
				lastAutoSellTime = now;
				class_go.a().f(ql.i);
				return; // Bán 1 món mỗi 1.5s để server xử lý tuần tự an toàn
			}
		}
	}

	/**
	 * Xử lý quét quái khoanh vùng 4 góc, áp sát tấn công, nhặt đồ và điều tiết về trung tâm.
	 * Được gọi mỗi frame từ class_abj.b().
	 */
	public static void handleAutoCombatRoaming() {
		try {
			if (!isAutoRunning()) {
				lastAuState = false;
				autoAnchorMapId = -1;
				autoAnchorX = -1;
				autoAnchorY = -1;
				isRegulating = false;
				return;
			}
			class_abj gameScreen = class_acv.s;
			if (gameScreen == null || gameScreen.q == null) {
				return;
			}
			class_hw player = gameScreen.q;
			// Nếu nhân vật đã chết (cV == 3) hoặc đang bị khóa (dc) hoặc đang bị choáng/khống chế (cW)
			if (player.cV == 3 || player.dc || player.cW) {
				return;
			}

			// Nếu người chơi đang chủ động tương tác/chọn NPC: Tuyệt đối không can thiệp, không chuyển target
			if (gameScreen.r != null && isNpc(gameScreen.r)) {
				return;
			}

			// Khi vừa mới bật Auto hoặc khi chuyển sang map mới:
			// KHÓA CHẶT TÂM BÃI TRAIN (ANCHOR) DUY NHẤT 1 LẦN NGAY TẠI TỌA ĐỘ BẮT ĐẦU!
			if (autoAnchorX == -1 || autoAnchorMapId != gameScreen.aG) {
				autoAnchorMapId = gameScreen.aG;
				autoAnchorX = player.cK;
				autoAnchorY = player.cL;
				player.ag = autoAnchorX;
				player.ah = autoAnchorY;
				lastAttackMobId = -1;
				lastMobHp = -1;
				mobAttackStartTime = 0;
				isRegulating = false;
			}
			lastAuState = true;

			int originX = autoAnchorX;
			int originY = autoAnchorY;
			long now = System.currentTimeMillis();

			// =========================================================================
			// 0. QUÁI TINH ANH TRONG TOÀN KHU VỰC: ƯU TIÊN TỐI CAO
			// =========================================================================
			class_bb eliteTarget = null;
			if (globalConfig.isPrioritizeElite) {
				eliteTarget = getEliteTarget(gameScreen);
				if (eliteTarget != null) {
					// Khi có Quái Tinh Anh, hủy ngay trạng thái điều tiết về tâm để tập trung săn tinh anh!
					isRegulating = false;
				}
			}

			// =========================================================================
			// A. NẾU ĐANG TRONG TRẠNG THÁI ĐIỀU TIẾT VỀ TRUNG TÂM (CHỈ KHI KHÔNG CÓ TINH ANH):
			// "Nếu đã vào trạng thái điều tiết trở lại rồi thì không target thêm thứ gì cho đến khi trở lại xong xuôi."
			// =========================================================================
			if (isRegulating && eliteTarget == null) {
				if (gameScreen.r != null && !isNpc(gameScreen.r)) {
					gameScreen.r = null; // Tuyệt đối không target quái vật khi đang điều tiết
				}
				int distToCenter = class_yg.a((int) player.cK, (int) player.cL, originX, originY);
				// Khi đã bước vào lại an toàn bên trong bãi train (hoặc cự ly tới tâm <= 60px): Hoàn thành điều tiết!
				// Không ghim cứng nhân vật vào 1 điểm sát sạt 20px làm mất nhịp farm tự nhiên.
				if (distToCenter <= 60 || isInsideZone((int) player.cK, (int) player.cL)) {
					isRegulating = false;
					((class_sc) player).s = null;
				} else {
					// Vẫn đang trên đường về tâm bãi
					if (((class_sc) player).s == null || now - lastRegulateMoveTime >= 400) {
						lastRegulateMoveTime = now;
						gameScreen.movePlayer(originX, originY);
					}
					return; // Dừng xử lý, chỉ tập trung chạy về tâm
				}
			}

			// =========================================================================
			// B. KHI KHÔNG TRONG TRẠNG THÁI ĐIỀU TIẾT:
			// TỰ ĐỘNG NHẶT VẬT PHẨM (CHỈ KHI KHÔNG CÓ QUÁI TINH ANH ĐANG CẦN ĐÁNH)
			// (Tránh việc đang đánh Tinh Anh mà rớt thuốc/xu lại bỏ dở chạy đi nhặt rác)
			// =========================================================================
			if (globalConfig.isAutoPickup && eliteTarget == null) {
				class_ba nearestItem = findNearestDroppedItem(gameScreen, originX, originY, player);
				if (nearestItem != null) {
					gameScreen.r = nearestItem;
					int distToItem = class_yg.a((int) player.cK, (int) player.cL, (int) nearestItem.cK, (int) nearestItem.cL);

					if (distToItem > 35) {
						if (((class_sc) player).s == null || now - lastItemMoveTime >= 500) {
							lastItemMoveTime = now;
							if (nearestItem.cG == lastMoveItemId) {
								if (player.cK == lastPlayerX && player.cL == lastPlayerY) {
									moveStuckCount++;
									if (moveStuckCount >= 8) {
										ignoredItems.put(new Short(nearestItem.cG), new Long(now + 15000L));
										gameScreen.r = null;
										moveStuckCount = 0;
										lastMoveItemId = -1;
										return;
									}
								} else {
									moveStuckCount = 0;
								}
							} else {
								lastMoveItemId = nearestItem.cG;
								moveStuckCount = 0;
							}
							lastPlayerX = player.cK;
							lastPlayerY = player.cL;
							gameScreen.movePlayer((int) nearestItem.cK, (int) nearestItem.cL);
						}
					} else {
						((class_sc) player).s = null;
						player.D = class_yg.b((class_vh) player, (class_vh) nearestItem);
						if (now - lastPickupTime >= 300) {
							lastPickupTime = now;
							if (nearestItem.cG == lastAttemptItemId) {
								pickupAttempts++;
								if (pickupAttempts > 10) {
									ignoredItems.put(new Short(nearestItem.cG), new Long(now + 10000L));
									gameScreen.r = null;
									pickupAttempts = 0;
									lastAttemptItemId = -1;
									return;
								}
							} else {
								lastAttemptItemId = nearestItem.cG;
								pickupAttempts = 1;
							}
							gameScreen.D.a(nearestItem.cF, nearestItem.cG);
						}
					}
					return; // Đang nhặt đồ, hoàn tất nhặt đồ trước
				} else {
					if (gameScreen.r instanceof class_ba) {
						gameScreen.r = null;
						((class_sc) player).s = null;
					}
				}
			}

			// =========================================================================
			// C. XỬ LÝ MỤC TIÊU QUÁI VẬT (ƯU TIÊN TINH ANH TUYỆT ĐỐI HOẶC TIẾP TỤC ĐÁNH DỞ)
			// =========================================================================
			boolean isRanged = (player.aO == 2 || player.aO == 4);
			int attackRange = isRanged ? 70 : 25;

			// Nếu có Quái Tinh Anh và mục tiêu hiện tại chưa phải là Quái Tinh Anh: CHUYỂN NGAY!
			if (eliteTarget != null && gameScreen.r != eliteTarget) {
				gameScreen.r = eliteTarget;
				lastAttackMobId = -1;
				((class_sc) player).s = null;
			}

			class_vh currentTarget = gameScreen.r;
			if (currentTarget != null) {
				if (currentTarget instanceof class_ba) {
					return;
				}
				if (currentTarget.cF == 1 && currentTarget instanceof class_bb) {
					class_bb mob = (class_bb) currentTarget;
					int distToMob = class_yg.a((int) player.cK, (int) player.cL, (int) mob.cK, (int) mob.cL);

					// --- XỬ LÝ RIÊNG CHO QUÁI TINH ANH ---
					if (mob.isElite) {
						// Quái Tinh Anh chỉ hủy target khi ĐÃ CHẾT!
						if (mob.cV == 5 || mob.v <= 0) {
							gameScreen.r = null;
							currentTarget = null;
							((class_sc) player).s = null;
							lastAttackMobId = -1;
							eliteTarget = null;
						} else {
							// Khóa chặt mục tiêu Quái Tinh Anh: KHÔNG timeout 4.5s vào ignoredMobs, KHÔNG giới hạn cự ly
							if (mob.cG != lastAttackMobId) {
								lastAttackMobId = mob.cG;
								lastMobHp = mob.v;
								mobAttackStartTime = now;
							} else {
								if (mob.v < lastMobHp) {
									lastMobHp = mob.v;
									mobAttackStartTime = now;
								}
							}

							if (distToMob > attackRange) {
								if (((class_sc) player).s == null || now - lastChaseTime >= 400) {
									lastChaseTime = now;
									gameScreen.movePlayer((int) mob.cK, (int) mob.cL);
								}
							} else {
								((class_sc) player).s = null;
								triggerAutoAttack(gameScreen);
							}
							return; // Luôn tập trung đánh Quái Tinh Anh cho đến khi chết hẳn!
						}
					} else {
						// --- XỬ LÝ CHO QUÁI THƯỜNG ---
						// Nếu quái đã ra ngoài 4 góc hoặc đã chết hoặc quá xa: Hủy target!
						if (!isInsideZone((int) mob.cK, (int) mob.cL) || mob.cV == 5 || mob.v <= 0 || distToMob > MAX_TARGET_DISTANCE) {
							gameScreen.r = null;
							currentTarget = null;
							((class_sc) player).s = null;
							lastAttackMobId = -1;
						} else {
							// Quái thường còn sống và nằm trong 4 góc: tiếp tục đánh nốt con quái này
							if (mob.cG == lastAttackMobId) {
								if (mob.v < lastMobHp) {
									lastMobHp = mob.v;
									mobAttackStartTime = now;
								} else if (now - mobAttackStartTime >= 3500L) {
									if (distToMob > 18) {
										gameScreen.movePlayer((int) mob.cK, (int) mob.cL);
									}
									if (now - mobAttackStartTime >= 4500L) {
										ignoredMobs.put(new Short(mob.cG), new Long(now + 8000L));
										gameScreen.r = null;
										currentTarget = null;
										((class_sc) player).s = null;
										lastAttackMobId = -1;
										return;
									}
								}
							} else {
								lastAttackMobId = mob.cG;
								lastMobHp = mob.v;
								mobAttackStartTime = now;
							}

							if (distToMob > attackRange) {
								if (((class_sc) player).s == null || now - lastChaseTime >= 500) {
									lastChaseTime = now;
									gameScreen.movePlayer((int) mob.cK, (int) mob.cL);
								}
							} else {
								((class_sc) player).s = null;
								triggerAutoAttack(gameScreen);
							}
							return; // Đang đánh dở quái, không ngắt quãng giữa chừng
						}
					}
				}
			}

			// =========================================================================
			// D. HOẠT ĐỘNG ĐIỀU TIẾT VỀ TRUNG TÂM (LÀM SAU CÙNG)
			// Sau khi không còn nhặt đồ dở và không còn đánh quái dở:
			// CHỈ kích hoạt khi KHÔNG có Quái Tinh Anh và người chơi ở ngoài 4 góc bãi train!
			// =========================================================================
			if (eliteTarget == null && !isInsideZone((int) player.cK, (int) player.cL)) {
				isRegulating = true;
				if (gameScreen.r != null && !isNpc(gameScreen.r)) {
					gameScreen.r = null;
				}
				((class_sc) player).s = null;
				lastAttackMobId = -1;
				lastRegulateMoveTime = now;
				gameScreen.movePlayer(originX, originY);
				return;
			}

			// =========================================================================
			// E. TÌM QUÁI MỚI (ƯU TIÊN TINH ANH, NẾU KHÔNG CÓ THÌ QUÉT QUÁI THƯỜNG TRONG 4 GÓC)
			// =========================================================================
			if (eliteTarget != null) {
				gameScreen.r = eliteTarget;
				((class_sc) player).s = null;
				lastAttackMobId = -1;
				int distToMob = class_yg.a((int) player.cK, (int) player.cL, (int) eliteTarget.cK, (int) eliteTarget.cL);
				if (distToMob > attackRange) {
					lastChaseTime = now;
					gameScreen.movePlayer((int) eliteTarget.cK, (int) eliteTarget.cL);
				} else {
					triggerAutoAttack(gameScreen);
				}
				return;
			}

			Vector entityList = gameScreen.l;
			if (entityList != null) {
				class_bb nearestMob = null;
				int minDistance = Integer.MAX_VALUE;

				int size = entityList.size();
				for (int i = 0; i < size; i++) {
					Object obj = entityList.elementAt(i);
					if (obj instanceof class_bb) {
						class_bb mob = (class_bb) obj;
						if (mob.cV == 5 || mob.v <= 0) {
							continue;
						}
						Long expire = (Long) ignoredMobs.get(new Short(mob.cG));
						if (expire != null) {
							if (now < expire.longValue()) {
								continue;
							} else {
								ignoredMobs.remove(new Short(mob.cG));
							}
						}

						// BẮT BUỘC QUÁI PHẢI NẰM TRONG VÙNG 4 GÓC CỦA BÃI TRAIN
						if (!isInsideZone((int) mob.cK, (int) mob.cL)) {
							continue;
						}

						int distToPlayer = class_yg.a((int) mob.cK, (int) mob.cL, (int) player.cK, (int) player.cL);
						if (distToPlayer <= MAX_TARGET_DISTANCE && distToPlayer < minDistance) {
							minDistance = distToPlayer;
							nearestMob = mob;
						}
					}
				}

				if (nearestMob != null) {
					gameScreen.r = nearestMob;
					lastAttackMobId = nearestMob.cG;
					lastMobHp = nearestMob.v;
					mobAttackStartTime = now;
					int distToMob = class_yg.a((int) player.cK, (int) player.cL, (int) nearestMob.cK, (int) nearestMob.cL);
					if (distToMob > attackRange) {
						lastChaseTime = now;
						gameScreen.movePlayer((int) nearestMob.cK, (int) nearestMob.cL);
					} else {
						((class_sc) player).s = null;
						triggerAutoAttack(gameScreen);
					}
					return;
				}
			}

			// =========================================================================
			// F. BÃI TRỐNG (KHÔNG CÓ QUÁI):
			// Nếu nhân vật vẫn đang ở bên trong vùng bãi train -> DI CHUYỂN QUA LẠI TUẦN TRA TÌM QUÁI
			// Giữ logic an toàn trong bán kính khoanh vùng (PATROL_RADIUS), không đi ra ngoài bãi.
			// =========================================================================
			if (isInsideZone((int) player.cK, (int) player.cL)) {
				// Nếu nhân vật đang bước đi tuần tra thì cứ để đi tiếp
				if (((class_sc) player).s != null) {
					// Đang di chuyển tìm quái
				} else {
					// Đã đến điểm trước đó hoặc đang đứng chờ: Đợi delay rồi chọn điểm tuần tra mới
					if (now - lastPatrolTime >= nextPatrolDelay) {
						lastPatrolTime = now;
						nextPatrolDelay = 2200 + (int)(Math.random() * 1500); // 2.2s - 3.7s

						// Chọn tọa độ tuần tra ngẫu nhiên quanh tâm bãi train (bán kính an toàn)
						int offsetX = (int)(Math.random() * (PATROL_RADIUS_X * 2 + 1)) - PATROL_RADIUS_X;
						int offsetY = (int)(Math.random() * (PATROL_RADIUS_Y * 2 + 1)) - PATROL_RADIUS_Y;
						int targetX = originX + offsetX;
						int targetY = originY + offsetY;

						// Đảm bảo tuyệt đối điểm tuần tra nằm trong bãi
						if (isInsideZone(targetX, targetY)) {
							gameScreen.movePlayer(targetX, targetY);
						}
					}
				}
			} else {
				// Đã lọt ra ngoài bãi -> điều hướng nhẹ nhàng về phía tâm
				int distToCenter = class_yg.a((int) player.cK, (int) player.cL, originX, originY);
				if (distToCenter > 60) {
					if (((class_sc) player).s == null || now - lastRegulateMoveTime >= 600) {
						lastRegulateMoveTime = now;
						gameScreen.movePlayer(originX, originY);
					}
				} else {
					((class_sc) player).s = null;
				}
			}
		} catch (Exception e) {
			// Bắt ngoại lệ an toàn, không bao giờ để crash game loop
		}
	}
}
