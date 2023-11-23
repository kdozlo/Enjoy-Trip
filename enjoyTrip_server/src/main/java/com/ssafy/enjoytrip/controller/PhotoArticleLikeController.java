package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.HeartDto;
import com.ssafy.enjoytrip.model.PhotoArticleDto;
import com.ssafy.enjoytrip.model.service.PhotoArticleLikeService;

import com.ssafy.enjoytrip.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST}, maxAge = 6000)
@RestController
@RequestMapping("/photoarticlelikes")
@AllArgsConstructor
@Api("포토 좋아요 컨트롤러  API V1")
@Slf4j
public class PhotoArticleLikeController {

    private PhotoArticleLikeService photoArticleLikeService;
    private JWTUtil jwtUtil;

    @PostMapping(produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> push(
            @RequestBody @ApiParam(value = "포토게시글 정보.", required = true) PhotoArticleDto photoArticleDto,
            HttpServletRequest request) throws Exception {
        try {
            HeartDto heartDto = new HeartDto();
            log.info(photoArticleDto.toString());
            heartDto.setUserId(jwtUtil.getUserId(request.getHeader("Authorization")));
            heartDto.setPhotoArticleId(photoArticleDto.getPhotoArticleId());
            log.info(heartDto.toString());
            int result = photoArticleLikeService.insert(heartDto);
            if(result == 0){
                return new ResponseEntity<String>("좋아요 실패",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("좋아요 실패", e);
            return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("좋아요 성공",HttpStatus.OK);
    }
/*
    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestBody @ApiParam(value = "포토게시글 정보.", required = true) HeartDto Dto, HttpServletRequest request) throws Exception {
        try {
            HeartDto heartDto = new HeartDto();
            heartDto.setUserId(jwtUtil.getUserId(request.getHeader("Authorization")));
            heartDto.setPhotoArticleId(Dto.getPhotoArticleId());
            log.info(heartDto.toString());
            photoArticleLikeService.delete(heartDto);
        } catch (Exception e) {
            log.error("좋아요 취소 실패 : {}", e);
            return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    */
}
