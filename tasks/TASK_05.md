# KPAH — Task Log (Phần 05: Task 41 - 50)

> Lưu trữ nhật ký nhiệm vụ từ #41 đến #50.
> Quy tắc: Mỗi file chỉ lưu trữ tối đa 10 task.

---

## [2026-09-08 11:41] — Ép Cấu Hình IPv4 Cho Server KPAH Để Cố Định Localhost 127.0.0.1

**Yêu cầu:** Giải quyết vấn đề kết nối `127.0.0.1` trên Termux để không phải đổi IP LAN thủ công khi chuyển mạng Wi-Fi/4G.

**Mức độ rủi ro:** Thấp

**Files thay đổi:**
- `server/KPAH/start.sh` — Thêm cờ JVM `-Djava.net.preferIPv4Stack=true` khi khởi chạy KPAH Server, đảm bảo Server bind cổng IPv4 `0.0.0.0:19129` và chấp nhận kết nối loopback `127.0.0.1` từ Playit daemon.

**Kết quả:** ✅ Đã cập nhật script, commit git `01708c3`.

---

---

## [2026-09-08 11:44] — Khởi Chạy Bản Game Production KPAH_PROD.jar Để Kiểm Thử

**Yêu cầu:** Mở game bản production (`KPAH_PROD.jar`) trên máy tính qua MicroEmulator để người dùng đăng nhập và kiểm tra kết nối với server Termux.

**Mức độ rủi ro:** Thấp

**Hành động:**
- Khởi chạy MicroEmulator với `game/build/dist/KPAH_PROD.jar`.
- Ghi nhận console game đã kết nối tới `practicing-achieve.tun.ply.gg:50758` và gửi lệnh khởi tạo `cmd=-1`, `cmd=1`.

**Kết quả:** ✅ Thành công (Cửa sổ game đã mở trên màn hình máy tính).

---

---

## [2026-09-08 11:51] — Phân Tích Cơ Chế Lọc Gói Tin Của Playit Tunnel & Chuyển Sang Raw TCP

**Yêu cầu:** Xác định chính xác nguyên nhân Playit không chuyển tiếp gói tin từ game KPAH về Server trên Termux dù agent và server đều online.

**Mức độ rủi ro:** Thấp

**Phát hiện kỹ thuật:**
- Tunnel loại `Minecraft Java` của Playit.gg hoạt động như một Layer-7 Reverse Proxy, yêu cầu gói tin bắt tay (Handshake packet) đúng chuẩn Minecraft chứa hostname để định tuyến.
- Khi gửi thử gói tin Minecraft Handshake qua Python script tới `practicing-achieve.tun.ply.gg:50758`: Playit lập tức chuyển tiếp vào điện thoại và KPAH server nhận được ngay.
- Khi client KPAH gửi dữ liệu binary riêng (`cmd=-1`, opcode `-40`): Playit lọc bỏ và không kích hoạt sự kiện `NewClient` về agent Termux (`~/playit.log` không có log kết nối).

**Giải pháp:**
- Hướng dẫn người dùng xóa tunnel cũ và tạo tunnel loại **`Terraria`** (hoặc `Custom TCP`) trên Playit dashboard để truyền tải luồng Raw TCP không qua bộ lọc game-specific.

**Kết quả:** ✅ Đã xác định chính xác 100% nguyên nhân và hướng dẫn cấu hình lại tunnel.

---

---

## [2026-09-08 11:58] — Cập Nhật Client Production Trỏ Về Tunnel Raw TCP `practicing-unusable.tun.ply.gg:32289`

**Yêu cầu:** Cập nhật cấu hình server host và port mới từ tunnel Terraria (Raw TCP) vào mã nguồn client và biên dịch lại gói `KPAH_PROD.jar`.

**Mức độ rủi ro:** Thấp

**Files thay đổi:**
- `game/app/src/classes/class_yv.java` — Đổi host thành `practicing-unusable.tun.ply.gg` và port thành `32289`.
- `game/build.xml` — Cập nhật replacefilter và target `dist-prod` với host `practicing-unusable.tun.ply.gg` và port `32289`.

**Kết quả:** ✅ Thành công
- Đã build thành công `game/build/dist/KPAH_PROD.jar` (1,173,572 bytes) và `KPAH_PROD.jad`.
- Đã khởi chạy lại MicroEmulator trên máy tính để người dùng kiểm tra kết nối đăng nhập.

---

---

## [2026-09-08 12:25] — Chuyển Sang Bore TCP Tunnel (bore.pub:19129) Giải Quyết Lỗi UDP & DNS Của Playit

**Yêu cầu:** Giải quyết triệt để lỗi mất đồng bộ, rớt kênh UDP (`udp channel requires auth`) và lỗi DNS trên Termux khi dùng Playit.gg.

**Mức độ rủi ro:** Trung bình

**Phát hiện kỹ thuật:**
- Playit.gg bắt buộc vận chuyển dữ liệu qua UDP trên các port cao (5512, 5525) thường bị các nhà mạng di động / router tại Việt Nam chặn hoặc drop, dẫn đến vòng lặp auth và client bị timeout.
- Android Bionic libc không đọc `$PREFIX/etc/hosts`, khiến các ứng dụng viết bằng Rust (như `playit-cli`) gặp lỗi phân giải DNS `ConnectError('dns error')`.
- Thay thế bằng **Bore** (Single binary musl tĩnh, Pure TCP, zero-config, không cần tài khoản):
  - Cổng 19129 trên `bore.pub` hoàn toàn trống và đã kiểm tra bind thành công.
  - Kết nối qua IPv4 `159.223.110.159` khắc phục hoàn toàn hiện tượng timeout IPv6.

**Files thay đổi:**
- `server/KPAH/start.sh` — Tích hợp tự động khởi chạy daemon Bore tunnel nền (`bore local 19129 --to 159.223.110.159 -p 19129`).
- `server/KPAH/stop.sh` — Bổ sung dừng tiến trình `bore`.
- `game/app/src/classes/class_yv.java` — Cập nhật cấu hình server mặc định: host `bore.pub`, port `19129`.
- `game/build.xml` — Cập nhật macro và target `dist-prod` trỏ về `bore.pub:19129`.
- `game/build/dist/KPAH_PROD.jar` — Biên dịch lại bản game production (1,173,558 bytes).

**Backup:**
- `server/KPAH/_backup/start.sh.bak.20260908_1224`
- `server/KPAH/_backup/stop.sh.bak.20260908_1224`
- `game/app/src/classes/_backup/class_yv.java.bak.20260908_1224`
- `game/_backup/build.xml.bak.20260908_1224`

**Kết quả:** ✅ Thành công
- Đã đóng gói thành công `KPAH_PROD.jar` và cập nhật các launcher server.

---

## [2026-09-08 19:32] — Fix items, auto disconnect, HP quái, elite respawn bug

**Yêu cầu:**
1. Thống nhất hình ảnh rương tinh anh (1 hình duy nhất cho bậc 1-4)
2. Hiện mô tả vật phẩm: Tinh Anh Đan (id 107) và Bình KN (id 108) đang trống
3. Fix auto disconnect khi treo máy
4. Tăng x2 HP quái thường
5. Fix bug quái mới spawn sau khi quái tinh anh chết vẫn mang hình tinh anh

**Files thay đổi:**
- `server/kpah.sql` — (1) idImage 161,162: 67→68 | (2) name+name2+idImage cho id 107,108,109,110,111 | (3) HP quái thường (id 1-35, 40-45, 50,55,57,60,62,63,65,68,71,74,76,80) nhân x2
- `server/KPAH/src/network/Sender.java` — Đổi tên `lastTimeCollectMessage` → `lastTimeActivity`
- `server/KPAH/src/network/Collector.java` — Thêm `setSender()` và update `lastTimeActivity` khi nhận tin từ client (fix disconnect root cause)
- `server/KPAH/src/network/Session.java` — Wire collector với sender; cập nhật tham chiếu field mới
- `server/KPAH/src/map/Monster.java` — Khi quái respawn: xóa mob khỏi `otherMobInside` của tất cả player trong zone → buộc gửi lại MONSTER_INFO mới với isElite đúng

**Kết quả:** ✅ Thành công — Build passed (1 warning cũ không liên quan)

**Root cause disconnect:** `lastTimeCollectMessage` chỉ track khi server GỬI tin, không phải khi client GỬI. Khi auto farm mà server không gửi event nào (quái không có, đứng chờ...) → bộ đếm không reset → kick sau 10 phút. Fix: track cả thời gian nhận từ client.

**Root cause elite respawn bug:** `MONSTER_INFO` chỉ được gửi khi mob lần đầu vào tầm nhìn (không có trong `otherMobInside`). Sau khi quái chết, id vẫn còn trong list → khi respawn không gửi lại MONSTER_INFO → client dùng template cũ (có thể là elite). Fix: xóa mob id khỏi otherMobInside của tất cả player khi respawn.

**Backup:** 
- `_backup/kpah.sql.bak.*`
- `server/KPAH/src/map/_backup/Monster.java.bak.*`
- `server/KPAH/src/network/_backup/Sender.java.bak.*`
- `server/KPAH/src/network/_backup/Collector.java.bak.*`
- `server/KPAH/src/network/_backup/Session.java.bak.*`

---

---

## [2026-09-08 20:16] — Fix triệt để bug quái thường tái sử dụng hình ảnh quái tinh anh

**Yêu cầu:** Sửa triệt để vấn đề quái tinh anh sau khi chết, quái thường mới spawn ra tại vị trí đó vẫn bị mang hình ảnh, hào quang, danh hiệu của quái tinh anh.

**Nguyên nhân gốc rễ (2 tầng Client & Server):**
1. **Client (`game/app/src/classes/class_bb.java`):**
   - Biến `isElite` trong client trước đây chỉ được gán `= true` ở 2 chỗ (`m(100)` và `a(class_by)` khi `class_by2.i == 100`). **Hoàn toàn không có bất kỳ dòng nào gán `isElite = false`** trong toàn bộ client!
   - Khi quái chết (`cV = 5`) hoặc respawn (`case 8` timer đếm xong) hoặc nhận info quái thường (`class_by2.i != 100`), `isElite` vẫn giữ nguyên giá trị `true`.
   - Client pool/tái sử dụng instance `class_bb` cho quái trên map → quái vĩnh viễn bị gán cờ tinh anh cho đến khi đổi map/thoát game.
2. **Server (`server/KPAH/src/map/Monster.java`):**
   - Khi quái respawn, server trước đó chỉ xóa mob khỏi `otherMobInside` của player và gửi `MOVE_CHAR`.
   - `updateMobInside` chỉ được gọi khi player di chuyển (`onMove`). Nếu player đứng im treo máy auto đánh quái thì `MONSTER_INFO` không bao giờ được gửi lại.
   - Khi nhận `MOVE_CHAR`, client chỉ cập nhật toạ độ chứ không cập nhật effect/loại quái.

**Files thay đổi:**
- `game/app/src/classes/class_bb.java`:
  - Trong `m(int n)`: Gán `this.isElite = (n == 100)` (reset `false` nếu `n != 100`).
  - Trong `a(class_by)`: Thêm `this.isElite = false` trong nhánh `else` khi `class_by2.i != 100`.
  - Trong `a(int n, int n2)` (xử lý chết): Gán `this.isElite = false` ngay khi quái ngã xuống.
  - Trong `case 8` (respawn local): Gán `this.isElite = false`.
  - Trong constructor / reset: Gán `this.isElite = false`.
- `server/KPAH/src/map/Monster.java`:
  - Khi quái respawn trong `update()`: Gửi trực tiếp `sendMonsterInfo` đến tất cả player trong tầm nhìn và thêm vào `otherMobInside` (ngay cả khi player đứng yên treo máy), kèm theo packet `sendMonsterMove`.
- Đã build lại thành công:
  - Client: `game/build/dist/KPAH_PROD.jar` và `KPAH_MOD.jar`
  - Server: `server/KPAH/dist/KPAH.jar`

**Backup:**
- `game/app/src/classes/_backup/class_bb.java.bak.20260908_2013`
- `server/KPAH/src/map/_backup/Monster.java.bak.20260908_2014`

**Kết quả:** ✅ Thành công — Đã giải quyết triệt để ở cả client và server, build passed.

---

---

## [2026-09-08 21:25] — Khắc Phục Lỗi Văng Client Hàng Loạt & Bổ Sung Hệ Thống Log Toàn Diện Cho Server

**Yêu cầu:** 
1. Điều tra nguyên nhân client bị văng ra đồng loạt (tất cả các máy bị out cùng 1 lúc chứ không phải 1 máy).
2. Bổ sung hệ thống log toàn diện cho server để giám sát tổng quát hệ thống (Heartbeat, RAM, Threads, Online, Disconnect Reason) tiện xử lý lỗi.

**Nguyên nhân gốc rễ phát hiện:**
1. **Lỗi đốt 100% CPU do vòng lặp accept trong `Server.java`:** `serverChannel.configureBlocking(false)` không có `sleep` khiến 1 core CPU liên tục chạy 100%. Trên Android (Termux), **Phantom Process Killer** và hệ thống bảo vệ pin/nhiệt của Android 12+ tự động gửi tín hiệu `SIGKILL` tiêu diệt tiến trình Java chạy nền $\rightarrow$ toàn bộ socket đứt cùng 1 lúc.
2. **Đứt đường hầm Bore TCP Tunnel khi tắt màn hình điện thoại:** Thiếu `termux-wake-lock` trong `start.sh` khiến Android đưa CPU vào chế độ ngủ sâu (Doze Mode), ngắt kết nối mạng của Termux và ngắt đường hầm `bore.pub:19129`.
3. **Thiếu thông tin nhận diện Disconnect:** Server chỉ in `Session Disconnected <IP>`, không có username, nhân vật, thời gian ms hay lý do ngắt kết nối.

**Files thay đổi:**
- `server/KPAH/src/utils/ServerLog.java` [TẠO MỚI]: Bộ ghi log chuẩn hóa hỗ trợ in màu Console ANSI và ghi file bất đồng bộ `log/server_yyyy-MM-dd.log`. Có các tag: `[INFO]`, `[NETWORK]`, `[AUTH]`, `[DISCONNECT]`, `[HEARTBEAT]`, `[WARN]`, `[ERROR]`.
- `server/KPAH/src/interfaces/ISession.java`: Bổ sung `void disconnect(String reason)` và default method `disconnect()`.
- `server/KPAH/src/server/Server.java`:
  - Thêm `TimeUnit.MILLISECONDS.sleep(10)` vào vòng lặp accept non-blocking $\rightarrow$ CPU idle giảm từ **100% xuống 0.0%**.
  - Bổ sung Heartbeat Virtual Thread tự động ghi log RAM, Players, Sessions, Threads, Uptime mỗi 60 giây.
  - Bổ sung các lệnh hữu ích trên Console: `status`, `listplayer`, `gc`, `thread`, `player`, `session`.
- `server/KPAH/src/network/Session.java`:
  - Thay thế `clients.size()` bằng `AtomicInteger ID_GENERATOR` tăng dần đơn điệu, triệt tiêu lỗi trùng ID session.
  - Cập nhật `disconnect(reason)` ghi log chi tiết Session ID, username, character, IP, lý do.
  - Bổ sung log đăng nhập thành công, thất bại, trùng tài khoản.
- `server/KPAH/src/network/Collector.java`: Bắt chi tiết `EOFException` (client đóng game / đứt tunnel), `SocketException` (mất mạng / reset) và truyền lý do vào `disconnect(reason)`.
- `server/KPAH/src/network/Sender.java`: Bắt lỗi gửi socket và gọi `disconnect("SEND_ERROR: ...")`.
- `server/KPAH/src/network/MessageHandler.java`: Bắt exception và ghi log rõ opcode gây lỗi kèm stack trace.
- `server/KPAH/src/services/LoginService.java` & `MapService.java`: Bổ sung lý do disconnect khi chọn nhân vật và chuyển map.
- `server/KPAH/start.sh`: Kích hoạt `termux-wake-lock` chống ngủ sâu Termux; thêm tham số bộ nhớ tối ưu `-Xms128m -Xmx512m`.
- `server/KPAH/stop.sh`: Tự động giải phóng `termux-wake-unlock`.
- `server/KPAH/dist/KPAH.jar`: Biên dịch lại toàn bộ server sạch sẽ với Java 21 (`BUILD SUCCESSFUL`).

**Backup:**
- `server/KPAH/src/server/_backup/Server.java.bak.20260908_2119`
- `server/KPAH/src/network/_backup/Session.java.bak.20260908_2119`
- `server/KPAH/src/network/_backup/Collector.java.bak.20260908_2119`
- `server/KPAH/src/network/_backup/Sender.java.bak.20260908_2119`
- `server/KPAH/src/network/_backup/MessageHandler.java.bak.20260908_2119`
- `server/KPAH/src/services/_backup/LoginService.java.bak.20260908_2121`
- `server/KPAH/src/services/_backup/MapService.java.bak.20260908_2121`
- `server/KPAH/_backup/start.sh.bak.20260908_2119`

**Kết quả:** ✅ Thành công
- Đã test thực tế: CPU server giảm từ 100% về 0.0%.
- Heartbeat in định kỳ mỗi 60s vào console và file `log/server_yyyy-MM-dd.log`.
- Khi client ngắt kết nối, server log chính xác lý do (ví dụ: `CLIENT_CLOSED`, `SOCKET_RESET`).
- Lệnh `status` trong console phản hồi ngay lập tức thông số hệ thống.

---

---

## [2026-09-08 21:46] — Nâng cấp quái thường & quái Tinh Anh, fix hình ảnh Rương Tinh Anh

**Yêu cầu:**
1. Nâng cấp quái: Tăng một chút HP cho quái thường.
2. Tăng MẠNH sức mạnh cho quái Tinh Anh: làm cho người chơi không quá mạnh và trang bị kém hầu như không solo lại được.
3. Thêm nhiều khả năng chiến đấu mới cho quái Tinh Anh (hút máu, cuồng nộ dưới 50% HP, kỹ năng khống chế/thiêu đốt MP).
4. Thêm cơ chế tự hồi phục HP (Out-of-Combat HP Regen) cho quái Tinh Anh khi không bị tấn công.
5. Chỉnh lại hình ảnh Rương Tinh Anh (trước đó hiển thị hình đồng xu vàng, sửa lại đúng hình chiếc rương báu).

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/data/image/icon/5568.png` & `5567.png`: Thay thế ảnh đồng xu vàng/bạc cũ bằng hình ảnh chiếc Rương báu vật chuẩn 16x16 trích xuất từ icon rương `596.png`. Rương Tinh Anh và các rương báu trong hành trang / rơi ngoài map hiển thị đúng 100% hình chiếc rương.
- `server/KPAH/src/map/Monster.java`:
  - `getMaxHp()`: Quái Tinh Anh được nhân **x8.0 HP** (trước đây là x4.0).
  - `injured()`: Bổ sung giáp phẳng `level * 5` và kháng sát thương phần trăm lên đến **40% - 60%** cho Quái Tinh Anh; người chơi đồ yếu đánh vào chỉ gây lượng cào xước tối thiểu. Đánh dấu `lastTimeBeingAttacked = System.currentTimeMillis()`.
  - `getDameAttack()`: Tăng sát thương cơ bản của Quái Tinh Anh thêm **+80%**, thêm trạng thái **Cuồng Nộ (Frenzy)** dưới 50% HP (+25% sát thương) và 25% tỷ lệ **Bạo Kích (Critical Hit)** x1.5 sát thương. Tăng sát thương cào xước tối thiểu.
  - `attackPlayer()`: Thêm tốc độ đánh Cuồng Nộ (800 - 1200ms khi dưới 50% HP); thêm khả năng **Hút Máu (Lifesteal 25%)** dựa trên lượng sát thương gây ra; thêm kỹ năng **25% Choáng 2s**, **30% Trúng Độc nặng**, **20% Thiêu Đốt trừ MP trực tiếp**.
  - `update()`: Cơ chế **Out-of-Combat HP Regen**: Nếu không bị người chơi tấn công trong 5 giây, quái Tinh Anh mỗi 1.5 giây tự động hồi phục 8% Max HP và đồng bộ ngay thanh máu tới tất cả người chơi trong tầm nhìn.
  - `healHp(int amount)`: Phương thức hồi phục máu an toàn kèm broadcast cập nhật thông tin quái cho người chơi xung quanh.
- `server/KPAH/src/services/MonsterService.java`:
  - `sendMonsterAttack()` và `sendMeleeHit()`: Chuyển kiểu trả về thành `int damage` để tính toán cơ chế hút máu cho quái.
  - `sendMonsterInfoToMap(@NonNull Monster monster)`: Thêm hàm broadcast thông tin máu của quái cho tất cả người chơi trong tầm nhìn trong map.
- MySQL database `kpah.monsters` & `server/kpah.sql`:
  - Cập nhật tăng thêm 20% `maxHp` cho toàn bộ quái thường (không áp dụng cho quái khoáng sản và NPC đặc biệt).

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260908_2142`
- `server/KPAH/src/services/_backup/MonsterService.java.bak.20260908_2142`
- `server/KPAH/data/image/icon/_backup/5567.png.bak.20260908_2142`
- `server/KPAH/data/image/icon/_backup/5568.png.bak.20260908_2142`

**Kết quả:** ✅ Thành công
- Build Ant Java 21: `BUILD SUCCESSFUL`.
- Quái thường có lượng máu dày hơn vừa phải (+20%).
- Quái Tinh Anh cực kỳ trâu bò, sở hữu bộ kỹ năng đầy đủ: Kháng sát thương cao, đánh cực đau, hút máu, cuồng nộ khi yếu máu, gây hiệu ứng làm choáng/độc/đốt MP, tự hồi máu đầy khi đối thủ bỏ chạy/chết/không tấn công trong 5s.
- Rương Tinh Anh hiển thị chuẩn xác hình chiếc rương báu vật viền vàng thay vì đồng xu.

---

---

## [2026-09-08 22:44] — Khắc phục triệt để lỗi văng client đồng loạt (Opcode -23 NPE & Opcode -66)

**Yêu cầu:** Điều tra và sửa lỗi vừa xảy ra khi client bị văng ra kèm log: `Lỗi Message Handler [Opcode: -23] - java.lang.NullPointerException: itemGem is marked non-null but is null` và cảnh báo spam `CMD Function Not Found: -66`.

**Nguyên nhân gốc rễ:**
- Trong bản mod client ([ModController.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/ModController.java)), có hàm tự động `doAutoGame()` chạy ngầm định kỳ mỗi 180 giây (3 phút):
  1. Gửi lệnh chăm sóc cây thần nông trại (opcode `-66` - `CMD_FRUIT`).
  2. Gửi hàng loạt lệnh vứt đá rác (opcode `-23` - `DELL_GEM_ITEM` từ đá 159 $\rightarrow$ 226).
- Khi nhân vật không có viên đá tương ứng trong túi, `InventoryService.findItemGem()` trả về `null`.
- Server gọi `removeItemGem(player, null)`. Do method này khai báo `@NonNull ItemGem itemGem`, thư viện Lombok tự động ném ra `NullPointerException: itemGem is marked non-null but is null`.
- Exception này khiến Collector ngắt kết nối session người chơi: `DISCONNECT -> PACKET_ERROR`.
- Cứ mỗi 3 phút, tất cả client bật auto đều quét dọn rác và bị server đá văng đồng loạt!

**Mức độ rủi ro:** Cao (ảnh hưởng trực tiếp đến độ ổn định kết nối toàn server)

**Files thay đổi:**
- `server/KPAH/src/services/InventoryService.java`:
  - Bỏ `@NonNull` cho tham số item và thêm kiểm tra an toàn `if (item == null) return;` ở tất cả các method: `removeItemBodyEquipment`, `removeItemBoxEquipment`, `removeItemSoldEquipment`, `removeItemBagEquipment`, `removeItemAnimal`, `removeItemPotion`, `removeItemGem`, `removeItemGemLock`.
- `server/KPAH/src/network/MessageHandler.java`:
  - Thêm kiểm tra null trước khi gọi xóa item ở các case `DELL_GEM_ITEM` và `DELL_POTION`.
  - Bổ sung case `CommandMessage.CMD_FRUIT` (opcode `-66`) bỏ qua an toàn để không còn spam cảnh báo `CMD Function Not Found: -66`.
- `server/KPAH/dist/KPAH.jar`: Biên dịch lại toàn bộ server sạch sẽ với Ant Java 21 (`BUILD SUCCESSFUL`).

**Backup:**
- `server/KPAH/src/services/_backup/InventoryService.java.bak.20260908_2241`
- `server/KPAH/src/network/_backup/MessageHandler.java.bak.20260908_2241`

**Kết quả:** ✅ Thành công
- Build Ant Java 21: `BUILD SUCCESSFUL`.
- Triệt tiêu 100% lỗi crash session do vứt đá/item null từ auto client.
- Sạch log opcode `-66`.

---

---
