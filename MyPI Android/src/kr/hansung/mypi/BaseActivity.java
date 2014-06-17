package kr.hansung.mypi;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.TextView;

public class BaseActivity extends Activity {
	private static CookieManager cookieManager;
	private static Typeface mTypeface;

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);

		if (BaseActivity.mTypeface == null)
			BaseActivity.mTypeface = Typeface.createFromAsset(getAssets(),
					"fonts/GodoM.ttf");

		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		setGlobalFont(root);
	}

	void setGlobalFont(ViewGroup root) {
		for (int i = 0; i < root.getChildCount(); i++) {
			View child = root.getChildAt(i);
			if (child instanceof TextView) {
				((TextView) child).setTypeface(mTypeface);
			} else if (child instanceof ViewGroup) {
				setGlobalFont((ViewGroup) child);
			}
		}
	}

	public static CookieManager getCookieManager() {
		return cookieManager;
	}

	public static void setCookieManager(CookieManager cookieManager) {
		BaseActivity.cookieManager = cookieManager;
	}

}
