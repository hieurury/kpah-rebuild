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
					|| (localclass_yc.c == 7)) && (localclass_ql.v > 0)) {
				return localclass_ql.u;
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

	/**
	 * Lấy danh sách chuỗi mô tả các hiệu ứng buff đang hoạt động kèm thời gian còn lại
	 */
	public static java.util.Vector getActiveBuffStrings() {
		java.util.Vector list = new java.util.Vector();
		try {
			if (class_acv.s == null || class_acv.s.q == null) {
				return list;
			}
			class_hw mainChar = (class_hw) class_acv.s.q;
			if (mainChar.de == null) {
				return list;
			}
			long now = System.currentTimeMillis();
			for (int i = 0; i < mainChar.de.size(); i++) {
				Object obj = mainChar.de.elementAt(i);
				if (obj instanceof class_zx) {
					class_zx buff = (class_zx) obj;
					long secLeft = (buff.a - now) / 1000L;
					if (secLeft > 0) {
						String name = getBuffName(buff.h);
						String time = formatTime(secLeft);
						list.addElement(name + ": " + time);
					}
				}
			}
		} catch (Exception ignored) {}
		return list;
	}

	private static String getBuffName(int id) {
		switch (id) {
			case 19: return "Bất Di Biến";
			case 20: return "Cương Thân Giáp";
			case 22: return "Độc Lưu Tiễn";
			case 23: return "Song Hộ Công Thủ";
			case 24: return "Di Lực Đảo Công";
			case 25: return "Hồi Công Lực Đạn";
			case 3:  return "Choáng";
			case 4:  return "Trúng Độc";
			case 5:  return "Phòng Thủ";
			default: return "Buff #" + id;
		}
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
