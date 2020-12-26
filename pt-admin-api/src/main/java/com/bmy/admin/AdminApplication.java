package com.bmy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName AdminApplication
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 下午11:41
 **/

@SpringBootApplication
@MapperScan("com.bmy.dao.mapper")
@ComponentScan("com.bmy")
@EnableDiscoveryClient
@EnableCaching
@EnableFeignClients("com.bmy.core.api")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }

}
