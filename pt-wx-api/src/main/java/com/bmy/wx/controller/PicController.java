package com.bmy.wx.controller;

import cn.hutool.core.io.FileUtil;
import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.service.MinioService;
import com.bmy.dao.domain.OssFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("pic")
    @ApiOperation("上传图片,最大6mb,响应返回图片路径")
    public R<Object> uploadPic(MultipartFile file) throws IOException {
        //根据后缀判断文件格式是否是图片
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        assert suffix != null;
        boolean flag = minioService.isPic(suffix);
        //开始上传文件请求并返回Pic对象
        if (!flag){
            return R.fail(406,"文件类型错误");
            //判断文件大小是否符合要求
        }else if (file.getSize()>=10485760){
            return R.fail(406,"图片大小超过限制");
        }else {
            minioService.checkBucket("pic");
            OssFile ossFile = minioService.upload(file, "pic");
            return R.success(200,"上传成功", ossFile);
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
            boolean flag = minioService.isPic(suffix);
            //开始上传文件请求并返回Pic对象
            if (!flag){
                return R.fail(406,"文件类型错误");
                //判断文件大小是否符合要求
            }else if (file.getSize()>=10485760){
                return R.fail(406,"图片大小超过限制");
            }
        }
        List<OssFile> pics = new ArrayList<>();
        for (MultipartFile file: files){
            OssFile ossFile = minioService.upload(file, "pic");
            pics.add(ossFile);
        }
        return R.success(200,"上传成功",pics);
    }

    /**
     * 删除单个文件
     */
    @ApiOperation("删除单张图片")
    @DeleteMapping("/pic")
    public R<Object> deleteOne(@RequestBody OssFile ossFile) {
        return R.success(Response.DELETE_SUCCESS,minioService.deleteOne(ossFile));
    }

    /**
     * 删除一批文件
     */
    @ApiOperation("删除一组图片")
    @DeleteMapping("delete_list")
    public R<Object> deleteList(@RequestBody List<OssFile> ossFiles) {
        return R.success(Response.DELETE_SUCCESS,minioService.deleteList(ossFiles));
    }

}
