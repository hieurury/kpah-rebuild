# 📡 GIAO THỨC MẠNG & KIẾN TRÚC SERVER (NETWORK & SERVER ARCHITECTURE)

Tài liệu hướng dẫn về giao thức truyền tin qua mạng giữa Client và Server cũng như kiến trúc mã nguồn Java Server KPAH.

---

## 📨 1. Giao Thức Gói Tin Mạng (Packet Opcodes)

Mọi giao tiếp đều thông qua đối tượng `Message(byte command)`:

| Command Opcode | Ý Nghĩa (Action) | Hướng gửi | Mô tả luồng xử lý |
|:---:|:---|:---:|:---|
| **`4`** | **Chat / Tin nhắn** | C ⟷ S | Gửi và nhận tin nhắn công cộng, tin riêng, thông báo server. |
| **`5`** | **Di chuyển (Move)** | C ⟷ S | Đồng bộ tọa độ X/Y của nhân vật trên bản đồ. |
| **`9`** | **Tấn công (Attack)** | C ⟷ S | Client gửi lệnh đánh mục tiêu; Server tính toán sát thương, trừ HP và gửi trả animation/số máu trừ về Client. |
| **`10`** | **Quái đánh (Mob Attack)** | S ⟶ C | Server báo cho Client biết quái vật đang vung đòn đánh người chơi nào. |
| **`-23`** | **Thêm Buff ảnh hưởng** | S ⟶ C | Server gửi lệnh kích hoạt hiệu ứng khống chế/buff lên Actor (Stun, Trúng độc, Hóa đá ID 7, Giảm giáp ID 9) kèm thời lượng miligiây. |
| **`-24`** | **Hủy Buff ảnh hưởng** | S ⟶ C | Báo cho Client xóa bỏ hiệu ứng khống chế khi hết thời gian hiệu lực. |
| **`-25`** | **Skill Buff Chủ Động** | S ⟶ C | Đồng bộ các buff kỹ năng nội tại/chủ động (Bất Di Biến, Cường Thân Giáp, Hồi Công Lực Đan). |
| **`40`** | **Giao dịch / NPC Shop** | C ⟷ S | Mở menu mua bán dược phẩm, vật phẩm, trang bị tại NPC. |
| **`42`** | **Chỉ số nhân vật (Point)** | S ⟶ C | Gửi toàn bộ thông tin HP, Max HP, MP, Giáp, Công, Điểm tiềm năng cho Client render thanh trạng thái. |
| **`60`** | **Chuyển bản đồ (Map Change)** | C ⟷ S | Load map mới, nạp danh sách người chơi, NPC và quái vật trong khu vực mới. |

---

## 🏛️ 2. Kiến Trúc Mã Nguồn Server Java (`server/KPAH/src/`)

```
server/KPAH/src/
├── consts/                 # Định nghĩa các hằng số ID hệ thống
│   ├── Const.java          # ID các phái (DAU_SI = 3, CHIEN_BINH = 1...), Quốc gia
│   └── BuffConst.java      # ID các loại Buff/Debuff (BUFF_HOA_DA, BUFF_GIAM_GIAP...)
├── player/                 # Quản lý thực thể Người chơi
│   ├── Player.java         # Class đại diện cho Player (Session, HP, MP, Location, Inventory)
│   └── Point.java          # Tính toán toàn bộ chỉ số nhân vật (HP Max, Giáp, Dame, Kháng)
├── services/               # Tầng xử lý nghiệp vụ chính (Business Logic)
│   ├── SkillService.java   # Logic tung chiêu, tính sát thương, multi-hit, khống chế
│   ├── BuffService.java    # Đóng gói và gửi packet buff/debuff về client
│   ├── MapService.java     # Đồng bộ thực thể vào/ra map, phát loa chat khu vực
│   └── ItemService.java    # Xử lý trang bị, cường hóa, khảm ngọc
├── manager/                # Quản trị hệ thống & nạp Database
│   └── Manager.java        # Load dữ liệu từ MySQL (others, skill_news, items, maps)
├── map/                    # Quản lý bản đồ thế giới & Quái vật
│   ├── Map.java            # Khu vực map, quản lý người chơi & quái trong map
│   └── Monster.java        # Thực thể quái vật, AI di chuyển, tấn công, trạng thái bị khống chế
└── skill/                  # Xử lý thời gian hồi và buff ảnh hưởng
    ├── BuffInfluencePlayer.java   # Hiệu ứng khống chế đang áp lên người chơi
    ├── BuffInfluenceMonster.java  # Hiệu ứng khống chế đang áp lên quái vật
    └── SkillBuff.java             # Quản lý các buff kỹ năng (Bất di biến...)
```

---

## 🔄 3. Quy Trình Chuẩn Khi Triển Khai Tính Năng Mới

1. **Bước 1: Định nghĩa hằng số:** Thêm ID vào [BuffConst.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/server/KPAH/src/consts/BuffConst.java) hoặc [Const.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/server/KPAH/src/consts/Const.java).
2. **Bước 2: Xử lý Server Logic:**
   - Cập nhật [Point.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/server/KPAH/src/player/Point.java) nếu có thay đổi về chỉ số (Máu, Giáp, Dame scale).
   - Cập nhật [SkillService.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/server/KPAH/src/services/SkillService.java) để kích hoạt hiệu ứng khi tấn công mục tiêu.
   - Cập nhật [BuffService.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/server/KPAH/src/services/BuffService.java) để gửi packet `-23` về Client.
3. **Bước 3: Xử lý Client Render:**
   - Cập nhật [MsgHandler.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/MsgHandler.java) để đọc packet `-23`.
   - Cập nhật [class_zx.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_zx.java) để render vòng màu hoặc icon visual an toàn.
   - Cập nhật [MainCharInfo.java](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/MainCharInfo.java) để hiển thị tên buff trên HUD Mod.
4. **Bước 4: Kiểm tra Build:**
   - Server: `JAVA_HOME=/usr/lib/jvm/java-1.21.0-openjdk-amd64 ant -f server/KPAH/build.xml compile`
   - Client: `JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64 ant -f game/build.xml dist-local`
