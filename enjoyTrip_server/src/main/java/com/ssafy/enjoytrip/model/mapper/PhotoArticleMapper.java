package com.ssafy.enjoytrip.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.PhotoArticleDto;

@Mapper
public interface PhotoArticleMapper {

	int writePhotoArticle(PhotoArticleDto PhotoArticleDto) throws SQLException;

	List<PhotoArticleDto> listPhotoArticle(Map<String, Object> param) throws SQLException;

	int getTotalPhotoArticleCount(Map<String, Object> param) throws SQLException;

	void modifyPhotoArticle(PhotoArticleDto PhotoArticleDto) throws SQLException;

	void deletePhotoArticle(int photoArticleId) throws SQLException;

}
