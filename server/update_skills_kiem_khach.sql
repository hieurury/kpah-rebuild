-- =============================================================================
-- KPAH REBALANCE MIGRATION: ĐIỀU CHỈNH TOÀN DIỆN MÔN PHÁI KIẾM KHÁCH (CLASS 0)
-- Ngày thực hiện: 2026-09-18
-- Nội dung:
-- 1. Cập nhật mô tả kỹ năng Kiếm Khách chuẩn xác theo cơ chế mới:
--    - Skill 3: Kinh lôi bát thủ (Multi-hit + Nhiễm điện 5s)
--    - Skill 4: Hộ sát tiến (Nội tại: Gây thêm sát thương chuẩn + Tỉ lệ nhiễm điện 5s)
--    - Skill 5: Dĩ lực đáo công (Buff 60s/90s CD: Giảm thương 5%-14%, phản đòn 25%-70% tỉ lệ, phản 50%-95% công font vàng)
--    - Skill 6: Thiên lôi điện trảm (AoE: Nhiễm điện 5s diện rộng)
--    - Skill 7: Sấm động dương gian (AoE: Nếu nhiễm điện -> thêm sát thương chuẩn + Choáng 1s)
--    - Skill 8: Kiếm phi kinh thiên (AoE: Nếu nhiễm điện -> Quái thường bị Execute hiện "DIET", Boss/Player chuyển thành sát thương chuẩn font trắng)
-- =============================================================================

-- Cập nhật bảng skill (nếu có lưu mô tả hoặc thuộc tính trong db)
UPDATE `skills`
SET
    `description` = 'Tung chuỗi kiếm liên hoàn chớp giật như lôi đình. Gây hiệu ứng Nhiễm điện trong 5 giây lên mục tiêu.'
WHERE
    `class` = 0
    AND `skill_id` = 3;

UPDATE `skills`
SET
    `description` = 'Nội tại: Mọi đòn đánh gây thêm sát thương chuẩn và có tỉ lệ gây Nhiễm điện 5 giây.'
WHERE
    `class` = 0
    AND `skill_id` = 4;

UPDATE `skills`
SET
    `description` = 'Vận kiếm khí hộ thể trong 60 giây: Giảm 5%-14% sát thương nhận vào, có 25%-70% tỷ lệ phản lại 50%-95% công bản thân (font vàng).'
WHERE
    `class` = 0
    AND `skill_id` = 5;

UPDATE `skills`
SET
    `description` = 'Triệu cuồng lôi tấn công diện rộng. Khiến toàn bộ kẻ địch trúng chiêu bị Nhiễm điện trong 5 giây.'
WHERE
    `class` = 0
    AND `skill_id` = 6;

UPDATE `skills`
SET
    `description` = 'Phóng luồng điện quang cực mạnh càn quét diện rộng. Nếu mục tiêu Nhiễm điện: Gây thêm sát thương chuẩn và Choáng 1 giây.'
WHERE
    `class` = 0
    AND `skill_id` = 7;

UPDATE `skills`
SET
    `description` = 'Cự kiếm từ chín tầng mây cắm xuống đất tiêu diệt diện rộng. Nếu mục tiêu Nhiễm điện: Tiêu diệt quái thường ngay lập tức, gây sát thương chuẩn lên quái cao cấp và người chơi.'
WHERE
    `class` = 0
    AND `skill_id` = 8;