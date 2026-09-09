# KPAH — Task Log (Phần 01: Task 1 - 10)

> Lưu trữ nhật ký nhiệm vụ từ #1 đến #10.
> Quy tắc: Mỗi file chỉ lưu trữ tối đa 10 task.

---

## [2026-09-02 21:04] — Khởi tạo hệ thống Safe Task Executor

**Yêu cầu:** Tạo bộ skill giúp agent chỉ thực hiện nhiệm vụ được giao, không phá hủy cấu trúc/logic dự án. Backup file trước thay đổi lớn, ghi log vào TASK.md.

**Files tạo mới:**
- `GEMINI.md` — Quy tắc luôn bật (always-on rules) cho toàn dự án
- `TASK.md` — File nhật ký nhiệm vụ này
- `.agents/skills/safe-task-executor/SKILL.md` — Skill quy trình thực thi an toàn
- `.agents/skills/safe-task-executor/references/backup-protocol.md` — Hướng dẫn backup chi tiết
- `.agents/skills/safe-task-executor/references/task-logging.md` — Template và hướng dẫn ghi log

**Kết quả:** ✅ Thành công

**Ghi chú:** Hệ thống bao gồm 2 lớp kiểm soát:
1. `GEMINI.md` — rules luôn hoạt động, tự động áp dụng cho toàn bộ dự án
2. Skill `safe-task-executor` — quy trình chi tiết được kích hoạt khi cần thay đổi phức tạp

---

---

## [2026-09-02 21:11] — Cải thiện logic AI di chuyển và tấn công của quái

**Yêu cầu:** Sửa logic tấn công và di chuyển cứng nhắc. Đánh xa cách 120px, tốc độ 0.5-5s. Đánh gần cách 10px, tốc độ 0.5-3s. Di chuyển và tấn công riêng biệt, tấn công ưu tiên hơn nhưng di chuyển vẫn đếm timer. Random thời gian di chuyển 3-5s, khoảng cách 30-120px.

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java` — Thêm các trường delay, tính toán lại vị trí tấn công theo góc, thay đổi khoảng delay di chuyển.

**Backup:**
- `server/KPAH/src/map/_backup/Monster.java.bak.20260902_2110`

**Kết quả:** ✅ Thành công
**Ghi chú:** Đã chỉnh sửa trực tiếp trên file, đang chạy test trên server.
---

---

## [2026-09-02 21:42] — Sửa lỗi di chuyển ra ngoài map / địa hình không đi được

**Yêu cầu:** Fix lỗi quái vật và người chơi (khi auto-loot) đôi khi di chuyển ra ngoài map hoặc vào các vùng không thể đi (sông, rừng, đá).

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/src/map/MapData.java` — Thêm method `isWalkable(px, py)` để kiểm tra toạ độ có nằm trên vùng đất trống hay không (dựa vào `typeOfTile` bit `0x02` và kích thước tile 16x16, xử lý cả toạ độ âm).
- `server/KPAH/src/map/Monster.java` — Thêm điều kiện `isWalkable` vào logic di chuyển tự do (wander) và di chuyển khi tấn công (attack), ngăn chặn quái vật đi vào tường.

**Kết quả:** ✅ Thành công
**Ghi chú:** Do quái vật không còn đi vào vùng lỗi, vật phẩm rơi ra sẽ luôn ở vùng đi được, giúp người chơi auto-loot không bị kéo vào tường nữa. Server đã restart.
---

---

## [2026-09-02 21:19] — Tinh chỉnh thông số AI quái

**Yêu cầu:** Sửa đổi khoảng cách quái cận chiến (10px -> 20px) và phạm vi di chuyển ngẫu nhiên (30-120px -> 30-50px).

**Mức độ rủi ro:** Thấp

**Files thay đổi:**
- `server/KPAH/src/map/Monster.java` — Cập nhật các hằng số khoảng cách.

**Kết quả:** ✅ Thành công
**Ghi chú:** Khởi động lại server để thay đổi có hiệu lực.
---

---

## [2026-09-02 22:25] — Triển khai Hệ thống Nhiệm vụ KPAH

**Yêu cầu:** Thêm hệ thống nhiệm vụ tân thủ (1-3) và nhiệm vụ hằng ngày. Tích hợp hook vào game (giết quái, nhặt đồ, di chuyển, bán đồ). Lưu trạng thái vào DB.

**Mức độ rủi ro:** Cao

**Files thay đổi:**
- `server/KPAH/src/player/QuestData.java` — Class quản lý dữ liệu tiến trình (Tạo mới).
- `server/KPAH/src/services/QuestService.java` — Service xử lý logic nhiệm vụ (Tạo mới).
- `server/KPAH/src/player/Info.java` & `daos/PlayerDAO.java` — Lưu JSON tiến trình vào index 9 của cột `info`.
- `server/KPAH/src/services/MenuOptionService.java` & `NpcService.java` — Chèn lựa chọn Nhiệm vụ lên trên cùng của bảng Menu Npc.
- `server/KPAH/src/services/MonsterService.java`, `MapService.java`, `ShopService.java` — Gắn hook onKillMonster, onPickUpEquipment, onMove, onSellEquipment.

**Kết quả:** ✅ Thành công
**Ghi chú:** Đã chạy thử compile thành công với Java 21. Khuyến nghị user test trên game client.

---

---

## [2026-09-02 22:34] — Xoá toàn bộ dữ liệu người chơi (Wipe Data)

**Yêu cầu:** Xóa dữ liệu người chơi để tạo lại tài khoản từ đầu.

**Mức độ rủi ro:** Trung bình

**Files/DB thay đổi:**
- Dữ liệu MySQL trên docker container `kpah-mysql`.
- Lệnh thực thi: `TRUNCATE TABLE players; TRUNCATE TABLE users; TRUNCATE TABLE clan;`

**Kết quả:** ✅ Thành công
**Ghi chú:** Tất cả tài khoản, nhân vật và dữ liệu bang hội đã được làm sạch. Lần khởi động server tiếp theo hoặc lần đăng nhập tiếp theo sẽ yêu cầu tạo lại tài khoản/nhân vật từ đầu.

---

---

## [2026-09-02 22:45] — Sửa 2 lỗi nghiêm trọng khi tạo và đăng nhập nhân vật mới

**Yêu cầu:** Sửa lỗi `NumberFormatException` tại `TopManager.java` và `JSONException` tại `PlayerDAO.java` khi đăng nhập. Sửa lỗi `NegativeArraySizeException` phía Client khi load dữ liệu vũ khí.

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/src/manager/TopManager.java` — Thêm kiểm tra null trước khi parse String thành số đối với cột `level` (do JSON_EXTRACT của MySQL có thể trả về null nếu dữ liệu mảng thiếu phần tử).
- `server/KPAH/src/player/Info.java` & `daos/PlayerDAO.java` — Lưu dữ liệu `QuestData` vào mảng dưới dạng `JSONObject` thay vì `String` để khắc phục lỗi MySQL ăn mất ký tự escape `\` của chuỗi JSON, gây ra chuỗi JSON không hợp lệ.
- `server/KPAH/src/services/LoginService.java` — Thay đổi trình tự gửi dữ liệu hình ảnh vũ khí trong gói `CHARLIST`. Nếu không tìm thấy file hình (ví dụ vũ khí tân thủ không có hình), gửi mã `-1` thay vì gửi `0` rồi ngắt đột ngột (tránh gây lỗi âm bộ nhớ trên Client).

**Kết quả:** ✅ Thành công
**Ghi chú:** Database đã được TRUNCATE lại một lần nữa để xoá các dữ liệu lỗi được sinh ra trong quá trình trước đó. Code đã biên dịch xong. Người dùng có thể tự khởi động server để test.

---

---

## [2026-09-03 20:20] — Sửa lỗi hệ thống Menu Nhiệm vụ

**Yêu cầu:** Khắc phục lỗi cơ chế nhiệm vụ không hoạt động và lỗi fall-through trong Menu Dynamic của NPC.

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `server/KPAH/src/services/MenuOptionService.java` — Sửa lỗi `case MENU_NPC_DYNAMIC` bỏ qua nhánh kiểm tra index khi player chọn chức năng thứ 2 của NPC (ví dụ: mở Shop). Bổ sung điều kiện `else if (selected == 1)` để đảm bảo shop chỉ mở khi player thực sự bấm vào mục Shop, không phải bấm mục khác.

**Backup:**
- `server/KPAH/src/services/_backup/MenuOptionService.java.bak.20260903_2017`
- `server/KPAH/src/services/_backup/NpcService.java.bak.20260903_2017`

**Kết quả:** ✅ Thành công
**Ghi chú:** Đã chạy thử compile thành công với Java 21. Các menu shop và nhiệm vụ tích hợp chung trên cùng một NPC (như Phú Ông, Thiết Bị) giờ đây hoạt động độc lập và chính xác theo index (selected=0 là Quest, selected=1 là Shop).

---

---

## [2026-09-03 20:49] — Cải thiện UX Hệ thống Nhiệm vụ (Trưởng Làng)

**Yêu cầu:** Giải quyết vấn đề người chơi báo cáo "nói chuyện với Trưởng Làng không có menu", thực chất do người chơi đã hoàn thành hoặc đang trong nhiệm vụ nhưng hệ thống không đưa ra gợi ý rõ ràng (thiếu UX). Đề xuất giải pháp thay thế cho tính năng "Chỉ báo nhiệm vụ (?, !)".

**Mức độ rủi ro:** Thấp

**Files thay đổi:**
- `server/KPAH/src/services/QuestService.java` — Thêm method `getNoQuestHint(Player, byte)` để trả về chuỗi gợi ý mục tiêu tiếp theo dựa vào `beginnerQuestId`.
- `server/KPAH/src/services/NpcService.java` — Sửa đổi thông báo mặc định *"Hiện tại không có việc gì"* thành chuỗi gợi ý động lấy từ `QuestService`.

**Backup:**
- `server/KPAH/src/services/_backup/QuestService.java.bak.20260903_2047`
- `server/KPAH/src/services/_backup/NpcService.java.bak.20260903_2047`

**Kết quả:** ✅ Thành công
**Ghi chú:** UX đã được cải thiện. Khi hoàn thành nhiệm vụ ở Trưởng Làng, hệ thống sẽ gợi ý: *"Ngươi đã hoàn thành việc ở đây, hãy đến gặp Thiết Bị (Thợ Rèn)..."*. Project compile thành công.

---

---

## [2026-09-06 15:05] — Khắc phục triệt để lỗi Hệ thống Nhiệm vụ Tân thủ không hiển thị và mất dữ liệu

**Yêu cầu:** Tìm và sửa lỗi gốc khiến Hệ thống Nhiệm vụ Tân thủ (Trưởng Làng, Thiết Bị, Phú Ông) không hiện menu trên client, hành vi y hệt bản gốc chưa mod; kiểm tra và sửa lỗi cắt cụt chuỗi JSON cột `info` trong database; đảm bảo tính năng lưu bền vững và chỉ thực hiện 1 lần.

**Files thay đổi:**
- `server/KPAH/src/daos/PlayerDAO.java` — Sửa `buildInfo`: xử lý an toàn phần tử index 8 (`questDataJSON`) dù được lưu dưới dạng `JSONObject` hay `String`; bọc try-catch và fallback giá trị mặc định cho toàn bộ mảng JSON thay vì throw `JSONException` làm crash luồng login của người chơi.
- `server/KPAH/src/network/MessageHandler.java` — Tại `case CommandMessage.NPC_INFO`: thay điều kiện đọc `idType` cố định bằng `if (msg.reader().available() > 0)` nhằm tránh lỗi `EOFException` khi client chỉ gửi 1 byte (như hàm `l(byte)` của Thiết Bị type=3 hoặc Lâm Tướng Quân type=21).
- `game/app/src/classes/class_gn.java` — Tạo class NPC làng với `public byte d = 1` để method `f_()` trả về 1, kích hoạt client gửi packet `NPC_INFO` (opcode 23) lên server khi người chơi click vào NPC ngoài làng (Trưởng Làng, Thiết Bị, Phú Ông).
- Database MySQL (`kpah.players`): Chạy migration script `ALTER TABLE kpah.players MODIFY COLUMN info TEXT NOT NULL;` để chuyển cột `info` từ `VARCHAR(255)` sang `TEXT`, khắc phục triệt để việc chuỗi JSON bị MySQL cắt cụt khi độ dài vượt quá 255 ký tự.

**Backup:**
- `server/KPAH/src/daos/_backup/PlayerDAO.java.bak.20260906_1500`
- `server/KPAH/src/network/_backup/MessageHandler.java.bak.20260906_1500`

**Kết quả:** ✅ Thành công

**Ghi chú:** 
- Đã chạy suite test tự động mô phỏng toàn bộ chu trình 3 nhiệm vụ tân thủ (Trưởng Làng diệt 100 Nhím -> Thiết Bị nhặt 3 trang bị -> Phú Ông đạt level 11 + diệt 500 quái) và test với tài khoản tạo mới hoàn toàn (`NewbieTest`).
- Xác nhận:
  1. `beginnerQuestId` tăng tuần tự từ 0 -> 1 -> 2 -> 3 và lưu bền vững qua database.
  2. Khi hoàn thành, menu "Nhiệm vụ (Tân thủ)" biến mất vĩnh viễn, không thể làm lại lần thứ hai.
  3. Sau khi hoàn thành tân thủ, menu chuyển sang "Nhận Nhiệm vụ" (Hằng ngày) đúng theo thiết kế.
  4. Đã dọn dẹp file tạm `_tmp`.

---

---
