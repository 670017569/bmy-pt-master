package com.bmy.core.api;


import com.bmy.core.constant.R;
import com.bmy.core.constant.SmsAuthenticated;
import com.bmy.core.exception.BadRequestException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient("pt-auth-center")
public interface AuthenticationApi {
    /**
     * 认证登录接口
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token", headers = {"Content-Type: multipart/form-data , “Authorization: "})
    public R<Object> generateToken(@RequestBody MultiValueMap<String, String> map) throws BadRequestException;

    /**
     * 发送短信,action字段表示验证码的功能,根据功能的不同会分开存储,目前有login register reset activity,默认值是login
     * @param phone
     * @param action
     * @return
     */
    @RequestMapping(value = "/sms/send/code", method = RequestMethod.GET)
    public R<Object> send(@RequestParam("phone") String phone, @RequestParam("action") String action);

    @PostMapping("/sms/check")
    public R<Object> check(@RequestParam("code") String code,
                            @RequestParam("phone") String phone,
                            @RequestParam(value = "action", defaultValue = SmsAuthenticated.ACTION.LOGIN) String action);

    @GetMapping("oauth/check_token")
    public R<Object> checkToken(@RequestParam("token") String value);

}
