package player;

import clan.Clan;
import lombok.Builder;
import lombok.Data;
import org.json.JSONArray;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
@Data
@Builder
public class Info {

    private byte classPlayer;
    private byte head;
    private byte gender;
    private byte idNation;
    private byte he;
    private byte level;
    private Clan clan;
    private short killer;
    
    @Builder.Default
    private String questDataJSON = "{}";

    public void plusLevel(byte plus) {
        if (plus <= 0) {
            return;
        }
        level += plus;
    }

    public void plusKiller(byte plus) {
        if (plus <= 0) {
            return;
        }
        killer += plus;
    }

    public void minusKiller(byte plus) {
        if (plus <= 0) {
            return;
        }
        if (killer - plus < 0) {
            killer = 0;
            return;
        }
        killer -= plus;
    }

    @Override
    public String toString() {
        JSONArray arr = new JSONArray();
        arr.put(classPlayer);
        arr.put(head);
        arr.put(gender);
        arr.put(idNation);
        arr.put(he);
        arr.put(level);
        arr.put(clan != null ? clan.getIndexIcon() : -1);
        arr.put(killer);
        try {
            arr.put(new org.json.JSONObject(questDataJSON != null ? questDataJSON : "{}"));
        } catch (Exception e) {
            arr.put(new org.json.JSONObject());
        }
        return arr.toString();
    }
}
