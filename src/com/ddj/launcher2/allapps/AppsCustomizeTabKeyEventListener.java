package com.ddj.launcher2.allapps;

import com.ddj.launcher.R;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TabHost;

/**
 * @author dingdj
 * Date:2014-3-17下午3:13:11
 *
 */
public class AppsCustomizeTabKeyEventListener implements View.OnKeyListener{


	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		 final TabHost tabHost = findTabHostParent(v);
	        final ViewGroup contents = tabHost.getTabContentView();
	        final View shop = tabHost.findViewById(R.id.market_button);

	        final int action = event.getAction();
	        final boolean handleKeyEvent = (action != KeyEvent.ACTION_UP);
	        boolean wasHandled = false;
	        switch (keyCode) {
	            case KeyEvent.KEYCODE_DPAD_RIGHT:
	                if (handleKeyEvent) {
	                    // Select the shop button if we aren't on it
	                    if (v != shop) {
	                        shop.requestFocus();
	                    }
	                }
	                wasHandled = true;
	                break;
	            case KeyEvent.KEYCODE_DPAD_DOWN:
	                if (handleKeyEvent) {
	                    // Select the content view (down is handled by the tab key handler otherwise)
	                    if (v == shop) {
	                        contents.requestFocus();
	                        wasHandled = true;
	                    }
	                }
	                break;
	            default: break;
	        }
	        return wasHandled;
	}
	
	
	/**
     * Private helper to get the parent TabHost in the view hiearchy.
     */
    private static TabHost findTabHostParent(View v) {
        ViewParent p = v.getParent();
        while (p != null && !(p instanceof TabHost)) {
            p = p.getParent();
        }
        return (TabHost) p;
    }

}
