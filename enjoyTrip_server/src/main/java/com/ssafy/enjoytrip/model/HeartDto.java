package com.ssafy.enjoytrip.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "HeartDto : 좋아요정보", description = "좋아요의 상세 기록을 나타낸다.")
public class HeartDto {

    @ApiModelProperty(value = "좋아요 고유번호")
    private int heartId;

    @ApiModelProperty(value = "유저 아이디")
    private String userId;

    @ApiModelProperty(value = "게시글 번호")
    private int photoArticleId;

}
