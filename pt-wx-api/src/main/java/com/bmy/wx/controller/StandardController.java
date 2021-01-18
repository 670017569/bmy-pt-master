package com.bmy.wx.controller;


import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.domain.Standard;
import com.bmy.dao.service.StandardService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "标准接口")
public class StandardController {

    @Resource
    private StandardService standardService;

    @GetMapping("/standards")
    public R<Object> getStandards(Integer regionId){

        List<Standard> standards = standardService.selectByRegionId(regionId);
        if (null != standards){
            return R.success(Response.QUERY_SUCCESS,standards);
        }else {
            return R.fail(Response.NONE_DATA);
        }
    }

}
