package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.service.DynamicPraiseService;
import com.bmy.dao.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "动态点赞接口")
public class DynamicPraiseController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private DynamicPraiseService dynamicPraiseService;

    /***********************************         对动态的点赞           **********************************/

    @ApiOperation("对动态点赞")
    @GetMapping("/dynamic/praise/{dynId}")
    public R<Object> praise(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        //根据令牌查询操接口请求者身份信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //然后将请求者uid与dynId插入动态点赞表中
        boolean res = dynamicPraiseService.praise(userInfo,dynId);
        //根据res返回操作结果
        if (res){
            return R.success(Response.PRAISE_SUCCESS);
        }else {
            return R.fail(Response.PRAISE_FAILED);
        }
    }

    @ApiOperation("取消对动态的点赞")
    @DeleteMapping("/dynamic/praise/{dynId}")
    public R<Object> delPraise(@PathVariable("dynId") Long dynId, HttpServletRequest request){
        //根据令牌查询操接口请求者身份信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //然后将根据请求者uid与dynId进行删除
        boolean res = dynamicPraiseService.delPraise(userInfo,dynId);
        if (res){
            return R.success(Response.PRAISE_CANCEL_SUCCESS);
        }
        return R.success(Response.PRAISE_CANCEL_FAILED);
    }

//    @ApiOperation("获取对某动态点赞的所有人信息")
//    @GetMapping("users")
//    public R<Object> getPraiseUser(Long dynId){
//        return R.success(Response.QUERY_SUCCESS,dynamicPraiseService.selectUsersByDynId(dynId));
//    }



}
