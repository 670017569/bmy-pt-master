package com.bmy.core.vo;

import io.swagger.annotations.ApiModel;

/**
 * 对象存储模型
 * @Created by f9miao
 */
@ApiModel("对象存储模型")
public class OssFile {

    private String filename;

    private String bucket;

    private String url;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
