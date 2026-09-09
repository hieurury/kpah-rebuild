package classes;

import classes.IAction;
import classes.class_abj;
import classes.class_go;
import classes.class_yi;

public final class class_np implements IAction {
    private class_abj a;
    private final int b;

    public class_np(class_abj class_abj2, int n) {
        this.a = class_abj2;
        this.b = n;
    }

    public final void perform() {
        if (this.b == 21) {
            class_go.a().l((short) this.b);
            return;
        }
        class_abj.a(this.a.r, class_yi.a(this.b), 500);
    }
}
