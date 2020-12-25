package com.bmy.auth.component;


import com.bmy.auth.entity.PhoneAuthenticationToken;
import com.bmy.auth.service.PhoneUserDetailService;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.UnAuthorizedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class PhoneAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private PhoneUserDetailService phoneUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = null;
        Map<String, String> map = (Map<String, String>) authentication.getPrincipal();
        String phone = map.get("phone");
        String code = map.get("code");
        String password = map.get("password");
        if(null != phone && null != code)
            user = phoneUserDetailService.loadUserByPhoneAndSmsCode(phone, code);
        else if(null != phone && null != password){
            user = phoneUserDetailService.loadUserByPhoneAndPassword(phone, password);
        }else {
            throw new UnAuthorizedException(Response.USER_NOT_EXIST);
        }
        return new PhoneAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

