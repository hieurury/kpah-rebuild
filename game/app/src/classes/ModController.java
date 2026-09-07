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

	public static void update() {
//		class_abj.as = 100;
		// 1. Kiểm tra nếu người dùng chủ động bấm phím di chuyển (2, 4, 6, 8 hoặc mũi tên điều hướng)
		if (class_acv.c[2] || class_acv.c[4] || class_acv.c[6] || class_acv.c[8]
				|| class_acv.e[2] || class_acv.e[4] || class_acv.e[6] || class_acv.e[8]) {
			onUserManualMove();
			return;
		}

		doAutoGame();
		handleAutoCombatRoaming();
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
		if (!globalConfig.isAutoPickup) return orig;
		if (context instanceof class_ba) {
			return orig / 4;   // đồ rơi: rút ngắn khoảng cách => ưu tiên tối cao
		}
		if (context instanceof class_vh && ((class_vh) context).cF == 1) {
			return orig * 4;   // quái vật: tăng khoảng cách => giảm ưu tiên
		}
		return orig;
	}

	/**
	 * Được gọi khi người dùng chủ động di chuyển (bấm phím điều hướng 2,4,6,8 / mũi tên
	 * hoặc click chuột/chạm màn hình để di chuyển).
	 * Tắt hoàn toàn tự động di chuyển & xóa dấu tích tọa độ cũ để nhân vật không bao giờ bị kéo giật ngược về chỗ cũ.
	 */
	public static void onUserManualMove() {
		class_abj.au = false; // Tắt Auto đánh
		class_abj.av = false; // Tắt cờ cấu hình auto (tránh class_hw tự động kích hoạt lại au)
		class_abj gameScreen = class_acv.s;
		if (gameScreen != null) {
			gameScreen.r = null; // Bỏ target mục tiêu
			if (gameScreen.q != null) {
				// Cập nhật tâm bãi train về vị trí hiện tại để xóa hoàn toàn dấu tích tọa độ cũ
				gameScreen.q.ag = gameScreen.q.cK;
				gameScreen.q.ah = gameScreen.q.cL;
			}
		}
	}

	/**
	 * Được gọi từ bytecode patch trong class_abj.b(class_vh, int)
	 * Cho phép nhân vật tự động di chuyển đuổi theo mục tiêu khi auto đánh,
	 * áp dụng cho cả quái vật (cF == 1) lẫn mục tiêu PK (cY == true).
	 * Nếu mục tiêu ở quá xa (vượt quá MAX_TARGET_DISTANCE) -> không đuổi.
	 */
	public static boolean shouldChase(Object target) {
		if (!class_abj.au) return false;
		if (target == null) return false;
		if (target instanceof class_vh) {
			class_vh vh = (class_vh) target;
			class_abj gameScreen = class_acv.s;
			if (gameScreen != null && gameScreen.q != null) {
				int dist = class_yg.a((int) gameScreen.q.cK, (int) gameScreen.q.cL, (int) vh.cK, (int) vh.cL);
				if (dist > MAX_TARGET_DISTANCE) {
					return false; // Quá xa mục tiêu -> không kích hoạt đuổi
				}
			}
			if (vh.cF == 1 || vh.cY || vh instanceof class_ba) {
				return true;
			}
		}
		return false;
	}

	// Trạng thái tuần tra và đuổi quái khi auto đánh
	public static int slot5Range = -1; // Tầm chiêu ô số 5 trích xuất tự động từ game
	private static long lastPatrolTime = 0;
	private static long lastChaseTime = 0;
	private static int patrolStep = 0;
	private static boolean lastAuState = false;

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

	public static final int MAX_TARGET_DISTANCE = 140; // Khoảng cách tối đa để bám đuổi target (pixel)
	public static final int MAX_ROAM_RADIUS = 200;      // Bán kính tối đa của bãi train (pixel)
	public static final int PATROL_RADIUS = 75;         // Bán kính tuần tra quanh tâm bãi khi hết quái

	/**
	 * Quét tìm vật phẩm rơi trên mặt đất (class_ba) gần nhất trong phạm vi bãi train.
	 * Bỏ qua các vật phẩm:
	 * - Đã hết hạn hoặc đã nhặt (cE == true)
	 * - Tạm thời bị bỏ qua (kẹt / hành trang đầy / vật phẩm của người khác)
	 * - Trang bị khi hành trang đã đầy
	 * - Tiền khi túi tiền đã vượt giới hạn
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

				// Khoảng cách từ vật phẩm tới tâm bãi train & tới người chơi
				int distToOrigin = class_yg.a((int) item.cK, (int) item.cL, originX, originY);
				int distToPlayer = class_yg.a((int) item.cK, (int) item.cL, (int) player.cK, (int) player.cL);

				// Vật phẩm phải nằm trong phạm vi bãi train
				if (distToOrigin <= MAX_ROAM_RADIUS && distToPlayer <= MAX_ROAM_RADIUS + 60) {
					if (distToPlayer < minDistance) {
						minDistance = distToPlayer;
						nearest = item;
					}
				}
			}
		}
		return nearest;
	}

	/**
	 * Xử lý quét quái mở rộng, đuổi theo mục tiêu và tuần tra di chuyển quanh bãi khi bãi trống quái.
	 * Được gọi mỗi frame từ class_abj.b().
	 */
	public static void handleAutoCombatRoaming() {
		try {
			if (!class_abj.au) {
				lastAuState = false;
				return;
			}
			class_abj gameScreen = class_acv.s;
			if (gameScreen == null || gameScreen.q == null) {
				return;
			}
			class_hw player = gameScreen.q;
			// Nếu nhân vật đã chết (cV == 3) hoặc đang bị khóa
			if (player.cV == 3 || player.dc) {
				return;
			}

			// Khi vừa mới bật Auto (chuyển trạng thái từ false -> true):
			// Thiết lập tâm bãi train NGAY TẠI vị trí hiện tại của nhân vật
			if (!lastAuState || (player.ag == 0 && player.ah == 0)) {
				player.ag = player.cK;
				player.ah = player.cL;
			}
			lastAuState = true;

			int originX = player.ag;
			int originY = player.ah;

			// Kiểm tra nếu nhân vật đã tiến quá xa khỏi tâm bãi train
			int distPlayerToOrigin = class_yg.a((int) player.cK, (int) player.cL, originX, originY);
			if (distPlayerToOrigin > MAX_ROAM_RADIUS) {
				// Bỏ target và quay trở lại tâm bãi train
				gameScreen.r = null;
				((class_sc) player).s = null;
				gameScreen.movePlayer(originX, originY);
				return;
			}

			// =========================================================================
			// 0. ƯU TIÊN SỐ 1: TỰ ĐỘNG NHẶT VẬT PHẨM TRÊN ĐẤT (class_ba)
			// Khi bật AutoPickup, vật phẩm là ưu tiên tối cao số 1:
			// Quét và tiến hành di chuyển đến nhặt trước khi đánh bất kỳ quái nào.
			// =========================================================================
			if (globalConfig.isAutoPickup) {
				class_ba nearestItem = findNearestDroppedItem(gameScreen, originX, originY, player);
				if (nearestItem != null) {
					// Khóa mục tiêu vào vật phẩm
					gameScreen.r = nearestItem;
					int distToItem = class_yg.a((int) player.cK, (int) player.cL, (int) nearestItem.cK, (int) nearestItem.cL);
					long now = System.currentTimeMillis();

					if (distToItem > 35) {
						// Ở xa hơn tầm nhặt (> 35px): Tiến hành di chuyển áp sát vật phẩm
						if (((class_sc) player).s == null || now - lastItemMoveTime >= 500) {
							lastItemMoveTime = now;
							// Kiểm tra phát hiện kẹt đường khi đi nhặt vật phẩm
							if (nearestItem.cG == lastMoveItemId) {
								if (player.cK == lastPlayerX && player.cL == lastPlayerY) {
									moveStuckCount++;
									if (moveStuckCount >= 5) {
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
						// Đã trong cự ly nhặt (<= 35px): Dừng di chuyển, hướng về vật phẩm và gửi gói tin nhặt đồ
						((class_sc) player).s = null;
						player.D = class_yg.b((class_vh) player, (class_vh) nearestItem);
						if (now - lastPickupTime >= 300) {
							lastPickupTime = now;
							if (nearestItem.cG == lastAttemptItemId) {
								pickupAttempts++;
								if (pickupAttempts > 6) {
									// Thử 6 lần không nhặt được (đồ người khác hoặc lỗi) -> tạm bỏ qua 15s
									ignoredItems.put(new Short(nearestItem.cG), new Long(now + 15000L));
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
					// Dừng tại đây: TUYỆT ĐỐI KHÔNG tấn công quái hay tuần tra khi có vật phẩm cần nhặt
					return;
				} else {
					// Đã nhặt xong hoặc không có vật phẩm nào trong phạm vi
					if (gameScreen.r instanceof class_ba) {
						gameScreen.r = null;
						((class_sc) player).s = null;
					}
				}
			}

			// Xác định tầm đánh phù hợp:
			// 1. Ưu tiên lấy từ tầm chiêu ô số 5 trích xuất được từ client
			// 2. Nếu không có hoặc tầm chiêu <= 40px, dùng cự ly chuẩn hợp lý (cận chiến 52px, đánh xa 90px)
			// Giúp nhân vật giữ cự ly đẹp mắt, không bao giờ chạy dí sát rạt vào người quái.
			int attackRange;
			if (slot5Range > 40) {
				// Trừ hao 8px để chắc chắn chạm quái nhưng không bị áp sát rạt
				attackRange = slot5Range - 8;
			} else {
				attackRange = (player.aO == 2 || player.aO == 3) ? 90 : 52;
			}

			// 1. Kiểm tra mục tiêu hiện tại
			class_vh currentTarget = gameScreen.r;
			if (currentTarget != null) {
				if (currentTarget instanceof class_ba) {
					// Đang nhặt đồ rơi -> không can thiệp
					return;
				}
				if (currentTarget.cF == 1 && currentTarget instanceof class_bb) {
					class_bb mob = (class_bb) currentTarget;
					int distToMob = class_yg.a((int) player.cK, (int) player.cL, (int) mob.cK, (int) mob.cL);
					int distMobToOrigin = class_yg.a((int) mob.cK, (int) mob.cL, originX, originY);

					// BỎ TARGET NẾU:
					// - Quái đã chết (cV == 5 hoặc máu <= 0)
					// - Hoặc TIẾN QUÁ XA MỤC TIÊU TARGET (distToMob > MAX_TARGET_DISTANCE)
					// - Hoặc quái chạy ra ngoài bán kính bãi train (distMobToOrigin > MAX_ROAM_RADIUS)
					if (mob.cV == 5 || mob.v <= 0 || distToMob > MAX_TARGET_DISTANCE || distMobToOrigin > MAX_ROAM_RADIUS) {
						gameScreen.r = null;
						currentTarget = null;
						((class_sc) player).s = null; // Hủy bước chạy đuổi theo quái
					} else {
						// Quái còn sống và trong tầm cho phép: Kiểm tra cự ly tấn công
						if (distToMob > attackRange) {
							// Ở XA HƠN TẦM ĐÁNH -> TỰ ĐỘNG DI CHUYỂN ÁP SÁT QUÁI
							long now = System.currentTimeMillis();
							if (((class_sc) player).s == null || now - lastChaseTime >= 600) {
								lastChaseTime = now;
								gameScreen.movePlayer((int) mob.cK, (int) mob.cL);
							}
						} else {
							// ĐÃ VÀO TẦM ĐÁNH -> DỪNG CHẠY VÀ KÍCH HOẠT PHÍM ĐÁNH
							((class_sc) player).s = null;
							class_acv.c[1] = true;
							class_acv.c[3] = true;
							class_acv.c[5] = true;
						}
						return;
					}
				}
			}

			// 2. Nếu chưa có target hợp lệ: Quét tìm quái còn sống gần nhất trong bán kính cho phép
			Vector entityList = gameScreen.l;
			if (entityList != null) {
				class_bb nearestMob = null;
				int minDistance = Integer.MAX_VALUE;

				int size = entityList.size();
				for (int i = 0; i < size; i++) {
					Object obj = entityList.elementAt(i);
					if (obj instanceof class_bb) {
						class_bb mob = (class_bb) obj;
						// Bỏ qua quái đã chết
						if (mob.cV == 5 || mob.v <= 0) {
							continue;
						}
						// Khoảng cách từ quái tới tâm bãi train & tới người chơi
						int distToOrigin = class_yg.a((int) mob.cK, (int) mob.cL, originX, originY);
						int distToPlayer = class_yg.a((int) mob.cK, (int) mob.cL, (int) player.cK, (int) player.cL);

						// Chỉ chọn quái nếu nằm trong bán kính bãi VÀ trong tầm khoảng cách cho phép
						if (distToOrigin <= MAX_ROAM_RADIUS && distToPlayer <= MAX_TARGET_DISTANCE) {
							if (distToPlayer < minDistance) {
								minDistance = distToPlayer;
								nearestMob = mob;
							}
						}
					}
				}

				if (nearestMob != null) {
					// Tìm thấy quái trong bãi -> Khóa mục tiêu ngay lập tức
					gameScreen.r = nearestMob;
					int distToMob = class_yg.a((int) player.cK, (int) player.cL, (int) nearestMob.cK, (int) nearestMob.cL);
					if (distToMob > attackRange) {
						// Áp sát quái
						lastChaseTime = System.currentTimeMillis();
						gameScreen.movePlayer((int) nearestMob.cK, (int) nearestMob.cL);
					} else {
						// Đã trong tầm đánh
						((class_sc) player).s = null;
						class_acv.c[1] = true;
						class_acv.c[3] = true;
						class_acv.c[5] = true;
					}
					return;
				}
			}

			// 3. NẾU KHÔNG CÓ QUÁI NÀO TRONG BÃI (BÃI TRỐNG):
			// Di chuyển tuần tra vòng quanh tâm bãi train
			long now = System.currentTimeMillis();
			// Nếu nhân vật đang di chuyển theo đường đi (((class_sc) player).s != null) thì để đi tiếp
			if (((class_sc) player).s != null) {
				return;
			}

			// Cách mỗi 2.5 giây đổi điểm tuần tra 1 lần
			if (now - lastPatrolTime >= 2500) {
				lastPatrolTime = now;
				patrolStep = (patrolStep + 1) % 6;

				// 6 điểm tuần tra hình lục giác xung quanh tâm bãi
				int destX = originX;
				int destY = originY;
				switch (patrolStep) {
					case 0: // Phải
						destX = originX + PATROL_RADIUS;
						destY = originY;
						break;
					case 1: // Dưới - Phải
						destX = originX + PATROL_RADIUS / 2;
						destY = originY + PATROL_RADIUS;
						break;
					case 2: // Dưới - Trái
						destX = originX - PATROL_RADIUS / 2;
						destY = originY + PATROL_RADIUS;
						break;
					case 3: // Trái
						destX = originX - PATROL_RADIUS;
						destY = originY;
						break;
					case 4: // Trên - Trái
						destX = originX - PATROL_RADIUS / 2;
						destY = originY - PATROL_RADIUS;
						break;
					case 5: // Trên - Phải
						destX = originX + PATROL_RADIUS / 2;
						destY = originY - PATROL_RADIUS;
						break;
				}

				// Ra lệnh cho nhân vật di chuyển tới điểm tuần tra bằng thuật toán pathfinding có sẵn
				gameScreen.movePlayer(destX, destY);
			}
		} catch (Exception e) {
			// Bắt ngoại lệ an toàn, không bao giờ để crash game loop
		}
	}
}
