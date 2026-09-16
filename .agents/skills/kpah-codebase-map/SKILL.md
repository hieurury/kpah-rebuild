---
name: kpah-codebase-map
description: >-
  Cẩm nang tra cứu và giải mã mã nguồn (Codebase Map & Deobfuscation Guide) cho dự án KPAH.
  Kích hoạt khi agent cần tìm hiểu, đọc hoặc chỉnh sửa các file client Java bị obfuscate (class_xxx),
  các biến entity viết tắt (cW, cZ, cG, cK, cL, de, bC, bD), giao thức gói tin mạng (Packet Opcodes),
  hoặc cấu trúc server Java KPAH. Giúp agent nhận biết ngay file nào làm gì mà không phải đọc lại từ đầu.
---

# 🗺️ KPAH Codebase Map & Quick Reference Skill

Khi làm việc với dự án KPAH, hầu hết mã nguồn Client ở [game/app/src/classes/](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/) đã bị obfuscate bởi ProGuard thành các tên dạng `class_xxx` và các biến ngắn.  
Sử dụng cẩm nang này để tra cứu ngay lập tức ý nghĩa của từng class và trường dữ liệu.

---

## 🚀 1. Tra Cứu Nhanh Các Class Client

| Class Decompiled | Tên Ý Nghĩa | Mô Tả & Chức Năng Chính |
|:---|:---|:---|
| [`class_hw`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_hw.java) | **`Actor` / `Entity`** | **Lớp cha của tất cả thực thể** (Player, Mob, Npc). Chứa tọa độ, máu, trạng thái khống chế, danh sách buff. |
| [`class_bi`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_bi.java) | **`Player`** | Nhân vật người chơi (cả nhân vật chính và người chơi khác). |
| [`class_bb`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_bb.java) | **`Monster` / `Mob`** | Quái vật trên bản đồ (máu, trạng thái bị đánh, hiệu ứng khống chế). |
| [`class_yi`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_yi.java) | **`Npc`** | NPC tương tác trong làng/thành (giao tiếp, mở shop, giao nhiệm vụ). |
| [`class_zx`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_zx.java) | **`VisualEffect`** | Hiệu ứng hình ảnh bám trên người Actor (Stun, Hóa Đá, Giảm Giáp, Độc, Khiên). |
| [`class_abj`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_abj.java) | **`GameScreen` / `Canvas`** | Màn hình game chính: Game loop, render map/thế giới, camera, danh sách quái/người, xử lý phím. |
| [`class_go`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_go.java) | **`MenuUI` / `Inventory`** | Màn hình giao diện: Hành trang (Túi đồ), Bảng kỹ năng, Thông tin nhân vật, Điểm tiềm năng. |
| [`class_acv`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_acv.java) | **`NetworkSession`** | Socket TCP client, gửi/nhận `Message`. |
| [`class_xw`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_xw.java) | **`TileMap`** | Bản đồ gạch, render địa hình, kiểm tra vật cản. |
| [`class_de`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_de.java) | **`Item` / `Equip`** | Thông tin vật phẩm, trang bị, vũ khí, ngọc khảm. |
| [`MsgHandler`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/MsgHandler.java) | **`PacketHandler`** | Điểm bắt và giải mã các gói tin `Message` từ Server gửi về. |
| [`MainCharInfo`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/MainCharInfo.java) | **`ModInfo`** | API tiện ích lấy thông tin nhân vật chính: HP, MP, tọa độ, danh sách buff/debuff. |
| [`ModController`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/ModController.java) | **`ModAuto`** | Hệ thống tự động: Auto train quái, Auto bơm máu, Teleport, Thay đổi khu vực. |

---

## 🔍 2. Tra Cứu Nhanh Các Biến Thực Thể (Actor Fields Trong `class_hw`)

* **`cK`, `cL`**: Tọa độ X, Y trên bản đồ (pixels).
* **`cG`**: ID định danh thực thể (Player ID hoặc Mob ID).
* **`cV`**: Trạng thái sống/chết (`0` = sống bình thường, `1` = đã chết/biến mất).
* **`da`**: Hướng quay mặt (`0`: Lên, `1`: Xuống, `2`: Trái, `3`: Phải).
* **`db`**: Tốc độ chạy của nhân vật.
* **`cx`, `cy`**: HP hiện tại và HP tối đa.
* **`cz`, `cA`**: MP hiện tại và MP tối đa.
* **`cW`**: Cờ báo trạng thái **Bị Choáng / Bất Động** (`boolean isStunned`).
* **`cZ`**: Timestamp kết thúc trạng thái Choáng (`long timeEndStun`).
* **`de`**: `Vector<class_zx>` danh sách các hiệu ứng hình ảnh gắn trên Actor.
* **`bC`, `bD`**: Mảng ID buff (`byte[]`) và Mảng Level buff tương ứng (`byte[]`).

---

## 📡 3. Giao Thức Gói Tin Mạng Chính

* **Cmd `4`**: Chat / Dialog
* **Cmd `5`**: Di chuyển nhân vật (Move)
* **Cmd `9`**: Đòn đánh / Tấn công (Attack)
* **Cmd `10`**: Quái vật tấn công người chơi
* **Cmd `-23`**: Thêm Buff ảnh hưởng (`addBuffInfluence`: ID buff, thời lượng)
* **Cmd `-24`**: Xóa Buff ảnh hưởng (`removeBuffInfluence`)
* **Cmd `-25`**: Kích hoạt Skill Buff chủ động (`addSkillBuff`)
* **Cmd `42`**: Đồng bộ chỉ số nhân vật (Point: HP, MP, Giáp, Công)

---

## 📚 4. Thư Mục Tài Liệu Chi Tiết

Mọi chi tiết sâu hơn vui lòng mở các tài liệu trong [docs/codebase_map/](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/docs/codebase_map/):
* [docs/codebase_map/client_classes.md](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/docs/codebase_map/client_classes.md)
* [docs/codebase_map/client_actor_fields.md](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/docs/codebase_map/client_actor_fields.md)
* [docs/codebase_map/network_and_server.md](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/docs/codebase_map/network_and_server.md)
