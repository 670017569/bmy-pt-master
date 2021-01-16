package com.bmy.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel("文件传输DTO")
public class OssFileDTO {

    @ApiModelProperty("文件id")
    private Long id;

    @ApiModelProperty("文件url路径")
    private String url;

}
