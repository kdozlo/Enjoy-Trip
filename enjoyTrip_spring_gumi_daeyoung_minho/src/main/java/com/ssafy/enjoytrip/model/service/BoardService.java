package com.ssafy.enjoytrip.model.service;

import java.util.List;

import com.ssafy.enjoytrip.model.Board;

public interface BoardService {

	int insertBoard(Board board) throws Exception;
	
	List<Board> listBoard() throws Exception;
	
	Board detailBoard(int articleNo) throws Exception;
	
	int updateBoard(Board board) throws Exception;
	
	int deleteBoard(Board board) throws Exception;
}
