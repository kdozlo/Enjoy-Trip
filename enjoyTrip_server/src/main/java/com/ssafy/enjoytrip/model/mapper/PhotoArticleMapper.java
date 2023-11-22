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

	int getTotalPhotoArticleCount(Map<String, Object> param) throws SQLException;

	void modifyPhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException;

	void deletePhotoArticle(int photoArticleId) throws SQLException;

}
