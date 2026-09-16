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

