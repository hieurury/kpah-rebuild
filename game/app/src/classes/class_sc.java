/*
 * Decompiled with CFR 0.152.
 */
package classes;

import classes.class_aai;
import classes.class_abj;
import classes.class_acv;
import classes.class_gd;
import classes.class_go;
import classes.class_hw;
import classes.class_ls;
import classes.class_nu;
import classes.class_qz;
import classes.class_ub;
import classes.class_xz;
import classes.class_yg;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public final class class_sc
extends class_hw {
    public static class_gd[][] a;
    public boolean b;
    public boolean c;
    public boolean d;
    private boolean dk;
    private boolean dl;
    public byte e;
    public static Vector f;
    public static Vector g;
    public static Vector h;
    public static Vector i;
    public Vector j = new Vector();
    public Vector k = new Vector();
    public static class_ub[] l;
    public static short[] m;
    public boolean n = false;
    public short o;
    public short p;
    public short q;
    public int r = 0;
    public short[] s;
    public short X;
    public int cA;
    public int cB;
    public String cC = "";
    public boolean cD = false;

    static {
        f = new Vector();
        g = new Vector();
        h = new Vector();
        i = new Vector();
    }

    public final void P() {
        if (a != null) {
            return;
        }
        a = new class_gd[2][5];
        int n = 0;
        while (n < 5) {
            class_sc.a[0][n] = new class_gd();
            class_sc.a[1][n] = new class_gd();
            ++n;
        }
        if (class_hw.aS[1] > 0) {
            a[0][0].a(1, false);
        }
        if (class_hw.aS[2] > 0) {
            a[0][1].a(2, false);
        }
        a[0][2].a(0, false);
        a[0][3].a(1);
        a[0][4].a(4);
        a[1][3].a(1);
        a[1][4].a(4);
        Object object = class_aai.a("nqshQuickSlot");
        if (object != null) {
            object = new ByteArrayInputStream((byte[])object);
            object = new DataInputStream((InputStream)object);
            try {
                String string = ((DataInputStream)object).readUTF();
                if (!string.equals(class_acv.s.q.am)) {
                    return;
                }
                int n2 = 0;
                while (n2 < a.length) {
                    int n3 = 0;
                    while (n3 < a[n2].length) {
                        class_gd class_gd2 = a[n2][n3];
                        a[n2][n3].a = ((DataInputStream)object).readUnsignedByte();
                        byte by = ((DataInputStream)object).readByte();
                        class_gd2.b = ((DataInputStream)object).readBoolean();
                        if (class_gd2.a == 2) {
                            class_gd2.a(by);
                        } else {
                            class_gd2.a(by, class_gd2.b);
                        }
                        class_abj.V = ((DataInputStream)object).readByte();
                        ++n3;
                    }
                    ++n2;
                }
                ((FilterInputStream)object).close();
                return;
            }
            catch (IOException iOException) {}
        }
    }

    public final String Q() {
        return String.valueOf(this.aR / 10) + "." + this.aR % 10 + "%";
    }

    public static void a(class_xz class_xz2) {
        int n = 0;
        while (n < class_hw.bw.size()) {
            if (((class_xz)class_hw.bw.elementAt((int)n)).b.equals(class_xz2.b)) {
                return;
            }
            ++n;
        }
        class_hw.bw.addElement(class_xz2);
    }

    public static String b(short s) {
        String string = null;
        int n = 0;
        while (n < class_hw.bw.size()) {
            if (((class_xz)class_hw.bw.elementAt((int)n)).a == s) {
                string = ((class_xz)class_hw.bw.elementAt((int)n)).b;
                class_hw.bw.removeElementAt(n);
                return string;
            }
            ++n;
        }
        return "";
    }

    public final void a() {
        int n;
        int n2;
        class_sc class_sc2;
        super.a();
        if (!this.S && this.s != null) {
            class_sc2 = this;
            if (class_yg.d(class_sc2.cK - class_sc2.av) <= 3 && class_yg.d(class_sc2.cL - class_sc2.aw) <= 3) {
                n2 = class_sc2.s.length - 1 - class_sc2.r;
                while (n2 >= 0) {
                    if (class_sc2.s[n2] > 0) {
                        n = (byte)(class_abj.aR + (class_sc2.s[n2] >> 8));
                        byte by = (byte)(class_abj.aS + (class_sc2.s[n2] & 0xFF));
                        if (class_abj.X != 2 && (class_sc2.D == 1 || class_ls.m) && class_acv.s.b((n << 4) + class_sc2.I, (by << 4) + class_sc2.I, (int)class_sc2.D)) {
                            class_sc2.s = null;
                            class_sc2.r = 0;
                            break;
                        }
                        if (class_acv.s.a((n << 4) + class_sc2.I, (by << 4) + class_sc2.I)) {
                            class_sc2.s = null;
                            break;
                        }
                        class_sc2.b((short)((n << 4) + class_sc2.I), (short)((by << 4) + class_sc2.I));
                        class_acv.s.b((int)((short)((n << 4) + class_sc2.I)), (int)((short)((by << 4) + class_sc2.I)));
                        class_sc2.s[n2] = -1;
                        ++class_sc2.r;
                        break;
                    }
                    if (n2 == 0) {
                        class_sc2.s = null;
                        class_sc2.r = 0;
                        break;
                    }
                    --n2;
                }
            }
            this.dk = false;
        }
        if (!this.cD && System.currentTimeMillis() - class_abj.at >= 0L) {
            class_abj.at = System.currentTimeMillis() + (long)class_abj.as;
            this.cD = true;
            class_go.a().h(this.cK, this.cL);
        }
        class_sc2 = this;
        if (class_acv.a.hasPointerEvents() && class_abj.X != 2) {
            if (class_sc2.dl && !class_sc2.dk && class_sc2.cV == 1) {
                n2 = 0;
                n = 0;
                if (class_sc2.D == 2) {
                    n2 = -32;
                } else if (class_sc2.D == 3) {
                    n2 = 32;
                }
                if (class_sc2.D == 0) {
                    n = 32;
                } else if (class_sc2.D == 1) {
                    n = -32;
                }
                if ((Math.abs(class_sc2.cK - class_sc2.p) >= 16 || Math.abs(class_sc2.cL - class_sc2.q) >= 16) && class_acv.s.b(class_sc2.cK + n2, class_sc2.cL + n, (int)class_sc2.D)) {
                    class_sc2.s = null;
                    class_sc2.dk = true;
                    return;
                }
            }
            if (class_sc2.cV == 1) {
                class_sc2.dl = true;
                return;
            }
            class_sc2.dl = false;
        }
    }

    public final String[] j(int n) {
        String[] custom = formatSkillDescription(this.aO, n);
        if (custom != null) {
            return custom;
        }

        String[] stringArray = null;
        if (class_hw.aS[n] == -1) {
            String[] stringArray2 = new String[2];
            stringArray = stringArray2;
            stringArray2[0] = "Chưa học kỹ năng này";
            stringArray2[1] = "Lv yêu cầu: " + (class_qz.b != null && n < class_qz.b.length && class_qz.b[n] != null && class_qz.b[n].length > 1 ? class_qz.b[n][1] : 1);
        } else if (class_hw.aS[n] == 0) {
            String[] stringArray3 = new String[2];
            stringArray = stringArray3;
            stringArray3[0] = "Chưa học kỹ năng này";
            stringArray3[1] = class_sc.n(n);
        } else if (!class_nu.a().m()) {
            String[] stringArray4 = new String[5];
            stringArray = stringArray4;
            stringArray4[0] = "Thời gian đánh: " + class_qz.a((byte)n, (int)class_hw.aS[n]) + " ms";
            stringArray4[1] = String.valueOf(class_qz.a[this.aO][n]) + class_qz.a(n, (int)class_hw.aS[n]) + "%";
            stringArray4[2] = "Phạm vi: " + class_qz.a((byte)n);
            stringArray4[3] = "MP mất: " + class_qz.b(n, class_hw.aS[n]);
            stringArray4[4] = class_hw.aS[n] < 9 ? "Lv yêu cầu: " + class_qz.b[n][class_hw.aS[n]] : "";
        } else {
            byte[][] byArrayArray = new byte[5][];
            byArrayArray[0] = new byte[]{1, 5};
            byArrayArray[1] = new byte[]{1, 2};
            byArrayArray[2] = new byte[]{3, 4, 5, 6};
            byte[] byArray = new byte[2];
            byArray[1] = 1;
            byArrayArray[3] = byArray;
            byte[] byArray2 = new byte[2];
            byArray2[1] = 3;
            byArrayArray[4] = byArray2;
            byte[][] byArrayArray2 = byArrayArray;
            String string = "MP mất: " + class_qz.b(n, class_hw.aS[n]);
            switch (this.aO) {
                case 1: {
                    if (n == 5) {
                        String[] stringArray7 = new String[4];
                        stringArray = stringArray7;
                        stringArray7[0] = "Tăng sức tấn công";
                        stringArray7[1] = "của bản thân";
                        stringArray7[2] = "Tỷ lệ tăng: " + class_qz.a(n, (int)class_hw.aS[n]) + "%";
                        stringArray7[3] = class_hw.aS[n] < 9 ? "Lv yêu cầu: " + class_qz.b[n][class_hw.aS[n]] : "";
                        break;
                    }
                    if (n != 4) break;
                    String[] stringArray8 = new String[4];
                    stringArray = stringArray8;
                    stringArray8[0] = "Phòng thủ tăng: " + class_qz.a(n, (int)class_hw.aS[n]) + "%";
                    stringArray8[1] = "Thời gian: " + class_qz.g[byArrayArray2[this.aO][n - 4]][class_hw.aS[n]] + "s";
                    stringArray8[2] = string;
                    stringArray8[3] = class_hw.aS[n] < 9 ? "Lv yêu cầu: " + class_qz.b[n][class_hw.aS[n]] : "";
                    break;
                }
                case 3: {
                    if (n == 5) {
                        String[] stringArray13 = new String[4];
                        stringArray = stringArray13;
                        stringArray13[0] = "Tăng sức phòng thủ";
                        stringArray13[1] = "của bản thân";
                        stringArray13[2] = "Tỷ lệ tăng: " + class_qz.a(n, (int)class_hw.aS[n]) + "%";
                        stringArray13[3] = class_sc.n(n);
                        break;
                    }
                    if (n != 4) break;
                    String[] stringArray14 = new String[6];
                    stringArray = stringArray14;
                    stringArray14[0] = "Gây choáng cho";
                    stringArray14[1] = "đối phương";
                    stringArray14[2] = "Thời gian: 5s";
                    stringArray14[3] = "Tỷ lệ gây choáng: " + class_qz.a(n, (int)class_hw.aS[n]) + "%";
                    stringArray14[4] = string;
                    stringArray14[5] = class_sc.n(n);
                    break;
                }
                case 4: {
                    if (n == 5) {
                        String[] stringArray15 = new String[3];
                        stringArray = stringArray15;
                        stringArray15[0] = "Tăng độc tính sử dụng";
                        stringArray15[1] = "Tỷ lệ tăng: " + class_qz.a(n, (int)class_hw.aS[n]) + "%";
                        stringArray15[2] = class_sc.n(n);
                        break;
                    }
                    if (n != 4) break;
                    String[] stringArray16 = new String[5];
                    stringArray = stringArray16;
                    stringArray16[0] = "Tẩm độc vào tên: ";
                    stringArray16[1] = "Độc tính: " + class_qz.a(n, (int)class_hw.aS[n]);
                    stringArray16[2] = "Thời gian: " + class_qz.a(n, (int)class_hw.aS[n]) + "s";
                    stringArray16[3] = string;
                    stringArray16[4] = class_sc.n(n);
                }
            }
        }
        return stringArray;
    }

    private String[] formatSkillDescription(int classChar, int n) {
        int rawLv = class_hw.aS[n];
        int lv = rawLv <= 0 ? 1 : rawLv;
        Vector v = new Vector();

        if (classChar == 0) { // Kiếm Khách
            switch (n) {
                case 0:
                    v.addElement("Chém kiếm cơ bản");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 0.7s");
                    break;
                case 1:
                    v.addElement("Vận khí chém đơn thể");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: " + (class_qz.a((byte)n, lv) / 1000.0f) + "s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 2:
                    v.addElement("Kiếm lôi chém đơn thể");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: " + (class_qz.a((byte)n, lv) / 1000.0f) + "s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 3:
                    v.addElement("Kiếm ảnh lôi trảm");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: " + (class_qz.a((byte)n, lv) / 1000.0f) + "s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 4:
                    v.addElement("Tăng xuyên giáp đòn đánh");
                    v.addElement("Xuyên giáp: +" + class_qz.a(n, lv));
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    v.addElement("Bị động (Không hồi chiêu)");
                    break;
                case 5:
                    v.addElement("Vận kiếm khí phản đòn");
                    v.addElement("Tỷ lệ phản: " + (10 + (lv - 1) * 5) + "%");
                    v.addElement("Phản công: " + (50 + (lv - 1) * 10) + "%");
                    v.addElement("Hồi chiêu: 90s | MP: " + class_qz.b(n, lv));
                    break;
                case 6:
                    v.addElement("Gọi sấm sét diện rộng");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 5s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 7:
                    v.addElement("Sét chuyền nhiều kẻ địch");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 6s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 8:
                    v.addElement("Đại kiếm trảm diện rộng");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 7s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                default:
                    return null;
            }
        } else if (classChar == 2) { // Pháp Sư
            switch (n) {
                case 0:
                    v.addElement("Chưởng phép cơ bản");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 0.7s");
                    break;
                case 1:
                    v.addElement("Thủy tiễn đơn mục tiêu");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: " + (class_qz.a((byte)n, lv) / 1000.0f) + "s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 2:
                    v.addElement("Thủy long đơn mục tiêu");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: " + (class_qz.a((byte)n, lv) / 1000.0f) + "s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 3:
                    v.addElement("Hải long trảm cực mạnh");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: " + (class_qz.a((byte)n, lv) / 1000.0f) + "s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 4:
                    v.addElement("Tăng Max HP/MP: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 120s | MP: " + class_qz.b(n, lv));
                    v.addElement("Duy trì: 90s");
                    break;
                case 5:
                    v.addElement("Tăng công theo MP có");
                    v.addElement("Tăng thêm: " + (5 + (lv - 1) * 2) + "% MP");
                    v.addElement("Bị động (Không tốn MP)");
                    break;
                case 6:
                    v.addElement("Rút HP/MP cứu đồng đội");
                    v.addElement("Rút tối đa: 80% HP/MP");
                    v.addElement("Hồi chiêu: " + (180 - (lv - 1) * 10) + "s");
                    break;
                case 7:
                    v.addElement("Khiên hộ thể hấp thu");
                    v.addElement("Hồi MP nhận đòn: " + (10 + (lv - 1) * 5) + "%");
                    v.addElement("Hồi Máu tốn MP: " + (20 + (lv - 1) * 5) + "%");
                    v.addElement("Hồi chiêu: 60s | MP: " + class_qz.b(n, lv));
                    break;
                case 8:
                    v.addElement("Sóng rồng đánh diện rộng");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 4s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 9:
                    v.addElement("Song long xoáy diện rộng");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 5s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                case 10:
                    v.addElement("Bão tuyết trút thương băng");
                    v.addElement("Lực công: " + class_qz.a(n, lv) + "%");
                    v.addElement("Hồi chiêu: 6s");
                    v.addElement("Mana tiêu hao: " + class_qz.b(n, lv));
                    break;
                default:
                    return null;
            }
        } else {
            return null;
        }

        if (rawLv < 9) {
            int reqLv = 0;
            if (class_qz.b != null && n < class_qz.b.length && class_qz.b[n] != null) {
                int col = (rawLv <= 0) ? 1 : rawLv;
                if (col < class_qz.b[n].length) {
                    reqLv = class_qz.b[n][col];
                }
            }
            if (reqLv > 0) {
                v.addElement("Cấp độ yêu cầu: " + reqLv);
            }
        }

        if (rawLv <= 0) {
            v.insertElementAt("Tình trạng: Chưa học", 0);
        }

        String[] res = new String[v.size()];
        v.copyInto(res);
        return res;
    }

    private static String n(int n) {
        return "Lv y\u00eau c\u1ea7u: " + class_qz.b[n][class_hw.aS[n]];
    }

    public final boolean c(int n, int n2) {
        short s = this.cK;
        if (n != 0) {
            if (!class_ls.a(this.cK + n, this.cL - 16, 2) && !class_ls.a(this.cK, this.cL - 16, 2)) {
                this.b(s, (short)(this.cL - 16));
                return true;
            }
            if (!class_ls.a(this.cK + n, this.cL + 16, 2) && !class_ls.a(this.cK, this.cL + 16, 2)) {
                this.b(s, (short)(this.cL + 16));
                return true;
            }
        } else if (n2 != 0) {
            if (!class_ls.a(this.cK - 16, this.cL + n2, 2) && !class_ls.a(this.cK - 16, this.cL, 2)) {
                this.b((short)(this.cK - 16), this.cL);
                return true;
            }
            if (!class_ls.a(this.cK + 16, this.cL + n2, 2) && !class_ls.a(this.cK + 16, this.cL, 2)) {
                this.b((short)(this.cK + 16), this.cL);
                return true;
            }
        }
        return false;
    }
}

