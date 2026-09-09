# KPAH Project — Quy Tắc Bắt Buộc Cho Agent

Đây là bộ quy tắc **luôn hoạt động** khi agent làm việc trong dự án KPAH.
Agent phải tuân thủ toàn bộ các quy tắc này ở mọi nhiệm vụ.

---

## 🎯 Nguyên Tắc Cốt Lõi

**Agent chỉ thực hiện đúng những gì được yêu cầu — không hơn, không kém.**

- Chỉ sửa đổi những file được chỉ định hoặc **trực tiếp liên quan** đến nhiệm vụ.
- Không tự ý refactor, đổi tên, xóa code nằm ngoài phạm vi yêu cầu.
- Nếu phát hiện vấn đề khác, ghi vào `TASK.md` và báo cáo — không tự ý sửa.

---

## 🔒 Quy Tắc Bảo Vệ Cấu Trúc Dự Án

### 1. Không Phá Vỡ Logic Hiện Tại
- Trước khi sửa bất kỳ method hoặc class nào, đọc và hiểu toàn bộ logic hiện tại.
- Không xóa method/field đang được sử dụng bởi code khác trừ khi được yêu cầu rõ ràng.
- Khi thêm tính năng mới, ưu tiên **mở rộng** (extend) thay vì **thay thế** (replace).

### 2. Không Thay Đổi Kiến Trúc Không Được Phép
- Không thay đổi tên package Java.
- Không di chuyển file sang thư mục khác.
- Không thay đổi `build.xml`, `project.properties`, `pom.xml` trừ khi được yêu cầu.

### 3. Không Xóa Thông Tin Quan Trọng
- Giữ nguyên tất cả comment giải thích logic nghiệp vụ.
- Không xóa `TODO`, `FIXME`, `HACK` comment của developer gốc.
- Chỉ xóa code dead khi được user yêu cầu rõ ràng.

---

## 📋 Quy Tắc Backup

### Khi nào cần backup:
- Sửa đổi > 20 dòng trong một file.
- Xóa hoặc thay thế toàn bộ một method/class.
- Thay đổi cơ chế xử lý packet hoặc database query.
- Tái cấu trúc (refactor) bất kỳ thành phần nào.

### Cách backup:
```bash
# Tạo _backup/ cùng cấp với file cần sửa
cp <file_gốc> <thư_mục_chứa_file>/_backup/<tên_file>.bak.$(date +%Y%m%d_%H%M)
```

### File tạm (để test):
- Đặt trong `_tmp/` ở thư mục gốc dự án.
- Đặt tên rõ ràng: `<tên_file>_test.<ext>` hoặc `<tên_file>_draft.<ext>`.
- **Xóa file tạm** sau khi xác nhận logic đúng.

---

## 📝 Quy Tắc Ghi TASK.md (BẮT BUỘC)

Sau **mỗi nhiệm vụ hoàn thành**, agent phải cập nhật `TASK.md` tại thư mục gốc dự án.

**Quy định số lượng Task mỗi file:**
- Mỗi file `TASK.md` chỉ ghi nhận tối đa **10 task**.
- Khi đủ 10 task, lưu trữ file hiện tại thành `tasks/TASK_XX.md` và bắt đầu file `TASK.md` mới để tránh file quá lớn và không cộng dồn.

**Cấu trúc entry tối thiểu:**
```markdown
## [YYYY-MM-DD HH:MM] — <Tên nhiệm vụ>

**Yêu cầu:** <mô tả>
**Files thay đổi:** `file.java` — <thay đổi gì>
**Kết quả:** ✅ Thành công / ❌ Thất bại / ⚠️ Cần kiểm tra
**Ghi chú:** <backup path nếu có, vấn đề phát sinh>

---
```

**Không được bỏ qua bước này dù nhiệm vụ nhỏ đến đâu.**

---

## ⚡ Quy Tắc Phát Sinh Vấn Đề

Khi agent phát hiện vấn đề nằm ngoài nhiệm vụ được giao:

1. **KHÔNG tự ý sửa**.
2. Ghi vào `TASK.md` dưới mục `⚠️ Phát hiện vấn đề (chưa xử lý)`.
3. Báo cáo cho user sau khi hoàn thành nhiệm vụ chính.
4. Chờ xác nhận trước khi tiếp tục.

---

## 🏗️ Cấu Trúc Dự Án KPAH

```
kpah_mod_chill/
├── game/           # Client Java (decompiled + patched)
│   └── app/src/classes/    # Các class game client
├── server/         # Server Java (NetBeans project)
│   └── KPAH/src/           # Source server
│       ├── services/       # Business logic services
│       ├── manager/        # Game state managers
│       ├── item/           # Item definitions
│       └── server/         # Network server
├── decompile_tmp/  # File decompile tạm thời
├── _backup/        # Backup files (không commit)
├── _tmp/           # File tạm để test (không commit)
├── TASK.md         # Nhật ký nhiệm vụ (BẮT BUỘC CẬP NHẬT)
└── GEMINI.md       # File này — quy tắc agent
```

---

## 🚫 Danh Sách Hành Động Bị Cấm Tuyệt Đối

| Hành động | Lý do |
|-----------|-------|
| Overwrite toàn bộ nội dung file lớn mà không backup | Mất code không thể khôi phục |
| Xóa import statement "có vẻ không dùng" | Java có thể dùng qua reflection |
| Thay đổi opcode/packet ID | Phá vỡ giao tiếp client-server |
| Xóa sleep/delay trong network code | Gây race condition |
| Sửa đồng thời nhiều file core mà không test từng bước | Khó debug khi có lỗi |
| Bỏ qua ghi TASK.md | Mất lịch sử thay đổi |

---

## ✅ Checklist Trước Khi Commit Thay Đổi

Trước khi kết thúc nhiệm vụ, agent tự kiểm tra:
- [ ] Chỉ thay đổi đúng những gì được yêu cầu.
- [ ] Không có method/class nào bị xóa nhầm.
- [ ] Backup đã được tạo (nếu rủi ro cao).
- [ ] File tạm đã được dọn dẹp.
- [ ] `TASK.md` đã được cập nhật.
- [ ] User đã được thông báo về bất kỳ vấn đề nào phát hiện thêm.
