package classes;

public class MainCharInfo {

	private static long initialCoins = -1;
	public static long expPlus = 0; // kinh nghiệm được cộng

	public static String getPlayerName() {
		return class_acv.s.q.a_();
	}

	/**
	 * Hàm lấy độ bền vũ khí
	 * 
	 * @return -> độ bền vũ khí
	 */
	public static final int getDoBen() {
		for (int i1 = 0; i1 < ((class_hw) class_acv.s.q).aT.size(); i1++) {
			class_ql localclass_ql = (class_ql) ((class_hw) class_acv.s.q).aT.elementAt(i1);
			class_yc localclass_yc = class_yi.b((int) localclass_ql.r);
			if (((localclass_yc.c == 3) || (localclass_yc.c == 4) || (localclass_yc.c == 5) || (localclass_yc.c == 6)
					|| (localclass_yc.c == 7))) {
				return localclass_ql.u > 0 ? localclass_ql.u : 0;
			}
		}
		return 0;
	}

	public static long getCurrentCoin() {
		return class_acv.s.q.br;
	}

	public static int getCurrentGold() {
		return class_acv.s.q.aV;
	}

	public static int getCurrentLockGold() {
		return class_acv.s.q.aW;
	}

	// lấy điểm tấn công của tài khoản
	public static int getAttackPoints() {
		return ((class_hw) class_acv.s.q).G();
	}

	/*
	 * Lấy xu up được
	 */
	public static long getCoinsEarned() {
		// Khởi tạo số xu ban đầu của tài khoản, điều kiện này chỉ chạy 1 lần.
		if (initialCoins == -1) {
			initialCoins = getCurrentCoin();
		}
		if (initialCoins == -1) {
			return 0;
		}
		// Khi đã có số xu ban đầu rồi thì việc tính xu up được bằng cách lấy xu hiện
		// tại trừ cho số xu lúc đầu có
		return getCurrentCoin() - initialCoins;
	}

	/**
	 * Lấy thông tin thuộc tính tăng EXP từ trang bị / thú cưỡi đang mang
	 * @return Chuỗi mô tả tỷ lệ và thời gian (nếu có), hoặc null nếu không có
	 */
	public static String getExpBonusInfo() {
		try {
			if (class_acv.s == null || class_acv.s.q == null) {
				return null;
			}
			class_hw mainChar = (class_hw) class_acv.s.q;
			if (mainChar.aT == null) {
				return null;
			}
			int totalRate = 0;
			long minSecLeft = -1;
			boolean hasExp = false;

			for (int i = 0; i < mainChar.aT.size(); i++) {
				class_ql item = (class_ql) mainChar.aT.elementAt(i);
				if (item != null && item.H != null) {
					for (int j = 0; j < item.H.size(); j++) {
						class_zu opt = (class_zu) item.H.elementAt(j);
						if (opt != null && opt.c() == 111) { // 111: Tăng exp
							hasExp = true;
							try {
								totalRate += Integer.parseInt(opt.b());
							} catch (Exception ignored) {}
							if (item.w > 0) {
								long remMin = (long) item.w - (System.currentTimeMillis() - item.x) / 60000L;
								if (remMin < 1) remMin = 1;
								long remSec = remMin * 60L;
								if (minSecLeft == -1 || remSec < minSecLeft) {
									minSecLeft = remSec;
								}
							}
						}
					}
				}
			}

			if (!hasExp && totalRate == 0) {
				return null;
			}

			String res = "Tăng EXP: +" + totalRate + "%";
			if (minSecLeft > 0) {
				long h = minSecLeft / 3600;
				long m = (minSecLeft % 3600) / 60;
				String timeStr = (h > 0 ? h + "h" : "") + (m > 0 ? m + "p" : (h == 0 ? "<1p" : ""));
				res += " (" + timeStr + ")";
			}
			return res;
		} catch (Exception e) {
			return null;
		}
	}

	public static class EquipDurability {
		public String name;
		public int durable;
		public int maxDurable;
		public boolean isBroken;

		public EquipDurability(String name, int durable, int maxDurable) {
			this.name = name;
			this.durable = durable;
			this.maxDurable = maxDurable;
			this.isBroken = (durable <= 0);
		}
	}

	public static java.util.Vector getEquipDurabilityList() {
		java.util.Vector list = new java.util.Vector();
		try {
			if (class_acv.s == null || class_acv.s.q == null) {
				return list;
			}
			class_hw mainChar = (class_hw) class_acv.s.q;
			if (mainChar.aT == null) {
				return list;
			}
			// Order: Vũ khí, Áo, Quần, Nón, Giày, Găng tay, Nhẫn, Dây chuyền, Ngọc bội
			int[] typeOrder = { 3, 1, 2, 0, 10, 11, 8, 9, 12 };
			for (int t = 0; t < typeOrder.length; t++) {
				int targetType = typeOrder[t];
				for (int i = 0; i < mainChar.aT.size(); i++) {
					class_ql item = (class_ql) mainChar.aT.elementAt(i);
					if (item == null) continue;
					class_yc template = class_yi.b((int) item.r);
					if (template == null) continue;

					boolean match = false;
					String label = "";
					if (targetType == 3) {
						if (template.c >= 3 && template.c <= 7) {
							match = true;
							label = "Vũ khí";
						}
					} else if (template.c == targetType) {
						match = true;
						switch (targetType) {
							case 0: label = "Nón"; break;
							case 1: label = "Áo"; break;
							case 2: label = "Quần"; break;
							case 10: label = "Giày"; break;
							case 11: label = "Găng tay"; break;
							case 8: label = "Nhẫn"; break;
							case 9: label = "Dây chuyền"; break;
							case 12: label = "Ngọc bội"; break;
						}
					}
					if (match) {
						list.addElement(new EquipDurability(label, item.u, item.v > 0 ? item.v : template.j));
						if (targetType != 8) {
							break;
						}
					}
				}
			}
		} catch (Exception ignored) {}
		return list;
	}

	public static class CustomBuff {
		public String name;
		public long endTime;
		public boolean isDebuff;

		public CustomBuff(String name, long endTime, boolean isDebuff) {
			this.name = name;
			this.endTime = endTime;
			this.isDebuff = isDebuff;
		}
	}

	private static java.util.Vector customBuffs = new java.util.Vector();

	public static void setCustomBuffs(java.util.Vector buffs) {
		customBuffs = buffs;
	}

	public static class BuffItem {
		public String name;
		public String timeStr;
		public int secLeft;
		public boolean isDebuff;
		public int color;

		public BuffItem(String name, String timeStr, int secLeft, boolean isDebuff, int color) {
			this.name = name;
			this.timeStr = timeStr;
			this.secLeft = secLeft;
			this.isDebuff = isDebuff;
			this.color = color;
		}
	}

	/**
	 * Lấy danh sách đối tượng buff/debuff có cấu trúc đầy đủ thông tin:
	 * Màu xanh lá (0x00E676 / class_d.j[1]) cho Buff
	 * Màu đỏ (0xFF1744 / class_d.j[2]) cho Debuff
	 * Không hiển thị thời gian hồi skill
	 */
	public static java.util.Vector getActiveBuffItems() {
		java.util.Vector list = new java.util.Vector();
		try {
			if (class_acv.s == null || class_acv.s.q == null) {
				return list;
			}
			class_hw mainChar = (class_hw) class_acv.s.q;
			long now = System.currentTimeMillis();

			// 1. Buff đồng bộ từ Server (Tinh Anh Đan, Vé giờ vàng, ...)
			if (customBuffs != null) {
				for (int i = 0; i < customBuffs.size(); i++) {
					CustomBuff cb = (CustomBuff) customBuffs.elementAt(i);
					if (cb != null && cb.endTime > now) {
						long secLeft = (cb.endTime - now) / 1000L;
						if (secLeft > 0) {
							list.addElement(new BuffItem(cb.name, formatTime(secLeft), (int) secLeft, cb.isDebuff, cb.isDebuff ? 0xFF1744 : 0x00E676));
						}
					}
				}
			}

			// 2. Trạng thái Choáng (Stun) từ cW / cZ (Ưu tiên Debuff)
			if (mainChar.cW && mainChar.cZ > now) {
				int secStun = (int) ((mainChar.cZ - now) / 1000L);
				if (secStun > 0 && !hasBuffNamed(list, "Choáng")) {
					list.addElement(new BuffItem("Choáng", formatTime(secStun), secStun, true, 0xFF1744));
				}
			}

			// 3. Trạng thái Trúng Độc từ dg / dh (Ưu tiên Debuff)
			if (mainChar.dg > 0 && (now - mainChar.dg) < (long) mainChar.dh * 1000L) {
				int secPoison = (int) ((long) mainChar.dh - (now - mainChar.dg) / 1000L);
				if (secPoison > 0 && !hasBuffNamed(list, "Trúng Độc")) {
					list.addElement(new BuffItem("Trúng Độc", formatTime(secPoison), secPoison, true, 0xFF1744));
				}
			}

			// 4. Quét các hiệu ứng buff/skill đang hoạt động trong mainChar.de
			if (mainChar.de != null) {
				for (int i = 0; i < mainChar.de.size(); i++) {
					Object obj = mainChar.de.elementAt(i);
					if (obj instanceof class_zx) {
						class_zx buff = (class_zx) obj;
						long secLeft = (buff.a - now) / 1000L;
						if (secLeft > 0) {
							String name;
							int color;
							boolean isDebuff = false;

							switch (buff.h) {
								case 19:
									if (mainChar.cW) {
										name = "Choáng";
										color = 0xFF1744;
										isDebuff = true;
									} else {
										name = (mainChar.aJ == 4 ? "+Công" : "Bảo Hộ");
										color = 0x00E676;
									}
									break;
								case 20:
									name = "+Giáp";
									color = 0x00E676;
									break;
								case 21:
									name = "+Công";
									color = 0x00E676;
									break;
								case 22:
									// Hiệu ứng 22: Nếu đang bị dính độc (dg > 0) hoặc không phải Cung Thủ thì là Debuff Trúng Độc
									if (mainChar.dg > 0 || mainChar.aJ != 2) {
										name = "Trúng Độc";
										color = 0xFF1744;
										isDebuff = true;
									} else {
										name = "Tẩm Độc";
										color = 0x00E676;
									}
									break;
								case 23:
									name = "+Công & Giáp";
									color = 0x00E676;
									break;
								case 24:
									name = "Phản Đòn";
									color = 0x00E676;
									break;
								case 25:
									name = "Hồi Lực";
									color = 0x00E676;
									break;
								case 27:
									name = "Hồi Sinh";
									color = 0x00E676;
									break;
								case 3:
									name = "Choáng";
									color = 0xFF1744;
									isDebuff = true;
									break;
								case 4:
									name = "Trúng Độc";
									color = 0xFF1744;
									isDebuff = true;
									break;
								case 5:
									name = "+Giáp";
									color = 0x00E676;
									break;
								default:
									name = "Buff #" + buff.h;
									color = 0x00E676;
									break;
							}
							if (!hasBuffNamed(list, name)) {
								list.addElement(new BuffItem(name, formatTime(secLeft), (int) secLeft, isDebuff, color));
							}
						}
					}
				}
			}

			// 5. Thuộc tính tăng EXP từ trang bị (nếu có và chưa có buff exp giờ vàng)
			if (!hasBuffStartingWith(list, "buff exp") && !hasBuffStartingWith(list, "Buff exp")) {
				String expBonus = getExpBonusInfo();
				if (expBonus != null && expBonus.length() > 0) {
					int plusIdx = expBonus.indexOf("+");
					String expStr = plusIdx >= 0 ? expBonus.substring(plusIdx) : expBonus;
					list.addElement(new BuffItem("buff exp " + expStr, "", 0, false, 0x00E676));
				}
			}

		} catch (Exception ignored) {}
		return list;
	}

	private static boolean hasBuffNamed(java.util.Vector list, String name) {
		for (int i = 0; i < list.size(); i++) {
			BuffItem item = (BuffItem) list.elementAt(i);
			if (item != null && item.name != null && item.name.equals(name)) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasBuffStartingWith(java.util.Vector list, String prefix) {
		for (int i = 0; i < list.size(); i++) {
			BuffItem item = (BuffItem) list.elementAt(i);
			if (item != null && item.name != null && item.name.toLowerCase().startsWith(prefix.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Lấy danh sách chuỗi mô tả các hiệu ứng buff đang hoạt động kèm thời gian còn lại
	 */
	public static java.util.Vector getActiveBuffStrings() {
		java.util.Vector res = new java.util.Vector();
		java.util.Vector items = getActiveBuffItems();
		for (int i = 0; i < items.size(); i++) {
			BuffItem it = (BuffItem) items.elementAt(i);
			res.addElement(it.name + (it.timeStr != null && it.timeStr.length() > 0 ? ": " + it.timeStr : ""));
		}
		return res;
	}

	public static String formatTime(long sec) {
		if (sec <= 0) return "0s";
		long h = sec / 3600;
		long m = (sec % 3600) / 60;
		long s = sec % 60;
		if (h > 0) {
			return h + "h" + (m > 0 ? (m < 10 ? "0" + m : "" + m) + "p" : "00p");
		}
		if (m > 0) {
			return m + "p" + (s < 10 ? "0" + s : "" + s) + "s";
		}
		return s + "s";
	}

}
