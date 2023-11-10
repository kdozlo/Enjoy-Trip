package com.ssafy.enjoytrip.controller;

import java.util.List;

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

import com.ssafy.enjoytrip.model.Board;
import com.ssafy.enjoytrip.model.service.BoardService;

@RestController
@RequestMapping("/boardapi")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody Board board) {
		try {
			int result = boardService.insertBoard(board);
			
			return result > 0 ? new ResponseEntity<String>("글을 작성했습니다.",HttpStatus.OK) :
				new ResponseEntity<String>("글 작성을 실패했습니다.",HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> list() {
		try {
			return new ResponseEntity<List<Board>>(boardService.listBoard(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/detail/{article-no}")
	public ResponseEntity<?> detail(@PathVariable("article-no")int articleNo) {
		try {
			Board board = boardService.detailBoard(articleNo);
			return board != null ? new ResponseEntity<Board>(boardService.detailBoard(articleNo), HttpStatus.OK) :
				new ResponseEntity<String>("존재하지 않은 글 입니다.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Board board) {
		try {
			int result = boardService.updateBoard(board);
			
			return result > 0 ? new ResponseEntity<String>("글을 수정했습니다.",HttpStatus.OK) :
				new ResponseEntity<String>("글 수정을 실패했습니다.",HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody Board board) {
		try {
			int result = boardService.deleteBoard(board);
			
			return result > 0 ? new ResponseEntity<String>("글을 삭제했습니다.",HttpStatus.OK) :
				new ResponseEntity<String>("글 삭제를 실패했습니다.",HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
