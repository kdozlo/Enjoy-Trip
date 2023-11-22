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
		HttpStatus status = HttpStatus.ACCEPTED;
		log.info(replyDto.getContent());
		log.info("writeReply - 호출 : {}" + replyDto.getArticleNo());
		log.info(replyDto.getUserId());
		try {
			log.info("writeReply - 호출 : {}" + replyDto.getArticleNo());
			replyService.writeReply(replyDto);
		} catch (Exception e) {
			log.error("댓글작성 실패 : {}", e);
			return new ResponseEntity<String>(status);
		}
		return new ResponseEntity<String>(status);
	}

	@ApiOperation(value = "댓글보기", notes = "글번호에 해당하는 게시글의 댓글들을 반환한다.")
	@GetMapping("/{articleno}")
	public ResponseEntity<List<ReplyDto>> listReply(
			@PathVariable("articleno") @ApiParam(value = "얻어올 댓글의 글번호.", required = true) int articleno)
			throws Exception {
		log.info("listReply - 호출 : " + articleno);
		replyService.updateLike(articleno);
		return new ResponseEntity<List<ReplyDto>>(replyService.listReply(articleno), HttpStatus.OK);
	}


	


	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}