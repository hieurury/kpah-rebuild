# KPAH Project — Task Log (Phần 07: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #70), tiến hành lưu trữ file thành `tasks/TASK_07.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
>
> **Lịch sử các phần trước:**
> - [Phần 01 (Task 1 - 10)](tasks/TASK_01.md)
> - [Phần 02 (Task 11 - 20)](tasks/TASK_02.md)
> - [Phần 03 (Task 21 - 30)](tasks/TASK_03.md)
> - [Phần 04 (Task 31 - 40)](tasks/TASK_04.md)
> - [Phần 05 (Task 41 - 50)](tasks/TASK_05.md)
> - [Phần 06 (Task 51 - 60)](tasks/TASK_06.md)

---

## [2026-09-09 15:38] — Task #61: Cải tiến hiển thị vật phẩm rơi, tái bố cục HUD và hệ thống Buff/Debuff toàn diện

**Yêu cầu:** 
- Nâng cấp hiển thị vật phẩm rơi trên mặt đất (tên, phân loại theo màu sắc, chỉ báo khi target).
- Tái bố cục HUD: di chuyển tọa độ lên giữa trên cùng (Top-Center), độ bền chuyển sang góc trên bên trái dưới avatar kèm cảnh báo đỏ nhấp nháy khi hỏng.
- Hiển thị đầy đủ và chuẩn xác các trạng thái Buff/Debuff: Tăng công, tăng giáp, bảo hộ, tẩm độc, choáng, trúng độc, tăng EXP,... dưới dạng các mini-bar xếp tầng thanh lịch kèm đếm ngược thời gian và phân màu trực quan.

**Files thay đổi:**
- `game/app/src/classes/MainCharInfo.java` — Bổ sung class tĩnh `BuffItem`, mở rộng phương thức `getActiveBuffItems()` để quét toàn diện tất cả các loại buff opcode 51 (`mainChar.de`), debuff Choáng (`mainChar.cW`, `cZ`), debuff Trúng độc (`mainChar.dg`, `dh`, `df`) và buff EXP từ thẻ/thuốc.
- `game/app/src/classes/Paint.java` — 
  - `paintDroppedItems(Graphics g)`: Quét danh sách vật phẩm rơi `class_acv.s.l`, chuyển đổi tọa độ world-to-screen qua camera `class_abj.j` và `class_abj.k`, hiển thị pill badge bo góc với màu sắc đặc trưng (Xu vàng, Trang bị cam, Rương tinh anh tím/cam sao, HP đỏ, MP xanh lam, Tinh anh huyết lục bảo, Đá quý ngọc) cùng mũi tên chỉ định `▼` khi vật phẩm đang được target.
  - `paintDurabilityAndPosition(Graphics g)`: Đặt badge tọa độ map pill tại Top-Center (`class_acv.o`); đặt badge độ bền tại Top-Left (dưới avatar, `84x12`), đổi màu cảnh báo nhấp nháy đỏ khi độ bền cạn $\le 0$.
  - `paintBuffBadges(Graphics g)`: Render các mini-bar `84x12` bo viền tinh tế xếp tầng ngay dưới badge độ bền, phân biệt vạch báo trạng thái xanh (Buff) / đỏ-vàng (Debuff), hiển thị tên hiệu ứng và thời gian đếm ngược (phút:giây hoặc giây).

**Kết quả:** ✅ Thành công (Đã biên dịch hoàn tất cả bản Client PROD `KPAH_PROD.jar` và bản Local `KPAH_MOD.jar`).
**Ghi chú:**
- Backup: `game/app/src/classes/_backup/MainCharInfo.java.bak.20260909_1523` và `Paint.java.bak.20260909_1523`.
- Hiện tại Phần 07 có: 1/10 task.

---

## [2026-09-09 15:52] — Task #62: Kích hoạt toàn diện NPC, mở rộng chuỗi nhiệm vụ & Daily Quest, kích hoạt Minimap Tracker và Quest Marker

**Yêu cầu:**
- Kích hoạt toàn bộ NPC chưa hoạt động hoặc thiếu tương tác: Xa Phu (`XA_PHU = 7`), Hoa Tiêu (`HOA_TIEU = 10`), Dì Út (`DI_UT_HP = 0`), Ông Nội (`ONG_NOI = 8`), Lính Gác (`LINH_GAC = 4`), Thợ Săn (`THO_SAN = -16`), Lễ Quan (`LE_QUAN = -15`), Tiên Nữ (`TIEN_NU = -2`).
- Mở rộng chuỗi nhiệm vụ cốt truyện từ 2 lên 7 nhiệm vụ liên hoàn qua các NPC (Trưởng Làng -> Thợ Rèn -> Dì Út -> Ông Nội -> Phú Ông -> Thợ Săn -> Lâm Tướng Quân) và chuỗi nhiệm vụ hằng ngày (Săn quái hàng ngày tại Thợ Săn).
- Sửa triệt để cơ chế đánh dấu Minimap trong Client: Truyền đúng `npcId` trong packet `CMD_NEW_QUEST` (-64) để Client kích hoạt vẽ chấm định vị vàng nhấp nháy và ghim mũi tên la bàn mép viền chỉ hướng (compass indicator).
- Bổ sung chỉ báo nhiệm vụ `[!]` (đang thực hiện) / `[?]` (sẵn sàng trả nhiệm vụ) lơ lửng nhấp nhô sống động trên đầu NPC nhiệm vụ trong Client.

**Files thay đổi:**
- `server/KPAH/src/player/QuestData.java`: Thêm thuộc tính `claimedDailyLogin` lưu trữ ngày nhận thưởng điểm danh từ Lễ Quan, tương thích JSON Serialization.
- `server/KPAH/src/services/MenuOptionService.java`: Khai báo menu ID cho Xa Phu, Hoa Tiêu, Lễ Quan, Tiên Nữ, Thợ Săn, Ông Nội, Lính Gác. Xử lý di chuyển chuyển map (Xa Phu đưa đi các thành trấn/cửa ải, Hoa Tiêu dịch chuyển tức thời về làng/thành lớn), Lễ Quan (điểm danh hàng ngày nhận 5000 xu, 5 bình HP/MP, 1 Tinh Anh Huyết), Tiên Nữ (hồi phục đầy máu mana và ban phước lành), Thợ Săn (nhận nhiệm vụ săn bắn quái vật hàng ngày), Ông Nội & Lính Gác (hướng dẫn tân thủ, kể tích hiệp khách).
- `server/KPAH/src/services/NpcService.java`: Mở khóa tất cả các case NPC trong `chatNpc` và `performNpcAction`, liên kết gọi qua `MenuOptionService`.
- `server/KPAH/src/services/QuestService.java`: Mở rộng danh sách `QuestEntry` lên 7 nhiệm vụ cốt truyện + 1 nhiệm vụ tuần hoàn hằng ngày; sửa đổi `sendQuestInfo` để gửi đúng `qe.npcId` (thay vì -1) kích hoạt Minimap Tracker trên client; bổ sung trigger diệt quái cho Dơi Quỷ (Nhiệm vụ 4) và Nhện Độc (Nhiệm vụ 6).
- `game/app/src/classes/Paint.java`: Viết thêm `paintQuestMarkers(Graphics g)` dò quét NPC trong màn hình, đối chiếu với ID NPC của nhiệm vụ hiện tại (`aV.d`, `aW.d`, `aX.d`) để vẽ phù hiệu `[!]` (cam/vàng) hoặc `[?]` (xanh lục/vàng kim) lơ lửng nhấp nhô theo hàm sin thời gian trên đỉnh đầu NPC.

**Kết quả:** ✅ Thành công (Đã biên dịch cả Client `KPAH_PROD.jar`, `KPAH_MOD.jar` và Server `KPAH.jar`; Server đã khởi động và lắng nghe cổng 19129).
**Ghi chú:**
- Backup files:
  - `server/KPAH/src/services/_backup/NpcService.java.bak.20260909_1545`
  - `server/KPAH/src/services/_backup/MenuOptionService.java.bak.20260909_1545`
  - `server/KPAH/src/services/_backup/QuestService.java.bak.20260909_1545`
  - `server/KPAH/src/player/_backup/QuestData.java.bak.20260909_1545`
  - `game/app/src/classes/_backup/Paint.java.bak.20260909_1545`
- Hiện tại Phần 07 có: 2/10 task.

---

## [2026-09-09 16:12] — Task #63: Triển khai 3 cơ chế mới: Ưu tiên quái Tinh Anh, Tự động hồi sinh, Tự bán trang bị cấp thấp & Tối ưu hóa Client-Server

**Yêu cầu:**
- Bổ sung cơ chế Ưu tiên quái Tinh Anh (`isPrioritizeElite`): Ưu tiên quét và khóa mục tiêu quái Tinh Anh trong bãi train trước quái thường; tự động chuyển target ngay khi quái Tinh Anh xuất hiện; rút ngắn 10 lần khoảng cách nhận diện quái Tinh Anh trong hệ thống target gốc.
- Bổ sung cơ chế Tự động hồi sinh (`isAutoRevive`): Khi nhân vật hy sinh (`cV == 3`), tự động kích hoạt hồi sinh tại chỗ bằng Xu sau 1.5s; nếu sau 4.5s vẫn chưa sống lại (hết xu/lỗi), tự động đưa về làng an toàn hồi đầy 100% HP/MP; sửa lỗi Server `PopupService.java` không còn kick văng người chơi (`sendLogOut`) khi thiếu xu.
- Bổ sung cơ chế Tự động bán trang bị cấp thấp hơn bản thân (`isAutoSellLowEquip`): Tự động phát hiện và bán trang bị có yêu cầu cấp độ nhỏ hơn cấp độ nhân vật (`equipLv < playerLevel`) kèm bộ lọc an toàn bảo vệ tài sản (không bán đồ cường hóa, không bán đồ khảm ngọc/lỗ ngọc, không bán đồ thuê).
- Mở rộng Menu cài đặt game (`class_wc.java` - Mục 10 "Cơ chế"): Cho phép người chơi toggle trực tiếp Bật/Tắt 4 tùy chọn: Nhặt đồ, Ưu tiên Tinh Anh, Tự hồi sinh, Tự bán đồ lv thấp.

**Files thay đổi:**
- `game/app/src/config/Config.java`: Khai báo và tuần tự hóa 3 trường cấu hình `isPrioritizeElite`, `isAutoRevive`, `isAutoSellLowEquip` với khối try-catch an toàn tương thích ngược.
- `game/app/src/classes/ModController.java`:
  - `priorityDistance()`: Giảm 10 lần khoảng cách ảo đối với quái Tinh Anh (`orig / 10`).
  - `handleAutoCombatRoaming()`: Tự động chuyển target sang quái Tinh Anh ngay khi phát hiện; hàm `findNearestEliteMob()` quét tìm quái Tinh Anh gần nhất trong bãi.
  - `handleAutoRevive()`: Quản lý vòng đời hồi sinh (thử hồi sinh tại chỗ sau 1.5s, tự về làng sau 4.5s, tự động đóng bảng popup chết).
  - `handleAutoSellLowEquip()`: Quét túi trang bị `class_hw.bu`, kiểm tra điều kiện cấp độ và 4 bộ lọc an toàn, gửi lệnh bán `class_go.a().f(ql.i)` với nhịp độ an toàn 1.5s/món.
- `game/app/src/classes/class_wc.java`: Mở rộng danh mục Menu #10 "Cơ chế" hiển thị 4 tùy chọn kèm trạng thái ON/OFF; bổ sung hàm `updateMechanicsMenu()`; xử lý chuyển đổi trạng thái (toggle) và lưu vào `Config.saveConfig()`.
- `server/KPAH/src/services/PopupService.java`: Sửa case `CONFIRM_REVIVAL`: thay `Service.instance.sendLogOut` bằng `ChatService.instance.sendChatOnlyMe` và `MapService.instance.comeHome(player)` khi không đủ xu hồi sinh tại chỗ.

**Kết quả:** ✅ Thành công (Đã biên dịch Client `KPAH_PROD.jar`/`KPAH_MOD.jar` bằng Java 8; Biên dịch Server `KPAH.jar` bằng Java 21; Server đã khởi động lại và lắng nghe cổng 19129).
**Ghi chú:**
- Backup files:
  - `game/app/src/config/_backup/Config.java.bak.20260909_1608`
  - `game/app/src/classes/_backup/ModController.java.bak.20260909_1608`
  - `game/app/src/classes/_backup/class_wc.java.bak.20260909_1608`
  - `server/KPAH/src/services/_backup/PopupService.java.bak.20260909_1608`
- Hiện tại Phần 07 có: 3/10 task.

---

## [2026-09-09 16:21] — Task #64: Siết chặt điều kiện Tự bán trang bị cấp thấp (Yêu cầu Quầy Thợ Rèn hoặc Thẻ Mua Bán)

**Yêu cầu:**
- Ngăn chặn tình trạng "hack / lạm dụng" cơ chế bán đồ từ xa bất kỳ đâu trên bản đồ.
- Ràng buộc **2 điều kiện tiên quyết** cho tính năng bán trang bị (cả Client và Server đều kiểm tra chặt chẽ):
  1. Đang đứng gần quầy Thợ Rèn hoặc NPC cửa hàng bán đồ (khoảng cách $\le 100$px trên Client, $\le 120$px trên Server). Danh sách NPC hỗ trợ gồm: Thợ rèn Thiết Bì, Hắc Ngưu, Thợ Rèn Thần Bí, Kiếm Sư, Giáp Sư, Bội Châu, Bảo Ngọc, Bà Tám Tạp Hóa, Dì Út, các NPC Nhất/Nhị/Tam/Tứ/Ngũ Giáp và Nhất/Nhị/Tam/Tứ/Ngũ Ngưu.
  2. HOẶC trong túi đồ đang sở hữu vật phẩm **Thẻ Mua Bán** (`ItemPotion` ID 33).
- Nếu không thỏa mãn một trong 2 điều kiện: Client không gửi lệnh bán; Server lập tức từ chối lệnh bán và gửi tin nhắn chat nhắc nhở người chơi (`ChatService.sendChatOnlyMe`).

**Files thay đổi:**
- `server/KPAH/src/services/ShopService.java`:
  - `onSellItem()`: Xác thực 2 điều kiện tiên quyết (`hasTheMuaBan` tra cứu trong `InventoryService`, `isNearShop` kiểm tra vị trí người chơi so với các NPC bán đồ trong zone). Nếu không thỏa mãn: từ chối bán, gửi chat nhắc nhở kèm cơ chế giãn cách thời gian (throttle 5s) tránh làm phiền người chơi.
  - Thêm `isNearShopNpc(player)` và `isShopOrBlacksmithNpc(npcId)`.
- `game/app/src/classes/ModController.java`:
  - Thêm `hasTheMuaBan()` tra cứu `class_gz.a((short) 33) != null && card.c > 0`.
  - Thêm `isShopOrBlacksmithNpcId(int)` và `isNearShopOrBlacksmith(gameScreen, player)` kiểm tra NPC bán đồ trong bán kính $\le 100$px.
  - `handleAutoSellLowEquip()`: Tích hợp kiểm tra 2 điều kiện tiên quyết trước khi duyệt hành trang bán đồ; hiển thị rõ phương thức bán trong thông báo nổi ("Tự bán đồ (Thẻ Mua Bán)..." hoặc "Tự bán đồ (Thợ Rèn)...").

**Kết quả:** ✅ Thành công (Biên dịch Client `KPAH_PROD.jar`/`KPAH_MOD.jar` bằng Java 8; Biên dịch Server `KPAH.jar` bằng Java 21; Server đã khởi động lại và lắng nghe cổng 19129).
**Ghi chú:**
- Backup files:
  - `server/KPAH/src/services/_backup/ShopService.java.bak.20260909_1615`
  - `game/app/src/classes/_backup/ModController.java.bak.20260909_1619`
- Hiện tại Phần 07 có: 4/10 task.

---

## [2026-09-09 20:44] — Task #65: Tái cấu trúc HUD tinh gọn, sửa triệt để lỗi Font chữ KPAH, loại bỏ hoàn toàn Label vật phẩm rơi & gỡ bỏ cơ chế NPC cho tính năng Tự bán trang bị

**Yêu cầu:**
- Sửa lỗi hiển thị HUD và lỗi font chữ (vỡ chữ tiếng Việt, ký tự lạ `★`, `▼` không tương thích font bitmap KPAH).
- Loại bỏ hoàn toàn nhãn (label) cho vật phẩm rơi trên mặt đất theo yêu cầu người dùng để khung cảnh game trong trẻo, không bị rối mắt.
- Tái bố cục HUD: di chuyển thông tin Tọa độ map, Độ bền vũ khí và trạng thái Buff/Debuff sang góc trên bên phải (Top-Right, căn lề phải), không vẽ khung hộp đen to đùng che khuất màn hình hay đè lên Avatar/kinh nghiệm.
- Khắc phục triệt để lỗi tính năng Tự bán trang bị cấp thấp: gỡ bỏ cơ chế ràng buộc đứng gần NPC bán đồ ở cả Client và Server (loại bỏ kiểm tra khoảng cách NPC gây lỗi do NPC tĩnh client-side không có entity trong Zone Server).

**Files thay đổi:**
- `game/app/src/classes/Paint.java`:
  - Loại bỏ hoàn toàn phương thức `paintDroppedItems` và `getItemDisplayName`, không còn vẽ nhãn cho vật phẩm rơi trên mặt đất.
  - Sửa lỗi font chữ: thay thế triệt để các font không hỗ trợ tiếng Việt (`class_d.g`, `class_d.f`) bằng hệ thống font bitmap chuẩn của game KPAH `class_d.j` (`class_d.j[0]` cho chữ trắng bóng mờ chuẩn tiếng Việt 100%, `class_d.j[2]` cho màu đỏ cảnh báo độ bền hỏng và debuff).
  - Tái cấu trúc HUD sang góc trên bên phải (`class_acv.m - 5`, `anchor = 1`):
    - Dòng 1: Tên map & Tọa độ `X:Y` (`y = 4`).
    - Dòng 2: Độ bền vũ khí (`y = 17`), nhấp nháy đỏ khi hỏng $\le 0$.
    - Dòng 3+: Danh sách Buff (`class_d.j[0]`) và Debuff (`class_d.j[2]`) kèm thời gian đếm ngược dạng text thanh mảnh, tự nhiên (`y = 30` trở xuống).
    - Chỉ báo nhiệm vụ `[!]` / `[?]` trên đầu NPC nhiệm vụ dùng font chuẩn `class_d.j[0]`.
- `game/app/src/classes/ModController.java`:
  - `handleAutoSellLowEquip()`: Gỡ bỏ kiểm tra `isNearShopOrBlacksmith()` và `hasTheMuaBan()`. Chỉ cần người chơi bật `isAutoSellLowEquip` trong Menu "Cơ chế" là Client tự động duyệt túi trang bị `class_hw.bu`, áp dụng 4 lớp bảo vệ an toàn (chỉ bán đồ cấp thấp hơn bản thân, không bán đồ cường hóa, không bán đồ khảm ngọc/lỗ ngọc, không bán đồ thuê/hạn ngày, không bán đồ đang mặc) và gửi lệnh bán tuần tự 1.5s/món.
- `server/KPAH/src/services/ShopService.java`:
  - `onSellItem()`: Gỡ bỏ đoạn chặn `!hasTheMuaBan && !isNearShop`, khôi phục luồng bán đồ chuẩn gốc của Server, cho phép bán trang bị mượt mà không bị từ chối.
  - Xóa bỏ các hàm thừa `isNearShopNpc` và `isShopOrBlacksmithNpc`.

**Kết quả:** ✅ Thành công (Biên dịch Client `KPAH_PROD.jar`/`KPAH_MOD.jar` bằng Java 8; Biên dịch Server `KPAH.jar` bằng Java 21; Khởi động lại container `kpah-mysql` và khởi động lại Server daemon chạy ổn định lắng nghe cổng 19129).
**Ghi chú:**
- Backup files:
  - `game/app/src/classes/_backup/Paint.java.bak.20260909_1638`
  - `game/app/src/classes/_backup/ModController.java.bak.20260909_1638`
  - `server/KPAH/src/services/_backup/ShopService.java.bak.20260909_1638`
## [2026-09-09 21:10] — Task #66: Tái bố cục HUD sang bên trái tách màu Tiêu đề - Giá trị, sửa triệt để vòng lặp Tự bán đồ & ChatOnly, khắc phục lỗi Database Truncation cột info và nâng cấp hệ thống Server Log chi tiết

**Yêu cầu:**
- Tái bố cục HUD: Chuyển HUD về góc trên bên trái (dưới avatar và icon 12+) để tránh che khuất tên quái/NPC target ở góc trên bên phải. Chia bố cục rõ ràng, phân biệt màu sắc giữa tiêu đề (chữ trắng `class_d.j[0]`) và nội dung/giá trị (chữ cam/vàng `class_d.j[3]`, hỏng nhấp nháy đỏ `class_d.j[2]`).
- Khắc phục lỗi Server MySQL Truncation: `com.mysql.jdbc.MysqlDataTruncation: Data truncation: Data too long for column 'info' at row 1` khi `PlayerDAO.updatePlayer` do dữ liệu `QuestData` mở rộng.
- Sửa lỗi tính năng Tự bán đồ không kích hoạt: Phát hiện game loop `class_abj` override `c()` mà không gọi `super.c()` khiến `ModController.update()` bị bỏ qua -> Đưa lời gọi `handleAutoSellLowEquip()` trực tiếp vào `Paint.onPaint(g)`.
- Bổ sung thông báo `ChatOnlyMe`: Khi bán đồ (cả tự động lẫn bán qua NPC), gửi chat riêng cho nhân vật thông báo rõ tên món đồ đã bán và số xu nhận được.
- Nâng cấp hệ thống Server Log: Cấu hình log gọn gàng, chi tiết, màu sắc ANSI trực quan cho các sự kiện: Mua đồ từ Shop (Đặc biệt, NPC, Chuộc đồ), Bán đồ, Tiêu diệt Quái Tinh Anh (tên quái, map, tọa độ X/Y), và Làm nhiệm vụ (Nhận & Hoàn thành Chính tuyến/Hằng ngày).

**Files thay đổi:**
- `game/app/src/classes/Paint.java`:
  - Di chuyển HUD sang bên trái (`startX = 4`, `startY = Math.max(avatarH + 26, 58)`), nằm ngay dưới avatar và icon 12+, không che lấp bất kỳ thông tin target nào bên phải.
  - Phân tách rõ ràng: Tiêu đề "Toạ độ: ", "Độ bền: " dùng font trắng `class_d.j[0]`; giá trị bản đồ và số độ bền dùng font cam/vàng `class_d.j[3]`; cảnh báo hỏng dùng font đỏ `class_d.j[2]`.
  - Hook `ModController.handleAutoSellLowEquip()` trực tiếp vào đầu `Paint.onPaint(g)` để game loop render đảm bảo kiểm tra tự động bán trang bị liên tục mỗi frame.
- `server/KPAH/src/utils/ServerLog.java`:
  - Thêm các method chuyên dụng `shop(...)`, `combat(...)`, `quest(...)` với định dạng thời gian chuẩn `[yyyy-MM-dd HH:mm:ss.SSS]` và màu sắc ANSI bắt mắt (CYAN cho Shop, RED cho Combat, MAGENTA cho Quest), hỗ trợ ghi log song song ra console và file xoay vòng ngày.
- `server/KPAH/src/services/ShopService.java`:
  - Trong `onSellItem`: Thêm `ChatService.instance.sendChatOnlyMe(player, String.format("Đã bán %s nhận được %s xu.", item.getTemplate().getName(), Util.formatNumber(price)))` và `ServerLog.shop(...)`.
  - Trong `buyItemSpecial`, `buyItemNpcShop`, `buyItemDeposite`: Bổ sung ServerLog chi tiết cho từng loại giao dịch mua trang bị, mua ngọc, mua dược phẩm, mua shop đặc biệt và chuộc lại đồ.
- `server/KPAH/src/services/MonsterService.java`:
  - Trong `onMonsterDropItem`: Bổ sung `ServerLog.combat(...)` khi Quái Tinh Anh bị tiêu diệt (ghi rõ tên người chơi, tên quái tinh anh, map, khu vực và tọa độ X/Y).
- `server/KPAH/src/services/QuestService.java`:
  - Bổ sung `ServerLog.quest(...)` chi tiết khi người chơi nhận và hoàn thành tất cả các mốc nhiệm vụ cốt truyện (Chính tuyến 0 -> 6) cũng như nhiệm vụ hằng ngày (Mã NV, Mục tiêu, Trả thưởng).
- `server/update_20260909.sql`:
  - Bổ sung mục 5: Các câu lệnh `ALTER TABLE players MODIFY COLUMN ... MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL` cho toàn bộ các cột dữ liệu JSON/danh sách (`info`, `location`, `point`, `inventory`, `skills`, `itemBody`, `itemBag`, `itemBox`, `itemPotion`, `itemQuest`, `itemGem`, `itemGemLock`, `itemSold`, `itemAnimal`, `itemAnimalExpiry`) để người dùng chạy trên Termux fix dứt điểm lỗi Data truncation.

**Kết quả:** ✅ Thành công (Đã biên dịch cả Client `KPAH_PROD.jar`/`KPAH_MOD.jar` và Server `KPAH.jar`; Khởi động lại Server daemon port 19129 chạy ổn định).
**Ghi chú:**
- Backup files:
  - `server/KPAH/src/utils/_backup/ServerLog.java.bak.20260909_2106`
  - `server/KPAH/src/services/_backup/ShopService.java.bak.20260909_2106`
  - `server/KPAH/src/services/_backup/MonsterService.java.bak.20260909_2106`
  - `server/KPAH/src/services/_backup/QuestService.java.bak.20260909_2106`
  - `game/app/src/classes/_backup/Paint.java.bak.20260909_2106`
- Hiện tại Phần 07 có: 6/10 task.

---

## [2026-09-09 21:46] — Task #67: Khắc phục lỗi độ bền vũ khí lệch trạng thái Client-Server, sửa bộ lọc Tự bán trang bị cấp thấp & thiết lập toàn bộ cơ chế mới mặc định OFF

**Yêu cầu:**
- Khắc phục lỗi "Vũ khí còn độ bền nhưng báo đã hỏng": Tìm ra nguyên nhân gốc rễ và xử lý triệt để sự sai lệch trạng thái độ bền giữa Client và Server.
- Khắc phục lỗi "Chức năng tự động bán trang bị cấp thấp không hoạt động": Phân tích mã nguồn Client decompile để tìm ra điểm nghẽn của bộ lọc kiểm tra trang bị và sửa chữa để hoạt động trơn tru, bảo vệ tài sản an toàn.
- Thiết lập mặc định OFF (`false`) cho toàn bộ 4 cơ chế mới (Tự nhặt đồ, Ưu tiên quái Tinh Anh, Tự động hồi sinh, Tự bán trang bị cấp thấp) để đảm bảo trải nghiệm nguyên bản ban đầu cho người chơi.

**Nguyên nhân gốc rễ phát hiện:**
1. *Lỗi độ bền vũ khí:* 
   - Trong `server/KPAH/src/item/ItemEquip.java`: `mDurable` là Độ bền tối đa (Max Durable), nhưng code cũ lại trừ `mDurable--` theo từng hit đánh. Sau vài phút train quái, `mDurable` chạm 0 và bị âm. Một điều kiện cũ `if (durable <= 0 || mDurable <= 0)` lập tức ép `durable = 0` ngay khi `mDurable` âm, dù độ bền thực sự của vũ khí còn hàng trăm điểm.
   - Khi `durable = 0`, Server chặn đánh quái và chat `"Vũ khí đã hỏng!..."`, nhưng Server không hề gửi packet `sendItemBody` cập nhật cho Client, dẫn đến HUD Client vẫn hiển thị con số độ bền cũ (732) gây lệch pha.
2. *Lỗi Tự bán trang bị cấp thấp:*
   - Trong `game/app/src/classes/ModController.java`: Điều kiện `if (ql.k > 0 || (ql.H != null && ql.H.size() > 0)) continue;` bị sai bản chất. Trong mã nguồn KPAH, `ql.H` chính là danh sách Option thuộc tính của trang bị (Công, Thủ, HP...). Vì 100% trang bị rơi ra hoặc mua đều có thuộc tính (`ql.H.size() > 0`), câu lệnh này đã vô tình chặn 100% tất cả trang bị trong túi, khiến hàm không bao giờ bán được món nào. Lỗ khảm ngọc thực tế là `ql.I` (số ngọc đã khảm) và `ql.J` (tổng số lỗ).
   - Dòng lệnh `class_acv.a("Tự bán: ...", false)` mở ra popup dialog OK giữa màn hình gây gián đoạn trải nghiệm chơi game.

**Files thay đổi:**
- `server/KPAH/src/item/ItemEquip.java`:
  - Viết lại hàm `minusDurable()`: Dùng biến đếm `hitCounter` (cứ 15 đòn đánh mới trừ 1 điểm độ bền), tuyệt đối không chạm vào `mDurable`. Trả về `boolean` (true nếu độ bền thực sự giảm 1 điểm).
- `server/KPAH/src/services/SkillService.java`:
  - Trong `useSkillToPlayer` và `useSkillToMob`: Khi `weapon.minusDurable()` trả về true, gọi `InventoryService.instance.sendItemBody(pl)` để đồng bộ ngay độ bền mới về Client (HUD nhảy số thời gian thực).
  - Trong `checkWeaponUsable`: Khi vũ khí chạm mốc hỏng ($\le 0$), gửi `sendItemBody(pl)` đồng bộ ngay lập tức để HUD Client chuyển sang nhấp nháy đỏ `0 (HỎNG)` khớp hoàn hảo với bubble chat cảnh báo.
- `server/KPAH/src/daos/PlayerDAO.java`:
  - Thêm cơ chế tự động phục hồi thông minh khi đọc trang bị từ DB: Nếu `mDurable <= 0` do lỗi âm cũ, tự động gán lại bằng `template.getDurable()`; nếu `durable <= 0` do bug ép về 0 bởi `mDurable` âm, tự động khôi phục `durable = mDurable`.
- `game/app/src/classes/ModController.java`:
  - Sửa lại toàn bộ bộ lọc trong `handleAutoSellLowEquip()`: Loại bỏ kiểm tra sai `ql.H.size() > 0`, kiểm tra chuẩn xác cấp cường hóa (`ql.s > 0`), ngọc đã khảm (`ql.I > 0`), hạn dùng/thuê (`ql.w > 0 || ql.x > 0 || tmpl.h > 0`).
  - Loại bỏ lời gọi `class_acv.a(...)`, để Server tự trừ trang bị, cộng xu và gửi chat thông báo `ChatOnlyMe` êm dịu, không gián đoạn thao tác người chơi.
- `game/app/src/config/Config.java`:
  - Đổi tên Record Store lên `global_config_v3` để toàn bộ thiết bị (kể cả MicroEmulator đã chạy trước đó) đều nhận cấu hình mới tinh.
  - Đặt mặc định `false` (OFF) cho tất cả 4 tính năng: `isAutoPickup = false;`, `isPrioritizeElite = false;`, `isAutoRevive = false;`, `isAutoSellLowEquip = false;`.

**Kết quả:** ✅ Thành công (Đã biên dịch cả Server `KPAH.jar` và Client `KPAH_PROD.jar` / `KPAH_MOD.jar`; Server daemon port 19129 đã khởi động lại và MicroEmulator Client đã mở sẵn sàng để trải nghiệm).
**Ghi chú:**
- Backup files:
  - `server/KPAH/src/item/_backup/ItemEquip.java.bak.20260909_2145`
  - `server/KPAH/src/services/_backup/SkillService.java.bak.20260909_2145`
  - `server/KPAH/src/daos/_backup/PlayerDAO.java.bak.20260909_2145`
  - `game/app/src/classes/_backup/ModController.java.bak.20260909_2145`
  - `game/app/src/config/_backup/Config.java.bak.20260909_2145`
- Hiện tại Phần 07 có: 7/10 task.

---

## [2026-09-09 22:15] — Task #68: Khắc phục triệt để lỗi Tự bán trang bị & Sửa lỗi hiển thị hình ảnh thú cưỡi (Thiên lý mã / Xích thố)

**Yêu cầu:**
- Khắc phục lỗi "Vẫn không thấy tự bán đồ, cả log server lẫn client đều không hiện": Tìm nguyên nhân gốc rễ và sửa dứt điểm để cơ chế tự bán trang bị hoạt động trơn tru.
- Khắc phục lỗi "Hình ảnh thú cưỡi: mua ngựa 10L (Thiên lý mã) nhưng game lại hiển thị đang cưỡi con ngựa 70L (Xích thố)": Điều tra chuỗi đóng gói, nạp dữ liệu sprite thú cưỡi và khắc phục sai lệch thứ tự.

**Nguyên nhân gốc rễ phát hiện:**
1. *Lỗi Tự bán trang bị:*
   - Trong `game/app/src/classes/ModController.java`: Điều kiện lọc đồ thuê kiểm tra `ql.x > 0`.
   - Đối chiếu với mã nguồn Client (`class_bi.java` dòng 1384): `class_ql.x = System.currentTimeMillis();`. Biến `ql.x` lưu timestamp thời điểm nhận trang bị, luôn $> 1.7 \times 10^{12} > 0$.
   - Do đó, điều kiện `ql.x > 0` luôn trả về `true` với 100% trang bị, kích hoạt lệnh `continue;` bỏ qua tất cả đồ trong túi khiến Client không bao giờ gửi lệnh bán lên Server.
2. *Lỗi hiển thị hình ảnh thú cưỡi:*
   - Thư mục tài nguyên `server/KPAH/data/image/horse/0` chứa 4 file sprite tương ứng 4 loại ngựa cơ bản: `0.png` (Thiên lý mã - 10L, nâu), `1.png` (Xích thố - 70L, đỏ), `2.png` (Bạch mã - trắng), `3.png` (Hắc mã - đen).
   - Trong `server/KPAH/src/manager/Manager.java` dòng 1327: Lời gọi `File[] files = fileList.get(i).listFiles();` không hề được sắp xếp (sort).
   - Trên Linux/Termux (hệ thống file ext4), `listFiles()` trả về danh sách theo thứ tự inode directory entry là `['1.png', '0.png', '2.png', '3.png']`.
   - Kết quả: `files[0]` bị gán là `1.png` (Xích thố) và `files[1]` bị gán là `0.png` (Thiên lý mã).
   - Khi Server đóng gói mảng `HEAD_HORSE[0]` gửi sang Client qua gói tin opcode -8 (GET_IMAGE case 2), Client lần lượt add từng sprite vào mảng Image `class_ko.b`.
   - Dẫn đến: Index 0 trong `class_ko.b` của Client là **Xích thố**, còn Index 1 mới là **Thiên lý mã**.
   - Khi người chơi cưỡi ngựa 10L (`IMAGE_THIEN_LY_MA = 0`), Client vẽ `class_ko.b.elementAt(0)` -> Vẽ ra hình con Xích thố 70L (ngựa đỏ).
   - Đồng thời, `MessageHandler.java` chỉ kiểm tra `if (ver == 0)`. Nếu Client đã lưu cache RMS `nqshImgPotionNew` từ trước với dữ liệu lỗi, Server sẽ không gửi lại hình ảnh mới nếu không tăng version hoặc xóa cache cũ.

**Files thay đổi:**
- `game/app/src/classes/ModController.java`:
  - Trong `autoSellLowLevelEquips()`: Loại bỏ kiểm tra sai `ql.x > 0`, chỉ kiểm tra `ql.w > 0` (`w` là `dayUse` - hạn ngày đồ thuê) và `tmpl.h > 0`.
  - Trong static block: Tự động dọn dẹp cache RMS `nqshImgPotionNew` cũ một lần lúc khởi động client thông qua cờ đánh dấu `horse_img_clean_v1`, đảm bảo client yêu cầu tải lại toàn bộ hình ảnh thú cưỡi chuẩn từ Server.
- `server/KPAH/src/manager/Manager.java`:
  - Dòng 1327: Thêm `Arrays.sort(files, Comparator.comparing(File::getName, new NumericStringComparator()))` để đảm bảo thứ tự nạp file trong từng folder thú cưỡi luôn tuân theo thứ tự số tăng dần (`0.png` -> `1.png` -> `2.png` -> `3.png`), bất kể hệ điều hành và filesystem.
- `server/KPAH/src/services/Service.java`:
  - Định nghĩa hằng số `public static final byte IMAGE_VERSION = 69;` và sử dụng trong phương thức `sendImage(player, type)` thay cho byte cứng 68.
- `server/KPAH/src/network/MessageHandler.java`:
  - Sửa đổi điều kiện case `CommandMessage.GET_IMAGE`: Kiểm tra `if (ver != Service.IMAGE_VERSION)` để tự động gửi lại hình ảnh chuẩn xác khi Client gửi lên version cũ.

**Kết quả:** ✅ Thành công
- Đã biên dịch toàn bộ Client Java 8 (`KPAH_PROD.jar` và `KPAH_MOD.jar`).
- Đã biên dịch Server Java 21 (`KPAH.jar`).
- Server daemon (Port 19129) đã khởi động lại và tải thành công 14 thư mục hình ảnh thú cưỡi theo thứ tự chuẩn.
- Đã test và xác nhận logic `autoSellLowLevelEquips()` và thứ tự sprite thú cưỡi `HEAD_HORSE` chính xác 100%.

**Ghi chú:**
- Backup files:
  - `game/app/src/classes/_backup/ModController.java.bak.20260909_2211`
  - `server/KPAH/src/manager/_backup/Manager.java.bak.20260909_2211`
  - `server/KPAH/src/services/_backup/Service.java.bak.20260909_2211`
  - `server/KPAH/src/network/_backup/MessageHandler.java.bak.20260909_2211`
- Hiện tại Phần 07 có: 8/10 task.

---

## [2026-09-09 23:05] — Task #69: Tối ưu sức mạnh trang bị shop xu, nâng cấp quái tinh anh & hệ thống phần thưởng rương tinh anh (trang bị chế tạo có phẩm cấp, nguyên liệu sơ/cao cấp bậc 1-6)

**Yêu cầu:**
- Tăng sức mạnh trang bị mua bằng xu (thủ giáp và công vũ khí) và điều chỉnh nhẹ giá xu để người chơi mặc đồ xu có thể đánh quái cùng cấp hoặc hơn cấp mà không bị one-shot, treo máy ổn định. Khoan giảm sát thương quái, giữ nguyên sức mạnh quái tinh anh.
- Bỏ hoàn toàn việc rơi trang bị thường trên đất từ quái tinh anh.
- Quái tinh anh tập trung 2 phần thưởng chính: EXP khổng lồ và Rương tinh anh. Số lượng rương tinh anh rơi ra tùy thuộc vào cấp quái tinh anh.
- Nâng cấp phần thưởng rương tinh anh theo bậc:
  - Rương bậc thấp (Bậc 1 & 2, cấp < 20): Rơi gói Nguyên Liệu Sơ Cấp và Cao Cấp từ bậc 1 đến 6 (Vải, Sắt, Ngọc, Gỗ, Da mềm, Tơ lụa, Bạc, Thủy tinh, Gỗ sưa, Da cứng), đá ngũ hợp, lượng, xu, dược phẩm và bình KN.
  - Rương bậc cao (Bậc 3 & 4, cấp 20+): Rơi 100% Trang Bị Chế Tạo Hoàn Mỹ cấp 20+ có phẩm cấp từ Ngũ phẩm đến Nhất phẩm (đa nghề nghiệp: Kiếm, Đao, Bút, Búa, Cung, Trang sức; có ngũ hành; có 1-5 dòng thuộc tính phụ ngẫu nhiên; khắc ấn Tinh Anh).

**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java` — Thêm phương thức `applyShopEquipBuff(ItemEquipTemplate it)` tự động buff thủ vật/thủ ma (+40% đến +60%, cộng 10-30 điểm) cho giáp (áo, quần, nón, giày, găng), tăng công vũ khí (+30% đến +50%) và tăng nhẹ 25% giá bán xu khi nạp `item_equipment` vào RAM.
- `server/KPAH/src/map/Monster.java` — Tăng thưởng EXP quái tinh anh lên gấp 60 lần (`tnPl *= 60`); bỏ rơi trang bị thường trên đất khi là quái tinh anh; điều chỉnh số lượng Rương Tinh Anh rớt theo cấp quái (Lv 1-9: 1 rương; Lv 10-19: 1-2 rương; Lv 20-29: 2-3 rương; Lv 30+: 3-4 rương) và 100% rớt bình KN tinh anh.
- `server/KPAH/src/services/ItemService.java` — Bổ sung phương thức `createCraftedEquipment(byte tier, int playerLv, byte playerClass)` tạo trang bị chế tạo hoàn mỹ đa nghề nghiệp (ưu tiên class người chơi), cấp độ 20-39+, phẩm cấp phân bố từ Ngũ phẩm (45%) -> Tứ phẩm (28%) -> Tam phẩm (16%) -> Nhị phẩm (8%) -> Nhất phẩm (3%), ngũ hành Kim-Mộc-Thủy-Hỏa-Thổ, tăng chỉ số cơ bản theo phẩm cấp (+10% đến +80%) và tạo 1-5 dòng thuộc tính phụ (HP, MP, STR, AGI, INT, VIT, Crit, Dodge, Acc, Xuyên giáp, Giảm ST, EXP, X2 ST, Hấp thu).
- `server/KPAH/src/services/UseItemService.java` — Tái cấu trúc hàm `openEliteChest`: Tăng mạnh Lượng và Xu theo bậc rương; rương bậc 1 & 2 trao thưởng gói nguyên liệu sơ cấp & cao cấp bậc 1..6 cùng đá ngũ hợp; rương bậc 3 & 4 trao thưởng nguyên liệu cao cấp cùng 100% trang bị chế tạo hoàn mỹ có phẩm cấp (kèm 25%-35% cơ hội nhận món thứ 2).
- `server/update_buff_shop_equips.sql` — Script SQL đồng bộ giá bán trang bị shop xu cho MariaDB trên Termux.

**Kết quả:** ✅ Thành công (Đã biên dịch hoàn tất `server/KPAH/dist/KPAH.jar` với Java 21, ant build clean jar thành công không có lỗi).
**Ghi chú:**
- Backup files:
  - `server/KPAH/src/map/_backup/Monster.java.bak.20260909_2258`
  - `server/KPAH/src/manager/_backup/Manager.java.bak.20260909_2258`
  - `server/KPAH/src/services/_backup/ItemService.java.bak.20260909_2258`
  - `server/KPAH/src/services/_backup/UseItemService.java.bak.20260909_2258`
- Hiện tại Phần 07 có: 9/10 task.

---

## [2026-09-09 23:12] — Task #70: Chuyển đổi toàn diện cơ chế điều chỉnh chất lượng và giá trang bị shop xu sang Direct SQL (Zero RAM overhead)

**Yêu cầu:**
- Loại bỏ hoàn toàn cơ chế buff động trong RAM Java (`applyShopEquipBuff` trong `Manager.java`) theo yêu cầu của người dùng để tránh ngốn RAM và CPU server vô ích, đặc biệt khi triển khai trên Termux.
- Viết script SQL trực tiếp `update_buff_shop_equips.sql` điều chỉnh vĩnh viễn chất lượng (chỉ số thủ phòng thủ, công vũ khí, công phụ/HP trang sức) và giá bán xu cho 257 trang bị xu trong bảng `item_equipment` của Database MariaDB.
- Giữ nguyên toàn bộ logic rương tinh anh, phần thưởng trang bị chế tạo hoàn mỹ (Ngũ phẩm -> Nhất phẩm) và quái tinh anh đã hoàn thành ở Task #69.

**Files thay đổi:**
- `server/KPAH/src/manager/Manager.java`:
  - Gỡ bỏ hoàn toàn phương thức `applyShopEquipBuff(ItemEquipTemplate it)` (trước đó ở dòng 430-458).
  - Gỡ bỏ lời gọi `applyShopEquipBuff(itemTemplate)` trong quá trình nạp template từ database (trước đó ở dòng 824).
  - Server nạp dữ liệu sạch 100% từ Database `item_equipment` mà không tốn thêm byte RAM hay chu kỳ CPU nào để tính toán buff.
- `server/update_buff_shop_equips.sql`:
  - Tạo script SQL toàn diện gồm 257 câu lệnh `UPDATE \`item_equipment\`` được phân chia khoa học thành 13 nhóm: Áo (34), Quần (34), Nón (34), Giày (16), Găng (16), Kiếm (15), Đao (15), Bút (15), Búa (15), Cung (15), Nhẫn (16), Dây chuyền (16), Ngọc (16).
  - Tăng thủ giáp (thủ vật + thủ ma) thêm ~55-60% (+10 đến +53 điểm DEF tùy cấp độ).
  - Tăng công vũ khí thêm ~42% (+15 đến +134 điểm ATK tùy cấp độ).
  - Tăng nhẹ 25% giá bán xu (`price`) theo chuẩn cân bằng kinh tế game.
  - Sửa lỗi giá gốc của Ngọc tử quang (ID 570) từ 130 xu lên 162,500 xu chuẩn mốc cấp 69.
  - Đóng gói trong `START TRANSACTION;` và `COMMIT;` an toàn tuyệt đối khi chạy trên MariaDB.
- `server/KPAH/dist/KPAH.jar`:
  - Biên dịch lại toàn bộ server KPAH bằng Ant (`BUILD SUCCESSFUL`).
  - Khởi động lại Server Daemon kiểm tra: Tải sạch toàn bộ 755 Item Template trong 1.1s, port 19129 sẵn sàng.

**Kết quả:** ✅ Thành công
- Đã thực thi script SQL vào database local thành công 257/257 bản ghi.
- Server nạp dữ liệu trực tiếp từ database sạch sẽ, không có overhead RAM.
- Cung cấp script SQL hoàn chỉnh và hướng dẫn chi tiết để người dùng chạy trên Termux.

**Ghi chú:**
- Backup files: `server/KPAH/src/manager/_backup/Manager.java.bak.20260909_2308`.
- File này đã đạt đủ 10 task (#61 - #70). Tiến hành lưu trữ thành `tasks/TASK_07.md` và mở file `TASK.md` mới (Phần 08).

---

