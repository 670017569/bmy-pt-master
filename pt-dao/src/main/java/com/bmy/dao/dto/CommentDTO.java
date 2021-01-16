package com.bmy.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ApiModel("评论DTO")
public class CommentDTO {

    @ApiModelProperty("指向动态id")
    private Long dynId;

    @ApiModelProperty("指向用户id")
    private Long toUid;

    //父级评论id
    @ApiModelProperty("指向回复的评论id")
    private Long id;

    @ApiModelProperty("评论内容")
    private String content;

}
