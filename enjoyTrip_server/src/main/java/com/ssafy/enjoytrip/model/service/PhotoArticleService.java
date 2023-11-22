package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.enjoytrip.model.PhotoArticleDto;
import com.ssafy.enjoytrip.model.PhotoArticleListDto;

public interface PhotoArticleService {
	
	void writPhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException;

	PhotoArticleListDto listPhotoArticle(Map<String, String> map) throws SQLException;

	void modifyPhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException;

	void deletePhotoArticle(int photo_article_id) throws SQLException;

}
