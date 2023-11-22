package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.model.PhotoArticleDto;

public interface PhotoArticleService {
	
	void writPhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException;

	List<PhotoArticleDto> listPhotoArticle(Map<String, Object> param) throws SQLException;

	int getTotalPhotoArticleDtoCount(Map<String, Object> param) throws SQLException;

	void modifyPhotoArticleDto(PhotoArticleDto PpotoArticleDto) throws SQLException;

	void deletePhotoArticleDto(int photo_article_id) throws SQLException;

}
