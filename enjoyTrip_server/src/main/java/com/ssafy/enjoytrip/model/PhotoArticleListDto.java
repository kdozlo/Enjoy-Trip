package com.ssafy.enjoytrip.model;

import java.util.List;

public class PhotoArticleListDto {
	
	List<PhotoArticleDto> photoArticles;
	private int currentPage;
	private int totalPageCount;
	public List<PhotoArticleDto> getPhotoArticles() {
		return photoArticles;
	}
	public void setPhotoArticles(List<PhotoArticleDto> photoArticles) {
		this.photoArticles = photoArticles;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	

}
