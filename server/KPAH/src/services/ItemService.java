package services;

import item.Attribute;
import item.ItemEquip;
import item.ItemFriend;
import item.ItemGem;
import item.ItemMap;
import item.ItemPotion;
import item.ItemQuest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import lombok.NonNull;
import manager.Manager;
import map.Zone;
import player.Player;
import network.Message;
import template.GemTemplate;
import template.AttributeEquipTemplate;
import template.ItemEquipTemplate;
import template.PotionTemplate;
import template.ShopTemplate;
import utils.CommandMessage;
import consts.Const;
import consts.ItemEquipConst;
import item.ItemAnimal;
import utils.Util;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
public class ItemService {

    public static final ItemService instance = new ItemService();

    public ItemEquip createNewItemEquipment(short id, byte classChar, byte... typeDamages) {
        ItemEquipTemplate template = Manager.getItemEquipment(id);
        if (template.getClassChar() != -1) {
            classChar = template.getClassChar();
        }
        byte typeDamage = (byte) (typeDamages.length <= 0 ? ItemEquipConst.DAMAGE_NONE : typeDamages[0]);
        ItemEquip item = ItemEquip.builder().he(ItemEquipConst.NONE_HE).template(template).colorName(ItemEquipConst.NONE_COLOR).level(template.getLevel()).plusTemplate((byte) 0).classChar(classChar).isLock(false).damageType(typeDamage).durable(template.getDurable()).mDurable(template.getDurable()).viTriVe((byte) 0).rank(ItemEquipConst.NONE_RANK).nameCharSeal("").itemAttributes(new ArrayList<>()).dayUse(0).timeCreateItem(System.currentTimeMillis()).build();
        if (item.getTemplate().getAttribute()[0] > 0) {
            item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate((short) 0), item.getTemplate().getAttribute()[0]));
        }
        for (short i = 1; i < 7; i++) {
            short value = (short) ((item.getTemplate().getAttribute()[i] + (!item.isAnimalArmor() ? item.getTemplate().getAttribute()[i] * Manager.PERCENT_ATTRIBUTE[classChar] / 100 : 0)));
            if (value > 0 && Manager.ATTRIBUTE_FOR_TYPE[item.getTemplate().getType()][i]) {
                item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate(i), value));
            }
        }
        item.subDefend(typeDamage);
        return item;
    }

    public ItemEquip createNewItemEquipment(@NonNull ItemMap itemMap) {
        ItemEquip item = this.createNewItemEquipment(itemMap.getItemTemplateID(), (byte) Util.nextInt(Const.KIEM_KHACH, Const.CUNG_THU));
        return item;
    }

    public ItemEquip createNewItemEquipment(@NonNull ItemEquip itemEquip) {
        ItemEquip item = ItemEquip.builder().template(itemEquip.getTemplate()).plusTemplate(itemEquip.getPlusTemplate()).classChar(itemEquip.getClassChar()).isLock(itemEquip.isLock()).durable(itemEquip.getDurable()).mDurable(itemEquip.getMDurable()).level(itemEquip.getLevel()).colorName(itemEquip.getColorName()).viTriVe(itemEquip.getViTriVe()).he(itemEquip.getHe()).rank(itemEquip.getRank()).damageType(itemEquip.getDamageType()).nameCharSeal(itemEquip.getNameCharSeal()).dayUse(itemEquip.getDayUse()).timeCreateItem(itemEquip.getTimeCreateItem()).itemAttributes(new ArrayList<>()).build();
        for (Attribute att : itemEquip.getItemAttributes()) {
            Attribute newAtt = Attribute.builder().template(att.getTemplate()).value(att.getValue()).build();
            item.getItemAttributes().add(newAtt);
        }
        return item;
    }

    public ItemEquip createCraftedEquipment(byte tier, int playerLv, byte playerClass) {
        // 1. Phân loại nghề nghiệp (ưu tiên 40% class người chơi, 60% random 4 class khác)
        byte classChar;
        if (Util.isTrue(40, 100) && playerClass >= Const.KIEM_KHACH && playerClass <= Const.CUNG_THU) {
            classChar = playerClass;
        } else {
            classChar = (byte) Util.nextInt(Const.KIEM_KHACH, Const.CUNG_THU);
        }

        // 2. Phân loại cấp độ theo tier rương (Bậc 3: lv20-29; Bậc 4: lv30-39)
        int minLv = (tier <= 3) ? 20 : 30;
        int maxLv = (tier <= 3) ? 29 : 39;

        // 3. Lựa chọn loại trang bị: 35% vũ khí, 45% phòng thủ, 20% trang sức
        int randType = Util.nextInt(1, 100);
        java.util.List<ItemEquipTemplate> candidates = new ArrayList<>();
        for (ItemEquipTemplate it : Manager.ITEM_EQUIPMENTS.values()) {
            if (it == null || it.getColorItem() != 0 || it.getNdayLoan() != 0) continue;
            if (it.getLevel() < minLv || it.getLevel() > maxLv) continue;

            if (randType <= 35) {
                // Vũ khí phù hợp classChar
                if (it.getType() >= 3 && it.getType() <= 7 && (it.getClassChar() == classChar || it.getClassChar() == -1)) {
                    candidates.add(it);
                }
            } else if (randType <= 80) {
                // Phòng thủ: Áo (0), Quần (1), Nón (2), Giày (10), Găng (11)
                if (it.getType() == 0 || it.getType() == 1 || it.getType() == 2 || it.getType() == 10 || it.getType() == 11) {
                    candidates.add(it);
                }
            } else {
                // Trang sức: Nhẫn (8), Dây chuyền (9), Ngọc (12)
                if (it.getType() == 8 || it.getType() == 9 || it.getType() == 12) {
                    candidates.add(it);
                }
            }
        }
        if (candidates.isEmpty()) {
            // Fallback: tìm bất kỳ trang bị nào thỏa level
            for (ItemEquipTemplate it : Manager.ITEM_EQUIPMENTS.values()) {
                if (it != null && it.getColorItem() == 0 && it.getNdayLoan() == 0 && it.getLevel() >= minLv && it.getLevel() <= maxLv) {
                    candidates.add(it);
                }
            }
        }
        if (candidates.isEmpty()) return null;

        ItemEquipTemplate template = candidates.get(Util.nextInt(0, candidates.size() - 1));

        // 4. Phẩm cấp: Ngũ phẩm (45%) -> Tứ phẩm (28%) -> Tam phẩm (16%) -> Nhị phẩm (8%) -> Nhất phẩm (3%)
        int randRank = Util.nextInt(1, 100);
        byte rank;
        byte colorName;
        int bonusAttrCount;
        double baseMultiplier;

        if (randRank <= 3) {
            rank = ItemEquipConst.NHAT_PHAM; // 1: Nhất phẩm (cực phẩm)
            colorName = ItemEquipConst.YELLOW_COLOR; // Màu vàng hoàn mỹ
            bonusAttrCount = 5;
            baseMultiplier = 1.80; // +80% chỉ số cơ bản
        } else if (randRank <= 11) {
            rank = ItemEquipConst.NHI_PHAM; // 2: Nhị phẩm
            colorName = ItemEquipConst.PURPLE_COLOR; // Màu tím
            bonusAttrCount = 4;
            baseMultiplier = 1.55; // +55%
        } else if (randRank <= 27) {
            rank = ItemEquipConst.TAM_PHAM; // 3: Tam phẩm
            colorName = ItemEquipConst.BLUE_COLOR; // Màu xanh dương
            bonusAttrCount = 3;
            baseMultiplier = 1.35; // +35%
        } else if (randRank <= 55) {
            rank = ItemEquipConst.TU_PHAM; // 4: Tứ phẩm
            colorName = ItemEquipConst.BLUE_COLOR;
            bonusAttrCount = 2;
            baseMultiplier = 1.20; // +20%
        } else {
            rank = ItemEquipConst.NGU_PHAM; // 5: Ngũ phẩm (phổ biến nhất)
            colorName = ItemEquipConst.BLUE_COLOR;
            bonusAttrCount = 1;
            baseMultiplier = 1.10; // +10%
        }

        // 5. Ngũ hành (Hệ): 0=Thủy, 1=Mộc, 2=Hỏa, 3=Thổ, 4=Kim
        byte he = (byte) Util.nextInt(ItemEquipConst.THUY, ItemEquipConst.KIM);

        // 6. Tạo ItemEquip
        short durable = (short) (template.getDurable() * 1.5);
        ItemEquip item = ItemEquip.builder()
                .idItem(template.getId())
                .template(template)
                .classChar(template.getClassChar() != -1 ? template.getClassChar() : classChar)
                .level(template.getLevel())
                .plusTemplate((byte) 0)
                .colorName(colorName)
                .isLock(false)
                .durable(durable)
                .mDurable(durable)
                .viTriVe((byte) 0)
                .rank(rank)
                .he(he)
                .damageType(ItemEquipConst.DAMAGE_NONE)
                .nameCharSeal("Tinh Anh")
                .dayUse(0)
                .timeCreateItem(System.currentTimeMillis())
                .itemAttributes(new ArrayList<>())
                .build();

        // 7. Thuộc tính cơ bản (tăng theo phẩm cấp)
        if (template.getAttribute()[0] > 0) {
            short baseAtk = (short) Math.round(template.getAttribute()[0] * baseMultiplier);
            item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate((short) 0), baseAtk));
        }
        for (short i = 1; i < 7; i++) {
            short val = template.getAttribute()[i];
            if (val > 0 && Manager.ATTRIBUTE_FOR_TYPE[template.getType()][i]) {
                short baseDef = (short) Math.round(val * baseMultiplier);
                item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate(i), baseDef));
            }
        }

        // 8. Thuộc tính phụ ngẫu nhiên (Bonus Attributes)
        byte[] pool = {33, 34, 10, 11, 12, 13, 4, 2, 3, 31, 28, 29, 111, 26, 81};
        java.util.List<Byte> available = new ArrayList<>();
        for (byte b : pool) available.add(b);

        int rankPower = 6 - rank; // 1..5 (Nhất phẩm = 5, Ngũ phẩm = 1)
        for (int i = 0; i < bonusAttrCount && !available.isEmpty(); i++) {
            int idx = Util.nextInt(0, available.size() - 1);
            byte attId = available.remove(idx);

            short value;
            switch (attId) {
                case 33, 34 -> { // HP / MP
                    value = (short) (150 * rankPower + Util.nextInt(50, 150) + template.getLevel() * 10);
                }
                case 10, 11, 12, 13 -> { // STR, AGI, INT, VIT
                    value = (short) (4 * rankPower + Util.nextInt(1, 5) + template.getLevel() / 5);
                }
                case 4, 2, 3 -> { // Crit, Dodge, Acc
                    value = (short) (2 * rankPower + Util.nextInt(1, 4));
                }
                case 31, 28, 29 -> { // Xuyên giáp, Giảm ST vật/ma (%)
                    value = (short) (1 + rankPower + Util.nextInt(1, 3));
                }
                case 111 -> { // Tăng EXP (%)
                    value = (short) (2 + rankPower * 2 + Util.nextInt(1, 3));
                }
                case 26, 81 -> { // X2 ST, Hấp thu (%)
                    value = (short) (1 + (rankPower >= 3 ? 1 : 0) + (rankPower == 5 ? 1 : 0));
                }
                default -> value = (short) (5 * rankPower);
            }
            if (Manager.getAttributeTemplate(attId) != null) {
                item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate(attId), value));
            }
        }
        return item;
    }

    public ItemAnimal createNewItemAnimal(short id) {
        ItemAnimal item = ItemAnimal.builder().template(Manager.getAnimalTemplate(id)).attributes(new ArrayList<>()).minutes(-1).level((byte) 1).timeStart(0).build();
        return item;
    }

    public ItemPotion createNewItemPotion(short id, int quantity) {
        PotionTemplate tpl = Manager.getPotionTemplate(id);
        if (tpl == null) {
            // Template không tồn tại -> không tạo item tránh NPE ở downstream
            return null;
        }
        ItemPotion item = ItemPotion.builder().template(tpl).quantity(quantity).build();
        return item;
    }

    public ItemGem createNewItemGem(short id, short quantity) {
        ItemGem item = ItemGem.builder().template(Manager.getGemTemplate(id)).quantity(quantity).build();
        return item;
    }

    public ItemPotion createNewItemPotion(@NonNull ItemMap itemMap) {
        ItemPotion item = createNewItemPotion((short) itemMap.getItemTemplateID(), itemMap.getQuantity());
        return item;
    }

    public ItemQuest createNewItemQuest(byte id, short quantity) {
        ItemQuest item = ItemQuest.builder().template(Manager.getItemQuestTemplate(id)).quantity(quantity).build();
        return item;
    }

    public ItemMap createNewItemMap(short id, short quantity, byte catagory, short x, short y, short playerDrop, Zone zone) {
        ItemMap item = new ItemMap(catagory, id, (short) -1, quantity, x, y, zone, System.currentTimeMillis(), playerDrop);
        return item;
    }

    public ItemFriend createNewItemFriend(ItemEquip item) {
        ItemFriend itemF = ItemFriend.builder().classChar(item.getClassChar()).idTemplate(item.getTemplate().getId()).level(item.getLevel()).plusTemplate(item.getPlusTemplate()).build();
        return itemF;
    }

    public void onRemoveItemMap(@NonNull ItemMap itemMap) throws IOException {
        Message msg = new Message(CommandMessage.REMOVE_ACTOR);
        msg.writer().writeByte(itemMap.getItemCatagory());
        msg.writer().writeShort(itemMap.getItemMapId());
        MapService.instance.sendAllPlayerInMap(itemMap.getZone(), msg);
    }

    public void sendItemInMap(@NonNull Player player, @NonNull ItemMap itemMap) throws IOException {
        Message msg = new Message(CommandMessage.MOVE_CHAR);
        msg.writer().writeByte(itemMap.getItemCatagory());
        msg.writer().writeByte(itemMap.getItemTemplateID());
        msg.writer().writeShort(itemMap.getItemMapId());
        msg.writer().writeShort(itemMap.getX());
        msg.writer().writeShort(itemMap.getY());
        msg.writer().writeByte(-1);
        msg.writer().writeInt(-1);
        msg.writer().writeByte(-1);
        msg.writer().writeBoolean(true);
        player.getSession().sendMessage(msg);
    }

    public void removeItemGemFromGround(@NonNull Player playerPick, @NonNull ItemMap itemMap) throws IOException {
        Message msg = new Message(CommandMessage.GET_GEM_FROM_GROUND);
        msg.writer().writeShort(playerPick.getIdPlayer());
        msg.writer().writeByte(itemMap.getItemCatagory());
        msg.writer().writeShort(itemMap.getItemMapId());
        playerPick.getLocation().getZone().removeItem(itemMap);
        MapService.instance.sendAllPlayerInMap(playerPick, msg);
    }

    public void removeItemPotionFromGround(@NonNull Player playerPick, @NonNull ItemMap itemMap) throws IOException {
        Message msg = new Message(CommandMessage.GET_POTION_FROM_GROUND);
        msg.writer().writeShort(playerPick.getIdPlayer());
        msg.writer().writeShort(itemMap.getItemMapId());
        msg.writer().writeByte(itemMap.getItemTemplateID());
        msg.writer().writeShort(itemMap.getQuantity());
        playerPick.getLocation().getZone().removeItem(itemMap);
        MapService.instance.sendAllPlayerInMap(playerPick, msg);
    }

    public void removeItemEquipmentFromGround(@NonNull Player playerPick, @NonNull ItemMap itemMap, @NonNull ItemEquip equipmentPick) throws IOException {
        Message msg = new Message(CommandMessage.GET_ITEM_FROM_GROUND);
        msg.writer().writeShort(playerPick.getIdPlayer());
        msg.writer().writeByte(equipmentPick.getClassChar());
        msg.writer().writeShort(itemMap.getItemMapId());
        msg.writer().writeShort(equipmentPick.getIdItem());
        msg.writer().writeShort(equipmentPick.getTemplate().getId());
        msg.writer().writeByte(equipmentPick.getPlusTemplate());
        msg.writer().writeByte(equipmentPick.getTemplate().getLevel());
        msg.writer().writeShort(equipmentPick.getDurable());
        msg.writer().writeShort(equipmentPick.getTemplate().getDurable());
        playerPick.getLocation().getZone().removeItem(itemMap);
        MapService.instance.sendAllPlayerInMap(playerPick, msg);
    }

    public void sendItemInfo(@NonNull Player player, short index, short idTemp) throws IOException {
        ItemEquip item = idTemp == player.getIdPlayer() ? InventoryService.instance.findItemBag(player, index) : InventoryService.instance.findItemBody(player, index, idTemp);
        if (item == null) {
            return;
        }
        Message msg = new Message(CommandMessage.ITEM_INFO);
        msg.writer().writeShort(item.getIdItem());
        msg.writer().writeShort(item.getDurable());
        msg.writer().writeByte(item.getClassChar());
        msg.writer().writeShort(item.getDayUse());
        msg.writer().writeByte(item.getItemAttributes().size());
        for (int i = 0; i < item.getItemAttributes().size(); i++) {
            Attribute att = item.getItemAttributes().get(i);
            msg.writer().writeByte(att.getTemplate().getId());
            msg.writer().writeShort(att.getValue());
        }
        msg.writer().writeByte(item.getPlusTemplate());
        msg.writer().writeByte(item.isLock() ? 1 : 0);
        player.getSession().sendMessage(msg);
    }

    public void sendItemTemplate(@NonNull Player pl) throws IOException {
        Message msg = new Message(CommandMessage.ITEM_TEMPLATE);
        msg.writer().writeByte(Manager.ITEM_ATTRIBUTE_TEMPLATES.size());
        Enumeration<Short> keysByte = Manager.ITEM_ATTRIBUTE_TEMPLATES.keys();
        while (keysByte.hasMoreElements()) {
            short key = keysByte.nextElement();
            AttributeEquipTemplate attributeTemplate = Manager.getAttributeTemplate(key);
            msg.writer().writeByte(attributeTemplate.getId());
            msg.writer().writeUTF(attributeTemplate.getName());
            msg.writer().writeByte(attributeTemplate.getIsPercent());
            msg.writer().writeByte(attributeTemplate.getColorPaint());
        }
        msg.writer().writeByte(20);
        for (byte i = 0; i < 20; i++) {
            PotionTemplate potionTemplate = Manager.getPotionTemplate(i);
            msg.writer().writeShort(potionTemplate.getPrice());
            msg.writer().writeShort(potionTemplate.getRecovered());
        }
        for (int i = 0; i < 5; i++) {
            msg.writer().writeByte(Manager.PERCENT_ATTRIBUTE[i]);
        }
        msg.writer().writeShort(Manager.ITEM_EQUIPMENTS.size());
        Enumeration<Short> keys = Manager.ITEM_EQUIPMENTS.keys();
        while (keys.hasMoreElements()) {
            short key = keys.nextElement();
            ItemEquipTemplate itemTemplate = Manager.getItemEquipment(key);
            msg.writer().writeShort(itemTemplate.getId());
            msg.writer().writeUTF(itemTemplate.getName());
            msg.writer().writeByte(itemTemplate.getType());
            msg.writer().writeByte(itemTemplate.getStyle());
            msg.writer().writeByte(itemTemplate.getHe());
            msg.writer().writeByte(itemTemplate.getGender());
            msg.writer().writeByte(itemTemplate.getLevel());
            msg.writer().writeShort(itemTemplate.getDurable());
            for (int j = 0; j < 10; j++) {
                msg.writer().writeShort(itemTemplate.getAttribute()[j]);
            }
            msg.writer().writeInt(itemTemplate.getPrice());
            msg.writer().writeByte(itemTemplate.getClassChar());
            msg.writer().writeByte(itemTemplate.getColorItem());
            msg.writer().writeShort(itemTemplate.getIdIcon());
            msg.writer().writeShort(itemTemplate.getNdayLoan());
        }

        msg.writer().writeShort(Manager.GEM_TEMPLATES.size());
        keys = Manager.GEM_TEMPLATES.keys();
        while (keys.hasMoreElements()) {
            short key = keys.nextElement();
            GemTemplate gemTemplate = Manager.getGemTemplate(key);
            msg.writer().writeShort(gemTemplate.getId());
            msg.writer().writeByte(gemTemplate.getIdImage());
            msg.writer().writeInt(gemTemplate.getPrice());
            msg.writer().writeUTF(gemTemplate.getName());
            msg.writer().writeUTF(gemTemplate.getDecript());
            msg.writer().writeByte(gemTemplate.getType());
            msg.writer().writeBoolean(gemTemplate.isSell());
            msg.writer().writeByte(gemTemplate.getTypeEp());
            msg.writer().writeByte(gemTemplate.getTypeMoney());
        }

        msg.writer().writeByte(Manager.SHOP_TEMPLATES.size());
        keys = Manager.SHOP_TEMPLATES.keys();
        while (keys.hasMoreElements()) {
            short key = keys.nextElement();
            ShopTemplate shopTemplate = Manager.getShopTemplate(key);
            msg.writer().writeByte(shopTemplate.getId());
            msg.writer().writeByte(shopTemplate.getIdImage());
            msg.writer().writeInt(shopTemplate.getPrice());
            msg.writer().writeUTF(shopTemplate.getName());
            msg.writer().writeUTF(shopTemplate.getDecript());
            msg.writer().writeByte(shopTemplate.getTypeMoney());
            msg.writer().writeByte(shopTemplate.getShopType());
            msg.writer().writeBoolean(shopTemplate.isSell());
            msg.writer().writeShort(shopTemplate.getValue());
        }
        pl.getSession().sendMessage(msg);
    }
}
