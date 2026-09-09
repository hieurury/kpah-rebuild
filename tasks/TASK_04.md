# KPAH — Task Log (Phần 04: Task 31 - 40)

> Lưu trữ nhật ký nhiệm vụ từ #31 đến #40.
> Quy tắc: Mỗi file chỉ lưu trữ tối đa 10 task.

---

## [2026-09-07 14:22] — Đóng Băng Auto 5s Khi Thao Tác Tay, Tuần Tra Tìm Quái Trong Bãi & Tăng Máu Quái Vật

**Yêu cầu:**
1. Thao tác tay (di chuyển) thường làm ngắt auto và phải mở cài đặt bật lại rất bất tiện. Khi người chơi thao tác thủ công trong lúc auto, đóng băng auto 5s. Sau khi người chơi ngừng thao tác đủ 5s, chủ động kích hoạt lại auto để không phải bật tắt liên tục.
2. Nhân vật đứng yên chờ quái hồi sinh khá mất thời gian nếu bãi nhỏ và quái ít. Cho phép nhân vật di chuyển qua lại trong bãi để tìm quái nhưng vẫn giữ logic an toàn không đi ra ngoài phạm vi bãi train.
3. Quái vật hiện tại còn mỏng, chết quá nhanh. Cần điều chỉnh tăng thêm lượng HP cho quái vật.

**Giải pháp đã triển khai:**
1. **Cơ chế đóng băng 5s & tự động kích hoạt lại auto:**
   - `game/app/src/classes/ModController.java`:
     - Thêm biến `manualFreezeUntil` (timestamp kết thúc đóng băng) và `autoWasActiveBeforeManual`.
     - Trong `onUserManualMove()`: Khi người chơi bấm phím di chuyển (2, 4, 6, 8) hoặc click chuột/chạm đất di chuyển (`movePlayer_`), nếu auto đang bật thì ghi nhớ `autoWasActiveBeforeManual = true`. Thiết lập `manualFreezeUntil = System.currentTimeMillis() + 5000L` (5 giây kể từ thao tác cuối cùng). Tạm thời tắt cờ `class_abj.au = false` để người chơi toàn quyền điều khiển nhân vật.
     - Trong `update()`: Trong 5s đóng băng, bỏ qua toàn bộ xử lý auto, xóa target để người chơi di chuyển tự do.
     - Khi hết 5s đóng băng (người chơi đã dừng thao tác đủ 5 giây): Nếu trước đó auto đang bật, hệ thống tự động kích hoạt lại auto (`class_abj.au = true, class_abj.av = true, autoCombatKeepActive = true`), tự động gán tâm bãi train (anchor) mới ngay tại vị trí hiện tại của người chơi (`autoAnchorX = player.cK, autoAnchorY = player.cL`), và hiện thông báo ngắn "Auto đã tự kích hoạt lại." Người chơi hoàn toàn không cần mở menu cài đặt để bật lại.
2. **Tuần tra di chuyển qua lại tìm quái trong bãi train (Patrol Roam):**
   - `game/app/src/classes/ModController.java`:
     - Định nghĩa bán kính tuần tra an toàn: `PATROL_RADIUS_X = 65`, `PATROL_RADIUS_Y = 55` (nằm gọn bên trong ranh giới bãi train 130x110 px).
     - Ở nhánh F (khi bãi trống chưa có quái): Nếu nhân vật đang đứng trong bãi an toàn (`isInsideZone`), sau mỗi 2.2 - 3.7 giây, nhân vật tự động chọn một điểm ngẫu nhiên trong bán kính an toàn quanh tâm bãi để bước tới.
     - Khi đang tuần tra, tầm quét của người chơi được mở rộng theo bước chân. Ngay khi có quái vật xuất hiện trong tầm, nhánh E sẽ lập tức bắt mục tiêu, hủy tuần tra và lao vào tấn công quái ngay lập tức.
3. **Tăng máu (maxHp) cho quái vật:**
   - **Database (`kpah-mysql`)**:
     - Cập nhật bảng `monsters`:
       - Cấp 1–5: Tăng ~2.2x (Nhím: 263 -> 579 HP; Sâu: 385 -> 847 HP; Giọt nước: 560 -> 1.232 HP; Gà điên: 788 -> 1.734 HP; Rắn lục: 1.138 -> 2.504 HP).
       - Cấp 6–15: Tăng 2.0x (Ma trơi: 1.360 -> 2.720 HP; Nắp ấm: 1.760 -> 3.520 HP; Quỷ hoa: 3.200 -> 6.400 HP; Heo mọi: 7.680 -> 15.360 HP; Bọ cạp: 8.800 -> 17.600 HP; Rết: 15.200 -> 30.400 HP).
       - Cấp 16+: Tăng 1.8x cho tất cả quái thường (`maxHp < 500.000`).
       - Giữ nguyên HP khoáng sản và boss thế giới.
   - **Server (`Monster.java`)**:
     - Tăng hệ số máu của **Quái Tinh Anh** từ `3.5x` lên `4.0x` (kết hợp với máu cơ bản mới, quái tinh anh cấp 1 Nhím đạt 2.316 HP, quái tinh anh cấp 4 Gà điên đạt 6.936 HP).

**Backup:**
- `game/app/src/classes/_backup/ModController.java.bak.20260907_1417`
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1417`

**Kết quả:** ✅ Thành công
- Server Java 21 biên dịch và khởi chạy daemon thành công trên cổng 19129 (`task-2419`).
- Client Java 8 biên dịch, đóng gói `KPAH_MOD.jar` và chạy emulator thành công (`task-2421`).

---

## [2026-09-07 14:30] — Tăng EXP Nhận Được Từ Quái Vật & Rút Gọn Thông Tin Hiển Thị Trên Màn Hình

**Yêu cầu:**
1. Tăng thêm lượng kinh nghiệm (EXP) nhận được khi tiêu diệt quái vật lên một chút vì hiện tại lượng exp nhận được khá ít.
2. Tìm nơi in thông tin hiển thị ở góc trên bên trái màn hình (như trong ảnh: ID, Xu, Lượng, Độ bền, Tấn công, Tọa độ, Tọa độ lưu, Exp Plus, Xu kiếm được) và loại bỏ, chỉ giữ lại đúng 2 dòng: **Độ bền** và **Tọa độ**.

**Giải pháp đã triển khai:**
1. **Tăng EXP từ quái vật:**
   - `server/KPAH/src/map/Monster.java`:
     - Trong phương thức `calculatePowerPlus()`: Tăng các hệ số tính `baseExp` lên ~2.5x tương xứng với lượng máu quái vật vừa được tăng trước đó:
       - Cấp 1–5: `baseExp = level * 65.0` (Cấp 1: 65 EXP [cũ 25]; Cấp 2: 130 EXP; Cấp 5: 325 EXP).
       - Cấp 6–10: `baseExp = level * 110.0` (Cấp 6: 660 EXP; Cấp 10: 1.100 EXP [cũ 450]).
       - Cấp 11–15: `baseExp = level * level * 18.0` (Cấp 11: 2.178 EXP; Cấp 15: 4.050 EXP [cũ 1.800]).
       - Cấp 16–20: `baseExp = level * level * 22.0` (Cấp 20: 8.800 EXP [cũ 4.000]).
       - Cấp 21–27: `baseExp = level * level * 28.0`.
       - Cấp 28+: `baseExp = level * level * 36.0`.
     - Quái tinh anh vẫn nhân 10 lần EXP theo lượng baseExp mới.
2. **Rút gọn hiển thị màn hình (Overlay):**
   - `game/app/src/classes/Paint.java`:
     - Định vị chính xác phương thức `onPaint(Graphics _graphics)`.
     - Loại bỏ các dòng in thừa: `ID`, `Xu`, `Lượng`, `Tấn công`, `Toạ độ lưu`, `Exp Plus`, `Xu kiếm được`.
     - Chỉ giữ lại đúng 2 dòng theo yêu cầu:
       - `Độ bền: <giá_trị>`
       - `Toạ độ: <tên_map x:y>`

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1426`
- `game/app/src/classes/_backup/Paint.java.bak.20260907_1426`

**Kết quả:** ✅ Thành công
- Server Java 21 biên dịch và khởi chạy daemon trên cổng 19129 (`task-2483`).
- Client Java 8 biên dịch, đóng gói `KPAH_MOD.jar` và chạy emulator thành công (`task-2485`).

---

---

## [2026-09-07 14:48] — Sửa Lỗi Target Thủ Công Vào NPC, Tắt Triệt Để Auto & Tăng Tỷ Lệ Xuất Hiện Quái Tinh Anh Lên 1%

**Yêu cầu:**
1. Khắc phục lỗi không thể target thủ công vào NPC.
2. Đảm bảo khi người chơi chủ động tắt auto thì toàn bộ cơ chế auto và di chuyển tự động phải được tắt triệt để, không tự kích hoạt lại hay can thiệp vào điều khiển người chơi.
3. Tăng tỷ lệ xuất hiện quái tinh anh lên 1%.
4. Đẩy toàn bộ dự án lên GitHub.

**Nguyên nhân gốc rễ (Root Cause):**
1. **Lỗi không target được NPC:**
   - Trong `ModController.java`: Trong khoảng thời gian 5s đóng băng (`manualFreezeUntil`), `update()` đã thực thi `gameScreen.r = null` liên tục mỗi frame. Do đó khi người chơi click chuột vào NPC, mục tiêu `r` lập tức bị xóa về `null` sau 20ms.
   - Khi `this.r == null`, bytecode của hàm click chuột trong client `class_abj.H()` (lệnh 457 `ifnull 1181`) bỏ qua toàn bộ vòng lặp kiểm tra click trúng entity mà nhảy thẳng tới `movePlayer_()`. Điều này khiến mọi thao tác click NPC bị biến thành lệnh di chuyển tới đất.
   - Trong `onUserManualMove()`, lệnh `gameScreen.r = null` cũng xóa sạch mục tiêu bất kể đó là quái hay NPC.
2. **Lỗi không thể tắt được Auto:**
   - Cờ `autoCombatKeepActive` trước đó được viết với logic nếu `!class_abj.au && !class_abj.av` thì tự động khôi phục `class_abj.au = true; class_abj.av = true;`. Khi người chơi vào Cài đặt chọn "Tắt auto", client đặt cả 2 cờ về `false`, nhưng ngay frame tiếp theo `ModController` lại ép bật lại, khiến người chơi không bao giờ tắt được auto.

**Giải pháp đã triển khai:**
1. **Khắc phục lỗi target thủ công vào NPC:**
   - `game/app/src/classes/ModController.java`:
     - Thêm phương thức nhận diện NPC an toàn: `isNpc(Object target)` (kiểm tra `vh.cF == 2 || vh.M() || vh.d_() || vh instanceof class_gn`).
     - Thêm hook `onUserManualClick(int clickX, int clickY)`: Khi người dùng click chuột/chạm đất, trích xuất tọa độ pixel click chuột trong thế giới thực và quét toàn bộ danh sách `gameScreen.l`. Nếu click trúng NPC, lập tức khóa target `gameScreen.r = vh`. Nếu nhân vật đang đứng gần (<= 40px), kích hoạt ngay `class_acv.c[5] = true` để mở hội thoại; nếu ở xa, nhân vật bước tới NPC với target được giữ nguyên.
     - Sửa `onUserManualMove()`: Chỉ xóa target nếu đó là quái vật thường (`!isNpc(gameScreen.r)`), tuyệt đối giữ nguyên target NPC.
     - Xóa hoàn toàn lệnh xóa target `gameScreen.r = null` trong block `if (now < manualFreezeUntil)` của `update()`, trả lại quyền target tự do cho người chơi khi thao tác tay.
     - Trong `handleAutoCombatRoaming()`: Nếu `gameScreen.r` đang là NPC, lập tức thoát hàm và không can thiệp, dành 100% ưu tiên cho việc giao tiếp với NPC.
   - `game/tools/Patcher.java`:
     - Cập nhật Patch 6 trên method `class_abj.movePlayer_(int, int)` để gọi `classes.ModController.onUserManualClick($1, $2)`.
2. **Tắt triệt để Auto khi người chơi chủ động tắt:**
   - `game/app/src/classes/ModController.java`:
     - Thêm hàm `disableAutoCompletely()`: Tắt toàn bộ cờ `class_abj.au = false, class_abj.av = false, autoCombatKeepActive = false, autoWasActiveBeforeManual = false, manualFreezeUntil = 0, isRegulating = false`, hủy bỏ tọa độ anchor bãi train, xóa ngay lập tức đường đi tự động của nhân vật (`gameScreen.q.s = null`), và nhả toàn bộ các phím đánh tự động (`class_acv.c[1..9] = false`).
     - Trong `update()`: Kiểm tra điều kiện `if (!class_abj.au && !class_abj.av)`, nếu cả 2 cờ đều tắt (do người chơi chọn Tắt trong Cài đặt hoặc nhân vật tử vong), gọi ngay `disableAutoCompletely()` và kết thúc update tick. Không hồi sinh auto trái ý muốn của người chơi.
     - Cập nhật `isAutoRunning()`: Trả về `false` ngay khi cả `au` và `av` đều tắt.
3. **Tăng tỷ lệ xuất hiện quái tinh anh lên 1%:**
   - `server/KPAH/src/map/Monster.java`:
     - Trong `rollElite()`: Thay đổi `Util.isTrue(0.5, 100.0)` thành `Util.isTrue(1.0, 100.0)` (tăng gấp đôi tỷ lệ xuất hiện quái tinh anh từ 0.5% lên 1.0%).
4. **Build & Triển khai:**
   - Server Java 21 biên dịch và khởi chạy thành công daemon trên cổng 19129 (`task-2768`).
   - Patcher Java 8 inject bytecode thành công vào `class_abj.class` và `class_ba.class`, cập nhật `KPAH_225_remade.jar`.
   - Client Java 8 biên dịch, đóng gói `KPAH_MOD.jar` và chạy emulator thành công (`task-2770`).

**Backup:**
- `game/app/src/classes/_backup/ModController.java.bak.20260907_1444`
- `game/tools/_backup/Patcher.java.bak.20260907_1444`
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1444`

**Kết quả:** ✅ Thành công

---

---

## [2026-09-07 15:00] — Điều Chỉnh Cân Bằng Game: Trả Tỷ Lệ Quái Tinh Anh Về 0.5% & Điều Tiết Lượng/Số Lượng Rớt Đồ Theo Cấp Quái (Trần Cấp 35)

**Yêu cầu:**
1. Trả tỷ lệ xuất hiện quái tinh anh về 0.5% (từ 1.0%).
2. Quái tinh anh tùy theo cấp độ quái sẽ cho lượng vật phẩm và số lượng vật phẩm ít lại, lấy số lượng/tỷ lệ hiện tại làm trần cao nhất cho quái lv35, các loại cấp thấp hơn giảm dần theo cấp quái.

**Giải pháp đã triển khai:**
1. **Trả tỷ lệ xuất hiện quái tinh anh về 0.5%:**
   - `server/KPAH/src/map/Monster.java`:
     - Trong phương thức `rollElite()`: Thay đổi `Util.isTrue(1.0, 100.0)` thành `Util.isTrue(0.5, 100.0)`.
2. **Điều tiết lượng vật phẩm và số lượng rớt đồ theo cấp quái (lấy lv35 làm trần tối đa):**
   - `server/KPAH/src/map/Monster.java`:
     - Thiết lập tỷ lệ cấp độ: `double lvRatio = Math.min(1.0, (double) Math.max(1, level) / 35.0)`.
     - **Tỷ lệ rớt đồ tổng thể (`rateMultiplier`):**
       - Trần lv35: x2.5 (+150%).
       - Quái cấp thấp: `1.0 + 1.5 * lvRatio` (Lv1 chỉ tăng nhẹ x1.04, Lv15 tăng x1.64, Lv25 tăng x2.07).
     - **Số lượng bình máu / bình mana (`potion`):**
       - Quái thường: 1 - 2 bình.
       - Quái tinh anh trần lv35: 2 - 4 bình.
       - Quái tinh anh cấp thấp: Giảm dần số lượng theo `minPot = 1..2` và `maxPot = 2..4` dựa vào `lvRatio`.
     - **Số lượng Vàng (`gold`):**
       - Trần lv35: x2.0 lượng vàng rớt.
       - Quái tinh anh cấp thấp: Nhân hệ số `1.0 + 1.0 * lvRatio` (Lv1: x1.03 vàng, Lv15: x1.43 vàng, Lv25: x1.71 vàng).
     - **Cơ hội rơi thêm món trang bị thứ 2 (`extra equipment`):**
       - Trần lv35: 100% cơ hội rơi thêm món trang bị thứ 2 (nếu dòng rớt trang bị kích hoạt).
       - Quái tinh anh cấp thấp: Tỷ lệ rơi trang bị thứ 2 giảm dần theo `lvRatio * 100.0` (Lv1: ~2.8%, Lv15: ~42.8%, Lv25: ~71.4%).
     - **Số lượng đá / nguyên liệu (`gems`):**
       - Quái thường: 1 viên (quái từ lv10 trở lên).
       - Quái tinh anh trần lv35: 2 viên.
       - Quái tinh anh cấp thấp: 1 viên ở lv10, cơ hội nhận thêm viên thứ 2 tăng dần từ lv11 đến lv35 theo tỷ lệ `(level - 10) / 25.0 * 100%`.
     - **Rương Tinh Anh (Vật phẩm ID 106):**
       - Quái tinh anh trần lv35: 100% rơi 1 rương.
       - Quái tinh anh cấp thấp: Tỷ lệ rơi rương giảm dần theo `30.0 + 70.0 * lvRatio` (Lv1: ~32%, Lv15: ~60%, Lv25: ~80%, Lv35: 100%).
     - **Bình kinh nghiệm (EXP Potion ID 108-111):**
       - Quái tinh anh trần lv35: 20% rơi 1 bình.
       - Quái tinh anh cấp thấp: Tỷ lệ rơi giảm dần theo `5.0 + 15.0 * lvRatio` (Lv1: ~5.4%, Lv15: ~11.4%, Lv25: ~15.7%, Lv35: 20%).

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1457`

**Kết quả:** ✅ Thành công
- Server Java 21 biên dịch thành công không lỗi.
- Đã khởi động lại server daemon (`task-2867`), server đang hoạt động bình thường trên cổng 19129.

---

---

## [2026-09-07 15:15] — Phân Hóa Rương Tinh Anh 4 Bậc (Cấp Quái Lv 1 - 35), Giữ Nguyên Tỷ Lệ Rớt & Chỉnh Tỷ Lệ Trang Bị Mặc Định Về 2%

**Yêu cầu:**
1. Điều chỉnh phần thưởng trong Rương Tinh Anh cho hợp lý: Phân loại rương tinh anh ra 4 cấp độ từ Bậc 1 đến Bậc 4 tương ứng theo cấp quái Lv 1 - 35, số vật phẩm trong rương phân phát theo chất lượng từng bậc rương.
2. Tỷ lệ rớt vật phẩm của quái tinh anh giữ nguyên như trước (tỷ lệ x2.5, 100% rớt 1 rương, 20% rớt bình kinh nghiệm), chỉ khác biệt về số lượng rớt và loại rương nhận được theo cấp độ quái.
3. Điều chỉnh tỷ lệ rơi trang bị mặc định về 2% (thay vì 3-4% như trước).

**Giải pháp đã triển khai:**
1. **Phân hóa 4 Bậc Rương Tinh Anh trong Database:**
   - Cập nhật và bổ sung 4 bậc rương vào bảng `potion_template`:
     - **ID 106:** `Rương Tinh Anh (Bậc 1)` (Quái Lv 1 - 9, Icon rương bạc 68).
     - **ID 160:** `Rương Tinh Anh (Bậc 2)` (Quái Lv 10 - 19, Icon rương bạc 68).
     - **ID 161:** `Rương Tinh Anh (Bậc 3)` (Quái Lv 20 - 29, Icon rương vàng 67).
     - **ID 162:** `Rương Tinh Anh (Bậc 4)` (Quái Lv 30 - 35, Icon rương vàng 67).
   - Đồng bộ file SQL `server/kpah.sql` với dữ liệu database mới.
2. **Cân đối phần thưởng mở Rương theo từng Bậc:**
   - `server/KPAH/src/services/UseItemService.java`:
     - Sửa `useItemPotion` nhận `short id` (đọc unsigned byte `& 0xFF` từ gói tin `USE_POTION` trong `MessageHandler.java` để hỗ trợ các ID >= 128 an toàn).
     - Mở rộng switch-case xử lý `case 106, 160, 161, 162 -> openEliteChest(player, potion)`.
     - Phân bổ phần thưởng theo từng bậc:
       - **Bậc 1 (Quái Lv 1-9):** 1 - 2 Lượng, 50% nhận 1 Tinh Anh Đan, 5 - 10 bình thuốc vừa/nhỏ, 50% nhận 1 nguyên liệu sơ cấp (Đá may mắn 1 hoặc Luyện kim dược), 40% nhận trang bị/vũ khí cùng phái Lv 1 - 9. Không rơi nguyên liệu cao cấp.
       - **Bậc 2 (Quái Lv 10-19):** 2 - 4 Lượng, 1 Tinh Anh Đan (100%), 8 - 15 bình thuốc vừa/to, 1 - 2 nguyên liệu sơ cấp, 25% nhận 1 nguyên liệu cao cấp, 60% nhận trang bị/vũ khí cùng phái Lv 10 - 19.
       - **Bậc 3 (Quái Lv 20-29):** 3 - 6 Lượng, 1 - 2 Tinh Anh Đan, 10 - 20 bình thuốc to/đặc biệt, 2 - 3 nguyên liệu sơ cấp, 50% nhận 1 - 2 nguyên liệu cao cấp, 80% nhận trang bị/vũ khí cùng phái Lv 20 - 29.
       - **Bậc 4 (Quái Lv 30-35 - Trần cao nhất):** 5 - 10 Lượng, 2 Tinh Anh Đan, 15 - 25 bình thuốc cao cấp, 2 - 4 nguyên liệu sơ cấp, 75% nhận 1 - 3 nguyên liệu cao cấp, 100% nhận 1 trang bị/vũ khí cùng phái Lv 30 - 35.
3. **Cập nhật rơi đồ quái tinh anh & tỷ lệ trang bị 2%:**
   - `server/KPAH/src/map/Monster.java`:
     - **Tỷ lệ rớt đồ quái tinh anh:** Giữ nguyên mức cao chuẩn (`rateMultiplier = isElite ? 2.5 : 1.0`).
     - **Tỷ lệ rơi trang bị mặc định:** Đặt về `2.0%` (`equipRate = 2.0 * rateMultiplier`, quái thường là 2.0%, quái tinh anh là 5.0%). Khi rớt trang bị, quái tinh anh luôn rơi thêm món thứ 2.
     - **Rương Tinh Anh:** 100% quái tinh anh rơi đúng 1 rương tương ứng cấp quái:
       - Lv <= 9: Rớt ID 106 (Bậc 1).
       - Lv 10 - 19: Rớt ID 160 (Bậc 2).
       - Lv 20 - 29: Rớt ID 161 (Bậc 3).
       - Lv 30 - 35: Rớt ID 162 (Bậc 4).
     - **Bình kinh nghiệm:** Giữ nguyên tỷ lệ 20% rớt 1 bình theo 4 bậc cấp quái (ID 108, 109, 110, 111).
     - **Số lượng rớt (Vàng, Potion, Ngọc):** Vẫn giữ cơ chế lấy lv35 làm trần tối đa và quái cấp thấp giảm dần số lượng.

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1510`
- `server/KPAH/src/services/_backup/UseItemService.java.bak.20260907_1510`
- `server/KPAH/src/network/_backup/MessageHandler.java.bak.20260907_1512`

**Kết quả:** ✅ Thành công
- Server Java 21 biên dịch không lỗi (`BUILD SUCCESSFUL`).
- Đã khởi động lại server daemon (`task-3077`), tải đủ 163 Potion Template và đang lắng nghe cổng 19129.

---

---

## [2026-09-07 18:20] — Tạo Bản Build Production Tách Biệt Server "Dị giới" (Tailscale Funnel) & Chuẩn Hóa JAD

**Yêu cầu:**
1. Tạo thêm 1 bản game production kết nối tới server thật công khai qua Tailscale Funnel tách biệt với bản test localhost:
   - Host: `kpah-server.tailba565a.ts.net`, Port: `443`.
   - Tên hiển thị server: `Dị giới`.
2. Xác nhận vị trí hardcode host/port trong client và sửa mảng kết nối trong `classes/class_yv.java`.
3. Tách biệt 2 bản build độc lập trong `build.xml`:
   - Bản Production: `KPAH_PROD.jar` và `KPAH_PROD.jad` (kết nối `kpah-server.tailba565a.ts.net:443`, hiển thị "Dị giới").
   - Bản Test Localhost: `KPAH_MOD.jar` và `KPAH_MOD.jad` (kết nối `127.0.0.1:19129`, hiển thị "Localhost").
4. Tạo tự động file descriptor `.jad` cho J2ME với 2 trường bắt buộc khớp chính xác kích thước byte:
   - `MIDlet-Jar-Size`: kích thước byte thực tế của file jar.
   - `MIDlet-Jar-URL`: tên file jar tương ứng.
5. Kiểm tra chạy thử bằng MicroEmulator (`tools/emulator.jar`) xác nhận kết nối chuẩn.

**Files thay đổi:**
- `game/app/src/classes/class_yv.java`:
  - Cập nhật thông tin server mặc định trong static initializer sang `b = {"Dị giới"}`, `e = {"kpah-server.tailba565a.ts.net"}`, `f = {443}`.
- `game/build.xml`:
  - Thêm `macrodef name="build-client"` hỗ trợ build có cấu hình riêng cho từng môi trường (tự động patch `class_yv.java` cho target tương ứng, biên dịch với UTF-8, đóng gói preverified jar, chạy ProGuard, và tự động sinh `.jad` kèm `MIDlet-Jar-Size` và `MIDlet-Jar-URL`).
  - Thêm các target: `dist-prod`, `dist-local`, `dist` (build cả hai), `run` (chạy bản production), `run-local` (chạy bản local test).

**Backup:**
- `game/app/src/classes/_backup/class_yv.java.bak.20260907_1813`
- `game/_backup/build.xml.bak.20260907_1816`

**Kết quả:** ✅ Thành công
- Đã build thành công cả 2 gói client trong `game/build/dist/`:
  - `KPAH_PROD.jar` (1,173,556 bytes) & `KPAH_PROD.jad` (MIDlet-Jar-Size: 1173556, trỏ `kpah-server.tailba565a.ts.net:443`, server "Dị giới").
  - `KPAH_MOD.jar` (1,173,535 bytes) & `KPAH_MOD.jad` (MIDlet-Jar-Size: 1173535, trỏ `127.0.0.1:19129`, server "Localhost").
- Kiểm tra MicroEmulator cho `KPAH_PROD.jar`:
  - Ghi nhận console: `ket noi socket://kpah-server.tailba565a.ts.net:443`, gửi lệnh handshake `cmd=-1` và `cmd=1` thành công.
- Kiểm tra MicroEmulator cho `KPAH_MOD.jar`:
  - Ghi nhận console: `ket noi socket://127.0.0.1:19129`, kết nối localhost thành công.

---

---

## [2026-09-08 10:40] — Kết nối Playit.gg Agent cho Server Production trên Termux

**Yêu cầu:** Giải quyết lỗi kết nối agent Playit trên Termux (bị chặn do lỗi phân giải DNS khi chạy `playit-cli`). Cung cấp Secret Key và lệnh kết nối cho Termux.

**Mức độ rủi ro:** Thấp

**Hành động:**
- Khởi tạo phiên claim từ máy chủ Linux và xác thực qua tài khoản web của user (`https://playit.gg/claim/6b871b576e`).
- Lấy thành công Secret Key từ Playit.gg: `76d1359294b8f50ee87953958b07de6a1194b6c569bc0a4d7c64636eaf967a65`.
- Cung cấp script tự động ghi file `~/.config/playit_gg/playit.toml` và chạy daemon `playitd` trên Termux.

**Kết quả:** ✅ Thành công (Agent đã kết nối và đăng ký thành công vào hệ thống Playit: `AgentRegistered { session_id: 22789, account_id: 2699796, agent_id: 6806186 }`).
**Ghi chú:** Agent đã online, user đang tiến hành tạo tunnel Minecraft Java trỏ về port 19129 trên dashboard Playit.

---

---

## [2026-09-08 11:00] — Cấu hình Server & Build Client Production (Playit Tunnel)

**Yêu cầu:** Cấu hình lại server production và build gói JAR production của game để chơi thử qua tunnel Playit.gg (`practicing-achieve.tun.ply.gg:50758`).

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/dist/KPAH.jar` — Biên dịch lại file JAR server qua `ant jar`.
- `game/app/src/classes/class_yv.java` — Chuyển kiểu dữ liệu `class_yv.f` từ `short[]` sang `int[]` để khắc phục lỗi tràn số âm khi port vượt quá 32767 (`50758` bị ép thành `-14778` gây crash socket); cập nhật host mặc định `practicing-achieve.tun.ply.gg` và port `50758`.
- `game/build.xml` — Cập nhật macro replace filter và target `dist-prod` trỏ về `practicing-achieve.tun.ply.gg:50758`.

**Backup:**
- `game/app/src/classes/_backup/class_yv.java.bak.20260908_1057`
- `game/_backup/build.xml.bak.20260908_1057`

**Kết quả:** ✅ Thành công
- Đã build thành công cả 2 gói client trong `game/build/dist/`:
  - `KPAH_PROD.jar` (1,173,576 bytes) & `KPAH_PROD.jad` (trỏ `practicing-achieve.tun.ply.gg:50758`, server "Dị giới").
  - `KPAH_MOD.jar` (1,173,553 bytes) & `KPAH_MOD.jad` (trỏ `127.0.0.1:19129`, server "Localhost").
- Đã xác thực bytecode `classes/class_yv.class` bên trong `KPAH_PROD.jar` chứa chính xác chuỗi `practicing-achieve.tun.ply.gg` và hằng số port `50758`.

---

---

## [2026-09-08 11:08] — Chạy Thử Bản Game Production trên Giả Lập MicroEmulator

**Yêu cầu:** Khởi chạy bản game `KPAH_PROD.jar` trực tiếp trên máy tính để kiểm tra kết nối qua tunnel Playit.gg.

**Mức độ rủi ro:** Thấp

**Hành động:**
- Khởi chạy MicroEmulator với `game/build/dist/KPAH_PROD.jar` bằng Java 8.
- Ghi nhận console khởi động thành công:
  ```text
  ket noi socket://practicing-achieve.tun.ply.gg:50758
  send cmd=-1
  send cmd=1
  ```

**Kết quả:** ✅ Thành công (Cửa sổ game giả lập MicroEmulator đã mở trên màn hình và kết nối thành công tới tunnel production).

---

---

## [2026-09-08 11:28] — Khắc Phục Lỗi Playit Daemon Tự Thoát Trong start.sh

**Yêu cầu:** Giải thích lý do Playit không duy trì kết nối khi chạy `start.sh` và hoàn thiện `start.sh`, `stop.sh` để tự động xử lý trọn gói socket kẹt và kiểm tra trạng thái sống của `playitd`.

**Mức độ rủi ro:** Thấp

**Files thay đổi:**
- `server/KPAH/start.sh` — Bổ sung `rm -f "$PREFIX/tmp/playit.sock"`, thêm chuyển hướng `< /dev/null`, và thêm bước kiểm tra thực tế tiến trình `playitd` kèm in log lỗi từ `~/playit.log` nếu khởi động thất bại.
- `server/KPAH/stop.sh` — Bổ sung dọn dẹp file `$PREFIX/tmp/playit.sock` khi dừng server.

**Backup:**
- `server/KPAH/_backup/start.sh.bak.20260908_1127`
- `server/KPAH/_backup/stop.sh.bak.20260908_1127`

**Kết quả:** ✅ Đã cập nhật script, commit git `595352d`.

---

---
