import javassist.*;
import javassist.expr.*;
import javassist.bytecode.*;

/**
 * Patcher v3 – sửa 3 vấn đề Auto-Loot:
 *
 * 1. [class_abj.z()]  obj.e_() => false cho class_ba khi isAutoPickup
 *    => Item không bị lọc ra khỏi danh sách mục tiêu
 *
 * 2. [class_abj.z()]  class_yg.d() => priorityDistance()
 *    => Item có khoảng cách ảo thấp hơn, quái cao hơn
 *
 * 3. [class_abj.$main_loop] this.r.cF check for auto-attack:
 *    Nếu this.r instanceof class_ba thì không auto-attack, chỉ đi nhặt.
 *    => Chặn method call d(byte, short) khi r là item
 *
 * 4. [class_ba.a()]   25000ms => 60000ms (1 phút) cho item drop
 *
 * 5. [class_nu.c()]   Touch handling right column f = n8 * g + 2 (hiện tooltip trang bị cột phải)
 *    [class_nu.o()]   Mount slot visibility (luôn vẽ thú cưỡi)
 *
 * QUAN TRỌNG: Luôn đọc từ /tmp/orig_abj_clean (backup sạch) và /tmp/orig_ba_clean
 */
public class Patcher {
    public static void main(String[] args) throws Exception {
        ClassPool pool = new ClassPool(true);
        pool.appendClassPath("../libs/KPAH_225_remade.jar");
        pool.appendClassPath("../wtk/lib/midpapi20.jar");
        pool.appendClassPath("../wtk/lib/cldcapi11.jar");
        pool.insertClassPath("../build/classes");
        pool.insertClassPath("../build/dist/KPAH_PROD.jar");
        pool.insertClassPath("_orig_classes");

        patchClassAbj(pool);
        patchClassBa(pool);
        patchClassNu(pool);
        patchClassHw(pool);
        patchClassYi(pool);

        System.out.println("All patches applied successfully!");
    }

    private static void patchClassAbj(ClassPool pool) throws Exception {
        CtClass cc = pool.get("classes.class_abj");

        // === Patch 0: Trích xuất tầm chiêu ô số 5 & Chèn ModController.update() vào vòng lặp game tick class_abj.b() ===
        try {
            CtMethod bTick = cc.getDeclaredMethod("b", new CtClass[0]);
            bTick.insertBefore(
                "try {" +
                "    classes.ModController.currentShortcutSlots = this.cp;" +
                "    if (this.cp != null && this.cp.length > 5) {" +
                "        int idx5 = this.cp[5];" +
                "        if (classes.class_sc.a != null && classes.class_sc.a.length > 1 && classes.class_sc.a[1] != null && idx5 >= 0 && idx5 < classes.class_sc.a[1].length) {" +
                "            classes.class_gd gd = classes.class_sc.a[1][idx5];" +
                "            if (gd != null) {" +
                "                classes.ModController.slot5Range = (int)classes.class_qz.a((byte)gd.b());" +
                "            }" +
                "        }" +
                "    }" +
                "} catch (Exception e) {}" +
                "classes.ModController.update();"
            );
            System.out.println("Patch 0 (game-tick & slot5 range extraction) applied to method b().");
        } catch (Exception e) {
            System.out.println("Warning: Could not patch b(): " + e.getMessage());
        }

        // === Patch 1+2: hàm z() – ưu tiên item trong danh sách mục tiêu, mở rộng bán kính quét quái khi auto, và chặn tuyệt đối mục tiêu ngoài 4 góc hoặc khi đang điều tiết ===
        CtMethod zMethod = cc.getDeclaredMethod("z", new CtClass[0]);
        zMethod.insertBefore(
            "if (au && classes.ModController.globalConfig.isPrioritizeElite) {" +
            "    classes.class_vh elite = classes.ModController.getEliteTarget(this);" +
            "    if (elite != null) {" +
            "        return elite;" +
            "    }" +
            "}" +
            "if (classes.ModController.isRegulating) {" +
            "    return null;" +
            "}" +
            "if (au && classes.ModController.globalConfig.isAutoPickup && this.r != null && (this.r instanceof classes.class_ba)) {" +
            "    return this.r;" +
            "}" +
            "if (au) {" +
            "    for (int i = 0; i < 4; i++) {" +
            "        cb[i][0] = -(classes.ModController.ZONE_BOX_RADIUS_X + classes.ModController.LOOT_BUFFER); cb[i][1] = classes.ModController.ZONE_BOX_RADIUS_X + classes.ModController.LOOT_BUFFER; cb[i][2] = -(classes.ModController.ZONE_BOX_RADIUS_Y + classes.ModController.LOOT_BUFFER); cb[i][3] = classes.ModController.ZONE_BOX_RADIUS_Y + classes.ModController.LOOT_BUFFER;" +
            "    }" +
            "} else {" +
            "    for (int i = 0; i < 4; i++) {" +
            "        cb[i][0] = -90; cb[i][1] = 90; cb[i][2] = -90; cb[i][3] = 90;" +
            "    }" +
            "}"
        );
        zMethod.instrument(new ExprEditor() {
            public void edit(MethodCall mc) throws CannotCompileException {
                // Patch e_() and b_(): buộc false cho class_ba khi AutoPickup bật; và loại bỏ ngay mục tiêu ngoài 4 góc bãi train
                if (mc.getMethodName().equals("e_")) {
                    mc.replace(
                        "if (classes.ModController.globalConfig.isAutoPickup && ($0 instanceof classes.class_ba)) {" +
                        "    $_ = false;" +
                        "} else {" +
                        "    $_ = $proceed($$);" +
                        "}"
                    );
                } else if (mc.getMethodName().equals("b_")) {
                    mc.replace(
                        "if (au && classes.ModController.globalConfig.isPrioritizeElite && ($0 instanceof classes.class_bb) && ((classes.class_bb)$0).isElite) {" +
                        "    if (this.r == $0 || classes.ModController.isInsideZone((int)((classes.class_vh)$0).cK, (int)((classes.class_vh)$0).cL)) {" +
                        "        $_ = false;" +
                        "    } else {" +
                        "        $_ = true;" +
                        "    }" +
                        "} else if (au && !($0 instanceof classes.class_ba) && !classes.ModController.isInsideZone((int)((classes.class_vh)$0).cK, (int)((classes.class_vh)$0).cL)) {" +
                        "    $_ = true;" +
                        "} else if (classes.ModController.globalConfig.isAutoPickup && ($0 instanceof classes.class_ba)) {" +
                        "    if (classes.ModController.isInsideLootZone((int)((classes.class_vh)$0).cK, (int)((classes.class_vh)$0).cL)) {" +
                        "        $_ = false;" +
                        "    } else {" +
                        "        $_ = true;" +
                        "    }" +
                        "} else {" +
                        "    $_ = $proceed($$);" +
                        "}"
                    );
                }
                // Patch class_yg.d(): điều chỉnh khoảng cách ưu tiên
                else if (mc.getClassName().equals("classes.class_yg") && mc.getMethodName().equals("d")) {
                    mc.replace(
                        "$_ = classes.ModController.priorityDistance($0, $proceed($$));"
                    );
                }
            }
        });


        // === Patch 5: cho phép đuổi theo quái vật khi bật auto đánh ===
        // Trong method b(class_vh, int) của class_abj:
        // Bytecode gốc:
        // if (au && ao == 1 && this.r != null && this.r.cY) { n = 1; }
        // 1. Thay field ao thành 1 để không phụ thuộc vào Chế độ thường/Đánh quái trong menu cài đặt.
        // 2. Thay field cY thành ModController.shouldChase($0) để quái vật (cF == 1) cũng kích hoạt n = 1.
        try {
            CtClass[] bParamTypes = new CtClass[]{
                pool.get("classes.class_vh"),
                pool.get("int")
            };
            CtMethod bMethod = cc.getDeclaredMethod("b", bParamTypes);
            bMethod.instrument(new ExprEditor() {
                public void edit(FieldAccess fa) throws CannotCompileException {
                    if (fa.getFieldName().equals("ao")) {
                        fa.replace("$_ = 1;");
                    } else if (fa.getFieldName().equals("cY")) {
                        fa.replace("$_ = classes.ModController.shouldChase($0);");
                    }
                }
                public void edit(MethodCall mc) throws CannotCompileException {
                    if (mc.getMethodName().equals("movePlayer")) {
                        // Vô hiệu hóa lệnh kéo ngược về ag, ah của client gốc để ModController quản lý thông minh
                        mc.replace("/* disabled original 120px pullback */ ;");
                    }
                }
            });
            System.out.println("Patch 5 (target-chase) applied to method b(class_vh,int).");
        } catch (Exception e) {
            System.out.println("Warning: Could not patch b(class_vh,int): " + e.getMessage());
        }

        // === Patch 6: Tắt auto & kiểm tra click NPC khi người dùng click chuột/chạm đất di chuyển ===
        try {
            CtClass[] mpParams = new CtClass[]{ CtClass.intType, CtClass.intType };
            CtMethod mpMethod = cc.getDeclaredMethod("movePlayer_", mpParams);
            mpMethod.insertBefore("classes.ModController.onUserManualClick($1, $2);");
            System.out.println("Patch 6 (manual-click & move hook) applied to movePlayer_(int,int).");
        } catch (Exception e) {
            System.out.println("Warning: Could not patch movePlayer_: " + e.getMessage());
        }

        cc.writeFile("patched_classes");
        System.out.println("class_abj patched.");
    }

    private static void patchClassBa(ClassPool pool) throws Exception {
        CtClass cc = pool.get("classes.class_ba");

        // === Patch 4: tăng thời gian biến mất từ 25s lên 60s ===
        // Trong method a(), điều kiện: currentTimeMillis - this.i > 25000L
        // Ta thay tất cả literal 25000 thành 60000
        CtMethod aMethod = cc.getDeclaredMethod("a", new CtClass[0]);
        aMethod.instrument(new ExprEditor() {
            public void edit(javassist.expr.FieldAccess fa) throws CannotCompileException {}
        });
        // Thay thế toàn bộ body của method a() để hardcode 60s
        // Cách đơn giản: insertBefore để ghi đè thời gian
        // Thực ra cần thay constant 25000L => dùng Javassist setBody()
        // nhưng setBody() không work tốt với J2ME bytecode
        // Thay vào đó: instrument để chặn comparison với 25000
        aMethod.instrument(new ExprEditor() {
            public void edit(javassist.expr.NewExpr ne) throws CannotCompileException {}
        });

        // Cách tiếp cận khác: dùng insertBefore để reset timer dài hơn
        // Sẽ override bằng cách set this.i mỗi lần để delay việc expire
        // Thực tế nhất: thay field 'h' (static short h = 6) không phải timer
        //
        // Cần thay hardcoded 25000L. Ta dùng instrument với MethodCall không được.
        // Cách tốt nhất: dùng setBody() với source code đã sửa timer
        aMethod.setBody(
            "{ final long currentTimeMillis = System.currentTimeMillis();" +
            "  long itemTimeout = 60000L;" + // tất cả item: 1 phút
            "  if (super.cF == 3 || super.cF == 6) {" +
            "      if (currentTimeMillis - this.i > itemTimeout) {" +
            "          super.cE = true;" +
            "      }" +
            "  } else if (super.cF == 4 && currentTimeMillis - this.i > itemTimeout) {" +
            "      super.cE = true;" +
            "  }" +
            "  if (this.a == 1 || this.a == 2) {" +
            "      super.cK += (short)(this.b - super.cK >> 2);" +
            "      super.cL += (short)(this.f - super.cL >> 2);" +
            "      if (this.g >= -classes.class_ba.h) {" +
            "          this.e += this.g;" +
            "          --this.g;" +
            "      }" +
            "      if ((classes.class_abj.c(super.cK - this.b) < 4 || classes.class_abj.c(super.cL - this.f) < 4) && this.e <= 1) {" +
            "          super.cK = this.b;" +
            "          super.cL = this.f;" +
            "          this.e = 0;" +
            "          this.g = 0;" +
            "          if (this.a == 2) {" +
            "              super.cE = true;" +
            "          }" +
            "          this.a = 0;" +
            "      }" +
            "  }" +
            "}"
        );

        cc.writeFile("patched_classes");
        System.out.println("class_ba patched (item timeout = 60s).");
    }

    private static void patchClassNu(ClassPool pool) throws Exception {
        CtClass cc = pool.get("classes.class_nu");

        // Patch 1: Cuốc coordinates in <clinit> (bj[13]=26, bk[13]=84)
        CtConstructor clinit = cc.getClassInitializer();
        if (clinit != null) {
            clinit.insertAfter("bj[13] = 26; bk[13] = 84;");
            System.out.println("Patched class_nu.<clinit> (pickaxe slot coordinates bj[13]=26, bk[13]=84)");
        }

        // Patch 2: Patch method c(): Fix right-column touch selection (weapon, necklace, rings, jade)
        CtMethod m = cc.getDeclaredMethod("c", new CtClass[0]);
        MethodInfo mi = m.getMethodInfo();
        CodeAttribute ca = mi.getCodeAttribute();
        CodeIterator ci = ca.iterator();
        while (ci.hasNext()) {
            int pos = ci.next();
            if (pos == 2508) {
                // replace iload 4 (2), iadd (1), iconst_1 (1), iadd (1) with iconst_2 (1), iadd (1), nop (1), nop (1), nop (1)
                ci.writeByte(Opcode.ICONST_2, pos);
                ci.writeByte(Opcode.IADD, pos + 1);
                ci.writeByte(Opcode.NOP, pos + 2);
                ci.writeByte(Opcode.NOP, pos + 3);
                ci.writeByte(Opcode.NOP, pos + 4);
                System.out.println("Patched class_nu.c() (right column click tooltip) at pos " + pos);
                break;
            }
        }

        // Patch 3: Center key (phím giữa / 5) in class_nu.c() opens tooltip
        m.insertBefore(
            "if (x[w] == 1 && classes.class_acv.b(5)) {" +
            "    if (this.r) {" +
            "        this.r = false;" +
            "        this.n();" +
            "        this.f = 0;" +
            "    }" +
            "    classes.class_acv.c[5] = false;" +
            "    classes.class_nu.f(this);" +
            "    return;" +
            "}"
        );
        System.out.println("Patched class_nu.c() (center key tooltip)");

        // Patch 4: Method f() for Cuốc (type 13) and all equipment tooltips
        CtMethod mf = cc.getDeclaredMethod("f", new CtClass[] { cc });
        mf.setBody("{\n" +
            "    int n = 0;\n" +
            "    int n2 = 0;\n" +
            "    if ($1.f % 3 == 1) {\n" +
            "        if ($1.f == 4) {\n" +
            "            if (classes.class_nu.O != null && classes.class_nu.O.aT != null) {\n" +
            "                int sz = classes.class_nu.O.aT.size();\n" +
            "                for (int i = 0; i < sz; i++) {\n" +
            "                    classes.class_ql item = (classes.class_ql)classes.class_nu.O.aT.elementAt(i);\n" +
            "                    classes.class_yc tmpl = classes.class_yi.b((int)item.r);\n" +
            "                    if (tmpl != null && (tmpl.c == 13 || tmpl.c == 19)) {\n" +
            "                        $1.a(item, false, 31, 82);\n" +
            "                        return;\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "            return;\n" +
            "        } else {\n" +
            "            java.util.Vector vec = new java.util.Vector();\n" +
            "            vec.addElement(new classes.class_s(\"Linh thú\", new classes.class_vy($1)));\n" +
            "            vec.addElement(new classes.class_s(\"Thú cưng\", new classes.class_vx($1)));\n" +
            "            classes.class_acv.u.a(vec, 3);\n" +
            "            return;\n" +
            "        }\n" +
            "    }\n" +
            "    n2 = ($1.f / 3 << 1) + ($1.f % 3 - ($1.f % 3 > 0 ? 1 : 0));\n" +
            "    if (classes.class_nu.O != null && classes.class_nu.O.aT != null) {\n" +
            "        int sz = classes.class_nu.O.aT.size();\n" +
            "        for (int i = 0; i < sz; i++) {\n" +
            "            classes.class_ql item = (classes.class_ql)classes.class_nu.O.aT.elementAt(i);\n" +
            "            classes.class_yc tmpl = classes.class_yi.b((int)item.r);\n" +
            "            if (tmpl != null && ((tmpl.c == bl[n2]) || (bl[n2] == -1 && tmpl.c > 2 && tmpl.c < 8)) && (n2 != 7 || ++n != 1)) {\n" +
            "                $1.a(item, false, bn[n2][0] + 5, bn[n2][1] - 2);\n" +
            "                return;\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}");
        System.out.println("Patched class_nu.f() (cuoc & equipment tooltip handling)");

        // Patch 5: Patch method o(Graphics): Always draw mount even if pickaxe is equipped
        CtClass[] gParam = new CtClass[] { pool.get("javax.microedition.lcdui.Graphics") };
        CtMethod mo = cc.getDeclaredMethod("o", gParam);
        MethodInfo mio = mo.getMethodInfo();
        CodeAttribute cao = mio.getCodeAttribute();
        CodeIterator cio = cao.iterator();
        while (cio.hasNext()) {
            int pos = cio.next();
            if (pos == 339) {
                for (int i = 0; i < 12; i++) {
                    cio.writeByte(Opcode.NOP, pos + i);
                }
                System.out.println("Patched class_nu.o(Graphics) (mount slot visibility) at pos " + pos);
                break;
            }
        }

        cc.writeFile("patched_classes");
        System.out.println("class_nu patched successfully.");
    }

    private static void patchClassHw(ClassPool pool) throws Exception {
        CtClass cc = pool.get("classes.class_hw");
        CtClass[] gParam = new CtClass[] { pool.get("javax.microedition.lcdui.Graphics") };
        CtMethod m = cc.getDeclaredMethod("a", gParam);
        m.instrument(new ExprEditor() {
            @Override
            public void edit(MethodCall mc) throws CannotCompileException {
                if (mc.getMethodName().equals("drawRegion")) {
                    mc.replace("if ($1 != null) { $proceed($$); }");
                } else if (mc.getMethodName().equals("elementAt") && mc.getClassName().equals("java.util.Vector")) {
                    mc.replace("$_ = ($1 >= 0 && $1 < $0.size()) ? $proceed($$) : null;");
                }
            }
        });
        cc.writeFile("patched_classes");
        System.out.println("class_hw patched successfully (safe elementAt & drawRegion).");
    }

    private static void patchClassYi(ClassPool pool) throws Exception {
        CtClass cyi = pool.get("classes.class_yi");
        CtMethod myib = cyi.getDeclaredMethod("b", new CtClass[] { CtClass.intType, CtClass.intType, CtClass.intType });
        myib.insertBefore(
            "if ($1 < 0 || $1 >= classes.class_hw.co.length || $2 < 0 || classes.class_hw.co[$1] == null || $2 >= classes.class_hw.co[$1].length || $3 < 0 || classes.class_hw.co[$1][$2] == null || $3 >= classes.class_hw.co[$1][$2].length) { return null; }"
        );
        cyi.writeFile("patched_classes");
        System.out.println("class_yi patched successfully (safe class_yi.b).");
    }
}
