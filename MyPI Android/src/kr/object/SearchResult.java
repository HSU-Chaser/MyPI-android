package kr.object;

import java.io.Serializable;

public class SearchResult implements Serializable {
	private static final long serialVersionUID = 1L;
	String engine = null;
	String title = null;
	String url = null;
	String snippet = null;
	String searchPage = null;
	int pr = 0;

	double exposure = 0; // 노출도

	public SearchResult(String engine, String title, String url,
			String snippet, String searchPage, double exposure) {
		this.engine = engine;
		this.title = title;
		this.url = url;
		this.snippet = snippet;
		this.searchPage = searchPage;
		this.exposure = exposure;
	}

	public int getPr() {
		return pr;
	}

	public void setPr(int pr) {
		this.pr = pr;
	}

	public double getExposure() {
		return exposure;
	}

	public void setExposure(double exposure) {
		this.exposure = exposure;
	}

	public String getTitle() {
		return title;
	}

	public String getSearchPage() {
		return searchPage;
	}

	public void setSearchPage(String searchPage) {
		this.searchPage = searchPage;
	}

	public String getURL() {
		return url;
	}

	public String getSnippet() {
		return snippet;
	}

	public String getEngine() {
		return engine;
	}

}