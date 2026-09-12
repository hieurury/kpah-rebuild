package skill;

import java.io.IOException;
import lombok.Builder;
import lombok.Data;
import lombok.Synchronized;
import map.Monster;
import player.Player;
import services.BuffService;
import consts.BuffConst;
import utils.Util;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
@Data
@Builder
public class BuffInfluenceMonster {

    private Monster mob;
    private Player playerUser;

    private boolean isPoisoned;
    private short docTo;
    private short secondOfPoisoned;
    private int percentHpPerTick;
    private int flatDamagePerTick;
    private long lastTimePoisoned;
    private long lastTimeMinusHp;

    private boolean isStunned;
    private short secondOfStunned;
    private long lastTimeStunned;

    private boolean isInstantPoisoned;
    private byte instantPoisonStacks;
    private short secondOfInstantPoison;
    private long lastTimeInstantPoisoned;

    @Synchronized
    public void addBuffPoisoned(Player player, short time, short docto) throws IOException {
        addBuffPoisoned(player, time, 0, (int) docto);
    }

    @Synchronized
    public void addBuffPoisoned(Player player, short time, int percentHp, int flatDamage) throws IOException {
        isPoisoned = true;
        secondOfPoisoned = time;
        percentHpPerTick = percentHp;
        flatDamagePerTick = flatDamage;
        docTo = (short) flatDamage;
        lastTimePoisoned = System.currentTimeMillis();
        playerUser = player;
        lastTimeMinusHp = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_DOC_TO);
    }

    @Synchronized
    public void removeBuffPoisoned() throws IOException {
        if (!isPoisoned) {
            return;
        }
        isPoisoned = false;
        secondOfPoisoned = 0;
        lastTimePoisoned = 0;
        docTo = 0;
        percentHpPerTick = 0;
        flatDamagePerTick = 0;
        playerUser = null;
        // Client KPAH tự quản lý thời gian hết độc dựa vào animation/timer, không gửi BUFF_ATTACK (89) để tránh client bị re-poison lặp lại
    }

    @Synchronized
    public byte getSecondPosonedLeft() {
        return (byte) Math.max(0, (secondOfPoisoned - Util.getSecondDifference(System.currentTimeMillis(), lastTimePoisoned)));
    }

    @Synchronized
    public void addBuffInstantPoison(Player player, short time) throws IOException {
        isInstantPoisoned = true;
        if (instantPoisonStacks < 5) {
            instantPoisonStacks++;
        }
        secondOfInstantPoison = time;
        lastTimeInstantPoisoned = System.currentTimeMillis();
        playerUser = player;
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_NHIEM_DOC);
    }

    @Synchronized
    public void removeBuffInstantPoison() {
        if (!isInstantPoisoned) {
            return;
        }
        isInstantPoisoned = false;
        instantPoisonStacks = 0;
        secondOfInstantPoison = 0;
        lastTimeInstantPoisoned = 0;
    }

    @Synchronized
    public byte getSecondInstantPoisonLeft() {
        return (byte) Math.max(0, (secondOfInstantPoison - Util.getSecondDifference(System.currentTimeMillis(), lastTimeInstantPoisoned)));
    }

    @Synchronized
    public void addBuffStunned(short time) throws IOException {
        if (isStunned) {
            return;
        }
        isStunned = true;
        secondOfStunned = time;
        lastTimeStunned = System.currentTimeMillis();
    }

    @Synchronized
    public void removeBuffStunned() throws IOException {
        if (!isStunned) {
            return;
        }
        isStunned = false;
        secondOfStunned = 0;
        lastTimeStunned = 0;
        playerUser = null;
    }

    public void clearBuff() throws IOException {
        if (isPoisoned) {
            removeBuffPoisoned();
        }
        if (isInstantPoisoned) {
            removeBuffInstantPoison();
        }
        if (isStunned) {
            removeBuffStunned();
        }
    }

    public void update() throws IOException {
        if (isStunned && (Util.canDoWithTime(lastTimeStunned, secondOfStunned * 1000) || mob.isDie())) {
            removeBuffStunned();
        }
        if (isPoisoned && (Util.canDoWithTime(lastTimePoisoned, secondOfPoisoned * 1000) || mob.isDie())) {
            removeBuffPoisoned();
        }
        if (isInstantPoisoned && (Util.canDoWithTime(lastTimeInstantPoisoned, secondOfInstantPoison * 1000) || mob.isDie())) {
            removeBuffInstantPoison();
        }
        // Gây sát thương độc mỗi giây (1000ms)
        if (isPoisoned && !mob.isDie() && Util.canDoWithTime(lastTimeMinusHp, 1000L)) {
            lastTimeMinusHp = System.currentTimeMillis();
            int maxHp = mob.getMaxHp();
            int damageAmount = 0;
            if (percentHpPerTick > 0) {
                damageAmount += (int) (maxHp * (percentHpPerTick / 100.0f));
            }
            damageAmount += flatDamagePerTick;
            if (damageAmount <= 0) {
                damageAmount = Math.max(1, (int) docTo);
            }
            short dame = (short) mob.injured(playerUser, damageAmount, false, true, false);
            if (dame > 0) {
                BuffService.instance.sendSubHpByBuffInfluence(mob, dame);
            }
        }
    }
}
