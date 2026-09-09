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

		// 1. Hiển thị nhãn nổi bật kèm phân màu cho vật phẩm rơi trên mặt đất
		paintDroppedItems(g);

		// 2. Hiển thị HUD thông tin Tọa độ & Độ bền vũ khí
		paintDurabilityAndPosition(g);

		// 3. Hiển thị các trạng thái Buff/Debuff dạng thanh nhỏ gọn gàng dưới thanh HP/MP
		paintBuffBadges(g);

		// 4. Hiển thị chỉ báo nhiệm vụ nổi bật trên đầu NPC (!) hoặc (?)
		paintQuestMarkers(g);
	}

	/**
	 * Vẽ nhãn tên và phân màu nổi bật ngay phía trên vật phẩm rơi trên mặt đất
	 */
	private static void paintDroppedItems(Graphics g) {
		try {
			if (class_acv.s == null || class_acv.s.l == null) {
				return;
			}
			Vector entities = class_acv.s.l;
			int camX = class_abj.j;
			int camY = class_abj.k;
			int scrW = class_acv.m;
			int scrH = class_acv.n;

			for (int i = 0; i < entities.size(); i++) {
				Object obj = entities.elementAt(i);
				if (!(obj instanceof class_ba)) {
					continue;
				}
				class_ba item = (class_ba) obj;
				int sx = item.cK - camX;
				int sy = (item.cL - item.e) - camY;

				// Chỉ vẽ khi vật phẩm nằm trong khung hình
				if (sx < -40 || sx > scrW + 40 || sy < -30 || sy > scrH + 20) {
					continue;
				}

				String name = getItemDisplayName(item);
				if (name == null || name.length() == 0) {
					continue;
				}

				boolean isTarget = (class_acv.s.r == item);

				// Xác định màu sắc theo phân loại
				int borderColor;
				classes.class_d font;

				if (item.cF == 4 && item.c == 0) {
					// Vàng (Xu) - Vàng kim rực rỡ
					borderColor = 0xFFD700;
					font = class_d.h;
				} else if (item.cF == 4 && (item.c == 106 || item.c == 160 || item.c == 161 || item.c == 162)) {
					// Rương Tinh Anh - Cam vàng ánh tím đặc biệt
					borderColor = 0xFFA000;
					font = class_d.h;
				} else if (item.cF == 4 && (item.c >= 1 && item.c <= 3)) {
					// Bình HP - Đỏ tươi
					borderColor = 0xFF5252;
					font = class_d.e;
				} else if (item.cF == 4 && (item.c >= 4 && item.c <= 6)) {
					// Bình MP - Xanh lam
					borderColor = 0x448AFF;
					font = class_d.g;
				} else if (item.cF == 4 && (item.c >= 108 && item.c <= 111)) {
					// Tinh anh huyết - Xanh lục bảo
					borderColor = 0x00E676;
					font = class_d.b;
				} else if (item.cF == 3) {
					// Trang bị - Màu cam nổi bật
					borderColor = 0xFF9800;
					font = class_d.a;
				} else if (item.cF == 6 || item.cF == 7) {
					// Đá quý & Nguyên liệu - Xanh ngọc
					borderColor = 0x00E5FF;
					font = class_d.b;
				} else {
					// Mặc định
					borderColor = 0xB0BEC5;
					font = class_d.g;
				}

				int textW = font.a(name);
				int boxW = textW + 6;
				int boxH = 11;
				int boxX = sx - (boxW / 2);
				int boxY = sy - 18;

				// Nền tối mờ chống chói trên mọi địa hình
				g.setColor(0x0a0e14);
				g.fillRect(boxX, boxY, boxW, boxH);

				// Khung viền màu phân loại
				g.setColor(isTarget ? 0xFFFF00 : borderColor);
				g.drawRect(boxX, boxY, boxW, boxH);

				// Nếu đang được chọn (target), vẽ thêm khung kép và mũi tên chỉ mục tiêu
				if (isTarget) {
					g.drawRect(boxX - 1, boxY - 1, boxW + 2, boxH + 2);
					class_d.h.a(g, "▼", sx, boxY + boxH - 2, 2);
				}

				// Vẽ tên vật phẩm
				font.a(g, name, sx, boxY + 1, 2);
			}
		} catch (Exception ignored) {}
	}

	/**
	 * Lấy tên hiển thị chuẩn hóa của vật phẩm rơi
	 */
	private static String getItemDisplayName(class_ba item) {
		if (item.cF == 4) {
			if (item.c == 0) return "Vàng";
			if (item.c == 1) return "HP Nhỏ";
			if (item.c == 2) return "HP Vừa";
			if (item.c == 3) return "HP Lớn";
			if (item.c == 4) return "MP Nhỏ";
			if (item.c == 5) return "MP Vừa";
			if (item.c == 6) return "MP Lớn";
			if (item.c == 106) return "★ Rương Tinh Anh I";
			if (item.c == 160) return "★ Rương Tinh Anh II";
			if (item.c == 161) return "★ Rương Tinh Anh III";
			if (item.c == 162) return "★ Rương Tinh Anh IV";
			if (item.c == 108) return "Tinh Anh Huyết (Sơ)";
			if (item.c == 109) return "Tinh Anh Huyết (Trung)";
			if (item.c == 110) return "Tinh Anh Huyết (Cao)";
			if (item.c == 111) return "Tinh Anh Huyết (Siêu)";
		}
		try {
			String rawName = item.a_();
			if (rawName != null && rawName.length() > 0) {
				return rawName;
			}
		} catch (Exception ignored) {}
		return "Vật phẩm";
	}

	/**
	 * Vẽ HUD hiển thị Tọa độ map và Độ bền vũ khí
	 */
	private static void paintDurabilityAndPosition(Graphics g) {
		try {
			int avatarH = class_abj.O.getHeight();

			// 1. Tọa độ bản đồ đặt ở chính giữa trên cùng (Top-Center)
			String mapPos = ModHelpers.getMapNameAndPosition();
			if (mapPos != null && mapPos.length() > 0) {
				int textW = class_d.g.a(mapPos);
				int boxW = textW + 8;
				int boxX = class_acv.o - (boxW / 2);
				g.setColor(0x0a1014);
				g.fillRect(boxX, 2, boxW, 12);
				g.setColor(0x2E3842);
				g.drawRect(boxX, 2, boxW, 12);
				class_d.g.a(g, mapPos, class_acv.o, 3, 2);
			}

			// 2. Độ bền vũ khí đặt thanh nhỏ gọn gàng góc trên trái dưới Avatar
			int doBen = MainCharInfo.getDoBen();
			int startX = 4;
			int startY = avatarH + 2;
			int badgeW = 84;
			int badgeH = 12;

			if (doBen <= 0) {
				// Cảnh báo vũ khí hỏng (nhấp nháy đỏ)
				boolean blink = (class_acv.l % 10 < 5);
				g.setColor(blink ? 0x4A0000 : 0x1A0000);
				g.fillRect(startX, startY, badgeW, badgeH);
				g.setColor(0xFF1744);
				g.drawRect(startX, startY, badgeW, badgeH);
				g.fillRect(startX, startY, 2, badgeH);
				class_d.e.a(g, "Độ bền: 0 (Hỏng)", startX + 5, startY + 1, 0);
			} else {
				// Trạng thái bình thường
				g.setColor(0x0f141a);
				g.fillRect(startX, startY, badgeW, badgeH);
				g.setColor(0x2E3842);
				g.drawRect(startX, startY, badgeW, badgeH);
				g.setColor(0x00E676);
				g.fillRect(startX, startY, 2, badgeH);
				class_d.b.a(g, "Độ bền: " + doBen, startX + 5, startY + 1, 0);
			}
		} catch (Exception ignored) {}
	}

	/**
	 * Vẽ trạng thái Buff/Debuff dạng thanh nhỏ gọn gàng phía dưới thanh HP/MP
	 */
	private static void paintBuffBadges(Graphics g) {
		try {
			Vector buffs = MainCharInfo.getActiveBuffItems();
			if (buffs == null || buffs.size() == 0) {
				return;
			}

			int avatarH = class_abj.O.getHeight();
			int startX = 4;
			int startY = avatarH + 16; // Nằm ngay dưới thanh Độ bền
			int badgeW = 84;
			int badgeH = 12;

			for (int i = 0; i < buffs.size(); i++) {
				MainCharInfo.BuffItem buff = (MainCharInfo.BuffItem) buffs.elementAt(i);
				if (buff == null) {
					continue;
				}

				// Nền tối
				g.setColor(0x0f141a);
				g.fillRect(startX, startY, badgeW, badgeH);

				// Viền và thanh phân màu bên trái
				g.setColor(buff.color);
				g.fillRect(startX, startY, 2, badgeH);

				g.setColor(buff.isDebuff ? 0x7F1D1D : 0x1B5E20);
				g.drawRect(startX, startY, badgeW, badgeH);

				// Tên hiệu ứng (trái)
				classes.class_d font = buff.isDebuff ? class_d.e : class_d.b;
				font.a(g, buff.name, startX + 5, startY + 1, 0);

				// Thời gian còn lại (phải)
				if (buff.timeStr != null && buff.timeStr.length() > 0) {
					class_d.g.a(g, buff.timeStr, startX + badgeW - 3, startY + 1, 1);
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
				int sy = entity.cL - camY - 50 - bobbing;

				if (sx < -20 || sx > scrW + 20 || sy < -20 || sy > scrH + 20) {
					continue;
				}

				boolean isDone = (isMainNpc && (questMain.m == 1 || questMain.m == 2)) ||
				                 (isDailyNpc && (questDaily.m == 1 || questDaily.m == 2));

				String markerText = isDone ? "?" : "!";
				int bgColor = isDone ? 0x00C853 : (isMainNpc ? 0xFFB300 : 0x29B6F6);
				int badgeW = 14;
				int badgeH = 14;
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

				// Chữ ! hoặc ?
				class_d.f.a(g, markerText, sx, by + 1, 2);
			}
		} catch (Exception ignored) {
		}
	}
}
