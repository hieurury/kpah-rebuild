import struct

def xor_bytes(data, key):
    res = bytearray(data)
    for i in range(len(res)):
        res[i] ^= key[i % len(key)]
    return bytes(res)

key = b'NguyenVanMinh'

with open('res/eff.sh', 'rb') as f:
    raw = f.read()

count = raw[0]
offset = 1
files = []

for _ in range(count):
    namelen = raw[offset]
    offset += 1
    name_xored = raw[offset:offset+namelen]
    name = xor_bytes(name_xored, key)
    offset += namelen
    size = struct.unpack('>H', raw[offset:offset+2])[0]
    offset += 2
    files.append({'name': name, 'size': size})

data_xored = raw[offset:]
data = xor_bytes(data_xored, key)

with open('res/explosion.png', 'rb') as f:
    explosion_data = f.read()

files.append({'name': b'explosion', 'size': len(explosion_data)})
data += explosion_data

out = bytearray([len(files)])
for f in files:
    name_bytes = f['name']
    out.append(len(name_bytes))
    out.extend(xor_bytes(name_bytes, key))
    out.extend(struct.pack('>H', f['size']))

out.extend(xor_bytes(data, key))

with open('res/eff.sh', 'wb') as f:
    f.write(out)
