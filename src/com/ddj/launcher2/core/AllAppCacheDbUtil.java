/**
 * @author dingdj
 * Date:2014-3-18下午4:27:46
 *
 */
package com.ddj.launcher2.core;

import java.util.HashSet;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * @author dingdj
 * Date:2014-3-18下午4:27:46
 *
 */
public class AllAppCacheDbUtil {

	public static HashSet<String> sInvalidPackages;
	
	public static final String WIDGET_PREFIX = "Widget:";
	public static final String SHORTCUT_PREFIX = "Shortcut:";

    static {
        sInvalidPackages = new HashSet<String>();
    }
    
    public static void removeFromDb(final CacheDb cacheDb, final String packageName) {
        synchronized(AllAppCacheDbUtil.sInvalidPackages) {
        	AllAppCacheDbUtil.sInvalidPackages.add(packageName);
        }
        new AsyncTask<Void, Void, Void>() {
            public Void doInBackground(Void ... args) {
                SQLiteDatabase db = cacheDb.getWritableDatabase();
                db.delete(CacheDb.TABLE_NAME,
                        CacheDb.COLUMN_NAME + " LIKE ? OR " +
                        CacheDb.COLUMN_NAME + " LIKE ?", // SELECT query
                        new String[] {
                            WIDGET_PREFIX + packageName + "/%",
                            SHORTCUT_PREFIX + packageName + "/%"} // args to SELECT query
                            );
                synchronized(AllAppCacheDbUtil.sInvalidPackages) {
                	AllAppCacheDbUtil.sInvalidPackages.remove(packageName);
                }
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
    }

}
