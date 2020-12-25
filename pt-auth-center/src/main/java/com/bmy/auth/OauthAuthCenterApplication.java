package com.bmy.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName OauthAuthCenterApplication
 * @Description TODO
 * @Author potato
 * @Date 2020/12/22 下午11:59
 **/
@SpringBootApplication
@MapperScan("com.bmy.dao.mapper")
@ComponentScan("com.bmy")
@EnableDiscoveryClient
public class OauthAuthCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthAuthCenterApplication.class,args);
    }

}
