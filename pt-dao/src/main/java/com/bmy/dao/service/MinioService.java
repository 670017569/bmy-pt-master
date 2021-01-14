package com.bmy.dao.service;

import com.bmy.dao.dto.OssFileDTO;
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
    public OssFileDTO upload(MultipartFile file, String bucket);

    public boolean deleteOne(OssFileDTO ossFileDTO);

    public boolean deleteList(List<OssFileDTO> ossFileDTOS);

    boolean isPic(String suffix);
}
