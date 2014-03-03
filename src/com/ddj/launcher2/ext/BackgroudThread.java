package com.ddj.launcher2.ext;

import android.os.HandlerThread;
import android.os.Process;

/**
 * 桌面后台消息线程, 整个桌面保持一份，用于处理后台的一些工作
 * @author dingdj
 * Date:2013-12-31上午11:54:16
 *
 */
public class BackgroudThread extends HandlerThread{

	/**
	 * @param name
	 */
	public BackgroudThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		super.run();
	}

}
