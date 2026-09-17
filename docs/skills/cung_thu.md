# 🏹 BỘ KỸ NĂNG & THIẾT KẾ TOÀN DIỆN LỚP CUNG THỦ (ARCHER) — KPAH

> **Lớp:** Cung Thủ (Class ID: `4`)  
> **Định vị:** Xạ thủ tầm xa, Bậc thầy Độc Dược & Sát thương bạo kích  
> **Thuộc tính:** Tấn công tầm xa, Hệ Mộc / Phong  
> **Vũ khí:** Cung tên / Nỏ  
> **Triết lý thiết kế:** Tầm bắn xa nhất trong tất cả các môn phái (90 – 128 pixel). Độ chính xác và né tránh vượt trội nhờ chỉ số Thân pháp (Agility). Sát thương độc dược rút máu theo thời gian kết hợp hiệu ứng độc tức thì khuyếch đại sát thương. Điểm yếu là lượng máu cơ bản và khả năng chống chịu thấp.

---

## 📊 Chỉ Số Cơ Bản (Base Stats) Sau Khi Cải Tổ (Glass Cannon)

| Chỉ số | Giá trị khởi tạo | Cơ chế cộng hưởng / Scale chỉ số | Đặc điểm môn phái |
|:---|:---:|:---|:---|
| **Sức mạnh (Strength / Str)** | `20` | Tăng nhẹ lực công cơ bản | Đóng vai trò phụ |
| **Thân pháp (Agility / Agi)** | **`35`** (Cao nhất game) | **Lực công:** $\text{Attack} += \text{Agi} \times 2.2$<br>**Chính xác:** $\text{Accurate} += \text{Agi}$<br>**Né tránh:** $\text{Dodge} += \text{Agi} \times 0.5$<br>**Thủ vật & Thủ phép:** $\text{Def} += \text{Agi}$ | Chỉ số then chốt quyết định lực sát thương tay cực cao, chính xác và né đòn |
| **Tinh thần (Spirit / Spi)** | `10` (Thấp) | $\text{MP}_{\max} += \text{Spirit} \times 16$ | Bể mana thấp, đòi hỏi tính toán cẩn thận |
| **Sức khỏe (Health / Hea)** | `10` (Thấp nhất game) | $\text{HP}_{\max} += \text{Health} \times 50$ | Lượng HP tối đa rất thấp (chuẩn Glass Cannon) |
| **May mắn (Luck)** | **`15`** (Rất cao) | $\text{Critical} += \text{Luck} / 15 + 5\%$ | Tỷ lệ chí mạng cơ bản rất cao |
| **Tầm bắn (Attack Range)** | **90 – 128 px** | Xa nhất toàn bộ 5 môn phái | Lợi thế thả diều (kiting) và bắn tỉa |

---

## 📋 Bảng Tổng Quan 9 Kỹ Năng Cung Thủ Sau Khi Cải Tổ

| ID | Tên Chiêu | Loại Chiêu | Cấp Học | Hồi Chiêu | Mana Tiêu Hao | Cơ Chế & Hiệu Ứng Mới |
|:---:|:---|:---:|:---:|:---:|:---:|:---|
| **0** | **Bắn** | Đơn thể | Lv 1 – 10 | 0.8s | 0 MP | Bắn tên cơ bản tầm xa (90px) |
| **1** | **Nhất hồn tiễn** | Đơn thể | Lv 6 – 15 | 1.5s | 15 – 45 MP | Phát bắn dồn kình lực tầm xa (100px) |
| **2** | **Phi thiên tiễn** | Đơn thể | Lv 11 – 20 | 1.8s | 20 – 60 MP | Tên bay vút tầm xa (110px) |
| **3** | **Bát kim tiễn đáo** | Đơn thể (Multi-hit) | Lv 17 – 42 | 3.0s – 4.0s | 30 – 90 MP | Bắn liên hoàn (cấp 1-3 bắn 3 đòn, cấp 4+ bắn số đòn = cấp, max 9 đòn). **Cơ chế Độc Nổ (Poison Detonate):** lập tức rút cạn toàn bộ sát thương độc DoT còn lại trên mục tiêu và xóa bỏ hiệu ứng độc |
| **4** | **Độc lưu tiễn** | Buff chủ động | Lv 3 – 32 | 80s cố định (duy trì 60s) | 50 – 135 MP | Buff chủ động duy trì 60s, cooldown 80s (khoảng trống 20s). Khi bật, **toàn bộ sát thương (đơn thể & AoE trúng lan)** đều gán độc DoT 10s: mỗi giây gây sát thương = **30% (+5%/cấp) Lực tấn công ($\pm 10\%$)** của Cung Thủ |
| **5** | **Hộ độc tiễn** | Bị động (Nội tại) | Lv 3 – 32 | Không có | 0 MP | Tăng **5% (+2%/cấp) Tỉ lệ chí mạng**; Mục tiêu nhiễm độc nhận thêm **50% (+10%/cấp) Sát thương chí mạng** |
| **6** | **Thập diện tâm tiễn** | AoE (Đánh lan 1) | Lv 25 – 41 | 6.0s | 40 – 115 MP | Bắn mưa tên tỏa 10 phương. Tỉ lệ **10% (+2%/cấp) gây MÙ 3s** (kẻ địch bị mù đánh hụt 100%) |
| **7** | **Thăng thiên loạn tiễn** | AoE (Đánh lan 2) | Lv 30 – 46 | 7.0s | 55 – 150 MP | Bão tên liên hoàn diện rộng. Gán **VẾT THƯƠNG SÂU 5s** (giảm 50% hồi HP của kẻ địch) và tăng bản thân **10% (+2%/cấp) NÉ TRÁNH trong 5s** |
| **8** | **Vạn tiễn quy tâm** | AoE (Đánh lan 3) | Lv 45 – 61 | 8.0s | 70 – 185 MP | Mưa tên phủ kín trận địa. **HÚT MÁU:** Hồi HP cho bản thân bằng **10% (+2%/cấp) tổng sát thương** gây ra lên kẻ địch |

---

## 🛠️ Trạng Thái Triển Khai & Kiểm Tra
- ✅ **Mã nguồn Server (`KPAH.jar`):** Đã biên dịch thành công 100%.
- ✅ **Mã nguồn Client (`kpah_mod_v1.0.0.1_local.jar`):** Đã biên dịch thành công 100%.
- ✅ **Kịch bản SQL:** `server/update_skills_cung_thu.sql` đã sẵn sàng.

