package com.bmy.wx.controller;

import com.bmy.core.api.AuthenticationApi;
import com.bmy.dao.dto.WxAuthInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * @ClassName WxLoginController
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午11:52
 **/
@RestController
@Api(tags = "微信认证接口")
@RequestMapping("auth")
public class WxLoginController {

    Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    @Resource
    private AuthenticationApi api;

    @ApiOperation(value = "微信登录",notes = "对于参数userInfo需要使用JSON.stringfy()对其json字符串内部的双引号进行转义处理，否则后端无法识别")
    @PostMapping("/oauth/token")
    public Object wxAuth(@RequestBody WxAuthInfoDTO wxAuthInfoDTO) throws HttpRequestMethodNotSupportedException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("loginCode", Optional.ofNullable(wxAuthInfoDTO.getLoginCode()).orElse(""));
        map.add("signature", Optional.ofNullable(wxAuthInfoDTO.getSignature()).orElse(""));
        map.add("userInfo", Optional.ofNullable(wxAuthInfoDTO.getUserInfo()).orElse(""));
        map.add("grant_type", Optional.of("wx").orElse(""));
        map.add("client_id", Optional.of("bmy_wechat_mp").orElse(""));
        map.add("scope", Optional.of("bmy").orElse(""));
        map.add("client_secret", Optional.of("123456").orElse(""));
        return api.generateToken(map);
    }

    @ApiOperation(value = "刷新token",notes = "当本地token访问服务返回invalid access_token时，传refresh_token的值")
    @GetMapping("/oauth/token")
    public Object wxAuthRefresh(String refresh_token) throws HttpRequestMethodNotSupportedException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", Optional.of("refresh_token").orElse(""));
        map.add("refresh_token", Optional.of(refresh_token).orElse(""));
        map.add("client_id", Optional.of("bmy_wechat_mp").orElse(""));
        map.add("scope", Optional.of("bmy").orElse(""));
        map.add("client_secret", Optional.of("123456").orElse(""));
        return api.generateToken(map);
    }

}
