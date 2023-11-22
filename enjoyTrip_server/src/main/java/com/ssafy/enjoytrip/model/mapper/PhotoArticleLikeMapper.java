package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.HeartDto;
import com.ssafy.enjoytrip.model.PhotoArticleDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface PhotoArticleLikeMapper {

	void insert(HeartDto heartDto) throws SQLException;
	int select(HeartDto heartDto) throws SQLException;
	void delete(HeartDto heartDto) throws SQLException;
	void updateup(HeartDto heartDto) throws SQLException;
	void updatedown(HeartDto heartDto) throws SQLException;
}
