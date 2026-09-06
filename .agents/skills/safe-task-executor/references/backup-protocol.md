# Backup Protocol — Quy Trình Backup Cho Thay Đổi Rủi Ro Cao

Áp dụng khi: tái cấu trúc lớn, xóa class/method, thay đổi > 30% nội dung file, thay đổi kiến trúc.

---

## Bước 1: Tạo thư mục backup

```bash
# Tạo thư mục _backup/ cùng cấp với file cần thay đổi
mkdir -p <thư_mục_chứa_file>/_backup/
```

Ví dụ: nếu file cần sửa là `server/KPAH/src/services/ChatService.java`, tạo:
```
server/KPAH/src/services/_backup/
```

## Bước 2: Sao lưu file gốc

Format tên backup: `<tên_file>.bak.<YYYYMMDD_HHMM>`

```bash
cp server/KPAH/src/services/ChatService.java \
   server/KPAH/src/services/_backup/ChatService.java.bak.$(date +%Y%m%d_%H%M)
```

## Bước 3: Tạo file tạm để test (nếu cần)

Nếu cần thử nghiệm logic mới trước khi áp dụng vào file chính:

```bash
# Tạo thư mục _tmp/ ở thư mục gốc dự án
mkdir -p _tmp/

# Copy file gốc thành file tạm để làm việc
cp server/KPAH/src/services/ChatService.java \
   _tmp/ChatService_test.java
```

> **Lưu ý**: File tạm trong `_tmp/` KHÔNG phải Java class hợp lệ cho compiler, chỉ dùng để tham khảo logic.

## Bước 4: Thực hiện thay đổi trên file gốc

- Chỉnh sửa file gốc theo từng bước nhỏ.
- Sau mỗi bước, kiểm tra xem logic còn đúng không (đọc lại, check dependency).
- **KHÔNG xóa code cũ ngay** — comment out trước nếu không chắc chắn.

## Bước 5: Xác nhận và dọn dẹp

Sau khi chắc chắn thay đổi hoạt động:
```bash
# Xóa file tạm
rm _tmp/ChatService_test.java

# Giữ lại backup ít nhất cho đến khi user xác nhận build/run thành công
# Sau đó có thể xóa nếu muốn:
# rm server/KPAH/src/services/_backup/ChatService.java.bak.*
```

## Bước 6: Ghi log vào TASK.md

Ghi rõ đường dẫn file backup trong mục **Ghi chú** của entry TASK.md.

---

## Ví Dụ Thực Tế — Tái Cấu Trúc Manager.java

```
Nhiệm vụ: Tách logic khởi tạo trong Manager.java thành ManagerInitializer.java

Files bị ảnh hưởng:
- server/KPAH/src/manager/Manager.java (sửa đổi lớn)
- server/KPAH/src/manager/ManagerInitializer.java (tạo mới)

Backup được tạo:
- server/KPAH/src/manager/_backup/Manager.java.bak.20260902_2100

File tạm:
- _tmp/Manager_refactor_notes.java (ghi chú logic cũ)
```

---

## Cấu Trúc `_backup/` Khuyến Nghị

```
_backup/
├── ChatService.java.bak.20260902_2100
├── MapService.java.bak.20260901_1530
└── Manager.java.bak.20260902_2045
```

> Các file trong `_backup/` nên được thêm vào `.gitignore` để không commit nhầm.
