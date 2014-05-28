/**
 * @author dingdj
 * Date:2014-3-17下午2:15:23
 *
 */
package com.ddj.launcher2.core;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.ddj.launcher.R;
import com.ddj.launcher2.CellLayout;
import com.ddj.launcher2.LauncherModel;

import android.appwidget.AppWidgetManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author dingdj
 * Date:2014-3-17下午2:15:23
 *
 */
public class LauncherUtil {
	
	 // The Intent extra that defines whether to ignore the launch animation
   public static final String INTENT_EXTRA_IGNORE_LAUNCH_ANIMATION =
            "com.android.launcher.intent.extra.shortcut.INGORE_LAUNCH_ANIMATION";
   
   private static final String sSharedPreferencesKey = "com.android.launcher2.prefs";
   
   public static final int APPWIDGET_HOST_ID = 1024;

	public static ComponentName getComponentNameFromResolveInfo(ResolveInfo info) {
        if (info.activityInfo != null) {
            return new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
        } else {
            return new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name);
        }
    }
	
	/**
	 * 是否大屏幕
	 * @author dingdj
	 * Date:2014-3-18上午11:11:42
	 *  @return
	 */
	public static boolean isScreenLarge(){
		return false;
	}
	
   public static Rect mLandscapeCellLayoutMetrics = null;
   public static Rect mPortraitCellLayoutMetrics = null;
   
   public static Rect getCellLayoutMetrics(Context context, int orientation) {
       Resources res = context.getResources();
       final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
       Display display = windowManager.getDefaultDisplay();
       Point smallestSize = new Point();
       Point largestSize = new Point();
       display.getCurrentSizeRange(smallestSize, largestSize);
       if (orientation == CellLayoutConfig.LANDSCAPE) {
           if (mLandscapeCellLayoutMetrics == null) {
               int paddingLeft = res.getDimensionPixelSize(R.dimen.workspace_left_padding_land);
               int paddingRight = res.getDimensionPixelSize(R.dimen.workspace_right_padding_land);
               int paddingTop = res.getDimensionPixelSize(R.dimen.workspace_top_padding_land);
               int paddingBottom = res.getDimensionPixelSize(R.dimen.workspace_bottom_padding_land);
               int width = largestSize.x - paddingLeft - paddingRight;
               int height = smallestSize.y - paddingTop - paddingBottom;
               mLandscapeCellLayoutMetrics = new Rect();
               CellLayout.getMetrics(mLandscapeCellLayoutMetrics, res,
                       width, height, LauncherModel.getCellCountX(), LauncherModel.getCellCountY(),
                       orientation);
           }
           return mLandscapeCellLayoutMetrics;
       } else if (orientation == CellLayoutConfig.PORTRAIT) {
           if (mPortraitCellLayoutMetrics == null) {
               int paddingLeft = res.getDimensionPixelSize(R.dimen.workspace_left_padding_land);
               int paddingRight = res.getDimensionPixelSize(R.dimen.workspace_right_padding_land);
               int paddingTop = res.getDimensionPixelSize(R.dimen.workspace_top_padding_land);
               int paddingBottom = res.getDimensionPixelSize(R.dimen.workspace_bottom_padding_land);
               int width = smallestSize.x - paddingLeft - paddingRight;
               int height = largestSize.y - paddingTop - paddingBottom;
               mPortraitCellLayoutMetrics = new Rect();
               CellLayout.getMetrics(mPortraitCellLayoutMetrics, res,
                       width, height, LauncherModel.getCellCountX(), LauncherModel.getCellCountY(),
                       orientation);
           }
           return mPortraitCellLayoutMetrics;
       }
       return null;
   }


   public static String getSharedPreferencesKey() {
       return sSharedPreferencesKey;
   }
   
   public static final Comparator<ApplicationInfo> getAppNameComparator() {
       final Collator collator = Collator.getInstance();
       return new Comparator<ApplicationInfo>() {
           public final int compare(ApplicationInfo a, ApplicationInfo b) {
               int result = collator.compare(a.title.toString(), b.title.toString());
               if (result == 0) {
                   result = a.componentName.compareTo(b.componentName);
               }
               return result;
           }
       };
   }
   
// Returns a list of ResolveInfos/AppWindowInfos in sorted order
   public static ArrayList<Object> getSortedWidgetsAndShortcuts(Context context) {
       PackageManager packageManager = context.getPackageManager();
       final ArrayList<Object> widgetsAndShortcuts = new ArrayList<Object>();
       widgetsAndShortcuts.addAll(AppWidgetManager.getInstance(context).getInstalledProviders());
       Intent shortcutsIntent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
       widgetsAndShortcuts.addAll(packageManager.queryIntentActivities(shortcutsIntent, 0));
       Collections.sort(widgetsAndShortcuts,
           new WidgetAndShortcutNameComparator(packageManager));
       return widgetsAndShortcuts;
   }
}
