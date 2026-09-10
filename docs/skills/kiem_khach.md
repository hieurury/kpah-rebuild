# ⚔️ BỘ KỸ NĂNG LỚP KIẾM KHÁCH (SWORDSMAN) — KPAH

> **Phân loại:** Kiếm Khách (Class ID: `0`)  
> **Thuộc tính:** Tấn công Vật lý (Damage Physic), cận chiến.  
> **Đặc trưng:** Tốc độ tấn công cao, khả năng xuyên giáp nội tại và phản đòn chí mạng dựa trên sát thương bản thân.

---

## 📋 Danh Sách Kỹ Năng

| ID | Tên Kỹ Năng | Loại Chiêu | Cấp Học | MP Tiêu Hao | Hồi Chiêu (CD) | Cơ Chế / Thuộc Tính Chính |
|:---:|:---|:---:|:---:|:---:|:---:|:---|
| **0** | **Chém** | Đơn thể | Lv 1 – 10 | 0 | 0.7s | Chém cơ bản, sát thương 80% – 135%. |
| **1** | **Kim tinh pháp** | Đơn thể | Lv 6 – 15 | 2 – 5 | 1.0s – 1.5s | Sát thương đơn mục tiêu 140% – 205%. |
| **2** | **Lôi điện pháp** | Đơn thể | Lv 11 – 20 | 4 – 6 | 1.5s – 2.5s | Sát thương đơn mục tiêu 160% – 265%. |
| **3** | **Kinh lôi bát thủ** | Đơn thể | Lv 17 – 42 | 2 – 6 | 1.9s – 2.5s | Sát thương đơn mục tiêu 200% – 300%. |
| **4** | **Hộ sát tiến** | Bị động (Passive) | **Lv 3 – 32** | 8 – 12 | Không có | **Tăng điểm Xuyên Giáp** trên đòn đánh. MP x2. |
| **5** | **Dĩ lực đáo công** | Buff chủ động | **Lv 3 – 32** | 100 – 550 | **90s** | **Phản đòn:** 10% (+5%/cấp) tỷ lệ phản lại 50% (+10%/cấp) dame bản thân. |
| **6** | **Thiên lôi điện trảm** | Lan (AoE 1) | Lv 25 – 41 | 10 – 55 | 60s | Dùng sấm sét tấn công nhiều mục tiêu. |
| **7** | **Sấm động dương gian** | Lan (AoE 2) | Lv 30 – 46 | 10 – 55 | 120s | Sét liên hoàn nhảy qua các mục tiêu gần nhau. |
| **8** | **Kiếm phi kinh thiên** | Lan (AoE 3) | Lv 45 – 61 | 10 – 80 | 300s | Kiếm khổng lồ từ trời giáng xuống diện rộng. |

---

## 🔍 Chi Tiết Cơ Chế Từng Chiêu Thức

### 1. Kỹ Năng Tấn Công Đơn Thể (Skill 0 – 3)

* **Skill 0 — Chém:**
  * Sát thương: `80%, 90%, 95%, 100%, 105%, 110%, 115%, 120%, 125%, 130%, 135%`
  * MP: 0 | Cooldown: 700ms | Phạm vi: 30
  * Cấp học: `1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10`

* **Skill 1 — Kim tinh pháp:**
  * Sát thương: `140%, 150%, 155%, 160%, 165%, 170%, 175%, 180%, 190%, 200%, 205%`
  * MP: `2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5` | Cooldown: `1000ms – 1500ms` | Phạm vi: 50
  * Cấp học: `6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 15`

* **Skill 2 — Lôi điện pháp:**
  * Sát thương: `160%, 210%, 215%, 220%, 225%, 230%, 235%, 240%, 250%, 260%, 265%`
  * MP: `4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 6` | Cooldown: `1500ms – 2500ms` | Phạm vi: 60
  * Cấp học: `11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 20`

* **Skill 3 — Kinh lôi bát thủ:**
  * Sát thương: `200%, 240%, 245%, 250%, 255%, 260%, 265%, 270%, 285%, 295%, 300%`
  * MP: `2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 6` | Cooldown: `1900ms – 2500ms` | Phạm vi: 70
  * Cấp học: `17, 20, 22, 24, 26, 30, 33, 36, 39, 42, 42`

---

### 2. Skill 4 — Hộ Sát Tiến (Nội Tại Xuyên Giáp)

* **Loại kỹ năng:** Kỹ năng bị động (Passive).
* **Cấp học yêu cầu (Base Lv 3):** `[3, 6, 9, 12, 18, 24, 26, 28, 30, 32, 32]`
* **Mana tiêu hao (Gấp đôi):** `[8, 8, 8, 10, 10, 10, 12, 12, 12, 12, 12]`
* **Thời gian hồi chiêu:** Không có (Bị động).
* **Tác dụng:** Tăng trực tiếp điểm Xuyên Giáp vào chỉ số nhân vật theo % cấu hình bảng sát thương kỹ năng:
  * Cấp 1: +10 xuyên giáp
  * Cấp 2: +15 xuyên giáp
  * Cấp 3: +20 xuyên giáp
  * Cấp 4: +25 xuyên giáp
  * Cấp 5: +30 xuyên giáp
  * Cấp 6: +35 xuyên giáp
  * Cấp 7: +45 xuyên giáp
  * Cấp 8: +60 xuyên giáp
  * Cấp 9: +70 xuyên giáp
  * Cấp 10: +80 xuyên giáp

---

### 3. Skill 5 — Dĩ Lực Đáo Công (Buff Phản Đòn)

* **Loại kỹ năng:** Buff kích hoạt chủ động.
* **Cấp học yêu cầu (Base Lv 3):** `[3, 6, 9, 12, 18, 24, 26, 28, 30, 32, 32]`
* **Mana tiêu hao (Gấp 10 lần):** `[100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 550]`
* **Thời gian hồi chiêu:** **90s** (`90,000ms`) cố định mọi cấp 1 – 10.
* **Thời gian hiệu lực buff:** `[50s, 60s, 70s, 80s, 90s, 100s, 120s, 140s, 160s, 170s, 180s]`
* **Cơ chế phản đòn mới (Cả PvE lẫn PvP):**
  * **Tỷ lệ kích hoạt phản đòn:** $10\% + (\text{LvSkill} - 1) \times 5\%$
    * Cấp 1: 10% cơ hội
    * Cấp 2: 15% cơ hội
    * Cấp 5: 30% cơ hội
    * Cấp 10: **55%** cơ hội phản đòn
  * **Lượng sát thương phản trả:** Bằng $50\% + (\text{LvSkill} - 1) \times 10\%$ sát thương tấn công của chính bản thân Kiếm Khách:
    * Cấp 1: Phản bằng **50%** dame bản thân.
    * Cấp 2: Phản bằng **60%** dame bản thân.
    * Cấp 5: Phản bằng **90%** dame bản thân.
    * Cấp 10: Phản bằng **140%** toàn bộ sát thương bản thân lên kẻ tấn công.

---

### 4. Kỹ Năng Đánh Lan Diện Rộng (AoE Skill 6 – 8)

* **Skill 6 — Thiên lôi điện trảm (AoE 1):**
  * Cấp học: Base Lv 25 (`[25, 25, 27, 29, 31, 33, 35, 37, 39, 41, 41]`)
  * MP tiêu hao: `10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 55`
  * Hồi chiêu: 60s
  * Tác dụng: Gọi sấm sét từ bầu trời giáng xuống nhiều mục tiêu trong bán kính nhất định.

* **Skill 7 — Sấm động dương gian (AoE 2):**
  * Cấp học: Base Lv 30 (`[30, 30, 32, 34, 36, 38, 40, 42, 44, 46, 46]`)
  * MP tiêu hao: `10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 55`
  * Hồi chiêu: 120s
  * Tác dụng: Dẫn truyền điện qua vũ khí tạo chuỗi tia sét liên hoàn nhảy qua các kẻ thù gần nhau.

* **Skill 8 — Kiếm phi kinh thiên (AoE 3):**
  * Cấp học: Base Lv 45 (`[45, 45, 47, 49, 51, 53, 55, 57, 59, 61, 61]`)
  * MP tiêu hao: `10, 40, 45, 50, 55, 60, 65, 70, 75, 80, 80`
  * Hồi chiêu: 300s
  * Tác dụng: Kình lực ngưng tụ thành đại kiếm khổng lồ từ không trung giáng thẳng xuống trận địa.
