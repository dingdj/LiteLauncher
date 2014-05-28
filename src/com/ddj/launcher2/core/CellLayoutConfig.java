/**
 * @author dingdj
 * Date:2014-3-18上午8:19:36
 *
 */
package com.ddj.launcher2.core;

import android.content.res.Resources;

import com.ddj.launcher.R;

/**
 * @author dingdj
 * Date:2014-3-18上午8:19:36
 *
 */
public class CellLayoutConfig {
	
	private static CellLayoutConfig instance;
	
	public static final int LANDSCAPE = 0;
    public static final int PORTRAIT = 1;
	
	public static CellLayoutConfig getInstance(){
		if(instance == null){
			instance = new CellLayoutConfig();
		}
		return instance;
	}
	
	private CellLayoutConfig(){
		
	}

	private int mCellCountX = 4;
	
	private int mCellCountY = 4;
	
	
	 public int getCellCountX() {
        return mCellCountX;
    }

	public int getCellCountY() {
        return mCellCountY;
    }

	public void setCellCountX(int mCellCountX) {
		this.mCellCountX = mCellCountX;
	}

	public void setCellCountY(int mCellCountY) {
		this.mCellCountY = mCellCountY;
	}
	
	
	 public static int[] rectToCell(Resources resources, int width, int height, int[] result) {
	        // Always assume we're working with the smallest span to make sure we
	        // reserve enough space in both orientations.
	        int actualWidth = resources.getDimensionPixelSize(R.dimen.workspace_cell_width);
	        int actualHeight = resources.getDimensionPixelSize(R.dimen.workspace_cell_height);
	        int smallerSize = Math.min(actualWidth, actualHeight);

	        // Always round up to next largest cell
	        int spanX = (int) Math.ceil(width / (float) smallerSize);
	        int spanY = (int) Math.ceil(height / (float) smallerSize);

	        if (result == null) {
	            return new int[] { spanX, spanY };
	        }
	        result[0] = spanX;
	        result[1] = spanY;
	        return result;
	    }


}
