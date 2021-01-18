package com.bmy.admin.cotroller;

import cn.hutool.core.io.FileUtil;
import com.bmy.core.constant.R;
import com.bmy.dao.domain.OssFile;
import com.bmy.dao.domain.Standard;
import com.bmy.dao.mapper.StandardMapper;
import com.bmy.dao.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

@RestController
@Api(tags = "标准管理接口")
public class StandardController {

    @Resource
    private MinioService minioService;

    @Resource
    private StandardMapper standardMapper;

    @ApiOperation("单个标准上传接口")
    @PutMapping("/standard")
    public R<Object> updateOneStandard(@RequestPart MultipartFile file,@RequestParam Integer regionId){
        //根据后缀判断文件格式是否是图片
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        assert suffix != null;
        boolean flag = minioService.isDoc(suffix);
        //开始上传文件请求并返回Standard对象
        if (!flag){
            return R.fail(406,"文件类型错误");
            //判断文件大小是否符合要求
        }else if (file.getSize()>=10485760){
            return R.fail(406,"文件大小超过限制");
        }else {
            minioService.checkBucket("standard");
            OssFile ossFile = minioService.upload(file, "standard");
            Standard standard = Standard.builder()
                    .id(ossFile.getId())
                    .url(ossFile.getUrl())
                    .title(ossFile.getFilename())
                    .regionId(regionId)
                    .build();
            int res = standardMapper.insertSelective(standard);
            if (res == 1){
                return R.success(200,"上传成功", ossFile);
            }else {
                return R.fail(406,"上传失败");
            }
        }
    }

//    @PostMapping(value = "pics",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ApiOperation("上传多个标准文件,最大6mb,响应返回文件路径及id")
//    public R<Object> uploadPics(@RequestParam(value = "files")MultipartFile[] files,@RequestParam Integer[] regions) throws IOException {
//        for (MultipartFile file : files){
//            //根据后缀判断文件格式是否是图片
//            String suffix = FileUtil.getSuffix(file.getOriginalFilename());
//            assert suffix != null;
//            boolean flag = minioService.isDoc(suffix);
//            //开始上传文件请求并返回Pic对象
//            if (!flag){
//                return R.fail(406,"文件类型错误");
//                //判断文件大小是否符合要求
//            }else if (file.getSize()>=10485760){
//                return R.fail(406,"文件大小超过限制");
//            }
//        }
//        minioService.checkBucket("standard");
//        List<OssFile> pics = new ArrayList<>();
//        for (int i = 0;i < files.length;i++){
//            OssFile ossFile = minioService.upload(files[i], "standard");
//            Standard standard = Standard.builder()
//                    .id(ossFile.getId())
//                    .url(ossFile.getUrl())
//                    .title(ossFile.getFilename())
//                    .regionId(regions[i])
//                    .build();
//            int res = standardMapper.insertSelective(standard);
//            pics.add(ossFile);
//        }
//        return R.success(200,"上传成功",pics);
//    }

}
