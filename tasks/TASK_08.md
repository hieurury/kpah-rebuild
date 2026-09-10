# KPAH Project — Task Log (Phần 08: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #80), tiến hành lưu trữ file thành `tasks/TASK_08.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
>
> **Lịch sử các phần trước:**
> - [Phần 01 (Task 1 - 10)](tasks/TASK_01.md)
> - [Phần 02 (Task 11 - 20)](tasks/TASK_02.md)
> - [Phần 03 (Task 21 - 30)](tasks/TASK_03.md)
> - [Phần 04 (Task 31 - 40)](tasks/TASK_04.md)
> - [Phần 05 (Task 41 - 50)](tasks/TASK_05.md)
> - [Phần 06 (Task 51 - 60)](tasks/TASK_06.md)
> - [Phần 07 (Task 61 - 70)](tasks/TASK_07.md)

---

## [2026-09-09 23:51] — Fix nhẫn: Cho phép đeo 2 nhẫn (Nhẫn Trên / Nhẫn Dưới)

**Yêu cầu:** Hiện tại chỉ cho đeo 1 nhẫn, không cho chọn nhẫn trên hay nhẫn dưới.  
Cần cho phép đeo đồng thời 2 chiếc nhẫn với UI menu chọn "Đeo nhẫn trên" / "Đeo nhẫn dưới".

**Files thay đổi:**
- `server/KPAH/src/services/InventoryService.java` — Thêm hàm `findItemBodyRingBySlot(player, viTriVe)` để tra nhẫn theo slot.
- `server/KPAH/src/services/UseItemService.java` — Thêm overload `useItemEquipment(player, index, slot)`. Xử lý riêng type==8 (nhẫn): theo slot (1=trên, 2=dưới, 0=auto lấp đầy ô trống). Sửa bug `equipment.getLevel() > equipment.getLevel()` → `player.getInfo().getLevel()`.
- `server/KPAH/src/network/MessageHandler.java` — Case `USE_ITEM`: đọc thêm `byte ringSlot` nếu available() > 0, tương thích ngược 100%.
- `game/app/src/classes/class_go.java` — Thêm overload `g(short var1, byte var2)` ghi thêm 1 byte slot vào packet 29.
- `game/app/src/classes/class_ul.java` — Tạo mới, clone từ decompile với trường `b` là `public` để class_dp inspect được.
- `game/app/src/classes/class_dp.java` — Thêm `wearRing(ql, slot)` helper và logic chặn menu "Sử dụng" khi là nhẫn (type==8), thay bằng 2 mục "Đeo nhẫn trên" và "Đeo nhẫn dưới".

**Kết quả:** ✅ Thành công — Server & Client biên dịch sạch, server khởi động lại Port 19129.

**Ghi chú:**
- Backup:
  - `server/KPAH/src/services/_backup/InventoryService.java.bak.20260909_2350`
  - `server/KPAH/src/services/_backup/UseItemService.java.bak.20260909_2350`
  - `server/KPAH/src/network/_backup/MessageHandler.java.bak.20260909_2350`
  - `game/app/src/classes/_backup/class_go.java.bak.20260909_2346`
  - `game/app/src/classes/_backup/class_dp.java.bak.20260909_2346`
- Phần 08 hiện có: 1/10 task.

---

## [2026-09-10 00:00] — Fix NPE: Nhặt vật phẩm crash server (Opcode 19)

**Yêu cầu:** Server crash và disconnect người chơi khi nhặt vật phẩm từ đất. Lỗi:  
`NullPointerException: Cannot invoke "PotionTemplate.getId()" because "ItemPotion.getTemplate()" is null`

**Nguyên nhân gốc rễ:** Quái tinh anh drop item với `itemTemplateID` không tồn tại trong `POTION_TEMPLATES`.  
`Manager.getPotionTemplate()` trả về `null`, nhưng code không guard → NPE tại `addItemPotion`.

**Files thay đổi:**
- `server/KPAH/src/services/ItemService.java` — Sửa `createNewItemPotion(short, int)`: kiểm tra null template trước khi build, trả về `null` thay vì build item lỗi. Fix ép kiểu `(byte)` → `(short)`.
- `server/KPAH/src/services/InventoryService.java` — Sửa `addItemPotion()`: bỏ `@NonNull`, thêm guard `if (itemPotion == null || itemPotion.getTemplate() == null) return`.

**Kết quả:** ✅ Thành công — BUILD SUCCESSFUL, server Port 19129 online, không còn NPE.

**Ghi chú:** Backup: `server/KPAH/src/services/_backup/ItemService.java.bak.20260910_0000`. Phần 08: 2/10 task.

---

## [2026-09-10 13:13] — Build và khởi chạy Game Client để test

**Yêu cầu:** Chạy game client để test tính năng sau khi cập nhật.
**Files thay đổi:**
- `game/build/dist/kpah_mod_v1.0.0.1.jar` — Build client kết nối tới server Dị giới (`bore.pub:19129`).
- `game/build/dist/kpah_mod_v1.0.0.1_local.jar` — Build client kết nối tới `127.0.0.1:19129`.
**Kết quả:** ✅ Thành công — Biên dịch thành công với OpenJDK 8, giả lập MicroEmulator đã được khởi chạy hiển thị trực tiếp trên màn hình (`DISPLAY=:0`).
**Ghi chú:** Phần 08 hiện có: 3/10 task.

## [2026-09-10 13:50] — Sửa lỗi nhặt rương tinh anh, sửa lỗi nhân vật tàng hình và bổ sung log server chi tiết

**Yêu cầu:**
1. Tiêu diệt quái tinh anh không thấy nhận được Rương Tinh Anh trong túi đồ.
2. Sửa lỗi sau khi đánh quái tinh anh nhân vật bị biến mất / tàng hình (do client crash ngoại lệ mảng khi nhặt vật phẩm tùy biến).
3. Thêm log server chi tiết: phần thưởng quái tinh anh rơi ra, người chơi nhặt vật phẩm, mở rương tinh anh, dùng bình exp.
4. Rà soát, kiểm tra đối chiếu toàn bộ logic và tài nguyên hình ảnh các vật phẩm tự định nghĩa (Rương Tinh Anh Bậc 1-4, Tinh Anh Huyết, Tinh Anh Đan).

**Nguyên nhân gốc rễ:**
1. `MapService.getPotionFromGround` thiếu gọi `InventoryService.instance.sendItemPotion(player);` sau khi nhặt rương/thuốc → Client không được gửi packet cập nhật túi đồ nên không hiển thị rương mới nhặt.
2. Ở client game gốc, mảng `bq` (lưu potion nhanh) chỉ có kích thước mặc định 25. Khi nhặt item tự định nghĩa có ID >= 25 (như Rương Tinh Anh ID 106, 160 hoặc Tinh Anh Huyết 108..111), client bị lỗi `ArrayIndexOutOfBoundsException` trong packet 19 (`GET_POTION_FROM_GROUND`) dẫn đến đứt luồng xử lý và nhân vật bị biến mất.

**Files thay đổi:**
- `server/KPAH/src/services/MapService.java`:
  - Thêm `InventoryService.instance.sendItemPotion(player);` khi nhặt potion/rương từ đất.
  - Bổ sung `utils.ServerLog.combat` ghi log chi tiết khi nhặt: Dược phẩm / Rương, Trang bị, Ngọc / Nguyên liệu, và Tiền xu.
- `server/KPAH/src/services/MonsterService.java`:
  - Bổ sung `utils.ServerLog.combat` liệt kê chi tiết toàn bộ các vật phẩm rơi ra khi tiêu diệt Quái Tinh Anh (tên item, số lượng, ID, category).
- `server/KPAH/src/services/UseItemService.java`:
  - Thêm server log khi mở Rương Tinh Anh (ghi nhận các món đồ nhận được) và khi sử dụng Tinh Anh Huyết.
- `game/app/src/classes/MsgHandler.java`:
  - Chặn và mở rộng an toàn mảng `bq` khi nhận Opcode 19 (`GET_POTION_FROM_GROUND`), tránh lỗi `ArrayIndexOutOfBoundsException` đối với các item ID >= 25.
  - Bỏ re-throw exception để bảo vệ luồng xử lý mạng của client.

**Kết quả:** ✅ Thành công — Cả Client và Server đều build thành công không lỗi.
**Ghi chú:**
- Backup files:
  - `game/app/src/classes/_backup/MsgHandler.java.bak.*`
  - `server/KPAH/src/services/_backup/MapService.java.bak.*`
  - `server/KPAH/src/services/_backup/MonsterService.java.bak.*`
  - `server/KPAH/src/services/_backup/UseItemService.java.bak.*`
- Phần 08 hiện có: 4/10 task.

---

## [2026-09-10 14:05] — Tối ưu hóa triệt để cơ chế Ưu tiên Quái Tinh Anh (Khắc phục lỗi ghi đè mục tiêu và bỏ dở giữa chừng)

**Yêu cầu:**
- Khắc phục tình trạng cơ chế ưu tiên quái Tinh Anh bị ghi đè/trùng lặp với bộ chọn mục tiêu gốc của client.
- Đảm bảo hệ thống ưu tiên đánh quái Tinh Anh trong toàn khu vực map (không bị giới hạn bởi hộp bãi train 130px hay bán kính hẹp).
- Khắc phục lỗi đang đánh quái Tinh Anh giữa chừng hay bị bỏ qua (do nhặt rác chen ngang, timeout 4.5s đưa vào danh sách đen, hoặc cơ chế điều tiết kéo về tâm).

**Nguyên nhân gốc rễ:**
1. **Lỗi ghi đè mục tiêu (`this.r = this.z()` mỗi 10 tick):**
   - Game engine liên tục gọi `z()` quét mục tiêu trong phạm vi hộp nhìn `cb`. `z()` chọn con quái thường gần nhất và ghi đè `this.r`. `ModController` lại phát hiện target quái thường và gán lại Quái Tinh Anh, dẫn đến hai cơ chế liên tục tranh chấp và ghi đè lẫn nhau.
2. **Lỗi bỏ dở giữa chừng:**
   - **Nhặt rác chen ngang:** Section B (nhặt đồ) chạy trước kiểm tra quái. Khi quái thường chết rớt đồ, bot lập tức chuyển target sang item và bỏ Quái Tinh Anh.
   - **Timeout 4.5s:** Quái tinh anh trâu máu/giáp cao, nếu sau 4.5s HP chưa giảm (hoặc đang tiếp cận), code đưa quái vào `ignoredMobs` và bỏ qua 8 giây.
   - **Hộp bãi train 130px kéo ngược về tâm:** Khi quái tinh anh hoặc người chơi bước ra ngoài phạm vi 130px, `isRegulating` kích hoạt kéo người chơi về tâm và hủy target quái tinh anh.
3. **Phạm vi nhận diện quá hẹp:**
   - `findNearestEliteMob` trước đây bị kẹp bởi `isInsideZone` và `MAX_TARGET_DISTANCE` (140px), khiến Quái Tinh Anh xuất hiện ở cự ly xa hơn trong khu vực không được bot phát hiện.

**Files thay đổi:**
- `game/tools/Patcher.java`:
  - Thêm can thiệp trực tiếp vào đầu method `class_abj.z()`: Khi `isPrioritizeElite` bật, gọi `ModController.getEliteTarget(this)` và trả về ngay Quái Tinh Anh nếu có. Loại bỏ 100% việc `z()` ghi đè target Quái Tinh Anh thành quái thường.
  - Sửa `b_()`: Không bao giờ đánh dấu Quái Tinh Anh là out-of-zone.
- `game/app/src/classes/ModController.java`:
  - Thêm helper `getEliteTarget(gameScreen)`: Khóa chặt mục tiêu Quái Tinh Anh còn sống, không bao giờ nhả target cho đến khi quái chết (`mob.cV == 5 || mob.v <= 0`).
  - Sửa `findNearestEliteMob`: **Chỉ quét Quái Tinh Anh trong bãi train** (`isInsideZone`), tránh nhân vật chạy loạn xạ khắp bản đồ.
  - Sửa `shouldChase`: Nếu quái Tinh Anh đã là mục tiêu đang đánh thì dù có chạy ra khỏi bãi train vẫn tiếp tục đuổi theo tiêu diệt.
  - Sửa `isInsideZone(x, y)`: Giữ nguyên giới hạn tọa độ 4 góc của bãi train.
  - Tối ưu `handleAutoCombatRoaming`:
    - Khi có Quái Tinh Anh: Vô hiệu hóa ngay `isRegulating` (không kéo về tâm bãi).
    - Tạm dừng nhặt rác thường khi đang có Quái Tinh Anh để dồn 100% hỏa lực tiêu diệt.
    - Loại bỏ hoàn toàn timeout 4.5s và blacklist `ignoredMobs` đối với Quái Tinh Anh.
    - Chỉ sau khi Quái Tinh Anh bị tiêu diệt hoàn toàn mới nhặt chiến lợi phẩm rơi ra và điều tiết về tâm bãi.

**Kết quả:** ✅ Thành công — Đã biên dịch sạch sẽ `ant dist-prod` tạo file jar `kpah_mod_v1.0.0.1.jar` sẵn sàng thử nghiệm.
**Ghi chú:**
- Backup:
  - `game/tools/_backup/Patcher.java.bak.20260910_1403`
  - `game/app/src/classes/_backup/ModController.java.bak.20260910_1403`
- Phần 08 hiện có: 5/10 task.

---

## [2026-09-10 14:34] — Bổ sung cơ chế khoảng cách Level cho Quái Thường (Giảm Dame & Giảm EXP)

**Yêu cầu:**
- Với quái thường, áp dụng cơ chế khoảng cách level để giảm sức mạnh của quái thường lên người chơi khi người chơi có level cao hơn quái:
  - Cứ chênh lệch 1 level (`playerLevel - mobLevel > 0`), quái bị giảm **20% sát thương** lên người chơi, **tối đa giảm 80%** (đạt mức trần khi cách >= 4 level).
  - Cứ chênh lệch 1 level, **kinh nghiệm (EXP)** quái mang lại cho người chơi giảm đi **10%**, **tối đa giảm 50%** (đạt mức trần khi cách >= 5 level) để cân bằng lại game.
- **Phạm vi áp dụng:** Tuyệt đối KHÔNG áp dụng cho Quái Tinh Anh, Quái Cao Cấp / Thủ Lĩnh, và Quái Boss. CHỈ áp dụng riêng cho Quái Thường.

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - Thêm phương thức `isNormalMonster()`: Xác định chính xác quái thường (`!isElite && template.getType() == 0 && !isKhoangSan() && !canNotAttackPlayer()`).
  - Cập nhật `getDameAttack(Player pl)`: Khi người chơi cao cấp hơn quái thường (`diffLevel > 0`), giảm sát thương tịnh (`netDmg`) theo tỷ lệ `Math.min(80, diffLevel * 20)%`.
  - Cập nhật `calculatePowerPlus(Player pl, int damage)`: Khi người chơi cao cấp hơn quái thường (`diffLevel > 0`), giảm lượng EXP nhận được `tnPl` theo tỷ lệ `Math.min(50, diffLevel * 10)%`.

**Kết quả:** ✅ Thành công — Đã biên dịch sạch sẽ bằng Java 21 (`ant jar` tạo `server/KPAH/dist/KPAH.jar`).
**Ghi chú:**
- Backup: `server/KPAH/src/map/_backup/Monster.java.bak.20260910_1432`
- Phần 08 hiện có: 6/10 task.

---

## [2026-09-10 14:58] — Cập nhật cơ chế độ bền trang bị hỏng, di dời HUD độ bền chi tiết sang phải & đồng bộ Buffs/Tinh Anh Đan

**Yêu cầu:**
1. **Kiểm tra và sửa cơ chế độ bền trang bị (Server):** Khi trang bị hỏng (`durable <= 0`), không được cộng bất kỳ chỉ số nào (giáp, công, HP, MP, thuộc tính) vào người chơi. Khi trang bị hỏng trong chiến đấu hoặc khi sửa chữa đồ, tự động tính toán lại chỉ số và cập nhật cho client ngay lập tức.
2. **Di dời HUD độ bền (Client):** Chuyển từ góc trái sang góc trên bên phải (dưới thanh target mob, `y = 38`), hiển thị chi tiết độ bền của từng món trang bị đang mặc (Vũ khí, Áo, Quần, Nón, Giày, Găng tay, Nhẫn, Dây chuyền, Ngọc bội). Nếu hỏng hiển thị `0 (HỎNG)` màu đỏ nhấp nháy.
3. **Thiết kế lại HUD bên trái (Client):** Dòng 1 là Tọa độ, dòng 2 là một đường kẻ line ngăn cách, phía dưới line là danh sách Effect:
   - Màu xanh lá (`class_d.j[1]`): Buff có lợi.
   - Màu đỏ (`class_d.j[2]`): Debuff bất lợi.
   - Không hiển thị thời gian hồi chiêu của skill.
   - Mô tả chi tiết các hiệu ứng được buff hoặc debuff kèm thời gian (ví dụ: `buff exp 100%: 1h20p`, `+20% dame: 3p20s`, `Trúng độc: 20s`, `-10% giáp: 10s`...).
4. **Đồng bộ hiệu ứng Tinh Anh Đan / Giờ Vàng (Server & Client):** Tạo packet `CMD_CUSTOM_BUFF` (`-115`) đồng bộ thời gian hiệu lực của Tinh Anh Đan (`+20% dame: ...`) và Vé Giờ Vàng khi sử dụng, khi đăng nhập và khi chuyển map, hiển thị trực quan trên HUD.

**Files thay đổi:**
- `server/KPAH/src/services/InventoryService.java`:
  - Sửa `sumAttributeValueForId`: chỉ tính chỉ số của item khi `durable > 0`.
  - Sửa `repairItem`: gọi `initPoint()` và `sendMainCharInfo()` cập nhật chỉ số sau khi sửa đồ.
- `server/KPAH/src/player/Player.java`:
  - Trong `injured`: khi độ bền trang bị giáp giảm về 0, tự động re-init point và gửi info mới cho người chơi.
- `server/KPAH/src/services/SkillService.java`:
  - Khi vũ khí trừ độ bền về 0, tự động re-init point và gửi info.
  - Khi tự động sửa chữa vũ khí bằng Thẻ Mua Bán trong `checkWeaponUsable`, re-init point và gửi info.
- `server/KPAH/src/services/UseItemService.java`:
  - Khi dùng Thẻ Mua Bán sửa trang bị (case 33): re-init point và gửi info.
  - Khi dùng Tinh Anh Đan (107), Bình tăng lực (80), Vé giờ vàng (35, 75, 81): gọi `sendCustomBuffs(player)`.
- `server/KPAH/src/utils/CommandMessage.java` & `server/KPAH/src/services/Service.java`:
  - Thêm `CMD_CUSTOM_BUFF = -115;` và phương thức `sendCustomBuffs(Player player)`.
- `server/KPAH/src/services/LoginService.java` & `ChangeMapService.java`:
  - Gửi `sendCustomBuffs(pl)` khi đăng nhập và khi chuyển map.
- `game/app/src/classes/MainCharInfo.java`:
  - Thêm `EquipDurability` và `getEquipDurabilityList()` quét các trang bị đang mặc (Vũ khí, Áo, Quần, Nón, Giày, Găng tay, Nhẫn, Dây chuyền, Ngọc bội).
  - Thêm model `CustomBuff` nhận danh sách buff từ server.
  - Cập nhật `getActiveBuffItems()`: phân định màu chuẩn xác (xanh lá cho buff, đỏ cho debuff), lọc bỏ cooldown skill tấn công, định dạng thời gian `XhYp` / `XpYs` / `Xs`.
- `game/app/src/classes/MsgHandler.java`:
  - Bắt opcode `-115` (`CMD_CUSTOM_BUFF`) để tiếp nhận buff từ server.
- `game/app/src/classes/Paint.java`:
  - Tách hàm `paintRightEquipDurability(g)` vẽ bảng độ bền chi tiết bên phải (y = 38).
  - Tái cấu trúc `paintLeftInfoAndBuffs(g)`: Dòng 1 Tọa độ $\rightarrow$ Đường line ngăn cách $\rightarrow$ Danh sách Effect chi tiết phân màu xanh lá/đỏ.

**Kết quả:** ✅ Thành công — Cả Server và Client đều biên dịch không lỗi (`BUILD SUCCESSFUL`). Giả lập client MicroEmulator đã được khởi chạy với bản build mới nhất.
**Ghi chú:**
- Backup files:
  - `server/KPAH/src/services/_backup/InventoryService.java.bak.*`
  - `server/KPAH/src/services/_backup/SkillService.java.bak.*`
  - `server/KPAH/src/services/_backup/UseItemService.java.bak.*`
  - `server/KPAH/src/services/_backup/Service.java.bak.*`
  - `server/KPAH/src/player/_backup/Player.java.bak.*`
  - `game/app/src/classes/_backup/MainCharInfo.java.bak.*`
  - `game/app/src/classes/_backup/MsgHandler.java.bak.*`
  - `game/app/src/classes/_backup/Paint.java.bak.*`
- Phần 08 hiện có: 7/10 task.

---

## [2026-09-10 15:08] — Sửa hiển thị Debuff Trúng Độc từ Quái Tinh Anh & Sửa lỗi hao mòn độ bền Giày, Găng tay, Nhẫn khi chiến đấu

**Yêu cầu:**
1. **Hiệu ứng Trúng Độc từ Quái Tinh Anh:** Trước đây bị hiển thị nhầm thành buff "Tẩm Độc" màu xanh lá. Cần sửa lại thành Debuff "Trúng Độc" màu đỏ và rà soát toàn bộ các debuff của quái tinh anh (Choáng, Trúng Độc) đảm bảo hiển thị chuẩn màu đỏ.
2. **Hao mòn độ bền trang bị (Giày, Găng tay, Nhẫn):** Kiểm tra vì sao khi chiến đấu các trang bị này không thấy bị hư hại/trừ độ bền. Nếu là lỗi thì chỉnh lại.

**Nguyên nhân gốc rễ:**
1. **Lỗi hiển thị Trúng Độc thành Tẩm Độc:**
   - Khi quái tinh anh tấn công gây độc (`BUFF_DOC_TO`), client tạo đối tượng `class_zx` có ID là 22 để vẽ hiệu ứng bong bóng độc quanh nhân vật.
   - Trong `MainCharInfo.java`, switch case 22 đang bị gán cố định là `"Tẩm Độc"` với màu xanh lá buff (`isDebuff = false`).
   - Do đó khi người chơi bị quái tinh anh tấn công trúng độc, HUD bên trái lại hiển thị dòng chữ "Tẩm Độc: 5s" màu xanh lá (như một buff có lợi).
2. **Lỗi độ bền Giày, Găng tay, Nhẫn:**
   - **Với Giày (Type 10) & Găng tay (Type 11):** Nằm trong `item.isArmor()`. Server có trừ độ bền trong `Player.injured()`, NHƯNG Server **chỉ gửi** `sendItemBody` cập nhật cho client khi `anyBroken == true` (độ bền về 0). Khi độ bền giảm dần (100 -> 99 -> 98), Server không hề gửi `sendItemBody`, dẫn đến việc trên HUD của client con số độ bền của Giày và Găng tay đứng im bất động.
   - **Với Nhẫn (Type 8, và cả Dây chuyền Type 9, Ngọc bội Type 12):** Server chỉ kiểm tra `item.isArmor()` trong `Player.injured()`, loại trừ hoàn toàn các món trang sức (`isJewelry()`), khiến Nhẫn và trang sức không bao giờ bị trừ độ bền khi chịu sát thương.

**Files thay đổi:**
- `game/app/src/classes/MainCharInfo.java`:
  - Ưu tiên kiểm tra các trạng thái Debuff khống chế (Choáng từ `cW/cZ`, Trúng Độc từ `dg/dh`) lên trước.
  - Phân loại chính xác hiệu ứng 22: Nếu đang bị dính độc (`mainChar.dg > 0`) hoặc nhân vật không phải phái Cung thủ (`mainChar.aJ != 2`), hiệu ứng 22 luôn là **Debuff "Trúng Độc"** màu đỏ (`0xFF1744`, `isDebuff = true`).
  - Kiểm tra hiệu ứng 19: Nếu đang bị Choáng (`mainChar.cW`), luôn là **Debuff "Choáng"** màu đỏ (`0xFF1744`, `isDebuff = true`).
- `server/KPAH/src/player/Player.java`:
  - Mở rộng phạm vi trừ độ bền trong `injured()` cho toàn bộ các trang bị giáp và trang sức đang đeo (`item.isArmor() || item.isJewelry() || type == 19`).
  - Bổ sung biến cờ `anyDurableChanged`: Ngay khi có bất kỳ món trang bị nào bị giảm 1 điểm độ bền (`item.minusDurableCheck() == true`), Server lập tức gửi `InventoryService.instance.sendItemBody(this)` về client để cập nhật số độ bền thời gian thực trên HUD.

**Kết quả:** ✅ Thành công — Cả Client và Server đều build sạch sẽ không lỗi (`BUILD SUCCESSFUL`).
**Ghi chú:**
- Backup:
  - `game/app/src/classes/_backup/MainCharInfo.java.bak.20260910_1506`
  - `server/KPAH/src/player/_backup/Player.java.bak.20260910_1506`
- Phần 08 hiện có: 8/10 task.

---

## [2026-09-10 15:28] — Sửa bug nhận diện quái thường (kích hoạt giảm dame lệch lv) & Phân loại hao mòn độ bền (Tấn công / Phòng thủ)

**Yêu cầu:**
1. **Khắc phục sát thương quái khi lệch level:** Người chơi lv26 đánh Gà con lv18 bị 1 hit hơn 100 dame dù đã mang đồ 19. Do đã tìm ra nguyên nhân gốc rễ là bug nhận diện quái thường khiến cơ chế giảm dame theo lệch lv không chạy, tiến hành hoàn trả chỉ số trang bị và sát thương gốc của quái về như cũ, chỉ giữ lại fix bug nhận diện quái thường.
2. **Phân loại hao mòn độ bền:**
   - Nhóm tấn công (Vũ khí, Nhẫn, Ngọc bội, Dây chuyền): Hao mòn độ bền khi người chơi tấn công.
   - Nhóm thủ (các món còn lại: Áo, Quần, Nón, Giày, Găng tay, Phi phong): Hao mòn độ bền khi bị quái/đối thủ tấn công.

**Nguyên nhân gốc rễ & Phân tích:**
1. **Lỗi `isNormalMonster()` khiến cơ chế giảm dame theo level không hoạt động:**
   - Trong `Monster.java`, hàm `isNormalMonster()` kiểm tra `template.getType() == 0`. Tuy nhiên trong CSDL `monsters`, cột `type` đại diện cho hệ nguyên tố (0: Thủy, 1: Mộc, 2: Hỏa, 3: Thổ, 4: Kim). Quái "Gà con" (ID 14, lv 18) có `type = 2` (hệ Hỏa), dẫn đến `isNormalMonster()` trả về `false`!
   - Do đó, cơ chế giảm sát thương theo khoảng cách level (cách 8 lv được giảm 80% dame) hoàn toàn bị bỏ qua đối với phần lớn quái thường có `type != 0`.
   - Sau khi sửa hàm này, toàn bộ quái thường đều áp dụng chuẩn xác cơ chế giảm 20% dame / lv chênh lệch (tối đa 80%), người chơi lv26 đánh quái lv18 nhận sát thương giảm 80% đúng như thiết kế mà không cần can thiệp buff chỉ số ảo.

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - Sửa lại `isNormalMonster()`: Xác định quái thường dựa trên `!isElite && !isKhoangSan() && !canNotAttackPlayer() && !playerCanNotAttack() && template.getLevel() < 90 && template.getId() < 113 && !template.getName().toLowerCase().contains("boss")`. Nhờ đó tất cả quái thường (kể cả hệ Mộc, Hỏa, Thổ, Kim...) đều được hưởng cơ chế giảm dame và exp theo level gap.
  - Giữ nguyên công thức sát thương tự nhiên gốc của quái (`mobLv * 11 + 5` đến `mobLv * 14 + 15`).
- `server/KPAH/src/player/Point.java`:
  - Giữ nguyên toàn bộ chỉ số trang bị gốc của người chơi (không tăng thêm % cộng dồn).
- `server/KPAH/src/services/SkillService.java`:
  - Bổ sung `onAttackWearEquip(Player pl)`: Duyệt trang bị đang đeo, kiểm tra Nhóm tấn công (`item.isWeapon() || item.isJewelry()`, gồm Vũ khí, Nhẫn, Dây chuyền, Ngọc bội). Mỗi lần xuất chiêu tấn công mục tiêu (quái hoặc người chơi), các trang bị này sẽ tích lũy đòn đánh và trừ độ bền qua `minusDurableCheck()`.
  - Đồng bộ cập nhật client qua `sendItemBody(pl)`, nếu hỏng trang bị tự động gọi `initPoint()` và `sendMainCharInfo(pl)`.
- `server/KPAH/src/player/Player.java`:
  - Trong `injured()`: Giới hạn chỉ trừ độ bền cho Nhóm phòng thủ (`!item.isWeapon() && !item.isJewelry()`, gồm Áo, Quần, Nón, Giày, Găng tay, Phi phong) khi người chơi bị trúng đòn.

**Kết quả:** ✅ Thành công — Cả Server và Client đều biên dịch sạch sẽ không lỗi (`BUILD SUCCESSFUL`).
**Ghi chú:**
- Backup:
  - `server/KPAH/src/map/_backup/Monster.java.bak.20260910_1527`
  - `server/KPAH/src/player/_backup/Point.java.bak.20260910_1527`
  - `server/KPAH/src/player/_backup/Player.java.bak.20260910_1523`
  - `server/KPAH/src/services/_backup/SkillService.java.bak.20260910_1523`
- Phần 08 hiện có: 9/10 task.

---

## [2026-09-10 15:35] — Rà soát toàn bộ phần thưởng nhiệm vụ tân thủ & hằng ngày; Sửa lỗi nhận thưởng không có trong kho đồ và sửa ID Vé Giờ Vàng tại Phú Ông

**Yêu cầu:**
- Rà soát toàn bộ hệ thống nhiệm vụ tân thủ (chính tuyến) và hằng ngày, kiểm tra danh sách phần thưởng.
- Khắc phục lỗi: Người chơi nhận phần thưởng nhưng không có trong kho đồ (hành trang không cập nhật).
- Khắc phục lỗi cụ thể tại NPC Phú Ông: Nhận Vé giờ vàng nhưng không có trong kho đồ.

**Nguyên nhân gốc rễ & Phân tích:**
1. **Lỗi không đồng bộ hành trang (`sendItemPotion`, `sendItemGem`, `sendItemBag`) xuống Client:**
   - Trong `QuestService.java`, khi người chơi hoàn thành nhiệm vụ (cả 7 nhiệm vụ tân thủ và các nhiệm vụ hằng ngày), server chỉ gọi `player.getInventory().plusXu()`, `addItemPotion()`, `addItemGem()`, `addItemBagEquipment()` và gọi `sendQuestInfo(player)`.
   - `sendQuestInfo` chỉ gửi thông tin cập nhật trạng thái nhiệm vụ (chữ, tiến độ), **hoàn toàn không gửi các gói tin cập nhật kho đồ** (`sendItemPotion`, `sendItemGem`, `sendItemBag`).
   - Do đó, dù dữ liệu trong RAM server đã có vật phẩm/xu/lượng, client không hề nhận được gói tin cập nhật giao diện, khiến người chơi mở rương/túi đồ ra thấy trống không, tưởng rằng không nhận được thưởng!
2. **Lỗi nhầm lẫn ID "Vé giờ vàng" với icon (`idImage`):**
   - Trong CSDL `potion_template`:
     - ID 35: `Vé giờ vàng 1h` (x2 EXP trong 1h, `idImage = 11`).
     - ID 81: `Vé giờ vàng 150%` (`idImage = 11`).
     - ID 11: `Tiên dược cao cấp` (dùng nhận 500.000 kinh nghiệm).
   - Lập trình viên trước đó nhìn thấy `idImage = 11` của Vé giờ vàng nên đã viết nhầm:
     `ItemService.instance.createNewItemPotion((short) 11, ...); // Thẻ x1.5 EXP`
   - Dẫn đến tại NPC Ông Nội (Quest 3), NPC Phú Ông (Quest 4), và Menu Điểm danh hằng ngày (`MenuOptionService`), server đã phát nhầm ID 11 thay vì Vé giờ vàng (ID 35). Kết hợp với lỗi không gọi `sendItemPotion`, người chơi không hề thấy Vé giờ vàng trong kho.
3. **Phần thưởng Phú Ông hàng ngày chưa tương xứng:**
   - Nhiệm vụ chuột cống của Phú Ông (`DAILY_PHU_ONG_MICE`) chỉ cho Xu và EXP mà thiếu vật phẩm đặc trưng của Phú Ông.

**Files thay đổi:**
- `server/KPAH/src/services/QuestService.java`:
  - **Nhiệm vụ Chính Tuyến (0 -> 6):**
    - Sửa Quest 3 (Ông Nội): Đổi phát thưởng ID 11 thành `(short) 35` (1 Vé giờ vàng 1h). Thêm đồng bộ `sendItemPotion(player)`, `sendItemGem(player)`, `sendMainCharInfo(player)`.
    - Sửa Quest 4 (Phú Ông): Đổi phát thưởng ID 11 thành `(short) 35` (2 Vé giờ vàng 1h) + 3 Tinh Anh Huyết (ID 108). Thêm đồng bộ `sendItemPotion(player)`, `sendMainCharInfo(player)`.
    - Sửa Quest 5 (Thợ Săn): Bổ sung 1 Rương Tinh Anh Bậc 2 (ID 160) cho đúng với mô tả nhiệm vụ. Thêm đồng bộ `sendItemPotion(player)`, `sendItemGem(player)`, `sendMainCharInfo(player)`.
    - Bổ sung đồng bộ tức thì cho Quest 0, 1, 2, 6 (`sendItemPotion`, `sendItemBag`, `sendMainCharInfo`).
  - **Nhiệm vụ Hằng Ngày:**
    - `DAILY_PHU_ONG_MICE` (Phú Ông): Bổ sung thưởng **1 Vé giờ vàng 1h (ID 35)** bên cạnh Xu và EXP.
    - `DAILY_THO_REN_SELL` (Thợ Rèn): Bổ sung thưởng 1 Luyện kim dược (ID 8).
    - Cuối hàm `giveDailyReward`: Gọi đồng bộ tức thì `sendItemPotion(player)`, `sendItemGem(player)`, `sendMainCharInfo(player)` cho tất cả các nhiệm vụ hằng ngày.
    - Cập nhật mô tả chi tiết phần thưởng trong `getDailyDesc()` và `sendQuestInfo()`.
- `server/KPAH/src/services/MenuOptionService.java`:
  - Sửa phần thưởng Điểm danh hàng ngày: Đổi ID 11 thành `(short) 35` (2 Vé giờ vàng 1h) và gọi `sendMainCharInfo(player)`.

**Kết quả:** ✅ Thành công — Biên dịch Server không lỗi (`BUILD SUCCESSFUL`). Mọi phần thưởng nhiệm vụ khi hoàn thành đều được gửi trực tiếp và hiển thị ngay lập tức trong kho đồ của người chơi.
**Ghi chú:**
- Backup:
  - `server/KPAH/src/services/_backup/QuestService.java.bak.20260910_1533`
  - `server/KPAH/src/services/_backup/MenuOptionService.java.bak.20260910_1533`
- Phần 08 đạt: 10/10 task. Đã lưu trữ thành `tasks/TASK_08.md` và mở `TASK.md` mới (Phần 09).

---


