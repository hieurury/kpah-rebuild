---
name: safe-task-executor
description: >-
  Skill chính để thực thi nhiệm vụ một cách an toàn trong dự án KPAH.
  Kích hoạt khi agent cần chỉnh sửa, tái cấu trúc, hoặc thêm tính năng vào
  bất kỳ file nào trong dự án. Đảm bảo không phá hủy cấu trúc/logic hiện tại,
  luôn tạo backup trước khi thay đổi lớn, dùng file tạm để test, và ghi lại
  toàn bộ nhiệm vụ đã hoàn thành vào TASK.md ở thư mục gốc dự án.
---

# Safe Task Executor — Quy Trình Thực Thi An Toàn

Skill này bắt buộc agent tuân thủ các bước kiểm soát an toàn **trước, trong, và sau** khi thực hiện bất kỳ nhiệm vụ nào liên quan đến chỉnh sửa code hoặc cấu trúc dự án.

---

## 1. PHÂN TÍCH NHIỆM VỤ (Bắt buộc trước khi làm)

Trước khi thực hiện bất kỳ thay đổi nào, agent phải:

1. **Đọc hiểu phạm vi nhiệm vụ**: Xác định rõ yêu cầu là gì — thêm tính năng, sửa bug, tái cấu trúc, hay thay đổi cấu hình.
2. **Xác định các file bị ảnh hưởng**: Liệt kê tất cả file sẽ bị chỉnh sửa.
3. **Đánh giá mức độ rủi ro**:
   - **Thấp**: Thêm method mới, sửa bug nhỏ, thay đổi 1–2 dòng.
   - **Trung bình**: Sửa đổi logic hiện có, thêm class mới, thay đổi cấu hình.
   - **Cao**: Tái cấu trúc lớn, xóa file, thay đổi kiến trúc, chỉnh sửa > 30% nội dung file.
4. **KHÔNG bao giờ** bắt đầu thay đổi khi chưa hiểu rõ logic hiện tại.

---

## 2. QUY TẮC AN TOÀN THEO MỨC ĐỘ RỦI RO

### Rủi ro THẤP
- Thực hiện trực tiếp.
- Ghi log vào `TASK.md` sau khi hoàn thành.

### Rủi ro TRUNG BÌNH
- Đọc kỹ toàn bộ file trước khi chỉnh sửa.
- Thực hiện thay đổi theo từng bước nhỏ (incremental), không xóa logic cũ khi chưa xác nhận logic mới hoạt động.
- Ghi log vào `TASK.md`.

### Rủi ro CAO — BẮT BUỘC áp dụng quy trình backup

Xem chi tiết tại: [backup-protocol.md](./references/backup-protocol.md)

**Tóm tắt**:
1. Tạo backup file: `<tên_file>.bak.<timestamp>` trong thư mục `_backup/` ở cùng cấp với file gốc.
2. Tạo file tạm để test: `<tên_file>_test.<ext>` trong thư mục `_tmp/` ở thư mục gốc dự án.
3. Chỉ áp dụng thay đổi vào file gốc sau khi đã xác nhận logic đúng.
4. Xóa file tạm sau khi xác nhận xong.
5. Ghi log vào `TASK.md`.

---

## 3. CÁC HÀNH ĐỘNG BỊ CẤM

Agent **TUYỆT ĐỐI KHÔNG ĐƯỢC**:
- Xóa hoặc overwrite toàn bộ nội dung một file đang hoạt động mà không có backup.
- Xóa method/class đang được sử dụng ở nơi khác mà chưa kiểm tra dependency.
- Thay đổi tên package, namespace, hoặc cấu trúc thư mục mà không có sự chấp thuận của user.
- Xóa comment/docstring quan trọng giải thích logic nghiệp vụ.
- Commit các file tạm (`_tmp/`, `*.bak.*`) vào version control.
- Thực hiện nhiều thay đổi lớn trong một lần mà không có checkpoint.

---

## 4. GHI LOG VÀO TASK.md (Bắt buộc sau mỗi nhiệm vụ)

Sau khi hoàn thành nhiệm vụ, agent **phải** cập nhật file `TASK.md` ở thư mục gốc dự án.

Xem template và hướng dẫn tại: [task-logging.md](./references/task-logging.md)

**Format entry nhanh**:
```markdown
## [YYYY-MM-DD HH:MM] — <Tên nhiệm vụ ngắn gọn>

**Yêu cầu:** <Mô tả ngắn yêu cầu từ user>
**Files thay đổi:**
- `path/to/file.java` — <mô tả thay đổi>
**Kết quả:** <Thành công / Thất bại / Cần kiểm tra thêm>
**Ghi chú:** <Backup tại, vấn đề phát sinh, v.v.>
```

---

## 5. QUY TRÌNH PHÁT SINH VẤN ĐỀ NGOÀI PHẠM VI

Nếu trong quá trình thực hiện, agent phát hiện vấn đề **nằm ngoài nhiệm vụ được giao** (bug khác, code smell nghiêm trọng, v.v.):

1. **KHÔNG tự ý sửa** — chỉ ghi chú vào `TASK.md` dưới mục `⚠️ Phát hiện vấn đề`.
2. **Báo cáo cho user** ngay sau khi hoàn thành nhiệm vụ chính.
3. Chờ sự chấp thuận trước khi mở rộng phạm vi.

---

## 6. XÁC NHẬN HOÀN THÀNH

Sau khi hoàn thành, agent xác nhận:
- [ ] Nhiệm vụ được giao đã hoàn thành đúng yêu cầu.
- [ ] Không có logic/method/class nào bị xóa nhầm.
- [ ] File backup đã được tạo (nếu rủi ro cao).
- [ ] File tạm đã được dọn dẹp.
- [ ] `TASK.md` đã được cập nhật.
