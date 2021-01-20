package com.bmy.wx;

import com.bmy.dao.mapper.CommentMapper;
import com.bmy.dao.mapper.DynamicMapper;
import com.bmy.dao.mapper.NewsLinkMapper;
import com.bmy.dao.mapper.UserInfoMapper;
import com.bmy.dao.mapper.ex.DynamicPicMapper;
import com.bmy.dao.mapper.ex.DynamicPraiseMapper;
import com.bmy.dao.mapper.ex.UserRoleMapper;
import com.bmy.dao.service.DynamicPraiseService;
import com.bmy.wx.service.DynamicServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Resource
    private DynamicPraiseService dynamicPraiseService;

    @Resource
    private DynamicPraiseMapper dynamicPraiseMapper;
    @Test
    public void test(){

        System.out.println(dynamicPraiseMapper.selectPraiseMessage(Long.parseLong("1338842507810701311")));

    }

}
