#!/data/data/com.termux/files/usr/bin/bash
echo "========================================="
echo "   KPAH SERVER PRODUCTION STOPPER        "
echo "========================================="

echo ">> Đang dừng KPAH Server và Bore/Playit..."
pkill -9 -f "server.Server"
pkill -9 -f bore
pkill -9 -f playitd
rm -f "$PREFIX/tmp/playit.sock"
if command -v termux-wake-unlock > /dev/null 2>&1; then
    termux-wake-unlock
fi
echo ">> Đã dừng thành công!"
