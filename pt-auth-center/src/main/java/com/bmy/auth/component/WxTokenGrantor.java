package com.bmy.auth.component;

import com.bmy.auth.common.vo.*;
import com.bmy.auth.service.PhoneUserDetailService;
import com.bmy.auth.service.WxOpenIdUserDetailsService;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.UnAuthorizedException;
import com.bmy.core.util.HttpRequestUtil;
import com.bmy.dao.domain.WxUserInfo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName WxTokenGrantor
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午11:12
 **/
public class WxTokenGrantor extends AbstractTokenGranter {

    Logger logger = LoggerFactory.getLogger(WxTokenGrantor.class);

    private final Gson gson = new Gson();

    private static final String GRANT_TYPE = "wx";

    private final WxOpenIdUserDetailsService wxOpenIdUserDetailsService;

    public WxTokenGrantor(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, WxOpenIdUserDetailsService wxOpenIdUserDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.wxOpenIdUserDetailsService = wxOpenIdUserDetailsService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        WxUserSession session;
        WxUserInfo wxUserInfo;
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        //获取请求参数
        String loginCode = parameters.get("loginCode");
        String signature = parameters.get("signature");
        String userInfo = parameters.get("userInfo");
        try {
            //打开微信会话
            logger.info("开始微信会话");
            session = wxOpenIdUserDetailsService.openSession(loginCode);
            logger.info("微信会话打开成功:{}",session);
        }catch (RuntimeException | IOException e){
            logger.info("微信会话打开失败:{}",e.toString());
            throw new UnAuthorizedException(Response.WX_SESSION_OPEN_FAIL);
        }
        try {
            logger.info("用户信息完整性验证 ==> 开始");
            boolean isValidate = wxOpenIdUserDetailsService.userInfoValidate(userInfo, session.getSessionKey(),signature);
//            if (!isValidate) {
//                logger.info("用户信息完整性验证错误");
//                throw new UnAuthorizedException(Response.WX_SESSION_INVALID);
//            }
            logger.info("用户信息完整性验证 <== 完成");
        }catch (Exception e){
            logger.info("用户信息完整性错误:{}",e.toString());
            throw new UnAuthorizedException(Response.WX_SESSION_INVALID);
        }

        wxUserInfo = gson.fromJson(userInfo, WxUserInfo.class);
        logger.info("gson解析微信用户信息:{}",wxUserInfo);

        SecurityUser userDetails = wxOpenIdUserDetailsService.loadUserByWxOpenid(session.getOpenid());

        logger.info("根据openid查询用户信息:{}",userDetails);


        if(null != userDetails){
            WxAuthenticationToken authenticationToken = new WxAuthenticationToken(userDetails, userDetails.getAuthorities());
            OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
            logger.info("用户存在, 直接创建Token:{}",authenticationToken);
            return new OAuth2Authentication(storedOAuth2Request,authenticationToken);
        }else {
            logger.info("用户不存在, 创建用户再创建Token");
            userDetails = wxOpenIdUserDetailsService.createUser(wxUserInfo, session.getOpenid());
            WxAuthenticationToken authenticationToken = new WxAuthenticationToken(userDetails, userDetails.getAuthorities());
            OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
            logger.info("用户创建成功,生成token：{}",authenticationToken);
            return new OAuth2Authentication(storedOAuth2Request,authenticationToken);
        }
    }
}
