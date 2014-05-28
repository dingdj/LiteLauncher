/**
 * @author dingdj
 * Date:2014-3-17下午5:34:18
 *
 */
package com.ddj.launcher2.core;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author dingdj
 * Date:2014-3-17下午5:34:18
 *
 */
public class CacheDb extends SQLiteOpenHelper {
	public final static int DB_VERSION = 2;
	public final static String DB_NAME = "widgetpreviews.db";
	public final static String TABLE_NAME = "shortcut_and_widget_previews";
	public final static String COLUMN_NAME = "name";
	public final static String COLUMN_SIZE = "size";
    public final static String COLUMN_PREVIEW_BITMAP = "preview_bitmap";
Context mContext;

public CacheDb(Context context) {
    super(context, new File(context.getCacheDir(), DB_NAME).getPath(), null, DB_VERSION);
    // Store the context for later use
    mContext = context;
}

@Override
public void onCreate(SQLiteDatabase database) {
    database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_SIZE + " TEXT NOT NULL, " +
            COLUMN_PREVIEW_BITMAP + " BLOB NOT NULL, " +
            "PRIMARY KEY (" + COLUMN_NAME + ", " + COLUMN_SIZE + ") " +
            ");");
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
        // Delete all the records; they'll be repopulated as this is a cache
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}

}
