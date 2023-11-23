package com.ssafy.enjoytrip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ssafy.enjoytrip.util.JWTUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MemberController {


    private MemberService memberService;
    private JWTUtil jwtUtil;

    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
        super();
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value="/join")
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

    @GetMapping(value="/{userid}")
    public ResponseEntity<?> idCheck(@PathVariable("userid") String userId) throws Exception {
        int cnt = memberService.idCheck(userId);
        return cnt > 0 ? new ResponseEntity<String>("해당 아이디는 이미 존재 합니다.", HttpStatus.OK) : new ResponseEntity<String>("사용 가능한 아이디 입니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
    @PostMapping(value="/login")
    public ResponseEntity<?> login(
            @RequestBody MemberDto memberDto) {
        log.debug("login user : {}", memberDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            MemberDto loginUser = memberService.loginMember(memberDto);
            if (loginUser != null) {
                String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
                String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
                log.debug("access token : {}", accessToken);
                log.debug("refresh token : {}", refreshToken);
//				발급받은 refresh token을 DB에 저장.
                int result  = memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);
                if(result ==0){
                    return new ResponseEntity<String>("회원 로그인 실패", HttpStatus.BAD_REQUEST);
                }
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                return new ResponseEntity<String>("아이디 또는 패스워드를 확인해주세요.", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.debug("로그인 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<String>("로그인 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);
    }

    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping(value="/info/{userId}")
    public ResponseEntity<?> getInfo(
            @PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) { //사용가능한 토큰인지 아닌지 확인
            log.info("사용 가능한 토큰!!!");
            if(userId.equals(jwtUtil.getUserId(request.getHeader("Authorization")))) { //비교를 토큰이랑 현재로그인된 아이디랑
                log.info("회원정보 조회 가능!!!");
                try {
//				로그인 사용자 정보.
                    MemberDto memberDto = memberService.userInfo(userId);
                    if (memberDto == null){
                        return new ResponseEntity<String>("정보조회 실패", HttpStatus.BAD_REQUEST);
                    }
                    resultMap.put("userInfo", memberDto);
                } catch (Exception e) {
                    log.error("정보조회 실패 : ", e);
                    resultMap.put("message", e.getMessage());
                    return new ResponseEntity<String>("정보조회 실패", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                return new ResponseEntity<String>("토큰과 일치하지 않습니다!!!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("사용 불가능 토큰!!!", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    // 회원 정보 수정
    @ApiOperation(value = "회원정보수정")
    @PutMapping(value="/updatemember")
    public ResponseEntity<?> update(@RequestBody MemberDto member, HttpServletRequest request) {
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) { //사용가능한 토큰인지
            log.info("사용가능 토큰!!!");
            if(member.getUserId().equals(jwtUtil.getUserId(request.getHeader("Authorization")))) { //비교를 토큰이랑 현재로그인된 아이디랑
                log.info("정보수정 가능!!!");
                try {
                    int result = memberService.updateMember(member);
                    log.info("정보수정 완료!!!");
                    if(result == 0) {
                        return new ResponseEntity<String>("회원 정보 수정 실패", HttpStatus.BAD_REQUEST);
                    }
                    return new ResponseEntity<String>("회원 정보가 수정되었습니다.", HttpStatus.OK);
                } catch (Exception e) {
                    log.error("회원정보수정 실패 : {}", e);
                    return new ResponseEntity<String>("회원정보수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else{
                log.error("토큰과 일치하지 않습니다!!!");
                return new ResponseEntity<String>("토큰과 일치하지 않습니다!!!", HttpStatus.BAD_REQUEST);
            }
        } else { //사용가능 토큰이 아니면
            log.error("로그인이 만료되었습니다!!!");
            return new ResponseEntity<String>("로그인 만료", HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 정보 탈퇴
    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping(value="/deletemember")
    public ResponseEntity<?> delete(@RequestBody MemberDto member, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) { //사용가능한 토큰인지
            log.info("사용가능 토큰!!!");
            if(member.getUserId().equals(jwtUtil.getUserId(request.getHeader("Authorization")))) { //비교를 토큰이랑 현재로그인된 아이디랑
                log.info("회원탈퇴 가능!!!");
                try {
                    int result = memberService.deleteMember(member);
                    log.info("회원탈퇴 완료!!!");
                    if(result == 0) {
                    	status = HttpStatus.BAD_REQUEST;
                    	return new ResponseEntity<String>("회원 탈퇴가 실패 했습니다.", status);
                    }
                    	
                    return new ResponseEntity<String>("회원 탈퇴 되었습니다.", status);
                } catch (Exception e) {
                    log.error("회원탈퇴 실패 : {}", e);
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
            else{
                log.error("토큰과 일치하지 않습니다!!!");
                return new ResponseEntity<String>("토큰과 일치하지 않습니다!!!", HttpStatus.BAD_REQUEST);
            }
        } else { //사용가능 토큰이 아니면
            log.error("로그인이 만료되었습니다!!!");
            return new ResponseEntity<String>("사용 불가능 토큰!!!", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<String>("", status);

    }

    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
    @GetMapping(value="/logout/{userId}")
    public ResponseEntity<?> removeToken(@PathVariable("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int result = memberService.deleRefreshToken(userId);
          
        } catch (Exception e) {
            log.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<String>("로그아웃 실패", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<String>("로그아웃 하였습니다.", HttpStatus.OK);

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


    @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String token = request.getHeader("refreshToken");
        log.debug("token : {}, memberDto : {}", token, memberDto);
        if (jwtUtil.checkToken(token)) {
            if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
                String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
                log.debug("token : {}", accessToken);
                log.debug("정상적으로 액세스토큰 재발급!!!");
                resultMap.put("access-token", accessToken);
            }
        } else {
            log.debug("리프레쉬토큰도 사용불가!!!!!!!");
            return new ResponseEntity<String>("Refresh 토큰 사용불가", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
