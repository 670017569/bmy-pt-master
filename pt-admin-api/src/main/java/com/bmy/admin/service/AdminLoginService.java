package com.bmy.admin.service;

import com.bmy.core.api.AuthenticationApi;
import com.bmy.core.constant.R;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import javax.annotation.Resource;

@Service
public class AdminLoginService {

    Logger logger = LoggerFactory.getLogger(AdminLoginService.class);

    @Resource
    private AuthenticationApi api;

    @Resource
    private UserInfoMapper userInfoMapper;

    public R<Object> login(MultiValueMap<String, String> map){
        R<Object> r = api.generateToken(map);
        logger.info("登录响应数据 ：{}",r);
        if (r.getCode() == 200){
            logger.info("登录成功,变更今日登录状态以及最后登录时间");
        }
        return r;
    }

}
