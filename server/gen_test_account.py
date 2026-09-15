import json
import subprocess

def create_item(id_item, template_id, class_char, level, plus=15, color=5, is_lock=False,
                durable=480, m_durable=480, vi_tri_ve=0, rank=1, damage_type=1,
                name_char_seal="", day_use=0, time_create=1726390000000, he=-1, attributes=None):
    if attributes is None:
        attributes = []
    # format: [idItem, templateId, classChar, level, plusTemplate, colorName, isLock, mDurable, durable, viTriVe, rank, damageType, nameCharSeal, dayUse, timeCreateItem, he, itemAttributes]
    return [
        id_item,
        template_id,
        class_char,
        level,
        plus,
        color,
        is_lock,
        m_durable,
        durable,
        vi_tri_ve,
        rank,
        damage_type,
        name_char_seal,
        day_use,
        time_create,
        he,
        attributes
    ]

# Common armor & jewelry items for Level 100 characters:
# Áo: 641 (Hoàng long bào nam, lv 85)
# Quần: 645 (Quần Hoàng long nam, lv 85)
# Nón: 649 (Hoàng long mão nam, lv 85)
# Giày: 662 (Giày Hoả phụng, lv 95)
# Găng: 666 (Găng Hoả phụng, lv 95)
# Nhẫn 1: 654 (Nhẫn Hoả phụng, lv 95, viTriVe 1)
# Nhẫn 2: 654 (Nhẫn Hoả phụng, lv 95, viTriVe 2)
# Dây chuyền: 658 (Dây chuyền Hoả phụng, lv 95)
# Ngọc bội: 670 (Ngọc Hoả phụng, lv 95)
# Phi phong: 597 (Phi phong Lăng Vân, lv 100)

def build_equipment_set(class_char, char_name, wp_template_id, he):
    items = []
    curr_id = -32768
    
    # 1. Vũ khí (Rank 1, +15, DamageType 2 = physic/magic depending on class)
    # Weapon attributes: [0: Atk, 3: Chính xác, 4: Chí mạng, 30: % Công, 31: Xuyên giáp, 40: Tăng chí mạng, 41: Tăng st chí mạng]
    wp_attrs = [[0, 680], [3, 30], [4, 25], [30, 20], [31, 25], [40, 25], [41, 40]]
    items.append(create_item(curr_id, wp_template_id, class_char, 81, plus=15, color=5, rank=1, damage_type=2, name_char_seal=char_name, he=he, attributes=wp_attrs))
    curr_id += 1
    
    # 2. Áo (641) - Thủ vật (1), Thủ ma (6), %HP (7), Flat HP (33), % Thủ (88)
    ao_attrs = [[1, 350], [6, 120], [7, 15], [33, 5000], [88, 15]]
    items.append(create_item(curr_id, 641, class_char, 85, plus=15, color=5, rank=1, damage_type=1, name_char_seal=char_name, he=he, attributes=ao_attrs))
    curr_id += 1

    # 3. Quần (645)
    quan_attrs = [[1, 320], [6, 100], [7, 15], [33, 5000], [88, 15]]
    items.append(create_item(curr_id, 645, class_char, 85, plus=15, color=5, rank=1, damage_type=1, name_char_seal=char_name, he=he, attributes=quan_attrs))
    curr_id += 1

    # 4. Nón (649)
    non_attrs = [[1, 300], [6, 90], [7, 15], [33, 5000], [88, 15]]
    items.append(create_item(curr_id, 649, class_char, 85, plus=15, color=5, rank=1, damage_type=1, name_char_seal=char_name, he=he, attributes=non_attrs))
    curr_id += 1

    # 5. Giày (662) - Né tránh (2), Thủ vật (1), Thủ ma (6), %HP (7)
    giay_attrs = [[1, 240], [6, 70], [2, 35], [7, 12], [33, 4000]]
    items.append(create_item(curr_id, 662, class_char, 95, plus=15, color=5, rank=1, damage_type=1, name_char_seal=char_name, he=he, attributes=giay_attrs))
    curr_id += 1

    # 6. Găng tay (666) - Chính xác (3), Thủ vật (1), Thủ ma (6), %HP (7)
    gang_attrs = [[1, 240], [6, 70], [3, 35], [7, 12], [33, 4000]]
    items.append(create_item(curr_id, 666, class_char, 95, plus=15, color=5, rank=1, damage_type=1, name_char_seal=char_name, he=he, attributes=gang_attrs))
    curr_id += 1

    # 7. Nhẫn 1 (654) - Slot 1 (viTriVe 1)
    ring1_attrs = [[0, 180], [4, 20], [30, 15], [40, 20], [33, 3000]]
    items.append(create_item(curr_id, 654, class_char, 95, plus=15, color=5, vi_tri_ve=1, rank=1, damage_type=2, name_char_seal=char_name, he=he, attributes=ring1_attrs))
    curr_id += 1

    # 8. Nhẫn 2 (654) - Slot 2 (viTriVe 2)
    ring2_attrs = [[0, 180], [4, 20], [30, 15], [40, 20], [33, 3000]]
    items.append(create_item(curr_id, 654, class_char, 95, plus=15, color=5, vi_tri_ve=2, rank=1, damage_type=2, name_char_seal=char_name, he=he, attributes=ring2_attrs))
    curr_id += 1

    # 9. Dây chuyền (658)
    dc_attrs = [[0, 200], [7, 15], [8, 15], [33, 4000], [34, 4000]]
    items.append(create_item(curr_id, 658, class_char, 95, plus=15, color=5, rank=1, damage_type=2, name_char_seal=char_name, he=he, attributes=dc_attrs))
    curr_id += 1

    # 10. Ngọc bội (670)
    ngoc_attrs = [[0, 200], [2, 25], [3, 25], [4, 25], [30, 15]]
    items.append(create_item(curr_id, 670, class_char, 95, plus=15, color=5, rank=1, damage_type=2, name_char_seal=char_name, he=he, attributes=ngoc_attrs))
    curr_id += 1

    # 11. Phi phong (597)
    pp_attrs = [[1, 400], [6, 400], [7, 20], [88, 20], [33, 6000]]
    items.append(create_item(curr_id, 597, class_char, 100, plus=15, color=5, rank=1, damage_type=2, name_char_seal=char_name, he=he, attributes=pp_attrs))
    
    return items

# Build 3 characters:
# Class 4: Cung Thủ, He = 1 (Moc), Wp = 607 (Thương Ưng cung)
# Class 1: Chiến Binh, He = 2 (Hoa), Wp = 601 (Minh Nguyệt đao)
# Class 3: Đấu Sĩ, He = 3 (Tho), Wp = 605 (Lôi Động búa)

chars_config = [
    {
        "id": 1,
        "idPlayer": -30999,
        "name": "CungThuMax",
        "class": 4,
        "he": 1,
        "wp_id": 607,
        "base_points": [20, 30, 15, 15, 10] # str, agi, spi, hea, luck
    },
    {
        "id": 2,
        "idPlayer": -30998,
        "name": "ChienBinhMax",
        "class": 1,
        "he": 2,
        "wp_id": 601,
        "base_points": [30, 20, 10, 20, 10]
    },
    {
        "id": 3,
        "idPlayer": -30997,
        "name": "DauSiMax",
        "class": 3,
        "he": 3,
        "wp_id": 605,
        "base_points": [20, 30, 10, 20, 10]
    }
]

sql_statements = []

# Xóa user và player cũ nếu có
sql_statements.append("DELETE FROM `users` WHERE `username` = 'admin';")
sql_statements.append("DELETE FROM `players` WHERE `id` IN (1, 2, 3) OR `name` IN ('CungThuMax', 'ChienBinhMax', 'DauSiMax');")

# Chèn user admin
user_chars = json.dumps([c["id"] for c in chars_config])
sql_statements.append(f"""INSERT INTO `users` (`id`, `username`, `password`, `email`, `chars`, `isAdmin`, `active`, `luong`, `xu`)
VALUES (1, 'admin', '123456', 'admin@kpah.vn', '{user_chars}', 1, 1, 900000, 9000000000)
ON DUPLICATE KEY UPDATE `password`='123456', `chars`='{user_chars}', `luong`=900000, `xu`=9000000000, `isAdmin`=1, `active`=1;""")

# 9 skills max cấp 9
skills_json = json.dumps([[9, 9, 9, 9, 9, 9, 9, 9, 9, -1, -1, -1, -1, -1, -1]])
inventory_json = json.dumps([900000, 0, 9000000000, 5, 1000])
location_json = json.dumps([0, 184, 280, 0])
potions_json = json.dumps([[1, 999], [4, 999]])

for c in chars_config:
    info_json = json.dumps([c["class"], 1, 1, 0, c["he"], 100, -1, 0, {}])
    pts = c["base_points"]
    # [-1, -1, str, agi, spi, hea, luck, basePoint, skillPoint, dedication, baoKich, exp]
    point_json = json.dumps([-1, -1, pts[0], pts[1], pts[2], pts[3], pts[4], 500, 0, 0, 0, 5000000000])
    
    equip_items = build_equipment_set(c["class"], c["name"], c["wp_id"], c["he"])
    item_body_json = json.dumps(equip_items)
    
    # Escape quotes
    info_esc = info_json.replace("'", "\\'")
    item_body_esc = item_body_json.replace("'", "\\'")
    
    sql = f"""INSERT INTO `players` (`id`, `idPlayer`, `name`, `location`, `info`, `point`, `inventory`, `skills`, `friends`, `horse`, `itemBody`, `itemBag`, `itemBox`, `itemPotion`, `itemQuest`, `itemGem`, `itemGemLock`, `itemSold`, `itemAnimal`, `itemAnimalExpiry`, `lastTimeLogout`, `lastTimeEndDelete`)
VALUES ({c["id"]}, {c["idPlayer"]}, '{c["name"]}', '{location_json}', '{info_esc}', '{point_json}', '{inventory_json}', '{skills_json}', '[]', '[]', '{item_body_esc}', '[]', '[]', '{potions_json}', '[]', '[]', '[]', '[]', '[]', '[]', 0, 0);"""
    sql_statements.append(sql)

with open("/tmp/setup_test_accounts.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(sql_statements))

print("SQL setup generated successfully to /tmp/setup_test_accounts.sql")
