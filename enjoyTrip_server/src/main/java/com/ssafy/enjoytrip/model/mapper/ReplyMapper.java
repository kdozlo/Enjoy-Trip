package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.FileInfoDto;
import com.ssafy.enjoytrip.model.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReplyMapper {

	void writeReply(ReplyDto replyDto) throws SQLException;

	int getTotalReplyCount(Map<String, Object> param) throws SQLException;

	List<ReplyDto> listReply(int articleNo) throws SQLException;

	void updateLike(int replyNo) throws SQLException;

	void modifyReply(ReplyDto replyDto) throws SQLException;

	void deleteReply(int replyNo) throws SQLException;
}
