package com.bmy.wx;

import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.mapper.CommentMapper;
import com.bmy.dao.mapper.DynamicMapper;
import com.bmy.dao.mapper.NewsLinkMapper;
import com.bmy.dao.mapper.UserInfoMapper;
import com.bmy.dao.mapper.ex.DynamicPicMapper;
import com.bmy.dao.mapper.ex.UserRoleMapper;
import com.bmy.wx.service.DynamicServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @ClassName ContextTest
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 下午7:22
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ContextTest {

    @Resource
    private NewsLinkMapper newsLinkMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private DynamicServiceImpl dynamicServiceImpl;

    @Resource
    private DynamicPicMapper dynamicPicMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private CommentMapper commentMapper;

    @Test
    public void test(){
        System.out.println(commentMapper.selectByPrimaryKey(Long.parseLong("1350410852473241600")));
    }

}
