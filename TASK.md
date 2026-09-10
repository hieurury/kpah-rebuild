# KPAH Project — Task Log (Phần 09: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #90), tiến hành lưu trữ file thành `tasks/TASK_09.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
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

---

## [2026-09-10 15:58] — Task #81: Nâng Cấp Toàn Diện Hệ Thống Log Server KPAH & Bộ Lệnh Console Admin

**Yêu cầu:** Log server trực quan, dễ nhìn hơn, hiển thị thông tin chi tiết và dễ kiểm soát hơn.
**Files thay đổi:**
- `server/KPAH/src/database/HikariCP.java` — Thêm `&useSSL=false&autoReconnect=true` loại bỏ hoàn toàn cảnh báo SSL spam của MySQL Connector.
- `server/KPAH/src/utils/ServerLog.java` — Nâng cấp Native ANSI escape codes đa nền tảng (Termux/SSH/Linux Terminal), bộ badge màu sắc trực quan, tự động lọc sạch ANSI khi lưu file log trên đĩa, bổ sung cờ toggle và chu kỳ cho Heartbeat.
- `server/KPAH/src/manager/TopManager.java` — Thay thế log in thô `Printer.printRed("Load Top Data")` bằng `ServerLog.system`.
- `server/KPAH/src/services/ChatService.java` — Bổ sung method `sendServerNotice(String chat)` để gửi thông báo từ console tới toàn server.
- `server/KPAH/src/services/MapService.java` — Ghi log thăng cấp nhân vật qua `ServerLog.levelUp`.
- `server/KPAH/src/server/Server.java` — Xóa các log in thô duplicate, nâng cấp Heartbeat hiển thị % RAM và tên người chơi online, mở rộng bộ lệnh Admin Console (`help`, `status`, `online`/`players`, `kick`, `say`/`chat`, `hb on|off|<sec>`, `gc`, `clear`/`cls`, `baotri`).
**Kết quả:** ✅ Thành công. Server biên dịch sạch 100% với `ant jar`.
**Ghi chú:** Đã tạo backup đầy đủ trong `_backup/` của từng thư mục tương ứng.

## [2026-09-10 16:50] — Task #82: Rà Soát & Khắc Phục Lỗi Level Ràng Buộc Kỹ Năng 5 Lớp Nghề KPAH

**Yêu cầu:** Rà soát toàn bộ các lớp nghề và kỹ năng, khắc phục sai level học kỹ năng và các vấn đề liên quan.
**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java` — Nâng cấp toàn diện phương thức `getLevelAddSkill`:
  - Khắc phục bug nghiêm trọng `lvSkill > 0`: Khi `lvSkill == 0`, server trả về 0 khiến nhân vật level 1 có thể nâng tất cả skill ngay lập tức và client hiển thị sai "Lv yêu cầu: 0".
  - Bổ sung level ràng buộc chuẩn xác cho skill buff của từng phái (theo nguyên bản `class_qz.c` client KPAH): Kiếm Khách (skill 4: base 20, skill 5: base 24), Chiến Binh (skill 4: base 20, skill 5: base 21), Pháp Sư (skill 4: base 25, skill 5: base 30, skill 6: base 27 - Hồi sinh, skill 7: base 23 - Khiên MP), Đấu Sĩ (skill 4: base 19, skill 5: base 20), Cung Thủ (skill 4: base 22, skill 5: base 19).
  - Chuẩn hóa ánh xạ 3 kỹ năng AoE mới (cấp 25, 30, 45) cho 4 phái thường (skill 6, 7, 8) và Pháp Sư (skill 8, 9, 10).
- `server/KPAH/src/services/SkillService.java` — Sửa `learnNewSkill`: Chỉnh sửa `levelRequest` kiểm tra `lvSkill = 0` (mức học ban đầu) thay vì `1`, giúp học skill AoE 1 đúng cấp 25, AoE 2 đúng cấp 30 và AoE 3 đúng cấp 45 (thay vì bị đội lên cấp 27, 32).
- `server/kpah.sql` — Cập nhật mảng `LEVEL_ADD_SKILL` trong bảng `others` cho 3 skill AoE mới (Row 6 base 25, Row 7 base 30, Row 8 base 45) đồng bộ giữa client và server.
- `server/update_skills_level.sql` — Tạo script migration cập nhật trực tiếp bảng `others` trong MySQL database.
**Kết quả:** ✅ Thành công. Toàn bộ server biên dịch sạch 100% bằng `ant clean jar` với Java 21, tạo mới `dist/KPAH.jar`.
**Ghi chú:** Đã tạo backup đầy đủ tại `server/KPAH/src/manager/_backup/`, `server/KPAH/src/services/_backup/`, và `server/_backup/`.

---
