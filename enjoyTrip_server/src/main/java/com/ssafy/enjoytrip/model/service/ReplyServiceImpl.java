package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.BoardListDto;
import com.ssafy.enjoytrip.model.FileInfoDto;
import com.ssafy.enjoytrip.model.ReplyDto;
import com.ssafy.enjoytrip.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.model.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplyServiceImpl implements ReplyService {

	private ReplyMapper replyMapper;

	@Autowired
	public ReplyServiceImpl(ReplyMapper replyMapper) {
		super();
		this.replyMapper = replyMapper;
	}

	@Override
	public void writeReply(ReplyDto replyDto) throws Exception {
		replyMapper.writeReply(replyDto);
	}

	@Override
	public int getTotalReplyCount(Map<String, Object> param) throws Exception {
		return replyMapper.getTotalReplyCount(param);
	}

	//
	@Override
	public List<ReplyDto> listReply(int articleNo) throws Exception {
		return replyMapper.listReply(articleNo);
	}

	@Override
	public void updateLike(int replyNo) throws Exception {
		replyMapper.updateLike(replyNo);
	}

	@Override
	public void modifyReply(ReplyDto replyDto) throws Exception {
		replyMapper.modifyReply(replyDto);
	}

	@Override
	public void deleteReply(int replyNo) throws Exception {
		replyMapper.deleteReply(replyNo);
	}
}
