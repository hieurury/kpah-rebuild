package classes;

import classes.IAction;
import classes.class_acv;
import classes.class_go;
import classes.class_nu;
import classes.class_ql;
import classes.class_ru;
import java.util.Vector;

public final class class_rs implements IAction {
    private class_ru a;
    private final int b;
    private final int c;

    public class_rs(class_ru class_ru2, int n, int n2) {
        this.a = class_ru2;
        this.b = n;
        this.c = n2;
    }

    public final void perform() {
        try {
            int n = Integer.parseInt(class_acv.y.a.e());
            if (n <= 0) {
                class_acv.a("Không được nhập số âm hoặc 0.");
                return;
            }
            if (n > 120) {
                class_acv.a("Chỉ được mua nhiều nhất 120 đơn vị một lần.");
                return;
            }
            long totalCost = (long) this.c * (long) n;
            if (totalCost > class_acv.s.q.br) {
                class_acv.a("Hết tiền");
                return;
            }
            class_ru class_ru2 = this.a;
            class_ql class_ql2 = (class_ql) class_ru2.a.o.elementAt(this.b);

            // Gửi gói tin mua potion trực tiếp lên server (opcode 24)
            Vector buyVec = new Vector();
            class_ql buyItem = new class_ql();
            buyItem.l = class_ql2.l;
            buyItem.j = (short) n;
            buyVec.addElement(buyItem);
            class_go.a().c(buyVec);

            // Cập nhật số lượng hiển thị và trừ tiền
            class_ql2.j = (short) (class_ql2.j + n);
            class_acv.s.q.br -= totalCost;
            class_acv.g();

            // Clear giỏ hàng tạm của menu để tránh đóng menu gửi nhầm dạng trang bị
            this.a.a.o.removeAllElements();
            this.a.a.z = 0;
            this.a.a.s();
            class_nu.a(this.a.a, class_ql2.d(), this.a.a.f % this.a.a.g * 18, this.a.a.f / this.a.a.g * 18);
            return;
        } catch (Exception exception) {
            class_acv.a("Không được nhập chữ");
            return;
        }
    }
}
