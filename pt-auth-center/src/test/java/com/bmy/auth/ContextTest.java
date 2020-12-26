package com.bmy.auth;

import cn.hutool.core.lang.Snowflake;
import com.bmy.auth.service.SmsService;
import com.bmy.dao.mapper.ex.RolePermissionMapper;
import com.bmy.dao.mapper.ex.UserRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @ClassName ContextTest
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 上午12:22
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ContextTest {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private RedisTokenStore redisTokenStore;


    @Bean
    public RedisTokenStore redisTokenStore(){
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("bmy-auth-token:");
        return redisTokenStore;
    }

    @Test
    public void test(){
        Object o =  redisTokenStore.readAuthentication("93465817-0bb6-47f3-8385-93a07fb6b7a5");
        System.out.println(o);
    }

}
