package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Hệ thống Logging tập trung và giám sát hệ thống cho KPAH Server.
 * Hỗ trợ ghi log đồng thời ra Console (màu ANSI) và File log xoay vòng hàng ngày.
 */
public class ServerLog {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String LOG_DIR = "log/";
    private static LocalDate currentLogDate = null;
    private static BufferedWriter fileWriter = null;
    private static final Object FILE_LOCK = new Object();

    // Hàng đợi ghi file nền để không làm chậm luồng xử lý game/mạng
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>(50000);

    static {
        // Đảm bảo thư mục log tồn tại
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Khởi chạy luồng ghi file nền (Virtual Thread)
        Thread.ofVirtual().name("ServerLog-Writer").start(() -> {
            while (true) {
                try {
                    String line = logQueue.take();
                    writeToFile(line);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    // Fallback console nếu ghi file lỗi
                    System.err.println("[ServerLog Writer Error] " + e.getMessage());
                }
            }
        });
    }

    private static String now() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }

    private static void writeToFile(String line) {
        synchronized (FILE_LOCK) {
            try {
                LocalDate today = LocalDate.now();
                if (fileWriter == null || currentLogDate == null || !currentLogDate.equals(today)) {
                    if (fileWriter != null) {
                        try {
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (Exception ignored) {
                        }
                    }
                    currentLogDate = today;
                    String filename = LOG_DIR + "server_" + today.format(DATE_FORMATTER) + ".log";
                    fileWriter = new BufferedWriter(new FileWriter(filename, true));
                }
                fileWriter.write(line);
                fileWriter.newLine();
                fileWriter.flush();
            } catch (Exception ex) {
                System.err.println("[ServerLog Write Error] " + ex.getMessage());
            }
        }
    }

    private static void log(Ansi.Color color, String tag, String message) {
        String time = now();
        String formatted = String.format("[%s] [%-10s] %s", time, tag, message);

        // 1. In ra Console có màu
        try {
            System.out.println(ansi().fg(color).a(formatted).reset());
        } catch (Exception e) {
            System.out.println(formatted);
        }

        // 2. Đưa vào hàng đợi ghi file (không màu)
        logQueue.offer(formatted);
    }

    public static void info(String message) {
        log(Ansi.Color.GREEN, "INFO", message);
    }

    public static void info(String format, Object... args) {
        info(String.format(format, args));
    }

    public static void network(String message) {
        log(Ansi.Color.CYAN, "NETWORK", message);
    }

    public static void network(String format, Object... args) {
        network(String.format(format, args));
    }

    public static void auth(String message) {
        log(Ansi.Color.YELLOW, "AUTH", message);
    }

    public static void auth(String format, Object... args) {
        auth(String.format(format, args));
    }

    /**
     * Ghi nhận sự kiện ngắt kết nối với thông tin tài khoản, nhân vật và lý do chi tiết.
     */
    public static void disconnect(int sessionId, String username, String charName, String ip, String reason) {
        String u = (username != null && !username.isEmpty()) ? username : "N/A";
        String c = (charName != null && !charName.isEmpty()) ? charName : "N/A";
        String msg = String.format("Session #%d [User: %s | Char: %s | IP: %s] Disconnected -> Lý do: %s",
                sessionId, u, c, ip, reason);
        log(Ansi.Color.MAGENTA, "DISCONNECT", msg);
    }

    /**
     * Ghi nhận log nhịp tim (Heartbeat) định kỳ theo dõi toàn bộ sức khỏe hệ thống.
     */
    public static void heartbeat(int onlinePlayers, int totalSessions, long usedMemMB, long totalMemMB, long maxMemMB, int threadCount, String uptime) {
        String msg = String.format("Online: %d players | Sessions: %d | RAM: %dMB / %dMB (Max: %dMB) | Active Threads: %d | Uptime: %s",
                onlinePlayers, totalSessions, usedMemMB, totalMemMB, maxMemMB, threadCount, uptime);
        log(Ansi.Color.BLUE, "HEARTBEAT", msg);
    }

    public static void warn(String message) {
        log(Ansi.Color.YELLOW, "WARN", message);
    }

    public static void warn(String format, Object... args) {
        warn(String.format(format, args));
    }

    public static void error(String message, Throwable t) {
        StringWriter sw = new StringWriter();
        if (t != null) {
            t.printStackTrace(new PrintWriter(sw));
        }
        String fullMsg = (t != null) ? (message + "\n" + sw.toString()) : message;
        log(Ansi.Color.RED, "ERROR", fullMsg);
    }

    public static void error(String message) {
        error(message, null);
    }
}
