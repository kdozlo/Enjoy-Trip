package com.ssafy.enjoytrip.model;

public class Sido {
	
	private int sidoCode;
	private String sidoName;
	
	public Sido() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Sido(int sidoCode, String sidoName) {
		super();
		this.sidoCode = sidoCode;
		this.sidoName = sidoName;
	}

	public int getSidoCode() {
		return sidoCode;
	}
	
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	
	public String getSidoName() {
		return sidoName;
	}
	
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	
}
