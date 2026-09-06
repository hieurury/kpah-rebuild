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

    private boolean isPlayerAttackable(@NonNull Player player) {
        return !player.getSundry().isNewlyRevived() && !player.isDie() && ClientManager.containsPlayers(player) && player.getLocation().getZone().equals(this.zone) && Util.getDistance(player, this) <= Settings.DISTANCE_MOB_CAN_ATTACK;
    }

    public int injured(@NonNull Player plAtt, int damage, boolean isXuyenGiap, boolean isInjuredByEffect, boolean x2) throws IOException {
        if (!this.isDie()) {
            if (!isKhoangSan()) {
                if (!isXuyenGiap) {
                    int level = this.template.getLevel();
                    // 1. Giáp phòng thủ phẳng theo level quái
                    int mobDef = level * 4;
                    damage -= mobDef;
                    
                    // 2. Kháng sát thương theo % (damage mitigation) để quái trâu hơn, chống dồn dame one-shot từ trang bị OP
                    int resistPercent = Math.min(45, (int) (level * 1.2));
                    damage -= damage * resistPercent / 100;
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
            while (iterator.hasNext()) {
                Player pl = iterator.next();
                if (pl == null || !isPlayerAttackable(pl)) {
                    iterator.remove();
                } else {
                    playerTarget = pl;
                }
            }
            for (Player pl : zone.getPlayers()) {
                if (!pl.isDie() && isPlayerAttackable(pl)) {
                    playerTarget = pl;
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
        int level = this.template.getLevel();
        // Cân bằng sát thương tương thích với chỉ số phòng thủ và HP người chơi từ lv 1 - 35
        int minAtk = Math.max(12, 10 * level - 15);
        int maxAtk = Math.max(20, 14 * level - 5);
        int dameAtt = Util.nextInt(minAtk, maxAtk);
        if (isMelee()) {
            dameAtt = (int) (dameAtt * 1.15); // Cận chiến 15% bonus dame
        }
        if (dameAtt <= 0) {
            dameAtt = 1;
        }
        return dameAtt;
    }

    @Synchronized
    public void calculatePowerPlus(@NonNull Player pl, int damage) throws IOException {
        // Cố định exp theo level, giảm exp để tránh lên cấp quá nhanh, giữ chuẩn nhịp cày cuốc
        int level = this.template.getLevel();
        double baseExp;
        if (level <= 5) {
            baseExp = level * 8.0;
        } else if (level <= 10) {
            baseExp = level * 12.0;
        } else if (level <= 15) {
            baseExp = level * level * 2.5;
        } else if (level <= 20) {
            baseExp = level * level * 4.0;
        } else if (level <= 27) {
            baseExp = level * level * 5.0;
        } else {
            baseExp = level * level * 6.0;
        }
        if (baseExp <= 0) {
            baseExp = 5.0;
        }
        
        // Tránh damage quá lớn vượt quá máu tối đa làm sai lệch
        double effectiveDamage = Math.min(damage, this.template.getMaxHp());
        double percentage = effectiveDamage / (double) this.template.getMaxHp();
        
        int tnPl = (int) (baseExp * percentage);
        if (tnPl <= 0) {
            tnPl = 1;
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
            }
        }
        
        // Level up
        boolean isLevelUp = false;
        while (pl.getPoint().getExp() >= Util.getExp(pl.getInfo().getLevel())) {
            pl.getPoint().setExp(pl.getPoint().getExp() - Util.getExp(pl.getInfo().getLevel()));
            pl.getInfo().plusLevel((byte) 1);
            pl.getPoint().plusStrength(1);
            pl.getPoint().plusHealth(1);
            pl.getPoint().plusAgility(1);
            pl.getPoint().plusLuck(1);
            pl.getPoint().plusSpirit(1);
            pl.getPoint().plusSkillPoint(1);
            pl.getPoint().plusBasePoint(5);
            isLevelUp = true;
        }
        if (isLevelUp) {
            pl.getPoint().initPoint();
            MapService.instance.onLevelUp(pl);
            Service.instance.sendMainCharInfo(pl);
        }
        
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

        // 1. Potion drop (25% cho lv <= 15, 30% cho lv 16-35)
        double potionRate = level <= 15 ? 25.0 : 30.0;
        if (Util.isTrue(potionRate, 100.0)) {
            short idItemPotion;
            short quantity = (short) Util.nextInt(1, 2);
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
            its.add(ItemService.instance.createNewItemMap(idItemPotion, quantity, Const.CATEGORY_POTION, destX, destY, plAttack.getIdPlayer(), zone));
        }
        
        // 2. Gold drop (15% cho lv <= 15, 20% cho lv 16-35)
        double goldRate = level <= 15 ? 15.0 : 20.0;
        if (Util.isTrue(goldRate, 100.0)) {
            short quantity;
            if (level <= 15) {
                quantity = (short) Util.nextInt(level * 30, level * 80);
            } else {
                quantity = (short) Util.nextInt(level * 80, level * 220);
            }
            if (quantity <= 0) quantity = 50;
            its.add(ItemService.instance.createNewItemMap((short) 0, quantity, Const.CATEGORY_POTION, destX, destY, plAttack.getIdPlayer(), zone));
        }
        
        // 3. Equipment drop (3% cho lv <= 15, 4% cho lv 16-35)
        double equipRate = level <= 15 ? 3.0 : 4.0;
        if (Util.isTrue(equipRate, 100.0)) {
            byte maxLevelEquip = (byte) level;
            short idItemEquipment = Manager.randomItemEquipment(maxLevelEquip, (byte) Util.getOne(plAttack.getInfo().getGender(), 0));
            if (idItemEquipment != -1) {
                its.add(ItemService.instance.createNewItemMap(idItemEquipment, (short) 1, Const.CATEGORY_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
            }
        }

        // 4. Gems & Materials drop (Lv 10-15: 2%; Lv 16-25: 4%; Lv 26-35: 5%)
        if (level >= 10 && level <= 35) {
            double gemRate = level <= 15 ? 2.0 : (level <= 25 ? 4.0 : 5.0);
            if (Util.isTrue(gemRate, 100.0)) {
                if (level <= 25) {
                    // Lv 10-25: 50% Đá may mắn cấp 1 (5), 50% Luyện kim dược (8)
                    short gemId = Util.isTrue(50, 100) ? (short) 5 : (short) 8;
                    its.add(ItemService.instance.createNewItemMap(gemId, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                } else {
                    // Lv 26-35: 40% Đá may mắn cấp 1 (5), 40% Luyện kim dược (8), 15% Đá may mắn cấp 2 (6), 5% Vé quay số (69)
                    int rand = Util.nextInt(1, 100);
                    if (rand <= 40) {
                        its.add(ItemService.instance.createNewItemMap((short) 5, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                    } else if (rand <= 80) {
                        its.add(ItemService.instance.createNewItemMap((short) 8, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                    } else if (rand <= 95) {
                        its.add(ItemService.instance.createNewItemMap((short) 6, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                    } else {
                        its.add(ItemService.instance.createNewItemMap((short) 69, (short) 1, Const.CATEGORY_POTION, destX, destY, plAttack.getIdPlayer(), zone));
                    }
                }
            }
        }
        
        if (isKhoangSan()) {
            switch (template.getId()) {
                case 85 ->
                    its.add(ItemService.instance.createNewItemMap((short) 81, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                case 86 ->
                    its.add(ItemService.instance.createNewItemMap((short) 67, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                case 87 ->
                    its.add(ItemService.instance.createNewItemMap((short) 88, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                case 88 ->
                    its.add(ItemService.instance.createNewItemMap((short) 95, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
                case 89 ->
                    its.add(ItemService.instance.createNewItemMap((short) 74, (short) 1, Const.CATEGORY_GEM_ITEM, destX, destY, plAttack.getIdPlayer(), zone));
            }
        }
        return its;
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
            if (isMelee()) {
                this.nextAttackDelay = Util.nextInt(500, 3000);
            } else {
                this.nextAttackDelay = Util.nextInt(500, 5000);
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
                        Thread.sleep(300);
                        
                        if (playerTarget != null && !playerTarget.isDie()) {
                            if (isMelee()) {
                                MonsterService.instance.sendMeleeHit(this, playerTarget);
                            } else {
                                MonsterService.instance.sendMonsterAttack(this, playerTarget);
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
            this.hp = template.getMaxHp();
            if (this.startX != -1) {
                this.x = this.startX;
                this.y = this.startY;
                MonsterService.instance.sendMonsterMove(this);
            }
        }
        buffInfluence.update();
        if (!canNotAttackPlayer()) {
            attackPlayer();
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
