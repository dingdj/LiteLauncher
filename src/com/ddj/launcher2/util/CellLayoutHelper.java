package com.ddj.launcher2.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.ddj.launcher.R;

/**
 * @author dingdj
 * Date:2014-2-27上午9:50:30
 *
 */
public class CellLayoutHelper {
	
	/**
	 * 默认4*4大小
	 */
	private static final int xCount = 4;
	private static final int yCount = 4;
	
	private int cellW;
	private int cellH;
	
	private static CellLayoutHelper instance;
	
	public static CellLayoutHelper getInstance(){
		if(instance == null){
			instance = new CellLayoutHelper();
		}
		return instance;
	}
	
	private CellLayoutHelper(){
		init();
	}
	
	/**
	 * 初始化参数
	 * @author dingdj
	 * Date:2014-2-27上午10:29:30
	 */
	private void init(){
		Context ctx = Global.getContext();
		setCellWH(ctx);
	}
	
	/**
	 * 获取每个cell单元格的宽和高
	 * @author dingdj
	 * Date:2014-2-27上午10:00:59
	 *  @return
	 */
	private void setCellWH(Context ctx){
		cellW = ctx.getResources().getDimensionPixelSize(R.dimen.workspace_cell_width_port);
		cellH = ctx.getResources().getDimensionPixelSize(R.dimen.workspace_cell_height_port);
	}

	public int getCellW() {
		return cellW;
	}

	public int getCellH() {
		return cellH;
	}

	public static int getXcount() {
		return xCount;
	}

	public static int getYcount() {
		return yCount;
	}
}
