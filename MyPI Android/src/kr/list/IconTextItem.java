package kr.list;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class IconTextItem {

	private Drawable mIcon;
	private String[] mData;

	public IconTextItem(Drawable icon, String[] obj) {
		mIcon = icon;
		mData = obj;
	}

	public IconTextItem(String num, String title, Drawable riskImg) {
		mIcon = riskImg;

		mData = new String[2];
		mData[0] = num;
		mData[1] = title;
	}

	public String[] getData() {
		return mData;
	}

	// selectable 함수 2가지

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

	// 다른 데이터와 비교해서 다르면 -1 리턴 아니면 오류
	public int compareTo(IconTextItem other) {
		if (mData != null) {
			String[] otherData = other.getData();
			if (mData.length == otherData.length) {
				for (int i = 0; i < mData.length; i++) {
					if (!mData[i].equals(otherData[i])) {
						return -1;
					}
				}
			} else {
				return -1;
			}
		} else {
			throw new IllegalArgumentException();
		}

		return 0;
	}
}
