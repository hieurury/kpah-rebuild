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

		// 4. Hiển thị số sát thương độc màu tím bay lên
		paintPoisonPopups(g);
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
				String valStr = eq.isBroken ? "0 (HỎNG)" : String.valueOf(eq.durable);
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
				String valStr = eq.isBroken ? "0 (HỎNG)" : String.valueOf(eq.durable);
				int titleW = class_d.j[0].a(label);

				boolean blink = eq.isBroken && (class_acv.l % 10 < 5);

				// Nền mờ
				g.setColor(blink ? 0x4A0000 : 0x000000);
				g.fillRect(startX - 2, startY, maxW + 4, 12);
				g.setColor(eq.isBroken ? 0xFF1744 : 0x2E3842);
				g.drawRect(startX - 2, startY, maxW + 4, 12);

				// Tiêu đề màu trắng
				class_d.j[0].a(g, label, startX, startY + 1, 0);

				// Giá trị: đỏ nhấp nháy nếu hỏng, vàng/cam nếu bình thường
				if (eq.isBroken) {
					if (blink) {
						class_d.j[2].a(g, valStr, startX + titleW, startY + 1, 0);
					}
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

	public static class PoisonPopup {
		public int damage;
		public int worldX;
		public int worldY;
		public int startY;
		public int frame;
		public int maxFrame = 25; // tồn tại ~1s (game chạy ~25fps)

		public PoisonPopup(int damage, int worldX, int worldY) {
			this.damage = damage;
			this.worldX = worldX;
			this.worldY = worldY;
			this.startY = worldY;
			this.frame = 0;
		}
	}

	public static Vector poisonPopups = new Vector();

	public static void addPoisonDamage(int damage, int worldX, int worldY) {
		try {
			if (damage <= 0) return;
			if (poisonPopups.size() > 20) {
				poisonPopups.removeElementAt(0);
			}
			poisonPopups.addElement(new PoisonPopup(damage, worldX, worldY));
		} catch (Exception ignored) {
		}
	}

	public static void paintPoisonPopups(Graphics g) {
		try {
			if (poisonPopups.isEmpty()) return;
			int camX = class_abj.j;
			int camY = class_abj.k;
			int scrW = class_acv.m;
			int scrH = class_acv.n;

			for (int i = poisonPopups.size() - 1; i >= 0; i--) {
				PoisonPopup p = (PoisonPopup) poisonPopups.elementAt(i);
				p.frame++;
				if (p.frame > p.maxFrame) {
					poisonPopups.removeElementAt(i);
					continue;
				}

				// Bay lên trên: 25 frame bay lên khoảng 20-25 pixel
				int currentY = p.startY - (p.frame * 22 / p.maxFrame);
				int sx = p.worldX - camX;
				int sy = currentY - camY;

				if (sx < -40 || sx > scrW + 40 || sy < -30 || sy > scrH + 30) {
					continue;
				}

				String str = "-" + p.damage;

				// Vẽ số sát thương màu tím với viền bóng đen/tím đậm
				g.setColor(0x2B003B);
				g.drawString(str, sx - 1, sy, Graphics.HCENTER | Graphics.TOP);
				g.drawString(str, sx + 1, sy, Graphics.HCENTER | Graphics.TOP);
				g.drawString(str, sx, sy - 1, Graphics.HCENTER | Graphics.TOP);
				g.drawString(str, sx, sy + 1, Graphics.HCENTER | Graphics.TOP);

				// Chữ màu tím rực rỡ neon
				g.setColor(0xDF5FFF);
				g.drawString(str, sx, sy, Graphics.HCENTER | Graphics.TOP);
			}
		} catch (Exception ignored) {
		}
	}
}

