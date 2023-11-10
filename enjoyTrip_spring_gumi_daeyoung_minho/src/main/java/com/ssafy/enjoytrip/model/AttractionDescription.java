package com.ssafy.enjoytrip.model;

public class AttractionDescription {
	
	private int contentId;
	private String homepage;
	private String overview;
	private String telname;
	
	public AttractionDescription() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AttractionDescription(int contentId, String homepage, String overview, String telname) {
		super();
		this.contentId = contentId;
		this.homepage = homepage;
		this.overview = overview;
		this.telname = telname;
	}

	public int getContentId() {
		return contentId;
	}
	
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	public String getHomepage() {
		return homepage;
	}
	
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	public String getOverview() {
		return overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public String getTelname() {
		return telname;
	}
	
	public void setTelname(String telname) {
		this.telname = telname;
	}
	
}
