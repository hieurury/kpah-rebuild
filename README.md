# KPAH Mod Chill - Hướng Dẫn Khởi Động

Dự án này bao gồm 2 thành phần chính: **Server** (chạy logic máy chủ) và **Game Client** (trình giả lập J2ME để chơi).

## 🛠️ 1. Yêu cầu hệ thống (Prerequisites)
- **Docker** (để chạy cơ sở dữ liệu MySQL).
- **Java JDK 21** (bắt buộc để chạy Server).
- **Java JDK 8** (bắt buộc để chạy Game Client / Giả lập MicroEmulator).
- **Apache Ant** (công cụ build dự án).

---

## 🗄️ 2. Khởi động Cơ sở dữ liệu (Database)
Server sử dụng MySQL được đóng gói sẵn trong Docker. Bạn cần khởi động container này trước khi bật Server.

```bash
# Khởi động container MySQL (nếu container đã tồn tại)
docker start kpah-mysql
```
*(Cấu hình mặc định: user `root`, mật khẩu `kpah`, database `kpah`)*

---

## 🖥️ 3. Khởi động Máy chủ (Server)
Server yêu cầu **Java 21**. Do máy bạn có thể có nhiều phiên bản Java, hãy chỉ định rõ đường dẫn Java 21 khi chạy.

```bash
# 1. Di chuyển vào thư mục Server
cd /home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/server/KPAH

# 2. Build và khởi chạy Server bằng Java 21
JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 ant run
```
*Lưu ý: Nếu bị lỗi cổng 19129 đã được sử dụng (Address already in use), hãy tắt server cũ đang chạy ngầm bằng lệnh `killall -9 java`.*

---

## 🎮 4. Khởi động Game Client (Client)
Client J2ME cũ bắt buộc phải chạy bằng **Java 8** cùng với flag `-noverify` (đã được thiết lập sẵn trong file `build.xml` của client).

```bash
# 1. Di chuyển vào thư mục Game
cd /home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game

# 2. Build và khởi chạy Giả lập Game bằng Java 8
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ant run
```

---

## 💡 Xử lý sự cố thường gặp (Troubleshooting)

- **Lỗi màn hình trắng/crash khi đánh quái:** (Đã fix) Do thiếu file `explosion.png` gây lỗi `NullPointerException` ở bộ phận render.
- **Lỗi không đăng ký/đăng nhập được:** Kiểm tra lại xem Docker MySQL (`kpah-mysql`) đã bật và đang chạy đúng cổng chưa.
- **Lỗi "invalid target release: 21":** Xảy ra khi bạn cố build Server bằng Java 8. Hãy chắc chắn bạn thêm `JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64` vào trước lệnh `ant run` khi bật server.
- **Lỗi "Inconsistent stackmap frames":** Xảy ra khi chạy Client ở các bản Java cao mà không có flag `-noverify`. Hãy chạy đúng Java 8 như hướng dẫn ở bước 4.

---

## 🔧 5. Workflow Bytecode Patching (Dành cho Developer)

Các class trong `libs/KPAH_225_remade.jar` bị làm rối (obfuscated). **Không được decompile rồi biên dịch lại** — sẽ làm hỏng logic ẩn. Thay vào đó, dùng **Javassist** để can thiệp Bytecode trực tiếp.

### Nguyên tắc bắt buộc
1. **Luôn giữ bản backup sạch** của class gốc trước khi patch.
2. **Không bao giờ** chạy Patcher hai lần liên tiếp mà không restore bản gốc ở giữa.
3. **Luôn kiểm tra** `strings patched.class | grep "tên method mới"` trước khi đưa vào jar.

### Cách thêm/sửa một mod mới (ví dụ Auto-Loot)

```bash
# Bước 0: Backup bản gốc sạch (CHỈ làm 1 lần, lần đầu tiên)
unzip -j game/libs/KPAH_225_remade.jar classes/class_abj.class -d /tmp/
mkdir -p /tmp/orig_abj_clean/classes
cp /tmp/class_abj.class /tmp/orig_abj_clean/classes/class_abj.class

# Bước 1: Build ModController và các class mod mới
cd game
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ant compile

# Bước 2: Restore jar về bản gốc sạch
cd /tmp/orig_abj_clean
jar uf /path/to/game/libs/KPAH_225_remade.jar classes/class_abj.class

# Bước 3: Chạy Patcher (đọc bản gốc, ghi ra patched_classes/)
rm -rf game/tools/patched_classes
mkdir game/tools/patched_classes
cd game/tools
java -cp .:/tmp/javassist.jar Patcher

# Bước 4: Kiểm tra kết quả (chỉ nên thấy method mới, không có method cũ thừa)
strings patched_classes/classes/class_abj.class | grep ModController

# Bước 5: Đưa class đã patch vào jar
cd patched_classes
jar uf /path/to/game/libs/KPAH_225_remade.jar classes/class_abj.class

# Bước 6: Build và chạy game
cd game
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ant run
```

### File liên quan
- `game/tools/Patcher.java` — Tool bytecode injection bằng Javassist.
- `game/app/src/classes/ModController.java` — Nơi đặt tất cả helper methods được gọi từ Patcher.
- `/tmp/javassist.jar` — Thư viện Javassist (cần tải lại nếu mất: `wget https://repo1.maven.org/maven2/org/javassist/javassist/3.29.2-GA/javassist-3.29.2-GA.jar -O /tmp/javassist.jar`).
- `/tmp/orig_abj_clean/classes/class_abj.class` — **Backup bản gốc sạch** (KHÔNG được xóa hoặc ghi đè).

