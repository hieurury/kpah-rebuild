package classes;

import classes.IAction;
import classes.class_nu;
import classes.class_ql;
import classes.class_sc;
import classes.class_ub;

final class class_on
implements IAction {
    private class_nu a;
    private final Object b;
    private final int c;
    private final int d;

    class_on(class_nu class_nu2, Object object, int n, int n2) {
        this.a = class_nu2;
        this.b = object;
        this.c = n;
        this.d = n2;
    }

    public final void perform() {
        try {
            if (this.b instanceof class_ql) {
                class_nu.a(this.a, (class_ql)this.b, this.a.q, this.c, this.d);
                return;
            }
            if (class_sc.l != null) {
                int n = class_sc.l.length;
                int n2 = 0;
                while (n2 < n) {
                    if (class_sc.l[n2] != null && this.b instanceof class_ub && class_sc.l[n2].e == ((class_ub)this.b).e) {
                        String tip = ModHelpers.getPotionTooltip(this.a, n2);
                        class_nu.a(this.a, tip, this.c, this.d);
                        return;
                    }
                    ++n2;
                }
            }
        } catch (Throwable t) {
        }
    }
}
