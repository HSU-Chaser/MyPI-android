package kr.object;

import android.graphics.Bitmap;

public class StaticItem {
	private Bitmap siteImage;
	private String siteName;
	private String siteURL;
	private String url;

	public StaticItem(String siteURL, String siteName, String url) {
		this.siteURL = siteURL;
		this.siteName = siteName;
		this.url = url;
	}

	public void setSiteImage(Bitmap bitmap) {
		this.siteImage = bitmap;
	}

	public String getSiteURL() {
		return siteURL;
	}

	public Bitmap getSiteImage() {
		return siteImage;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getUrl() {
		return url;
	}
}
