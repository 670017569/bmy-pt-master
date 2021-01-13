package com.bmy.dao.service;

import com.bmy.dao.domain.User;

public interface UserService {

    public User getUserInfo(String token);

}
