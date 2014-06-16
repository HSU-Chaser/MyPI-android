package kr.list;

public class ChildItem {
	
	private String snippet = null;
	private String solution1 = null;
	private String solution2 = null;
	
	
	public ChildItem(String snippet, String solution1, String solution2){
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

}
