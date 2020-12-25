package com.bmy.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName WxApplication
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 下午1:27
 **/
@SpringBootApplication
@MapperScan("com.bmy.dao.mapper")
@ComponentScan("com.bmy")
@EnableDiscoveryClient
public class WxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class,args);
    }

}
