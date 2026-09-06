package classes;

import classes.class_abj;
import classes.class_acv;
import classes.class_vh;
import classes.class_yi;
import javax.microedition.lcdui.Graphics;

public final class class_zq extends class_vh {
    private int a = 0;
    private int b;

    public class_zq(short s, short s2) {
        this.cF = (byte)126;
        this.cK = s;
        this.cL = s2;
        this.b = 0;
    }

    public final void a(Graphics graphics) {
        class_zq class_zq2 = this;
        if (!(class_zq2.cK < class_abj.j ? false : (class_zq2.cK > class_abj.j + class_acv.m ? false : (class_zq2.cL < class_abj.k ? false : class_zq2.cL <= class_abj.k + class_acv.n + 30)))) {
            return;
        }
        if (class_yi.s == null) {
            System.out.println("Skipped drawing explosion because class_yi.s is null!");
            return;
        }
        try {
            graphics.drawRegion(class_yi.s, 0, this.a * 24, 24, 24, 0, this.cK - 12, this.cL - 24, 0);
        } catch (Exception e) {
            System.out.println("Explosion draw exception: " + e.getMessage());
        }
    }

    public final void a(short s, short s2) {
        this.cK = s;
        this.cL = s2;
    }

    public final void a() {
        ++this.b;
        if (this.b > 8) {
            this.b = 0;
            this.cE = true;
        }
        this.a = this.b >> 1;
    }
}
