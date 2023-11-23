package com.ssafy.enjoytrip.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.model.PhotoArticleDto;
import com.ssafy.enjoytrip.model.PhotoArticleListDto;
import com.ssafy.enjoytrip.model.mapper.PhotoArticleMapper;

@Service
public class PhotoArticleServiceImpl implements PhotoArticleService{
	
	private PhotoArticleMapper photoArticleMapper;

	@Autowired
	public PhotoArticleServiceImpl(PhotoArticleMapper photoArticleMapper) {
		super();
		this.photoArticleMapper = photoArticleMapper;
	}

	@Override
	@Transactional
	public int writePhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException {
		// TODO Auto-generated method stub
		return photoArticleMapper.writePhotoArticle(PpotoArticleDto);
    }

	@Override
	public PhotoArticleListDto listPhotoArticle(Map<String, String> map) throws SQLException {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "p.user_id");
		List<PhotoArticleDto> list = photoArticleMapper.listPhotoArticle(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = photoArticleMapper.getTotalPhotoArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		PhotoArticleListDto photoArticleListDto = new PhotoArticleListDto();
		photoArticleListDto.setPhotoArticles(list);
		photoArticleListDto.setCurrentPage(currentPage);
		photoArticleListDto.setTotalPageCount(totalPageCount);
		
		return photoArticleListDto;
	}
	@Override
	public PhotoArticleListDto bestListPhotoArticle() throws SQLException {
		List<PhotoArticleDto> list = photoArticleMapper.bestListPhotoArticle();
		
		PhotoArticleListDto photoArticleListDto = new PhotoArticleListDto();
		photoArticleListDto.setPhotoArticles(list);
		
		return photoArticleListDto; 
	}

	@Override
	@Transactional
	public void modifyPhotoArticle(PhotoArticleDto PpotoArticleDto) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void deletePhotoArticle(int photo_article_id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
