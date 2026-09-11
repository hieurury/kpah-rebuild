package classes;

import java.io.IOException;

public class MsgHandler {
	public static void onMessage(class_bi globalMsgHandler, class_abs msg) {
		try {
			switch (msg.a) {
			case 19: {
				if (class_acv.s != null && class_acv.s.q != null) {
					if (class_acv.s.q.bq == null || class_acv.s.q.bq.length < 256) {
						int[] newBq = new int[256];
						if (class_acv.s.q.bq != null) {
							System.arraycopy(class_acv.s.q.bq, 0, newBq, 0, class_acv.s.q.bq.length);
						}
						class_acv.s.q.bq = newBq;
					}
				}
				break;
			}
			case 22: {
				// USE_POTION / Đồng bộ HP-MP chuẩn xác, chống ghi đè MP bằng HP và chống chết oan khi MP = 0
				short entityId = msg.b().readShort();
				byte potionId = msg.b().readByte();
				short valueAdd = msg.b().readShort();
				int curVal = msg.b().readInt();
				byte extra = 0;
				try {
					extra = msg.b().readByte();
				} catch (Exception ignored) {}

				if (class_acv.s != null) {
					boolean isMp = (potionId == 4 || potionId == 5 || potionId == 6 || potionId == 23 || potionId == 24 || (extra != 1 && (potionId == 82 || potionId == 85 || potionId == 34)));
					boolean isHp = (potionId == 1 || potionId == 2 || potionId == 3 || potionId == 21 || potionId == 22 || (extra == 1 && (potionId == 82 || potionId == 85 || potionId == 34)));

					// Nếu entity là chính người chơi
					if (class_acv.s.q != null && class_acv.s.q.cG == entityId) {
						if (isMp) {
							// Cập nhật MP chính xác (cả khi tăng lẫn khi giảm, rút mana)
							class_acv.s.q.bz = Math.max(0, curVal);
							if (class_acv.s.q.by > 0 && class_acv.s.q.bz > class_acv.s.q.by) {
								class_acv.s.q.bz = class_acv.s.q.by;
							}
							if (valueAdd != 0) {
								Paint.addStatusPopup(Paint.POPUP_MP, valueAdd, class_acv.s.q.cK, class_acv.s.q.cL - 40);
							}
						} else if (isHp) {
							// Cập nhật HP chính xác
							class_acv.s.q.v = class_acv.s.q.t = Math.max(0, curVal);
							if (class_acv.s.q.w > 0 && class_acv.s.q.v > class_acv.s.q.w) {
								class_acv.s.q.v = class_acv.s.q.w;
							}
							if (curVal <= 0) {
								class_acv.s.q.cV = (byte) 3; // Chết
							}
							if (valueAdd != 0) {
								Paint.addStatusPopup(Paint.POPUP_HP, valueAdd, class_acv.s.q.cK, class_acv.s.q.cL - 30);
							}
						}
						return; // Đã xử lý chuẩn xác, chặn không cho class_abj cũ gán sai
					} else {
						// Entity khác trong map
						class_vh target = (class_vh) class_acv.s.b(entityId);
						if (target != null && target instanceof class_hw) {
							class_hw otherPlayer = (class_hw) target;
							if (isMp) {
								otherPlayer.bz = Math.max(0, curVal);
								if (valueAdd != 0) {
									Paint.addStatusPopup(Paint.POPUP_MP, valueAdd, otherPlayer.cK, otherPlayer.cL - 40);
								}
							} else if (isHp) {
								otherPlayer.v = otherPlayer.t = Math.max(0, curVal);
								if (curVal <= 0) {
									otherPlayer.cV = (byte) 3;
								}
								if (valueAdd != 0) {
									Paint.addStatusPopup(Paint.POPUP_HP, valueAdd, otherPlayer.cK, otherPlayer.cL - 30);
								}
							}
							return;
						}
					}
				}
				return;
			}
			case 30: {
				short userId = msg.b().readShort();
				short percenLv = msg.b().readShort();
				int expPlus = msg.b().readInt();
				class_acv.s.a(userId, percenLv, expPlus);
				if (userId == class_acv.s.q.cG) {
					MainCharInfo.expPlus += expPlus;
				}
				return;
			}
			case 89: {
				// BUFF_ATTACK: targetId(short), cat(byte), b2(byte), s2(short hpSub), b3(byte), b4(byte), dur(byte)
				short targetId = msg.b().readShort();
				byte cat = msg.b().readByte();
				byte b2 = msg.b().readByte();
				short s2 = msg.b().readShort();
				byte b3 = msg.b().readByte();
				byte b4 = msg.b().readByte();
				byte dur = -1;
				try {
					dur = msg.b().readByte();
				} catch (Exception ignored) {
				}

				if (b4 == -1) {
					// DoT tick sát thương độc hoặc instant poison bonus damage
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								if (s2 > 0) {
									Paint.addStatusPopup(Paint.POPUP_POISON, -s2, entity.cK, entity.cL - 40);
								}
								if (cat == 1 && entity instanceof class_bb) {
									((class_bb) entity).d((int) s2);
									if (((class_bb) entity).v <= 0) {
										((class_bb) entity).cE = true;
									}
								} else if (entity instanceof class_hw) {
									((class_hw) entity).v -= s2;
									if (((class_hw) entity).v <= 0) {
										((class_hw) entity).cV = (byte) 3;
									}
								}
								break;
							}
						}
					}
					return;
				} else if (b4 == 4) {
					// Áp dụng trúng độc (BUFF_DOC_TO)
					int sec = dur > 0 ? dur : 10;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_zx eff = new class_zx(entity.cK, entity.cL, 22);
								eff.isDebuff = true;
								eff.a(sec);
								entity.a(eff);
								entity.dg = System.currentTimeMillis();
								entity.df = s2;
								entity.dh = b2;
								break;
							}
						}
					}
					if (class_acv.s != null && class_acv.s.q != null && class_acv.s.q.cG == targetId) {
						MainCharInfo.poisonEndTime = System.currentTimeMillis() + (long) sec * 1000L;
					}
					return;
				} else if (b4 == 3) {
					// Áp dụng Choáng (BUFF_STUN)
					int sec = dur > 0 ? dur : 3;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								entity.cW = true;
								entity.cZ = System.currentTimeMillis() + (long) (sec * 1000L);
								class_zx eff = new class_zx(entity.cK, entity.cL, 19);
								eff.a(sec);
								entity.a(eff);
								break;
							}
						}
					}
					return;
				} else if (b4 == 2) {
					// Hút MP
					if (b3 == 7 && s2 > 0 && class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_acv.s.a("-" + s2, 3, (int) entity.cK, entity.cL - 40, 0, -1);
								if (entity instanceof class_hw) {
									((class_hw) entity).bz -= s2;
									if (((class_hw) entity).bz < 0) {
										((class_hw) entity).bz = 0;
									}
								}
								break;
							}
						}
					}
					return;
				}
				return;
			}
			case -115: {
				byte count = msg.b().readByte();
				java.util.Vector list = new java.util.Vector();
				long now = System.currentTimeMillis();
				for (int i = 0; i < count; i++) {
					String name = msg.b().readUTF();
					int secLeft = msg.b().readInt();
					boolean isDebuff = msg.b().readBoolean();
					list.addElement(new MainCharInfo.CustomBuff(name, now + (long) secLeft * 1000L, isDebuff));
				}
				MainCharInfo.setCustomBuffs(list);
				return;
			}
			case -117: {
				byte classChar = msg.b().readByte();
				byte equipType = msg.b().readByte();
				short count = msg.b().readShort();
				java.util.Vector list = new java.util.Vector();
				for (int i = 0; i < count; i++) {
					short id = msg.b().readShort();
					String name = msg.b().readUTF();
					byte level = msg.b().readByte();
					byte type = msg.b().readByte();
					short icon = msg.b().readShort();
					byte attrLen = msg.b().readByte();
					short[] attrs = new short[attrLen];
					for (int a = 0; a < attrLen; a++) {
						attrs[a] = msg.b().readShort();
					}
					list.addElement(new CraftShopScreen.CraftItem(id, name, level, type, icon, attrs));
				}
				CraftShopScreen.show(classChar, equipType, list);
				return;
			}
			}
			try {
				globalMsgHandler.a(msg);
			} catch (Exception ex) {
				System.err.println("Error when processing Cmd " + msg.a + ": " + ex.getMessage());
				ex.printStackTrace();
			}

			if (msg.a == 25 || msg.a == 4 || msg.a == 19 || msg.a == 11) {
				updateMaterialVisuals();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Thiết lập hào quang vàng (cấp 5) cho 10 loại nguyên liệu sơ cấp & cao cấp mới
	 */
	public static void updateMaterialVisuals() {
		try {
			if (class_yi.e != null) {
				for (int i = 0; i < class_yi.e.size(); i++) {
					class_xv item = (class_xv) class_yi.e.elementAt(i);
					if (item != null) {
						short id = item.o;
						// Sơ cấp: 68 (Vải), 75 (Sắt), 82 (Ngọc), 89 (Gỗ thường), 96 (Da mềm)
						// Cao cấp: 103 (Tơ lụa), 110 (Bạc), 117 (Thủy tinh), 124 (Gỗ sưa), 131 (Da cứng)
						if (id == 68 || id == 75 || id == 82 || id == 89 || id == 96
								|| id == 103 || id == 110 || id == 117 || id == 124 || id == 131) {
							item.s = 1; // Hào quang vàng phẩm cấp 5
						}
					}
				}
			}
		} catch (Exception ignored) {
		}
	}
}
