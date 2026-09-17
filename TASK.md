# KPAH Project — Task Log (Phần 12: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #120), tiến hành lưu trữ file thành `tasks/TASK_12.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
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

---

## [2026-09-16 00:35] — Task #110: Tổng Hợp & Lập Tài Liệu Chi Tiết Toàn Bộ Kỹ Năng Đấu Sĩ Vào docs/skills/dau_si.md

**Yêu cầu:**
1. Khảo sát toàn bộ hệ thống kỹ năng môn phái Đấu Sĩ (Class ID: 3, Hệ Thổ, Vũ khí Búa) từ database (`init_kpah.sql`, `others`, `skill_news`), mã nguồn server (`Manager.java`, `Point.java`, `BuffService.java`, `Player.java`) và client (`class_qz.java`, `class_sc.java`).
2. Liệt kê rõ ràng và chi tiết từng thành phần của toàn bộ 9 kỹ năng Đấu Sĩ (tên chiêu, loại chiêu, mô tả, lực công, hồi chiêu, mana tiêu hao, phạm vi, cơ chế buff/khống chế, cấp độ học từng cấp) vào `docs/skills/dau_si.md`.
3. Phân tích các vấn đề và lỗi kỹ năng Đấu Sĩ cần nâng cấp/sửa đổi (lỗi integer division ở Skill 5 Hộ thủ tiến, chuẩn hóa hồi chiêu kỹ năng AoE 6, 7, 8, bộ format tiếng Việt trên client...).

**Files thay đổi:**
- `docs/skills/dau_si.md` — Tạo tài liệu hoàn chỉnh 9 kỹ năng của Đấu Sĩ kèm bảng tổng quan và danh sách các điểm cần nâng cấp/sửa lỗi.

**Kết quả:** ✅ Thành công
- Đã lập tài liệu chuẩn xác, đầy đủ chi tiết từng kỹ năng của Đấu Sĩ.

---

## [2026-09-16 14:18] — Task #111: Cải Tổ Toàn Diện Đấu Sĩ (Tanker & Khống Chế), Thêm Hiệu Ứng Hóa Đá & Giảm Giáp, Xây Dựng Codebase Map & Skill Deobfuscation

**Yêu cầu:**
1. Tái định vị Đấu Sĩ (Class 3) thành Tanker & Khống Chế thuần:
   - Base Stats: Máu cơ bản 20 -> 30, Thân pháp 30 -> 20, hệ số HP tối đa 70 -> 90.
   - Skill 3 (Khổng kình bát vĩ): Đòn đơn mạnh nhất; cấp 1-3 đánh 3 đòn, cấp 4-9 số đòn = cấp (max 9 đòn). Fix bug chỉ gây dame 1 lần: mỗi đòn tính sát thương độc lập và có 50% tỉ lệ gây Choáng 1s.
   - Skill 4 (Bất di biến): Tăng kích thước nhân vật (server sync về client), sát thương scale theo HP tối đa (5% + 3%/cấp), duy trì 60s, CD 80s.
   - Skill 5 (Khí huyết sinh sôi): Nội tại tăng HP tối đa (10% - 55%) + hồi 2% HP/s khi không nhận sát thương trong 10s. Xóa code tăng giáp cũ bị lỗi chia số nguyên.
   - Skill 6 (Kinh thiên động địa): Thêm debuff giảm 10% giáp trong 5s.
   - Skill 7 (Sơn Tinh bộ thiên): Thêm tỉ lệ Hóa Đá 1s theo cấp (20% - 65%).
   - Skill 8 (Thạch nhũ công tâm): Choáng 1s AoE + tăng 1% sát thương cho mỗi 1.000 HP tối đa.
2. Xây dựng cơ chế hiệu ứng Hóa Đá (BUFF_HOA_DA = 7) và Giảm Giáp (BUFF_GIAM_GIAP = 9) an toàn trên Client (class_zx render vòng xám tro / đỏ cam) và Server (BuffConst, BuffInfluence, BuffService, SkillService, Point, Monster, Player).
3. Viết script migration MySQL `server/update_skills_dau_si.sql` và cập nhật tài liệu `docs/skills/dau_si.md`.
4. Xây dựng hệ thống Codebase Map & Deobfuscation cheatsheet (`docs/codebase_map/`) và Custom Skill `.agents/skills/kpah-codebase-map/SKILL.md` để tự động tra cứu nhanh ý nghĩa từng class (`class_xxx`), từng biến (`cW`, `cZ`, `cG`, `cK`, `cL`, `de`...) và gói tin mạng.

**Files thay đổi:**
- `server/KPAH/src/consts/BuffConst.java` — Bổ sung BUFF_HOA_DA (7), BUFF_DONG_BANG (8), BUFF_GIAM_GIAP (9).
- `server/KPAH/src/skill/BuffInfluenceMonster.java` — Xử lý buff Hóa Đá & Giảm Giáp cho quái.
- `server/KPAH/src/skill/BuffInfluencePlayer.java` — Xử lý buff Hóa Đá & Giảm Giáp cho player.
- `server/KPAH/src/player/Point.java` — Điều chỉnh base stats Đấu Sĩ, hệ số HP = 90, scale dame HP Skill 4 & 8, fix bug Skill 5.
- `server/KPAH/src/player/Player.java` — Thêm thời gian nhận đòn `lastTimeHitDauSi`, hồi phục 2% HP/s sau 10s không bị đánh, trừ 10% giáp khi bị debuff Giảm Giáp.
- `server/KPAH/src/map/Monster.java` — Quái bị hóa đá không thể di chuyển/đánh, trừ 10% giáp khi bị debuff Giảm Giáp.
- `server/KPAH/src/manager/Manager.java` — CD và MP chuẩn cho Đấu Sĩ, Bất di biến duy trì 60s.
- `server/KPAH/src/services/BuffService.java` — Gửi packet -23 chuẩn cho Hóa Đá và Giảm Giáp.
- `server/KPAH/src/services/SkillService.java` — Multi-hit Skill 3 (3-9 đòn, 50% stun mỗi đòn), hiệu ứng giảm giáp Skill 6, hóa đá Skill 7, choáng Skill 8.
- `game/app/src/classes/class_zx.java` — Render visual effect an toàn cho Hóa Đá và Giảm Giáp.
- `game/app/src/classes/MsgHandler.java` — Bắt packet -23 cho hiệu ứng 7 và 9.
- `game/app/src/classes/MainCharInfo.java` — Thêm UI hiển thị debuff Hóa Đá và Giảm Giáp.
- `server/update_skills_dau_si.sql` — Script SQL cập nhật skill_news.
- `docs/skills/dau_si.md` — Tài liệu thiết kế hoàn chỉnh Đấu Sĩ mới.
- `docs/codebase_map/README.md`, `client_classes.md`, `client_actor_fields.md`, `network_and_server.md` — Bộ tài liệu bản đồ codebase KPAH.
- `.agents/skills/kpah-codebase-map/SKILL.md` — Skill tra cứu nhanh cho AI Agent.

**Kết quả:** ✅ Thành công
- Server Java compile thành công 100% (JDK 21, Ant).
- Client Java ME build thành công 100% (`kpah_mod_v1.0.0.1_local.jar`, JDK 8).
- Đầy đủ tài liệu tra cứu và skill định danh codebase.

---

## [2026-09-16 15:15] — Task #112: Hoàn Thiện & Khắc Phục Triệt Để 5 Vấn Đề Kỹ Năng Đấu Sĩ

**Yêu cầu:**
1. Cập nhật mô tả kỹ năng Đấu Sĩ trong menu kỹ năng client (hiện tại hiển thị mô tả cũ do thiếu định dạng `classChar == 3` trong `class_sc.java`).
2. Sửa lỗi Skill 3 (Khổng kình bát vĩ) dù vung búa nhiều lần nhưng chỉ nổ 1-2 lần dame: Tách các đợt sát thương theo nhịp vung búa bằng Virtual Thread và delay (240ms) cho cả PvP và PvE.
3. Hiển thị chỉ số Tấn công buff theo % Max HP từ Skill 4 (Bất di biến) trên HUD Avatar (hiển thị màu xanh lá `+xxxx` tương tự Pháp Sư).
4. Khi hồi máu bằng nội tại Skill 5 (Khí huyết sinh sôi, hồi 2% HP/s sau 10s phi giao tranh) phải nhảy số hồi HP nổi (`+xxxx`) như khi bơm bình máu.
5. Hiệu ứng trực quan cho Skill 4 (Bất di biến): Render hào quang Titan bộc phát, trận đồ thổ thạch bát quái khổng lồ xoay dưới chân nhân vật và danh hiệu `[Bất Di Biến Xs]` màu vàng kim, hoạt động ổn định trên cả Android (J2ME Loader) lẫn PC.
7. Fix triệt để lỗi không thấy hiệu ứng Choáng (Stun) trên mục tiêu: Bổ sung gọi `BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_STUN)` trong `BuffInfluenceMonster.java` khi quái bị choáng (trước đó chỉ đổi biến nội bộ server mà không gửi packet 89 cho client).
8. Chuẩn hóa hồi chiêu Skill 4 (Bất di biến): Cố định 80s mọi cấp trong `Manager.java` (`applySkillCooldownRebalance`) và bảng `others` MySQL, tránh tình trạng cấp cao bị cooldown 140s-160s trong DB gốc. Bổ sung hiển thị `[Hồi chiêu: Xs]` đếm lùi 20s trên đầu nhân vật ngay khi hết 60s buff trong `Paint.java`.

**Files thay đổi:**
- `game/app/src/classes/class_sc.java` — Bổ sung định dạng mô tả chi tiết 9 kỹ năng Đấu Sĩ mới (`classChar == 3`).
- `server/KPAH/src/services/SkillService.java` — Xử lý nổ dame theo từng nhịp đánh cho Skill 3 (Virtual Thread + delay 240ms) cho cả quái và người chơi.
- `server/KPAH/src/skill/BuffInfluenceMonster.java` — Gửi packet 89 đồng bộ hiệu ứng Choáng trên quái cho client.
- `server/KPAH/src/manager/Manager.java` — Khóa hồi chiêu 80.000ms cố định cho Skill 4 Đấu Sĩ trong mảng `SKILL_COOLDOWN`.
- `game/app/src/classes/MainCharInfo.java` — Định danh buff 19 là "Bất Di Biến" cho Đấu Sĩ.
- `game/app/src/classes/Paint.java` — Hiển thị buff công xanh lá trên HUD, vẽ hiệu ứng hào quang Titan + trận đồ thổ thạch xoay dưới chân khi bật Skill 4, hiển thị tag đếm lùi 20s hồi chiêu khi hết buff.
- `server/KPAH/src/player/Player.java` — Kích hoạt Command 22 (USE_POTION) khi hồi máu nội tại Skill 5 để client nhảy số HP nổi.
- `server/update_skills_dau_si.sql` — Bỏ trường `cooldown` trong `skill_news`, bổ sung UPDATE bảng `others` cho `SKILL_COOLDOWN` Đấu Sĩ.

**Kết quả:** ✅ Thành công
- Server Java compile thành công 100% (`server/KPAH/dist/KPAH.jar`).
- Client Java ME build thành công 100% (`game/build/dist/KPAH_PROD.jar` và `game/build/dist/KPAH_MOD.jar`).
- Tất cả các file đã được backup an toàn trong `_backup/`.

---

## [2026-09-16 16:00] — Task #113: Sửa Lỗi Rơi Rương Tinh Anh Cấp Cao & Tối Ưu Hiệu Ứng Bất Di Biến

**Yêu cầu:**
1. Fix lỗi khi diệt quái vật tinh anh cấp cao (từ cấp 10 trở lên) không nhận được Rương Tinh Anh:
   - Phân tích: Quái cấp <= 9 rơi Rương Bậc 1 (ID 106). Quái cấp >= 10 rơi Rương Bậc 2 (ID 160), Bậc 3 (ID 161), Bậc 4 (ID 162). Trong DB gốc của server và packet đồng bộ template client, các item ID >= 160 chưa tồn tại hoặc bị tràn kích thước mảng khiến client ném ngoại lệ hoặc server bỏ qua không nhặt được.
   - Xử lý: Tự động chèn template Rương Tinh Anh Bậc 1-4 (106, 160, 161, 162), Tinh Anh Đan (107) và Tinh Anh Huyết (108..111) vào DB và nạp bộ nhớ đệm `Manager.java`; nâng tổng số lượng potion templates gửi về client (`LoginService.java`) lên tối thiểu 165 để client khởi tạo mảng đầy đủ; mở rộng an toàn mảng vật phẩm client (`MsgHandler.java` case 16) lên 256 phần tử, chống hoàn toàn `ArrayIndexOutOfBoundsException`.
2. Cải thiện hiệu ứng Bất Di Biến (Skill 4 Đấu Sĩ):
   - Loại bỏ toàn bộ các vòng hiệu ứng thô (vòng hào quang, trận đồ bát quái xoay).
   - Chỉ vẽ chữ `Bất Di Biến` màu vàng đỏ (chữ vàng viền đỏ nổi bật) ngay trên đỉnh đầu Đấu Sĩ, không kèm thời gian đếm ngược.
   - Sửa triệt để lỗi khi bị Choáng (Stun) thì hiệu ứng Bất Di Biến biến mất: Tách rời hoàn toàn logic kiểm tra hiệu ứng Bất Di Biến khỏi cờ `cW` (isStun) và nhận diện buff thông qua danh sách buff chủ động `bC` cùng điều kiện lọc thời lượng > 4s.

**Files thay đổi:**
- `game/app/src/classes/Paint.java` — Xóa bỏ vòng hiệu ứng thô và số giây đếm ngược, chỉ vẽ chữ vàng đỏ `Bất Di Biến` trên đầu; bỏ điều kiện `!mainChar.cW` để không bị mất hiệu ứng hình ảnh và bonus công khi bị choáng.
- `game/app/src/classes/MsgHandler.java` — Mở rộng mảng potion template và inventory arrays (`bq`, `bs`, `class_sc.l`) lên 256 phần tử trong case 16, đảm bảo an toàn tuyệt đối khi nhận Rương Tinh Anh Bậc 2-4 (ID 160-162).
- `server/KPAH/src/manager/Manager.java` — Tự động chèn và load các potion template Rương Tinh Anh 160-162, 106, 107, 108..111 vào DB/bộ nhớ server.
- `server/KPAH/src/services/LoginService.java` — Đồng bộ độ dài danh sách potion template chuẩn (>= 165) khi player đăng nhập.
- `server/update_skills_dau_si.sql` — Bổ sung câu lệnh SQL `INSERT ... ON DUPLICATE KEY UPDATE` cho các template Rương Tinh Anh và Tinh Anh Đan/Huyết.

**Kết quả:** ✅ Thành công
- Server Java compile thành công 100% (`server/KPAH/dist/KPAH.jar`).
- Client Java ME build thành công 100% (`game/build/dist/KPAH_PROD.jar` và `game/build/dist/kpah_mod_v1.0.0.1_local.jar`).
- Đã tạo backup các file trong `_backup/` trước khi sửa.

---

## [2026-09-16 20:25] — Task #114: Khắc Phục Lỗi Hồi Chiêu Skill 4 Đấu Sĩ, Tối Ưu Hiệu Ứng/Cơ Chế Bất Di Biến, Tái Cân Bằng HP & MP Đấu Sĩ, Đổi Skill 5 Pháp Sư Theo Max MP

**Yêu cầu:**
1. Khắc phục lỗi thời gian hồi chiêu Skill 4 (Bất di biến) thực tế ở client bị reset về 80s sau khi vừa hết 60s tác dụng:
   - Tìm rõ nguyên nhân: Trong `class_abj.d(int, int)` của client gốc KPAH, khi buff hết (`bI - now < 0`) nếu auto bật (`au == true`), cờ `this.bB` bị kích hoạt làm game gốc cưỡng ép `l = at + 1L`, lập tức gửi packet 51 lên server và gán lại `aq[4] = System.currentTimeMillis()`. Server reject packet vì chưa trôi qua đủ 80s cooldown, nhưng client đã trót reset mốc thời gian `aq`, dẫn đến UI client bắt đầu đếm 80s lại từ đầu ngay tại giây thứ 60.
   - Xử lý: Áp dụng Javassist Patch 7 trong `game/tools/Patcher.java`, chặn đọc field `bB` trong `class_abj.d(int, int)` (luôn trả về `false`). Nhờ đó client không bao giờ cưỡng ép hồi chiêu sớm, bảo toàn bộ đếm 80s trôi tự nhiên chuẩn xác. Đồng thời cập nhật `ModController.handleAutoSupportSkills` khởi tạo đầy đủ `player.at[skillId]` trước khi so sánh thời gian.
2. Skill 4 Đấu Sĩ (Bất di biến):
   - Loại bỏ hoàn toàn cơ chế ghi chữ "Bất Di Biến" và hiển thị timer trên đỉnh đầu nhân vật, giữ nguyên vẹn hiệu ứng vòng xoáy nguyên bản dưới chân (`eff19`) như game gốc.
   - Thay đổi cơ chế buff sát thương: Chuyển từ scale theo HP tối đa sang scale theo **HP hiện tại** (`mainChar.v * percentHp / 100`).
3. Tái cân bằng chỉ số Đấu Sĩ:
   - Giảm HP cơ bản: `defaultHea` giảm từ 30 xuống 25; hệ số HP tối đa `hpMax += (health + healthAdd) * 80` (giảm từ 90 xuống 80).
   - Tăng Mana tiêu hao của tất cả các chiêu thức Đấu Sĩ trong `Manager.java` (Skill 1: 6-10 MP, Skill 2: 8-12 MP, Skill 3: 15-32 MP, Skill 4: 40-75 MP, Skill 6: 25-60 MP, Skill 7: 30-70 MP, Skill 8: 45-90 MP) và cập nhật `server/update_skills_dau_si.sql`.
4. Pháp Sư:
   - Đổi cơ chế Skill 5 (Hồi lực tiến) nội tại tăng sát thương: Thay vì scale theo mana hiện tại, đổi sang scale theo **Mana tối đa (Max MP)** (`Point.java`: `bonusDame = (int) ((long) this.mpMax * percentMana / 100)`).
   - Đồng bộ hiển thị HUD client (`Paint.java`) và tooltip mô tả (`class_sc.java`).
5. Cập nhật tài liệu:
   - `docs/skills/dau_si.md` và `docs/skills/phap_su.md`.

**Files thay đổi:**
- `game/tools/Patcher.java` — Bổ sung Patch 7 chặn đọc `bB` trong `class_abj.d(int, int)`.
- `game/libs/KPAH_225_remade.jar` — Cập nhật bytecode `class_abj.class` đã vá.
- `game/app/src/classes/Paint.java` — Xóa vẽ chữ "Bất Di Biến" trên đầu; đổi HUD Đấu Sĩ tính bonus theo HP hiện tại; đổi HUD Pháp Sư tính bonus theo Max MP.
- `game/app/src/classes/class_sc.java` — Cập nhật mô tả kỹ năng Skill 4 Đấu Sĩ (theo HP hiện tại) và Skill 5 Pháp Sư (theo Max MP).
- `game/app/src/classes/ModController.java` — Khởi tạo `player.at[skillId]` an toàn trong `handleAutoSupportSkills`.
- `server/KPAH/src/player/Point.java` — Giảm HP cơ bản Đấu Sĩ (hea=25, factor=80); Skill 4 buff theo HP hiện tại; Skill 5 Pháp Sư buff theo Max MP.
- `server/KPAH/src/manager/Manager.java` — Tăng `SKILL_MP` cho Đấu Sĩ.
- `server/update_skills_dau_si.sql` — Bổ sung SQL update `SKILL_MP` vào bảng `others`.
- `docs/skills/dau_si.md` & `docs/skills/phap_su.md` — Cập nhật tài liệu thiết kế kỹ năng.

**Kết quả:** ✅ Thành công
- Server Java biên dịch thành công (`KPAH.jar`).
- Patcher áp dụng Bytecode Patch 7 thành công.
- Client Java ME build thành công cả bản Prod và Local (`KPAH_PROD.jar` và `KPAH_MOD.jar`).
- Đã backup an toàn tất cả các file trước khi sửa đổi.

---

## [2026-09-16 22:00] — Task #115: Cải Tổ Toàn Diện Môn Phái Cung Thủ (Glass Cannon, Multi-hit Độc Nổ, Mù, Vết Thương Sâu, Hút Máu, UI Kỹ Năng & Hiệu Ứng)

**Yêu cầu:**
1. Định vị phong cách Cung Thủ (Class ID: 4) thành Xạ Thủ Siêu Sát Thương (Glass Cannon):
   - Base Stats: Dame cơ bản tăng vọt (Agi x2.2), Tỉ lệ chí mạng cơ bản cao (Luck / 15 + 5%), nhưng máu cực thấp (Hea x50) và mana hạn chế (Spi x16). Chỉ số khởi tạo: Str 20, Agi 35, Spi 10, Hea 10, Luck 15.
2. Thiết kế & Triển khai cơ chế bộ kỹ năng Cung Thủ:
   - **Skill 3 (Bát kim tiễn đáo):** Bắn nhiều đòn liên tiếp theo cấp kỹ năng (Cấp 1–3 bắn 3 đòn; Cấp 4–9 bắn số đòn = cấp, tối đa 9 đòn, nhịp bắn 240ms). Cơ chế **Độc Nổ (Poison Detonate)**: lập tức rút cạn toàn bộ sát thương độc DoT còn lại trên mục tiêu và kết thúc hiệu ứng trúng độc; đồng bộ sát thương nổ độc hiển thị số trừ máu màu **TÍM** cực lớn nhảy trên đầu mục tiêu qua packet 89 và gỡ bỏ ngay lập tức animation bọt độc xoay quanh mục tiêu.
   - **Skill 4 (Độc lưu tiễn):** Buff duy trì 60s, hồi chiêu cố định 80s (khoảng trống 20s không buff); đòn đánh thường và chiêu thức gán độc DoT kéo dài 10s lên mục tiêu, mỗi giây gây sát thương = `30% + 5%/cấp` Lực tấn công (nhảy số trừ máu màu **TÍM** nguyên bản 8px trên đầu mục tiêu).
   - **Skill 5 (Hộ độc tiễn):** Kỹ năng nội tại tăng `5% + 2%/cấp` Tỉ lệ chí mạng; đặc biệt khi mục tiêu đang dính độc, nhận thêm `50% + 10%/cấp` Sát thương chí mạng.
   - **Skill 6 (Thập diện tâm tiễn):** Hồi chiêu 6s; tỉ lệ `10% + 2%/cấp` gây trạng thái **MÙ (Blind)** trong 1s (khi bị mù, đối thủ 100% đánh hụt/Miss cả quái lẫn người chơi).
   - **Skill 7 (Thăng thiên loạn tiễn):** Hồi chiêu 7s; gây trạng thái **Vết thương sâu** trong 5s (giảm 50% lượng máu hồi phục của đối thủ từ mọi nguồn); đồng thời tăng cho bản thân `10% + 2%/cấp` Né đòn trong 5s.
   - **Skill 8 (Vạn tiễn quy tâm):** Hồi chiêu 8s; Hút máu hồi phục HP cho bản thân = `10% + 2%/cấp` tổng sát thương gây ra (áp dụng cho cả PvP, PvE đơn mục tiêu và AoE quái).
   - Chuẩn hóa mức tiêu hao Mana (`SKILL_MP`): mức tiêu hao cao thứ 2 trong game, chỉ đứng sau Pháp Sư.
3. Client Visual & UI:
   - Render vòng hiệu ứng debuff Mù (vòng khói đen tối xoay chân `effectType = 10`) và Vết thương sâu (vòng đỏ máu xoay chân `effectType = 11`) an toàn trong `class_zx.java` và xử lý packet 89 trong `MsgHandler.java`.
   - Bổ sung định dạng mô tả chi tiết tiếng Việt cho 9 kỹ năng Cung Thủ trong `class_sc.java` (hiển thị chuẩn xác công thức, cấp học, mana, hồi chiêu và hiệu ứng độc quyền).
4. Đồng bộ Database & Tài liệu:
   - Tạo migration script `server/update_skills_cung_thu.sql` cập nhật dữ liệu bảng `skill_news` và cấu hình bảng `others`.
   - Cập nhật đầy đủ tài liệu thiết kế kỹ năng `docs/skills/cung_thu.md`.

**Files thay đổi:**
- `server/KPAH/src/consts/BuffConst.java` — Định nghĩa `BUFF_MU = 10`, `BUFF_VET_THUONG_SAU = 11`.
- `server/KPAH/src/skill/BuffInfluenceMonster.java` — Xử lý buff Mù, Vết thương sâu, cơ chế rút nổ độc `detonatePoison()`.
- `server/KPAH/src/skill/BuffInfluencePlayer.java` — Xử lý buff Mù, Vết thương sâu, cơ chế rút nổ độc `detonatePoison()` trên người chơi.
- `server/KPAH/src/player/Player.java` — Thêm cơ chế buff né đòn `timeEndBuffNeDonCungThu` và `dodgeBonusCungThu`.
- `server/KPAH/src/player/Point.java` — Cập nhật hệ số công Agi x2.2, HP max Hea x50, MP max Spi x16, Base stats khởi tạo, `setCrit()`, `setDodge()`, và `plusHp()` (giảm 50% khi dính Vết thương sâu).
- `server/KPAH/src/services/BuffService.java` — Cập nhật `onMobInjured`, `onPlayerInjured` (Skill 4 DoT 10s: 30% + 5%/cấp Lực tấn công), `sendAddBuffInfluence` cho `BUFF_MU` và `BUFF_VET_THUONG_SAU`.
- `server/KPAH/src/services/MonsterService.java` — Quái bị Mù 100% đánh hụt (áp dụng cả đánh thường và cận chiến).
- `server/KPAH/src/services/SkillService.java` — Check Mù đánh hụt; Skill 5 tăng chí mạng mục tiêu dính độc; Skill 3 multi-hit + Độc Nổ; Skill 6 Mù 1s; Skill 7 Vết thương sâu + Buff Né đòn 5s; Skill 8 Hút máu (PvP, PvE đơn và AoE quái).
- `server/KPAH/src/manager/Manager.java` — Khóa hồi chiêu cố định Skill 4 (80s), Skill 6 (6s), Skill 7 (7s), Skill 8 (8s); chuẩn hóa `SKILL_MP[Const.CUNG_THU]`.
- `game/app/src/classes/class_sc.java` — Bổ sung định dạng mô tả chi tiết 9 kỹ năng Cung Thủ mới (`classChar == 4`).
- `game/app/src/classes/MsgHandler.java` — Xử lý packet 89 case 10 (`BUFF_MU`) và case 11 (`BUFF_VET_THUONG_SAU`).
- `game/app/src/classes/class_zx.java` — Render vòng khói đen huyền bí (Mù) và vòng đỏ máu (Vết thương sâu) an toàn dưới chân nhân vật.
- `server/update_skills_cung_thu.sql` — Script SQL cập nhật `skill_news` và mô tả kỹ năng Cung Thủ.
- `docs/skills/cung_thu.md` — Cập nhật tài liệu thiết kế kỹ năng hoàn chỉnh.

**Kết quả:** ✅ Thành công
- Server Java biên dịch thành công 100% (`KPAH.jar`).
- Client Java ME build thành công 100% (`kpah_mod_v1.0.0.1_local.jar`).
- Tất cả các file đã được backup an toàn trong các thư mục `_backup/` tương ứng.

---

## [2026-09-16 23:30] — Task #116: Thiết Lập Cooldown Bơm HP/MP 10 Giây & Tăng Sức Mạnh Cho Quái Vật

**Yêu cầu:**
1. Khắc phục vấn nạn spam bơm HP và MP quá nhanh gây quá tải hạ tầng Client - Server:
   - Thiết lập thời gian hồi chiêu (cooldown) là **10 giây (10.000ms)** mỗi lần bơm máu (HP) hoặc mana (MP).
   - Cơ chế hoạt động đồng bộ: Khi sử dụng bất kỳ bình HP nào, toàn bộ các bình HP cùng nhóm đều bị khóa trong 10 giây (không thể đổi bình khác để lách cooldown). Tương tự với toàn bộ các bình MP.
   - Phía Server: Thêm `lastTimeUseHpPotion` và `lastTimeUseMpPotion` vào `Inventory.java`; chặn đứng mọi request bơm HP/MP dưới 10s trong `UseItemService.java`; cập nhật `delay = 10000` cho các template bình thuốc trong `Manager.java`.
   - Phía Client: Cập nhật `class_sc.l[id].c = 10000` cho toàn bộ bình HP/MP trong `MsgHandler.java` (case 16/19); đồng bộ khóa toàn bộ mảng `class_acv.s.q.bs` khi nhận packet 22 thành công; hiển thị `"Hồi chiêu: 10 giây"` trong tooltip rương đồ (`ModHelpers.java`).
2. Tăng thêm sức mạnh cho quái vật để tạo tính công bằng và thử thách trong chiến đấu:
   - **Tăng máu (Max HP):** Quái thường tăng 20% lượng HP tối đa trong `Monster.getMaxHp()`.
   - **Tăng sát thương công (Attack Damage):** Nâng mức sát thương tối thiểu và tối đa thêm ~20-25% theo cấp quái: `minAtk = Math.max(20, (int) (mobLv * 13 + 10))`, `maxAtk = Math.max(30, (int) (mobLv * 17 + 20))`.
   - **Tăng sát thương cào xước tối thiểu (Scratch Damage):** `Math.max(5, (int) (mobLv * 2.0 + 4))`.
   - **Bổ sung tỉ lệ Bạo Kích cho quái thường:** Thêm 8% cơ hội Bạo Kích gây $\times 1.3$ sát thương bất ngờ.
   - **Điều chỉnh giảm phạt cấp độ (Level Difference Penalty):** Giảm mức suy giảm sát thương khi người chơi vượt cấp quái từ 20%/cấp (tối đa 80%) xuống còn 10%/cấp (tối đa 60%), giúp quái luôn duy trì được tính uy hiếp hợp lý khi người chơi thăng cấp.

**Files thay đổi:**
- `server/KPAH/src/player/Inventory.java` — Bổ sung `lastTimeUseHpPotion` và `lastTimeUseMpPotion`.
- `server/KPAH/src/services/UseItemService.java` — Kiểm tra và cưỡng chế cooldown 10s cho nhóm HP và nhóm MP.
- `server/KPAH/src/manager/Manager.java` — Gán delay 10000ms cho template các bình HP/MP.
- `server/KPAH/src/map/Monster.java` — Tăng 20% HP quái thường, tăng ~25% sát thương cơ bản và scratch damage, thêm 8% bạo kích quái thường, giảm phạt level diff.
- `game/app/src/classes/MsgHandler.java` — Khóa delay 10s cho toàn bộ bình HP/MP trong template và đồng bộ mảng cooldown `bs` khi nhận phản hồi dùng bình.
- `game/app/src/classes/ModHelpers.java` — Thêm dòng `"Hồi chiêu: 10 giây"` vào tooltip vật phẩm.
- `server/update_potion_cooldown.sql` — Tạo script SQL cập nhật `delay = 10000` cho bảng `potion_template`.

**Kết quả:** ✅ Thành công
- Server Java biên dịch thành công 100% (`KPAH.jar`).
- Client Java ME build thành công 100% (`kpah_mod_v1.0.0.1_local.jar`).
- Đã sao lưu backup tất cả các file trong `_backup/` trước khi sửa đổi.

---

## [2026-09-17 21:30] — Task #117: Khôi Phục Cơ Chế Giảm Sức Mạnh Quái Đến 80%, Tối Ưu Sát Thương Cào Xước & Cân Bằng Cung Thủ (DoT +-10%, Lan Độc AoE, Mù 3s)

**Yêu cầu:**
1. Khôi phục cơ chế giảm sức mạnh quái vật theo độ chênh lệch cấp độ với người chơi:
   - Cứ mỗi 1 cấp người chơi cao hơn quái, quái bị giảm **20% sát thương**, tối đa giảm tới **80%** (ở độ chênh lệch $\ge 4$ cấp). Quái tinh anh giảm tối đa **60%**. Không áp dụng cho Boss thế giới/phụ bản (type 2).
   - Tối ưu sát thương cào xước tối thiểu (`minScratch`): Quái thường giảm từ `mobLv * 2.0 + 4` xuống `mobLv * 1.0 + 2` (min 3). Quái Tinh Anh giảm từ `mobLv * 3.5 + 15` xuống `mobLv * 2.0 + 10` (min 15) để người chơi có giáp thủ cao không bị mất hàng trăm HP mỗi nhịp khi quái bu đông trong thời gian bình máu hồi chiêu 10s.
2. Cân bằng môn phái Cung Thủ (Archer):
   - **Skill 4 (Độc lưu tiễn):** Bổ sung biến thiên ngẫu nhiên $\pm 10\%$ sát thương DoT độc mỗi giây phát tác trong `BuffInfluenceMonster.java` và `BuffInfluencePlayer.java`.
   - **Gán độc AoE:** Trong `SkillService.java` (`onPlayerAttackMultiMob`), gọi `BuffService.instance.onMobInjured(player, mob)` cho toàn bộ các mục tiêu phụ ($j \ge 1$), đảm bảo 100% quái trúng sát thương lan đều bị dính độc khi Skill 4 đang kích hoạt.
   - **Skill 6 (Thập diện tâm tiễn):** Tăng thời gian gây MÙ từ **1s lên 3s** cho cả PvP, PvE đơn và PvE AoE trong `SkillService.java`.
   - **Giao diện & Tài liệu:** Cập nhật mô tả kỹ năng trên Client `class_sc.java` ("Tỉ lệ gây Mù 3s") và tài liệu `docs/skills/cung_thu.md`.

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java` — Khôi phục công thức giảm dame theo chênh lệch cấp độ tối đa 80% (tinh anh 60%), tối ưu `minScratch`.
- `server/KPAH/src/skill/BuffInfluenceMonster.java` — Thêm dao động $\pm 10\%$ sát thương DoT độc phát tác lên quái.
- `server/KPAH/src/skill/BuffInfluencePlayer.java` — Thêm dao động $\pm 10\%$ sát thương DoT độc phát tác lên người chơi.
- `server/KPAH/src/services/SkillService.java` — Gán độc cho mọi mục tiêu trúng AoE khi bật Skill 4, tăng thời gian Mù Skill 6 lên 3s.
- `game/app/src/classes/class_sc.java` — Cập nhật mô tả kỹ năng Skill 6 gây Mù 3s trên Client.
- `docs/skills/cung_thu.md` — Cập nhật tài liệu thiết kế kỹ năng Cung Thủ.

**Kết quả:** ✅ Thành công
- Server Java biên dịch thành công 100% (`KPAH.jar`).
- Client Java ME build thành công 100% (`kpah_mod_v1.0.0.1_local.jar`).
- Đã sao lưu backup tất cả các file trong các thư mục `_backup/` tương ứng.

---

