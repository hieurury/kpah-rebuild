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
        byte playerGender = player.getInfo().getGender(); // 1 = Nam, 2 = Nữ

        for (ItemEquipTemplate it : Manager.ITEM_EQUIPMENTS.values()) {
            if (it == null || it.getColorItem() != 0 || it.getNdayLoan() != 0) continue;
            // Chỉ lấy trang bị chế tạo (Level 20 trở lên)
            if (it.getLevel() < 20 || it.getLevel() > 85) continue;

            byte type = it.getType();
            boolean match = false;

            switch (equipType) {
                case 0 -> { // Vũ khí
                    // Type 3: Kiếm (Kiếm khách 0)
                    // Type 4: Đao (Chiến binh 1)
                    // Type 5: Bút (Pháp sư 2)
                    // Type 6: Búa (Đấu sĩ 3)
                    // Type 7: Cung (Cung thủ 4)
                    byte expectedWpType = (byte) (3 + classChar);
                    if (type == expectedWpType) {
                        match = true;
                    }
                }
                case 1 -> { // Áo (Type 0)
                    if (type == 0 && (it.getGender() == 0 || it.getGender() == playerGender)) {
                        match = true;
                    }
                }
                case 2 -> { // Quần (Type 1)
                    if (type == 1 && (it.getGender() == 0 || it.getGender() == playerGender)) {
                        match = true;
                    }
                }
                case 3 -> { // Nón (Type 2)
                    if (type == 2 && (it.getGender() == 0 || it.getGender() == playerGender)) {
                        match = true;
                    }
                }
                case 4 -> { // Giày (Type 10)
                    if (type == 10) {
                        match = true;
                    }
                }
                case 5 -> { // Găng tay (Type 11)
                    if (type == 11) {
                        match = true;
                    }
                }
                case 6 -> { // Nhẫn (Type 8)
                    if (type == 8) {
                        match = true;
                    }
                }
                case 7 -> { // Dây chuyền (Type 9)
                    if (type == 9) {
                        match = true;
                    }
                }
                case 8 -> { // Ngọc (Type 12)
                    if (type == 12) {
                        match = true;
                    }
                }
            }

            if (match) {
                list.add(it);
            }
        }

        // Sắp xếp tăng dần theo Level
        Collections.sort(list, Comparator.comparingInt(ItemEquipTemplate::getLevel));
        return list;
    }

    /**
     * Xác định tầng nguyên liệu (Tier 1..6) dựa theo level trang bị.
     */
    public static int getMaterialTier(int level) {
        if (level < 30) return 1;
        if (level < 40) return 2;
        if (level < 50) return 3;
        if (level < 60) return 4;
        if (level < 70) return 5;
        return 6;
    }

    /**
     * Lấy ID nguyên liệu 1 theo loại trang bị và tier (1..6).
     */
    public static short getMaterial1Id(byte type, int tier) {
        int offset = tier - 1;
        return switch (type) {
            case 0, 1 -> (short) (68 + offset);      // Vải (68..73)
            case 2, 10, 11 -> (short) (96 + offset); // Da mềm (96..101)
            case 3, 4, 5, 6, 7 -> (short) (75 + offset); // Sắt (75..80)
            case 8, 9 -> (short) (110 + offset);     // Bạc (110..115)
            case 12 -> (short) (82 + offset);        // Ngọc (82..87)
            default -> (short) (75 + offset);
        };
    }

    /**
     * Lấy ID nguyên liệu 2 theo loại trang bị và tier (1..6).
     */
    public static short getMaterial2Id(byte type, int tier) {
        int offset = tier - 1;
        return switch (type) {
            case 0 -> (short) (103 + offset);        // Tơ lụa (103..108)
            case 1 -> (short) (96 + offset);         // Da mềm (96..101)
            case 2 -> (short) (75 + offset);         // Sắt (75..80)
            case 10, 11 -> (short) (131 + offset);   // Da cứng (131..136)
            case 3, 4, 5, 6, 7 -> (short) (89 + offset); // Gỗ thường (89..94)
            case 8 -> (short) (82 + offset);         // Ngọc (82..87)
            case 9, 12 -> (short) (117 + offset);    // Thủy tinh (117..122)
            default -> (short) (89 + offset);
        };
    }

    /**
     * Lấy ID Đá Ngũ Hợp theo phẩm cấp và tier (1..6).
     * Rank: 1=Nhất phẩm, 2=Nhị phẩm, 3=Tam phẩm, 4=Tứ phẩm, 5=Ngũ phẩm.
     */
    public static short getDaNguHopId(byte rank, int tier) {
        int offset = tier - 1;
        if (rank == 1) {
            return (short) (149 + offset); // Đá ngũ hợp tinh khiết (149..154)
        } else if (rank == 2 || rank == 3) {
            return (short) (143 + offset); // Đá ngũ hợp cao cấp (143..148)
        } else {
            return (short) (137 + offset); // Đá ngũ hợp thường (137..142)
        }
    }

    /**
     * Lấy số lượng nguyên liệu cần thiết: Hệ số K = 6 - rank.
     */
    public static int getMaterialQuantity(byte rank) {
        int k = 6 - rank;
        return 5 * k;
    }

    /**
     * Lấy số lượng Đá Ngũ Hợp cần thiết: 1 * K.
     */
    public static int getDaQuantity(byte rank) {
        int k = 6 - rank;
        return 1 * k;
    }

    /**
     * Phí xu chế tạo: Level * 1000 * K.
     */
    public static int getCraftXuFee(int level, byte rank) {
        int k = 6 - rank;
        return level * 1000 * k;
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

        int tier = getMaterialTier(template.getLevel());
        short mat1Id = getMaterial1Id(template.getType(), tier);
        short mat2Id = getMaterial2Id(template.getType(), tier);
        short daId = getDaNguHopId(rank, tier);

        short ngocRenId = 268;
        int ngocRenNeed = 6 - rank;
        int mat1Need = getMaterialQuantity(rank);
        int mat2Need = getMaterialQuantity(rank);
        int daNeed = getDaQuantity(rank);
        int xuFee = getCraftXuFee(template.getLevel(), rank);

        // Kiểm tra nguyên liệu
        int mat1Have = countGem(player, mat1Id);
        int mat2Have = countGem(player, mat2Id);
        int daHave = countGem(player, daId);
        int ngocRenHave = countGem(player, ngocRenId);

        if (mat1Have < mat1Need || mat2Have < mat2Need || daHave < daNeed || ngocRenHave < ngocRenNeed) {
            Service.instance.sendLogOut(player.getSession(), "Bạn không đủ nguyên liệu để chế tạo!");
            return;
        }

        // Kiểm tra xu
        if (!player.getInventory().minusXu(xuFee)) {
            Service.instance.sendLogOut(player.getSession(), String.format("Bạn không đủ %s xu để chế tạo!", Util.formatNumber(xuFee)));
            return;
        }

        // Trừ nguyên liệu
        deductGem(player, mat1Id, mat1Need);
        deductGem(player, mat2Id, mat2Need);
        deductGem(player, daId, daNeed);
        deductGem(player, ngocRenId, ngocRenNeed);

        // Tạo trang bị theo phẩm cấp
        double baseMultiplier;
        byte colorName;
        int bonusAttrCount;

        if (rank == 1) { // Nhất phẩm
            baseMultiplier = 1.80;
            colorName = ItemEquipConst.YELLOW_COLOR;
            bonusAttrCount = 5;
        } else if (rank == 2) { // Nhị phẩm
            baseMultiplier = 1.55;
            colorName = ItemEquipConst.PURPLE_COLOR;
            bonusAttrCount = 4;
        } else if (rank == 3) { // Tam phẩm
            baseMultiplier = 1.35;
            colorName = ItemEquipConst.BLUE_COLOR;
            bonusAttrCount = 3;
        } else if (rank == 4) { // Tứ phẩm
            baseMultiplier = 1.20;
            colorName = ItemEquipConst.BLUE_COLOR;
            bonusAttrCount = 2;
        } else { // Ngũ phẩm
            baseMultiplier = 1.10;
            colorName = ItemEquipConst.BLUE_COLOR;
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

        // Nạp thuộc tính cơ bản
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

        // Thuộc tính phụ ngẫu nhiên
        byte[] pool = {33, 34, 10, 11, 12, 13, 4, 2, 3, 31, 28, 29, 111, 26, 81};
        List<Byte> available = new ArrayList<>();
        for (byte b : pool) available.add(b);

        int rankPower = 6 - rank;
        for (int i = 0; i < bonusAttrCount && !available.isEmpty(); i++) {
            int idx = Util.nextInt(0, available.size() - 1);
            byte attId = available.remove(idx);

            short value;
            switch (attId) {
                case 33, 34 -> value = (short) (150 * rankPower + Util.nextInt(50, 150) + template.getLevel() * 10);
                case 10, 11, 12, 13 -> value = (short) (4 * rankPower + Util.nextInt(1, 5) + template.getLevel() / 5);
                case 4, 2, 3 -> value = (short) (2 * rankPower + Util.nextInt(1, 4));
                case 31, 28, 29 -> value = (short) (1 + rankPower + Util.nextInt(1, 3));
                case 111 -> value = (short) (2 + rankPower * 2 + Util.nextInt(1, 3));
                case 26, 81 -> value = (short) (1 + (rankPower >= 3 ? 1 : 0) + (rankPower == 5 ? 1 : 0));
                default -> value = (short) (5 * rankPower);
            }
            if (Manager.getAttributeTemplate(attId) != null) {
                item.getItemAttributes().add(new Attribute(Manager.getAttributeTemplate(attId), value));
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
                player.getName(), player.getIdPlayer(), template.getName(), rankName, template.getLevel(), Util.formatNumber(xuFee));

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
     * Mở menu danh sách trang bị để phân rã (tối đa 5 trang bị / trang).
     */
    public void openDismantleMenu(@NonNull Player player, int page) throws IOException {
        List<ItemEquip> bagItems = player.getInventory().getItemBag();
        List<ItemEquip> equipList = new ArrayList<>();
        if (bagItems != null) {
            for (ItemEquip item : bagItems) {
                if (item != null && item.getTemplate() != null) {
                    equipList.add(item);
                }
            }
        }
        if (equipList.isEmpty()) {
            Service.instance.sendLogOut(player.getSession(), "Hành trang không có trang bị để phân rã!");
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
                if (item != null && item.getTemplate() != null) {
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
