package server;

import manager.Manager;
import network.Session;
import manager.ClientManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import manager.ClanManager;
import manager.ExecutorVirtualThread;
import manager.Settings;
import manager.TopManager;
import network.MessageHandler;
import network.MessageSendCollect;
import org.fusesource.jansi.AnsiConsole;
import player.Player;
import services.ChatService;
import utils.Logger;
import utils.ServerLog;

public class Server implements Runnable {

    private ServerSocketChannel serverChannel;
    private boolean isBaoTri;
    private final long startTime = System.currentTimeMillis();

    public void init() {
        new Thread(this, "Server Socket Thread").start();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }

    @Override
    public void run() {
        try {
            AnsiConsole.systemInstall();
            ServerLog.info("=========================================");
            ServerLog.info("        KPAH GAME SERVER KHỞI ĐỘNG       ");
            ServerLog.info("=========================================");
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(Settings.PORT_SERVER));
            serverChannel.configureBlocking(false);
            Manager.init();
            activeCommandLine();
            startHeartbeat();
            ServerLog.info("Máy chủ sẵn sàng! Lắng nghe cổng: %d", Settings.PORT_SERVER);
            ExecutorVirtualThread.submitServer(ClanManager.update());
            ExecutorVirtualThread.submitServer(TopManager.updateTopClan());
            while (!isBaoTri) {
                SocketChannel clientChannel = serverChannel.accept();
                if (clientChannel != null) {
                    if (ClientManager.getPlayers().size() < Settings.MAX_PLAYER) {
                        ExecutorVirtualThread.submitServer(() -> handleClient(clientChannel));
                    } else {
                        ServerLog.warn("Đã đạt giới hạn tối đa (%d) người chơi! Từ chối kết nối mới.", Settings.MAX_PLAYER);
                        clientChannel.close();
                    }
                } else {
                    // Tránh vòng lặp bận đốt 100% CPU (bảo vệ CPU và tránh bị Android Phantom Process Killer hạ sát)
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            }
        } catch (Exception e) {
            ServerLog.error("Lỗi nghiêm trọng trong luồng chạy Server", e);
            e.printStackTrace();
        } finally {
            try {
                if (serverChannel != null) {
                    serverChannel.close();
                }
            } catch (Exception e) {
                ServerLog.error("Lỗi đóng Server Channel: " + e.getMessage());
            }
            ExecutorVirtualThread.shutdownServer();
            ServerLog.info("Server Socket Channel đã đóng.");
        }
    }

    private void startHeartbeat() {
        Thread.ofVirtual().name("Server-Heartbeat").start(() -> {
            while (!isBaoTri) {
                try {
                    int interval = ServerLog.getHeartbeatIntervalSeconds();
                    TimeUnit.SECONDS.sleep(Math.max(5, interval));
                    if (!ServerLog.isHeartbeatEnabled()) {
                        continue;
                    }
                    Runtime rt = Runtime.getRuntime();
                    long totalMem = rt.totalMemory() / (1024 * 1024);
                    long freeMem = rt.freeMemory() / (1024 * 1024);
                    long usedMem = totalMem - freeMem;
                    long maxMem = rt.maxMemory() / (1024 * 1024);
                    int activeThreads = Thread.activeCount();
                    int onlinePlayers = ClientManager.getPlayers().size();
                    int totalSessions = ClientManager.getClients().size();
                    long uptimeSeconds = (System.currentTimeMillis() - startTime) / 1000;
                    long hours = uptimeSeconds / 3600;
                    long minutes = (uptimeSeconds % 3600) / 60;
                    long seconds = uptimeSeconds % 60;
                    String uptimeStr = String.format("%02dh:%02dm:%02ds", hours, minutes, seconds);

                    List<String> names = null;
                    if (onlinePlayers > 0) {
                        names = ClientManager.getPlayers().values().stream()
                                .filter(Objects::nonNull)
                                .map(Player::getName)
                                .toList();
                    }

                    ServerLog.heartbeat(onlinePlayers, totalSessions, usedMem, totalMem, maxMem, activeThreads, uptimeStr, names);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    ServerLog.error("Lỗi luồng giám sát Heartbeat", e);
                }
            }
        });
    }

    private void handleClient(SocketChannel clientChannel) {
        try {
            Session session = new Session(clientChannel.socket());
            session.initThreadSession();
            session.setMessageHandler(new MessageHandler()).setSendCollect(new MessageSendCollect()).startCollect();
            ExecutorVirtualThread.submitThreadSession(session.update());
            ServerLog.network("Chấp nhận kết nối từ IP: %s (Session #%d)", session.getIP(), session.getID());
            
            ClientManager.joinClient(session);
        } catch (IOException e) {
            ServerLog.error("Lỗi khởi tạo Client Session", e);
            Logger.logError("Lỗi Handle Client", e);
        }
    }

    private void activeCommandLine() {
        Thread.ofVirtual().name("Server-Console").start(() -> {
            try {
                Scanner sc = new Scanner(System.in);
                while (sc.hasNextLine()) {
                    String rawLine = sc.nextLine();
                    if (rawLine == null) {
                        continue;
                    }
                    String line = rawLine.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String lower = line.toLowerCase();

                    if (lower.equals("help")) {
                        printCommandHelp();
                    } else if (lower.equals("status")) {
                        printStatus();
                    } else if (lower.equals("online") || lower.equals("players") || lower.equals("listplayer") || lower.equals("player")) {
                        printOnlinePlayers();
                    } else if (lower.startsWith("kick ")) {
                        handleKickCommand(line);
                    } else if (lower.startsWith("say ") || lower.startsWith("chat ")) {
                        handleBroadcastCommand(line);
                    } else if (lower.startsWith("hb")) {
                        handleHeartbeatCommand(line);
                    } else if (lower.equals("gc")) {
                        handleGcCommand();
                    } else if (lower.equals("clear") || lower.equals("cls")) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                    } else if (lower.equals("baotri")) {
                        ServerLog.warn("Nhận lệnh bảo trì từ Console. Đang dừng máy chủ...");
                        isBaoTri = true;
                        closeServer();
                    } else {
                        ServerLog.command("Lệnh không xác định: '%s'. Gõ 'help' để xem danh sách lệnh.", line);
                    }
                }
            } catch (Exception e) {
                Logger.logError("Lỗi command line", e);
            }
        });
    }

    private void printCommandHelp() {
        ServerLog.command("===================== DANH MỤC LỆNH ADMIN CONSOLE =====================");
        ServerLog.command("  help                   : Hiển thị bảng trợ giúp này");
        ServerLog.command("  status                 : Xem thông số tài nguyên JVM, RAM, Uptime, Kết nối");
        ServerLog.command("  online / players       : Xem chi tiết toàn bộ người chơi đang online");
        ServerLog.command("  kick <tên/id>          : Ngắt kết nối và đá người chơi chỉ định");
        ServerLog.command("  say <nội dung>         : Phát thanh thông báo hệ thống tới toàn server");
        ServerLog.command("  chat <nội dung>        : Tương tự lệnh say");
        ServerLog.command("  hb on                  : BẬT hiển thị log nhịp tim Heartbeat");
        ServerLog.command("  hb off                 : TẮT hiển thị log nhịp tim Heartbeat");
        ServerLog.command("  hb <số giây>           : Đổi chu kỳ gửi log Heartbeat (tối thiểu 5s)");
        ServerLog.command("  gc                     : Ép thu dọn rác JVM & đo bộ nhớ giải phóng");
        ServerLog.command("  clear / cls            : Xóa sạch màn hình Terminal");
        ServerLog.command("  baotri                 : Lưu toàn bộ dữ liệu người chơi/clan và tắt server");
        ServerLog.command("=======================================================================");
    }

    private void printStatus() {
        Runtime rt = Runtime.getRuntime();
        long used = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
        long total = rt.totalMemory() / (1024 * 1024);
        long max = rt.maxMemory() / (1024 * 1024);
        int ramPercent = (int) (max > 0 ? (used * 100 / max) : 0);
        long uptimeSeconds = (System.currentTimeMillis() - startTime) / 1000;
        long hours = uptimeSeconds / 3600;
        long minutes = (uptimeSeconds % 3600) / 60;
        long seconds = uptimeSeconds % 60;
        String uptimeStr = String.format("%02dh:%02dm:%02ds", hours, minutes, seconds);

        ServerLog.command("========================== THÔNG SỐ MÁY CHỦ ==========================");
        ServerLog.command(" • Cổng lắng nghe    : %d", Settings.PORT_SERVER);
        ServerLog.command(" • Thời gian chạy    : %s", uptimeStr);
        ServerLog.command(" • Người chơi online : %d / %d", ClientManager.getPlayers().size(), Settings.MAX_PLAYER);
        ServerLog.command(" • Số Session kết nối: %d", ClientManager.getClients().size());
        ServerLog.command(" • RAM JVM sử dụng   : %dMB / %dMB (Max: %dMB) [%d%%]", used, total, max, ramPercent);
        ServerLog.command(" • Số luồng hoạt động: %d threads", Thread.activeCount());
        ServerLog.command(" • Nhịp tim Heartbeat: %s (Chu kỳ: %ds)", ServerLog.isHeartbeatEnabled() ? "BẬT" : "TẮT", ServerLog.getHeartbeatIntervalSeconds());
        ServerLog.command("======================================================================");
    }

    private void printOnlinePlayers() {
        var players = ClientManager.getPlayers();
        ServerLog.command("================ DANH SÁCH NGƯỜI CHƠI ONLINE (%d) ================", players.size());
        if (players.isEmpty()) {
            ServerLog.command("  (Hiện không có người chơi nào đang kết nối)");
        } else {
            for (Player p : players.values()) {
                if (p == null) {
                    continue;
                }
                String clsName = switch (p.getInfo().getClassPlayer()) {
                    case 0 -> "Kiếm Khách";
                    case 1 -> "Chiến Binh";
                    case 2 -> "Pháp Sư";
                    case 3 -> "Đấu Sĩ";
                    case 4 -> "Cung Thủ";
                    default -> "Tân Thủ";
                };
                int mapId = p.getLocation() != null && p.getLocation().getZone() != null && p.getLocation().getZone().getMap() != null
                        ? p.getLocation().getZone().getMap().getMapId() : -1;
                int zoneId = p.getLocation() != null && p.getLocation().getZone() != null
                        ? p.getLocation().getZone().getId() : -1;
                int x = p.getLocation() != null ? p.getLocation().getX() : 0;
                int y = p.getLocation() != null ? p.getLocation().getY() : 0;
                String ip = p.getSession() != null ? p.getSession().getIP() : "N/A";
                long xu = p.getInventory() != null ? p.getInventory().getXu() : 0;
                int luong = p.getInventory() != null ? p.getInventory().getLuong() : 0;

                ServerLog.command(" • [%d] %s | Cấp: %d | Phái: %s | Map: %d (Khu: %d) [%d,%d] | IP: %s | Xu: %,d | Lượng: %,d",
                        p.getIdPlayer(), p.getName(), p.getInfo().getLevel(), clsName, mapId, zoneId, x, y, ip, xu, luong);
            }
        }
        ServerLog.command("======================================================================");
    }

    private void handleKickCommand(String line) {
        String target = line.substring(4).trim();
        if (target.isEmpty()) {
            ServerLog.command("Cú pháp: kick <tên nhân vật hoặc ID>");
            return;
        }
        Player targetPlayer = null;
        try {
            short id = Short.parseShort(target);
            targetPlayer = ClientManager.getPlayer(id);
        } catch (NumberFormatException e) {
            targetPlayer = ClientManager.getPlayer(target);
        }
        if (targetPlayer != null && targetPlayer.getSession() != null) {
            String name = targetPlayer.getName();
            int id = targetPlayer.getIdPlayer();
            targetPlayer.getSession().disconnect("Bị Admin Console đá khỏi game (KICK)");
            ServerLog.command("Đã đá người chơi '%s' (ID: %d) khỏi máy chủ thành công.", name, id);
        } else {
            ServerLog.command("Không tìm thấy người chơi có tên hoặc ID: '%s'", target);
        }
    }

    private void handleBroadcastCommand(String line) {
        int spaceIdx = line.indexOf(' ');
        if (spaceIdx < 0 || spaceIdx >= line.length() - 1) {
            ServerLog.command("Cú pháp: %s <nội dung thông báo>", line.trim());
            return;
        }
        String msg = line.substring(spaceIdx + 1).trim();
        if (!msg.isEmpty()) {
            ChatService.instance.sendServerNotice(msg);
            ServerLog.command("Đã phát thanh toàn server: %s", msg);
        }
    }

    private void handleHeartbeatCommand(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length < 2) {
            ServerLog.command("Trạng thái Heartbeat: %s (Chu kỳ: %ds). Dùng: hb on | hb off | hb <số giây>",
                    ServerLog.isHeartbeatEnabled() ? "BẬT" : "TẮT", ServerLog.getHeartbeatIntervalSeconds());
            return;
        }
        String arg = parts[1].toLowerCase();
        if (arg.equals("on")) {
            ServerLog.setHeartbeatEnabled(true);
            ServerLog.command("Log nhịp tim (Heartbeat): ĐÃ BẬT.");
        } else if (arg.equals("off")) {
            ServerLog.setHeartbeatEnabled(false);
            ServerLog.command("Log nhịp tim (Heartbeat): ĐÃ TẮT.");
        } else {
            try {
                int sec = Integer.parseInt(arg);
                if (sec < 5) {
                    sec = 5;
                }
                ServerLog.setHeartbeatIntervalSeconds(sec);
                ServerLog.command("Đã cập nhật chu kỳ Heartbeat thành: %d giây.", sec);
            } catch (NumberFormatException e) {
                ServerLog.command("Cú pháp không hợp lệ. Dùng: hb on | hb off | hb <số giây>");
            }
        }
    }

    private void handleGcCommand() {
        Runtime rt = Runtime.getRuntime();
        long before = rt.totalMemory() - rt.freeMemory();
        System.gc();
        long after = rt.totalMemory() - rt.freeMemory();
        long freedKB = Math.max(0, (before - after) / 1024);
        ServerLog.command("Đã kích hoạt Garbage Collector. Giải phóng: ~%,d KB (~%.2f MB).", freedKB, freedKB / 1024.0);
    }

    private void closeServer() throws SQLException {
        ServerLog.warn("Đang lưu dữ liệu Clan & người chơi trước khi thoát...");
        ClanManager.saveDataClan();
        ClientManager.close();
        ServerLog.info("Đã hoàn tất lưu trữ. Máy chủ tắt hoàn toàn.");
        System.exit(0);
    }
}
