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

			// 2. Đường line ngăn cách (Dòng 2)
			int lineY = startY + 14;
			g.setColor(0x3B4252);
			g.drawLine(startX - 2, lineY + 1, startX + maxLeftW + 2, lineY + 1);

			// 3. Danh sách các hiệu ứng Effect (Buff màu xanh lá class_d.j[1], Debuff màu đỏ class_d.j[2])
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
}

