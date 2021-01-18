package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "地区信息接口")
@RestController
public class RegionController {

    @Resource
    private RegionService regionService;

    @ApiOperation("获取所有省级地区信息")
    @GetMapping("provinces")
    public R<Object> provinces(){
        return R.success(Response.QUERY_SUCCESS, regionService.selectAllProvince());
    }

}
