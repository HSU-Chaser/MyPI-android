package kr.object;

public class StaticItem {
	private String siteImage;
	private String siteName;
	private String url;

	public StaticItem(String siteImage, String siteName, String url) {
		this.siteImage = siteImage;
		this.siteName = siteName;
		this.url = url;
	}

	public String getSiteImage() {
		return siteImage;
	}

	public void setSiteImage(String siteImage) {
		this.siteImage = siteImage;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
