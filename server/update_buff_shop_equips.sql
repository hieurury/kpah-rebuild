-- =========================================================================
-- KPAH - SCRIPT CẬP NHẬT CHẤT LƯỢNG VÀ GIÁ TRANG BỊ XU (DIRECT SQL)
-- Ngày cập nhật: 2026-09-09
-- Mục đích: Điều chỉnh trực tiếp trong Database thay vì buff động trong RAM Java.
-- Hiệu quả: Tiết kiệm tối đa RAM, dữ liệu vĩnh viễn, không tốn tài nguyên server.
-- Quy tắc điều chỉnh:
--   1. Giáp phòng thủ (Áo, Quần, Nón, Giày, Găng): Tăng thủ vật và ma ~55-60% (+10..+50 DEF).
--   2. Vũ khí (Kiếm, Đao, Bút, Búa, Cung): Tăng sát thương tấn công ~42% (+15..+130 ATK).
--   3. Trang sức (Nhẫn, Dây chuyền, Ngọc): Tăng công/HP hỗ trợ ~40-50%.
--   4. Giá xu (price): Điều chỉnh tăng 25% theo chuẩn kinh tế game.
-- =========================================================================

USE `kpah`;

START TRANSACTION;

-- -------------------------------------------------------------------------
-- ÁO GIÁP (TYPE 0) - Tăng Thủ Vật & Thủ Ma ~55-60%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[0,20,1,1,0,0,20,0,0,0]', `price` = 625 WHERE `id` = 1; -- ID   1: Áo bà ba (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [0, 10, 1, 1, 0, 0, 10, 0, 0, 0] -> [0,20,1,1,0,0,20,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,20,1,1,0,0,20,0,0,0]', `price` = 625 WHERE `id` = 2; -- ID   2: (ở trần) (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [0, 10, 1, 1, 0, 0, 10, 0, 0, 0] -> [0,20,1,1,0,0,20,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,26,1,2,0,0,26,0,0,0]', `price` = 1250 WHERE `id` = 3; -- ID   3: Áo đai thô (nữ) (Cấp  4) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 15, 1, 2, 0, 0, 15, 0, 0, 0] -> [0,26,1,2,0,0,26,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,26,1,2,0,0,26,0,0,0]', `price` = 1250 WHERE `id` = 4; -- ID   4: Áo đai thô (nam) (Cấp  4) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 15, 1, 2, 0, 0, 15, 0, 0, 0] -> [0,26,1,2,0,0,26,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,34,2,2,0,0,34,0,0,0]', `price` = 2500 WHERE `id` = 5; -- ID   5: Áo thổ cẩm (nữ) (Cấp  9) | Giá: 2,000 -> 2,500 xu | Chỉ số: [0, 20, 2, 2, 0, 0, 20, 0, 0, 0] -> [0,34,2,2,0,0,34,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,34,2,2,0,0,34,0,0,0]', `price` = 2500 WHERE `id` = 6; -- ID   6: Áo thổ cẩm (nam) (Cấp  9) | Giá: 2,000 -> 2,500 xu | Chỉ số: [0, 20, 2, 2, 0, 0, 20, 0, 0, 0] -> [0,34,2,2,0,0,34,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,42,2,3,0,0,42,0,0,0]', `price` = 6250 WHERE `id` = 7; -- ID   7: Áo bông (nữ) (Cấp 14) | Giá: 5,000 -> 6,250 xu | Chỉ số: [0, 25, 2, 3, 0, 0, 25, 0, 0, 0] -> [0,42,2,3,0,0,42,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,42,2,3,0,0,42,0,0,0]', `price` = 6250 WHERE `id` = 8; -- ID   8: Áo bông (nam) (Cấp 14) | Giá: 5,000 -> 6,250 xu | Chỉ số: [0, 25, 2, 3, 0, 0, 25, 0, 0, 0] -> [0,42,2,3,0,0,42,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,49,3,3,0,0,49,0,0,0]', `price` = 12500 WHERE `id` = 9; -- ID   9: Áo lụa (nữ) (Cấp 19) | Giá: 10,000 -> 12,500 xu | Chỉ số: [0, 30, 3, 3, 0, 0, 30, 0, 0, 0] -> [0,49,3,3,0,0,49,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,49,3,3,0,0,49,0,0,0]', `price` = 12500 WHERE `id` = 10; -- ID  10: Áo lụa (nam) (Cấp 19) | Giá: 10,000 -> 12,500 xu | Chỉ số: [0, 30, 3, 3, 0, 0, 30, 0, 0, 0] -> [0,49,3,3,0,0,49,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,57,0,3,0,0,57,0,0,0]', `price` = 25000 WHERE `id` = 11; -- ID  11: Áo tơ tằm (nữ) (Cấp 24) | Giá: 20,000 -> 25,000 xu | Chỉ số: [0, 35, 0, 3, 0, 0, 35, 0, 0, 0] -> [0,57,0,3,0,0,57,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,57,0,3,0,0,57,0,0,0]', `price` = 25000 WHERE `id` = 12; -- ID  12: Áo tơ tằm (nam) (Cấp 24) | Giá: 20,000 -> 25,000 xu | Chỉ số: [0, 35, 0, 3, 0, 0, 35, 0, 0, 0] -> [0,57,0,3,0,0,57,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,65,1,4,0,0,65,0,0,0]', `price` = 31250 WHERE `id` = 13; -- ID  13: Áo nhung (nữ) (Cấp 29) | Giá: 25,000 -> 31,250 xu | Chỉ số: [0, 40, 1, 4, 0, 0, 40, 0, 0, 0] -> [0,65,1,4,0,0,65,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,65,1,4,0,0,65,0,0,0]', `price` = 31250 WHERE `id` = 14; -- ID  14: Áo nhung (nam) (Cấp 29) | Giá: 25,000 -> 31,250 xu | Chỉ số: [0, 40, 1, 4, 0, 0, 40, 0, 0, 0] -> [0,65,1,4,0,0,65,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,73,1,4,0,0,73,0,0,0]', `price` = 42500 WHERE `id` = 15; -- ID  15: Áo gấm (nữ) (Cấp 34) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 45, 1, 4, 0, 0, 45, 0, 0, 0] -> [0,73,1,4,0,0,73,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,73,1,4,0,0,73,0,0,0]', `price` = 42500 WHERE `id` = 16; -- ID  16: Áo gấm (nam) (Cấp 34) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 45, 1, 4, 0, 0, 45, 0, 0, 0] -> [0,73,1,4,0,0,73,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,81,0,4,0,0,81,0,0,0]', `price` = 50000 WHERE `id` = 17; -- ID  17: Khoái linh thượng y (nữ) (Cấp 39) | Giá: 40,000 -> 50,000 xu | Chỉ số: [0, 50, 0, 4, 0, 0, 50, 0, 0, 0] -> [0,81,0,4,0,0,81,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,81,0,4,0,0,81,0,0,0]', `price` = 50000 WHERE `id` = 18; -- ID  18: Khoái linh thượng y (nam) (Cấp 39) | Giá: 40,000 -> 50,000 xu | Chỉ số: [0, 50, 0, 4, 0, 0, 50, 0, 0, 0] -> [0,81,0,4,0,0,81,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,88,0,4,0,0,88,0,0,0]', `price` = 55000 WHERE `id` = 19; -- ID  19: Cẩm trác y (nữ) (Cấp 44) | Giá: 44,000 -> 55,000 xu | Chỉ số: [0, 55, 0, 4, 0, 0, 55, 0, 0, 0] -> [0,88,0,4,0,0,88,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,88,0,4,0,0,88,0,0,0]', `price` = 55000 WHERE `id` = 20; -- ID  20: Cẩm trác y (nam) (Cấp 44) | Giá: 44,000 -> 55,000 xu | Chỉ số: [0, 55, 0, 4, 0, 0, 55, 0, 0, 0] -> [0,88,0,4,0,0,88,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,96,0,5,0,0,96,0,0,0]', `price` = 61250 WHERE `id` = 21; -- ID  21: Hổ bào (nữ) (Cấp 49) | Giá: 49,000 -> 61,250 xu | Chỉ số: [0, 60, 0, 5, 0, 0, 60, 0, 0, 0] -> [0,96,0,5,0,0,96,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,96,0,5,0,0,96,0,0,0]', `price` = 61250 WHERE `id` = 22; -- ID  22: Hổ bào (nam) (Cấp 49) | Giá: 49,000 -> 61,250 xu | Chỉ số: [0, 60, 0, 5, 0, 0, 60, 0, 0, 0] -> [0,96,0,5,0,0,96,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,104,0,5,0,0,104,0,0,0]', `price` = 67500 WHERE `id` = 23; -- ID  23: Sư vương bào (nữ) (Cấp 54) | Giá: 54,000 -> 67,500 xu | Chỉ số: [0, 65, 0, 5, 0, 0, 65, 0, 0, 0] -> [0,104,0,5,0,0,104,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,104,0,5,0,0,104,0,0,0]', `price` = 67500 WHERE `id` = 24; -- ID  24: Sư vương bào (nam) (Cấp 54) | Giá: 54,000 -> 67,500 xu | Chỉ số: [0, 65, 0, 5, 0, 0, 65, 0, 0, 0] -> [0,104,0,5,0,0,104,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,111,0,5,0,0,111,0,0,0]', `price` = 73750 WHERE `id` = 25; -- ID  25: Kim long bào (nữ) (Cấp 59) | Giá: 59,000 -> 73,750 xu | Chỉ số: [0, 70, 0, 5, 0, 0, 70, 0, 0, 0] -> [0,111,0,5,0,0,111,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,111,0,5,0,0,111,0,0,0]', `price` = 73750 WHERE `id` = 26; -- ID  26: Kim long bào (nam) (Cấp 59) | Giá: 59,000 -> 73,750 xu | Chỉ số: [0, 70, 0, 5, 0, 0, 70, 0, 0, 0] -> [0,111,0,5,0,0,111,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,119,0,5,0,0,119,0,0,0]', `price` = 100000 WHERE `id` = 467; -- ID 467: Phi phong bào(nữ) (Cấp 64) | Giá: 80,000 -> 100,000 xu | Chỉ số: [0, 75, 0, 5, 0, 0, 75, 0, 0, 0] -> [0,119,0,5,0,0,119,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,119,0,5,0,0,119,0,0,0]', `price` = 100000 WHERE `id` = 468; -- ID 468: Phi phong bào(nam) (Cấp 64) | Giá: 80,000 -> 100,000 xu | Chỉ số: [0, 75, 0, 5, 0, 0, 75, 0, 0, 0] -> [0,119,0,5,0,0,119,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,127,0,5,0,0,127,0,0,0]', `price` = 125000 WHERE `id` = 469; -- ID 469: Tử quang bào(nữ) (Cấp 69) | Giá: 100,000 -> 125,000 xu | Chỉ số: [0, 80, 0, 5, 0, 0, 80, 0, 0, 0] -> [0,127,0,5,0,0,127,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,127,0,5,0,0,127,0,0,0]', `price` = 125000 WHERE `id` = 470; -- ID 470: Tử quang bào(nam) (Cấp 69) | Giá: 100,000 -> 125,000 xu | Chỉ số: [0, 80, 0, 5, 0, 0, 80, 0, 0, 0] -> [0,127,0,5,0,0,127,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,135,0,5,0,0,135,0,0,0]', `price` = 150000 WHERE `id` = 471; -- ID 471: Đại hùng bào(nữ) (Cấp 74) | Giá: 120,000 -> 150,000 xu | Chỉ số: [0, 85, 0, 5, 0, 0, 85, 0, 0, 0] -> [0,135,0,5,0,0,135,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,135,0,5,0,0,135,0,0,0]', `price` = 150000 WHERE `id` = 472; -- ID 472: Đại hùng bào(nữ) (Cấp 74) | Giá: 120,000 -> 150,000 xu | Chỉ số: [0, 85, 0, 5, 0, 0, 85, 0, 0, 0] -> [0,135,0,5,0,0,135,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,143,0,5,0,0,143,0,0,0]', `price` = 175000 WHERE `id` = 473; -- ID 473: Phi yến bào (nam) (Cấp 79) | Giá: 140,000 -> 175,000 xu | Chỉ số: [0, 90, 0, 5, 0, 0, 90, 0, 0, 0] -> [0,143,0,5,0,0,143,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,143,0,5,0,0,143,0,0,0]', `price` = 175000 WHERE `id` = 474; -- ID 474: Phi yến bào (nam) (Cấp 79) | Giá: 140,000 -> 175,000 xu | Chỉ số: [0, 90, 0, 5, 0, 0, 90, 0, 0, 0] -> [0,143,0,5,0,0,143,0,0,0]

-- -------------------------------------------------------------------------
-- QUẦN GIÁP (TYPE 1) - Tăng Thủ Vật & Thủ Ma ~55-60%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[0,17,0,0,0,0,17,0,0,0]', `price` = 625 WHERE `id` = 27; -- ID  27: Quần bà ba (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [0, 7, 0, 0, 0, 0, 7, 0, 0, 0] -> [0,17,0,0,0,0,17,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,17,0,0,0,0,17,0,0,0]', `price` = 625 WHERE `id` = 28; -- ID  28: Quần đùi (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [0, 7, 0, 0, 0, 0, 7, 0, 0, 0] -> [0,17,0,0,0,0,17,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,22,0,1,0,0,22,0,0,0]', `price` = 1000 WHERE `id` = 29; -- ID  29: Quần đai thô (nữ) (Cấp  4) | Giá: 800 -> 1,000 xu | Chỉ số: [0, 12, 0, 1, 0, 0, 12, 0, 0, 0] -> [0,22,0,1,0,0,22,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,22,0,1,0,0,22,0,0,0]', `price` = 1000 WHERE `id` = 30; -- ID  30: Quần đai thô (nam) (Cấp  4) | Giá: 800 -> 1,000 xu | Chỉ số: [0, 12, 0, 1, 0, 0, 12, 0, 0, 0] -> [0,22,0,1,0,0,22,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,29,0,1,0,0,29,0,0,0]', `price` = 1250 WHERE `id` = 31; -- ID  31: Quần thổ cẩm (nữ) (Cấp  9) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 17, 0, 1, 0, 0, 17, 0, 0, 0] -> [0,29,0,1,0,0,29,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,29,0,1,0,0,29,0,0,0]', `price` = 1250 WHERE `id` = 32; -- ID  32: Quần thổ cẩm (nam) (Cấp  9) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 17, 0, 1, 0, 0, 17, 0, 0, 0] -> [0,29,0,1,0,0,29,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,37,0,2,0,0,37,0,0,0]', `price` = 3750 WHERE `id` = 33; -- ID  33: Quần bông (nữ) (Cấp 14) | Giá: 3,000 -> 3,750 xu | Chỉ số: [0, 22, 0, 2, 0, 0, 22, 0, 0, 0] -> [0,37,0,2,0,0,37,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,37,0,2,0,0,37,0,0,0]', `price` = 3750 WHERE `id` = 34; -- ID  34: Quần bông (nam) (Cấp 14) | Giá: 3,000 -> 3,750 xu | Chỉ số: [0, 22, 0, 2, 0, 0, 22, 0, 0, 0] -> [0,37,0,2,0,0,37,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,45,0,2,0,0,45,0,0,0]', `price` = 10000 WHERE `id` = 35; -- ID  35: Quần lụa (nữ) (Cấp 19) | Giá: 8,000 -> 10,000 xu | Chỉ số: [0, 27, 0, 2, 0, 0, 27, 0, 0, 0] -> [0,45,0,2,0,0,45,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,45,0,2,0,0,45,0,0,0]', `price` = 10000 WHERE `id` = 36; -- ID  36: Quần lụa (nam) (Cấp 19) | Giá: 8,000 -> 10,000 xu | Chỉ số: [0, 27, 0, 2, 0, 0, 27, 0, 0, 0] -> [0,45,0,2,0,0,45,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,53,0,2,0,0,53,0,0,0]', `price` = 17500 WHERE `id` = 37; -- ID  37: Quần tơ tằm (nữ) (Cấp 24) | Giá: 14,000 -> 17,500 xu | Chỉ số: [0, 32, 0, 2, 0, 0, 32, 0, 0, 0] -> [0,53,0,2,0,0,53,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,53,0,2,0,0,53,0,0,0]', `price` = 17500 WHERE `id` = 38; -- ID  38: Quần tơ tằm (nam) (Cấp 24) | Giá: 14,000 -> 17,500 xu | Chỉ số: [0, 32, 0, 2, 0, 0, 32, 0, 0, 0] -> [0,53,0,2,0,0,53,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,60,0,3,0,0,60,0,0,0]', `price` = 23750 WHERE `id` = 39; -- ID  39: Quần nhung (nữ) (Cấp 29) | Giá: 19,000 -> 23,750 xu | Chỉ số: [0, 37, 0, 3, 0, 0, 37, 0, 0, 0] -> [0,60,0,3,0,0,60,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,60,0,3,0,0,60,0,0,0]', `price` = 23750 WHERE `id` = 40; -- ID  40: Quần nhung (nam) (Cấp 29) | Giá: 19,000 -> 23,750 xu | Chỉ số: [0, 37, 0, 3, 0, 0, 37, 0, 0, 0] -> [0,60,0,3,0,0,60,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,68,0,3,0,0,68,0,0,0]', `price` = 30000 WHERE `id` = 41; -- ID  41: Quần gấm (nữ) (Cấp 34) | Giá: 24,000 -> 30,000 xu | Chỉ số: [0, 42, 0, 3, 0, 0, 42, 0, 0, 0] -> [0,68,0,3,0,0,68,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,68,0,3,0,0,68,0,0,0]', `price` = 30000 WHERE `id` = 42; -- ID  42: Quần gấm (nam) (Cấp 34) | Giá: 24,000 -> 30,000 xu | Chỉ số: [0, 42, 0, 3, 0, 0, 42, 0, 0, 0] -> [0,68,0,3,0,0,68,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,76,0,3,0,0,76,0,0,0]', `price` = 36250 WHERE `id` = 43; -- ID  43: Quần khoái linh (nữ) (Cấp 39) | Giá: 29,000 -> 36,250 xu | Chỉ số: [0, 47, 0, 3, 0, 0, 47, 0, 0, 0] -> [0,76,0,3,0,0,76,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,76,0,3,0,0,76,0,0,0]', `price` = 36250 WHERE `id` = 44; -- ID  44: Quần khoái linh (nam) (Cấp 39) | Giá: 29,000 -> 36,250 xu | Chỉ số: [0, 47, 0, 3, 0, 0, 47, 0, 0, 0] -> [0,76,0,3,0,0,76,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,84,0,3,0,0,84,0,0,0]', `price` = 42500 WHERE `id` = 45; -- ID  45: Quần cẩm trác (nữ) (Cấp 44) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 52, 0, 3, 0, 0, 52, 0, 0, 0] -> [0,84,0,3,0,0,84,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,84,0,3,0,0,84,0,0,0]', `price` = 42500 WHERE `id` = 46; -- ID  46: Quần cẩm trác (nam) (Cấp 44) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 52, 0, 3, 0, 0, 52, 0, 0, 0] -> [0,84,0,3,0,0,84,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,91,0,4,0,0,91,0,0,0]', `price` = 50000 WHERE `id` = 47; -- ID  47: Hổ mao (nữ) (Cấp 49) | Giá: 40,000 -> 50,000 xu | Chỉ số: [0, 57, 0, 4, 0, 0, 57, 0, 0, 0] -> [0,91,0,4,0,0,91,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,91,0,4,0,0,91,0,0,0]', `price` = 50000 WHERE `id` = 48; -- ID  48: Hổ mao (nam) (Cấp 49) | Giá: 40,000 -> 50,000 xu | Chỉ số: [0, 57, 0, 4, 0, 0, 57, 0, 0, 0] -> [0,91,0,4,0,0,91,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,99,0,4,0,0,99,0,0,0]', `price` = 56250 WHERE `id` = 49; -- ID  49: Sư vương mao (nữ) (Cấp 54) | Giá: 45,000 -> 56,250 xu | Chỉ số: [0, 62, 0, 4, 0, 0, 62, 0, 0, 0] -> [0,99,0,4,0,0,99,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,99,0,4,0,0,99,0,0,0]', `price` = 56250 WHERE `id` = 50; -- ID  50: Sư vương mao (nam) (Cấp 54) | Giá: 45,000 -> 56,250 xu | Chỉ số: [0, 62, 0, 4, 0, 0, 62, 0, 0, 0] -> [0,99,0,4,0,0,99,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,107,0,4,0,0,107,0,0,0]', `price` = 62500 WHERE `id` = 51; -- ID  51: Quần kim long (nữ) (Cấp 59) | Giá: 50,000 -> 62,500 xu | Chỉ số: [0, 67, 0, 4, 0, 0, 67, 0, 0, 0] -> [0,107,0,4,0,0,107,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,107,0,4,0,0,107,0,0,0]', `price` = 62500 WHERE `id` = 52; -- ID  52: Quần kim long (nam) (Cấp 59) | Giá: 50,000 -> 62,500 xu | Chỉ số: [0, 67, 0, 4, 0, 0, 67, 0, 0, 0] -> [0,107,0,4,0,0,107,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,115,0,4,0,0,115,0,0,0]', `price` = 87500 WHERE `id` = 475; -- ID 475: Quần phi long(nữ) (Cấp 64) | Giá: 70,000 -> 87,500 xu | Chỉ số: [0, 72, 0, 4, 0, 0, 72, 0, 0, 0] -> [0,115,0,4,0,0,115,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,115,0,4,0,0,115,0,0,0]', `price` = 87500 WHERE `id` = 476; -- ID 476: Quần phi long(nam) (Cấp 64) | Giá: 70,000 -> 87,500 xu | Chỉ số: [0, 72, 0, 4, 0, 0, 72, 0, 0, 0] -> [0,115,0,4,0,0,115,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,122,0,4,0,0,122,0,0,0]', `price` = 112500 WHERE `id` = 477; -- ID 477: Quần Tử quang(nữ) (Cấp 69) | Giá: 90,000 -> 112,500 xu | Chỉ số: [0, 77, 0, 4, 0, 0, 77, 0, 0, 0] -> [0,122,0,4,0,0,122,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,122,0,4,0,0,122,0,0,0]', `price` = 112500 WHERE `id` = 478; -- ID 478: Quần Tử quang(nam) (Cấp 69) | Giá: 90,000 -> 112,500 xu | Chỉ số: [0, 77, 0, 4, 0, 0, 77, 0, 0, 0] -> [0,122,0,4,0,0,122,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,130,0,4,0,0,130,0,0,0]', `price` = 137500 WHERE `id` = 479; -- ID 479: Quần Đại hùng(nữ) (Cấp 74) | Giá: 110,000 -> 137,500 xu | Chỉ số: [0, 82, 0, 4, 0, 0, 82, 0, 0, 0] -> [0,130,0,4,0,0,130,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,130,0,4,0,0,130,0,0,0]', `price` = 137500 WHERE `id` = 480; -- ID 480: Quần Đại hùng(nam) (Cấp 74) | Giá: 110,000 -> 137,500 xu | Chỉ số: [0, 82, 0, 4, 0, 0, 82, 0, 0, 0] -> [0,130,0,4,0,0,130,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,138,0,4,0,0,138,0,0,0]', `price` = 162500 WHERE `id` = 481; -- ID 481: Quần Phi yến(nữ) (Cấp 79) | Giá: 130,000 -> 162,500 xu | Chỉ số: [0, 87, 0, 4, 0, 0, 87, 0, 0, 0] -> [0,138,0,4,0,0,138,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,138,0,4,0,0,138,0,0,0]', `price` = 162500 WHERE `id` = 482; -- ID 482: Quần Phi yến(nam) (Cấp 79) | Giá: 130,000 -> 162,500 xu | Chỉ số: [0, 87, 0, 4, 0, 0, 87, 0, 0, 0] -> [0,138,0,4,0,0,138,0,0,0]

-- -------------------------------------------------------------------------
-- NÓN GIÁP (TYPE 2) - Tăng Thủ Vật & Thủ Ma ~55-60%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[0,15,0,1,0,0,15,0,0,0]', `price` = 625 WHERE `id` = 53; -- ID  53: Băng đô (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [0, 5, 0, 1, 0, 0, 5, 0, 0, 0] -> [0,15,0,1,0,0,15,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,15,0,1,0,0,15,0,0,0]', `price` = 625 WHERE `id` = 54; -- ID  54: Khăn (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [0, 5, 0, 1, 0, 0, 5, 0, 0, 0] -> [0,15,0,1,0,0,15,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,20,0,2,0,0,20,0,0,0]', `price` = 1000 WHERE `id` = 55; -- ID  55: Nón dây gai (nữ) (Cấp  4) | Giá: 800 -> 1,000 xu | Chỉ số: [0, 10, 0, 2, 0, 0, 10, 0, 0, 0] -> [0,20,0,2,0,0,20,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,20,0,2,0,0,20,0,0,0]', `price` = 1000 WHERE `id` = 56; -- ID  56: Nón dây gai (nam) (Cấp  4) | Giá: 800 -> 1,000 xu | Chỉ số: [0, 10, 0, 2, 0, 0, 10, 0, 0, 0] -> [0,20,0,2,0,0,20,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,26,0,2,0,0,26,0,0,0]', `price` = 1250 WHERE `id` = 57; -- ID  57: Nón lam trúc (nữ) (Cấp  9) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 15, 0, 2, 0, 0, 15, 0, 0, 0] -> [0,26,0,2,0,0,26,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,26,0,2,0,0,26,0,0,0]', `price` = 1250 WHERE `id` = 58; -- ID  58: Nón lam trúc (nam) (Cấp  9) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 15, 0, 2, 0, 0, 15, 0, 0, 0] -> [0,26,0,2,0,0,26,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,34,0,2,0,0,34,0,0,0]', `price` = 2500 WHERE `id` = 59; -- ID  59: Nón hắc trúc (nữ) (Cấp 14) | Giá: 2,000 -> 2,500 xu | Chỉ số: [0, 20, 0, 2, 0, 0, 20, 0, 0, 0] -> [0,34,0,2,0,0,34,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,34,0,2,0,0,34,0,0,0]', `price` = 2500 WHERE `id` = 60; -- ID  60: Nón hắc trúc (nam) (Cấp 14) | Giá: 2,000 -> 2,500 xu | Chỉ số: [0, 20, 0, 2, 0, 0, 20, 0, 0, 0] -> [0,34,0,2,0,0,34,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,42,0,2,0,0,42,0,0,0]', `price` = 7500 WHERE `id` = 61; -- ID  61: Nón mộc trúc (nữ) (Cấp 19) | Giá: 6,000 -> 7,500 xu | Chỉ số: [0, 25, 0, 2, 0, 0, 25, 0, 0, 0] -> [0,42,0,2,0,0,42,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,42,0,2,0,0,42,0,0,0]', `price` = 7500 WHERE `id` = 62; -- ID  62: Nón mộc trúc (nam) (Cấp 19) | Giá: 6,000 -> 7,500 xu | Chỉ số: [0, 25, 0, 2, 0, 0, 25, 0, 0, 0] -> [0,42,0,2,0,0,42,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,49,0,3,0,0,49,0,0,0]', `price` = 15000 WHERE `id` = 63; -- ID  63: Nón thanh vải (nữ) (Cấp 24) | Giá: 12,000 -> 15,000 xu | Chỉ số: [0, 30, 0, 3, 0, 0, 30, 0, 0, 0] -> [0,49,0,3,0,0,49,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,49,0,3,0,0,49,0,0,0]', `price` = 15000 WHERE `id` = 64; -- ID  64: Nón thanh vải (nam) (Cấp 24) | Giá: 12,000 -> 15,000 xu | Chỉ số: [0, 30, 0, 3, 0, 0, 30, 0, 0, 0] -> [0,49,0,3,0,0,49,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,57,0,3,0,0,57,0,0,0]', `price` = 22500 WHERE `id` = 65; -- ID  65: Nón đồng đen (nữ) (Cấp 29) | Giá: 18,000 -> 22,500 xu | Chỉ số: [0, 35, 0, 3, 0, 0, 35, 0, 0, 0] -> [0,57,0,3,0,0,57,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,57,0,3,0,0,57,0,0,0]', `price` = 22500 WHERE `id` = 66; -- ID  66: Nón đồng đen (nam) (Cấp 29) | Giá: 18,000 -> 22,500 xu | Chỉ số: [0, 35, 0, 3, 0, 0, 35, 0, 0, 0] -> [0,57,0,3,0,0,57,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,65,0,3,0,0,65,0,0,0]', `price` = 27500 WHERE `id` = 67; -- ID  67: Nón đồng đỏ (nữ) (Cấp 34) | Giá: 22,000 -> 27,500 xu | Chỉ số: [0, 40, 0, 3, 0, 0, 40, 0, 0, 0] -> [0,65,0,3,0,0,65,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,65,0,3,0,0,65,0,0,0]', `price` = 27500 WHERE `id` = 68; -- ID  68: Nón đồng đỏ (nam) (Cấp 34) | Giá: 22,000 -> 27,500 xu | Chỉ số: [0, 40, 0, 3, 0, 0, 40, 0, 0, 0] -> [0,65,0,3,0,0,65,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,73,0,3,0,0,73,0,0,0]', `price` = 32500 WHERE `id` = 69; -- ID  69: Nón sắt đen (nữ) (Cấp 39) | Giá: 26,000 -> 32,500 xu | Chỉ số: [0, 45, 0, 3, 0, 0, 45, 0, 0, 0] -> [0,73,0,3,0,0,73,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,73,0,3,0,0,73,0,0,0]', `price` = 32500 WHERE `id` = 70; -- ID  70: Nón sắt đen (nam) (Cấp 39) | Giá: 26,000 -> 32,500 xu | Chỉ số: [0, 45, 0, 3, 0, 0, 45, 0, 0, 0] -> [0,73,0,3,0,0,73,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,81,0,3,0,0,81,0,0,0]', `price` = 42500 WHERE `id` = 71; -- ID  71: Nón sắt xám (nữ) (Cấp 44) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 50, 0, 3, 0, 0, 50, 0, 0, 0] -> [0,81,0,3,0,0,81,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,81,0,3,0,0,81,0,0,0]', `price` = 42500 WHERE `id` = 72; -- ID  72: Nón sắt xám (nam) (Cấp 44) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 50, 0, 3, 0, 0, 50, 0, 0, 0] -> [0,81,0,3,0,0,81,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,88,0,3,0,0,88,0,0,0]', `price` = 45000 WHERE `id` = 73; -- ID  73: Tuyệt tâm mão (nữ) (Cấp 49) | Giá: 36,000 -> 45,000 xu | Chỉ số: [0, 55, 0, 3, 0, 0, 55, 0, 0, 0] -> [0,88,0,3,0,0,88,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,88,0,4,0,0,88,0,0,0]', `price` = 45000 WHERE `id` = 74; -- ID  74: Tuyệt tâm mão (nam) (Cấp 49) | Giá: 36,000 -> 45,000 xu | Chỉ số: [0, 55, 0, 4, 0, 0, 55, 0, 0, 0] -> [0,88,0,4,0,0,88,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,96,0,4,0,0,96,0,0,0]', `price` = 50000 WHERE `id` = 75; -- ID  75: Tùng linh mão (nữ) (Cấp 54) | Giá: 40,000 -> 50,000 xu | Chỉ số: [0, 60, 0, 4, 0, 0, 60, 0, 0, 0] -> [0,96,0,4,0,0,96,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,96,0,4,0,0,96,0,0,0]', `price` = 50000 WHERE `id` = 76; -- ID  76: Tùng linh mão (nam) (Cấp 54) | Giá: 40,000 -> 50,000 xu | Chỉ số: [0, 60, 0, 4, 0, 0, 60, 0, 0, 0] -> [0,96,0,4,0,0,96,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,104,0,4,0,0,104,0,0,0]', `price` = 56250 WHERE `id` = 77; -- ID  77: Kim long mão (nữ) (Cấp 59) | Giá: 45,000 -> 56,250 xu | Chỉ số: [0, 65, 0, 4, 0, 0, 65, 0, 0, 0] -> [0,104,0,4,0,0,104,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,104,0,4,0,0,104,0,0,0]', `price` = 56250 WHERE `id` = 78; -- ID  78: Kim long mão (nam) (Cấp 59) | Giá: 45,000 -> 56,250 xu | Chỉ số: [0, 65, 0, 4, 0, 0, 65, 0, 0, 0] -> [0,104,0,4,0,0,104,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,111,0,4,0,0,111,0,0,0]', `price` = 81250 WHERE `id` = 483; -- ID 483: Phi long mão(nữ) (Cấp 64) | Giá: 65,000 -> 81,250 xu | Chỉ số: [0, 70, 0, 4, 0, 0, 70, 0, 0, 0] -> [0,111,0,4,0,0,111,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,111,0,4,0,0,111,0,0,0]', `price` = 81250 WHERE `id` = 484; -- ID 484: Phi long mão(nam) (Cấp 64) | Giá: 65,000 -> 81,250 xu | Chỉ số: [0, 70, 0, 4, 0, 0, 70, 0, 0, 0] -> [0,111,0,4,0,0,111,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,119,0,4,0,0,119,0,0,0]', `price` = 106250 WHERE `id` = 485; -- ID 485: Tử quang mão(nữ) (Cấp 69) | Giá: 85,000 -> 106,250 xu | Chỉ số: [0, 75, 0, 4, 0, 0, 75, 0, 0, 0] -> [0,119,0,4,0,0,119,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,119,0,4,0,0,119,0,0,0]', `price` = 106250 WHERE `id` = 486; -- ID 486: Tử quang mão(nam) (Cấp 69) | Giá: 85,000 -> 106,250 xu | Chỉ số: [0, 75, 0, 4, 0, 0, 75, 0, 0, 0] -> [0,119,0,4,0,0,119,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,127,0,4,0,0,127,0,0,0]', `price` = 137500 WHERE `id` = 487; -- ID 487: Đại hùng mão(nữ) (Cấp 74) | Giá: 110,000 -> 137,500 xu | Chỉ số: [0, 80, 0, 4, 0, 0, 80, 0, 0, 0] -> [0,127,0,4,0,0,127,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,127,0,4,0,0,127,0,0,0]', `price` = 137500 WHERE `id` = 488; -- ID 488: Đại hùng mão(nam) (Cấp 74) | Giá: 110,000 -> 137,500 xu | Chỉ số: [0, 80, 0, 4, 0, 0, 80, 0, 0, 0] -> [0,127,0,4,0,0,127,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,135,0,4,0,0,135,0,0,0]', `price` = 162500 WHERE `id` = 489; -- ID 489: Phi yến mão(nữ) (Cấp 79) | Giá: 130,000 -> 162,500 xu | Chỉ số: [0, 85, 0, 4, 0, 0, 85, 0, 0, 0] -> [0,135,0,4,0,0,135,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,135,0,4,0,0,135,0,0,0]', `price` = 162500 WHERE `id` = 490; -- ID 490: Phi yến mão(nam) (Cấp 79) | Giá: 130,000 -> 162,500 xu | Chỉ số: [0, 85, 0, 4, 0, 0, 85, 0, 0, 0] -> [0,135,0,4,0,0,135,0,0,0]

-- -------------------------------------------------------------------------
-- GIÀY (TYPE 10) - Tăng Thủ Vật & Thủ Ma ~55-60%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[0,22,2,0,0,0,22,0,0,0]', `price` = 625 WHERE `id` = 138; -- ID 138: Giày đai thô (Cấp  4) | Giá: 500 -> 625 xu | Chỉ số: [0, 12, 2, 0, 0, 0, 12, 0, 0, 0] -> [0,22,2,0,0,0,22,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,29,2,0,0,0,29,0,0,0]', `price` = 1250 WHERE `id` = 139; -- ID 139: Giày thổ cẩm (Cấp  9) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 17, 2, 0, 0, 0, 17, 0, 0, 0] -> [0,29,2,0,0,0,29,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,37,3,0,0,0,37,0,0,0]', `price` = 2500 WHERE `id` = 140; -- ID 140: Giày bông (Cấp 14) | Giá: 2,000 -> 2,500 xu | Chỉ số: [0, 22, 3, 0, 0, 0, 22, 0, 0, 0] -> [0,37,3,0,0,0,37,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,45,3,0,0,0,45,0,0,0]', `price` = 6250 WHERE `id` = 141; -- ID 141: Giày lụa (Cấp 19) | Giá: 5,000 -> 6,250 xu | Chỉ số: [0, 27, 3, 0, 0, 0, 27, 0, 0, 0] -> [0,45,3,0,0,0,45,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,53,4,0,0,0,53,0,0,0]', `price` = 16250 WHERE `id` = 142; -- ID 142: Giày tơ tằm (Cấp 24) | Giá: 13,000 -> 16,250 xu | Chỉ số: [0, 32, 4, 0, 0, 0, 32, 0, 0, 0] -> [0,53,4,0,0,0,53,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,60,4,0,0,0,60,0,0,0]', `price` = 23750 WHERE `id` = 143; -- ID 143: Giày nhung (Cấp 29) | Giá: 19,000 -> 23,750 xu | Chỉ số: [0, 37, 4, 0, 0, 0, 37, 0, 0, 0] -> [0,60,4,0,0,0,60,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,68,5,0,0,0,68,0,0,0]', `price` = 28750 WHERE `id` = 144; -- ID 144: Giày gấm (Cấp 34) | Giá: 23,000 -> 28,750 xu | Chỉ số: [0, 42, 5, 0, 0, 0, 42, 0, 0, 0] -> [0,68,5,0,0,0,68,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,76,5,0,0,0,76,0,0,0]', `price` = 36250 WHERE `id` = 145; -- ID 145: Giày khoái linh (Cấp 39) | Giá: 29,000 -> 36,250 xu | Chỉ số: [0, 47, 5, 0, 0, 0, 47, 0, 0, 0] -> [0,76,5,0,0,0,76,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,84,6,0,0,0,84,0,0,0]', `price` = 42500 WHERE `id` = 146; -- ID 146: Giày cẩm trác (Cấp 44) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 52, 6, 0, 0, 0, 52, 0, 0, 0] -> [0,84,6,0,0,0,84,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,91,6,0,0,0,91,0,0,0]', `price` = 48750 WHERE `id` = 147; -- ID 147: Giày da hổ (Cấp 49) | Giá: 39,000 -> 48,750 xu | Chỉ số: [0, 57, 6, 0, 0, 0, 57, 0, 0, 0] -> [0,91,6,0,0,0,91,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,99,7,0,0,0,99,0,0,0]', `price` = 56250 WHERE `id` = 148; -- ID 148: Giày sư vương (Cấp 54) | Giá: 45,000 -> 56,250 xu | Chỉ số: [0, 62, 7, 0, 0, 0, 62, 0, 0, 0] -> [0,99,7,0,0,0,99,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,107,7,0,0,0,107,0,0,0]', `price` = 62500 WHERE `id` = 149; -- ID 149: Giày kim long (Cấp 59) | Giá: 50,000 -> 62,500 xu | Chỉ số: [0, 67, 7, 0, 0, 0, 67, 0, 0, 0] -> [0,107,7,0,0,0,107,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,115,7,0,0,0,115,0,0,0]', `price` = 87500 WHERE `id` = 491; -- ID 491: Giày phi long (Cấp 64) | Giá: 70,000 -> 87,500 xu | Chỉ số: [0, 72, 7, 0, 0, 0, 72, 0, 0, 0] -> [0,115,7,0,0,0,115,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,122,7,0,0,0,122,0,0,0]', `price` = 112500 WHERE `id` = 492; -- ID 492: Giày Tử quang (Cấp 69) | Giá: 90,000 -> 112,500 xu | Chỉ số: [0, 77, 7, 0, 0, 0, 77, 0, 0, 0] -> [0,122,7,0,0,0,122,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,130,7,0,0,0,130,0,0,0]', `price` = 137500 WHERE `id` = 493; -- ID 493: Giày Đại hùng (Cấp 74) | Giá: 110,000 -> 137,500 xu | Chỉ số: [0, 82, 7, 0, 0, 0, 82, 0, 0, 0] -> [0,130,7,0,0,0,130,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,138,7,0,0,0,138,0,0,0]', `price` = 162500 WHERE `id` = 494; -- ID 494: Giày phi yến (Cấp 79) | Giá: 130,000 -> 162,500 xu | Chỉ số: [0, 87, 7, 0, 0, 0, 87, 0, 0, 0] -> [0,138,7,0,0,0,138,0,0,0]

-- -------------------------------------------------------------------------
-- GĂNG TAY (TYPE 11) - Tăng Thủ Vật & Thủ Ma ~55-60%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[0,20,0,1,1,0,20,0,0,0]', `price` = 625 WHERE `id` = 150; -- ID 150: Găng đai thô (Cấp  4) | Giá: 500 -> 625 xu | Chỉ số: [0, 10, 0, 1, 1, 0, 10, 0, 0, 0] -> [0,20,0,1,1,0,20,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,26,0,1,1,0,26,0,0,0]', `price` = 1250 WHERE `id` = 151; -- ID 151: Găng thổ cẩm (Cấp  9) | Giá: 1,000 -> 1,250 xu | Chỉ số: [0, 15, 0, 1, 1, 0, 15, 0, 0, 0] -> [0,26,0,1,1,0,26,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,34,0,2,2,0,34,0,0,0]', `price` = 2500 WHERE `id` = 152; -- ID 152: Găng bông (Cấp 14) | Giá: 2,000 -> 2,500 xu | Chỉ số: [0, 20, 0, 2, 2, 0, 20, 0, 0, 0] -> [0,34,0,2,2,0,34,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,42,0,2,2,0,42,0,0,0]', `price` = 7500 WHERE `id` = 153; -- ID 153: Găng lụa (Cấp 19) | Giá: 6,000 -> 7,500 xu | Chỉ số: [0, 25, 0, 2, 2, 0, 25, 0, 0, 0] -> [0,42,0,2,2,0,42,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,49,0,3,2,0,49,0,0,0]', `price` = 15000 WHERE `id` = 154; -- ID 154: Găng tơ tằm (Cấp 24) | Giá: 12,000 -> 15,000 xu | Chỉ số: [0, 30, 0, 3, 2, 0, 30, 0, 0, 0] -> [0,49,0,3,2,0,49,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,57,0,3,3,0,57,0,0,0]', `price` = 22500 WHERE `id` = 155; -- ID 155: Găng nhung (Cấp 29) | Giá: 18,000 -> 22,500 xu | Chỉ số: [0, 35, 0, 3, 3, 0, 35, 0, 0, 0] -> [0,57,0,3,3,0,57,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,65,0,4,3,0,65,0,0,0]', `price` = 27500 WHERE `id` = 156; -- ID 156: Găng gấm (Cấp 34) | Giá: 22,000 -> 27,500 xu | Chỉ số: [0, 40, 0, 4, 3, 0, 40, 0, 0, 0] -> [0,65,0,4,3,0,65,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,73,0,4,3,0,73,0,0,0]', `price` = 32500 WHERE `id` = 157; -- ID 157: Găng khoái linh (Cấp 39) | Giá: 26,000 -> 32,500 xu | Chỉ số: [0, 45, 0, 4, 3, 0, 45, 0, 0, 0] -> [0,73,0,4,3,0,73,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,81,0,5,4,0,81,0,0,0]', `price` = 42500 WHERE `id` = 158; -- ID 158: Găng cẩm trác (Cấp 44) | Giá: 34,000 -> 42,500 xu | Chỉ số: [0, 50, 0, 5, 4, 0, 50, 0, 0, 0] -> [0,81,0,5,4,0,81,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,88,0,5,4,0,88,0,0,0]', `price` = 45000 WHERE `id` = 159; -- ID 159: Găng da hổ (Cấp 49) | Giá: 36,000 -> 45,000 xu | Chỉ số: [0, 55, 0, 5, 4, 0, 55, 0, 0, 0] -> [0,88,0,5,4,0,88,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,96,0,6,4,0,96,0,0,0]', `price` = 50000 WHERE `id` = 160; -- ID 160: Găng sư vương (Cấp 54) | Giá: 40,000 -> 50,000 xu | Chỉ số: [0, 60, 0, 6, 4, 0, 60, 0, 0, 0] -> [0,96,0,6,4,0,96,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,104,0,6,5,0,104,0,0,0]', `price` = 56250 WHERE `id` = 161; -- ID 161: Găng kim long (Cấp 59) | Giá: 45,000 -> 56,250 xu | Chỉ số: [0, 65, 0, 6, 5, 0, 65, 0, 0, 0] -> [0,104,0,6,5,0,104,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,111,0,6,5,0,111,0,0,0]', `price` = 81250 WHERE `id` = 495; -- ID 495: Găng phi long (Cấp 64) | Giá: 65,000 -> 81,250 xu | Chỉ số: [0, 70, 0, 6, 5, 0, 70, 0, 0, 0] -> [0,111,0,6,5,0,111,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,119,0,6,5,0,119,0,0,0]', `price` = 106250 WHERE `id` = 496; -- ID 496: Găng Tử quang (Cấp 69) | Giá: 85,000 -> 106,250 xu | Chỉ số: [0, 75, 0, 6, 5, 0, 75, 0, 0, 0] -> [0,119,0,6,5,0,119,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,127,0,6,5,0,127,0,0,0]', `price` = 131250 WHERE `id` = 497; -- ID 497: Găng Đại hùng (Cấp 74) | Giá: 105,000 -> 131,250 xu | Chỉ số: [0, 80, 0, 6, 5, 0, 80, 0, 0, 0] -> [0,127,0,6,5,0,127,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,135,0,6,5,0,135,0,0,0]', `price` = 156250 WHERE `id` = 498; -- ID 498: Găng Phi yến (Cấp 79) | Giá: 125,000 -> 156,250 xu | Chỉ số: [0, 85, 0, 6, 5, 0, 85, 0, 0, 0] -> [0,135,0,6,5,0,135,0,0,0]

-- -------------------------------------------------------------------------
-- KIẾM (TYPE 3) - Tăng Tấn Công ~42%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[45,0,0,1,0,0,0,0,0,0]', `price` = 625 WHERE `id` = 79; -- ID  79: Kiếm tre (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [30, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [45,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[71,0,0,4,0,0,0,0,0,0]', `price` = 6250 WHERE `id` = 80; -- ID  80: Kiếm gỗ (Cấp  6) | Giá: 5,000 -> 6,250 xu | Chỉ số: [50, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [71,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[99,0,0,4,0,0,0,0,0,0]', `price` = 18750 WHERE `id` = 81; -- ID  81: Kiếm đồng (Cấp 11) | Giá: 15,000 -> 18,750 xu | Chỉ số: [70, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [99,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[128,0,0,8,0,0,0,0,0,0]', `price` = 37500 WHERE `id` = 82; -- ID  82: Kiếm sắt (Cấp 16) | Giá: 30,000 -> 37,500 xu | Chỉ số: [90, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [128,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[156,0,0,8,0,0,0,0,0,0]', `price` = 56250 WHERE `id` = 83; -- ID  83: Mộ khúc kiếm (Cấp 21) | Giá: 45,000 -> 56,250 xu | Chỉ số: [110, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [156,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[185,0,0,8,0,0,0,0,0,0]', `price` = 75000 WHERE `id` = 84; -- ID  84: Lưu Chỉ Kiếm (Cấp 26) | Giá: 60,000 -> 75,000 xu | Chỉ số: [130, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [185,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[213,0,0,8,0,0,0,0,0,0]', `price` = 100000 WHERE `id` = 85; -- ID  85: Khổng Tú Kiếm (Cấp 31) | Giá: 80,000 -> 100,000 xu | Chỉ số: [150, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [213,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[256,0,0,8,0,0,0,0,0,0]', `price` = 137500 WHERE `id` = 174; -- ID 174: Thanh Phong Kiếm (Cấp 36) | Giá: 110,000 -> 137,500 xu | Chỉ số: [180, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [256,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[284,0,0,8,0,0,0,0,0,0]', `price` = 175000 WHERE `id` = 175; -- ID 175: Long Tuyền Kiếm (Cấp 41) | Giá: 140,000 -> 175,000 xu | Chỉ số: [200, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [284,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[327,0,0,8,0,0,0,0,0,0]', `price` = 212500 WHERE `id` = 176; -- ID 176: Thất Xích Kiếm (Cấp 46) | Giá: 170,000 -> 212,500 xu | Chỉ số: [230, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [327,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[341,0,0,8,0,0,0,0,0,0]', `price` = 250000 WHERE `id` = 177; -- ID 177: Vạn Hoa Kiếm (Cấp 51) | Giá: 200,000 -> 250,000 xu | Chỉ số: [240, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [341,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[369,0,0,8,0,0,0,0,0,0]', `price` = 287500 WHERE `id` = 178; -- ID 178: Huyền Thiết Kiếm (Cấp 56) | Giá: 230,000 -> 287,500 xu | Chỉ số: [260, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [369,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[398,0,0,8,0,0,0,0,0,0]', `price` = 325000 WHERE `id` = 179; -- ID 179: Hoàn Tử Kiếm (Cấp 61) | Giá: 260,000 -> 325,000 xu | Chỉ số: [280, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [398,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[426,0,0,8,0,0,0,0,0,0]', `price` = 362500 WHERE `id` = 180; -- ID 180: Báo Vĩ Kiếm (Cấp 66) | Giá: 290,000 -> 362,500 xu | Chỉ số: [300, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [426,0,0,8,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[454,0,0,8,0,0,0,0,0,0]', `price` = 400000 WHERE `id` = 181; -- ID 181: Nhạn Linh Kiếm (Cấp 71) | Giá: 320,000 -> 400,000 xu | Chỉ số: [320, 0, 0, 8, 0, 0, 0, 0, 0, 0] -> [454,0,0,8,0,0,0,0,0,0]

-- -------------------------------------------------------------------------
-- ĐAO (TYPE 4) - Tăng Tấn Công ~42%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[71,0,0,1,0,0,0,0,0,0]', `price` = 625 WHERE `id` = 86; -- ID  86: Đao tre (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [50, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [71,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[99,0,0,2,0,0,0,0,0,0]', `price` = 6250 WHERE `id` = 87; -- ID  87: Đao gỗ (Cấp  6) | Giá: 5,000 -> 6,250 xu | Chỉ số: [70, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [99,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[128,0,0,2,0,0,0,0,0,0]', `price` = 18750 WHERE `id` = 88; -- ID  88: Đao đồng (Cấp 11) | Giá: 15,000 -> 18,750 xu | Chỉ số: [90, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [128,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[156,0,0,3,0,0,0,0,0,0]', `price` = 37500 WHERE `id` = 89; -- ID  89: Đao sắt (Cấp 16) | Giá: 30,000 -> 37,500 xu | Chỉ số: [110, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [156,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[185,0,0,3,0,0,0,0,0,0]', `price` = 56250 WHERE `id` = 90; -- ID  90: Đà đao (Cấp 21) | Giá: 45,000 -> 56,250 xu | Chỉ số: [130, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [185,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[213,0,0,4,0,0,0,0,0,0]', `price` = 75000 WHERE `id` = 91; -- ID  91: Tuệ Đao (Cấp 26) | Giá: 60,000 -> 75,000 xu | Chỉ số: [150, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [213,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[241,0,0,4,0,0,0,0,0,0]', `price` = 100000 WHERE `id` = 92; -- ID  92: Khổng Trác Đao (Cấp 31) | Giá: 80,000 -> 100,000 xu | Chỉ số: [170, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [241,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[270,0,0,4,0,0,0,0,0,0]', `price` = 150000 WHERE `id` = 182; -- ID 182: Quỷ Đầu Đao (Cấp 36) | Giá: 120,000 -> 150,000 xu | Chỉ số: [190, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [270,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[298,0,0,4,0,0,0,0,0,0]', `price` = 187500 WHERE `id` = 183; -- ID 183: Nguyệt Nha Đao (Cấp 41) | Giá: 150,000 -> 187,500 xu | Chỉ số: [210, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [298,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[327,0,0,4,0,0,0,0,0,0]', `price` = 225000 WHERE `id` = 184; -- ID 184: Phá Phong Đao (Cấp 46) | Giá: 180,000 -> 225,000 xu | Chỉ số: [230, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [327,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[355,0,0,4,0,0,0,0,0,0]', `price` = 262500 WHERE `id` = 185; -- ID 185: Vạn Linh Đao (Cấp 51) | Giá: 210,000 -> 262,500 xu | Chỉ số: [250, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [355,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[383,0,0,4,0,0,0,0,0,0]', `price` = 300000 WHERE `id` = 186; -- ID 186: Cổn Châu Đao (Cấp 56) | Giá: 240,000 -> 300,000 xu | Chỉ số: [270, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [383,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[412,0,0,4,0,0,0,0,0,0]', `price` = 337500 WHERE `id` = 187; -- ID 187: Đại Phong Đao (Cấp 61) | Giá: 270,000 -> 337,500 xu | Chỉ số: [290, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [412,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[440,0,0,4,0,0,0,0,0,0]', `price` = 375000 WHERE `id` = 188; -- ID 188: Điểm Cang Đao (Cấp 66) | Giá: 300,000 -> 375,000 xu | Chỉ số: [310, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [440,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[469,0,0,4,0,0,0,0,0,0]', `price` = 412500 WHERE `id` = 189; -- ID 189: Hỏa Diệm Đao (Cấp 71) | Giá: 330,000 -> 412,500 xu | Chỉ số: [330, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [469,0,0,4,0,0,0,0,0,0]

-- -------------------------------------------------------------------------
-- BÚT (TYPE 5) - Tăng Tấn Công ~42%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[99,0,0,0,0,0,0,0,0,0]', `price` = 625 WHERE `id` = 93; -- ID  93: Bút tre (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [70, 0, 0, 0, 0, 0, 0, 0, 0, 0] -> [99,0,0,0,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[128,0,0,1,0,0,0,0,0,0]', `price` = 6250 WHERE `id` = 94; -- ID  94: Bút gỗ (Cấp  6) | Giá: 5,000 -> 6,250 xu | Chỉ số: [90, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [128,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[156,0,0,1,0,0,0,0,0,0]', `price` = 18750 WHERE `id` = 95; -- ID  95: Bút đồng (Cấp 11) | Giá: 15,000 -> 18,750 xu | Chỉ số: [110, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [156,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[185,0,0,2,0,0,0,0,0,0]', `price` = 37500 WHERE `id` = 96; -- ID  96: Bút sắt (Cấp 16) | Giá: 30,000 -> 37,500 xu | Chỉ số: [130, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [185,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[213,0,0,2,0,0,0,0,0,0]', `price` = 56250 WHERE `id` = 97; -- ID  97: Cơ duyên bút (Cấp 21) | Giá: 45,000 -> 56,250 xu | Chỉ số: [150, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [213,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[241,0,0,3,0,0,0,0,0,0]', `price` = 75000 WHERE `id` = 98; -- ID  98: Mộ Danh Bút (Cấp 26) | Giá: 60,000 -> 75,000 xu | Chỉ số: [170, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [241,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[270,0,0,3,0,0,0,0,0,0]', `price` = 100000 WHERE `id` = 99; -- ID  99: Bãn Nhãn Bút (Cấp 31) | Giá: 80,000 -> 100,000 xu | Chỉ số: [190, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [270,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[284,0,0,3,0,0,0,0,0,0]', `price` = 150000 WHERE `id` = 190; -- ID 190: Kim Qua Bút (Cấp 36) | Giá: 120,000 -> 150,000 xu | Chỉ số: [200, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [284,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[319,0,0,3,0,0,0,0,0,0]', `price` = 193750 WHERE `id` = 191; -- ID 191: Đào Hoa Bút (Cấp 41) | Giá: 155,000 -> 193,750 xu | Chỉ số: [225, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [319,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[348,0,0,3,0,0,0,0,0,0]', `price` = 237500 WHERE `id` = 192; -- ID 192: Tâm Ý Bút (Cấp 46) | Giá: 190,000 -> 237,500 xu | Chỉ số: [245, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [348,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[376,0,0,3,0,0,0,0,0,0]', `price` = 281250 WHERE `id` = 193; -- ID 193: Độc Cơ Bút (Cấp 51) | Giá: 225,000 -> 281,250 xu | Chỉ số: [265, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [376,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[405,0,0,3,0,0,0,0,0,0]', `price` = 325000 WHERE `id` = 194; -- ID 194: Bạo Vũ Bút (Cấp 56) | Giá: 260,000 -> 325,000 xu | Chỉ số: [285, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [405,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[433,0,0,3,0,0,0,0,0,0]', `price` = 368750 WHERE `id` = 195; -- ID 195: Tề Mi Bút (Cấp 61) | Giá: 295,000 -> 368,750 xu | Chỉ số: [305, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [433,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[461,0,0,3,0,0,0,0,0,0]', `price` = 400000 WHERE `id` = 196; -- ID 196: Hỗn Thiết Bút (Cấp 66) | Giá: 320,000 -> 400,000 xu | Chỉ số: [325, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [461,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[490,0,0,3,0,0,0,0,0,0]', `price` = 443750 WHERE `id` = 197; -- ID 197: Thu Vũ Bút (Cấp 71) | Giá: 355,000 -> 443,750 xu | Chỉ số: [345, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [490,0,0,3,0,0,0,0,0,0]

-- -------------------------------------------------------------------------
-- BÚA (TYPE 6) - Tăng Tấn Công ~42%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[128,0,0,0,0,0,0,0,0,0]', `price` = 625 WHERE `id` = 100; -- ID 100: Thổ búa (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [90, 0, 0, 0, 0, 0, 0, 0, 0, 0] -> [128,0,0,0,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[156,0,0,1,0,0,0,0,0,0]', `price` = 6250 WHERE `id` = 101; -- ID 101: Mộc búa (Cấp  6) | Giá: 5,000 -> 6,250 xu | Chỉ số: [110, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [156,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[185,0,0,1,0,0,0,0,0,0]', `price` = 18750 WHERE `id` = 102; -- ID 102: Búa đồng (Cấp 11) | Giá: 15,000 -> 18,750 xu | Chỉ số: [130, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [185,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[213,0,0,2,0,0,0,0,0,0]', `price` = 37500 WHERE `id` = 103; -- ID 103: Búa sắt (Cấp 16) | Giá: 30,000 -> 37,500 xu | Chỉ số: [150, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [213,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[241,0,0,2,0,0,0,0,0,0]', `price` = 56250 WHERE `id` = 104; -- ID 104: Búa thủ ngưu (Cấp 21) | Giá: 45,000 -> 56,250 xu | Chỉ số: [170, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [241,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[270,0,0,2,0,0,0,0,0,0]', `price` = 68750 WHERE `id` = 105; -- ID 105: Búa Mã Diện (Cấp 26) | Giá: 55,000 -> 68,750 xu | Chỉ số: [190, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [270,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[298,0,0,3,0,0,0,0,0,0]', `price` = 93750 WHERE `id` = 106; -- ID 106: Búa Địa Tinh (Cấp 31) | Giá: 75,000 -> 93,750 xu | Chỉ số: [210, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [298,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[327,0,0,3,0,0,0,0,0,0]', `price` = 131250 WHERE `id` = 198; -- ID 198: Tú Cốt Búa (Cấp 36) | Giá: 105,000 -> 131,250 xu | Chỉ số: [230, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [327,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[355,0,0,3,0,0,0,0,0,0]', `price` = 168750 WHERE `id` = 199; -- ID 199: Toán Đầu Búa (Cấp 41) | Giá: 135,000 -> 168,750 xu | Chỉ số: [250, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [355,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[383,0,0,3,0,0,0,0,0,0]', `price` = 206250 WHERE `id` = 200; -- ID 200: Bát Lăng Búa (Cấp 46) | Giá: 165,000 -> 206,250 xu | Chỉ số: [270, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [383,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[412,0,0,3,0,0,0,0,0,0]', `price` = 243750 WHERE `id` = 201; -- ID 201: Ngũ Tinh Búa (Cấp 51) | Giá: 195,000 -> 243,750 xu | Chỉ số: [290, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [412,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[440,0,0,3,0,0,0,0,0,0]', `price` = 281250 WHERE `id` = 202; -- ID 202: Tích Lịch Búa (Cấp 56) | Giá: 225,000 -> 281,250 xu | Chỉ số: [310, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [440,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[469,0,0,3,0,0,0,0,0,0]', `price` = 318750 WHERE `id` = 203; -- ID 203: Lượng Ngân Búa (Cấp 61) | Giá: 255,000 -> 318,750 xu | Chỉ số: [330, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [469,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[497,0,0,3,0,0,0,0,0,0]', `price` = 356250 WHERE `id` = 204; -- ID 204: Liệt Tâm Búa (Cấp 66) | Giá: 285,000 -> 356,250 xu | Chỉ số: [350, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [497,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[525,0,0,3,0,0,0,0,0,0]', `price` = 393750 WHERE `id` = 205; -- ID 205: Nghiệt Long Búa (Cấp 71) | Giá: 315,000 -> 393,750 xu | Chỉ số: [370, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [525,0,0,3,0,0,0,0,0,0]

-- -------------------------------------------------------------------------
-- CUNG (TYPE 7) - Tăng Tấn Công ~42%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[35,0,0,5,1,0,0,0,0,0]', `price` = 625 WHERE `id` = 107; -- ID 107: Cung tre (Cấp  1) | Giá: 500 -> 625 xu | Chỉ số: [20, 0, 0, 5, 1, 0, 0, 0, 0, 0] -> [35,0,0,5,1,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[57,0,0,7,2,0,0,0,0,0]', `price` = 6250 WHERE `id` = 108; -- ID 108: Cung gỗ (Cấp  6) | Giá: 5,000 -> 6,250 xu | Chỉ số: [40, 0, 0, 7, 2, 0, 0, 0, 0, 0] -> [57,0,0,7,2,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[85,0,0,7,3,0,0,0,0,0]', `price` = 18750 WHERE `id` = 109; -- ID 109: Cung đồng (Cấp 11) | Giá: 15,000 -> 18,750 xu | Chỉ số: [60, 0, 0, 7, 3, 0, 0, 0, 0, 0] -> [85,0,0,7,3,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[114,0,0,10,4,0,0,0,0,0]', `price` = 37500 WHERE `id` = 110; -- ID 110: Cung sắt (Cấp 16) | Giá: 30,000 -> 37,500 xu | Chỉ số: [80, 0, 0, 10, 4, 0, 0, 0, 0, 0] -> [114,0,0,10,4,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[142,0,0,10,5,0,0,0,0,0]', `price` = 56250 WHERE `id` = 111; -- ID 111: Bán nguyệt cung (Cấp 21) | Giá: 45,000 -> 56,250 xu | Chỉ số: [100, 0, 0, 10, 5, 0, 0, 0, 0, 0] -> [142,0,0,10,5,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[156,0,0,12,6,0,0,0,0,0]', `price` = 68750 WHERE `id` = 112; -- ID 112: Kim Loan Cung (Cấp 26) | Giá: 55,000 -> 68,750 xu | Chỉ số: [110, 0, 0, 12, 6, 0, 0, 0, 0, 0] -> [156,0,0,12,6,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[170,0,0,12,7,0,0,0,0,0]', `price` = 93750 WHERE `id` = 113; -- ID 113: Lĩnh Nam Cung (Cấp 31) | Giá: 75,000 -> 93,750 xu | Chỉ số: [120, 0, 0, 12, 7, 0, 0, 0, 0, 0] -> [170,0,0,12,7,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[213,0,0,12,0,0,0,0,0,0]', `price` = 125000 WHERE `id` = 206; -- ID 206: Kim Tiền Cung (Cấp 36) | Giá: 100,000 -> 125,000 xu | Chỉ số: [150, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [213,0,0,12,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[241,0,0,12,0,0,0,0,0,0]', `price` = 162500 WHERE `id` = 207; -- ID 207: Yến Tử Cung (Cấp 41) | Giá: 130,000 -> 162,500 xu | Chỉ số: [170, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [241,0,0,12,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[270,0,0,12,0,0,0,0,0,0]', `price` = 200000 WHERE `id` = 208; -- ID 208: Phi Hoàng Cung (Cấp 46) | Giá: 160,000 -> 200,000 xu | Chỉ số: [190, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [270,0,0,12,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[298,0,0,12,0,0,0,0,0,0]', `price` = 237500 WHERE `id` = 209; -- ID 209: Liễu Diệp Cung (Cấp 51) | Giá: 190,000 -> 237,500 xu | Chỉ số: [210, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [298,0,0,12,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[327,0,0,12,0,0,0,0,0,0]', `price` = 275000 WHERE `id` = 210; -- ID 210: Lượng Ngân Cung (Cấp 56) | Giá: 220,000 -> 275,000 xu | Chỉ số: [230, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [327,0,0,12,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[341,0,0,12,0,0,0,0,0,0]', `price` = 312500 WHERE `id` = 211; -- ID 211: Hồ Điệp Cung (Cấp 61) | Giá: 250,000 -> 312,500 xu | Chỉ số: [240, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [341,0,0,12,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[369,0,0,12,0,0,0,0,0,0]', `price` = 350000 WHERE `id` = 212; -- ID 212: Nhạn Vĩ Cung (Cấp 66) | Giá: 280,000 -> 350,000 xu | Chỉ số: [260, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [369,0,0,12,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[398,0,0,12,0,0,0,0,0,0]', `price` = 387500 WHERE `id` = 213; -- ID 213: Phá Thiên Cung (Cấp 71) | Giá: 310,000 -> 387,500 xu | Chỉ số: [280, 0, 0, 12, 0, 0, 0, 0, 0, 0] -> [398,0,0,12,0,0,0,0,0,0]

-- -------------------------------------------------------------------------
-- NHẪN (TYPE 8) - Tăng Sát Thương Phụ ~40%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[8,0,0,1,0,0,0,0,0,0]', `price` = 1250 WHERE `id` = 114; -- ID 114: Nhẫn hoàng ngọc (Cấp  4) | Giá: 1,000 -> 1,250 xu | Chỉ số: [5, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [8,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[14,0,0,1,0,0,0,0,0,0]', `price` = 2500 WHERE `id` = 115; -- ID 115: Nhẫn lam ngọc (Cấp  9) | Giá: 2,000 -> 2,500 xu | Chỉ số: [10, 0, 0, 1, 0, 0, 0, 0, 0, 0] -> [14,0,0,1,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[21,0,0,2,0,0,0,0,0,0]', `price` = 5000 WHERE `id` = 116; -- ID 116: Nhẫn hoàng bảo (Cấp 14) | Giá: 4,000 -> 5,000 xu | Chỉ số: [15, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [21,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[28,0,0,2,0,0,0,0,0,0]', `price` = 12500 WHERE `id` = 117; -- ID 117: Nhẫn lam bảo (Cấp 19) | Giá: 10,000 -> 12,500 xu | Chỉ số: [20, 0, 0, 2, 0, 0, 0, 0, 0, 0] -> [28,0,0,2,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[35,0,0,3,0,0,0,0,0,0]', `price` = 23750 WHERE `id` = 118; -- ID 118: Nhẫn cảm lãm (Cấp 24) | Giá: 19,000 -> 23,750 xu | Chỉ số: [25, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [35,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[42,0,0,3,0,0,0,0,0,0]', `price` = 38750 WHERE `id` = 119; -- ID 119: Nhẫn phù dung (Cấp 29) | Giá: 31,000 -> 38,750 xu | Chỉ số: [30, 0, 0, 3, 0, 0, 0, 0, 0, 0] -> [42,0,0,3,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[49,0,0,4,0,0,0,0,0,0]', `price` = 48750 WHERE `id` = 120; -- ID 120: Nhẫn phỉ thúy (Cấp 34) | Giá: 39,000 -> 48,750 xu | Chỉ số: [35, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [49,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[56,0,0,4,0,0,0,0,0,0]', `price` = 56250 WHERE `id` = 121; -- ID 121: Nhẫn thúy lựu (Cấp 39) | Giá: 45,000 -> 56,250 xu | Chỉ số: [40, 0, 0, 4, 0, 0, 0, 0, 0, 0] -> [56,0,0,4,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[63,0,0,5,0,0,0,0,0,0]', `price` = 62500 WHERE `id` = 122; -- ID 122: Nhẫn tổ mẫu (Cấp 44) | Giá: 50,000 -> 62,500 xu | Chỉ số: [45, 0, 0, 5, 0, 0, 0, 0, 0, 0] -> [63,0,0,5,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[70,0,0,5,0,0,0,0,0,0]', `price` = 73750 WHERE `id` = 123; -- ID 123: Nhẫn hải lam (Cấp 49) | Giá: 59,000 -> 73,750 xu | Chỉ số: [50, 0, 0, 5, 0, 0, 0, 0, 0, 0] -> [70,0,0,5,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[77,0,0,6,0,0,0,0,0,0]', `price` = 81250 WHERE `id` = 124; -- ID 124: Nhẫn hồng bảo (Cấp 54) | Giá: 65,000 -> 81,250 xu | Chỉ số: [55, 0, 0, 6, 0, 0, 0, 0, 0, 0] -> [77,0,0,6,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[84,0,0,6,0,0,0,0,0,0]', `price` = 88750 WHERE `id` = 125; -- ID 125: Nhẫn sa thạch (Cấp 59) | Giá: 71,000 -> 88,750 xu | Chỉ số: [60, 0, 0, 6, 0, 0, 0, 0, 0, 0] -> [84,0,0,6,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[91,0,0,6,0,0,0,0,0,0]', `price` = 113750 WHERE `id` = 499; -- ID 499: Nhẫn Phi long (Cấp 64) | Giá: 91,000 -> 113,750 xu | Chỉ số: [65, 0, 0, 6, 0, 0, 0, 0, 0, 0] -> [91,0,0,6,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[98,0,0,6,0,0,0,0,0,0]', `price` = 143750 WHERE `id` = 500; -- ID 500: Nhẫn Tử quang (Cấp 69) | Giá: 115,000 -> 143,750 xu | Chỉ số: [70, 0, 0, 6, 0, 0, 0, 0, 0, 0] -> [98,0,0,6,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[105,0,0,6,0,0,0,0,0,0]', `price` = 168750 WHERE `id` = 501; -- ID 501: Nhẫn Đại hùng (Cấp 74) | Giá: 135,000 -> 168,750 xu | Chỉ số: [75, 0, 0, 6, 0, 0, 0, 0, 0, 0] -> [105,0,0,6,0,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[112,0,0,6,0,0,0,0,0,0]', `price` = 193750 WHERE `id` = 502; -- ID 502: Nhẫn phi yến (Cấp 79) | Giá: 155,000 -> 193,750 xu | Chỉ số: [80, 0, 0, 6, 0, 0, 0, 0, 0, 0] -> [112,0,0,6,0,0,0,0,0,0]

-- -------------------------------------------------------------------------
-- DÂY CHUYỀN (TYPE 9) - Tăng Sát Thương Phụ ~40%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[10,0,0,0,1,0,0,0,0,0]', `price` = 1875 WHERE `id` = 126; -- ID 126: Dây chuyền đồng (Cấp  4) | Giá: 1,500 -> 1,875 xu | Chỉ số: [7, 0, 0, 0, 1, 0, 0, 0, 0, 0] -> [10,0,0,0,1,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[17,0,0,0,2,0,0,0,0,0]', `price` = 3125 WHERE `id` = 127; -- ID 127: Dây chuyền san hô (Cấp  9) | Giá: 2,500 -> 3,125 xu | Chỉ số: [12, 0, 0, 0, 2, 0, 0, 0, 0, 0] -> [17,0,0,0,2,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[24,0,0,0,2,0,0,0,0,0]', `price` = 5625 WHERE `id` = 128; -- ID 128: Dây chuyền lục tùng (Cấp 14) | Giá: 4,500 -> 5,625 xu | Chỉ số: [17, 0, 0, 0, 2, 0, 0, 0, 0, 0] -> [24,0,0,0,2,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[31,0,0,0,3,0,0,0,0,0]', `price` = 15000 WHERE `id` = 129; -- ID 129: Dây chuyền miêu nhãn (Cấp 19) | Giá: 12,000 -> 15,000 xu | Chỉ số: [22, 0, 0, 0, 3, 0, 0, 0, 0, 0] -> [31,0,0,0,3,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[38,0,0,0,3,0,0,0,0,0]', `price` = 26250 WHERE `id` = 130; -- ID 130: Dây chuyền bạch kim (Cấp 24) | Giá: 21,000 -> 26,250 xu | Chỉ số: [27, 0, 0, 0, 3, 0, 0, 0, 0, 0] -> [38,0,0,0,3,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[45,0,0,0,3,0,0,0,0,0]', `price` = 42500 WHERE `id` = 131; -- ID 131: Dây chuyền thủy tinh (Cấp 29) | Giá: 34,000 -> 42,500 xu | Chỉ số: [32, 0, 0, 0, 3, 0, 0, 0, 0, 0] -> [45,0,0,0,3,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[52,0,0,0,4,0,0,0,0,0]', `price` = 51250 WHERE `id` = 132; -- ID 132: Dây chuyền ngọc châu (Cấp 34) | Giá: 41,000 -> 51,250 xu | Chỉ số: [37, 0, 0, 0, 4, 0, 0, 0, 0, 0] -> [52,0,0,0,4,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[59,0,0,0,4,0,0,0,0,0]', `price` = 58750 WHERE `id` = 133; -- ID 133: Dây chuyền hổ phách (Cấp 39) | Giá: 47,000 -> 58,750 xu | Chỉ số: [42, 0, 0, 0, 4, 0, 0, 0, 0, 0] -> [59,0,0,0,4,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[66,0,0,0,4,0,0,0,0,0]', `price` = 65000 WHERE `id` = 134; -- ID 134: Dây chuyền ngân châu (Cấp 44) | Giá: 52,000 -> 65,000 xu | Chỉ số: [47, 0, 0, 0, 4, 0, 0, 0, 0, 0] -> [66,0,0,0,4,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[73,0,0,0,5,0,0,0,0,0]', `price` = 76250 WHERE `id` = 135; -- ID 135: Dây chuyền phỉ thúy (Cấp 49) | Giá: 61,000 -> 76,250 xu | Chỉ số: [52, 0, 0, 0, 5, 0, 0, 0, 0, 0] -> [73,0,0,0,5,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[80,0,0,0,5,0,0,0,0,0]', `price` = 83750 WHERE `id` = 136; -- ID 136: Dây chuyền toàn thạch (Cấp 54) | Giá: 67,000 -> 83,750 xu | Chỉ số: [57, 0, 0, 0, 5, 0, 0, 0, 0, 0] -> [80,0,0,0,5,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[87,0,0,0,5,0,0,0,0,0]', `price` = 93750 WHERE `id` = 137; -- ID 137: Dây chuyền khổng tước (Cấp 59) | Giá: 75,000 -> 93,750 xu | Chỉ số: [62, 0, 0, 0, 5, 0, 0, 0, 0, 0] -> [87,0,0,0,5,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[94,0,0,0,5,0,0,0,0,0]', `price` = 118750 WHERE `id` = 503; -- ID 503: Dây chuyền phi long (Cấp 64) | Giá: 95,000 -> 118,750 xu | Chỉ số: [67, 0, 0, 0, 5, 0, 0, 0, 0, 0] -> [94,0,0,0,5,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[101,0,0,0,5,0,0,0,0,0]', `price` = 143750 WHERE `id` = 504; -- ID 504: Dây chuyền Tử quang (Cấp 69) | Giá: 115,000 -> 143,750 xu | Chỉ số: [72, 0, 0, 0, 5, 0, 0, 0, 0, 0] -> [101,0,0,0,5,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[108,0,0,0,5,0,0,0,0,0]', `price` = 168750 WHERE `id` = 505; -- ID 505: Dây chuyền Đại hùng (Cấp 74) | Giá: 135,000 -> 168,750 xu | Chỉ số: [77, 0, 0, 0, 5, 0, 0, 0, 0, 0] -> [108,0,0,0,5,0,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[115,0,0,0,5,0,0,0,0,0]', `price` = 193750 WHERE `id` = 506; -- ID 506: Dây chuyền phi yến (Cấp 79) | Giá: 155,000 -> 193,750 xu | Chỉ số: [82, 0, 0, 0, 5, 0, 0, 0, 0, 0] -> [115,0,0,0,5,0,0,0,0,0]

-- -------------------------------------------------------------------------
-- NGỌC (TYPE 12) - Tăng Máu Hỗ Trợ ~50%, Tăng Giá 25%
-- -------------------------------------------------------------------------
UPDATE `item_equipment` SET `attribute` = '[0,0,0,1,1,2,0,0,0,0]', `price` = 2500 WHERE `id` = 162; -- ID 162: Ngọc Bích ngọc (Cấp  4) | Giá: 2,000 -> 2,500 xu | Chỉ số: [0, 0, 0, 1, 1, 1, 0, 0, 0, 0] -> [0,0,0,1,1,2,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,1,2,3,0,0,0,0]', `price` = 3750 WHERE `id` = 163; -- ID 163: Ngọc Ngân bích (Cấp  9) | Giá: 3,000 -> 3,750 xu | Chỉ số: [0, 0, 0, 1, 2, 2, 0, 0, 0, 0] -> [0,0,0,1,2,3,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,2,2,4,0,0,0,0]', `price` = 6250 WHERE `id` = 164; -- ID 164: Ngọc Tứ tuyệt (Cấp 14) | Giá: 5,000 -> 6,250 xu | Chỉ số: [0, 0, 0, 2, 2, 3, 0, 0, 0, 0] -> [0,0,0,2,2,4,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,2,3,6,0,0,0,0]', `price` = 18750 WHERE `id` = 165; -- ID 165: Ngọc Hoàng phủ (Cấp 19) | Giá: 15,000 -> 18,750 xu | Chỉ số: [0, 0, 0, 2, 3, 4, 0, 0, 0, 0] -> [0,0,0,2,3,6,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,3,3,8,0,0,0,0]', `price` = 31250 WHERE `id` = 166; -- ID 166: Ngọc Hồ điệp (Cấp 24) | Giá: 25,000 -> 31,250 xu | Chỉ số: [0, 0, 0, 3, 3, 5, 0, 0, 0, 0] -> [0,0,0,3,3,8,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,3,3,9,0,0,0,0]', `price` = 43750 WHERE `id` = 167; -- ID 167: Ngọc Lam hoa (Cấp 29) | Giá: 35,000 -> 43,750 xu | Chỉ số: [0, 0, 0, 3, 3, 6, 0, 0, 0, 0] -> [0,0,0,3,3,9,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,4,4,10,0,0,0,0]', `price` = 56250 WHERE `id` = 168; -- ID 168: Ngọc Túy tiên (Cấp 34) | Giá: 45,000 -> 56,250 xu | Chỉ số: [0, 0, 0, 4, 4, 7, 0, 0, 0, 0] -> [0,0,0,4,4,10,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,4,4,12,0,0,0,0]', `price` = 68750 WHERE `id` = 169; -- ID 169: Ngọc Viên xích (Cấp 39) | Giá: 55,000 -> 68,750 xu | Chỉ số: [0, 0, 0, 4, 4, 8, 0, 0, 0, 0] -> [0,0,0,4,4,12,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,5,4,14,0,0,0,0]', `price` = 81250 WHERE `id` = 170; -- ID 170: Ngọc Ấu nguyệt (Cấp 44) | Giá: 65,000 -> 81,250 xu | Chỉ số: [0, 0, 0, 5, 4, 9, 0, 0, 0, 0] -> [0,0,0,5,4,14,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,5,5,15,0,0,0,0]', `price` = 93750 WHERE `id` = 171; -- ID 171: Ngọc Đào hoa (Cấp 49) | Giá: 75,000 -> 93,750 xu | Chỉ số: [0, 0, 0, 5, 5, 10, 0, 0, 0, 0] -> [0,0,0,5,5,15,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,6,5,16,0,0,0,0]', `price` = 106250 WHERE `id` = 172; -- ID 172: Ngọc Mai hoa (Cấp 54) | Giá: 85,000 -> 106,250 xu | Chỉ số: [0, 0, 0, 6, 5, 11, 0, 0, 0, 0] -> [0,0,0,6,5,16,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,6,5,18,0,0,0,0]', `price` = 118750 WHERE `id` = 173; -- ID 173: Ngọc Long tiên (Cấp 59) | Giá: 95,000 -> 118,750 xu | Chỉ số: [0, 0, 0, 6, 5, 12, 0, 0, 0, 0] -> [0,0,0,6,5,18,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,7,6,22,0,0,0,0]', `price` = 137500 WHERE `id` = 569; -- ID 569: Ngọc phi long (Cấp 64) | Giá: 110,000 -> 137,500 xu | Chỉ số: [0, 0, 0, 7, 6, 15, 0, 0, 0, 0] -> [0,0,0,7,6,22,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,7,6,24,0,0,0,0]', `price` = 162500 WHERE `id` = 570; -- ID 570: Ngọc tử quang (Cấp 69) | Giá: 130,000 -> 162,500 xu | Chỉ số: [0, 0, 0, 7, 6, 16, 0, 0, 0, 0] -> [0,0,0,7,6,24,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,8,7,20,0,0,0,0]', `price` = 187500 WHERE `id` = 571; -- ID 571: Ngọc đại hùng (Cấp 74) | Giá: 150,000 -> 187,500 xu | Chỉ số: [0, 0, 0, 8, 7, 13, 0, 0, 0, 0] -> [0,0,0,8,7,20,0,0,0,0]
UPDATE `item_equipment` SET `attribute` = '[0,0,0,8,7,21,0,0,0,0]', `price` = 212500 WHERE `id` = 572; -- ID 572: Ngọc phi yến (Cấp 79) | Giá: 170,000 -> 212,500 xu | Chỉ số: [0, 0, 0, 8, 7, 14, 0, 0, 0, 0] -> [0,0,0,8,7,21,0,0,0,0]

COMMIT;
-- =========================================================================
-- HOÀN TẤT CẬP NHẬT CHẤT LƯỢNG VÀ GIÁ TRANG BỊ XU
-- =========================================================================
