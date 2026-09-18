# KPAH Project — Task Log (Phần 13: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #130), tiến hành lưu trữ file thành `tasks/TASK_13.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
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
> - [Phần 11 (Task 101 - 110)](tasks/TASK_11.md)
> - [Phần 12 (Task 110 - 119)](tasks/TASK_12.md)

---

## [2026-09-18 09:50] — Task #120: Loại Bỏ Hoàn Toàn Khoảng Cách Cấp Độ Đối Với Quái Tinh Anh (Elite Mob Luôn Gây Đủ 100% Sát Thương)

**Yêu cầu:**
1. Quái Tinh Anh (Elite Mob) **không có khoảng cách cấp độ**: sát thương của quái tinh anh lên người chơi **không bị suy giảm bởi cấp độ** của người chơi.
2. Dù người chơi có cấp cao hơn quái tinh anh bao nhiêu cấp đi chăng nữa, sát thương nhận từ quái tinh anh vẫn bảo toàn 100% (không áp dụng cơ chế giảm 20% mỗi cấp độ như quái thường).

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java` — Thêm điều kiện `!isElite` vào khối tính toán giảm sát thương theo chênh lệch cấp độ (`diffLevel > 0`). Quái tinh anh hoàn toàn miễn nhiễm với cơ chế này, luôn gây sát thương đầy đủ theo chỉ số công và phòng thủ của người chơi.

**Kết quả:** ✅ Thành công
- Quái tinh anh luôn giữ trọn vẹn 100% uy lực sát thương bất kể cấp độ người chơi.
- Server Java biên dịch thành công 100% (`KPAH.jar`).

---
