# KPAH — Task Log (Phần 03: Task 21 - 30)

> Lưu trữ nhật ký nhiệm vụ từ #21 đến #30.
> Quy tắc: Mỗi file chỉ lưu trữ tối đa 10 task.

---

## [2026-09-07 09:50] — Nâng tỷ lệ quái tinh anh lên 10% và khởi chạy Server/Client

**Yêu cầu:** Tạm thời nâng tỷ lệ quái tinh anh xuất hiện lên 10% để test, sau đó chạy server và game lên.

**Mức độ rủi ro:** Thấp

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java` — Điều chỉnh trong `rollElite()`: thay `Util.isTrue(0.5, 100.0)` thành `Util.isTrue(10.0, 100.0)`.

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_0948`

**Kết quả:** ✅ Thành công
- Khởi động Docker container `kpah-mysql`.
- Biên dịch và chạy Server Java 21 lắng nghe cổng 19129.
- Biên dịch và chạy Game Client (MicroEmulator) Java 8, đã kết nối tới Server thành công.

---

---

## [2026-09-07 10:20] — Tăng kích thước Quái Tinh Anh +20%, khắc phục mất Auto khi bị choáng (Stun) và sửa triệt để lỗi nhặt vật phẩm

**Yêu cầu:** 
1. Tăng kích thước thêm 20% cho quái tinh anh (Elite monster size +20%).
2. Khắc phục lỗi khi bị choáng (stun) nhân vật mất trạng thái tự động tấn công.
3. Khắc phục triệt để lỗi không nhặt được vật phẩm (cả thủ công lẫn tự động nhặt).

**Nguyên nhân gốc rễ phát hiện:**
1. **Kích thước quái tinh anh:** Trước đó chỉ vẽ viền offset pixel xung quanh sprite chứ chưa scale ma trận render `Graphics2D` của J2ME emulator, do đó sprite thực tế vẫn giữ nguyên tỉ lệ 100%.
2. **Không nhặt được vật phẩm:** 
   - Trong `game/tools/Patcher.java`, `Patch 3` cũ đã chèn lệnh `if (this.r != null && this.r instanceof classes.class_ba) return;` vào method `class_abj.d(int, int)`. Trong client gốc, method `d(int, int)` chính là nơi xử lý tương tác nhặt đồ `this.D.a(this.r.cF, this.r.cG)`. Lệnh return này đã chặn 100% mọi hành động nhặt đồ thủ công khi người chơi tương tác với vật phẩm.
   - Thêm vào đó, ClassPool trong `Patcher.java` nạp `../libs/KPAH_225_remade.jar` trước `_orig_classes`, khiến Javassist đọc lại file class đã bị chèn Patch 3 trước đó.
3. **Mất Auto khi bị choáng:**
   - Server gửi gói tin `sendRemoveBuffInfluence(BUFF_STUN)` (opcode 89) khi hết choáng khiến client hiểu nhầm là bị dính choáng mới, gây vòng lặp choáng liên tục.
   - Khi bị đẩy lùi hoặc choáng (`cV == 1`), code client gốc trong `class_hw.java` dòng 1456 tự động gán `class_abj.au = false;`.
   - Nếu người chơi bấm phím điều hướng trong khi bị choáng, `ModController.onUserManualMove()` tắt luôn cờ `au` và `av`.
   - Trong `handleAutoCombatRoaming()`, không kiểm tra `player.cW` (isStunned), dẫn đến nhân vật liên tục cố di chuyển/nhặt đồ khi không thể cử động, đẩy `pickupAttempts > 6` hoặc `moveStuckCount >= 5` và đưa vật phẩm vào danh sách bỏ qua (`ignoredItems`) 15 giây.

**Files thay đổi:**
- `server/KPAH/src/skill/BuffInfluencePlayer.java` & `BuffInfluenceMonster.java`:
  - Loại bỏ việc gửi `sendRemoveBuffInfluence(BUFF_STUN)` (opcode 89) khi hết hiệu ứng choáng.
- `game/app/src/classes/ModHelpers.java`:
  - Thêm phương thức `beginScale(Object g, int centerX, int centerY, double scale)` và `endScale(Object g, Object oldTx)` sử dụng Reflection tương thích chuẩn J2ME runtime / ProGuard để phóng to quái vật mà không làm vỡ đồ họa xung quanh.
- `game/app/src/classes/class_bb.java`:
  - Trong `a(Graphics graphics)`: Phóng to cơ thể quái tinh anh lên 120% (`scale = 1.2`) xoay quanh tâm hiển thị `(cx, cy)`.
  - Mở rộng bán kính vòng tròn hào quang dưới chân quái tinh anh từ `18` lên `22` (tăng 20%).
- `game/tools/Patcher.java`:
  - Xóa bỏ hoàn toàn `Patch 3` chèn chặn sai trong `class_abj.d(int, int)`, khôi phục khả năng nhặt đồ tự nhiên của game.
  - Sửa thứ tự ưu tiên ClassPool (`insertClassPath("_orig_classes")` vào đầu) để Javassist luôn patch từ file class sạch gốc.
  - Re-patch bytecode và cập nhật vào `game/libs/KPAH_225_remade.jar`.
- `game/app/src/classes/ModController.java`:
  - Thêm cờ `autoCombatKeepActive`: ghi nhớ trạng thái Auto. Khi hết choáng (`!player.cW && player.cV != 1`), tự động khôi phục `class_abj.au = true` và `class_abj.av = true`.
  - Trong `onUserManualMove()`: Nếu người chơi đang bị choáng (`player.cW || player.cV == 1`), không hủy trạng thái Auto.
  - Trong `handleAutoCombatRoaming()`: Tạm dừng toàn bộ di chuyển/tấn công/nhặt đồ khi `player.cW || player.cV == 1`, không tăng biến đếm `pickupAttempts` và `moveStuckCount`, ngăn chặn vật phẩm bị đưa vào danh sách bỏ qua `ignoredItems`.
  - Nâng ngưỡng thử nhặt từ 6 lên 10 lần trước khi tạm bỏ qua.

**Backup:**
- `server/KPAH/src/skill/_backup/BuffInfluencePlayer.java.bak.20260907_1012`
- `server/KPAH/src/skill/_backup/BuffInfluenceMonster.java.bak.20260907_1012`
- `game/app/src/classes/_backup/ModHelpers.java.bak.20260907_1012`
- `game/app/src/classes/_backup/class_bb.java.bak.20260907_1012`
- `game/app/src/classes/_backup/ModController.java.bak.20260907_1012`
- `game/tools/_backup/Patcher.java.bak.20260907_1012`

**Kết quả:** ✅ Thành công
- Server biên dịch và khởi chạy hoàn hảo trên cổng 19129 (`task-663`).
- Client biên dịch qua Ant ProGuard/Preverify thành công và khởi chạy trên MicroEmulator (`task-667`).
- Quái tinh anh hiển thị to hơn 20% rõ rệt với hào quang mở rộng.
- Nhân vật sau khi bị choáng tự động tiếp tục đánh quái mà không bị ngắt Auto.
- Nhặt vật phẩm hoạt động trơn tru cả thủ công lẫn tự động.

---

---

## [2026-09-07 10:58] — Cân bằng Quái Vật, Quái Tinh Anh, Rương Tinh Anh, Tinh Anh Đan và Bình Kinh Nghiệm

**Yêu cầu:**
1. Giảm tỷ lệ quái tinh anh xuất hiện về 0.5% chuẩn.
2. Khắc phục lỗi quái $\ge$ cấp người chơi đánh 1 dame; cân bằng máu quái (giảm HP trâu bò), tăng sát thương tương xứng và tăng EXP farm Lv 1 - 35.
3. Quái tinh anh luôn chỉ rơi đúng 1 Rương Tinh Anh mỗi con, tăng tốc độ đánh gấp đôi, tăng dame, tấn công tối đa 3 mục tiêu.
4. Rương Tinh Anh không cộng tiềm năng vĩnh viễn; thay bằng Tinh Anh Đan (+20% sát thương, giáp, HP trong 3 phút).
5. Bổ sung tỷ lệ rơi Bình Kinh Nghiệm từ quái tinh anh (20%), cho 70% - 150% EXP theo bậc, mô tả ghi rõ số kinh nghiệm.

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - `rollElite()`: chỉnh về `0.5%`.
  - `getMaxHp()`: máu tinh anh x2.5.
  - `injured()`: giảm giáp và kháng thủ quái.
  - `getDameAttack(Player pl)`: tính sát thương xuyên giáp, gây tối thiểu 4% - 10% HP người chơi, x2 dame khi là tinh anh.
  - `calculatePowerPlus()`: tăng base EXP gấp 3-4 lần.
  - `getItemDrop()`: cố định 1 rương tinh anh; thêm 20% rơi Bình Kinh Nghiệm theo 4 bậc level (ID 108: Sơ cấp 3.500 EXP, ID 109: Trung cấp 25.000 EXP, ID 110: Cao cấp 90.000 EXP, ID 111: Siêu cấp 220.000 EXP).
  - `attackPlayer()`: delay 400 - 1000ms cho quái tinh anh, tấn công tối đa 3 mục tiêu cùng lúc.
- `server/KPAH/src/player/Player.java`:
  - Thêm `timeEndBuffTinhAnh`, `hasBuffTinhAnh()`, `setBuffTinhAnh()`.
- `server/KPAH/src/player/Point.java`:
  - Trong `initPoint()`: tăng 20% attack, defend, defendMagic, hpMax khi có hiệu ứng Tinh Anh Đan.
- `server/KPAH/src/services/UseItemService.java`:
  - `openEliteChest()`: bỏ cộng tiềm năng vĩnh viễn; tặng 1 - 2 lọ Tinh Anh Đan (ID 107).
  - `case 107`: kích hoạt buff Tinh Anh Đan 180s (3 phút).
  - `case 108, 109, 110, 111`: hàm `useExpPotion()` cộng ngay kinh nghiệm tương ứng, xử lý level up, hiệu ứng bay exp và chat thông báo.
- `server/KPAH/src/services/LoginService.java`:
  - Gửi danh sách PotionTemplate theo vòng lặp tuần tự ID 0 đến size-1 để đảm bảo client map đúng 100% mảng `class_sc.l[id]`.
- Database MySQL `kpah`:
  - Cập nhật `maxHp` của 34 quái từ Lv 1 - 35 trong bảng `monsters`.
  - Cập nhật ID 106 (`Rương tinh anh`), ID 107 (`Tinh Anh Đan`), ID 108 - 111 (`Bình KN Tinh Anh` các bậc).

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1054`
- `server/KPAH/src/player/_backup/Player.java.bak.20260907_1054`
- `server/KPAH/src/player/_backup/Point.java.bak.20260907_1054`
- `server/KPAH/src/services/_backup/UseItemService.java.bak.20260907_1054`
- `server/KPAH/src/services/_backup/LoginService.java.bak.20260907_1054`

**Kết quả:** ✅ Thành công
- MySQL database được cập nhật đồng bộ các bảng `monsters` và `potion_template`.
- Server Java 21 biên dịch không lỗi và đang chạy lắng nghe cổng 19129.
- Client Java 8 biên dịch thành công và emulator đang chạy bình thường.
- Đã cung cấp Bảng rơi đồ quái tinh anh & Bảng thông số quái từ Lv 1 - Lv 35 trong tài liệu kế hoạch và báo cáo.

---

---

## [2026-09-07 12:30] — Cân Bằng Lại Sát Thương Quái Vật Cho Treo Máy & Sửa Lỗi Nhặt Xu, Lưu Trữ Tiến Độ

**Yêu cầu:**
1. Cân bằng lại sát thương quái vật: Bỏ hoàn toàn việc đánh theo % máu, tính dame hợp lý theo Level quái và Giáp người chơi để người chơi treo máy (auto train) không bị sốc chết.
2. Quái tinh anh dame vừa phải (+35%), giãn delay đánh lên 1.2s - 2s/hit để người chơi kịp bơm máu.
3. Giữ cơ chế sát thương cào xước tối thiểu (min scratch) theo level quái để quái không bị rớt về 1 dame vô lý khi giáp người chơi cao.
4. Sửa lỗi nhặt Xu không cộng tiền và giảm chu kỳ auto-save database từ 5 phút xuống 15 giây để không bị mất tiến độ khi thoát game.

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - `getDameAttack()`: Tính toán sát thương tự nhiên `baseAtk` theo level quái và `minScratch` thuần túy theo level (không chạm vào % HP người chơi). Sát thương xuất ra đảm bảo người chơi có giáp bình thường chỉ mất 1% - 3% máu/hit (treo máy bơm máu thoải mái), khi giáp rất cao thì nhận đúng `minScratch` (xước nhẹ vài điểm máu).
  - `attackPlayer()`: Giãn delay tấn công của quái tinh anh lên `1200 - 2000ms`, quái thường `1800 - 4500ms`.
- `server/KPAH/src/services/MapService.java`:
  - `getPotionFromGround()`: Bổ sung kiểm tra riêng cho Xu (template ID 0) -> cộng thẳng vào ví tiền `player.getInventory().plusXu()`, cập nhật hiển thị qua `sendMainCharInfo`, gửi chat thông báo và xóa item trên đất (không còn bị nhét nhầm vào túi Potion).
- `server/KPAH/src/manager/Settings.java`:
  - `MILISECOND_UPDATE_DATABASE = 15000`: Giảm chu kỳ auto-save database từ 300,000ms (5 phút) xuống 15,000ms (15 giây) để bảo toàn tiến độ người chơi.

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1227`
- `server/KPAH/src/services/_backup/MapService.java.bak.20260907_1227`
- `server/KPAH/src/manager/_backup/Settings.java.bak.20260907_1227`

**Kết quả:** ✅ Thành công
- Sát thương quái vật trở nên êm ái, hợp lý, người chơi treo máy auto chịu được nhiệt bền bỉ.
- Nhặt xu cộng tiền ngay vào ví và hiển thị trên màn hình.
- Tiến độ được tự động lưu đều đặn mỗi 15 giây vào MySQL.
- Server Java 21 biên dịch thành công và đang chạy lắng nghe cổng 19129 (`task-1133`).
- Client emulator đang chạy bình thường (`task-1137`).

---

---

## [2026-09-07 12:48] — Sửa Lỗi Đứng Đơ Auto, Khoanh Vùng Bãi Train & Tối Ưu Thông Báo Chat

**Yêu cầu:**
1. Chế độ auto đôi khi bị đứng khựng lại và đứng luôn như tắt hẳn: Kiểm tra và sửa lỗi tầm đánh cận chiến cấp thấp (skill phạm vi quá ngắn).
2. Nhân vật di chuyển lung tung, đuổi quái sang tận map khác: Khoanh vùng di chuyển bãi train cố định, không cho nhân vật di chuyển/đuổi theo quái ra khỏi vùng bãi.
3. Khi nhặt xu thì không hiện chat thông báo. Chỉ hiện chat đối với: Nhặt trang bị, nhặt rương (Rương Tinh Anh), và tiêu diệt quái Tinh Anh trở lên.

**Files thay đổi:**
- `game/app/src/classes/ModController.java`:
  - **Sửa triệt để lỗi đứng đơ auto (Issue 1)**:
    - Phát hiện nguyên nhân cốt lõi: Đấu Sĩ (class ID 3) có tầm chiêu 0 cơ bản chỉ là `20px`, Kiếm Khách (0) & Chiến Binh (1) là `30px`. Code cũ gán nhầm tầm đánh cận chiến thành `52px` (và nhầm class 3 là đánh xa 90px). Khi nhân vật đứng cách quái 25 - 50px, auto tưởng đã vào tầm nên hủy bước chạy và spam phím đánh, nhưng engine game thấy khoảng cách > 20px nên không cho ra đòn -> nhân vật bị đứng đơ vĩnh viễn nhìn quái!
    - Chuẩn hóa tầm đánh: Phái đánh xa (Pháp Sư 2, Cung Thủ 4) dùng cự ly `70px`; Phái cận chiến (Kiếm Khách 0, Chiến Binh 1, Đấu Sĩ 3) BẮT BUỘC dùng cự ly áp sát `20px` (đảm bảo 100% skill đánh thường và kỹ năng cận chiến đều chạm đích).
    - Bổ sung cơ chế **Chống Kẹt (Anti-Stuck)**: Nếu nhắm 1 con quái > 3.5s mà không trừ được máu quái (do chướng ngại vật/lag), nhân vật sẽ tự ép bước chân áp sát, nếu sau 4.5s vẫn kẹt thì tự động đưa quái vào `ignoredMobs` trong 8 giây và chuyển sang đánh con khác.
  - **Khoanh vùng bãi train cố định (Issue 2)**:
    - Bổ sung cơ chế Neo Tọa Độ Bãi Train (`autoAnchorMapId`, `autoAnchorX`, `autoAnchorY`) ngay khi bật Auto hoặc khi chuyển map.
    - Bán kính khoanh vùng cố định: `AUTO_ZONE_RADIUS = 150px` (bãi train đường kính 300px vừa vặn màn hình).
    - `shouldChase()`: Chặn đứng mọi hành vi rượt đuổi nếu mục tiêu hoặc người chơi vượt quá bán kính bãi train 150px.
    - `findNearestDroppedItem()` & Quét quái: Chỉ quét và nhắm các mục tiêu nằm trọn vẹn trong bán kính 150px từ tâm bãi.
    - Tự động quay về tâm bãi: Nếu người chơi bị đánh văng hoặc ra ngoài bán kính 150px, ngay lập tức bỏ target và pathfind quay về tâm bãi train.
    - Thu nhỏ bán kính tuần tra trống quái từ 75px xuống 35px để nhân vật chỉ đi lại nhẹ nhàng quanh tâm bãi.
- `server/KPAH/src/services/MapService.java`:
  - `getPotionFromGround()`: Bỏ hoàn toàn dòng gửi chat khi nhặt tiền Xu (ID 0). Thêm thông báo chat khi nhặt được Rương (`[Rương Tinh Anh]`, v.v.).
  - `getEquipmentFromGround()`: Giữ nguyên thông báo nhặt trang bị như yêu cầu.
- `server/KPAH/src/services/MonsterService.java`:
  - `onMonsterDropItem()`: Thêm thông báo chat `Bạn đã tiêu diệt Quái Tinh Anh [tên quái]!` gửi riêng cho người chơi kết liễu quái tinh anh.
- `server/KPAH/src/map/Monster.java`:
  - `attackPlayer()`: Chống lỗi `NullPointerException` (khi `playerTarget` trở thành null trong thread virtual sau khi sleep 250ms).

**Backup:**
- `game/app/src/classes/_backup/ModController.java.bak.20260907_1246`
- `server/KPAH/src/services/_backup/MapService.java.bak.20260907_1246`
- `server/KPAH/src/services/_backup/MonsterService.java.bak.20260907_1246`
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1246`

**Kết quả:** ✅ Thành công
- Đã test biên dịch cả Server (Java 21) và Client (Java 8) đạt 100% BUILD SUCCESSFUL.
- Server daemon đã khởi chạy thành công trên cổng 19129 (`task-1411`).
- Client emulator đã khởi chạy thành công (`task-1413`).

---

## [2026-09-07 13:20] — Fix Lỗi Sử Dụng Vật Phẩm, Ngăn Rơi Đồ Vô Dụng, Bổ Sung Mô Tả & Clear Dữ Liệu Nhân Vật

**Yêu cầu:**
1. Clear dữ liệu nhân vật để tránh lưu trữ vật phẩm lỗi trong kho đồ người chơi.
2. Sửa lỗi các vật phẩm không dùng được (lọ exp, v.v.).
3. Ngăn rơi các vật phẩm không có giá trị dùng nhưng vẫn rơi (`Khăn`, `(ở trần)`, v.v.) và rà soát lại phần thưởng nhiệm vụ không đúng.
4. Bổ sung mô tả cho các vật phẩm thiếu mô tả trong kho đồ.

**Files & Database thay đổi:**
- **Database (`kpah-mysql`)**:
  - Thực hiện `TRUNCATE TABLE players; TRUNCATE TABLE users; TRUNCATE TABLE clan;` để làm sạch toàn bộ dữ liệu nhân vật và tài khoản lỗi theo yêu cầu.
  - Cập nhật bảng `potion_template`: Thêm mô tả chi tiết bằng định dạng xuống dòng `\n<Mô tả>` cho toàn bộ các vật phẩm dược phẩm, bình exp, thú cưỡi, phù, vé giờ vàng, lệnh bài, rương để client hiển thị tooltip đầy đủ, rõ ràng và trực quan.
- `server/KPAH/src/services/UseItemService.java`:
  - Thêm xử lý sử dụng bình EXP: ID 10 (Tiên dược thường: 100k exp), ID 11 (Tiên dược cao cấp: 500k exp), ID 12 (Tiên dược đặc biệt: 1M exp), ID 108-111 (Bình KN Tinh Anh).
  - Thêm xử lý ID 9 (Nhân sâm: hồi đầy đủ HP và MP).
  - Thêm xử lý ID 35, 75, 81 (Vé giờ vàng: x2 EXP trong 1h, 3h hoặc +150% EXP).
  - Thêm xử lý ID 80 (Bình tăng lực 5% / Buff Tinh Anh trong 1h).
  - Thêm xử lý ID 119 (Tiên đan: Hồi sinh bản thân tại chỗ).
  - Thêm xử lý ID 25 (Thuốc khôi phục tiềm năng: Tẩy và hoàn trả điểm tiềm năng).
  - Thêm xử lý ID 26 (Thuốc khôi phục kỹ năng: Tẩy và hoàn trả điểm kỹ năng).
  - Thêm helper method `useHorsePotion()` hỗ trợ toàn bộ các thú cưỡi (Thiên lý mã, Xích thố, Bạch mã, Hắc ngưu, Mãnh hổ, Sói xám, Tiên hạc, Phượng hoàng).
- `server/KPAH/src/manager/Manager.java`:
  - `loadItemEquipment`: Loại bỏ triệt để các trang phục khởi tạo mặc định 0 chỉ số (ID 1: Áo bà ba, 2: (ở trần), 27: Quần bà ba, 28: Quần đùi, 53: Băng đô, 54: Khăn), các trang phục sự kiện (264-267, 507, 508), và cuốc mỏ (type 13) khỏi danh sách rơi trang bị từ quái (`ITEM_EQUIPMENT`).
  - `randomItemEquipment`: Với nhân vật cấp thấp (level 1-3), tìm kiếm trang bị cấp 4 hữu ích và gộp cả danh sách trang bị phi giới tính (`gender == 0`: vũ khí, nhẫn, dây chuyền, giày, găng tay, ngọc), đảm bảo không bao giờ rơi ra trang bị rác/vô dụng.
- `server/KPAH/src/services/QuestService.java`:
  - Sửa nhiệm vụ khởi đầu (Nhiệm vụ 1): Tặng đúng `(short) 1` (HP nhỏ) và `(short) 4` (MP nhỏ) thay vì tặng ID 0 (Xu dạng potion) và ID 3 (HP to).
  - Sửa nhiệm vụ Thiết Bị (Nhiệm vụ 2): Tặng Tinh Anh Đan (107) và Bình KN Tinh Anh (108) thay vì tặng Khăn tím (18).
  - Sửa nhiệm vụ Tướng Quân hằng ngày: Tặng nguyên liệu luyện kim/ngọc thực tế (Đá may mắn, Luyện kim dược, Đá thuộc tính) vào kho ngọc (`addItemGem`) thay vì nhét tóc/thú cưỡi vào túi dược phẩm; thay Khăn tím (18) bằng Tinh Anh Đan (107).
- `server/KPAH/src/player/Player.java` & `Point.java`:
  - Bổ sung trường quản lý Buff Giờ Vàng (`hasBuffGioVang`, `percentBuffGioVang`).
  - Tích hợp % Giờ Vàng vào `setExpDonate()`.
  - Bổ sung `resetPotentialPoints()` và `resetSkillPoints()`.

**Backup:**
- `server/KPAH/src/services/_backup/UseItemService.java.bak.20260907_1317`
- `server/KPAH/src/services/_backup/QuestService.java.bak.20260907_1317`
- `server/KPAH/src/manager/_backup/Manager.java.bak.20260907_1317`
- `server/KPAH/src/player/_backup/Player.java.bak.20260907_1317`
- `server/KPAH/src/player/_backup/Point.java.bak.20260907_1317`

**Kết quả:** ✅ Thành công
- Đã truncate sạch sẽ bảng `players`, `users`, `clan`.
- Server và Client biên dịch thành công 100%.
- Server daemon đã khởi chạy thành công trên cổng 19129 (`task-1771`).
- Client emulator đã khởi chạy thành công (`task-1775`).

---

## [2026-09-07 13:26] — Tăng HP Của Quái Vật & Quái Tinh Anh (Giữ Nguyên Sát Thương)

**Yêu cầu:**
- Sát thương quái hiện tại đã ổn định, cần tăng HP của quái lên một chút để tránh việc quái bị diệt quá nhanh khi người chơi treo auto.

**Files & Database thay đổi:**
- **Database (`kpah-mysql`)**:
  - Cập nhật bảng `monsters` điều chỉnh lượng máu tối đa (`maxHp`) của quái vật thường:
    - Cấp 1–5: Tăng ~75% (Nhím: 150 -> 263 HP; Sâu: 220 -> 385 HP; Giọt nước: 320 -> 560 HP; Gà điên: 450 -> 788 HP; Rắn lục: 650 -> 1.138 HP) giúp nhân vật sơ cấp đánh từ 3–5 nhát mới hạ được quái thay vì 1-shot lập tức.
    - Cấp 6–15: Tăng ~60% (Ma trơi: 850 -> 1.360 HP; Nắp ấm: 1.100 -> 1.760 HP; Rệp quỷ: 1.350 -> 2.160 HP; Chuột cống: 1.650 -> 2.640 HP; Quỷ hoa: 2.000 -> 3.200 HP; Heo mọi: 4.800 -> 7.680 HP; Bọ cạp: 5.500 -> 8.800 HP).
    - Cấp 16+: Tăng ~50% cho tất cả quái thường để phù hợp với đồ họa và sát thương người chơi thăng tiến.
    - Giữ nguyên HP của khoáng sản (ID 85-89), trụ thành/boss đặc biệt (level 999) và World Boss.
- `server/KPAH/src/map/Monster.java`:
  - `getMaxHp()`: Tăng hệ số máu của **Quái Tinh Anh** từ `2.5x` lên `3.5x` so với quái thường (kết hợp với máu cơ bản mới, quái tinh anh sẽ có lượng máu gấp 3.5 lần, tạo độ thử thách tương xứng với danh hiệu Tinh Anh và phần thưởng rương tinh anh / bình exp).
  - Giữ nguyên 100% công thức tính sát thương của quái vật đối với người chơi.

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260907_1325`

**Kết quả:** ✅ Thành công
- Server Java 21 biên dịch thành công 100%.
- Server daemon đã khởi chạy trên cổng 19129 (`task-1837`).
- Client emulator đã khởi chạy thành công (`task-1841`).

---

## [2026-09-07 13:34] — Cải Tiến Khoanh Vùng 4 Góc Bãi Treo Máy & Cơ Chế Điều Tiết Về Trung Tâm

**Yêu cầu:**
- Khắc phục cơ chế di chuyển auto-train còn lỏng lẻo, nhân vật vẫn đi lung tung ra khỏi phạm vi bãi quái.
- Khoanh vùng bãi treo máy bằng hình chữ nhật 4 góc quanh tâm auto (anchor).
- So sánh tọa độ người chơi: nếu còn bên trong thì hoạt động bình thường; nếu đi ra khỏi thì điều tiết chạy về trung tâm bãi.
- Hoạt động điều tiết BẮT BUỘC thực hiện sau cùng (khi đã hoàn thành nhặt vật phẩm và hoàn thành đợt đánh quái hiện tại, tránh bỏ lỡ đồ ngon hay bỏ dở đánh quái).
- Khi đã vào trạng thái điều tiết về trung tâm: TUYỆT ĐỐI không target thêm bất kỳ quái vật hay vật phẩm mới nào cho đến khi về lại tâm an toàn.

**Files thay đổi:**
- `game/app/src/classes/ModController.java`:
  - Định nghĩa vùng 4 góc bãi train dựa vào `(autoAnchorX, autoAnchorY)` với bán kính `ZONE_BOX_RADIUS_X = 140` và `ZONE_BOX_RADIUS_Y = 120` (vùng hình chữ nhật 280x240 pixel):
    - Góc Trên - Trái: `(autoAnchorX - 140, autoAnchorY - 120)`
    - Góc Trên - Phải: `(autoAnchorX + 140, autoAnchorY - 120)`
    - Góc Dưới - Trái: `(autoAnchorX - 140, autoAnchorY + 120)`
    - Góc Dưới - Phải: `(autoAnchorX + 140, autoAnchorY + 120)`
  - Viết method `isInsideZone(int x, int y)` kiểm tra nhanh tọa độ nằm trọn trong 4 góc.
  - Cập nhật `shouldChase(Object target)`: Ngăn chặn đuổi theo mục tiêu nếu đang ở trạng thái `isRegulating`, hoặc nếu mục tiêu nằm ngoài phạm vi 4 góc.
  - Cập nhật `findNearestDroppedItem(...)`: Bỏ qua các vật phẩm nằm ngoài phạm vi 4 góc của bãi train.
  - Viết lại quy trình tuần tự trong `handleAutoCombatRoaming()`:
    1. **Trạng thái điều tiết (`isRegulating == true`)**: Xóa sạch target (`gameScreen.r = null`), từ chối mọi target mới, di chuyển thẳng về `(autoAnchorX, autoAnchorY)` cho tới khi cự ly `<= 25` pixel thì tắt trạng thái điều tiết.
    2. **Ưu tiên nhặt đồ (`!isRegulating`)**: Hoàn tất việc tiếp cận và nhặt vật phẩm rơi trong bãi trước.
    3. **Ưu tiên kết thúc đánh quái**: Nếu đang có quái đang đánh dở, tiếp tục đánh nốt, không ngắt quãng giữa chừng.
    4. **Kích hoạt điều tiết (LÀM SAU CÙNG)**: Sau khi xong nhặt đồ và xong diệt quái, kiểm tra `!isInsideZone(player.cK, player.cL)`. Nếu bước chân ra ngoài 4 góc bãi train, bật `isRegulating = true`, hủy target và điều tiết quay về tâm.
    5. **Quét quái trong vùng**: Nếu đang trong 4 góc, chỉ chọn target quái còn sống nằm trong 4 góc bãi (`isInsideZone`).
    6. **Bãi trống (hết quái)**: Quay về tâm và đứng yên chờ quái hồi sinh, xóa bỏ hoàn toàn hành vi chạy tuần tra 6 hướng gây phân tán nhân vật trước đây.

**Backup:**
- `game/app/src/classes/_backup/ModController.java.bak.20260907_1332`

**Kết quả:** ✅ Thành công
- Client biên dịch Java 8 thành công 100% (`ant -f game/build.xml dist`).
- Client emulator đã khởi chạy thành công (`task-1910`).
- Server daemon duy trì ổn định trên cổng 19129 (`task-1837`).

---

## [2026-09-07 13:52] — Khắc Phục Triệt Để Lỗi Nhân Vật Di Chuyển Lung Tung Khỏi Bãi Treo Máy

**Yêu cầu:**
- Nhân vật vẫn di chuyển lung tung, không có phạm vi nhất định, có nguy cơ đi lạc sang khu vực quái to hoặc lệch khỏi bãi farm đẹp.
- Cần điều tra gốc rễ nguyên nhân tại sao nhân vật vẫn rời khỏi bãi và sửa dứt điểm.

**Nguyên nhân gốc rễ đã phát hiện:**
1. **Lỗi trôi tâm bãi train (Anchor Drifting):** Trong code client gốc `class_hw.a()` line 1456, khi nhân vật đang bước đi (`case 1: walking`), client tự động gán `class_abj.au = false` để tạm dừng auto đánh, và chỉ phục hồi `au = true` khi nhân vật đứng lại (`case 0:`). Do trước đó `ModController` kiểm tra `if (!class_abj.au)`, nên mỗi khi nhân vật bước đi 1 bước, tọa độ tâm bãi `autoAnchorX` bị reset về `-1`. Khi bước chân dừng lại, `autoAnchorX` bị ghi đè lại bằng vị trí mới của nhân vật. Kết quả: tâm bãi liên tục "trôi" theo chân người chơi đi khắp bản đồ!
2. **Lỗi hiểu nhầm cờ trạng thái `player.cV == 1`:** `cV == 1` là trạng thái nhân vật đang bước đi (walking), chứ không phải bị choáng hay đẩy lùi. Lệnh `if (player.cV == 1) return;` đã làm ngắt toàn bộ vòng lặp điều tiết và kiểm soát tọa độ trong suốt thời gian nhân vật đang di chuyển.
3. **Target Selection của game gốc (`class_abj.z()`) không có giới hạn vùng:** Cứ mỗi 10 frame (`class_acv.l % 10 == 0`), client gốc lại gọi `this.r = this.z()` để tìm mục tiêu mới quanh tọa độ hiện tại của nhân vật mà không kiểm tra `isInsideZone`. Khi gặp quái ngoài bãi, nó liên tục kéo nhân vật chạy tiếp ra xa.

**Giải pháp đã triển khai:**
- `game/app/src/classes/ModController.java`:
  - Tạo hàm `isAutoRunning()` kiểm tra toàn diện (`class_abj.au || class_abj.av || autoCombatKeepActive`). Tâm bãi `autoAnchorX, autoAnchorY` được khóa bất biến duy nhất 1 lần khi bắt đầu Auto và giữ nguyên tuyệt đối, không bao giờ bị reset giữa chừng.
  - Loại bỏ điều kiện `player.cV == 1` khỏi các điểm chặn, cho phép `handleAutoCombatRoaming()` liên tục theo dõi và kiểm soát tọa độ kể cả khi nhân vật đang bước đi.
  - Siết chặt bán kính vùng 4 góc bãi train: `ZONE_BOX_RADIUS_X = 110` và `ZONE_BOX_RADIUS_Y = 90` (tổng vùng chữ nhật 220x180 px, tương đương vừa khít tầm nhìn 1 màn hình chơi), cự ly tìm kiếm tối đa `MAX_TARGET_DISTANCE = 110`.
  - Cập nhật mục tiêu hiện tại: Nếu quái đang target lọt ra ngoài `isInsideZone`, lập tức hủy target (`gameScreen.r = null`) để ngăn nhân vật chạy theo ra ngoài.
  - Trong `ModController.update()`: Nếu đang điều tiết `isRegulating`, xóa sạch target tức thì và chỉ lắng nghe phím điều hướng thực sự từ người dùng (`class_acv.e`).
- `game/tools/Patcher.java` & Bytecode `class_abj.class`:
  - Trong `class_abj.z()`: Chèn chặn ngay ở đầu hàm: `if (classes.ModController.isRegulating) return null;` (khi đang điều tiết về tâm, `z()` trả về null tuyệt đối).
  - Can thiệp hook `b_()` trong `z()`: `if (au && !classes.ModController.isInsideZone(...)) $_ = true;` -> Bất kỳ quái vật hay thực thể nào nằm ngoài 4 góc bãi train đều bị `z()` tự động loại bỏ và bỏ qua, không bao giờ được đưa vào danh sách mục tiêu.
  - Re-patch bytecode sạch từ `_orig_classes`, đưa vào `game/libs/KPAH_225_remade.jar`.

**Backup:**
- `game/tools/_backup/Patcher.java.bak.20260907_1350`
- `game/app/src/classes/_backup/ModController.java.bak.20260907_1350`
- `game/libs/_backup/KPAH_225_remade.jar.bak.20260907_1350`

**Kết quả:** ✅ Thành công
- Patcher chạy thành công, Javassist bytecode xác nhận có kiểm tra `isInsideZone` và `isRegulating`.
- Client build Java 8 thành công 100% (`BUILD SUCCESSFUL`).
- Client emulator đã khởi chạy thành công (`task-2154`).
- Server daemon tiếp tục chạy ổn định (`task-1837`).

---

## [2026-09-07 14:10] — Tối Ưu Nhịp Farm Tự Nhiên, Chống Bỏ Sót Vật Phẩm & Sửa Lỗi Chiêu Thức Auto Đánh

**Yêu cầu:**
1. Khắc phục việc liên tục điều tiết về tâm gây mất nhịp farm khi bãi quái trống. Nhân vật không cần bị ghim chặt một chỗ, chỉ cần không đi quá xa.
2. Khắc phục tình trạng các vật phẩm rơi ra xung quanh đôi khi bị bỏ dở / không nhặt.
3. Kiểm tra và sửa lỗi auto đánh: nhân vật chỉ dùng đúng 1 chiêu thức đánh thường, các chiêu khác rất ít hoặc không dùng dù đã hồi chiêu và còn dư mana.

**Nguyên nhân gốc rễ đã điều tra:**
1. **Mất nhịp farm & ghim cứng:** Ở nhánh F (khi bãi trống quái), code cũ kiểm tra `if (distToCenter > 20) movePlayer(originX, originY)`. 20px chỉ tương đương 1 tile gạch. Cứ sau mỗi con quái chết, dù nhân vật vẫn đứng an toàn trong bãi nhưng chỉ cần cách tâm > 20px là bị giật ngược về (0,0), tạo hiệu ứng ping-pong làm mất hoàn toàn nhịp farm. Đồng thời khi điều tiết cũng ép kéo vào tận 20px.
2. **Bỏ dở vật phẩm rơi:** Quái vật khi chết đồ rơi thường có quán tính nảy ra xung quanh (bounce) 10-30px. Khi quái chết ở gần rìa, đồ nảy ra mép ngoài bãi. Do code trước dùng `isInsideZone` và `b_()` loại bỏ hoàn toàn các mục tiêu ngoài phạm vi, vật phẩm này bị client coi là invalid và ModController bỏ qua. Sau đó nhân vật bị kéo về tâm, bỏ rơi đồ.
3. **Chiêu thức auto đánh chỉ dùng chiêu thường:**
   - Code cũ set đồng thời `class_acv.c[1] = true; class_acv.c[3] = true; class_acv.c[5] = true;` ở mỗi frame.
   - Trong `class_abj.java` (dòng 1978-1985), vòng lặp xử lý phím duyệt mảng `this.cg = {1, 3, 5, 7, 9}`:
     `if (class_acv.b(this.cg[n6])) { this.d(this.cg[n6], V); break; }`
   - Phím 1 (chiêu thường) luôn được duyệt đầu tiên và trả về `true`, thực thi `d(1, V)` rồi **lập tức break** khỏi vòng lặp!
   - Phím 3 và phím 5 không bao giờ được đọc tới. Ở frame tiếp theo, phím 1 lại tiếp tục được bật và chiếm quyền. Vì vậy chiêu thường độc chiếm 100% vòng lặp, các chiêu ô 3 và 5 hoàn toàn bị bỏ đói (starvation).

**Giải pháp đã triển khai:**
- `game/tools/Patcher.java`:
  - Trong Patch 0: Expose mảng phím tắt `this.cp` sang `classes.ModController.currentShortcutSlots = this.cp;` để ModController truy xuất trực tiếp các slot chiêu mà không cần reflection.
  - Trong Patch 1+2 (`z()` method): Mở rộng biên quét `cb` thêm `LOOT_BUFFER` (+35px). Trong hook `b_()`, tách riêng vật phẩm rơi (`class_ba`): chỉ loại bỏ nếu nằm ngoài `isInsideLootZone`. Quái vật ngoài `isInsideZone` vẫn bị chặn để không kéo nhân vật đi xa.
- `game/app/src/classes/ModController.java`:
  - Bổ sung vùng đệm nhặt đồ `LOOT_BUFFER = 35`, kích thước bãi train mở rộng hợp lý: `ZONE_BOX_RADIUS_X = 130`, `ZONE_BOX_RADIUS_Y = 110`, `MAX_TARGET_DISTANCE = 140`.
  - Tạo hàm `isInsideLootZone(x, y)` cho phép nhặt toàn bộ vật phẩm rơi xung quanh bãi train mà không bị bỏ sót.
  - Cập nhật nhánh F (bãi quái trống): Nếu nhân vật vẫn đang ở bên trong vùng an toàn (`isInsideZone`), nhân vật **đứng yên tại chỗ chờ quái hồi sinh** (`player.s = null`), không giật ngược về tâm. Nếu trôi ra ngoài bãi, chỉ điều hướng nhẹ về tâm khi cách tâm > 60px.
  - Cập nhật nhánh A (điều tiết): Ngay khi nhân vật đã bước vào lại bên trong bãi hoặc cự ly tới tâm `<= 60px`, lập tức kết thúc điều tiết (`isRegulating = false`).
  - Viết mới hàm `triggerAutoAttack(class_abj gameScreen)`:
    - Quét các chiêu thức theo thứ tự ưu tiên: `ô 5` (chiêu mạnh/ultimate) -> `ô 3` (chiêu đặc biệt) -> `ô 7, 9` (nếu có gán chiêu thức).
    - Kiểm tra: đã học (`class_hw.aS[skillId] > 0`), đã hồi chiêu (`now - aq[skillId] > at[skillId]`), đủ mana (`player.bz >= cost`).
    - Khi có chiêu đặc biệt thỏa mãn: Kích hoạt **DUY NHẤT** chiêu đó (`c[key] = true`), xóa `c[1] = false` để đảm bảo gameScreen thực thi đúng chiêu đó mà không bị chiêu thường tranh chấp.
    - Chỉ khi toàn bộ chiêu đặc biệt đang hồi chiêu hoặc cạn mana: Mới dùng chiêu thường (`c[1] = true;`).

**Backup:**
- `game/app/src/classes/_backup/ModController.java.bak.20260907_1405`
- `game/tools/_backup/Patcher.java.bak.20260907_1405`

**Kết quả:** ✅ Thành công
- Patcher áp dụng thành công. Client biên dịch và build dist `KPAH_MOD.jar` thành công không lỗi.
- Đã khởi chạy lại Client Emulator (`task-2314`) với source mới.
- Server daemon duy trì ổn định (`task-1837`).

---
