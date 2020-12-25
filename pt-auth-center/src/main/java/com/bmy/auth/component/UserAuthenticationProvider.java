//package com.bmy.auth.component;
//
//import com.bmy.auth.entity.SecurityUser;
//import com.bmy.auth.service.UserDetailsServiceImpl;
//import com.bmy.core.constant.Response;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.Resource;
//
///**
// * @ClassName UserAuthenticationProvider
// * @Description 用户名密码认证重写
// * （暂未注入）
// * @Author potato
// * @Date 2020/12/15 下午10:18
// **/
//@Component
//public class UserAuthenticationProvider implements AuthenticationProvider {
//
//    Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);
//
//    public UserAuthenticationProvider(){}
//
//    @Resource
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Resource
//    private BCryptPasswordEncoder passwordEncoder;
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = (String) authentication.getPrincipal();
//        String password = (String) authentication.getCredentials();
//        logger.info("用户名密码认证:{} ==> 开始",username);
//        SecurityUser currentSecurityUser = userDetailsService.loadUserByUsername(username);
//        if (currentSecurityUser.getUsername().equals(username) && passwordEncoder.encode(currentSecurityUser.getPassword()).equals(password)){
//            logger.info("用户名密码验证成功");
//            authentication.setAuthenticated(true);
//        }else {
//            logger.info("用户名密码验证失败");
//            authentication.setAuthenticated(false);
//        }
//        if (!currentSecurityUser.isAccountNonLocked() || !currentSecurityUser.isAccountNonExpired() || !currentSecurityUser.isCredentialsNonExpired()) {
//            logger.info("账号状态异常");
//            throw new LockedException(Response.ACCOUNT_LOCKED.getMessage());
//        }
//        logger.info("用户名密码验证 <== 结束");
//        return authentication;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//}
