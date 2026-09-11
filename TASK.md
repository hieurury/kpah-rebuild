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

---

## [2026-09-11 19:10] — Task #93: Cân Bằng Mana Kiếm Khách & Pháp Sư, Khắc Phục Lỗi HUD HP/MP Bar và Đồng Bộ Bitmap Font Sát Thương Độc Màu Tím

**Yêu cầu:**
- Cân bằng lượng mana tiêu hao giữa Kiếm Khách và Pháp Sư:
  - Kiếm Khách có mức tiêu tốn mana trung bình, điều chỉnh giảm mana tiêu hao hợp lý (trước đó quá lớn, skill 5 tốn 100-550 MP nuốt sạch mana và bị lỗi tràn byte).
  - Pháp Sư là class thiên về mana, hao nhiều mana, sức mạnh phụ thuộc mana, hồi chiêu nhanh nhất: tăng mana tiêu tốn của 3 chiêu AoE liên hoàn (Skill 8 hồi 4s, Skill 9 hồi 5s, Skill 10 hồi 6s) cho tương xứng với bể mana khổng lồ.
- Khắc phục triệt để lỗi HUD thanh HP & MP bar của người chơi nhảy đầy và cạn liên tục, hiển thị hết mana nhưng thực chất còn khi bơm/rút máu/mana nhanh.
- Thiết kế lại số hiển thị sát thương độc màu tím đồng bộ 100% về font chữ, kích thước, đổ bóng pixel art với các số nhảy khác của game (thay vì font vector trơn lệch tone trước đó).

**Files thay đổi:**
- `server/KPAH/src/services/UseItemService.java` — Sửa lỗi typo nghiêm trọng trong `onPlusHp`: đổi `writeByte(4)` (bình mana) thành `writeByte(1)` (bình HP), chấm dứt việc server vô tình ghi đè MP bằng HP mỗi khi hồi máu.
- `server/KPAH/src/manager/Manager.java` — Thêm `applySkillMPRebalance()` cân bằng `SKILL_MP` cho Kiếm Khách (Skill 5: 35-80 MP, Skill 6: 20-45 MP, Skill 7: 25-55 MP, Skill 8: 35-75 MP) và Pháp Sư (Skill 7: 80-200 MP, Skill 8: 50-140 MP, Skill 9: 70-180 MP, Skill 10: 90-240 MP); đảm bảo không vượt quá 240 MP để triệt tiêu lỗi tràn byte giao thức MIDP.
- `server/kpah.sql` & `server/update_skills_mana_rebalance.sql` — Cập nhật bản ghi `SKILL_MP` trong database và tạo script migration mới.
- `game/res/font/fs_poison.png` — Tạo mới Bitmap Font màu tím độc 7x336 pixel art chuẩn xác dựa trên `fs4_red.png` với 42 ký tự và viền đen đổ bóng 3D.
- `game/app/src/classes/Paint.java` — Thêm hàm `drawPoisonBitmapString` render số sát thương độc chuẩn xác theo Bitmap Font pixel art; thêm hàm `paintPlayerHpMpBar` vẽ đè 2 thanh HP/MP bar siêu mượt, loại bỏ triệt để lỗi vẽ số âm và tràn số.
- `game/app/src/classes/MsgHandler.java` — Thêm xử lý gói tin `case 22` (`USE_POTION`): phân định rạch ròi giữa cập nhật HP (`v, t`) và MP (`bz`), đồng bộ chính xác cả khi tăng lẫn giảm/rút mana, chặn đứng lỗi chết oan khi MP = 0.
- `docs/skills/kiem_khach.md` & `docs/skills/phap_su.md` — Cập nhật tài liệu kỹ năng đồng bộ với các thông số mana mới.
- `server/dist/KPAH.jar` & `game/build/dist/KPAH_PROD.jar` & `game/build/dist/KPAH_MOD.jar` — Biên dịch sạch sẽ 100% bằng ant với JDK 21 (server) và JDK 8 (client).
- Backup files: Tạo tại `_backup/` trong các thư mục tương ứng.

**Kết quả:** ✅ Thành công. Toàn bộ 3 vấn đề đã được xử lý triệt để, đồng bộ và biên dịch không có bất kỳ lỗi nào.

---

## [2026-09-11 20:05] — Task #94: Đồng Bộ Số Liệu Hiển Thị HP, MP, Độc Và Hiển Thị Thông Số Trên Status HUD

**Yêu cầu:**
- Đồng bộ hóa toàn diện các số liệu cộng / trừ HP, MP và Độc:
  - HP cộng / trừ: định dạng `HP +10` / `HP -10`, màu **Đỏ**.
  - MP cộng / trừ: định dạng `MP +10` / `MP -10`, màu **Xanh dương**.
  - Độc rút máu DoT: định dạng `Độc -10`, màu **Tím**.
  - Dẹp bỏ hoàn toàn các lỗi font hiển thị lung tung của game cũ (`hp+`, `mp+`, lỗi biến khoảng trắng hoặc tiếng Việt thành số 0).
- Hiển thị thông số HP và MP bên phải thanh status bar của người dùng:
  - Bên phải thanh HP: hiển thị số `curHp/maxHp` (ví dụ: `1201/1780`) bằng font **màu Đỏ**.
  - Bên phải thanh MP: hiển thị số `curMp/maxMp` (ví dụ: `12900/15000`) bằng font **màu Xanh dương**.
  - Bố trí ngay ngắn, căn dòng hoàn hảo, có khung nền tối mờ sang trọng nối liền với khung Avatar bảo đảm số liệu luôn nổi bật trên mọi nền bản đồ.

**Files thay đổi:**
- `game/app/src/classes/Paint.java` — Xây dựng hệ thống `StatusPopup` quản lý tập trung toàn bộ các số nổi HP (Đỏ: `class_d.j[2]`), MP (Xanh dương: `class_d.j[3]`) và Độc (Tím: `class_d.j[4]`); nâng cấp hàm `paintPlayerHpMpBar` vẽ bảng thông số `curHp/maxHp` màu Đỏ và `curMp/maxMp` màu Xanh dương bên phải thanh bar.
- `game/app/src/classes/MsgHandler.java` — Chuyển toàn bộ các sự kiện hồi máu, trừ máu, hồi mana, trừ mana (`case 22`) và sát thương độc DoT (`case 89`) sang sử dụng `Paint.addStatusPopup`.
- `game/build/dist/KPAH_PROD.jar` & `game/build/dist/KPAH_MOD.jar` — Biên dịch sạch 100% bằng ant với Java 8.
- Backup files: Tạo tại `game/app/src/classes/_backup/` theo quy định.

**Kết quả:** ✅ Thành công. Toàn bộ số liệu hiển thị và Status HUD đã được đồng bộ chuẩn xác 100%.

---



