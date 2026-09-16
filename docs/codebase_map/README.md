# 🗺️ KPAH CODEBASE MAP & DEOBFUSCATION GUIDE

Hệ thống tài liệu và bản đồ tra cứu toàn diện cấu trúc dự án **KPAH Chill Mod**.  
Tài liệu này được biên soạn để mọi lập trình viên và AI Agent có thể tra cứu và hiểu ngay lập tức từng file, từng class mã hóa (`class_xxx`), từng biến viết tắt (`cW`, `cZ`, `cG`, `cK`, `cL`...) mà **không phải đọc lại hay decompile lại từ đầu**.

---

## 📂 Cấu Trúc Tài Liệu Bản Đồ

1. [client_classes.md](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/docs/codebase_map/client_classes.md): Danh mục toàn bộ các class Java Client bị obfuscate (Decompiled name ⟷ Vai trò thực tế).
2. [client_actor_fields.md](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/docs/codebase_map/client_actor_fields.md): Giải mã chi tiết các biến viết tắt trong thực thể Game (Actor, Player, Monster: Tọa độ, Máu, Mana, Trạng thái khống chế, Buffs).
3. [network_and_server.md](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/docs/codebase_map/network_and_server.md): Giao thức gói tin mạng (Packet Opcodes) & Kiến trúc Server Java (Manager, Services, Player, Map).

---

## ⚡ Quy Tắc Vàng Khi Làm Việc Với Codebase KPAH

* **Client Java ME (J2ME / ProGuard):**
  * Hầu hết mã nguồn gốc bị ProGuard thu gọn thành `class_xx` và các biến 1-2 ký tự.
  * Chỉ sửa đổi những gì thực sự cần thiết, giữ nguyên các tên biến/hàm gốc để tránh làm gãy luồng reflection hoặc serialization.
  * Khi cần lấy thông tin nhân vật hoặc trạng thái game, ưu tiên tra cứu qua [MainCharInfo](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/MainCharInfo.java) hoặc [ModController](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/ModController.java).
* **Server Java (NetBeans / Java 21):**
  * Mã nguồn server viết bằng Java chuẩn, dùng Lombok (`@Data`, `@Builder`).
  * Tất cả các thay đổi về logic chiến đấu, kỹ năng, buff/debuff phải xử lý ở server trước, sau đó gửi packet Message đồng bộ về client.
  * Tuân thủ quy tắc backup và ghi nhận [TASK.md](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/TASK.md).
