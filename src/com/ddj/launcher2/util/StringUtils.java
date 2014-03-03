package com.ddj.launcher2.util;

/**
 * @author dingdj
 * Date:2014-2-27下午2:27:20
 *
 */
public class StringUtils {
	
	
	/**
	 * 判读空字符串
	 * @author dingdj
	 * Date:2014-2-27下午2:28:25
	 *  @param str
	 *  @return
	 */
	public static boolean isEmpty(CharSequence str){
		if(str == null || "".equals(str.toString())){
			return true;
		}
		return false;
	}

}
