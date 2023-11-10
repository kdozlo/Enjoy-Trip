package com.ssafy.enjoytrip.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.Board;

@Mapper
public interface BoardMapper {

	int insertBoard(Board board) throws SQLException;
	
	List<Board> listBoard() throws SQLException;
	
	Board detailBoard(int articleNo) throws SQLException;
	
	int updateBoard(Board board) throws SQLException;
	
	int deleteBoard(Board board) throws SQLException;
	
}
