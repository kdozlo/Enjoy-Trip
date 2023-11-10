package com.ssafy.enjoytrip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.model.Board;
import com.ssafy.enjoytrip.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Transactional
	@Override
	public int insertBoard(Board board) throws Exception {
		return boardMapper.insertBoard(board);
	}
	
	@Override
	public List<Board> listBoard() throws Exception {
		return boardMapper.listBoard();
	}
	
	@Override
	public Board detailBoard(int articleNo) throws Exception {
		return boardMapper.detailBoard(articleNo);
	}
	
	
	@Transactional
	@Override
	public int updateBoard(Board board) throws Exception {
		return boardMapper.updateBoard(board);
	}
	
	@Transactional
	@Override
	public int deleteBoard(Board board) throws Exception {
		return boardMapper.deleteBoard(board);
	}
	
}
