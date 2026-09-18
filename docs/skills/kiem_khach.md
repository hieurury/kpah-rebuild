# ⚔️ BỘ KỸ NĂNG LỚP KIẾM KHÁCH (SWORDSMAN) — KPAH REBALANCE

> **Lớp:** Kiếm Khách (Class ID: `0`)  
> **Thuộc tính:** Sát thương Vật lý cận chiến cao, diệt Tanker, xuyên giáp, tấn công đa mục tiêu  
> **Định vị:** Chỉ số cơ bản ở mức trung bình ổn định (Str 25, Agi 20, Spi 10, Hea 25, Luck 10; HP factor = 75, MP factor = 20). Tiêu hao MP trung bình - thấp, thời gian hồi chiêu chuẩn hóa.  
> **Cơ chế độc quyền:** **Hiệu ứng Nhiễm Điện & Sấm Sét Lan Truyền** (Chain Lightning).

---

## ⚡ Cơ Chế Độc Quyền: Nhiễm Điện (Electrified)

* **Mã buff:** `BUFF_NHIEM_DIEN = 12`
* **Hiệu ứng đồ họa (Client):**
  * Aura sấm sét màu vàng neon bao quanh mục tiêu bị nhiễm điện trong 5 giây.
  * Khi kích hoạt lan sét, các tia lôi điện màu vàng giật sang các mục tiêu xung quanh.
* **Quy tắc kích nổ & lan truyền (Chain Reaction):**
  1. Kẻ địch đang bị nhiễm điện, **bất cứ khi nào trúng bất kỳ chiêu thức tấn công nào** sẽ lập tức kích hoạt lan sét sang **tối đa 2 kẻ địch lân cận trong phạm vi $\le 40\text{px}$**.
  2. Sát thương của đòn sét lan bằng **$25\%$ sát thương của đòn đánh gốc**.
  3. **Tiêu hao điện tích:** Sau khi lan sét, mục tiêu gốc **ngay lập tức mất trạng thái Nhiễm điện** (aura vàng biến mất).
  4. **Lan truyền liên hoàn:** Nếu kẻ địch nhận đòn lan vốn đang bị Nhiễm điện, nó tiếp tục giải phóng điện tích lan tiếp sang 2 kẻ địch lân cận khác ($25\%$ của sát thương đòn lan) rồi cũng mất Nhiễm điện. Đòn sét lan không gây Nhiễm điện cho kẻ chưa có.

---

## 📋 Bảng Tổng Quan Bộ Kỹ Năng

| ID | Tên Chiêu | Loại Chiêu | Cấp Độ Học | Lực Công | Hồi Chiêu | Mana Tiêu Hao | Cơ Chế / Hiệu Ứng Chính |
|:---:|:---|:---:|:---:|:---:|:---:|:---:|:---|
| **0** | **Chém** | Đơn thể | Lv 1 – 10 | 80% – 135% | 800ms | 0 | Đòn chém cơ bản |
| **1** | **Kim tinh pháp** | Đơn thể | Lv 6 – 15 | 140% – 205% | 1.5s | 3 – 12 | Đòn đánh đơn mục tiêu |
| **2** | **Lôi điện pháp** | Đơn thể | Lv 11 – 20 | 160% – 265% | 1.8s | 5 – 14 | Đòn đánh dồn lôi lực |
| **3** | **Kinh lôi bát thủ** | Đơn thể Multi-hit | Lv 17 – 42 | 200% – 300% | 3.0s – 4.0s | 6 – 20 | Multi-hit (3–9 đòn), gây **Nhiễm điện** 5s |
| **4** | **Hộ sát tiến** | Nội tại (Passive) | Lv 3 – 32 | — | Không có | 0 | Đòn đánh gây thêm **Sát thương chuẩn** + Tỉ lệ **Nhiễm điện** 5s |
| **5** | **Dĩ lực đáo công** | Buff chủ động | Lv 3 – 32 | — | 90s | 35 – 80 | Duy trì 60s. Giảm 5%–14% sát thương nhận vào, phản đòn 25%–70% tỉ lệ, phản 50%–95% công (Font Vàng) |
| **6** | **Thiên lôi điện trảm** | Đánh lan (AoE 1) | Lv 25 – 41 | Đánh lan | 5s | 20 – 45 | Gây **Nhiễm điện** 5s toàn bộ mục tiêu trúng chiêu |
| **7** | **Sấm động dương gian** | Đánh lan (AoE 2) | Lv 30 – 46 | Đánh lan | 6s | 25 – 55 | Nếu mục tiêu Nhiễm điện: Gây thêm Sát thương chuẩn + Choáng 1s |
| **8** | **Kiếm phi kinh thiên** | Đánh lan (AoE 3) | Lv 45 – 61 | Đánh lan | 7s | 35 – 75 | Nếu mục tiêu Nhiễm điện: **Quái thường bị Execute (hiện "DIET")**; Boss/Người chơi chuyển toàn bộ thành Sát thương chuẩn |

---

## 🔍 Chi Tiết Bộ Kỹ Năng

### Skill 0: Chém
* **Tên chiêu:** Chém
* **Mô tả:** Đòn chém kiếm cơ bản gây sát thương vật lý lên mục tiêu.
* **Lực công:** `80%, 90%, 95%, 100%, 105%, 110%, 115%, 120%, 125%, 130%, 135%`
* **Hồi chiêu:** 800ms
* **Mana tiêu hao:** 0

---

### Skill 1: Kim tinh pháp
* **Tên chiêu:** Kim tinh pháp
* **Mô tả:** Vận kình lực vào kiếm chém mạnh vào điểm yếu đối phương.
* **Lực công:** `140%, 150%, 155%, 160%, 165%, 170%, 175%, 180%, 190%, 200%, 205%`
* **Hồi chiêu:** 1.5s
* **Mana tiêu hao:** `3, 4, 5, 6, 7, 8, 9, 10, 11, 12`

---

### Skill 2: Lôi điện pháp
* **Tên chiêu:** Lôi điện pháp
* **Mô tả:** Kiếm khí tích tụ lôi điện giáng xuống đối thủ gây lượng lớn sát thương.
* **Lực công:** `160%, 210%, 215%, 220%, 225%, 230%, 235%, 240%, 250%, 260%, 265%`
* **Hồi chiêu:** 1.8s
* **Mana tiêu hao:** `5, 6, 7, 8, 9, 10, 11, 12, 13, 14`

---

### Skill 3: Kinh lôi bát thủ
* **Tên chiêu:** Kinh lôi bát thủ
* **Mô tả:** Tuyệt kỹ đơn kiếm xuất chiêu thần tốc như lôi đình bão táp. Tung ra chuỗi đòn kiếm liên hoàn chém liên tiếp vào đối thủ.
* **Số đòn đánh liên hoàn (Multi-hit):**
  * Cấp 1 – 3: 3 đòn liên hoàn.
  * Cấp 4+: Số đòn đánh bằng cấp độ chiêu (Cấp 4: 4 đòn, Cấp 5: 5 đòn ... Cấp 9: 9 đòn).
* **Hiệu ứng kèm theo:** Gây hiệu ứng **Nhiễm điện** trong 5 giây lên mục tiêu.
* **Hồi chiêu:** 3.0s – 4.0s (`3000ms + (cấp - 1) * 125ms`)
* **Mana tiêu hao:** `6, 7, 8, 9, 10, 12, 14, 16, 18, 20`

---

### Skill 4: Hộ sát tiến (Nội tại)
* **Tên chiêu:** Hộ sát tiến
* **Mô tả:** Tâm pháp kiếm thuật tôi luyện kiếm khí đạt đến mức sắc bén vô song, bỏ qua phòng ngự của đối thủ và tích tụ tĩnh điện trên mũi kiếm.
* **Cơ chế:**
  * **Sát thương chuẩn cộng thêm:** Mỗi đòn đánh của Kiếm Khách gây thêm lượng sát thương chuẩn (True Damage):
    $$\text{True Damage} = 10 + (\text{Cấp} - 1) \times 2 + 2\% \text{ Lực Tấn Công}$$
    *(Bỏ qua 100% giáp và kháng phòng ngự, hiển thị Font Trắng trên đầu mục tiêu).*
  * **Tỉ lệ gây Nhiễm điện:** Mọi đòn đánh có tỉ lệ $10\% + (\text{Cấp} - 1) \times 5\%$ (Cấp 1: 10%, Cấp 10: 55%) khiến kẻ địch dính hiệu ứng **Nhiễm điện** trong 5 giây.
* **Hồi chiêu:** Không có (Bị động)
* **Mana tiêu hao:** 0

---

### Skill 5: Dĩ lực đáo công
* **Tên chiêu:** Dĩ lực đáo công
* **Mô tả:** Vận chuyển kiếm khí bao bọc thân thể tạo thành kiếm trận hộ thể, vừa triệt tiêu một phần uy lực đòn đánh của kẻ địch, vừa phản ngược kiếm khí trừng phạt kẻ tấn công.
* **Thời gian duy trì:** Cố định **60 giây** mọi cấp độ.
* **Thời gian hồi chiêu:** Cố định **90 giây** mọi cấp độ (khoảng trống 30s).
* **Mana tiêu hao:** `35, 40, 45, 50, 55, 60, 65, 70, 75, 80`
* **Hiệu ứng chiêu thức:**
  * **Giảm sát thương nhận vào:** Giảm $5\% + (\text{Cấp} - 1) \times 1\%$ (Cấp 1: 5%, Cấp 10: 14%) toàn bộ sát thương nhận vào.
  * **Tỷ lệ phản đòn:** $25\% + (\text{Cấp} - 1) \times 5\%$ (Cấp 1: 25%, Cấp 10: 70%).
  * **Sát thương phản đòn:** Phản lại $50\% + (\text{Cấp} - 1) \times 5\%$ (Cấp 1: 50%, Cấp 10: 95%) lực tấn công của Kiếm Khách.
  * **Hiển thị:** Sát thương phản đòn nhảy số bằng **Font Vàng neon** (`fs_yellow.png`).

---

### Skill 6: Thiên lôi điện trảm (AoE 1)
* **Tên chiêu:** Thiên lôi điện trảm
* **Mô tả:** Vung kiếm chỉ trời triệu hồi cuồng lôi giáng xuống vùng chiến địa, tấn công đồng loạt nhiều kẻ địch.
* **Hiệu ứng đặc biệt:** Khiến **toàn bộ kẻ địch trúng chiêu bị Nhiễm điện trong 5 giây**.
* **Hồi chiêu:** 5.0 giây
* **Mana tiêu hao:** `20, 22, 25, 28, 31, 34, 37, 40, 43, 45`

---

### Skill 7: Sấm động dương gian (AoE 2)
* **Tên chiêu:** Sấm động dương gian
* **Mô tả:** Phóng luồng điện quang cực mạnh chấn động mặt đất càn quét nhiều mục tiêu.
* **Hiệu ứng đặc biệt:**
  * Nếu mục tiêu đang bị **Nhiễm điện**:
    * Gây thêm lượng **Sát thương chuẩn** tương đương $20\% + (\text{Cấp} - 1) \times 2\%$ (Cấp 1: 20%, Cấp 10: 38%) sát thương chiêu thức (hiển thị Font Trắng).
    * Gây hiệu ứng **Choáng (Stun) trong 1.0 giây**.
    * Kích hoạt đòn sét lan truyền sang 2 kẻ địch lân cận và giải phóng hoàn toàn trạng thái Nhiễm điện.
* **Hồi chiêu:** 6.0 giây
* **Mana tiêu hao:** `25, 28, 31, 34, 37, 40, 43, 46, 50, 55`

---

### Skill 8: Kiếm phi kinh thiên (AoE 3)
* **Tên chiêu:** Kiếm phi kinh thiên
* **Mô tả:** Ngưng tụ kình lực trời đất thành cự kiếm từ chín tầng mây cắm thẳng xuống đất tiêu diệt diện rộng.
* **Hiệu ứng đặc biệt:**
  * Nếu mục tiêu đang bị **Nhiễm điện**:
    * **Đối với Quái thường:** **Tiêu diệt ngay lập tức (Execute - One-hit Kill)**, trên đầu quái hiện chữ **`"DIET"`** màu đỏ nhảy lên (như chữ CHÍ MẠNG), không kèm số sát thương.
    * **Đối với Người chơi, Quái Tinh Anh & Boss:** Toàn bộ sát thương chiêu thức chuyển hóa thành **Sát thương chuẩn** (bỏ qua 100% giáp và kháng phòng ngự, hiển thị Font Trắng).
    * Kích hoạt chuỗi lan sét sang 2 kẻ địch lân cận và giải phóng hoàn toàn trạng thái Nhiễm điện.
* **Hồi chiêu:** 7.0 giây
* **Mana tiêu hao:** `35, 39, 43, 47, 51, 55, 60, 65, 70, 75`

---

## 🎨 Hệ Thống Font & Đồ Họa Hiển Thị

1. **Font Vàng Neon (`fs_yellow.png` - 7x344):**
   * Sử dụng riêng biệt cho **Sát thương phản đòn** từ Skill 5 (Dĩ lực đáo công).
2. **Font Trắng Tinh Khôi (`fss_white.png` - 4x215):**
   * Sử dụng cho **Sát thương chuẩn (True Damage)** từ Skill 4 nội tại, Skill 7 kích nổ và Skill 8 lên Boss/PvP.
3. **Popup Chữ "DIET" (`class_zp` - index 5):**
   * Sử dụng riêng cho cơ chế **Execute (Tiêu diệt tức thì)** của Skill 8 lên quái thường bị Nhiễm điện.
4. **Aura Sấm Sét Vàng (`class_zx` - effect 12):**
   * Vòng hào quang điện tích màu vàng neon quanh người mục tiêu bị Nhiễm điện trong 5 giây.
