package com.ddj.launcher2.core;

import java.util.ArrayList;

import android.view.ViewGroup;

/**
 * @author dingdj
 * Date:2014-3-18下午3:23:03
 *
 */
public interface IAppsCustomizePagedView {
	
	public ViewGroup _getParent();
	
	 public void onPackagesUpdated(ArrayList<Object> widgetsAndShortcuts);
	 
	 public void resetDrawableState();
	 
	 public void setBulkBind(boolean bulkBind);
	 
	 public void surrender();
	 
	 public void setup(ILauncher launcher, IDragController dragController);
	 
	 public void restorePageForIndex(int index);
	 
	 public void setApps(ArrayList<ApplicationInfo> list);
	 
	 public void updateApps(ArrayList<ApplicationInfo> list);
	 
	 public void removeApps(ArrayList<ApplicationInfo> appInfos);
	 
	 public void addApps(ArrayList<ApplicationInfo> list);
	 
	 public void dumpState();
	 
	 public int _getCurrentPage();
	 
	 public int _getSaveInstanceStateIndex();
	 
	 public void _loadAssociatedPages(int page);
	 
	 public void _updateCurrentPageScroll();
	 
	 public void _resumeScrolling();
	 
	 public void _pauseScrolling();
	 
	 /**
	     * The different content types that this paged view can show.
	     */
    public enum ContentType {
        Applications,
        Widgets
    }

}
