package classes;

import java.io.IOException;

public class MsgHandler {
	public static void onMessage(class_bi globalMsgHandler, class_abs msg) {
		try {
			switch (msg.a) {
			case 10: {
				// MONSTER_ATTACK_PLAYER: Đạn đỏ cho quái cận chiến (bullet 21), đạn gốc cho quái đánh xa (bullet 20)
				short mobId = msg.b().readShort();
				short targetId = msg.b().readShort();
				int damage = msg.b().readInt();
				int targetHp = msg.b().readInt();

				handleMonsterAttack(mobId, targetId, damage, targetHp);
				return;
			}
			case 16:
			case 19: {
				if (class_hw.Y < 256) {
					class_hw.Y = 256;
				}
				if (class_sc.l == null || class_sc.l.length < 256) {
					class_ub[] newL = new class_ub[256];
					if (class_sc.l != null) {
						System.arraycopy(class_sc.l, 0, newL, 0, class_sc.l.length);
					}
					for (int i = 0; i < 256; i++) {
						if (newL[i] == null) {
							newL[i] = new class_ub();
							newL[i].d = (short) i;
							newL[i].g = "";
							newL[i].h = "";
						}
					}
					class_sc.l = newL;
				} else {
					for (int i = 0; i < class_sc.l.length; i++) {
						if (class_sc.l[i] == null) {
							class_sc.l[i] = new class_ub();
							class_sc.l[i].d = (short) i;
							class_sc.l[i].g = "";
							class_sc.l[i].h = "";
						}
					}
				}
				if (class_sc.l[106] != null && (class_sc.l[106].g == null || class_sc.l[106].g.length() == 0)) {
					class_sc.l[106].e = 68;
					class_sc.l[106].g = "Rương Tinh Anh (Bậc 1)\nMở nhận: Lượng, Tinh anh huyết Sơ Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv1-9.";
				}
				if (class_sc.l[160] != null && (class_sc.l[160].g == null || class_sc.l[160].g.length() == 0)) {
					class_sc.l[160].e = 68;
					class_sc.l[160].g = "Rương Tinh Anh (Bậc 2)\nMở nhận: Lượng, Tinh anh huyết Trung Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv10-19.";
				}
				if (class_sc.l[161] != null && (class_sc.l[161].g == null || class_sc.l[161].g.length() == 0)) {
					class_sc.l[161].e = 67;
					class_sc.l[161].g = "Rương Tinh Anh (Bậc 3)\nMở nhận: Lượng, Tinh anh huyết Cao Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv20-29.";
				}
				if (class_sc.l[162] != null && (class_sc.l[162].g == null || class_sc.l[162].g.length() == 0)) {
					class_sc.l[162].e = 67;
					class_sc.l[162].g = "Rương Tinh Anh (Bậc 4)\nMở nhận: Lượng, Tinh anh huyết Siêu Cấp, Bình thuốc, Nguyên liệu, Vũ khí Lv30-35.";
				}
				// Khóa hồi chiêu 10 giây cho toàn bộ bình HP và MP trên client
				int[] allHpPots = {1, 2, 3, 21, 22, 93, 94};
				for (int i = 0; i < allHpPots.length; i++) {
					if (allHpPots[i] < class_sc.l.length && class_sc.l[allHpPots[i]] != null) {
						class_sc.l[allHpPots[i]].c = 10000;
					}
				}
				int[] allMpPots = {4, 5, 6, 23, 24, 95, 96};
				for (int i = 0; i < allMpPots.length; i++) {
					if (allMpPots[i] < class_sc.l.length && class_sc.l[allMpPots[i]] != null) {
						class_sc.l[allMpPots[i]].c = 10000;
					}
				}
				if (class_acv.s != null && class_acv.s.q != null) {
					if (class_acv.s.q.bs == null || class_acv.s.q.bs.length < 256) {
						long[] newBs = new long[256];
						if (class_acv.s.q.bs != null) {
							System.arraycopy(class_acv.s.q.bs, 0, newBs, 0, class_acv.s.q.bs.length);
						}
						class_acv.s.q.bs = newBs;
					}
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
						// Đồng bộ khóa cooldown 10s cho toàn bộ nhóm bình HP hoặc MP
						long now = System.currentTimeMillis();
						if (class_acv.s.q.bs != null) {
							if (isHp) {
								int[] hpPots = {1, 2, 3, 21, 22, 93, 94};
								for (int i = 0; i < hpPots.length; i++) {
									if (hpPots[i] < class_acv.s.q.bs.length) {
										class_acv.s.q.bs[hpPots[i]] = now;
									}
								}
							} else if (isMp) {
								int[] mpPots = {4, 5, 6, 23, 24, 95, 96};
								for (int i = 0; i < mpPots.length; i++) {
									if (mpPots[i] < class_acv.s.q.bs.length) {
										class_acv.s.q.bs[mpPots[i]] = now;
									}
								}
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
				} else if (b4 == -4) {
					// Sát thương phản đòn Kiếm Khách (nhảy số Font Vàng)
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								if (s2 > 0) {
									Paint.addReflectDamage(s2, entity.cK, entity.cL - 35);
								}
								break;
							}
						}
					}
					return;
				} else if (b4 == -5) {
					// Sát thương chuẩn True Damage Kiếm Khách (nhảy số Font Trắng)
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								if (s2 > 0) {
									Paint.addTrueDamage(s2, entity.cK, entity.cL - 35);
								}
								break;
							}
						}
					}
					return;
				} else if (b4 == -6) {
					// Tiêu diệt quái thường tức thì (Chiêu 8 Kiếm Khách) -> Nhảy chữ "DIET" không kèm dame
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_zp.a((byte) 5, (int) entity.cK, (int) (entity.cL - 25));
								if (cat == 1 && entity instanceof class_bb) {
									((class_bb) entity).v = 0;
									((class_bb) entity).cE = true;
								}
								break;
							}
						}
					}
					return;
				} else if (b4 == -3) {
					// Gỡ bỏ hiệu ứng Độc tức thì (khi bị Độc Nổ rút cạn)
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								if (entity.de != null) {
									for (int j = entity.de.size() - 1; j >= 0; j--) {
										Object obj = entity.de.elementAt(j);
										if (obj instanceof class_zx && ((class_zx) obj).h == 22 && ((class_zx) obj).effectType == 0) {
											entity.de.removeElementAt(j);
										}
									}
								}
								break;
							}
						}
					}
					if (class_acv.s != null && class_acv.s.q != null && class_acv.s.q.cG == targetId) {
						MainCharInfo.poisonEndTime = 0L;
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
				} else if (b4 == 6) {
					// Áp dụng Nhiễm Độc (BUFF_NHIEM_DOC - khuếch đại sát thương nhận vào)
					int sec = dur > 0 ? dur : 6;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_zx eff = new class_zx(entity.cK, entity.cL, 22);
								eff.isDebuff = true;
								eff.a(sec);
								entity.a(eff);
								break;
							}
						}
					}
					if (class_acv.s != null && class_acv.s.q != null && class_acv.s.q.cG == targetId) {
						MainCharInfo.instantPoisonEndTime = System.currentTimeMillis() + (long) sec * 1000L;
						if (MainCharInfo.instantPoisonStacks < 5) {
							MainCharInfo.instantPoisonStacks++;
						}
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
				} else if (b4 == 7) {
					// Áp dụng Hóa Đá (BUFF_HOA_DA - Bất động + Vòng xám tro)
					int sec = dur > 0 ? dur : 1;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								entity.cW = true;
								entity.cZ = System.currentTimeMillis() + (long) (sec * 1000L);
								class_zx eff = new class_zx(entity.cK, entity.cL, 22);
								eff.isDebuff = true;
								eff.effectType = 7;
								eff.a(sec);
								entity.a(eff);
								break;
							}
						}
					}
					if (class_acv.s != null && class_acv.s.q != null && class_acv.s.q.cG == targetId) {
						MainCharInfo.hoaDaEndTime = System.currentTimeMillis() + (long) sec * 1000L;
					}
					return;
				} else if (b4 == 9) {
					// Áp dụng Giảm Giáp (BUFF_GIAM_GIAP - Vòng đỏ cam)
					int sec = dur > 0 ? dur : 5;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_zx eff = new class_zx(entity.cK, entity.cL, 22);
								eff.isDebuff = true;
								eff.effectType = 9;
								eff.a(sec);
								entity.a(eff);
								break;
							}
						}
					}
					if (class_acv.s != null && class_acv.s.q != null && class_acv.s.q.cG == targetId) {
						MainCharInfo.giamGiapEndTime = System.currentTimeMillis() + (long) sec * 1000L;
					}
					return;
				} else if (b4 == 10) {
					// Áp dụng MÙ (BUFF_MU - 1s)
					int sec = dur > 0 ? dur : 1;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_zx eff = new class_zx(entity.cK, entity.cL, 22);
								eff.isDebuff = true;
								eff.effectType = 10;
								eff.a(sec);
								entity.a(eff);
								break;
							}
						}
					}
					return;
				} else if (b4 == 11) {
					// Áp dụng Vết Thương Sâu (BUFF_VET_THUONG_SAU - 5s)
					int sec = dur > 0 ? dur : 5;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_zx eff = new class_zx(entity.cK, entity.cL, 22);
								eff.isDebuff = true;
								eff.effectType = 11;
								eff.a(sec);
								entity.a(eff);
								break;
							}
						}
					}
					return;
				} else if (b4 == 12) {
					// Áp dụng Nhiễm Điện (BUFF_NHIEM_DIEN - 5s): Aura kiếm khí vàng neon bao quanh
					int sec = dur > 0 ? dur : 5;
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								class_zx eff = new class_zx(entity.cK, entity.cL, 24);
								eff.isDebuff = true;
								eff.effectType = 12;
								eff.a(sec);
								entity.a(eff);
								break;
							}
						}
					}
					return;
				} else if (b4 == -12) {
					// Gỡ bỏ Nhiễm Điện (khi lan sét hoặc hết thời gian)
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								if (entity.de != null) {
									for (int j = entity.de.size() - 1; j >= 0; j--) {
										Object obj = entity.de.elementAt(j);
										if (obj instanceof class_zx) {
											class_zx eff = (class_zx) obj;
											if (eff.isDebuff && eff.effectType == 12) {
												eff.j = true;
												entity.de.removeElementAt(j);
											}
										}
									}
								}
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
	 * Thiết lập hào quang cho nguyên liệu:
	 * Sơ cấp: hào quang cấp 5 (item.s = 1)
	 * Cao cấp: hào quang cấp 6 (item.s = 2)
	 */
	public static void updateMaterialVisuals() {
		try {
			if (class_yi.e != null) {
				for (int i = 0; i < class_yi.e.size(); i++) {
					class_xv item = (class_xv) class_yi.e.elementAt(i);
					if (item != null) {
						short id = item.o;
						// Sơ cấp: 68 (Vải), 75 (Sắt), 82 (Ngọc), 89 (Gỗ thường), 96 (Da mềm) -> Cấp 5
						if (id == 68 || id == 75 || id == 82 || id == 89 || id == 96) {
							item.s = 1;
						}
						// Cao cấp: 103 (Tơ lụa), 110 (Bạc), 117 (Thủy tinh), 124 (Gỗ sưa), 131 (Da cứng) -> Cấp 6
						else if (id == 103 || id == 110 || id == 117 || id == 124 || id == 131) {
							item.s = 2;
						}
					}
				}
			}
		} catch (Exception ignored) {
		}
	}

	private static void handleMonsterAttack(short mobId, short targetId, int damage, int targetHp) {
		try {
			if (class_acv.s == null) return;
			class_bb mob = findMonster(mobId);
			if (mob != null) {
				if (targetId == 32001) {
					mob.r();
					return;
				}
				if (mob.l != 84) {
					mob.u();
				}
				if (class_acv.s.q != null && class_acv.s.q.cG == targetId) {
					mob.a((class_ap) class_acv.s.q);
					if (class_acv.s.q.ce) {
						class_acv.s.q.cV = 0;
						class_acv.s.q.ce = false;
					}
					class_acv.s.q.t = class_acv.s.q.v = targetHp;
					if (mob.l == 90) {
						class_acv.s.a(damage != 0 ? "-" + damage : "MISS", damage == 0 ? 0 : 4, (int) class_acv.s.q.cK, class_acv.s.q.cL - 40, 0, -1);
						mob.a((class_ap) class_acv.s.q, damage, (byte) 8, (byte) 4);
						if (class_acv.s.q.v <= 0) {
							class_acv.s.q.cV = (byte) 3;
						}
						return;
					}
					mob.D = class_yg.b((class_vh) mob, (class_vh) class_acv.s.q);
					if (class_acv.s.r == null) {
						class_acv.s.r = mob;
					}
					int bulletType = mob.isMelee() ? 21 : 20;
					class_abj.a(bulletType, (class_ap) mob, (class_ap) class_acv.s.q, (int) mob.cK, (int) mob.cL, damage, (byte) 0);
					class_acv.s.q.l();
					if (class_acv.s.q.v <= 0) {
						class_acv.s.q.cV = (byte) 3;
					}
					class_acv.s.q.J();
					if (mob.l == 84) {
						java.util.Vector vector = new java.util.Vector();
						vector.addElement(class_acv.s.q);
						mob.a(vector, (byte) 0);
					}
				} else {
					class_hw otherPl = findOtherPlayer(targetId);
					if (otherPl != null) {
						mob.a((class_ap) otherPl);
						otherPl.t = otherPl.v = targetHp;
						if (otherPl.v <= 0) {
							otherPl.cV = (byte) 3;
						}
						mob.D = class_yg.b((class_vh) mob, (class_vh) otherPl);
						otherPl.l();
						if (mob.l == 90) {
							class_acv.s.a(damage != 0 ? "-" + damage : "MISS", damage == 0 ? 0 : 4, (int) otherPl.cK, otherPl.cL - 40, 0, -1);
							mob.a((class_ap) otherPl, damage, (byte) 8, (byte) 4);
							return;
						}
						int bulletType = mob.isMelee() ? 21 : 20;
						class_abj.a(bulletType, (class_ap) mob, (class_ap) otherPl, (int) mob.cK, (int) mob.cL, damage, (byte) 0);
						class_abm.b(new class_di((int) otherPl.cK, otherPl.cL - 10, 9));
						if (mob.l == 84) {
							java.util.Vector vector = new java.util.Vector();
							vector.addElement(otherPl);
							mob.a(vector, (byte) 0);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class_bb findMonster(short mobId) {
		if (class_acv.s == null || class_acv.s.l == null) return null;
		for (int i = class_acv.s.l.size() - 1; i >= 0; i--) {
			Object obj = class_acv.s.l.elementAt(i);
			if (obj instanceof class_bb) {
				class_bb mob = (class_bb) obj;
				if (mob.cF == 1 && mob.cG == mobId) {
					return mob;
				}
			}
		}
		return null;
	}

	public static class_hw findOtherPlayer(short plId) {
		if (class_acv.s == null) return null;
		class_vh vh = class_acv.s.b(plId);
		if (vh instanceof class_hw) {
			return (class_hw) vh;
		}
		return null;
	}
}
