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
				msg.b().mark(64);
				short targetId = msg.b().readShort();
				byte cat = msg.b().readByte();
				byte b2 = msg.b().readByte();
				short hpSub = msg.b().readShort();
				byte b3 = msg.b().readByte();
				byte b4 = msg.b().readByte();
				int dur = -1;
				try {
					dur = msg.b().readByte();
				} catch (Exception ignored) {
				}

				if (b4 == -1) {
					// DoT tick sát thương độc
					if (class_acv.s != null && class_acv.s.l != null) {
						for (int i = 0; i < class_acv.s.l.size(); i++) {
							class_vh entity = (class_vh) class_acv.s.l.elementAt(i);
							if (entity != null && entity.cG == targetId) {
								Paint.addPoisonDamage(hpSub, entity.cK, entity.cL - 40);
								if (cat == 1 && entity instanceof class_bb) {
									((class_bb) entity).d((int) hpSub);
									if (((class_bb) entity).v <= 0) {
										((class_bb) entity).cE = true;
									}
								} else if (entity instanceof class_hw) {
									((class_hw) entity).v -= hpSub;
									if (((class_hw) entity).v <= 0) {
										((class_hw) entity).cV = (byte) 3;
									}
								}
								break;
							}
						}
					}
					// Đã xử lý trừ máu và hiển thị popup tím, return để chặn class_abj vẽ chữ trắng/đỏ đè lên
					return;
				} else if (b4 == 4) {
					// Áp dụng trúng độc
					int sec = dur > 0 ? dur : 10;
					if (class_acv.s != null && class_acv.s.q != null && class_acv.s.q.cG == targetId) {
						MainCharInfo.poisonEndTime = System.currentTimeMillis() + (long) sec * 1000L;
					}
					msg.b().reset();
					break;
				} else {
					msg.b().reset();
					break;
				}
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
			}
			try {
				globalMsgHandler.a(msg);
			} catch (Exception ex) {
				System.err.println("Error when processing Cmd " + msg.a + ": " + ex.getMessage());
				ex.printStackTrace();
			}
		} catch (IOException e) {
		}
	}
}
