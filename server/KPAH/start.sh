#!/data/data/com.termux/files/usr/bin/bash
# Đảm bảo MariaDB đang chạy
mysql -u root -pkpah -e "SELECT 1;" > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo ">> MariaDB chưa chạy, đang khởi động..."
    mariadbd-safe --datadir=$PREFIX/var/lib/mysql &
    sleep 5
fi

cd "$(dirname "$0")"
echo ">> Khởi động KPAH Server..."
java --enable-preview -cp "lib/*:dist/KPAH.jar" server.Server
