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
