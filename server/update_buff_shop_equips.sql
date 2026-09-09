-- =========================================================================
-- KPAH - UPDATE BUFF TRANG BỊ MUA BẰNG XU
-- Ngày: 2026-09-09
-- Mô tả: Tăng sức mạnh (thủ giáp +40%-60%, công vũ khí +30%-50%) và điều chỉnh giá +25%
-- Lưu ý: Server KPAH đã tự động áp dụng buff này trong Manager.java khi nạp vào RAM.
-- Script này dùng để đồng bộ vĩnh viễn vào Database MariaDB trên Termux (nếu cần).
-- =========================================================================

USE `kpah`;

-- Tăng nhẹ 25% giá bán xu cho các trang bị thường bán ở shop
UPDATE `item_equipment` 
SET `price` = ROUND(`price` * 1.25)
WHERE `colorItem` = 0 AND `ndayLoan` = 0 AND `price` > 0;
