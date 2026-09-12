-- =========================================================
-- KPAH Update Script - 2026-09-12
-- Cập nhật tên và màu sắc hiển thị thuộc tính trang bị (item_attribute)
-- =========================================================

-- 1. Cập nhật các chỉ số chế tạo & chỉ số cơ bản
UPDATE item_attribute SET name = 'Tăng HP', isPercent = 1, colorPaint = 2 WHERE id = 7;
UPDATE item_attribute SET name = 'Tăng MP', isPercent = 1, colorPaint = 1 WHERE id = 8;
UPDATE item_attribute SET name = 'May mắn', isPercent = 0, colorPaint = 5 WHERE id = 9;
UPDATE item_attribute SET name = 'Tăng HP', isPercent = 0, colorPaint = 2 WHERE id = 33;
UPDATE item_attribute SET name = 'Tăng MP', isPercent = 0, colorPaint = 1 WHERE id = 34;

-- 2. Chỉ số đặc biệt (Nhất phẩm) -> Màu Vàng (colorPaint = 5)
UPDATE item_attribute SET colorPaint = 5 WHERE id IN (118, 30, 31, 41, 65, 115, 117);

-- 3. Chỉ số thường -> Màu Xanh dương (colorPaint = 1)
UPDATE item_attribute SET colorPaint = 1 WHERE id IN (0, 1, 6, 10, 11, 12, 13, 88);
