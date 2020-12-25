package com.bmy.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @ClassName WxProperties
 * @Description Wx小程序配置类
 * @Author potato
 * @Date 2020/12/15 下午5:47
 **/
@Data
@Component
@ConfigurationProperties(prefix = "wx.mp")
public class WxProperties {

    /**
     * 小程序appid
     */
    private String appId;

    /**
     * 小程序appsecret
     */
    private String appSecret;

    /**
     * 微信认证api（授权码模式）
     */
    private String api;


}
