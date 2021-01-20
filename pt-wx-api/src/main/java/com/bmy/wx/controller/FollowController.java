package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.BadRequestException;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.service.FollowService;
import com.bmy.dao.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "关注接口")
@RestController
public class FollowController {

    @Resource
    private FollowService followService;

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("关注用户")
    @GetMapping("follow")
    public R<Object> follow(Long fuid, HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        try {
            boolean res = followService.follow(userInfo.getId(),fuid);
            if (res){
                UserInfo info = userInfoService.selectByUid(fuid);
                info.setFans(info.getFans()+1);
                userInfoService.updateUserInfo(info);
                userInfo.setFollows(userInfo.getFollows()+1);
                userInfoService.updateUserInfo(userInfo);
                return R.success(Response.FOLLOW_SUCCESS);
            }else {
                return R.fail(Response.FOLLOW_FAILED);
            }
        }catch (BadRequestException e){
            return e.getResp();
        }
    }

    @ApiOperation("取消对某用户的关注")
    @DeleteMapping("follow")
    public R<Object> delFollow(Long fuid ,HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        try {
            boolean res = followService.delFollow(userInfo.getId(),fuid);
            if (res){
                UserInfo info = userInfoService.selectByUid(fuid);
                info.setFans(info.getFans()-1);
                userInfoService.updateUserInfo(info);

                userInfo.setFollows(userInfo.getFollows()-1);
                userInfoService.updateUserInfo(userInfo);
                return R.success(Response.DELETE_FOLLOW_SUCCESS);
            }else {
                return R.fail(Response.DELETE_FOLLOW_FAILED);
            }
        }catch (BadRequestException e){
            return e.getResp();
        }
    }

    @ApiOperation("获取自己关注的人的列表")
    @GetMapping("follows")
    public R<Object> getAllFollows(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size,HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        PageHelper.startPage(page,size);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(followService.selectFollows(userInfo.getId())));
    }



    @ApiOperation("获取关注自己的粉丝的列表")
    @GetMapping("fans")
    public R<Object> getAllFans(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size , HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        PageHelper.startPage(page,size);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(followService.selectFans(userInfo.getId())));
    }

}
