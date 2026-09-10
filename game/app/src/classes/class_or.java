package classes;

import classes.IAction;
import classes.class_nu;

final class class_or
implements IAction {
    private class_nu a;
    private final int b;
    private final int c;

    class_or(class_nu class_nu2, int n, int n2) {
        this.a = class_nu2;
        this.b = n;
        this.c = n2;
    }

    public final void perform() {
        try {
            String tip = ModHelpers.getPotionTooltip(this.a, this.b);
            class_nu.a(this.a, tip, this.c % this.a.g * 18, this.c / this.a.g * 18);
        } catch (Throwable t) {
        }
    }
}
