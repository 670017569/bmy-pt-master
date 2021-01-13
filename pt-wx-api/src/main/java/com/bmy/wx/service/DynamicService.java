package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.domain.ex.DynamicPic;
import com.bmy.dao.mapper.ex.DynamicPicMapper;
import com.bmy.wx.vo.DynamicInVo;
import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.mapper.DynamicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DynamicService {

    Logger logger = LoggerFactory.getLogger(DynamicService.class);

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private DynamicPicMapper dynamicPicMapper;

    @Resource
    private Snowflake snowflake;

    /**
     * 分页查询某用户的所有动态
     * 并带上动态所有图片的链接
     * @return
     */
//    public List<Dynamic> selectAllByUid(Long uid){
//        List<Dynamic> list = dynamicMapper.selectAllDynamicByUid(uid);
//        return list;
//    }

    /**
     * 查询所有动态
     * @return
     */
    public List<Dynamic> selectAll(){
        return dynamicMapper.selectAll();
    }

    /**
     * 发布一条新动态
     * @param dynamicVo
     * @param userInfo
     * @return
     */
    public boolean pubDynamic(DynamicInVo dynamicVo, UserInfo userInfo){
        //生成一个id
        Long id = snowflake.nextId();
        Dynamic dynamic = Dynamic.builder()
                .id(id)
                .uid(userInfo.getId())
                .content(dynamicVo.getContent())
                .region(dynamicVo.getRegion())
                .build();
        List<DynamicPic> pics = dynamicVo.getPics();
        //将动态插入表中
        Integer res = dynamicMapper.insertSelective(dynamic);
        //将上传的图片插入pic表中
        for (DynamicPic pic : pics){
            int r = dynamicPicMapper.insertSelective(pic);
            if (r == 1) {
                DynamicPic dynamicPic = DynamicPic.builder()
                        .id(pic.getId())
                        .dynId(id)
                        .url(pic.getUrl())
                        .build();
                //将图片和动态的id插入关系表中
                dynamicPicMapper.insertSelective(dynamicPic);
            }
        }
        return true;
    }

}
