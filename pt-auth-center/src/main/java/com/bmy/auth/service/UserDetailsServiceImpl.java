package com.bmy.auth.service;

import com.bmy.auth.entity.SecurityUser;
import com.bmy.dao.domain.User;
import com.bmy.dao.mapper.UserMapper;
import com.bmy.dao.mapper.ex.UserRoleMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @ClassName UserDetailServiceImpl
 * @Description 用户信息Service实现类
 * 实现UserDetailsService接口，通过username查询数据库中的信息并返回
 * @Author potato
 * @Date 2020/12/15 下午5:43
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private Gson gson;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("查询用户信息 ==> 开始:{}",username);
//        User user = userMapper.selectByUsername(username);
        Example example = new Example(User.class);
        example.and()
                .andEqualTo("username",username)
                .andEqualTo("deleted",0);
        User user = userMapper.selectOneByExample(example);

        if (null == user){
            log.info("用户查询为空");
            throw new UsernameNotFoundException("用户名不存在");
        }else {
            user.setRoles(userRoleMapper.selectRolesByUid(user.getId()));
        }
        log.info("查询用户信息 <== 结束{}",user);
        return gson.fromJson(gson.toJson(user), SecurityUser.class);
    }
}
