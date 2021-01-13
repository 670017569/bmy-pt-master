package com.bmy.core.service;

import com.bmy.core.vo.OssFile;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {


    /**
     * 检查确认存储桶
     * @param bucket
     */
    public void checkBucket(String bucket);

    /**
     * 上传一个文件
     * @param file
     * @param bucket
     * @param name
     * @return
     */
    public OssFile upload(MultipartFile file, String bucket, String name);

}
