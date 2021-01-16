package com.bmy.dao.service;

import com.bmy.dao.domain.OssFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * @return
     */
    public OssFile upload(MultipartFile file, String bucket);

    public boolean deleteOne(OssFile ossFile);

    public boolean deleteList(List<OssFile> ossFiles);

    boolean isPic(String suffix);
}
