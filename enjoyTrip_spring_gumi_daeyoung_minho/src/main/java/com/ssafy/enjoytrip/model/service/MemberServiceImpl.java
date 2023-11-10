package com.ssafy.enjoytrip.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.model.MemberDto;
import com.ssafy.enjoytrip.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;

	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Transactional
	@Override
	public int joinMember(MemberDto memberDto) throws Exception {
		return memberMapper.joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {

		return memberMapper.loginMember(memberDto);
	}
	
	/* ADMIN */
	@Override
	public List<MemberDto> listMember() throws Exception {
		return memberMapper.listMember();
	}

	@Transactional
	@Override
	public void updateMember(MemberDto memberDto) throws Exception {
		memberMapper.updateMember(memberDto);
	}

	@Transactional
	@Override
	public void deleteMember(MemberDto memberDto) throws Exception {
		memberMapper.deleteMember(memberDto);		
	}

}
