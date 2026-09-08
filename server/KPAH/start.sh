#!/data/data/com.termux/files/usr/bin/bash
DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$DIR"

echo "========================================="
echo "   KPAH SERVER PRODUCTION LAUNCHER       "
echo "========================================="

# 1. Đảm bảo MariaDB đang chạy
mysql -u root -pkpah -e "SELECT 1;" > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo ">> [1/3] MariaDB chưa chạy, đang khởi động..."
    mariadbd-safe --datadir=$PREFIX/var/lib/mysql > /dev/null 2>&1 &
    for i in {1..10}; do
        sleep 1
        mysql -u root -pkpah -e "SELECT 1;" > /dev/null 2>&1 && break
    done
    echo ">> [1/3] MariaDB đã sẵn sàng."
else
    echo ">> [1/3] MariaDB đang chạy."
fi

# 2. Đảm bảo Playit tunnel daemon đang chạy
if ! pgrep -f "playitd" > /dev/null 2>&1; then
    echo ">> [2/3] Đang khởi động Playit tunnel..."
    rm -f "$PREFIX/tmp/playit.sock"
    mkdir -p ~/.config/playit_gg
    nohup playitd --socket-path "$PREFIX/tmp/playit.sock" --secret 76d1359294b8f50ee87953958b07de6a1194b6c569bc0a4d7c64636eaf967a65 < /dev/null > ~/playit.log 2>&1 &
    sleep 3
    if pgrep -f "playitd" > /dev/null 2>&1; then
        echo ">> [2/3] Playit tunnel đang hoạt động (practicing-achieve.tun.ply.gg:50758)."
    else
        echo ">> [CẢNH BÁO] Playit tunnel không khởi động được! Chi tiết lỗi (~/playit.log):"
        cat ~/playit.log 2>/dev/null
    fi
else
    echo ">> [2/3] Playit tunnel đang chạy."
fi

# 3. Khởi động KPAH Game Server
echo ">> [3/3] Khởi động KPAH Server (Port 19129)..."
java --enable-preview -cp "lib/*:dist/KPAH.jar" server.Server

