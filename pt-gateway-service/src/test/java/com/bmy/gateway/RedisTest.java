package com.bmy.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName RedisTest
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午3:55
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

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
        OAuth2Authentication  o =  redisTokenStore.readAuthentication("e838256a-55a9-432f-b7b1-42296e0a4dc4");
        System.out.println(o);
    }

}
