package services;

import item.ItemMap;
import java.io.IOException;
import java.util.List;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.Synchronized;
import manager.Manager;
import manager.Settings;
import map.Monster;
import player.Player;
import network.Message;
import template.MonsterTemplate;
import utils.CommandMessage;
import consts.Const;
import consts.ItemEquipConst;
import utils.Util;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
public class MonsterService {

    public static final MonsterService instance = new MonsterService();

    public void sendMonsterEndAttack(@NonNull Monster monster) throws IOException {
        Message msg = new Message(CommandMessage.MONSTER_ATTACK_PLAYER);
        msg.writer().writeShort(monster.getId());
        msg.writer().writeShort(32001);
        msg.writer().writeInt(0);
        msg.writer().writeInt(0);
        MapService.instance.sendAllPlayerInMap(monster, msg);
    }

    public int sendMonsterAttack(@NonNull Monster monster, @NonNull Player plTarget) throws IOException {
        int dameMob = monster.getDameAttack(plTarget);
        dameMob = BuffService.instance.onAttackPlayerHasBuff(monster, plTarget, dameMob);
        int damage = plTarget.injured(dameMob, false, ItemEquipConst.DAMAGE_PHYSIC, false);
        Message msg = new Message(CommandMessage.MONSTER_ATTACK_PLAYER);
        msg.writer().writeShort(monster.getId());
        msg.writer().writeShort(plTarget.getIdPlayer());
        msg.writer().writeInt(damage);
        msg.writer().writeInt(plTarget.getPoint().getHp());
        MapService.instance.sendAllPlayerInMap(monster, msg);
        return damage;
    }

    @Synchronized
    public void onMonsterDropItem(@NonNull Monster monster, @NonNull Player plAtt) throws IOException {
        if (!monster.isDie()) {
            return;
        }
        if (monster.isElite()) {
            ChatService.instance.sendChatOnlyMe(plAtt, "Bạn đã tiêu diệt Quái Tinh Anh [" + monster.getTemplate().getName() + "]!");
            String mapName = (monster.getZone() != null && monster.getZone().getMap() != null) ? monster.getZone().getMap().getName() : "Bản đồ";
            int zoneId = (monster.getZone() != null) ? monster.getZone().getId() : 0;
            utils.ServerLog.combat("Nhân vật '%s' (ID: %d, Cấp %d) đã tiêu diệt Quái Tinh Anh [%s] (Cấp %d) tại %s [Khu %d - X:%d, Y:%d].",
                    plAtt.getName(), plAtt.getIdPlayer(), plAtt.getInfo().getLevel(), monster.getTemplate().getName(), monster.getTemplate().getLevel(), mapName, zoneId, monster.getX(), monster.getY());
        }
        services.QuestService.instance.onKillMonster(plAtt, monster.getTemplate().getId(), (byte) monster.getTemplate().getLevel());
        Message msg = new Message(CommandMessage.MONSTER_DIE);
        msg.writer().writeShort(plAtt.getIdPlayer());
        msg.writer().writeShort(monster.getId());
        msg.writer().writeByte(plAtt.getSkill().getTypeSkill());
        msg.writer().writeInt(-1);
        msg.writer().writeByte(Const.NONE_EFFECT);
        @Cleanup("clear")
        List<ItemMap> itemsDrop = monster.getItemDrop(plAtt);
        msg.writer().writeByte(itemsDrop.size());
        for (int i = 0; i < itemsDrop.size(); i++) {
            ItemMap itemMap = itemsDrop.get(i);
            plAtt.getOtherItemMapInside().add(itemMap.getItemMapId());
            monster.getZone().addItem(itemMap);
            msg.writer().writeByte(itemMap.getItemCatagory());
            msg.writer().writeShort(itemMap.getItemTemplateID());
            msg.writer().writeShort(itemMap.getItemMapId());
            msg.writer().writeShort(itemMap.getX());
            msg.writer().writeShort(itemMap.getY());
        }
        MapService.instance.sendAllPlayerInMap(monster, msg);
        // Auto-Loot via Walk
        if (plAtt.getSundry().isAutoLoot) {
            Thread.startVirtualThread(() -> {
                for (int i = 0; i < itemsDrop.size(); i++) {
                    ItemMap itemMap = itemsDrop.get(i);
                    try {
                        Thread.sleep(200); // Small delay between items
                        int dist = utils.Util.getDistance(plAtt.getLocation().getX(), plAtt.getLocation().getY(), itemMap.getX(), itemMap.getY());
                        
                        if (dist > 5) {
                            plAtt.getLocation().setX(itemMap.getX());
                            plAtt.getLocation().setY(itemMap.getY());
                            MapService.instance.sendMove(plAtt);
                            
                            // Simulate travel time based on distance (approx 15ms per pixel for animation)
                            Thread.sleep(dist * 15L);
                        }
                        
                        if (itemMap.getItemCatagory() == Const.CATEGORY_ITEM) {
                            MapService.instance.getItemEquipmentFromGround(plAtt, itemMap.getItemMapId());
                        } else if (itemMap.getItemCatagory() == Const.CATEGORY_POTION) {
                            MapService.instance.getPotionFromGround(plAtt, itemMap.getItemMapId());
                        } else if (itemMap.getItemCatagory() == Const.CATEGORY_GEM_ITEM) {
                            MapService.instance.getGemFromGround(plAtt, itemMap.getItemMapId());
                        }
                    } catch (Exception e) {}
                }
            });
        }
    }

    public void sendMonsterMove(@NonNull Monster monster) throws IOException {
        if (monster.isDie()) {
            return;
        }
        Message msg = new Message(CommandMessage.MOVE_CHAR);
        msg.writer().writeByte(Const.CATEGORY_MONSTER);
        msg.writer().writeByte(monster.getTemplate().getId());
        msg.writer().writeShort(monster.getId());
        msg.writer().writeShort(monster.getX());
        msg.writer().writeShort(monster.getY());
        msg.writer().writeByte(-1);         // dir
        msg.writer().writeInt(monster.getHp());
        // NOTE: horse byte is NOT sent for monsters (category=2).
        // Client only reads horse when category=1 (player).
        msg.writer().writeByte((byte) (monster.isElite() ? 100 : -1)); // effect (100 = Elite)
        msg.writer().writeBoolean(true);    // smooth
        MapService.instance.sendAllPlayerInMap(monster, msg);
    }

    /**
     * Move monster instantly (teleport) without pathfinding animation.
     * Uses MOVE_CHAR with smooth=false flag.
     */
    public void sendMonsterTeleport(@NonNull Monster monster) throws IOException {
        if (monster.isDie()) {
            return;
        }
        Message msg = new Message(CommandMessage.MOVE_CHAR);
        msg.writer().writeByte(Const.CATEGORY_MONSTER);
        msg.writer().writeByte(monster.getTemplate().getId());
        msg.writer().writeShort(monster.getId());
        msg.writer().writeShort(monster.getX());
        msg.writer().writeShort(monster.getY());
        msg.writer().writeByte(-1);         // dir
        msg.writer().writeInt(monster.getHp());
        // NOTE: horse byte is NOT sent for monsters (category=2).
        // Client only reads horse when category=1 (player).
        msg.writer().writeByte((byte) (monster.isElite() ? 100 : -1)); // effect (100 = Elite)
        msg.writer().writeBoolean(false);   // smooth=false → instant jump
        MapService.instance.sendAllPlayerInMap(monster, msg);
    }

    /**
     * Apply melee damage to a player WITHOUT sending MONSTER_ATTACK_PLAYER.
     * This prevents the client from drawing a projectile.
     * Uses NEW_HP_MP to silently update the player's HP.
     */
    public int sendMeleeHit(@NonNull Monster monster, @NonNull Player plTarget) throws IOException {
        int dameMob = monster.getDameAttack(plTarget);
        dameMob = BuffService.instance.onAttackPlayerHasBuff(monster, plTarget, dameMob);
        int damage = plTarget.injured(dameMob, false, ItemEquipConst.DAMAGE_PHYSIC, false);
        // Show floating damage text to everyone near the monster
        Message msgDmg = new Message(CommandMessage.MONSTER_ATTACK_PLAYER);
        msgDmg.writer().writeShort(monster.getId());
        msgDmg.writer().writeShort(plTarget.getIdPlayer());
        msgDmg.writer().writeInt(damage);
        msgDmg.writer().writeInt(plTarget.getPoint().getHp());
        // Only send to the target player (not broadcast) - avoids projectile rendering
        plTarget.getSession().sendMessage(msgDmg);
        // Broadcast HP update to all so everyone sees the HP bar change
        Message msgHp = new Message(CommandMessage.NEW_HP_MP);
        msgHp.writer().writeShort(plTarget.getIdPlayer());
        msgHp.writer().writeInt(plTarget.getPoint().getHpMax());
        msgHp.writer().writeInt(plTarget.getPoint().getMpMax());
        msgHp.writer().writeShort(plTarget.getPoint().getDefend());
        msgHp.writer().writeInt(plTarget.getPoint().getHp());
        MapService.instance.sendAllPlayerInMap(monster, msgHp);
        return damage;
    }

    public void sendMonsterInfoToMap(@NonNull Monster monster) throws IOException {
        if (monster.getZone() == null || monster.getZone().getPlayers() == null) {
            return;
        }
        for (Player pl : monster.getZone().getPlayers()) {
            if (pl != null && pl.getSession() != null && !pl.isDie()) {
                if (Util.getDistance(pl, monster) < pl.getSession().getDistanceLoad()) {
                    sendMonsterInfo(pl, monster.getId());
                }
            }
        }
    }

    public void sendMonsterMove(@NonNull Player pl, @NonNull Monster monster) throws IOException {
        if (monster.isDie()) {
            return;
        }
        Message msg = new Message(CommandMessage.MOVE_CHAR);
        msg.writer().writeByte(Const.CATEGORY_MONSTER);
        msg.writer().writeByte(monster.getTemplate().getId());
        msg.writer().writeShort(monster.getId());
        msg.writer().writeShort(monster.getX());
        msg.writer().writeShort(monster.getY());
        msg.writer().writeByte(-1);         // dir
        msg.writer().writeInt(monster.getHp());
        // NOTE: horse byte is NOT sent for monsters (category=2).
        // Client only reads horse when category=1 (player).
        msg.writer().writeByte((byte) (monster.isElite() ? 100 : -1)); // effect (100 = Elite)
        msg.writer().writeBoolean(true);    // smooth
        pl.getSession().sendMessage(msg);
    }

    public void sendMonsterInfo(@NonNull Player pl, short id) throws IOException {
        for (Monster monster : pl.getLocation().getZone().getMobs()) {
            if (monster != null && Util.getDistance(pl, monster) < pl.getSession().getDistanceLoad() && !monster.isDie() && monster.getId() == id) {
                Message m = new Message(CommandMessage.MONSTER_INFO);
                m.writer().writeShort(id);
                m.writer().writeByte(monster.getTemplate().getId());
                m.writer().writeShort(monster.getX());
                m.writer().writeShort(monster.getY());
                m.writer().writeInt(monster.getHp());
                m.writer().writeByte(monster.getTemplate().getLevel());
                m.writer().writeByte((byte) (monster.isElite() ? 100 : monster.getTemplate().getType()));
                m.writer().writeInt(monster.getMaxHp());
                m.writer().writeInt(Settings.TIME_LIVE_MOB);
                pl.getSession().sendMessage(m);
            }
        }
    }

    public void sendMonsterImage(@NonNull Player pl, short id) throws IOException {
        byte[] data = Manager.getImageMonster(id);
        if (data == null) {
            return;
        }
        Message m = new Message(CommandMessage.LOAD_IMAGE_MONSTER);
        m.writer().writeShort(id);
        m.writer().writeBoolean(false);
        m.writer().writeShort(data.length);
        m.writer().write(data);
        pl.getSession().sendMessage(m);
    }

    public void sendMonsterTemplate(@NonNull Player pl, byte type, short idMob) throws IOException {
        if (type != 0) {
            return;
        }
        MonsterTemplate monsterTemplate = Manager.getMobTemplate(idMob);
        if (monsterTemplate == null) {
            return;
        }
        Message m = new Message(CommandMessage.GET_INFO_TEMPLATE);
        m.writer().writeByte(type);
        m.writer().writeShort(monsterTemplate.getId());
        m.writer().writeByte(9);
        m.writer().writeByte(monsterTemplate.getMoveType());
        m.writer().writeByte(monsterTemplate.getSpeed());
        m.writer().writeByte(monsterTemplate.getHeight());
        m.writer().writeByte(monsterTemplate.getW());
        m.writer().writeByte(monsterTemplate.getH());
        m.writer().writeByte(monsterTemplate.getXCenter());
        m.writer().writeByte(monsterTemplate.getYCenter());
        m.writer().writeByte(0);
        m.writer().writeByte(0);
        m.writer().writeUTF(monsterTemplate.getName());
        m.writer().writeByte(monsterTemplate.getType());
        m.writer().writeInt(monsterTemplate.getMaxHp());
        m.writer().writeByte(monsterTemplate.getPalate());
        m.writer().writeByte(monsterTemplate.getSpalate());
        m.writer().writeByte(monsterTemplate.isNewMonster() ? 1 : 0);
        pl.getSession().sendMessage(m);
    }
}
