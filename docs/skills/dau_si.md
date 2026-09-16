# 🛡️ BỘ KỸ NĂNG & THIẾT KẾ TOÀN DIỆN LỚP ĐẤU SĨ (TANKER & KHỐNG CHẾ) — KPAH

> **Lớp:** Đấu Sĩ (Class ID: `3`)  
> **Định vị:** Pure Tanker & Khống chế tối thượng của chiến trường  
> **Thuộc tính:** Tấn công Vật lý cận chiến, Hệ Thổ  
> **Vũ khí:** Búa (Đao búa / Rìu búa)  
> **Triết lý thiết kế:** Sát thương cơ bản và sát thương skill thấp hơn các sát thủ thuần công, tuy nhiên lượng HP và Giáp khổng lồ biến Đấu Sĩ thành một pháo đài sống. Sát thương của Đấu Sĩ tỉ lệ thuận (scale) mạnh mẽ với lượng Máu tối đa (Max HP). Khả năng khống chế liên hoàn: Choáng, Hóa Đá và Bào Giáp kẻ thù.

---

## 📊 Chỉ Số Cơ Bản (Base Stats)

| Chỉ số | Trước thay đổi | Thiết kế mới (Sau Rebalance) | Ý nghĩa chiến lược |
|:---|:---:|:---:|:---|
| **Máu cơ bản (Health / Hea)** | `20` | **`30`** (+50%) | Tăng lượng HP khởi đầu và khả năng chống chịu |
| **Thân pháp (Agility / Agi)** | `30` | **`20`** (-33%) | Giảm nhẹ né tránh/tốc đánh để chuẩn tanker |
| **Hệ số tính HP Tối Đa (Max HP Factor)** | `70` | **`90`** (+28.5%) | Lượng HP tăng thêm theo từng điểm cộng Health cao nhất game |
| **Mana tiêu hao & Cooldown** | Tiêu chuẩn cũ | **Được tối ưu lại** | CD trung bình và Mana tiêu hao hợp lý để tránh spam vô hạn khống chế |

---

## 📋 Bảng Tổng Quan Kỹ Năng Đấu Sĩ

| ID | Tên Chiêu | Loại Chiêu | Cấp Học | Sát Thương Skill | Hồi Chiêu | Mana Tiêu Hao | Hiệu Ứng Đặc Trưng / Khống Chế |
|:---:|:---|:---:|:---:|:---:|:---:|:---:|:---|
| **0** | **Đập** | Đơn thể | Lv 1 – 10 | 70% – 110% | 0.8s | 0 | Đòn vung búa cận chiến cơ bản |
| **1** | **Thổ Tú** | Đơn thể | Lv 6 – 15 | 110% – 160% | 1.5s | 8 | Đòn đánh dồn thổ kình đơn thể |
| **2** | **Kim sơn thủy** | Đơn thể | Lv 11 – 20 | 130% – 190% | 1.8s | 12 | Khí thế như núi non sông nước |
| **3** | **Khổng kình bát vĩ** | Đơn thể (Multi-hit) | Lv 17 – 42 | 50% – 85% / đòn | 3.0s – 4.0s | 18 – 30 | **Đòn đơn mạnh nhất:** Cấp 1-3 tung 3 đòn; Cấp 4+ tung số đòn bằng cấp (max 9 đòn ở cấp 9). Mỗi đòn gây sát thương riêng và có **50% tỉ lệ gây Choáng 1s**. |
| **4** | **Bất di biến** | Buff chủ động | Lv 3 – 30 | Thêm dame theo HP | **80s** | 45 – 65 | **Phóng to kích thước nhân vật**, duy trì **60s**. Tăng thêm sát thương = **`5% + 3%/cấp` lượng HP tối đa**. |
| **5** | **Khí huyết sinh sôi** | Bị động (Nội tại) | Lv 3 – 30 | — | Nội tại | 0 | **Tăng vĩnh viễn `10% - 55%` HP tối đa**. Khi không nhận sát thương trong 10s, tự động **hồi 2% HP/giây**. |
| **6** | **Kinh thiên động địa** | AoE (Đánh lan) | Lv 25 – 41 | 180% – 250% | **5.0s** | 35 – 55 | Địa chấn rung chuyển mặt đất, làm **GIẢM 10% GIÁP** của tất cả kẻ địch trúng đòn trong **5 giây**. |
| **7** | **Sơn Tinh bộ thiên** | AoE (Đánh lan) | Lv 30 – 46 | 210% – 280% | **6.0s** | 45 – 70 | Mưa đá thiên thạch, có **tỉ lệ 20% – 65% gây HÓA ĐÁ đối thủ trong 1 giây** (bất động hoàn toàn). |
| **8** | **Thạch nhũ công tâm** | AoE (Đánh lan) | Lv 45 – 61 | 240% – 320% | **7.0s** | 60 – 95 | Trồi cột thạch nhũ giáng đòn hủy diệt, **gây Choáng 1 giây** diện rộng và **tăng 1% sát thương cho mỗi 1.000 HP tối đa**. |

---

## 🔍 Chi Tiết Cơ Chế Từng Kỹ Năng

### 🔨 Skill 3: Khổng kình bát vĩ
* **Mô tả:** Đấu Sĩ dồn toàn bộ kình lực vung búa liên hoàn cực hạn vào mục tiêu duy nhất.
* **Cơ chế số đòn đánh (Multi-hit) & Bug Fix:**
  * Ở phiên bản cũ, animation vung nhiều đòn nhưng server chỉ tính sát thương 1 lần.
  * Bản mod mới đã fix triệt để: **Mỗi đòn vung búa đều được tính sát thương độc lập gửi về client**.
  * **Cấp 1 – 3:** Tung đúng **3 đòn** liên hoàn.
  * **Cấp 4 – 9:** Số đòn đánh bằng chính **cấp độ kỹ năng** (Cấp 4 = 4 đòn, Cấp 5 = 5 đòn ... Cấp 9 = 9 đòn).
* **Khống chế:** Mỗi đòn đánh có **50% tỷ lệ gây Choáng (Stun) trong 1 giây**. Khả năng khóa chết mục tiêu nếu trúng trọn combo!

### 🏔️ Skill 4: Bất di biến (Buff Kích Thước & Scale HP)
* **Mô tả:** Vận chuyển thổ khí kích thích cơ bắp phình to, hóa thân thành người khổng lồ hộ vệ.
* **Thời gian duy trì:** **60 giây** cố định.
* **Thời gian hồi chiêu:** **80 giây** (Đảm bảo có khoảng nghỉ 20s, không thể buff liên tục).
* **Hiệu ứng đặc biệt:**
  * **Tăng kích thước cơ thể:** Server đồng bộ trạng thái, Client render phóng to nhân vật Đấu Sĩ.
  * **Sát thương gia tăng theo Máu tối đa:**
    $$\text{Bonus Damage} = \text{HP}_{\max} \times [5\% + (\text{Level} - 1) \times 3\%]$$
    *(Ví dụ: Cấp 1 thêm 5% Max HP vào đòn đánh, cấp 10 thêm tới 32% Max HP vào từng đòn đánh!)*

### 🩸 Skill 5: Khí huyết sinh sôi (Nội Tại HP & Hồi Phục)
* **Mô tả:** Cơ thể tôi luyện qua ngàn đòn roi, sở hữu huyết mạch dồi dào và khả năng tái sinh mạnh mẽ.
* **Sửa lỗi logic cũ:** Loại bỏ công thức tăng giáp cũ có lỗi chia số nguyên (`15 + (lv-1)*5 / 100`).
* **Hiệu ứng nội tại mới:**
  * **Gia tăng Max HP:** Tăng vĩnh viễn từ **+10% đến +55% HP tối đa** (cấp 1: +10%, mỗi cấp +5%, cấp 10: +55%).
  * **Hồi phục sinh mệnh phi giao tranh:** Sau **10 giây** không nhận sát thương từ bất kỳ nguồn nào, Đấu Sĩ tự động hồi **2% HP tối đa mỗi giây** cho đến khi đầy máu.

### 💥 Skill 6: Kinh thiên động địa (AoE Giảm Giáp)
* **Phạm vi:** Lan rộng ra xung quanh mục tiêu chính.
* **Hồi chiêu:** **5 giây**.
* **Debuff Giảm Giáp:** Tất cả kẻ địch (quái vật hoặc người chơi) trúng đòn đều bị gắn hiệu ứng **BUFF_GIAM_GIAP** (ID: 9) trong **5 giây**.
* **Cơ chế giảm giáp:** Kẻ địch đang bị dính hiệu ứng sẽ bị **trừ 10% tổng chỉ số Giáp vật lý và Giáp phép** khi chịu sát thương. Vòng sáng đỏ cam hiển thị quanh chân mục tiêu.

### 🪨 Skill 7: Sơn Tinh bộ thiên (AoE Hóa Đá)
* **Phạm vi:** Trút mưa đá tảng diện rộng.
* **Hồi chiêu:** **6 giây**.
* **Hiệu ứng Hóa Đá (BUFF_HOA_DA - ID: 7):**
  * Tỉ lệ kích hoạt: **`20% + (Level - 1) * 5%`** (Cấp 1: 20%, Cấp 10: 65%).
  * Thời gian khống chế: **1 giây**.
  * Tác dụng: Mục tiêu bị biến thành tượng đá (vòng ma pháp xám tro phong ấn), hoàn toàn **bất động, không thể di chuyển, không thể tung chiêu hay đánh thường**.

### 🌋 Skill 8: Thạch nhũ công tâm (AoE Choáng & Scale 1% / 1000 HP)
* **Phạm vi:** Triệu hồi các cột thạch nhũ sắc nhọn trồi lên từ lòng đất càn quét khu vực rộng.
* **Hồi chiêu:** **7 giây**.
* **Hiệu ứng khống chế:** Gây **Choáng (Stun) 1 giây** cho mọi mục tiêu trúng đòn.
* **Sát thương dồn theo Máu:**
  $$\text{Bonus Sát Thương (\%)} = \lfloor \frac{\text{HP}_{\max}}{1000} \rfloor \times 1\%$$
  *(Ví dụ: Đấu Sĩ sở hữu 50.000 HP sẽ được tăng thêm thẳng 50% sát thương khi tung chiêu 8!)*
