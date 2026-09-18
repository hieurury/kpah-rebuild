-- =========================================================================
-- HỆ THỐNG SỰ KIỆN & QUÀ TẶNG KPAH
-- Thêm Potion Template ID 165: Rương Kho Báu (Cấp 30)
-- Mở ra nhận đủ nguyên liệu chế trọn bộ đồ Cấp 30 và vũ khí Cấp 31 Nhất phẩm
-- =========================================================================

INSERT INTO `potion_template` (`id`, `name`, `name2`, `idImage`, `delay`, `isTrade`, `price`, `recovered`) 
VALUES (165, 'Rương Kho Báu (Cấp 30)\nMở ra nhận đủ nguyên liệu chế trọn bộ trang bị cấp 30 và vũ khí cấp 31 Nhất phẩm.', 'ruongkhobau30', 67, 0, 1, 0, 0)
ON DUPLICATE KEY UPDATE 
    `name` = VALUES(`name`), 
    `name2` = VALUES(`name2`),
    `idImage` = VALUES(`idImage`), 
    `delay` = VALUES(`delay`),
    `isTrade` = VALUES(`isTrade`),
    `price` = VALUES(`price`),
    `recovered` = VALUES(`recovered`);
