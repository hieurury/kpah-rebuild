import javassist.*;
import javassist.expr.*;

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
 * QUAN TRỌNG: Luôn đọc từ /tmp/orig_abj_clean (backup sạch) và /tmp/orig_ba_clean
 */
public class Patcher {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("/tmp/orig_abj_clean");           // class_abj gốc sạch
        pool.insertClassPath("/tmp/orig_ba_clean");            // class_ba gốc sạch
        pool.insertClassPath("../libs/KPAH_225_remade.jar");
        pool.insertClassPath("../wtk/lib/midpapi20.jar");
        pool.insertClassPath("../wtk/lib/cldcapi11.jar");
        pool.insertClassPath("../build/classes");

        patchClassAbj(pool);
        patchClassBa(pool);

        System.out.println("All patches applied successfully!");
    }

    private static void patchClassAbj(ClassPool pool) throws Exception {
        CtClass cc = pool.get("classes.class_abj");

        // === Patch 1+2: hàm z() – ưu tiên item trong danh sách mục tiêu ===
        CtMethod zMethod = cc.getDeclaredMethod("z", new CtClass[0]);
        zMethod.instrument(new ExprEditor() {
            public void edit(MethodCall mc) throws CannotCompileException {
                // Patch e_() and b_(): buộc false cho class_ba khi AutoPickup bật
                if (mc.getMethodName().equals("e_") || mc.getMethodName().equals("b_")) {
                    mc.replace(
                        "if (classes.ModController.globalConfig.isAutoPickup && ($0 instanceof classes.class_ba)) {" +
                        "    $_ = false;" +
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

        // === Patch 3: dừng auto-attack khi r là item (class_ba) ===
        // Tìm hàm thực thi auto logic (main loop) – method dùng class_acv.c[this.cl]
        // Đây là method A() (private void A()) gọi auto-attack action
        // Thay vì patch bytecode phức tạp, ta dùng insertBefore trên hàm b() để
        // kiểm tra và skip attack khi target là item
        //
        // Cách tiếp cận: chặn lời gọi class_acv.c[this.cl] = true
        // bằng cách patch method setAutoAttack() trong ModController
        // và chèn vào trước khi gán class_acv.c
        //
        // Thực tế đơn giản hơn: patch method a(boolean) để khi r là class_ba
        // không gọi auto-attack mà thay vào đó đi nhặt
        //
        // Ta patch tại điểm gán class_acv.c[this.cl] = true trong A()
        // bằng cách inject ModController.shouldAutoAttack() check
        //
        // Patch 3: Khi auto, nếu r là item thì không dùng skill (gây lỗi đánh không khí/lan quái)
        // mà ép gửi luôn gói tin nhặt đồ (Vacuum Loot) và xóa target hiện tại để auto chuyển sang mục tiêu khác.
        try {
            CtClass[] paramTypes = new CtClass[]{
                pool.get("int"),
                pool.get("int")
            };
            CtMethod dMethod = cc.getDeclaredMethod("d", paramTypes);
            dMethod.insertBefore(
                "if (classes.ModController.globalConfig.isAutoPickup && " +
                "    this.r != null && " +
                "    this.r instanceof classes.class_ba) {" +
                "    this.D.a((byte)((classes.class_ba)this.r).cF, this.r.cG);" +
                "    this.r = null;" +
                "    return;" +
                "}"
            );
            System.out.println("Patch 3 (vacuum-loot) applied to method d(int,int).");
        } catch (Exception e) {
            System.out.println("Warning: Could not patch d(int,int): " + e.getMessage());
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
}
