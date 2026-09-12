package classes;

import classes.class_abm;
import classes.class_acd;
import classes.class_acv;
import classes.class_ap;
import classes.class_bb;
import classes.class_c;
import classes.class_di;
import classes.class_gx;
import classes.class_hw;
import classes.class_yb;
import classes.class_yi;
import classes.class_zp;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class class_de extends class_acd {
    private int c;
    private int d;
    private byte e;
    private int f;
    private int g;
    private int h;
    private int i;
    public static Image b;
    public static Image bRed; // Đạn màu đỏ cho quái cận chiến
    private class_gx j = new class_gx();
    private static int[] k;
    private byte[][] l;
    private byte m;
    private byte n;

    static {
        k = new int[]{-1, -1, -1, 4, 3, 4, 6, -1, 7, 16, 19};
    }

    public class_de() {
        byte[][] byArrayArray = new byte[3][];
        byte[] byArray = new byte[4];
        byArray[2] = 1;
        byArray[3] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{2, 2, 3, 3};
        byArrayArray[2] = new byte[]{4, 4, 5, 5};
        this.l = byArrayArray;
        this.m = 0;
    }

    public final void a(int n) {
        this.j.a(n);
    }

    public final void a(int n, int n2, int n3, int n4, byte by, class_ap class_ap2, class_ap class_ap3) {
        this.j.a(n, n2, n3, (int)class_ap2.D, class_ap3);
        this.d = n;
        this.e = by;
        this.f = n4;
        this.c = n4;
    }

    /**
     * Lấy viên đạn màu đỏ tạo từ viên đạn gốc (fire.png) cho quái cận chiến
     */
    public static Image getRedBullet() {
        if (bRed != null) {
            return bRed;
        }
        if (b == null) {
            return null;
        }
        try {
            int w = b.getWidth();
            int h = b.getHeight();
            int[] rgb = new int[w * h];
            b.getRGB(rgb, 0, w, 0, 0, w, h);
            for (int idx = 0; idx < rgb.length; idx++) {
                int p = rgb[idx];
                int a = (p >> 24) & 0xFF;
                if (a > 0) {
                    int r = (p >> 16) & 0xFF;
                    int g = (p >> 8) & 0xFF;
                    int bl = p & 0xFF;
                    // Chuyển sang tông đỏ rực (Crimson Fire)
                    int newR = Math.min(255, (int)(r * 1.15) + (g / 2));
                    int newG = g / 5;
                    int newB = bl / 5;
                    rgb[idx] = (a << 24) | (newR << 16) | (newG << 8) | newB;
                }
            }
            bRed = Image.createRGBImage(rgb, w, h, true);
        } catch (Exception e) {
            bRed = b;
        }
        return bRed;
    }

    public final void a() {
        class_de class_de2;
        this.j.a();
        this.h = this.j.a;
        this.i = this.j.b;
        if (this.j.i) {
            class_de2 = this;
            short s = class_de2.j.d.cK;
            short s2 = class_de2.j.d.cL;
            switch (class_de2.d) {
                case 0: {
                    class_acv.s.a(s, s2);
                    break;
                }
                case 1: {
                    class_abm.a((int)s, (int)(s2 - 10), (int)3);
                    class_abm.b((int)s, (int)(s2 - 25), (int)11);
                    class_abm.a((int)s, (int)(s2 - 15), (int)11);
                    class_abm.a((int)(s - 10), (int)(s2 - 20), (int)11);
                    class_abm.a((int)(s + 10), (int)(s2 - 20), (int)11);
                    break;
                }
                case 2: {
                    class_abm.a((int)s, (int)(s2 - 10), (int)5);
                    break;
                }
                case 3: {
                    class_abm.a((int)s, (int)(s2 - 10), (int)7);
                    break;
                }
                case 4: {
                    class_abm.b((int)s, (int)(s2 - 25), (int)15);
                    class_abm.a((int)s, (int)(s2 - 15), (int)15);
                    class_abm.a((int)(s - 10), (int)(s2 - 20), (int)15);
                    class_abm.a((int)(s + 10), (int)(s2 - 20), (int)15);
                    break;
                }
                case 5: 
                case 7: {
                    class_abm.a((int)s, (int)(s2 - 10), (int)30);
                    break;
                }
                case 8: {
                    class_abm.a((int)s, (int)(s2 - 10), (int)3);
                    class_abm.b((int)s, (int)(s2 - 25), (int)11);
                    class_abm.a((int)s, (int)(s2 - 15), (int)9);
                    class_abm.a((int)(s - 10), (int)(s2 - 20), (int)11);
                    class_abm.a((int)(s + 10), (int)(s2 - 20), (int)9);
                    break;
                }
                case 9: {
                    class_abm.a((int)s, (int)(s2 - 15), (int)50);
                }
            }
            if (class_de2.e != 0 && class_de2.e < class_zp.d.length) {
                class_acv.s.a(class_zp.d[class_de2.e], 0, (int)s, s2 - 25, 1, -2);
            }
            if (class_de2.c != 2000000) {
                if (class_de2.c != 0) {
                    if (class_de2.d < 20) {
                        class_acv.s.a("-" + class_de2.c, 0, (int)s, s2 - 15, 1, -2);
                    } else {
                        class_acv.s.a("-" + class_de2.f, 0, (int)s, s2 - 15, 1, -2);
                    }
                } else {
                    class_acv.s.a("MISS", 0, (int)s, s2 - 15, 1, -2);
                }
            }
            class_de2.j.d.u = 2;
            if (class_de2.j.d.cF == 1) {
                if (class_de2.e == 0) {
                    ((class_bb)class_de2.j.d).f();
                    class_abm.b.addElement(new class_di((int)s, s2 - 10, 11));
                } else if (class_de2.e == 2) {
                    ((class_bb)class_de2.j.d).k();
                    class_abm.b.addElement(new class_di((int)s, s2 - 10, 12));
                }
            } else if (class_de2.j.d.cF == 0) {
                if (class_de2.e == 0) {
                    ((class_hw)class_de2.j.d).f();
                    class_abm.b.addElement(new class_di((int)s, s2 - 10, 11));
                } else if (class_de2.e == 2) {
                    ((class_hw)class_de2.j.d).k();
                    class_abm.b.addElement(new class_di((int)s, s2 - 10, 12));
                }
            }
            class_de2.a = true;
        }
        switch (this.d) {
            case 0: {
                class_abm.a((int)this.h, (int)this.i, (int)1);
                return;
            }
            case 5: 
            case 7: {
                class_abm.a((int)this.h, (int)this.i, (int)29);
                return;
            }
            case 1: {
                class_abm.a((int)this.h, (int)this.i, (int)2);
                return;
            }
            case 2: {
                class_abm.a((int)this.h, (int)this.i, (int)4);
                return;
            }
            case 3: {
                class_abm.a((int)this.h, (int)this.i, (int)6);
                class_abm.a((int)this.h, (int)this.i, (int)7);
                return;
            }
            case 4: {
                class_abm.a((int)this.h, (int)this.i, (int)8);
                return;
            }
            case 8: {
                return;
            }
            case 9: {
                class_abm.b((int)this.h, (int)(this.i + 25), (int)59);
                class_abm.b(new class_c(this.h, this.i + 25));
                class_abm.b(new class_c(this.h, this.i + 25));
                this.m = (byte)(this.m + 1);
                if (this.m <= 3) break;
                this.m = 0;
                return;
            }
            case 10: {
                ++this.g;
                if (this.g > this.l[this.j.g].length - 1) {
                    this.g = 0;
                }
                this.n = this.l[this.j.g][this.g];
            }
        }
    }

    public final void a(Graphics graphics) {
        if (this.d < 20) {
            int n;
            Image image;
            if (k[this.d] != -1 && (image = class_yi.c((int)(n = k[this.d]))) != null) {
                if (this.d != 10 && this.d != 9) {
                    graphics.drawRegion(image, 0, this.j.g * class_yb.c[1][n], (int)class_yb.c[0][n], (int)class_yb.c[1][n], this.j.h, this.h, this.i, 3);
                    return;
                }
                if (this.d == 10) {
                    graphics.drawRegion(image, 0, this.n * class_yb.c[1][n], (int)class_yb.c[0][n], (int)class_yb.c[1][n], this.j.h, this.h, this.i, 3);
                    return;
                }
                if (this.d == 9) {
                    graphics.drawRegion(image, 0, this.m * class_yb.c[1][n], (int)class_yb.c[0][n], (int)class_yb.c[1][n], 0, this.h, this.i, 3);
                    return;
                }
            }
        } else if (this.d == 21) {
            // Đạn màu đỏ quái cận chiến
            Image redImg = getRedBullet();
            graphics.drawImage(redImg != null ? redImg : b, this.h, this.i, 3);
        } else {
            // Đạn lửa gốc quái đánh xa
            graphics.drawImage(b, this.h, this.i, 3);
        }
    }
}
