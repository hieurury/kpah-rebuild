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

    private boolean isHoaDa;
    private short secondOfHoaDa;
    private long lastTimeHoaDa;

    private boolean isGiamGiap;
    private short secondOfGiamGiap;
    private int giamGiapPercent;
    private long lastTimeGiamGiap;

    private boolean isMu;
    private short secondOfMu;
    private long lastTimeMu;

    private boolean isVetThuongSau;
    private short secondOfVetThuongSau;
    private long lastTimeVetThuongSau;

    private boolean isNhiemDien;
    private short secondOfNhiemDien;
    private long lastTimeNhiemDien;

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
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_STUN);
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

    @Synchronized
    public void addBuffHoaDa(short time) throws IOException {
        isHoaDa = true;
        secondOfHoaDa = time;
        lastTimeHoaDa = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_HOA_DA);
    }

    @Synchronized
    public void removeBuffHoaDa() {
        if (!isHoaDa) return;
        isHoaDa = false;
        secondOfHoaDa = 0;
        lastTimeHoaDa = 0;
    }

    @Synchronized
    public void addBuffGiamGiap(short time, int percent) throws IOException {
        isGiamGiap = true;
        secondOfGiamGiap = time;
        giamGiapPercent = percent;
        lastTimeGiamGiap = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_GIAM_GIAP);
    }

    @Synchronized
    public void removeBuffGiamGiap() {
        if (!isGiamGiap) return;
        isGiamGiap = false;
        secondOfGiamGiap = 0;
        giamGiapPercent = 0;
        lastTimeGiamGiap = 0;
    }

    @Synchronized
    public int detonatePoison() throws IOException {
        if (!isPoisoned) {
            return 0;
        }
        int secondsLeft = getSecondPosonedLeft();
        if (secondsLeft <= 0) {
            removeBuffPoisoned();
            return 0;
        }
        int damagePerTick = flatDamagePerTick;
        if (percentHpPerTick > 0 && mob != null) {
            damagePerTick += (int) (mob.getMaxHp() * (percentHpPerTick / 100.0f));
        }
        if (damagePerTick <= 0) {
            damagePerTick = Math.max(1, (int) docTo);
        }
        int totalDetonateDamage = damagePerTick * secondsLeft;
        removeBuffPoisoned();
        return totalDetonateDamage;
    }

    @Synchronized
    public void addBuffMu(short time) throws IOException {
        isMu = true;
        secondOfMu = time;
        lastTimeMu = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_MU);
    }

    @Synchronized
    public void removeBuffMu() {
        if (!isMu) return;
        isMu = false;
        secondOfMu = 0;
        lastTimeMu = 0;
    }

    @Synchronized
    public void addBuffVetThuongSau(short time) throws IOException {
        isVetThuongSau = true;
        secondOfVetThuongSau = time;
        lastTimeVetThuongSau = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_VET_THUONG_SAU);
    }

    @Synchronized
    public void removeBuffVetThuongSau() {
        if (!isVetThuongSau) return;
        isVetThuongSau = false;
        secondOfVetThuongSau = 0;
        lastTimeVetThuongSau = 0;
    }

    @Synchronized
    public void addBuffNhiemDien(short time) throws IOException {
        isNhiemDien = true;
        secondOfNhiemDien = time;
        lastTimeNhiemDien = System.currentTimeMillis();
        BuffService.instance.sendAddBuffInfluence(mob, BuffConst.BUFF_NHIEM_DIEN);
    }

    @Synchronized
    public void removeBuffNhiemDien() throws IOException {
        if (!isNhiemDien) return;
        isNhiemDien = false;
        secondOfNhiemDien = 0;
        lastTimeNhiemDien = 0;
        BuffService.instance.sendRemoveBuffNhiemDien(mob);
    }

    @Synchronized
    public byte getSecondNhiemDienLeft() {
        return (byte) Math.max(0, (secondOfNhiemDien - Util.getSecondDifference(System.currentTimeMillis(), lastTimeNhiemDien)));
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
        if (isHoaDa) {
            removeBuffHoaDa();
        }
        if (isGiamGiap) {
            removeBuffGiamGiap();
        }
        if (isMu) {
            removeBuffMu();
        }
        if (isVetThuongSau) {
            removeBuffVetThuongSau();
        }
        if (isNhiemDien) {
            removeBuffNhiemDien();
        }
    }

    public void update() throws IOException {
        if (isNhiemDien && (Util.canDoWithTime(lastTimeNhiemDien, secondOfNhiemDien * 1000) || mob.isDie())) {
            removeBuffNhiemDien();
        }
        if (isStunned && (Util.canDoWithTime(lastTimeStunned, secondOfStunned * 1000) || mob.isDie())) {
            removeBuffStunned();
        }
        if (isHoaDa && (Util.canDoWithTime(lastTimeHoaDa, secondOfHoaDa * 1000) || mob.isDie())) {
            removeBuffHoaDa();
        }
        if (isGiamGiap && (Util.canDoWithTime(lastTimeGiamGiap, secondOfGiamGiap * 1000) || mob.isDie())) {
            removeBuffGiamGiap();
        }
        if (isMu && (Util.canDoWithTime(lastTimeMu, secondOfMu * 1000) || mob.isDie())) {
            removeBuffMu();
        }
        if (isVetThuongSau && (Util.canDoWithTime(lastTimeVetThuongSau, secondOfVetThuongSau * 1000) || mob.isDie())) {
            removeBuffVetThuongSau();
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
            // Điều chỉnh sát thương độc theo thời gian thêm dao động +-10% dame
            int variance = (int) Math.round(damageAmount * 0.10);
            if (variance > 0) {
                damageAmount = Util.nextInt(Math.max(1, damageAmount - variance), damageAmount + variance);
            }
            short dame = (short) mob.injured(playerUser, damageAmount, false, true, false);
            if (dame > 0) {
                BuffService.instance.sendSubHpByBuffInfluence(mob, dame);
            }
        }
    }
}
