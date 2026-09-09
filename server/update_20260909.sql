-- =========================================================
-- KPAH Update Script - 2026-09-09
-- Cập nhật Tinh anh huyết, Dược phẩm HP/MP, Thẻ mua bán
-- =========================================================

-- 1. Cập nhật tên và EXP 10x cho Tinh anh huyết (Lọ Luyện Kinh Dược)
UPDATE potion_template SET idImage = 40, name = 'Tinh anh huyết (Sơ Cấp)\nSử dụng nhận 35.000 kinh nghiệm.' WHERE id = 108;
UPDATE potion_template SET idImage = 40, name = 'Tinh anh huyết (Trung Cấp)\nSử dụng nhận 250.000 kinh nghiệm.' WHERE id = 109;
UPDATE potion_template SET idImage = 40, name = 'Tinh anh huyết (Cao Cấp)\nSử dụng nhận 900.000 kinh nghiệm.' WHERE id = 110;
UPDATE potion_template SET idImage = 40, name = 'Tinh anh huyết (Siêu Cấp)\nSử dụng nhận 2.200.000 kinh nghiệm.' WHERE id = 111;

-- 2. Cập nhật Thẻ mua bán & Rương Tinh Anh & Tinh Anh Đan
UPDATE potion_template SET idImage = 39, name = 'Thẻ mua bán\nCho phép tự động sửa chữa trang bị khi bị hỏng (cần xu).' WHERE id = 33;
UPDATE shop_template SET name = 'Thẻ mua bán' WHERE id = 13;
UPDATE potion_template SET idImage = 68, name = 'Rương Tinh Anh (Bậc 1)\nMở nhận: Lượng, Tinh anh huyết Sơ Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv1-9.' WHERE id = 106;
UPDATE potion_template SET idImage = 68, name = 'Rương Tinh Anh (Bậc 2)\nMở nhận: Lượng, Tinh anh huyết Trung Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv10-19.' WHERE id = 160;
UPDATE potion_template SET idImage = 67, name = 'Rương Tinh Anh (Bậc 3)\nMở nhận: Lượng, Tinh anh huyết Cao Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv20-29.' WHERE id = 161;
UPDATE potion_template SET idImage = 67, name = 'Rương Tinh Anh (Bậc 4)\nMở nhận: Lượng, Tinh anh huyết Siêu Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv30-35.' WHERE id = 162;
UPDATE potion_template SET idImage = 158, name = 'Tinh Anh Đan\nTăng 20% sát thương, 20% giáp và 20% HP trong 3 phút.' WHERE id = 107;

-- 3. Cập nhật dược phẩm HP và MP (HP > MP)
UPDATE potion_template SET recovered = 500, name = 'HP nhỏ\nHồi phục 500 HP.' WHERE id = 1;
UPDATE potion_template SET recovered = 1500, name = 'HP vừa\nHồi phục 1.500 HP.' WHERE id = 2;
UPDATE potion_template SET recovered = 4000, name = 'HP to\nHồi phục 4.000 HP.' WHERE id = 3;
UPDATE potion_template SET recovered = 300, name = 'MP nhỏ\nHồi phục 300 MP.' WHERE id = 4;
UPDATE potion_template SET recovered = 1000, name = 'MP vừa\nHồi phục 1.000 MP.' WHERE id = 5;
UPDATE potion_template SET recovered = 2500, name = 'MP to\nHồi phục 2.500 MP.' WHERE id = 6;
UPDATE potion_template SET recovered = 3000, name = 'HP đ.biệt vừa\nHồi phục 3.000 HP tức thì.' WHERE id = 21;
UPDATE potion_template SET recovered = 8000, name = 'HP đ.biệt to\nHồi phục 8.000 HP tức thì.' WHERE id = 22;
UPDATE potion_template SET recovered = 2000, name = 'MP đ.biệt vừa\nHồi phục 2.000 MP tức thì.' WHERE id = 23;
UPDATE potion_template SET recovered = 5000, name = 'MP đ.biệt to\nHồi phục 5.000 MP tức thì.' WHERE id = 24;
UPDATE potion_template SET recovered = 15000, name = 'HP 15k\nHồi phục 15.000 HP tức thì.' WHERE id = 93;
UPDATE potion_template SET recovered = 30000, name = 'HP 30k\nHồi phục 30.000 HP tức thì.' WHERE id = 94;
UPDATE potion_template SET recovered = 10000, name = 'MP 10k\nHồi phục 10.000 MP tức thì.' WHERE id = 95;
UPDATE potion_template SET recovered = 20000, name = 'MP 20k\nHồi phục 20.000 MP tức thì.' WHERE id = 96;

-- 4. Cập nhật cấu hình VALUE_MP_HP trong others
UPDATE others SET data = '[[500,1500,4000,3000,8000,15000,30000],[300,1000,2500,2000,5000,10000,20000]]' WHERE type = 'VALUE_MP_HP';

-- 5. Nâng cấp các cột bảng players sang MEDIUMTEXT utf8mb4 (Fix dứt điểm lỗi Data truncation: Data too long for column 'info')
ALTER TABLE `players` 
  MODIFY COLUMN `info` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `location` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `point` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `inventory` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `skills` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemBody` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemBag` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemBox` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemPotion` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemQuest` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemGem` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemGemLock` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemSold` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemAnimal` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  MODIFY COLUMN `itemAnimalExpiry` MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL;
