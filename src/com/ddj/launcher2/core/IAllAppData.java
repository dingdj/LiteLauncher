/**
 * @author dingdj
 * Date:2014-3-18下午4:05:23
 *
 */
package com.ddj.launcher2.core;

import java.util.ArrayList;

import android.content.Context;

/**
 * @author dingdj
 * Date:2014-3-18下午4:05:23
 *
 */
public interface IAllAppData {
	
	ArrayList<ApplicationInfo> getData();
	
	ArrayList<ApplicationInfo> getAdded();
	
	ArrayList<ApplicationInfo> getRemoved();
	
	ArrayList<ApplicationInfo> getModified();

	/**
	 * @author dingdj
	 * Date:2014-3-18下午4:12:36
	 */
	void clear();

	/**
	 * @author dingdj
	 * Date:2014-3-18下午4:13:39
	 *  @param string
	 */
	void removePackage(String string);

	/**
	 * @author dingdj
	 * Date:2014-3-18下午4:13:46
	 *  @param context
	 *  @param string
	 */
	void updatePackage(Context context, String string);

	/**
	 * @author dingdj
	 * Date:2014-3-18下午4:13:54
	 *  @param context
	 *  @param string
	 */
	void addPackage(Context context, String string);

	/**
	 * @author dingdj
	 * Date:2014-3-18下午4:14:53
	 *  @param applicationInfo
	 */
	void add(ApplicationInfo applicationInfo);
	
	
	void setAdd(ArrayList<ApplicationInfo> applicationInfos);
}
