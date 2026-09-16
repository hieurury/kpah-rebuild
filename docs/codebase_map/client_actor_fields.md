# 🔬 GIẢI MÃ BIẾN THỰC THỂ CLIENT (ACTOR / ENTITY FIELDS MAP)

Tài liệu này giải mã toàn bộ các biến viết tắt (obfuscated fields) trong lớp cơ sở [`class_hw`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_hw.java) và các lớp con kế thừa [`class_bi`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/class_bi.java) (Player), [`class_bb`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_bb.java) (Monster).

---

## 📍 1. Vị Trí, Định Danh & Trạng Thái Sống

| Tên Biến Viết Tắt | Kiểu Dữ Liệu | Tên Thực Tế | Mô tả & Chức năng |
|:---|:---:|:---|:---|
| **`cG`** | `short` | **`actorId`** | ID định danh duy nhất của thực thể trên bản đồ (ID Player hoặc ID Mob). |
| **`cK`** | `int` | **`x`** | Tọa độ X trên bản đồ thế giới (pixels). |
| **`cL`** | `int` | **`y`** | Tọa độ Y trên bản đồ thế giới (pixels). |
| **`da`** | `byte` | **`direction`** | Hướng quay mặt của nhân vật: `0`: Lên, `1`: Xuống, `2`: Trái, `3`: Phải. |
| **`db`** | `byte` | **`speed`** | Tốc độ di chuyển cơ bản của thực thể. |
| **`cV`** | `byte` | **`state` / `isDie`** | Trạng thái sống chết: `0`: Đang sống bình thường, `1`: Đang ngã gục / Đã chết. |
| **`cu`** | `String` | **`name`** | Tên của nhân vật hoặc tên loài quái vật. |
| **`ct`** | `byte` | **`charClass`** | Lớp nhân vật (`0`: Kiếm Khách, `1`: Chiến Binh, `2`: Pháp Sư, `3`: Đấu Sĩ, `4`: Cung Thủ). |
| **`cf`** | `short` | **`level`** | Cấp độ hiện tại của nhân vật hoặc quái vật. |

---

## 🩸 2. Chỉ Số Sinh Lực & Năng Lượng (HP & MP)

| Tên Biến Viết Tắt | Kiểu Dữ Liệu | Tên Thực Tế | Mô tả & Chức năng |
|:---|:---:|:---|:---|
| **`cx`** | `int` | **`hp`** | Lượng Máu (HP) hiện tại của thực thể. |
| **`cy`** | `int` | **`maxHp`** | Lượng Máu tối đa (Max HP) của thực thể. |
| **`cz`** | `int` | **`mp`** | Lượng Mana (MP) hiện tại của nhân vật. |
| **`cA`** | `int` | **`maxMp`** | Lượng Mana tối đa (Max MP) của nhân vật. |

---

## 💫 3. Khống Chế & Trạng Thái Bất Động (Stun & CC)

| Tên Biến Viết Tắt | Kiểu Dữ Liệu | Tên Thực Tế | Mô tả & Chức năng |
|:---|:---:|:---|:---|
| **`cW`** | `boolean` | **`isStunned`** | Cờ báo trạng thái **Bị Choáng / Bất Động**. Khi `cW == true`, Actor không thể di chuyển, không thể tấn công. |
| **`cZ`** | `long` | **`timeEndStun`** | Mốc thời gian (System timestamp tính bằng ms) kết thúc hiệu ứng Choáng. |
| **`dg`, `dh`, `df`** | `int` | **`dotDamageData`** | Các thông số tính toán sát thương trúng độc / trừ máu theo thời gian (Damage over Time). |

---

## 🛡️ 4. Danh Sách Buff & Hiệu Ứng Trạng Thái (Buffs & Effects)

| Tên Biến Viết Tắt | Kiểu Dữ Liệu | Tên Thực Tế | Mô tả & Chức năng |
|:---|:---:|:---|:---|
| **`de`** | `Vector<class_zx>` | **`listEffects`** | Danh sách các đối tượng hiệu ứng hình ảnh [`class_zx`](file:///home/hieurury/Programs/mod_game/kpah/kpah_mod_chill/game/app/src/classes/class_zx.java) đang bám trên nhân vật (vòng xoay, hào quang, khói độc, icon hóa đá). |
| **`bC`** | `byte[]` | **`buffIds`** | Mảng ID các loại Buff/Debuff đang có hiệu lực trên nhân vật. |
| **`bD`** | `byte[]` | **`buffLevels`** | Mảng Cấp độ (Level) tương ứng của từng buff trong `bC`. |

---

## 💡 Ví Dụ Tra Cứu Nhanh

1. **Kiểm tra một quái vật có đang bị khống chế hay không:**
   ```java
   class_bb mob = ...;
   if (mob.cW || System.currentTimeMillis() < mob.cZ) {
       // Quái đang bị Choáng hoặc Bất Động!
   }
   ```

2. **Lấy tọa độ và khoảng cách tới mục tiêu:**
   ```java
   int targetX = target.cK;
   int targetY = target.cL;
   int distance = Math.abs(mainChar.cK - targetX) + Math.abs(mainChar.cL - targetY);
   ```

3. **Thêm hiệu ứng hiển thị mới cho Actor:**
   ```java
   class_zx eff = new class_zx();
   eff.effectType = 7; // BUFF_HOA_DA (Hóa Đá - vòng tròn xám tro)
   actor.de.addElement(eff);
   ```
