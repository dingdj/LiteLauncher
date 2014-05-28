/**
 * @author dingdj
 * Date:2014-3-17下午2:27:39
 *
 */
package com.ddj.launcher2.core;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * @author dingdj
 * Date:2014-3-17下午2:27:39
 *
 */
public abstract class ILauncher extends Activity{
	
	public abstract void dismissWorkspaceCling(View v);
	
	public abstract boolean startActivitySafely(View v, Intent intent, Object tag);
	
	public abstract void updateWallpaperVisibility(boolean visible);
	
	public abstract IDragLayer getDragLayer();
	
	public abstract void showFirstRunAllAppsCling(int[] pos);
	
	public abstract IWorkspace getWorkspace();
	
	public abstract LauncherAppWidgetHost getAppWidgetHost();
	
	public abstract void lockScreenOrientation();
	
	public abstract IDragController getDragController();
	
	public abstract void dismissFolderCling(View v);
	
	public abstract void dismissAllAppsCling(View v);
	
	public abstract void enterSpringLoadedDragMode();
	
	public abstract void exitSpringLoadedDragMode();
	
	public abstract void unlockScreenOrientation(boolean immediate);
	
	public abstract int getCurrentWorkspaceScreen();
	
	public abstract void showOutOfSpaceMessage(boolean b);
	
	public abstract boolean isDraggingEnabled();
	
	public abstract boolean _isAllAppsVisible();
	
	public abstract void bindPackagesUpdated(final ArrayList<Object> widgetsAndShortcuts);

}
