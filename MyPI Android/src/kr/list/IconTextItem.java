package kr.list;

import android.graphics.drawable.Drawable;

public class IconTextItem {

	private Drawable mIcon;
	private String[] mData;

	public IconTextItem(Drawable icon, String[] obj) {
		mIcon = icon;
		mData = obj;
	}

	public IconTextItem(String num, String title, Drawable riskImg) {
		mIcon = riskImg;
		mData[0] = num;
		mData[1] = title;
	}

	public String[] getData() {
		return mData;
	}

	public String getData(int index) {
		if (mData == null || index >= mData.length) {
			return null;
		}

		return mData[index];
	}

	public void setData(String[] obj) {
		mData = obj;
	}

	public void setIcon(Drawable icon) {
		mIcon = icon;
	}

	public Drawable getIcon() {
		return mIcon;
	}
}
