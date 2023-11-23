package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.HeartDto;
import com.ssafy.enjoytrip.model.PhotoArticleDto;
import com.ssafy.enjoytrip.model.PhotoArticleListDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Map;

public interface PhotoArticleLikeService {
	
	int insert(HeartDto heartDto) throws Exception;
/*
	void delete(HeartDto heartDto) throws Exception;
*/
}
