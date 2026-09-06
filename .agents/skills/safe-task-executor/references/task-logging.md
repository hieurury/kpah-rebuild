# Task Logging — Hướng Dẫn Ghi TASK.md

File `TASK.md` nằm ở **thư mục gốc dự án** và ghi lại mọi nhiệm vụ agent đã thực hiện.

---

## Quy Tắc Ghi Log

1. **Mỗi nhiệm vụ** là một entry riêng biệt với timestamp.
2. **Không xóa** các entry cũ — chỉ thêm vào cuối file.
3. Ghi **trung thực**: nếu có lỗi phát sinh, ghi rõ.
4. Ghi **đủ thông tin** để có thể rollback nếu cần.

---

## Format Entry Đầy Đủ

```markdown
## [YYYY-MM-DD HH:MM] — <Tên nhiệm vụ ngắn gọn>

**Yêu cầu:** <Mô tả yêu cầu từ user>

**Mức độ rủi ro:** Thấp / Trung bình / Cao

**Files thay đổi:**
- `path/to/file1.java` — <mô tả thay đổi cụ thể>
- `path/to/file2.java` — <mô tả thay đổi cụ thể>

**Files tạo mới:** (nếu có)
- `path/to/new_file.java` — <mục đích>

**Backup:** (nếu rủi ro Cao)
- `path/to/_backup/file1.java.bak.YYYYMMDD_HHMM`

**Kết quả:** ✅ Thành công / ❌ Thất bại / ⚠️ Cần kiểm tra thêm

**Ghi chú:** <vấn đề phát sinh, dependency cần chú ý, cách test, v.v.>

---
```

---

## Format Entry Nhanh (cho thay đổi nhỏ)

```markdown
## [YYYY-MM-DD HH:MM] — <Tên nhiệm vụ>

**Yêu cầu:** <mô tả>
**Files:** `path/to/file.java` — <thay đổi>
**Kết quả:** ✅ Thành công

---
```

---

## Mục Phát Hiện Vấn Đề

Khi agent phát hiện vấn đề nằm ngoài phạm vi nhiệm vụ:

```markdown
### ⚠️ Phát hiện vấn đề (chưa xử lý)

- **Vấn đề:** <mô tả vấn đề phát hiện>
- **Vị trí:** `path/to/file.java:line`
- **Mức độ ảnh hưởng:** <ước tính>
- **Đề xuất:** <hành động nên thực hiện>
- **Trạng thái:** Chờ xác nhận từ user
```

---

## Ví Dụ TASK.md Thực Tế

```markdown
# TASK.md — Nhật Ký Nhiệm Vụ KPAH Mod

---

## [2026-09-02 21:05] — Sửa lỗi Monster AI Movement

**Yêu cầu:** Monster bị đứng hình, cần đồng bộ packet MOVE_CHAR giữa server và client.

**Mức độ rủi ro:** Trung bình

**Files thay đổi:**
- `game/app/src/classes/class_bi.java` — Sửa xử lý packet MOVE_CHAR, áp dụng tọa độ server vào entity monster
- `server/KPAH/src/services/ZoneService.java` — Đảm bảo trigger movement packet cho tất cả monster trong chunk

**Kết quả:** ✅ Thành công — Monster di chuyển bình thường sau khi fix

**Ghi chú:** Kiểm tra bằng cách spawn 3 monster loại khác nhau và quan sát animation.

---

## [2026-09-02 21:30] — Tái Cấu Trúc MapService.java (Auto-Loot)

**Yêu cầu:** Xóa distance validation trong pickup logic.

**Mức độ rủi ro:** Cao

**Files thay đổi:**
- `server/KPAH/src/services/MapService.java` — Xóa check distance, giữ nguyên inventory logic

**Backup:**
- `server/KPAH/src/services/_backup/MapService.java.bak.20260902_2130`

**Kết quả:** ✅ Thành công

**Ghi chú:** Backup tại đường dẫn trên. Test bằng cách loot item từ xa > 10 tiles.

---

### ⚠️ Phát hiện vấn đề (chưa xử lý)

- **Vấn đề:** `MonsterService.java` có memory leak tiềm ẩn tại dòng 245 — List monster không được clear sau khi zone unload.
- **Vị trí:** `server/KPAH/src/services/MonsterService.java:245`
- **Mức độ ảnh hưởng:** Cao nếu server chạy dài hạn
- **Đề xuất:** Thêm cleanup logic trong onZoneUnload()
- **Trạng thái:** Chờ xác nhận từ user
```
