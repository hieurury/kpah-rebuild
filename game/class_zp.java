/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  classes.class_acv
 */
package classes;

import classes.class_acv;

public final class class_zp {
    public int a;
    public byte b;
    public int c = -1;
    public static final String[] d = new String[]{"", "miss", "CHI MANG", "XUYEN GIAP", "BAOKICH"};

    public class_zp(int n, byte by) {
        this.a = n;
        this.b = by;
    }

    public class_zp() {
    }

    public static void a(byte by, int n, int n2) {
        if (by > 0) {
            class_acv.s.a(d[by], 0, n, n2, 1, -2);
        }
    }
}
