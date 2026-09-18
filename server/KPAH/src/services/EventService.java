package services;

import java.io.IOException;
import lombok.NonNull;
import player.Player;
import utils.ServerLog;

/**
 * Service quản lý Hệ Thống Sự Kiện & Quà Tặng (Event & Gift System).
 * Hỗ trợ tự động kiểm tra và trao quà khi thăng cấp/đăng nhập,
 * có cơ chế hẹn thử lại sau 1 giờ nếu hành trang đầy.
 */
public class EventService {

    public static final EventService instance = new EventService();

    // Các mã quà tặng / sự kiện
    public static final String GIFT_LV30 = "GIFT_LV30";
    public static final long RETRY_INTERVAL_MS = 3600000L; // 1 giờ (3.600.000 ms)

    /**
     * Tự động kiểm tra và trao các phần quà mốc cấp độ / sự kiện cho nhân vật.
     * Được gọi khi:
     * 1. Người chơi thăng cấp (Point.plusXp).
     * 2. Người chơi đăng nhập hoàn tất vào game.
     * 3. Vòng lặp định kỳ nếu đang có quà hẹn gửi lại sau 1 giờ.
     */
    public void checkAndAutoDeliverGifts(@NonNull Player player) {
        try {
            if (player.getSession() == null || player.isDie()) {
                return;
            }

            // 1. Quà mốc Cấp 30: 1 Rương Kho Báu Cấp 30 (ID 165)
            checkLevel30MilestoneGift(player);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra và tự động trao phần thưởng mốc Cấp 30.
     */
    private void checkLevel30MilestoneGift(@NonNull Player player) throws IOException {
        if (player.getInfo().getLevel() < 30) {
            return;
        }

        // Đã nhận rồi -> không trao lại
        if (player.getQuestData().isGiftClaimed(GIFT_LV30)) {
            return;
        }

        // Chưa đến thời gian thử lại (nếu trước đó bị đầy túi)
        if (!player.getQuestData().canRetryGift(GIFT_LV30)) {
            return;
        }

        // Kiểm tra chỗ trống trong túi Potion
        boolean canStack = (InventoryService.instance.findItemPotion(player, (short) 165) != null);
        boolean canAddPotion = canStack || !player.getInventory().isFullInventory();

        if (canAddPotion) {
            // Trao 1 Rương Kho Báu Cấp 30 vào hành trang
            InventoryService.instance.addItemPotion(player, ItemService.instance.createNewItemPotion((short) 165, 1));
            InventoryService.instance.sendItemPotion(player);

            // Đánh dấu đã nhận vĩnh viễn
            player.getQuestData().claimGift(GIFT_LV30);

            // Gửi popup chúc mừng trang trọng từ hệ thống
            String popupMsg = "🎉 CHÚC MỪNG DŨNG SĨ ĐẠT CẤP 30! 🎉\n"
                    + "Hệ thống đã tự động gửi [Rương Kho Báu (Cấp 30)] vào hành trang của bạn!\n\n"
                    + "Mở rương sẽ nhận đủ nguyên liệu chế trọn bộ 8 món trang bị Cấp 30 và 1 vũ khí Cấp 31 Nhất phẩm.\n"
                    + "Chúc dũng sĩ bách chiến bách thắng!";
            Service.instance.sendLogOut(player.getSession(), popupMsg);
            ChatService.instance.sendChatOnlyMe(player, "Chúc mừng! Bạn đã nhận [Rương Kho Báu (Cấp 30)] vào hành trang!");
            ServerLog.combat("Nhân vật '%s' (ID: %d) đã tự động nhận [Rương Kho Báu (Cấp 30)] khi đạt mốc Cấp 30",
                    player.getName(), player.getIdPlayer());
        } else {
            // Hành trang đầy -> hẹn tự động thử lại sau 1 giờ
            player.getQuestData().setGiftRetry(GIFT_LV30, RETRY_INTERVAL_MS);

            String fullMsg = "⚠️ HÀNH TRANG ĐÃ ĐẦY! ⚠️\n"
                    + "Chúc mừng bạn đạt Cấp 30! Tuy nhiên hành trang dược phẩm của bạn đã đầy nên không thể nhận [Rương Kho Báu (Cấp 30)].\n\n"
                    + "Hệ thống sẽ tự động gửi lại quà sau 1 giờ nữa.\n"
                    + "Vui lòng dọn dẹp hành trang để nhận quà!";
            Service.instance.sendLogOut(player.getSession(), fullMsg);
            ChatService.instance.sendChatOnlyMe(player, "Hành trang đầy! Quà mốc Cấp 30 sẽ được tự động gửi lại sau 1 giờ.");
            ServerLog.combat("Nhân vật '%s' (ID: %d) đạt Cấp 30 nhưng hành trang đầy, hẹn gửi lại sau 1 giờ",
                    player.getName(), player.getIdPlayer());
        }
    }
}
