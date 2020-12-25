package com.bmy.wx;

import com.bmy.dao.mapper.NewsLinkMapper;
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

    @Test
    public void test(){



    }

}
