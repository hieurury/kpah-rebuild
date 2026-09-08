package server;

import manager.Manager;
import network.Session;
import manager.ClientManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Scanner;
import manager.ClanManager;
import manager.ExecutorVirtualThread;
import manager.Settings;
import manager.TopManager;
import network.MessageHandler;
import network.MessageSendCollect;
import org.fusesource.jansi.AnsiConsole;
import utils.Logger;
import utils.Printer;

import utils.ServerLog;
import java.util.concurrent.TimeUnit;

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
            Printer.printGreen("Listen Port " + Settings.PORT_SERVER);
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
            Printer.printRed("Error occurred in server run");
        } finally {
            try {
                if (serverChannel != null) {
                    serverChannel.close();
                }
            } catch (Exception e) {
                Printer.printRed("Error closing server channel.");
            }
            ExecutorVirtualThread.shutdownServer();
            ServerLog.info("Server Socket Channel đã đóng.");
        }
    }

    private void startHeartbeat() {
        Thread.ofVirtual().name("Server-Heartbeat").start(() -> {
            while (!isBaoTri) {
                try {
                    TimeUnit.SECONDS.sleep(60);
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

                    ServerLog.heartbeat(onlinePlayers, totalSessions, usedMem, totalMem, maxMem, activeThreads, uptimeStr);
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
            Printer.printGreen("Accept IpAddress " + session.getIP());
            
            ClientManager.joinClient(session);
        } catch (IOException e) {
            ServerLog.error("Lỗi khởi tạo Client Session", e);
            Printer.printRed("Error Handling Client");
            Logger.logError("Lỗi Handle Client", e);
        }
    }

    private void activeCommandLine() {
        Thread.ofVirtual().name("Server-Console").start(() -> {
            try {
                Scanner sc = new Scanner(System.in);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    switch (line.toLowerCase()) {
                        case "baotri" -> {
                            ServerLog.warn("Nhận lệnh bảo trì từ Console. Đang dừng máy chủ...");
                            isBaoTri = true;
                            closeServer();
                        }
                        case "status" -> {
                            Runtime rt = Runtime.getRuntime();
                            long used = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
                            long total = rt.totalMemory() / (1024 * 1024);
                            long max = rt.maxMemory() / (1024 * 1024);
                            ServerLog.info("=== HỆ THỐNG ===");
                            ServerLog.info("Online Players: %d | Total Sessions: %d", ClientManager.getPlayers().size(), ClientManager.getClients().size());
                            ServerLog.info("RAM Used: %dMB / %dMB (Max: %dMB)", used, total, max);
                            ServerLog.info("Active Threads: %d", Thread.activeCount());
                        }
                        case "thread" ->
                            ServerLog.info("Thread count: %d", Thread.activeCount());
                        case "player" ->
                            ServerLog.info("Player in game: %d", ClientManager.getPlayers().size());
                        case "session" ->
                            ServerLog.info("Session connect: %d", ClientManager.getClients().size());
                        case "listplayer" -> {
                            ServerLog.info("Danh sách người chơi online (%d):", ClientManager.getPlayers().size());
                            ClientManager.getPlayers().values().forEach(p -> {
                                if (p != null) {
                                    ServerLog.info(" - %s (Lv: %d, Map: %d, IP: %s)",
                                            p.getName(), p.getInfo().getLevel(),
                                            p.getLocation().getZone().getMap().getMapId(),
                                            p.getSession() != null ? p.getSession().getIP() : "N/A");
                                }
                            });
                        }
                        case "gc" -> {
                            long before = Runtime.getRuntime().freeMemory();
                            System.gc();
                            long after = Runtime.getRuntime().freeMemory();
                            ServerLog.info("Đã chạy Garbage Collector. Giải phóng: ~%d KB", Math.max(0, (after - before) / 1024));
                        }
                    }
                }
            } catch (Exception e) {
                Logger.logError("Lỗi command line", e);
            }
        });
    }

    private void closeServer() throws SQLException {
        ServerLog.warn("Đang lưu dữ liệu Clan & người chơi trước khi thoát...");
        ClanManager.saveDataClan();
        ClientManager.close();
        ServerLog.info("Đã hoàn tất lưu trữ. Máy chủ tắt hoàn toàn.");
        System.exit(0);
    }
}
