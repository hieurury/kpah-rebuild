-- =========================================================
-- TỔNG HỢP CÁC LỆNH UPDATE DATABASE CHO TERMUX / SERVER
-- Bao gồm: Mana Rebalance, Thẻ Mua Bán, Ngọc Rèn, Chỉ Số 177 Đồ Chế Tạo
-- =========================================================

-- 1. Điều chỉnh tiêu hao Mana Kiếm Khách & Pháp Sư
UPDATE `others`
SET
    `data` = '[[[0,0,0,0,0,0,0,0,0,0,0],[0,3,4,5,6,7,8,9,10,11,12],[0,5,6,7,8,9,10,11,12,13,14],[0,6,7,8,9,10,12,14,16,18,20],[0,10,11,13,15,17,19,21,23,25,25],[0,35,40,45,50,55,60,65,70,75,80],[0,20,22,25,28,31,34,37,40,43,45],[0,25,28,31,34,37,40,43,46,50,55],[0,35,39,43,47,51,55,60,65,70,75],[10,40,45,50,55,60,65,70,75,80,80],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55]],[[0,0,0,0,0,0,0,0,0,0,0],[2,2,3,3,4,4,5,5,6,6,6],[3,3,4,4,5,5,6,6,7,7,7],[2,2,3,3,4,4,5,5,6,6,6],[3,3,4,4,5,5,6,6,7,7,7],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,40,45,50,55,60,65,70,75,80,80],[10,40,45,50,55,60,65,70,75,80,80],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55]],[[0,0,0,0,0,0,0,0,0,0,0],[0,15,18,21,24,27,30,33,36,40,45],[0,20,23,26,30,34,38,42,46,50,55],[0,25,30,35,40,45,50,55,60,65,70],[0,60,70,80,90,100,110,120,130,140,150],[0,0,0,0,0,0,0,0,0,0,0],[0,50,60,70,80,90,100,110,120,130,140],[0,80,90,100,115,130,145,160,175,190,200],[0,50,60,70,80,90,100,110,120,130,140],[0,70,80,90,100,115,130,145,160,170,180],[0,90,105,120,135,150,165,180,200,220,240],[10,40,45,50,55,60,65,70,75,80,80],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55]],[[0,0,0,0,0,0,0,0,0,0,0],[3,4,4,4,5,5,5,6,6,6,6],[3,3,3,4,4,4,5,5,6,6,6],[3,4,4,4,5,5,5,6,6,6,6],[3,3,3,4,4,4,5,5,6,6,6],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,40,45,50,55,60,65,70,75,80,80],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55]],[[0,0,0,0,0,0,0,0,0,0,0],[4,4,4,4,5,5,5,5,6,6,6],[7,7,7,8,8,8,9,9,9,9,9],[4,4,4,4,5,5,5,5,6,6,6],[7,7,7,8,8,8,9,9,9,9,9],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,40,45,50,55,60,65,70,75,80,80],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55],[10,15,20,25,30,35,40,45,50,55,55]]]'
WHERE
    `type` = 'SKILL_MP';

-- 2. Cập nhật Thẻ Mua Bán (Hạn 3 ngày = 4320 phút, Giá 10 Lượng)
UPDATE `item_equipment`
SET
    `name` = 'Thẻ mua bán. Cho phép tự động sửa chữa trang bị ở mọi nơi với giá 150% so với sửa tại tiệm. Sử dụng trực tiếp để giao dịch với Hắc Ngưu.',
    `ndayLoan` = 4320,
    `price` = 10,
    `colorItem` = 1
WHERE
    `id` = 675;

UPDATE `potion_template`
SET
    `name` = 'Thẻ mua bán. Cho phép tự động sửa chữa trang bị ở mọi nơi với giá 150% so với sửa tại tiệm. Sử dụng trực tiếp để giao dịch với Hắc Ngưu.',
    `price` = 10
WHERE
    `id` = 33;

-- 3. Thêm/Cập nhật Ngọc Rèn (ID 268)
INSERT INTO
    `gem_template` (
        `id`,
        `idImage`,
        `price`,
        `name`,
        `decript`,
        `type`,
        `isSell`,
        `typeEp`,
        `typeMoney`
    )
VALUES (
        268,
        20,
        1000,
        'Ngọc rèn',
        'Dùng để tinh luyện và nâng cấp trang bị tại Thợ Rèn Thần Bí.',
        6,
        0,
        0,
        0
    )
ON DUPLICATE KEY UPDATE
    `idImage` = 20,
    `price` = 1000,
    `name` = 'Ngọc rèn',
    `decript` = 'Dùng để tinh luyện và nâng cấp trang bị tại Thợ Rèn Thần Bí.',
    `type` = 6,
    `isSell` = 0,
    `typeEp` = 0,
    `typeMoney` = 0;

-- 4. Cập nhật chỉ số gốc cho 177 trang bị chế tạo (Thợ Rèn Thần Bí ID 268 - 444)
UPDATE `item_equipment`
SET
    `attribute` = '[0,75,0,6,0,0,75,0,0,0]'
WHERE
    `id` = 268;
-- Áo Thanh Tuyệt (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,75,0,6,0,0,75,0,0,0]'
WHERE
    `id` = 269;
-- Áo Thanh Tuyệt (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,80,0,6,0,0,80,0,0,0]'
WHERE
    `id` = 270;
-- Áo Thạch Lựu (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,80,0,6,0,0,80,0,0,0]'
WHERE
    `id` = 271;
-- Áo Thạch Lựu (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,85,0,7,0,0,85,0,0,0]'
WHERE
    `id` = 272;
-- Áo Dật Hiệp (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,85,0,7,0,0,85,0,0,0]'
WHERE
    `id` = 273;
-- Áo Dật Hiệp (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,90,0,7,0,0,90,0,0,0]'
WHERE
    `id` = 274;
-- Áo Linh Xà (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,90,0,7,0,0,90,0,0,0]'
WHERE
    `id` = 275;
-- Áo Linh Xà (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 276;
-- Áo Vô sắc (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 277;
-- Áo Vô sắc (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 278;
-- Áo Tâm ma (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 279;
-- Áo Tâm ma (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 280;
-- Áo  Hắc báo (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 281;
-- Áo Hắc báo (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 282;
-- Áo Băng tinh (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 283;
-- Áo Băng tinh (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 284;
-- Áo Thất bảo (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 285;
-- Áo Thất bảo (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 286;
-- Áo Liên vân (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 287;
-- Áo Liên vân (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 288;
-- Áo Ngân tơ (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 289;
-- Áo Ngân tơ (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 290;
-- Áo Vô cực (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 291;
-- Áo Vô cực (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,1,1,1,1,1,1,1,1,0]'
WHERE
    `id` = 292;
-- Quần Thanh tuyệt (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,28,0,2,0,0,28,0,0,0]'
WHERE
    `id` = 293;
-- Quần Thanh tuyệt (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,33,0,2,0,0,33,0,0,0]'
WHERE
    `id` = 294;
-- Quần Thạch lựu (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,33,0,2,0,0,33,0,0,0]'
WHERE
    `id` = 295;
-- Quần Thạch lựu (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,38,0,2,0,0,38,0,0,0]'
WHERE
    `id` = 296;
-- Quần Dật Hiệp (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,38,0,2,0,0,38,0,0,0]'
WHERE
    `id` = 297;
-- Quần Dật Hiệp (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,43,0,2,0,0,43,0,0,0]'
WHERE
    `id` = 298;
-- Quần Linh xà (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,43,0,2,0,0,43,0,0,0]'
WHERE
    `id` = 299;
-- Quần Linh xà (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,48,0,3,0,0,48,0,0,0]'
WHERE
    `id` = 300;
-- Quần Vô sắc (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,48,0,3,0,0,48,0,0,0]'
WHERE
    `id` = 301;
-- Quần Vô sắc (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,53,0,3,0,0,53,0,0,0]'
WHERE
    `id` = 302;
-- Quần Tâm ma (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,53,0,3,0,0,53,0,0,0]'
WHERE
    `id` = 303;
-- Quần Tâm ma (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,58,0,3,0,0,58,0,0,0]'
WHERE
    `id` = 304;
-- Quần Hắc báo (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,58,0,3,0,0,58,0,0,0]'
WHERE
    `id` = 305;
-- Quần Hắc báo (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,63,0,3,0,0,63,0,0,0]'
WHERE
    `id` = 306;
-- Quần Băng tinh (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,63,0,3,0,0,63,0,0,0]'
WHERE
    `id` = 307;
-- Quần Băng tinh (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,68,0,4,0,0,68,0,0,0]'
WHERE
    `id` = 308;
-- Quần Thất bảo (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,68,0,4,0,0,68,0,0,0]'
WHERE
    `id` = 309;
-- Quần Thất bảo (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,73,0,4,0,0,73,0,0,0]'
WHERE
    `id` = 310;
-- Quần Liên vân (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,73,0,4,0,0,73,0,0,0]'
WHERE
    `id` = 311;
-- Quần Liên vân (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,78,0,4,0,0,78,0,0,0]'
WHERE
    `id` = 312;
-- Quần Ngân tơ (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,78,0,4,0,0,78,0,0,0]'
WHERE
    `id` = 313;
-- Quần Ngân tơ (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,83,0,4,0,0,83,0,0,0]'
WHERE
    `id` = 314;
-- Quần Vô cưc (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,83,0,4,0,0,83,0,0,0]'
WHERE
    `id` = 315;
-- Quần Vô cưc (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,26,0,2,0,0,26,0,0,0]'
WHERE
    `id` = 316;
-- Nón Thanh tuyệt (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,26,0,2,0,0,26,0,0,0]'
WHERE
    `id` = 317;
-- Nón Thanh tuyệt (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,31,0,2,0,0,31,0,0,0]'
WHERE
    `id` = 318;
-- Nón Thạch lựu (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,31,0,2,0,0,31,0,0,0]'
WHERE
    `id` = 319;
-- Nón Thạch lựu (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,36,0,2,0,0,36,0,0,0]'
WHERE
    `id` = 320;
-- Nón Dật hiệp (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,36,0,2,0,0,36,0,0,0]'
WHERE
    `id` = 321;
-- Nón Dật hiệp (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,41,0,2,0,0,41,0,0,0]'
WHERE
    `id` = 322;
-- Nón Linh xà (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,41,0,2,0,0,41,0,0,0]'
WHERE
    `id` = 323;
-- Nón Linh xà (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,46,0,3,0,0,46,0,0,0]'
WHERE
    `id` = 324;
-- Nón vô sắc (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,46,0,3,0,0,46,0,0,0]'
WHERE
    `id` = 325;
-- Nón vô sắc (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,51,0,3,0,0,51,0,0,0]'
WHERE
    `id` = 326;
-- Nón Tâm ma (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,51,0,3,0,0,51,0,0,0]'
WHERE
    `id` = 327;
-- Nón Tâm ma (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,56,0,3,0,0,56,0,0,0]'
WHERE
    `id` = 328;
-- Nón Hắc báo (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,56,0,3,0,0,56,0,0,0]'
WHERE
    `id` = 329;
-- Nón Hắc báo (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,61,0,3,0,0,61,0,0,0]'
WHERE
    `id` = 330;
-- Nón Băng tinh (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,61,0,3,0,0,61,0,0,0]'
WHERE
    `id` = 331;
-- Nón Băng tinh (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,66,0,4,0,0,66,0,0,0]'
WHERE
    `id` = 332;
-- Nón Thất bảo (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,66,0,4,0,0,66,0,0,0]'
WHERE
    `id` = 333;
-- Nón Thất bảo (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,71,0,4,0,0,71,0,0,0]'
WHERE
    `id` = 334;
-- Nón Liên vân (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,71,0,4,0,0,71,0,0,0]'
WHERE
    `id` = 335;
-- Nón Liên vân (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,76,0,4,0,0,76,0,0,0]'
WHERE
    `id` = 336;
-- Nón Ngân tơ (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,76,0,4,0,0,76,0,0,0]'
WHERE
    `id` = 337;
-- Nón Ngân tơ (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,81,0,4,0,0,81,0,0,0]'
WHERE
    `id` = 338;
-- Nón Vô cực (nữ)
UPDATE `item_equipment`
SET
    `attribute` = '[0,81,0,4,0,0,81,0,0,0]'
WHERE
    `id` = 339;
-- Nón Vô cực (nam)
UPDATE `item_equipment`
SET
    `attribute` = '[0,28,4,0,0,0,28,0,0,0]'
WHERE
    `id` = 340;
-- Giày Thanh Tuyệt
UPDATE `item_equipment`
SET
    `attribute` = '[0,33,4,0,0,0,33,0,0,0]'
WHERE
    `id` = 341;
-- Giày Thạch lựu
UPDATE `item_equipment`
SET
    `attribute` = '[0,38,5,0,0,0,38,0,0,0]'
WHERE
    `id` = 342;
-- Giày Dật Hiệp
UPDATE `item_equipment`
SET
    `attribute` = '[0,43,5,0,0,0,43,0,0,0]'
WHERE
    `id` = 343;
-- Giày Linh xà
UPDATE `item_equipment`
SET
    `attribute` = '[0,48,6,0,0,0,48,0,0,0]'
WHERE
    `id` = 344;
-- Giày Vô sắc
UPDATE `item_equipment`
SET
    `attribute` = '[0,53,6,0,0,0,53,0,0,0]'
WHERE
    `id` = 345;
-- Giày Tâm ma
UPDATE `item_equipment`
SET
    `attribute` = '[0,58,7,0,0,0,58,0,0,0]'
WHERE
    `id` = 346;
-- Giày Hắc báo
UPDATE `item_equipment`
SET
    `attribute` = '[0,63,7,0,0,0,63,0,0,0]'
WHERE
    `id` = 347;
-- Giày Băng tinh
UPDATE `item_equipment`
SET
    `attribute` = '[0,68,8,0,0,0,68,0,0,0]'
WHERE
    `id` = 348;
-- Giày Thất bảo
UPDATE `item_equipment`
SET
    `attribute` = '[0,73,8,0,0,0,73,0,0,0]'
WHERE
    `id` = 349;
-- Giày Liên vân
UPDATE `item_equipment`
SET
    `attribute` = '[0,78,9,0,0,0,78,0,0,0]'
WHERE
    `id` = 350;
-- Giày Ngân tơ
UPDATE `item_equipment`
SET
    `attribute` = '[0,83,9,0,0,0,83,0,0,0]'
WHERE
    `id` = 351;
-- Giày Vô cực
UPDATE `item_equipment`
SET
    `attribute` = '[0,26,0,2,2,0,26,0,0,0]'
WHERE
    `id` = 352;
-- Găng Thanh Tuyệt
UPDATE `item_equipment`
SET
    `attribute` = '[0,31,0,3,2,0,31,0,0,0]'
WHERE
    `id` = 353;
-- Găng Thạch lựu
UPDATE `item_equipment`
SET
    `attribute` = '[0,36,0,3,3,0,36,0,0,0]'
WHERE
    `id` = 354;
-- Găng Dật Hiệp
UPDATE `item_equipment`
SET
    `attribute` = '[0,41,0,3,3,0,41,0,0,0]'
WHERE
    `id` = 355;
-- Găng Linh xà
UPDATE `item_equipment`
SET
    `attribute` = '[0,46,0,4,3,0,46,0,0,0]'
WHERE
    `id` = 356;
-- Găng Vô sắc
UPDATE `item_equipment`
SET
    `attribute` = '[0,51,0,4,4,0,51,0,0,0]'
WHERE
    `id` = 357;
-- Găng Tâm ma
UPDATE `item_equipment`
SET
    `attribute` = '[0,56,0,5,4,0,56,0,0,0]'
WHERE
    `id` = 358;
-- Găng Hắc báo
UPDATE `item_equipment`
SET
    `attribute` = '[0,61,0,5,4,0,61,0,0,0]'
WHERE
    `id` = 359;
-- Găng Băng tinh
UPDATE `item_equipment`
SET
    `attribute` = '[0,66,0,6,5,0,66,0,0,0]'
WHERE
    `id` = 360;
-- Găng Thất bảo
UPDATE `item_equipment`
SET
    `attribute` = '[0,71,0,6,5,0,71,0,0,0]'
WHERE
    `id` = 361;
-- Găng Liên vân
UPDATE `item_equipment`
SET
    `attribute` = '[0,76,0,6,5,0,76,0,0,0]'
WHERE
    `id` = 362;
-- Găng Ngân tơ
UPDATE `item_equipment`
SET
    `attribute` = '[0,81,0,7,6,0,81,0,0,0]'
WHERE
    `id` = 363;
-- Găng Vô cực
UPDATE `item_equipment`
SET
    `attribute` = '[55,0,0,3,0,0,0,0,0,0]'
WHERE
    `id` = 364;
-- Nhẫn Toàn Thạch
UPDATE `item_equipment`
SET
    `attribute` = '[65,0,0,3,0,0,0,0,0,0]'
WHERE
    `id` = 365;
-- Nhẫn Linh xà
UPDATE `item_equipment`
SET
    `attribute` = '[75,0,0,4,0,0,0,0,0,0]'
WHERE
    `id` = 366;
-- Nhẫn Ngọc châu
UPDATE `item_equipment`
SET
    `attribute` = '[85,0,0,4,0,0,0,0,0,0]'
WHERE
    `id` = 367;
-- Nhẫn Ngân châu
UPDATE `item_equipment`
SET
    `attribute` = '[95,0,0,4,0,0,0,0,0,0]'
WHERE
    `id` = 368;
-- Nhẫn San hô
UPDATE `item_equipment`
SET
    `attribute` = '[105,0,0,5,0,0,0,0,0,0]'
WHERE
    `id` = 369;
-- Nhẫn Ngũ sắc
UPDATE `item_equipment`
SET
    `attribute` = '[115,0,0,5,0,0,0,0,0,0]'
WHERE
    `id` = 370;
-- Nhẫn Huyền tê
UPDATE `item_equipment`
SET
    `attribute` = '[125,0,0,5,0,0,0,0,0,0]'
WHERE
    `id` = 371;
-- Nhẫn Khổng tượng
UPDATE `item_equipment`
SET
    `attribute` = '[135,0,0,6,0,0,0,0,0,0]'
WHERE
    `id` = 372;
-- Nhẫn Âm dương
UPDATE `item_equipment`
SET
    `attribute` = '[145,0,0,6,0,0,0,0,0,0]'
WHERE
    `id` = 373;
-- Nhẫn Nhật nguyệt
UPDATE `item_equipment`
SET
    `attribute` = '[155,0,0,6,0,0,0,0,0,0]'
WHERE
    `id` = 374;
-- Nhẫn Song phụng
UPDATE `item_equipment`
SET
    `attribute` = '[165,0,0,7,0,0,0,0,0,0]'
WHERE
    `id` = 375;
-- Nhẫn Song long
UPDATE `item_equipment`
SET
    `attribute` = '[60,0,0,0,2,0,0,0,0,0]'
WHERE
    `id` = 376;
-- Dây chuyền Toàn Thạch
UPDATE `item_equipment`
SET
    `attribute` = '[70,0,0,0,2,0,0,0,0,0]'
WHERE
    `id` = 377;
-- Dây chuyền Linh xà
UPDATE `item_equipment`
SET
    `attribute` = '[80,0,0,0,3,0,0,0,0,0]'
WHERE
    `id` = 378;
-- Dây chuyền Ngọc châu
UPDATE `item_equipment`
SET
    `attribute` = '[90,0,0,0,3,0,0,0,0,0]'
WHERE
    `id` = 379;
-- Dây chuyền Ngân châu
UPDATE `item_equipment`
SET
    `attribute` = '[100,0,0,0,3,0,0,0,0,0]'
WHERE
    `id` = 380;
-- Dây chuyền San hô
UPDATE `item_equipment`
SET
    `attribute` = '[110,0,0,0,4,0,0,0,0,0]'
WHERE
    `id` = 381;
-- Dây chuyền Ngũ sắc
UPDATE `item_equipment`
SET
    `attribute` = '[120,0,0,0,4,0,0,0,0,0]'
WHERE
    `id` = 382;
-- Dây chuyền Huyền tê
UPDATE `item_equipment`
SET
    `attribute` = '[130,0,0,0,4,0,0,0,0,0]'
WHERE
    `id` = 383;
-- Dây chuyền Khổng tượng
UPDATE `item_equipment`
SET
    `attribute` = '[140,0,0,0,5,0,0,0,0,0]'
WHERE
    `id` = 384;
-- Dây chuyền Âm dương
UPDATE `item_equipment`
SET
    `attribute` = '[150,0,0,0,5,0,0,0,0,0]'
WHERE
    `id` = 385;
-- Dây chuyền Nhật nguyệt
UPDATE `item_equipment`
SET
    `attribute` = '[160,0,0,0,5,0,0,0,0,0]'
WHERE
    `id` = 386;
-- Dây chuyền Song phụng
UPDATE `item_equipment`
SET
    `attribute` = '[170,0,0,0,6,0,0,0,0,0]'
WHERE
    `id` = 387;
-- Dây chuyền Song long
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,3,2,80,0,0,0,0]'
WHERE
    `id` = 388;
-- Ngọc Toàn Thạch
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,3,2,95,0,0,0,0]'
WHERE
    `id` = 389;
-- Ngọc Linh xà
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,4,2,110,0,0,0,0]'
WHERE
    `id` = 390;
-- Ngọc Ngọc châu
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,4,2,125,0,0,0,0]'
WHERE
    `id` = 391;
-- Ngọc Ngân châu
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,4,3,140,0,0,0,0]'
WHERE
    `id` = 392;
-- Ngọc San hô
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,5,3,155,0,0,0,0]'
WHERE
    `id` = 393;
-- Ngọc Ngũ sắc
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,5,3,170,0,0,0,0]'
WHERE
    `id` = 394;
-- Ngọc Huyền tê
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,5,3,185,0,0,0,0]'
WHERE
    `id` = 395;
-- Ngọc Khổng tượng
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,6,4,200,0,0,0,0]'
WHERE
    `id` = 396;
-- Ngọc Âm dương
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,6,4,215,0,0,0,0]'
WHERE
    `id` = 397;
-- Ngọc Nhật nguyệt
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,6,4,230,0,0,0,0]'
WHERE
    `id` = 398;
-- Ngọc Song phụng
UPDATE `item_equipment`
SET
    `attribute` = '[0,0,0,7,4,245,0,0,0,0]'
WHERE
    `id` = 399;
-- Ngọc Song long
UPDATE `item_equipment`
SET
    `attribute` = '[164,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 400;
-- Khổng tước kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[187,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 401;
-- Phù du kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[210,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 402;
-- Thiêu hoả kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[232,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 403;
-- Bàn hoa kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[254,0,0,3,3,0,0,0,0,0]'
WHERE
    `id` = 404;
-- Hồ vĩ kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[277,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 405;
-- Giác hải kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[300,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 406;
-- Kim cô kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[322,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 407;
-- Tấn thiết kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[344,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 408;
-- Hắc bạch kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[367,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 409;
-- Tuyết hoa kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[390,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 410;
-- Uyên ương kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[412,0,0,6,4,0,0,0,0,0]'
WHERE
    `id` = 411;
-- Phù dung kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[434,0,0,6,5,0,0,0,0,0]'
WHERE
    `id` = 412;
-- Phá giáp kiếm
UPDATE `item_equipment`
SET
    `attribute` = '[164,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 413;
-- Trảm mã đao
UPDATE `item_equipment`
SET
    `attribute` = '[187,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 414;
-- Điểm cang đao
UPDATE `item_equipment`
SET
    `attribute` = '[210,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 415;
-- Lang nha đao
UPDATE `item_equipment`
SET
    `attribute` = '[232,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 416;
-- Thập tự đao
UPDATE `item_equipment`
SET
    `attribute` = '[254,0,0,3,3,0,0,0,0,0]'
WHERE
    `id` = 417;
-- Tinh trang đao
UPDATE `item_equipment`
SET
    `attribute` = '[277,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 418;
-- Đới y đao
UPDATE `item_equipment`
SET
    `attribute` = '[300,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 419;
-- Quan cán đao
UPDATE `item_equipment`
SET
    `attribute` = '[322,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 420;
-- Toái nguyệt đao
UPDATE `item_equipment`
SET
    `attribute` = '[344,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 421;
-- Thanh phong đao
UPDATE `item_equipment`
SET
    `attribute` = '[367,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 422;
-- Túc đế đao
UPDATE `item_equipment`
SET
    `attribute` = '[390,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 423;
-- Bôi trang đao
UPDATE `item_equipment`
SET
    `attribute` = '[412,0,0,6,4,0,0,0,0,0]'
WHERE
    `id` = 424;
-- Bá vương đao
UPDATE `item_equipment`
SET
    `attribute` = '[434,0,0,6,5,0,0,0,0,0]'
WHERE
    `id` = 425;
-- Gia cát đao
UPDATE `item_equipment`
SET
    `attribute` = '[164,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 426;
-- Cân quản bút
UPDATE `item_equipment`
SET
    `attribute` = '[187,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 427;
-- Phi yến bút
UPDATE `item_equipment`
SET
    `attribute` = '[210,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 428;
-- Lộ thiên bút
UPDATE `item_equipment`
SET
    `attribute` = '[232,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 429;
-- Thiết khôi bút
UPDATE `item_equipment`
SET
    `attribute` = '[254,0,0,3,3,0,0,0,0,0]'
WHERE
    `id` = 430;
-- Nhạn vũ bút
UPDATE `item_equipment`
SET
    `attribute` = '[277,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 431;
-- Ngân hoàn bút
UPDATE `item_equipment`
SET
    `attribute` = '[300,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 432;
-- Xích đồn bút
UPDATE `item_equipment`
SET
    `attribute` = '[322,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 433;
-- Tứ giác bút
UPDATE `item_equipment`
SET
    `attribute` = '[344,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 434;
-- Thanh văn bút
UPDATE `item_equipment`
SET
    `attribute` = '[367,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 435;
-- Khôn lộc bút
UPDATE `item_equipment`
SET
    `attribute` = '[390,0,0,5,4,0,0,0,0,0]'
WHERE
    `id` = 436;
-- Huyền sa bút
UPDATE `item_equipment`
SET
    `attribute` = '[412,0,0,6,4,0,0,0,0,0]'
WHERE
    `id` = 437;
-- Bạch trực bút
UPDATE `item_equipment`
SET
    `attribute` = '[434,0,0,6,5,0,0,0,0,0]'
WHERE
    `id` = 438;
-- Y tơ bút
UPDATE `item_equipment`
SET
    `attribute` = '[164,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 439;
-- Thôn nhật búa
UPDATE `item_equipment`
SET
    `attribute` = '[187,0,0,2,2,0,0,0,0,0]'
WHERE
    `id` = 440;
-- Bán diện búa
UPDATE `item_equipment`
SET
    `attribute` = '[210,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 441;
-- Ngân liêm búa
UPDATE `item_equipment`
SET
    `attribute` = '[232,0,0,3,2,0,0,0,0,0]'
WHERE
    `id` = 442;
-- Tim đồng búa
UPDATE `item_equipment`
SET
    `attribute` = '[254,0,0,3,3,0,0,0,0,0]'
WHERE
    `id` = 443;
-- Hoàng bố búa
UPDATE `item_equipment`
SET
    `attribute` = '[277,0,0,4,3,0,0,0,0,0]'
WHERE
    `id` = 444;
-- Hoàng đồng búa

-- =========================================================
-- 5. TÁI THIẾT HỆ THỐNG NGUYÊN LIỆU & NPC THƯƠNG NHÂN / LUYỆN KIM
-- =========================================================

-- 5.1 Đổi tên 2 NPC Thợ hợp thành thành Thương nhân nguyên liệu và Thợ luyện kim
UPDATE `npc_actor` SET `name` = 'Thuong nhan nguyen lieu' WHERE `id` = 4;
UPDATE `npc_actor` SET `name` = 'Tho luyen kim' WHERE `id` = 5;

-- 5.2 Ẩn toàn bộ các bậc phẩm cũ (cấp 2 đến 6) khỏi shop
UPDATE `gem_template`
SET `isSell` = 0
WHERE `id` IN (
    69,70,71,72,73,
    76,77,78,79,80,
    83,84,85,86,87,
    90,91,92,93,94,
    97,98,99,100,101,
    104,105,106,107,108,
    111,112,113,114,115,
    118,119,120,121,122,
    125,126,127,128,129,
    132,133,134,135,136
);

-- 5.3 Chuẩn hóa 5 loại nguyên liệu sơ cấp (2 Lượng, bán trong shop, tên bỏ cấp 1)
UPDATE `gem_template` SET `name` = 'Vải', `price` = 2, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 68;
UPDATE `gem_template` SET `name` = 'Sắt', `price` = 2, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 75;
UPDATE `gem_template` SET `name` = 'Ngọc', `price` = 2, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 82;
UPDATE `gem_template` SET `name` = 'Gỗ thường', `price` = 2, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 89;
UPDATE `gem_template` SET `name` = 'Da mềm', `price` = 2, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 96;

-- 5.4 Chuẩn hóa 5 loại nguyên liệu cao cấp (5 Lượng, bán trong shop, tên bỏ cấp 1)
UPDATE `gem_template` SET `name` = 'Tơ lụa', `price` = 5, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 103;
UPDATE `gem_template` SET `name` = 'Bạc', `price` = 5, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 110;
UPDATE `gem_template` SET `name` = 'Thủy tinh', `price` = 5, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 117;
UPDATE `gem_template` SET `name` = 'Gỗ sưa', `price` = 5, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 124;
UPDATE `gem_template` SET `name` = 'Da cứng', `price` = 5, `typeMoney` = 1, `isSell` = 1 WHERE `id` = 131;

-- 5.5 Chuẩn hóa Ngọc rèn (ID 268: 10.000 Xu, bán trong shop)
UPDATE `gem_template` SET `price` = 10000, `typeMoney` = 0, `isSell` = 1 WHERE `id` = 268;