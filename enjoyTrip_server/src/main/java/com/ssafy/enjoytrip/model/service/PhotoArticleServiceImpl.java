package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.enjoytrip.model.PhotoArticleDto;
import com.ssafy.enjoytrip.model.mapper.PhotoArticleMapper;

public class PhotoArticleServiceImpl implements PhotoArticleService{
	
	private PhotoArticleMapper photoArticleMapper;

	@Autowired
	public PhotoArticleServiceImpl(PhotoArticleMapper photoArticleMapper) {
		super();
		this.photoArticleMapper = photoArticleMapper;
	}

	@Override
	public void writPhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PhotoArticleDto> listPhotoArticle(Map<String, Object> param) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalPhotoArticleDtoCount(Map<String, Object> param) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void modifyPhotoArticleDto(PhotoArticleDto PpotoArticleDto) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePhotoArticleDto(int photo_article_id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
