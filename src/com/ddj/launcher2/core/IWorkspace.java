/**
 * @author dingdj
 * Date:2014-3-18上午9:03:22
 *
 */
package com.ddj.launcher2.core;

import android.graphics.Bitmap;
import android.view.View;

/**
 * @author dingdj
 * Date:2014-3-18上午9:03:22
 *
 */
public interface IWorkspace {
	
	public void onDragStartedWithItem(View v);
	
	public void beginDragShared(View child, DragSource source);

	public int[] estimateItemSize(int hSpan, int vSpan,
            ItemInfo itemInfo, boolean springLoaded);
	
	public void onDragStartedWithItem(PendingAddItemInfo info, Bitmap b, boolean clipAlpha);
	

	/**
	 * @author dingdj
	 * Date:2014-3-18上午11:31:00
	 *  @return
	 */
	public int _getScrollX();

	/**
	 * @author dingdj
	 * Date:2014-3-18上午11:31:10
	 *  @return
	 */
	public int _getScrollY();

	/**
	 * @author dingdj
	 * Date:2014-3-18上午11:53:36
	 *  @param currentScreen
	 *  @return
	 */
	public ICellLayout _getChildAt(int currentScreen);
	
	public boolean isSwitchingState();
}
