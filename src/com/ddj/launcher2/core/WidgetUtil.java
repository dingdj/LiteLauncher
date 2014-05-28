/**
 * @author dingdj
 * Date:2014-3-18上午8:15:21
 *
 */
package com.ddj.launcher2.core;

import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;


/**
 * @author dingdj
 * Date:2014-3-18上午8:15:21
 *
 */
public class WidgetUtil {

	 public static int[] getSpanForWidget(Context context, ComponentName component, int minWidth,
	            int minHeight) {
		 
		 	Rect padding = AppWidgetHostView.getDefaultPaddingForWidget(context, component, null);
		
	        // We want to account for the extra amount of padding that we are adding to the widget
	        // to ensure that it gets the full amount of space that it has requested
	        int requiredWidth = minWidth + padding.left + padding.right;
	        int requiredHeight = minHeight + padding.top + padding.bottom;
	        return CellLayoutConfig.rectToCell(context.getResources(), requiredWidth, requiredHeight, null);
	    }

	 public  static int[] getSpanForWidget(Context context, AppWidgetProviderInfo info) {
	        return getSpanForWidget(context, info.provider, info.minWidth, info.minHeight);
	    }

	 public  static int[] getMinSpanForWidget(Context context, AppWidgetProviderInfo info) {
	        return getSpanForWidget(context, info.provider, info.minResizeWidth, info.minResizeHeight);
	    }

	 public  static int[] getSpanForWidget(Context context, PendingAddWidgetInfo info) {
	        return getSpanForWidget(context, info.componentName, info.minWidth, info.minHeight);
	    }

	 public  static int[] getMinSpanForWidget(Context context, PendingAddWidgetInfo info) {
	        return getSpanForWidget(context, info.componentName, info.minResizeWidth,
	                info.minResizeHeight);
	    }

}
