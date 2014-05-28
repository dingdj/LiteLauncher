/**
 * @author dingdj
 * Date:2014-3-18上午8:57:22
 *
 */
package com.ddj.launcher2.core;

import android.content.pm.ActivityInfo;

/**
 * @author dingdj
 * Date:2014-3-18上午8:57:22
 *
 */
public class PendingAddShortcutInfo extends PendingAddItemInfo {

    public ActivityInfo shortcutActivityInfo;

    public PendingAddShortcutInfo(ActivityInfo activityInfo) {
        shortcutActivityInfo = activityInfo;
    }

    @Override
    public String toString() {
        return "Shortcut: " + shortcutActivityInfo.packageName;
    }
}

