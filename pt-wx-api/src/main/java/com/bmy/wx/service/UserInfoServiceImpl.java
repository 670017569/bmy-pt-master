package com.bmy.wx.service;

import com.bmy.core.api.AuthenticationApi;
import com.bmy.core.constant.R;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.mapper.UserInfoMapper;
import com.bmy.dao.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName UserInfoService
 * @Description TODO
 * @Author potato
 * @Date 2020/12/25 下午5:56
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private AuthenticationApi api;
    /**
     * 根据主键查询用户信息
     * @param
     * @return
     */
    public UserInfo getUserByToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.replace("bearer ","");
        R<Object> jsonObject =  api.checkToken(token);
        Map<String,String> map = (Map<String, String>) jsonObject.getData();
        String username = map.get("user_name");
        Example example = new Example(UserInfo.class);
        example.and()
                .andEqualTo("username",username);
        return userInfoMapper.selectOneByExample(example);
    }
    public Object me(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.replace("bearer ","");
        return api.checkToken(token);
    }

    public boolean updateUserInfo(UserInfo userInfo){
        return 1 == userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

}
