package com.bmy.core.api;

import com.bmy.core.vo.OssFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@FeignClient(name = "pt-file-center")
public interface FileCenterApi {

    @RequestMapping(value = "/upload_one", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OssFile uploadOne(@RequestPart MultipartFile file, @RequestParam("bucket") String bucket, @RequestParam("name") String name);

    @RequestMapping(value = "/upload_list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<OssFile> uploadList(@RequestPart List<MultipartFile> files, @RequestParam("bucket") String bucket, @RequestParam("name") String name);

    @RequestMapping("/delete_one")
    public String deleteOne(@RequestBody OssFile ossFile);

    @RequestMapping("/delete_list")
    public String deleteList(@RequestBody List<OssFile> ossFiles);

}
