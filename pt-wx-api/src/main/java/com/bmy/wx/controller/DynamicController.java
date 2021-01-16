package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.service.DynamicService;
import com.bmy.dao.service.UserInfoService;
import com.bmy.dao.dto.DynamicDTO;
import com.bmy.dao.domain.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "动态管理接口")
public class DynamicController {

    @Resource
    private DynamicService dynamicService;

    @Resource
    private UserInfoService userInfoService;
    /***********************************         对动态的查询           **********************************/
    @ApiOperation("分页获取动态所有动态")
    @GetMapping("dynamics")
    public R<Object> getAllDynamics(Integer page, Integer size,HttpServletRequest request){
        PageHelper.startPage(page,size);
        UserInfo userInfo = userInfoService.getUserByToken(request);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(dynamicService.selectAll(userInfo.getId())));
    }

    @ApiOperation(value = "发布动态")
    @PostMapping("dynamic")
    public R<Object> pubDynamic(HttpServletRequest request ,@RequestBody DynamicDTO dynamicDTO){
        //获取操作发布的用户信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //执行动态发布流程并获取返回结果
        Dynamic res = dynamicService.pubDynamic(dynamicDTO,userInfo);
        if (null == res){
            return R.fail(Response.PUBLISH_FAILED);
        }
        return R.success(Response.DYNAMIC_PUBLISH_SUCCESS,res);
    }

    @GetMapping("/dynamic")
    @ApiOperation("获取用户自己的动态列表")
    public R<Object> getDynamic(HttpServletRequest request,Integer page, Integer size){
        PageHelper.startPage(page,size);
        //获取用户自己的详细信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //获取用户自己的动态列表
        List<Dynamic> dynamics = dynamicService.selectByUid(userInfo.getId());
        return R.success(Response.QUERY_SUCCESS,dynamics);
    }

    @ApiOperation("根据动态id删除动态")
    @DeleteMapping("/dynamic/{dynId}")
    public R<Object> delete(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        return R.success(Response.DELETE_SUCCESS,dynamicService.deleteById(dynId,userInfo));
    }
}
