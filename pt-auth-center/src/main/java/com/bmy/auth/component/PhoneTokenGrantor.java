package com.bmy.auth.component;

import com.bmy.auth.entity.PhoneAuthenticationToken;
import com.bmy.auth.service.PhoneUserDetailService;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.UnAuthorizedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;


public class PhoneTokenGrantor extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "phone";
    private final PhoneUserDetailService phoneUserDetailService;

    public PhoneTokenGrantor(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, PhoneUserDetailService phoneUserDetailService) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.phoneUserDetailService = phoneUserDetailService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        UserDetails user  = null;
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

        String phone = parameters.get("phone");
        String code = parameters.get("code");
        String password = parameters.get("password");

        if(phone!=null&&code!=null)
            user = phoneUserDetailService.loadUserByPhoneAndSmsCode(phone, code);
        else if(phone!=null&&password!=null)
            user = phoneUserDetailService.loadUserByPhoneAndPassword(phone, password);
        if(user==null) throw new UnAuthorizedException(Response.INVALID_INPUT);

        PhoneAuthenticationToken token = new PhoneAuthenticationToken(user, null, user.getAuthorities());
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, token);
    }

}
