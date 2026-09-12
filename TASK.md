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

