package com.bmy.admin.cotroller;

import com.bmy.admin.service.AdminLoginService;
import com.bmy.core.api.AuthenticationApi;
import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.core.constant.SmsAuthenticated;
import com.bmy.core.vo.PhonePwdLoginVo;
import com.bmy.core.vo.PhoneSmsLoginVo;
import com.bmy.core.vo.UsernameLoginVo;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @ClassName AdminLoginController
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 上午2:01
 **/
@RestController
@Api(tags = "管理员登录接口")
@RequestMapping("auth")
public class AdminLoginController {

    @Resource
    private AdminLoginService adminLoginService;

    @Resource
    private AuthenticationApi api;

    /**
     * 账号密码登录
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @ApiOperation("账号密码登录")
    @PostMapping("/login")
    public Object login(@RequestBody UsernameLoginVo usernameLoginVo) throws HttpRequestMethodNotSupportedException {
        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", Optional.ofNullable(usernameLoginVo.getUsername()).orElse(""));
            map.add("password", Optional.ofNullable(usernameLoginVo.getPassword()).orElse(""));
            map.add("grant_type", Optional.ofNullable("password").orElse(""));
            map.add("client_id", Optional.ofNullable("bmy_wechat_mp").orElse(""));
            map.add("scope", Optional.ofNullable("bmy").orElse(""));
            map.add("client_secret", Optional.ofNullable("123456").orElse(""));
            return adminLoginService.login(map);
        }catch (FeignException e){
            return new R<>(404,"请求超时");
        }

    }

    @GetMapping("/sms/send")
    @ApiOperation("发送手机短信 认证模式默认：login 可选：activity，register")
    public R send(@RequestParam("phone") String phone,
                  @RequestParam(value = "action", defaultValue = SmsAuthenticated.ACTION.LOGIN) String action){
        return api.send(phone,action);
    }

    @ApiOperation("短信验证码登录")
    @PostMapping("/sms/login")
    public Object smslogin( @Validated @RequestBody PhoneSmsLoginVo phoneSmsLoginVo) throws HttpRequestMethodNotSupportedException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("phone", Optional.ofNullable(phoneSmsLoginVo.getPhone()).orElse(""));
        map.add("code", Optional.ofNullable(phoneSmsLoginVo.getCode()).orElse(""));
        map.add("grant_type", Optional.ofNullable("phone").orElse(""));
        map.add("client_id", Optional.ofNullable("bmy_wechat_mp").orElse(" "));
        map.add("scope", Optional.ofNullable("bmy").orElse(""));
        map.add("client_secret", Optional.ofNullable("123456").orElse(""));

        Object object = null;
        try {
            object =  api.generateToken(map);
        }catch (FeignException.BadRequest e){
            return new R<>(Response.INVALID_PASSWORD_USERNAME,e);
        }
        return  object;
    }

    @ApiOperation("手机密码登录")
    @PostMapping("/phone/login")
    public Object phoneLogin(@Validated @RequestBody PhonePwdLoginVo phonePwdLoginVo) throws HttpRequestMethodNotSupportedException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("phone", Optional.ofNullable(phonePwdLoginVo.getPhone()).orElse(""));
        map.add("password", Optional.ofNullable(phonePwdLoginVo.getPassword()).orElse(""));
        map.add("grant_type", Optional.ofNullable("phone").orElse(""));
        map.add("client_id", Optional.ofNullable("bmy_wechat_mp").orElse(" "));
        map.add("scope", Optional.ofNullable("bmy").orElse(""));
        map.add("client_secret", Optional.ofNullable("123456").orElse(""));

        try {
            return api.generateToken(map);
        }catch (FeignException.BadRequest e){
            return new R<>(Response.INVALID_PASSWORD_USERNAME,e);
        }
    }
}
