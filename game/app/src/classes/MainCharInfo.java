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
	 * Lấy danh sách đối tượng buff/debuff có cấu trúc đầy đủ thông tin
	 */
	public static java.util.Vector getActiveBuffItems() {
		java.util.Vector list = new java.util.Vector();
		try {
			if (class_acv.s == null || class_acv.s.q == null) {
				return list;
			}
			class_hw mainChar = (class_hw) class_acv.s.q;
			long now = System.currentTimeMillis();

			// 1. Quét các hiệu ứng buff/skill đang hoạt động trong mainChar.de
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
									if (mainChar.aJ == 4) { // Cung thủ
										name = "Tăng Công";
										color = 0xFF9100;
									} else {
										name = "Bảo Hộ";
										color = 0xFFD600;
									}
									break;
								case 20:
									name = "Tăng Giáp";
									color = 0x00E676;
									break;
								case 21:
									name = "Tăng Công";
									color = 0xFF9100;
									break;
								case 22:
									name = "Tẩm Độc";
									color = 0x76FF03;
									break;
								case 23:
									name = "Công & Giáp";
									color = 0x00E5FF;
									break;
								case 24:
									name = "Phản Đòn";
									color = 0xE040FB;
									break;
								case 25:
									name = "Hồi Lực";
									color = 0x40C4FF;
									break;
								case 27:
									name = "Hồi Sinh";
									color = 0xFFFF00;
									break;
								case 3:
									name = "Choáng";
									color = 0xFFD600;
									isDebuff = true;
									break;
								case 4:
									name = "Trúng Độc";
									color = 0xFF1744;
									isDebuff = true;
									break;
								case 5:
									name = "Tăng Giáp";
									color = 0x00E676;
									break;
								default:
									name = "Buff #" + buff.h;
									color = 0x81C784;
									break;
							}
							list.addElement(new BuffItem(name, formatTime(secLeft), (int) secLeft, isDebuff, color));
						}
					}
				}
			}

			// 2. Trạng thái Choáng (Stun) từ cW / cZ
			if (mainChar.cW && mainChar.cZ > now) {
				int secStun = (int) ((mainChar.cZ - now) / 1000L);
				if (secStun > 0 && !hasBuffNamed(list, "Choáng")) {
					list.addElement(new BuffItem("Choáng", formatTime(secStun), secStun, true, 0xFFD600));
				}
			}

			// 3. Trạng thái Trúng Độc từ dg / dh
			if (mainChar.dg > 0 && (now - mainChar.dg) < (long) mainChar.dh * 1000L) {
				int secPoison = (int) ((long) mainChar.dh - (now - mainChar.dg) / 1000L);
				if (secPoison > 0 && !hasBuffNamed(list, "Trúng Độc")) {
					list.addElement(new BuffItem("Trúng Độc", formatTime(secPoison), secPoison, true, 0xFF1744));
				}
			}

			// 4. Thuộc tính tăng EXP (nếu có)
			String expBonus = getExpBonusInfo();
			if (expBonus != null && expBonus.length() > 0) {
				int plusIdx = expBonus.indexOf("+");
				String expStr = plusIdx >= 0 ? expBonus.substring(plusIdx) : expBonus;
				list.addElement(new BuffItem("Tăng EXP", expStr, 0, false, 0x00E5FF));
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

	/**
	 * Lấy danh sách chuỗi mô tả các hiệu ứng buff đang hoạt động kèm thời gian còn lại
	 */
	public static java.util.Vector getActiveBuffStrings() {
		java.util.Vector res = new java.util.Vector();
		java.util.Vector items = getActiveBuffItems();
		for (int i = 0; i < items.size(); i++) {
			BuffItem it = (BuffItem) items.elementAt(i);
			res.addElement(it.name + ": " + it.timeStr);
		}
		return res;
	}

	private static String formatTime(long sec) {
		if (sec <= 0) return "0s";
		long h = sec / 3600;
		long m = (sec % 3600) / 60;
		long s = sec % 60;
		if (h > 0) {
			return h + "h" + (m > 0 ? m + "p" : "");
		}
		if (m > 0) {
			return m + "p" + (s > 0 ? s + "s" : "");
		}
		return s + "s";
	}

}
