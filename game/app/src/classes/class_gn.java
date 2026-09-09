package classes;

import classes.class_abj;
import classes.class_acf;
import classes.class_acv;
import classes.class_ls;
import classes.class_vh;
import classes.class_yg;
import classes.class_yi;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class class_gn
extends class_vh {
    public int a;
    public int b;
    private Image[] e;
    public String c;
    public byte d = 1; // Cho phép tương tác NPC gửi packet lên server
    private static String[] f = new String[]{"Dì út HP", "Bà tám tạp hóa", "Hắc ngưu", "Thiết bị", "Lính gác", "Trưởng làng", "Phú ông", "Xa phu", "ông nội", "Anh bảy", "Hoa tiêu", "Nhất giáp", "Nhị giáp", "Tam giáp", "Tứ giáp", "Ngũ giáp", "Nhất ngưu", "Nhị ngưu", "Tam ngưu", "Tứ ngưu", "Ngũ ngưu", "Lâm tướng quân", "Nhật thương nhân", "Hỏa xích", "Bảo ngọc", "Trần thống lĩnh", "Kim hoa", "Giáp Sư", "Kiếm Sư", "Bội Châu", "An Tâm", "Lộc Phát"};
    private static byte[] g;
    private int h = 0;
    private int i = 0;
    private static byte[] j;
    private static byte[] k;

    static {
        byte[] byArray = new byte[32];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byArray[5] = 5;
        byArray[6] = 6;
        byArray[7] = 7;
        byArray[8] = 8;
        byArray[9] = 9;
        byArray[10] = 10;
        byArray[11] = 11;
        byArray[12] = 11;
        byArray[13] = 11;
        byArray[14] = 11;
        byArray[15] = 11;
        byArray[16] = 12;
        byArray[17] = 12;
        byArray[18] = 12;
        byArray[19] = 12;
        byArray[20] = 12;
        byArray[21] = 14;
        byArray[22] = 13;
        byArray[23] = 15;
        byArray[24] = 16;
        byArray[25] = 17;
        byArray[26] = 1;
        byArray[27] = 3;
        byArray[28] = 2;
        byArray[29] = 6;
        byArray[30] = 9;
        byArray[31] = 13;
        g = byArray;
        byte[] byArray2 = new byte[18];
        byArray2[2] = 2;
        byArray2[4] = 1;
        byArray2[7] = 2;
        j = byArray2;
        byte[] byArray3 = new byte[18];
        byArray3[0] = -7;
        byArray3[1] = -4;
        byArray3[2] = -5;
        byArray3[3] = -5;
        byArray3[4] = -5;
        byArray3[5] = -9;
        byArray3[6] = -9;
        byArray3[7] = -10;
        byArray3[8] = -7;
        byArray3[10] = -11;
        byArray3[11] = -10;
        byArray3[12] = -9;
        byArray3[13] = -10;
        byArray3[15] = -5;
        byArray3[16] = -9;
        byArray3[17] = -5;
        k = byArray3;
    }

    public class_gn() {
    }

    public class_gn(int n, int n2, int n3, class_acf class_acf2) {
        this.cF = (byte)2;
        this.cK = (short)((n << 4) + 8);
        this.cL = (short)((n2 << 4) + 8);
        this.cM = (short)30;
        this.cN = (short)40;
        this.a = n3;
        this.e = new Image[2];
        this.e[0] = class_acf2.d("npc" + g[n3] + "0" + ".png");
        this.e[1] = class_acf2.d("npc" + g[n3] + "1" + ".png");
        if (n3 == 4) {
            this.b = ++class_ls.l;
        }
    }

    public final String a_() {
        if (this.c != null) {
            return this.c;
        }
        return f[this.a];
    }

    public void a(Graphics graphics) {
        Image image;
        graphics.drawImage(this.e[1], (int)this.cK, (int)this.cL, 33);
        if (this.e[0] != null) {
            graphics.drawImage(this.e[0], this.cK + j[g[this.a]], this.cL + k[g[this.a]] + this.i / 5, 33);
        }
        if (!(image = class_abj.e(this.a)).equals(class_yi.l)) {
            graphics.drawImage(image, (int)this.cK, this.cL - this.cM - 5, 33);
        }
    }

    public void a_(Graphics graphics, int n, int n2) {
        graphics.setClip(n - 10, 32 - this.cM, 20, 22);
        if (this.e[0] != null) {
            graphics.drawImage(this.e[0], n, 32 - this.cM, 17);
            return;
        }
        if (this.e[1] != null) {
            graphics.drawImage(this.e[1], n, 32 - this.cM, 17);
        }
    }

    public void a(short s, short s2) {
        this.cK = s;
        this.cL = s2;
    }

    public void a() {
        super.a();
        if (this.da != null && this != class_acv.s.r) {
            this.da = null;
        }
        if (this.e[0] != null) {
            ++this.i;
            if (this.i > 9) {
                this.i = 0;
            }
        }
        if (class_acv.s.T != null && class_yg.d(this.cK / 16 - class_acv.s.T.posX / 16) <= 1 && class_yg.d(this.cL / 16 - class_acv.s.T.posY / 16) <= 1) {
            if (this.h > -2) {
                --this.h;
                return;
            }
            this.h = 0;
        }
    }

    public boolean d_() {
        return true;
    }

    public int f_() {
        if (this.a == 2 || this.a == 28 || this.a == 21) {
            return 0;
        }
        return this.d;
    }

    public final int g_() {
        return this.a;
    }
}
