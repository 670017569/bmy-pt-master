package com.bmy.wx.controller;

import cn.hutool.core.lang.Snowflake;
import com.bmy.core.api.FileCenterApi;
import com.bmy.core.vo.OssFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "图片上传接口")
public class PicController {

    @Resource
    private FileCenterApi fileCenterApi;

    @Resource
    private Snowflake snowflake;

    @PostMapping("upload")
    @ApiOperation("上传图片,最大6mb,响应返回图片路径")
    public Map<String, String> upload(MultipartFile file) throws IOException {
//        FileInputStream in = (FileInputStream) file.getInputStream();
        long id = snowflake.nextId();
        OssFile ossFile = fileCenterApi.uploadOne(file, "pic", Long.toString(id));
        Map<String, String> map = new HashMap<>();
        map.put("id",Long.toString(id));
        map.put("url", ossFile.getUrl());
        return map;
    }

}
