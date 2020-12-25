package com.bmy.auth.filter;

import com.bmy.auth.entity.PhoneAuthenticationToken;
import com.bmy.core.util.HttpRequestUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class PhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Gson gson = new Gson();

    Logger logger = LoggerFactory.getLogger(PhoneAuthenticationFilter.class);

    public PhoneAuthenticationFilter() {
        super(new AntPathRequestMatcher("/oauth/phone", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        String requestBodyString = HttpRequestUtil.readBodyToString(request);
        logger.info("过滤器接收处理请求体 ==> 开始:{}",requestBodyString);

        HashMap map = gson.fromJson(requestBodyString, HashMap.class);
        logger.info("gson转换请求体:{}",map);

        PhoneAuthenticationToken authenticationToken = new PhoneAuthenticationToken(map, null);
        logger.info("创建未通过验证的token:{}",authenticationToken);

        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        logger.info("调用AuthenticationManager进行验证:{}",authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

}
