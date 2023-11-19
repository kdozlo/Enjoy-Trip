package com.ssafy.enjoytrip.model;

public class Gugun {
	
	private int gugunCode;
	private String gugunName;
	private int sidoCode;
	
	public Gugun() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Gugun(int gugunCode, String gugunName, int sidoCode) {
		super();
		this.gugunCode = gugunCode;
		this.gugunName = gugunName;
		this.sidoCode = sidoCode;
	}

	public int getGugunCode() {
		return gugunCode;
	}
	
	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}
	
	public String getGugunName() {
		return gugunName;
	}
	
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	
	public int getSidoCode() {
		return sidoCode;
	}
	
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

}
