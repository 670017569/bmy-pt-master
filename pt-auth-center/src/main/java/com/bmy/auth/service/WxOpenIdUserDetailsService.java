package com.bmy.auth.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.auth.config.WxProperties;
import com.bmy.auth.entity.SecurityUser;
import com.bmy.auth.vo.WxUserSession;
import com.bmy.dao.domain.Role;
import com.bmy.dao.domain.User;
import com.bmy.dao.domain.WxUserInfo;
import com.bmy.dao.mapper.RoleMapper;
import com.bmy.dao.mapper.UserMapper;
import com.bmy.dao.mapper.WxUserInfoMapper;
import com.bmy.dao.mapper.ex.UserRoleMapper;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName WxOpenIdUserDetailsService
 * @Description 微信用户登录用户详情类实现
 * @Author potato
 * @Date 2020/12/15 下午6:00
 **/

@Service
public class WxOpenIdUserDetailsService {

    Logger log = LoggerFactory.getLogger(WxOpenIdUserDetailsService.class);

    @Resource
    private WxProperties properties;

    @Resource
    private UserMapper userMapper;

    @Resource
    private Snowflake snowflake;

    @Resource
    protected RestTemplate restTemplate;

    @Resource
    private Gson gson;

    @Resource
    private WxUserInfoMapper wxUserInfoMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 传入wxopenid, 用户存在则返回信息
     * @return {@link UserDetails}
     * @param wxOpenid 微信openid
     */
    public UserDetails loadUserByWxOpenid(String wxOpenid) {
        log.info("微信小程序用户登录开始: 传入wxOpenid:{}",wxOpenid);

        Example example = new Example(User.class);
        example.and()
                .andEqualTo("wxOpenid",wxOpenid)
                .andEqualTo("deleted","0");
        User user = userMapper.selectOneByExample(example);
        if (null == user){
            return null;
        }
        List<Role> roles = userRoleMapper.selectRolesByUid(user.getId());
        user.setRoles(roles);
        log.info("查询到用户信息并返回:{}",user);
        return gson.fromJson(gson.toJson(user), SecurityUser.class);
    }

    /**
     * 用户不存在, 创建用户并返回{@link User}
     * 在这里未进行小程序用户的角色赋予
     * 将在job中进行
     * @return {@link User}
     * @param wxUserInfo {@link WxUserInfo}
     * @param wxOpenid 微信openid
     */
    public UserDetails createUser(WxUserInfo wxUserInfo, String wxOpenid) {
        Long id = snowflake.nextId();
        try {
            log.info("微信小程序用户注册 ==> 开始 生成uid:{}",id);
            User user = User.builder()
                    .id(id)
                    .wxOpenid(wxOpenid)
                    .build();
            int res = userMapper.insertSelective(user);
            if (1 == res) {
                log.info("用户表数据插入成功");
                wxUserInfo.setUid(id);
                log.info("生成微信用户信息：{}",wxUserInfo);
                wxUserInfoMapper.insertSelective(wxUserInfo);
                log.info("微信用户信息插入成功");
            }
        }catch (DuplicateKeyException e){
            log.info("用户信息插入失败:{}",e.toString());
            return null;
        }
//        User user = userMapper.selectOneByUid(id);
        Example example = new Example(User.class);
        example.and()
                .andEqualTo("wxOpenid",wxOpenid)
                .andEqualTo("deleted",0);
        User user = userMapper.selectOneByExample(example);
        user.setRoles(userRoleMapper.selectRolesByUid(user.getId()));
        SecurityUser securityUser = gson.fromJson(gson.toJson(user), SecurityUser.class);
        log.info("微信小程序用户注册 <== 成功，返回用户信息：{}",securityUser);
        return securityUser;
    }

    /**
     * 创建微信用户会话
     * @param code
     * @return
     */
    public WxUserSession openSession(String code) throws IOException {
        log.info("创建微信用户会话 ==> 开始");
        String url = String.format(properties.getApi(), properties.getAppId(), properties.getAppSecret(),code);
        log.info("生成用户会话url：{}",url);
        log.info("开始请求微信后台接口");
        String res = restTemplate.getForObject(url, String.class);
        log.info("请求微信后台服务器 <== 成功：{}",res);
        return gson.fromJson(res, WxUserSession.class);
    }

    /**
     * 解码验证数据完整性
     * @param userInfo
     * @param sessionKey
     * @param signature
     * @return
     *
     */
    public boolean userInfoValidate(String userInfo, String sessionKey, String signature)throws UnauthorizedUserException {
        String signatureBytes = DigestUtils.sha1Hex(userInfo + sessionKey);
        return signature.equals(signatureBytes);
    }

}
