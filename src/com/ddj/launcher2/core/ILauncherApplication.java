/**
 * @author dingdj
 * Date:2014-3-17下午2:19:05
 *
 */
package com.ddj.launcher2.core;

/**
 * @author dingdj
 * Date:2014-3-17下午2:19:05
 *
 */
public interface ILauncherApplication {
	
	 public void setLauncherProvider(LauncherProvider provider);

	 public IconCache getIconCache();
	 
	 public CacheDb getWidgetPreviewCacheDb();
	 
	 
}
