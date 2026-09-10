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
## [2026-09-10 17:43] — Task #83: Sửa Lỗi Logic Level Ràng Buộc Skill Buff Kiếm Khách

**Yêu cầu:** Rà soát và sửa lỗi level yêu cầu học skill buff của lớp Kiếm Khách phát sinh từ Task #82.
**Nguyên nhân:** Trong task #82, code Kiếm Khách (skill 4 & 5) được viết để đọc từ `LEVEL_ADD_SKILL[4][lvSkill]` và `LEVEL_ADD_SKILL[5][lvSkill]` (hàng DB chung có base lv 3), thay vì dùng công thức `base + Math.min(lvSkill, 9)` đúng với thiết kế (base lv 20 và 24). Lỗi logic nên không bị bắt khi build, nhưng gây ra việc Kiếm Khách học skill buff từ cấp 3 — thấp hơn rất nhiều so với nguyên bản.
**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java` — Sửa `getLevelAddSkill` nhánh Kiếm Khách:
  - `case 4`: Đổi từ đọc `LEVEL_ADD_SKILL[4][lvSkill]` (base lv 3) → `(short)(20 + Math.min(lvSkill, 9))` (base lv 20).
  - `case 5`: Đổi từ đọc `LEVEL_ADD_SKILL[5][lvSkill]` (base lv 3) → `(short)(24 + Math.min(lvSkill, 9))` (base lv 24).
  - Nhất quán với `EFF_BUFF_SKILL[KIEM_KHACH] = {20, 24}` và nguyên bản `class_qz.c` client KPAH.
**Kết quả:** ✅ Thành công. Server biên dịch sạch 100% với `ant clean jar` (Java 21), chỉ có 2 warning Lombok cũ không liên quan.
**Ghi chú:** Backup tạo tại `server/KPAH/src/manager/_backup/Manager.java.bak.20260910_17XX`.

---

## [2026-09-10 19:04] — Task #84: Điều Chỉnh Skill 4 & 5 Kiếm Khách (MP / Cooldown)

**Yêu cầu:**
1. Hoàn nguyên level học skill 4 & 5 Kiếm Khách về lv3 (Manager.java — task #83 sai thiết kế).
2. Skill 4 (xuyên giáp passive): Nhân đôi MP hao hụt.
3. Skill 5 (phản dame): Nhân 10 lần MP hao hụt + cooldown 90s. Cơ chế phản đòn: 10%(+5%/cấp) cơ hội phản 50%(+10%/cấp) dame bản thân (code đã có trong BuffService, giữ nguyên).

**Phạm vi thay đổi:**
- `server/KPAH/src/manager/Manager.java` — Hoàn nguyên Kiếm Khách skill 4 & 5 về base lv 3.
- `server/kpah.sql` — Cập nhật `SKILL_MP[0]` và `SKILL_COOLDOWN[0]`.
- `server/update_skills_kiem_khach.sql` — Script migration chạy trên DB thực.

**Giá trị mục tiêu:**
- Skill 4 MP: `[4,4,4,5,5,5,6,6,6,6,6]` → `[8,8,8,10,10,10,12,12,12,12,12]`
- Skill 5 MP: `[10,15,20,25,30,35,40,45,50,55,55]` → `[100,150,200,250,300,350,400,450,500,550,550]`
- Skill 5 CD: `[5000,60000,...×10]` → `[0,90000,90000,...×10]` (index 1-10 = 90s)

**Kết quả:** ✅ Thành công. Server biên dịch sạch 100% với `ant clean jar` (Java 21). Chỉ có 2 warning Lombok cũ không liên quan.
**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java` — Hoàn nguyên skill 4 & 5 Kiếm Khách về base lv3 đúng thiết kế gốc.
- `server/kpah.sql` — Cập nhật `SKILL_MP[KIEM_KHACH]` skill 4 & 5; `SKILL_COOLDOWN[KIEM_KHACH]` skill 5.
- `server/update_skills_kiem_khach.sql` — Tạo script migration chạy trên DB thực.
**Ghi chú:** Backup tại `server/KPAH/src/manager/_backup/Manager.java.bak.20260910_1906`, `server/_backup/kpah.sql.bak.20260910_1907`.

## [2026-09-10 20:35] — Task #85: Điều Chỉnh Bộ Kỹ Năng Pháp Sư & Tạo Tài Liệu Chiêu Thức

**Yêu cầu:**
1. Mốc level học:
   - Skill 4 (Hồi công lực đan) & Skill 5 (Hồi lực tiến): Base học lv 3.
   - Skill 6 (Hồi sinh) & Skill 7 (Song hộ công thủ): Base học lv 6.
2. Skill 5 (Hồi lực tiến): Bị động tăng dame theo lượng MP đang có (cấp 1: 5% MP, tăng 2%/cấp).
3. Skill 4 (Hồi công lực đan): MP x10, Cooldown 120s, thời gian buff cố định 90s.
4. Skill 6 (Hồi sinh): Rút 80% HP và MP của bản thân khi hồi sinh đồng đội, Cooldown 3 phút (180s).
5. Skill 7 (Song hộ công thủ): MP x10, khi bị tấn công hồi mana = 10% (+5%/cấp) dame nhận vào; hồi máu = 20% (+5%/cấp) mana tiêu hao.
6. 3 chiêu AoE cuối (Skill 8, 9, 10): Giảm cooldown hợp lý (20s / 40s / 60s).
7. Tài liệu hóa: Tạo thư mục `docs/skills/` với 2 file markdown chi tiết cho Pháp Sư và Kiếm Khách.

**Kế hoạch thực hiện:**
1. Backup các file liên quan: `Manager.java`, `BuffService.java`, `Point.java`, `kpah.sql`.
2. Cập nhật `Manager.java`: `getLevelAddSkill`, `getTimeLifeBuffSkill`.
3. Cập nhật `Point.java`: Skill 5 nội tại tăng dame theo MP đang có cho Pháp Sư.
4. Cập nhật `BuffService.java`: Cơ chế hồi sinh rút 80% HP/MP và Song hộ công thủ hồi mana/hồi máu.
5. Cập nhật `kpah.sql` & tạo script migration `update_skills_phap_su.sql`: `SKILL_MP`, `SKILL_COOLDOWN`, `skill_news`.
6. Biên dịch server bằng `ant clean jar` để verify 100%.
7. Tạo tài liệu `docs/skills/phap_su.md` và `docs/skills/kiem_khach.md`.
8. Cập nhật trạng thái hoàn thành vào `TASK.md`.

**Kết quả:** ✅ Thành công. Server biên dịch sạch 100% với `ant clean jar` (Java 21).
**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java` — Mốc level học (Skill 4, 5 base 3; Skill 6, 7 base 6), overload `getTimeLifeBuffSkill` cố định 90s cho skill 4 Pháp Sư.
- `server/KPAH/src/player/Point.java` — Skill 5 (Hồi lực tiến) nội tại tăng sát thương trực tiếp theo lượng MP đang có (cấp 1: 5%, +2%/cấp).
- `server/KPAH/src/services/BuffService.java` — Skill 6 (Hồi sinh) rút 80% HP và MP hiện tại của bản thân; Skill 7 (Song hộ công thủ) hồi mana = 10% (+5%/cấp) sát thương nhận vào, hồi máu = 20% (+5%/cấp) mana tiêu hao.
- `server/KPAH/src/services/SkillService.java` — Hồi máu từ mana tiêu hao khi có buff Song hộ công thủ trong cả tấn công quái lẫn người chơi.
- `server/kpah.sql` — Cập nhật `SKILL_MP`, `SKILL_COOLDOWN` và `skill_news` (AoE CD: 20s/40s/60s).
- `server/update_skills_phap_su.sql` — Script migration chạy trên database thực tế (Termux/MySQL).
- `docs/skills/phap_su.md` — Tài liệu chi tiết toàn bộ 11 kỹ năng lớp Pháp Sư.
- `docs/skills/kiem_khach.md` — Tài liệu chi tiết toàn bộ 9 kỹ năng lớp Kiếm Khách.
**Ghi chú:** Backup tại `_backup/` của từng thư mục tương ứng.

## [2026-09-10 20:42] — Task #86: Tinh Chỉnh Cơ Chế Rút HP/MP Chiêu Hồi Sinh & Cập Nhật CD 3 Chiêu AoE Pháp Sư

**Yêu cầu:**
1. Chiêu Hồi Sinh (Skill 6):
   - Rút HP và Mana của bản thân để truyền sang hồi sinh cho mục tiêu: cần ít thì rút ít, cần nhiều thì rút nhiều, nếu lượng cần quá cao thì tối đa chỉ rút 80% HP và MP của bản thân (không theo cấp chiêu, giữ tối thiểu 1 HP).
   - Thời gian hồi chiêu: Cấp 1 là 180s, giảm 10s mỗi cấp (Cấp 1: 180s, Cấp 2: 170s, ..., Cấp 10: 90s).
2. 3 chiêu AoE:
   - Skill 8 (Hải long xuất thế): Hồi chiêu (CD) là **4s** (`4000ms`).
   - Skill 9 (Song long thị uy): Hồi chiêu (CD) là **5s** (`5000ms`).
   - Skill 10 (Hàn băng vũ): Hồi chiêu (CD) là **6s** (`6000ms`).
3. Cập nhật `docs/skills/phap_su.md`, `kpah.sql`, `update_skills_phap_su.sql` và biên dịch lại server.

**Kế hoạch thực hiện:**
1. Cập nhật `MapService.java`: Thêm overload `revivePlayer(Player player, int hpPlus, int mpPlus)`.
2. Cập nhật `BuffService.java`: Tính lượng HP/MP mục tiêu cần, rút tương ứng từ bản thân (tối đa 80% HP/MP) và truyền sang cho mục tiêu.
3. Cập nhật `kpah.sql` & `update_skills_phap_su.sql`: Đặt CD skill 6 giảm 10s mỗi cấp (180s -> 90s), CD skill 8, 9, 10 trong `SKILL_COOLDOWN` và `skill_news` thành 4s, 5s, 6s.
4. Biên dịch server bằng `ant clean jar` để verify code Java.
5. Cập nhật tài liệu `docs/skills/phap_su.md`.
6. Cập nhật kết quả vào `TASK.md`.

**Kết quả:** ✅ Thành công. Server biên dịch sạch 100% với `ant clean jar` (Java 21).
**Files thay đổi:**
- `server/KPAH/src/services/MapService.java` — Thêm overload `revivePlayer(Player, int, int)`.
- `server/KPAH/src/services/BuffService.java` — Cập nhật logic Hồi Sinh: lấy đầy đủ HpMax và MpMax của mục tiêu để bù đắp (không theo cấp chiêu), trích từ HP/MP bản thân (tối đa 80%), người nhận nhận đúng lượng Pháp Sư trao cho.
- `server/kpah.sql` — Cập nhật `SKILL_COOLDOWN` cho skill 6 (180s giảm 10s/cấp) và skill 8 (4s), skill 9 (5s), skill 10 (6s).
- `server/update_skills_phap_su.sql` — Cập nhật script migration với cooldown skill 6 (180s - 90s) và AoE mới (4s / 5s / 6s).
- `docs/skills/phap_su.md` — Cập nhật mô tả chiêu Hồi Sinh (rút HP/MP, cooldown 180s - 90s) và thời gian hồi chiêu 3 chiêu AoE.
**Ghi chú:** Đã kiểm tra logic chuyển đổi HP/MP đảm bảo an toàn (không gây tử vong cho người dùng chiêu, luôn giữ lại tối thiểu 1 HP).

---

## [2026-09-10 21:02] — Task #87: Cập Nhật Toàn Diện Mô Tả Kỹ Năng Client & Database Cho Kiếm Khách & Pháp Sư

**Yêu cầu:** Sửa đổi toàn bộ mô tả kỹ năng (in-game client & database) phản ánh chính xác các cơ chế và hồi chiêu mới đã được chỉnh sửa.
**Files thay đổi:**
- `game/app/src/classes/class_sc.java` — Nâng cấp phương thức `j(int n)` hiển thị tooltip mô tả kỹ năng trong giao diện Kỹ Năng của client:
  - **Kiếm Khách:**
    - Skill 4 (Hộ sát tiến): Hiển thị "Hộ sát tiến (Bị động)", tăng điểm xuyên giáp `+x`, không hồi chiêu.
    - Skill 5 (Dĩ lực đáo công): Hiển thị "Dĩ lực đáo công (Phản đòn)", tỷ lệ phản `10% (+5%/cấp)`, lượng phản `50% (+10%/cấp) dame bản thân`, thời gian buff, hồi chiêu 90s.
    - Skill 6, 7, 8 (AoE): Hiển thị tên kỹ năng kèm nhãn `(Lan)` và thời gian hồi chiêu cụ thể (60s / 120s / 300s).
  - **Pháp Sư:**
    - Skill 4 (Hồi công lực đan): Tăng Max HP & MP `x%`, thời gian cố định 90s, hồi chiêu 120s.
    - Skill 5 (Hồi lực tiến): "Hồi lực tiến (Bị động)", tăng sát thương `5% (+2%/cấp)` theo MP hiện có, không hồi chiêu.
    - Skill 6 (Hồi sinh): "Hồi sinh đồng đội", rút HP/MP bù cho mục tiêu (tối đa 80% bản thân), hồi chiêu `180s` ở cấp 1 giảm `10s` mỗi cấp (`180s - 90s`).
    - Skill 7 (Song hộ công thủ): Hồi MP khi bị đánh `10% (+5%/cấp)` dame, hồi máu khi dùng skill `20% (+5%/cấp)` mana tiêu hao, thời gian buff, hồi chiêu 60s.
    - Skill 8, 9, 10 (AoE): Hiển thị tên kỹ năng `(Lan)` và thời gian hồi chiêu chuẩn xác (Hải long xuất thế: 4s / Song long thị uy: 5s / Hàn băng vũ: 6s).
  - **Kỹ năng chưa học:** Vẫn hiển thị tên và mô tả tóm tắt kỹ năng kèm điều kiện Lv yêu cầu để người chơi nắm rõ công dụng trước khi học.
- `server/kpah.sql` — Cập nhật cột `des` và `cooldown` trong bảng `skill_news` cho 15 kỹ năng mới (đặc biệt là Kiếm Khách ID 1, 2, 3 và Pháp Sư ID 7, 8, 9) đồng bộ nội dung và thời gian hồi chiêu.
- `server/update_all_skill_descriptions.sql` — Tạo script migration cập nhật mô tả kỹ năng bảng `skill_news` trực tiếp trên MySQL.
- `game/build/dist/KPAH_PROD.jar` & `game/build/dist/kpah_mod_v1.0.0.1.jar` — Biên dịch thành công 100% bằng Java 8 (`ant dist-prod`).
**Kết quả:** ✅ Thành công. Mô tả kỹ năng trong client và database đồng bộ hoàn toàn với logic thực tế.
**Ghi chú:** Backup tạo tại `game/app/src/classes/_backup/class_sc.java.bak.20260910_2100` và `server/_backup/kpah.sql.bak.20260910_2100`.


---

## [2026-09-10 21:12] — Task #88: Chuẩn Hóa Mô Tả Kỹ Năng Trực Quan (Kiếm Khách & Pháp Sư)

**Yêu cầu:**
1. Bỏ toàn bộ các dạng mở ngoặc công thức như `(+5%/cấp)`, `(+10%/cấp)`, `(+2%/cấp)`... khỏi mô tả kỹ năng; cấp bao nhiêu tính toán và hiển thị trực tiếp con số bấy nhiêu cho trực quan.
2. Chuẩn hóa hình thức mô tả của toàn bộ kỹ năng cho 2 phái Kiếm Khách và Pháp Sư theo cấu trúc thống nhất:
   - Tên chiêu
   - Mô tả
   - Lực công: value (nếu có)
   - Hồi chiêu: value (nếu có)
   - Mana tiêu hao: value (nếu có)
   - Chỉ số buff hoặc cơ chế hiện tại
   - Cấp độ yêu cầu (nếu chưa đạt max cấp)
3. Đồng bộ mô tả chuẩn hóa vào Client game, Database MySQL và các tài liệu kỹ năng.

**Files thay đổi:**
- `game/app/src/classes/class_sc.java` — Triển khai `formatSkillDescription(int classChar, int n)`:
  - Kiếm Khách (Skill 0 – 8) & Pháp Sư (Skill 0 – 10): Định dạng đồng nhất theo mẫu người dùng cung cấp.
  - Tính toán trực tiếp con số thực tế cho từng cấp độ (Cấp 1 đến Cấp 10), loại bỏ triệt để các ghi chú `+5%/cấp`.
  - Hiển thị linh hoạt: Kỹ năng đã học hiển thị chỉ số của cấp hiện tại; kỹ năng chưa học hiển thị "Tình trạng: Chưa học", chỉ số cơ bản cấp 1 và cấp độ yêu cầu để người chơi xem trước.
- `docs/skills/kiem_khach.md` — Chuẩn hóa bảng tổng quan và chi tiết từng chiêu theo mẫu mới, liệt kê bảng chỉ số chi tiết từng cấp không dùng công thức trong ngoặc.
- `docs/skills/phap_su.md` — Chuẩn hóa bảng tổng quan và chi tiết từng chiêu theo mẫu mới, bảng chỉ số cụ thể từng cấp cho các skill 4, 5, 6, 7 và hồi chiêu chuẩn 4s, 5s, 6s cho skill AoE 8, 9, 10.
- `server/kpah.sql` & `server/update_all_skill_descriptions.sql` — Cập nhật `des` và `cooldown` bảng `skill_news` trong database.
- `game/build/dist/KPAH_PROD.jar` & `game/build/dist/kpah_mod_v1.0.0.1.jar` — Biên dịch sạch 100% bằng Java 8 (`ant dist-prod`).

**Kết quả:** ✅ Thành công. Mô tả kỹ năng trực quan, rõ ràng, đồng bộ 100% giữa Client, Database và Tài liệu.
**Ghi chú:** Backup tạo tại `docs/skills/_backup/kiem_khach.md.bak.20260910_2110` và `docs/skills/_backup/phap_su.md.bak.20260910_2110`.


---

## [2026-09-10 21:37] — Task #89: Khắc Phục Lỗi Khuyết Mô Tả Kỹ Năng & Lỗi Đứng Game Khi Xem Tooltip Vật Phẩm Tùy Biến

**Yêu cầu:**
1. Khắc phục lỗi khuyết mô tả trong khung Kỹ Năng: Chữ bị cắt cụt mép phải do quá dài, các dòng dưới bị mất do tràn đáy khung kỹ năng, trùng lặp tên chiêu ở dòng đầu tiên.
2. Khắc phục lỗi khi bấm vào các vật phẩm tùy biến (như Rương Tinh Anh, Tinh Anh Đan, v.v.) trong hành trang không hiển thị tooltip và làm đứng/treo game.

**Nguyên nhân gốc rễ:**
1. Khung Kỹ Năng client chỉ có kích thước cố định ~130px chiều ngang (tối đa ~22-25 ký tự/dòng) và ~60px chiều dọc (tối đa 5 dòng chữ). Mô tả cũ chèn thêm dòng tên chiêu lặp lại với tiêu đề sẵn có của game, và các chuỗi text dài 35-40 ký tự khiến chữ bị cắt mép phải và các dòng cuối trôi ra khỏi vùng hiển thị.
2. Khi bấm vào vật phẩm potion trong hành trang, client gọi `class_nu.b(ui, n)`. Với các potion tùy biến (ID >= 20, như ID 106, 107, 160..162), hàm cố truy cập mảng `class_yi.V[n - 19].b` (bảng hồi phục HP/MP chỉ nạp 20 loại dược phẩm đầu). Việc truy cập index vượt quá 20 dẫn đến `NullPointerException`, làm đứt luồng sự kiện UI và khiến game bị đơ cứng hoàn toàn.

**Files thay đổi:**
- `game/app/src/classes/ModHelpers.java` — Thêm method `getPotionTooltip(class_nu ui, int n)` xử lý an toàn:
  - Hiển thị trọn vẹn tên và mô tả nhiều dòng từ database, kèm số lượng của vật phẩm.
  - Bọc try-catch và kiểm tra null an toàn tuyệt đối, loại bỏ hoàn toàn nguy cơ `NullPointerException` hoặc `ArrayIndexOutOfBoundsException`.
- `game/app/src/classes/class_nw.java`, `class_or.java`, `class_on.java` — Điều hướng các sự kiện click / hover item hành trang sang `ModHelpers.getPotionTooltip`.
- `game/app/src/classes/class_sc.java` — Tối ưu hóa `formatSkillDescription`:
  - Bỏ dòng tên chiêu lặp lại ở đầu.
  - Rút ngắn toàn bộ các dòng mô tả và thông số <= 22 ký tự, không bao giờ bị cắt mép phải.
  - Khống chế số dòng tối đa 4-5 dòng, hiển thị đầy đủ 100% trong khung mà không bị tràn đáy.
- `game/build/dist/KPAH_PROD.jar` & `kpah_mod_v1.0.0.1.jar` — Biên dịch thành công và khởi chạy lại giả lập MicroEmulator (`task-821`).

**Kết quả:** ✅ Thành công. Mô tả kỹ năng hiển thị trọn vẹn không bị cắt; xem tooltip Rương Tinh Anh, Tinh Anh Đan và mọi vật phẩm tùy biến mượt mà, không bị đứng game.
**Ghi chú:** Backup tạo tại `game/app/src/classes/_backup/class_sc.java.bak.20260910_2135` và `game/app/src/classes/_backup/ModHelpers.java.bak.20260910_2135`.

---

## [2026-09-10 22:05] — Task #90: Sửa Hiển Thị Tinh Anh Đan, Chuẩn Hóa Cooldown, Đồng Thời 2 Buff Pháp Sư & Thêm HUD Chỉ Số +Bonus Xanh Lá

**Yêu cầu:**
1. Sửa thông báo dùng Tinh Anh Đan (+20% sát thương, 20% giáp và 20% HP) thay vì chỉ hiện `+20% dame`.
2. Đảm bảo kỹ càng cơ chế hồi chiêu, hồi đúng với mô tả (chuẩn hóa tập trung tại Manager.java và SkillService.java).
3. Cho phép kích hoạt và duy trì đồng thời cả 2 kỹ năng buff chủ động của Pháp Sư (Skill 4: Hồi công lực đan & Skill 7: Song hộ công thủ).
4. Thêm hiển thị HUD bên trái dưới phần tọa độ:
   tấn công: value +bonus
   thủ ma: value +bonus
   thủ vật: value +bonus
   (Trong đó phần `+bonus` màu xanh lá cây, là số liệu cụ thể được cộng thêm từ các hiệu ứng và buff).

**Files thay đổi:**
- `server/KPAH/src/services/Service.java` — Đổi thông báo Tinh Anh Đan thành `"+20% Công-Thủ-HP"`.
- `server/KPAH/src/manager/Manager.java` — Chuẩn hóa thời gian buff chiêu 7 Pháp Sư (60s), chuẩn hóa tập trung thời gian hồi chiêu `getSkillCooldown` cho toàn bộ các kỹ năng Kiếm Khách & Pháp Sư.
- `server/KPAH/src/services/SkillService.java` — Đồng bộ gửi thông tin hồi chiêu và thời gian buff qua `Manager.getSkillCooldown` và `Manager.getTimeLifeBuffSkill`.
- `server/update_skills_phap_su.sql` — Cập nhật cooldown Skill 7 thành 60s cho mọi cấp trong DB.
- `game/app/src/classes/MainCharInfo.java` — Đổi tên hiển thị badge hiệu ứng 23 thành `"Song Hộ"`, thêm helper `hasBuffTinhAnh()`.
- `game/app/src/classes/Paint.java` — Vẽ HUD bên trái dưới tọa độ: `tấn công: value +bonus`, `thủ ma: value +bonus`, `thủ vật: value +bonus` (nhãn và giá trị màu trắng, `+bonus` màu xanh lá cây).
- `game/app/src/classes/ModController.java` — Bổ sung `handleAutoSupportSkills` tự động kiểm tra và duy trì đồng thời toàn bộ các buff chủ động trong trang Hỗ Trợ độc lập nhau.
- `server/dist/KPAH.jar` & `game/build/dist/KPAH_PROD.jar` — Biên dịch sạch 100% bằng ant.

**Kết quả:** ✅ Thành công. Đã test và xác thực đầy đủ cả 4 tính năng.
**Ghi chú:** Backup tạo tại `game/app/src/classes/_backup/Paint.java.bak.20260910_2204` và `game/app/src/classes/_backup/ModController.java.bak.20260910_2204`.
