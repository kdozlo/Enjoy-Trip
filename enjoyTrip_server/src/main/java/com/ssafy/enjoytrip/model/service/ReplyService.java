package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.BoardListDto;
import com.ssafy.enjoytrip.model.FileInfoDto;
import com.ssafy.enjoytrip.model.ReplyDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ReplyService {

	void writeReply(ReplyDto replyDto) throws Exception;

	int getTotalReplyCount(Map<String, Object> param) throws Exception;

	List<ReplyDto> listReply(int articleNo) throws Exception;

	void updateLike(int replyNo) throws Exception;

	void modifyReply(ReplyDto replyDto) throws Exception;

	void deleteReply(int replyNo) throws Exception;
}
