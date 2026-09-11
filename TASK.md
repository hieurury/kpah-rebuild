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

- **Tinh chỉnh hoàn thiện (theo phản hồi thực tế từ hình ảnh gameplay)**:
  - Loại bỏ hoàn toàn hộp nền đen đặc che tầm nhìn trên status bar.
  - Đồng bộ hóa toàn bộ số nổi về font pixel art 8px (`fs_red.png`, `fs_blue.png`, `fs_poison.png`) bằng đúng kích cỡ font EXP của game (`+100`, `-100`, `-35`).
  - Bổ sung ký tự `'/'` vào các bộ font pixel art và áp dụng cho `curHp/maxHp` (Đỏ) và `curMp/maxMp` (Xanh dương) trên status bar gọn gàng, sắc nét.
  - Đã đóng gói và khởi chạy thành công trên MicroEmulator (`task-554`).

---

## [2026-09-11 20:55] — Task #95: Điều Chỉnh Tỷ Lệ Drop Quái, Đồng Bộ Thẻ Mua Bán Thợ Rèn Hắc Ngưu (150% Phí, Dùng Giao Dịch Tại Chỗ) Và Tối Ưu HUD Độ Bền Hỏng

**Yêu cầu:**
1. Điều chỉnh tỷ lệ rơi đồ từ quái:
   - Bình potion (HP/MP): 20%
   - Xu: 5%
   - Trang bị: 2%
2. Thẻ mua bán (ItemEquip ID 675 & ItemPotion ID 33):
   - Xuất hiện trong gian hàng của Thợ rèn Hắc Ngưu (giá 10 lượng, thời hạn ấn định 3 ngày = 4320 phút).
   - Đồng bộ toàn diện: dù người chơi sở hữu thẻ dưới dạng nào cũng kích hoạt tính năng như nhau.
   - Khi có thẻ trong hành lý: tự động sửa chữa trang bị hỏng ở mọi nơi ngay lập tức với chi phí 150% xu.
   - Khi tự động sửa chữa, hiển thị chat only: `Đã tiêu tốn value xu để sửa các trang bị hỏng.`
   - Bấm "Sử dụng" thẻ trực tiếp trong hành lý để mở giao diện gian hàng giao dịch với Thợ rèn Hắc Ngưu ngay tại chỗ (vô hạn lượt dùng khi còn hạn).
   - Cập nhật mô tả thẻ chuẩn xác: `"Thẻ mua bán. Cho phép tự động sửa chữa trang bị ở mọi nơi với giá 150%. Sử dụng trực tiếp để giao dịch với Hắc Ngưu.\nThời gian còn lại: value"`
3. HUD Độ bền trang bị:
   - Khi trang bị hỏng: bỏ hoàn toàn cơ chế chớp nháy đỏ và chữ "HỎNG", chỉ hiển thị số `0` màu đỏ tĩnh và vẽ viền đỏ cho hàng trang bị hỏng đó.

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java` — Chỉnh tỷ lệ drop: `potionRate = 20%`, `goldRate = 5%`, `equipRate = 2%`.
- `server/KPAH/src/services/InventoryService.java` — Thêm helper `hasTheMuaBan` kiểm tra đồng bộ cả Potion ID 33 và ItemEquip ID 675.
- `server/KPAH/src/services/UseItemService.java` — Khi bấm dùng Thẻ mua bán (cả Potion 33 và Equip 675), mở trực tiếp gian hàng vũ khí Hắc Ngưu tại chỗ; không trừ số lượng thẻ.
- `server/KPAH/src/services/SkillService.java` — Sửa chi phí tự sửa vũ khí/cuốc/trang bị hỏng thành 150% khi có Thẻ mua bán, hiển thị thông báo chat only: `Đã tiêu tốn value xu để sửa các trang bị hỏng.`
- `server/KPAH/src/player/Player.java` — Tự động sửa chữa trang bị phòng thủ bị hỏng lúc bị quái đánh với chi phí 150% xu nếu có Thẻ mua bán, thông báo chat only: `Đã tiêu tốn value xu để sửa các trang bị hỏng.`
- `game/app/src/classes/Paint.java` — Tinh chỉnh `paintRightEquipDurability`: bỏ chữ `(HỎNG)` và hiệu ứng chớp tắt `blink`, hiển thị số `0` màu đỏ tĩnh `class_d.j[2]` và vẽ viền đỏ `0xFF1744` cho hàng hỏng.
- `server/update_the_mua_ban.sql` & `server/kpah.sql` — Cập nhật mô tả Thẻ mua bán, `ndayLoan = 4320` (3 ngày), `price = 10` lượng.
- `server/dist/KPAH.jar` & `game/build/dist/KPAH_PROD.jar` — Biên dịch thành công 100% bằng ant với JDK 21 và JDK 8. Khởi chạy client mới trên MicroEmulator (`task-916`).
- Backup files: Lưu tại `_backup/` trong các thư mục tương ứng.

**Kết quả:** ✅ Thành công. Mọi tính năng yêu cầu đã được triển khai, kiểm thử và biên dịch hoàn hảo.

## [2026-09-11 21:10] — Task #96: Đồng Bộ Mô Tả Thẻ Mua Bán Trực Tiếp Từ Server, Sửa Lỗi Tiền Tệ Mua Hàng (Lượng vs Xu) Và Minh Bạch Hóa Server Log

**Yêu cầu:**
- Cập nhật mô tả hiển thị của Thẻ Mua Bán chuẩn xác theo yêu cầu:
  `Thẻ mua bán. Cho phép tự động sửa chữa trang bị ở mọi nơi với giá 150%. Sử dụng trực tiếp để giao dịch với Hắc Ngưu.`
  kèm thời gian còn lại tự động tính bởi Client (`Thời gian còn lại: value`).
- Sửa lỗi nạp mô tả Thẻ Mua Bán: đảm bảo tự động đồng bộ trực tiếp từ code nạp Server (`Manager.java`) và tự động cập nhật database, không phụ thuộc vào việc chạy tay migration script.
- Khắc phục lỗi tiền tệ khi mua vật phẩm có hạn (`ndayLoan != 0`): trước đó code trừ cả Xu lẫn Lượng và log ra đơn vị `xu`, nay tách biệt rạch ròi:
  - Vật phẩm có hạn (`ndayLoan != 0` như Thẻ Mua Bán): chỉ trừ **Lượng**, không trừ Xu.
  - Vật phẩm vĩnh viễn (`ndayLoan == 0`): chỉ trừ **Xu**.
  - Log server hiển thị minh bạch, chính xác từng loại tiền tệ (`với giá %s lượng` hoặc `với giá %s xu`) cho cả Trang Bị, Ngọc và Dược Phẩm.
- Đảm bảo các Thẻ Mua Bán đã mua trước đó trong hành lý nếu có thời hạn cũ (168h / 7 ngày) sẽ tự động đồng bộ về tối đa 3 ngày (4320 phút = 72h).

**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java`:
  - Trong quá trình nạp `item_equipment` và `potion_template`: tự động thực thi query cập nhật database và ghi đè trực tiếp trong bộ nhớ cho Thẻ Mua Bán (ID 675 và Potion 33) với đúng mô tả chuẩn, giá 10 Lượng, hạn 4320 phút (3 ngày).
- `server/KPAH/src/services/ShopService.java`:
  - `buyItemNpcShop`: Tách biệt điều kiện trừ tiền: nếu `template.getNdayLoan() != 0` thì chỉ gọi `minusLuong()`, ngược lại chỉ gọi `minusXu()`.
  - Log mua hàng của Server được cập nhật động theo đơn vị tiền tệ thực tế (`lượng` hoặc `xu`).
  - Áp dụng logic tương tự cho nhóm Dược Phẩm (Potion ID 33 dùng Lượng).
- `server/KPAH/src/daos/PlayerDAO.java`:
  - Khi load trang bị người chơi từ database, nếu phát hiện Thẻ Mua Bán có `dayUse > 4320`, tự động giới hạn về `4320` phút (3 ngày) để đồng bộ dữ liệu người chơi cũ.
- `server/update_the_mua_ban.sql`: Cập nhật lại câu lệnh SQL đồng bộ tuyệt đối với logic trên server.
- `server/KPAH/dist/KPAH.jar`: Biên dịch sạch sẽ 100% bằng ant với JDK 21.
- Backup files: Tạo tại `_backup/` trong các thư mục `server/KPAH/src/services/`, `server/KPAH/src/manager/`, `server/KPAH/src/daos/` theo đúng quy định.

**Kết quả:** ✅ Thành công. Mô tả Thẻ Mua Bán, cơ chế thanh toán Lượng/Xu và hệ thống log server đã được đồng bộ chuẩn xác và minh bạch 100%.

## [2026-09-11 22:15] — Task #97: Khôi Phục & Nâng Cấp Toàn Diện Thợ Rèn Thần Bí (Gian Hàng Chế Tạo 5 Phẩm Cấp & Cơ Chế Phân Rã Trang Bị)

**Yêu cầu:**
1. **Khôi phục tính năng Thợ Rèn Thần Bí (NPC ID -8)**:
   - Giao tiếp NPC -> Hiện menu 2 lựa chọn: `Chế tạo trang bị` và `Phân rã trang bị`.
2. **Chế tạo trang bị**:
   - Chọn class nhân vật: `Kiếm khách`, `Chiến binh`, `Pháp sư`, `Đấu sĩ`, `Cung thủ`.
   - Chọn loại trang bị: `Vũ khí`, `Áo`, `Quần`, `Nón`, `Giày`, `Găng tay`, `Nhẫn`, `Dây chuyền`, `Ngọc`.
   - Mở giao diện **Gian Hàng Chế Tạo** (`CraftShopScreen`) thay vì dạng text menu cũ:
     - 5 tab phẩm cấp: **Ngũ phẩm -> Tứ phẩm -> Tam phẩm -> Nhị phẩm -> Nhất phẩm** (chuyển đổi bằng phím điều hướng trái/phải hoặc click tab).
     - Màu sắc phẩm cấp đồng bộ: Ngũ phẩm (Trắng), Tứ phẩm (Xanh lá), Tam phẩm (Xanh dương), Nhị phẩm (Vàng), Nhất phẩm (Tím).
     - Danh sách trang bị kèm biểu tượng icon, cấp độ yêu cầu, chỉ số cơ bản tự động nhân hệ số phẩm cấp (1.1x -> 1.8x).
     - Hiển thị danh sách nguyên liệu chế tạo kèm kiểm tra số lượng: Nguyên liệu 1 (có/cần), Nguyên liệu 2 (có/cần), Đá ngũ hợp (có/cần), Ngọc rèn (có/cần), Phí xu (xanh lá nếu đủ, đỏ nếu thiếu).
     - Bấm "Chế tạo" -> Hiện popup xác nhận -> Trừ nguyên liệu & xu -> Nhận trang bị chuẩn phẩm cấp, ngũ hành và dòng thuộc tính ẩn ngẫu nhiên.
3. **Phân rã trang bị**:
   - Chọn trang bị từ hành lý để phân rã (phân trang 5 món/trang).
   - Xem trước số lượng Ngọc rèn hoàn trả theo công thức: `max(1, level/10) + bonus phẩm cấp (0 đến 4)`.
   - Popup xác nhận: `"Bạn có chắc muốn phân rã [tên] không? Bạn sẽ nhận lại [X] ngọc rèn."`
   - Xác nhận -> Hủy trang bị -> Cộng trực tiếp Ngọc rèn (ID 268) vào hành lý kèm thông báo hệ thống.
4. **Sửa lỗi dữ liệu gốc**:
   - Bổ sung chỉ số gốc cho 177 trang bị chế tạo (ID 268-444) trước đó bị rỗng `[0,0,0,0,0,0,0,0,0,0]` trong database và cơ chế fallback runtime trong `Manager.java`.

**Files thay đổi:**
- `server/kpah.sql` — Bổ sung chỉ số gốc cho 177 trang bị chế tạo (ID 268-444) và định nghĩa Gem ID 268 (`Ngọc rèn`).
- `server/KPAH/src/manager/Manager.java` — Thêm `initCraftedEquipmentStats()` khởi tạo chỉ số runtime cho trang bị chế tạo và Gem `Ngọc rèn`.
- `server/KPAH/src/network/CommandMessage.java` — Thêm opcode `CRAFT_ITEM = -116` và `CRAFT_SHOP = -117`.
- `server/KPAH/src/network/MessageHandler.java` — Bắt opcode `CommandMessage.CRAFT_ITEM` và gọi `CraftService.instance.craftEquipment(player, idItem, rank)`.
- `server/KPAH/src/player/Sundry.java` — Thêm trạng thái phiên chế tạo (`craftClass`, `craftType`, `idItemDismantle`, `dismantlePage`).
- `server/KPAH/src/services/MenuOptionService.java` — Tách biệt ID menu `THO_REN_THAN_BI = 20`, bổ sung các sub-menu class (21), loại trang bị (22), phân rã (23); chuyển luồng chọn sang `CraftService.openCraftShop`.
- `server/KPAH/src/services/PopupService.java` — Thêm `CONFIRM_DISMANTLE = 3` và popup xác nhận phân rã hoàn trả ngọc rèn.
- `server/KPAH/src/services/CraftService.java` — Viết mới toàn bộ logic `openCraftShop`, `craftEquipment` (kiểm tra nguyên liệu, trừ xu/đá/ngọc, tính bonus phẩm cấp, add item), `openDismantleMenu`, `onSelectDismantleItem`, `confirmDismantle`.
- `game/app/src/classes/class_go.java` — Thêm method `sendCraftItem(short idItem, byte rank)` gửi opcode `-116`.
- `game/app/src/classes/CraftShopScreen.java` — Tạo mới màn hình giao diện Gian hàng chế tạo: 5 tab phẩm cấp, điều hướng phím/touch, hiển thị chỉ số tỉ lệ phẩm cấp, hiển thị nguyên liệu xanh/đỏ, nút Chế tạo & Đóng.
- `game/app/src/classes/MsgHandler.java` — Bắt opcode `-117` (`CRAFT_SHOP`), đọc danh sách trang bị chế tạo và khởi tạo `CraftShopScreen.show(...)`.
- `server/dist/KPAH.jar` & `game/build/dist/KPAH_PROD.jar` — Biên dịch thành công 100% bằng ant. Đã khởi động server và client trên emulator.
- Backup files: Lưu tại `_backup/` trong các thư mục tương ứng theo quy định.

**Kết quả:** ✅ Thành công. Hệ thống Chế tạo trang bị dạng Gian hàng 5 phẩm cấp và Phân rã trang bị tại Thợ Rèn Thần Bí hoạt động trơn tru, đồng bộ giữa Server và Client.

---
