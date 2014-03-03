package com.ddj.launcher2.debug;

/**
 * 全局debug类
 * @author dingdj
 * Date:2013-12-27下午3:46:14
 *
 */
public class Debug {
	
	public final static boolean Launcher_DEBUG = true;
	public final static boolean Workspace_DEBUG = false;
	public final static boolean CellLayout_DEBUG = false;


	/**
     * 构建一个异常 打出堆栈
     * @author dingdj
     * Date:2013-12-26下午5:34:59
     */
    public static void printStackForDebug(){
    	try{
    		throw new IllegalArgumentException("Fake Exception");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 构建一个异常 打出堆栈
     * @author dingdj
     * Date:2013-12-26下午5:34:59
     */
    public static void printStackForDebug(String exceptionContent){
    	try{
    		throw new IllegalArgumentException(exceptionContent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
