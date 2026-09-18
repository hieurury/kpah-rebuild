-- =============================================================================
-- KPAH REBALANCE MIGRATION: ĐIỀU CHỈNH TOÀN DIỆN MÔN PHÁI KIẾM KHÁCH (CLASS 0)
-- Ngày thực hiện: 2026-09-18
-- =============================================================================

-- Bảng lưu sách kỹ năng trong database KPAH là `skill_news` (dành cho Skill 6, 7, 8).
-- Trong đó: Kiếm Khách có `charClass` = 0:
--   - id = 1, idSkill = 6: Thiên lôi điện trảm
--   - id = 2, idSkill = 7: Sấm động dương gian
--   - id = 3, idSkill = 8: Kiếm phi kinh thiên
-- Cột mô tả kỹ năng là `decript` (chú ý: không phải `description`).

-- 1. Skill 6: Thiên lôi điện trảm (AoE 1)
UPDATE `skill_news`
SET
    `decript` = 'Triệu cuồng lôi tấn công diện rộng. Khiến toàn bộ kẻ địch trúng chiêu bị Nhiễm điện trong 5 giây. Hồi chiêu: 5s.'
WHERE
    `charClass` = 0
    AND `idSkill` = 6;

-- 2. Skill 7: Sấm động dương gian (AoE 2)
UPDATE `skill_news`
SET
    `decript` = 'Phóng luồng điện quang cực mạnh càn quét diện rộng. Nếu mục tiêu Nhiễm điện: Gây thêm sát thương chuẩn (bỏ qua giáp) và Choáng 1 giây. Hồi chiêu: 6s.'
WHERE
    `charClass` = 0
    AND `idSkill` = 7;

-- 3. Skill 8: Kiếm phi kinh thiên (AoE 3)
UPDATE `skill_news`
SET
    `decript` = 'Cự kiếm từ chín tầng mây cắm xuống đất tiêu diệt diện rộng. Nếu mục tiêu Nhiễm điện: Tiêu diệt quái thường ngay lập tức, gây sát thương chuẩn lên Boss và Người chơi. Hồi chiêu: 7s.'
WHERE
    `charClass` = 0
    AND `idSkill` = 8;

-- =============================================================================
-- GHI CHÚ QUAN TRỌNG VỀ HỆ THỐNG KỸ NĂNG KPAH:
-- Trong database KPAH không có bảng `skills`.
-- Toàn bộ thông tin, tên chiêu thức và mô tả hiển thị của Skill 1 đến 5:
--   - Skill 1: Chém thường
--   - Skill 2: Ngưng kiếm
--   - Skill 3: Kinh lôi bát thủ (Multi-hit + Nhiễm điện 5s)
--   - Skill 4: Hộ sát tiến (Nội tại: Thêm sát thương chuẩn + Tỉ lệ nhiễm điện 5s)
--   - Skill 5: Dĩ lực đáo công (Buff hộ thể 60s/90s CD: Giảm sát thương nhận vào, phản đòn font vàng)
-- được quản lý và render trực tiếp trong mã nguồn Client Java (`class_sc.java`)
-- và mã nguồn Server Java (`Manager.java`, `SkillService.java`, `BuffService.java`).
-- =============================================================================