package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * Hệ thống Logging tập trung và giám sát hệ thống cho KPAH Server.
 * Sử dụng Native ANSI Escape Sequences tương thích toàn diện với Linux Terminal, SSH và Termux.
 * Tự động lọc sạch mã ANSI khi lưu trữ file log hàng ngày.
 */
public class ServerLog {

    // Native ANSI Escape Codes
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BRIGHT_BLACK = "\u001B[90m"; // Dark Gray
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_MAGENTA = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern ANSI_PATTERN = Pattern.compile("\\u001B\\[[;\\d]*m");

    private static final String LOG_DIR = "log/";
    private static LocalDate currentLogDate = null;
    private static BufferedWriter fileWriter = null;
    private static final Object FILE_LOCK = new Object();

    // Điều khiển nhịp tim Heartbeat từ Console
    private static volatile boolean heartbeatEnabled = true;
    private static volatile int heartbeatIntervalSeconds = 60;

    // Hàng đợi ghi file nền (Virtual Thread)
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>(50000);

    static {
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Thread.ofVirtual().name("ServerLog-Writer").start(() -> {
            while (true) {
                try {
                    String line = logQueue.take();
                    writeToFile(line);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    System.err.println("[ServerLog Writer Error] " + e.getMessage());
                }
            }
        });
    }

    public static boolean isHeartbeatEnabled() {
        return heartbeatEnabled;
    }

    public static void setHeartbeatEnabled(boolean enabled) {
        heartbeatEnabled = enabled;
    }

    public static int getHeartbeatIntervalSeconds() {
        return heartbeatIntervalSeconds;
    }

    public static void setHeartbeatIntervalSeconds(int seconds) {
        if (seconds < 5) {
            seconds = 5;
        }
        heartbeatIntervalSeconds = seconds;
    }

    public static String stripAnsi(String text) {
        if (text == null) {
            return "";
        }
        return ANSI_PATTERN.matcher(text).replaceAll("");
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

    private static void log(String colorCode, String tag, String message) {
        String time = now();
        
        // Console output với mã ANSI trực quan
        String consoleFormatted = String.format("%s[%s]%s %s[%-10s]%s %s",
                BRIGHT_BLACK, time, RESET,
                colorCode + BOLD, tag, RESET,
                message);
        System.out.println(consoleFormatted);

        // File output: Bóc tách sạch mã màu ANSI
        String fileFormatted = String.format("[%s] [%-10s] %s", time, tag, stripAnsi(message));
        logQueue.offer(fileFormatted);
    }

    public static void info(String message) {
        log(BRIGHT_GREEN, "INFO", message);
    }

    public static void info(String format, Object... args) {
        info(String.format(format, args));
    }

    public static void system(String message) {
        log(BRIGHT_CYAN, "SYSTEM", message);
    }

    public static void system(String format, Object... args) {
        system(String.format(format, args));
    }

    public static void network(String message) {
        log(CYAN, "NETWORK", message);
    }

    public static void network(String format, Object... args) {
        network(String.format(format, args));
    }

    public static void auth(String message) {
        log(BRIGHT_YELLOW, "AUTH", message);
    }

    public static void auth(String format, Object... args) {
        auth(String.format(format, args));
    }

    public static void command(String message) {
        log(BRIGHT_MAGENTA, "COMMAND", message);
    }

    public static void command(String format, Object... args) {
        command(String.format(format, args));
    }

    public static void levelUp(String charName, int charId, int newLevel) {
        String msg = String.format("Nhân vật %s%s%s (ID: %d) đã thăng cấp lên %sLv.%d%s!",
                BRIGHT_WHITE + BOLD, charName, RESET,
                charId,
                BRIGHT_GREEN + BOLD, newLevel, RESET);
        log(BRIGHT_GREEN, "LEVEL_UP", msg);
    }

    /**
     * Ghi nhận sự kiện ngắt kết nối với thông tin tài khoản, nhân vật và lý do chi tiết.
     */
    public static void disconnect(int sessionId, String username, String charName, String ip, String reason) {
        String u = (username != null && !username.isEmpty()) ? username : "N/A";
        String c = (charName != null && !charName.isEmpty()) ? charName : "N/A";
        String msg = String.format("Session #%d [User: %s | Char: %s | IP: %s] -> Lý do: %s",
                sessionId, u, c, ip, reason);
        log(MAGENTA, "DISCONNECT", msg);
    }

    /**
     * Ghi nhận log nhịp tim (Heartbeat) định kỳ theo dõi sức khỏe hệ thống.
     */
    public static void heartbeat(int onlinePlayers, int totalSessions, long usedMemMB, long totalMemMB, long maxMemMB, int threadCount, String uptime) {
        heartbeat(onlinePlayers, totalSessions, usedMemMB, totalMemMB, maxMemMB, threadCount, uptime, null);
    }

    public static void heartbeat(int onlinePlayers, int totalSessions, long usedMemMB, long totalMemMB, long maxMemMB, int threadCount, String uptime, List<String> onlineNames) {
        if (!heartbeatEnabled) {
            return;
        }
        int ramPercent = (int) (maxMemMB > 0 ? (usedMemMB * 100 / maxMemMB) : 0);
        String ramColor = ramPercent >= 85 ? BRIGHT_RED : (ramPercent >= 70 ? BRIGHT_YELLOW : BRIGHT_GREEN);

        StringBuilder sb = new StringBuilder();
        if (onlinePlayers > 0) {
            sb.append(BRIGHT_GREEN).append(BOLD).append("🟢 Online: ").append(onlinePlayers).append(" người").append(RESET);
            if (onlineNames != null && !onlineNames.isEmpty()) {
                sb.append(" [").append(String.join(", ", onlineNames)).append("]");
            }
            sb.append(" | ");
        } else {
            sb.append("Online: 0 | ");
        }
        sb.append("Sessions: ").append(totalSessions).append(" | ");
        sb.append("RAM: ").append(ramColor).append(usedMemMB).append("MB/").append(maxMemMB).append("MB (").append(ramPercent).append("%)").append(RESET).append(" | ");
        sb.append("Threads: ").append(threadCount).append(" | ");
        sb.append("Uptime: ").append(BRIGHT_CYAN).append(uptime).append(RESET);

        log(BRIGHT_BLUE, "HEARTBEAT", sb.toString());
    }

    public static void shop(String message) {
        log(CYAN, "SHOP", message);
    }

    public static void shop(String format, Object... args) {
        shop(String.format(format, args));
    }

    public static void combat(String message) {
        log(RED, "COMBAT", message);
    }

    public static void combat(String format, Object... args) {
        combat(String.format(format, args));
    }

    public static void quest(String message) {
        log(MAGENTA, "QUEST", message);
    }

    public static void quest(String format, Object... args) {
        quest(String.format(format, args));
    }

    public static void warn(String message) {
        log(BRIGHT_YELLOW, "WARN", message);
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
        log(BRIGHT_RED, "ERROR", fullMsg);
    }

    public static void error(String message) {
        error(message, null);
    }
}
