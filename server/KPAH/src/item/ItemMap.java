package item;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import map.Zone;
import services.ItemService;
import consts.Const;
import utils.Util;

/**
 *
 * @author ☂️☂️Duy Coder 💖💖
 */
@Data
@AllArgsConstructor
public class ItemMap {

    private byte itemCatagory;
    private short itemTemplateID;
    private short itemMapId;
    private short quantity;
    private short x;
    private short y;
    private Zone zone;
    private long lastTimeCreate;
    private short idPlayerDrop;

    public void dispose() {
        zone = null;
    }

    public void update() throws IOException {
        int timeWait = 60000; // Default 1 min for potions/gold
        if (itemCatagory == Const.CATEGORY_ITEM || itemCatagory == Const.CATEGORY_GEM_ITEM) {
            timeWait = 180000; // 3 mins for equips and gems
        }
        if (idPlayerDrop != -1 && Util.canDoWithTime(lastTimeCreate, timeWait / 2)) {
            idPlayerDrop = -1;
        }
        if (Util.canDoWithTime(lastTimeCreate, timeWait)) {
            ItemService.instance.onRemoveItemMap(this);
            zone.removeItem(this);
        }
    }
}
