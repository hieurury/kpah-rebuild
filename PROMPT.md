Vài vấn đề liên quan đến chế tạo trang bị.
Nói sơ qua về cơ chế nguyên liệu và chế tạo.
Nguyên liệu bao gồm nguyên liệu thô, nguyên liệu sơ cấp và nguyên liệu cao cấp.
Nguyên liệu thô được lấy qua thu thập từ khu mỏ. cứ 5 nguyên liệu thô sẽ ghép được 1 nguyên liệu sơ cấp.
Nguyên liệu cao cấp thì không farm được, chủ yếu được kiếm từ các nhiệm vụ, rương thưởng hoặc mua trực tiếp từ phú ông hoặc các NPC khác.

> Ở đây ta sẽ tái thiết lại toàn bộ logic chế tạo trang bị của bản game gốc.
Các nguyên liệu không còn chia phẩm cấp. tất cả nguyên liệu sẽ điều là 1 dạng cấp bậc, trang bị chỉ tiêu hao số nguyên liệu khác nhau thôi.
Trang bị vẫn chia từ ngũ phẩm đến nhất phẩm. Số nguyên liệu cứ tăng dần theo các điều kiện sau: phẩm cấp trang bị, lv trang bị.
Các nguyên liệu cần để chế tạo trang bị: Nguyên liệu sơ cấp, nguyên liệu cao cấp, ngọc rèn và tiền xu. Trong đó trang bị Ngũ và tứ phẩm sẽ không yêu cầu đến nguyên liệu cao cấp.

Về chỉ số, các chỉ số của trang bị chế tạo vượt trội hơn rất nhiều so với trang bị mua thông thường, từ ngũ phẩm đến nhất phẩm còn có các dòng thuộc tính bổ sung ngòai các thuộc tính cơ bản của trang bị.
Các chỉ số bổ sung sẽ bao gồm:
- tăng %HP 2-5%
- tăng %MP 2-5%
- tăng HP 1000-5000
- tăng MP 1000-5000
- tăng % thủ 2-5%
- tăng thủ vật 50-100
- tăng thủ ma 50-100
- tăng % công 2-5%
- tăng công 50-100
- tăng sức mạnh 5-10 điểm
- tăng tinh thần 5-10 điểm
- tăng khéo léo 5-10 điểm
- tăng sức khỏe 5-10 điểm
- tăng may mắn 5-10 điểm

Riêng trang bị nhất phẩm có thêm 2 thuộc tính đặc biệt bao gồm ngẫu nhiên trong đây
- giảm sát thương 2-5%
- tăng sát thương 2-5%
- né tránh 2-5%
- chí mạng 5-10%
- xuyên giáp 2-5%
- tăng sát thương chí mạng 10-20%

---
Về UI menu chế tạo, hiện tại menu chưa ổn định lắm, khi di chuyển qua các trang bị hay chuyển tab thì chưa được mượt mà, hiện tại khi bấm -> và <- thì tab chuyển luôn, phải là di chuyển lên tiêu đề tab rồi bấm 2 nút đó mới chuyển, nếu không 2 nút đó sẽ đổi target item trong trang hiện tại. Khi đổi target item thì di chuyển màn hình theo danh sách, hiện tại bấm target cái trang bị cuối thì màn hình vẫn đứng ở trang bị đầu.

---
các trang bị chế tạo đang chưa đúng và chưa đủ. Đê tránh phình to thì tôi chỉ yêu cầu làm các nhóm trang bị ở các mốc lv sau

vũ khí: 21, 26, 31, 36
trang bị: 20, 25, 30, 35

lập bảng danh sách gồm: lv, tên trang bị, số nguyên liệu và tài nguyên để chế tạo, chỉ số cơ bản, số thuộc tính cộng thêm và link dẫn tới hình ảnh của trang bị đó cho tôi check.
đảm bảo đủ 2 giới tính nam và nữ nhé.

> Nếu thấy thiếu gì hay còn thắc mắt gì thì cứ góp ý và hỏi, tôi sẽ mô tả và đóng góp thêm.