Điều chỉnh class kiếm khách.
Kiếm khách là lớp nhân vật cận chiến sát thương cao chỉ sau cung thủ, đặc điểm mạnh nhất là tính chất diệt tanker, xuyên giáp cao và khả năng tấn công đa mục tiêu mạnh mẽ.

---
Điều chỉnh lại các thuộc tính cơ bản của kiếm khách điều ở mức trung bình, không quá cao cũng không quá thấp, mọi thứ điều ổn định.
Mức độ tiêu hao MP ở mức trung bình - thấp, thời gian hồi chiêu ở mức trung bình.

---
Điều chỉnh kỹ năng
### Skill 3: Kinh lôi bát thủ
điều chỉnh lại như cung thủ và đấu sĩ, với số lần tung chiêu và sát thương tương ứng với cấp chiêu như họ. Bổ sung hiệu ứng "Nhiễm điện" 5 giây lên mục tiêu, kẻ địch bị nhiễm điện khi bị tấn công sẽ lan sát thương sang 2 kẻ địch xung quanh với sát thương gây ra bằng 25% sát thương gốc.

### Skill 4: Hộ sát tiến
Các đòn tấn công ng gây thêm 10 (+2 + 2% tấn công) sát thương chuẩn. Ngoài ra, kẻ địch có tỷ lệ 10% (+5% mỗi cấp) bị dính nhiễm điện.

### Skill 5: Dĩ lực đáo công
Thời gian duy trì cố định 60s và hồi chiêu 90s. Hiệu ứng skill, trong thời gian tác dụng, kiếm khách được giảm 5% (+1% mỗi cấp) sát thương nhận vào, kẻ địch tấn công sẽ có 25% (+5% mỗi cấp) bị phản lại sát thương tương ứng với 50% (+5% mỗi cấp) sát thương của kiếm sẽ lên chúng.


### Skill 6: Thiên lôi điện trảm
Bổ sung hiệu ứng cho skill. Gây nhiễm điện 5s cho toàn bộ kẻ địch trúng chiêu.

### Skill 7: Sấm động dương gian
Bổ sung hiệu ứng skill. Nếu kẻ địch bị nhiễm điện, gây thêm 20% (+2% mỗi cấp) sát thương ở dạng sát thương chuẩn và làm chóang chúng 1s.

### Skill 8: Kiếm phi kinh thiên
Bổ sung hiệu ứng cho skill, các kẻ địch là quái thường, nếu đang nhiễm điện sẽ lập tức bị tiêu diệt. Nếu là người chơi hoặc là các quái tinh anh trở lên, sẽ nhận thêm 50% sát thương.

---
Các vấn đề cần bỏ sung.
1. Hiệu ứng nhiễm điện và cơ chế sát thương phản đòn.
Tận dụng hiệu ứng bao quanh người của kiếm khách từ skill 5 để áp dụng lên các mục tiêu nhiễm điện làm hiệu ứng, đổi màu lại từ trắng(gốc) -> sang vàng(nhiễm điện). Hiệu ứng giật sét lan đối với các mục tiêu bị nhiễm điện, bình thường phản đòn chỉ hiện dame trên đầu mục tiêu mà không có hiệu ứng, tuy nhiên tôi thấy skill của kiếm khách có skill 6 là có một logic giật điện từ mục tiêu này sang mục tiêu khác, thiết nghĩ có thể tận dụng hiệu ứng này và áp lên các mục tiêu nhiễm điện làm hiệu ứng giật sét lan cho mục tiêu khác (đổi màu từ trắng -> vàng là được để phân biệt).

2. Về các font sát thương
Bổ sung thêm 2 font mới là vàng và trắng tương tự như font độc.
Trong đó font vàng dùng cho các loại sát thương cơ bản mà không phải sát thương độc để nhảy trên đầu mục tiêu, sát thương phản đòn sẽ là font này.
font trắng dành cho các dạng sát thương chuẩn mà mục tiêu phải chịu.

> font vàng thì có kích cỡ giống font tím /home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/res/font/fs_poison.png còn font trắng thì là dạng sát thương nhảy lên như font vàng này /home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/res/font/fss_yellow.png

---
Đảm bảo logic hoạt động đúng
kẻ địch bị nhiễm điện lan dame cho 2 kẻ địch gần bên (có giới hạn khoảng cách tầm <= 40px) và không áp nhiễm điện lên chúng.
Kẻ địch nhiễm điện lan sét cho kẻ địch đang nhiễm điện sẽ tiếp tục lan nhưng vẫn đảm bảo đòn lan này sẽ không gây nhiễm điện để tránh vòng lặp vĩnh cửu (tức là người chơi chỉ tung 1 đòn là quái tự lan ddame rồi chết hết)
đảm bảo dame lan sẽ giảm còn 25% mỗi lần thế.

Lên kế hoạch và chuẩn bị sửa đổi.