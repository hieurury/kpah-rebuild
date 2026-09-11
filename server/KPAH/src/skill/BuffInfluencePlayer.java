package skill;

import java.io.IOException;
import lombok.Builder;
import lombok.Data;
import lombok.Synchronized;
import player.Player;
import services.BuffService;
import consts.BuffConst;
import consts.ItemEquipConst;
import utils.Util;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
@Data
@Builder
public class BuffInfluencePlayer {

    private Player player;

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
    public void addBuffPoisoned(short time, short docto) throws IOException {
        addBuffPoisoned(time, 0, (int) docto);
    }

    @Synchronized
    public void addBuffPoisoned(short time, int percentHp, int flatDamage) throws IOException {
        isPoisoned = true;
        secondOfPoisoned = time;
        percentHpPerTick = percentHp;
        flatDamagePerTick = flatDamage;
        docTo = (short) flatDamage;
        lastTimePoisoned = System.currentTimeMillis();
        lastTimeMinusHp = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(this.player, BuffConst.BUFF_DOC_TO);
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
        // Client KPAH tự quản lý thời gian hết độc dựa vào animation/timer, không gửi BUFF_ATTACK (89) để tránh client bị re-poison lặp lại
    }

    @Synchronized
    public byte getSecondPosonedLeft() {
        return (byte) Math.max(0, (secondOfPoisoned - Util.getSecondDifference(System.currentTimeMillis(), lastTimePoisoned)));
    }

    @Synchronized
    public void addBuffInstantPoison(short time) throws IOException {
        isInstantPoisoned = true;
        if (instantPoisonStacks < 5) {
            instantPoisonStacks++;
        }
        secondOfInstantPoison = time;
        lastTimeInstantPoisoned = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(this.player, BuffConst.BUFF_DOC_TO);
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
        BuffService.instance.sendAddBuffInfluence(this.player, BuffConst.BUFF_STUN);
    }

    @Synchronized
    public void removeBuffStunned() throws IOException {
        if (!isStunned) {
            return;
        }
        isStunned = false;
        secondOfStunned = 0;
        lastTimeStunned = 0;
        // Client KPAH tự quản lý thời gian hết choáng dựa vào cZ, không gửi BUFF_ATTACK (89) để tránh client bị re-stun lặp lại
    }

    public void dispose() {
        this.player = null;
    }

    public void update() throws IOException {
        if (isStunned && (Util.canDoWithTime(lastTimeStunned, secondOfStunned * 1000) || player.isDie())) {
            removeBuffStunned();
        }
        if (isPoisoned && (Util.canDoWithTime(lastTimePoisoned, secondOfPoisoned * 1000) || player.isDie())) {
            removeBuffPoisoned();
        }
        if (isInstantPoisoned && (Util.canDoWithTime(lastTimeInstantPoisoned, secondOfInstantPoison * 1000) || player.isDie())) {
            removeBuffInstantPoison();
        }
        // Gây sát thương độc mỗi giây (1000ms)
        if (isPoisoned && !player.isDie() && Util.canDoWithTime(lastTimeMinusHp, 1000L)) {
            lastTimeMinusHp = System.currentTimeMillis();
            int maxHp = player.getPoint() != null ? player.getPoint().getHpMax() : 1000;
            int damageAmount = 0;
            if (percentHpPerTick > 0) {
                damageAmount += (int) (maxHp * (percentHpPerTick / 100.0f));
            }
            damageAmount += flatDamagePerTick;
            if (damageAmount <= 0) {
                damageAmount = Math.max(1, (int) docTo);
            }
            short dame = (short) player.injured(damageAmount, true, ItemEquipConst.DAMAGE_MAGIC, false);
            if (dame > 0) {
                BuffService.instance.sendSubHpByBuffInfluence(player, dame);
            }
        }
    }
}
