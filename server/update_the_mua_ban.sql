-- =========================================================
-- KPAH Update Script - Thẻ Mua Bán (Hạn 3 ngày, Giá 10 Lượng, Mô tả chuẩn)
-- =========================================================

-- 1. Cập nhật Thẻ mua bán dạng ItemEquip (ID 675 trong Gian hàng Hắc Ngưu)
-- ndayLoan = 4320 phút = 3 ngày (3 * 24 * 60)
UPDATE `item_equipment`
SET
    `name` = 'Thẻ mua bán. Cho phép tự động sửa chữa trang bị ở mọi nơi với giá 150%. Sử dụng trực tiếp để giao dịch với Hắc Ngưu.',
    `ndayLoan` = 4320,
    `price` = 10,
    `colorItem` = 1
WHERE
    `id` = 675;

-- 2. Cập nhật Thẻ mua bán dạng Potion (ID 33)
UPDATE `potion_template`
SET
    `name` = 'Thẻ mua bán. Cho phép tự động sửa chữa trang bị ở mọi nơi với giá 150%. Sử dụng trực tiếp để giao dịch với Hắc Ngưu.',
    `price` = 10
WHERE
    `id` = 33;