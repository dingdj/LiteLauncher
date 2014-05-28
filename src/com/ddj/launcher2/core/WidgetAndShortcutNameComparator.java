/**
 * @author dingdj
 * Date:2014-3-18下午3:04:26
 *
 */
package com.ddj.launcher2.core;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;

import android.appwidget.AppWidgetProviderInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * @author dingdj
 * Date:2014-3-18下午3:04:26
 *
 */
public class WidgetAndShortcutNameComparator implements Comparator<Object> {
    private Collator mCollator;
    private PackageManager mPackageManager;
    private HashMap<Object, String> mLabelCache;
    WidgetAndShortcutNameComparator(PackageManager pm) {
        mPackageManager = pm;
        mLabelCache = new HashMap<Object, String>();
        mCollator = Collator.getInstance();
    }
    public final int compare(Object a, Object b) {
        String labelA, labelB;
        if (mLabelCache.containsKey(a)) {
            labelA = mLabelCache.get(a);
        } else {
            labelA = (a instanceof AppWidgetProviderInfo) ?
                ((AppWidgetProviderInfo) a).label :
                ((ResolveInfo) a).loadLabel(mPackageManager).toString();
            mLabelCache.put(a, labelA);
        }
        if (mLabelCache.containsKey(b)) {
            labelB = mLabelCache.get(b);
        } else {
            labelB = (b instanceof AppWidgetProviderInfo) ?
                ((AppWidgetProviderInfo) b).label :
                ((ResolveInfo) b).loadLabel(mPackageManager).toString();
            mLabelCache.put(b, labelB);
        }
        return mCollator.compare(labelA, labelB);
    }
}
