-- Script cập nhật toàn bộ mô tả chiêu thức trong database kpah
-- Cập nhật bảng skill_news (hiển thị khi xem kỹ năng tại NPC)

UPDATE `skill_news` SET 
    `decript` = 'Dùng sức mạnh sấm sét tấn công nhiều mục tiêu trong phạm vi. Hồi chiêu: 60s.'
WHERE `id` = 1;

UPDATE `skill_news` SET 
    `decript` = 'Dùng vũ khí dẫn truyền tia sét tạo chuỗi liên hoàn giáng vào các kẻ thù gần nhau. Hồi chiêu: 120s.'
WHERE `id` = 2;

UPDATE `skill_news` SET 
    `decript` = 'Vận kình lực tạo đại kiếm khổng lồ từ không trung giáng xuống hủy diệt diện rộng. Hồi chiêu: 300s.'
WHERE `id` = 3;

UPDATE `skill_news` SET 
    `decript` = 'Gọi sức mạnh rồng nước cuộn sóng tấn công nhiều mục tiêu diện rộng. Hồi chiêu: 4s.'
WHERE `id` = 7;

UPDATE `skill_news` SET 
    `decript` = 'Vận nội kình chưởng ra đôi rồng xoáy liên hoàn, công phá hủy diệt đối phương diện rộng. Hồi chiêu: 5s.'
WHERE `id` = 8;

UPDATE `skill_news` SET 
    `decript` = 'Tuyệt kỹ tối thượng gọi bão tuyết băng giá trút thương băng diện rộng. Hồi chiêu: 6s.'
WHERE `id` = 9;
