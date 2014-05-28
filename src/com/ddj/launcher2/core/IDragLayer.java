/**
 * @author dingdj
 * Date:2014-3-18上午9:07:59
 *
 */
package com.ddj.launcher2.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author dingdj
 * Date:2014-3-18上午9:07:59
 *
 */
public abstract class IDragLayer extends FrameLayout{
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public IDragLayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public IDragLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public IDragLayer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public abstract float getLocationInDragLayer(View child, int[] loc);

	
	

}
