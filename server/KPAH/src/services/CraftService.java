package services;

import consts.Const;
import consts.ItemEquipConst;
import item.Attribute;
import item.ItemEquip;
import item.ItemGem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.NonNull;
import manager.Manager;
import network.Message;
import player.Player;
import template.ItemEquipTemplate;
import utils.CommandMessage;
import utils.ServerLog;
import utils.Util;

/**
 * Service quản lý Hệ Thống Chế Tạo Trang Bị tại Thợ Rèn Thần Bí.
 */
public class CraftService {

    public static final CraftService instance = new CraftService();

    /**
     * Mở gian hàng chế tạo cho người chơi theo classChar và equipType.
     * equipType:
     * 0: Vũ khí
     * 1: Áo
     * 2: Quần
     * 3: Nón
     * 4: Giày
     * 5: Găng tay
     * 6: Nhẫn
     * 7: Dây chuyền
     * 8: Ngọc
     */
    public void openCraftShop(@NonNull Player player, byte classChar, byte equipType) throws IOException {
        List<ItemEquipTemplate> items = getCraftableItems(player, classChar, equipType);
        if (items.isEmpty()) {
            Service.instance.sendLogOut(player.getSession(), "Không tìm thấy trang bị phù hợp!");
            return;
        }

        Message msg = new Message(CommandMessage.CRAFT_SHOP);
        msg.writer().writeByte(classChar);
        msg.writer().writeByte(equipType);
        msg.writer().writeShort(items.size());
        for (ItemEquipTemplate item : items) {
            msg.writer().writeShort(item.getId());
            msg.writer().writeUTF(item.getName());
            msg.writer().writeByte(item.getLevel());
            msg.writer().writeByte(item.getType());
            msg.writer().writeShort(item.getIdIcon());
            short[] attrs = item.getAttribute();
            msg.writer().writeByte(attrs.length);
            for (short a : attrs) {
                msg.writer().writeShort(a);
            }
        }
        player.getSession().sendMessage(msg);
    }

    /**
     * Lấy danh sách ItemEquipTemplate tương ứng theo classChar và equipType.
     */
    public List<ItemEquipTemplate> getCraftableItems(@NonNull Player player, byte classChar, byte equipType) {
        List<ItemEquipTemplate> list = new ArrayList<>();

        for (ItemEquipTemplate it : Manager.ITEM_EQUIPMENTS.values()) {
            if (it == null || it.getColorItem() != 0 || it.getNdayLoan() != 0) continue;

            byte type = it.getType();
            byte lvl = it.getLevel();
            boolean match = false;

            if (equipType == 0) { // Vũ khí: Lv 21, 26, 31, 36
                if (lvl == 21 || lvl == 26 || lvl == 31 || lvl == 36) {
                    byte expectedWpType = (byte) (3 + classChar);
                    if (type == expectedWpType) {
                        match = true;
                    }
                }
            } else { // Trang bị: Lv 20, 25, 30, 35
                if (lvl == 20 || lvl == 25 || lvl == 30 || lvl == 35) {
                    switch (equipType) {
                        case 1 -> { if (type == 0) match = true; }  // Áo (cả Nam và Nữ)
                        case 2 -> { if (type == 1) match = true; }  // Quần (cả Nam và Nữ)
                        case 3 -> { if (type == 2) match = true; }  // Nón (cả Nam và Nữ)
                        case 4 -> { if (type == 10) match = true; } // Giày
                        case 5 -> { if (type == 11) match = true; } // Găng tay
                        case 6 -> { if (type == 8) match = true; }  // Nhẫn
                        case 7 -> { if (type == 9) match = true; }  // Dây chuyền
                        case 8 -> { if (type == 12) match = true; } // Ngọc
                    }
                }
            }

            if (match) {
                list.add(it);
            }
        }

        // Sắp xếp tăng dần theo Level rồi đến ID
        Collections.sort(list, Comparator.comparingInt(ItemEquipTemplate::getLevel).thenComparingInt(ItemEquipTemplate::getId));
        return list;
    }

    /**
     * Cấu trúc công thức chế tạo trang bị theo 10 loại nguyên liệu KPAH.
     */
    public static class CraftRecipe {
        public short mat1Id;       // Sơ cấp chính
        public short mat2Id;       // Sơ cấp phụ
        public short caoCap1Id;    // Cao cấp chính
        public short caoCap2Id;    // Cao cấp phụ
        public int mat1Need;
        public int mat2Need;
        public int caoCap1Need;
        public int caoCap2Need;
        public int ngocRenNeed;
        public int xuFee;
    }

    /**
     * Xác định công thức chế tạo theo loại trang bị, level và phẩm cấp.
     */
    public static CraftRecipe getRecipe(byte type, int level, byte rank) {
        CraftRecipe r = new CraftRecipe();

        // 1. Phân loại nguyên liệu cấu tạo (5 cặp: Sắt/Bạc, Vải/Tơ, Gỗ/Gỗ sưa, Da mềm/Da cứng, Ngọc/Thủy tinh)
        switch (type) {
            case 3, 4, 6 -> { // Vũ khí cận chiến (Kiếm, Đao, Búa): Sắt + Gỗ thường (Cao cấp: Bạc + Gỗ sưa)
                r.mat1Id = 75;     // Sắt
                r.mat2Id = 89;     // Gỗ thường
                r.caoCap1Id = 110; // Bạc
                r.caoCap2Id = 124; // Gỗ sưa
            }
            case 5, 7 -> { // Vũ khí tầm xa / phép (Bút, Cung): Gỗ thường + Sắt (Cao cấp: Gỗ sưa + Bạc)
                r.mat1Id = 89;     // Gỗ thường
                r.mat2Id = 75;     // Sắt
                r.caoCap1Id = 124; // Gỗ sưa
                r.caoCap2Id = 110; // Bạc
            }
            case 0, 1 -> { // Áo, Quần: Vải + Da mềm (Cao cấp: Tơ lụa + Da cứng)
                r.mat1Id = 68;     // Vải
                r.mat2Id = 96;     // Da mềm
                r.caoCap1Id = 103; // Tơ lụa
                r.caoCap2Id = 131; // Da cứng
            }
            case 2, 10, 11 -> { // Nón, Giày, Găng tay: Da mềm + Vải (Cao cấp: Da cứng + Tơ lụa)
                r.mat1Id = 96;     // Da mềm
                r.mat2Id = 68;     // Vải
                r.caoCap1Id = 131; // Da cứng
                r.caoCap2Id = 103; // Tơ lụa
            }
            case 8, 9 -> { // Nhẫn, Dây chuyền: Ngọc + Sắt (Cao cấp: Thủy tinh + Bạc)
                r.mat1Id = 82;     // Ngọc
                r.mat2Id = 75;     // Sắt
                r.caoCap1Id = 117; // Thủy tinh
                r.caoCap2Id = 110; // Bạc
            }
            case 12 -> { // Bội ngọc: Ngọc + Gỗ thường (Cao cấp: Thủy tinh + Gỗ sưa)
                r.mat1Id = 82;     // Ngọc
                r.mat2Id = 89;     // Gỗ thường
                r.caoCap1Id = 117; // Thủy tinh
                r.caoCap2Id = 124; // Gỗ sưa
            }
            default -> {
                r.mat1Id = 75;
                r.mat2Id = 89;
                r.caoCap1Id = 110;
                r.caoCap2Id = 124;
            }
        }

        // 2. Định mức tiêu hao theo level & rank (1=Nhất, 2=Nhị, 3=Tam, 4=Tứ, 5=Ngũ)
        if (level <= 24) { // Lv 20, 21
            switch (rank) {
                case 5 -> { r.mat1Need = 15; r.mat2Need = 10; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 1; r.xuFee = 15000; }
                case 4 -> { r.mat1Need = 22; r.mat2Need = 15; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 2; r.xuFee = 25000; }
                case 3 -> { r.mat1Need = 30; r.mat2Need = 20; r.caoCap1Need = 4; r.caoCap2Need = 2; r.ngocRenNeed = 3; r.xuFee = 40000; }
                case 2 -> { r.mat1Need = 45; r.mat2Need = 30; r.caoCap1Need = 8; r.caoCap2Need = 4; r.ngocRenNeed = 5; r.xuFee = 65000; }
                default -> { r.mat1Need = 60; r.mat2Need = 40; r.caoCap1Need = 15; r.caoCap2Need = 8; r.ngocRenNeed = 8; r.xuFee = 100000; }
            }
        } else if (level <= 29) { // Lv 25, 26
            switch (rank) {
                case 5 -> { r.mat1Need = 20; r.mat2Need = 15; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 2; r.xuFee = 25000; }
                case 4 -> { r.mat1Need = 30; r.mat2Need = 20; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 3; r.xuFee = 40000; }
                case 3 -> { r.mat1Need = 45; r.mat2Need = 30; r.caoCap1Need = 6; r.caoCap2Need = 3; r.ngocRenNeed = 4; r.xuFee = 60000; }
                case 2 -> { r.mat1Need = 60; r.mat2Need = 40; r.caoCap1Need = 12; r.caoCap2Need = 6; r.ngocRenNeed = 6; r.xuFee = 90000; }
                default -> { r.mat1Need = 80; r.mat2Need = 55; r.caoCap1Need = 22; r.caoCap2Need = 12; r.ngocRenNeed = 10; r.xuFee = 140000; }
            }
        } else if (level <= 34) { // Lv 30, 31
            switch (rank) {
                case 5 -> { r.mat1Need = 25; r.mat2Need = 20; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 2; r.xuFee = 35000; }
                case 4 -> { r.mat1Need = 40; r.mat2Need = 25; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 4; r.xuFee = 55000; }
                case 3 -> { r.mat1Need = 60; r.mat2Need = 40; r.caoCap1Need = 9; r.caoCap2Need = 5; r.ngocRenNeed = 6; r.xuFee = 85000; }
                case 2 -> { r.mat1Need = 80; r.mat2Need = 55; r.caoCap1Need = 18; r.caoCap2Need = 10; r.ngocRenNeed = 8; r.xuFee = 130000; }
                default -> { r.mat1Need = 110; r.mat2Need = 75; r.caoCap1Need = 35; r.caoCap2Need = 18; r.ngocRenNeed = 12; r.xuFee = 200000; }
            }
        } else { // Lv 35, 36+
            switch (rank) {
                case 5 -> { r.mat1Need = 35; r.mat2Need = 25; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 3; r.xuFee = 50000; }
                case 4 -> { r.mat1Need = 50; r.mat2Need = 35; r.caoCap1Need = 0; r.caoCap2Need = 0; r.ngocRenNeed = 5; r.xuFee = 80000; }
                case 3 -> { r.mat1Need = 75; r.mat2Need = 50; r.caoCap1Need = 12; r.caoCap2Need = 6; r.ngocRenNeed = 7; r.xuFee = 120000; }
                case 2 -> { r.mat1Need = 100; r.mat2Need = 70; r.caoCap1Need = 25; r.caoCap2Need = 12; r.ngocRenNeed = 10; r.xuFee = 180000; }
                default -> { r.mat1Need = 140; r.mat2Need = 95; r.caoCap1Need = 48; r.caoCap2Need = 25; r.ngocRenNeed = 15; r.xuFee = 280000; }
            }
        }

        return r;
    }

    /**
     * Đếm tổng số lượng một loại gem trong hành trang (cả khóa và không khóa).
     */
    public static int countGem(@NonNull Player player, short templateId) {
        int count = 0;
        for (ItemGem g : player.getInventory().getItemGem()) {
            if (g != null && g.getTemplate() != null && g.getTemplate().getId() == templateId) {
                count += g.getQuantity();
            }
        }
        for (ItemGem g : player.getInventory().getItemGemLock()) {
            if (g != null && g.getTemplate() != null && g.getTemplate().getId() == templateId) {
                count += g.getQuantity();
            }
        }
        return count;
    }

    /**
     * Khấu trừ nguyên liệu (ưu tiên trừ đồ khóa trước, sau đó trừ đồ thường).
     */
    public static void deductGem(@NonNull Player player, short templateId, int quantity) {
        if (quantity <= 0) return;
        int remain = quantity;

        // 1. Trừ trong đồ khóa
        for (ItemGem g : new ArrayList<>(player.getInventory().getItemGemLock())) {
            if (remain <= 0) break;
            if (g != null && g.getTemplate() != null && g.getTemplate().getId() == templateId) {
                int available = g.getQuantity();
                int deduct = Math.min(available, remain);
                InventoryService.instance.minusQuantityItemGemLock(player, g, (short) deduct);
                remain -= deduct;
            }
        }

        // 2. Trừ trong đồ không khóa
        for (ItemGem g : new ArrayList<>(player.getInventory().getItemGem())) {
            if (remain <= 0) break;
            if (g != null && g.getTemplate() != null && g.getTemplate().getId() == templateId) {
                int available = g.getQuantity();
                int deduct = Math.min(available, remain);
                InventoryService.instance.minusQuantityItemGem(player, g, (short) deduct);
                remain -= deduct;
            }
        }
    }

    /**
     * Thực hiện chế tạo trang bị.
     */
    public void craftEquipment(@NonNull Player player, short idItem, byte rank) throws IOException {
        ItemEquipTemplate template = Manager.getItemEquipment(idItem);
        if (template == null) {
            Service.instance.sendLogOut(player.getSession(), "Không tìm thấy thông tin trang bị!");
            return;
        }
        if (rank < 1 || rank > 5) {
            Service.instance.sendLogOut(player.getSession(), "Phẩm cấp không hợp lệ!");
            return;
        }
        if (player.getInventory().isFullInventory()) {
            Service.instance.sendLogOut(player.getSession(), "Hành trang đã đầy, vui lòng dọn dẹp!");
            return;
        }

        CraftRecipe recipe = getRecipe(template.getType(), template.getLevel(), rank);
        short ngocRenId = 268;

        // 1. Kiểm tra nguyên liệu
        int mat1Have = countGem(player, recipe.mat1Id);
        int mat2Have = countGem(player, recipe.mat2Id);
        int cc1Have = recipe.caoCap1Need > 0 ? countGem(player, recipe.caoCap1Id) : 0;
        int cc2Have = recipe.caoCap2Need > 0 ? countGem(player, recipe.caoCap2Id) : 0;
        int ngocRenHave = countGem(player, ngocRenId);

        if (mat1Have < recipe.mat1Need || mat2Have < recipe.mat2Need) {
            Service.instance.sendLogOut(player.getSession(), "Bạn không đủ nguyên liệu sơ cấp để chế tạo!");
            return;
        }
        if (recipe.caoCap1Need > 0 && (cc1Have < recipe.caoCap1Need || cc2Have < recipe.caoCap2Need)) {
            Service.instance.sendLogOut(player.getSession(), "Bạn không đủ nguyên liệu cao cấp để chế tạo!");
            return;
        }
        if (ngocRenHave < recipe.ngocRenNeed) {
            Service.instance.sendLogOut(player.getSession(), String.format("Bạn cần %d Ngọc rèn để chế tạo!", recipe.ngocRenNeed));
            return;
        }

        // 2. Kiểm tra xu
        if (!player.getInventory().minusXu(recipe.xuFee)) {
            Service.instance.sendLogOut(player.getSession(), String.format("Bạn không đủ %s xu để chế tạo!", Util.formatNumber(recipe.xuFee)));
            return;
        }

        // 3. Khấu trừ nguyên liệu
        deductGem(player, recipe.mat1Id, recipe.mat1Need);
        deductGem(player, recipe.mat2Id, recipe.mat2Need);
        if (recipe.caoCap1Need > 0) deductGem(player, recipe.caoCap1Id, recipe.caoCap1Need);
        if (recipe.caoCap2Need > 0) deductGem(player, recipe.caoCap2Id, recipe.caoCap2Need);
        deductGem(player, ngocRenId, recipe.ngocRenNeed);

        // 4. Tạo trang bị theo phẩm cấp
        double baseMultiplier;
        byte colorName;
        int bonusAttrCount;

        if (rank == 1) { // Nhất phẩm (Tím)
            baseMultiplier = 1.80;
            colorName = ItemEquipConst.PURPLE_COLOR;
            bonusAttrCount = 5; // 5 dòng nhóm A + 2 dòng nhóm B
        } else if (rank == 2) { // Nhị phẩm (Vàng)
            baseMultiplier = 1.55;
            colorName = ItemEquipConst.YELLOW_COLOR;
            bonusAttrCount = 4;
        } else if (rank == 3) { // Tam phẩm (Xanh dương)
            baseMultiplier = 1.35;
            colorName = ItemEquipConst.BLUE_COLOR;
            bonusAttrCount = 3;
        } else if (rank == 4) { // Tứ phẩm (Xanh lá)
            baseMultiplier = 1.20;
            colorName = ItemEquipConst.GREEN_COLOR;
            bonusAttrCount = 2;
        } else { // Ngũ phẩm (Trắng)
            baseMultiplier = 1.10;
            colorName = ItemEquipConst.NONE_COLOR;
            bonusAttrCount = 1;
        }

        byte he = (byte) Util.nextInt(ItemEquipConst.THUY, ItemEquipConst.KIM);
        short durable = (short) (template.getDurable() * 1.5);

        ItemEquip item = ItemEquip.builder()
                .idItem(template.getId())
                .template(template)
                .classChar(template.getClassChar() != -1 ? template.getClassChar() : player.getInfo().getClassPlayer())
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
                .nameCharSeal(player.getName())
                .dayUse(0)
                .timeCreateItem(System.currentTimeMillis())
                .itemAttributes(new ArrayList<>())
                .build();

        // 5. Nạp thuộc tính cơ bản đã nhân tỷ lệ phẩm cấp
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

        // 6. Nhóm A: 14 Thuộc tính bổ sung chung (Ngũ phẩm -> Nhất phẩm)
        // 7: Tăng %HP (2-5%)
        // 8: Tăng %MP (2-5%)
        // 33: Tăng HP (1000-5000)
        // 34: Tăng MP (1000-5000)
        // 88: Tăng % thủ (2-5%)
        // 1: Tăng thủ vật (50-100)
        // 6: Tăng thủ ma (50-100)
        // 30: Tăng % công (2-5%)
        // 0: Tăng công (50-100)
        // 10: Tăng sức mạnh (5-10)
        // 12: Tăng tinh thần (5-10)
        // 11: Tăng khéo léo (5-10)
        // 13: Tăng sức khỏe (5-10)
        // 9: Tăng may mắn (5-10)
        byte[] poolA = {7, 8, 33, 34, 88, 1, 6, 30, 0, 10, 12, 11, 13, 9};
        List<Byte> availableA = new ArrayList<>();
        for (byte b : poolA) availableA.add(b);

        for (int i = 0; i < bonusAttrCount && !availableA.isEmpty(); i++) {
            int idx = Util.nextInt(0, availableA.size() - 1);
            byte attId = availableA.remove(idx);
            short val = switch (attId) {
                case 7, 8, 88, 30 -> (short) Util.nextInt(2, 5);      // % HP, % MP, % thủ, % công
                case 33, 34 -> (short) Util.nextInt(1000, 5000);       // Flat HP, Flat MP
                case 1, 6, 0 -> (short) Util.nextInt(50, 100);         // Thủ vật, Thủ ma, Công
                case 10, 12, 11, 13, 9 -> (short) Util.nextInt(5, 10); // Sức mạnh, Tinh thần, Khéo léo, Sức khỏe, May mắn
                default -> (short) Util.nextInt(5, 10);
            };
            if (Manager.getAttributeTemplate(attId) != null) {
                item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate(attId), val));
            }
        }

        // 7. Nhóm B: 2 Thuộc tính đặc biệt độc quyền cho Nhất phẩm (rank == 1)
        if (rank == 1) {
            // 118: Giảm sát thương (2-5%)
            // 30: Tăng sát thương (2-5%)
            // 2: Né tránh (2-5%)
            // 4: Chí mạng (5-10%)
            // 31: Xuyên giáp (2-5%)
            // 41: Tăng st chí mạng (10-20%)
            byte[] poolB = {118, 30, 2, 4, 31, 41};
            List<Byte> availableB = new ArrayList<>();
            for (byte b : poolB) availableB.add(b);

            for (int i = 0; i < 2 && !availableB.isEmpty(); i++) {
                int idx = Util.nextInt(0, availableB.size() - 1);
                byte attId = availableB.remove(idx);
                short val = switch (attId) {
                    case 118, 30, 2, 31 -> (short) Util.nextInt(2, 5); // Giảm ST, Tăng ST, Né tránh, Xuyên giáp
                    case 4 -> (short) Util.nextInt(5, 10);              // Chí mạng (5-10%)
                    case 41 -> (short) Util.nextInt(10, 20);            // Tăng ST chí mạng (10-20%)
                    default -> (short) Util.nextInt(2, 5);
                };
                if (Manager.getAttributeTemplate(attId) != null) {
                    item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate(attId), val));
                }
            }
        }

        // Thêm vào hành trang
        InventoryService.instance.addItemBagEquipment(player, item);
        InventoryService.instance.sendItemBag(player);
        InventoryService.instance.sendItemPotion(player);
        InventoryService.instance.sendItemGem(player);
        InventoryService.instance.sendItemGemLock(player);

        String rankName = getRankName(rank);
        ServerLog.shop("Nhân vật '%s' (ID: %d) chế tạo thành công [%s - %s] (Cấp %d) tiêu tốn %s xu.",
                player.getName(), player.getIdPlayer(), template.getName(), rankName, template.getLevel(), Util.formatNumber(recipe.xuFee));

        Service.instance.sendLogOut(player.getSession(), String.format("Chế tạo thành công %s (%s)!", template.getName(), rankName));
    }

    public static String getRankName(byte rank) {
        return switch (rank) {
            case 1 -> "Nhất phẩm";
            case 2 -> "Nhị phẩm";
            case 3 -> "Tam phẩm";
            case 4 -> "Tứ phẩm";
            case 5 -> "Ngũ phẩm";
            default -> "";
        };
    }

    /**
     * Kiểm tra xem một trang bị có phải là trang bị chế tạo hay không.
     * Cho phép phân rã tất cả trang bị chế tạo (từ thợ rèn, rương tinh anh, hoặc dải template chế tạo).
     */
    public static boolean isCraftedEquipment(ItemEquip item) {
        if (item == null || item.getTemplate() == null) return false;
        // Có phẩm cấp (1: Nhất phẩm -> 5: Ngũ phẩm)
        if (item.getRank() > 0) return true;
        // Có phong ấn tên thợ rèn hoặc "Sinh ra từ thiên địa"
        if (item.getNameCharSeal() != null && !item.getNameCharSeal().trim().isEmpty()) return true;
        // Thuộc dải template chế tạo
        short id = item.getTemplate().getId();
        if (id >= 268 && id <= 444) return true;
        return false;
    }

    /**
     * Mở menu danh sách trang bị để phân rã (tối đa 5 trang bị / trang).
     */
    public void openDismantleMenu(@NonNull Player player, int page) throws IOException {
        List<ItemEquip> bagItems = player.getInventory().getItemBag();
        List<ItemEquip> equipList = new ArrayList<>();
        if (bagItems != null) {
            for (ItemEquip item : bagItems) {
                if (item != null && item.getTemplate() != null && isCraftedEquipment(item)) {
                    equipList.add(item);
                }
            }
        }
        if (equipList.isEmpty()) {
            Service.instance.sendLogOut(player.getSession(), "Hành trang không có trang bị chế tạo để phân rã!");
            return;
        }

        int pageSize = 5;
        int totalPages = (equipList.size() + pageSize - 1) / pageSize;
        if (page >= totalPages) page = 0;
        player.getSundry().setDismantlePage(page);

        int start = page * pageSize;
        int end = Math.min(start + pageSize, equipList.size());
        List<String> options = new ArrayList<>();
        for (int i = start; i < end; i++) {
            ItemEquip item = equipList.get(i);
            int gems = calculateDismantleGem(item);
            String rankStr = getRankName(item.getRank());
            if (rankStr.isEmpty()) rankStr = "Ngũ phẩm";
            options.add(String.format("%s lv%d (%s) -> %d Ngọc rèn", item.getTemplate().getName(), item.getTemplate().getLevel(), rankStr, gems));
        }
        if (totalPages > 1) {
            options.add(String.format("Trang tiếp (%d/%d)", page + 1, totalPages));
        }

        MenuOptionService.instance.sendOptionMenu(player, MenuOptionService.THO_REN_DISMANTLE, options.toArray(new String[0]));
    }

    /**
     * Xử lý khi người chơi chọn một trang bị trong danh sách phân rã.
     */
    public void onSelectDismantleItem(@NonNull Player player, byte selected) throws IOException {
        List<ItemEquip> bagItems = player.getInventory().getItemBag();
        List<ItemEquip> equipList = new ArrayList<>();
        if (bagItems != null) {
            for (ItemEquip item : bagItems) {
                if (item != null && item.getTemplate() != null && isCraftedEquipment(item)) {
                    equipList.add(item);
                }
            }
        }
        int page = player.getSundry().getDismantlePage();
        int pageSize = 5;
        int totalPages = (equipList.size() + pageSize - 1) / pageSize;
        int start = page * pageSize;
        int end = Math.min(start + pageSize, equipList.size());
        int countInPage = end - start;

        if (totalPages > 1 && selected == countInPage) {
            openDismantleMenu(player, page + 1);
            return;
        }

        int targetIndex = start + selected;
        if (targetIndex < 0 || targetIndex >= equipList.size()) {
            return;
        }

        ItemEquip target = equipList.get(targetIndex);
        int gems = calculateDismantleGem(target);
        player.getSundry().setIdItemDismantle(target.getIdItem());

        PopupService.instance.sendPopupConfirmDismantle(player,
                String.format("Bạn có chắc muốn phân rã %s không? Bạn sẽ nhận lại %d Ngọc rèn.", target.getTemplate().getName(), gems));
    }

    /**
     * Xác nhận phân rã trang bị sau khi nhấn popup Có.
     */
    public void confirmDismantle(@NonNull Player player) throws IOException {
        short idItem = player.getSundry().getIdItemDismantle();
        player.getSundry().setIdItemDismantle((short) 0);
        if (idItem == 0) return;

        ItemEquip item = null;
        for (ItemEquip eq : player.getInventory().getItemBag()) {
            if (eq != null && eq.getIdItem() == idItem) {
                item = eq;
                break;
            }
        }
        if (item == null) {
            Service.instance.sendLogOut(player.getSession(), "Không tìm thấy trang bị cần phân rã!");
            return;
        }
        if (!isCraftedEquipment(item)) {
            Service.instance.sendLogOut(player.getSession(), "Chỉ có trang bị chế tạo mới có thể phân rã!");
            return;
        }

        int gems = calculateDismantleGem(item);
        String name = item.getTemplate().getName();

        // Xóa trang bị khỏi hành trang
        player.getInventory().getItemBag().remove(item);
        InventoryService.instance.sendItemBag(player);

        // Thêm Ngọc rèn (ID 268) vào kho Gem
        ItemGem ngocRen = ItemService.instance.createNewItemGem((short) 268, (short) gems);
        InventoryService.instance.addItemGem(player, ngocRen);
        InventoryService.instance.sendItemGem(player);

        String msg = String.format("Phân rã thành công %s! Nhận được %d Ngọc rèn.", name, gems);
        Service.instance.sendLogOut(player.getSession(), msg);
        ChatService.instance.sendChatOnlyMe(player, msg);
    }

    /**
     * Tính toán số lượng Ngọc rèn trả về (1..10) theo cấp độ và phẩm chất.
     */
    public static int calculateDismantleGem(ItemEquip item) {
        if (item == null || item.getTemplate() == null) return 1;
        int level = item.getTemplate().getLevel();
        int baseLevel = Math.max(1, level / 10);
        int rank = item.getRank();
        int rankBonus = 0;
        if (rank == 4) rankBonus = 1;
        else if (rank == 3) rankBonus = 2;
        else if (rank == 2) rankBonus = 3;
        else if (rank == 1) rankBonus = 4;

        int total = baseLevel + rankBonus;
        return Math.min(10, Math.max(1, total));
    }
}
