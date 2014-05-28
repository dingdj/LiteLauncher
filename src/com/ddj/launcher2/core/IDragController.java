package com.ddj.launcher2.core;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * @author dingdj
 * Date:2014-3-17下午1:55:48
 *
 */
public interface IDragController {
	
	   /** Indicates the drag is a move.  */
    public static int DRAG_ACTION_MOVE = 0;

    /** Indicates the drag is a copy.  */
    public static int DRAG_ACTION_COPY = 1;
	
	public void addDragListener(DragListener l);
	
	 /**
     * Remove a previously installed drag listener.
     */
    public void removeDragListener(DragListener l);
    
    
    public void startDrag(View v, Bitmap bmp, DragSource source, Object dragInfo, int dragAction,
            Point extraPadding, float initialDragViewScale);
    
    public void startDrag(Bitmap b, int dragLayerX, int dragLayerY,
            DragSource source, Object dragInfo, int dragAction, Point dragOffset, Rect dragRegion,
            float initialDragViewScale);

    public boolean isDragging();
}
