package kr.list;

public class ChildItem {
	private String url = null;
	private String urlDelete = null;
	private String center = null;
	private String snippet = null;
	private String solution1 = null;
	private String solution2 = null;

	public ChildItem(String url, String urlDelete, String center,
			String snippet, String solution1, String solution2) {
		this.url = url;
		if(urlDelete.equals("Google")) {
			this.urlDelete = "https://support.google.com/websearch/troubleshooter/3111061?hl=ko&ref_topic=3285072#ts=2889054,2889099,2889104";
		} else if(urlDelete.equals("Naver")) {
			this.urlDelete = "https://help.naver.com/support/contents/contents.nhn?serviceNo=607&categoryNo=2828";
		} else {
			this.urlDelete = "";
		}
		this.center = center;
		this.snippet = snippet;
		this.solution1 = solution1;
		this.solution2 = solution2;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getSolution1() {
		return solution1;
	}

	public void setSolution1(String solution1) {
		this.solution1 = solution1;
	}

	public String getSolution2() {
		return solution2;
	}

	public void setSolution2(String solution2) {
		this.solution2 = solution2;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlDelete() {
		return urlDelete;
	}

	public void setUrlDelete(String urlDelete) {
		this.urlDelete = urlDelete;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

}
