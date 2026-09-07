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
			class_d.b.a(_graphics, "Độ bền: " + MainCharInfo.getDoBen(), 1, xPos += 12, 0);
			class_d.b.a(_graphics, "Toạ độ: " + ModHelpers.getMapNameAndPosition(), 1, xPos += 12, 0);
		}
	}
}
