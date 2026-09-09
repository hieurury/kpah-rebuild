package services;

import consts.Const;
import consts.HorseConst;
import item.ItemAnimal;
import item.ItemEquip;
import item.ItemPotion;
import item.ItemGem;
import template.ItemEquipTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import manager.Manager;
import network.Message;
import player.Player;
import utils.CommandMessage;
import utils.Printer;
import utils.Util;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
public class UseItemService {

    public static final UseItemService instance = new UseItemService();

    public void useItemPotion(@NonNull Player player, short id) throws IOException {
        if (player.isDie()) {
            return;
        }
        ItemPotion potion = InventoryService.instance.findItemPotion(player, id);
        if (potion == null || potion.getQuantity() <= 0) {
            return;
        }
        if (!Util.canDoWithTime(player.getInventory().getLastTimeUsePotion()[id], potion.getTemplate().getDelay())
                || (player.getPoint().isFullHp() && potion.isHpAverage())
                || (player.getPoint().isFullMp() && potion.isMpAverage())
                || (id == 19 && player.getLocation().getZone().getMap().isMapVillage())) {
            return;
        }
        player.getInventory().getLastTimeUsePotion()[id] = System.currentTimeMillis();
        if (potion.isHpAverage()) {
            short valueAdd = (short) Manager.getMpHpPlus(0, id);
            player.getPoint().plusHp(valueAdd);
            onUsePotionHp(player, potion, valueAdd);
            InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
        } else if (potion.isMpAverage()) {
            short valueAdd = (short) Manager.getMpHpPlus(1, id);
            player.getPoint().plusMp(valueAdd);
            onUsePotionMp(player, potion, valueAdd);
            InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
        } else {
            switch (id) {
                case 19 -> {
                    if (player.getLocation().getZone().getMap().isMapVillage()) {
                        Service.instance.sendLogOut(player.getSession(), "Không thể sử dụng trong làng. ");
                        return;
                    }
                    player.getSundry().setLastTimeComeHome(System.currentTimeMillis());
                    player.getSundry().setComeHome(true);
                }
                case 14, 15, 16, 17, 18 -> {
                    if (player.getSundry().getPk() == 0 && player.getSundry().isKiller()) {
                        Service.instance.sendLogOut(player.getSession(), "Không thể đeo khăn khi đang phạm tội");
                        return;
                    }
                    if (player.getSundry().getPk() != 0 && Util.canDoWithTime(player.getSundry().getLastTimeChangePk(), 180000)) {
                        player.getSundry().setPk((byte) 0);
                        onUseItemPk(player);
                        return;
                    }
                    player.getSundry().setPk((byte) id);
                    onUseItemPk(player);
                }
                case 100 -> {
                    TextBoxService.instance.sendChatWorld(player);
                }
                case 91 -> {
                    ChangeMapService.instance.changeMap(player, (short) 17, (short) -1, (short) -1);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                }
                case 29 -> {
                    player.getInfo().minusKiller((byte) 100);
                    if (player.getInfo().getKiller() <= 0) {
                        player.getSundry().setKiller(false);
                    }
                    MapService.instance.sendKiller(player);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                }
                case 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49 -> {
                    byte head = (byte) (id - 37);
                    String message = (head == 1 || head == 3 || head == 5 || head == 7 || head == 9 || head == 11)
                            ? (player.getInfo().getGender() == Const.MALE ? "Chỉ dành cho nữ" : "")
                            : (player.getInfo().getGender() == Const.FEMALE ? "Chỉ dành cho nam" : "");
                    if (!message.isEmpty()) {
                        Service.instance.sendLogOut(player.getSession(), message);
                        return;
                    }
                    player.getInfo().setHead(head);
                    Service.instance.sendMainCharInfo(player);
                    MapService.instance.sendInfoMe(player);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                }
                case 9 -> {
                    player.getPoint().setHp(player.getPoint().getHpMax());
                    player.getPoint().setMp(player.getPoint().getMpMax());
                    MapService.instance.onNewHpMp(player);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã dùng Nhân Sâm! Hồi phục đầy đủ HP và MP.");
                }
                case 10, 11, 12, 108, 109, 110, 111 -> {
                    useExpPotion(player, potion, id);
                }
                case 25 -> {
                    player.getPoint().resetPotentialPoints();
                    Service.instance.sendMainCharInfo(player);
                    MapService.instance.onNewHpMp(player);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã tẩy lại toàn bộ điểm tiềm năng thành công!");
                }
                case 26 -> {
                    player.getPoint().resetSkillPoints();
                    Service.instance.sendMainCharInfo(player);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã tẩy lại toàn bộ điểm kỹ năng thành công!");
                }
                case 30, 34, 64, 65, 66, 67, 68, 86 -> {
                    useHorsePotion(player, potion, id);
                }
                case 33 -> {
                    int price = player.getInventory().getPriceRepair(consts.ItemEquipConst.REPAIR_ALL);
                    if (price <= 0) {
                        ChatService.instance.sendChatOnlyMe(player, "Tất cả trang bị đang mặc đều còn nguyên độ bền, không cần sửa chữa.");
                        return;
                    }
                    if (!player.getInventory().minusXu(price)) {
                        ChatService.instance.sendChatOnlyMe(player, "Không đủ " + Util.formatNumber(price) + " xu để sửa chữa toàn bộ trang bị!");
                        return;
                    }
                    for (int i = 0; i < player.getInventory().getItemBody().size(); i++) {
                        ItemEquip item = player.getInventory().getItemBody().get(i);
                        if (item == null || item.getTemplate() == null) {
                            continue;
                        }
                        short mDurable = item.getTemplate().getDurable();
                        item.setDurable(mDurable);
                        item.setMDurable(mDurable);
                    }
                    InventoryService.instance.sendItemBody(player);
                    InventoryService.instance.sendItemPotion(player);
                    ChatService.instance.sendChatOnlyMe(player, "Đã dùng Thẻ mua bán sửa chữa toàn bộ trang bị (Trừ " + Util.formatNumber(price) + " xu).");
                }
                case 35 -> {
                    player.setBuffGioVang(3600000L, (short) 100);
                    player.getPoint().initPoint();
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã dùng Vé giờ vàng 1h! Tăng 100% kinh nghiệm trong 1 giờ.");
                }
                case 75 -> {
                    player.setBuffGioVang(3 * 3600000L, (short) 100);
                    player.getPoint().initPoint();
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã dùng Vé giờ vàng 3h! Tăng 100% kinh nghiệm trong 3 giờ.");
                }
                case 80 -> {
                    player.setBuffTinhAnh(3600000L); // Hiệu lực 1 giờ
                    player.getPoint().initPoint();
                    Service.instance.sendMainCharInfo(player);
                    MapService.instance.sendInfoMe(player);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã dùng Bình tăng lực! Tăng sức mạnh trong 1 giờ.");
                }
                case 81 -> {
                    player.setBuffGioVang(3600000L, (short) 150);
                    player.getPoint().initPoint();
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã dùng Vé giờ vàng! Tăng 150% kinh nghiệm.");
                }
                case 106, 160, 161, 162 -> {
                    openEliteChest(player, potion);
                }
                case 107 -> {
                    player.setBuffTinhAnh(180000L); // Hiệu lực 3 phút (180 giây)
                    player.getPoint().initPoint();
                    Service.instance.sendMainCharInfo(player);
                    MapService.instance.sendInfoMe(player);
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã dùng Tinh Anh Đan! Tăng 20% sát thương, giáp và HP trong 3 phút.");
                }
                case 119 -> {
                    if (!player.isDie()) {
                        Service.instance.sendLogOut(player.getSession(), "Bạn chưa chết, không thể sử dụng Tiên đan.");
                        return;
                    }
                    MapService.instance.revivePlayer(player, (byte) 100);
                    player.getSundry().setLastTimeRevived(System.currentTimeMillis());
                    InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
                    ChatService.instance.sendChatOnlyMe(player, "Đã sử dụng Tiên đan hồi sinh thành công!");
                }
                default -> {
                    Service.instance.sendLogOut(player.getSession(), "Không thể sử dụng");
                    Printer.printRed("Id Potion Has't Been Written " + id);
                }
            }
        }
        InventoryService.instance.sendItemPotion(player);
    }

    private void useHorsePotion(@NonNull Player player, @NonNull ItemPotion potion, short id) throws IOException {
        if (player.getHorse().getUseHorse() != HorseConst.NON_HORSE) {
            Service.instance.sendLogOut(player.getSession(), "Vui lòng xuống ngựa");
            return;
        }
        byte imageHorse = switch (id) {
            case 30 -> HorseConst.IMAGE_THIEN_LY_MA;
            case 34 -> HorseConst.IMAGE_XICH_THO;
            case 68 -> HorseConst.IMAGE_BACH_MA;
            case 64 -> HorseConst.IMAGE_HAC_NGUU;
            case 65 -> HorseConst.IMAGE_MANH_HO;
            case 66 -> HorseConst.IMAGE_SOI_XAM;
            case 67 -> HorseConst.IMAGE_TIEN_HAC;
            case 86 -> HorseConst.IMAGE_PHUONG_HOANG;
            default -> HorseConst.IMAGE_THIEN_LY_MA;
        };
        byte horseType = switch (id) {
            case 64 -> HorseConst.HORSE_HAC_NGUU;
            case 65 -> HorseConst.HORSE_MANH_HO;
            case 66 -> HorseConst.HORSE_SOI_XAM;
            case 67 -> HorseConst.HORSE_TIEN_HAC;
            case 86 -> HorseConst.HORSE_PHUONG_HOANG;
            default -> HorseConst.HORSE;
        };
        player.getHorse().setFly(id == 67 || id == 86);
        player.getHorse().setUseHorse(horseType);
        player.getHorse().setImageHorse(imageHorse);
        player.getHorse().setIdItem(id);
        player.getPoint().initPoint();
        Service.instance.sendMainCharInfo(player);
        MapService.instance.sendInfoMe(player);
        InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
    }

    private void useExpPotion(@NonNull Player player, @NonNull ItemPotion potion, short id) throws IOException {
        int expAdd = switch (id) {
            case 10 -> 100000;
            case 11 -> 500000;
            case 12 -> 1000000;
            case 108 -> 35000;
            case 109 -> 250000;
            case 110 -> 900000;
            case 111 -> 2200000;
            default -> 1000;
        };
        InventoryService.instance.minusQuantityItemPotion(player, potion, (short) 1);
        player.getPoint().plusExp(expAdd);
        
        // Kiểm tra thăng cấp nếu đủ kinh nghiệm
        MapService.instance.checkLevelUp(player);
        MapService.instance.onSetXP(player, expAdd);
        ChatService.instance.sendChatOnlyMe(player, "Bạn nhận được " + Util.formatNumber(expAdd) + " điểm kinh nghiệm!");
    }

    private void openEliteChest(@NonNull Player player, @NonNull ItemPotion chest) throws IOException {
        if (player.getInventory().isFullInventory()) {
            Service.instance.sendLogOut(player.getSession(), "Hành trang của bạn đã đầy, vui lòng dọn dẹp trước khi mở rương!");
            return;
        }
        
        // Trừ 1 rương tinh anh
        InventoryService.instance.minusQuantityItemPotion(player, chest, (short) 1);
        
        List<String> rewardNames = new ArrayList<>();
        int playerLv = player.getInfo().getLevel();
        byte classChar = player.getInfo().getClassPlayer();

        // Xác định bậc rương (Bậc 1: lv1-9, Bậc 2: lv10-19, Bậc 3: lv20-29, Bậc 4: lv30-35)
        int tier = switch (chest.getTemplate().getId()) {
            case 106 -> 1;
            case 160 -> 2;
            case 161 -> 3;
            case 162 -> 4;
            default -> 1;
        };

        // 1. Tiền tệ (Lượng): Bậc 1: 1-2; Bậc 2: 2-4; Bậc 3: 3-6; Bậc 4: 5-10 Lượng
        int minLuong = switch (tier) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 5;
            default -> 1;
        };
        int maxLuong = switch (tier) {
            case 1 -> 2;
            case 2 -> 4;
            case 3 -> 6;
            case 4 -> 10;
            default -> 2;
        };
        int luong = Util.nextInt(minLuong, maxLuong);
        player.getInventory().plusLuong(luong);
        rewardNames.add(luong + " Lượng");

        // 2. Tinh anh huyết (Bình KN Tinh Anh): theo bậc
        // Bậc 1: id=108 (Sơ cấp 35.000 KN), qty 1-2
        // Bậc 2: id=109 (Trung cấp 250.000 KN), qty 1-2
        // Bậc 3: id=110 (Cao cấp 900.000 KN), qty 1-2
        // Bậc 4: id=111 (Siêu cấp 2.200.000 KN), qty 1-2
        short knId = (short) switch (tier) {
            case 1 -> 108;
            case 2 -> 109;
            case 3 -> 110;
            case 4 -> 111;
            default -> 108;
        };
        short knQty = (short) (tier <= 2 ? Util.nextInt(1, 2) : Util.nextInt(1, 3));
        InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion(knId, knQty));
        rewardNames.add(knQty + " " + Manager.getPotionTemplate(knId).getName().split("\\n")[0]);

        // 3. Bình thuốc theo bậc chất lượng
        short potId;
        short potQty;
        if (tier == 1) {
            short[] potPool = {1, 4, 2, 5}; // HP nhỏ, MP nhỏ, HP vừa, MP vừa
            potId = potPool[Util.nextInt(0, potPool.length - 1)];
            potQty = (short) Util.nextInt(5, 10);
        } else if (tier == 2) {
            short[] potPool = {2, 5, 3, 6}; // HP vừa, MP vừa, HP to, MP to
            potId = potPool[Util.nextInt(0, potPool.length - 1)];
            potQty = (short) Util.nextInt(8, 15);
        } else if (tier == 3) {
            short[] potPool = {3, 6, 21, 23}; // HP to, MP to, HP đặc biệt, MP đặc biệt
            potId = potPool[Util.nextInt(0, potPool.length - 1)];
            potQty = (short) Util.nextInt(10, 20);
        } else {
            short[] potPool = {3, 6, 21, 23}; // Trần cao nhất: 15-25 bình cao cấp
            potId = potPool[Util.nextInt(0, potPool.length - 1)];
            potQty = (short) Util.nextInt(15, 25);
        }
        InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion(potId, potQty));
        rewardNames.add(potQty + " " + Manager.getPotionTemplate(potId).getName().split("\\n")[0]);

        // 4. Nguyên liệu sơ cấp: Bậc 1: 50% nhận 1 viên; Bậc 2: 1-2 viên; Bậc 3: 2-3 viên; Bậc 4: 2-4 viên
        short scQty = 0;
        if (tier == 1) {
            if (Util.isTrue(50, 100)) scQty = 1;
        } else if (tier == 2) {
            scQty = (short) Util.nextInt(1, 2);
        } else if (tier == 3) {
            scQty = (short) Util.nextInt(2, 3);
        } else {
            scQty = (short) Util.nextInt(2, 4);
        }
        if (scQty > 0) {
            short[] scPool = {5, 8, 12, 13, 14, 15, 16, 17, 18, 19};
            short scId = scPool[Util.nextInt(0, scPool.length - 1)];
            InventoryService.instance.addItemGem(player, ItemService.instance.createNewItemGem(scId, scQty));
            rewardNames.add(scQty + " " + Manager.getGemTemplate(scId).getName());
        }

        // 5. Nguyên liệu cao cấp: Bậc 1: 0%; Bậc 2: 25% (1 viên); Bậc 3: 50% (1-2 viên); Bậc 4: 75% (1-3 viên)
        int ccChance = switch (tier) {
            case 2 -> 25;
            case 3 -> 50;
            case 4 -> 75;
            default -> 0;
        };
        if (ccChance > 0 && Util.isTrue(ccChance, 100)) {
            short ccQty = (short) (tier == 2 ? 1 : (tier == 3 ? Util.nextInt(1, 2) : Util.nextInt(1, 3)));
            short[] ccPool = {0, 1, 2, 6, 7, 9, 10, 20, 21, 22, 23, 24, 25, 26, 27};
            short ccId = ccPool[Util.nextInt(0, ccPool.length - 1)];
            InventoryService.instance.addItemGem(player, ItemService.instance.createNewItemGem(ccId, ccQty));
            rewardNames.add(ccQty + " " + Manager.getGemTemplate(ccId).getName());
        }

        // 6. Trang bị / Vũ khí theo bậc:
        // Bậc 1: 40% nhận trang bị Lv 1-9
        // Bậc 2: 60% nhận trang bị Lv 10-19
        // Bậc 3: 80% nhận trang bị Lv 20-29
        // Bậc 4: 100% nhận trang bị Lv 30-35
        int equipChance = switch (tier) {
            case 1 -> 40;
            case 2 -> 60;
            case 3 -> 80;
            case 4 -> 100;
            default -> 50;
        };
        if (Util.isTrue(equipChance, 100) && !player.getInventory().isFullInventory()) {
            int targetMinLv = switch (tier) {
                case 1 -> 1;
                case 2 -> 10;
                case 3 -> 20;
                case 4 -> 30;
                default -> 1;
            };
            int targetMaxLv = switch (tier) {
                case 1 -> 9;
                case 2 -> 19;
                case 3 -> 29;
                case 4 -> 35;
                default -> 35;
            };
            List<ItemEquipTemplate> weaponList = new ArrayList<>();
            for (ItemEquipTemplate it : Manager.ITEM_EQUIPMENTS.values()) {
                if (it != null && it.getType() >= 3 && it.getType() <= 7) {
                    if (it.getClassChar() == classChar && it.getLevel() >= targetMinLv && it.getLevel() <= targetMaxLv) {
                        weaponList.add(it);
                    }
                }
            }
            if (weaponList.isEmpty()) {
                for (ItemEquipTemplate it : Manager.ITEM_EQUIPMENTS.values()) {
                    if (it != null && it.getType() >= 3 && it.getType() <= 7 && Math.abs(it.getLevel() - playerLv) <= 8) {
                        weaponList.add(it);
                    }
                }
            }
            if (!weaponList.isEmpty()) {
                ItemEquipTemplate weaponTpl = weaponList.get(Util.nextInt(0, weaponList.size() - 1));
                ItemEquip weapon = ItemService.instance.createNewItemEquipment(weaponTpl.getId(), classChar);
                if (weapon != null) {
                    InventoryService.instance.addItemBagEquipment(player, weapon);
                    rewardNames.add("Vũ khí [" + weaponTpl.getName() + "]");
                }
            }
        }

        // Cập nhật các gói tin cho client
        InventoryService.instance.sendItemPotion(player);
        InventoryService.instance.sendItemGem(player);
        InventoryService.instance.sendItemBag(player);
        Service.instance.sendMainCharInfo(player);

        // Thông báo kết quả mở rương
        String msg = "Mở Rương Tinh Anh (Bậc " + tier + ") nhận được: " + String.join(", ", rewardNames);
        Service.instance.sendLogOut(player.getSession(), msg);
    }

    public void riderAnimal(@NonNull Player player, short id, byte typeAnimal) throws IOException {
        if (player.getHorse().getUseHorse() != HorseConst.NON_HORSE) {
            Service.instance.sendLogOut(player.getSession(), "Vui lòng xuống ngựa");
            return;
        }
        ItemAnimal animal = InventoryService.instance.findItemAnimal(player, id, typeAnimal);
        if (animal == null) {
            return;
        }
        InventoryService.instance.removeItemAnimal(player, animal);
        byte useHorse = -1;
        byte imgHorse = -1;
        player.getHorse().setFly(false);
        switch (animal.getTemplate().getIdImage()) {
            case HorseConst.BACH_MA -> {
                useHorse = HorseConst.HORSE;
                imgHorse = HorseConst.IMAGE_BACH_MA;
            }
            case HorseConst.MANH_HO -> {
                useHorse = HorseConst.HORSE_MANH_HO;
                imgHorse = HorseConst.IMAGE_MANH_HO;
            }
            case HorseConst.SOI_XAM -> {
                useHorse = HorseConst.HORSE_SOI_XAM;
                imgHorse = HorseConst.IMAGE_SOI_XAM;
            }
            case HorseConst.TIEN_HAC -> {
                useHorse = HorseConst.HORSE_TIEN_HAC;
                imgHorse = HorseConst.IMAGE_TIEN_HAC;
                player.getHorse().setFly(true);
            }
            case HorseConst.HAC_NGUU -> {
                useHorse = HorseConst.HORSE_HAC_NGUU;
                imgHorse = HorseConst.IMAGE_HAC_NGUU;
            }
        }
        player.getHorse().setUseHorse(useHorse);
        player.getHorse().setImageHorse(imgHorse);
        player.getHorse().setAnimalUse(animal);
        player.getPoint().initPoint();
        Service.instance.sendMainCharInfo(player);
        InventoryService.instance.sendItemBody(player);
        InventoryService.instance.sendItemBodyAnimal(player);
        InventoryService.instance.sendItemAnimal(player);
        MapService.instance.sendInfoMe(player);
    }

    public void useItemEquipment(@NonNull Player player, short index) throws IOException {
        if (player.isDie()) {
            return;
        }
        ItemEquip equipment = InventoryService.instance.findItemBag(player, index);
        if (equipment == null) {
            return;
        }
        if (equipment.getTemplate().getGender() != 0 && equipment.getTemplate().getGender() != player.getInfo().getGender()) {
            return;
        }
        if (equipment.getClassChar() != -1 && equipment.getClassChar() != player.getInfo().getClassPlayer() && equipment.isWeapon()) {
            return;
        }
        if (equipment.getLevel() > equipment.getLevel()) {
            return;
        }
        ItemEquip hasEquipment = InventoryService.instance.findItemBodyByType(player, equipment.getTemplate().getType());
        if (hasEquipment != null) {
            InventoryService.instance.swapItemBagToBody(player, equipment, hasEquipment);
        } else {
            InventoryService.instance.removeItemBagEquipment(player, equipment);
            InventoryService.instance.addItemBodyEquipment(player, equipment);
        }
        InventoryService.instance.sendWeaponImage(player);
        InventoryService.instance.sendItemBag(player);
        InventoryService.instance.sendItemBody(player);
        player.getPoint().initPoint();
        MapService.instance.onNewHpMp(player);
    }

    private void onUseItemPk(@NonNull Player player) throws IOException {
        Message msg = new Message(CommandMessage.USE_ITEM_PK);
        msg.writer().writeShort(player.getIdPlayer());
        msg.writer().writeByte(player.getSundry().getPk() != 0 ? 1 : 0);
        msg.writer().writeByte(player.getSundry().getPk());
        MapService.instance.sendAllPlayerInMap(player, msg);
    }

    private void onUsePotionMp(@NonNull Player player, ItemPotion itemPotion, short valueAdd) throws IOException {
        Message msg = new Message(CommandMessage.USE_POTION);
        msg.writer().writeShort(player.getIdPlayer());
        msg.writer().writeByte(itemPotion.getTemplate().getId());
        msg.writer().writeShort(valueAdd);
        msg.writer().writeInt(player.getPoint().getMp());
        MapService.instance.sendAllPlayerInMap(player, msg);
    }

    private void onUsePotionHp(@NonNull Player player, ItemPotion itemPotion, short valueAdd) throws IOException {
        Message msg = new Message(CommandMessage.USE_POTION);
        msg.writer().writeShort(player.getIdPlayer());
        msg.writer().writeByte(itemPotion.getTemplate().getId());
        msg.writer().writeShort(valueAdd);
        msg.writer().writeInt(player.getPoint().getHp());
        msg.writer().writeByte(1);
        MapService.instance.sendAllPlayerInMap(player, msg);
    }

    public void onPlusHp(@NonNull Player player, short valueAdd) throws IOException {
        Message msg = new Message(CommandMessage.USE_POTION);
        msg.writer().writeShort(player.getIdPlayer());
        msg.writer().writeByte(4);
        msg.writer().writeShort(valueAdd);
        msg.writer().writeInt(player.getPoint().getHp());
        msg.writer().writeByte(1);
        MapService.instance.sendAllPlayerInMap(player, msg);
    }

    public void onPlusMp(@NonNull Player player, short valueAdd) throws IOException {
        Message msg = new Message(CommandMessage.USE_POTION);
        msg.writer().writeShort(player.getIdPlayer());
        msg.writer().writeByte(4);
        msg.writer().writeShort(valueAdd);
        msg.writer().writeInt(player.getPoint().getMp());
        MapService.instance.sendAllPlayerInMap(player, msg);
    }
}
