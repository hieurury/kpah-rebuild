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
- Hiện tại Phần 07 có: 5/10 task.

---
