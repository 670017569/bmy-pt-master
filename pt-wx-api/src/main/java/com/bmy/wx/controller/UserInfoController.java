package com.bmy.wx.controller;

import com.bmy.core.api.AuthenticationApi;
import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.wx.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName UserInfoController
 * @Description TODO
 * @Author potato
 * @Date 2020/12/25 下午5:59
 **/
@RestController
@Api(tags = "用户详细信息接口")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private AuthenticationApi api;

    @GetMapping("userinfo")
    public R<Object> getUserInfo(Long uid){
        return new R<>(Response.QUERY_SUCCESS,userInfoService.getUserInfo(uid));
    }

    @GetMapping("/check_token")
    public R<Object> checkToken(@RequestParam("token") String token){
        return api.checkToken(token);
    }

}
