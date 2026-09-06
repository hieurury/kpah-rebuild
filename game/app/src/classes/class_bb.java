/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  classes.class_acc
 *  classes.class_ace
 *  classes.class_acv
 *  classes.class_ap
 *  classes.class_by
 *  classes.class_go
 *  classes.class_ls
 *  classes.class_mo
 *  classes.class_qz
 *  classes.class_vh
 *  classes.class_zx
 *  javax.microedition.lcdui.Graphics
 */
package classes;

import classes.class_abj;
import classes.class_acc;
import classes.class_ace;
import classes.class_acv;
import classes.class_ap;
import classes.class_by;
import classes.class_go;
import classes.class_ls;
import classes.class_mo;
import classes.class_qz;
import classes.class_vh;
import classes.class_yi;
import classes.class_zx;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class class_bb
extends class_ap {
    public boolean a = false;
    public class_ap b;
    public static final byte[][][] c;
    public class_mo d;
    public class_ap e;
    public int f = -1;
    public short g;
    public short h;
    public short i;
    public short j;
    public short k;
    public short l;
    long m;
    public boolean n;
    int o;
    int p;
    byte[] q;
    boolean r;
    long s;
    long X;
    int Y;
    int Z;
    short aa;
    byte ab;
    byte ac;
    public short ad;
    public boolean ae;
    public long af;
    int ag;
    public int ah;
    public int ai;

    static {
        byte[][][] byArrayArray = new byte[5][][];
        byte[][] byArrayArray2 = new byte[4][];
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArrayArray2[0] = byArray;
        byArrayArray2[1] = new byte[]{4, 5, 6, 7};
        byArrayArray2[2] = new byte[]{8, 9, 10, 11};
        byArrayArray2[3] = new byte[]{12, 13, 14, 15};
        byArrayArray[0] = byArrayArray2;
        byte[][] byArrayArray3 = new byte[4][];
        byte[] byArray2 = new byte[4];
        byArray2[1] = 1;
        byArray2[2] = 2;
        byArray2[3] = 3;
        byArrayArray3[0] = byArray2;
        byArrayArray3[1] = new byte[]{4, 5, 6, 7};
        byte[] byArray3 = new byte[4];
        byArray3[1] = 1;
        byArray3[2] = 2;
        byArray3[3] = 3;
        byArrayArray3[2] = byArray3;
        byArrayArray3[3] = new byte[]{4, 5, 6, 7};
        byArrayArray[1] = byArrayArray3;
        byte[][] byArrayArray4 = new byte[4][];
        byte[] byArray4 = new byte[4];
        byArray4[1] = 1;
        byArray4[2] = 2;
        byArray4[3] = 3;
        byArrayArray4[0] = byArray4;
        byte[] byArray5 = new byte[4];
        byArray5[1] = 1;
        byArray5[2] = 2;
        byArray5[3] = 3;
        byArrayArray4[1] = byArray5;
        byte[] byArray6 = new byte[4];
        byArray6[1] = 1;
        byArray6[2] = 2;
        byArray6[3] = 3;
        byArrayArray4[2] = byArray6;
        byte[] byArray7 = new byte[4];
        byArray7[1] = 1;
        byArray7[2] = 2;
        byArray7[3] = 3;
        byArrayArray4[3] = byArray7;
        byArrayArray[2] = byArrayArray4;
        byte[][] byArrayArray5 = new byte[4][];
        byte[] byArray8 = new byte[4];
        byArray8[1] = 1;
        byArray8[2] = 2;
        byArray8[3] = 3;
        byArrayArray5[0] = byArray8;
        byArrayArray5[1] = new byte[]{4, 5, 6, 7};
        byte[] byArray9 = new byte[4];
        byArray9[1] = 1;
        byArray9[2] = 2;
        byArray9[3] = 3;
        byArrayArray5[2] = byArray9;
        byArrayArray5[3] = new byte[]{4, 5, 6, 7};
        byArrayArray[3] = byArrayArray5;
        byArrayArray[4] = new byte[][]{new byte[4], new byte[4], new byte[4], new byte[4]};
        c = byArrayArray;
    }

    public class_bb() {
        byte[] byArray = new byte[5];
        byArray[0] = 1;
        byArray[1] = 4;
        byArray[3] = 2;
        byArray[4] = 3;
        this.q = byArray;
        this.r = false;
        this.aa = (short)-100;
        this.ad = (short)60;
        this.ae = false;
        this.af = System.currentTimeMillis();
        this.ag = 12;
        this.ah = 0;
        this.ai = 0;
        this.n = false;
        this.D = (byte)class_ap.V.nextInt(4);
        this.Z = class_yi.a(10, 20);
        ((class_vh)this).cV = 0;
        this.Y = 0;
        this.k = 0;
        this.j = 0;
        this.i = 0;
    }

    public final String a_() {
        if (class_yi.T == null) {
            return "";
        }
        if (class_yi.T[this.l] == null) {
            return "";
        }
        return class_yi.T[this.l].l;
    }

    public final void a(short s) {
        this.l = s;
        if (class_yi.T != null && class_yi.T[s] == null) {
            class_yi.T[s] = new class_ace();
            class_go.a().d(0, (int)this.l);
        }
        this.m = System.currentTimeMillis() + 10000L;
    }

    public final void r() {
        this.b = null;
        if (((class_vh)this).cV != 5) {
            ((class_vh)this).cV = (byte)7;
        }
    }

    public final void a(class_ap class_ap2) {
        this.b = class_ap2;
        ((class_vh)this).cV = (byte)6;
    }

    public final void s() {
    }

    public void a(Graphics graphics) {
        class_bb class_bb2 = this;
        if (!(((class_vh)class_bb2).cK < class_abj.j ? false : (((class_vh)class_bb2).cK > class_abj.j + class_acv.m ? false : (((class_vh)class_bb2).cL < class_abj.k ? false : ((class_vh)class_bb2).cL <= class_abj.k + class_acv.n + 30))) || ((class_vh)this).cV == 8 || this.v <= 0) {
            return;
        }
        this.a(graphics, ((class_vh)this).cK, ((class_vh)this).cL, false);
        if (!this.S) {
            if (this.x != 0) {
                this.O = (byte)3;
            }
            if (class_yi.T[this.l] != null) {
                byte by = c[class_yi.T[this.l].b][this.D][this.O];
                byte by2 = 0;
                byte by3 = 0;
                if (class_yi.T[this.l].a != null) {
                    by2 = class_yi.T[this.l].i;
                    by3 = class_yi.T[this.l].j;
                }
                if (this.U != null) {
                    int n = 0;
                    while (n < this.U.size()) {
                        ((class_acc)this.U.elementAt(n)).a(graphics, (int)((class_vh)this).cK, (int)((class_vh)this).cL);
                        ++n;
                    }
                }
                graphics.drawImage(class_yi.j, ((class_vh)this).cK + by2, ((class_vh)this).cL + by3, 3);
                class_yi.T[this.l].a(graphics, ((class_vh)this).cK + this.B, ((class_vh)this).cL + this.C + this.H + this.x, 0, 0, (int)by);
            }
            int n = 0;
            while (n < ((class_vh)this).de.size()) {
                ((class_zx)((class_vh)this).de.elementAt(n)).a(graphics);
                ++n;
            }
            if (this.T != null) {
                n = 0;
                while (n < this.T.size()) {
                    ((class_acc)this.T.elementAt(n)).a(graphics, (int)((class_vh)this).cK, (int)((class_vh)this).cL);
                    ++n;
                }
            }
        }
        this.b(graphics, ((class_vh)this).cK, ((class_vh)this).cL, false);
        super.a(graphics);
    }

    public void a_(Graphics graphics, int n, int n2) {
        if (this.x != 0) {
            this.O = (byte)3;
        }
        try {
            n2 = c[class_yi.T[this.l].b][0][this.O];
            graphics.drawRegion(class_yi.T[this.l].a, 0, n2 * class_yi.T[this.l].f, (int)class_yi.T[this.l].e, (int)class_yi.T[this.l].f, 0, n - class_yi.T[this.l].g, 0, 0);
            graphics.drawRegion(class_yi.A, this.q[this.P] << 4, 0, 16, 16, 0, n - 15, 23, 20);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void t() {
        int n = 0;
        while (n < ((class_vh)this).de.size()) {
            class_zx class_zx2 = (class_zx)((class_vh)this).de.elementAt(n);
            class_zx2.a();
            if (class_zx2.j) {
                ((class_vh)this).de.removeElementAt(n);
            } else {
                class_zx2.a((int)((class_vh)this).cK, class_zx2.b() ? ((class_vh)this).cL - 12 : ((class_vh)this).cL);
            }
            ++n;
        }
    }

    public void a(int n, int n2) {
        ((class_vh)this).cV = (byte)5;
        this.i = 0;
        this.j = (short)n;
        this.k = (short)n2;
        if (this.n) {
            class_acv.s.a(((class_vh)this).cK, ((class_vh)this).cL);
            ((class_vh)this).cE = true;
        }
    }

    public void a() {
        this.i();
        this.I = class_yi.T[this.l].c;
        if ((((class_vh)this).cE || this.v <= 0) && ((class_vh)this).dd != -1 && ((class_vh)this).cV != 8) {
            ((class_vh)this).cV = (byte)8;
        }
        if (!this.ae && System.currentTimeMillis() - this.af > 15000L) {
            this.af = System.currentTimeMillis();
            class_acv.s.D.c(((class_vh)this).cG);
        }
        if (class_yi.T != null) {
            if (class_yi.T[this.l] == null) {
                if (!this.r && System.currentTimeMillis() > this.m) {
                    this.r = false;
                    class_go.a().d(0, (int)this.l);
                    this.m = System.currentTimeMillis() + 10000L;
                }
            } else if (!this.r) {
                this.a(this.l);
                this.r = true;
                this.I = class_yi.T[this.l].c;
                ((class_vh)this).cM = class_yi.T[this.l].d;
                if (class_yi.T[this.l].a == null) {
                    class_yi.T[this.l].b();
                }
            }
        }
        if (((class_vh)this).cW && System.currentTimeMillis() > ((class_vh)this).cZ) {
            ((class_vh)this).cW = false;
        }
        this.c();
        this.t();
        super.a();
        if (this.u > 0) {
            --this.u;
            if (this.u == 0) {
                if (this.t < 0) {
                    this.t = 0;
                }
                if (this.v > this.t || this.t == 0) {
                    this.v = this.t;
                }
                if (this.v == 0) {
                    ((class_vh)this).cV = (byte)4;
                }
            }
        }
        this.d();
        switch (((class_vh)this).cV) {
            case 4: {
                this.O = (byte)3;
                this.i = (short)(this.i + 1);
                if (this.i == 5) {
                    class_acv.s.a(((class_vh)this).cK, ((class_vh)this).cL);
                }
                if (this.i <= 7) break;
                this.i = 0;
                ((class_vh)this).cV = 0;
                break;
            }
            case 0: {
                this.O = 0;
                break;
            }
            case 3: {
                this.O = (byte)2;
                this.i = (short)(this.i + 1);
                if (this.i > 6) {
                    this.i = 0;
                    ((class_vh)this).cV = (byte)2;
                    break;
                }
                if (this.d == null) break;
                this.d.a(this);
                break;
            }
            case 5: {
                this.O = (byte)3;
                this.i = (short)(this.i + 1);
                if (!this.a) {
                    ((class_vh)this).cK = (short)(((class_vh)this).cK + this.j);
                    ((class_vh)this).cL = (short)(((class_vh)this).cL + this.k);
                }
                this.j = (short)(this.j >> 1);
                this.k = (short)(this.k >> 1);
                if (this.i == 5) {
                    class_acv.s.a(((class_vh)this).cK, ((class_vh)this).cL);
                    this.X = System.currentTimeMillis() + (long)((class_vh)this).dd;
                }
                this.n = true;
                this.s = System.currentTimeMillis() / 1000L + 3L;
                if (this.i <= 7) break;
                this.n = false;
                ((class_vh)this).cE = true;
                break;
            }
            case 8: {
                if (class_acv.s.r != null && class_acv.s.r == this && class_acv.s.q.cV == 0) {
                    class_acv.s.r = null;
                }
                if (this.X - System.currentTimeMillis() >= 0L) break;
                ((class_vh)this).cE = false;
                this.n = false;
                this.D = (byte)class_ap.V.nextInt(4);
                this.Z = class_yi.a(10, 20);
                ((class_vh)this).cV = 0;
                this.Y = 0;
                this.k = 0;
                this.j = 0;
                this.i = 0;
                ((class_vh)this).cK = this.g = ((class_vh)this).cO;
                ((class_vh)this).cL = this.h = ((class_vh)this).cP;
                this.v = this.w;
                class_acv.s.a(((class_vh)this).cK, ((class_vh)this).cL);
                this.X = System.currentTimeMillis() + (long)((class_vh)this).dd;
                break;
            }
            case 2: 
            case 6: 
            case 7: {
                this.i = (short)(this.i + 1);
                if (this.i > 6) {
                    this.i = 0;
                }
                this.O = this.i > 2 ? (byte)1 : 0;
                if (!this.g()) break;
                if (this.f > -1) {
                    this.b();
                    break;
                }
                if (((class_vh)this).cV == 7) {
                    this.b(((class_vh)this).cO, ((class_vh)this).cP);
                    break;
                }
                if (((class_vh)this).cV == 2) {
                    this.b();
                    break;
                }
                if (this.b == null) {
                    this.k = 0;
                    this.j = 0;
                    this.i = 0;
                    ((class_vh)this).cV = 0;
                    this.Z = class_yi.a(10, 20);
                    this.ac = 0;
                    this.ab = 0;
                    this.aa = this.D;
                    break;
                }
                if (this.b != null && Math.abs(((class_vh)this).cK - this.b.cK) <= this.ag && Math.abs(((class_vh)this).cL - this.b.cL) <= this.ag) {
                    this.ac = 0;
                    this.ab = 0;
                    break;
                }
                this.b(this.b.cK + this.o, this.b.cL + this.p);
            }
        }
        if (this.n && this.s - System.currentTimeMillis() / 1000L <= 0L && !((class_vh)this).cE) {
            class_acv.s.a(((class_vh)this).cK, ((class_vh)this).cL);
            ((class_vh)this).cE = true;
            ((class_vh)this).dc = false;
        }
        if (this.v <= 0 && class_acv.s.r != null && class_acv.s.r == this) {
            class_acv.s.r = null;
        }
    }

    public final void b(int n, int n2) {
        if (class_yi.T[this.l].b == 4) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            ((class_vh)this).cV = 0;
            return;
        }
        boolean bl = false;
        boolean bl2 = false;
        int n3 = Math.abs(((class_vh)this).cK - n);
        int n4 = Math.abs(((class_vh)this).cL - n2);
        if (n3 <= this.I) {
            ((class_vh)this).cK = (short)n;
            bl = true;
        }
        if (n4 < this.I) {
            ((class_vh)this).cL = (short)n2;
            bl2 = true;
        }
        if (bl && bl2) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            ((class_vh)this).cV = 0;
            this.Z = class_yi.a(10, 20);
            this.ac = 0;
            this.ab = 0;
            this.aa = this.D;
            return;
        }
        if (((class_vh)this).cK < n) {
            ((class_vh)this).cK = (short)(((class_vh)this).cK + this.I);
            this.D = (short)3;
            return;
        }
        if (((class_vh)this).cK > n) {
            ((class_vh)this).cK = (short)(((class_vh)this).cK - this.I);
            this.D = (short)2;
            return;
        }
        if (((class_vh)this).cL > n2) {
            ((class_vh)this).cL = (short)(((class_vh)this).cL - this.I);
            this.D = 1;
            return;
        }
        if (((class_vh)this).cL < n2) {
            this.D = 0;
            ((class_vh)this).cL = (short)(((class_vh)this).cL + this.I);
        }
    }

    public void b() {
        if (class_yi.T[this.l].b == 4) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            ((class_vh)this).cV = 0;
            return;
        }

        boolean bl = false;
        boolean bl2 = false;
        int n = Math.abs(((class_vh)this).cK - this.g);
        int n6 = Math.abs(((class_vh)this).cL - this.h);
        if (n <= this.I) {
            ((class_vh)this).cK = this.g;
            bl = true;
        }
        if (n6 < this.I) {
            ((class_vh)this).cL = this.h;
            bl2 = true;
        }
        if (bl && bl2) {
            this.k = 0;
            this.j = 0;
            this.i = 0;
            ((class_vh)this).cV = 0;
            return;
        }
        if (((class_vh)this).cK < this.g) {
            ((class_vh)this).cK = (short)(((class_vh)this).cK + this.I);
            this.D = (short)3;
            return;
        }
        if (((class_vh)this).cK > this.g) {
            ((class_vh)this).cK = (short)(((class_vh)this).cK - this.I);
            this.D = (short)2;
            return;
        }
        if (((class_vh)this).cL > this.h) {
            ((class_vh)this).cL = (short)(((class_vh)this).cL - this.I);
            this.D = 1;
            return;
        }
        if (((class_vh)this).cL < this.h) {
            this.D = 0;
            ((class_vh)this).cL = (short)(((class_vh)this).cL + this.I);
        }
    }

    public void a(short s, short s2) {
        System.out.println("[CLIENT DEBUG] Monster " + this.l + " move to (" + s + "," + s2 + ") cV=" + ((class_vh)this).cV + " f=" + this.f);
        if (((class_vh)this).cV != 3 && ((class_vh)this).cK == s && ((class_vh)this).cL == s2) {
            ((class_vh)this).cV = 0;
            return;
        }
        this.g = s;
        this.h = class_yi.T[this.l].b == 4 ? s2 : (short)(s2 - 4 + class_acv.t.nextInt() % 8);
        if (((class_vh)this).cV != 3) {
            ((class_vh)this).cV = (byte)2;
        }
        if (((class_vh)this).cV != 3 && ((class_vh)this).cK == this.g && ((class_vh)this).cL == this.h) {
            ((class_vh)this).cV = 0;
            return;
        }
    }

    public void a(class_by class_by2) {
        ((class_vh)this).dd = class_by2.g;
        int[] nArray = new int[]{16, 32, 48, -16, -32, -48};
        this.o = nArray[class_ap.V.nextInt(nArray.length - 1)];
        this.p = nArray[class_ap.V.nextInt(nArray.length - 1)];
        if (this.a) {
            ((class_vh)this).cK = class_by2.b;
            ((class_vh)this).cL = class_by2.c;
        } else {
            this.g = class_by2.b;
            this.h = class_by2.c;
        }
        this.ag = class_ap.V.nextInt(10) + 6;
        this.t = this.v = class_by2.e;
        this.P = class_by2.i;
        ((class_vh)this).cV = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = class_by2.h;
        if (class_yi.T != null && class_yi.T[this.l] != null) {
            this.I = class_yi.T[this.l].c;
            ((class_vh)this).cM = class_yi.T[this.l].d;
            if (class_yi.T[this.l].a == null) {
                class_yi.T[this.l].b();
            }
        }
        this.N = (byte)class_by2.d;
        this.w = class_by2.f;
        this.ae = true;
    }

    public final void u() {
        ((class_vh)this).cV = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
    }

    public final void a(class_ap class_ap2, int n, byte by, byte by2) {
        ((class_vh)this).cV = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.ah = n;
        this.e = class_ap2;
        this.d = class_qz.f((int)8, (int)4);
    }

    public void a(Vector vector, byte by) {
        ((class_vh)this).cV = (byte)3;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        if (this.l == 90) {
            this.d = class_qz.a();
            this.d.a(vector);
        }
    }

    public void f() {
        if (!this.a) {
            this.x = -3;
            this.y = -5;
        }
    }

    public final void d(int n) {
        this.v -= n;
    }

    public void b(int n) {
        this.v = this.t = n;
        this.u = 20;
    }

    public final boolean v() {
        return true;
    }

    public final boolean w() {
        return this.a;
    }
}
