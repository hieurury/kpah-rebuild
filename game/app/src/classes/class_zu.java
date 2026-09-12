package classes;

public final class class_zu {
	private short a; // attribute ID
	private short b; // attribute value

	public class_zu(short s, short s2) {
		this.a = s;
		this.b = s2;
	}

	public final byte a(boolean bl) {
		if (bl) {
			return 2; // red if disabled/unmet
		}
		// Chỉ số HP -> Màu Đỏ (2)
		if (this.a == 7 || this.a == 33 || this.a == 78 || this.a == 101 || this.a == 109) {
			return 2;
		}
		// Chỉ số MP -> Màu Xanh dương (1)
		if (this.a == 8 || this.a == 34 || this.a == 79) {
			return 1;
		}
		// Chỉ số Đặc biệt (Nhất phẩm, Bạo kích, Xuyên giáp, Sát thương chuẩn, May mắn, Giảm ST cuối) -> Màu Vàng (5)
		if (this.a == 118 || this.a == 30 || this.a == 31 || this.a == 41 || this.a == 65 || this.a == 115 || this.a == 117 || this.a == 9 || this.a == 119) {
			return 5;
		}
		// Chỉ số thường: kiểm tra template color nếu có
		if (class_yc.p != null) {
			class_it tmpl = (class_it) class_yc.p.get(String.valueOf(this.a));
			if (tmpl != null) {
				if (tmpl.b == 5) return 5;
				if (tmpl.b == 2) return 2;
				if (tmpl.b == 1 || tmpl.b == 0) return 1;
				return tmpl.b;
			}
		}
		// Mặc định cho chỉ số thường -> Màu Xanh dương (1)
		return 1;
	}

	public final String a(int n) {
		String string = null;
		// Tên ghi đè cố định đảm bảo không bị lỗi "chỉ số 7", "chỉ số 8"
		if (this.a == 7 || this.a == 33) {
			string = "Tăng HP";
		} else if (this.a == 8 || this.a == 34) {
			string = "Tăng MP";
		} else if (this.a == 9) {
			string = "May mắn";
		} else if (this.a == 118) {
			string = "Giảm sát thương cuối";
		} else if (this.a >= 43 && this.a <= 57) {
			string = class_qz.k[n][this.a - 43];
		} else if (class_yc.p != null) {
			class_it tmpl = (class_it) class_yc.p.get(String.valueOf(this.a));
			if (tmpl != null) {
				string = tmpl.c;
			}
		}
		if (string == null || string.length() == 0) {
			string = "Chỉ số " + this.a;
		}
		return string + ": ";
	}

	public final boolean a() {
		// Thuộc tính phần trăm (%)
		if (this.a == 7 || this.a == 8 || this.a == 30 || this.a == 31 || this.a == 41 || this.a == 118 || this.a == 2 || this.a == 3 || this.a == 4 || this.a == 65) {
			return true;
		}
		if (this.a == 33 || this.a == 34 || this.a == 9 || this.a == 0 || this.a == 1 || this.a == 5 || this.a == 6 || this.a == 10 || this.a == 11 || this.a == 12 || this.a == 13) {
			return false;
		}
		if (class_yc.p != null) {
			class_it tmpl = (class_it) class_yc.p.get(String.valueOf(this.a));
			if (tmpl != null) {
				byte by = tmpl.a;
				return by == 1 || by == 2;
			}
		}
		return false;
	}

	public final String b() {
		String prefix = (this.b > 0) ? "+" : "";
		if (class_yc.p != null) {
			class_it tmpl = (class_it) class_yc.p.get(String.valueOf(this.a));
			if (tmpl != null && tmpl.a == 2) {
				return prefix + (this.b / 10) + "." + (this.b % 10);
			}
		}
		return prefix + this.b;
	}

	public final short c() {
		return this.a;
	}
}
