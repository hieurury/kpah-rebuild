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

    private void checkDailyReset(Player player) {
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

        // Kiểm tra Tân thủ
        if (idNpc == NpcConst.TRUONG_LANG && qd.getBeginnerQuestId() == 0) {
            menu.add(0, "Nhiệm vụ (Tân thủ)");
            return;
        }
        if (idNpc == NpcConst.THIET_BI && qd.getBeginnerQuestId() == 1) {
            menu.add(0, "Nhiệm vụ (Tân thủ)");
            return;
        }
        if (idNpc == NpcConst.PHU_ONG && qd.getBeginnerQuestId() == 2) {
            menu.add(0, "Nhiệm vụ (Tân thủ)");
            return;
        }

        // Nếu đã nhận nhiệm vụ ở NPC này
        if (qd.getDailyQuests().containsKey(idNpc)) {
            menu.add(0, "Nhiệm vụ (Hằng ngày)");
            return;
        }

        // Khả năng nhận nhiệm vụ hằng ngày mới
        if (qd.getBeginnerQuestId() >= 3 && qd.getDailyQuestsGiven() < 3) {
            if (idNpc == NpcConst.PHU_ONG || idNpc == NpcConst.THIET_BI || idNpc == NpcConst.LAM_TUONG_QUAN) {
                menu.add(0, "Nhận Nhiệm vụ");
            }
        }
    }

    public String getNoQuestHint(Player player, byte idNpc) {
        QuestData qd = player.getQuestData();
        if (idNpc == NpcConst.TRUONG_LANG) {
            if (qd.getBeginnerQuestId() > 0) {
                return "Ngươi đã hoàn thành việc ở đây, hãy đến gặp Thiết Bị (Thợ Rèn) để nhận việc mới nhé.";
            }
        }
        if (idNpc == NpcConst.THIET_BI) {
            if (qd.getBeginnerQuestId() > 1) {
                return "Ngươi đã học xong cách rèn đồ, hãy đi tìm Phú Ông.";
            }
        }
        return "Hiện tại không có việc gì cho ngươi.";
    }

    public boolean processQuestMenu(Player player, byte idNpc) throws IOException {
        checkDailyReset(player);
        QuestData qd = player.getQuestData();

        // 1. Xử lý Tân thủ
        if (idNpc == NpcConst.TRUONG_LANG && qd.getBeginnerQuestId() == 0) {
            if (qd.getBeginnerProgress() == 0) {
                qd.setBeginnerProgress(1); // Đánh dấu đã nhận
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Chào con, để trở thành một anh hùng thực thụ, con hãy ra ngoài kia tiêu diệt 100 con Nhím để chứng tỏ bản thân nhé!");
            } else if (qd.getBeginnerProgress() >= 101) {
                // Hoàn thành
                qd.setBeginnerQuestId(1);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(3000);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 0, 10)); // HP nhỏ
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 3, 10)); // MP nhỏ
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Làm tốt lắm! Con đã nhận được 3000 xu và thuốc. Hãy đến gặp Thợ Rèn để lên đồ nhé!");
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
                Service.instance.sendLogOut(player.getSession(), "Nghe nói ngươi vừa giúp trưởng làng. Ta có thể rèn vũ khí cho ngươi, nhưng ngươi phải mang về 3 món trang bị rơi ra từ quái vật trước!");
            } else if (qd.getBeginnerProgress() >= 4) {
                qd.setBeginnerQuestId(2);
                qd.setBeginnerProgress(0);
                ItemEquip weapon = ItemService.instance.createNewItemEquipment(getWeaponLv6(player.getInfo().getClassPlayer()), player.getInfo().getClassPlayer());
                InventoryService.instance.addItemBagEquipment(player, weapon);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 18, 5)); // Pot 100% str
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Đây là vũ khí cấp 6 và thuốc tăng sức mạnh cho ngươi. Hãy đến gặp Phú Ông nhé!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Ngươi mới nhặt được " + (qd.getBeginnerProgress() - 1) + "/3 trang bị. Tiếp tục đi!");
            }
            return true;
        }

        if (idNpc == NpcConst.PHU_ONG && qd.getBeginnerQuestId() == 2) {
            if (player.getInfo().getLevel() >= 11 && qd.getTotalMonstersKilled() >= 500) {
                qd.setBeginnerQuestId(3);
                qd.setBeginnerProgress(0);
                player.getInventory().plusXu(10000);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 11, 1)); // Exp card 50%
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Ha ha, ngươi đã trưởng thành rồi! Đây là 10.000 xu và Thẻ x1.5 EXP. Giờ ngươi đã là một dũng sĩ thực thụ!");
            } else {
                sendQuestInfo(player);
                Service.instance.sendLogOut(player.getSession(), "Để nhận thưởng, ngươi phải đạt cấp 11 và tiêu diệt ít nhất 500 quái vật. Ngươi đang ở cấp " + player.getInfo().getLevel() + " và diệt " + qd.getTotalMonstersKilled() + " quái.");
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
                Service.instance.sendLogOut(player.getSession(), "Ta có một nhiệm vụ cho ngươi:\n" + desc);
                return true;
            }
        }

        return false; // Không thuộc menu nhiệm vụ
    }

    private int getRandomQuestForNpc(byte idNpc) {
        if (idNpc == NpcConst.PHU_ONG) return DAILY_PHU_ONG_MICE;
        if (idNpc == NpcConst.THIET_BI) return DAILY_THO_REN_SELL;
        if (idNpc == NpcConst.LAM_TUONG_QUAN) {
            return Util.nextInt(2) == 0 ? DAILY_TUONG_QUAN_TRAVEL : DAILY_TUONG_QUAN_KILL;
        }
        return -1;
    }

    private int generateTargetForQuest(int questId) {
        return switch (questId) {
            case DAILY_PHU_ONG_MICE -> Util.nextInt(30, 100);
            case DAILY_TUONG_QUAN_TRAVEL -> Util.nextInt(3000, 10000);
            case DAILY_THO_REN_SELL -> 10;
            case DAILY_TUONG_QUAN_KILL -> Util.nextInt(100, 1000);
            default -> 10;
        };
    }

    private String getDailyDesc(int questId, int progress, int target) {
        return switch (questId) {
            case DAILY_PHU_ONG_MICE -> "Tiêu diệt chuột: " + progress + "/" + target;
            case DAILY_TUONG_QUAN_TRAVEL -> "Hành quân: " + progress + "/" + target + " px";
            case DAILY_THO_REN_SELL -> "Bán trang bị (+-2 Lv): " + progress + "/" + target;
            case DAILY_TUONG_QUAN_KILL -> "Trừ hại cho dân (Diệt quái +-2 Lv): " + progress + "/" + target;
            default -> "Nhiệm vụ: " + progress + "/" + target;
        };
    }

    private void giveDailyReward(Player player, int questId, int target) throws IOException {
        switch (questId) {
            case DAILY_PHU_ONG_MICE -> {
                player.getInventory().plusXu(200 * target);
                MapService.instance.onSetXP(player, 250 * target);
            }
            case DAILY_TUONG_QUAN_TRAVEL -> {
                player.getInventory().plusXu(2L * target);
                int hpCount = Util.nextInt(10, 20);
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 1, hpCount)); // med HP
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 4, hpCount)); // med MP
            }
            case DAILY_THO_REN_SELL -> {
                int lv = player.getInfo().getLevel();
                player.getInventory().plusXu((long) 100 * lv * target);
                MapService.instance.onSetXP(player, 200 * lv * target);
            }
            case DAILY_TUONG_QUAN_KILL -> {
                MapService.instance.onSetXP(player, 100 * target);
                player.getInventory().plusLuong(Util.nextInt(10, 20));
                
                // Materials
                int matCount = Util.nextInt(2, 4);
                for (int i=0; i<matCount; i++) {
                    short matId = (short) Util.nextInt(34, 39); // IDs for primary materials level 1-6
                    InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion(matId, 1));
                }
                
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 18, Util.nextInt(1, 3))); // 100% str
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 1, 20)); // med HP
                InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 4, 20)); // med MP
            }
        }
    }

    // --- Triggers ---

    public void onKillMonster(Player player, short mobId, byte mobLevel) {
        QuestData qd = player.getQuestData();
        qd.setTotalMonstersKilled(qd.getTotalMonstersKilled() + 1);

        // Tân thủ 1: Diệt nhím (id = 1 trong bảng monsters)
        if (qd.getBeginnerQuestId() == 0 && qd.getBeginnerProgress() > 0 && qd.getBeginnerProgress() <= 100) {
            if (mobId == 1) { // 1 = Nhím
                qd.setBeginnerProgress(qd.getBeginnerProgress() + 1);
                sendQuestInfo(player);
                try {
                    int count = qd.getBeginnerProgress() - 1;
                    if (count >= 100) {
                        ChatService.instance.sendChatDelay(player, "Nhiệm vụ Nhím: 100/100 (Hoàn thành! Hãy về gặp Trưởng Làng)");
                    } else {
                        ChatService.instance.sendChatDelay(player, "Nhiệm vụ Nhím: " + count + "/100");
                    }
                } catch (Exception ignored) {}
            }
        }

        // Tân thủ 3: Đạt cấp 11 và diệt 500 quái
        if (qd.getBeginnerQuestId() == 2) {
            int kills = qd.getTotalMonstersKilled();
            if (kills <= 500 && (kills % 25 == 0 || kills == 500)) {
                sendQuestInfo(player);
                try {
                    if (kills == 500) {
                        ChatService.instance.sendChatDelay(player, "Đã tiêu diệt 500 quái! Đạt cấp 11 và gặp Phú Ông.");
                    }
                } catch (Exception ignored) {}
            }
        }

        // Daily 1: Diệt chuột (id = 9 trong bảng monsters)
        if (qd.getDailyQuests().containsValue(DAILY_PHU_ONG_MICE)) {
            if (mobId == 9) { // 9 = Chuột cống
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
                            } else {
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
                            } else {
                                ChatService.instance.sendChatDelay(player, "Nhiệm vụ Diệt quái: " + count + "/" + target);
                            }
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
    }

    public void onPickUpEquipment(Player player) {
        QuestData qd = player.getQuestData();
        // Tân thủ 2: Nhặt đồ
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

            // 1. Tân thủ quest (slot 0 -> class_abj.aV)
            if (qd.getBeginnerQuestId() == 0) {
                int progress = qd.getBeginnerProgress();
                String desc;
                if (progress == 0) {
                    desc = "Đến gặp Trưởng Làng để nhận việc";
                } else if (progress <= 100) {
                    desc = "Tiêu diệt 100 Nhím ngoài làng|Tiến độ: " + (progress - 1) + "/100";
                } else {
                    desc = "Tiêu diệt 100 Nhím ngoài làng|Tiến độ: 100/100 (Về gặp Trưởng Làng)";
                }
                list.add(new QuestEntry(
                    (short) 1,
                    (byte) 0,
                    "Nhiệm vụ: Tân thủ (1/3)",
                    (byte) -1,
                    desc,
                    "Thưởng: 3000 xu, 10 HP nhỏ, 10 MP nhỏ"
                ));
            } else if (qd.getBeginnerQuestId() == 1) {
                int progress = qd.getBeginnerProgress();
                String desc;
                if (progress == 0) {
                    desc = "Đến gặp Thợ Rèn (Thiết Bị) để nhận việc";
                } else if (progress <= 3) {
                    desc = "Thu thập trang bị rơi từ quái|Tiến độ: " + (progress - 1) + "/3";
                } else {
                    desc = "Thu thập trang bị rơi từ quái|Tiến độ: 3/3 (Về gặp Thợ Rèn)";
                }
                list.add(new QuestEntry(
                    (short) 2,
                    (byte) 0,
                    "Nhiệm vụ: Tân thủ (2/3)",
                    (byte) -1,
                    desc,
                    "Thưởng: Vũ khí cấp 6, 5 bình Str 100%"
                ));
            } else if (qd.getBeginnerQuestId() == 2) {
                int lv = player.getInfo().getLevel();
                int kills = qd.getTotalMonstersKilled();
                boolean done = (lv >= 11 && kills >= 500);
                String desc = done ? "Đạt cấp 11 và diệt 500 quái|(Đã xong, hãy đến gặp Phú Ông)" :
                                     "Đạt cấp 11 (hiện: " + lv + "/11)|Diệt 500 quái (hiện: " + Math.min(kills, 500) + "/500)";
                list.add(new QuestEntry(
                    (short) 3,
                    (byte) 0,
                    "Nhiệm vụ: Tân thủ (3/3)",
                    (byte) -1,
                    desc,
                    "Thưởng: 10.000 xu, 1 Thẻ x1.5 EXP"
                ));
            }

            // 2. Daily quests (slot 2 -> class_abj.aX)
            if (!qd.getDailyQuests().isEmpty()) {
                for (var entry : qd.getDailyQuests().entrySet()) {
                    byte npcId = entry.getKey();
                    int questId = entry.getValue();
                    int progress = qd.getDailyProgress().getOrDefault(npcId, 0);
                    int target = qd.getDailyTargets().getOrDefault(npcId, 1);
                    String desc = getDailyDesc(questId, Math.min(progress, target), target);
                    if (progress >= target) {
                        desc += "|(Đã xong, hãy về trả nhiệm vụ)";
                    }
                    list.add(new QuestEntry(
                        (short) (100 + questId),
                        (byte) 2,
                        "Nhiệm vụ: Hằng ngày",
                        (byte) -1,
                        desc,
                        "Thưởng: Xu, EXP và vật phẩm"
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
                msg.writer().writeByte(qe.npcId);
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
