package com.bmy.wx.controller;


import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.service.DynamicPraiseService;
import com.bmy.dao.service.DynamicService;
import com.bmy.dao.service.UserInfoService;
import com.bmy.dao.vo.DynamicInVo;
import com.bmy.dao.domain.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "动态管理接口")
public class DynamicController {

    Logger logger = LoggerFactory.getLogger(DynamicController.class);

    @Resource
    private DynamicService dynamicService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private DynamicPraiseService dynamicPraiseService;

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
        Boolean res = dynamicService.pubDynamic(dynamicVo,userInfo.getId());
        logger.info("动态发布操作结果：{}",res);
        return R.success(Response.REFRESH_SUCCESS);
    }

    @GetMapping("/dynamic")
    @ApiOperation("获取用户自己的动态列表")
    public R<Object> getDynamic(HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        return R.success(Response.QUERY_SUCCESS,dynamicService.selectByUid(userInfo.getId()));
    }

    @DeleteMapping("/dynamic/{dynId}")
    public R<Object> delete(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        //根据token令牌获取用户详细信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //根据dynId查询该动态，并判断该动态的uid是否与userInfo中的uid相同，若不同则返回非法参数
        //若相同则继续执行删除
        return R.success(Response.DELETE_SUCCESS,dynamicService.deleteById(dynId));
    }

    @GetMapping("/dynamic/praise/{dynId}")
    public R<Object> praise(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        //根据令牌查询操接口请求者身份信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //然后将请求者uid与dynId插入动态点赞表中
        boolean res = dynamicPraiseService.praise(userInfo.getId(),dynId);
        //根据res返回操作结果
        return R.success(Response.PRAISE_SUCCESS);
    }
    @DeleteMapping("/dynamic/praise/{dynId}")
    public R<Object> delPraise(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        //根据令牌查询操接口请求者身份信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //然后将根据请求者uid与dynId进行删除
        boolean res = dynamicPraiseService.delPraise(userInfo.getId(),dynId);
        return R.success(Response.REFRESH_SUCCESS);
    }

}
