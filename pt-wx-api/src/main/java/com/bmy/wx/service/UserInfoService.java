package com.bmy.wx.service;

import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserInfoService
 * @Description TODO
 * @Author potato
 * @Date 2020/12/25 下午5:56
 **/
@Service
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 根据主键查询用户信息
     * @param uid
     * @return
     */
    public UserInfo getUserInfo(Long uid){
        return userInfoMapper.selectByPrimaryKey(uid);
    }

}
