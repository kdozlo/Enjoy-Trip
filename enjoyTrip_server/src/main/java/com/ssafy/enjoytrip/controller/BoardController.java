package com.ssafy.enjoytrip.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.model.service.MemberService;
import com.ssafy.enjoytrip.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.BoardDto;
import com.ssafy.enjoytrip.model.BoardListDto;
import com.ssafy.enjoytrip.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;

//http://localhost/vue/swagger-ui.html
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API V1")
@Slf4j
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
//	private static final String SUCCESS = "success";
//	private static final String FAIL = "fail";

	private BoardService boardService;
	private JWTUtil jwtUtil;

	public BoardController(BoardService boardService, JWTUtil jwtUtil) {
		super();
		this.boardService = boardService;
		this.jwtUtil = jwtUtil;
	}

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<?> writeArticle(@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto) {
		HttpStatus status = HttpStatus.ACCEPTED;
		log.info("writeArticle boardDto - {}", boardDto);
		try {
			boardService.writeArticle(boardDto);
//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("글작성 실패 : {}", e);
			return new ResponseEntity<String>(status);
		}
	}

	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> listArticle(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		HttpStatus status = HttpStatus.ACCEPTED;
		log.info("listArticle map - {}", map);
		try {
			BoardListDto boardListDto = boardService.listArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			log.error("글 목록 조회 실패 : {}", e);
			return new ResponseEntity<String>(status);
		}
	}

	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<BoardDto> getArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
		boardService.updateHit(articleno);
		System.out.println(boardService.getArticle(articleno));
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}

	@ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getModifyArticle - 호출 : " + articleno);
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto ,HttpServletRequest request) throws Exception {
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) { //사용가능한 토큰인지
			log.info("사용가능 토큰!!!");
			if(boardDto.getUserId().equals(jwtUtil.getUserId(request.getHeader("Authorization")))) { //비교를 토큰이랑 현재로그인된 아이디랑
				try {
					log.info("글수정 실행 - 호출 {}", boardDto);
					boardService.modifyArticle(boardDto);
				} catch (Exception e) {
					log.error("게시글 수정 실패 : {}", e);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}else{
				log.error("토큰과 일치하지 않습니다!!!");
				status = HttpStatus.BAD_REQUEST;
			}
		} else { //사용가능 토큰이 아니면
			log.error("로그인이 만료되었습니다!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<String>(status);
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{userid}/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno,
												@PathVariable("userid") String userId, HttpServletRequest request) throws Exception {
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) { //사용가능한 토큰인지
			log.info("사용가능 토큰!!!");
			if(userId.equals(jwtUtil.getUserId(request.getHeader("Authorization")))) { //비교를 토큰이랑 현재로그인된 아이디랑
				try {
					logger.info("게시글 삭제 - 호출");
					boardService.deleteArticle(articleno);
				} catch (Exception e) {
					log.error("게시글 삭제 실패 : {}", e);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}else{
				log.error("토큰과 일치하지 않습니다!!!");
				status = HttpStatus.BAD_REQUEST;
			}
		} else { //사용가능 토큰이 아니면
			log.error("로그인이 만료되었습니다!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<String>(status);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}