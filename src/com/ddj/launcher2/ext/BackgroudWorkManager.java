package com.ddj.launcher2.ext;

import android.os.Handler;

/**
 * 后台工作管理
 * @author dingdj
 * Date:2013-12-31上午11:57:09
 *
 */
public class BackgroudWorkManager {
	
	private static BackgroudWorkManager instance;
	
	private Handler workHandler;
	
	private BackgroudWorkManager(){
		BackgroudThread backgroudThread = new BackgroudThread("BackgroudThread");
		backgroudThread.start();
		workHandler = new Handler(backgroudThread.getLooper());
	}
	
	public static BackgroudWorkManager getNewInstance(){
		if(instance != null){
			instance = new BackgroudWorkManager();
		}
		return instance;
	}

	public Handler getWorkHandler() {
		return workHandler;
	}
	
}
