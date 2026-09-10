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
