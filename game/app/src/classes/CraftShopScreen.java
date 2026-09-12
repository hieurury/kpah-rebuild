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
	public static final int[] BONUS_ATTRS = { 1, 2, 3, 4, 7 };

	public static final int FOCUS_TAB = 0;
	public static final int FOCUS_ITEM = 1;
	public int focusMode = FOCUS_ITEM;

	public int scrollX = 0;
	public int targetScrollX = 0;

	private int winX, winY, winW, winH;

	public CraftShopScreen(byte classChar, byte equipType, Vector items) {
		this.classChar = classChar;
		this.equipType = equipType;
		this.items = items != null ? items : new Vector();
		this.currentTab = 0;
		this.selectedIndex = 0;
		this.focusMode = FOCUS_ITEM;
		this.scrollX = 0;
		this.targetScrollX = 0;

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
		if (scrollX != targetScrollX) {
			int diff = targetScrollX - scrollX;
			if (Math.abs(diff) <= 2) {
				scrollX = targetScrollX;
			} else {
				scrollX += diff / 2;
			}
		}
	}

	private void updateScrollTarget() {
		if (winW <= 0) return;
		int itemSlotW = 26;
		int viewW = winW - 20;
		int selectedX = selectedIndex * itemSlotW;
		if (selectedX - targetScrollX < 0) {
			targetScrollX = selectedX;
		} else if (selectedX + itemSlotW - targetScrollX > viewW) {
			targetScrollX = selectedX + itemSlotW - viewW;
		}
		int maxScroll = Math.max(0, items.size() * itemSlotW - viewW);
		if (targetScrollX < 0) targetScrollX = 0;
		if (targetScrollX > maxScroll) targetScrollX = maxScroll;
	}

	public void c() {
		if (class_acv.q != this) return;

		if (focusMode == FOCUS_TAB) {
			// Phím trái / phải: chuyển Tab
			if (class_acv.b(4)) {
				currentTab--;
				if (currentTab < 0) currentTab = TAB_NAMES.length - 1;
			} else if (class_acv.b(6)) {
				currentTab++;
				if (currentTab >= TAB_NAMES.length) currentTab = 0;
			} else if (class_acv.b(8)) { // Phím xuống: chuyển tiêu điểm xuống danh sách trang bị
				focusMode = FOCUS_ITEM;
			}
		} else {
			// FOCUS_ITEM
			// Phím trái / phải: chọn trang bị
			if (class_acv.b(4)) {
				selectedIndex--;
				if (selectedIndex < 0) selectedIndex = Math.max(0, items.size() - 1);
				updateScrollTarget();
			} else if (class_acv.b(6)) {
				selectedIndex++;
				if (selectedIndex >= items.size()) selectedIndex = 0;
				updateScrollTarget();
			} else if (class_acv.b(2)) { // Phím lên: chuyển tiêu điểm lên Tab
				focusMode = FOCUS_TAB;
			}
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
				focusMode = FOCUS_TAB;
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
			int startX = winX + 10;
			for (int i = 0; i < items.size(); i++) {
				int ix = startX + i * itemSlotW - scrollX;
				if (touchX >= ix && touchX <= ix + itemSlotW && touchY >= itemAreaY && touchY <= itemAreaY + 26) {
					focusMode = FOCUS_ITEM;
					if (selectedIndex == i) {
						onCraftClicked();
					} else {
						selectedIndex = i;
						updateScrollTarget();
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
		if (focusMode == FOCUS_TAB) {
			g.setColor(0xffd700);
		} else {
			g.setColor(0x6b6b6b);
		}
		g.drawRect(winX + 4, winY + 5, winW - 8, headerH);

		// Mũi tên trái / phải
		g.setColor(0xd8a136);
		g.drawString("<", winX + 10, winY + 7, 0);
		g.drawString(">", winX + winW - 14, winY + 7, 0);

		// Tiêu đề Tab theo phẩm
		int tabColor = RANK_COLORS[currentTab];
		class_d.j[tabColor].a(g, TAB_NAMES[currentTab], winX + winW / 2, winY + 8, 2);

		// Danh sách trang bị (Hàng icon ngang có scroll)
		int itemRowY = winY + 32;
		int itemSlotW = 26;
		int startX = winX + 10;

		int clipX = g.getClipX();
		int clipY = g.getClipY();
		int clipW = g.getClipWidth();
		int clipH = g.getClipHeight();

		g.setClip(winX + 6, itemRowY - 2, winW - 12, 28);

		for (int i = 0; i < items.size(); i++) {
			CraftItem it = (CraftItem) items.elementAt(i);
			int ix = startX + i * itemSlotW - scrollX;
			if (ix + itemSlotW < winX - 20 || ix > winX + winW + 20) continue;

			// Khung item slot
			if (i == selectedIndex && focusMode == FOCUS_ITEM) {
				g.setColor(0xffd700);
				g.fillRect(ix, itemRowY, 22, 22);
				g.setColor(0x000000);
				g.fillRect(ix + 1, itemRowY + 1, 20, 20);
			} else if (i == selectedIndex) {
				g.setColor(0xffffff);
				g.fillRect(ix, itemRowY, 22, 22);
				g.setColor(0x222222);
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

		g.setClip(clipX, clipY, clipW, clipH);

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

		// 4. Nguyên liệu cần để chế tạo theo chuẩn 10 nguyên liệu
		RecipeData recipe = getRecipe(it.type, it.level, rank);

		int sc1Have = countMaterial(recipe.soCap1Id);
		int sc2Have = countMaterial(recipe.soCap2Id);
		int cc1Have = countMaterial(recipe.caoCap1Id);
		int cc2Have = countMaterial(recipe.caoCap2Id);
		int ngocRenHave = countMaterial(recipe.ngocRenId);
		long xuHave = class_acv.s != null && class_acv.s.q != null ? class_acv.s.q.br : 0;

		// Dòng 1: Sơ cấp 1
		int colSc1 = sc1Have >= recipe.soCap1Need ? 1 : 2; // 1: Green, 2: Red
		class_d.j[colSc1].a(g, getMaterialName(recipe.soCap1Id) + ": " + sc1Have + "/" + recipe.soCap1Need, boxX + 6, curY, 0);
		curY += 12;

		// Dòng 2: Sơ cấp 2
		int colSc2 = sc2Have >= recipe.soCap2Need ? 1 : 2;
		class_d.j[colSc2].a(g, getMaterialName(recipe.soCap2Id) + ": " + sc2Have + "/" + recipe.soCap2Need, boxX + 6, curY, 0);
		curY += 12;

		// Dòng 3 & 4: Cao cấp (hoặc Không yêu cầu nếu Ngũ/Tứ phẩm)
		if (rank <= 3) {
			int colCc1 = cc1Have >= recipe.caoCap1Need ? 1 : 2;
			class_d.j[colCc1].a(g, getMaterialName(recipe.caoCap1Id) + ": " + cc1Have + "/" + recipe.caoCap1Need, boxX + 6, curY, 0);
			curY += 12;

			int colCc2 = cc2Have >= recipe.caoCap2Need ? 1 : 2;
			class_d.j[colCc2].a(g, getMaterialName(recipe.caoCap2Id) + ": " + cc2Have + "/" + recipe.caoCap2Need, boxX + 6, curY, 0);
			curY += 12;
		} else {
			class_d.j[0].a(g, "Cao cấp: Không yêu cầu", boxX + 6, curY, 0);
			curY += 12;
		}

		// Dòng 5: Ngọc rèn
		int colNgoc = ngocRenHave >= recipe.ngocRenNeed ? 1 : 2;
		class_d.j[colNgoc].a(g, "Ngọc rèn: " + ngocRenHave + "/" + recipe.ngocRenNeed, boxX + 6, curY, 0);
		curY += 12;

		// Dòng 6: Phí xu
		int colXu = xuHave >= recipe.xuFee ? 1 : 2;
		class_d.j[colXu].a(g, "Phí: " + formatXu(recipe.xuFee) + " xu", boxX + 6, curY, 0);
	}

	public static class RecipeData {
		public short soCap1Id;
		public int soCap1Need;
		public short soCap2Id;
		public int soCap2Need;
		public short caoCap1Id;
		public int caoCap1Need;
		public short caoCap2Id;
		public int caoCap2Need;
		public short ngocRenId = 268;
		public int ngocRenNeed;
		public long xuFee;
	}

	public static RecipeData getRecipe(byte type, int level, int rank) {
		RecipeData r = new RecipeData();
		int rankIndex = 5 - rank; // 0 to 4
		int levelFactor = level / 5;
		r.soCap1Need = levelFactor * (rankIndex + 1) * 3;
		r.soCap2Need = levelFactor * (rankIndex + 1) * 2;
		r.ngocRenNeed = rankIndex + 1;
		r.xuFee = (long) level * 1000L * (long) (rankIndex + 1);

		if (rank <= 3) {
			r.caoCap1Need = (rankIndex - 1) * 2;
			r.caoCap2Need = (rankIndex - 1);
		} else {
			r.caoCap1Need = 0;
			r.caoCap2Need = 0;
		}

		switch (type) {
			case 3: // Kiếm
			case 4: // Đao
			case 6: // Búa
				r.soCap1Id = 75;   // Sắt
				r.soCap2Id = 89;   // Gỗ thường
				r.caoCap1Id = 110; // Bạc
				r.caoCap2Id = 124; // Gỗ sưa
				break;
			case 5: // Bút
			case 7: // Cung
				r.soCap1Id = 89;   // Gỗ thường
				r.soCap2Id = 75;   // Sắt
				r.caoCap1Id = 124; // Gỗ sưa
				r.caoCap2Id = 110; // Bạc
				break;
			case 0: // Áo
			case 1: // Quần
				r.soCap1Id = 68;   // Vải
				r.soCap2Id = 96;   // Da mềm
				r.caoCap1Id = 103; // Tơ lụa
				r.caoCap2Id = 131; // Da cứng
				break;
			case 2:  // Nón
			case 11: // Giày
			case 10: // Găng tay
				r.soCap1Id = 96;   // Da mềm
				r.soCap2Id = 68;   // Vải
				r.caoCap1Id = 131; // Da cứng
				r.caoCap2Id = 103; // Tơ lụa
				break;
			case 8: // Nhẫn
			case 9: // Dây chuyền
				r.soCap1Id = 82;   // Ngọc
				r.soCap2Id = 75;   // Sắt
				r.caoCap1Id = 117; // Thủy tinh
				r.caoCap2Id = 110; // Bạc
				break;
			case 12: // Bội ngọc
			default:
				r.soCap1Id = 82;   // Ngọc
				r.soCap2Id = 89;   // Gỗ thường
				r.caoCap1Id = 117; // Thủy tinh
				r.caoCap2Id = 124; // Gỗ sưa
				break;
		}
		return r;
	}

	public static String getMaterialName(short id) {
		switch (id) {
			case 68: return "Vải";
			case 75: return "Sắt";
			case 89: return "Gỗ thường";
			case 96: return "Da mềm";
			case 82: return "Ngọc";
			case 103: return "Tơ lụa";
			case 110: return "Bạc";
			case 124: return "Gỗ sưa";
			case 131: return "Da cứng";
			case 117: return "Thủy tinh";
			case 268: return "Ngọc rèn";
			default: return "NL " + id;
		}
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
