/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package classes;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public final class class_acf {
    public static class_acf a;
    private String[] c;
    private int[] d;
    private int[] e;
    private byte[] f;
    private int g;
    private int h;
    private String i;
    private byte[] j = new byte[]{78, 103, 117, 121, 101, 110, 86, 97, 110, 77, 105, 110, 104};
    private int k = this.j.length;
    public static final String[] b;
    private DataInputStream l;

    static {
        b = new String[]{"/c/leg/", "/c/body/", "/c/head/", "/c/hat/", "/c/coat/"};
    }

    public class_acf() {
    }

    public static void a() {
        if (a != null) {
            a.b();
        }
        a = null;
        System.gc();
    }

    public class_acf(String string, byte[] object) {
        int n = 0;
        int n2 = 0;
        this.i = string;
        this.h = 0;
        if (object == null) {
            object = this;
            this.l = new DataInputStream(object.getClass().getResourceAsStream(((class_acf)object).i));
        } else {
            byte[] byArray = object;
            object = this;
            this.l = new DataInputStream(new ByteArrayInputStream(byArray));
        }
        if (this.l == null) {
            a = null;
            return;
        }
        try {
            int n3;
            this.g = n3 = this.l.readUnsignedByte();
            ++this.h;
            this.c = new String[this.g];
            this.d = new int[this.g];
            this.e = new int[this.g];
            int n4 = 0;
            while (n4 < this.g) {
                byte by;
                n3 = this.l.readByte();
                byte[] byArray = new byte[by];
                this.l.read(byArray);
                this.b(byArray);
                this.c[n4] = new String(byArray);
                this.d[n4] = n;
                this.e[n4] = n3 = this.l.readUnsignedShort();
                n += this.e[n4];
                n2 += this.e[n4];
                this.h += by + 3;
                ++n4;
            }
            this.f = new byte[n2];
            this.l.readFully(this.f);
            this.b(this.f);
        }
        catch (Exception exception) {
            String cfr_ignored_0 = String.valueOf(string) + " Error in fileback constructor > " + exception.toString();
        }
        this.b();
    }

    public static Image a(String string) {
        return a.d(String.valueOf(string) + ".png");
    }

    public static void b(String string) {
        a = new class_acf(string, null);
    }

    public static void a(byte[] byArray) {
        a = new class_acf("", byArray);
    }

    private void b(byte[] byArray) {
        int n = byArray.length;
        int n2 = 0;
        while (n2 < n) {
            byArray[n2] = (byte)(byArray[n2] ^ this.j[n2 % this.k]);
            ++n2;
        }
    }

    public final void b() {
        try {
            if (this.l != null) {
                this.l.close();
                return;
            }
        }
        catch (IOException iOException) {}
    }

    public final byte[] c(String object) {
        int n = 0;
        while (n < this.g) {
            if (this.c[n].compareTo((String)object) == 0) {
                object = new byte[this.e[n]];
                System.arraycopy(this.f, this.d[n], object, 0, this.e[n]);
                return object;
            }
            ++n;
        }
        throw new Exception("File '" + (String)object + "' not found!");
    }

    public final Image d(String string) {
        int n = 0;
        while (n < this.g) {
            if (this.c[n].compareTo(string) == 0) {
                return Image.createImage((byte[])this.f, (int)this.d[n], (int)this.e[n]);
            }
            ++n;
        }
        return null;
    }

    public final InputStream e(String object) {
        int n = 0;
        while (n < this.g) {
            if (this.c[n].compareTo((String)object) == 0) {
                object = new byte[this.e[n]];
                System.arraycopy(this.f, this.d[n], object, 0, this.e[n]);
                return new ByteArrayInputStream((byte[])object);
            }
            ++n;
        }
        return null;
    }
}
