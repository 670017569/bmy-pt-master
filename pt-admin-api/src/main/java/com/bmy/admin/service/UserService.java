package com.bmy.admin.service;

import com.bmy.core.api.AuthenticationApi;
import com.bmy.core.constant.R;
import com.bmy.dao.domain.User;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private AuthenticationApi api;

    @Resource
    private UserMapper userMapper;

    public User getUserByToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.replace("bearer ","");
        R<Object> jsonObject =  api.checkToken(token);
        Map<String,String> map = (LinkedHashMap<String, String>) jsonObject.getData();
        String username = map.get("user_name");
        Example example = new Example(UserInfo.class);
        example.and()
                .andEqualTo("username",username);
        return userMapper.selectOneByExample(example);
    }

}
