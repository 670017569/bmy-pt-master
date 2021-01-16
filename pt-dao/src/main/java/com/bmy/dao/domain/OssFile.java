package com.bmy.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * 对象存储模型
 * @Created by f9miao
 */
@ApiModel("对象存储模型")
@Data
@Builder
public class OssFile {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String filename;

    private String bucket;

    private String url;


}
