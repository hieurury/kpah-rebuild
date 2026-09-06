import struct
import zlib

def make_png(width, height):
    png = b'\x89PNG\r\n\x1a\n'
    ihdr_data = struct.pack('!IIBBBBB', width, height, 8, 6, 0, 0, 0)
    png += struct.pack('!I', len(ihdr_data)) + b'IHDR' + ihdr_data + struct.pack('!I', zlib.crc32(b'IHDR' + ihdr_data) & 0xffffffff)
    raw_data = b'\x00' + b'\x00\x00\x00\x00' * width
    raw_data *= height
    compressed_data = zlib.compress(raw_data)
    png += struct.pack('!I', len(compressed_data)) + b'IDAT' + compressed_data + struct.pack('!I', zlib.crc32(b'IDAT' + compressed_data) & 0xffffffff)
    png += struct.pack('!I', 0) + b'IEND' + struct.pack('!I', zlib.crc32(b'IEND') & 0xffffffff)
    return png

with open('app/res/explosion.png', 'wb') as f:
    f.write(make_png(24, 120))
