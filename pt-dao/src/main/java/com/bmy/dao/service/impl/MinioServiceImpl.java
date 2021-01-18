package com.bmy.dao.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import com.bmy.dao.domain.OssFile;
import com.bmy.dao.service.MinioService;
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
import java.util.List;

@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;

    @Resource
    private Snowflake snowflake;

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
    public OssFile upload(MultipartFile file, String bucket) {
        try {
            Long id = snowflake.nextId();
            //生成id为文件名
            String originalFilename = file.getOriginalFilename();
            // 源文件名
            String name = FileUtil.getPrefix(originalFilename);
            InputStream ins = file.getInputStream();
            // 输入流
            //获取文件名称
            assert originalFilename != null;
            String extname = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 文件扩展名
            String finalname = String.format("%s%s", name, extname);
            // 最终文件名
            String finalnameUTF_8 = URLEncoder.encode(finalname, "UTF-8");
            // 中文转码以便机器打开
            // 组装生成url路径
            String url = String.format("%s/%s/%s", minioHost, bucket, finalnameUTF_8);
            OssFile ossFile = OssFile.builder().id(id).filename(finalname).url(url).bucket(bucket).build();
            //上传到minio服务器
            minioClient.putObject(bucket, finalname, ins, ins.available(), file.getContentType());
            return ossFile;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException | InvalidArgumentException e) {
            e.printStackTrace();
            logger.error("UPLOAD ERROR!" + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteOne(OssFile ossFile) {
        try {
            minioClient.removeObject(ossFile.getBucket(), ossFile.getFilename());
            return true;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
            logger.error("DELETE ERROR!" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteList(List<OssFile> ossFiles) {
        try {
            for (OssFile ossFile : ossFiles){
                minioClient.removeObject(ossFile.getBucket(), ossFile.getFilename());
            }
            return true;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
            logger.error("DELETE ERROR!" + e.getMessage());
        }
        return false;
    }

    /**
     * 根据文件后缀判断文件类型是否符合要求
     * @param suffix
     * @return
     */
    public boolean isPic(String suffix){
        return suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png");
    }

    @Override
    public boolean isDoc(String suffix) {
        return suffix.equals("doc") || suffix.equals("docx") || suffix.equals("pdf");
    }

}
