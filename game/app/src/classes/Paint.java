package classes;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;

/*
 * Class này để in ấn, hiển thị các thông tin HUD, Buff/Debuff và Nhãn vật phẩm rơi
 */
public class Paint {

	public static void onPaint(Graphics g) {
		// Kiểm tra màn hình, nếu ở màn hình GameScreen thì mới hiển thị
		if (class_acv.q != class_acv.s || class_acv.s == null || class_acv.s.q == null) {
			return;
		}

		// Kích hoạt nhịp kiểm tra tự động bán trang bị cấp thấp liên tục mỗi frame
		ModController.handleAutoSellLowEquip();

		// 1. Hiển thị HUD thông tin Tọa độ & Buff/Debuff ở bên trái
		paintLeftInfoAndBuffs(g);

		// 2. Hiển thị HUD Độ bền chi tiết trang bị ở bên phải (dưới thanh target)
		paintRightEquipDurability(g);

		// 3. Hiển thị chỉ báo nhiệm vụ trên đầu NPC (!) hoặc (?)
		paintQuestMarkers(g);

		// 4. Hiển thị số liệu trạng thái nổi (HP đỏ, MP xanh dương, Độc tím) bay lên
		paintStatusPopups(g);

		// 5. Hiển thị đè thanh HP & MP bar chuẩn xác và số liệu bên phải thanh bar
		paintPlayerHpMpBar(g);
	}

	/**
	 * Vẽ HUD bên trái (dưới Avatar):
	 * Dòng 1: Tọa độ
	 * Dòng 2: Line ngăn cách
	 * Dòng 3+: Danh sách Effect (Xanh lá = buff, Đỏ = debuff, không hiển thị skill cooldown)
	 */
	private static void paintLeftInfoAndBuffs(Graphics g) {
		try {
			int startX = 4;
			int avatarH = class_abj.O != null ? class_abj.O.getHeight() : 32;
			int startY = Math.max(avatarH + 26, 58); // Nằm ngay dưới icon 12+ bên trái

			int maxLeftW = 90;

			// 1. Tọa độ bản đồ (Dòng 1)
			String mapPos = ModHelpers.getMapNameAndPosition();
			if (mapPos != null && mapPos.length() > 0) {
				String title = "Toạ độ: ";
				int titleW = class_d.j[0].a(title);
				int valW = class_d.j[3].a(mapPos);
				int totalW = titleW + valW;
				if (totalW > maxLeftW) {
					maxLeftW = totalW;
				}

				// Nền mờ tối giản
				g.setColor(0x000000);
				g.fillRect(startX - 2, startY, totalW + 4, 12);
				g.setColor(0x2E3842);
				g.drawRect(startX - 2, startY, totalW + 4, 12);

				// Tiêu đề chữ trắng
				class_d.j[0].a(g, title, startX, startY + 1, 0);
				// Tọa độ chữ cam/vàng
				class_d.j[3].a(g, mapPos, startX + titleW, startY + 1, 0);
			}

			// 2. Chỉ số Tấn công, Thủ ma, Thủ vật (kèm +bonus xanh lá cây)
			int statsY = startY + 14;
			class_sc mainChar = class_acv.s.q;
			if (mainChar != null) {
				int totalAttack = mainChar.K;
				int totalDefend = mainChar.L;
				int totalDefendMagic = mainChar.M;

				boolean hasTinhAnh = MainCharInfo.hasBuffTinhAnh();
				int bonusAttackTinhAnh = hasTinhAnh ? (int) (totalAttack - (totalAttack * 10L / 12L)) : 0;
				int bonusDefendTinhAnh = hasTinhAnh ? (int) (totalDefend - (totalDefend * 10L / 12L)) : 0;
				int bonusDefendMagicTinhAnh = hasTinhAnh ? (int) (totalDefendMagic - (totalDefendMagic * 10L / 12L)) : 0;

				int baseAttack = Math.max(0, totalAttack - bonusAttackTinhAnh);
				int baseDefend = Math.max(0, totalDefend - bonusDefendTinhAnh);
				int baseDefendMagic = Math.max(0, totalDefendMagic - bonusDefendMagicTinhAnh);

				// Bonus nội tại Pháp Sư Skill 5 (Hồi lực tiến)
				int bonusAttackPhapSu = 0;
				if (mainChar.aO == 2 && class_hw.aS != null && class_hw.aS.length > 5) {
					byte lvSkill5 = class_hw.aS[5];
					if (lvSkill5 > 0) {
						bonusAttackPhapSu = (int) ((long) mainChar.bz * (5 + (lvSkill5 - 1) * 2) / 100);
					}
				}

				int bonusAttack = bonusAttackTinhAnh + bonusAttackPhapSu;
				int bonusDefend = bonusDefendTinhAnh;
				int bonusDefendMagic = bonusDefendMagicTinhAnh;

				String[] labels = new String[]{"tấn công: " + baseAttack + " ", "thủ ma: " + baseDefendMagic + " ", "thủ vật: " + baseDefend + " "};
				String[] bonuses = new String[]{"+" + bonusAttack, "+" + bonusDefendMagic, "+" + bonusDefend};

				for (int i = 0; i < 3; i++) {
					int labelW = class_d.j[0].a(labels[i]);
					int bonusW = class_d.j[1].a(bonuses[i]);
					int rowW = labelW + bonusW;
					if (rowW > maxLeftW) {
						maxLeftW = rowW;
					}

					g.setColor(0x000000);
					g.fillRect(startX - 2, statsY, rowW + 4, 12);
					g.setColor(0x2E3842);
					g.drawRect(startX - 2, statsY, rowW + 4, 12);

					class_d.j[0].a(g, labels[i], startX, statsY + 1, 0);
					class_d.j[1].a(g, bonuses[i], startX + labelW, statsY + 1, 0);

					statsY += 14;
				}
			}

			// 3. Đường line ngăn cách (Dòng phân cách giữa Stats và Buffs)
			int lineY = statsY + 1;
			g.setColor(0x3B4252);
			g.drawLine(startX - 2, lineY, startX + maxLeftW + 2, lineY);

			// 4. Danh sách các hiệu ứng Effect (Buff màu xanh lá class_d.j[1], Debuff màu đỏ class_d.j[2])
			Vector buffs = MainCharInfo.getActiveBuffItems();
			if (buffs != null && buffs.size() > 0) {
				int effectY = lineY + 4;
				for (int i = 0; i < buffs.size(); i++) {
					MainCharInfo.BuffItem buff = (MainCharInfo.BuffItem) buffs.elementAt(i);
					if (buff == null) {
						continue;
					}

					String effectText = buff.name + (buff.timeStr != null && buff.timeStr.length() > 0 ? ": " + buff.timeStr : "");
					// class_d.j[1] là màu xanh lá cho Buff, class_d.j[2] là màu đỏ cho Debuff
					class_d font = buff.isDebuff ? class_d.j[2] : class_d.j[1];
					int textW = font.a(effectText);

					// Khung nền
					g.setColor(0x000000);
					g.fillRect(startX - 2, effectY, textW + 4, 12);
					g.setColor(buff.isDebuff ? 0x7F1D1D : 0x1B4332);
					g.drawRect(startX - 2, effectY, textW + 4, 12);

					font.a(g, effectText, startX, effectY + 1, 0);
					effectY += 14;
				}
			}
		} catch (Exception ignored) {}
	}

	/**
	 * Vẽ HUD Độ bền chi tiết các trang bị bên phải màn hình
	 * Nằm dưới góc trên bên phải (startY = 38) để tránh che thanh target mob
	 */
	private static void paintRightEquipDurability(Graphics g) {
		try {
			Vector equips = MainCharInfo.getEquipDurabilityList();
			if (equips == null || equips.size() == 0) {
				return;
			}

			// Tính chiều rộng tối đa của bảng độ bền
			int maxW = 50;
			for (int i = 0; i < equips.size(); i++) {
				MainCharInfo.EquipDurability eq = (MainCharInfo.EquipDurability) equips.elementAt(i);
				if (eq == null) continue;
				String label = eq.name + ": ";
				String valStr = eq.isBroken ? "0" : String.valueOf(eq.durable);
				int w = class_d.j[0].a(label) + (eq.isBroken ? class_d.j[2].a(valStr) : class_d.j[5].a(valStr));
				if (w > maxW) {
					maxW = w;
				}
			}

			int startX = class_acv.m - maxW - 4;
			int startY = 38; // Nằm ngay dưới thanh máu quái mục tiêu (khoảng y = 14-32)

			for (int i = 0; i < equips.size(); i++) {
				MainCharInfo.EquipDurability eq = (MainCharInfo.EquipDurability) equips.elementAt(i);
				if (eq == null) continue;

				String label = eq.name + ": ";
				String valStr = eq.isBroken ? "0" : String.valueOf(eq.durable);
				int titleW = class_d.j[0].a(label);

				// Nền mờ đen
				g.setColor(0x000000);
				g.fillRect(startX - 2, startY, maxW + 4, 12);

				// Viền: viền đỏ nếu trang bị hỏng, viền xám đen nếu bình thường
				g.setColor(eq.isBroken ? 0xFF1744 : 0x2E3842);
				g.drawRect(startX - 2, startY, maxW + 4, 12);

				// Tiêu đề màu trắng
				class_d.j[0].a(g, label, startX, startY + 1, 0);

				// Giá trị: số 0 màu đỏ tĩnh nếu hỏng, vàng/cam nếu bình thường
				if (eq.isBroken) {
					class_d.j[2].a(g, valStr, startX + titleW, startY + 1, 0);
				} else {
					class_d.j[5].a(g, valStr, startX + titleW, startY + 1, 0);
				}

				startY += 13;
			}
		} catch (Exception ignored) {}
	}

	/**
	 * Vẽ biểu tượng đánh dấu trên đầu NPC nhiệm vụ (Dấu chấm than [!] hoặc Dấu hỏi [?])
	 */
	private static void paintQuestMarkers(Graphics g) {
		try {
			if (class_acv.s == null || class_acv.s.l == null) {
				return;
			}
			class_do questMain = class_abj.aV;
			class_do questDaily = class_abj.aX;
			if (questMain == null && questDaily == null) {
				return;
			}

			Vector entities = class_acv.s.l;
			int camX = class_abj.j;
			int camY = class_abj.k;
			int scrW = class_acv.m;
			int scrH = class_acv.n;
			long time = System.currentTimeMillis();
			int bobbing = (int) ((time / 300) % 4); // Hiệu ứng nhấp nhô lơ lửng

			for (int i = 0; i < entities.size(); i++) {
				Object obj = entities.elementAt(i);
				if (!(obj instanceof class_vh)) {
					continue;
				}
				class_vh entity = (class_vh) obj;
				if (!entity.d_()) { // Phải là NPC
					continue;
				}

				int npcId = entity.g_();
				boolean isMainNpc = (questMain != null && questMain.d == npcId);
				boolean isDailyNpc = (questDaily != null && questDaily.d == npcId);

				if (!isMainNpc && !isDailyNpc) {
					continue;
				}

				int sx = entity.cK - camX;
				int sy = entity.cL - camY - 45 - bobbing;

				if (sx < -20 || sx > scrW + 20 || sy < -20 || sy > scrH + 20) {
					continue;
				}

				boolean isDone = (isMainNpc && (questMain.m == 1 || questMain.m == 2)) ||
				                 (isDailyNpc && (questDaily.m == 1 || questDaily.m == 2));

				String markerText = isDone ? "?" : "!";
				int bgColor = isDone ? 0x00C853 : (isMainNpc ? 0xFFB300 : 0x29B6F6);
				int badgeW = 12;
				int badgeH = 12;
				int bx = sx - badgeW / 2;
				int by = sy;

				// Viền bóng đen
				g.setColor(0x000000);
				g.fillRoundRect(bx - 1, by - 1, badgeW + 2, badgeH + 2, 4, 4);

				// Nền màu sáng
				g.setColor(bgColor);
				g.fillRoundRect(bx, by, badgeW, badgeH, 4, 4);

				// Viền sáng
				g.setColor(0xFFFFFF);
				g.drawRoundRect(bx, by, badgeW, badgeH, 4, 4);

				// Chữ ! hoặc ? dùng font chuẩn class_d.j[0]
				class_d.j[0].a(g, markerText, sx, by + 1, 2);
			}
		} catch (Exception ignored) {
		}
	}

	public static final int POPUP_HP = 1;
	public static final int POPUP_MP = 2;
	public static final int POPUP_POISON = 3;

	public static final int FONT_RED = 1;
	public static final int FONT_BLUE = 2;
	public static final int FONT_POISON = 3;

	private static javax.microedition.lcdui.Image imgFsRed;
	private static javax.microedition.lcdui.Image imgFsBlue;
	private static javax.microedition.lcdui.Image imgFsPoison;

	private static final String FS_CHARS = "0123456789+-%$:abcdefghijklmnopqrstuvwxyz@/";
	private static final byte[] FS_WIDTHS = new byte[]{
		5, 3, 5, 5, 5, 5, 5, 5, 5, 5, // 0-9
		5, 5,                         // + -
		7, 5, 3,                      // % $ :
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 6, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 5, 5, // a-z
		7,                            // @
		5                             // /
	};

	/**
	 * Vẽ chuỗi ký tự bằng Bitmap Font 8px pixel art (đồng bộ kích cỡ với font EXP)
	 */
	public static void drawFsString(Graphics g, int fontType, String str, int x, int y, int align) {
		if (str == null || str.length() == 0) return;
		try {
			javax.microedition.lcdui.Image fontImg = null;
			if (fontType == FONT_RED) {
				if (imgFsRed == null) imgFsRed = javax.microedition.lcdui.Image.createImage("/font/fs_red.png");
				fontImg = imgFsRed;
			} else if (fontType == FONT_BLUE) {
				if (imgFsBlue == null) imgFsBlue = javax.microedition.lcdui.Image.createImage("/font/fs_blue.png");
				fontImg = imgFsBlue;
			} else if (fontType == FONT_POISON) {
				if (imgFsPoison == null) imgFsPoison = javax.microedition.lcdui.Image.createImage("/font/fs_poison.png");
				fontImg = imgFsPoison;
			}

			if (fontImg == null) return;

			int totalW = 0;
			int len = str.length();
			for (int i = 0; i < len; i++) {
				char c = str.charAt(i);
				int idx = FS_CHARS.indexOf(c);
				if (idx >= 0) {
					totalW += FS_WIDTHS[idx] - 1;
				} else {
					totalW += 3;
				}
			}

			int startX = x;
			if ((align & Graphics.HCENTER) != 0 || align == 2) {
				startX = x - (totalW >> 1);
			} else if ((align & Graphics.RIGHT) != 0 || align == 1) {
				startX = x - totalW;
			}

			for (int i = 0; i < len; i++) {
				char c = str.charAt(i);
				int idx = FS_CHARS.indexOf(c);
				if (idx >= 0) {
					g.drawRegion(fontImg, 0, idx * 8, 7, 8, 0, startX, y, 20);
					startX += FS_WIDTHS[idx] - 1;
				} else {
					startX += 3;
				}
			}
		} catch (Exception ignored) {
		}
	}

	public static class StatusPopup {
		public String text;
		public int fontType;
		public int worldX;
		public int startY;
		public int frame;
		public int maxFrame = 25; // tồn tại ~1s (game chạy ~25fps)

		public StatusPopup(String text, int fontType, int worldX, int worldY) {
			this.text = text;
			this.fontType = fontType;
			this.worldX = worldX;
			this.startY = worldY;
			this.frame = 0;
		}
	}

	public static Vector statusPopups = new Vector();

	/**
	 * Thêm popup trạng thái nổi đồng bộ theo quy định:
	 * HP: "+10" hoặc "-10" màu Đỏ (fs_red)
	 * MP: "+10" hoặc "-10" màu Xanh dương (fs_blue)
	 * Độc: "-10" màu Tím (fs_poison)
	 * Kích cỡ chữ nhỏ 8px bằng đúng font EXP của game
	 */
	public static void addStatusPopup(int type, int value, int worldX, int worldY) {
		try {
			if (value == 0) return;
			if (statusPopups.size() > 25) {
				statusPopups.removeElementAt(0);
			}

			String text = (value > 0 ? "+" : "-") + Math.abs(value);
			int fontType;
			if (type == POPUP_HP) {
				fontType = FONT_RED; // Đỏ nguyên bản KPAH
			} else if (type == POPUP_MP) {
				fontType = FONT_BLUE; // Xanh dương nguyên bản KPAH
			} else {
				fontType = FONT_POISON; // Tím nguyên bản KPAH
			}

			statusPopups.addElement(new StatusPopup(text, fontType, worldX, worldY));
		} catch (Exception ignored) {
		}
	}

	public static void addPoisonDamage(int damage, int worldX, int worldY) {
		addStatusPopup(POPUP_POISON, -damage, worldX, worldY);
	}

	public static void paintStatusPopups(Graphics g) {
		try {
			if (statusPopups.isEmpty()) return;
			int camX = class_abj.j;
			int camY = class_abj.k;
			int scrW = class_acv.m;
			int scrH = class_acv.n;

			for (int i = statusPopups.size() - 1; i >= 0; i--) {
				StatusPopup p = (StatusPopup) statusPopups.elementAt(i);
				p.frame++;
				if (p.frame > p.maxFrame) {
					statusPopups.removeElementAt(i);
					continue;
				}

				// Bay lên trên: 25 frame bay lên khoảng 22 pixel
				int currentY = p.startY - (p.frame * 22 / p.maxFrame);
				int sx = p.worldX - camX;
				int sy = currentY - camY;

				if (sx < -60 || sx > scrW + 60 || sy < -30 || sy > scrH + 30) {
					continue;
				}

				// Vẽ số nổi bằng bộ font pixel art 8px đồng bộ với font EXP
				drawFsString(g, p.fontType, p.text, sx, sy, Graphics.HCENTER | Graphics.TOP);
			}
		} catch (Exception ignored) {
		}
	}

	/**
	 * Vẽ đè 2 thanh HP và MP bar chuẩn xác trên HUD Avatar:
	 * Tọa độ HP: X: 46, Y: 8, W: 44, H: 3
	 * Tọa độ MP: X: 46, Y: 18, W: 44, H: 3
	 * Ngăn chặn tuyệt đối tình trạng vẽ số âm, tràn số, hoặc nhấp nháy đầy/cạn
	 * Đồng thời hiển thị số liệu cụ thể (cur/max) bên phải 2 thanh bar bằng font pixel art 8px
	 */
	private static void paintPlayerHpMpBar(Graphics g) {
		try {
			if (class_acv.s == null || class_acv.s.q == null) return;
			class_sc player = class_acv.s.q;

			int maxHp = Math.max(1, player.w);
			int curHp = Math.max(0, player.v);
			int maxMp = Math.max(1, player.by);
			int curMp = Math.max(0, player.bz);

			final int barW = 44;
			final int barH = 3;

			// 1. Thanh HP (Y = 8)
			int hpW = 0;
			if (maxHp > 0) {
				long clampedHp = Math.max(0, Math.min((long) maxHp, (long) curHp));
				hpW = (int) (clampedHp * (long) barW / (long) maxHp);
				hpW = Math.max(0, Math.min(barW, hpW));
			}
			// Nền xám đen
			g.setColor(0x181818);
			g.fillRect(46, 8, barW, barH);
			if (hpW > 0) {
				// Máu đỏ tươi
				g.setColor(0xEE1122);
				g.fillRect(46, 8, hpW, barH);
				// Highlight trên
				g.setColor(0xFF6677);
				g.drawLine(46, 8, 46 + hpW - 1, 8);
			}

			// 2. Thanh MP (Y = 18)
			int mpW = 0;
			if (maxMp > 0) {
				long clampedMp = Math.max(0, Math.min((long) maxMp, (long) curMp));
				mpW = (int) (clampedMp * (long) barW / (long) maxMp);
				mpW = Math.max(0, Math.min(barW, mpW));
			}
			// Nền xám đen
			g.setColor(0x181818);
			g.fillRect(46, 18, barW, barH);
			if (mpW > 0) {
				// Mana xanh dương
				g.setColor(0x1177EE);
				g.fillRect(46, 18, mpW, barH);
				// Highlight trên
				g.setColor(0x66B3FF);
				g.drawLine(46, 18, 46 + mpW - 1, 18);
			}

			// 3. Hiển thị thông số cụ thể bên phải thanh bar (Task #94)
			// Không dùng hộp đen dày đặc che tầm nhìn; dùng font pixel art 8px có viền đen tự nhiên
			String hpStr = curHp + "/" + maxHp;
			String mpStr = curMp + "/" + maxMp;

			int textX = 94; // Ngay mép phải thanh HP/MP (kết thúc ở 90-92)
			// HP ghi bên phải thanh HP (Y = 6, khớp thanh HP ở Y = 8)
			drawFsString(g, FONT_RED, hpStr, textX, 6, 0);

			// MP ghi bên phải thanh MP (Y = 16, khớp thanh MP ở Y = 18)
			drawFsString(g, FONT_BLUE, mpStr, textX, 16, 0);
		} catch (Exception ignored) {
		}
	}
}

