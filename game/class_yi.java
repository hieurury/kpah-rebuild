/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  classes.class_aab
 *  classes.class_aai
 *  classes.class_aaq
 *  classes.class_ace
 *  classes.class_acf
 *  classes.class_acv
 *  classes.class_aw
 *  classes.class_br
 *  classes.class_d
 *  classes.class_de
 *  classes.class_dh
 *  classes.class_g
 *  classes.class_ga
 *  classes.class_gd
 *  classes.class_go
 *  classes.class_iz
 *  classes.class_jl
 *  classes.class_ko
 *  classes.class_ls
 *  classes.class_pw
 *  classes.class_ql
 *  classes.class_rx
 *  classes.class_vp
 *  classes.class_xv
 *  classes.class_yc
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package classes;

import classes.class_aab;
import classes.class_aai;
import classes.class_aaq;
import classes.class_abj;
import classes.class_ace;
import classes.class_acf;
import classes.class_acv;
import classes.class_aw;
import classes.class_br;
import classes.class_d;
import classes.class_de;
import classes.class_dh;
import classes.class_g;
import classes.class_ga;
import classes.class_gd;
import classes.class_go;
import classes.class_hw;
import classes.class_iz;
import classes.class_jl;
import classes.class_ko;
import classes.class_ls;
import classes.class_pw;
import classes.class_ql;
import classes.class_rx;
import classes.class_vp;
import classes.class_xv;
import classes.class_yc;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class class_yi {
    public static String a = "";
    public static String b = "";
    public static String c = "";
    public static Vector d = new Vector();
    public static Vector e = new Vector();
    public static Vector f = new Vector();
    public static class_aaq[] g;
    private static String[][] ag;
    private static Random ah;
    public static final String[] h;
    public static Image[] i;
    public static Image j;
    public static Image k;
    public static Image l;
    public static Image m;
    public static Image n;
    public static Image o;
    public static Image p;
    public static Image q;
    public static Image r;
    private static Image[] ai;
    public static Image s;
    public static Image t;
    public static Image u;
    public static Image v;
    public static Image w;
    public static Image[] x;
    private static Image[] aj;
    public static Image[] y;
    public static Image z;
    public static Image A;
    public static Image[] B;
    public static Image[] C;
    public static Image D;
    public static Image E;
    public static Image[] F;
    public static Image G;
    public static class_aab H;
    public static class_aab I;
    public static class_aab J;
    public static class_aab K;
    public static Image L;
    private static Image ak;
    public static Image M;
    private static Image al;
    public static Image N;
    public static Image O;
    public static int P;
    public static class_ga[][] Q;
    public static byte[] R;
    public static class_jl S;
    public static class_ace[] T;
    public static Vector U;
    private static class_br am;
    private static class_br an;
    private static class_br ao;
    private static Hashtable ap;
    public static class_aw[] V;
    public static int[] W;
    private static int aq;
    private static int ar;
    public static String X;
    private static Random as;
    public static short[][] Y;
    public static String[][] Z;
    public static String[][][] aa;
    public static short[][][] ab;
    public static String[] ac;
    public static byte[] ad;
    public static final String[] ae;
    public static final String[] af;

    static {
        ag = new String[][]{{"V\u00e0o trong \u0111i c\u1eadu. \u0110\u1ee7 lo\u1ea1i HP t\u1eeb l\u1edbn \u0111\u1ebfn b\u00e9!"}, {"H\u00f2 \u01a1\u2026 mua g\u00ec c\u0169ng c\u00f3, h\u1ecfi g\u00ec c\u0169ng bi\u1ebft", "G\u00e1nh h\u00e0ng nh\u1ecf nh\u01b0ng m\u00f3n g\u00ec c\u0169ng c\u00f3 \u0111\u00e2y", "Mua g\u00ec \u0111\u00e2y, n\u00f3i ta nghe"}, {"V\u0169 kh\u00ed \u1edf \u0111\u00e2y r\u1ea5t l\u1ee3i h\u1ea1i, v\u00e0o trong t\u00f4i cho c\u1eadu xem!", "Ng\u01b0\u01a1i mu\u1ed1n s\u1eeda ch\u1eefa \u0111\u1ed3?", "Ng\u01b0\u01a1i mu\u1ed1n luy\u1ec7n \u0111\u1ed3?"}, {"Mua gi\u00e1p h\u1ed9 th\u00e2n n\u00e0o \u2026", "Mua gi\u00e1p c\u1ee7a ta, ng\u01b0\u01a1i s\u1ebd lu\u00f4n \u0111\u01b0\u1ee3c b\u1ea3o v\u1ec7 an to\u00e0n", "\u0110\u1ebfn v\u1edbi ta, ta s\u1ebd n\u00f3i cho ng\u01b0\u01a1i bi\u1ebft t\u1ea1i sao ng\u01b0\u1eddi ta g\u1ecdi ta l\u00e0 Thi\u1ebft B\u00ec"}, {"Tr\u00e1ch nhi\u1ec7m c\u1ee7a ta l\u00e0 b\u1ea3o v\u1ec7 ng\u00f4i l\u00e0ng n\u00e0y."}, {"C\u1ed1 g\u1eafng l\u00ean con, h\u00e3y ho\u00e0n th\u00e0nh c\u00e1c nhi\u1ec7m v\u1ee5 \u0111i nh\u00e9", "Anh h\u00f9ng xu\u1ea5t thi\u1ebfu ni\u00ean..anh h\u00f9ng xu\u1ea5t thi\u1ebfu ni\u00ean", "Con l\u00e0 th\u00e0nh vi\u00ean c\u1ee7a l\u00e0ng Ngh\u0129a S\u0129 n\u00e0y ! H\u00e3y nh\u1edb l\u1ea5y \u0111i\u1ec1u \u0111\u00f3"}, {"Ch\u00e0o ch\u00e0ng trai tr\u1ebb", "Mu\u1ed1n mua ng\u1ecdc th\u00ec v\u00e0o nh\u00e0", "Ng\u01b0\u01a1i mu\u1ed1n mua v\u1eadt ph\u1ea9m \u0111\u1ec3 luy\u1ec7n \u0111\u1ed3?"}, {"\u0110\u01b0\u1eddng xa v\u1ea1n l\u00fd, kh\u00f4ng l\u00e0m n\u1ea3n l\u00f2ng xa phu ta \u0111\u00e2y", "\u0110i l\u00e2u th\u00e0nh l\u1ed1i, l\u1ed1i th\u00e0nh \u0111\u01b0\u1eddng \u0111i.", "N\u00f3i ta nghe ng\u01b0\u01a1i mu\u1ed1n \u0111i \u0111\u00e2u?"}, {""}, {"G\u1eedi \u0111\u1ed3 ch\u1ed7 ta l\u00e0 an to\u00e0n nh\u1ea5t \u0111\u00f3", "Ng\u01b0\u01a1i mu\u1ed1n nh\u1edd ta gi\u1eef \u0111\u1ed3?", "Ng\u01b0\u01a1i c\u00f3 nhi\u1ec1u \u0111\u1ed3 mu\u1ed1n g\u1eedi \u00e0h?"}, {"B\u1ea1n ph\u1ea3i c\u00f3 v\u00e9 m\u1edbi \u0111\u01b0\u1ee3c l\u00ean t\u00e0u."}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"Mu\u1ed1n nh\u1edd ta b\u00e1n g\u00ec n\u00e0o"}, {"T\u1eadp luy\u1ec7n n\u00e0o, t\u1eadp luy\u1ec7n n\u00e0o ..", "S\u1ee9c kh\u1ecfe tr\u01b0\u1edbc \u0111\u00e3, t\u1eadp luy\u1ec7n n\u00e0o \u2026", "Cho ta xem kh\u1ea3 n\u0103ng c\u1ee7a c\u00e1c ng\u01b0\u01a1i n\u00e0o !!"}, {"Giao ti\u1ec1n ta gi\u1eef n\u00e0o, y\u00ean t\u00e2m nh\u00e9", "Ng\u01b0\u01a1i mu\u1ed1n \u0111\u1ed5i ti\u1ec1n \u00e0, n\u00f3i ta nghe", "Ta l\u1ea5y ch\u1eef T\u00edn l\u00e0m \u0111\u1ea7u, y\u00ean t\u00e2m n\u00e0o"}, {"Ch\u00e0, l\u00e2u r\u1ed3i m\u1edbi c\u00f3 ng\u01b0\u1eddi \u0111i qua."}, {"Ng\u01b0\u01a1i mu\u1ed1n thu\u00ea g\u00ec?"}, {"H\u00e3y qu\u1ea3n l\u00fd bang c\u1ee7a ng\u01b0\u01a1i th\u1eadt t\u1ed1t v\u00e0o."}, {"H\u00f2 \u01a1\u2026 mua g\u00ec c\u0169ng c\u00f3, h\u1ecfi g\u00ec c\u0169ng bi\u1ebft", "G\u00e1nh h\u00e0ng nh\u1ecf nh\u01b0ng m\u00f3n g\u00ec c\u0169ng c\u00f3 \u0111\u00e2y", "Mua g\u00ec \u0111\u00e2y, n\u00f3i ta nghe"}, {"Mua gi\u00e1p h\u1ed9 th\u00e2n n\u00e0o \u2026", "Mua gi\u00e1p c\u1ee7a ta, ng\u01b0\u01a1i s\u1ebd lu\u00f4n \u0111\u01b0\u1ee3c b\u1ea3o v\u1ec7 an to\u00e0n", "\u0110\u1ebfn v\u1edbi ta, ta s\u1ebd n\u00f3i cho ng\u01b0\u01a1i bi\u1ebft t\u1ea1i sao ng\u01b0\u1eddi ta g\u1ecdi ta l\u00e0 Gi\u00e1p S\u01b0"}, {"V\u0169 kh\u00ed \u1edf \u0111\u00e2y r\u1ea5t l\u1ee3i h\u1ea1i, v\u00e0o trong t\u00f4i cho c\u1eadu xem!", "Ng\u01b0\u01a1i mu\u1ed1n s\u1eeda ch\u1eefa \u0111\u1ed3?", "Ng\u01b0\u01a1i mu\u1ed1n luy\u1ec7n \u0111\u1ed3?"}, {"Ch\u00e0o ch\u00e0ng trai tr\u1ebb", "Mu\u1ed1n mua ng\u1ecdc th\u00ec v\u00e0o nh\u00e0", "Ng\u01b0\u01a1i mu\u1ed1n mua v\u1eadt ph\u1ea9m \u0111\u1ec3 luy\u1ec7n \u0111\u1ed3?"}, {"G\u1eedi \u0111\u1ed3 ch\u1ed7 ta l\u00e0 an to\u00e0n nh\u1ea5t \u0111\u00f3", "Ng\u01b0\u01a1i mu\u1ed1n nh\u1edd ta gi\u1eef \u0111\u1ed3?", "Ng\u01b0\u01a1i c\u00f3 nhi\u1ec1u \u0111\u1ed3 mu\u1ed1n g\u1eedi \u00e0h?"}, {"Giao ti\u1ec1n ta gi\u1eef n\u00e0o, y\u00ean t\u00e2m nh\u00e9", "Ng\u01b0\u01a1i mu\u1ed1n \u0111\u1ed5i ti\u1ec1n \u00e0, n\u00f3i ta nghe", "Ta l\u1ea5y ch\u1eef T\u00edn l\u00e0m \u0111\u1ea7u, y\u00ean t\u00e2m n\u00e0o"}};
        String[] stringArray = new String[]{"T\u0103ng Hp ", "T\u0103ng Mp ", "S\u1ee9c m\u1ea1nh +", "Nhanh nh\u1eb9n +", "Tinh th\u1ea7n +", "S\u1ee9c kho\u1ebb +", "", "Ch\u00ed m\u1ea1ng t\u0103ng ", "T\u0103ng st ch\u00ed m\u1ea1ng "};
        String[] stringArray2 = new String[]{"T\u0103ng c\u00f4ng ", "T\u0103ng th\u1ee7 ma ", "T\u0103ng th\u1ee7 v\u1eadt "};
        ah = new Random(System.currentTimeMillis());
        h = new String[]{"b\u1ea5t k\u1ef3", "nam", "n\u1eef"};
        ai = new Image[66];
        x = new Image[3];
        aj = new Image[18];
        F = new Image[2];
        class_acf.b((String)"/main.sh");
        K = new class_aab(class_acf.a((String)"no"), 10, 10);
        M = class_acf.a((String)"kham");
        G = class_acf.a((String)"msg0");
        z = class_acf.a((String)"wicon");
        D = class_acf.a((String)"bar");
        al = class_acf.a((String)"kc");
        class_pw.c = class_aab.a((String)"arF", (int)11, (int)9);
        class_yi.a();
        C = new Image[3];
        int n = 0;
        while (n < 3) {
            class_yi.C[n] = class_acf.a((String)("inv" + n));
            ++n;
        }
        if (class_acv.a.hasPointerEvents()) {
            o = class_acf.a((String)"panel150x36");
            P = 30;
        } else {
            o = class_acf.a((String)"panel");
            P = 20;
        }
        N = class_acf.a((String)"sk31");
        u = class_acf.a((String)"die");
        v = class_acf.a((String)"eye");
        class_acf.a((String)"plus");
        j = class_acf.a((String)"shadow");
        t = class_acf.a((String)"grid");
        y = new Image[2];
        n = 0;
        while (n < y.length) {
            class_yi.y[n] = class_acf.a((String)("inv_" + n));
            ++n;
        }
        class_rx.h = class_acf.a((String)"c");
        class_rx.i = class_acf.a((String)"ar");
        class_gd.a();
        H = new class_aab(class_acf.a((String)"smoke"), 14, 15);
        J = new class_aab(class_acf.a((String)"check"), 10, 10);
        class_d.a();
        class_acf.a();
        class_acf.b((String)"/eff.sh");
        s = class_acf.a((String)"explosion");
        class_acf.a();
        class_acf.b((String)"/g.sh");
        class_yi.x[0] = class_acf.a((String)"g0");
        class_yi.x[1] = class_acf.a((String)"g1");
        class_yi.x[2] = class_acf.a((String)"g2");
        class_acf.a();
        class_acf.b((String)"/nation");
        O = class_acf.a((String)"icon");
        class_acf.a();
        class_acf.b((String)"/box.sh");
        class_yi.F[0] = class_acf.a((String)"b0");
        class_yi.F[1] = class_acf.a((String)"b1");
        class_acf.a();
        try {
            if (r == null) {
                r = Image.createImage((String)"/m/coat.png");
            }
            if (p == null) {
                p = Image.createImage((String)"/m/imgshadow.png");
            }
            q = Image.createImage((String)"/m/notice.png");
        }
        catch (Exception exception) {
            String cfr_ignored_0 = "loi load hinh res  ++  " + exception.toString();
        }
        try {
            w = Image.createImage((String)"/sword skill/h0.png");
            class_abj.R = Image.createImage((String)"/plus12.png");
            class_abj.S = Image.createImage((String)"/shadow.png");
            Image[] imageArray = new Image[2];
            class_abj.ag = imageArray;
            imageArray[0] = Image.createImage((String)"/m1.png");
            class_abj.ag[1] = Image.createImage((String)"/m2.png");
        }
        catch (Exception exception) {}
        Q = new class_ga[5][3];
        R = new byte[]{20, 50, 10, 70, 20};
        S = new class_jl();
        new Hashtable();
        T = new class_ace[115];
        U = new Vector();
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        U.addElement(new Hashtable());
        ap = new Hashtable();
        V = new class_aw[class_hw.Y];
        W = new int[]{8346120, 15852810, 14527502, 14595691, 11241794, 4858880, 2181450};
        aq = 6;
        X = "xu";
        as = new Random();
        ae = new String[]{"L\u00e0ng S\u01a1n Nam", "Dao Ch\u00e2u", "Ti\u00ean Du", "Ph\u00f9 Li\u1ec7t", "K\u1ef3 B\u1ed1", "H\u00e0m T\u1eed", "Th\u1ea1ch Giang", "\u0110\u00f4ng S\u01a1n", "T\u1eed Quan", "Tr\u01b0\u1eddng Giang", "L\u1ed9c Tr\u0129", "S\u01a1n L\u00e2m", "Hang \u0111\u1ed9ng", "Hang m\u00e3ng x\u00e0", "Hang th\u1eb1n l\u1eb1n", "\u0110\u1ea5u tr\u01b0\u1eddng", "Khu v\u1ef1c 1"};
        af = new String[]{"Ki\u1ebfm kh\u00e1ch", "Chi\u1ebfn binh", "Ph\u00e1p s\u01b0", "\u0110\u1ea5u s\u0129", "Cung th\u1ee7", ""};
    }

    public static String a(int n) {
        String string = "";
        try {
            string = ag[n][class_abj.c(ah.nextInt(ag[n].length))];
        }
        catch (Exception exception) {}
        return string;
    }

    public static void a() {
        int n = class_acv.m / 2 + 1;
        E = Image.createImage((int)class_acv.m, (int)al.getHeight());
        Graphics graphics = E.getGraphics();
        int n2 = 0;
        while (n2 < n) {
            graphics.drawImage(al, n2 << 1, 0, 0);
            ++n2;
        }
    }

    public static void a(byte[] byArray) {
        class_acf.a((byte[])byArray);
        s = class_acf.a((String)"explosion");
        class_acf.a();
    }

    public static void b() {
        if (l == null) {
            class_acf.b((String)"/main.sh");
            l = class_acf.a((String)"select");
            m = class_acf.a((String)"select1");
            n = class_acf.a((String)"select3");
            class_abj.Q = new Image[2];
            int n = 0;
            while (n < 2) {
                class_abj.Q[n] = class_acf.a((String)("cong" + (n + 1)));
                ++n;
            }
            if (class_abj.O == null) {
                class_abj.O = class_acf.a((String)"info");
            }
            if (class_abj.P == null) {
                class_abj.P = class_acf.a((String)"mauquai");
            }
            class_de.b = class_acf.a((String)"fire");
            class_hw.Z = class_acf.a((String)"flg");
            class_hw.aa = class_acf.a((String)"light");
            i = new Image[2];
            n = 0;
            while (n < 2) {
                class_yi.i[n] = class_acf.a((String)("soft" + n));
                ++n;
            }
            A = class_acf.a((String)"nguhanh");
            if (B == null) {
                B = new Image[2];
            }
            n = 0;
            while (n < 2) {
                class_yi.B[n] = class_acf.a((String)("ch" + n));
                ++n;
            }
            class_acf.a();
        }
    }

    public static class_xv a(short s) {
        int n = 0;
        while (n < e.size()) {
            class_xv class_xv2 = (class_xv)e.elementAt(n);
            if (class_xv2.o == s) {
                return class_xv2;
            }
            ++n;
        }
        return null;
    }

    public static int a(int n, int n2) {
        return n + as.nextInt(n2 - n);
    }

    public static boolean a(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        return n <= n4 && n2 >= n3 && n5 <= n8 && n6 >= n7;
    }

    public static int[] a(int n, int n2, int n3) {
        int[] nArray = new int[10];
        class_yc class_yc2 = ((class_yc[])d.elementAt(0))[n2];
        nArray[0] = class_yc2.k[0];
        int n4 = 1;
        while (n4 < 4) {
            nArray[n4] = (short)(class_yc2.k[n4] + (class_ql.b((int)n3) ? 0 : class_yc2.k[n4] * R[n] / 100));
            ++n4;
        }
        n4 = 4;
        while (n4 < 10) {
            nArray[n4] = class_yc2.k[n4];
            ++n4;
        }
        return nArray;
    }

    public static class_yc b(int n) {
        class_yc[] class_ycArray = (class_yc[])d.elementAt(0);
        int n2 = 0;
        while (n2 < class_ycArray.length) {
            if (class_ycArray[n2] != null && class_ycArray[n2].m == n) {
                return class_ycArray[n2];
            }
            ++n2;
        }
        return null;
    }

    public static class_xv b(short s) {
        int n = 0;
        while (n < f.size()) {
            class_xv class_xv2 = (class_xv)f.elementAt(n);
            if (class_xv2.o == s) {
                return class_xv2;
            }
            ++n;
        }
        return null;
    }

    public static class_aaq c() {
        if (class_ls.k >= 0) {
            return g[class_ls.k];
        }
        return null;
    }

    public static Image a(byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = new byte[byArray.length + byArray2.length];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        return Image.createImage((byte[])byArray3, (int)0, (int)byArray3.length);
    }

    public static void d() {
        int n;
        int n2 = 0;
        while (n2 < class_hw.co.length) {
            n = 0;
            while (n < class_hw.co[n2].length) {
                int n3 = 0;
                while (n3 < class_hw.co[n2][n].length) {
                    class_hw.co[n2][n][n3] = null;
                    ++n3;
                }
                ++n;
            }
            ++n2;
        }
        n2 = 0;
        while (n2 < 5) {
            n = 0;
            while (n < 3) {
                class_yi.Q[n2][n] = null;
                ++n;
            }
            ++n2;
        }
    }

    public static class_iz b(int n, int n2, int n3) {
        if (class_hw.co[n][n2][n3] == null) {
            int n4 = n3;
            int n5 = n2;
            int n6 = n;
            class_jl class_jl2 = S;
            if (class_jl2.a == -1 && class_jl2.b == -1) {
                class_jl2.a = n6;
                class_jl2.b = n5;
                class_jl2.c = n4;
            }
        }
        return class_hw.co[n][n2][n3];
    }

    public static Image c(int n) {
        class_dh class_dh2 = null;
        class_dh2 = null;
        if (n < aj.length - 1) {
            if (aj[n] == null && aj[n] == null && class_yi.S.e == -1) {
                class_yi.S.e = n;
            }
            if ((class_dh2 = aj[n]) != null) {
                return class_dh2;
            }
        }
        if ((class_dh2 = class_ko.a((short)((short)(n + 10000)))) != null && class_dh2.a != null) {
            return class_dh2.a;
        }
        return null;
    }

    public static Image d(int n) {
        class_dh class_dh2 = null;
        class_dh2 = null;
        if (n <= ai.length - 1) {
            if (ai[n] == null) {
                int n2 = n;
                class_dh2 = S;
                if (class_dh2.d == -1) {
                    class_dh2.d = n2;
                }
            }
            if ((class_dh2 = ai[n]) != null) {
                return class_dh2;
            }
        }
        if ((class_dh2 = class_ko.a((short)((short)(n + 9000)))) != null && class_dh2.a != null) {
            return class_dh2.a;
        }
        return null;
    }

    public static void e(int n) {
        class_acf.b((String)"/arrow.sh");
        try {
            if (n == 7) {
                class_yi.aj[n] = Image.createImage((String)"/sword skill/kiem.png");
            } else if (n == 8) {
                class_yi.aj[n] = Image.createImage((String)"/sword skill/skillboss.png");
            } else if (n == 9) {
                class_yi.aj[n] = Image.createImage((String)"/sword skill/nut.png");
            } else if (n == 10) {
                class_yi.aj[10] = Image.createImage((String)"/newEf/46.png");
            } else if (n == 11) {
                class_yi.aj[11] = Image.createImage((String)"/newEf/47.png");
            } else if (n == 12) {
                class_yi.aj[12] = Image.createImage((String)"/newEf/48.png");
            } else if (n == 13) {
                class_yi.aj[n] = Image.createImage((String)"/newEf/54.png");
            } else if (n == 14) {
                class_yi.aj[n] = Image.createImage((String)"/newEf/56.png");
            }
        }
        catch (Exception exception) {}
        class_acf.a();
    }

    public static void a(Graphics graphics, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        try {
            Image image = class_yi.d(n);
            if (image != null) {
                graphics.drawRegion(image, 0, n3, n4, n5, 0, n6, n7, n8);
                return;
            }
        }
        catch (Exception exception) {}
    }

    public static Image f(int n) {
        class_dh class_dh2 = class_ko.a((short)((short)(n + 9000)));
        if (class_dh2 != null && class_dh2.a != null) {
            if (n == 32) {
                I = new class_aab(class_dh2.a, 12, 13);
            }
            return class_dh2.a;
        }
        return null;
    }

    public static void g(int n) {
        class_acf.b((String)"/eff.sh");
        if (n < 25 || n > 33) {
            try {
                class_yi.ai[n] = class_acf.a((String)("g" + n));
            }
            catch (Exception exception) {}
        }
        if (ai[26] == null) {
            class_yi.ai[25] = class_acf.a((String)"g25");
            class_yi.ai[26] = class_acf.a((String)"g26");
            class_yi.ai[27] = Image.createImage((Image)ai[25]);
            class_yi.ai[28] = class_acf.a((String)"g27");
            class_yi.ai[29] = class_acf.a((String)"g28");
            class_yi.ai[30] = class_acf.a((String)"g29");
            class_yi.ai[31] = class_acf.a((String)"g30");
            class_yi.ai[32] = class_acf.a((String)"g31");
            class_yi.ai[33] = class_acf.a((String)"g32");
            class_yi.ai[41] = Image.createImage((String)"/newEf/42.png");
            class_yi.ai[42] = Image.createImage((String)"/newEf/43.png");
            class_yi.ai[43] = Image.createImage((String)"/newEf/44.png");
            class_yi.ai[44] = Image.createImage((String)"/newEf/45.png");
            class_yi.ai[45] = Image.createImage((String)"/newEf/49.png");
            class_yi.ai[46] = Image.createImage((String)"/newEf/50.png");
            class_yi.ai[47] = Image.createImage((String)"/newEf/51.png");
            class_yi.ai[48] = Image.createImage((String)"/newEf/52.png");
            class_yi.ai[49] = Image.createImage((String)"/newEf/53.png");
            class_yi.ai[50] = Image.createImage((String)"/newEf/55.png");
            class_yi.ai[51] = Image.createImage((String)"/newEf/57.png");
            class_yi.ai[52] = Image.createImage((String)"/newEf/58.png");
            class_yi.ai[53] = Image.createImage((String)"/newEf/59.png");
            class_yi.ai[54] = Image.createImage((String)"/newEf/60.png");
            class_yi.ai[55] = Image.createImage((String)"/newEf/61.png");
            class_yi.ai[63] = Image.createImage((String)"/newEf/63.png");
            class_yi.ai[64] = Image.createImage((String)"/newEf/64.png");
            class_yi.ai[65] = Image.createImage((String)"/newEf/65.png");
            I = new class_aab(ai[32], 12, 13);
        }
        class_acf.a();
    }

    public static void c(int n, int n2, int n3) {
        Object object = new String[]{"kiem", "daidao", "phapsu", "bua", "cung"};
        class_acf.b((String)("/wpsplash/" + object[n] + "/" + n2));
        class_hw.co[n][n2][n3] = new class_iz();
        try {
            object = class_acf.a.c(String.valueOf(n3) + "_h");
            byte[] byArray = class_acf.a.c("data");
            class_hw.co[n][n2][n3].a = class_yi.a((byte[])object, byArray);
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        class_acf.a();
    }

    public static class_ga b(int n, int n2) {
        try {
            class_yi.Q[n][n2] = new class_ga();
            int n3 = n;
            int n4 = n2;
            if (n3 == 3 || n3 == 4 || n3 == 2 && n4 == 1) {
                n4 = 0;
            } else if ((n3 == 0 || n3 == 1) && n4 == 2) {
                n4 = 1;
            }
            Object object = new class_acf("/wpsplash/" + n3 + ".wp", null);
            class_yi.Q[n][n2].g = object.d(n4 + ".png");
            object = object.e(String.valueOf(n4) + ".d");
            if (object != null) {
                n4 = 0;
                while (n4 < 4) {
                    int n5 = 0;
                    while (n5 < 8) {
                        class_yi.Q[n][n2].a[n4][n5] = ((InputStream)object).read();
                        class_yi.Q[n][n2].b[n4][n5] = ((InputStream)object).read();
                        class_yi.Q[n][n2].c[n4][n5] = class_yi.a((InputStream)object);
                        class_yi.Q[n][n2].d[n4][n5] = class_yi.a((InputStream)object);
                        class_yi.Q[n][n2].e[n4][n5] = ((InputStream)object).read();
                        class_yi.Q[n][n2].f[n4][n5] = ((InputStream)object).read();
                        ++n5;
                    }
                    ++n4;
                }
            }
            class_acf.a();
        }
        catch (IOException iOException) {
            class_yi.Q[n][n2] = null;
        }
        return Q[n][n2];
    }

    public static void e() {
        ak = null;
    }

    public static void a(int n, byte[] byArray) {
        class_ace class_ace2 = T[n];
        String cfr_ignored_0 = "m" + n;
        class_ace2.a(n, "", byArray[0], byArray[1], byArray[2], byArray[3], byArray[4], byArray[5], byArray[6], byArray[7], byArray[8]);
    }

    public static void f() {
        if (T != null) {
            int n = 0;
            while (n < T.length) {
                if (T[n] != null) {
                    class_yi.T[n].a = null;
                    class_yi.T[n].p = null;
                }
                ++n;
            }
        }
    }

    public static void g() {
        class_yi.f();
        ap.clear();
    }

    public static class_br c(int n, int n2) {
        class_br class_br2 = null;
        Hashtable hashtable = (Hashtable)U.elementAt(n);
        class_br2 = (class_br)hashtable.get(String.valueOf(n2));
        if (class_br2 == null) {
            class_br2 = new class_br(n, n2);
            new class_br(n, n2).c = (int)(System.currentTimeMillis() / 1000L);
            if (class_br2.b == null) {
                class_go.a().d(-1, n, n2);
            }
            hashtable.put(String.valueOf(n2), class_br2);
        }
        if (class_br2.b == null && System.currentTimeMillis() / 1000L - (long)class_br2.c == 15L) {
            class_go.a().d(-1, n, n2);
            class_br2.c = (int)(System.currentTimeMillis() / 1000L);
        }
        class_br2.a = (int)(System.currentTimeMillis() / 1000L);
        return class_br2;
    }

    public static void a(Graphics graphics, int n, int n2, int n3, int n4) {
        if (n4 == 1 && am != null) {
            am.a(graphics, n, n4, n2, n3);
        }
        if (n4 == 0 && an != null) {
            an.a(graphics, n, n4, n2, n3);
        }
        if (n4 == 2 && ao != null) {
            ao.a(graphics, n, n4, n2, n3);
        }
    }

    public static void h() {
        if (am == null) {
            am = class_yi.c(1, 54);
        }
        if (an == null) {
            an = class_yi.c(0, 54);
        }
        if (ao == null) {
            ao = class_yi.c(2, 27);
        }
    }

    public static class_vp h(int n) {
        class_vp class_vp2 = (class_vp)ap.get("" + n);
        if (class_vp2 == null) {
            class_vp2 = new class_vp();
            new class_vp().f = (short)n;
            ap.put("" + n, class_vp2);
        }
        return class_vp2;
    }

    public static int i(int n) {
        class_vp class_vp2 = class_yi.h(n);
        if (class_vp2 != null) {
            if (class_vp2.e != null) {
                return class_vp2.e.getHeight();
            }
            return 0;
        }
        return 0;
    }

    public static int j(int n) {
        class_vp class_vp2 = class_yi.h(n);
        if (class_vp2 != null) {
            if (class_vp2.e != null) {
                return class_vp2.e.getWidth();
            }
            return 0;
        }
        return 0;
    }

    public static int k(int n) {
        class_vp class_vp2 = class_yi.h(n);
        if (class_vp2 != null) {
            return class_vp2.b;
        }
        return 0;
    }

    public static int a(InputStream object) {
        byte[] byArray = new byte[1];
        try {
            ((InputStream)object).read(byArray, 0, 1);
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
        }
        return byArray[0];
    }

    public static final void l(int n) {
        if (k == null) {
            try {
                InputStream inputStream = null;
                if (n > 200) {
                    try {
                        k = Image.createImage((String)"/t_thanh.png");
                    }
                    catch (Exception exception) {
                        byte[] byArray = class_aai.a((String)class_acv.M[1]);
                        k = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                    }
                    inputStream = "".getClass().getResourceAsStream("/t_thanh.type");
                    L = Image.createImage((String)"/t_thanh_s.png");
                } else if (n < 110) {
                    k = Image.createImage((String)"/t.png");
                    inputStream = "".getClass().getResourceAsStream("/t.type");
                    L = Image.createImage((String)"/t_small.png");
                } else {
                    try {
                        k = Image.createImage((String)"/t_hang.png");
                    }
                    catch (Exception exception) {
                        byte[] byArray = class_aai.a((String)class_acv.M[0]);
                        k = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                    }
                    inputStream = "".getClass().getResourceAsStream("/t_hang.type");
                    L = Image.createImage((String)"/t_hang_s.png");
                }
                try {
                    class_ls.h = null;
                    class_ls.h = new int[inputStream.available()];
                    int n2 = 0;
                    while (n2 < class_ls.h.length) {
                        class_ls.h[n2] = inputStream.read();
                        ++n2;
                    }
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
            catch (Exception exception) {}
        }
    }

    public static void a(Graphics graphics, int n, int n2) {
        int n3 = n2 + 14;
        graphics.drawImage(C[2], (n -= 10) + 15, n3, 20);
        graphics.drawImage(C[2], n + 85, n3, 20);
        graphics.drawImage(C[2], n + 15, n3 + 46, 20);
        graphics.drawImage(C[2], n + 85, n3 + 46, 20);
        if (class_acv.q == class_g.a()) {
            graphics.drawImage(C[2], n + 15, n3 + 92 - 10, 20);
            graphics.drawImage(C[2], n + 85, n3 + 92 - 10, 20);
        } else {
            graphics.drawImage(C[2], n + 15, n3 + 92, 20);
            graphics.drawImage(C[2], n + 85, n3 + 92, 20);
            graphics.drawImage(C[2], n + 15, n3 + 92 + 5, 20);
            graphics.drawImage(C[2], n + 85, n3 + 92 + 5, 20);
        }
        n3 = n + 12;
        int n4 = n2 + 11;
        int n5 = 144;
        if (class_acv.q == class_g.a()) {
            n5 = 129;
        }
        int n6 = 0;
        while (n6 < 3) {
            graphics.setColor(W[n6]);
            graphics.drawRect(n3 + n6, n4 + n6, 144 - (n6 << 1), n5 - (n6 << 1) + 5);
            ++n6;
        }
        graphics.drawImage(C[0], n + 10, n2 + 9, 20);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n + 159, n2 + 9, 24);
        if (class_acv.q == class_g.a()) {
            graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n + 10, n2 + 148, 36);
            graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n + 159, n2 + 148, 40);
            return;
        }
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n + 10, n2 + 163, 36);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n + 159, n2 + 163, 40);
    }

    public static void b(Graphics graphics, int n, int n2, int n3, int n4) {
        graphics.setColor(34949);
        graphics.fillRect(n, n2, n3, n4);
        graphics.setColor(0xB5B6B6);
        graphics.drawRect(n, n2, n3, n4);
    }

    public static void c(Graphics graphics, int n, int n2, int n3, int n4) {
        int n5 = n3 / 70 + 1;
        int n6 = n4 / 47 + 1;
        graphics.setClip(n, n2, n3, n4);
        int n7 = 0;
        while (n7 < n5) {
            int n8 = 0;
            while (n8 < n6) {
                graphics.drawImage(C[2], n + n7 * 70, n2 + n8 * 47, 0);
                ++n8;
            }
            ++n7;
        }
        graphics.setClip(0, 0, class_acv.m, class_acv.n);
        n7 = 0;
        while (n7 < 3) {
            graphics.setColor(W[n7]);
            graphics.drawRect(n + n7, n2 + n7, n3 - (n7 << 1), n4 - (n7 << 1));
            ++n7;
        }
        graphics.drawImage(C[0], n - 2, n2 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n + n3 - 15, n2 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n - 2, n2 + n4 - 15, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n + n3 - 15, n2 + n4 - 16, 0);
        graphics.drawImage(C[1], n + n3 / 2, n2 - 4, 3);
    }

    public static void a(Graphics graphics, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.setColor(n6);
        graphics.drawRect(n, n2, n3, n4);
        graphics.setColor(n7);
        if (n5 < n3) {
            n6 = aq;
            if (n6 + n5 >= n3) {
                n6 = n3 - n5;
            }
            graphics.fillRect(n + n5, n2, n6, 1);
        } else if (n5 < n3 + n4) {
            n6 = aq;
            if (n6 + (n5 - n3) >= n4) {
                n6 = n4 - (n5 - n3);
            }
            graphics.fillRect(n + n3, n2 + (n5 - n3), 1, n6);
        } else if (n5 < (n3 << 1) + n4) {
            n6 = aq;
            if (n6 + (n5 - n3 - n4) >= n3) {
                n6 = n3 - (n5 - n3 - n4);
            }
            graphics.fillRect(n + (n3 - (n5 - n3 - n4)) - n6, n2 + n4, n6, 1);
        } else if (n5 < n3 * n4 << 1) {
            n6 = aq;
            if (n6 + (n5 - (n3 << 1) - n4) >= n4) {
                n6 = n4 - (n5 - (n3 << 1) - n4);
            }
            graphics.fillRect(n, n2 + (n4 - (n5 - (n3 << 1) - n4)) - n6, 1, n6);
        }
        if (++ar >= 4) {
            ar = 0;
        }
    }

    public static void d(Graphics graphics, int n, int n2, int n3, int n4) {
        int n5 = n3 / 70 + 1;
        graphics.setClip(n, n2, n3, n4);
        int n6 = 0;
        while (n6 < n5) {
            graphics.drawImage(C[2], n + n6 * 70, n2, 0);
            ++n6;
        }
        graphics.setColor(277044);
        graphics.fillRect(n, n2 + 25, n3, n4);
        graphics.setClip(0, 0, class_acv.m, class_acv.n);
        n6 = 0;
        while (n6 < 3) {
            graphics.setColor(W[n6]);
            graphics.drawRect(n + n6, n2 + n6, n3 - (n6 << 1) - 1, n4 - (n6 << 1) - 1);
            graphics.fillRect(n + 3, n2 + 25, n3 - 6, 1);
            ++n6;
        }
        graphics.drawImage(C[0], n - 2, n2 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n + n3 + 2, n2 - 2, 24);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n - 2, n2 + n4 + 2, 36);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n + n3 + 2, n2 + n4 + 2, 40);
    }

    public static void a(Graphics graphics, int n, int n2, int n3, int n4, int n5, String string, boolean bl, int n6) {
        int n7 = n3 / 70 + 1;
        graphics.setClip(n, n2, n3, n4);
        int n8 = 0;
        while (n8 < n7) {
            graphics.drawImage(C[2], n + n8 * 70, n2, 0);
            ++n8;
        }
        graphics.setColor(277044);
        graphics.fillRect(n, n2 + 25, n3, n4);
        graphics.setClip(0, 0, class_acv.m, class_acv.n);
        n8 = 0;
        while (n8 < 3) {
            graphics.setColor(W[n8]);
            graphics.drawRect(n + n8, n2 + n8, n3 - (n8 << 1) - 1, n4 - (n8 << 1) - 1);
            ++n8;
        }
        graphics.fillRect(n + 3, n2 + 25, n3 - 6, 1);
        if (!bl) {
            graphics.fillRect(n + 3, n2 + 25 + n5 + n6, n3 - 6, 1);
        } else {
            graphics.fillRect(n + 3, n2 + 25 + n5 + n6, n3 - 6, 1);
            graphics.fillRect(n + 3, n2 + n5 + n6 - 2, n3 - 6, 1);
        }
        class_d.j[0].a(graphics, string, n + n3 / 2, n2 + 8, 2);
        graphics.drawImage(C[0], n - 2, n2 - 2, 0);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 2, n + n3 + 2, n2 - 2, 24);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 6, n - 2, n2 + n4 + 2, 36);
        graphics.drawRegion(C[0], 0, 0, 18, 19, 3, n + n3 + 2, n2 + n4 + 2, 40);
    }

    public static void e(Graphics graphics, int n, int n2, int n3, int n4) {
        class_ko.a((Graphics)graphics, (short)((short)(n + 5500)), (int)n2, (int)n3, (int)n4);
    }

    public static void a(Graphics graphics, int n, int n2, int n3) {
        class_ko.a((Graphics)graphics, (short)((short)(n + 6500)), (int)n2, (int)n3, (int)3);
    }

    public static void b(Graphics graphics, int n, int n2, int n3) {
        class_ko.a((Graphics)graphics, (short)((short)(n + 6500)), (int)n2, (int)n3, (int)3);
    }

    public static Image b(byte[] byArray) {
        return Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
    }

    public static int m(int n) {
        return as.nextInt(n);
    }

    public static int d(int n, int n2) {
        int n3 = as.nextInt(2);
        if (n3 == 0) {
            return n;
        }
        return n2;
    }

    public static String[] e(int n, int n2) {
        try {
            return aa[n][n2];
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static short[] f(int n, int n2) {
        try {
            return ab[n][n2];
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Image i() {
        if (ak == null) {
            class_acf.b((String)"/main.sh");
            ak = class_acf.a((String)"waypoint");
            class_acf.a();
        }
        return ak;
    }

    public static Image a(Image image) {
        int n = image.getWidth();
        int n2 = image.getHeight();
        int[] nArray = new int[n * n2];
        image.getRGB(nArray, 0, n, 0, 0, n, n2);
        int n3 = 0;
        while (n3 < nArray.length) {
            if (nArray[n3] == -65315) {
                nArray[n3] = 0xFFFFFF;
            }
            ++n3;
        }
        return Image.createRGBImage((int[])nArray, (int)n, (int)n2, (boolean)true);
    }

    public static int n(int n) {
        return as.nextInt(n);
    }

    public static int a(int n, int n2, int n3, int n4) {
        n -= n3;
        if ((n = n * n + (n2 -= n4) * n2) <= 0) {
            return 0;
        }
        n2 = (n + 1) / 2;
        while (Math.abs((n3 = n2) - (n2 = n2 / 2 + n / (n2 * 2))) > 1) {
        }
        return n2;
    }

    public static int o(int n) {
        n = 0;
        while (n == 0) {
            n = as.nextInt() % 3;
        }
        return n;
    }

    public static int b(int n, int n2, int n3, int n4) {
        return Math.abs(n - n2) + Math.abs(n3 - n4);
    }
}
