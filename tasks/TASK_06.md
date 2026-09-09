# KPAH Project — Task Log (Phần 06: Hiện Tại)

> **Quy tắc bắt buộc:** Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**. 
> Khi đủ 10 task (đạt task #60), tiến hành lưu trữ file thành `tasks/TASK_06.md` và mở file `TASK.md` mới (không cộng dồn làm file quá lớn).
>
> **Lịch sử các phần trước:**
> - [Phần 01 (Task 1 - 10)](tasks/TASK_01.md)
> - [Phần 02 (Task 11 - 20)](tasks/TASK_02.md)
> - [Phần 03 (Task 21 - 30)](tasks/TASK_03.md)
> - [Phần 04 (Task 31 - 40)](tasks/TASK_04.md)
> - [Phần 05 (Task 41 - 50)](tasks/TASK_05.md)

---

## [2026-09-09 10:54] — Khởi chạy bản Game Public (KPAH_PROD.jar) trên Giả lập MicroEmulator để Test

**Yêu cầu:** Chạy game bản public tại máy tính để test kết nối và gameplay.

**Mức độ rủi ro:** Thấp

**Hành động:**
- Biên dịch cập nhật gói client `KPAH_PROD.jar` và `KPAH_PROD.jad` (kết nối `bore.pub:19129`, server "Dị giới") với Java 8 Ant build.
- Khởi chạy giả lập MicroEmulator trên màn hình (`DISPLAY=:0`) với file `game/build/dist/KPAH_PROD.jar`.
- Ghi nhận console game kết nối socket thành công:
  ```text
  ket noi socket://bore.pub:19129
  send cmd=-1
  send cmd=1
  ```

**Kết quả:** ✅ Thành công (Cửa sổ game bản public đã mở trên màn hình và kết nối thành công tới server public).

---

---

## [2026-09-09 11:01] — Sửa lỗi NoSuchFileException khi gửi danh sách nhân vật (Login CHARLIST)

**Yêu cầu:** Khắc phục lỗi crash đăng nhập: `NoSuchFileException: data/image/weapon/96.png` khiến người chơi bị ngắt kết nối (`LOGIN_EXCEPTION`).

**Nguyên nhân gốc rễ:**
- Nhân vật trang bị vũ khí ID 96 (Bút sắt) hoặc các vũ khí mặc định không có file PNG riêng trong thư mục `data/image/weapon/`.
- Trong `LoginService.java`, code gọi `Util.readFileAndSplit(...)`. Khi file không tồn tại, hàm này trực tiếp gọi `FileChannel.open` ném ra `NoSuchFileException` thay vì trả về `null` để nhánh `if (img == null)` gửi cờ an toàn `-1`.
- Hơn nữa, toàn bộ ảnh vũ khí hợp lệ đã được nạp sẵn vào cache RAM `Manager.IMAGES_WEAPON` khi khởi động server nhưng `LoginService` không tận dụng mà đọc lại từ ổ đĩa mỗi lần đăng nhập.

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/src/utils/Util.java` — Thêm kiểm tra `if (!file.exists() || !file.isFile()) return null;` trong `readFileAndSplit(String url)` để trả về `null` an toàn.
- `server/KPAH/src/services/LoginService.java` — Sử dụng cache `Manager.getImageWeapon((short) weapon.getTemplate().getId())`, fallback `Util.readFileAndSplit`, và nếu `img == null` gửi byte `-1` an toàn cho client.
- `server/KPAH/dist/KPAH.jar` — Biên dịch lại toàn bộ server với Ant Java 21 (`BUILD SUCCESSFUL`).

**Backup:**
- `server/KPAH/src/utils/_backup/Util.java.bak.20260909_1100`
- `server/KPAH/src/services/_backup/LoginService.java.bak.20260909_1100`

**Kết quả:** ✅ Thành công (Đã build passed, triệt tiêu lỗi crash khi đăng nhập với các vũ khí không có file ảnh riêng).

---

---

## [2026-09-09 11:58] — Cập nhật hình ảnh Rương Tinh Anh, Khôi phục Menu NPC (Lâm Tướng Quân & Hắc Ngưu), và Sửa lỗi nhảy con trỏ khi dùng / bán đồ

**Yêu cầu:**
1. Hình ảnh rương tinh anh: Đổi từ hình xu vàng sang hình rương chuẩn, phân chia rương vàng và rương bạc cho 4 cấp rương tinh anh.
2. NPC: Khôi phục "Học kỹ năng" tại Lâm tướng quân, menu "Sửa đồ" / "Luyện đồ" (đập đồ), nghiền bột, khảm, hợp thành tại Thợ rèn Hắc ngưu.
3. Gameplay: Khi dùng 1 vật phẩm trong ô đồ hoặc bán đồ, con trỏ không bị nhảy/target lại lên thanh menu tab trên cùng mà giữ nguyên vị trí ở ô hiện tại, tránh khựng và giật lag thao tác.

**Mức độ rủi ro:** Trung bình

**Hành động & Phân tích nguyên nhân:**
1. **Hình ảnh Rương Tinh Anh:**
   - Trích xuất 2 frame rương kích thước 16x16 từ `server/KPAH/data/image/potion/potion.png` (frame 67 = Rương Vàng, frame 68 = Rương Bạc) dưới dạng ảnh RGBA chuẩn 8-bit/color.
   - Lưu thành `server/KPAH/data/image/icon/5567.png` (Rương Vàng) và `5568.png` (Rương Bạc).
   - Thiết lập trong cơ sở dữ liệu `potion_template`: Bậc 1 & Bậc 2 dùng `idImage = 68` (Rương Bạc), Bậc 3 & Bậc 4 dùng `idImage = 67` (Rương Vàng).
2. **Menu NPC Lâm Tướng Quân & Thợ Rèn Hắc Ngưu:**
   - *Nguyên nhân:* Trước đây trong `class_gn.java:19`, biến `d` bị gán cứng `= 1`, khiến `f_()` luôn trả về 1 cho mọi NPC và bypass toàn bộ menu tương tác nội bộ của client để gửi thẳng opcode 23 lên server.
   - *Khắc phục:*
     - Cập nhật `class_gn.java:f_()`: trả về `0` cho NPC 2 (Hắc Ngưu), 28 (Kiếm Sư) và 21 (Lâm Tướng Quân).
     - Tạo `class_np.java`: Khi người chơi bấm "Nói chuyện" với Lâm tướng quân (NPC 21), tự động gọi `class_go.a().l((short)21)` gửi lên server để nhận/trả nhiệm vụ, đồng thời giữ nguyên chức năng "Học kỹ năng" mở bảng nâng skill gốc của game (`class_nn`).
     - Với Thợ rèn Hắc Ngưu (NPC 2/28), client tự động hiển thị đầy đủ menu gốc: "Mua bán", "Nghiền bột", "Thêm dòng", "Luyện đồ" (đập đồ), "Luyện đồ tự động", "Cộng thuộc tính", "Khóa đồ thú", "Khóa trang bị", "Sửa đồ", "Đục lỗ", "Khảm", "Hợp thành".
3. **Gameplay / Giữ nguyên vị trí con trỏ trong túi đồ:**
   - *Nguyên nhân:* Khi dùng potion/item hoặc bán trang bị, server gửi packet cập nhật hành trang (`CHAR_INVENTORY` hoặc `ITEM_POTION`). Client khi nhận packet đã gọi `class_wc.b(0)` khiến `class_nu.d()` đặt `this.r = true` (nhảy lên menu tab bar) và `class_nu.a()` đặt `this.f = 0` (nhảy về ô 0).
   - *Khắc phục:*
     - Trong `game/app/src/classes/class_wc.java:b(int n)`: Lưu trạng thái `wasInGrid`, `oldSlot`, `oldW`, `oldPage` trước khi làm mới; nếu người chơi đang ở trong lưới ô đồ, tự động khôi phục `nu.r = false`, `class_nu.w = oldW`, `nu.W = oldPage`, clamp `nu.f` hợp lệ và gọi `nu.n()` để cập nhật action phím chọn.
     - Trong `game/app/src/classes/class_vr.java`: Sau khi dùng item, giữ nguyên `this.a.r = false`, cập nhật `this.a.f` và gọi `this.a.n()`.
     - Trong `server/KPAH/src/services/ShopService.java`: Loại bỏ lệnh `sendItemBag` dư thừa trong `onSellItem` vì gói tin `SELL_ITEM` (opcode 28) đã đồng bộ số lượng ở client mà không cần reload toàn bộ giỏ hàng.

**Files thay đổi:**
- `server/KPAH/data/image/icon/5567.png` — Icon Rương Tinh Anh Vàng (Bậc 3, 4)
- `server/KPAH/data/image/icon/5568.png` — Icon Rương Tinh Anh Bạc (Bậc 1, 2)
- `game/app/src/classes/class_gn.java` — Mở lại tương tác menu client cho NPC 2, 28, 21
- `game/app/src/classes/class_np.java` — Xử lý đối thoại quest server cho Lâm Tướng Quân
- `game/app/src/classes/class_wc.java` — Giữ nguyên vị trí con trỏ ô và grid focus trong `b()`
- `game/app/src/classes/class_vr.java` — Giữ nguyên con trỏ khi dùng potion/item
- `server/KPAH/src/services/ShopService.java` — Tối ưu hóa gửi gói tin bán trang bị

**Backup:**
- `game/app/src/classes/_backup/class_gn.java.bak.*`
- `game/app/src/classes/_backup/class_wc.java.bak.*`
- `game/app/src/classes/_backup/class_vr.java.bak.*`
- `server/KPAH/src/services/_backup/ShopService.java.bak.*`

**Kết quả:** ✅ Thành công
- Đã biên dịch server `KPAH.jar` (`BUILD SUCCESSFUL`).
- Đã biên dịch client `KPAH_PROD.jar` và `KPAH_MOD.jar` (`BUILD SUCCESSFUL`).
- Dọn dẹp sạch sẽ các file tạm `_tmp/`.

---

---

## [2026-09-09 12:06] — Cấu hình Build Game Client Đặt Tên Kèm Phiên Bản Chuẩn (`kpah_mod_v1.0.0.1.jar`)

**Yêu cầu:** Build file JAR của game với tên kèm số phiên bản theo chuẩn phiên bản chỉnh sửa (`kpah_mod_v1.0.0.1`).

**Mức độ rủi ro:** Thấp

**Hành động:**
1. Cập nhật `game/app/res/META-INF/MANIFEST.MF`:
   - Thiết lập `MIDlet-Version: 1.0.0.1`.
2. Cập nhật `game/build.xml`:
   - Thêm thuộc tính `<property name="version" value="1.0.0.1"/>` và đặt tên mặc định `${app.name}` theo `kpah_mod_v${version}`. Hỗ trợ ghi đè phiên bản qua command line `-Dversion=...`.
   - Cập nhật target `dist-prod`: Tạo ra file `build/dist/kpah_mod_v${version}.jar` và `build/dist/kpah_mod_v${version}.jad` (đồng thời sao chép alias `KPAH_PROD.jar` để giữ tương thích ngược).
   - Cập nhật target `dist-local`: Tạo ra `build/dist/kpah_mod_v${version}_local.jar` và `build/dist/kpah_mod_v${version}_local.jad` (kèm alias `KPAH_MOD.jar`).
   - Cập nhật các target chạy giả lập `run` và `run-local` trỏ đúng vào các file jar có phiên bản mới.
3. Tiến hành biên dịch bằng Java 8 Ant:
   - `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ant dist-prod` $\rightarrow$ `kpah_mod_v1.0.0.1.jar` (1,173,896 bytes).
   - `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ant dist-local` $\rightarrow$ `kpah_mod_v1.0.0.1_local.jar` (1,173,891 bytes).

**Files thay đổi:**
- `game/app/res/META-INF/MANIFEST.MF` — Cập nhật `MIDlet-Version: 1.0.0.1`
- `game/build.xml` — Thêm cấu hình thuộc tính `version` và các target build có tên phiên bản chuẩn
- `game/build/dist/kpah_mod_v1.0.0.1.jar` — File JAR bản Public có phiên bản
- `game/build/dist/kpah_mod_v1.0.0.1.jad` — File JAD bản Public có phiên bản
- `game/build/dist/kpah_mod_v1.0.0.1_local.jar` — File JAR bản Localhost có phiên bản
- `game/build/dist/kpah_mod_v1.0.0.1_local.jad` — File JAD bản Localhost có phiên bản

**Backup:**
- `game/_backup/build.xml.bak.20260909_1205`
- `game/_backup/MANIFEST.MF.bak.20260909_1205`

**Kết quả:** ✅ Thành công (`BUILD SUCCESSFUL`)

---

---

## [2026-09-09 13:42] — Nâng cấp EXP quái, sửa mua bán potion Bà Tám, HUD hiển thị buff & exp, sửa kỹ năng Pháp Sư, sửa nón nhân vật

**Yêu cầu:**
1. Tăng mạnh base EXP quái vật; bonus EXP chỉ nhận khi dùng thẻ/vật phẩm hoặc mang trang bị tăng EXP.
2. Sửa lỗi mua potion Bà Tám (tiền trừ không đúng số lượng, vật phẩm không cộng vào); sửa logic tiền tệ xa phu, sửa đồ, học kỹ năng không trừ/không đồng bộ tiền.
3. Bổ sung thông tin dưới Độ bền & Tọa độ trên HUD: thời gian/tỷ lệ của thuộc tính tăng EXP và danh sách hiệu ứng buff kèm thời gian còn lại.
4. Sửa cấp độ học & tăng điểm kỹ năng phái Pháp Sư (kỹ năng cấp 30 và cấp 6 bị đảo lộn).
5. Sửa lỗi nhân vật chính không hiển thị nón (người khác nhìn thấy nhưng tự nhìn không thấy).

**Mức độ rủi ro:** Cao

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java` — Nâng cấp công thức `baseExp` cho quái vật mọi cấp độ.
- `server/KPAH/src/network/MessageHandler.java` — Cho phép `id >= 0` khi mua đồ từ shop NPC (chấp nhận potion ID 0).
- `server/KPAH/src/services/ShopService.java` — Xử lý mua potion đúng giá, cộng vào hành trang và gọi `sendItemPotion`.
- `server/KPAH/src/services/ChangeMapService.java` — Thêm `sendItemPotion` sau khi trừ xu đi xa phu.
- `server/KPAH/src/services/MenuOptionService.java` — Trừ 50 xu khi đi xa phu mới (`XA_PHU_NEW`) và gọi `sendItemPotion`.
- `server/KPAH/src/services/InventoryService.java` — Gọi `sendItemPotion` sau khi trừ xu sửa đồ.
- `server/KPAH/src/manager/Manager.java` — Thêm `getLevelAddSkill(byte clazz, int idSkill, int lvSkill)` chuẩn hóa cấp độ Pháp Sư.
- `server/KPAH/src/player/Point.java` — Truyền class nhân vật khi tăng điểm kỹ năng.
- `server/KPAH/src/services/SkillService.java` — Kiểm tra level trước khi trừ xu học skill mới, đồng bộ tiền và gửi `LEVEL_ADD_SKILL` chuẩn theo phái.
- `server/KPAH/src/services/Service.java` — Gửi `writeBoolean(true)` (paint hat) trong `sendMainCharInfo`.
- `game/app/src/classes/class_rs.java` — Tạo mới để tính đúng tiền theo số lượng, gửi gói mua potion tức thì và xóa giỏ hàng tạm.
- `game/app/src/classes/MainCharInfo.java` — Thêm `getExpBonusInfo()` và `getActiveBuffStrings()` lấy thông tin thuộc tính tăng EXP và các buff đang hoạt động.
- `game/app/src/classes/Paint.java` — Vẽ thông tin tăng EXP và danh sách buff kèm thời gian còn lại dưới Tọa độ.

**Backup:**
- `server/KPAH/src/_backup/Monster.java.bak.*`
- `server/KPAH/src/_backup/MessageHandler.java.bak.*`
- `server/KPAH/src/_backup/ShopService.java.bak.*`
- `server/KPAH/src/_backup/SkillService.java.bak.*`
- `server/KPAH/src/_backup/Manager.java.bak.*`
- `server/KPAH/src/_backup/Point.java.bak.*`
- `server/KPAH/src/_backup/Service.java.bak.*`
- `game/app/src/classes/_backup/Paint.java.bak.*`

**Kết quả:** ✅ Thành công — Cả Server và Client (`kpah_mod_v1.0.0.1.jar`) đều đã được build thành công không có lỗi.

---

---

## [2026-09-09 14:28] — Cân bằng hồi phục HP/MP (HP > MP), chuẩn hóa icon Thẻ mua bán & Lọ Luyện Kinh Dược, và cơ chế tự sửa vũ khí

**Yêu cầu:**
1. Tăng mạnh lượng hồi phục của các loại dược phẩm HP và MP, trong đó HP hồi nhiều hơn MP ở tất cả các bậc theo yêu cầu.
2. Chuẩn hóa hình ảnh: Frame 39 là Thẻ mua bán (`idImage = 39`, item `id = 33`), Frame 40 là Lọ Luyện Kinh Dược (Lọ Tiên cộng EXP, `idImage = 40`, item `id = 108, 109, 110, 111`).
3. Cơ chế tự động sửa chữa vũ khí và cuốc: Khi độ bền vũ khí/cuốc về 0, nếu người chơi sở hữu Thẻ mua bán trong hành trang, hệ thống tự động trừ xu sửa chữa, phục hồi đầy đủ độ bền, đồng bộ ngay lập tức sang client và thông báo chatonly. Nếu không có thẻ hoặc không đủ xu, chặn tấn công và yêu cầu đến thợ rèn. Cho phép dùng trực tiếp Thẻ mua bán từ hành trang để sửa chữa toàn bộ trang bị.
4. Đồng bộ hiển thị độ bền trên HUD: Khắc phục điều kiện phụ thuộc `v > 0`, hiển thị chính xác độ bền vũ khí và cảnh báo đỏ `Độ bền: 0 (Hỏng)` khi vũ khí bị hư hỏng.

**Mức độ rủi ro:** Trung bình

**Chi tiết thông số Dược phẩm sau khi cân bằng (HP > MP):**
- **HP nhỏ (id 1):** 80 $\rightarrow$ **500 HP** | **MP nhỏ (id 4):** 160 $\rightarrow$ **300 MP**
- **HP vừa (id 2):** 300 $\rightarrow$ **1.500 HP** | **MP vừa (id 5):** 600 $\rightarrow$ **1.000 MP**
- **HP to (id 3):** 1.000 $\rightarrow$ **4.000 HP** | **MP to (id 6):** 2.000 $\rightarrow$ **2.500 MP**
- **HP đ.biệt vừa (id 21):** 1.500 $\rightarrow$ **3.000 HP** (tức thì) | **MP đ.biệt vừa (id 23):** 2.500 $\rightarrow$ **2.000 MP** (tức thì)
- **HP đ.biệt to (id 22):** 3.000 $\rightarrow$ **8.000 HP** (tức thì) | **MP đ.biệt to (id 24):** 3.500 $\rightarrow$ **5.000 MP** (tức thì)
- **HP cao cấp (id 93):** 7.000 $\rightarrow$ **15.000 HP** | **MP cao cấp (id 95):** 7.000 $\rightarrow$ **10.000 MP**
- **HP siêu cấp (id 94):** 15.000 $\rightarrow$ **30.000 HP** | **MP siêu cấp (id 96):** 15.000 $\rightarrow$ **20.000 MP**

**Files & Database thay đổi:**
- `Database MariaDB`:
  - `others`: Cập nhật cấu hình JSON `VALUE_MP_HP` gửi tới client và server.
  - `potion_template`: Cập nhật `recovered` và mô tả đa dòng `name` cho toàn bộ 14 loại dược phẩm HP/MP; thiết lập `idImage = 40` cho cả 4 cấp Lọ Luyện Kinh Dược (id 108..111); thiết lập `name` cho Thẻ mua bán (id 33); cập nhật mô tả Rương Tinh Anh (id 106, 160, 161, 162) và Tinh Anh Đan (id 107).
  - `shop_template`: Cập nhật shop item 13 thành 'Thẻ mua bán'.
- `server/KPAH/src/services/SkillService.java` — Thêm `checkWeaponUsable` và `checkCuocUsable`: tự động kiểm tra Thẻ mua bán (id 33), trừ xu và khôi phục độ bền, gửi packet đồng bộ `sendItemBody` + `sendItemPotion` sang client; chặn tấn công và nhắc nhở qua chatonly khi hỏng.
- `server/KPAH/src/services/UseItemService.java` — Bổ sung `case 33`: kích hoạt sửa nhanh toàn bộ trang bị khi bấm sử dụng Thẻ mua bán trong hành trang; làm sạch tên hiển thị phần thưởng trong `openEliteChest`.
- `server/KPAH/src/manager/Manager.java` — Bảo vệ nạp dữ liệu `data/image/icon`: lọc file an toàn và xử lý ngoại lệ tránh crash khi có thư mục hoặc file phi số.
- `game/app/src/classes/MainCharInfo.java` — Cập nhật `getDoBen()`: lấy trực tiếp độ bền `u` của vũ khí, loại bỏ phụ thuộc vào `v > 0`.
- `game/app/src/classes/Paint.java` — Hiển thị chữ đỏ `Độ bền: 0 (Hỏng)` trên HUD khi vũ khí bị hư hỏng.

**Backup:**
- `server/KPAH/src/services/_backup/SkillService.java.bak.20260909_1425`
- `server/KPAH/src/services/_backup/UseItemService.java.bak.20260909_1425`
- `server/KPAH/src/manager/_backup/Manager.java.bak.20260909_1428`
- `game/app/src/classes/_backup/MainCharInfo.java.bak.20260909_1425`
- `game/app/src/classes/_backup/Paint.java.bak.20260909_1425`

**Kết quả:** ✅ Thành công
- Đã kiểm tra biên dịch cả Server (`KPAH.jar`) với Java 21 và Client (`kpah_mod_v1.0.0.1.jar`) với Java 8 không có lỗi.
- Server đã được khởi động và lắng nghe trên port 19129.
- Đã dọn dẹp toàn bộ file tạm trong `/tmp/`.

---

---

## [2026-09-09 14:35] — Đổi tên "Lọ Luyện Kinh Dược" thành "Tinh anh huyết", tăng gấp 10 lần EXP, và cung cấp đường dẫn hình ảnh

**Yêu cầu:**
1. Đổi tên vật phẩm "Lọ Luyện Kinh Dược" thành "Tinh anh huyết".
2. Tăng gấp 10 lần lượng kinh nghiệm (EXP) nhận được khi sử dụng:
   - Sơ Cấp (ID 108): 3.500 $\rightarrow$ **35.000 EXP**
   - Trung Cấp (ID 109): 25.000 $\rightarrow$ **250.000 EXP**
   - Cao Cấp (ID 110): 90.000 $\rightarrow$ **900.000 EXP**
   - Siêu Cấp (ID 111): 220.000 $\rightarrow$ **2.200.000 EXP**
3. Cung cấp đường dẫn chi tiết từng hình ảnh của item để người dùng kiểm chứng không gán sai.

**Mức độ rủi ro:** Thấp

**Files & Database thay đổi:**
- `Database MariaDB`:
  - `potion_template`: Cập nhật tên và mô tả mới cho 4 bậc ID 108..111 thành `Tinh anh huyết (Sơ Cấp/Trung Cấp/Cao Cấp/Siêu Cấp)`.
  - `potion_template`: Cập nhật mô tả mở thưởng của Rương Tinh Anh (ID 106, 160, 161, 162) ghi nhận "Tinh anh huyết".
- `server/KPAH/src/services/UseItemService.java`:
  - `useExpPotion()`: Tăng x10 lượng kinh nghiệm cho các ID 108 (35.000), 109 (250.000), 110 (900.000), 111 (2.200.000).
  - Cập nhật chú thích và text thông báo.

**Kết quả:** ✅ Thành công
- Server Java 21 biên dịch hoàn tất (`BUILD SUCCESSFUL`) và daemon server đang chạy lắng nghe cổng 19129.
- Dọn dẹp sạch sẽ các file tạm trong `/tmp/`.

## [2026-09-09 15:00] — Khắc phục 7 lỗi logic & nâng cao độ ổn định: Sửa đồ, NPE, âm độ bền, chống flood packet, mua potion và nạp đồ DB

**Yêu cầu:**
1. Phân chia `TASK.md` theo quy định mới (mỗi file tối đa 10 task, lưu trữ vào `tasks/TASK_XX.md`).
2. Sửa lỗi `getPriceRepair` trong `Inventory.java`: chỉ tính phí sửa các món đồ bị giảm độ bền (`durable < maxDurable`).
3. Sửa lỗi vũ khí/cuốc giá thấp (0 xu) không thể tự sửa và rate-limit cảnh báo vũ khí hỏng trong `SkillService.java`.
4. Khắc phục NullPointerException khi sửa vũ khí ở Thợ rèn trong `InventoryService.java` và vòng lặp `UseItemService.java`.
5. Chặn độ bền tụt xuống âm trong `ItemEquip.java`.
6. Chống spam phím 30 FPS và flood packet khi vũ khí hỏng trong `ModController.java`.
7. Cho phép mua cộng dồn Potion khi hành trang đầy trong `ShopService.java`.
8. Phòng ngừa NPE khi nạp trang bị template không tồn tại trong `PlayerDAO.java`.

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `TASK.md`: Phân chia 50 task lịch sử thành `tasks/TASK_01.md` đến `tasks/TASK_05.md`.
- `GEMINI.md`: Cập nhật quy định mỗi file task tối đa 10 task.
- `server/KPAH/src/player/Inventory.java`: `getPriceRepair()` chỉ tính phí cho đồ có `durable < maxDurable`.
- `server/KPAH/src/player/Sundry.java`: Thêm `lastTimeWarnBrokenWeapon`, `lastTimeWarnNoWeapon`.
- `server/KPAH/src/services/SkillService.java`: `checkWeaponUsable` và `checkCuocUsable` xử lý `price <= 0 || minusXu(price)` và rate-limit thông báo chat.
- `server/KPAH/src/services/InventoryService.java`: Kiểm tra `price <= 0` và check null `weapon`.
- `server/KPAH/src/services/UseItemService.java`: Check null trang bị trong vòng lặp case 33.
- `server/KPAH/src/item/ItemEquip.java`: `minusDurable()` chặn cận dưới 0.
- `server/KPAH/src/services/ShopService.java`: `canStack` cho phép mua Potion khi túi đầy nếu đã có loại potion đó trong người.
- `server/KPAH/src/daos/PlayerDAO.java`: `loadDataItemEquipment` kiểm tra `template == null` thì continue.
- `game/app/src/classes/ModController.java`: `triggerAutoAttack` kiểm tra `getDoBen() <= 0` thì giãn nhịp 2.5s/hit để chống spam và flood packet.

**Backup:**
- `server/KPAH/src/player/_backup/Inventory.java.bak.*`
- `server/KPAH/src/player/_backup/Sundry.java.bak.*`
- `server/KPAH/src/services/_backup/SkillService.java.bak.*`
- `server/KPAH/src/services/_backup/InventoryService.java.bak.*`
- `server/KPAH/src/services/_backup/UseItemService.java.bak.*`
- `server/KPAH/src/item/_backup/ItemEquip.java.bak.*`
- `server/KPAH/src/services/_backup/ShopService.java.bak.*`
- `server/KPAH/src/daos/_backup/PlayerDAO.java.bak.*`
- `game/app/src/classes/_backup/ModController.java.bak.*`

**Kết quả:** ✅ Thành công
- Đã phân chia sạch sẽ 50 task trước vào `tasks/TASK_01.md` đến `tasks/TASK_05.md`.
- Cả Server (`KPAH.jar`) và Client (`kpah_mod_v1.0.0.1.jar`) đều đã được build thành công 100%.
- Server daemon đã khởi động lại và lắng nghe trên port 19129 (`task-3456`).

---

## [2026-09-09 15:20] — Tối ưu hóa toàn diện: Level Up Party/Item, Trưởng nhóm Kick, Quái vật Targeting, Đồ rớt và Giao dịch

**Yêu cầu:** Khắc phục 8 vấn đề cốt lõi về gameplay và logic server sau quá trình rà soát chuyên sâu:
1. Thành viên Party không lên cấp khi nhận EXP chia sẻ từ đồng đội: Xây dựng hàm `MapService.checkLevelUp(Player)` dùng chung cho toàn bộ logic thăng cấp.
2. Trưởng nhóm tự kick chính mình: Sửa lỗi `removeMember(leader)` thành `removeMember(playerKicked)` trong `PartyService.java`.
3. Trùng ID vật phẩm rơi trên mặt đất: Loại bỏ `countItemAppeaerd--;` trong `Zone.removeItem` để ngăn tràn va chạm sequence ID.
4. Mất hiển thị Potion và kẹt giao diện khi hủy giao dịch: Gửi lại gói tin `sendItemPotion` và `sendCancelTrade` cho cả hai người chơi trong `TradeService.cancelTrade`.
5. Chống crash ArrayIndexOutOfBoundsException và tràn cấp max level trong `Util.java` (`getExp` và `getPercentExp`).
6. AI quái vật chọn mục tiêu gần nhất thay vì luôn nhắm người cuối danh sách, đồng thời bọc `synchronized (zone)` chống `ConcurrentModificationException`.
7. Rải đều tọa độ vật phẩm rơi từ quái (`scatterX`, `scatterY` $\pm 12$px) tránh đè khít lên cùng một pixel.
8. Bổ sung thông báo chat phản hồi cho người chơi khi bật/tắt `autoloot on/off` trong `ChatService.java`.

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/src/services/MapService.java` — Bổ sung `checkLevelUp(Player)` chuẩn hóa thăng cấp, cộng chỉ số và đồng bộ thông tin.
- `server/KPAH/src/map/Monster.java` — Đồng bộ kiểm tra lên cấp cho thành viên party; AI quái nhắm mục tiêu người chơi gần nhất có thread-safe; rải tọa độ vật phẩm rơi.
- `server/KPAH/src/services/UseItemService.java` — Sử dụng `checkLevelUp` chung khi cắn bình kinh nghiệm.
- `server/KPAH/src/services/PartyService.java` — Sửa `kickMember` xóa đúng `playerKicked`.
- `server/KPAH/src/map/Zone.java` — Giữ sequence ID `countItemAppeaerd` đơn điệu tăng dần.
- `server/KPAH/src/services/TradeService.java` — Cập nhật `cancelTrade` gửi hoàn trả potion và đóng cửa sổ cho cả 2 bên.
- `server/KPAH/src/utils/Util.java` — Xử lý an toàn `getExp`, `getPercentExp`, bổ sung `getDistance(Monster, Player)`.
- `server/KPAH/src/services/ChatService.java` — Gửi thông báo chat hiển thị trạng thái khi gõ `autoloot on` / `autoloot off`.

**Backup:**
- `server/KPAH/src/services/_backup/MapService.java.bak.*`
- `server/KPAH/src/map/_backup/Monster.java.bak.*`
- `server/KPAH/src/services/_backup/UseItemService.java.bak.*`
- `server/KPAH/src/services/_backup/PartyService.java.bak.*`
- `server/KPAH/src/map/_backup/Zone.java.bak.*`
- `server/KPAH/src/services/_backup/TradeService.java.bak.*`
- `server/KPAH/src/utils/_backup/Util.java.bak.*`
- `server/KPAH/src/services/_backup/ChatService.java.bak.*`

**Kết quả:** ✅ Thành công
- Server build thành công 100% với Java 21 Ant (`dist/KPAH.jar`).
- Daemon Server đang chạy ổn định tại port 19129 (`task-3668`).

---
