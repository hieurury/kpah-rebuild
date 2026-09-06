/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  classes.IAction
 *  classes.MoveObj
 *  classes.class_a
 *  classes.class_aaa
 *  classes.class_aai
 *  classes.class_aaq
 *  classes.class_abk
 *  classes.class_abm
 *  classes.class_abs
 *  classes.class_abt
 *  classes.class_abz
 *  classes.class_acc
 *  classes.class_ace
 *  classes.class_acq
 *  classes.class_act
 *  classes.class_acv
 *  classes.class_af
 *  classes.class_am
 *  classes.class_aq
 *  classes.class_aw
 *  classes.class_az
 *  classes.class_bk
 *  classes.class_bq
 *  classes.class_bs
 *  classes.class_bt
 *  classes.class_by
 *  classes.class_d
 *  classes.class_dh
 *  classes.class_dm
 *  classes.class_du
 *  classes.class_ei
 *  classes.class_ej
 *  classes.class_el
 *  classes.class_en
 *  classes.class_eq
 *  classes.class_et
 *  classes.class_ex
 *  classes.class_f
 *  classes.class_fb
 *  classes.class_fd
 *  classes.class_g
 *  classes.class_gn
 *  classes.class_go
 *  classes.class_gw
 *  classes.class_hm
 *  classes.class_hn
 *  classes.class_hw
 *  classes.class_hz
 *  classes.class_it
 *  classes.class_kk
 *  classes.class_ko
 *  classes.class_kq
 *  classes.class_kr
 *  classes.class_ls
 *  classes.class_na
 *  classes.class_nu
 *  classes.class_ql
 *  classes.class_qz
 *  classes.class_s
 *  classes.class_sc
 *  classes.class_ub
 *  classes.class_vh
 *  classes.class_vo
 *  classes.class_vp
 *  classes.class_wc
 *  classes.class_xe
 *  classes.class_xv
 *  classes.class_xw
 *  classes.class_yc
 *  classes.class_yg
 *  classes.class_yi
 *  classes.class_zs
 *  classes.class_zt
 *  classes.class_zu
 *  classes.class_zy
 *  game.GameMidlet
 *  javax.microedition.lcdui.Image
 */
package classes;

import classes.IAction;
import classes.MoveObj;
import classes.class_a;
import classes.class_aaa;
import classes.class_aai;
import classes.class_aaq;
import classes.class_abj;
import classes.class_abk;
import classes.class_abm;
import classes.class_abs;
import classes.class_abt;
import classes.class_abz;
import classes.class_acc;
import classes.class_ace;
import classes.class_acq;
import classes.class_act;
import classes.class_acv;
import classes.class_af;
import classes.class_am;
import classes.class_aq;
import classes.class_aw;
import classes.class_az;
import classes.class_bk;
import classes.class_bq;
import classes.class_bs;
import classes.class_bt;
import classes.class_by;
import classes.class_d;
import classes.class_dh;
import classes.class_dm;
import classes.class_du;
import classes.class_ei;
import classes.class_ej;
import classes.class_el;
import classes.class_en;
import classes.class_eq;
import classes.class_et;
import classes.class_ex;
import classes.class_f;
import classes.class_fb;
import classes.class_fd;
import classes.class_g;
import classes.class_gn;
import classes.class_go;
import classes.class_gw;
import classes.class_hm;
import classes.class_hn;
import classes.class_hw;
import classes.class_hz;
import classes.class_it;
import classes.class_kk;
import classes.class_ko;
import classes.class_kq;
import classes.class_kr;
import classes.class_ls;
import classes.class_na;
import classes.class_nu;
import classes.class_ql;
import classes.class_qz;
import classes.class_s;
import classes.class_sc;
import classes.class_ub;
import classes.class_vh;
import classes.class_vo;
import classes.class_vp;
import classes.class_wc;
import classes.class_xe;
import classes.class_xv;
import classes.class_xw;
import classes.class_yc;
import classes.class_yg;
import classes.class_yi;
import classes.class_zs;
import classes.class_zt;
import classes.class_zu;
import classes.class_zy;
import game.GameMidlet;
import java.util.Vector;
import javax.microedition.lcdui.Image;

public final class class_bi
extends class_kr {
    private static class_bi a;

    public static class_bi a() {
        if (a == null) {
            a = new class_bi();
        }
        return a;
    }

    public final void a(class_abs class_abs2) {
        try {
            Object object = null;
            switch (class_abs2.a) {
                case -74: {
                    class_acv.s.C(class_abs2);
                    return;
                }
                case -73: {
                    class_abj.A(class_abs2);
                    return;
                }
                case 62: {
                    class_abj.y(class_abs2);
                    return;
                }
                case -68: {
                    class_acv.s.x(class_abs2);
                    return;
                }
                case -69: {
                    class_acv.s.w(class_abs2);
                    return;
                }
                case -70: {
                    class_acv.s.v(class_abs2);
                    return;
                }
                case -71: {
                    class_acv.s.B(class_abs2);
                    return;
                }
                case -66: {
                    class_abj.t(class_abs2);
                    return;
                }
                case -67: {
                    class_abj.u(class_abs2);
                    return;
                }
                case -64: {
                    class_acv.s.s(class_abs2);
                    return;
                }
                case 104: {
                    try {
                        class_abj.d(class_abs2.b().readByte());
                        class_abs2.b().readByte();
                        boolean bl = false;
                        return;
                    }
                    catch (Exception exception) {
                        String cfr_ignored_0 = "LOI CHO NAY NE " + exception.toString();
                        return;
                    }
                }
                case 103: {
                    class_acv.s.n(class_abs2);
                    return;
                }
                case 102: {
                    class_abj.a(class_abs2, 0, "B\u1ea0N B\u00c8");
                    return;
                }
                case 101: {
                    class_acv.s.m(class_abs2);
                    return;
                }
                case 95: {
                    short s = class_abs2.b().readShort();
                    short s2 = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    class_acv.s.c(s, s2, by);
                    return;
                }
                case 94: {
                    class_acv.s.k(class_abs2);
                    return;
                }
                case 92: {
                    class_acv.s.l(class_abs2);
                    return;
                }
                case 91: {
                    return;
                }
                case 72: {
                    class_acv.a((String)"\u0110\u00e3 s\u1eeda xong.");
                    return;
                }
                case 89: {
                    class_acv.s.j(class_abs2);
                    return;
                }
                case 90: {
                    class_acv.s.i(class_abs2);
                    return;
                }
                case 82: {
                    class_acv.w = null;
                    if (class_acv.s.q.bP <= 0) {
                        class_acv.s.q.cf = class_acv.s.q.bP = (int)class_abs2.b().readByte();
                        class_acv.s.q.bM = System.currentTimeMillis();
                        return;
                    }
                    break;
                }
                case 81: {
                    class_acv.a((String)"\u0110\u00e3 mua \u0111\u01b0\u1ee3c v\u00e9");
                    return;
                }
                case 80: {
                    class_yi.g = new class_aaq[class_abs2.b().readByte()];
                    int n = 0;
                    while (n < class_yi.g.length) {
                        class_yi.g[n] = new class_aaq();
                        byte by = class_abs2.b().readByte();
                        class_yi.g[n].a = new int[by];
                        int n2 = 0;
                        while (n2 < class_yi.g[n].a.length) {
                            class_yi.g[n].a[n2] = class_abs2.b().readByte();
                            ++n2;
                        }
                        class_yi.g[n].b = new int[by];
                        n2 = 0;
                        while (n2 < class_yi.g[n].b.length) {
                            class_yi.g[n].b[n2] = class_abs2.b().readShort();
                            ++n2;
                        }
                        class_yi.g[n].c = new int[by];
                        n2 = 0;
                        while (n2 < class_yi.g[n].c.length) {
                            class_yi.g[n].c[n2] = class_abs2.b().readShort();
                            ++n2;
                        }
                        class_yi.g[n].d = new int[by];
                        n2 = 0;
                        while (n2 < class_yi.g[n].d.length) {
                            class_yi.g[n].d[n2] = class_abs2.b().readShort();
                            ++n2;
                        }
                        ++n;
                    }
                    return;
                }
                case 77: {
                    class_abj.e(class_abs2.b().readUTF());
                    return;
                }
                case -35: {
                    class_abj.e(class_abs2.b().readUTF());
                    return;
                }
                case -36: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        class_acv.s.m(class_abs2.b().readShort());
                        return;
                    }
                    class_abj.e(class_abs2.b().readUTF());
                    return;
                }
                case 85: {
                    class_acv.a((String)"\u0110\u00e3 mua, m\u00f3n \u0111\u1ed3 \u0111ang \u1edf trong h\u00e0nh trang.");
                    return;
                }
                case 86: {
                    byte by = class_abs2.b().readByte();
                    if (by == 1) {
                        class_acv.s.q.bN = System.currentTimeMillis();
                        class_abs2.b().readByte();
                        class_hw.bO = 1440;
                        class_hw.bQ = class_abs2.b().readUTF();
                        return;
                    }
                    if (by == 0) {
                        class_acv.s.q.bN = 0L;
                        class_hw.bO = 0;
                        class_hw.bQ = "";
                        return;
                    }
                    if (by == 2) {
                        class_abs2.b().readByte();
                        class_acv.s.q.bN = System.currentTimeMillis();
                        class_hw.bO = class_abs2.b().readInt();
                        class_hw.bQ = class_abs2.b().readUTF();
                        return;
                    }
                    break;
                }
                case 73: {
                    class_abj.h(class_abs2);
                    return;
                }
                case 76: {
                    class_abj.g(class_abs2);
                    return;
                }
                case 71: {
                    class_abs2.b().readByte();
                    short s = class_abs2.b().readShort();
                    byte by = 0;
                    try {
                        by = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    class_acv.s.b(s, by);
                    return;
                }
                case -34: {
                    short s = class_abs2.b().readShort();
                    byte by = 0;
                    try {
                        by = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    class_acv.s.c(s, by);
                    return;
                }
                case 68: {
                    class_acv.s.e(class_abs2.b().readShort());
                    class_acv.g();
                    return;
                }
                case 69: {
                    class_acv.s.f(class_abs2.b().readShort());
                    class_acv.g();
                    return;
                }
                case 66: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        class_acv.s.j(class_abs2.b().readShort());
                        return;
                    }
                    if (by == -2) {
                        class_acv.a((String)"Ng\u01b0\u1eddi b\u1ea1n m\u1eddi \u0111ang trao \u0111\u1ed5i v\u1edbi ng\u01b0\u1eddi kh\u00e1c");
                        return;
                    }
                    if (by == 1) {
                        class_acv.s.k(class_abs2.b().readShort());
                        return;
                    }
                    if (by == -1) {
                        class_acv.s.l(class_abs2.b().readShort());
                        return;
                    }
                    if (by == 2) {
                        byte by2 = class_abs2.b().readByte();
                        class_acv.s.a(class_abs2.b().readShort(), class_abs2, by2);
                        return;
                    }
                    if (by == 3) {
                        class_acv.s.t();
                        return;
                    }
                    if (by == 4) {
                        class_acv.s.s();
                        return;
                    }
                    if (by == 5) {
                        class_acv.s.r();
                        return;
                    }
                    break;
                }
                case 67: {
                    class_acv.s.a((int)class_abs2.b().readShort(), (int)class_abs2.b().readByte(), class_abs2.b().readShort());
                    return;
                }
                case 60: {
                    class_acv.s.f(class_abs2);
                    return;
                }
                case 65: {
                    class_acv.s.a(class_abs2.b().readShort(), (int)class_abs2.b().readByte(), (int)class_abs2.b().readByte());
                    return;
                }
                case 2: {
                    Object object2 = class_abs2.b().readUTF();
                    if (((String)object2).startsWith("2")) {
                        object = new String[class_abs2.b().readByte()];
                        short[] sArray = new short[((String[])object).length];
                        String[] stringArray = new String[((class_du[])object).length];
                        int n = 0;
                        while (n < ((class_du[])object).length) {
                            object[n] = class_abs2.b().readUTF();
                            sArray[n] = class_abs2.b().readShort();
                            stringArray[n] = class_abs2.b().readUTF();
                            ++n;
                        }
                        class_acv.s.a(((String)object2).substring(1), (String[])object, sArray, stringArray);
                        return;
                    }
                    if (((String)object2).startsWith("3")) {
                        object = class_abs2.b().readUTF();
                        String string = class_abs2.b().readUTF();
                        GameMidlet.d = object;
                        GameMidlet.e = string;
                        class_aai.a((String)"provider", (String)object);
                        class_aai.a((String)"agent", (String)string);
                        return;
                    }
                    class_acv.s.c((String)object2);
                    return;
                }
                case 1: {
                    int n;
                    Object object3;
                    int n3;
                    Object object2;
                    class_hw.cb = class_abs2.b().readByte();
                    if (class_hw.cb < 3) {
                        class_hw.cb = (byte)3;
                    }
                    class_abj.aP = class_abs2.b().readShort();
                    class_hw.Y = (short)class_abs2.b().readUnsignedByte();
                    class_sc.l = new class_ub[class_hw.Y];
                    class_acv.s.q.bs = new long[class_hw.Y];
                    int n4 = 0;
                    while (n4 < class_hw.Y) {
                        class_sc.l[n4] = new class_ub();
                        class_sc.l[n4].e = (short)class_abs2.b().readUnsignedByte();
                        class_sc.l[n4].g = class_abs2.b().readUTF();
                        class_sc.l[n4].h = class_abs2.b().readUTF();
                        class_sc.l[n4].c = class_abs2.b().readShort();
                        class_sc.l[n4].d = (short)n4;
                        class_sc.l[n4].f = class_abs2.b().readBoolean();
                        ++n4;
                    }
                    n4 = 0;
                    while (n4 < class_hw.bt.length) {
                        class_hw.bt[n4] = class_abs2.b().readByte();
                        ++n4;
                    }
                    class_abj.x.removeAllElements();
                    n4 = 0;
                    while (n4 < 5) {
                        n3 = class_abs2.b().readByte();
                        object3 = new Vector();
                        n = 0;
                        while (n < n3) {
                            object2 = new class_bt();
                            new class_bt().c = class_abs2.b().readByte();
                            ((class_bt)object2).a = class_abs2.b().readUTF();
                            ((class_bt)object2).b = class_abs2.b().readUTF();
                            ((class_bt)object2).d = class_abs2.b().readInt();
                            ((Vector)object3).addElement(object2);
                            ++n;
                        }
                        class_abj.x.addElement(object3);
                        ++n4;
                    }
                    if (class_abs2.b().available() > 0) {
                        class_abj.W = class_abs2.b().readShort();
                        n4 = class_abs2.b().readUnsignedByte();
                        n3 = 0;
                        while (n3 < n4) {
                            object3 = String.valueOf(class_abs2.b().readUnsignedByte());
                            class_nu.a().ai.addElement(object3);
                            ++n3;
                        }
                        n3 = 0;
                        while (n3 < 5) {
                            int n5 = 0;
                            while (n5 < 5) {
                                class_hw.cu[n3][n5] = class_abs2.b().readByte();
                                class_hw.cv[n3][n5] = class_abs2.b().readByte();
                                class_hw.cw[n3][n5] = class_abs2.b().readByte();
                                class_hw.cx[n3][n5] = class_abs2.b().readByte();
                                ++n5;
                            }
                            ++n3;
                        }
                    }
                    class_abj.Z = class_abs2.b().readByte();
                    class_abj.aa = class_abs2.b().readByte();
                    class_abj.ab = class_abs2.b().readByte();
                    class_abj.ac = class_abs2.b().readByte();
                    byte by = class_abs2.b().readByte();
                    n4 = by;
                    class_yi.ac = new String[by];
                    class_yi.ad = new byte[n4];
                    n3 = 0;
                    while (n3 < n4) {
                        class_yi.ac[n3] = class_abs2.b().readUTF();
                        class_yi.ad[n3] = class_abs2.b().readByte();
                        ++n3;
                    }
                    String string = class_abs2.b().readUTF();
                    if (!class_xw.f.equals(string)) {
                        class_xw.f = string;
                        class_aai.a((String)"numbersupport", (String)string);
                    }
                    byte by3 = class_abs2.b().readByte();
                    class_abj.aY[0] = new byte[by3];
                    class_abj.aY[1] = new byte[by3];
                    class_abj.aZ[1] = new int[by3];
                    class_abj.aZ[0] = new int[by3];
                    n = 0;
                    while (n < by3) {
                        class_abj.aY[0][n] = class_abs2.b().readByte();
                        class_abj.aZ[0][n] = class_abs2.b().readInt();
                        class_abj.aY[1][n] = class_abs2.b().readByte();
                        class_abj.aZ[1][n] = class_abs2.b().readInt();
                        ++n;
                    }
                    byte by4 = class_abs2.b().readByte();
                    n = by4;
                    class_aq.a = new String[by4];
                    class_aq.b = new byte[n];
                    int n6 = 0;
                    while (n6 < n) {
                        class_aq.b[n6] = class_abs2.b().readByte();
                        class_aq.a[n6] = class_abs2.b().readUTF();
                        ++n6;
                    }
                    GameMidlet.f = class_abs2.b().readUTF();
                    n6 = class_abs2.b().readByte();
                    String cfr_ignored_1 = String.valueOf(n6) + "tong so tile";
                    n4 = 0;
                    while (n4 < n6) {
                        short s = class_abs2.b().readShort();
                        byte[] byArray = new byte[s];
                        n = 0;
                        while (n < s) {
                            byArray[n] = class_abs2.b().readByte();
                            ++n;
                        }
                        class_aai.a((String)class_acv.M[n4], (byte[])byArray);
                        ++n4;
                    }
                    class_abs2.b().readByte();
                    try {
                        class_acq.a.clear();
                        int n7 = class_abs2.b().readByte();
                        int n8 = 0;
                        while (n8 < n7) {
                            byte[] byArray = new byte[class_abs2.b().readShort()];
                            class_abs2.b().read(byArray);
                            class_acq.a((int)n8, (byte[])byArray);
                            ++n8;
                        }
                        break;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                case 3: {
                    class_sc class_sc2 = class_acv.s.q;
                    try {
                        class_sc2.cG = class_abs2.b().readShort();
                        class_sc2.am = class_abs2.b().readUTF();
                        class_sc2.ay = (short)class_d.g.a(class_sc2.am);
                        class_sc2.v = class_sc2.t = class_abs2.b().readInt();
                        class_sc2.w = class_abs2.b().readInt();
                        class_sc2.bz = class_abs2.b().readInt();
                        class_sc2.by = class_abs2.b().readInt();
                        class_sc2.aJ = class_abs2.b().readByte();
                        class_sc2.aO = class_abs2.b().readByte();
                        class_sc2.K = class_abs2.b().readInt();
                        class_sc2.L = class_abs2.b().readInt();
                        class_sc2.M = class_abs2.b().readInt();
                        class_sc2.E = class_abs2.b().readShort();
                        class_sc2.F = class_abs2.b().readShort();
                        class_sc2.G = class_abs2.b().readShort();
                        class_sc2.P = class_abs2.b().readByte();
                        class_sc2.N = class_abs2.b().readByte();
                        class_sc2.aR = class_abs2.b().readShort();
                        class_sc2.aB = class_abs2.b().readShort();
                        class_sc2.aD = class_abs2.b().readShort();
                        class_sc2.aC = class_abs2.b().readShort();
                        class_sc2.aE = class_abs2.b().readShort();
                        class_sc2.aF = class_abs2.b().readShort();
                        class_sc2.az = class_abs2.b().readShort();
                        class_sc2.aA = class_abs2.b().readShort();
                        class_sc2.cA = class_abs2.b().readInt();
                        class_sc2.bX = class_abs2.b().readShort();
                        class_hw.aS = new byte[class_abs2.b().readByte()];
                        int n = 0;
                        while (n < class_hw.aS.length) {
                            class_hw.aS[n] = class_abs2.b().readByte();
                            ++n;
                        }
                        class_sc2.cQ = class_abs2.b().readShort();
                        if (class_sc2.cQ > 0) {
                            class_sc2.cY = true;
                        }
                        class_sc2.ap = class_abs2.b().readByte();
                        byte by = class_abs2.b().readByte();
                        n = by;
                        if (by >= 0) {
                            class_sc2.ck = (byte)(n - 1);
                        }
                        class_sc2.cl = class_abs2.b().readByte();
                        class_sc2.cj = class_abs2.b().readByte();
                        class_sc2.I = class_abs2.b().readByte();
                        class_sc2.cH = class_abs2.b().readShort();
                        if (class_sc2.cH != -1) {
                            class_acv.s.q.ae = class_abs2.b().readByte();
                        }
                        if (class_acv.s.q.ae == 0) {
                            class_hw.ad = class_abs2.b().readBoolean();
                        }
                        class_sc2.bJ = class_abs2.b().readBoolean();
                        class_sc2.e = class_abs2.b().readByte();
                        class_sc2.cS = class_abs2.b().readByte();
                        class_sc2.cT = class_abs2.b().readByte();
                        class_sc2.bZ = class_abs2.b().readShort();
                        class_sc2.bY = class_abs2.b().readInt();
                        class_sc2.ca = class_abs2.b().readShort();
                        class_sc2.cB = class_abs2.b().readInt();
                        class_sc2.cC = class_abs2.b().readUTF();
                        class_sc2.aj = class_abs2.b().readBoolean();
                        int n9 = 0;
                        short s = 0;
                        try {
                            n9 = class_abs2.b().readShort();
                            s = class_abs2.b().readByte();
                        }
                        catch (Exception exception) {
                            n9 = -1;
                            s = -1;
                        }
                        class_acv.s.q.b(n9, (int)s);
                        short s3 = 0;
                        s = 0;
                        short s4 = 0;
                        try {
                            s3 = class_abs2.b().readShort();
                            s = class_abs2.b().readByte();
                            s4 = class_abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            s3 = -1;
                            s = -1;
                            s4 = -1;
                        }
                        class_acv.s.q.a((int)s3, (int)s, (int)s4);
                        s3 = 0;
                        s = 0;
                        s4 = 0;
                        try {
                            s3 = class_abs2.b().readShort();
                            s = class_abs2.b().readShort();
                            s4 = class_abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            s3 = -1;
                            s = -1;
                            s4 = -1;
                        }
                        class_acv.s.q.a(s3, s, s4);
                        s = 0;
                        try {
                            byte by5 = class_abs2.b().readByte();
                            s = by5;
                            short[] sArray = new short[by5];
                            byte[] byArray = new byte[s];
                            n9 = 0;
                            while (n9 < s) {
                                sArray[n9] = class_abs2.b().readShort();
                                byArray[n9] = class_abs2.b().readByte();
                                ++n9;
                            }
                            class_acv.s.q.a(sArray, byArray);
                            String string = class_abs2.b().readUTF();
                            class_nu.k = !string.equals("") ? class_yg.a((String)string, (String)"@") : null;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        int n10 = 0;
                        try {
                            n10 = class_abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            n10 = -1;
                        }
                        class_acv.s.q.bc = (short)n10;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    int n = 0;
                    int n11 = 0;
                    int n12 = 0;
                    byte by = 0;
                    try {
                        n = class_abs2.b().readByte();
                        n11 = class_abs2.b().readShort();
                        n12 = class_abs2.b().readByte();
                        by = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n = -1;
                        n11 = -1;
                        n12 = 0;
                        by = 0;
                    }
                    class_acv.s.q.bf = (byte)n;
                    class_acv.s.q.bk = (short)n11;
                    class_acv.s.q.bg = n12;
                    class_acv.s.q.bh = by;
                    n12 = 0;
                    int n13 = 0;
                    by = 0;
                    try {
                        n12 = class_abs2.b().readByte();
                        n13 = class_abs2.b().readShort();
                        by = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n12 = -1;
                        n13 = -1;
                        by = 0;
                    }
                    class_acv.s.q.bj = (byte)n12;
                    class_acv.s.q.bl = (short)n13;
                    class_acv.s.q.bi = by;
                    class_acv.s.j();
                    return;
                }
                case 5: {
                    if (!class_acv.s.s) {
                        return;
                    }
                    int n = class_abs2.b().readShort();
                    class_hw class_hw2 = (class_hw)class_acv.s.b((short)n);
                    if (class_hw2 == null) break;
                    class_hw2.cG = n;
                    if (class_hw2.Q != -1) {
                        class_hw2.D = 0;
                    }
                    class_hw2.am = class_abs2.b().readUTF();
                    class_hw2.cK = class_abs2.b().readShort();
                    class_hw2.cL = class_abs2.b().readShort();
                    class_hw2.v = class_abs2.b().readInt();
                    class_hw2.w = class_abs2.b().readInt();
                    class_hw2.bz = class_abs2.b().readInt();
                    class_hw2.by = class_abs2.b().readInt();
                    class_hw2.aJ = class_abs2.b().readByte();
                    class_hw2.aO = class_abs2.b().readByte();
                    class_abs2.b().read(class_hw2.bC, 0, class_hw2.bC.length);
                    n = 0;
                    while (n < class_hw2.bC.length) {
                        class_hw2.bE[n] = class_abs2.b().readShort();
                        ++n;
                    }
                    class_hw2.cQ = class_abs2.b().readShort();
                    class_hw2.cR = class_abs2.b().readByte();
                    class_hw2.L = class_abs2.b().readShort();
                    class_hw2.M = class_abs2.b().readShort();
                    class_hw2.N = class_abs2.b().readByte();
                    class_hw2.P = class_abs2.b().readByte();
                    byte by = class_abs2.b().readByte();
                    n = by;
                    if (by >= 0) {
                        class_hw2.ck = (byte)(n - 1);
                    }
                    class_hw2.cl = class_abs2.b().readByte();
                    class_hw2.cj = class_abs2.b().readByte();
                    class_hw2.I = class_abs2.b().readByte();
                    class_hw2.cH = class_abs2.b().readShort();
                    class_hw2.Q = class_abs2.b().readByte();
                    if (class_hw2.cH != -1) {
                        class_hw2.ae = class_abs2.b().readByte();
                    }
                    class_hw2.aP = class_abs2.b().readByte();
                    int n14 = 0;
                    while (n14 < class_hw2.bW.length) {
                        class_hw2.bW[n14] = class_abs2.b().readShort();
                        ++n14;
                    }
                    class_hw2.bJ = class_abs2.b().readBoolean();
                    class_hw2.H();
                    class_abj.a(class_hw2);
                    if (class_abs2.b().readBoolean()) {
                        class_acv.s.D.u((int)class_hw2.cG);
                    }
                    class_hw2.cS = class_abs2.b().readByte();
                    class_hw2.cT = class_abs2.b().readByte();
                    class_hw2.aj = class_abs2.b().readBoolean();
                    int n15 = 0;
                    try {
                        n14 = class_abs2.b().readShort();
                        n15 = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n14 = -1;
                        n15 = -1;
                    }
                    class_hw2.b(n14, n15);
                    int n16 = 0;
                    int n17 = 0;
                    int n18 = 0;
                    try {
                        n16 = class_abs2.b().readShort();
                        n17 = class_abs2.b().readByte();
                        n18 = class_abs2.b().readShort();
                    }
                    catch (Exception exception) {
                        n16 = -1;
                        n17 = -1;
                        n18 = -1;
                    }
                    class_hw2.a(n16, n17, n18);
                    int n19 = 0;
                    short s = 0;
                    short s5 = 0;
                    try {
                        n19 = class_abs2.b().readShort();
                        s = class_abs2.b().readShort();
                        s5 = class_abs2.b().readShort();
                    }
                    catch (Exception exception) {
                        n19 = -1;
                        s = -1;
                        s5 = -1;
                    }
                    class_hw2.a((short)n19, s, s5);
                    int n20 = 0;
                    try {
                        n20 = class_abs2.b().readByte();
                        short[] sArray = new short[n20];
                        byte[] byArray = new byte[n20];
                        n15 = 0;
                        while (n15 < n20) {
                            sArray[n15] = class_abs2.b().readShort();
                            byArray[n15] = class_abs2.b().readByte();
                            ++n15;
                        }
                        class_hw2.a(sArray, byArray);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    int n21 = 0;
                    try {
                        n21 = class_abs2.b().readShort();
                    }
                    catch (Exception exception) {
                        n21 = -1;
                    }
                    class_hw2.bc = (short)n21;
                    int n22 = 0;
                    n15 = 0;
                    n16 = 0;
                    n17 = 0;
                    try {
                        n22 = class_abs2.b().readByte();
                        n15 = class_abs2.b().readShort();
                        n16 = class_abs2.b().readByte();
                        n17 = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n22 = -1;
                        n15 = -1;
                        n16 = 0;
                        n17 = 0;
                    }
                    class_hw2.bf = (byte)n22;
                    class_hw2.bk = (short)n15;
                    class_hw2.bg = (byte)n16;
                    class_hw2.bh = (byte)n17;
                    int n23 = 0;
                    n19 = 0;
                    byte by6 = 0;
                    try {
                        n23 = class_abs2.b().readByte();
                        n19 = class_abs2.b().readShort();
                        by6 = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        n23 = -1;
                        n19 = -1;
                        by6 = 0;
                    }
                    class_hw2.bj = (byte)n23;
                    class_hw2.bl = (short)n19;
                    class_hw2.bi = by6;
                    return;
                }
                case 12: {
                    short s = class_abs2.b().readShort();
                    short s6 = class_abs2.b().readShort();
                    short s7 = class_abs2.b().readShort();
                    int n = -1;
                    try {
                        n = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                    }
                    short s8 = class_abs2.b().readShort();
                    String string = class_abs2.b().readUTF();
                    byte[] byArray = null;
                    try {
                        boolean bl = class_abs2.b().readBoolean();
                        if (bl) {
                            int n24 = 0;
                            int n25 = 0;
                            byArray = new byte[class_abs2.b().available()];
                            while (n24 != -1 && n25 < byArray.length) {
                                n24 = class_abs2.b().read(byArray, 0, byArray.length - n25);
                                n25 += n24;
                            }
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    class_acv.s.a(s, s6, s7, s8, string, byArray);
                    class_ls.k = (byte)n;
                    return;
                }
                case 4: {
                    while (class_abs2.b().available() > 0) {
                        byte by = class_abs2.b().readByte();
                        short s = (short)class_abs2.b().readUnsignedByte();
                        short s9 = class_abs2.b().readShort();
                        short s10 = class_abs2.b().readShort();
                        short s11 = class_abs2.b().readShort();
                        byte by7 = class_abs2.b().readByte();
                        byte by8 = 0;
                        int n = -1;
                        try {
                            n = class_abs2.b().readInt();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        try {
                            if (by == 1) {
                                by8 = class_abs2.b().readByte();
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        byte by9 = -1;
                        try {
                            by9 = class_abs2.b().readByte();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        boolean bl = true;
                        try {
                            bl = class_abs2.b().readBoolean();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        class_acv.s.a(by, s, s9, s10, s11, by7, n, by8, by9, bl);
                    }
                    return;
                }
                case 8: {
                    while (class_abs2.b().available() > 0) {
                        short s = class_abs2.b().readShort();
                        class_acv.s.d(s);
                    }
                    return;
                }
                case 7: {
                    class_by class_by2 = new class_by();
                    new class_by().a = class_abs2.b().readShort();
                    class_by2.h = (short)class_abs2.b().readUnsignedByte();
                    class_by2.b = class_abs2.b().readShort();
                    class_by2.c = class_abs2.b().readShort();
                    class_by2.e = class_abs2.b().readInt();
                    class_by2.d = class_abs2.b().readByte();
                    class_by2.i = class_abs2.b().readByte();
                    class_by2.f = class_abs2.b().readInt();
                    class_by2.g = class_abs2.b().readInt();
                    class_acv.s.a(class_by2);
                    return;
                }
                case 6: {
                    short s = class_abs2.b().readShort();
                    short s12 = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    int n = class_abs2.b().readInt();
                    int n26 = class_abs2.b().readInt();
                    byte by10 = class_abs2.b().readByte();
                    byte by11 = class_abs2.b().readByte();
                    byte by12 = class_abs2.b().readByte();
                    byte by13 = class_abs2.b().readByte();
                    class_acv.s.a(s, s12, by, n, n26, by10, by11, by12, by13);
                    return;
                }
                case 9: {
                    short s = class_abs2.b().readShort();
                    short s13 = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    int n = class_abs2.b().readInt();
                    int n27 = class_abs2.b().readInt();
                    byte by14 = class_abs2.b().readByte();
                    byte by15 = class_abs2.b().readByte();
                    byte by16 = class_abs2.b().readByte();
                    byte by17 = class_abs2.b().readByte();
                    class_acv.s.b(s, s13, by, n, n27, by14, by15, by16, by17);
                    return;
                }
                case 106: {
                    class_acv.s.a(class_abs2);
                    return;
                }
                case 10: {
                    short s = class_abs2.b().readShort();
                    short s14 = class_abs2.b().readShort();
                    int n = class_abs2.b().readInt();
                    int n28 = class_abs2.b().readInt();
                    class_acv.s.a(s, s14, n, n28);
                    return;
                }
                case 83: {
                    class_acv.s.b(class_abs2);
                    return;
                }
                case 17: {
                    Object object2;
                    try {
                        object2 = new class_a();
                        new class_a().a = class_abs2.b().readShort();
                        ((class_a)object2).b = class_abs2.b().readShort();
                        ((class_a)object2).c = class_abs2.b().readByte();
                        ((class_a)object2).e = class_abs2.b().readInt();
                        ((class_a)object2).f = class_abs2.b().readByte();
                        ((class_a)object2).d = new class_du[class_abs2.b().readByte()];
                        int n = 0;
                        while (n < ((class_a)object2).d.length) {
                            ((class_a)object2).d[n] = new class_du();
                            ((class_a)object2).d[n].a = class_abs2.b().readByte();
                            String string = String.valueOf(((class_a)object2).d[n].a) + "_";
                            ((class_a)object2).d[n].b = class_abs2.b().readShort();
                            string = String.valueOf(string) + ((class_a)object2).d[n].b + "_";
                            ((class_a)object2).d[n].c = class_abs2.b().readShort();
                            string = String.valueOf(string) + ((class_a)object2).d[n].c + "_";
                            ((class_a)object2).d[n].d = class_abs2.b().readShort();
                            string = String.valueOf(string) + ((class_a)object2).d[n].d + "_";
                            ((class_a)object2).d[n].e = class_abs2.b().readShort();
                            String cfr_ignored_2 = String.valueOf(string) + ((class_a)object2).d[n].e + "_";
                            ++n;
                        }
                        if (class_abs2.b().available() > 0) {
                            ((class_a)object2).g = class_abs2.b().readByte();
                        }
                        if (class_abs2.b().available() > 0) {
                            class_abs2.b().readByte();
                        }
                        if (class_abs2.b().available() > 0) {
                            ((class_a)object2).h = class_abs2.b().readByte();
                        }
                        class_acv.s.a((class_a)object2);
                        return;
                    }
                    catch (Exception exception) {
                        String cfr_ignored_3 = "LOI TRONG HAM NHAN DATA " + exception.toString();
                        return;
                    }
                }
                case 64: {
                    object = new class_du[class_abs2.b().readByte()];
                    int n = 0;
                    while (n < ((class_du[])object).length) {
                        object[n] = new class_du();
                        object[n].a = class_abs2.b().readByte();
                        object[n].b = class_abs2.b().readByte();
                        object[n].c = class_abs2.b().readShort();
                        object[n].d = class_abs2.b().readShort();
                        object[n].e = class_abs2.b().readShort();
                        ++n;
                    }
                    return;
                }
                case 11: {
                    class_acv.s.k();
                    return;
                }
                case 13: {
                    int n;
                    int n29;
                    int n30;
                    int n31;
                    int n32;
                    int n33 = class_abs2.b().readByte();
                    object = null;
                    if (n33 == -1) {
                        class_acv.a((String)class_abs2.b().readUTF());
                        return;
                    }
                    object = new class_sc[n33];
                    int n34 = 0;
                    while (n34 < n33) {
                        object[n34] = new class_sc();
                        object[n34].ac = class_abs2.b().readInt();
                        object[n34].am = class_abs2.b().readUTF();
                        object[n34].aJ = class_abs2.b().readByte();
                        n32 = class_abs2.b().readByte();
                        int n35 = 0;
                        while (n35 < n32) {
                            n31 = class_abs2.b().readByte();
                            n30 = class_abs2.b().readByte();
                            if (n31 == 0) {
                                object[n34].aG = (short)n30;
                            } else if (n31 == 1) {
                                object[n34].aH = (short)n30;
                            } else if (n31 == 2) {
                                object[n34].aI = (short)n30;
                            } else if (n31 == 3 || n31 == 4 || n31 == 5 || n31 == 6 || n31 == 7) {
                                object[n34].bn = n31;
                                object[n34].bm = n30;
                            } else if (n31 == 19) {
                                object[n34].aK = (short)n30;
                            }
                            ++n35;
                        }
                        object[n34].o = class_abs2.b().readShort();
                        object[n34].ab = class_abs2.b().readByte();
                        object[n34].cS = class_abs2.b().readByte();
                        object[n34].X = class_abs2.b().readShort();
                        byte by = class_abs2.b().readByte();
                        n35 = by;
                        if (by != -1) {
                            short s = class_abs2.b().readShort();
                            n31 = s;
                            byte[] byArray = new byte[s];
                            n29 = 0;
                            while (n29 < n31) {
                                byArray[n29] = class_abs2.b().readByte();
                                ++n29;
                            }
                            short s15 = class_abs2.b().readShort();
                            n29 = s15;
                            byte[] byArray2 = new byte[s15];
                            n = 0;
                            while (n < n29) {
                                byArray2[n] = class_abs2.b().readByte();
                                ++n;
                            }
                            object[n34].aX = class_yi.a((byte[])byArray, (byte[])byArray2);
                            object[n34].aZ = class_abs2.b().readByte();
                            object[n34].ba = class_abs2.b().readByte();
                        }
                        ++n34;
                    }
                    n34 = 0;
                    while (n34 < n33) {
                        n32 = 0;
                        try {
                            n32 = class_abs2.b().readInt();
                        }
                        catch (Exception exception) {
                            n32 = -1;
                        }
                        if (n32 != -1 && object[n34].ac == n32) {
                            int n36 = 0;
                            n31 = 0;
                            n30 = 0;
                            n29 = 0;
                            int n37 = 0;
                            n = 0;
                            int n38 = 0;
                            int n39 = 0;
                            int n40 = 0;
                            try {
                                n36 = class_abs2.b().readShort();
                                n31 = class_abs2.b().readByte();
                                n30 = class_abs2.b().readByte();
                                n29 = class_abs2.b().readShort();
                                n37 = class_abs2.b().readByte();
                                n = class_abs2.b().readByte();
                                n38 = class_abs2.b().readShort();
                                n39 = class_abs2.b().readByte();
                                n40 = class_abs2.b().readByte();
                            }
                            catch (Exception exception) {
                                n36 = -1;
                                n31 = -1;
                                n30 = -1;
                                n29 = -1;
                                n37 = -1;
                                n = -1;
                                n38 = -1;
                                n39 = -1;
                                n40 = -1;
                            }
                            object[n34].bl = (short)n36;
                            object[n34].bj = (byte)n31;
                            object[n34].bi = (byte)n30;
                            object[n34].bk = (short)n29;
                            object[n34].bf = (byte)n37;
                            object[n34].bh = (byte)n;
                            object[n34].be = (short)n38;
                            object[n34].bd = (short)n39;
                            object[n34].bg = (byte)n40;
                        }
                        ++n34;
                    }
                    class_bq.a().a((class_sc[])object);
                    class_bq.a().d();
                    return;
                }
                case 15: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        String string;
                        byte by18;
                        byte by19;
                        Object object4;
                        Vector<class_ql> vector = new Vector<class_ql>();
                        short s = class_abs2.b().readShort();
                        int n = class_abs2.b().readByte();
                        int n41 = 0;
                        while (n41 < n) {
                            object4 = new class_ql();
                            new class_ql().m = object4.D = class_abs2.b().readByte();
                            object4.i = class_abs2.b().readShort();
                            object4.r = class_abs2.b().readShort();
                            object4.s = class_abs2.b().readByte();
                            object4.y = class_abs2.b().readByte();
                            object4.v = class_abs2.b().readShort();
                            object4.u = class_abs2.b().readShort();
                            object4.K = class_abs2.b().readByte();
                            object4.L = class_abs2.b().readByte();
                            object4.n = class_abs2.b().readByte();
                            object4.o = class_abs2.b().readByte();
                            object4.p = class_abs2.b().readByte();
                            object4.q = class_abs2.b().readByte();
                            object4.C = class_abs2.b().readByte();
                            object4.d = class_abs2.b().readUTF();
                            object4.H.removeAllElements();
                            object4.x = System.currentTimeMillis();
                            object4.w = class_abs2.b().readUnsignedShort();
                            by19 = class_abs2.b().readByte();
                            by18 = 0;
                            while (by18 < by19) {
                                string = new class_zu((short)class_abs2.b().readUnsignedByte(), class_abs2.b().readShort());
                                object4.H.addElement(string);
                                by18 = (byte)(by18 + 1);
                            }
                            object4.F = true;
                            vector.addElement((class_ql)object4);
                            ++n41;
                        }
                        n41 = class_abs2.b().readByte();
                        object4 = new short[]{-1, -1, -1, -1, -1};
                        by19 = 0;
                        while (by19 < ((class_ql)object4).length) {
                            object4[by19] = (class_ql)class_abs2.b().readShort();
                            ++by19;
                        }
                        by19 = class_abs2.b().readByte();
                        by18 = 0;
                        string = "";
                        if (by19 != -1) {
                            string = class_abs2.b().readUTF();
                            by18 = class_abs2.b().readByte();
                        }
                        byte by20 = class_abs2.b().readByte();
                        class_af class_af2 = null;
                        if (by20 != -1) {
                            class_af2 = new class_af();
                            new class_af().l = class_abs2.b().readByte();
                            class_af2.m = class_abs2.b().readShort();
                            class_af2.g = class_abs2.b().readByte();
                            class_af2.s = class_abs2.b().readUTF();
                            class_af2.p = class_abs2.b().readInt();
                            class_af2.r = System.currentTimeMillis();
                            class_af2.o = 0;
                            class_af2.n = 0;
                            class_af2.cF = (byte)12;
                        }
                        class_acv.s.a(s, vector, (byte)n41, by19, string, class_af2, (short[])object4, by18);
                        return;
                    }
                    Vector<class_ql> vector = new Vector<class_ql>();
                    short s = class_abs2.b().readShort();
                    int n = class_abs2.b().readByte();
                    if (n > -1) {
                        int n42;
                        int n43;
                        class_ql class_ql2;
                        int n44 = 0;
                        while (n44 < n) {
                            class_ql2 = new class_ql();
                            new class_ql().m = class_ql2.D = class_abs2.b().readByte();
                            class_ql2.i = class_abs2.b().readShort();
                            class_ql2.r = class_abs2.b().readShort();
                            class_ql2.s = class_abs2.b().readByte();
                            class_ql2.y = class_abs2.b().readByte();
                            class_ql2.v = class_abs2.b().readShort();
                            class_ql2.u = class_abs2.b().readShort();
                            class_ql2.K = class_abs2.b().readByte();
                            class_ql2.L = class_abs2.b().readByte();
                            class_ql2.n = class_abs2.b().readByte();
                            class_ql2.o = class_abs2.b().readByte();
                            class_ql2.p = class_abs2.b().readByte();
                            class_ql2.q = class_abs2.b().readByte();
                            class_ql2.C = class_abs2.b().readByte();
                            class_ql2.d = class_abs2.b().readUTF();
                            class_ql2.H.removeAllElements();
                            n43 = class_abs2.b().readByte();
                            n42 = 0;
                            while (n42 < n43) {
                                class_zu class_zu2 = new class_zu((short)class_abs2.b().readUnsignedByte(), class_abs2.b().readShort());
                                class_ql2.H.addElement(class_zu2);
                                n42 = (byte)(n42 + 1);
                            }
                            class_ql2.F = true;
                            vector.addElement(class_ql2);
                            ++n44;
                        }
                        n44 = class_abs2.b().readByte();
                        class_ql2 = null;
                        n43 = 0;
                        n42 = 0;
                        byte by21 = 1;
                        byte[] byArray = new byte[class_abs2.b().available()];
                        while (class_abs2.b().available() > 0) {
                            class_abs2.b().read(byArray, 0, byArray.length);
                        }
                        if (byArray.length > 0) {
                            class_ql2 = class_yi.b((byte[])byArray);
                            by21 = (byte)(n44 == 3 ? 3 : 6);
                            if (class_ql2 != null) {
                                n43 = class_ql2.getWidth();
                                n42 = class_ql2.getHeight() / n44;
                            }
                        }
                        class_nu.a().l = 0;
                        class_acv.s.a(s, vector, (Image)class_ql2, (byte)n44, n43, n42, by21);
                        return;
                    }
                    break;
                }
                case 16: {
                    byte by = class_abs2.b().readByte();
                    int[] nArray = new int[class_hw.Y];
                    long l = 0L;
                    try {
                        int n;
                        int n45;
                        if (by == 0) {
                            l = class_abs2.b().readLong();
                            nArray[0] = 0;
                            int n46 = class_abs2.b().readUnsignedByte();
                            n45 = 0;
                            while (n45 < n46) {
                                int n47;
                                n = class_abs2.b().readUnsignedByte();
                                class_sc.l[n].a = n47 = class_abs2.b().readInt();
                                nArray[n] = n47;
                                ++n45;
                            }
                        }
                        Vector<class_ql> vector = new Vector<class_ql>();
                        if (by == 1) {
                            n45 = class_abs2.b().readShort();
                            n = 0;
                            while (n < n45) {
                                class_ql class_ql3 = new class_ql();
                                new class_ql().m = class_abs2.b().readByte();
                                class_ql3.i = class_abs2.b().readShort();
                                class_ql3.r = class_abs2.b().readShort();
                                class_ql3.s = class_abs2.b().readByte();
                                class_ql3.y = class_abs2.b().readByte();
                                class_ql3.v = class_abs2.b().readShort();
                                class_ql3.u = class_abs2.b().readShort();
                                class_ql3.D = class_abs2.b().readByte();
                                class_ql3.I = class_abs2.b().readByte();
                                class_ql3.J = class_abs2.b().readByte();
                                class_ql3.K = class_abs2.b().readByte();
                                class_ql3.n = class_abs2.b().readByte();
                                class_ql3.o = class_abs2.b().readByte();
                                class_ql3.p = class_abs2.b().readByte();
                                class_ql3.q = class_abs2.b().readByte();
                                class_ql3.C = class_abs2.b().readByte();
                                class_ql3.d = class_abs2.b().readUTF();
                                class_ql3.H.removeAllElements();
                                class_ql3.x = System.currentTimeMillis();
                                class_ql3.w = class_abs2.b().readUnsignedShort();
                                byte by22 = class_abs2.b().readByte();
                                byte by23 = 0;
                                while (by23 < by22) {
                                    class_zu class_zu3 = new class_zu((short)class_abs2.b().readUnsignedByte(), class_abs2.b().readShort());
                                    class_ql3.H.addElement(class_zu3);
                                    by23 = (byte)(by23 + 1);
                                }
                                class_ql3.h = class_abs2.b().readByte();
                                class_ql3.F = true;
                                vector.addElement(class_ql3);
                                ++n;
                            }
                        }
                        class_acv.s.q.aV = class_abs2.b().readInt();
                        Vector<class_abz> vector2 = new Vector<class_abz>();
                        if (by == 2) {
                            n = class_abs2.b().readByte();
                            int n48 = 0;
                            while (n48 < n) {
                                class_abz class_abz2 = new class_abz();
                                new class_abz().e = class_abs2.b().readShort();
                                class_abs2.b().readByte();
                                class_abz2.h = class_abs2.b().readByte();
                                class_abz2.c = class_abs2.b().readByte();
                                class_abz2.b = class_abs2.b().readUTF();
                                class_abz2.a = class_abs2.b().readUTF();
                                class_abz2.d = class_abs2.b().readByte();
                                vector2.addElement(class_abz2);
                                ++n48;
                            }
                            n = class_abs2.b().readByte();
                            n48 = 0;
                            while (n48 < n) {
                                class_abz class_abz3 = new class_abz();
                                new class_abz().e = class_abs2.b().readShort();
                                class_abs2.b().readByte();
                                class_abz3.h = class_abs2.b().readByte();
                                class_abz3.b = class_abs2.b().readUTF();
                                class_abs2.b().readByte();
                                class_abz3.a = class_abs2.b().readUTF();
                                class_abz3.g = class_abs2.b().readInt();
                                class_abz3.f = System.currentTimeMillis();
                                class_abz3.d = class_abs2.b().readByte();
                                vector2.addElement(class_abz3);
                                ++n48;
                            }
                        }
                        if (by == 3) {
                            class_sc.m = new short[class_abs2.b().readByte()];
                            n = 0;
                            while (n < class_sc.m.length) {
                                class_sc.m[n] = class_abs2.b().readShort();
                                ++n;
                            }
                        }
                        class_acv.s.q.aW = class_abs2.b().readInt();
                        class_acv.s.a(l, nArray, vector, vector2, (int)by);
                        return;
                    }
                    catch (Exception exception) {
                        String cfr_ignored_4 = "LOI CHARINVENTORY " + by + exception.toString();
                        return;
                    }
                }
                case 18: {
                    short s = class_abs2.b().readShort();
                    class_ql class_ql4 = new class_ql();
                    new class_ql().D = class_ql4.m = class_abs2.b().readByte();
                    class_ql4.k = class_abs2.b().readShort();
                    class_ql4.i = class_abs2.b().readShort();
                    class_ql4.r = class_abs2.b().readShort();
                    class_ql4.s = class_abs2.b().readByte();
                    class_ql4.y = class_abs2.b().readByte();
                    class_ql4.u = class_abs2.b().readShort();
                    class_ql4.v = class_abs2.b().readShort();
                    class_acv.s.a(s, class_ql4);
                    return;
                }
                case 19: {
                    short s = class_abs2.b().readShort();
                    short s16 = class_abs2.b().readShort();
                    short s17 = (short)class_abs2.b().readUnsignedByte();
                    short s18 = class_abs2.b().readShort();
                    class_acv.s.a(s, s16, s17, s18);
                    return;
                }
                case -65: {
                    try {
                        short s = class_abs2.b().readShort();
                        short s19 = class_abs2.b().readShort();
                        byte by = class_abs2.b().readByte();
                        class_acv.s.b(s, s19, by);
                        return;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                case -41: {
                    short s = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    short s20 = class_abs2.b().readShort();
                    class_acv.s.a(s, s20, by);
                    return;
                }
                case 21: {
                    class_ql class_ql5 = new class_ql();
                    new class_ql().i = class_abs2.b().readShort();
                    class_ql5.u = class_abs2.b().readShort();
                    class_ql5.D = class_abs2.b().readByte();
                    class_ql5.w = class_abs2.b().readUnsignedShort();
                    class_ql5.H.removeAllElements();
                    byte by = class_abs2.b().readByte();
                    byte by24 = 0;
                    while (by24 < by) {
                        class_zu class_zu4 = new class_zu((short)class_abs2.b().readUnsignedByte(), class_abs2.b().readShort());
                        class_ql5.H.addElement(class_zu4);
                        by24 = (byte)(by24 + 1);
                    }
                    class_ql5.s = class_abs2.b().readByte();
                    try {
                        class_ql5.C = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    class_acv.s.a(class_ql5);
                    return;
                }
                case 22: {
                    short s = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    short s21 = class_abs2.b().readShort();
                    int n = class_abs2.b().readInt();
                    byte by25 = 0;
                    try {
                        by25 = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    class_acv.s.a(s, by, s21, n, (int)by25);
                    return;
                }
                case 23: {
                    short s;
                    int n;
                    Object object5;
                    int n49;
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        n49 = class_abs2.b().readByte();
                        object5 = new byte[n49];
                        n = 0;
                        while (n < n49) {
                            object5[n] = class_abs2.b().readByte();
                            ++n;
                        }
                        class_acv.s.a((byte[])object5);
                    }
                    if (by == 1) {
                        n49 = class_abs2.b().readByte();
                        class_nu.a().y = n49;
                        object5 = new Vector();
                        n = class_abs2.b().readShort();
                        int n50 = 0;
                        while (n50 < n) {
                            class_ql class_ql6 = new class_ql();
                            new class_ql().F = true;
                            class_ql6.A = true;
                            s = class_abs2.b().readShort();
                            class_ql6.m = n49 == -1 ? (byte)class_bi.a(s) : (byte)n49;
                            class_ql6.r = s;
                            class_ql6.D = (byte)(n49 == -1 ? class_bi.a(s) : n49);
                            class_yc class_yc2 = class_yi.b((int)s);
                            class_ql6.y = class_yc2.f;
                            class_ql6.u = class_yc2.g;
                            ((Vector)object5).addElement(class_ql6);
                            ++n50;
                        }
                        try {
                            class_ql.M = (byte)2;
                            class_ql.M = class_abs2.b().readByte();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        class_acv.s.b((Vector)object5);
                    }
                    try {
                        if (by == 2) {
                            Vector<class_ql> vector = new Vector<class_ql>();
                            byte by26 = class_abs2.b().readByte();
                            n = 0;
                            while (n < by26) {
                                class_ql class_ql7 = new class_ql();
                                byte by27 = class_abs2.b().readByte();
                                class_ql7.i = class_abs2.b().readShort();
                                s = class_abs2.b().readShort();
                                if (s < 0) {
                                    s = (short)(s + 256);
                                }
                                class_ql7.m = by27;
                                class_ql7.r = s;
                                class_ql7.s = class_abs2.b().readByte();
                                class_ql7.y = class_abs2.b().readByte();
                                class_ql7.v = class_abs2.b().readShort();
                                class_ql7.u = class_abs2.b().readShort();
                                class_ql7.D = by27;
                                vector.addElement(class_ql7);
                                ++n;
                            }
                            class_acv.s.a(vector);
                        }
                    }
                    catch (Exception exception) {
                        Exception exception3 = exception;
                        exception.printStackTrace();
                    }
                    if (by == 3) {
                        class_acv.s.p();
                    }
                    if (by == 4) {
                        byte by28 = class_abs2.b().readByte();
                        byte[] byArray = new byte[by28 + 2];
                        n = 0;
                        while (n < by28) {
                            byArray[n] = class_abs2.b().readByte();
                            ++n;
                        }
                        byArray[by28] = class_abs2.b().readByte();
                        byArray[by28 + 1] = class_abs2.b().readByte();
                        class_acv.s.b(byArray);
                        return;
                    }
                    break;
                }
                case 25: {
                    Object object6;
                    int n;
                    int n51 = class_abs2.b().readUnsignedByte();
                    class_yc.p.clear();
                    int n52 = 0;
                    while (n52 < n51) {
                        class_yc.p.put(String.valueOf(n52), new class_it((short)class_abs2.b().readUnsignedByte(), class_abs2.b().readUTF(), class_abs2.b().readByte(), class_abs2.b().readByte()));
                        ++n52;
                    }
                    n52 = class_abs2.b().readByte();
                    int n53 = 0;
                    while (n53 < n52) {
                        class_yi.V[n53] = new class_aw(class_abs2.b().readShort(), class_abs2.b().readShort());
                        ++n53;
                    }
                    n53 = 0;
                    while (n53 < 5) {
                        class_yi.R[n53] = class_abs2.b().readByte();
                        ++n53;
                    }
                    short s = class_abs2.b().readShort();
                    n53 = s;
                    class_yc[] class_ycArray = new class_yc[s + 1];
                    int n54 = 0;
                    while (n54 < n53) {
                        n = class_abs2.b().readShort();
                        try {
                            class_ycArray[n] = new class_yc();
                            class_ycArray[n].m = (short)n;
                            class_ycArray[n].a = class_abs2.b().readUTF();
                            class_ycArray[n].c = class_abs2.b().readByte();
                            class_ycArray[n].d = class_abs2.b().readByte();
                            class_abs2.b().readByte();
                            class_ycArray[n].e = class_abs2.b().readByte();
                            class_ycArray[n].f = class_abs2.b().readByte();
                            class_ycArray[n].g = class_abs2.b().readShort();
                            int n55 = 0;
                            while (n55 < 10) {
                                class_ycArray[n].k[n55] = class_abs2.b().readShort();
                                ++n55;
                            }
                            class_ycArray[n].j = class_abs2.b().readInt();
                            class_ycArray[n].l = class_abs2.b().readByte();
                            class_ycArray[n].o = class_abs2.b().readByte();
                            class_ycArray[n].h = class_abs2.b().readShort();
                            class_ycArray[n].i = class_abs2.b().readShort();
                        }
                        catch (Exception exception) {
                            Exception exception4 = exception;
                            exception.printStackTrace();
                        }
                        ++n54;
                    }
                    class_yi.d.addElement(class_ycArray);
                    n54 = class_abs2.b().readShort();
                    class_yi.e.removeAllElements();
                    n = 0;
                    while (n < n54) {
                        class_xv class_xv2 = new class_xv();
                        new class_xv().o = class_abs2.b().readShort();
                        class_xv2.l = class_abs2.b().readByte();
                        class_xv2.r = class_abs2.b().readInt();
                        class_xv2.j = class_abs2.b().readUTF();
                        object6 = class_xv2.j.substring(class_xv2.j.length() - 1);
                        class_xv2.k = class_abs2.b().readUTF();
                        class_xv2.h = class_abs2.b().readByte();
                        try {
                            int n56 = Integer.parseInt((String)object6);
                            if (n56 >= 4 && class_xv2.h != 4) {
                                class_xv2.s = (byte)(n56 - 4);
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        class_xv2.q = class_abs2.b().readBoolean();
                        class_xv2.i = class_abs2.b().readByte();
                        class_xv2.p = class_abs2.b().readByte();
                        class_yi.e.addElement(class_xv2);
                        ++n;
                    }
                    n = class_abs2.b().readUnsignedByte();
                    class_yi.f.removeAllElements();
                    int n57 = 0;
                    while (n57 < n) {
                        object6 = new class_xv();
                        new class_xv().o = (short)class_abs2.b().readUnsignedByte();
                        object6.l = (short)class_abs2.b().readUnsignedByte();
                        object6.r = class_abs2.b().readInt();
                        object6.j = class_abs2.b().readUTF();
                        object6.k = class_abs2.b().readUTF();
                        object6.p = class_abs2.b().readByte();
                        object6.g = class_abs2.b().readByte();
                        object6.q = class_abs2.b().readBoolean();
                        object6.m = class_abs2.b().readShort();
                        class_yi.f.addElement(object6);
                        ++n57;
                    }
                    return;
                }
                case 26: {
                    Object object7;
                    int n;
                    Object object8;
                    int n58 = class_abs2.b().readUnsignedByte();
                    class_yi.T = new class_ace[n58];
                    int n59 = class_abs2.b().readShort();
                    byte[] byArray = new byte[n59];
                    class_abs2.b().read(byArray);
                    class_yi.a((byte[])byArray);
                    try {
                        n59 = class_abs2.b().readUnsignedByte();
                        object8 = new byte[n59][];
                        n = 0;
                        while (n < n59) {
                            object8[n] = new byte[class_abs2.b().readShort()];
                            class_abs2.b().read((byte[])object8[n]);
                            ++n;
                        }
                        class_acc.a((byte[][])object8);
                    }
                    catch (Exception exception) {
                        object8 = exception;
                        exception.printStackTrace();
                    }
                    try {
                        n59 = (short)class_abs2.b().readUnsignedByte();
                        int n60 = 0;
                        while (n60 < n59) {
                            n = class_abs2.b().readShort();
                            byte[] byArray3 = new byte[class_abs2.b().readShort()];
                            class_abs2.b().read(byArray3);
                            object7 = new class_bk(byArray3, n);
                            class_az.X.put("" + n, object7);
                            ++n60;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        int n61 = class_abs2.b().readUnsignedByte();
                        n = 0;
                        while (n < n61) {
                            short s = class_abs2.b().readShort();
                            object7 = new byte[class_abs2.b().readShort()];
                            class_abs2.b().read((byte[])object7);
                            class_hm class_hm2 = new class_hm((int)s, object7);
                            class_gw.f.put(String.valueOf(s), class_hm2);
                            ++n;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        byte by = class_abs2.b().readByte();
                        class_hw.cp = new byte[by][];
                        class_hw.cq = new byte[by][];
                        n = 0;
                        while (n < by) {
                            class_hw.cp[n] = new byte[4];
                            class_hw.cq[n] = new byte[4];
                            int n62 = 0;
                            while (n62 < 4) {
                                class_hw.cp[n][n62] = class_abs2.b().readByte();
                                ++n62;
                            }
                            n62 = 0;
                            while (n62 < 4) {
                                class_hw.cq[n][n62] = class_abs2.b().readByte();
                                ++n62;
                            }
                            ++n;
                        }
                    }
                    catch (Exception exception) {
                        byte[][] byArrayArray = new byte[1][];
                        byte[] byArray4 = new byte[4];
                        byArray4[2] = 7;
                        byArray4[3] = -7;
                        byArrayArray[0] = byArray4;
                        class_hw.cp = byArrayArray;
                        class_hw.cq = new byte[][]{{-20, -15, -15, -15}};
                    }
                    try {
                        int n63 = 0;
                        while (n63 < 2) {
                            n = class_abs2.b().readByte();
                            class_hw.cr[n63] = new byte[n];
                            int n64 = 0;
                            while (n64 < n) {
                                class_hw.cr[n63][n64] = class_abs2.b().readByte();
                                ++n64;
                            }
                            ++n63;
                        }
                    }
                    catch (Exception exception) {
                        class_hw.cr = new byte[][]{new byte[1], new byte[1]};
                    }
                    try {
                        int n65 = 0;
                        while (n65 < 2) {
                            n = class_abs2.b().readByte();
                            class_hw.cs[n65] = new byte[n];
                            int n66 = 0;
                            while (n66 < n) {
                                class_hw.cs[n65][n66] = class_abs2.b().readByte();
                                ++n66;
                            }
                            ++n65;
                        }
                    }
                    catch (Exception exception) {
                        class_hw.cs = new byte[][]{new byte[1], new byte[1]};
                    }
                    try {
                        int n67 = 0;
                        while (n67 < 2) {
                            n = class_abs2.b().readByte();
                            class_hw.ct[n67] = new byte[n];
                            int n68 = 0;
                            while (n68 < n) {
                                class_hw.ct[n67][n68] = class_abs2.b().readByte();
                                ++n68;
                            }
                            ++n67;
                        }
                        return;
                    }
                    catch (Exception exception) {
                        class_hw.ct = new byte[][]{new byte[1], new byte[1]};
                        return;
                    }
                }
                case 100: {
                    class_abj.z(class_abs2);
                    return;
                }
                case 58: {
                    int n = class_abs2.b().readByte();
                    int[] nArray = new int[n];
                    int n69 = 0;
                    while (n69 < n) {
                        nArray[n69] = class_abs2.b().readShort();
                        ++n69;
                    }
                    return;
                }
                case 24: {
                    class_acv.g();
                    return;
                }
                case 27: {
                    short s = class_abs2.b().readShort();
                    String string = class_abs2.b().readUTF();
                    class_acv.s.a(s, string);
                    return;
                }
                case 28: {
                    short s = class_abs2.b().readShort();
                    class_acv.s.g(s);
                    return;
                }
                case 78: {
                    short s = class_abs2.b().readShort();
                    class_acv.s.h(s);
                    return;
                }
                case 61: {
                    short s = class_abs2.b().readShort();
                    short s22 = class_abs2.b().readShort();
                    class_du class_du2 = new class_du();
                    new class_du().a = class_abs2.b().readByte();
                    class_du2.b = class_abs2.b().readByte();
                    class_du2.c = class_abs2.b().readShort();
                    class_du2.d = class_abs2.b().readShort();
                    class_du2.e = class_abs2.b().readShort();
                    class_acv.s.a(s, s22, class_du2);
                    return;
                }
                case 30: {
                    short s = class_abs2.b().readShort();
                    short s23 = class_abs2.b().readShort();
                    int n = class_abs2.b().readInt();
                    class_acv.s.a(s, s23, n);
                    return;
                }
                case 32: {
                    short s = class_abs2.b().readShort();
                    int n = class_abs2.b().readByte();
                    class_zs[] class_zsArray = new class_zs[n];
                    int n70 = 0;
                    while (n70 < n) {
                        class_zsArray[n70] = new class_zs();
                        class_zsArray[n70].b = class_abs2.b().readUTF();
                        class_zsArray[n70].a = class_abs2.b().readInt();
                        ++n70;
                    }
                    if (s == class_acv.s.q.cG) {
                        class_acv.s.q.N = class_abs2.b().readByte();
                        class_acv.s.q.aR = class_abs2.b().readShort();
                    }
                    class_acv.s.a(s, class_zsArray);
                    return;
                }
                case 33: {
                    short s = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    class_acv.s.a(s, by, class_abs2.b().readInt(), class_abs2.b().readInt());
                    return;
                }
                case 35: {
                    int n;
                    class_qz.f = new short[1][15][11];
                    int n71 = 0;
                    while (n71 < 15) {
                        n = 0;
                        while (n < 11) {
                            class_qz.f[0][n71][n] = class_abs2.b().readShort();
                            ++n;
                        }
                        ++n71;
                    }
                    class_qz.h = new int[1][15][11];
                    n71 = 0;
                    while (n71 < 15) {
                        n = 0;
                        while (n < 11) {
                            class_qz.h[0][n71][n] = class_abs2.b().readShort() * 100;
                            ++n;
                        }
                        ++n71;
                    }
                    class_qz.j = new short[1][15];
                    n71 = 0;
                    while (n71 < 15) {
                        class_qz.j[0][n71] = class_abs2.b().readShort();
                        ++n71;
                    }
                    class_qz.i = new short[1][15][11];
                    n71 = 0;
                    while (n71 < 15) {
                        n = 0;
                        while (n < 11) {
                            class_qz.i[0][n71][n] = (short)class_abs2.b().readUnsignedByte();
                            ++n;
                        }
                        ++n71;
                    }
                    class_qz.g = new short[15][11];
                    n71 = 0;
                    while (n71 < 15) {
                        n = 0;
                        while (n < 11) {
                            class_qz.g[n71][n] = class_abs2.b().readShort();
                            ++n;
                        }
                        ++n71;
                    }
                    class_qz.b = new byte[15][11];
                    n71 = 0;
                    while (n71 < 15) {
                        n = 0;
                        while (n < 11) {
                            class_qz.b[n71][n] = class_abs2.b().readByte();
                            ++n;
                        }
                        ++n71;
                    }
                    class_qz.e = new byte[5][];
                    n71 = 0;
                    while (n71 < 5) {
                        class_qz.e[n71] = new byte[class_abs2.b().readByte()];
                        n = 0;
                        while (n < class_qz.e[n71].length) {
                            class_qz.e[n71][n] = class_abs2.b().readByte();
                            ++n;
                        }
                        ++n71;
                    }
                    return;
                }
                case 14: {
                    class_acv.a((String)"Xin ch\u1ecdn t\u00ean nh\u00e2n v\u1eadt kh\u00e1c");
                    return;
                }
                case 34: {
                    class_acv.g();
                    return;
                }
                case 36: {
                    class_acv.g();
                    return;
                }
                case 48: {
                    class_acv.s.i(class_abs2.b().readShort());
                    return;
                }
                case 49: {
                    class_acv.s.c(class_abs2);
                    return;
                }
                case 50: {
                    class_acv.s.d(class_abs2);
                    return;
                }
                case 38: {
                    String string = class_abs2.b().readUTF();
                    class_abj.C.addElement(string);
                    return;
                }
                case 37: {
                    String string = class_abs2.b().readUTF();
                    String string2 = class_abs2.b().readUTF();
                    if (string2.equals("")) {
                        class_acv.a((String)string);
                        return;
                    }
                    class_acv.s.aH = true;
                    class_fb class_fb2 = new class_fb((class_bi)((Object)object2), string2);
                    class_acv.x.a = false;
                    class_acv.x.a(string, new class_s("OK", (IAction)class_fb2), new class_s("", (IAction)class_fb2), new class_s("\u0110\u00f3ng", (IAction)new class_fd((class_bi)((Object)object2))));
                    class_acv.w = class_acv.x;
                    return;
                }
                case 39: {
                    String string = class_abs2.b().readUTF();
                    String cfr_ignored_5 = "UNAME " + string;
                    boolean bl = class_abs2.b().readBoolean();
                    System.out.println("UNAME1 " + bl);
                    String string3 = class_abs2.b().readUTF();
                    String string4 = class_abs2.b().readUTF();
                    System.out.println(String.valueOf(string4) + " NOI DUNG");
                    if (!bl) {
                        class_acv.a((String)"Xin ch\u1ecdn nick kh\u00e1c");
                        return;
                    }
                    GameMidlet.a((String)(String.valueOf(string3) + string), (String)("sms://" + string4), (IAction)new class_et((class_bi)((Object)object2), string), (IAction)new class_ex((class_bi)((Object)object2)));
                    return;
                }
                case 20: {
                    byte by = class_abs2.b().readByte();
                    short s = class_abs2.b().readShort();
                    class_acv.s.a(s, by);
                    return;
                }
                case 51: {
                    class_acv.s.e(class_abs2);
                    return;
                }
                case 52: {
                    class_abj.q();
                    return;
                }
                case 54: {
                    return;
                }
                case 56: {
                    return;
                }
                case 59: {
                    class_acv.s.d(class_abs2.b().readUTF());
                    return;
                }
                case 57: {
                    return;
                }
                case -5: {
                    int n = class_abs2.b().readByte();
                    int n72 = 0;
                    while (n72 < n) {
                        String string = class_abs2.b().readUTF();
                        String string5 = class_abs2.b().readUTF();
                        class_act.a().a(String.valueOf(string) + ": " + string5, string);
                        ++n72;
                    }
                    return;
                }
                case -53: {
                    String string = class_abs2.b().readUTF();
                    class_abj.a(class_abs2, 6, string.toUpperCase());
                    return;
                }
                case -7: {
                    String string = class_abs2.b().readUTF();
                    class_abj.a(class_abs2, 1, string.toUpperCase());
                    return;
                }
                case -8: {
                    byte by = class_abs2.b().readByte();
                    byte by29 = class_abs2.b().readByte();
                    byte[] byArray = null;
                    short s = class_abs2.b().readShort();
                    byArray = new byte[s];
                    class_abs2.b().read(byArray);
                    switch (by) {
                        case 1: {
                            class_ko.a((byte[])byArray);
                            return;
                        }
                        case 2: {
                            short s24 = class_abs2.b().readShort();
                            byte[] byArray5 = new byte[s24];
                            class_abs2.b().read(byArray5);
                            int n = class_abs2.b().readByte();
                            byte[][] byArrayArray = new byte[n][];
                            byte[][][] byArrayArray2 = new byte[n][][];
                            int n73 = 0;
                            while (n73 < n) {
                                short s25 = class_abs2.b().readShort();
                                byArrayArray[n73] = new byte[s25];
                                class_abs2.b().read(byArrayArray[n73]);
                                int n74 = class_abs2.b().readByte();
                                byArrayArray2[n73] = new byte[n74][];
                                int n75 = 0;
                                while (n75 < n74) {
                                    short s26 = class_abs2.b().readShort();
                                    byArrayArray2[n73][n75] = new byte[s26];
                                    class_abs2.b().read(byArrayArray2[n73][n75]);
                                    ++n75;
                                }
                                ++n73;
                            }
                            class_ko.a((byte)by29, (byte[])byArray, (byte[][])byArrayArray, (byte[][][])byArrayArray2);
                            return;
                        }
                        case 3: {
                            class_ko.a((byte)by29, (byte[])byArray);
                            return;
                        }
                        case 4: {
                            Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                            return;
                        }
                    }
                    return;
                }
                case -9: {
                    int n = class_abs2.b().readShort();
                    short[] sArray = new short[n];
                    int n76 = 0;
                    while (n76 < n) {
                        sArray[n76] = class_abs2.b().readShort();
                        ++n76;
                    }
                    String string = class_abs2.b().readUTF();
                    if (string.equals("")) {
                        class_acv.s.a(sArray);
                        return;
                    }
                    short[] sArray2 = sArray;
                    class_acv.b((String)string, (IAction)new class_en((class_bi)((Object)object2), sArray2));
                    return;
                }
                case -10: {
                    short s = class_abs2.b().readShort();
                    String string = class_abs2.b().readUTF();
                    class_acv.s.q.cH = s;
                    class_acv.s.q.ae = 0;
                    class_wc.a().f();
                    class_acv.a((String)string);
                    return;
                }
                case -11: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        short s = class_abs2.b().readShort();
                        String string = class_abs2.b().readUTF();
                        class_acv.a((String)string, (IAction)new class_eq((class_bi)((Object)object2), s), (IAction)new class_ej((class_bi)((Object)object2), s));
                        return;
                    }
                    String string = class_abs2.b().readUTF();
                    boolean bl = class_abs2.b().readBoolean();
                    if (bl) {
                        short s = class_abs2.b().readShort();
                        short s27 = class_abs2.b().readShort();
                        if (s == class_acv.s.q.cG) {
                            class_acv.s.q.cH = s;
                            class_wc.a().f();
                        } else {
                            class_hw class_hw3 = (class_hw)class_acv.s.b(s);
                            ((class_hw)class_acv.s.b(s)).cH = s27;
                        }
                    }
                    class_acv.a((String)string);
                    return;
                }
                case -12: {
                    class_zy class_zy2 = new class_zy();
                    new class_zy().a = class_abs2.b().readShort();
                    class_zy2.b = class_abs2.b().readUTF();
                    class_zy2.c = class_abs2.b().readUTF();
                    class_zy2.g = class_abs2.b().readByte();
                    class_zy2.h = class_abs2.b().readShort();
                    class_zy2.i = class_abs2.b().readLong();
                    class_zy2.l = class_abs2.b().readLong();
                    class_zy2.j = class_abs2.b().readLong();
                    class_zy2.d = class_abs2.b().readUTF();
                    class_zy2.e = class_abs2.b().readUTF();
                    class_zy2.k = class_abs2.b().readBoolean();
                    class_zy2.m = class_abs2.b().readByte();
                    if (class_zy2.k) {
                        class_zy2.f = class_abs2.b().readUTF();
                    }
                    class_abj.a(class_zy2);
                    return;
                }
                case -13: {
                    class_acv.s.q.cH = (short)-1;
                    class_acv.a((String)"B\u1ea1n b\u1ecb m\u1eddi kh\u1ecfi bang h\u1ed9i.");
                    return;
                }
                case -17: {
                    Vector<class_kk> vector = new Vector<class_kk>();
                    int n = class_abs2.b().readShort();
                    int n77 = 0;
                    while (n77 < n) {
                        class_kk class_kk2 = new class_kk();
                        new class_kk().b = class_abs2.b().readInt();
                        class_kk2.a = class_abs2.b().readUTF();
                        class_kk2.c = class_abs2.b().readUTF();
                        vector.addElement(class_kk2);
                        ++n77;
                    }
                    class_na.a().a(vector, 2, "TH\u00d4NG B\u00c1O");
                    class_na.a().d();
                    class_acv.g();
                    return;
                }
                case -18: {
                    String string = class_abs2.b().readUTF();
                    class_act.a().a(string, "Bang h\u1ed9i");
                    return;
                }
                case -19: {
                    byte by = class_abs2.b().readByte();
                    String string = class_abs2.b().readUTF();
                    Vector<class_zy> vector = new Vector<class_zy>();
                    if (by == 5) {
                        int n = class_abs2.b().readShort();
                        int n78 = 0;
                        while (n78 < n) {
                            class_zy class_zy3 = new class_zy();
                            new class_zy().a = class_abs2.b().readShort();
                            class_zy3.b = class_abs2.b().readUTF();
                            class_zy3.c = class_abs2.b().readUTF();
                            class_zy3.g = class_abs2.b().readByte();
                            class_zy3.h = class_abs2.b().readShort();
                            class_zy3.i = class_abs2.b().readLong();
                            class_zy3.m = class_abs2.b().readByte();
                            vector.addElement(class_zy3);
                            ++n78;
                        }
                        class_na.a().a(vector, (int)by, "TOP BANG H\u1ed8I");
                        class_na.a().d();
                        class_acv.g();
                        return;
                    }
                    if (by == 7 || by == 8) {
                        class_abj.a(class_abs2, (int)by, string);
                        return;
                    }
                    if (by == 4) {
                        class_abj.a(class_abs2, (int)by, "TOP CAO TH\u1ee6");
                        return;
                    }
                    class_abj.a(class_abs2, (int)by, "TOP \u0110\u1ea0I GIA");
                    return;
                }
                case -20: {
                    int n = class_abs2.b().readInt();
                    class_acv.a((String)("B\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c " + n + "xu t\u1eeb bang h\u1ed9i."), (IAction)new class_el((class_bi)((Object)object2), n));
                    return;
                }
                case -22: {
                    class_hw class_hw4 = new class_hw();
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        class_hw4.am = class_abs2.b().readUTF();
                        class_hw4.aJ = class_abs2.b().readByte();
                        class_hw4.D = 0;
                        class_hw4.N = class_abs2.b().readByte();
                        class_hw4.ax = 0;
                        int n = class_abs2.b().readByte();
                        Vector<class_ql> vector = new Vector<class_ql>();
                        int n79 = 0;
                        while (n79 < n) {
                            class_ql class_ql8 = new class_ql();
                            class_ql8.D = class_ql8.m = class_abs2.b().readByte();
                            class_hw4.aO = class_ql8.m;
                            class_ql8.r = class_abs2.b().readShort();
                            class_ql8.y = class_abs2.b().readByte();
                            class_ql8.s = class_abs2.b().readByte();
                            class_ql8.i = class_abs2.b().readShort();
                            class_ql8.K = class_abs2.b().readByte();
                            class_ql8.E = new short[5];
                            int n80 = 0;
                            while (n80 < 5) {
                                class_ql8.E[n80] = class_abs2.b().readShort();
                                ++n80;
                            }
                            class_ql8.n = class_abs2.b().readByte();
                            class_ql8.o = class_abs2.b().readByte();
                            class_ql8.p = class_abs2.b().readByte();
                            class_ql8.q = class_abs2.b().readByte();
                            class_ql8.C = class_abs2.b().readByte();
                            class_ql8.d = class_abs2.b().readUTF();
                            n80 = 0;
                            while (n80 < class_ql8.a.length) {
                                class_ql8.a[n80] = class_abs2.b().readByte();
                                ++n80;
                            }
                            n80 = 0;
                            while (n80 < class_ql8.c.length) {
                                class_ql8.c[n80] = class_abs2.b().readByte();
                                ++n80;
                            }
                            n80 = 0;
                            while (n80 < class_ql8.b.length) {
                                class_ql8.b[n80] = class_abs2.b().readByte();
                                ++n80;
                            }
                            vector.addElement(class_ql8);
                            class_yc class_yc3 = class_yi.b((int)class_ql8.r);
                            if (class_yc3.c >= 3 && class_yc3.c < 8) {
                                class_yc class_yc4 = class_yi.b((int)class_ql8.r);
                                class_go.a().a(2, (int)class_yc3.c, (int)class_yc3.d, class_yc4.o);
                                class_acv.h();
                            }
                            ++n79;
                        }
                        class_hw4.cH = class_abs2.b().readShort();
                        class_hw4.aP = class_abs2.b().readByte();
                        n79 = 0;
                        while (n79 < class_hw4.bW.length) {
                            class_hw4.bW[n79] = class_abs2.b().readShort();
                            ++n79;
                        }
                        class_hw4.ak = class_abs2.b().readByte();
                        if (class_hw4.ak != -1) {
                            class_hw4.an = class_abs2.b().readUTF();
                        }
                        class_hw4.a(vector);
                        if (class_acv.s.r != null) {
                            class_hw4.cG = class_acv.s.r.cG;
                        }
                        class_abj.b(class_hw4);
                        return;
                    }
                    Vector<class_ql> vector = new Vector<class_ql>();
                    int n = class_abs2.b().readByte();
                    if (n > -1) {
                        int n81;
                        int n82 = 0;
                        while (n82 < n) {
                            class_ql class_ql9 = new class_ql();
                            new class_ql().m = class_ql9.D = class_abs2.b().readByte();
                            class_ql9.i = class_abs2.b().readShort();
                            class_ql9.r = class_abs2.b().readShort();
                            class_ql9.s = class_abs2.b().readByte();
                            class_ql9.y = class_abs2.b().readByte();
                            class_ql9.v = class_abs2.b().readShort();
                            class_ql9.u = class_abs2.b().readShort();
                            class_ql9.K = class_abs2.b().readByte();
                            class_ql9.E = new short[5];
                            n81 = 0;
                            while (n81 < 5) {
                                class_ql9.E[n81] = class_abs2.b().readShort();
                                ++n81;
                            }
                            class_ql9.n = class_abs2.b().readByte();
                            class_ql9.o = class_abs2.b().readByte();
                            class_ql9.p = class_abs2.b().readByte();
                            class_ql9.q = class_abs2.b().readByte();
                            class_ql9.C = class_abs2.b().readByte();
                            class_ql9.d = class_abs2.b().readUTF();
                            n81 = 0;
                            while (n81 < class_ql9.a.length) {
                                class_ql9.a[n81] = class_abs2.b().readByte();
                                ++n81;
                            }
                            n81 = 0;
                            while (n81 < class_ql9.c.length) {
                                class_ql9.c[n81] = class_abs2.b().readByte();
                                ++n81;
                            }
                            n81 = 0;
                            while (n81 < class_ql9.b.length) {
                                class_ql9.b[n81] = class_abs2.b().readByte();
                                ++n81;
                            }
                            vector.addElement(class_ql9);
                            ++n82;
                        }
                        Image image = null;
                        byte by30 = class_abs2.b().readByte();
                        n81 = 0;
                        int n83 = 0;
                        byte by31 = 0;
                        byte[] byArray = new byte[class_abs2.b().available()];
                        while (class_abs2.b().available() > 0) {
                            class_abs2.b().read(byArray, 0, byArray.length);
                        }
                        if (byArray.length > 0) {
                            image = class_yi.b((byte[])byArray);
                            by31 = (byte)(by30 == 3 ? 3 : 6);
                            if (image != null) {
                                n81 = image.getWidth();
                                n83 = image.getHeight() / by30;
                            }
                        }
                        class_acv.s.a(class_acv.s.E, vector, image, by30, n81, n83, by31);
                        return;
                    }
                    class_acv.a((String)"Ch\u01b0a c\u00f3 th\u00f4ng tin linh th\u00fa");
                    return;
                }
                case 105: {
                    int n;
                    int n84;
                    byte by = class_abs2.b().readByte();
                    int n85 = class_abs2.b().readByte();
                    class_yi.ab = new short[n85][][];
                    class_yi.aa = new String[n85][][];
                    int n86 = 0;
                    while (n86 < n85) {
                        n84 = class_abs2.b().readByte();
                        class_yi.ab[n86] = new short[n84][];
                        class_yi.aa[n86] = new String[n84][];
                        n = 0;
                        while (n < n84) {
                            class_yi.ab[n86][n] = new short[by * 3];
                            class_yi.aa[n86][n] = new String[by];
                            int n87 = 0;
                            while (n87 < by * 3) {
                                class_yi.ab[n86][n][n87] = class_abs2.b().readShort();
                                if (n87 % 3 == 0) {
                                    class_yi.aa[n86][n][n87 / 3] = class_abs2.b().readUTF();
                                }
                                ++n87;
                            }
                            ++n;
                        }
                        ++n86;
                    }
                    n85 = class_abs2.b().readByte();
                    class_abj.aJ = new short[n85][];
                    class_abj.aI = new short[n85];
                    n86 = 0;
                    while (n86 < n85) {
                        class_abj.aJ[n86] = new short[by];
                        n84 = 0;
                        while (n84 < by) {
                            class_abj.aJ[n86][n84] = class_abs2.b().readShort();
                            ++n84;
                        }
                        class_abj.aI[n86] = class_abs2.b().readShort();
                        ++n86;
                    }
                    n86 = class_abs2.b().readByte();
                    class_yi.Y = new short[n86][by * 3];
                    class_yi.Z = new String[n86][by];
                    n84 = 0;
                    while (n84 < n86) {
                        n = 0;
                        while (n < by * 3) {
                            class_yi.Y[n84][n] = class_abs2.b().readShort();
                            if (n % 3 == 0) {
                                class_yi.Z[n84][n / 3] = class_abs2.b().readUTF();
                            }
                            ++n;
                        }
                        ++n84;
                    }
                    return;
                }
                case -24: {
                    byte by = class_abs2.b().readByte();
                    String string = class_abs2.b().readUTF();
                    if (by == 0) {
                        class_bs.a().a(string);
                    } else if (by == 1) break;
                }
                case -26: {
                    class_acv.s.g(class_abs2.b().readUTF());
                    return;
                }
                case -27: {
                    byte by = class_abs2.b().readByte();
                    byte by32 = class_abs2.b().readByte();
                    if (by32 != -1) {
                        int n = class_abs2.b().readShort();
                        byte[] byArray = new byte[n];
                        int n88 = 0;
                        while (n88 < n) {
                            byArray[n88] = class_abs2.b().readByte();
                            ++n88;
                        }
                        n88 = class_abs2.b().readShort();
                        byte[] byArray6 = new byte[n88];
                        int n89 = 0;
                        while (n89 < n88) {
                            byArray6[n89] = class_abs2.b().readByte();
                            ++n89;
                        }
                        Image image = class_yi.a((byte[])byArray, (byte[])byArray6);
                        byte by33 = class_abs2.b().readByte();
                        byte by34 = class_abs2.b().readByte();
                        if (by == 0) {
                            class_g.a().h.aX = image;
                            class_g.a().h.aZ = by33;
                            class_g.a().h.ba = by34;
                        } else if (by == 1) {
                            class_acv.s.q.bo = by32;
                            class_acv.s.q.aX = image;
                            class_acv.s.q.aZ = by33;
                            class_acv.s.q.ba = by34;
                        } else if (by == 2) {
                            if (class_nu.f() == 1) {
                                class_nu.O.aX = image;
                                class_nu.O.aZ = by33;
                                class_nu.O.ba = by34;
                            } else {
                                class_nu.a().aj = image;
                            }
                        }
                    }
                    class_acv.g();
                    return;
                }
                case -28: {
                    short s = class_abs2.b().readShort();
                    byte[] byArray = new byte[s];
                    class_abs2.b().read(byArray);
                    class_acv.s.c(byArray);
                    return;
                }
                case -29: {
                    return;
                }
                case -30: {
                    short s = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    int n = class_abs2.b().readByte();
                    String[] stringArray = new String[n];
                    int n90 = 0;
                    while (n90 < n) {
                        stringArray[n90] = class_abs2.b().readUTF();
                        ++n90;
                    }
                    class_acv.s.a((int)s, by, stringArray);
                    return;
                }
                case -31: {
                    short s = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    String string = class_abs2.b().readUTF();
                    byte by35 = class_abs2.b().readByte();
                    class_acv.s.a((int)s, by, string, (int)by35);
                    return;
                }
                case -32: {
                    short s = class_abs2.b().readShort();
                    byte by = class_abs2.b().readByte();
                    String string = class_abs2.b().readUTF();
                    class_acv.a((String)string, (IAction)new class_ei((class_bi)((Object)object2), (int)s, by, string), (IAction)new class_hz((class_bi)((Object)object2), (int)s, by, string));
                    return;
                }
                case -33: {
                    int n = class_abs2.b().readByte() << 4;
                    int n91 = class_abs2.b().readByte() << 4;
                    short s = class_abs2.b().readShort();
                    class_acv.s.y = new MoveObj(n, n91);
                    class_acv.s.y.c = s;
                    if (class_acv.s.y.c == class_acv.s.aG) {
                        class_abm.a.addElement(new class_dm(n, n91, s, 0, true));
                        class_abm.b.addElement(new class_dm(n, n91, s, 1, true));
                        return;
                    }
                    break;
                }
                case -37: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        String string = "`" + class_abs2.b().readUTF();
                        byte by36 = class_abs2.b().readByte();
                        if (by36 == 0) {
                            class_nu.G = class_abs2.b().readByte();
                            class_nu.M = class_abs2.b().readShort();
                            class_nu.L = class_abs2.b().readShort();
                            string = String.valueOf(string) + "\nHo\u00e0n th\u00e0nh: " + class_nu.L + "/" + class_nu.M + "Con";
                        } else if (by36 == 1) {
                            class_abj.L = class_abs2.b().readByte();
                            class_nu.M = class_abs2.b().readShort();
                            class_nu.L = class_abs2.b().readShort();
                            string = String.valueOf(string) + "\n\u0110\u00e3 l\u1ea5y: " + class_nu.L + "/" + class_nu.M;
                        } else if (by36 == 2) {
                            class_abj.M = new MoveObj();
                            new MoveObj().c = class_abs2.b().readByte();
                            int n = 0;
                            while (n < class_acv.s.l.size()) {
                                class_vh class_vh2 = (class_vh)class_acv.s.l.elementAt(n);
                                if (class_vh2 instanceof class_gn && ((class_gn)class_vh2).a == class_abj.M.c) {
                                    class_abj.M.posX = class_vh2.cK;
                                    class_abj.M.posY = class_vh2.cL;
                                    break;
                                }
                                ++n;
                            }
                        }
                        class_yi.b = class_abs2.b().readUTF();
                        class_nu.a().a(string);
                        return;
                    }
                    if (by == 1) {
                        String string = class_abs2.b().readUTF();
                        class_acv.s.f(string);
                        return;
                    }
                    if (by == 2) {
                        String string = class_abs2.b().readUTF();
                        class_yi.b = class_abs2.b().readUTF();
                        class_zt.a().a("NHI\u1ec6M V\u1ee4", string);
                        class_zt.a().d();
                        class_acv.g();
                        return;
                    }
                    break;
                }
                case -38: {
                    class_abj.v = class_abs2.b().readBoolean();
                    int n = class_abs2.b().readByte();
                    class_abj.ad = new int[n];
                    class_abj.ah = new String[n];
                    class_abj.ae = new int[n];
                    class_abj.af = new short[n];
                    class_abj.ai = new String[n];
                    int n92 = 0;
                    while (n92 < n) {
                        class_abj.ad[n92] = class_abs2.b().readShort();
                        short s = class_abs2.b().readShort();
                        class_abj.ah[n92] = class_abs2.b().readUTF();
                        class_abj.ae[n92] = (int)(System.currentTimeMillis() / 1000L);
                        class_hw class_hw5 = (class_hw)class_acv.s.b(s);
                        class_abj.af[n92] = s;
                        if (class_hw5 != null) {
                            class_hw5.cm = null;
                        }
                        if (class_hw5 != null && class_abj.ad[n92] > 0) {
                            class_hw5.cm = new class_dm((int)class_hw5.cK, class_hw5.cL - 5, 1, 1, false);
                        }
                        class_abj.ai[n92] = class_abs2.b().readUTF().toLowerCase();
                        ++n92;
                    }
                    return;
                }
                case -39: {
                    byte by = class_abs2.b().readByte();
                    short s = class_abs2.b().readShort();
                    int n = class_abs2.b().readInt();
                    class_abj.a(by, (int)s, n);
                    return;
                }
                case -42: {
                    short s = class_abs2.b().readShort();
                    byte by = (byte)(class_abs2.b().available() / 2);
                    byte[] byArray = new byte[by];
                    byte[] byArray7 = new byte[by];
                    int n = 0;
                    while (class_abs2.b().available() > 0) {
                        byArray[n] = class_abs2.b().readByte();
                        byArray7[n] = class_abs2.b().readByte();
                        ++n;
                    }
                    class_acv.s.a(s, byArray, byArray7);
                    return;
                }
                case -43: {
                    int n = class_abs2.b().readByte();
                    if (n <= 0) break;
                    Image[] imageArray = new Image[n];
                    int n93 = 0;
                    while (n93 < n) {
                        try {
                            byte[] byArray = new byte[class_abs2.b().readInt()];
                            class_abs2.b().read(byArray, 0, byArray.length);
                            imageArray[n93] = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ++n93;
                    }
                    n93 = class_abs2.b().readByte();
                    int n94 = 0;
                    while (n94 < n93) {
                        class_hn class_hn2 = new class_hn();
                        byte by = class_abs2.b().readByte();
                        short s = class_abs2.b().readByte();
                        short s28 = class_abs2.b().readByte();
                        class_hn2.b = class_abs2.b().readByte();
                        class_hn2.c = class_abs2.b().readByte();
                        class_hn2.d = class_abs2.b().readByte();
                        s = (short)(s << 4);
                        s28 = (short)(s28 << 4);
                        class_hn2.a(s, s28);
                        class_hn2.a = imageArray[by];
                        class_hn2.cM = (short)(class_hn2.a.getHeight() / class_hn2.d);
                        class_acv.s.a(class_hn2);
                        ++n94;
                    }
                    return;
                }
                case -46: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        class_abs2.b().readUTF();
                        Object var177_433 = null;
                        class_abs2.b().readByte();
                        return;
                    }
                    class_abs2.b().readByte();
                    boolean bl = false;
                    class_abs2.b().readByte();
                    byte[] byArray = new byte[class_abs2.b().available()];
                    class_abs2.b().read(byArray);
                    return;
                }
                case -47: {
                    short s = class_abs2.b().readShort();
                    class_abs2.b().readBoolean();
                    short s29 = class_abs2.b().readShort();
                    byte[] byArray = new byte[s29];
                    class_abs2.b().read(byArray);
                    if (class_yi.T[s] != null) {
                        class_yi.T[s].a(byArray);
                        return;
                    }
                    break;
                }
                case -48: {
                    byte by = class_abs2.b().readByte();
                    if (by != -1) {
                        short s = class_abs2.b().readShort();
                        byte[] byArray = new byte[s];
                        class_abs2.b().read(byArray);
                        class_vp class_vp2 = class_yi.h((int)by);
                        if (class_vp2 != null) {
                            class_vp2.a(byArray, (int)by);
                            return;
                        }
                        break;
                    }
                    byte by37 = class_abs2.b().readByte();
                    byte by38 = class_abs2.b().readByte();
                    byte[] byArray = new byte[class_abs2.b().available()];
                    int n = 0;
                    while (n < byArray.length) {
                        byArray[n] = class_abs2.b().readByte();
                        ++n;
                    }
                    class_abj.a(byArray, by37, by38);
                    return;
                }
                case -49: {
                    byte by = class_abs2.b().readByte();
                    byte by39 = class_abs2.b().readByte();
                    if (by == 0) {
                        class_abt class_abt2 = class_abk.a((short)by39);
                        if (class_abt2 == null) {
                            class_go.a().i((short)by39);
                        }
                        class_abk class_abk2 = new class_abk();
                        new class_abk().a = by39;
                        class_abk2.i = class_abs2.b().readByte();
                        byte by40 = class_abs2.b().readByte();
                        class_abk2.g = by40;
                        class_abk2.c = by40;
                        class_abk2.b = class_abs2.b().readShort();
                        class_abk2.j = class_abs2.b().readByte();
                        if (class_abk2.j == 1) {
                            class_abk2.d = class_abs2.b().readShort();
                        } else if (class_abk2.j == 2) {
                            int n = class_abs2.b().readByte();
                            class_abk2.k = new short[n];
                            class_abk2.l = new short[n];
                            int n95 = 0;
                            while (n95 < n) {
                                class_abk2.k[n95] = class_abs2.b().readShort();
                                class_abk2.l[n95] = class_abs2.b().readShort();
                                ++n95;
                            }
                        }
                        if (class_abk2.i == 0) {
                            class_abk2.h = class_abs2.b().readShort();
                        } else {
                            class_abk2.e = class_abs2.b().readShort();
                            class_abk2.f = class_abs2.b().readShort();
                        }
                        class_abj.a(class_abk2);
                        return;
                    }
                    class_abt class_abt3 = new class_abt();
                    new class_abt().e = by39;
                    class_abt3.a(class_abs2);
                    class_abk.m.addElement(class_abt3);
                    return;
                }
                case -50: {
                    class_acv.s.a(class_abs2.b().readUTF(), class_abs2.b().readShort(), class_abs2.b().readShort(), class_abs2.b().readShort(), class_abs2.b().readShort(), class_abs2.b().readShort(), class_abs2.b().readShort(), class_abs2.b().readByte(), class_abs2.b().readByte());
                    return;
                }
                case -51: {
                    short s = class_abs2.b().readShort();
                    byte[] byArray = new byte[class_abs2.b().available()];
                    class_abs2.b().read(byArray);
                    class_dh class_dh2 = (class_dh)class_ko.c.get("" + s);
                    ((class_dh)class_ko.c.get("" + s)).c = false;
                    class_dh2.a = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
                    return;
                }
                case -52: {
                    String string = class_abs2.b().readUTF();
                    short s = class_abs2.b().readShort();
                    int n = class_abs2.b().readByte();
                    Vector<class_kq> vector = new Vector<class_kq>();
                    int n96 = 0;
                    while (n96 < n) {
                        class_kq class_kq2 = new class_kq();
                        new class_kq().a = class_abs2.b().readUTF();
                        class_kq2.b = class_abs2.b().readShort();
                        class_kq2.c = class_abs2.b().readByte();
                        vector.addElement(class_kq2);
                        ++n96;
                    }
                    n96 = class_abs2.b().readByte();
                    byte by = 0;
                    try {
                        by = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    class_acv.s.a(string, s, vector, n96, (int)by);
                    return;
                }
                case -55: {
                    class_acv.s.o(class_abs2);
                    return;
                }
                case -56: {
                    int n = class_abs2.b().readByte();
                    class_f[] class_fArray = new class_f[n];
                    int n97 = 0;
                    while (n97 < n) {
                        class_fArray[n97] = new class_f();
                        class_fArray[n97].a = class_abs2.b().readShort();
                        class_fArray[n97].d = class_abs2.b().readByte();
                        class_fArray[n97].c = class_abs2.b().readUTF();
                        class_fArray[n97].e = class_abs2.b().readByte();
                        ++n97;
                    }
                    n97 = class_abs2.b().readByte();
                    class_f[] class_fArray2 = new class_f[n97];
                    int n98 = 0;
                    while (n98 < n97) {
                        class_fArray2[n98] = new class_f();
                        class_fArray2[n98].a = class_abs2.b().readShort();
                        class_fArray2[n98].d = class_abs2.b().readByte();
                        class_fArray2[n98].c = class_abs2.b().readUTF();
                        class_fArray2[n98].e = class_abs2.b().readByte();
                        ++n98;
                    }
                    class_acv.s.a(class_fArray, class_fArray2);
                    return;
                }
                case -57: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        boolean bl = class_abs2.b().readBoolean();
                        short s = class_abs2.b().readShort();
                        byte by41 = class_abs2.b().readByte();
                        if (class_acv.s.q.cG == s) {
                            class_acv.s.q.bT = bl;
                            if (bl) {
                                class_acv.s.q.bU = new class_xe();
                                class_acv.s.q.bU.i = by41;
                                class_acv.s.q.bS = new class_dm((int)class_acv.s.q.cK, class_acv.s.q.cL - 5, 1, 1, false);
                                class_acv.s.q.ci = System.currentTimeMillis() / 1000L + 5L;
                            } else if (class_acv.s.q.bU != null) {
                                class_acv.s.q.bU = null;
                            }
                        } else {
                            class_hw class_hw6 = (class_hw)class_acv.s.b(s);
                            if (class_hw6 != null) {
                                class_hw6.bT = bl;
                                if (bl) {
                                    class_hw6.bU = new class_xe();
                                    class_hw6.bU.i = by41;
                                    class_hw6.bS = new class_dm((int)class_hw6.cK, class_hw6.cL - 5, 1, 1, false);
                                    class_hw6.ci = System.currentTimeMillis() / 1000L + 5L;
                                } else if (class_hw6.bU != null) {
                                    class_hw6.bU = null;
                                }
                            }
                        }
                        if (class_xe.d[by41] == null) {
                            class_acv.s.D.m((int)by41, (int)s);
                            return;
                        }
                        break;
                    }
                    byte by42 = class_abs2.b().readByte();
                    short s = class_abs2.b().readShort();
                    byte[] byArray = new byte[s];
                    class_abs2.b().read(byArray, 0, s);
                    class_xe.f[by42] = class_abs2.b().readByte();
                    class_xe.d[by42] = class_yi.b((byte[])byArray);
                    class_xe.e[by42] = class_xe.d[by42].getWidth();
                    byte by43 = class_abs2.b().readByte();
                    class_xe.a[by42] = new byte[4][by43];
                    int n = 0;
                    while (n < class_xe.a[by42][0].length) {
                        class_xe.a[by42][0][n] = class_abs2.b().readByte();
                        class_xe.a[by42][1][n] = class_abs2.b().readByte();
                        class_xe.a[by42][2][n] = class_abs2.b().readByte();
                        class_xe.a[by42][3][n] = class_abs2.b().readByte();
                        ++n;
                    }
                    n = class_abs2.b().readByte();
                    class_xe.b[by42] = new byte[4][n];
                    int n99 = 0;
                    while (n99 < class_xe.b[by42][0].length) {
                        class_xe.b[by42][0][n99] = class_abs2.b().readByte();
                        class_xe.b[by42][1][n99] = class_abs2.b().readByte();
                        class_xe.b[by42][2][n99] = class_abs2.b().readByte();
                        class_xe.b[by42][3][n99] = class_abs2.b().readByte();
                        ++n99;
                    }
                    n99 = class_abs2.b().readByte();
                    class_xe.c[by42] = new byte[4][n99];
                    int n100 = 0;
                    while (n100 < class_xe.c[by42][0].length) {
                        class_xe.c[by42][0][n100] = class_abs2.b().readByte();
                        class_xe.c[by42][1][n100] = class_abs2.b().readByte();
                        class_xe.c[by42][2][n100] = class_abs2.b().readByte();
                        class_xe.c[by42][3][n100] = class_abs2.b().readByte();
                        ++n100;
                    }
                    class_xe.g[by42] = class_abs2.b().readByte();
                    class_xe.h[by42] = class_abs2.b().readByte();
                    return;
                }
                case -59: {
                    byte by = class_abs2.b().readByte();
                    byte by44 = 0;
                    while (by44 < by) {
                        byte by45 = class_abs2.b().readByte();
                        byte by46 = class_abs2.b().readByte();
                        String string = class_abs2.b().readUTF();
                        byte by47 = class_abs2.b().readByte();
                        int n = class_abs2.b().readInt();
                        byte by48 = class_abs2.b().readByte();
                        byte by49 = class_abs2.b().readByte();
                        boolean bl = class_abs2.b().readBoolean();
                        byte by50 = class_abs2.b().readByte();
                        byte by51 = class_abs2.b().readByte();
                        class_acv.s.a((int)by45, (int)by46, by48 << 4, by49 << 4, string, string, (int)by47, n, bl, by50, by51);
                        by44 = (byte)(by44 + 1);
                    }
                    class_acv.s.aK = (short)class_bi.a(class_acv.s.l, (byte)0, 5000);
                    class_acv.s.aL = (short)class_bi.a(class_acv.s.l, (byte)1, 5000);
                    class_acv.s.aM = (short)class_bi.a(class_acv.s.l, (byte)2, -1);
                    class_acv.s.aN = (short)class_bi.a(class_acv.s.l, (byte)3, -1);
                    return;
                }
                case -60: {
                    class_abj.a(class_abs2.b().readUTF());
                    return;
                }
                case -62: {
                    byte by = class_abs2.b().readByte();
                    if (by == 0) {
                        class_am.a().d();
                        class_am.a().a(class_abs2);
                        return;
                    }
                    if (by == 1) {
                        byte by52 = class_abs2.b().readByte();
                        String string = class_abs2.b().readUTF();
                        byte by53 = class_abs2.b().readByte();
                        String[] stringArray = new String[by53];
                        byte by54 = 0;
                        while (by54 < by53) {
                            stringArray[by54] = class_abs2.b().readUTF();
                            by54 = (byte)(by54 + 1);
                        }
                        class_am.a().a(by52, string, stringArray);
                        return;
                    }
                    if (by == 2) {
                        class_acv.a((String)"B\u1ea1n \u0111\u00e3 h\u1ebft l\u01b0\u1ee3t ch\u01a1i");
                        class_am.a().f();
                        class_acv.s.d();
                        return;
                    }
                    break;
                }
                case -63: {
                    class_aaa.a().a(class_abs2);
                    class_aaa.a().d();
                    return;
                }
                case -61: {
                    byte by = class_abs2.b().readByte();
                    byte by55 = class_abs2.b().readByte();
                    byte by56 = class_abs2.b().readByte();
                    short s = class_abs2.b().readShort();
                    byte by57 = class_abs2.b().readByte();
                    byte by58 = 0;
                    try {
                        by58 = class_abs2.b().readByte();
                    }
                    catch (Exception exception) {}
                    class_acv.s.a((short)1000, by, by55, s, by56, by57, by58);
                    return;
                }
                case 87: {
                    class_acv.s.p(class_abs2);
                    return;
                }
                case -75: {
                    class_acv.s.r(class_abs2);
                    return;
                }
                case -76: {
                    class_abj.q(class_abs2);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {
            Exception exception5 = exception;
            exception.printStackTrace();
            String cfr_ignored_6 = String.valueOf(class_abs2.a) + " tai cmd nay";
            String cfr_ignored_7 = String.valueOf(class_abs2.a) + " tai cmd nay";
        }
    }

    private static int a(Vector vector, byte by, int n) {
        int n2 = 0;
        while (n2 < vector.size()) {
            class_vh class_vh2 = (class_vh)vector.elementAt(n2);
            if (class_vh2.cF == 10) {
                class_vh2 = (class_vo)class_vh2;
                switch (by) {
                    case 0: {
                        if (n <= class_vh2.cK) break;
                        n = class_vh2.cK;
                        break;
                    }
                    case 1: {
                        if (n <= class_vh2.cL) break;
                        n = class_vh2.cL;
                        break;
                    }
                    case 2: {
                        if (n >= class_vh2.cK) break;
                        n = class_vh2.cK;
                        break;
                    }
                    case 3: {
                        if (n >= class_vh2.cL) break;
                        n = class_vh2.cL;
                    }
                }
            }
            ++n2;
        }
        return n;
    }

    private static int a(int n) {
        if (n >= 79 && n <= 113) {
            return (n - 79) / 7;
        }
        if (n >= 174 && n <= 213) {
            return (n - 174) / 8;
        }
        if (n >= 214 && n <= 263) {
            return (n - 214) / 10;
        }
        return 0;
    }
}
