package com.ddj.launcher2.debug;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;

/**
 * @author dingdj
 * Date:2013-12-18上午11:28:02
 *
 */
public class MemoryUse {
	
	private static final String TAG = "MemoryUse";
	
	private static MemoryUse instance;
	
	private long periodTime = 60*1000;//2*60*60*1000;
	
	private MemoryUse(){
		WorkThread sWorkerThread = new WorkThread("clean-notification");
		sWorkerThread.start();
		final Handler cleanHandler = new Handler(sWorkerThread.getLooper());
		Runnable task = new Runnable(){

			@Override
			public void run() {
				dumpMemoryUsed();
				cleanHandler.postDelayed(this, periodTime);
			}
		};
		cleanHandler.postDelayed(task, periodTime);
	}
	
	public static void start(){
		if(instance == null){
			instance = new MemoryUse();
		}
	}
	
	// 设置线程为低优先级
	public static class WorkThread extends HandlerThread {

		/**
		 * @param name
		 */
		public WorkThread(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
			super.run();
		}
	}
	
	public static String toMB(long size){
		return String.format("%.2f", size/(1024*1024f));
	}
	
	public static void dumpMemoryUsed(){
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		long max = Runtime.getRuntime().maxMemory();
		Log.e(TAG, "total:"+toMB(total) + "|" +
				"free:"+toMB(free) + "|" + "max:"+
				toMB(max)+"|"+"used:"+toMB(total-free));
	}

}
