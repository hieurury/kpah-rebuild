package classes;

import javax.microedition.lcdui.Graphics;

import config.Config.DrawConfig;
import utility.StringUtils;

/*
 * Class này để in ấn , hiển thị các thông tin
 */
public class Paint {

	public static void onPaint(Graphics _graphics) {
		// Kiểm tra màn hình, nếu ở màn hình GameScreen thì mới hiển thị
		if (class_acv.q == class_acv.s) {
			int xPos = class_abj.O.getHeight() - 9 + 12;
			int doBen = MainCharInfo.getDoBen();
			if (doBen <= 0) {
				class_d.e.a(_graphics, "Độ bền: 0 (Hỏng)", 1, xPos += 12, 0);
			} else {
				class_d.b.a(_graphics, "Độ bền: " + doBen, 1, xPos += 12, 0);
			}
			class_d.b.a(_graphics, "Toạ độ: " + ModHelpers.getMapNameAndPosition(), 1, xPos += 12, 0);

			// Hiển thị thông tin thuộc tính tăng EXP nếu có
			String expBonus = MainCharInfo.getExpBonusInfo();
			if (expBonus != null && expBonus.length() > 0) {
				class_d.h.a(_graphics, expBonus, 1, xPos += 12, 0);
			}

			// Hiển thị các hiệu ứng buff đang hoạt động kèm thời gian còn lại
			java.util.Vector activeBuffs = MainCharInfo.getActiveBuffStrings();
			if (activeBuffs != null) {
				for (int i = 0; i < activeBuffs.size(); i++) {
					String buffText = (String) activeBuffs.elementAt(i);
					class_d.h.a(_graphics, buffText, 1, xPos += 12, 0);
				}
			}
		}
	}
}
