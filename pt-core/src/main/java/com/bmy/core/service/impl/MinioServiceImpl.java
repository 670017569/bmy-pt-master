package com.bmy.core.service.impl;

import com.bmy.core.service.MinioService;
import com.bmy.core.vo.OssFile;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.policy.PolicyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.host}")
    private String minioHost;

    public static final Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

    @Override
    public void checkBucket(String bucket) {
        try {
            boolean isExist = minioClient.bucketExists(bucket);
            if(!isExist) {
                minioClient.makeBucket(bucket);
                minioClient.setBucketPolicy(bucket, "", PolicyType.READ_ONLY);
            }
        } catch (InvalidObjectPrefixException | IOException | InternalException | ErrorResponseException | InsufficientDataException | RegionConflictException | InvalidBucketNameException | XmlPullParserException | NoResponseException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("BUCKET CHECK ERROR!" + e.getMessage());
        }
    }

    @Override
    public OssFile upload(MultipartFile file, String bucket, String name) {
        try {
            InputStream ins = file.getInputStream();                                                    // 输入流
            String originalFilename = file.getOriginalFilename();                                       // 源文件名
            assert originalFilename != null;
            String extname = originalFilename.substring(originalFilename.lastIndexOf("."));         // 文件扩展名
            String finalname = String.format("%s%s", name, extname);                                    // 最终文件名
            String finalnameUTF_8 = URLEncoder.encode(finalname, "UTF-8");                         // 中文转码以便机器打开
            String url = String.format("%s/%s/%s", minioHost, bucket, finalnameUTF_8);                  // 组装生成url路径
            Long id = Long.parseLong(name);
            OssFile ossFile = OssFile.builder().id(id).filename(finalname).url(url).bucket(bucket).build();
            minioClient.putObject(bucket, finalname, ins, ins.available(), file.getContentType());
            return ossFile;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException | InvalidArgumentException e) {
            e.printStackTrace();
            logger.error("UPLOAD ERROR!" + e.getMessage());
        }
        return null;
    }
}
