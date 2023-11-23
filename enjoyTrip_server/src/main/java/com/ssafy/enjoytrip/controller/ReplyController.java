package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.BoardListDto;
import com.ssafy.enjoytrip.model.FileInfoDto;
import com.ssafy.enjoytrip.model.ReplyDto;
import com.ssafy.enjoytrip.model.service.BoardService;
import com.ssafy.enjoytrip.model.service.ReplyService;
import com.ssafy.enjoytrip.util.JWTUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

//http://localhost/vue/swagger-ui.html
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/reply")
@Api("댓글 컨트롤러  API V1")
@Slf4j
public class ReplyController {

//	private static final String SUCCESS = "success";
//	private static final String FAIL = "fail";


	private ReplyService replyService;
	private JWTUtil jwtUtil;

	public ReplyController(ReplyService replyService, JWTUtil jwtUtil) {
		super();
		this.replyService = replyService;
		this.jwtUtil = jwtUtil;
	}

	@ApiOperation(value = "댓글작성", notes = "글번호에 해당하는 게시글에 댓글작성")
	@PostMapping
	public ResponseEntity<?> writeReply(
			@RequestBody @ApiParam(value = "댓글 작성 내용.", required = true) ReplyDto replyDto)
			throws Exception {
		log.info(replyDto.getContent());
		log.info("writeReply - 호출 : {}" + replyDto.getArticleNo());
		log.info(replyDto.getUserId());
		try {
			log.info("writeReply - 호출 : {}" + replyDto.getArticleNo());
			replyService.writeReply(replyDto);
		} catch (Exception e) {
			log.error("댓글작성 실패 : {}", e);
			return new ResponseEntity<String>("댓글작성 실패",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("댓글작성 성공",HttpStatus.OK);
	}

	@ApiOperation(value = "댓글보기", notes = "글번호에 해당하는 게시글의 댓글들을 반환한다.")
	@GetMapping(value="/{articleno}")
	public ResponseEntity<?> listReply(
			@PathVariable("articleno") @ApiParam(value = "얻어올 댓글의 글번호.", required = true) int articleno)
			throws Exception {
		log.info("listReply - 호출 : " + articleno);
		try {
			List<ReplyDto> replys= replyService.listReply(articleno);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(replys);
		} catch (Exception e) {
			log.error("글 목록 조회 실패 : {}", e);
			return new ResponseEntity<String>("글 목록 조회 실패 : {}",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "댓글수정", notes = "수정할 댓글 정보를 입력한다.")
	@PutMapping
	public ResponseEntity<String> modifyReply(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) ReplyDto replyDto ,HttpServletRequest request) throws Exception {
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) { //사용가능한 토큰인지
			log.info("사용가능 토큰!!!");
			if(replyDto.getUserId().equals(jwtUtil.getUserId(request.getHeader("Authorization")))) { //비교를 토큰이랑 현재로그인된 아이디랑
				try {
					log.info("댓글수정 실행 - 호출 {}", replyDto);
					replyService.modifyReply(replyDto);
				} catch (Exception e) {
					log.error("댓글 수정 실패 : {}", e);
					return new ResponseEntity<String>("댓글 수정 실패",HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}else{
				log.error("토큰과 일치하지 않습니다!!!");
				return new ResponseEntity<String>("토큰과 일치하지 않습니다!!!",HttpStatus.BAD_REQUEST);
			}
		} else { //사용가능 토큰이 아니면
			log.error("로그인이 만료되었습니다!!!");
			return new ResponseEntity<String>("로그인이 만료되었습니다!!!",HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>("댓글 수정 성공",HttpStatus.OK);
	}

	@ApiOperation(value = "댓글삭제", notes = "댓글ID에 해당하는 댓글의 정보를 삭제한다.")
	@DeleteMapping(value="/{userid}/{replyid}")
	public ResponseEntity<String> deleteArticle(@PathVariable("replyid") @ApiParam(value = "삭제할 댓글의 글번호.", required = true) int replyId,
												@PathVariable("userid") String userId, HttpServletRequest request) throws Exception {
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) { //사용가능한 토큰인지
			log.info("사용가능 토큰!!!");
			if(userId.equals(jwtUtil.getUserId(request.getHeader("Authorization")))) { //비교를 토큰이랑 현재로그인된 아이디랑
				try {
					log.info("댓글 삭제 - 호출");
					replyService.deleteReply(replyId);
				} catch (Exception e) {
					log.error("댓글 삭제 실패 : {}", e);
					return new ResponseEntity<String>("댓글 삭제 실패",HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}else{
				log.error("토큰과 일치하지 않습니다!!!");
				return new ResponseEntity<String>("토큰과 일치하지 않습니다!!!",HttpStatus.BAD_REQUEST);
			}
		} else { //사용가능 토큰이 아니면
			log.error("로그인이 만료되었습니다!!!");
			return new ResponseEntity<String>("로그인이 만료되었습니다!!!",HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>("댓글 삭제 성공",HttpStatus.OK);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}