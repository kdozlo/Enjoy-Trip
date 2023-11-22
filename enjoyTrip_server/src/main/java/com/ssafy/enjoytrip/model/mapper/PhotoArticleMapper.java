package com.ssafy.enjoytrip.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.PhotoArticleDto;

@Mapper
public interface PhotoArticleMapper {

	void writPhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException;

	List<PhotoArticleDto> listPhotoArticle(Map<String, Object> param) throws SQLException;

	int getTotalPhotoArticleDtoCount(Map<String, Object> param) throws SQLException;

	void modifyPhotoArticleDto(PhotoArticleDto PpotoArticleDto) throws SQLException;

	void deletePhotoArticleDto(int photo_article_id) throws SQLException;

}
