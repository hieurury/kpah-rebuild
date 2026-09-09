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

		// 1. Hiển thị HUD thông tin Tọa độ & Độ bền vũ khí ở bên trái (dưới Avatar, tiêu đề trắng, giá trị cam/vàng)
		paintDurabilityAndPosition(g);

		// 2. Hiển thị các trạng thái Buff/Debuff thanh lịch dạng danh sách phía dưới HUD bên trái
		paintBuffBadges(g);

		// 3. Hiển thị chỉ báo nhiệm vụ trên đầu NPC (!) hoặc (?)
		paintQuestMarkers(g);
	}

	/**
	 * Vẽ HUD hiển thị Tọa độ map và Độ bền vũ khí ở bên trái (dưới Avatar)
	 * Bố cục tách biệt rõ ràng: Tiêu đề màu trắng, Nội dung màu cam/vàng.
	 */
	private static void paintDurabilityAndPosition(Graphics g) {
		try {
			int startX = 4;
			int avatarH = class_abj.O != null ? class_abj.O.getHeight() : 32;
			int startY = Math.max(avatarH + 26, 58); // Nằm ngay dưới icon 12+ bên trái, tách bạch rõ ràng

			// 1. Tọa độ bản đồ (Dòng 1)
			String mapPos = ModHelpers.getMapNameAndPosition();
			if (mapPos != null && mapPos.length() > 0) {
				String title = "Toạ độ: ";
				int titleW = class_d.j[0].a(title);
				int valW = class_d.j[3].a(mapPos);
				int totalW = titleW + valW;

				// Nền mờ tối giản chống chói
				g.setColor(0x000000);
				g.fillRect(startX - 2, startY, totalW + 4, 12);
				g.setColor(0x2E3842);
				g.drawRect(startX - 2, startY, totalW + 4, 12);

				// Tiêu đề chữ trắng
				class_d.j[0].a(g, title, startX, startY + 1, 0);
				// Tọa độ chữ cam/vàng
				class_d.j[3].a(g, mapPos, startX + titleW, startY + 1, 0);
			}

			// 2. Độ bền vũ khí (Dòng 2)
			int doBen = MainCharInfo.getDoBen();
			int row2Y = startY + 14;
			String titleDoBen = "Độ bền: ";
			int titleDoBenW = class_d.j[0].a(titleDoBen);

			if (doBen <= 0) {
				// Cảnh báo vũ khí hỏng (nhấp nháy đỏ)
				boolean blink = (class_acv.l % 10 < 5);
				String valHỏng = "0 (HỎNG)";
				int valW = class_d.j[2].a(valHỏng);
				int totalW = titleDoBenW + valW;

				g.setColor(blink ? 0x4A0000 : 0x000000);
				g.fillRect(startX - 2, row2Y, totalW + 4, 12);
				g.setColor(0xFF1744);
				g.drawRect(startX - 2, row2Y, totalW + 4, 12);

				class_d.j[0].a(g, titleDoBen, startX, row2Y + 1, 0);
				if (blink) {
					class_d.j[2].a(g, valHỏng, startX + titleDoBenW, row2Y + 1, 0);
				}
			} else {
				String valStr = String.valueOf(doBen);
				int valW = class_d.j[3].a(valStr);
				int totalW = titleDoBenW + valW;

				g.setColor(0x000000);
				g.fillRect(startX - 2, row2Y, totalW + 4, 12);
				g.setColor(0x2E3842);
				g.drawRect(startX - 2, row2Y, totalW + 4, 12);

				// Tiêu đề chữ trắng
				class_d.j[0].a(g, titleDoBen, startX, row2Y + 1, 0);
				// Giá trị độ bền chữ cam/vàng
				class_d.j[3].a(g, valStr, startX + titleDoBenW, row2Y + 1, 0);
			}
		} catch (Exception ignored) {}
	}

	/**
	 * Vẽ trạng thái Buff/Debuff dạng danh sách phân màu phía dưới HUD bên trái
	 */
	private static void paintBuffBadges(Graphics g) {
		try {
			Vector buffs = MainCharInfo.getActiveBuffItems();
			if (buffs == null || buffs.size() == 0) {
				return;
			}

			int startX = 4;
			int avatarH = class_abj.O != null ? class_abj.O.getHeight() : 32;
			int startY = Math.max(avatarH + 26, 58) + 28; // Nằm dưới 2 dòng HUD

			for (int i = 0; i < buffs.size(); i++) {
				MainCharInfo.BuffItem buff = (MainCharInfo.BuffItem) buffs.elementAt(i);
				if (buff == null) {
					continue;
				}

				String titleText = buff.name + ": ";
				String timeText = (buff.timeStr != null && buff.timeStr.length() > 0) ? buff.timeStr : "";

				classes.class_d fontTitle = buff.isDebuff ? class_d.j[2] : class_d.j[0];
				int titleW = fontTitle.a(titleText);
				int timeW = class_d.j[3].a(timeText);
				int totalW = titleW + timeW;

				// Nền mờ
				g.setColor(0x000000);
				g.fillRect(startX - 2, startY, totalW + 4, 12);
				g.setColor(buff.isDebuff ? 0x7F1D1D : 0x2E3842);
				g.drawRect(startX - 2, startY, totalW + 4, 12);

				// Tên hiệu ứng
				fontTitle.a(g, titleText, startX, startY + 1, 0);
				// Thời gian còn lại màu cam/vàng
				if (timeText.length() > 0) {
					class_d.j[3].a(g, timeText, startX + titleW, startY + 1, 0);
				}

				startY += 14;
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

