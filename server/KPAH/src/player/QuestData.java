package player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Util;

/**
 * Quản lý dữ liệu nhiệm vụ tân thủ và hằng ngày của người chơi.
 */
@Data
public class QuestData {
    public int beginnerQuestId = 0; // 0 = Khởi đầu anh hùng, 1 = Lên đồ, 2 = Trưởng thành, 3 = Xong tân thủ
    public int beginnerProgress = 0;
    
    // NpcId -> QuestID
    public Map<Byte, Integer> dailyQuests = new HashMap<>();
    // NpcId -> Progress
    public Map<Byte, Integer> dailyProgress = new HashMap<>();
    // NpcId -> Target
    public Map<Byte, Integer> dailyTargets = new HashMap<>();
    
    public int dailyQuestsGiven = 0; // Tối đa 3 nhiệm vụ hằng ngày xuất hiện 1 ngày
    public String lastDailyReset = ""; 
    
    public int totalMonstersKilled = 0;
    public long totalPixelsTraveled = 0;
    public int totalItemsSold = 0;
    public String claimedDailyLogin = "";

    // Hệ thống Quà Tặng & Sự Kiện
    public java.util.Set<String> claimedGifts = new java.util.HashSet<>();
    public java.util.Map<String, Long> giftRetryTimers = new java.util.HashMap<>();

    public boolean isGiftClaimed(String giftId) {
        return claimedGifts != null && claimedGifts.contains(giftId);
    }

    public void claimGift(String giftId) {
        if (claimedGifts == null) claimedGifts = new java.util.HashSet<>();
        claimedGifts.add(giftId);
        if (giftRetryTimers != null) giftRetryTimers.remove(giftId);
    }

    public boolean canRetryGift(String giftId) {
        if (giftRetryTimers == null || !giftRetryTimers.containsKey(giftId)) return true;
        return System.currentTimeMillis() >= giftRetryTimers.get(giftId);
    }

    public void setGiftRetry(String giftId, long delayMs) {
        if (giftRetryTimers == null) giftRetryTimers = new java.util.HashMap<>();
        giftRetryTimers.put(giftId, System.currentTimeMillis() + delayMs);
    }

    public static QuestData parse(String jsonString) {
        QuestData data = new QuestData();
        if (Util.isNullOrEmpty(jsonString) || jsonString.equals("{}")) {
            return data;
        }
        try {
            JSONObject obj = new JSONObject(jsonString);
            if (obj.has("beginnerQuestId")) data.beginnerQuestId = obj.getInt("beginnerQuestId");
            if (obj.has("beginnerProgress")) data.beginnerProgress = obj.getInt("beginnerProgress");
            if (obj.has("dailyQuestsGiven")) data.dailyQuestsGiven = obj.getInt("dailyQuestsGiven");
            if (obj.has("lastDailyReset")) data.lastDailyReset = obj.getString("lastDailyReset");
            if (obj.has("claimedDailyLogin")) data.claimedDailyLogin = obj.getString("claimedDailyLogin");
            if (obj.has("totalMonstersKilled")) data.totalMonstersKilled = obj.getInt("totalMonstersKilled");
            if (obj.has("totalPixelsTraveled")) data.totalPixelsTraveled = obj.getLong("totalPixelsTraveled");
            if (obj.has("totalItemsSold")) data.totalItemsSold = obj.getInt("totalItemsSold");
            
            if (obj.has("dailyQuests")) {
                JSONObject dq = obj.getJSONObject("dailyQuests");
                Iterator<String> keys = dq.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    data.dailyQuests.put(Byte.parseByte(key), dq.getInt(key));
                }
            }
            if (obj.has("dailyProgress")) {
                JSONObject dp = obj.getJSONObject("dailyProgress");
                Iterator<String> keys = dp.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    data.dailyProgress.put(Byte.parseByte(key), dp.getInt(key));
                }
            }
            if (obj.has("dailyTargets")) {
                JSONObject dt = obj.getJSONObject("dailyTargets");
                Iterator<String> keys = dt.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    data.dailyTargets.put(Byte.parseByte(key), dt.getInt(key));
                }
            }
            if (obj.has("claimedGifts")) {
                org.json.JSONArray cg = obj.getJSONArray("claimedGifts");
                for (int i = 0; i < cg.length(); i++) {
                    data.claimedGifts.add(cg.getString(i));
                }
            }
            if (obj.has("giftRetryTimers")) {
                JSONObject grt = obj.getJSONObject("giftRetryTimers");
                Iterator<String> keys = grt.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    data.giftRetryTimers.put(key, grt.getLong(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("beginnerQuestId", beginnerQuestId);
            obj.put("beginnerProgress", beginnerProgress);
            obj.put("dailyQuestsGiven", dailyQuestsGiven);
            obj.put("lastDailyReset", lastDailyReset != null ? lastDailyReset : "");
            obj.put("claimedDailyLogin", claimedDailyLogin != null ? claimedDailyLogin : "");
            obj.put("totalMonstersKilled", totalMonstersKilled);
            obj.put("totalPixelsTraveled", totalPixelsTraveled);
            obj.put("totalItemsSold", totalItemsSold);
            
            JSONObject dq = new JSONObject();
            for (Map.Entry<Byte, Integer> entry : dailyQuests.entrySet()) {
                dq.put(String.valueOf(entry.getKey()), entry.getValue());
            }
            obj.put("dailyQuests", dq);
            
            JSONObject dp = new JSONObject();
            for (Map.Entry<Byte, Integer> entry : dailyProgress.entrySet()) {
                dp.put(String.valueOf(entry.getKey()), entry.getValue());
            }
            obj.put("dailyProgress", dp);
            
            JSONObject dt = new JSONObject();
            for (Map.Entry<Byte, Integer> entry : dailyTargets.entrySet()) {
                dt.put(String.valueOf(entry.getKey()), entry.getValue());
            }
            obj.put("dailyTargets", dt);

            org.json.JSONArray cg = new org.json.JSONArray();
            for (String g : claimedGifts) {
                cg.put(g);
            }
            obj.put("claimedGifts", cg);

            JSONObject grt = new JSONObject();
            for (Map.Entry<String, Long> entry : giftRetryTimers.entrySet()) {
                grt.put(entry.getKey(), entry.getValue());
            }
            obj.put("giftRetryTimers", grt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
