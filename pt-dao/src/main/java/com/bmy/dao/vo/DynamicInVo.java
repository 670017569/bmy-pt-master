package com.bmy.dao.vo;


import com.bmy.dao.domain.ex.DynamicPic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "动态参数模型")
public class DynamicInVo {


    //定位信息
    @ApiModelProperty("(可选)定位信息")
    private Integer region;

    //内容
    @ApiModelProperty("内容 不能为空 最长255")
    private String content;

    //图片
    @ApiModelProperty("图片数组")
    private List<DynamicPic> pics;

}
