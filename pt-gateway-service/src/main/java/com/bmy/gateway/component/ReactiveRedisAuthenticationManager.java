package com.bmy.gateway.component;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import reactor.core.publisher.Mono;


/**
 * @ClassName ReactiveRedisAuthenticationManager
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午3:15
 **/
public class ReactiveRedisAuthenticationManager implements ReactiveAuthenticationManager {

    Logger logger = LoggerFactory.getLogger(ReactiveRedisAuthenticationManager.class);

    public ReactiveRedisAuthenticationManager(RedisTokenStore tokenStore){
        this.tokenStore = tokenStore;
    }

    @Getter
    @Setter
    private RedisTokenStore tokenStore;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessToken ->{
                    logger.info("accessToken is :{}",accessToken);
                    OAuth2AccessToken oAuth2AccessToken = this.tokenStore.readAccessToken(accessToken);
                    //根据access_token从redis获取不到OAuth2AccessToken

                    if(oAuth2AccessToken == null){
                        return Mono.error(new InvalidTokenException("invalid access token,please check"));
                    }else if(oAuth2AccessToken.isExpired()){
                        return Mono.error(new InvalidTokenException("access token has expired,please reacquire token"));
                    }

                    logger.info("token存在:{}",oAuth2AccessToken);

                    OAuth2Authentication oAuth2Authentication =this.tokenStore.readAuthentication(accessToken);

                    if(oAuth2Authentication == null){
                        return Mono.error(new InvalidTokenException("Access Token 无效!"));
                    }else {
                        return Mono.just(oAuth2Authentication);
                    }
                })).cast(Authentication.class);
    }
}
