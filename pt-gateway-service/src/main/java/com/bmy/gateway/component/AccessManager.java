package com.bmy.gateway.component;

import cn.hutool.core.collection.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @ClassName AccessManager
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午3:39
 **/
@Component
public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    Logger logger = LoggerFactory.getLogger(AccessManager.class);

    //线程安全集合设置静态资源路径白名单
    private final Set<String> permitAll = new ConcurrentHashSet<>();

    //路径匹配器
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();


    public AccessManager (){
        permitAll.add("/");
        permitAll.add("/error");
        permitAll.add("/favicon.ico");
        permitAll.add("/**/v2/api-docs/**");
        permitAll.add("/**/swagger-resources/**");
        permitAll.add("/webjars/**");
        permitAll.add("/swagger-ui.html");
        permitAll.add("/**/auth/**");
        permitAll.add("/wx/news");
    }

    /**
     * 实现权限验证判断
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {
        ServerWebExchange exchange = authorizationContext.getExchange();
        //请求路径
        String requestPath = exchange.getRequest().getURI().getPath();
        // 白名单静态资源请求以及认证请求直接放行
        if (permitAll(requestPath)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        return authenticationMono.map(auth -> new AuthorizationDecision(checkAuthorities(exchange, auth, requestPath))).defaultIfEmpty(new AuthorizationDecision(false));
    }


    /**
     * 根据认证信息与角色权限信息进行对比
     * 若认证信息中的授权角色在缓存或数据库中的权限信息包含了该请求路径则放行
     * 否则拦截该请求
     * 后续需要将client_id和client_secret存储在数据库中
     * 形成多租户形式
     * @param exchange
     * @param auth
     * @param requestPath
     * @return
     */
    private boolean checkAuthorities(ServerWebExchange exchange, Authentication auth, String requestPath) {
        if(auth instanceof OAuth2Authentication){
            OAuth2Authentication athentication = (OAuth2Authentication) auth;
            //验证客户端id及客户端secret
            String clientId = athentication.getOAuth2Request().getClientId();
            logger.info("requestPath is:{}",requestPath);
            logger.info("clientId is {}",clientId);
        }
        Object principal = auth.getPrincipal();
        return true;
    }

    /**
     * 校验是否属于静态资源
     * @param requestPath 请求路径
     * @return
     */
    private boolean permitAll(String requestPath) {
        return permitAll.stream().anyMatch(r -> antPathMatcher.match(r, requestPath));
    }

}
