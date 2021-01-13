package com.bmy.core.vo;

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

    private Long id;

    private String filename;

    private String bucket;

    private String url;


}
