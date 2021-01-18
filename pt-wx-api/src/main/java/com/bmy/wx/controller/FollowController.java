package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.BadRequestException;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.service.FollowService;
import com.bmy.dao.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
}
