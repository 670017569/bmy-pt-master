package com.bmy.dao.dto;

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
public class OssFileDTO {

    private Long id;

    private String filename;

    private String bucket;

    private String url;


}
