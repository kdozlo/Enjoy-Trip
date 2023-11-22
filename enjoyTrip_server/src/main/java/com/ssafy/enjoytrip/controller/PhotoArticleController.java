package com.ssafy.enjoytrip.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.model.FileInfoDto;
import com.ssafy.enjoytrip.model.PhotoArticleDto;
import com.ssafy.enjoytrip.model.PhotoArticleListDto;
import com.ssafy.enjoytrip.model.service.PhotoArticleService;
import com.ssafy.enjoytrip.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/photoarticle")
@Api("포토 게시판 컨트롤러  API V1")
@Slf4j
public class PhotoArticleController {


	private PhotoArticleService photoArticleService;
	private JWTUtil jwtUtil;

	public PhotoArticleController(PhotoArticleService photoArticleService, JWTUtil jwtUtil) {
		super();
		this.photoArticleService = photoArticleService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping
	public ResponseEntity<?> writPhotoArticle(@RequestBody @ApiParam(value = "게시글 정보.", required = true) PhotoArticleDto photoArticleDto
	,@RequestPart @ApiParam(value = "파일정보.", required = false) MultipartFile file) throws Exception{
		HttpStatus status = HttpStatus.ACCEPTED;
		log.debug("MultipartFile.isEmpty : {}", file.isEmpty());
		if (!file.isEmpty()) {
			//파일이름 변경과정
			String today = new SimpleDateFormat("yyMMdd").format(new Date());

			String saveFolder = System.getProperty("user.dir") + "/../imgServer" + File.separator + today;
			
			log.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
		
			FileInfoDto fileInfoDto = new FileInfoDto();
			String originalFileName = file.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				photoArticleDto.setSaveFolder(today);
				photoArticleDto.setOriginalFile(originalFileName);
				photoArticleDto.setSaveFile(saveFileName);
				log.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", file.getOriginalFilename(), saveFileName);
				file.transferTo(new File(folder, saveFileName));
			}
		}

		try {
			photoArticleService.writPhotoArticle(photoArticleDto);

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("글작성 실패 : {}", e);
			return new ResponseEntity<Void>(status);
		}
	}

	@GetMapping
	public ResponseEntity<?> listPhotoArticle(
			@RequestParam Map<String, String> map) {
		HttpStatus status = HttpStatus.ACCEPTED;
		log.info("listPhotoArticle map - {}", map);
		try {
			
			PhotoArticleListDto photoArticleListDto = photoArticleService.listPhotoArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(photoArticleListDto);
		} catch (Exception e) {
			log.error("글 목록 조회 실패 : {}", e);
			return new ResponseEntity<String>(status);
		}
	}

	
}
