package com.ssafy.enjoytrip.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.BoardListDto;
import com.ssafy.enjoytrip.model.FileInfoDto;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;
	BoardListDto listArticle(Map<String, String> map) throws Exception;
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	int updateHit(int articleNo) throws Exception;
	
	void modifyArticle(BoardDto boardDto) throws Exception;
//
	void deleteFile(int articleNo) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	public List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
}
