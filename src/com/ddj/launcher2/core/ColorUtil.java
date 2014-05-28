package com.ddj.launcher2.core;

import android.graphics.Color;

/**
 * @author dingdj
 * Date:2013-12-19上午9:30:30
 *
 */
public class ColorUtil {

	/**
	 * 颜色设置
	 * @param color
	 * @param alpha
	 * @return int
	 */
	public static int argbColorAlpha(int color, int alpha) {
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);
		return Color.argb(alpha, r, g, b);
	}

}
