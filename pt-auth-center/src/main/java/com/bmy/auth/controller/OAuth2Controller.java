package com.bmy.auth.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * OAuth2认证
 *
 * @author 向振华
 * @date 2020/11/13 16:11
 */
@RestController
@RequestMapping("/oauth")
public class OAuth2Controller {
    Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    private final WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();
    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    private CheckTokenEndpoint checkTokenEndpoint;

    @GetMapping("/token")
    public R<Object> getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        Object o = null;
        try {
            o = tokenEndpoint.getAccessToken(principal, parameters).getBody();
        }catch (BadCredentialsException | InvalidGrantException | InvalidClientException | UnAuthorizedException  e ){
            return new R<>(Response.INVALID_PASSWORD_USERNAME,e);
        }
        return new R<>(Response.LOGIN_SUCCESS,o);
    }
    @PostMapping("/token")
    public R<Object> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        Object o = null;
        try {
            o = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        }catch (BadCredentialsException | InvalidGrantException | UnsupportedGrantTypeException | InvalidScopeException | InvalidClientException | UnAuthorizedException | CredentialsExpiredException | UsernameNotFoundException e ){

            if (e instanceof UnsupportedGrantTypeException){
                return new R<>(Response.INVALID_GRANT_TYPE);
            }else if (e instanceof InvalidClientException){
                return new R<>(Response.ILLEGAL_CLIENT);
            }else if (e instanceof InvalidScopeException){
                return new R<>(Response.ILLEGAL_SCOPE);
            }
            return new R<>(Response.INVALID_PASSWORD_USERNAME);
        }
        return new R<>(Response.LOGIN_SUCCESS,o);
    }

    @GetMapping("/check_token")
    public R<Object> checkToken(@RequestParam("token") String value) {
        return new  R<>(Response.CHECK_SUCCESS,checkTokenEndpoint.checkToken(value));
    }
    //这里是异常翻译，如果这里不设置，则需要在全局异常处处理
    @ExceptionHandler({Exception.class})
    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
        return this.exceptionTranslator.translate(e);
    }

}