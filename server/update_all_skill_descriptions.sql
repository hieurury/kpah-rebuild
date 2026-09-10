-- Script cập nhật toàn bộ mô tả chiêu thức và hồi chiêu trong database kpah
-- Cập nhật bảng skill_news (hiển thị khi học kỹ năng tại NPC)

UPDATE `skill_news` SET 
    `des` = 'Dùng sức mạnh sấm sét tấn công nhiều mục tiêu trong phạm vi. Sát thương cực mạnh lan rộng, hồi chiêu: 60s.',
    `cooldown` = 60000
WHERE `id` = 1;

UPDATE `skill_news` SET 
    `des` = 'Dùng vũ khí dẫn truyền tia sét tạo chuỗi liên hoàn giáng vào các kẻ thù gần nhau. Hồi chiêu: 120s.',
    `cooldown` = 120000
WHERE `id` = 2;

UPDATE `skill_news` SET 
    `des` = 'Vận kình lực tạo đại kiếm khổng lồ từ không trung giáng xuống hủy diệt diện rộng. Hồi chiêu: 300s.',
    `cooldown` = 300000
WHERE `id` = 3;

UPDATE `skill_news` SET 
    `des` = 'Gọi sức mạnh rồng nước cuộn sóng tấn công nhiều mục tiêu. Sát thương cực mạnh lan rộng, hồi chiêu: 4s.',
    `cooldown` = 4000
WHERE `id` = 7;

UPDATE `skill_news` SET 
    `des` = 'Vận nội kình chưởng ra đôi rồng xoáy liên hoàn, công phá hủy diệt đối phương trong phạm vi. Hồi chiêu: 5s.',
    `cooldown` = 5000
WHERE `id` = 8;

UPDATE `skill_news` SET 
    `des` = 'Tuyệt kỹ tối thượng gọi bão tuyết băng giá trút ngàn mũi thương băng xuống chiến trường, hủy diệt diện rộng. Hồi chiêu: 6s.',
    `cooldown` = 6000
WHERE `id` = 9;
