package network;

import services.Service;
import manager.ClientManager;
import player.Player;
import daos.PlayerDAO;
import database.HikariCP;
import database.ResultSetImpl;
import interfaces.IMessageSendCollect;
import interfaces.ISession;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Synchronized;
import manager.ExecutorVirtualThread;
import manager.Settings;
import org.json.JSONArray;
import org.json.JSONException;
import services.LoginService;
import utils.CommandMessage;
import utils.Logger;
import utils.Printer;
import utils.Util;

import java.util.concurrent.atomic.AtomicInteger;
import utils.ServerLog;

public class Session implements ISession {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private int id;
    private Socket socket;
    private Runnable tSender;
    private Runnable tCollector;
    private boolean connected;
    private Sender sender;
    private Collector collector;
    private boolean sendKeyComplete;
    public String ip;
    private byte curR;
    private byte curW;
    private short version;
    private String username;
    private int userId;
    private byte zoomLevel;
    private int distanceLoad;
    private boolean isAdmin;
    public Player player;
    public List<Player> listChar = new ArrayList<>();

    public Session(Socket sc) throws SocketException {
        this.id = ID_GENERATOR.getAndIncrement();
        this.socket = sc;
        this.socket.setKeepAlive(true);
        try {
            this.socket.setSendBufferSize(1048576);
            this.socket.setReceiveBufferSize(1048576);
        } catch (SocketException e) {
        }
        this.sendKeyComplete = false;
        this.connected = true;
        this.ip = ((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress().toString().replace("/", "");
    }

    @Override
    public int getDistanceLoad() {
        return this.distanceLoad;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void addChar(Player p) {
        listChar.add(p);
    }

    @Override
    public void setPlayer(Player p) {
        this.player = p;
        ClientManager.joinPlayer(p);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void initThreadSession() {
        this.tSender = ((this.sender != null) ? this.sender.setSocket(this.socket) : (this.sender = new Sender(this, this.socket)));
        this.tCollector = ((this.collector != null) ? this.collector.setSocket(this.socket) : (this.collector = new Collector(this, this.socket)));
        // Wire collector với sender để collector có thể update lastTimeActivity khi nhận tin từ client
        this.collector.setSender(this.sender);
    }

    @Override
    public void sendMessage(final Message msg) {
        if (this.sender != null) {
            this.sender.sendMessage(msg);
        }
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public List<Player> getListChar() {
        return this.listChar;
    }

    @Override
    public void doSendMessage(final Message msg) {
        try {
            this.sender.doSendMessage(msg);
        } catch (Exception e) {
        }
    }

    @Override
    public void sendKey() throws IOException {
        Message m = new Message(CommandMessage.REQUEST_KEY);
        m.writer().writeByte(Settings.KEYS.length);
        m.writer().writeByte(Settings.KEYS[0]);
        for (int i = 1; i < Settings.KEYS.length; i++) {
            m.writer().writeByte(Settings.KEYS[i] ^ Settings.KEYS[i - 1]);
        }
        this.doSendMessage(m);
        sendKeyComplete = true;
        this.startSend();
    }

    @Override
    public void disconnect(String reason) {
        if (this.connected) {
            this.connected = false;
            String charName = (this.player != null) ? this.player.getName() : null;
            ServerLog.disconnect(this.id, this.username, charName, this.ip, reason);
            if (this.sender != null) {
                this.sender.close();
            }
            if (this.collector != null) {
                this.collector.close();
            }
            try {
                if (this.socket != null && !this.socket.isClosed()) {
                    this.socket.close();
                }
                this.dispose();
            } catch (IOException | SQLException e) {
            }
        }
    }

    @Override
    public void disconnect() {
        disconnect("Không rõ lý do");
    }

    @Override
    public Runnable update() {
        return () -> {
            try {
                while (connected) {
                    if (player == null || !player.getSundry().isInGame()) {
                        if (Util.canDoWithTime(sender.lastTimeActivity, Settings.MILISECOND_WAIT_KICK_SESSION)) {
                            this.disconnect("IDLE_SESSION_TIMEOUT (Chưa chọn nhân vật quá 60s)");
                        }
                    } else if (player.getSundry().isInGame()) {
                        if (Util.canDoWithTime(sender.lastTimeActivity, Settings.MILISECOND_WAIT_KICK_PLAYER)) {
                            this.disconnect("IDLE_PLAYER_TIMEOUT (Treo máy không hoạt động quá 10 phút)");
                        }
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (Exception e) {
                this.disconnect("SESSION_UPDATE_EXCEPTION: " + e.getMessage());
            }
        };
    }

    public void dispose() throws IOException, SQLException {
        if (this.sender != null) {
            this.sender.dispose();
        }
        if (this.collector != null) {
            this.collector.dispose();
        }
        this.socket = null;
        this.sender = null;
        this.collector = null;
        this.tSender = null;
        this.tCollector = null;
        this.ip = null;
        if (this.player != null) {
            this.player.dispose();
        }
        ClientManager.kickClient(this);
    }

    @Override
    public void setZoomLevel(Message m) {
        try {
            this.zoomLevel = m.reader().readByte();
        } catch (IOException e) {
        }
    }

    @Override
    public byte getZoomLevel() {
        return this.zoomLevel;
    }

    @Override
    public short getVersion() {
        return version;
    }

    @Override
    public void setVersion(short r) {
        this.version = r;
    }

    @Override
    public int getUserID() {
        return this.userId;
    }

    @Override
    public void loginAccount(Message msg) {
        try {
            this.username = msg.reader().readUTF();
            String pass = msg.reader().readUTF();
            this.setVersion(Short.parseShort(msg.reader().readUTF().replace(".", "")));
            msg.reader().readUTF();
            msg.reader().readUTF();
            msg.reader().readUTF();
            msg.reader().readUTF();
            msg.reader().readByte();
            short w = msg.reader().readShort();
            distanceLoad = w / 2 + 120;
            msg.reader().readByte();
            msg.reader().readByte();
            ResultSetImpl rs = HikariCP.executeQuery("SELECT * FROM `users` WHERE `username` = ? and password= ? LIMIT 1;", username, pass);
            if (!rs.next()) {
                ServerLog.auth("Đăng nhập thất bại: user '%s' từ IP %s (Sai tài khoản/mật khẩu)", username, this.ip);
                Service.instance.sendLogOut(this, "Tài khoản hoặc mật khẩu không chính xác! Vui lòng thử lại!");
                return;
            }
            userId = rs.getInt("id");
            isAdmin = rs.getBoolean("isAdmin");
            Player playerCheck = ClientManager.getPlayerByUserID(userId);
            if (playerCheck != null) {
                ServerLog.auth("Đăng nhập trùng lặp: user '%s' (ID %d) từ IP %s -> Ngắt phiên cũ", username, userId, this.ip);
                Service.instance.sendLogOut(playerCheck.getSession(), "Mất kết nối.");
                playerCheck.getSession().disconnect("DUPLICATE_LOGIN (Tài khoản được đăng nhập từ nơi khác)");
                Service.instance.sendLogOut(this, "Tài khoản đang đăng nhập ở nơi khác!");
                this.disconnect("DUPLICATE_LOGIN_REJECT (Tài khoản đang đăng nhập ở nơi khác)");
                return;
            }
            emptyListChar(-1);
            JSONArray js = new JSONArray(rs.getString("chars"));
            for (int i = 0; i < js.length(); i++) {
                Player pl = PlayerDAO.setupPlayer(js.getInt(i));
                if (pl != null) {
                    listChar.add(pl);
                }
            }
            rs.close();
            ServerLog.auth("Đăng nhập thành công: user '%s' (ID %d) từ IP %s (%d nhân vật)", username, userId, this.ip, listChar.size());
            LoginService.instance.sendListChar(this);
        } catch (IOException | NumberFormatException | SQLException | JSONException e) {
            ServerLog.error("Lỗi đăng nhập tài khoản " + username, e);
            Logger.logError("Lỗi Login", e);
            this.disconnect("LOGIN_EXCEPTION: " + e.getMessage());
        }
    }

    @Override
    @Synchronized
    public void emptyListChar(int idExcept) throws SQLException {
        List<Player> playersToRemove = new ArrayList<>();
        for (Player pl : listChar) {
            if (pl != null && pl.getIdDatabase() != idExcept) {
                try {
                    pl.dispose();
                } catch (IOException ex) {
                }
                playersToRemove.add(pl);
            }
        }
        listChar.removeAll(playersToRemove);
        playersToRemove.clear();
    }

    @Override
    public void updateChar() throws SQLException {
        JSONArray js = new JSONArray();
        for (int i = 0; i < this.listChar.size(); i++) {
            js.put(this.listChar.get(i).getIdDatabase());
        }
        HikariCP.executeUpdate("UPDATE `users` SET `chars` = '" + js.toString() + "' WHERE `username` = '" + this.username + "'");
    }

    @Override
    @Synchronized
    public void reloadChar(int id) {
        try {
            int indexRemove = -1;
            for (int i = 0; i < listChar.size(); i++) {
                Player plRemove = listChar.get(i);
                if (plRemove != null && plRemove.getIdDatabase() == id) {
                    plRemove.dispose();
                    listChar.remove(plRemove);
                    indexRemove = i;
                }
            }
            if (indexRemove != -1) {
                Player newPlayer = PlayerDAO.setupPlayer(id);
                if (newPlayer != null) {
                    listChar.add(indexRemove, newPlayer);
                }
            }
        } catch (IOException | SQLException | JSONException e) {
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setCurW(byte r) {
        this.curW = r;
    }

    @Override
    public void setCurR(byte r) {
        this.curR = r;
    }

    @Override
    public String getIP() {
        return this.ip;
    }

    @Override
    public byte getCurW() {
        return this.curW;
    }

    @Override
    public byte getCurR() {
        return this.curR;
    }

    @Override
    public boolean isSendKeyComplete() {
        return sendKeyComplete;
    }

    @Override
    public ISession setSendCollect(IMessageSendCollect collect) {
        this.sender.setSend(collect);
        this.collector.setCollect(collect);
        return this;
    }

    @Override
    public ISession setMessageHandler(MessageHandler handler) {
        this.collector.setMessageHandler(handler);
        return this;
    }

    @Override
    public ISession startCollect() {
        ExecutorVirtualThread.submitThreadSession(this.tCollector);
        return this;
    }

    @Override
    public ISession startSend() {
        ExecutorVirtualThread.submitThreadSession(this.tSender);
        return this;
    }

    @Override
    public Player findPlayer(int id) throws SQLException {
        int result = HikariCP.executeExist(String.format("SELECT EXISTS(SELECT * FROM `players` WHERE `id` = '%s')", id));
        if (result == 0) {
            return null;
        }
        return listChar.stream().filter(p -> p != null && p.getIdDatabase() == id).findFirst().orElse(null);
    }
}
