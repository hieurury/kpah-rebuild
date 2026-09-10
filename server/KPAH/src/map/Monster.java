package map;

import item.ItemMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Builder;
import template.MonsterTemplate;
import lombok.Data;
import lombok.NonNull;
import lombok.Synchronized;
import manager.ClientManager;
import manager.Manager;
import manager.Settings;
import player.Player;
import services.BuffService;
import services.ItemService;
import services.MapService;
import services.MonsterService;
import services.Service;
import skill.BuffInfluenceMonster;
import consts.Const;
import utils.Util;

@Builder
@Data
public class Monster implements Cloneable {

    private short id;
    private short x, y;
    private int hp;
    private Zone zone;
    private MonsterTemplate template;
    private List<Player> playerAttack;
    private BuffInfluenceMonster buffInfluence;
    private long lastTimeDie;
    private long lastTimeAttackPlayer;
    private Player playerTarget;
    @Builder.Default
    private short startX = -1;
    @Builder.Default
    private short startY = -1;
    @Builder.Default
    private long lastTimeMove = System.currentTimeMillis();
    @Builder.Default
    private boolean isAttacking = false;
    @Builder.Default
    private long nextMoveDelay = 3000;
    @Builder.Default
    private long nextAttackDelay = 2000;
    @Builder.Default
    private boolean isElite = false;
    @Builder.Default
    private long lastTimeBeingAttacked = 0;
    @Builder.Default
    private long lastTimeRegenHp = 0;

    public int getMaxHp() {
        if (template == null) {
            return 100;
        }
        return isElite ? (int) (template.getMaxHp() * 8.0) : template.getMaxHp();
    }

    @Synchronized
    public void healHp(int amount) throws IOException {
        if (isDie() || amount <= 0) {
            return;
        }
        this.hp = Math.min(getMaxHp(), this.hp + amount);
        MonsterService.instance.sendMonsterInfoToMap(this);
    }

    public void rollElite() {
        if (!isKhoangSan() && !playerCanNotAttack() && !canNotAttackPlayer()) {
            this.isElite = Util.isTrue(0.5, 100.0); // Tỷ lệ xuất hiện tinh anh trả về 0.5%
            if (this.isElite) {
                this.hp = getMaxHp();
            }
        } else {
            this.isElite = false;
        }
    }


    public boolean isDie() {
        return this.hp <= (isKhoangSan() ? 10 : 0);
    }

    public boolean playerCanNotAttack() {
        return template.getId() >= 36 && template.getId() <= 46;
    }

    public boolean canNotAttackPlayer() {
        return (template.getId() >= 85 && template.getId() <= 89) || (template.getId() >= 36 && template.getId() <= 46);
    }

    public boolean isKhoangSan() {
        return template.getId() >= 85 && template.getId() <= 89;
    }

    public boolean isNormalMonster() {
        if (isElite) {
            return false;
        }
        if (template == null) {
            return false;
        }
        if (isKhoangSan() || canNotAttackPlayer() || playerCanNotAttack()) {
            return false;
        }
        // Boss và quái đặc biệt (level 999 hoặc tên chứa boss)
        if (template.getLevel() >= 90 || template.getId() >= 113 || (template.getName() != null && template.getName().toLowerCase().contains("boss"))) {
            return false;
        }
        return true;
    }

    private boolean isPlayerAttackable(@NonNull Player player) {
        return !player.getSundry().isNewlyRevived() && !player.isDie() && ClientManager.containsPlayers(player) && player.getLocation().getZone().equals(this.zone) && Util.getDistance(player, this) <= Settings.DISTANCE_MOB_CAN_ATTACK;
    }

    public int injured(@NonNull Player plAtt, int damage, boolean isXuyenGiap, boolean isInjuredByEffect, boolean x2) throws IOException {
        if (!this.isDie()) {
            this.lastTimeBeingAttacked = System.currentTimeMillis(); // Cập nhật thời điểm bị đánh
            if (!isKhoangSan()) {
                if (!isXuyenGiap) {
                    int level = this.template.getLevel();
                    // 1. Giáp phòng thủ phẳng theo level quái
                    int mobDef = isElite ? level * 5 : level * 2;
                    damage -= mobDef;
                    
                    // 2. Kháng sát thương theo % (damage mitigation)
                    // Quái tinh anh kháng 40% - 60% sát thương (người chơi yếu đánh gần như không thấm)
                    int resistPercent = isElite ? Math.min(60, 35 + (int) (level * 0.6)) : Math.min(25, (int) (level * 0.7));
                    damage -= damage * resistPercent / 100;
                } else if (isElite) {
                    // Kể cả bị xuyên giáp, quái tinh anh vẫn triệt tiêu 25% sát thương
                    damage -= damage * 25 / 100;
                }
            }
            if (damage <= 0) {
                damage = 1;
            }
            if (isInjuredByEffect && this.buffInfluence.isPoisoned()) {
                if (hp - damage < 1) {
                    damage = hp - 1;
                }
            }
            if (!isKhoangSan()) {
                BuffService.instance.onMobInjured(plAtt, this);
            }
            if (x2) {
                this.minusHp(plAtt, damage);
            }
            this.minusHp(plAtt, damage);
        }
        return damage;
    }

    @Synchronized
    public void minusHp(Player plAtt, int damage) throws IOException {
        if (isDie()) {
            return;
        }
        this.hp -= damage;
        this.addPlayerAttack(plAtt);
        if (!isKhoangSan()) {
            calculatePowerPlus(plAtt, damage);
        }
        if (this.isDie()) {
            if (this.template.getLevel() - plAtt.getInfo().getLevel() >= 10) {
                plAtt.getInfo().minusKiller((byte) 1);
                if (plAtt.getInfo().getKiller() <= 0) {
                    plAtt.getSundry().setKiller(false);
                }
                MapService.instance.sendKiller(plAtt);
            }
            MonsterService.instance.onMonsterDropItem(this, plAtt);
            this.lastTimeDie = System.currentTimeMillis();
            this.playerTarget = null;
            this.playerAttack.clear();
            buffInfluence.clearBuff();
        }
    }

    @Synchronized
    private void getPlayerCanAttack() throws IOException {
        if (playerTarget != null) {
            if (!isPlayerAttackable(playerTarget)) {
                playerTarget = null;
                MonsterService.instance.sendMonsterEndAttack(this);
            }
        } else {
            Iterator<Player> iterator = playerAttack.iterator();
            int nearestAggroDist = Integer.MAX_VALUE;
            Player nearestAggro = null;
            while (iterator.hasNext()) {
                Player pl = iterator.next();
                if (pl == null || !isPlayerAttackable(pl)) {
                    iterator.remove();
                } else {
                    int dist = Util.getDistance(this, pl);
                    if (dist < nearestAggroDist) {
                        nearestAggroDist = dist;
                        nearestAggro = pl;
                    }
                }
            }
            if (nearestAggro != null) {
                playerTarget = nearestAggro;
            } else if (zone != null) {
                int nearestDist = Integer.MAX_VALUE;
                Player nearestPlayer = null;
                synchronized (zone) {
                    for (Player pl : zone.getPlayers()) {
                        if (pl != null && !pl.isDie() && isPlayerAttackable(pl)) {
                            int dist = Util.getDistance(this, pl);
                            if (dist < nearestDist) {
                                nearestDist = dist;
                                nearestPlayer = pl;
                            }
                        }
                    }
                }
                if (nearestPlayer != null) {
                    playerTarget = nearestPlayer;
                }
            }
        }
    }

    @Synchronized
    private void addPlayerAttack(@NonNull Player pl) {
        if (playerAttack.contains(pl)) {
            return;
        }
        if (playerAttack.size() > 10) {
            Iterator<Player> iterator = playerAttack.iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
        playerAttack.add(pl);
    }

    public int getDameAttack(Player pl) {
        int mobLv = this.template.getLevel();
        int plDef = (pl != null && pl.getPoint() != null) ? pl.getPoint().getDefend() : 0;

        // 1. Sát thương cơ bản tự nhiên của quái theo level
        int minAtk = Math.max(16, mobLv * 11 + 5);
        int maxAtk = Math.max(24, mobLv * 14 + 15);
        int baseAtk = Util.nextInt(minAtk, maxAtk);

        // 2. Bonus cận chiến hoặc tinh anh
        if (isMelee()) {
            baseAtk = (int) (baseAtk * 1.1); // Cận chiến +10%
        }
        if (isElite) {
            // Quái tinh anh tăng mạnh sát thương +80% (người chơi trang bị kém sẽ chịu không nổi)
            baseAtk = (int) (baseAtk * 1.8);
            // Trạng thái Cuồng Nộ (Frenzy): dưới 50% HP tăng thêm 25% sát thương
            if (this.hp < getMaxHp() / 2) {
                baseAtk = (int) (baseAtk * 1.25);
            }
            // 25% tỷ lệ Bạo Kích (Critical Hit) của Tinh Anh: x1.5 sát thương
            if (Util.isTrue(25.0, 100.0)) {
                baseAtk = (int) (baseAtk * 1.5);
            }
        }

        // 3. Sát thương cào xước tối thiểu (min scratch damage) theo level quái
        // Khi giáp người chơi rất cao, quái vẫn gây ra lượng sát thương nhỏ hợp lý (không bị về 1 dame vô lý)
        int minScratch = Math.max(3, (int) (mobLv * 1.5 + 2));
        if (isElite) {
            minScratch = Math.max(25, (int) (mobLv * 3.5 + 15));
        }

        int netDmg = Math.max(baseAtk - plDef, minScratch);

        // 4. Cơ chế khoảng cách level cho quái thường:
        // Cứ cách 1 lv (người chơi cao hơn quái) thì quái bị giảm 20% dame lên người chơi, tối đa 80%.
        // Không áp dụng cho quái tinh anh, cao cấp và các loại boss.
        if (isNormalMonster() && pl != null && pl.getInfo() != null) {
            int playerLevel = pl.getInfo().getLevel();
            int diffLevel = playerLevel - mobLv;
            if (diffLevel > 0) {
                int dmgReductionPercent = Math.min(80, diffLevel * 20);
                netDmg = Math.max(1, (int) (netDmg * (100 - dmgReductionPercent) / 100.0));
            }
        }

        // 5. Đảm bảo người chơi nhận sát thương hợp lý khi trừ giáp trong Player.injured()
        int finalDmg = plDef + netDmg;

        return Math.max(1, finalDmg);
    }

    @Synchronized
    public void calculatePowerPlus(@NonNull Player pl, int damage) throws IOException {
        // Tăng base EXP của quái vật theo yêu cầu để người chơi up level thoải mái
        int level = this.template.getLevel();
        double baseExp;
        if (level <= 5) {
            baseExp = level * 160.0;
        } else if (level <= 10) {
            baseExp = level * 260.0;
        } else if (level <= 15) {
            baseExp = level * level * 45.0;
        } else if (level <= 20) {
            baseExp = level * level * 65.0;
        } else if (level <= 27) {
            baseExp = level * level * 85.0;
        } else {
            baseExp = level * level * 110.0;
        }
        if (baseExp <= 0) {
            baseExp = 160.0;
        }
        
        // Tránh damage quá lớn vượt quá máu tối đa làm sai lệch
        double effectiveDamage = Math.min(damage, this.getMaxHp());
        double percentage = effectiveDamage / (double) this.getMaxHp();
        
        int tnPl = (int) (baseExp * percentage);
        if (tnPl <= 0) {
            tnPl = 1;
        }

        // Quái tinh anh cho kinh nghiệm khổng lồ gấp 60 lần
        if (isElite) {
            tnPl *= 60;
        }

        // Cơ chế khoảng cách level cho quái thường:
        // Cứ cách 1 lv (người chơi cao hơn quái) thì kinh nghiệm quái cho người chơi giảm đi 10%, tối đa 50%.
        // Không áp dụng cho quái tinh anh, cao cấp và các loại boss.
        if (isNormalMonster() && pl != null && pl.getInfo() != null) {
            int playerLevel = pl.getInfo().getLevel();
            int diffLevel = playerLevel - level;
            if (diffLevel > 0) {
                int expReductionPercent = Math.min(50, diffLevel * 10);
                tnPl = Math.max(1, (int) (tnPl * (100 - expReductionPercent) / 100.0));
            }
        }

        // Áp dụng % thưởng từ người chơi (thú cưỡi, sự kiện, item, etc.)
        tnPl += (tnPl * pl.getPoint().getExpDonate() / 100);
        
        tnPl *= Settings.EXP_DONATE; // Bonus chung của server

        pl.getPoint().plusExp(tnPl);
        
        // Chia exp cho party (nếu có)
        if (!pl.getParty().isEmpty()) {
            int expParty = Math.max(1, tnPl * Settings.PERCENT_EXP_PARTY / 100);
            for (Player member : pl.getParty().getMembers()) {
                if (member.getIdPlayer() == pl.getIdPlayer() || !member.getLocation().getZone().equals(pl.getLocation().getZone())) {
                    continue;
                }
                member.getPoint().plusExp(expParty);
                MapService.instance.onSetXP(member, expParty);
                MapService.instance.checkLevelUp(member);
            }
        }
        
        // Level up
        MapService.instance.checkLevelUp(pl);
        
        // Hiển thị +EXP cho client
        MapService.instance.onSetXP(pl, tnPl);
    }

    @Synchronized
    public List<ItemMap> getItemDrop(@NonNull Player plAttack) {
        List<ItemMap> its = new ArrayList<>();
        
        // Calculate a drop location closer to the player (midpoint + random scatter)
        short destX = (short) (x + (plAttack.getLocation().getX() - x) / 2 + Util.nextInt(-20, 20));
        short destY = (short) (y + (plAttack.getLocation().getY() - y) / 2 + Util.nextInt(-20, 20));

        int level = this.template.getLevel();
        // Quái tinh anh giữ tỷ lệ rớt đồ cao như cũ (x2.5), số lượng vật phẩm điều tiết theo cấp quái (trần lv35)
        double lvRatio = Math.min(1.0, (double) Math.max(1, level) / 35.0);
        double rateMultiplier = isElite ? 2.5 : 1.0; // Tỷ lệ rớt đồ giữ nguyên x2.5

        // 1. Potion drop (25% cho lv <= 15, 30% cho lv 16-35)
        double potionRate = Math.min(100.0, (level <= 15 ? 25.0 : 30.0) * rateMultiplier);
        if (Util.isTrue(potionRate, 100.0)) {
            short idItemPotion;
            short quantity;
            if (isElite) {
                // Trần lv35: 2 - 4 bình. Cấp thấp hơn giảm dần về 1 - 2 bình
                int minPot = 1 + (Util.isTrue(lvRatio * 100.0, 100.0) ? 1 : 0);
                int maxPot = 2 + (int) Math.round(2.0 * lvRatio);
                if (minPot > maxPot) minPot = maxPot;
                quantity = (short) Util.nextInt(minPot, maxPot);
            } else {
                quantity = (short) Util.nextInt(1, 2);
            }
            if (level <= 15) {
                // Quái lv 1-15: 60% HP nhỏ (1), 40% MP nhỏ (4)
                idItemPotion = Util.isTrue(60, 100) ? (short) 1 : (short) 4;
            } else if (level <= 25) {
                // Quái lv 16-25: 60% HP vừa (2), 40% MP vừa (5)
                idItemPotion = Util.isTrue(60, 100) ? (short) 2 : (short) 5;
            } else {
                // Quái lv 26-35: 40% HP to (3), 30% HP vừa (2), 20% MP to (6), 10% MP vừa (5)
                int rand = Util.nextInt(1, 100);
                if (rand <= 40) {
                    idItemPotion = (short) 3; // HP to
                } else if (rand <= 70) {
                    idItemPotion = (short) 2; // HP vừa
                } else if (rand <= 90) {
                    idItemPotion = (short) 6; // MP to
                } else {
                    idItemPotion = (short) 5; // MP vừa
                }
            }
            its.add(ItemService.instance.createNewItemMap(idItemPotion, quantity, Const.CATEGORY_POTION, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
        }
        
        // 2. Gold drop (15% cho lv <= 15, 20% cho lv 16-35)
        double goldRate = Math.min(100.0, (level <= 15 ? 15.0 : 20.0) * rateMultiplier);
        if (Util.isTrue(goldRate, 100.0)) {
            short quantity;
            if (level <= 15) {
                quantity = (short) Util.nextInt(level * 30, level * 80);
            } else {
                quantity = (short) Util.nextInt(level * 80, level * 220);
            }
            if (quantity <= 0) quantity = 50;
            // Trần lv35: gấp 2 lần vàng, cấp thấp hơn giảm dần theo cấp quái
            double goldEliteMultiplier = isElite ? (1.0 + 1.0 * lvRatio) : 1.0;
            quantity = (short) Math.max(10, Math.round(quantity * goldEliteMultiplier));
            its.add(ItemService.instance.createNewItemMap((short) 0, quantity, Const.CATEGORY_POTION, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
        }
        
        // 3. Equipment drop (mặc định 2.0%, quái tinh anh KHÔNG rơi trang bị thường trên đất - phần thưởng tập trung vào rương tinh anh)
        if (!isElite) {
            double equipRate = 2.0;
            if (Util.isTrue(equipRate, 100.0)) {
                byte maxLevelEquip = (byte) level;
                short idItemEquipment = Manager.randomItemEquipment(maxLevelEquip, (byte) Util.getOne(plAttack.getInfo().getGender(), 0));
                if (idItemEquipment != -1) {
                    its.add(ItemService.instance.createNewItemMap(idItemEquipment, (short) 1, Const.CATEGORY_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                }
            }
        }

        // 4. Gems & Materials drop (Lv 10-15: 2%; Lv 16-25: 4%; Lv 26-35: 5%)
        if (level >= 10 && level <= 35) {
            double gemRate = Math.min(100.0, (level <= 15 ? 2.0 : (level <= 25 ? 4.0 : 5.0)) * rateMultiplier);
            if (Util.isTrue(gemRate, 100.0)) {
                // Trần lv35: 2 viên; cấp càng thấp giảm dần về 1 viên
                short gemQty = 1;
                if (isElite) {
                    double bonusGemRate = Math.min(100.0, ((double) (level - 10) / 25.0) * 100.0);
                    gemQty = (short) (1 + (Util.isTrue(bonusGemRate, 100.0) ? 1 : 0));
                }
                if (level <= 25) {
                    // Lv 10-25: 50% Đá may mắn cấp 1 (5), 50% Luyện kim dược (8)
                    short gemId = Util.isTrue(50, 100) ? (short) 5 : (short) 8;
                    its.add(ItemService.instance.createNewItemMap(gemId, gemQty, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                } else {
                    // Lv 26-35: 40% Đá may mắn cấp 1 (5), 40% Luyện kim dược (8), 15% Đá may mắn cấp 2 (6), 5% Vé quay số (69)
                    int rand = Util.nextInt(1, 100);
                    if (rand <= 40) {
                        its.add(ItemService.instance.createNewItemMap((short) 5, gemQty, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                    } else if (rand <= 80) {
                        its.add(ItemService.instance.createNewItemMap((short) 8, gemQty, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                    } else if (rand <= 95) {
                        its.add(ItemService.instance.createNewItemMap((short) 6, gemQty, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                    } else {
                        its.add(ItemService.instance.createNewItemMap((short) 69, gemQty, Const.CATEGORY_POTION, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                    }
                }
            }
        }

        // 5. Rương tinh anh: Số lượng rương tùy theo cấp quái tinh anh
        if (isElite) {
            short idChest;
            short chestQty;
            if (level <= 9) {
                idChest = 106; // Rương Tinh Anh (Bậc 1)
                chestQty = 1;
            } else if (level <= 19) {
                idChest = 160; // Rương Tinh Anh (Bậc 2)
                chestQty = (short) Util.nextInt(1, 2);
            } else if (level <= 29) {
                idChest = 161; // Rương Tinh Anh (Bậc 3)
                chestQty = (short) Util.nextInt(2, 3);
            } else {
                idChest = 162; // Rương Tinh Anh (Bậc 4)
                chestQty = (short) Util.nextInt(3, 4);
            }
            its.add(ItemService.instance.createNewItemMap(idChest, chestQty, Const.CATEGORY_POTION, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));

            // 6. Bình kinh nghiệm: 100% rơi từ quái tinh anh theo bậc
            short idPotionExp;
            if (level <= 9) {
                idPotionExp = 108; // Sơ cấp: 35.000 EXP
            } else if (level <= 19) {
                idPotionExp = 109; // Trung cấp: 250.000 EXP
            } else if (level <= 29) {
                idPotionExp = 110; // Cao cấp: 900.000 EXP
            } else {
                idPotionExp = 111; // Siêu cấp: 2.200.000 EXP
            }
            short expQty = (short) (level <= 19 ? 1 : Util.nextInt(1, 2));
            its.add(ItemService.instance.createNewItemMap(idPotionExp, expQty, Const.CATEGORY_POTION, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
        }
        
        if (isKhoangSan()) {
            switch (template.getId()) {
                case 85 ->
                    its.add(ItemService.instance.createNewItemMap((short) 81, (short) 1, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                case 86 ->
                    its.add(ItemService.instance.createNewItemMap((short) 67, (short) 1, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                case 87 ->
                    its.add(ItemService.instance.createNewItemMap((short) 88, (short) 1, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                case 88 ->
                    its.add(ItemService.instance.createNewItemMap((short) 95, (short) 1, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
                case 89 ->
                    its.add(ItemService.instance.createNewItemMap((short) 74, (short) 1, Const.CATEGORY_GEM_ITEM, scatterX(destX), scatterY(destY), plAttack.getIdPlayer(), zone));
            }
        }
        return its;
    }

    private short scatterX(short baseX) {
        return (short) (baseX + Util.nextInt(-12, 12));
    }

    private short scatterY(short baseY) {
        return (short) (baseY + Util.nextInt(-12, 12));
    }

    public boolean isMelee() {
        // Dummy logic to separate Melee vs Ranged for demonstration. 
        // e.g., Even IDs are Melee (Dash in), Odd IDs are Ranged (Stand and shoot).
        return template.getId() % 2 == 0; 
    }

    private void attackPlayer() throws IOException {
        if (!isDie() && !this.buffInfluence.isStunned() && Util.canDoWithTime(lastTimeAttackPlayer, nextAttackDelay)) {
            this.lastTimeAttackPlayer = System.currentTimeMillis();
            
            // Randomize next attack delay based on monster type
            if (isElite) {
                // Cuồng Nộ (Frenzy) khi máu < 50%: tốc độ đánh điên cuồng 800-1200ms, bình thường 1200-1800ms
                this.nextAttackDelay = (this.hp < getMaxHp() / 2) ? Util.nextInt(800, 1200) : Util.nextInt(1200, 1800);
            } else if (isMelee()) {
                this.nextAttackDelay = Util.nextInt(1800, 3200);
            } else {
                this.nextAttackDelay = Util.nextInt(2500, 4500);
            }
            
            getPlayerCanAttack();
            if (playerTarget != null && !isAttacking) {
                isAttacking = true;
                final int attackDistance = isMelee() ? 20 : 120;
                
                Thread.startVirtualThread(() -> {
                    try {
                        // Calculate position to move to (distance from target)
                        int targetPx = playerTarget.getLocation().getX();
                        int targetPy = playerTarget.getLocation().getY();
                        
                        double angle = Math.atan2(this.y - targetPy, this.x - targetPx);
                        short targetX = (short) (targetPx + attackDistance * Math.cos(angle));
                        short targetY = (short) (targetPy + attackDistance * Math.sin(angle));
                        
                        // Cập nhật vị trí và gửi packet di chuyển
                        if (this.zone != null && this.zone.getMap() != null && this.zone.getMap().getMapData().isWalkable(targetX, targetY)) {
                            this.x = targetX;
                            this.y = targetY;
                            MonsterService.instance.sendMonsterMove(this);
                        }
                        
                        // Đợi một khoảng nhỏ để animation di chuyển (nếu có)
                        Thread.sleep(250);
                        
                        // Danh sách mục tiêu: luôn có primaryTarget, nếu là Tinh Anh thì tấn công tối đa 3 người chơi cùng lúc
                        Player primaryTarget = playerTarget;
                        if (primaryTarget == null || primaryTarget.isDie()) {
                            return;
                        }
                        List<Player> targetList = new ArrayList<>();
                        targetList.add(primaryTarget);
                        if (isElite && zone != null && zone.getPlayers() != null) {
                            for (Player otherPl : zone.getPlayers()) {
                                if (targetList.size() >= 3) {
                                    break;
                                }
                                if (otherPl != null && otherPl.getIdPlayer() != primaryTarget.getIdPlayer() && !otherPl.isDie() && isPlayerAttackable(otherPl)) {
                                    targetList.add(otherPl);
                                }
                            }
                        }

                        for (Player target : targetList) {
                            if (target != null && !target.isDie()) {
                                int damageDealt = 0;
                                if (isMelee()) {
                                    damageDealt = MonsterService.instance.sendMeleeHit(Monster.this, target);
                                } else {
                                    damageDealt = MonsterService.instance.sendMonsterAttack(Monster.this, target);
                                }
                                // Kỹ năng đặc biệt của Quái Tinh Anh
                                if (isElite) {
                                    // 1. Hút máu (Lifesteal): hồi 25% sát thương gây ra
                                    if (damageDealt > 0 && !Monster.this.isDie()) {
                                        int healAmount = (int) (damageDealt * 0.25);
                                        if (healAmount > 0) {
                                            healHp(healAmount);
                                        }
                                    }
                                    // 2. Kỹ năng khống chế & thiêu đốt
                                    int randSkill = Util.nextInt(1, 100);
                                    if (randSkill <= 25) {
                                        // 25% Gây Choáng 2s (Stun)
                                        target.getBuffInfluence().addBuffStunned((short) 2);
                                    } else if (randSkill <= 55) {
                                        // 30% Gây Trúng Độc Quái Tinh Anh (2-5% HP tối đa mỗi giây, duy trì 10s)
                                        int mobLv = template.getLevel();
                                        int percentHp = 2;
                                        if (mobLv > 60) {
                                            percentHp = 5;
                                        } else if (mobLv > 40) {
                                            percentHp = 4;
                                        } else if (mobLv > 20) {
                                            percentHp = 3;
                                        }
                                        target.getBuffInfluence().addBuffPoisoned((short) 10, percentHp, mobLv * 2);
                                    } else if (randSkill <= 75) {
                                        // 20% Thiêu đốt (Burn: trừ trực tiếp MP người chơi khiến khó dùng skill)
                                        if (target.getPoint() != null) {
                                            int burnMp = Math.max(50, template.getLevel() * 15);
                                            target.getPoint().minusMp(burnMp);
                                        }
                                    }
                                }
                            }
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        isAttacking = false;
                    }
                });
            }
        }
    }



    public void update() throws IOException {
        if (this.isDie() && Util.canDoWithTime(lastTimeDie, Settings.TIME_LIVE_MOB)) {
            rollElite();
            this.hp = getMaxHp();
            if (this.startX != -1) {
                this.x = this.startX;
                this.y = this.startY;
            }
            // Gui truc tiep MONSTER_INFO cap nhat cho tat ca player trong tam nhin
            // de client cap nhat dung trang thai tinh anh / quai thuong (ke ca khi player dung im auto)
            if (this.zone != null) {
                for (player.Player pl : this.zone.getPlayers()) {
                    if (pl != null && pl.getSession() != null && !pl.isDie()) {
                        if (Util.getDistance(pl, this) < pl.getSession().getDistanceLoad()) {
                            pl.getOtherMobInside().add(this.id);
                            MonsterService.instance.sendMonsterInfo(pl, this.id);
                        } else {
                            pl.getOtherMobInside().removeIf(m -> m == this.id);
                        }
                    }
                }
            }
            // Gui packet move de client biet quai xuat hien lai
            MonsterService.instance.sendMonsterMove(this);
        }

        buffInfluence.update();
        if (!canNotAttackPlayer()) {
            attackPlayer();
        }

        // Cơ chế tự hồi phục khi không bị tấn công (Out-of-Combat HP Regen) của Quái Tinh Anh
        if (isElite && !isDie() && this.hp < getMaxHp()) {
            long now = System.currentTimeMillis();
            // Nếu không bị người chơi tấn công trong 5 giây
            if (now - lastTimeBeingAttacked >= 5000L) {
                // Cứ mỗi 1.5 giây hồi phục 8% HP tối đa
                if (Util.canDoWithTime(lastTimeRegenHp, 1500L)) {
                    this.lastTimeRegenHp = now;
                    int regenAmount = Math.max(15, (int) (getMaxHp() * 0.08));
                    healHp(regenAmount);
                }
            }
        }
        
        // Wandering logic
        // "tuy nhiên trong lúc đó logic di chuyển vẫn kích hoạt" - timer vẫn đếm và kiểm tra
        if (!this.isDie() && !this.buffInfluence.isStunned() && playerTarget == null) {
            if (Util.canDoWithTime(lastTimeMove, nextMoveDelay)) {
                lastTimeMove = System.currentTimeMillis();
                nextMoveDelay = Util.nextInt(3000, 5000); // Random delay 3-5s
                
                // Chỉ di chuyển nếu không đang bận tấn công (ưu tiên tấn công)
                if (!isAttacking) {
                    if (startX == -1) {
                        startX = x;
                        startY = y;
                    }
                    int dir = Util.nextInt(4);
                    short newX = this.x;
                    short newY = this.y;
                    int dist = Util.nextInt(30, 50); // Khoảng cách 30px - 50px
                    
                    if (dir == 0) newY -= dist;
                    else if (dir == 1) newY += dist;
                    else if (dir == 2) newX -= dist;
                    else if (dir == 3) newX += dist;
                    
                    if (Math.abs(newX - startX) < 150 && Math.abs(newY - startY) < 150 && newX > 10 && newY > 10) {
                        if (this.zone != null && this.zone.getMap() != null && this.zone.getMap().getMapData().isWalkable(newX, newY)) {
                            this.x = newX;
                            this.y = newY;
                            MonsterService.instance.sendMonsterMove(this);
                        }
                    }
                }
            }
        }
    }
}
