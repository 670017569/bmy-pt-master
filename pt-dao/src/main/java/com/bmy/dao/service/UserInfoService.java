package com.bmy.dao.service;

import com.bmy.dao.domain.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface UserInfoService {

    public UserInfo getUserByToken(HttpServletRequest request);


}
