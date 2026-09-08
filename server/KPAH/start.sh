#!/data/data/com.termux/files/usr/bin/bash
DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$DIR"

echo "========================================="
echo "   KPAH SERVER PRODUCTION LAUNCHER       "
echo "========================================="

# 0. Kích hoạt wake lock ngăn Android đưa CPU vào chế độ ngủ sâu khi tắt màn hình
if command -v termux-wake-lock > /dev/null 2>&1; then
    termux-wake-lock
    echo ">> [0/3] Đã kích hoạt termux-wake-lock (chống ngủ sâu/ngắt mạng khi tắt màn hình)."
fi

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

# 2. Đảm bảo Bore TCP tunnel daemon đang chạy
if ! pgrep -f "bore local 19129" > /dev/null 2>&1; then
    echo ">> [2/3] Đang khởi động Bore TCP tunnel (bore.pub:19129)..."
    nohup bore local 19129 --to 159.223.110.159 -p 19129 > ~/bore.log 2>&1 &
    sleep 2
    if pgrep -f "bore local 19129" > /dev/null 2>&1; then
        echo ">> [2/3] Bore TCP tunnel đang hoạt động (bore.pub:19129)."
    else
        echo ">> [CẢNH BÁO] Bore tunnel không khởi động được! Chi tiết lỗi (~/bore.log):"
        cat ~/bore.log 2>/dev/null
    fi
else
    echo ">> [2/3] Bore TCP tunnel đang chạy."
fi

# 3. Khởi động KPAH Game Server
echo ">> [3/3] Khởi động KPAH Server (Port 19129)..."
java -Xms128m -Xmx512m -Djava.net.preferIPv4Stack=true --enable-preview -cp "lib/*:dist/KPAH.jar" server.Server


