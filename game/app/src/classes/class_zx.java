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
    public byte effectType = 0; // 0: độc tím, 7: hóa đá xám tro, 9: giảm giáp đỏ cam, 10: mù đen tối, 11: vết thương sâu đỏ máu
    private static Image purpleRingImg = null;
    private static Image stoneRingImg = null;
    private static Image armorBreakRingImg = null;
    private static Image blindRingImg = null;
    private static Image deepWoundRingImg = null;
    private static Image electricAuraImg = null;

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
        if ((this.h == 22 || this.h == 24) && this.isDebuff) {
            Image ringImgToDraw = null;
            if (this.effectType == 12) {
                // Hiệu ứng Nhiễm Điện: Vòng hào quang kiếm khí vàng neon (từ effect 24 của Kiếm Khách)
                if (electricAuraImg == null) {
                    try {
                        Image orig = class_yi.d(24);
                        if (orig == null) orig = class_yi.d(22);
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
                                int bright = Math.max(r, Math.max(g, b));
                                int newR = Math.min(255, (int)(bright * 1.05f) + 30);
                                int newG = Math.min(255, (int)(bright * 0.95f) + 15);
                                int newB = Math.min(255, (int)(bright * 0.15f));
                                rgb[i] = (alpha << 24) | (newR << 16) | (newG << 8) | newB;
                            }
                            electricAuraImg = Image.createRGBImage(rgb, w, h, true);
                        }
                    } catch (Throwable t) {
                        electricAuraImg = null;
                    }
                }
                ringImgToDraw = electricAuraImg;
            } else if (this.effectType == 7) {
                // Hiệu ứng Hóa Đá: Vòng đá xám tro
                if (stoneRingImg == null) {
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
                                int gray = (r * 30 + g * 59 + b * 11) / 100;
                                rgb[i] = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                            }
                            stoneRingImg = Image.createRGBImage(rgb, w, h, true);
                        }
                    } catch (Throwable t) {
                        stoneRingImg = null;
                    }
                }
                ringImgToDraw = stoneRingImg;
            } else if (this.effectType == 9) {
                // Hiệu ứng Giảm Giáp: Vòng đỏ cam vỡ giáp
                if (armorBreakRingImg == null) {
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
                                int g = (p >> 8) & 0xFF;
                                int newR = Math.min(255, (int)(g * 1.2f) + 40);
                                int newG = (int)(g * 0.35f);
                                int newB = 0;
                                rgb[i] = (alpha << 24) | (newR << 16) | (newG << 8) | newB;
                            }
                            armorBreakRingImg = Image.createRGBImage(rgb, w, h, true);
                        }
                    } catch (Throwable t) {
                        armorBreakRingImg = null;
                    }
                }
                ringImgToDraw = armorBreakRingImg;
            } else if (this.effectType == 10) {
                // Hiệu ứng Mù: Vòng đen khói tối
                if (blindRingImg == null) {
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
                                int g = (p >> 8) & 0xFF;
                                int dark = Math.min(60, (int)(g * 0.25f));
                                rgb[i] = (alpha << 24) | (dark << 16) | (dark << 8) | dark;
                            }
                            blindRingImg = Image.createRGBImage(rgb, w, h, true);
                        }
                    } catch (Throwable t) {
                        blindRingImg = null;
                    }
                }
                ringImgToDraw = blindRingImg;
            } else if (this.effectType == 11) {
                // Hiệu ứng Vết Thương Sâu: Vòng đỏ máu đậm
                if (deepWoundRingImg == null) {
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
                                int g = (p >> 8) & 0xFF;
                                int newR = Math.min(255, (int)(g * 1.4f) + 60);
                                int newG = (int)(g * 0.15f);
                                rgb[i] = (alpha << 24) | (newR << 16) | (newG << 8);
                            }
                            deepWoundRingImg = Image.createRGBImage(rgb, w, h, true);
                        }
                    } catch (Throwable t) {
                        deepWoundRingImg = null;
                    }
                }
                ringImgToDraw = deepWoundRingImg;
            } else {
                // Mặc định: Hiệu ứng Độc (màu tím)
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
                ringImgToDraw = purpleRingImg;
            }

            if (ringImgToDraw != null) {
                if (this.i == -1) {
                    this.i = 0;
                }
                int frameY = b[this.h][this.i] * this.g;
                graphics.drawRegion(ringImgToDraw, 0, frameY, class_di.c[this.h], this.g, 0, this.e, this.f, 3);
                return;
            }
        }
        super.a(graphics);
    }
}
