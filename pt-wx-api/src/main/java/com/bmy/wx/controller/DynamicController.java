package com.bmy.wx.controller;


import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.wx.vo.DynamicInVo;
import com.bmy.dao.domain.UserInfo;
import com.bmy.wx.service.DynamicService;
import com.bmy.wx.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "动态发布管理接口")
public class DynamicController {

    Logger logger = LoggerFactory.getLogger(DynamicController.class);

    @Resource
    private DynamicService dynamicService;

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("根据uid分页获取动态自己的动态（动态管理）")
    @GetMapping("/user/dynamics")
    public R<Object> getDynamicsByUid(Integer page, Integer size, Long uid){
        PageHelper.startPage(page,size);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(dynamicService.selectAllByUid(uid)));
    }

    @ApiOperation("分页获取动态所有动态")
    @GetMapping("dynamics")
    public R<Object> getAllDynamics(Integer page, Integer size){
        PageHelper.startPage(page,size);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(dynamicService.selectAll()));
    }

    @ApiOperation(value = "发布动态")
    @PostMapping("dynamic")
    public R<Object> pubDynamic(HttpServletRequest request ,@RequestBody DynamicInVo dynamicVo){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        Boolean res = dynamicService.pubDynamic(dynamicVo,userInfo);
        logger.info("动态发布操作结果：{}",res);
        return R.success(Response.REFRESH_SUCCESS);
    }




}
