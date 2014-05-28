/**
 * @author dingdj
 * Date:2014-3-18下午3:31:29
 *
 */
package com.ddj.launcher2.core;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

import com.ddj.launcher2.core.IAppsCustomizePagedView.ContentType;


/**
 * @author dingdj
 * Date:2014-3-18下午3:31:29
 *
 */
public abstract class IAppsCustomizeTabHost extends TabHost{
	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public IAppsCustomizeTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public IAppsCustomizeTabHost(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	abstract public ContentType getContentTypeForTabTag(String tag);

	/**
	 * @author dingdj
	 * Date:2014-3-18下午3:51:43
	 *  @param contentTypeForTabTag
	 */
	abstract public void setContentTypeImmediate(ContentType contentTypeForTabTag);

	/**
	 * @author dingdj
	 * Date:2014-3-18下午3:55:43
	 */
	abstract public void reset();

	/**
	 * @author dingdj
	 * Date:2014-3-18下午3:56:04
	 */
	abstract public void onWindowVisible();

	/**
	 * @author dingdj
	 * Date:2014-3-18下午3:56:20
	 *  @return
	 */
	abstract public boolean isTransitioning();

	/**
	 * @author dingdj
	 * Date:2014-3-18下午3:56:47
	 */
	abstract public void onTrimMemory();
}
