package com.bmy.auth;

import com.bmy.dao.mapper.ex.RolePermissionMapper;
import com.bmy.dao.mapper.ex.UserRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName ContextTest
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 上午12:22
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ContextTest {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Test
    public void test(){
        System.out.println(rolePermissionMapper.selectPermissions(Long.parseLong("1")));
    }

}
