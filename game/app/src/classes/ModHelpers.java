/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

/**
 *
 * @author Noverify
 */
public class ModHelpers {

	public static String getMapNameAndPosition() {
		return class_abj.a + "  " + class_acv.s.q.cK / 16 + ":" + class_acv.s.q.cL / 16;
	}

	public static String getOnOffStatus(boolean boo) {
		return boo ? "Bật" : "Tắt";
	}

	public static long currentTime() {
		return System.currentTimeMillis() / 1000;
	}

	public static long leftTime(long time) {
		return currentTime() - time;
	}

	public static void setCurrentScreen(Displayable dsplayable) {
		Display.getDisplay(game.GameMidlet.a).setCurrent(dsplayable);
	}

	private static boolean scaleMethodsLoaded = false;
	private static java.lang.reflect.Method methodGetGraphics = null;
	private static java.lang.reflect.Method methodGetTransform = null;
	private static java.lang.reflect.Method methodSetTransform = null;
	private static java.lang.reflect.Method methodTranslate = null;
	private static java.lang.reflect.Method methodScale = null;

	private static void initScaleMethods(Object g) {
		if (scaleMethodsLoaded) return;
		scaleMethodsLoaded = true;
		try {
			methodGetGraphics = g.getClass().getMethod("getGraphics", new Class[0]);
			Class g2dClass = Class.forName("java.awt.Graphics2D");
			Class txClass = Class.forName("java.awt.geom.AffineTransform");
			methodGetTransform = g2dClass.getMethod("getTransform", new Class[0]);
			methodSetTransform = g2dClass.getMethod("setTransform", new Class[]{ txClass });
			methodTranslate = g2dClass.getMethod("translate", new Class[]{ Double.TYPE, Double.TYPE });
			methodScale = g2dClass.getMethod("scale", new Class[]{ Double.TYPE, Double.TYPE });
		} catch (Throwable t) {
		}
	}

	public static Object beginScale(Object g, int centerX, int centerY, double scale) {
		if (g == null) return null;
		try {
			initScaleMethods(g);
			if (methodGetGraphics != null && methodTranslate != null && methodScale != null) {
				Object g2d = methodGetGraphics.invoke(g, new Object[0]);
				if (g2d != null) {
					Object oldTx = methodGetTransform.invoke(g2d, new Object[0]);
					Double cx = new Double((double) centerX);
					Double cy = new Double((double) centerY);
					Double sc = new Double(scale);
					Double ncx = new Double((double) -centerX);
					Double ncy = new Double((double) -centerY);
					methodTranslate.invoke(g2d, new Object[]{ cx, cy });
					methodScale.invoke(g2d, new Object[]{ sc, sc });
					methodTranslate.invoke(g2d, new Object[]{ ncx, ncy });
					return oldTx;
				}
			}
		} catch (Throwable t) {
		}
		return null;
	}

	public static void endScale(Object g, Object oldTx) {
		if (g == null || oldTx == null) return;
		try {
			if (methodGetGraphics != null && methodSetTransform != null) {
				Object g2d = methodGetGraphics.invoke(g, new Object[0]);
				if (g2d != null) {
					methodSetTransform.invoke(g2d, new Object[]{ oldTx });
				}
			}
		} catch (Throwable t) {
		}
	}
}
