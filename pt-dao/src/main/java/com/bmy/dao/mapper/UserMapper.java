package com.bmy.dao.mapper;

import com.bmy.dao.domain.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> , ExampleMapper<User> {
}
