# 🔮 BỘ KỸ NĂNG LỚP PHÁP SƯ (MAGE) — KPAH

> **Phân loại:** Pháp Sư (Class ID: `2`)  
> **Thuộc tính:** Tấn công Phép thuật (Damage Magic), tầm xa.  
> **Đặc trưng:** Lượng MP dồi dào, khả năng tăng sát thương theo lượng mana hiện có, sở hữu khiên chuyển hóa sát thương và kỹ năng hồi sinh đồng đội.

---

## 📋 Danh Sách Kỹ Năng

| ID | Tên Kỹ Năng | Loại Chiêu | Cấp Học | MP Tiêu Hao | Hồi Chiêu (CD) | Cơ Chế / Thuộc Tính Chính |
|:---:|:---|:---:|:---:|:---:|:---:|:---|
| **0** | **Đánh** | Đơn thể | Lv 1 – 10 | 0 | 0.7s | Chưởng phép cơ bản, sát thương 80% – 135%. |
| **1** | **Thủy giáng minh** | Đơn thể | Lv 6 – 15 | 4 – 12 | 1.2s – 1.7s | Sát thương phép đơn thể 140% – 205%. |
| **2** | **Thần long thủy** | Đơn thể | Lv 11 – 20 | 12 – 28 | 1.6s – 2.0s | Sát thương phép đơn thể 160% – 265%. |
| **3** | **Bát đại hải long** | Đơn thể | Lv 17 – 42 | 15 – 55 | 1.8s – 2.6s | Sát thương phép đơn thể 200% – 300%. |
| **4** | **Hồi công lực đan** | Buff chủ động | **Lv 3 – 32** | 80 – 120 | **120s** | Tăng **Max HP%** & **Max MP%** thêm 10% – 85% trong **cố định 90s**. |
| **5** | **Hồi lực tiến** | Bị động (Passive) | **Lv 3 – 32** | 0 | Không có | **Tăng dame theo lượng MP đang có** (5% MP ở cấp 1, +2%/cấp). |
| **6** | **Hồi sinh** | Hồi sinh đồng đội | **Lv 6 – 33** | Theo mục tiêu | **180s – 90s** | Rút HP/MP bản thân truyền cho mục tiêu (tối đa 80% bản thân). **Giảm 10s hồi chiêu mỗi cấp** (180s $\rightarrow$ 90s). |
| **7** | **Song hộ công thủ** | Buff chủ động (Khiên) | **Lv 6 – 33** | 100 – 550 | 5s – 170s | **Hồi mana 10% (+5%/cấp)** sát thương nhận vào; **hồi máu 20% (+5%/cấp)** mana tiêu hao. |
| **8** | **Hải long xuất thế** | Lan (AoE 1) | Lv 25 – 41 | 10 – 55 | **4s** | Gọi rồng nước cuộn sóng đánh diện rộng. |
| **9** | **Song long thị uy** | Lan (AoE 2) | Lv 30 – 46 | 10 – 55 | **5s** | Song long xoắn ốc công phá hủy diệt diện rộng. |
| **10** | **Hàn băng vũ** | Lan (AoE 3) | Lv 45 – 61 | 10 – 90 | **6s** | Tuyệt kỹ mưa băng tuyết trút xuống chiến trường diện cực rộng. |

---

## 🔍 Chi Tiết Cơ Chế Từng Chiêu Thức

### 1. Kỹ Năng Tấn Công Đơn Thể (Skill 0 – 3)

* **Skill 0 — Đánh:**
  * Sát thương: `80%, 90%, 95%, 100%, 105%, 110%, 115%, 120%, 125%, 130%, 135%`
  * MP: 0 | Cooldown: 700ms | Phạm vi: 80
  * Cấp học: `1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10`

* **Skill 1 — Thủy giáng minh:**
  * Sát thương: `140%, 150%, 155%, 160%, 165%, 170%, 175%, 180%, 190%, 200%, 205%`
  * MP: `4, 4, 6, 6, 8, 8, 10, 10, 12, 12, 12` | Cooldown: `1200ms – 1700ms` | Phạm vi: 90
  * Cấp học: `6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 15`

* **Skill 2 — Thần long thủy:**
  * Sát thương: `160%, 210%, 215%, 220%, 225%, 230%, 235%, 240%, 250%, 260%, 265%`
  * MP: `12, 12, 14, 15, 18, 20, 22, 24, 26, 28, 28` | Cooldown: `1600ms – 2000ms` | Phạm vi: 100
  * Cấp học: `11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 20`

* **Skill 3 — Bát đại hải long:**
  * Sát thương: `200%, 240%, 245%, 250%, 255%, 260%, 265%, 270%, 285%, 295%, 300%`
  * MP: `15, 15, 20, 25, 30, 35, 40, 45, 50, 55, 55` | Cooldown: `1800ms – 2600ms` | Phạm vi: 110
  * Cấp học: `17, 20, 22, 24, 26, 30, 33, 36, 39, 42, 42`

---

### 2. Skill 4 — Hồi Công Lực Đan (Buff Max HP/MP)

* **Loại kỹ năng:** Buff chủ động bản thân.
* **Cấp học yêu cầu (Base Lv 3):** `[3, 6, 9, 12, 18, 24, 26, 28, 30, 32, 32]`
* **Mana tiêu hao (x10):** `[80, 80, 90, 90, 100, 100, 110, 110, 120, 120, 120]`
* **Thời gian hồi chiêu:** Cố định **120s** (`120,000ms`) cho mọi cấp (từ cấp 1 đến 10).
* **Thời gian hiệu lực buff:** Cố định **90s** cho mọi cấp.
* **Tác dụng:** Tăng cả **Max HP** và **Max MP** của Pháp Sư theo %:
  * Cấp 1: +10%
  * Cấp 2: +15%
  * Cấp 3: +20%
  * Cấp 4: +25%
  * Cấp 5: +30%
  * Cấp 6: +35%
  * Cấp 7: +45%
  * Cấp 8: +60%
  * Cấp 9: +70%
  * Cấp 10: +85%

---

### 3. Skill 5 — Hồi Lực Tiến (Nội Tại Tăng Dame Theo Mana)

* **Loại kỹ năng:** Kỹ năng bị động (Passive).
* **Cấp học yêu cầu (Base Lv 3):** `[3, 6, 9, 12, 18, 24, 26, 28, 30, 32, 32]`
* **Mana tiêu hao:** 0 (Không tốn MP).
* **Hồi chiêu:** Không có (Luôn kích hoạt trên mọi đòn đánh).
* **Cơ chế mới:** Cộng thẳng sát thương vào đòn đánh dựa trên **lượng Mana hiện có (`Current MP`)**:
  $$\text{Bonus Damage} = \frac{\text{Current MP} \times [5\% + (\text{LvSkill} - 1) \times 2\%]}{100}$$
  * Cấp 1: Tăng **5%** MP hiện tại thành sát thương.
  * Cấp 2: Tăng **7%** MP hiện tại.
  * Cấp 3: Tăng **9%** MP hiện tại.
  * Cấp 4: Tăng **11%** MP hiện tại.
  * Cấp 5: Tăng **13%** MP hiện tại.
  * Cấp 6: Tăng **15%** MP hiện tại.
  * Cấp 7: Tăng **17%** MP hiện tại.
  * Cấp 8: Tăng **19%** MP hiện tại.
  * Cấp 9: Tăng **21%** MP hiện tại.
  * Cấp 10: Tăng **23%** MP hiện tại.

---

### 4. Skill 6 — Hồi Sinh (Cứu Đồng Đội)

* **Loại kỹ năng:** Hỗ trợ / Hồi sinh đồng đội đã chết trong màn hình.
* **Cấp học yêu cầu (Base Lv 6):** `[6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 33]`
* **Thời gian hồi chiêu:** **180s ở cấp 1, giảm 10s mỗi cấp** (Cấp 1: 180s, Cấp 2: 170s, Cấp 3: 160s, Cấp 4: 150s, Cấp 5: 140s, Cấp 6: 130s, Cấp 7: 120s, Cấp 8: 110s, Cấp 9: 100s, Cấp 10: 90s).
* **Cơ chế mới (Truyền HP & MP trực tiếp, không phụ thuộc cấp chiêu):**
  * **Cần ít rút ít, cần nhiều rút nhiều:** Pháp Sư lấy HP và MP của chính mình bù đầy lượng HP (`HpMax`) và MP (`MpMax`) cho mục tiêu.
  * **Giới hạn an toàn:** Nếu lượng HP/MP mục tiêu cần quá cao, Pháp Sư **tối đa chỉ trích 80% lượng HP và 80% lượng MP hiện tại** của bản thân (luôn giữ lại tối thiểu 1 HP để bản thân không tử vong).
  * **Người được hồi sinh nhận đúng lượng HP & MP mà Pháp Sư trao cho.**
  * **Ví dụ minh họa:**
    * *Trường hợp mục tiêu nhỏ:* Pháp Sư có 10.000 HP & 5.000 MP; mục tiêu cần hồi sinh có 1.000 HP & 300 MP $\rightarrow$ Pháp Sư bị rút đúng 1.000 HP & 300 MP; mục tiêu hồi sinh nhận đúng 1.000 HP & 300 MP (đầy cây).
    * *Trường hợp mục tiêu lớn:* Mục tiêu cần hồi sinh có 50.000 HP $\rightarrow$ Pháp Sư tối đa chỉ rút 80% của 10.000 HP = 8.000 HP để trao cho họ $\rightarrow$ Mục tiêu hồi sinh nhận đúng 8.000 HP; Pháp Sư còn lại 2.000 HP. (MP tính tương tự).

---

### 5. Skill 7 — Song Hộ Công Thủ (Khiên Mana Hồi Máu)

* **Loại kỹ năng:** Buff khiên bảo hộ chủ động.
* **Cấp học yêu cầu (Base Lv 6):** `[6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 33]`
* **Mana tiêu hao (x10):** `[100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 550]`
* **Cơ chế mới (Cặp bài trùng Mana – Máu):**
  1. **Khi nhận sát thương (PvE & PvP):**
     * Hồi Mana = **$10\% + (\text{LvSkill} - 1) \times 5\%$** lượng sát thương nhận vào:
       * Cấp 1: Hồi **10%** dame nhận vào thành Mana.
       * Cấp 2: Hồi **15%**.
       * Cấp 5: Hồi **30%**.
       * Cấp 10: Hồi **55%** dame nhận vào thành Mana.
     * Sát thương thực tế nhận vào được giảm trừ bằng đúng lượng Mana hồi phục này.
  2. **Khi tiêu hao Mana (Dùng skill / Buff):**
     * Hồi Máu = **$20\% + (\text{LvSkill} - 1) \times 5\%$** lượng Mana tiêu hao:
       * Cấp 1: Hồi **20%** lượng MP tiêu hao thành Máu.
       * Cấp 2: Hồi **25%**.
       * Cấp 5: Hồi **40%**.
       * Cấp 10: Hồi **65%** lượng MP tiêu hao thành Máu.

---

### 6. Kỹ Năng Đánh Lan Diện Rộng (AoE Skill 8 – 10)

* **Skill 8 — Hải long xuất thế (AoE 1):**
  * Cấp học: Base Lv 25 (`[25, 25, 27, 29, 31, 33, 35, 37, 39, 41, 41]`)
  * MP tiêu hao: `10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 55`
  * Hồi chiêu (Đã tối ưu): **4s** (trước đây 60s).
  * Tác dụng: Cuộn sóng rồng nước tấn công đồng loạt quái/người chơi trong phạm vi.

* **Skill 9 — Song long thị uy (AoE 2):**
  * Cấp học: Base Lv 30 (`[30, 30, 32, 34, 36, 38, 40, 42, 44, 46, 46]`)
  * MP tiêu hao: `10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 55`
  * Hồi chiêu (Đã tối ưu): **5s** (trước đây 120s).
  * Tác dụng: Chưởng đôi rồng xoắn ốc liên hoàn, công phá hủy diệt đối phương trong phạm vi.

* **Skill 10 — Hàn băng vũ (AoE 3):**
  * Cấp học: Base Lv 45 (`[45, 45, 47, 49, 51, 53, 55, 57, 59, 61, 61]`)
  * MP tiêu hao: `10, 50, 55, 60, 65, 70, 75, 80, 85, 90, 90`
  * Hồi chiêu (Đã tối ưu): **6s** (trước đây 300s).
  * Tác dụng: Tuyệt kỹ gọi bão tuyết băng giá trút hàng loạt thương băng cực mạnh xuống chiến trường diện rộng.
