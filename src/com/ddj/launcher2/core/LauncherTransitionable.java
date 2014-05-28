package com.ddj.launcher2.core;

import android.view.View;

/**
 * @author dingdj
 * Date:2014-3-17下午2:27:25
 *
 */
public interface LauncherTransitionable {
	 	View getContent();
	    void onLauncherTransitionPrepare(ILauncher l, boolean animated, boolean toWorkspace);
	    void onLauncherTransitionStart(ILauncher l, boolean animated, boolean toWorkspace);
	    void onLauncherTransitionStep(ILauncher l, float t);
	    void onLauncherTransitionEnd(ILauncher l, boolean animated, boolean toWorkspace);
}
