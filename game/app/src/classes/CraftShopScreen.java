package classes;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class CraftShopScreen extends class_aae {

	public static class CraftItem {
		public short id;
		public String name;
		public byte level;
		public byte type;
		public short icon;
		public short[] baseStats;

		public CraftItem(short id, String name, byte level, byte type, short icon, short[] baseStats) {
			this.id = id;
			this.name = name;
			this.level = level;
			this.type = type;
			this.icon = icon;
			this.baseStats = baseStats;
		}
	}

	public static CraftShopScreen instance;

	public byte classChar;
	public byte equipType;
	public Vector items = new Vector();

	public int currentTab = 0; // 0: Ngũ phẩm -> 4: Nhất phẩm
	public int selectedIndex = 0;

	public static final String[] TAB_NAMES = { "Ngũ phẩm", "Tứ phẩm", "Tam phẩm", "Nhị phẩm", "Nhất phẩm" };
	public static final int[] TAB_RANKS = { 5, 4, 3, 2, 1 };
	public static final int[] RANK_COLORS = { 0, 1, 3, 4, 5 }; // White, Green, Blue, Yellow, Violet
	public static final double[] STAT_MULTS = { 1.10, 1.20, 1.35, 1.55, 1.80 };
	public static final int[] BONUS_ATTRS = { 1, 2, 3, 4, 5 };

	private int winX, winY, winW, winH;

	public CraftShopScreen(byte classChar, byte equipType, Vector items) {
		this.classChar = classChar;
		this.equipType = equipType;
		this.items = items != null ? items : new Vector();
		this.currentTab = 0;
		this.selectedIndex = 0;

		this.ba = new class_s("Chế tạo", new IAction() {
			public void perform() {
				onCraftClicked();
			}
		});
		this.bc = new class_s("Đóng", new IAction() {
			public void perform() {
				class_acv.s.d();
			}
		});
	}

	public static void show(byte classChar, byte equipType, Vector items) {
		instance = new CraftShopScreen(classChar, equipType, items);
		instance.d();
	}

	public void onCraftClicked() {
		if (items == null || items.size() == 0 || selectedIndex < 0 || selectedIndex >= items.size()) {
			return;
		}
		final CraftItem it = (CraftItem) items.elementAt(selectedIndex);
		final byte rank = (byte) TAB_RANKS[currentTab];
		final String rankName = TAB_NAMES[currentTab];

		class_acv.b("Bạn có chắc muốn chế tạo " + it.name + " (" + rankName + ") không?", new IAction() {
			public void perform() {
				class_go.a().sendCraftItem(it.id, rank);
			}
		});
	}

	public void b() {
		// Update logic
	}

	public void c() {
		if (class_acv.q != this) return;

		// Phím trái / phải: chuyển Tab
		if (class_acv.b(4)) {
			currentTab--;
			if (currentTab < 0) currentTab = TAB_NAMES.length - 1;
		} else if (class_acv.b(6)) {
			currentTab++;
			if (currentTab >= TAB_NAMES.length) currentTab = 0;
		}

		// Phím lên / xuống: chọn trang bị
		if (class_acv.b(2)) {
			selectedIndex--;
			if (selectedIndex < 0) selectedIndex = Math.max(0, items.size() - 1);
		} else if (class_acv.b(8)) {
			selectedIndex++;
			if (selectedIndex >= items.size()) selectedIndex = 0;
		}

		// Phím chọn (Fire / Key 5)
		if (class_acv.b(5)) {
			onCraftClicked();
		}

		// Touchscreen xử lý
		if (class_acv.g) {
			int touchX = class_acv.j;
			int touchY = class_acv.k;

			// Touch nút tab trái/phải
			if (touchY >= winY + 6 && touchY <= winY + 28) {
				if (touchX >= winX + 6 && touchX <= winX + 35) {
					currentTab--;
					if (currentTab < 0) currentTab = TAB_NAMES.length - 1;
					class_acv.g = false;
					return;
				} else if (touchX >= winX + winW - 35 && touchX <= winX + winW - 6) {
					currentTab++;
					if (currentTab >= TAB_NAMES.length) currentTab = 0;
					class_acv.g = false;
					return;
				}
			}

			// Touch vào danh sách item
			int itemAreaY = winY + 32;
			int itemSlotW = 26;
			int startX = winX + 12;
			for (int i = 0; i < items.size(); i++) {
				int ix = startX + i * itemSlotW;
				if (touchX >= ix && touchX <= ix + itemSlotW && touchY >= itemAreaY && touchY <= itemAreaY + 26) {
					if (selectedIndex == i) {
						onCraftClicked();
					} else {
						selectedIndex = i;
					}
					class_acv.g = false;
					return;
				}
			}
		}

		super.c();
	}

	public void a(Graphics g) {
		class_acv.a(g);
		class_acv.s.a(g);

		int sw = class_acv.m;
		int sh = class_acv.n;

		winW = Math.min(176, sw - 8);
		winH = Math.min(226, sh - 28);
		winX = (sw - winW) / 2;
		winY = (sh - 22 - winH) / 2;

		// Nền mờ và khung cửa sổ
		g.setColor(0x000000);
		g.fillRect(0, 0, sw, sh);

		g.setColor(0x272727);
		g.fillRect(winX + 2, winY + 2, winW - 4, winH - 4);
		g.setColor(0x525252);
		g.drawRect(winX + 1, winY + 1, winW - 2, winH - 2);
		g.setColor(0xd8a136);
		g.drawRect(winX, winY, winW, winH);

		// Thanh Header Tab
		int headerH = 22;
		g.setColor(0x181818);
		g.fillRect(winX + 4, winY + 5, winW - 8, headerH);
		g.setColor(0x6b6b6b);
		g.drawRect(winX + 4, winY + 5, winW - 8, headerH);

		// Mũi tên trái / phải
		g.setColor(0xd8a136);
		g.drawString("<", winX + 10, winY + 7, 0);
		g.drawString(">", winX + winW - 14, winY + 7, 0);

		// Tiêu đề Tab theo phẩm
		int tabColor = RANK_COLORS[currentTab];
		class_d.j[tabColor].a(g, TAB_NAMES[currentTab], winX + winW / 2, winY + 8, 2);

		// Danh sách trang bị (Hàng icon ngang)
		int itemRowY = winY + 32;
		int itemSlotW = 26;
		int startX = winX + 10;

		for (int i = 0; i < items.size(); i++) {
			CraftItem it = (CraftItem) items.elementAt(i);
			int ix = startX + i * itemSlotW;
			if (ix + itemSlotW > winX + winW - 5) break;

			// Khung item slot
			if (i == selectedIndex) {
				g.setColor(0xffd700);
				g.fillRect(ix, itemRowY, 22, 22);
				g.setColor(0x000000);
				g.fillRect(ix + 1, itemRowY + 1, 20, 20);
			} else {
				g.setColor(0x3a3a3a);
				g.fillRect(ix, itemRowY, 22, 22);
				g.setColor(0x666666);
				g.drawRect(ix, itemRowY, 22, 22);
			}

			// Vẽ Icon trang bị
			class_ko.a(g, it.icon, ix + 11, itemRowY + 11);

			// Level badge
			class_d.j[0].a(g, String.valueOf(it.level), ix + 11, itemRowY + 12, 2);
		}

		// Khung chi tiết trang bị được chọn
		if (items.size() > 0 && selectedIndex >= 0 && selectedIndex < items.size()) {
			CraftItem curItem = (CraftItem) items.elementAt(selectedIndex);
			drawItemTooltip(g, curItem, winX + 8, winY + 60, winW - 16, winH - 66);
		}

		super.a(g);
	}

	private void drawItemTooltip(Graphics g, CraftItem it, int boxX, int boxY, int boxW, int boxH) {
		g.setColor(0x1a1a1a);
		g.fillRect(boxX, boxY, boxW, boxH);
		g.setColor(0x4a4a4a);
		g.drawRect(boxX, boxY, boxW, boxH);

		int curY = boxY + 4;
		int tabColor = RANK_COLORS[currentTab];
		double mult = STAT_MULTS[currentTab];
		int rank = TAB_RANKS[currentTab];
		int k = 6 - rank; // Ngũ phẩm = 1, Tứ phẩm = 2, Tam phẩm = 3, Nhị phẩm = 4, Nhất phẩm = 5

		// 1. Tên trang bị (Kèm Phẩm và Màu sắc tương ứng)
		class_d.j[tabColor].a(g, it.name + " (" + TAB_NAMES[currentTab] + ")", boxX + 6, curY, 0);
		curY += 14;

		// 2. Yêu cầu cấp độ & Dòng thuộc tính cộng thêm
		class_d.j[0].a(g, "Yêu cầu: Cấp " + it.level + " (" + BONUS_ATTRS[currentTab] + " dòng phụ)", boxX + 6, curY, 0);
		curY += 13;

		// 3. Chỉ số cơ bản đã nhân hệ số theo phẩm
		if (it.baseStats != null) {
			if (it.baseStats.length > 0 && it.baseStats[0] > 0) {
				int atk = (int) Math.round(it.baseStats[0] * mult);
				class_d.j[0].a(g, "Tấn công: +" + atk, boxX + 6, curY, 0);
				curY += 13;
			}
			if (it.baseStats.length > 1 && it.baseStats[1] > 0) {
				int def = (int) Math.round(it.baseStats[1] * mult);
				class_d.j[0].a(g, "Phòng thủ: +" + def, boxX + 6, curY, 0);
				curY += 13;
			}
			if (it.baseStats.length > 2 && it.baseStats[2] > 0) {
				int res = (int) Math.round(it.baseStats[2] * mult);
				class_d.j[0].a(g, "Kháng: +" + res, boxX + 6, curY, 0);
				curY += 13;
			}
		}

		// Đường phân cách
		g.setColor(0x383838);
		g.drawLine(boxX + 4, curY, boxX + boxW - 4, curY);
		curY += 3;

		// 4. Nguyên liệu cần để chế tạo
		int tier = getMaterialTier(it.level);
		short mat1Id = getMaterial1Id(it.type, tier);
		short mat2Id = getMaterial2Id(it.type, tier);
		short daId = getDaNguHopId((byte) rank, tier);
		short ngocRenId = 268;

		int mat1Need = 5 * k;
		int mat2Need = 5 * k;
		int daNeed = k;
		int ngocRenNeed = k;
		long xuFee = (long) it.level * 1000L * (long) k;

		int mat1Have = countMaterial(mat1Id);
		int mat2Have = countMaterial(mat2Id);
		int daHave = countMaterial(daId);
		int ngocRenHave = countMaterial(ngocRenId);
		long xuHave = class_acv.s != null && class_acv.s.q != null ? class_acv.s.q.br : 0;

		// Dòng 1: Mat 1
		String mat1Name = getGemName(mat1Id, "Nguyên liệu 1");
		int col1 = mat1Have >= mat1Need ? 1 : 2; // 1 = Green, 2 = Red
		class_d.j[col1].a(g, mat1Name + ": " + mat1Have + "/" + mat1Need, boxX + 6, curY, 0);
		curY += 13;

		// Dòng 2: Mat 2
		String mat2Name = getGemName(mat2Id, "Nguyên liệu 2");
		int col2 = mat2Have >= mat2Need ? 1 : 2;
		class_d.j[col2].a(g, mat2Name + ": " + mat2Have + "/" + mat2Need, boxX + 6, curY, 0);
		curY += 13;

		// Dòng 3: Ngọc rèn
		int colNgoc = ngocRenHave >= ngocRenNeed ? 1 : 2;
		class_d.j[colNgoc].a(g, "Ngọc rèn: " + ngocRenHave + "/" + ngocRenNeed, boxX + 6, curY, 0);
		curY += 13;

		// Dòng 4: Đá ngũ hợp
		String daName = getGemName(daId, "Đá ngũ hợp");
		int colDa = daHave >= daNeed ? 1 : 2;
		class_d.j[colDa].a(g, daName + ": " + daHave + "/" + daNeed, boxX + 6, curY, 0);
		curY += 13;

		// Dòng 5: Phí xu
		int colXu = xuHave >= xuFee ? 1 : 2;
		class_d.j[colXu].a(g, "Phí xu: " + formatXu(xuFee) + " xu", boxX + 6, curY, 0);
	}

	public static int countMaterial(short templateId) {
		int total = 0;
		if (class_sc.g != null) {
			for (int i = 0; i < class_sc.g.size(); i++) {
				class_gz g = (class_gz) class_sc.g.elementAt(i);
				if (g != null && g.a == templateId) {
					total += g.c;
				}
			}
		}
		if (class_sc.h != null) {
			for (int i = 0; i < class_sc.h.size(); i++) {
				class_gz g = (class_gz) class_sc.h.elementAt(i);
				if (g != null && g.a == templateId) {
					total += g.c;
				}
			}
		}
		return total;
	}

	public static String getGemName(short id, String def) {
		if (id == 268) return "Ngọc rèn";
		try {
			class_xv info = class_yi.a(id);
			if (info != null && info.j != null && info.j.trim().length() > 0) {
				return info.j.trim();
			}
		} catch (Exception ignored) {
		}
		return def;
	}

	public static int getMaterialTier(int level) {
		if (level < 30) return 1;
		if (level < 40) return 2;
		if (level < 50) return 3;
		if (level < 60) return 4;
		if (level < 70) return 5;
		return 6;
	}

	public static short getMaterial1Id(byte type, int tier) {
		int offset = tier - 1;
		switch (type) {
			case 0:
			case 1:
				return (short) (68 + offset);      // Vải
			case 2:
			case 10:
			case 11:
				return (short) (96 + offset);      // Da mềm
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				return (short) (75 + offset);      // Sắt
			case 8:
			case 9:
				return (short) (110 + offset);     // Bạc
			case 12:
				return (short) (82 + offset);      // Ngọc
			default:
				return (short) (75 + offset);
		}
	}

	public static short getMaterial2Id(byte type, int tier) {
		int offset = tier - 1;
		switch (type) {
			case 0:
				return (short) (103 + offset);     // Tơ lụa
			case 1:
				return (short) (96 + offset);      // Da mềm
			case 2:
				return (short) (75 + offset);      // Sắt
			case 10:
			case 11:
				return (short) (131 + offset);     // Da cứng
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				return (short) (89 + offset);      // Gỗ thường
			case 8:
				return (short) (82 + offset);      // Ngọc
			case 9:
			case 12:
				return (short) (117 + offset);     // Thủy tinh
			default:
				return (short) (89 + offset);
		}
	}

	public static short getDaNguHopId(byte rank, int tier) {
		int offset = tier - 1;
		if (rank == 1) {
			return (short) (149 + offset); // Tinh khiết
		} else if (rank == 2 || rank == 3) {
			return (short) (143 + offset); // Cao cấp
		} else {
			return (short) (137 + offset); // Thường
		}
	}

	public static String formatXu(long amount) {
		String s = String.valueOf(amount);
		StringBuffer sb = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			sb.append(s.charAt(i));
			if ((len - i - 1) % 3 == 0 && i < len - 1) {
				sb.append('.');
			}
		}
		return sb.toString();
	}
}
