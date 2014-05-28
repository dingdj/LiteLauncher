package com.ddj.launcher2.core;

import java.lang.reflect.Constructor;

import android.content.Context;

/**
 * @author dingdj
 * Date:2014-3-18上午8:43:13
 *
 */
public class LauncherState {
	
	private static LauncherState instance;
	
	private LauncherState(){
		
	}
	
	public static LauncherState getInstance(){
		if(instance == null){
			instance = new LauncherState();
		}
		return instance;
	}
	
	
	private ILauncher launcher;
	
	private Context applicationContext;
	
	private IconCache iconCache;
	
	private IAllAppData allAppList;

	public boolean isWorkspaceSwitchingState() {
		return launcher.getWorkspace().isSwitchingState();
	}


	public boolean isAllAppsVisible() {
		return launcher._isAllAppsVisible();
	}


	public ILauncher getLauncher() {
		return launcher;
	}

	public void setLauncher(ILauncher launcher) {
		this.launcher = launcher;
	}

	public Context getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(Context applicationContext) {
		this.applicationContext = applicationContext;
	}

	public IconCache getIconCache() {
		return iconCache;
	}

	public void setIconCache(IconCache iconCache) {
		this.iconCache = iconCache;
	}
	
	public IAllAppData getAllAppList(IconCache iconCache){
		if(allAppList == null){
			try {
				Constructor<?> construct = Class.forName("com.ddj.launcher2.allapps.AllAppsList").getConstructor(iconCache.getClass());
				allAppList = (IAllAppData) construct.newInstance(iconCache);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return allAppList;
	}
	
	

}
