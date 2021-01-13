package com.bmy.wx.controller;

import com.bmy.core.api.AuthenticationApi;
import com.bmy.core.vo.WxAuthInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object wxAuth(@RequestBody WxAuthInfo wxAuthInfo) throws HttpRequestMethodNotSupportedException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("loginCode", Optional.ofNullable(wxAuthInfo.getLoginCode()).orElse(""));
        map.add("signature", Optional.ofNullable(wxAuthInfo.getSignature()).orElse(""));
        map.add("userInfo", Optional.ofNullable(wxAuthInfo.getUserInfo()).orElse(""));
        map.add("grant_type", Optional.ofNullable("wx").orElse(""));
        map.add("client_id", Optional.ofNullable("bmy_wechat_mp").orElse(""));
        map.add("scope", Optional.ofNullable("bmy").orElse(""));
        map.add("client_secret", Optional.ofNullable("123456").orElse(""));
        return api.generateToken(map);
    }

}
