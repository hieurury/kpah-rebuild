-- ==========================================================
-- SCRIPT CẬP NHẬT KỸ NĂNG & THÔNG SỐ ĐẤU SĨ (DAU_SI - CLASS ID: 3)
-- KPAH Chill Mod - Toàn diện Đấu Sĩ: Tanker & Khống Chế
-- ==========================================================

-- Cập nhật mô tả kỹ năng mới và thời gian hồi chiêu trong bảng skill_news
UPDATE `skill_news` SET 
    `decript` = 'Tạo địa chấn rung chuyển mặt đất, tấn công nhiều mục tiêu diện rộng và làm GIẢM 10% GIÁP của kẻ địch trong 5 giây. Hồi chiêu: 5s.'
WHERE `id` = 10 AND `charClass` = 3;

UPDATE `skill_news` SET 
    `decript` = 'Tạo mưa thiên thạch đá tảng nghiền nát kẻ thù diện rộng, có tỉ lệ HÓA ĐÁ đối thủ trong 1 giây (bất động hoàn toàn). Hồi chiêu: 6s.'
WHERE `id` = 11 AND `charClass` = 3;

UPDATE `skill_news` SET 
    `decript` = 'Tuyệt kỹ giáng búa triệu hồi cột thạch nhũ công kích cực mạnh, gây CHOÁNG 1 giây diện rộng. Sát thương tăng thêm 1% với mỗi 1.000 HP tối đa. Hồi chiêu: 7s.'
WHERE `id` = 12 AND `charClass` = 3;

-- Ghi chú:
-- Các logic sau đã được cấu hình và thực thi trực tiếp trong mã nguồn Server (KPAH.jar):
-- - Base Stats: Máu cơ bản 30, Thân pháp 20, Hệ số HP tối đa tăng lên 90 (Point.java).
-- - Skill 3 (Khổng kình bát vĩ): Cấp 1-3 đánh 3 đòn, cấp 4+ số đòn = cấp (max 9 đòn ở cấp 9). Mỗi đòn gây sát thương riêng biệt và có 50% tỉ lệ gây Choáng 1s (SkillService.java).
-- - Skill 4 (Bất di biến): Tăng kích thước nhân vật, thời gian duy trì 60s, hồi chiêu 80s, buff thêm sát thương theo % HP tối đa (5% + 3%/cấp) (Manager.java, Point.java).
-- - Skill 5 (Khí huyết sinh sôi): Nội tại tăng HP tối đa (10% + 5%/cấp) và hồi 2% HP/s sau 10s không nhận sát thương (Point.java, Player.java).
-- - Hiệu ứng mới BUFF_HOA_DA (ID 7) và BUFF_GIAM_GIAP (ID 9) đã được đồng bộ chuẩn giữa Client và Server.
