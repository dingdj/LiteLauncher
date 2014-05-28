/**
 * @author dingdj
 * Date:2014-3-18上午11:42:29
 *
 */
package com.ddj.launcher2.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * @author dingdj
 * Date:2014-3-18上午11:42:29
 *
 */
public class CellLayoutLayoutParams extends ViewGroup.MarginLayoutParams {
    /**
     * Horizontal location of the item in the grid.
     */
    @ViewDebug.ExportedProperty
    public int cellX;

    /**
     * Vertical location of the item in the grid.
     */
    @ViewDebug.ExportedProperty
    public int cellY;

    /**
     * Temporary horizontal location of the item in the grid during reorder
     */
    public int tmpCellX;

    /**
     * Temporary vertical location of the item in the grid during reorder
     */
    public int tmpCellY;

    /**
     * Indicates that the temporary coordinates should be used to layout the items
     */
    public boolean useTmpCoords;

    /**
     * Number of cells spanned horizontally by the item.
     */
    @ViewDebug.ExportedProperty
    public int cellHSpan;

    /**
     * Number of cells spanned vertically by the item.
     */
    @ViewDebug.ExportedProperty
    public int cellVSpan;

    /**
     * Indicates whether the item will set its x, y, width and height parameters freely,
     * or whether these will be computed based on cellX, cellY, cellHSpan and cellVSpan.
     */
    public boolean isLockedToGrid = true;

    /**
     * Indicates whether this item can be reordered. Always true except in the case of the
     * the AllApps button.
     */
    public boolean canReorder = true;

    // X coordinate of the view in the layout.
    @ViewDebug.ExportedProperty
	public int x;
    // Y coordinate of the view in the layout.
    @ViewDebug.ExportedProperty
    public int y;

    public boolean dropped;

    public CellLayoutLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
        cellHSpan = 1;
        cellVSpan = 1;
    }

    public CellLayoutLayoutParams(ViewGroup.LayoutParams source) {
        super(source);
        cellHSpan = 1;
        cellVSpan = 1;
    }

    public CellLayoutLayoutParams(CellLayoutLayoutParams source) {
        super(source);
        this.cellX = source.cellX;
        this.cellY = source.cellY;
        this.cellHSpan = source.cellHSpan;
        this.cellVSpan = source.cellVSpan;
    }

    public CellLayoutLayoutParams(int cellX, int cellY, int cellHSpan, int cellVSpan) {
        super(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.cellX = cellX;
        this.cellY = cellY;
        this.cellHSpan = cellHSpan;
        this.cellVSpan = cellVSpan;
    }

    public void setup(int cellWidth, int cellHeight, int widthGap, int heightGap,
            boolean invertHorizontally, int colCount) {
        if (isLockedToGrid) {
            final int myCellHSpan = cellHSpan;
            final int myCellVSpan = cellVSpan;
            int myCellX = useTmpCoords ? tmpCellX : cellX;
            int myCellY = useTmpCoords ? tmpCellY : cellY;

            if (invertHorizontally) {
                myCellX = colCount - myCellX - cellHSpan;
            }

            width = myCellHSpan * cellWidth + ((myCellHSpan - 1) * widthGap) -
                    leftMargin - rightMargin;
            height = myCellVSpan * cellHeight + ((myCellVSpan - 1) * heightGap) -
                    topMargin - bottomMargin;
            x = (int) (myCellX * (cellWidth + widthGap) + leftMargin);
            y = (int) (myCellY * (cellHeight + heightGap) + topMargin);
        }
    }

    public String toString() {
        return "(" + this.cellX + ", " + this.cellY + ")";
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}