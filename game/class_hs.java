/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  classes.MsgHandler
 *  classes.class_abs
 *  classes.class_aco
 *  classes.class_acv
 */
package classes;

import classes.MsgHandler;
import classes.class_abs;
import classes.class_aco;
import classes.class_acv;
import classes.class_bi;

final class class_hs
implements Runnable {
    private class_aco a;

    class_hs(class_aco class_aco2) {
        this.a = class_aco2;
    }

    @Override
    public final void run() {
        block4: while (true) {
            try {
                while (this.a.b()) {
                    int n;
                    byte[] byArray;
                    ++class_aco.k;
                    byte by = this.a.a.readByte();
                    if (this.a.g) {
                        by = class_aco.a((class_aco)this.a, (byte)by);
                    }
                    if (this.a.g) {
                        if (by == -128) {
                            by = class_aco.a((class_aco)this.a, (byte)this.a.a.readByte());
                            byArray = new byte[]{this.a.a.readByte(), this.a.a.readByte(), this.a.a.readByte(), this.a.a.readByte()};
                            n = class_aco.a((class_aco)this.a, (byte)byArray[3]) & 0xFF | (class_aco.a((class_aco)this.a, (byte)byArray[2]) & 0xFF) << 8 | (class_aco.a((class_aco)this.a, (byte)byArray[1]) & 0xFF) << 16 | (class_aco.a((class_aco)this.a, (byte)byArray[0]) & 0xFF) << 24;
                        } else {
                            n = (class_aco.a((class_aco)this.a, (byte)this.a.a.readByte()) & 0xFF) << 8 | class_aco.a((class_aco)this.a, (byte)this.a.a.readByte()) & 0xFF;
                        }
                    } else {
                        n = this.a.a.readUnsignedShort();
                    }
                    byArray = new byte[n];
                    int n2 = 0;
                    int n3 = 0;
                    while (n2 != -1 && n3 < n) {
                        n2 = this.a.a.read(byArray, n3, n - n3);
                        if (n2 > 0) {
                            // empty if block
                        }
                        class_aco class_aco2 = this.a;
                        class_aco2.f += (n3 += n2) + 5;
                    }
                    if (this.a.g) {
                        for (n2 = 0; n2 < byArray.length; ++n2) {
                            byArray[n2] = class_aco.a((class_aco)this.a, (byte)byArray[n2]);
                        }
                    }
                    class_abs class_abs2 = new class_abs(by, byArray);
                    try {
                        if (class_abs2.a == -40) {
                            int n4;
                            class_abs class_abs3 = class_abs2;
                            int n5 = class_abs3.b().readByte();
                            this.a.h = new byte[n5];
                            for (n4 = 0; n4 < n5; n4 = (byte)(n4 + 1)) {
                                this.a.h[n4] = class_abs3.b().readByte();
                            }
                            for (n4 = 0; n4 < this.a.h.length - 1; ++n4) {
                                int n6;
                                byte[] byArray2 = this.a.h;
                                int n7 = n6 = n4 + 1;
                                byArray2[n7] = (byte)(byArray2[n7] ^ this.a.h[n4]);
                            }
                            this.a.g = true;
                            continue block4;
                        }
                        n3 = class_abs2.a;
                        if (n3 != 4 && n3 != 90 && n3 != 60 && n3 != 70 && n3 != 27 && n3 != 15 && n3 != 47 && n3 != 8 && n3 != -51 && n3 != 5 && n3 != -48 && n3 != 17 && n3 != 7) {
                            System.out.println("receive cmd=" + n3);
                        }
                        MsgHandler.onMessage((class_bi)this.a.b, (class_abs)class_abs2);
                        continue block4;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                break;
            }
            catch (Exception exception) {
                // empty catch block
                break;
            }
        }
        if (this.a.c) {
            if (this.a.b != null) {
                if (System.currentTimeMillis() - this.a.i > 500L) {
                    class_bi class_bi2 = this.a.b;
                    class_acv.s.n();
                } else {
                    class_bi class_bi3 = this.a.b;
                    class_acv.s.m();
                }
            }
            if (class_aco.a((class_aco)this.a) != null) {
                class_aco.c((class_aco)this.a);
            }
        }
    }
}
