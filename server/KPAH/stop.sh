#!/data/data/com.termux/files/usr/bin/bash
echo "========================================="
echo "   KPAH SERVER PRODUCTION STOPPER        "
echo "========================================="

echo ">> Đang dừng KPAH Server và Playit..."
pkill -9 -f "server.Server"
pkill -9 -f playitd
rm -f "$PREFIX/tmp/playit.sock"
echo ">> Đã dừng thành công!"
