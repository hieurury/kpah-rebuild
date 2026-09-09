# KPAH — Task Log (Phần 02: Task 11 - 20)

> Lưu trữ nhật ký nhiệm vụ từ #11 đến #20.
> Quy tắc: Mỗi file chỉ lưu trữ tối đa 10 task.

---

## [2026-09-06 15:30] — Sửa lỗi tiến độ nhiệm vụ không tăng và đồng bộ giao diện Nhiệm vụ người chơi (Tab Nhiệm vụ)

**Yêu cầu:** 
1. Khắc phục lỗi khi nhận nhiệm vụ tân thủ thì trong giao diện Nhiệm vụ của người chơi (`Menu -> Thông tin -> Nhiệm vụ`) vẫn hiển thị "Chưa nhận nhiệm vụ".
2. Khắc phục lỗi khi tiêu diệt quái vật (ví dụ diệt Nhím cho nhiệm vụ tân thủ 1) thì tiến độ không tăng lên, vẫn giữ nguyên 0/100.

**Files thay đổi:**
- `server/KPAH/src/services/QuestService.java`:
  - **Sửa ID quái vật (`onKillMonster`):** Đổi điều kiện kiểm tra Nhím từ `mobId == 0 || mobId == 4` thành `mobId == 1` (trong database `kpah.monsters`, Nhím có `id = 1`; `id = 4` là Gà điên); đổi kiểm tra Chuột cống từ `mobId == 1 || mobId == 5` thành `mobId == 9`.
  - **Thêm method `sendQuestInfo(Player player)`:** Đóng gói và gửi packet `CommandMessage.CMD_NEW_QUEST` (opcode `-64`) tới client:
    - Mode `by = 1` kích hoạt `class_abj.aV` (nhiệm vụ chính/tân thủ, slot 0) và `class_abj.aX` (nhiệm vụ hằng ngày, slot 2).
    - Chuỗi mô tả phân tách bằng dấu `|` để client (`class_nu.k`) hiển thị đa dòng (Dòng 0: Tiêu đề vàng, Dòng 1: Mục tiêu & tiến độ, Dòng 2: Phần thưởng).
    - Khi không có nhiệm vụ, gửi mode `by = 3` để xóa sạch hiển thị cũ trên client về trạng thái "Chưa nhận nhiệm vụ".
  - **Đồng bộ thời gian thực:** Gọi `sendQuestInfo(player)` và gửi thông báo nổi (`ChatService.instance.sendChatDelay`) mỗi khi:
    - Nhận hoặc trả nhiệm vụ tại NPC (`processQuestMenu`).
    - Tiêu diệt đúng quái vật mục tiêu (`onKillMonster`).
    - Nhặt trang bị rơi từ quái (`onPickUpEquipment`).
    - Di chuyển hành quân hoặc bán trang bị (`onMove`, `onSellEquipment`).
- `server/KPAH/src/services/LoginService.java`:
  - Thêm lời gọi `QuestService.instance.sendQuestInfo(pl);` trong method `sendDataWhenLogin` để ngay khi người chơi đăng nhập, tab Nhiệm vụ luôn được đồng bộ chính xác.
- `server/KPAH/src/network/MessageHandler.java`:
  - Bổ sung `case CommandMessage.CMD_NEW_QUEST` để phản hồi an toàn nếu client gửi yêu cầu thông tin nhiệm vụ.

**Backup:**
- `server/KPAH/src/services/_backup/QuestService.java.bak.20260906_1523`
- `server/KPAH/src/services/_backup/LoginService.java.bak.20260906_1523`
- `server/KPAH/src/network/_backup/MessageHandler.java.bak.20260906_1523`

**Kết quả:** ✅ Thành công
- Đã chạy unit test và integration test mô phỏng toàn bộ chu trình đăng nhập, nhận nhiệm vụ, tiêu diệt 100 Nhím (tiến độ tăng 1/100 -> 100/100) và trả nhiệm vụ cho Trưởng Làng sang nhiệm vụ 2.
- Biên dịch server thành công và đã khởi động lại tiến trình server (PID 88265, port 19129).
- Đã dọn dẹp toàn bộ file test trong `_tmp/`.

---

---

## [2026-09-06 15:48] — Khởi tạo Git repository và cấu hình remote kpah-rebuild

**Yêu cầu:** Chuyển đổi và khởi tạo Git repository cho toàn bộ dự án KPAH, liên kết với remote `https://github.com/hieurury/kpah-rebuild.git` và tạo commit đầu tiên.

**Files thay đổi:**
- `.gitignore`: Cập nhật cấu hình loại trừ các thư mục build (`build/`, `dist/`), NetBeans private config (`nbproject/private/`), IDEA config (`.idea/`), log (`log/`, `*.log`), file tạm (`decompile_tmp/`, `_tmp/`, `_backup/`, `*.bak.*`).
- `game/`: Đã loại bỏ `.git` con (tránh biến `game` thành submodule bị tách rời) để toàn bộ client mod được lưu trực tiếp vào repository gốc.
- Đã khởi tạo `git init`, đổi branch mặc định sang `main`, liên kết remote `origin https://github.com/hieurury/kpah-rebuild.git`.
- Đã tạo commit đầu tiên (`6027667`) chứa toàn bộ mã nguồn Client và Server sạch.

**Kết quả:** ✅ Thành công (Đã commit toàn bộ 5.036 files, sẵn sàng push lên GitHub)

---

## [2026-09-06 17:22] — Cân bằng & Điều chỉnh Quái vật Lv 16 - 35 (Chỉ số, EXP, Dame, Drop)

**Yêu cầu:** Điều chỉnh tiếp quái từ lv 16 - 35 và báo cáo kết quả chi tiết về chỉ số (HP, speed), exp, dame, vật phẩm rớt (potions, vàng, trang bị, đá may mắn, luyện kim dược...).

**Mức độ rủi ro:** Trung bình

**Files & Database thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - `injured`: Bổ sung cơ chế giảm trừ giáp quái hợp lý theo level (`mobDef = level * 2.5`) cho lv 16-35 thay vì trừ cố định 2% maxHp (tránh tình trạng quái lv cao có hàng nghìn giáp khiến người chơi đánh ra 0 dame). Đảm bảo sát thương tối thiểu = 1.
  - `getDameAttack`: Cân bằng sát thương quái tấn công người chơi cho lv 16-35 (`minAtk = 15 * level - 40`, `maxAtk = 20 * level - 20`, cận chiến +15% bonus), tương thích với lượng máu và giáp của người chơi ở từng cấp độ (gây khoảng 8% - 15% HP người chơi/hit thay vì one-shot như trước).
  - `calculatePowerPlus`: Cải thiện công thức EXP thưởng khi tiêu diệt quái lv 16-35 (Lv 16-20: $L^2 \times 35$; Lv 21-27: $L^2 \times 55$; Lv 28-35: $L^2 \times 90$) giúp tiến trình lên cấp mượt mà, hợp lý và tạo cảm giác cày cuốc sảng khoái (chill).
  - `getItemDrop`: Cải tiến hệ thống rơi đồ:
    - Bình dược phẩm (30%): Lv 16-25 rơi HP/MP vừa; Lv 26-35 rơi HP/MP vừa và HP/MP to.
    - Tiền vàng (20%): Tăng lượng xu rơi theo cấp độ (`level * 120` đến `level * 350`).
    - Trang bị (4%): Tăng tỉ lệ rơi đồ kích ẩn/phôi trang bị theo level người chơi và quái.
    - Nguyên liệu/Đá may mắn (4% - 6%): Rơi Đá may mắn cấp 1, Luyện kim dược, Đá may mắn cấp 2 và Vé quay số.
- Database MariaDB (`kpah.monsters`):
  - Cập nhật chỉ số `maxHp` và `speed` cho toàn bộ 17 loài quái vật từ lv 16 đến lv 35 theo đường cong tăng trưởng mượt mà (nối tiếp từ 6.900 HP ở lv 15 lên 34.500 HP ở lv 35; điều chỉnh tốc độ chạy các loài quái nhanh như Bướm, Nhện, Cọp, Sơn tặc lên speed 2).

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260906_1718`
- `_backup/monsters_backup_20260906_1718.sql`

**Kết quả:** ✅ Thành công
- Đã chạy kiểm tra database và simulation test tính toán dame, exp, drop rate.
- Đã biên dịch server thành công với Java 21 và khởi động lại tiến trình server (port 19129).
- Đã dọn dẹp sạch sẽ các file test tạm trong `_tmp/`.

---

## [2026-09-06 17:31] — Tăng độ bền (trâu) quái vật, giảm EXP và mở rộng cân bằng toàn diện Lv 1 - 35

**Yêu cầu:** 
1. Giảm EXP thưởng từ quái để tránh tình trạng người chơi lên cấp quá nhanh, giữ chuẩn nhịp độ cày cuốc.
2. Tăng khả năng chống chịu, máu và phòng ngự của quái vật để người chơi không thể "chọt 2, 3 chiêu là quái clear hết", đặc biệt trước sức mạnh của trang bị cấp cao rất OP.
3. Đồng bộ và cân bằng toàn bộ cho cả nhóm quái vật từ Lv 1 - 15.

**Mức độ rủi ro:** Trung bình

**Files & Database thay đổi:**
- `server/KPAH/src/map/Monster.java`:
  - `injured`: 
    - Bổ sung giáp phòng thủ phẳng: `mobDef = level * 4` (từ 4 đến 140).
    - Thêm cơ chế **Kháng sát thương theo % (Damage Mitigation)**: `resistPercent = Math.min(45, (int)(level * 1.2))` (dao động từ 1% đến 42%) giúp quái hấp thụ bớt sát thương bộc phát cực lớn từ các vũ khí/trang bị cấp cao OP, buộc người chơi phải đánh nhiều combo chiêu thức liên hoàn mới hạ gục được bãi quái.
  - `calculatePowerPlus`: Giảm mạnh Base EXP thưởng từ 9 - 15 lần (Lv 1-5: $L \times 8$; Lv 6-10: $L \times 12$; Lv 11-15: $L^2 \times 2.5$; Lv 16-20: $L^2 \times 4$; Lv 21-27: $L^2 \times 5$; Lv 28-35: $L^2 \times 6$) giúp tiến trình lên cấp chậm lại, đòi hỏi người chơi phải cày cuốc đúng nghĩa.
  - `getDameAttack`: Thống nhất công thức sát thương cho toàn bộ quái Lv 1 - 35 (`minAtk = 10 * level - 15`, `maxAtk = 14 * level - 5`, cận chiến +15% bonus), gây sát thương bào mòn đều đặn 5% - 10% HP người chơi mỗi hit, buộc người chơi phải dùng dược phẩm hồi phục.
  - `getItemDrop`: Thiết lập danh mục rơi đồ chuẩn từ Lv 1 - 35:
    - Bình dược phẩm: Lv 1-15 rơi HP/MP nhỏ (25%); Lv 16-25 rơi HP/MP vừa (30%); Lv 26-35 rơi HP/MP to và vừa (30%).
    - Tiền vàng (15% - 20%): Rơi xu theo cấp độ vừa phải.
    - Trang bị (3% - 4%): Rớt đồ theo cấp độ quái và người chơi.
    - Nguyên liệu luyện kim (2% - 5%): Lv 10 trở lên bắt đầu rớt Đá may mắn cấp 1, Luyện kim dược, Đá may mắn cấp 2 và Vé quay số.
- Database MariaDB (`kpah.monsters`):
  - Nâng cấp chỉ số `maxHp` cho toàn bộ 34 loài quái vật từ Lv 1 đến Lv 35 (Nhím từ 200 lên 650 HP; Quỷ hoa từ 3.000 lên 10.000 HP; Cọp khổng lồ từ 10.800 lên 42.000 HP; Sơn tặc từ 34.500 lên 138.000 HP).

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260906_1729`
- `_backup/monsters_before_nerf_exp_20260906_1729.sql`

**Kết quả:** ✅ Thành công
- Đã chạy kiểm tra mô phỏng combat trên bộ số mới, xác nhận quái sống dai và chịu đòn tốt trước dame OP, EXP lên cấp chậm rãi và ổn định.
- Biên dịch server thành công với Java 21 và khởi động lại Server (port 19129).
- Đã dọn dẹp toàn bộ file test tạm trong `_tmp/`.

---

## [2026-09-06 17:55] — Thêm hệ thống Quái Tinh Anh (Elite Monster) và Rương Tinh Anh (Elite Chest)

**Yêu cầu:**
1. Thêm cơ chế Quái Tinh Anh (Elite Monster):
   - Tỷ lệ xuất hiện: 0.5% khi quái sinh ra hoặc hồi sinh (vẫn là chủng loại quái đó).
   - Phần thưởng: Rớt số lượng vật phẩm gấp 2 lần, tỷ lệ rớt tăng 150% (x2.5), kinh nghiệm (EXP) nhận được gấp 10 lần quái thường.
   - Thêm phần thưởng đặc biệt: Chắc chắn (100%) rớt thêm 1 - 2 "Rương tinh anh".
   - Hình ảnh và hiệu ứng: Quái tinh anh có thân hình to lớn hơn, hào quang vàng quay dưới chân, tia sáng xoay quanh, hạt lấp lánh (sparkles) và huy hiệu/tiêu đề `[Tinh Anh]` màu vàng rực rỡ nổi bật trên đầu và thanh máu.
   - Kỹ năng chiến đấu: Tăng 30% sát thương, 3x lượng máu, khi tấn công có 20% gây Choáng (Stun 2s) hoặc 25% gây Nhiễm độc (Poison 5s).
2. Thêm vật phẩm "Rương tinh anh" (Elite Chest):
   - Sử dụng asset/icon rương vàng lấp lánh (idImage = 67 trong `potion_template`, slot id = 106).
   - Cơ chế mở rương trao thưởng ít nhất 3 món (từ 3 đến 5 món), bao gồm:
     - Vũ khí: Đồng cấp hoặc lệch vài level với người chơi/quái, ưu tiên phái của nhân vật.
     - Bình thuốc cao cấp: 10 - 20 bình (HP to, MP to, HP đặc biệt, MP đặc biệt).
     - Tiền tệ: 5 - 10 Lượng.
     - Nguyên liệu sơ cấp: 2 - 4 viên (Đá may mắn cấp 1, Luyện kim dược, Ngọc thuộc tính cấp 1-2).
     - Nguyên liệu cao cấp: 1 - 3 viên (Đá may mắn cấp 2-3, Luyện kim dược cao cấp, Bảo hiểm 20%-40%, Ngọc thuộc tính cấp 3-4).
     - Phần thưởng cân bằng, không quá OP.

**Mức độ rủi ro:** Cao (sửa đổi đồng thời Server và Client)

**Files & Database thay đổi:**
- `Database MariaDB`:
  - `potion_template`: Cập nhật slot `id = 106`: `name = 'Rương tinh anh'`, `name2 = 'ruongtinhanh'`, `idImage = 67`, `isTrade = 1`.
- `server/KPAH/src/map/Monster.java`:
  - Thêm thuộc tính `isElite`, phương thức `rollElite()` (0.5% tỷ lệ) và `getMaxHp()` (3x HP).
  - Tự động gọi `rollElite()` khi quái hồi sinh trong `update()`.
  - `getDameAttack`: Tinh anh tăng 30% sát thương.
  - `calculatePowerPlus`: Tinh anh thưởng gấp 10 lần EXP.
  - `getItemDrop`: Nhân đôi số lượng (x2), tăng 150% tỷ lệ rớt (x2.5), rơi thêm trang bị và 100% rớt 1-2 Rương tinh anh (id 106).
  - `attackPlayer`: Tinh anh có 20% cơ hội gây Choáng (Stun 2s) và 25% cơ hội gây Độc tố (Poison 5s) lên người chơi.
- `server/KPAH/src/map/Map.java`:
  - Gọi `rollElite()` khi khởi tạo các Monster mới trong map và child map.
- `server/KPAH/src/services/MonsterService.java`:
  - Truyền cờ hiệu ứng quái tinh anh (byte 100) trong `sendMonsterMove`, `sendMonsterTeleport` và `sendMonsterInfo`.
  - Cập nhật gửi đúng `maxHp` của quái tinh anh sang client.
- `server/KPAH/src/services/UseItemService.java`:
  - Thêm `case 106` và hàm `openEliteChest`: Trừ 1 rương, trao thưởng ngẫu nhiên ít nhất 3-5 loại quà tặng (5-10 Lượng, 10-20 Bình thuốc cao cấp, 2-4 Nguyên liệu sơ cấp, 1-3 Nguyên liệu cao cấp, Vũ khí cùng phái đồng cấp), thông báo chi tiết kết quả mở rương.
- `game/app/src/classes/class_bb.java`:
  - Nhận biết quái tinh anh qua cờ `isElite`, method `m(int)` và `a(class_by)`.
  - Hiển thị danh hiệu `[Tinh Anh]` ở `a_()` và vẽ banner `[Tinh Anh]` nổi bật trên đầu quái.
  - Vẽ hiệu ứng hào quang dưới chân (ma trận tròn vàng cam xoay tròn với 4 tia sáng).
  - Vẽ hiệu ứng tăng kích thước (vẽ lớp hào quang phủ dày dặn quanh thân quái).
  - Hiệu ứng hạt năng lượng lấp lánh (sparkles) bay xung quanh thân quái.

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260906_175053`
- `server/KPAH/src/map/_backup/Map.java.bak.20260906_175053`
- `server/KPAH/src/services/_backup/MonsterService.java.bak.20260906_175053`
- `server/KPAH/src/services/_backup/UseItemService.java.bak.20260906_175053`
- `game/app/src/classes/_backup/class_bb.java.bak.20260906_175053`

**Kết quả:** ✅ Thành công
- Đã kiểm tra mô phỏng tỷ lệ spawn 0.5% (đạt ~5.000 / 1.000.000 lần thử).
- Đã kiểm tra logic mở Rương tinh anh đảm bảo nhận tối thiểu 3 món quà và cân bằng.
- Server biên dịch thành công với Java 21 và đã khởi động lại tiến trình daemon (port 19129).
- Client ME build thành công ra file `game/build/dist/KPAH_MOD.jar` với Java 8.
- Toàn bộ file tạm trong `_tmp/` đã được dọn dẹp sạch sẽ.

---

---

## [2026-09-06 20:48] — Nâng cấp Auto Đánh: Tự động di chuyển đuổi theo quái & Tuần tra quanh bãi khi trống quái

**Yêu cầu:** Ở chế độ auto đánh, nhân vật di chuyển vòng quanh để tấn công thay vì cứ đứng 1 chỗ và tự động di chuyển đuổi theo mục tiêu khi ở xa:
1. Khi bãi trống: Không mất nhịp farm, tự động lượn tuần tra tìm quái thay vì đứng đơ khó target.
2. Khi mục tiêu xa: Tự động áp sát cự ly kỹ năng để đánh và nhặt đồ rơi ngay cạnh.
3. Tạo tính chân thực, mượt mà và nâng cao trải nghiệm cày cuốc.

**Mức độ rủi ro:** Trung bình (Bytecode patch client và mở rộng logic `ModController`)

**Files thay đổi:**
- `game/app/src/classes/ModController.java`:
  - Thêm method `shouldChase(Object target)`: kiểm tra nếu mục tiêu là quái vật (`cF == 1`) hoặc PK (`cY == true`) để cho phép đuổi.
  - Thêm các thuộc tính theo dõi tuần tra: `lastPatrolTime`, `patrolStep`, `MAX_ROAM_RADIUS` (220px), `PATROL_RADIUS` (75px).
  - Thêm method `handleAutoCombatRoaming()` được gọi mỗi frame trong `update()`:
    - Rà soát danh sách quái (`class_acv.s.l`) trong bán kính bãi farm (220px quanh tâm bãi `ag, ah`). Nếu phát hiện quái còn sống thì lập tức khóa target `class_acv.s.r` và lao tới tiêu diệt.
    - Khi bãi sạch bóng quái (chờ quái hồi sinh): nhân vật không đứng im mà tự động di chuyển tuần tra theo 6 điểm lục giác xung quanh tâm bãi train bằng thuật toán tìm đường `movePlayer(destX, destY)`.
    - Phản xạ lập tức: ngay khi có quái mới respawn, trạng thái tuần tra lập tức bị ngắt để chuyển sang đánh quái ngay.
- `game/tools/Patcher.java`:
  - Bổ sung `Patch 5 (target-chase)` vào method `b(class_vh, int)` của `class_abj`:
    - Thay thế điểm truy cập field `cY` của mục tiêu bằng lời gọi `classes.ModController.shouldChase($0)`.
    - Mở khóa biến cờ di chuyển `n = 1` khi mục tiêu là quái vật, cho phép nhân vật tự động bước từng bước áp sát quái vật đến cự ly skill và gửi gói tin di chuyển lên server.
- `game/libs/KPAH_225_remade.jar`: Cập nhật `class_abj.class` đã patch bytecode.
- `game/build/dist/KPAH_MOD.jar`: Build lại toàn bộ client hoàn chỉnh.

**Backup:**
- `game/app/src/classes/_backup/ModController.java.bak.20260906_2045`
- `game/tools/Patcher.java.bak.20260906_2045`
- `game/libs/KPAH_225_remade.jar.bak.20260906_2045`

**Kết quả:** ✅ Thành công
- Đã patch bytecode `class_abj.class` bằng Javassist sạch sẽ không lỗi.
- Đã xác minh bằng `javap`: lệnh `invokestatic ModController.shouldChase` được chèn đúng vị trí.
- Client build Ant thành công ra `KPAH_MOD.jar`.
- Đã khởi động MicroEmulator trên `task-2095` với bản build mới.
- Toàn bộ file tạm trong `_tmp/` đã được dọn dẹp sạch sẽ.

---

---

## [2026-09-06 20:57] — Fix triệt để Auto Đánh: Khắc phục nguyên nhân gốc rễ & kích hoạt di chuyển đuổi quái

**Yêu cầu:** Sửa lại logic do test thực tế chưa thấy hoạt động (chưa đuổi theo mục tiêu, chưa tuần tra bãi, chưa mở rộng bán kính quét).

**Nguyên nhân gốc rễ phát hiện:**
1. `ModController.update()` nằm trong `class_bg.c()` (chỉ chạy khi có sự kiện bấm phím/touch), khi người chơi buông tay thì vòng lặp auto không chạy.
2. Hàm tìm target `class_abj.z()` cố định bán kính quét `cb` ở phạm vi hẹp `[-90, 90]`, quái ở xa hơn 90px hoàn toàn không được chọn.
3. Cơ chế đuổi quái phụ thuộc vào `ao == 1` ("Đánh quái" trong menu), nhưng mặc định game là `ao == 0` ("Chế độ thường").
4. Trong `handleAutoCombatRoaming()` khi có quái còn sống thì chỉ `return` mà không chủ động di chuyển áp sát quái.

**Giải pháp toàn diện đã triển khai:**
1. `game/tools/Patcher.java`:
   - `Patch 0`: Chèn `classes.ModController.update();` vào ngay đầu method `class_abj.b()` (vòng lặp game tick chính chạy liên tục 30 FPS).
   - `Patch 1+2`: Chèn logic mở rộng bán kính tìm quái `cb` lên `[-250, 250]` px khi `au == true` ngay đầu `class_abj.z()`.
   - `Patch 5`: Override `ao = 1` và `cY = shouldChase($0)` trong `class_abj.b(class_vh, int)` để quái vật luôn kích hoạt cờ di chuyển `n = 1`.
2. `game/app/src/classes/ModController.java`:
   - Khi có mục tiêu quái vật ở xa ngoài tầm đánh (`dist > attackRange`): chủ động gọi `gameScreen.movePlayer((int) mob.cK, (int) mob.cL)` chạy lại gần quái.
   - Khi đã vào tầm đánh (`dist <= attackRange`): dừng chạy và kích hoạt phím skill đánh.
   - Khi bãi trống: tuần tra 6 điểm lục giác quanh tâm bãi train bán kính 80px, đổi điểm mỗi 2.5 giây.
   - Quét tìm quái mở rộng trong bán kính 250px quanh tâm bãi train.
3. Đóng gói vào `KPAH_225_remade.jar`, build lại `KPAH_MOD.jar`, khởi động lại MicroEmulator (`task-2210`).

**Kết quả:** ✅ Thành công
- `invokestatic ModController.update` đã nằm tại lệnh đầu tiên của `class_abj.b()`.
- Bán kính quét quái `cb` mở rộng lên 250px đã được kiểm tra bytecode.
- Quá trình build và khởi chạy client hoàn tất.

---

---

## [2026-09-06 21:06] — Tinh chỉnh khoảng cách tiếp cận theo tầm chiêu ô số 5 (Tránh đuổi quá sát)

**Yêu cầu:** Nhân vật di chuyển đuổi theo quái bị quá sát người quái mặc dù tầm đánh dư sức tới:
1. Trích xuất tầm chiêu ô số 5 làm phạm vi khoảng cách tiếp cận.
2. Nếu không có chiêu hoặc tầm quá ngắn, dùng cự ly chuẩn hợp lý (cận chiến 52px, đánh xa 90px) để không bị dí sát rạt người quái.

**Files thay đổi:**
- `game/app/src/classes/ModController.java`:
  - Thêm biến `public static int slot5Range = -1`.
  - Cập nhật logic tính `attackRange`:
    - Nếu `slot5Range > 40`: đặt khoảng cách dừng là `slot5Range - 8` pixel.
    - Nếu `slot5Range <= 40`: fallback cự ly chuẩn: cận chiến 52px (thay vì 32px áp sát rạt cũ), đánh xa 90px.
    - Ngay khi cự ly đạt `<= attackRange`: dừng di chuyển (`player.s = null`) và tung skill đánh ngay từ khoảng cách đẹp mắt đó.
- `game/tools/Patcher.java`:
  - Trong `Patch 0`: chèn đoạn trích xuất skill ID từ `this.cp[5]` $\rightarrow$ tra bảng `class_qz.a((byte) skillId)` $\rightarrow$ lưu vào `ModController.slot5Range` ngay trước mỗi frame update.
- `game/libs/KPAH_225_remade.jar` & `game/build/dist/KPAH_MOD.jar`: Cập nhật jar và build lại.

**Kết quả:** ✅ Thành công
- Bytecode đọc tầm chiêu ô số 5 đã được inject an toàn vào `class_abj.b()`.
- Client build Ant thành công ra `KPAH_MOD.jar`.
- Đã khởi động MicroEmulator trên `task-2291`.

---

---

## [2026-09-06 21:19] — Bỏ target khi mục tiêu quá xa & tắt tự động di chuyển khi người dùng chủ động điều khiển

**Yêu cầu:** 
1. Khi tiến quá xa mục tiêu target thì bỏ target mục tiêu đó (không cắm đầu đuổi theo quái ở xa).
2. Tự động di chuyển phải tắt khi người dùng chủ động di chuyển (bấm phím điều hướng hoặc click đất).
3. Đảm bảo khi người dùng di chuyển, nhân vật không tự quay lại chỗ cũ vì dấu tích hệ thống tự động (tọa độ ag, ah cũ).

**Files thay đổi:**
- `game/app/src/classes/ModController.java`:
  - Thêm `onUserManualMove()`: Tắt `au = false`, `av = false`, xóa `r = null`, đồng bộ ngay tọa độ `player.ag = player.cK; player.ah = player.cL;` về vị trí hiện tại của nhân vật.
  - Trong `update()`: Kiểm tra nếu người dùng bấm phím điều hướng (2, 4, 6, 8 hoặc mũi tên) thì gọi ngay `onUserManualMove()`.
  - Giới hạn `MAX_TARGET_DISTANCE = 140` px: Nếu khoảng cách tới mục tiêu `distToMob > 140px` hoặc quái đi ra ngoài bán kính bãi `MAX_ROAM_RADIUS = 200px` $\rightarrow$ Hủy target (`r = null`) và hủy bước chạy (`player.s = null`).
  - Quét quái mới: Chỉ khóa mục tiêu trong bán kính 140px quanh người chơi.
  - Trong `shouldChase`: Chỉ cho phép đuổi nếu quái cách người chơi `<= 140px`.
  - Tự động cập nhật `ag, ah` về vị trí hiện tại mỗi khi Auto chuyển trạng thái từ Tắt sang Bật.
- `game/tools/Patcher.java`:
  - `Patch 1+2`: Giảm bán kính quét quái `cb` trong `class_abj.z()` từ 250px về 140px tương ứng với `MAX_TARGET_DISTANCE`.
  - `Patch 5`: Vô hiệu hóa lệnh `movePlayer(ag, ah)` cứng nhắc 120px của client gốc trong `class_abj.b(class_vh, int)` để tránh bị giật lùi ngược về chỗ cũ.
  - `Patch 6`: Hook `movePlayer_(int, int)` (khi người dùng click chuột/chạm đất di chuyển) gọi `ModController.onUserManualMove()`.
- `game/libs/KPAH_225_remade.jar` & `game/build/dist/KPAH_MOD.jar`: Cập nhật jar và build lại.

**Kết quả:** ✅ Thành công
- Đã test build sạch sẽ, MicroEmulator đang chạy trên `task-2471`.
- Quái ở xa hơn 140px được bỏ target ngay lập tức, không còn dí theo quái xa.
- Người dùng bấm phím hoặc click đất di chuyển sẽ tắt ngay Auto, không bị kéo giật ngược về chỗ cũ.

---

---

## [2026-09-07 08:30] — Cơ chế ưu tiên nhặt đồ: Đảm bảo vật phẩm rơi trên đất là ưu tiên số 1

**Yêu cầu:** 
1. Kiểm tra lại cơ chế ưu tiên nhặt đồ, đảm bảo ưu tiên chọn vật phẩm và tiến hành đến nhặt trong phạm vi quét.
2. Khi bật tính năng (AutoPickup), vật phẩm là ưu tiên số 1 (ngắt mọi hành động đánh quái/tuần tra để đi nhặt đồ trước).
3. Khắc phục triệt để lỗi vật phẩm trên đất bị bỏ qua khi đang auto train.

**Nguyên nhân gốc rễ phát hiện:**
1. Trong `handleAutoCombatRoaming()`, khi đang target quái vật (`class_bb`), code liên tục giữ target và đánh quái, hoàn toàn không quét vật phẩm rơi xung quanh.
2. Khi quái chết (`currentTarget == null`), code chỉ quét tìm quái vật sống kế tiếp (`class_bb`) mà không hề quét tìm `class_ba` (vật phẩm).
3. `Patch 3` cũ trong `Patcher.java` khi gọi `d(int,int)` tự động gán `this.r = null`, làm mất target vật phẩm trước khi nhân vật kịp chạy tới nhặt.
4. Bytecode `class_abj.z()` có thể ghi đè target `r` mỗi 10 frame update nếu không được bảo vệ.

**Giải pháp đã triển khai:**
- `game/app/src/classes/ModController.java`:
  - Thêm phương thức `findNearestDroppedItem(gameScreen, originX, originY, player)`: quét toàn bộ danh sách thực thể `gameScreen.l` để tìm vật phẩm rơi `class_ba` gần nhất trong phạm vi bãi train (`MAX_ROAM_RADIUS`). Tự động lọc bỏ các vật phẩm đã nhặt (`cE`), trang bị khi rương đầy, hoặc tiền khi túi tiền vượt mốc.
  - Đặt cơ chế nhặt đồ lên **MỤC 0 (ƯU TIÊN SỐ 1)** trong `handleAutoCombatRoaming()`, chạy trước tất cả logic nhắm quái, đánh quái hay tuần tra:
    - Nếu phát hiện vật phẩm: Khóa ngay target `gameScreen.r = nearestItem`.
    - Cự ly `> 35px`: Dùng thuật toán tìm đường `gameScreen.movePlayer(item.cK, item.cL)` chủ động chạy thẳng tới vật phẩm. Tích hợp cơ chế phát hiện kẹt đường (chống kẹt địa hình).
    - Cự ly `<= 35px`: Dừng chạy, quay mặt vào vật phẩm và gửi gói tin nhặt đồ `gameScreen.D.a(item.cF, item.cG)`. Tích hợp bộ đếm thử lại (bỏ qua 15s nếu là đồ người khác hoặc không thể nhặt).
    - Lập tức ngắt luồng frame (`return`), **tuyệt đối không tấn công quái vật hay tuần tra** cho đến khi vật phẩm được nhặt xong.
    - Khi hết vật phẩm: Reset target vật phẩm và quay lại tiếp tục đánh quái bình thường.
  - Cập nhật `shouldChase(target)` cho phép bám đuổi cả đối tượng `class_ba`.
- `game/tools/Patcher.java`:
  - Cập nhật `Patch 1+2` trong `class_abj.z()`: Nếu `r` đang là `class_ba` và bật `isAutoPickup`, giữ nguyên mục tiêu hiện tại (tránh bị hàm z định kỳ ghi đè mục tiêu vật phẩm).
  - Sửa `Patch 3` trong `class_abj.d(int,int)`: Ngăn chặn thi triển kỹ năng lên vật phẩm rơi, không gán `r = null` để giữ nguyên mục tiêu vật phẩm.
  - Cập nhật cấu hình ClassPool tự động trích xuất từ `game/tools/_orig_classes` độc lập và an toàn.
- `game/libs/KPAH_225_remade.jar` & `game/build/dist/KPAH_MOD.jar`: Re-patch bytecode và build thành công với Java 8.

**Backup:**
- `game/app/src/classes/_backup/ModController.java.bak.20260907_0825`
- `game/tools/_backup/Patcher.java.bak.20260907_0825`
- `game/libs/_backup/KPAH_225_remade.jar.bak.20260907_0825`

**Kết quả:** ✅ Thành công
- Build Ant Java 8 hoàn thành xuất sắc (`BUILD SUCCESSFUL`).
- Bytecode đã được kiểm tra bằng `javap`: method `findNearestDroppedItem`, hook `z()` và hook `d()` đều chuẩn xác.
- File tạm trong `_tmp/` đã được dọn dẹp sạch sẽ.

---
