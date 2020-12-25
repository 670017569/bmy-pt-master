package com.bmy.oss.controller;

import com.bmy.oss.model.OssFile;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.policy.PolicyType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by f9miao
 */
@Api(tags = "文件系统接口(测试使用)")
@RestController
public class MinioController {

    public static final Logger logger = LoggerFactory.getLogger(MinioController.class);

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.host}")
    private String minioHost;


    /**
     * 上传单个文件
     * Content-Type为form-data
     * @param file
     * @param bucket 存储桶名称, 不存在则创建
     * @param name 文件名, 不需要给出后缀名
     */
    @ApiOperation("上传单个文件")
    @PostMapping("upload_one")
    public OssFile uploadOne(MultipartFile file, String bucket, String name) {
        checkBucket(bucket);
        return upload(file, bucket, name);
    }

    /**
     * 上传一批文件
     * Content-Type为form-data
     * @param files
     * @param bucket 存储桶名称, 不存在则创建
     * @param name 文件名, 不需要给出后缀名
     * @return
     */
    @PostMapping("upload_list")
    public List<OssFile> uploadList(List<MultipartFile> files, String bucket, String name) {
        int count = 0;
        List<OssFile> list = new ArrayList<>();
        checkBucket(bucket);
        for (MultipartFile file: files){
            list.add(upload(file, bucket, String.format("%s_p%d", name, count)));
            count++;
        }
        return list;
    }

    /**
     * 删除单个文件
     */
    @DeleteMapping("delete_one")
    public String deleteOne(@RequestBody OssFile ossFile) {
        try {
            minioClient.removeObject(ossFile.getBucket(), ossFile.getFilename());
            return "OK";
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
            logger.error("DELETE ERROR!" + e.getMessage());
        }
        return "FAIL";
    }

    /**
     * 删除一批文件
     */
    @DeleteMapping("delete_list")
    public String deleteList(@RequestBody List<OssFile> ossFiles) {
        try {
            for (OssFile ossFile : ossFiles){
                minioClient.removeObject(ossFile.getBucket(), ossFile.getFilename());
            }
            return "OK";
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
            logger.error("DELETE ERROR!" + e.getMessage());
        }
        return "FAIL";
    }

    /**
     * 检查存储桶是否存在, 不存在则创建
     */
    protected void checkBucket(String bucket) {
        try {
            boolean isExist = minioClient.bucketExists(bucket);
            if(!isExist) {
                logger.info(String.format("bucket %s is not exist before, create now!", bucket));
                minioClient.makeBucket(bucket);
                minioClient.setBucketPolicy(bucket, "", PolicyType.READ_ONLY);
            }
        } catch (InvalidObjectPrefixException | IOException | InternalException | ErrorResponseException | InsufficientDataException | RegionConflictException | InvalidBucketNameException | XmlPullParserException | NoResponseException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("BUCKET CHECK ERROR!" + e.getMessage());
        }
    }

    /**
     * 上传文件
     */
    protected OssFile upload(MultipartFile file, String bucket, String name) {
        try {
            InputStream ins = file.getInputStream();                                                    // 输入流
            String originalFilename = file.getOriginalFilename();                                       // 源文件名
            String extname = originalFilename.substring(originalFilename.lastIndexOf("."));         // 文件扩展名
            String finalname = String.format("%s%s", name, extname);                                    // 最终文件名
            String finalnameUTF_8 = URLEncoder.encode(finalname, "UTF-8");                         // 中文转码以便机器打开
            String url = String.format("%s/%s/%s", minioHost, bucket, finalnameUTF_8);                  // 组装生成url路径
            OssFile ossFile = new OssFile();
            ossFile.setBucket(bucket);
            ossFile.setFilename(finalname);
            ossFile.setUrl(url);
            minioClient.putObject(bucket, finalname, ins, ins.available(), file.getContentType());
            return ossFile;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException | InvalidArgumentException e) {
            e.printStackTrace();
            logger.error("UPLOAD ERROR!" + e.getMessage());
        }
        return null;
    }

}
