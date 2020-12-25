package com.bmy.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @ClassName WxOfficialProperties
 * @Description TODO
 * @Author potato
 * @Date 2020/12/24 下午2:32
 **/
@Data
@Component
@ConfigurationProperties(prefix = "wx.official")
public class WxOfficialProperties {

    /**
     * 公众号appid
     */
    private String appId;

    /**
     * 公众号appsecret
     */
    private String appSecret;

    /**
     * 公众号api
     */
    private HashMap<String,String> api;

}
