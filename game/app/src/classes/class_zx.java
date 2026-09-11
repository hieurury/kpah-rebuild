package classes;

import classes.class_acv;
import classes.class_di;
import classes.class_yi;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class class_zx extends class_di {
    private int o = -1;
    public long a = 0L;
    public boolean isDebuff = false;
    private static Image purpleRingImg = null;

    public class_zx(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public final void a(int n) {
        this.o = n;
        this.a = System.currentTimeMillis() + (long)(this.o * 1000);
        if (this.o <= 0) {
            this.j = true;
        }
    }

    public final boolean b() {
        return this.h == 20 || this.h == 22 || this.h == 23 || this.h == 24 || this.h == 25 || this.h == 27;
    }

    public final void a() {
        if (class_acv.l % 2 == 0) {
            this.i = (this.i + 1) % class_di.b[this.h].length;
        }
        if (System.currentTimeMillis() > this.a) {
            this.j = true;
        }
    }

    public final void a(int n, int n2) {
        this.e = n;
        this.f = n2;
    }

    public void a(Graphics graphics) {
        if (this.h == 22 && this.isDebuff) {
            if (purpleRingImg == null) {
                try {
                    Image orig = class_yi.d(22);
                    if (orig != null) {
                        int w = orig.getWidth();
                        int h = orig.getHeight();
                        int[] rgb = new int[w * h];
                        orig.getRGB(rgb, 0, w, 0, 0, w, h);
                        for (int i = 0; i < rgb.length; i++) {
                            int p = rgb[i];
                            int alpha = (p >> 24) & 0xFF;
                            if (alpha == 0) continue;
                            int r = (p >> 16) & 0xFF;
                            int g = (p >> 8) & 0xFF;
                            int b = p & 0xFF;
                            if (g > r && g >= b) {
                                int newR = Math.min(255, (int)(g * 0.95f) + 30);
                                int newG = (int)(g * 0.20f);
                                int newB = Math.min(255, (int)(g * 1.15f) + 40);
                                rgb[i] = (alpha << 24) | (newR << 16) | (newG << 8) | newB;
                            }
                        }
                        purpleRingImg = Image.createRGBImage(rgb, w, h, true);
                    }
                } catch (Throwable t) {
                    purpleRingImg = null;
                }
            }
            if (purpleRingImg != null) {
                if (this.i == -1) {
                    this.i = 0;
                }
                int frameY = b[this.h][this.i] * this.g;
                graphics.drawRegion(purpleRingImg, 0, frameY, class_di.c[this.h], this.g, 0, this.e, this.f, 3);
                return;
            }
        }
        super.a(graphics);
    }
}
