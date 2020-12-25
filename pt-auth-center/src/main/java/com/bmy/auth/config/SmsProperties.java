package com.bmy.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName SmsProperties
 * @Description 手机短信验证码应用配置类
 * @Author potato
 * @Date 2020/12/17 下午6:24
 **/
@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    /**
     * 短信验证码appid
     */
    private String appId;

    /**
     * 短信验证码appsecret
     */
    private String appSecret;

}
