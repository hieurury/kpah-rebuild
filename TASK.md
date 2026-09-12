# KPAH Project — Task Log (Phần 11: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #110), tiến hành lưu trữ file thành `tasks/TASK_11.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
>
> **Lịch sử các phần trước:**
> - [Phần 01 (Task 1 - 10)](tasks/TASK_01.md)
> - [Phần 02 (Task 11 - 20)](tasks/TASK_02.md)
> - [Phần 03 (Task 21 - 30)](tasks/TASK_03.md)
> - [Phần 04 (Task 31 - 40)](tasks/TASK_04.md)
> - [Phần 05 (Task 41 - 50)](tasks/TASK_05.md)
> - [Phần 06 (Task 51 - 60)](tasks/TASK_06.md)
> - [Phần 07 (Task 61 - 70)](tasks/TASK_07.md)
> - [Phần 08 (Task 71 - 80)](tasks/TASK_08.md)
> - [Phần 09 (Task 81 - 90)](tasks/TASK_09.md)
> - [Phần 10 (Task 91 - 100)](tasks/TASK_10.md)

---

## [2026-09-12 14:30] — Task #101: Cân Bằng Độc Quái Tinh Anh, Cập Nhật Tooltip Pháp Sư, Đồng Bộ Công Thức & Hiển Thị Đồ Chế Tạo

**Yêu cầu:**
1. Cập nhật mô tả kỹ năng Pháp Sư chính xác với các chỉ số giảm sức mạnh gần đây (Hồi lực tiến & Song hộ công thủ).
2. Khắc phục lỗi sát thương độc quái tinh anh luôn hiển thị 1 dame; giảm tỷ lệ dính độc và chuyển DoT từ % máu sang sát thương phẳng tăng theo level quái.
3. Chuẩn hóa hệ thống độc thành 2 loại độc duy nhất: Trúng Độc (DoT ăn mòn) và Nhiễm Độc (khuếch đại sát thương nhận vào, tối đa 5 tầng); loại bỏ hoàn toàn "Dính Độc" & "Độc Ăn Mòn" và khắc phục trùng lặp debuff trên HUD.
4. Sửa lỗi chế tạo trang bị client báo đủ nhưng server báo thiếu nguyên liệu (đồng bộ công thức & sửa lệch type vũ khí Búa/Cung).
5. Điều chỉnh hào quang nguyên liệu: Sơ cấp giữ hào quang cấp 5 (`s=1`), Cao cấp nhận hào quang cấp 6 (`s=2`).
6. Mở khóa và hiển thị đầy đủ cả 4 mốc vũ khí chế tạo (21, 26, 31, 36) cho toàn bộ 5 hệ phái.

**Files thay đổi:**
- `game/app/src/classes/class_sc.java` — Điều chỉnh mô tả Skill 5 (2% + 1%/cấp) và Skill 7 (hồi MP 5% + 2%/cấp, HP 10% + 2%/cấp) cho Pháp Sư.
- `server/KPAH/src/map/Monster.java` — Giảm tỷ lệ độc tinh anh xuống 15%, chuyển dame độc thành sát thương phẳng `(int) (mobLv * 3.5 + 20)`, sửa `injured()` bỏ qua trừ thủ và tỷ lệ kháng khi `isInjuredByEffect == true`.
- `server/KPAH/src/player/Player.java` — Sửa `injured()` bỏ qua trừ giáp/kháng phép và không trừ độ bền trang bị khi `isInjuredByEffect == true` (sửa triệt để bug DoT 1 dame).
- `server/KPAH/src/consts/BuffConst.java` — Định nghĩa hằng số debuff `BUFF_NHIEM_DOC = 6`.
- `server/KPAH/src/services/BuffService.java` — Thêm xử lý gói tin cho `BUFF_NHIEM_DOC` gửi đến người chơi và quái vật.
- `server/KPAH/src/skill/BuffInfluencePlayer.java` & `BuffInfluenceMonster.java` — Sử dụng `BUFF_NHIEM_DOC` khi áp dụng độc tức thời / khuếch đại sát thương.
- `server/KPAH/src/services/CraftService.java` — Mở khóa vũ khí 21, 26, 31, 36 (bỏ lọc `colorItem != 0`), đồng bộ chuẩn xác công thức tiêu hao nguyên liệu với Client.
- `game/app/src/classes/CraftShopScreen.java` — Sửa lỗi đảo type vũ khí (Búa=6, Bút=5, Cung=7) trong tính toán nguyên liệu.
- `game/app/src/classes/MsgHandler.java` — Cập nhật hào quang (Sơ cấp cấp 5 `s=1`, Cao cấp cấp 6 `s=2`), bắt opcode 89 với `b4 == 6` (`BUFF_NHIEM_DOC`).
- `game/app/src/classes/MainCharInfo.java` — Thêm theo dõi `instantPoisonEndTime` và `instantPoisonStacks`; định nghĩa chuẩn Trúng Độc và Nhiễm Độc, loại bỏ Dính Độc và ngăn trùng lặp debuff.

**Kết quả:** ✅ Thành công (Đã biên dịch cả Server và Game Client bằng Ant không có lỗi).
**Ghi chú:**
- Backup paths:
  - `game/app/src/classes/_backup/class_sc.java.bak.20260912_1420`
  - `server/KPAH/src/map/_backup/Monster.java.bak.20260912_1420`
  - `server/KPAH/src/player/_backup/Player.java.bak.20260912_1420`
  - `server/KPAH/src/skill/_backup/BuffInfluencePlayer.java.bak.20260912_1420`
  - `server/KPAH/src/skill/_backup/BuffInfluenceMonster.java.bak.20260912_1422`
  - `server/KPAH/src/services/_backup/BuffService.java.bak.20260912_1420`
  - `server/KPAH/src/services/_backup/CraftService.java.bak.20260912_1423`
  - `game/app/src/classes/_backup/CraftShopScreen.java.bak.20260912_1423`
  - `game/app/src/classes/_backup/MainCharInfo.java.bak.20260912_1424`
  - `game/app/src/classes/_backup/MsgHandler.java.bak.20260912_1424`

---

## [2026-09-12 14:50] — Task #102: Cải Tiến Cơ Chế Tấn Công Và Hành Vi Của Quái Vật (Quái Cận Chiến Dash & Retreat + Đạn Đỏ, Quái Đánh Xa Hit & Run / Giữ Khoảng Cách)

**Yêu cầu:**
1. **Quái cận chiến (Melee):** Thực hiện chuỗi hành vi di chuyển áp sát (dash in 24px) -> ra đòn tấn công -> lùi về giữ khoảng cách (retreat 65px) -> lặp lại chu kỳ để tạo nhịp chiến đấu linh hoạt, sinh động. Đạn của quái cận chiến đổi thành màu đỏ (Crimson Red) tạo từ viên đạn gốc (`fire.png`).
2. **Quái đánh xa (Ranged):** Loại bỏ hành vi đi lang thang nhong nhong làm loãng bãi quái; khóa mục tiêu và luôn hướng về mục tiêu tấn công; giữ khoảng cách lý tưởng (75 - 140px); khi bị áp sát (< 75px) tự động lùi lại giữ cự ly; khi người chơi đi xa (> 140px) chủ động tiến lại gần; trong cự ly lý tưởng đứng yên xả đạn.
3. **Quái tinh anh (Elite):** Áp dụng toàn bộ cơ chế di chuyển và tấn công tương ứng cho cả quái tinh anh (tăng thêm khoảng cách truy đuổi và cự ly giữ khoảng cách phù hợp).

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - Mở rộng phạm vi giữ mục tiêu `isPlayerAttackable()` (Melee: 130px, Ranged: 200px, Elite: +50px) tránh việc quái đánh xa liên tục mất mục tiêu do cự ly cũ quá ngắn (90px).
  - Viết lại chu trình `attackPlayer()`:
    - Quái cận chiến: Tiếp cận mục tiêu (24px) $\rightarrow$ delay ra đòn 180ms $\rightarrow$ tấn công $\rightarrow$ delay 220ms $\rightarrow$ lùi lại (65px) $\rightarrow$ hoàn thành chu kỳ.
    - Quái đánh xa: Kiểm tra cự ly; nếu quá gần (< 75px) lùi ra 110px; nếu quá xa (> 140px) tiến tới 110px; nếu trong khoảng 75-140px thì đứng yên khai hỏa.
    - Tách hàm dùng chung `performAttackOnTargets(...)` hỗ trợ cả quái thường và đòn kỹ năng quái tinh anh.
  - Vô hiệu hóa hành vi đi lang thang ngẫu nhiên trong `update()` đối với quái đánh xa (`!isMelee()`) để giữ bãi quái tập trung.
- `game/app/src/classes/class_bb.java`:
  - Bổ sung helper method `public final boolean isMelee() { return this.l % 2 == 0; }` đồng bộ với phân loại quái của Server.
- `game/app/src/classes/class_de.java`:
  - Khởi tạo override source file cho đạn `class_de`.
  - Thêm xử lý đồ họa chuyển đổi RGB đạn gốc (`fire.png`) thành đạn lửa đỏ Crimson Red rực rỡ (`bRed` / `getRedBullet()`).
  - Hỗ trợ loại đạn `d == 21` (đạn đỏ quái cận chiến) và `d == 20` (đạn lửa quái đánh xa).
- `game/app/src/classes/MsgHandler.java`:
  - Bắt gói tin cmd 10 (`MONSTER_ATTACK_PLAYER`): Tự động phát hiện quái cận chiến hay đánh xa; quái cận chiến bắn đạn đỏ (bullet 21), quái đánh xa bắn đạn lửa gốc (bullet 20), hiển thị đầy đủ hiệu ứng và sát thương khi đạn trúng đích.

**Kết quả:** ✅ Thành công (Đã biên dịch cả Server và Game Client bằng Ant không có lỗi).
**Ghi chú:**
- Backup paths:
  - `server/KPAH/src/map/_backup/Monster.java.bak.20260912_1445`
  - `server/KPAH/src/services/_backup/MonsterService.java.bak.20260912_1445`
  - `game/app/src/classes/_backup/class_bb.java.bak.20260912_1445`
  - `game/app/src/classes/_backup/MsgHandler.java.bak.20260912_1445`

---

## [2026-09-12 17:10] — Task #103: Sửa Toàn Diện Hệ Thống Trang Bị (2 Nhẫn, Tooltip Cột Phải, Đồ Khác Giới, Cuốc/Vũ Khí, Thú Cưỡi) & Nâng Cấp Hiển Thị Chỉ Số Chế Tạo

**Yêu cầu:**
1. **Lỗi 2 Nhẫn:** Khi đeo đủ 2 nhẫn, nhẫn trên (`107, 52`) không hiển thị do client chỉ vẽ khi `L == 1`, trong khi server trước đó không gán `viTriVe = 1` và `2`. Chuẩn hóa để 2 nhẫn hiển thị đúng cả 2 ô trên và dưới.
2. **Lỗi Tooltip cột phải:** Bấm vào các trang bị cột phải (Vũ khí, Dây chuyền, 2 Nhẫn, Ngọc bội) trong menu Trang bị không hiện tooltip do tọa độ cảm ứng tính sai cột (`f = n8 * g + n2 + 1` rơi vào cột 1 của avatar). Sửa lại chuẩn xác cột 2 (`f = n8 * g + 2`).
3. **Lỗi đồ khác giới / sai phái:** Khi mặc đồ không đúng giới tính/hệ phái/cấp, server báo lỗi nhưng túi đồ client bị mất item tạm thời do không gửi lại `sendItemBag`. Cần đồng bộ ngay lập tức.
4. **Lỗi Cuốc & Vũ khí:** Đeo cuốc xong không hiển thị tháo và không thể trang bị lại vũ khí (bị kẹt trạng thái đào khoáng). Cần hỗ trợ hoán đổi qua lại 2 chiều giữa Cuốc (type 13) và Vũ khí (type 3..7).
5. **Lỗi Thú cưỡi:** Ô trang bị thú cưỡi không hiển thị icon thú do gói tin 15 (`CHAR_WEARING`) ở server thiếu byte `getIdImage()`, và client ẩn thú khi `bK == true` (đang cầm cuốc).
6. **Tooltip chỉ số trang bị chế tạo:** Khắc phục tình trạng hiển thị `chỉ số 7: 5`, `chỉ số 8: 5`. Format chuẩn hóa tên thuộc tính rõ ràng kèm dấu `+` (ví dụ `Sức khỏe: +5`, `Tấn công: +100`, `Né: +5%`), phân loại màu sắc chính xác: HP màu đỏ (2), MP màu xanh (1), chỉ số thường màu xanh (1), chỉ số đặc biệt/VIP màu vàng (5).

**Files thay đổi:**
- `server/kpah.sql` & `server/update_item_attributes_20260912.sql`: Cập nhật bảng `item_attribute` đặt tên rõ ràng cho ID 7 (`Tăng HP`), ID 8 (`Tăng MP`), ID 9 (`May mắn`), ID 33 (`Tăng HP`), ID 34 (`Tăng MP`), và chuẩn hóa mã màu (HP=2, MP=1, chỉ số đặc biệt=5, thường=1).
- `server/KPAH/src/manager/Manager.java`: Chuẩn hóa runtime `item_attribute` ngay khi nạp DB vào bộ nhớ (ID 7, 8, 9, 33, 34 và color mapping) bảo đảm tính nhất quán ngay cả khi chưa chạy SQL update.
- `server/KPAH/src/services/ItemService.java`: Sắp xếp attribute ID trước khi gửi opcode 25 (`ITEM_TEMPLATE`) tới client.
- `server/KPAH/src/services/MapService.java`: Bổ sung byte `animal.getTemplate().getIdImage()` còn thiếu trong gói tin 15 (`CHAR_WEARING`) khi nhân vật đang cưỡi thú.
- `server/KPAH/src/services/InventoryService.java`:
  - Thêm `findItemBodyWeaponOrPickaxe()` tìm kiếm hoán đổi giữa Cuốc và Vũ khí.
  - Thêm `normalizeItemBodyRings()` tự động gán `viTriVe = 1` cho nhẫn thứ nhất và `viTriVe = 2` cho nhẫn thứ hai; gọi tự động trong `sendItemBody()`.
- `server/KPAH/src/services/UseItemService.java`:
  - Cho phép hoán đổi 2 chiều giữa Vũ khí và Cuốc trực tiếp trong hành trang mà không bị kẹt.
  - Khi kiểm tra giới tính/hệ phái/cấp độ thất bại, luôn gọi `InventoryService.instance.sendItemBag(player)` để giao diện túi đồ client không bao giờ bị mất đồ.
  - Chuẩn hóa vị trí đeo nhẫn (`slot = 1` hoặc `2`).
- `game/app/src/classes/class_zu.java`: Override class hiển thị thuộc tính trang bị client: thêm dấu `+` tự động, format phần trăm `%`, ánh xạ tên thuộc tính rõ ràng và tô màu chuẩn (HP đỏ=2, MP xanh=1, đặc biệt vàng=5, thường xanh=1).
- `game/tools/Patcher.java`: Thêm `patchClassNu()`:
  - Patch `c()`: Sửa bytecode tọa độ click cột phải từ `iload 4; iadd; iconst_1; iadd` thành `iconst_2; iadd; nop; nop; nop` (`f = n8 * g + 2`), kích hoạt mở tooltip chính xác cho Vũ khí, Dây chuyền, 2 Nhẫn, Ngọc bội.
  - Patch `o(Graphics)`: Bỏ lệnh nhảy bỏ qua khi `bK == true`, bảo đảm icon thú cưỡi luôn hiển thị tại ô `(82, 92)` trên UI nhân vật.
- `game/libs/KPAH_225_remade.jar`: Đã inject `class_nu.class` đã patch và `class_zu.class`.
- `game/build/dist/KPAH_PROD.jar`: Đã biên dịch bản phát hành mới và khởi chạy MicroEmulator.

**Kết quả:** ✅ Thành công (Cả Server và Client đã biên dịch và đóng gói hoàn tất, client đã khởi chạy trong emulator).
**Ghi chú:**
- Backup paths:
  - `server/_backup/kpah.sql.bak.20260912_1638`
  - `server/KPAH/src/manager/_backup/Manager.java.bak.20260912_1639`
  - `server/KPAH/src/services/_backup/ItemService.java.bak.20260912_1640`
  - `server/KPAH/src/services/_backup/InventoryService.java.bak.20260912_1641`
  - `server/KPAH/src/services/_backup/UseItemService.java.bak.20260912_1642`
  - `server/KPAH/src/services/_backup/MapService.java.bak.20260912_1643`
  - `game/libs/_backup/KPAH_225_remade.jar.bak.20260912_1705`
  - `game/tools/_backup/Patcher.java.bak.20260912_1709`

## [2026-09-12 19:55] — Task #104: Sửa Lỗi Trang Bị Vũ Khí & Thú Cưỡi (Hiển Thị Ô Thú Cưỡi, Đồng Bộ Chỉ Số, Chống Trùng ID Trang Bị Chế Tạo, Sửa Crash Render Ngựa)

**Yêu cầu:**
1. **Lỗi không hiện thú cưỡi:** Nhân vật đang cưỡi ngựa (uống rượu thú cưỡi Bạch Mã, v.v.) nhưng vào menu Trang bị ô thú cưỡi `(82, 92)` vẫn trống, bấm vào không xem được thông tin Linh thú.
2. **Lỗi trang bị vũ khí không hiện & thông tin không đúng (Hình 2 & Hình 3):** Sau khi bấm "Sử dụng" vũ khí chế tạo (`Cơ duyên bút tam phẩm`), người chơi nhìn vào ô đầu túi đồ thấy hiện vũ khí cũ (`Bút sắt`, độ bền 1024) tưởng chưa mặc hoặc hiển thị sai chỉ số; bấm tiếp "Sử dụng" thì lại tháo vũ khí chế tạo cất vào túi và đeo lại vũ khí cũ. Thiếu thông báo xác nhận và thiếu đồng bộ chỉ số HUD sau khi trang bị.
3. **Lỗi ID trang bị chế tạo bị trùng template ID:** `CraftService` và `ItemService` gán `.idItem(template.getId())` khiến vật phẩm mới không có ID thực thể tăng dần (bị bỏ qua bởi `initIdItemEquip`), dẫn đến xung đột khi hoán đổi trang bị.
4. **Lỗi crash `ArrayIndexOutOfBoundsException` trong `class_hw.a(Graphics)`:** Khi client render thú cưỡi, `class_ko.b.elementAt(this.cj)` không kiểm tra độ dài vector dẫn đến crash game khi `cj >= size`.

**Files thay đổi:**
- `server/KPAH/src/services/CraftService.java`:
  - Sửa dòng 334 gán `.idItem((short) 0)` (thay vì `template.getId()`) để `initIdItemEquip` tự động cấp phát ID thực thể duy nhất cho trang bị chế tạo.
- `server/KPAH/src/services/ItemService.java`:
  - Sửa dòng 162 gán `.idItem((short) 0)` (thay vì `template.getId()`) cho trang bị nhặt rơi.
- `server/KPAH/src/services/InventoryService.java`:
  - Trong `sendItemBody()`: Thêm logic tự động tra cứu `AnimalTemplate` theo `idItem` (hoặc switch theo `imageHorse`) khi `animalUse == null` nhưng nhân vật đang cưỡi thú (`useHorse != NON_HORSE`). Gửi đúng mã hình ảnh `animalImg` (0=Bạch mã, 1=Mãnh hổ, 2=Hắc ngưu, 3=Sói xám, 4=Tiên hạc) và tên thú cưỡi giúp client vẽ icon tại ô `(82, 92)` và mở tooltip Linh thú.
  - Sửa `initIdItemEquip()` và `initIdItemGem()`: Kiểm tra tràn `idMax >= 32760 || idMax < 0` trước khi tăng ID để tự động gọi `initIdItem()`.
- `server/KPAH/src/services/UseItemService.java`:
  - Trong `useItemHorse()`: Gọi `InventoryService.instance.sendItemBody(player)` để cập nhật ô thú cưỡi ngay khi dùng thuốc thú cưỡi.
  - Trong `useItemEquipment()`:
    - Sửa điều kiện kiểm tra cấp độ: `equipment.getTemplate().getLevel() > player.getInfo().getLevel()`.
    - Gọi `sendMainCharInfo(player)`, `sendInfoMe(player)`, `onNewHpMp(player)` sau khi trang bị để cập nhật toàn diện chỉ số công/thủ/HP/MP và ngoại trang.
    - Thêm thông báo xác nhận: `ChatService.instance.sendChatOnlyMe(player, "Đã trang bị " + equipment.getTemplate().getName() + ".")`.
- `server/KPAH/src/services/MapService.java`:
  - Trong `onDownHorse()`: Gọi `InventoryService.instance.sendItemBody(pl)` khi xuống ngựa để dọn sạch icon thú cưỡi trên giao diện Trang bị.
- `game/tools/Patcher.java`:
  - Thêm method `patchClassHw()` chèn kiểm tra an toàn cho `Vector.elementAt()` và `drawRegion()` trong `classes.class_hw.a(Graphics)` bằng Javassist.
  - Thực thi patch `class_hw.class` và inject vào `game/libs/KPAH_225_remade.jar`.
- `game/build/dist/KPAH_PROD.jar` & `server/KPAH/dist/KPAH.jar`:
  - Biên dịch và đóng gói hoàn tất cả Client và Server. Đã khởi động Server và MicroEmulator kiểm tra hoạt động ổn định.

**Kết quả:** ✅ Thành công (Đã kiểm tra logic, recompile cả Client và Server, khởi động Server và Emulator chạy ổn định).
**Ghi chú:**
- Backup paths:
  - `server/KPAH/src/services/_backup/CraftService.java.bak.20260912_1950`
  - `server/KPAH/src/services/_backup/ItemService.java.bak.20260912_1950`
  - `server/KPAH/src/services/_backup/InventoryService.java.bak.20260912_1950`
  - `server/KPAH/src/services/_backup/UseItemService.java.bak.20260912_1950`
  - `server/KPAH/src/services/_backup/MapService.java.bak.20260912_1950`
  - `game/tools/_backup/Patcher.java.bak.20260912_1950`

---

## [2026-09-12 20:45] — Task #105: Tách Biệt Độc Lập Ô Trang Bị Vũ Khí & Cuốc (Công Cụ), Sửa Tọa Độ Vẽ Icon Cuốc Chuẩn Xác, Và Kích Hoạt Tooltip Bằng Phím Giữa

**Yêu cầu:**
1. **Khắc phục lỗi trang bị Cuốc làm tháo Gậy phép (Vũ khí) và cả 2 ô đều trống:** Người chơi trang bị Cuốc (`type 13`) thì Gậy phép bị gỡ ra hành trang và cả 2 ô vũ khí, công cụ đều trống; không thể mang đồng thời cả vũ khí và cuốc.
2. **Khắc phục lỗi ô công cụ (Cuốc) không hiển thị hình ảnh:** Dù có Cuốc trong `itemBody`, ô công cụ `(26, 84)` vẫn trống do mảng tọa độ `bj[13], bk[13]` bị trỏ sai sang ô thú cưỡi `(82, 92)`.
3. **Khắc phục lỗi bấm phím giữa (phím 5 / FIRE) không hiện tooltip trong menu Trang bị:**
   - Khi vừa vào tab Trang bị, tiêu điểm nằm trên thanh tiêu đề `< Trang bị >` (`this.r == true`), `this.bb` bị gán `null` nên bấm phím giữa không có bất kỳ phản hồi nào.
   - Khi di chuyển con trỏ vào ô công cụ (`f == 4`), method `class_nu.f()` chỉ tìm item `type == 19` (ngoại trang/thời trang) mà không tìm Cuốc (`type == 13`), dẫn đến bấm phím giữa tại ô cuốc không hiện tooltip.
   - Khắc phục nguy cơ crash `ArrayIndexOutOfBoundsException` trong `class_yi.b` khi đọc chỉ số mảng 3 chiều sprite vũ khí.

**Files thay đổi:**
- `server/KPAH/src/services/InventoryService.java`:
  - Thêm method `findItemBodyWeapon(@NonNull Player player)` lọc các item thỏa mãn `it.isWeapon()`.
  - Cập nhật `sendWeaponImage()` sử dụng `findItemBodyWeapon(player)` thay vì công thức tính hệ phái cứng để luôn gửi đúng hình ảnh vũ khí đang mang.
- `server/KPAH/src/services/UseItemService.java`:
  - Trong `useItemEquipment()`: Phân tách rõ ràng giữa Vũ khí (`isWeapon()`) và Cuốc (`type == 13`):
    - Khi trang bị vũ khí: Chỉ hoán đổi với vũ khí hiện tại (`findItemBodyWeapon`).
    - Khi trang bị cuốc: Chỉ hoán đổi với cuốc hiện tại (`findItemBodyByType(player, (byte) 13)`).
    - Cả 2 slot hoạt động độc lập hoàn toàn, cho phép mang đồng thời cả vũ khí và cuốc.
- `game/tools/Patcher.java`:
  - Thêm patch vào `<clinit>` của `class_nu`: gán `bj[13] = 26; bk[13] = 84;` để Cuốc luôn vẽ chính xác tại ô công cụ bên trái.
  - Thêm patch vào `class_nu.c()`: bắt sự kiện bấm phím giữa `class_acv.b(5)` khi đang ở tab Trang bị (`x[w] == 1`). Nếu `this.r == true`, tự động chuyển tiêu điểm xuống ô đầu tiên (`this.f = 0`) và gọi ngay `class_nu.f(this)` mở tooltip; nếu đã ở trong lưới, luôn gọi `class_nu.f(this)`.
  - Thay thế body của `class_nu.f(class_nu)`:
    - Khi `this.f == 4` (ô công cụ): tìm item Cuốc (`tmpl.c == 13`) hoặc `19`, hiển thị tooltip chính xác tại tọa độ `(31, 82)`.
    - Khi `this.f % 3 == 1` và `this.f != 4`: mở menu Linh thú / Thú cưng.
    - Với các ô trang bị khác (`f % 3 != 1`): tra cứu theo mảng loại trang bị `bl[n2]` và hiển thị tooltip.
  - Thêm method `patchClassYi()`: chèn kiểm tra biên an toàn cho mảng 3 chiều `class_hw.co` trong `class_yi.b(int, int, int)` ngăn triệt để lỗi `ArrayIndexOutOfBoundsException`.
- **Triển khai & Cập nhật môi trường:**
  - Build server bằng Ant JDK 21 (`dist/KPAH.jar`), SCP triển khai lên thiết bị Termux Android (`192.168.1.37:8022`), khởi động lại server daemon tiến trình mới hoạt động hoàn hảo.
  - Chạy `Patcher` inject bytecode đã vá vào `libs/KPAH_225_remade.jar`.
  - Đóng gói Client distribution bằng Ant JDK 8 (`KPAH_PROD.jar` & `KPAH_MOD.jar`).
  - Khởi động lại Server cục bộ và MicroEmulator chạy trơn tru, không phát sinh lỗi.

**Kết quả:** ✅ Thành công (Đã xác nhận server và client biên dịch sạch, cuốc và vũ khí mang song song độc lập, cuốc vẽ đúng ô công cụ, bấm phím giữa hiện đầy đủ tooltip).
**Ghi chú:**
- Backup paths:
  - `server/KPAH/src/services/_backup/InventoryService.java.bak.20260912_2034`
  - `server/KPAH/src/services/_backup/UseItemService.java.bak.20260912_2034`
  - `game/tools/_backup/Patcher.java.bak.20260912_2034`

---

## [2026-09-12 20:55] — Task #106: Cân Bằng Giảm Sức Mạnh & Tăng Thời Gian Giãn Cách Đòn Đánh Của Quái (Xa 5-10s, Gần 5-7s) & Reset Sạch Toàn Bộ Dữ Liệu Tài Khoản, Người Chơi

**Yêu cầu:**
1. **Điều chỉnh thời gian giãn cách đòn đánh của quái vật:**
   - Quái đánh xa (Ranged): giãn cách thời gian giữa các đòn tấn công lên 5 - 10s.
   - Quái cận chiến (Melee): giãn cách thời gian giữa các đòn tấn công lên 5 - 7s.
   - Giảm sức mạnh đòn đánh của quái (sát thương cơ bản và đòn quái tinh anh).
2. **Clear toàn bộ dữ liệu người dùng & tài khoản:**
   - Tạo bản sao lưu dự phòng (backup) dữ liệu tài khoản và nhân vật trước khi thao tác.
   - Truncate sạch toàn bộ các bảng người chơi (`users`, `players`, `clan`, `cpanel`, `atm_check`, `atm_lichsu`, `napthe`) trên cả Database cục bộ và Database thiết bị Android (Termux) để sẵn sàng đăng ký và trải nghiệm lại từ đầu.
   - Triển khai `KPAH.jar` mới và khởi động lại Server trên cả hai môi trường.

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - Trong `attackPlayer()`:
    - Quái đánh gần: `this.timeAttack = System.currentTimeMillis() + Util.nextInt(5000, 7000)`.
    - Quái đánh xa: `this.timeAttack = System.currentTimeMillis() + Util.nextInt(5000, 10000)`.
    - Quái tinh anh: `(this.hp < getMaxHp() / 2) ? Util.nextInt(2500, 4000) : Util.nextInt(4000, 6000)`.
  - Trong `getDameAttack()`:
    - Giảm công thức sát thương cơ bản của quái từ `mobLv * 11 + 5` / `mobLv * 14 + 15` xuống `Math.max(10, mobLv * 7 + 3)` / `Math.max(16, mobLv * 10 + 8)`.
    - Giảm hệ số cường hóa sát thương quái tinh anh từ `1.8x` xuống `1.4x`, giảm bạo kích từ `1.5x` xuống `1.25x`.
    - Giảm sát thương xước tối thiểu khi thủ người chơi quá cao từ `mobLv * 1.5 + 2` xuống `Math.max(2, (int)(mobLv * 1.1 + 1))`.
- **Database (`kpah`):**
  - Sao lưu dự phòng an toàn trước khi xóa:
    - Cục bộ: `_backup/db_users_players.sql.bak.20260912_2053`
    - Termux Android: `~/data/programs/kpah-rebuild/_backup/db_users_players.sql.bak.20260912_2054`
  - Truncate sạch dữ liệu các bảng: `users`, `players`, `clan`, `cpanel`, `atm_check`, `atm_lichsu`, `napthe`, reset `AUTO_INCREMENT = 1`.
- **Triển khai & Khởi động lại Server:**
  - Biên dịch thành công `KPAH.jar` bằng Ant JDK 21.
  - Triển khai file `KPAH.jar` và `Monster.java` lên điện thoại Android qua SCP.
  - Khởi động lại Server Game trên điện thoại Android (kết nối Bore tunnel `bore.pub:19129`) và trên máy chủ cục bộ (Daemon Port 19129).

**Kết quả:** ✅ Thành công (Dữ liệu tài khoản/nhân vật đã làm sạch 100%, quái đánh xa giãn cách 5-10s, đánh gần 5-7s, sát thương đã giảm phù hợp cho khởi đầu game mới).
**Ghi chú:**
- Backup paths:
  - `server/KPAH/src/map/_backup/Monster.java.bak.20260912_2052`
  - `_backup/db_users_players.sql.bak.20260912_2053`
  - Termux: `~/data/programs/kpah-rebuild/_backup/db_users_players.sql.bak.20260912_2054`

---

## [2026-09-12 21:40] — Task #107: Sửa Triệt Để Lỗi Quái Cận Chiến Không Tấn Công, Nâng Cấp Hành Vi Di Chuyển Khi Chờ Đòn & Khôi Phục Sát Thương Gốc Của Quái

**Yêu cầu:**
1. **Khắc phục lỗi quái cận chiến không tấn công và không hiện đạn đỏ:**
   - Điều tra nguyên nhân quái cận chiến hoàn toàn không ra đòn, không hiển thị đạn đỏ và không gây sát thương lên người chơi.
   - Sửa lỗi client xử lý loại đạn `bulletType = 21` bị crash / lỗi chỉ số mảng dẫn đến đứt gãy luồng tấn công.
2. **Nâng cấp cơ chế di chuyển trong lúc chờ hồi chiêu (Cooldown Movement):**
   - **Quái cận chiến:** Trong thời gian chờ hồi chiêu giữa 2 đòn đánh (5-7s), quái chủ động di chuyển lượn quanh mục tiêu (bán kính 38 - 65px), không đứng yên cứng đơ một chỗ, đảm bảo không vượt quá tầm đánh tối đa (130px) để không bị mất aggro.
   - **Quái đánh xa:** Trong thời gian ngắm bắn (75 - 140px), quái di chuyển qua lại nhẹ nhàng (strafing 12 - 20px) tạo cảm giác sống động, nhịp thở tự nhiên thay vì đứng im như tượng.
3. **Khôi phục sức mạnh sát thương của quái vật:**
   - Trả lại toàn bộ công thức sát thương cơ bản và bạo kích của quái trong `getDameAttack()` như lúc ban đầu theo mong muốn của người dùng.

**Files thay đổi:**
- `game/app/src/classes/class_de.java`:
  - Phát hiện nguyên nhân cốt lõi: lớp nội bộ `class_gx` của client chỉ có mảng tốc độ đạn `n` với kích thước 11 phần tử, khi nhận `bulletType = 21` sẽ văng ngoại lệ `ArrayIndexOutOfBoundsException: Index 21 out of bounds for length 11`, làm crash toàn bộ tiến trình render đạn và dừng chuỗi hiển thị dame.
  - Sửa `class_de.a(...)`: ánh xạ `n == 21` sang `20` khi truyền vào `class_gx` để mượn quỹ đạo bay an toàn của đạn lửa, trong khi giữ nguyên `this.d = 21` cho `class_de` vẽ đạn màu đỏ rực rỡ (`getRedBullet()`).
  - Lazy load an toàn ảnh `fire` nếu `b == null` và kiểm tra `null` tuyệt đối trước khi gọi `graphics.drawImage()`.
- `server/KPAH/src/map/Monster.java`:
  - Khôi phục `getDameAttack()` về các mốc sát thương ban đầu: `minAtk = mobLv * 11 + 5`, `maxAtk = mobLv * 14 + 15`, sát thương quái cận chiến `+10%`, quái tinh anh `+80%` và bạo kích x1.5, min scratch `mobLv * 1.5 + 2`.
  - Tách bạch luồng di chuyển chiến đấu (`Cooldown Movement`) và luồng ra đòn (`attackPlayer()`):
    - Quái cận chiến: Khi đang target người chơi và chờ cooldown, mỗi 1.2 - 2.0s tự động lượn góc quanh người chơi ở cự ly 38 - 65px (kiểm tra `isWalkable` an toàn). Khi hết cooldown, lướt vào áp sát (24px) $\rightarrow$ tấn công $\rightarrow$ bước lùi lại 55px.
    - Quái đánh xa: Khi trong khoảng cách lý tưởng (75 - 140px), mỗi 1.8 - 2.8s đảo vị trí qua lại theo phương vuông góc 12 - 20px; nếu bị áp sát (< 75px) lùi ra 110px; nếu mục tiêu ra xa (> 140px) tiến tới 110px.
- **Triển khai & Cập nhật môi trường:**
  - Build lại client game distribution bằng Ant (`KPAH_PROD.jar` và `KPAH_MOD.jar`).
  - Build lại `KPAH.jar` cho Server, SCP cập nhật trực tiếp lên Termux Android (`192.168.1.37:8022`).
  - Khởi động lại Server Game trên điện thoại và máy cục bộ, lắng nghe thông suốt trên cổng `19129`.

**Kết quả:** ✅ Thành công (Quái cận chiến ra đòn ổn định, đạn đỏ hiển thị rõ nét kèm hiệu ứng va chạm, quái di chuyển sống động trong lúc chờ hồi chiêu, dame quái đã khôi phục đầy đủ).
**Ghi chú:**
- Backup paths:
  - `game/app/src/classes/_backup/class_de.java.bak.20260912_2138`
  - `server/KPAH/src/map/_backup/Monster.java.bak.20260912_2139`

---
