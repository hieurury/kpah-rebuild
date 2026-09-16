-- ==========================================================
-- SCRIPT CẬP NHẬT KỸ NĂNG & THÔNG SỐ CUNG THỦ (CUNG_THU - CLASS ID: 4)
-- KPAH Chill Mod - Cung Thủ: Glass Cannon, Tầm Bắn Xa, Độc Dược & Bạo Kích
-- ==========================================================

-- Cập nhật mô tả kỹ năng mới trong bảng skill_news
UPDATE `skill_news` SET 
    `decript` = 'Bắn mưa tên tỏa ra khắp mười phương diện rộng. Tỉ lệ 10% (+2%/cấp) làm MÙ đối phương trong 1s (đòn tấn công của đối thủ 100% đánh hụt). Hồi chiêu: 6s.'
WHERE `id` = 13 AND `charClass` = 4;

UPDATE `skill_news` SET 
    `decript` = 'Hất tung đối phương và bắn bão tên liên hoàn diện rộng, gán VẾT THƯƠNG SÂU 5s (giảm 50% hồi phục HP của kẻ địch), đồng thời tăng bản thân 10% (+2%/cấp) NÉ TRÁNH trong 5s. Hồi chiêu: 7s.'
WHERE `id` = 14 AND `charClass` = 4;

UPDATE `skill_news` SET 
    `decript` = 'Mưa tên trút từ không trung bao phủ toàn trận địa. HÚT MÁU hồi HP cho bản thân bằng 10% (+2%/cấp) tổng sát thương gây ra lên toàn bộ kẻ địch. Hồi chiêu: 8s.'
WHERE `id` = 15 AND `charClass` = 4;

-- Ghi chú:
-- Các logic sau đã được cấu hình và thực thi trực tiếp trong mã nguồn Server (KPAH.jar) và Client:
-- - Base Stats Glass Cannon: Sức mạnh 20, Thân pháp 35, Tinh thần 10, Thể lực 10, May mắn 15.
--   Hệ số công Agi x2.2, HP max Hea x50, MP max Spi x16 (Point.java).
-- - Skill 3 (Bát kim tiễn đáo): Cấp 1-3 bắn 3 đòn, cấp 4+ số đòn = cấp (tối đa 9 đòn). Cơ chế Độc Nổ (Poison Detonate)
--   rút cạn toàn bộ sát thương độc DoT còn lại trên mục tiêu ngay lập tức và xóa bỏ hiệu ứng độc (BuffInfluence, SkillService).
-- - Skill 4 (Độc lưu tiễn): Buff chủ động duy trì 60s, hồi chiêu 80s (khoảng trống 20s). Gán độc DoT 10s:
--   mỗi giây gây sát thương = 30% (+5%/cấp) Lực tấn công (BuffService, Manager).
-- - Skill 5 (Hộ độc tiễn): Nội tại tăng 5% (+2%/cấp) Chí mạng; mục tiêu dính độc nhận thêm 50% (+10%/cấp) Sát thương chí mạng (Point, SkillService).
-- - Skill 6 (Thập diện tâm tiễn): Hồi chiêu 6s, tỉ lệ 10% (+2%/cấp) gây MÙ 1s (đối phương đánh hụt 100%) (SkillService, MonsterService).
-- - Skill 7 (Thăng thiên loạn tiễn): Hồi chiêu 7s, gây Vết thương sâu 5s (giảm 50% hồi HP), tăng bản thân 10% (+2%/cấp) Né tránh trong 5s (SkillService, Point).
-- - Skill 8 (Vạn tiễn quy tâm): Hồi chiêu 8s, Hút máu hồi HP = 10% (+2%/cấp) tổng sát thương gây ra (SkillService).
-- - Chuẩn hóa Mana tiêu hao: Cung Thủ tiêu hao mana cao thứ hai sau Pháp Sư (Manager.SKILL_MP).
-- - Client UI: Cập nhật format mô tả chiêu thức 1-9 hiển thị đầy đủ thông số tiếng Việt rõ ràng, chuẩn hóa icon visual debuff (class_sc.java, MsgHandler.java, class_zx.java).
