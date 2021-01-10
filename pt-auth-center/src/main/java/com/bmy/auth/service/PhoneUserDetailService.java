package com.bmy.auth.service;

import com.bmy.auth.common.vo.SecurityUser;
import com.bmy.core.constant.Response;
import com.bmy.core.constant.SmsAuthenticated;
import com.bmy.core.exception.UnAuthorizedException;
import com.bmy.dao.domain.Role;
import com.bmy.dao.domain.User;
import com.bmy.dao.mapper.UserMapper;
import com.bmy.dao.mapper.ex.UserRoleMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName PhoneAuthUserDetailsService
 * @Description 手机认证用户Service实现类
 * @Author potato
 * @Date 2020/12/17 下午6:22
 **/
@Service
public class PhoneUserDetailService {

    Logger logger = LoggerFactory.getLogger(PhoneUserDetailService.class);

    @Resource
    private SmsService smsService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private Gson gson;

    @Resource
    private UserRoleMapper userRoleMapper;
    /**
     * 手机号+短信验证码 验证
     * @param phone 手机号
     * @param code 验证码
     * @return {@link UserDetails}
     */
    public UserDetails loadUserByPhoneAndSmsCode(String phone, String code) {
        logger.info("短信验证码校验 ==> 开始");

        Boolean res = smsService.smsCheck(phone, code, SmsAuthenticated.ACTION.LOGIN);

        logger.info("验证码校验结果:{}",res);


        User user = null;
        SecurityUser securityUser = null;
        if (res){
            logger.info("短信验证码正确,查询{}所在用户",phone);
            Example example = new Example(User.class);
            example.and()
                    .andEqualTo("phone",phone)
                    .andEqualTo("deleted",0);
            user = userMapper.selectOneByExample(example);
            securityUser = gson.fromJson(gson.toJson(user),SecurityUser.class);
            List<Role> roles = userRoleMapper.selectRolesByUid(user.getId());
            securityUser.setRoles(roles);
        }
        logger.info("查询到用户信息:{}",securityUser);
        return securityUser;
    }

    /**
     * 手机号+密码登陆
     * @param phone 手机号
     * @param rawPassword 密码(经过前端初步加密)
     * @return {@link UserDetails}
     */
    public UserDetails loadUserByPhoneAndPassword(String phone, String rawPassword) {
        Example example = new Example(User.class);
        example.and()
                .andEqualTo("phone",phone)
                .andEqualTo("deleted",0);
        User user = userMapper.selectOneByExample(example);

        if (null == user){
            throw new UnAuthorizedException(Response.USER_NOT_EXIST); // 用户不存在
        }
        else if (null == user.getPassword()){
            throw new UnAuthorizedException(Response.INVALID_PASSWORD);
        }
        else if (!BCrypt.checkpw(rawPassword, user.getPassword())){
            throw new UnAuthorizedException(Response.INVALID_PASSWORD); // 密码不正确
        }
        SecurityUser securityUser = gson.fromJson(gson.toJson(user),SecurityUser.class);
        List<Role> roles = userRoleMapper.selectRolesByUid(user.getId());
        securityUser.setRoles(roles);
        logger.info("查询到用户信息:{}",securityUser);
        return securityUser;
    }

}
