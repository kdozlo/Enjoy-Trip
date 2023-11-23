package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.HeartDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface PhotoArticleLikeMapper {

	void insert(HeartDto heartDto) throws SQLException;
	int select(HeartDto heartDto) throws SQLException;
	void delete(HeartDto heartDto) throws SQLException;
	int updateup(HeartDto heartDto) throws SQLException;
	int updatedown(HeartDto heartDto) throws SQLException;
}
