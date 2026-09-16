# 🗺️ BẢN ĐỒ CÁC CLASS CLIENT (CLIENT CLASSES MAP)

Bảng tra cứu toàn bộ các class Java Client bị obfuscated trong thư mục [game/app/src/classes/](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/).

---

## 🏛️ 1. Các Thực Thể Chính (Game Entities & Actors)

| Tên Decompiled | Tên Ý Nghĩa (Role) | Kế thừa từ | Mô tả & Chức năng chính |
|:---|:---|:---|:---|
| [`class_hw`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_hw.java) | **`Actor` / `Entity`** | `Object` | **Lớp cha của tất cả thực thể trên bản đồ** (Player, Monster, NPC). Chứa tọa độ x/y (`cK`, `cL`), ID thực thể (`cG`), trạng thái sống/chết (`cV`), khống chế (`cW`, `cZ`), danh sách hiệu ứng buff (`de`), mảng buff active (`bC`, `bD`). |
| [`class_bi`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_bi.java) | **`Player` / `Char`** | `class_hw` | Đại diện cho **Nhân vật người chơi** (bao gồm cả nhân vật chính `MainChar` và người chơi khác xung quanh). Chứa thông tin trang bị, vũ khí, thú cưỡi, bang hội, danh hiệu, level, PK status. |
| [`class_bb`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_bb.java) | **`Monster` / `Mob`** | `class_hw` | Đại diện cho **Quái vật trên bản đồ**. Quản lý render quái, thanh máu quái, trạng thái bị tấn công, quái tinh anh/thủ lĩnh, hiệu ứng khống chế của quái. |
| [`class_yi`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_yi.java) | **`Npc`** | `class_hw` | Đại diện cho **NPC trong làng/thành**. Xử lý đối thoại, trả/nhận nhiệm vụ, mở menu mua bán/rèn đồ. |
| [`class_zx`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_zx.java) | **`VisualEffect`** | `Object` | **Hiệu ứng đồ họa / Icon trạng thái** gắn trên đầu hoặc quanh chân Actor (Vòng sao choáng, khói tím trúng độc, khiên buff, vòng Hóa Đá xám tro, vòng Giảm Giáp đỏ cam). |
| [`class_pp`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_pp.java) | **`ParticleEffect`** | `Object` | Các hiệu ứng hạt, tia lửa, sấm sét, vệt chém bay khi tung đòn hoặc xuất hiện kỹ năng AoE. |

---

## 🎮 2. Màn Hình & Render (Screens & Game Canvas)

| Tên Decompiled | Tên Ý Nghĩa (Role) | Mô tả & Chức năng chính |
|:---|:---|:---|
| [`class_abj`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_abj.java) | **`GameScreen` / `GameWorld`** | **Màn hình game chính**. Quản lý toàn bộ thế giới game: Game loop (`update`, `paint`), Camera (`cmx`, `cmy`), Vector danh sách người chơi (`vCharInMap`), danh sách quái (`vMob`), danh sách NPC, vật phẩm rơi dưới đất (`vItemMap`). Điều khiển input phím và chuột ảo. |
| [`class_sc`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_sc.java) | **`ScreenBase`** | Lớp cha cơ sở của tất cả màn hình giao diện trong game. |
| [`class_go`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_go.java) | **`MenuScreen` / `InventoryUI`** | Màn hình Hành trang (Túi đồ), Bảng thông tin nhân vật, Điểm tiềm năng, Kỹ năng, Bang hội. Quản lý các Tab giao diện. |
| [`class_yv`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_yv.java) | **`DialogPopup`** | Khung hộp thoại thông báo (Alert popup, Confirm Yes/No, Nhập số lượng mua/bán). |
| [`class_wc`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_wc.java) | **`ChatManager`** | Hộp thoại trò chuyện, kênh thế giới, bang hội, mật, tin nhắn hệ thống. |
| [`class_xw`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_xw.java) | **`TileMap`** | Bản đồ gạch ngói, render địa hình, kiểm tra va chạm tường/nước/đất đá (`tileType`). |

---

## 🌐 3. Mạng & Dữ Liệu (Network & Data)

| Tên Decompiled | Tên Ý Nghĩa (Role) | Mô tả & Chức năng chính |
|:---|:---|:---|
| [`class_acv`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_acv.java) | **`Session` / `NetService`** | Kết nối Socket TCP tới Game Server. Chịu trách nhiệm gửi và nhận các đối tượng gói tin `Message`. |
| [`class_de`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_de.java) | **`Item` / `Equipment`** | Đối tượng vật phẩm, trang bị, vũ khí, dược phẩm. Lưu trữ thuộc tính, chỉ số cộng thêm, độ bền, ngọc khảm. |
| [`class_gn`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_gn.java) | **`SkillData`** | Thông tin kỹ năng của nhân vật (ID, cấp độ, thời gian hồi, mana, loại mục tiêu). |
| [`class_dp`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_dp.java) | **`FontRenderer`** | Bộ vẽ font chữ tiếng Việt, đổ bóng viền chữ, render chữ nhiều màu sắc. |
| [`class_jd`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_jd.java) | **`SpriteCache` / `ImageCache`** | Quản lý bộ nhớ đệm hình ảnh, nạp sprite từ RMS hoặc từ server gửi về. |
| [`class_zu`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_zu.java) | **`SoundManager`** | Quản lý phát âm thanh hiệu ứng (nhát chém, nổ chiêu, tiếng quái chết). |

---

## 🛠️ 4. Các Class Mod Chill & Mở Rộng (Mod Extension)

| Tên Class | Vai trò | Mô tả |
|:---|:---|:---|
| [`MainCharInfo`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/MainCharInfo.java) | **Mod Core Info** | Cung cấp API trực tiếp lấy thông tin nhân vật chính: Tọa độ, HP/MP, Level, danh sách Buffs/Debuffs (kể cả Hóa Đá, Giảm Giáp mới thêm). |
| [`ModController`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/ModController.java) | **Mod Automation** | Chứa logic Auto đánh quái, Auto bơm máu/mana, Hack di chuyển nhanh, Tự nhặt đồ, Auto luyện kỹ năng, Đổi khu vực. |
| [`ModHelpers`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/ModHelpers.java) | **Graphics & Reflection** | Hỗ trợ can thiệp xử lý đồ họa nâng cao, scale hình ảnh, transform sprite thông qua reflection. |
| [`MsgHandler`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/MsgHandler.java) | **Packet Interceptor** | Bắt và phân tích các gói tin `Message` nhận từ Server, kích hoạt các hiệu ứng client tương ứng. |
| [`CraftShopScreen`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/CraftShopScreen.java) | **Craft UI** | Giao diện tự chế tạo / ghép đồ / khảm ngọc nhanh của bản Mod. |
