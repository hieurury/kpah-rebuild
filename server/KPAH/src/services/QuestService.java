package services;

import consts.NpcConst;
import item.ItemEquip;
import item.ItemPotion;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import network.Message;
import player.Player;
import player.QuestData;
import utils.CommandMessage;
import utils.Util;

/**
 * Quản lý logic Hệ thống Nhiệm vụ (Tân thủ & Hằng ngày).
 */
public class QuestService {

    public static final QuestService instance = new QuestService();

    // Quest IDs Daily
    public static final int DAILY_PHU_ONG_MICE = 1;
    public static final int DAILY_TUONG_QUAN_TRAVEL = 2;
    public static final int DAILY_THO_REN_SELL = 3;
    public static final int DAILY_TUONG_QUAN_KILL = 4;
    public static final int DAILY_THO_SAN_HUNT = 5;

    public void checkDailyReset(Player player) {
        QuestData qd = player.getQuestData();
        String today = LocalDate.now().toString();
        if (!today.equals(qd.getLastDailyReset())) {
            qd.setLastDailyReset(today);
            qd.setDailyQuestsGiven(0);
            qd.getDailyQuests().clear();
            qd.getDailyProgress().clear();
            qd.getDailyTargets().clear();
        }
    }

    public void insertQuestMenu(Player player, byte idNpc, List<String> menu) {
        checkDailyReset(player);
        QuestData qd = player.getQuestData();

        // Kiểm tra Chuỗi nhiệm vụ chính tuyến (0 -> 6)
        if (idNpc == NpcConst.TRUONG_LANG && qd.getBeginnerQuestId() == 0) {
            menu.add(0, "Nhiệm vụ (Chính tuyến)");
            return;
        }
        if (idNpc == NpcConst.THIET_BI && qd.getBeginnerQuestId() == 1) {
            menu.add(0, "Nhiệm vụ (Chính tuyến)");
            return;
        }
        if (idNpc == NpcConst.DI_UT_HP && qd.getBeginnerQuestId() == 2) {
            menu.add(0, "Nhiệm vụ (Chính tuyến)");
            return;
        }
        if (idNpc == NpcConst.ONG_NOI && qd.getBeginnerQuestId() == 3) {
            menu.add(0, "Nhiệm vụ (Chính tuyến)");
            return;
        }
        if (idNpc == NpcConst.PHU_ONG && qd.getBeginnerQuestId() == 4) {
            menu.add(0, "Nhiệm vụ (Chính tuyến)");
            return;
        }
        if (idNpc == NpcConst.THO_SAN && qd.getBeginnerQuestId() == 5) {
            menu.add(0, "Nhiệm vụ (Chính tuyến)");
            return;
        }
        if (idNpc == NpcConst.LAM_TUONG_QUAN && qd.getBeginnerQuestId() == 6) {
            menu.add(0, "Nhiệm vụ (Chính tuyến)");
            return;
        }

        // Nếu đã nhận nhiệm vụ hằng ngày ở NPC này
        if (qd.getDailyQuests().containsKey(idNpc)) {
            menu.add(0, "Nhiệm vụ (Hằng ngày)");
            return;
        }

        // Khả năng nhận nhiệm vụ hằng ngày mới (khi đã hoàn thành cấp 3 trở lên)
        if (qd.getBeginnerQuestId() >= 3 && qd.getDailyQuestsGiven() < 3) {
            if (idNpc == NpcConst.PHU_ONG || idNpc == NpcConst.THIET_BI || idNpc == NpcConst.LAM_TUONG_QUAN || idNpc == NpcConst.THO_SAN) {
                menu.add(0, "Nhận Nhiệm vụ (Hằng ngày)");
            }
        }
    }

    public String getNoQuestHint(Player player, byte idNpc) {
        QuestData qd = player.getQuestData();
        int qId = qd.getBeginnerQuestId();
        return switch (qId) {
            case 0 -> "Ngươi hãy đến gặp Trưởng Làng tại Làng Sen để nhận nhiệm vụ đầu tiên.";
            case 1 -> "Ngươi hãy đến gặp Thiết Bị (Thợ Rèn) để học cách tìm kiếm trang bị.";
            case 2 -> "Ngươi hãy đến gặp Dì Út tại Làng Sen để hỗ trợ diệt Chuột cống.";
            case 3 -> "Ngươi hãy đến gặp Ông Nội tại Làng Sen để nghe về bí kíp Ngũ Hành.";
            case 4 -> "Ngươi hãy đến gặp Phú Ông để thử thách rèn luyện bản lĩnh.";
            case 5 -> "Ngươi hãy đến gặp Thợ Săn để nhận nhiệm vụ săn dã thú rừng sâu.";
            case 6 -> "Ngươi hãy đến gặp Lâm Tướng Quân tại doanh trại để nhận lệnh chiêu mộ.";
            default -> "Ngươi đã hoàn thành toàn bộ chuỗi nhiệm vụ chính tuyến! Giờ hãy tham gia các nhiệm vụ hằng ngày.";
        };
    }

    public boolean processQuestMenu(Player player, byte idNpc) throws IOException {
        checkDailyReset(player);
        QuestData qd = player.getQuestData();

        // 1. Xử lý Chuỗi Nhiệm Vụ Chính Tuyến
        if (idNpc == NpcConst.TRUONG_LANG && qd.getBeginnerQuestId() == 0) {
            if (qd.getBeginnerProgress() == 0) {
                qd.setBeginnerProgress(1); // Đánh dấu đã nhận
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) nhận Nhiệm vụ Chính Tuyến: [Thử thách đầu tiên - Diệt 100 Nhím].", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Chào con, để trở thành một anh hùng thực thụ, con hãy ra ngoài kia tiêu diệt 100 con Nhím để chứng tỏ bản thân nhé!");
            } else if (qd.getBeginnerProgress() >= 101) {
                qd.setBeginnerQuestId(1);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(3000);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 1, 10)); // HP nhỏ
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 4, 10)); // MP nhỏ
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Chính Tuyến: [Thử thách đầu tiên] -> Nhận 3.000 xu, 10 HP nhỏ, 10 MP nhỏ.", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Làm tốt lắm! Con đã nhận được 3.000 xu và bình máu/mana. Hãy đến gặp Thợ Rèn Thiết Bị để lên đồ nhé!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Con mới tiêu diệt được " + (qd.getBeginnerProgress() - 1) + "/100 con Nhím thôi. Hãy tiếp tục cố gắng!");
            }
            return true;
        }

        if (idNpc == NpcConst.THIET_BI && qd.getBeginnerQuestId() == 1) {
            if (qd.getBeginnerProgress() == 0) {
                qd.setBeginnerProgress(1);
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) nhận Nhiệm vụ Chính Tuyến: [Tìm kiếm trang bị - Thu thập 3 trang bị].", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Nghe nói ngươi vừa giúp trưởng làng. Ta có thể rèn vũ khí cho ngươi, nhưng ngươi phải mang về 3 món trang bị rơi ra từ quái vật trước!");
            } else if (qd.getBeginnerProgress() >= 4) {
                qd.setBeginnerQuestId(2);
                qd.setBeginnerProgress(0);
                ItemEquip weapon = ItemService.instance.createNewItemEquipment(getWeaponLv6(player.getInfo().getClassPlayer()), player.getInfo().getClassPlayer());
                InventoryService.instance.addItemBagEquipment(player, weapon);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 107, 2)); // Tinh Anh Đan
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 108, 2)); // Tinh Anh Huyết
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Chính Tuyến: [Tìm kiếm trang bị] -> Nhận Vũ khí Lv6, 2 Tinh Anh Đan, 2 Tinh Anh Huyết.", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Đây là vũ khí cấp 6, Tinh Anh Đan và Tinh Anh Huyết. Giờ hãy đến gặp Dì Út bán thuốc nhé!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Ngươi mới nhặt được " + (qd.getBeginnerProgress() - 1) + "/3 trang bị. Tiếp tục đi!");
            }
            return true;
        }

        if (idNpc == NpcConst.DI_UT_HP && qd.getBeginnerQuestId() == 2) {
            if (qd.getBeginnerProgress() == 0) {
                qd.setBeginnerProgress(1);
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) nhận Nhiệm vụ Chính Tuyến: [Tiêu diệt Chuột Cống - Diệt 30 Chuột Cống].", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Chào dũng sĩ, kho thảo dược của dì đang bị lũ Chuột Cống phá phách. Cháu hãy giúp dì diệt 30 con Chuột Cống nhé!");
            } else if (qd.getBeginnerProgress() >= 31) {
                qd.setBeginnerQuestId(3);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(5000);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 2, 20)); // HP vừa
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 5, 20)); // MP vừa
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Chính Tuyến: [Tiêu diệt Chuột Cống] -> Nhận 5.000 xu, 20 HP vừa, 20 MP vừa.", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Cảm ơn cháu nhiều lắm! Dì gửi tặng 5.000 xu và 40 bình dược phẩm. Giờ hãy đến thăm Ông Nội ở Làng Sen nhé!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Cháu mới tiêu diệt được " + (qd.getBeginnerProgress() - 1) + "/30 Chuột Cống thôi. Cố lên nhé!");
            }
            return true;
        }

        if (idNpc == NpcConst.ONG_NOI && qd.getBeginnerQuestId() == 3) {
            if (player.getInfo().getLevel() >= 10) {
                qd.setBeginnerQuestId(4);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(8000);
                InventoryService.instance.addItemGem(player, ItemService.instance.createNewItemGem((short) 5, (short) 1)); // Đá may mắn 1
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 11, 1)); // Thẻ x1.5 EXP
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Chính Tuyến: [Bí kíp Ngũ Hành - Đạt cấp 10] -> Nhận 8.000 xu, Đá may mắn 1, Thẻ x1.5 EXP.", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Tốt lắm, cháu đã đạt cấp 10 và thấu hiểu đạo ngũ hành! Ta tặng cháu 8.000 xu, Bùa may mắn và Thẻ EXP. Hãy đến tìm Phú Ông để thử thách bản lĩnh!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Cháu hãy rèn luyện đạt cấp 10 rồi quay lại đây, ta sẽ truyền thụ bí kíp Ngũ Hành và ban thưởng! (Hiện tại: Cấp " + player.getInfo().getLevel() + "/10)");
            }
            return true;
        }

        if (idNpc == NpcConst.PHU_ONG && qd.getBeginnerQuestId() == 4) {
            if (player.getInfo().getLevel() >= 15 && qd.getTotalMonstersKilled() >= 500) {
                qd.setBeginnerQuestId(5);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(15000);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 11, 2)); // Thẻ x1.5 EXP
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 108, 3)); // Tinh Anh Huyết
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Chính Tuyến: [Thử thách Phú Ông - Cấp 15 & 500 Quái] -> Nhận 15.000 xu, 2 Thẻ x1.5 EXP, 3 Tinh Anh Huyết.", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Ha ha, dũng sĩ quả nhiên danh bất hư truyền! Nhận lấy 15.000 xu, Thẻ EXP và Tinh Anh Huyết. Giờ hãy đến tìm Thợ Săn để thử tài săn thú!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Để nhận thưởng của ta, ngươi phải đạt cấp 15 và tiêu diệt ít nhất 500 quái vật! (Hiện tại: Cấp " + player.getInfo().getLevel() + "/15, Diệt: " + qd.getTotalMonstersKilled() + "/500)");
            }
            return true;
        }

        if (idNpc == NpcConst.THO_SAN && qd.getBeginnerQuestId() == 5) {
            if (qd.getBeginnerProgress() == 0) {
                qd.setBeginnerProgress(1);
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) nhận Nhiệm vụ Chính Tuyến: [Tài săn dã thú - Diệt 50 quái cấp >= 12].", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Chào tráng sĩ, rừng sâu dạo này dã thú hung dữ hoành hành. Ngươi hãy tiêu diệt 50 quái vật từ cấp 12 trở lên để chứng tỏ tài săn bắn!");
            } else if (qd.getBeginnerProgress() >= 51) {
                qd.setBeginnerQuestId(6);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(25000);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 107, 5)); // Tinh Anh Đan
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 108, 3)); // Tinh Anh Huyết
                InventoryService.instance.addItemGem(player, ItemService.instance.createNewItemGem((short) 8, (short) 2)); // Luyện kim dược
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Chính Tuyến: [Tài săn dã thú] -> Nhận 25.000 xu, 5 Tinh Anh Đan, 3 Tinh Anh Huyết, 2 Luyện kim dược.", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Tuyệt vời! Ngươi quả là một thợ săn bậc thầy. Hãy đến diện kiến Lâm Tướng Quân tại doanh trại để nhận lệnh chiêu mộ!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Ngươi mới săn được " + (qd.getBeginnerProgress() - 1) + "/50 dã thú hung tợn. Hãy tiếp tục săn lùng!");
            }
            return true;
        }

        if (idNpc == NpcConst.LAM_TUONG_QUAN && qd.getBeginnerQuestId() == 6) {
            if (player.getInfo().getLevel() >= 20 && qd.getBeginnerProgress() >= 101) {
                qd.setBeginnerQuestId(7);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(50000);
                player.getInventory().plusLuong(20);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 108, 5)); // Tinh Anh Huyết
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Chính Tuyến: [Chiêu mộ Lâm Tướng Quân - Cấp 20 & Diệt 100 quái cấp >= 18] -> Nhận 50.000 Xu, 20 Lượng và 5 Tinh Anh Huyết.", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Chúc mừng Đại Dũng Sĩ! Ngươi đã hoàn thành toàn bộ chuỗi nhiệm vụ chính tuyến của giang sơn KPAH! Đã nhận: 50.000 Xu, 20 Lượng và 5 Tinh Anh Huyết.");
            } else if (qd.getBeginnerProgress() == 0) {
                qd.setBeginnerProgress(1);
                sendQuestInfo(player);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) nhận Nhiệm vụ Chính Tuyến: [Chiêu mộ Lâm Tướng Quân - Đạt cấp 20 & Diệt 100 quái cấp >= 18].", player.getName(), player.getIdPlayer());
                Service.instance.sendLogOut(player.getSession(), "Lâm Tướng Quân: Đất nước cần những bậc kỳ tài! Ngươi hãy đạt cấp 20 và tiêu diệt 100 quái vật cấp cao (từ cấp 18 trở lên) để gia nhập đội quân danh dự!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Tiến độ: Cấp " + player.getInfo().getLevel() + "/20, Diệt quái biên ải: " + (qd.getBeginnerProgress() - 1) + "/100. Hãy kiên trì dũng sĩ!");
            }
            return true;
        }

        // 2. Xử lý Nhiệm vụ Hằng ngày
        if (qd.getBeginnerQuestId() >= 3) {
            if (qd.getDailyQuests().containsKey(idNpc)) {
                int questId = qd.getDailyQuests().get(idNpc);
                int progress = qd.getDailyProgress().get(idNpc);
                int target = qd.getDailyTargets().get(idNpc);

                if (progress >= target) {
                    // Hoàn thành và phát thưởng
                    giveDailyReward(player, questId, target);
                    qd.getDailyQuests().remove(idNpc);
                    qd.getDailyProgress().remove(idNpc);
                    qd.getDailyTargets().remove(idNpc);
                    sendQuestInfo(player);
                    utils.ServerLog.quest("Nhân vật '%s' (ID: %d) hoàn thành Nhiệm vụ Hằng Ngày [Mã: %d, Mục tiêu: %d] -> Đã phát thưởng thành công.", player.getName(), player.getIdPlayer(), questId, target);
                    Service.instance.sendLogOut(player.getSession(), "Làm tốt lắm! Phần thưởng đã được gửi vào hành trang.");
                } else {
                    String desc = getDailyDesc(questId, progress, target);
                    Service.instance.sendLogOut(player.getSession(), "Ngươi vẫn chưa hoàn thành nhiệm vụ:\n" + desc);
                }
                return true;
            } else if (qd.getDailyQuestsGiven() < 3 && (idNpc == NpcConst.PHU_ONG || idNpc == NpcConst.THIET_BI || idNpc == NpcConst.LAM_TUONG_QUAN)) {
                // Nhận nhiệm vụ mới
                int questId = getRandomQuestForNpc(idNpc);
                if (questId == -1) return false;
                
                int target = generateTargetForQuest(questId);
                qd.getDailyQuests().put(idNpc, questId);
                qd.getDailyProgress().put(idNpc, 0);
                qd.getDailyTargets().put(idNpc, target);
                qd.setDailyQuestsGiven(qd.getDailyQuestsGiven() + 1);
                sendQuestInfo(player);
                
                String desc = getDailyDesc(questId, 0, target);
                utils.ServerLog.quest("Nhân vật '%s' (ID: %d) nhận Nhiệm vụ Hằng Ngày: [%s].", player.getName(), player.getIdPlayer(), desc.replace('\n', ' '));
                Service.instance.sendLogOut(player.getSession(), "Ta có một nhiệm vụ cho ngươi:\n" + desc);
                return true;
            }
        }

        return false; // Không thuộc menu nhiệm vụ
    }

    private int getRandomQuestForNpc(byte idNpc) {
        if (idNpc == NpcConst.PHU_ONG) return DAILY_PHU_ONG_MICE;
        if (idNpc == NpcConst.THIET_BI) return DAILY_THO_REN_SELL;
        if (idNpc == NpcConst.THO_SAN) return DAILY_THO_SAN_HUNT;
        if (idNpc == NpcConst.LAM_TUONG_QUAN) {
            return Util.nextInt(2) == 0 ? DAILY_TUONG_QUAN_TRAVEL : DAILY_TUONG_QUAN_KILL;
        }
        return -1;
    }

    private int generateTargetForQuest(int questId) {
        return switch (questId) {
            case DAILY_PHU_ONG_MICE -> Util.nextInt(30, 80);
            case DAILY_TUONG_QUAN_TRAVEL -> Util.nextInt(3000, 8000);
            case DAILY_THO_REN_SELL -> 10;
            case DAILY_TUONG_QUAN_KILL -> Util.nextInt(50, 200);
            case DAILY_THO_SAN_HUNT -> Util.nextInt(20, 50);
            default -> 10;
        };
    }

    private String getDailyDesc(int questId, int progress, int target) {
        return switch (questId) {
            case DAILY_PHU_ONG_MICE -> "Tiêu diệt Chuột cống: " + progress + "/" + target;
            case DAILY_TUONG_QUAN_TRAVEL -> "Hành quân: " + progress + "/" + target + " px";
            case DAILY_THO_REN_SELL -> "Bán trang bị (+-2 Lv): " + progress + "/" + target;
            case DAILY_TUONG_QUAN_KILL -> "Trừ hại cho dân (Diệt quái +-2 Lv): " + progress + "/" + target;
            case DAILY_THO_SAN_HUNT -> "Săn dã thú (Lv >= 15): " + progress + "/" + target;
            default -> "Nhiệm vụ: " + progress + "/" + target;
        };
    }

    private void giveDailyReward(Player player, int questId, int target) throws IOException {
        switch (questId) {
            case DAILY_PHU_ONG_MICE -> {
                player.getInventory().plusXu(250L * target);
                MapService.instance.onSetXP(player, 300 * target);
            }
            case DAILY_TUONG_QUAN_TRAVEL -> {
                player.getInventory().plusXu(3L * target);
                int hpCount = Util.nextInt(15, 30);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 2, hpCount)); // med HP
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 5, hpCount)); // med MP
            }
            case DAILY_THO_REN_SELL -> {
                int lv = player.getInfo().getLevel();
                player.getInventory().plusXu((long) 150 * lv * target);
                MapService.instance.onSetXP(player, 250 * lv * target);
            }
            case DAILY_TUONG_QUAN_KILL -> {
                MapService.instance.onSetXP(player, 150 * target);
                player.getInventory().plusLuong(Util.nextInt(10, 25));
                
                int matCount = Util.nextInt(2, 4);
                for (int i = 0; i < matCount; i++) {
                    short gemId = switch (Util.nextInt(1, 4)) {
                        case 1 -> (short) 5; // Đá may mắn cấp 1
                        case 2 -> (short) 8; // Luyện kim dược
                        case 3 -> (short) 12; // Tăng 1 sức mạnh
                        default -> (short) 15; // Tăng 1 sức khoẻ
                    };
                    InventoryService.instance.addItemGem(player, ItemService.instance.createNewItemGem(gemId, (short) 1));
                }
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 108, 1)); // Tinh Anh Huyết
            }
            case DAILY_THO_SAN_HUNT -> {
                player.getInventory().plusXu(400L * target);
                MapService.instance.onSetXP(player, 400 * target);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 108, 1)); // Tinh Anh Huyết
                InventoryService.instance.addItemGem(player, ItemService.instance.createNewItemGem((short) 5, (short) 1)); // Đá may mắn 1
                InventoryService.instance.sendItemPotion(player);
                InventoryService.instance.sendItemGem(player);
            }
        }
    }

    // --- Triggers ---

    public void onKillMonster(Player player, short mobId, byte mobLevel) {
        QuestData qd = player.getQuestData();
        qd.setTotalMonstersKilled(qd.getTotalMonstersKilled() + 1);

        // Quest 0: Diệt nhím (id = 1 trong bảng monsters)
        if (qd.getBeginnerQuestId() == 0 && qd.getBeginnerProgress() > 0 && qd.getBeginnerProgress() <= 100) {
            if (mobId == 1) { // 1 = Nhím
                qd.setBeginnerProgress(qd.getBeginnerProgress() + 1);
                sendQuestInfo(player);
                try {
                    int count = qd.getBeginnerProgress() - 1;
                    if (count >= 100) {
                        ChatService.instance.sendChatDelay(player, "Nhiệm vụ Nhím: 100/100 (Hoàn thành! Hãy về gặp Trưởng Làng)");
                    } else if (count % 10 == 0) {
                        ChatService.instance.sendChatDelay(player, "Nhiệm vụ Nhím: " + count + "/100");
                    }
                } catch (Exception ignored) {}
            }
        }

        // Quest 2: Diệt Chuột cống (id = 9) giúp Dì Út
        if (qd.getBeginnerQuestId() == 2 && qd.getBeginnerProgress() > 0 && qd.getBeginnerProgress() <= 30) {
            if (mobId == 9) { // 9 = Chuột cống
                qd.setBeginnerProgress(qd.getBeginnerProgress() + 1);
                sendQuestInfo(player);
                try {
                    int count = qd.getBeginnerProgress() - 1;
                    if (count >= 30) {
                        ChatService.instance.sendChatDelay(player, "Nhiệm vụ Chuột cống: 30/30 (Hoàn thành! Hãy về gặp Dì Út)");
                    } else if (count % 5 == 0) {
                        ChatService.instance.sendChatDelay(player, "Nhiệm vụ Chuột cống: " + count + "/30");
                    }
                } catch (Exception ignored) {}
            }
        }

        // Quest 4: Đạt cấp 15 và diệt 500 quái
        if (qd.getBeginnerQuestId() == 4) {
            int kills = qd.getTotalMonstersKilled();
            if (kills <= 500 && (kills % 50 == 0 || kills == 500)) {
                sendQuestInfo(player);
                try {
                    if (kills == 500) {
                        ChatService.instance.sendChatDelay(player, "Đã tiêu diệt 500 quái! Hãy đạt cấp 15 và về gặp Phú Ông.");
                    }
                } catch (Exception ignored) {}
            }
        }

        // Quest 5: Săn 50 dã thú hung tợn (mobLevel >= 12)
        if (qd.getBeginnerQuestId() == 5 && qd.getBeginnerProgress() > 0 && qd.getBeginnerProgress() <= 50) {
            if (mobLevel >= 12) {
                qd.setBeginnerProgress(qd.getBeginnerProgress() + 1);
                sendQuestInfo(player);
                try {
                    int count = qd.getBeginnerProgress() - 1;
                    if (count >= 50) {
                        ChatService.instance.sendChatDelay(player, "Săn dã thú: 50/50 (Hoàn thành! Hãy về gặp Thợ Săn)");
                    } else if (count % 10 == 0) {
                        ChatService.instance.sendChatDelay(player, "Săn dã thú: " + count + "/50");
                    }
                } catch (Exception ignored) {}
            }
        }

        // Quest 6: Diệt 100 quái vùng biên giới (mobLevel >= 18)
        if (qd.getBeginnerQuestId() == 6 && qd.getBeginnerProgress() > 0 && qd.getBeginnerProgress() <= 100) {
            if (mobLevel >= 18) {
                qd.setBeginnerProgress(qd.getBeginnerProgress() + 1);
                sendQuestInfo(player);
                try {
                    int count = qd.getBeginnerProgress() - 1;
                    if (count >= 100) {
                        ChatService.instance.sendChatDelay(player, "Quái biên ải: 100/100 (Hoàn thành! Hãy về gặp Lâm Tướng Quân)");
                    } else if (count % 10 == 0) {
                        ChatService.instance.sendChatDelay(player, "Quái biên ải: " + count + "/100");
                    }
                } catch (Exception ignored) {}
            }
        }

        // Daily 1: Diệt chuột (id = 9)
        if (qd.getDailyQuests().containsValue(DAILY_PHU_ONG_MICE)) {
            if (mobId == 9) {
                byte npcId = NpcConst.PHU_ONG;
                if (qd.getDailyProgress().containsKey(npcId)) {
                    int p = qd.getDailyProgress().get(npcId);
                    int target = qd.getDailyTargets().get(npcId);
                    if (p < target) {
                        qd.getDailyProgress().put(npcId, p + 1);
                        sendQuestInfo(player);
                        try {
                            int count = p + 1;
                            if (count >= target) {
                                ChatService.instance.sendChatDelay(player, "Nhiệm vụ Chuột: " + count + "/" + target + " (Hoàn thành! Về gặp Phú Ông)");
                            } else if (count % 10 == 0) {
                                ChatService.instance.sendChatDelay(player, "Nhiệm vụ Chuột: " + count + "/" + target);
                            }
                        } catch (Exception ignored) {}
                    }
                }
            }
        }

        // Daily 4: Diệt quái +-2 lv
        if (qd.getDailyQuests().containsValue(DAILY_TUONG_QUAN_KILL)) {
            int lvDiff = Math.abs(player.getInfo().getLevel() - mobLevel);
            if (lvDiff <= 2) {
                byte npcId = NpcConst.LAM_TUONG_QUAN;
                if (qd.getDailyProgress().containsKey(npcId)) {
                    int p = qd.getDailyProgress().get(npcId);
                    int target = qd.getDailyTargets().get(npcId);
                    if (p < target) {
                        qd.getDailyProgress().put(npcId, p + 1);
                        sendQuestInfo(player);
                        try {
                            int count = p + 1;
                            if (count >= target) {
                                ChatService.instance.sendChatDelay(player, "Nhiệm vụ Diệt quái: " + count + "/" + target + " (Hoàn thành! Về gặp Lâm Tướng Quân)");
                            } else if (count % 10 == 0) {
                                ChatService.instance.sendChatDelay(player, "Nhiệm vụ Diệt quái: " + count + "/" + target);
                            }
                        } catch (Exception ignored) {}
                    }
                }
            }
        }

        // Daily 5: Săn dã thú (mobLevel >= 15)
        if (qd.getDailyQuests().containsValue(DAILY_THO_SAN_HUNT)) {
            if (mobLevel >= 15) {
                byte npcId = NpcConst.THO_SAN;
                if (qd.getDailyProgress().containsKey(npcId)) {
                    int p = qd.getDailyProgress().get(npcId);
                    int target = qd.getDailyTargets().get(npcId);
                    if (p < target) {
                        qd.getDailyProgress().put(npcId, p + 1);
                        sendQuestInfo(player);
                        try {
                            int count = p + 1;
                            if (count >= target) {
                                ChatService.instance.sendChatDelay(player, "Nhiệm vụ Thợ Săn: " + count + "/" + target + " (Hoàn thành! Về gặp Thợ Săn)");
                            } else if (count % 5 == 0) {
                                ChatService.instance.sendChatDelay(player, "Nhiệm vụ Thợ Săn: " + count + "/" + target);
                            }
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
    }

    public void onPickUpEquipment(Player player) {
        QuestData qd = player.getQuestData();
        // Quest 1: Nhặt đồ
        if (qd.getBeginnerQuestId() == 1 && qd.getBeginnerProgress() > 0 && qd.getBeginnerProgress() <= 3) {
            qd.setBeginnerProgress(qd.getBeginnerProgress() + 1);
            sendQuestInfo(player);
            try {
                int count = qd.getBeginnerProgress() - 1;
                if (count >= 3) {
                    ChatService.instance.sendChatDelay(player, "Nhiệm vụ Nhặt đồ: 3/3 (Hoàn thành! Về gặp Thợ Rèn)");
                } else {
                    ChatService.instance.sendChatDelay(player, "Nhiệm vụ Nhặt đồ: " + count + "/3");
                }
            } catch (Exception ignored) {}
        }
    }

    public void onMove(Player player, int distance) {
        QuestData qd = player.getQuestData();
        qd.setTotalPixelsTraveled(qd.getTotalPixelsTraveled() + distance);

        // Daily 2: Hành quân
        if (qd.getDailyQuests().containsValue(DAILY_TUONG_QUAN_TRAVEL)) {
            byte npcId = NpcConst.LAM_TUONG_QUAN;
            if (qd.getDailyProgress().containsKey(npcId)) {
                int p = qd.getDailyProgress().get(npcId);
                int target = qd.getDailyTargets().get(npcId);
                if (p < target) {
                    int next = Math.min(p + distance, target);
                    qd.getDailyProgress().put(npcId, next);
                    if (next >= target) {
                        sendQuestInfo(player);
                        try {
                            ChatService.instance.sendChatDelay(player, "Hành quân: Hoàn thành! Về gặp Lâm Tướng Quân");
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
    }

    public void onSellEquipment(Player player, ItemEquip item) {
        QuestData qd = player.getQuestData();
        qd.setTotalItemsSold(qd.getTotalItemsSold() + 1);

        // Daily 3: Bán trang bị +-2 lv
        if (qd.getDailyQuests().containsValue(DAILY_THO_REN_SELL)) {
            int lvDiff = Math.abs(player.getInfo().getLevel() - item.getLevel());
            if (lvDiff <= 2) {
                byte npcId = NpcConst.THIET_BI;
                if (qd.getDailyProgress().containsKey(npcId)) {
                    int p = qd.getDailyProgress().get(npcId);
                    int target = qd.getDailyTargets().get(npcId);
                    if (p < target) {
                        qd.getDailyProgress().put(npcId, p + 1);
                        sendQuestInfo(player);
                        try {
                            int count = p + 1;
                            if (count >= target) {
                                ChatService.instance.sendChatDelay(player, "Bán đồ: " + count + "/" + target + " (Hoàn thành! Về gặp Thợ Rèn)");
                            } else {
                                ChatService.instance.sendChatDelay(player, "Bán đồ: " + count + "/" + target);
                            }
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
    }

    public void sendQuestInfo(Player player) {
        if (player == null || player.getSession() == null || !player.getSession().isConnected()) {
            return;
        }
        try {
            checkDailyReset(player);
            QuestData qd = player.getQuestData();
            if (qd == null) return;

            class QuestEntry {
                short id;
                byte slot;
                String title;
                byte npcId;
                String desc;
                String reward;

                QuestEntry(short id, byte slot, String title, byte npcId, String desc, String reward) {
                    this.id = id;
                    this.slot = slot;
                    this.title = title;
                    this.npcId = npcId;
                    this.desc = desc;
                    this.reward = reward;
                }
            }

            List<QuestEntry> list = new ArrayList<>();

            // 1. Chuỗi Nhiệm Vụ Chính Tuyến (slot 0 -> class_abj.aV)
            int mainQ = qd.getBeginnerQuestId();
            int progress = qd.getBeginnerProgress();

            switch (mainQ) {
                case 0 -> { // Trưởng Làng
                    String desc = (progress == 0) ? "Đến gặp Trưởng Làng nhận nhiệm vụ" :
                                  (progress <= 100) ? "Tiêu diệt 100 Nhím Làng Sen|Tiến độ: " + (progress - 1) + "/100" :
                                                      "Tiêu diệt 100 Nhím Làng Sen|Tiến độ: 100/100 (Về gặp Trưởng Làng)";
                    list.add(new QuestEntry(
                        (short) 1,
                        (byte) 0,
                        "Nhiệm vụ: Tân thủ (1/7)",
                        NpcConst.TRUONG_LANG,
                        desc,
                        "Thưởng: 3.000 xu, 10 HP nhỏ, 10 MP nhỏ"
                    ));
                }
                case 1 -> { // Thợ Rèn Thiết Bị
                    String desc = (progress == 0) ? "Đến gặp Thợ Rèn Thiết Bị nhận việc" :
                                  (progress <= 3) ? "Thu thập 3 trang bị từ quái|Tiến độ: " + (progress - 1) + "/3" :
                                                    "Thu thập 3 trang bị từ quái|Tiến độ: 3/3 (Về gặp Thợ Rèn)";
                    list.add(new QuestEntry(
                        (short) 2,
                        (byte) 0,
                        "Nhiệm vụ: Tân thủ (2/7)",
                        NpcConst.THIET_BI,
                        desc,
                        "Thưởng: Vũ khí cấp 6, 2 Tinh Anh Đan, 2 Tinh Anh Huyết"
                    ));
                }
                case 2 -> { // Dì Út
                    String desc = (progress == 0) ? "Đến gặp Dì Út tại Làng Sen nhận việc" :
                                  (progress <= 30) ? "Diệt Chuột cống bảo vệ kho|Tiến độ: " + (progress - 1) + "/30" :
                                                     "Diệt Chuột cống bảo vệ kho|Tiến độ: 30/30 (Về gặp Dì Út)";
                    list.add(new QuestEntry(
                        (short) 3,
                        (byte) 0,
                        "Nhiệm vụ: Dược Sĩ (3/7)",
                        NpcConst.DI_UT_HP,
                        desc,
                        "Thưởng: 5.000 xu, 20 HP vừa, 20 MP vừa"
                    ));
                }
                case 3 -> { // Ông Nội
                    int lv = player.getInfo().getLevel();
                    boolean done = (lv >= 10);
                    String desc = done ? "Đạt cấp 10 (Đã hoàn thành! Hãy về gặp Ông Nội)" :
                                         "Luyện cấp đạt cấp 10|Hiện tại: Cấp " + lv + "/10";
                    list.add(new QuestEntry(
                        (short) 4,
                        (byte) 0,
                        "Nhiệm vụ: Ngũ Hành (4/7)",
                        NpcConst.ONG_NOI,
                        desc,
                        "Thưởng: 8.000 xu, 1 Bùa May Mắn, 1 Thẻ x1.5 EXP"
                    ));
                }
                case 4 -> { // Phú Ông
                    int lv = player.getInfo().getLevel();
                    int kills = qd.getTotalMonstersKilled();
                    boolean done = (lv >= 15 && kills >= 500);
                    String desc = done ? "Đạt cấp 15 & diệt 500 quái (Về gặp Phú Ông)" :
                                         "Đạt cấp 15 (hiện: " + lv + "/15)|Diệt 500 quái (hiện: " + Math.min(kills, 500) + "/500)";
                    list.add(new QuestEntry(
                        (short) 5,
                        (byte) 0,
                        "Nhiệm vụ: Thử Thách (5/7)",
                        NpcConst.PHU_ONG,
                        desc,
                        "Thưởng: 15.000 xu, 2 Thẻ x1.5 EXP, 3 Tinh Anh Huyết"
                    ));
                }
                case 5 -> { // Thợ Săn
                    String desc = (progress == 0) ? "Đến gặp Thợ Săn nhận nhiệm vụ săn thú" :
                                  (progress <= 50) ? "Săn 50 dã thú (Lv >= 12)|Tiến độ: " + (progress - 1) + "/50" :
                                                     "Săn 50 dã thú (Lv >= 12)|Tiến độ: 50/50 (Về gặp Thợ Săn)";
                    list.add(new QuestEntry(
                        (short) 6,
                        (byte) 0,
                        "Nhiệm vụ: Thợ Săn (6/7)",
                        NpcConst.THO_SAN,
                        desc,
                        "Thưởng: 25.000 xu, 1 Rương Tinh Anh, 5 Tinh Anh Đan, 2 Luyện Kim Dược"
                    ));
                }
                case 6 -> { // Lâm Tướng Quân
                    int lv = player.getInfo().getLevel();
                    boolean lvOk = (lv >= 20);
                    boolean killOk = (progress >= 101);
                    String desc = (lvOk && killOk) ? "Gia nhập quân đội (Đã xong, hãy gặp Lâm Tướng Quân)" :
                                  "Đạt cấp 20 (hiện: " + lv + "/20)|Diệt 100 quái biên giới (hiện: " + Math.max(0, progress - 1) + "/100)";
                    list.add(new QuestEntry(
                        (short) 7,
                        (byte) 0,
                        "Nhiệm vụ: Quân Đội (7/7)",
                        NpcConst.LAM_TUONG_QUAN,
                        desc,
                        "Thưởng: 50.000 xu, 20 Lượng, 5 Tinh Anh Huyết"
                    ));
                }
            }

            // 2. Daily quests (slot 2 -> class_abj.aX)
            if (!qd.getDailyQuests().isEmpty()) {
                for (var entry : qd.getDailyQuests().entrySet()) {
                    byte npcId = entry.getKey();
                    int questId = entry.getValue();
                    int p = qd.getDailyProgress().getOrDefault(npcId, 0);
                    int target = qd.getDailyTargets().getOrDefault(npcId, 1);
                    String desc = getDailyDesc(questId, Math.min(p, target), target);
                    if (p >= target) {
                        desc += "|(Đã xong, hãy về trả nhiệm vụ)";
                    }
                    list.add(new QuestEntry(
                        (short) (100 + questId),
                        (byte) 2,
                        "Nhiệm vụ: Hằng ngày",
                        npcId, // Gán đúng NPC ID cho Minimap tracker
                        desc,
                        "Thưởng: Xu, EXP và vật phẩm quý"
                    ));
                }
            }

            if (list.isEmpty()) {
                // Clear active quest in client (by = 3)
                Message msg = new Message(CommandMessage.CMD_NEW_QUEST);
                msg.writer().writeByte(3);
                msg.writer().writeByte(1);
                msg.writer().writeShort(0);
                msg.writer().writeByte(0);
                msg.writer().writeUTF("");
                player.getSession().sendMessage(msg);
                return;
            }

            Message msg = new Message(CommandMessage.CMD_NEW_QUEST);
            msg.writer().writeByte(1); // by = 1
            msg.writer().writeByte(list.size());
            for (QuestEntry qe : list) {
                msg.writer().writeShort(qe.id);
                msg.writer().writeByte(qe.slot); // 0 = main, 2 = daily
                msg.writer().writeUTF(qe.title);
                msg.writer().writeByte(qe.npcId); // NPC ID định vị trên Minimap
                msg.writer().writeUTF("");
                msg.writer().writeUTF(qe.desc);
                msg.writer().writeUTF(qe.reward);
            }
            player.getSession().sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private short getWeaponLv6(byte classPlayer) {
        return switch (classPlayer) {
            case consts.Const.CHIEN_BINH -> 86; // ID vũ khí lv thấp
            case consts.Const.CUNG_THU -> 107;
            case consts.Const.KIEM_KHACH -> 79;
            case consts.Const.PHAP_SU -> 93;
            case consts.Const.DAU_SI -> 100;
            default -> 79;
        };
    }
}
