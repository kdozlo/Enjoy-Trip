package com.ssafy.enjoytrip.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BoardDto : 댓글정보", description = "댓글의 정보를 나타낸다.")
public class ReplyDto {

    @ApiModelProperty(value = "댓글번호")
    private int replyId;
    @ApiModelProperty(value = "글번호")
    private int articleNo;
    @ApiModelProperty(value = "작성자 아이디")
    private String userId;
    @ApiModelProperty(value = "댓글내용")
    private String content;
    @ApiModelProperty(value = "작성일")
    private String createTime;
    @ApiModelProperty(value = "좋아요")
    private int pushLike;
}
