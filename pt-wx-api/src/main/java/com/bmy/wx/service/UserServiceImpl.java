package com.bmy.wx.service;

import com.bmy.core.api.AuthenticationApi;
import com.bmy.core.constant.R;
import com.bmy.dao.domain.User;
import com.bmy.dao.mapper.UserMapper;
import com.bmy.dao.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private AuthenticationApi api;

    @Resource
    private UserMapper userMapper;

    public User getUserInfo(String token){
        R<Object> jsonObject =  api.checkToken(token);
        Map<String,String> map = (LinkedHashMap<String, String>) jsonObject.getData();
        String username = map.get("user_name");
        Example example = new Example(User.class);
        example.and()
                .andEqualTo("username",username);
        return userMapper.selectOneByExample(example);
    }


}
