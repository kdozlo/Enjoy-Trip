package com.ssafy.enjoytrip.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.model.MemberDto;

public interface MemberService {

	int idCheck(String userId) throws Exception;
	int joinMember(MemberDto memberDto) throws Exception;

//	/* Admin */
	List<MemberDto> listMember() throws Exception;
	void updateMember(MemberDto memberDto) throws Exception;
	void deleteMember(MemberDto memberDto) throws Exception;

	//
	MemberDto loginMember(MemberDto memberDto) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;

}
