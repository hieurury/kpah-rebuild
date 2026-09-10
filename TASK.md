# KPAH Project — Task Log (Phần 10: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #100), tiến hành lưu trữ file thành `tasks/TASK_10.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
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

---

## [2026-09-10 22:12] — Task #91: Điều Chỉnh Hồi Chiêu 3 Kỹ Năng Cuối Kiếm Khách Thành 5s, 6s, 7s

**Yêu cầu:**
- Điều chỉnh thời gian hồi chiêu (cooldown) của 3 kỹ năng AoE cuối cùng của phái Kiếm Khách (Kiếm Sĩ) lần lượt thành:
  - Skill 6 (Thiên lôi điện trảm - AoE lv 25): **5s** (`5000ms`)
  - Skill 7 (Sấm động dương gian - AoE lv 30): **6s** (`6000ms`)
  - Skill 8 (Kiếm phi kinh thiên - AoE lv 45): **7s** (`7000ms`)

**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java` — Sửa `getSkillCooldown` cho `KIEM_KHACH`: skill 6 trả về `5000L`, skill 7 trả về `6000L`, skill 8 trả về `7000L`.
- `game/app/src/classes/class_sc.java` — Sửa mô tả tooltip kỹ năng trong bảng Kỹ Năng Client cho Kiếm Khách: skill 6 hồi chiêu 5s, skill 7 hồi chiêu 6s, skill 8 hồi chiêu 7s.
- `server/kpah.sql` — Cập nhật `skill_news` ID 1 (CD 5000), ID 2 (CD 6000), ID 3 (CD 7000) và mô tả hiển thị.
- `server/update_all_skill_descriptions.sql` — Cập nhật script migration MySQL cho 3 kỹ năng mới của Kiếm Khách.
- `docs/skills/kiem_khach.md` — Cập nhật bảng tổng quan và chi tiết hồi chiêu của Skill 6, 7, 8 thành 5s, 6s, 7s.
- `server/dist/KPAH.jar` & `game/build/dist/KPAH_PROD.jar` — Biên dịch sạch 100% bằng ant.
- Giả lập MicroEmulator đã được khởi động lại với bản build mới (`task-1292`).

**Kết quả:** ✅ Thành công. Toàn bộ logic hồi chiêu Kiếm Khách được cập nhật đồng bộ 100% giữa Server, Client, Database và Tài liệu.

---

## [2026-09-10 22:45] — Task #92: Sửa Lỗi Lặp 10s Độc, Nâng Cấp Cơ Chế Độc DoT Mỗi Giây, Quái Tinh Anh Phân Cấp Độc 2-5% HP và Nhảy Dame Màu Tím

**Yêu cầu:**
- Khắc phục triệt để lỗi Client liên tục bị reset thời gian 10s độc khi dính đạn của quái tinh anh dù thực tế không còn trúng.
- Nâng cấp toàn diện cơ chế trúng độc: độc gây sát thương mỗi giây (1s/tick DoT) và duy trì trong thời gian cấu hình (hỗ trợ cả % HP tối đa và sát thương phẳng).
- Quái Tinh Anh có độc dược mạnh yếu theo cấp độ: gây rút 2% - 5% HP tối đa mỗi giây duy trì 10s (Lv 1-20: 2%, Lv 21-40: 3%, Lv 41-60: 4%, Lv > 60: 5%).
- Nhất quán thuộc tính độc và cơ chế độc của game.
- Số sát thương độc (Poison Damage) hiển thị nhảy bay lên màu tím rực rỡ (Neon Purple: `0xDF5FFF`) với viền đổ bóng đậm (`0x2B003B`), phân biệt rõ ràng với sát thương vật lý/phép thông thường.

**Files thay đổi:**
- `server/KPAH/src/skill/BuffInfluencePlayer.java` — Mở rộng `addBuffPoisoned` nhận `percentHp` và `flatDamage`, tick DoT chu kỳ 1000ms (1s/lần), bỏ lệnh gửi `BUFF_ATTACK` khi hết độc để chống client bị re-poison.
- `server/KPAH/src/skill/BuffInfluenceMonster.java` — Đồng bộ hoá cấu trúc DoT 1000ms, nhận % HP và sát thương phẳng.
- `server/KPAH/src/map/Monster.java` — Quái Tinh Anh khi bắn đạn gây trúng độc 10s với 2-5% Max HP mỗi giây tùy cấp độ quái.
- `game/app/src/classes/MainCharInfo.java` — Thêm `poisonEndTime` quản lý đếm ngược tuyệt đối theo timestamp thực; khi hết thời gian, tự động dọn dẹp sạch `dg`, `dh`, `W` và xóa hiệu ứng `class_zx 22`, triệt tiêu hoàn toàn vòng lặp đếm lùi vô hạn. Đổi màu badge sang tím `0xBA55D3`.
- `game/app/src/classes/Paint.java` — Thêm `PoisonPopup`, method `addPoisonDamage(damage, x, y)` và `paintPoisonPopups(g)` render số sát thương bay lên màu tím neon trên màn hình.
- `game/app/src/classes/MsgHandler.java` — Bắt gói tin `BUFF_ATTACK` (cmd 89): khi nhận DoT tick (`b4 == -1`), gọi `Paint.addPoisonDamage` nhảy số màu tím và trừ HP, ngăn không cho render chữ trắng/đỏ đè lên. Khi nhận `b4 == 4`, cập nhật `MainCharInfo.poisonEndTime`.
- Backup files: Lưu tại `_backup/` trong thư mục tương ứng theo quy định.

**Kết quả:** ✅ Thành công. Cả Server (`server/dist/KPAH.jar`) và Client (`game/build/dist/KPAH_PROD.jar`) đã được biên dịch thành công và emulator đã được khởi động lại.

