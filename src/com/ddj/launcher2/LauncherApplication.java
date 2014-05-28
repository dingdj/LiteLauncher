/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ddj.launcher2;

import java.lang.ref.WeakReference;

import android.app.Application;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.ddj.launcher.R;
import com.ddj.launcher2.core.CacheDb;
import com.ddj.launcher2.core.ILauncherApplication;
import com.ddj.launcher2.core.IconCache;
import com.ddj.launcher2.core.LauncherProvider;
import com.ddj.launcher2.core.LauncherSettings;
import com.ddj.launcher2.core.LauncherState;
import com.ddj.launcher2.util.Global;

public class LauncherApplication extends Application implements ILauncherApplication {
    private LauncherModel mModel;
    private IconCache mIconCache;
    private CacheDb mWidgetPreviewCacheDb;
    private static boolean sIsScreenLarge;
    private static float sScreenDensity;
    private static int sLongPressTimeout = 300;
    
    WeakReference<LauncherProvider> mLauncherProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        
        sIsScreenLarge = getResources().getBoolean(R.bool.is_large_screen);
        sScreenDensity = getResources().getDisplayMetrics().density;
        
        mWidgetPreviewCacheDb = new CacheDb(this);
        mIconCache = new IconCache(this);
        LauncherState.getInstance().setIconCache(mIconCache);
        mModel = new LauncherModel(this, mIconCache);

        // Register intent receivers
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(mModel, filter);
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        filter.addAction(Intent.ACTION_LOCALE_CHANGED);
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        registerReceiver(mModel, filter);
        filter = new IntentFilter();
        filter.addAction(SearchManager.INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED);
        registerReceiver(mModel, filter);
        filter = new IntentFilter();
        filter.addAction(SearchManager.INTENT_ACTION_SEARCHABLES_CHANGED);
        registerReceiver(mModel, filter);

        // Register for changes to the favorites
        ContentResolver resolver = getContentResolver();
        resolver.registerContentObserver(LauncherSettings.Favorites.CONTENT_URI, true,
                mFavoritesObserver);
        Global.setContext(this.getBaseContext());
        LauncherState.getInstance().setApplicationContext(this.getBaseContext());
        
    }

    /**
     * There's no guarantee that this function is ever called.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

        unregisterReceiver(mModel);

        ContentResolver resolver = getContentResolver();
        resolver.unregisterContentObserver(mFavoritesObserver);
    }

    /**
     * Receives notifications whenever the user favorites have changed.
     */
    private final ContentObserver mFavoritesObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            // If the database has ever changed, then we really need to force a reload of the
            // workspace on the next load
            mModel.resetLoadedState(false, true);
            mModel.startLoaderFromBackground();
        }
    };

    LauncherModel setLauncher(Launcher launcher) {
        mModel.initialize(launcher);
        return mModel;
    }

   public IconCache getIconCache() {
        return mIconCache;
    }

    LauncherModel getModel() {
        return mModel;
    }

   public CacheDb getWidgetPreviewCacheDb() {
        return mWidgetPreviewCacheDb;
    }

   public void setLauncherProvider(LauncherProvider provider) {
        mLauncherProvider = new WeakReference<LauncherProvider>(provider);
    }

    LauncherProvider getLauncherProvider() {
        return mLauncherProvider.get();
    }


    public static boolean isScreenLarge() {
        return sIsScreenLarge;
    }

    public static boolean isScreenLandscape(Context context) {
        return context.getResources().getConfiguration().orientation ==
            Configuration.ORIENTATION_LANDSCAPE;
    }

    public static float getScreenDensity() {
        return sScreenDensity;
    }

    public static int getLongPressTimeout() {
        return sLongPressTimeout;
    }
    
    /**
     * 获取屏幕密度
     * @author dingdj
     * Date:2014-3-3上午10:26:06
     *  @return
     */
    private float fetchScreenDensity(){
    	DisplayMetrics metrics = new DisplayMetrics();
    	final WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		display.getMetrics(metrics);
		return metrics.density;
    }
}
