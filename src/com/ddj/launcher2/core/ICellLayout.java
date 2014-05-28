/**
 * @author dingdj
 * Date:2014-3-18上午9:00:37
 *
 */
package com.ddj.launcher2.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author dingdj
 * Date:2014-3-18上午9:00:37
 *
 */
public abstract class ICellLayout extends ViewGroup {
	
	abstract public boolean findCellForSpan(int[] cellXY, int spanX, int spanY);
	
	abstract public void markCellsAsUnoccupiedForView(View view);
	
	abstract public int getCellWidth();

	abstract public int getCellHeight();

	abstract public int getWidthGap();

	abstract public int getHeightGap();
	
	abstract public int getCountX();
	
	abstract public int getCountY();
	
	abstract public boolean createAreaForResize(int cellX, int cellY, int spanX, int spanY,
            View dragView, int[] direction, boolean commit);

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ICellLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ICellLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public ICellLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author dingdj
	 * Date:2014-3-18上午11:59:28
	 *  @param itemInfo
	 */
	public void calculateSpans(ItemInfo itemInfo) {
	}
	
	

}
