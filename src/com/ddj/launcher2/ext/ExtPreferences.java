package com.ddj.launcher2.ext;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author dingdj
 * Date:2013-12-31下午12:06:29
 *
 */
public class ExtPreferences {
	
	private static final String PREFERENCES = "launcherext.preferences";
	
	public static final String BOHAO = "BOHAO_NEWMASK";
	
	/**
	 * 获取SharePreference
	 * @author dingdj
	 * Date:2013-12-31下午1:43:17
	 *  @param context
	 *  @return
	 */
	public static SharedPreferences getExtSharePreference(Context context){
		return context.getSharedPreferences(PREFERENCES, 0);
	}

	

}
