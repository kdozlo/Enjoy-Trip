package com.ssafy.enjoytrip.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.MemberDto;
import com.ssafy.enjoytrip.model.service.MemberService;

@RestController
@RequestMapping("/memberapi")
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody MemberDto member) {
		 System.out.println(member.toString()); 
		try {
		 	int result = memberService.joinMember(member);
		 	
		 	return result > 0 ? new ResponseEntity<String>("회원가입 성공했습니다.", HttpStatus.OK) : 
		 		new ResponseEntity<String>("회원가입 실패했습니다.", HttpStatus.OK);
		 	
		 } catch (Exception e) {
		 	return exceptionHandling(e);
		 }
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<?> idCheck(@PathVariable("userid") String userId) throws Exception {
		int cnt = memberService.idCheck(userId);
		
		return cnt > 0 ? new ResponseEntity<String>("해당 아이디는 이미 존재 합니다.", HttpStatus.OK) : new ResponseEntity<String>("사용 가능한 아이디 입니다.", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MemberDto member, HttpSession session) {
		try { 
			System.out.println(member);
			MemberDto memberDto = memberService.loginMember(member);
			if(memberDto != null) {
				session.setAttribute("memberInfo", memberDto);
				
				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("해당 아이디가 없습니다.", HttpStatus.OK);
			}
		} catch (Exception e) {
			return  exceptionHandling(e);
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<String>("로그아웃 되었습니다.", HttpStatus.OK);
	}
	
	// 회원 정보 조회
	@GetMapping("/listmember")
	public ResponseEntity<?> list() {
		try {
			return new ResponseEntity<List<MemberDto>>(memberService.listMember(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	// 회원 정보 수정
	@PutMapping("/updatemember")
	public ResponseEntity<?> update(@RequestBody MemberDto member) {
		try {
			memberService.updateMember(member);
			return new ResponseEntity<String>("회원 정보가 수정되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	// 회원 정보 탈퇴
	@DeleteMapping("/deletemember")
	public ResponseEntity<?> delete(@RequestBody MemberDto member) {
		try {
			memberService.deleteMember(member);
			return new ResponseEntity<String>("탈퇴 되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
