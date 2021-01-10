package com.bmy.admin;

import com.bmy.admin.config.WxOfficialProperties;
import com.bmy.admin.service.WxOfficialService;
import com.bmy.dao.mapper.WxOfficialNewsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @ClassName AdminTest
 * @Description TODO
 * @Author potato
 * @Date 2020/12/24 下午2:36
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminTest {

    @Resource
    WxOfficialProperties wxOfficialProperties;

    @Resource
    private WxOfficialService wxOfficialService;

    @Resource
    private WxOfficialNewsMapper wxOfficialNewsMapper;

    @Test
    public void test(){

        Date date = new Date((long) 1.609394629E9);
        System.out.println(date);
//        System.out.println(wxOfficialService.refresh(10));
//        System.out.println(wxOfficialService.getOfficialNews(10));;
    }

}
