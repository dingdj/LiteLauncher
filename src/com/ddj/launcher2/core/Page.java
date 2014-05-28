package com.ddj.launcher2.core;

import android.view.View;

/**
 * @author dingdj
 * Date:2014-3-18上午8:55:13
 *
 */
public interface Page {
	
	public int getPageChildCount();
    public View getChildOnPageAt(int i);
    public void removeAllViewsOnPage();
    public void removeViewOnPageAt(int i);
    public int indexOfChildOnPage(View v);

}
