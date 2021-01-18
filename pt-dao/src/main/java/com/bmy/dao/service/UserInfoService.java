package com.bmy.dao.service;

import com.bmy.dao.domain.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface UserInfoService {

    UserInfo getUserByToken(HttpServletRequest request);

    Object me(HttpServletRequest request);

    boolean updateUserInfo(UserInfo userInfo);

    UserInfo selectByUid(Long uid);
}
