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

## [2026-09-18 12:15] — Task #121: Điều Chỉnh & Nâng Cấp Toàn Diện Bộ Kỹ Năng Phái Kiếm Khách (Class 0)

**Yêu cầu:**
1. Cân bằng chỉ số cơ bản Kiếm Khách ở mức trung bình ổn định (Str 25, Agi 20, Spi 10, Hea 25, Luck 10; HP factor = 75, MP factor = 20), tiêu hao MP trung bình - thấp, hồi chiêu chuẩn hóa.
2. Xây dựng cơ chế **Nhiễm điện (BUFF_NHIEM_DIEN = 12)**:
   - Kẻ địch bị nhiễm điện khi trúng bất kỳ chiêu nào sẽ lan sét sang 2 kẻ địch lân cận ($\le 40\text{px}$) với 25% sát thương gốc.
   - Sau khi lan sét, mục tiêu gốc **ngay lập tức mất trạng thái Nhiễm điện** (tiêu hao điện tích).
   - Nếu kẻ nhận lan vốn đang nhiễm điện, nó tiếp tục giải phóng đòn sét lan tiếp theo rồi mất nhiễm điện.
3. Skill 3 (Kinh lôi bát thủ): Multi-hit liên hoàn (cấp 1-3: 3 đòn; cấp 4+: số đòn = cấp, tối đa 9 đòn), gây Nhiễm điện 5s.
4. Skill 4 (Hộ sát tiến - Nội tại): Đòn đánh gây thêm **Sát thương chuẩn** (`10 + (lv-1)*2 + 2% công`, hiện Font Trắng), tỉ lệ 10%–55% gây Nhiễm điện 5s.
5. Skill 5 (Dĩ lực đáo công): Duy trì 60s, hồi chiêu 90s. Giảm 5%–14% sát thương nhận vào; tỷ lệ 25%–70% phản đòn bằng 50%–95% công bản thân (hiện Font Vàng).
6. Skill 6 (Thiên lôi điện trảm - AoE): Gây Nhiễm điện 5s toàn bộ kẻ địch trúng chiêu.
7. Skill 7 (Sấm động dương gian - AoE): Nếu mục tiêu Nhiễm điện: gây thêm 20%–38% Sát thương chuẩn (Font Trắng) + Choáng 1s.
8. Skill 8 (Kiếm phi kinh thiên - AoE): Nếu mục tiêu Nhiễm điện: Quái thường bị **Execute (tiêu diệt ngay lập tức)**, hiện chữ **`"DIET"`** nhảy lên (không hiện dame); Boss/Người chơi chuyển toàn bộ thành Sát thương chuẩn.
9. Đồ họa & Font: Tạo bitmap font mới `fs_yellow.png` (Font Vàng phản đòn) và `fss_white.png` (Font Trắng sát thương chuẩn), Aura Nhiễm Điện màu vàng neon quanh người mục tiêu.

**Files thay đổi:**
- `game/res/font/fs_yellow.png` — Asset bitmap font số vàng neon 8px viền đen cho phản đòn.
- `game/res/font/fss_white.png` — Asset bitmap font số trắng tinh khôi 8px viền đen cho sát thương chuẩn.
- `game/class_zp.java` & `game/app/src/classes/class_zp.java` — Bổ sung chữ popup `"DIET"` vào mảng text hiệu ứng (index 5).
- `game/app/src/classes/Paint.java` — Thêm các loại popup `POPUP_REFLECT`, `POPUP_TRUE_DAMAGE`, `POPUP_TEXT_ONLY` và hỗ trợ vẽ font vàng, font trắng.
- `game/app/src/classes/class_zx.java` — Render hào quang Aura Nhiễm Điện màu vàng neon 5s quanh thân mục tiêu (effect type 12).
- `game/app/src/classes/MsgHandler.java` — Xử lý packet `BUFF_ATTACK` với các mã opcode: `b4 = -4` (phản đòn font vàng), `b4 = -5` (dame chuẩn font trắng), `b4 = -6` (popup DIET), `b4 = 12` (gán aura nhiễm điện), `b4 = -12` (xóa aura nhiễm điện).
- `game/app/src/classes/class_sc.java` — Cập nhật tooltip chi tiết bộ kỹ năng Kiếm Khách theo cơ chế mới.
- `server/KPAH/src/consts/BuffConst.java` — Thêm hằng số `BUFF_NHIEM_DIEN = 12`.
- `server/KPAH/src/skill/BuffInfluenceMonster.java` & `BuffInfluencePlayer.java` — Quản lý trạng thái, thời gian, thêm/xóa buff Nhiễm điện.
- `server/KPAH/src/services/BuffService.java` — Bổ sung các packet gửi popup font vàng, font trắng, popup DIET, gỡ bỏ nhiễm điện; xử lý giảm thương 5%–14% và phản đòn 25%–70% cho Skill 5 Kiếm Khách.
- `server/KPAH/src/player/Point.java` — Điều chỉnh hệ số HP Kiếm Khách từ 80 về 75 theo chuẩn thiết kế.
- `server/KPAH/src/manager/Manager.java` — Cấu hình chuẩn hóa thời gian duy trì (Skill 5 cố định 60s), thời gian hồi chiêu (Skill 5: 90s, Skill 6: 5s, Skill 7: 6s, Skill 8: 7s), bảng MP Kiếm Khách (Skill 4 nội tại = 0 MP).
- `server/KPAH/src/services/SkillService.java` — Cài đặt thuật toán lan sét đệ quy `triggerChainLightningMob` / `triggerChainLightningPlayer`, xử lý multi-hit Skill 3, sát thương chuẩn nội tại Skill 4, lan điện Skill 6, kích nổ sát thương chuẩn + choáng Skill 7, cơ chế Execute chữ DIET và sát thương chuẩn Skill 8 cho cả PvE và PvP.
- `server/update_skills_kiem_khach.sql` — Script SQL migration cập nhật mô tả chiêu thức Kiếm Khách.
- `docs/skills/kiem_khach.md` — Cập nhật đầy đủ tài liệu chi tiết bộ kỹ năng Kiếm Khách theo thiết kế mới.

**Kết quả:** ✅ Thành công
- Đã kiểm tra và biên dịch thành công 100% cả Client (`kpah_mod_v1.0.0.1.jar`, `KPAH_PROD.jar`) và Server (`KPAH.jar`).
- Toàn bộ bản sao lưu an toàn trước khi chỉnh sửa đã được lưu tại các thư mục `_backup/`.

---

