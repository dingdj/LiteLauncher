package com.ddj.launcher2.util;

import android.content.Context;

/**
 * @author dingdj
 * Date:2014-2-27上午9:55:27
 *
 */
public class Global {
	
	private static Context appContext;
	/**
	 * 是否加载匣子的开关
	 */
	private static boolean loadAllApp = true;

	
	public static Context getContext() {
		return appContext;
	}

	public static void setContext(Context context) {
		Global.appContext = context;
	}
	
	

}
