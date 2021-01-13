package com.bmy.wx.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import com.bmy.core.api.FileCenterApi;
import com.bmy.core.constant.R;
import com.bmy.core.service.MinioService;
import com.bmy.core.vo.OssFile;
import com.bmy.dao.domain.ex.DynamicPic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "图片上传接口")
public class PicController {

    @Resource
    private MinioService minioService;

    @Resource
    private Snowflake snowflake;

    private Logger logger = LoggerFactory.getLogger(PicController.class);

    @PostMapping("pic")
    @ApiOperation("上传图片,最大6mb,响应返回图片路径")
    public R<Object> uploadPic(MultipartFile file) throws IOException {
        //根据后缀判断文件格式是否是图片
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        assert suffix != null;
        boolean flag = isPic(suffix);
        //开始上传文件请求并返回Pic对象
        if (!flag){
            return R.fail(406,"文件类型错误");
            //判断文件大小是否符合要求
        }else if (file.getSize()>=10485760){
            return R.fail(406,"图片大小超过限制");
        }else {
            minioService.checkBucket("pic");
            long name = snowflake.nextId();
            OssFile ossFile = minioService.upload(file, "pic", Long.toString(name));
            return R.success(200,"上传成功", DynamicPic.builder().id(ossFile.getId()).url(ossFile.getUrl()).build());
        }
    }

    /**
     * 上传一组照片
     * @param files
     * @return
     * @throws IOException
     */
    @PostMapping(value = "pics",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("上传一组图片,最大6mb,响应返回图片路径及id")
    public R<Object> uploadPics(@RequestParam(value = "files")MultipartFile[] files) throws IOException {
        for (MultipartFile file : files){
            //根据后缀判断文件格式是否是图片
            String suffix = FileUtil.getSuffix(file.getOriginalFilename());
            assert suffix != null;
            boolean flag = isPic(suffix);
            //开始上传文件请求并返回Pic对象
            if (!flag){
                return R.fail(406,"文件类型错误");
                //判断文件大小是否符合要求
            }else if (file.getSize()>=10485760){
                return R.fail(406,"图片大小超过限制");
            }
        }
        List<DynamicPic> pics = new ArrayList<>();
        long name = snowflake.nextId();
        for (MultipartFile file: files){
            OssFile ossFile = minioService.upload(file, "pic", Long.toString(name));
            DynamicPic pic = DynamicPic.builder().id(ossFile.getId()).url(ossFile.getUrl()).build();
            pics.add(pic);
        }
        return R.success(200,"上传成功",pics);
    }

    //    /**
//     * 删除单个文件
//     */
//    @DeleteMapping("delete_one")
//    public String deleteOne(@RequestBody OssFile ossFile) {
//        try {
//            minioClient.removeObject(ossFile.getBucket(), ossFile.getFilename());
//            return "OK";
//        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
//            logger.error("DELETE ERROR!" + e.getMessage());
//        }
//        return "FAIL";
//    }

//    /**
//     * 删除一批文件
//     */
//    @DeleteMapping("delete_list")
//    public String deleteList(@RequestBody List<OssFile> ossFiles) {
//        try {
//            for (OssFile ossFile : ossFiles){
//                minioClient.removeObject(ossFile.getBucket(), ossFile.getFilename());
//            }
//            return "OK";
//        } catch (InvalidKeyException | NoSuchAlgorithmException | NoResponseException | XmlPullParserException | InvalidBucketNameException | InsufficientDataException | ErrorResponseException | InternalException | IOException e) {
//            logger.error("DELETE ERROR!" + e.getMessage());
//        }
//        return "FAIL";
//    }
    /**
     * 根据文件后缀判断文件类型是否符合要求
     * @param suffix
     * @return
     */
    private boolean isPic(String suffix){
        return suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png");
    }


}
