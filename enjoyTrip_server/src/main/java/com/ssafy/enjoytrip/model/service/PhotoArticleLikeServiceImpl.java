package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.HeartDto;
import com.ssafy.enjoytrip.model.PhotoArticleDto;
import com.ssafy.enjoytrip.model.PhotoArticleListDto;
import com.ssafy.enjoytrip.model.mapper.PhotoArticleLikeMapper;
import com.ssafy.enjoytrip.model.mapper.PhotoArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PhotoArticleLikeServiceImpl implements PhotoArticleLikeService{

	private PhotoArticleLikeMapper photoArticleLikeMapper;

	@Autowired
	public PhotoArticleLikeServiceImpl(PhotoArticleLikeMapper photoArticleLikeMapper) {
		super();
		this.photoArticleLikeMapper = photoArticleLikeMapper;
	}

	@Override
	public void insert(HeartDto heartDto) throws Exception {
		if (photoArticleLikeMapper.select(heartDto) == 0) {
			photoArticleLikeMapper.insert(heartDto);
			photoArticleLikeMapper.updateup(heartDto);
		} else {
			photoArticleLikeMapper.delete(heartDto);
			photoArticleLikeMapper.updatedown(heartDto);
		}
	}
/*
	@Override
	public void delete(HeartDto heartDto) throws Exception {
		if (photoArticleLikeMapper.select(heartDto) == 0) {
			photoArticleLikeMapper.insert(heartDto);
			photoArticleLikeMapper.updateup(heartDto);
		} else {
			photoArticleLikeMapper.delete(heartDto);
			photoArticleLikeMapper.updatedown(heartDto);
		}
	}
	*/
}
