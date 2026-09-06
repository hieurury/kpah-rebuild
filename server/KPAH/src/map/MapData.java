package map;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import player.Player;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
@Data
@Builder
public class MapData {

    @NonNull
    private List<Monster> mobsOrigin;
    @NonNull
    private List<LoctionWayPoint> locationWayPoints;
    @NonNull
    private WayPoint[][][] wayPoints;
    @NonNull
    private List<Actor> tileTops;
    @NonNull
    private List<Actor> tileTops2;
    @NonNull
    private List<Actor> npcs;
    @NonNull
    private List<NpcServer> npcServer;
    @NonNull
    private List<Player> npcsActor;
    @NonNull
    private List<Actor> trees;
    private byte idXaPhu;
    private byte idHoaTieu;
    private boolean isOfflineMap;
    private byte maxZone;
    private short[] map;
    private int[] type;
    private short w;
    private short h;

    public boolean isWalkable(int px, int py) {
        if (px < 0 || py < 0) return false;
        int tileX = px / 16;
        int tileY = py / 16;
        if (tileX >= w || tileY >= h) {
            return false;
        }
        int t = type[tileY * w + tileX];
        if (t >= 2000000000) return true; // Waypoint
        return (t & 2) != 2;
    }
}
