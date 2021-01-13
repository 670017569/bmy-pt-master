package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.dao.domain.ex.DynamicPic;
import com.bmy.dao.mapper.ex.DynamicPicMapper;
import com.bmy.dao.service.DynamicService;
import com.bmy.dao.vo.DynamicInVo;
import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.mapper.DynamicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DynamicServiceImpl implements DynamicService {

    Logger logger = LoggerFactory.getLogger(DynamicServiceImpl.class);

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private DynamicPicMapper dynamicPicMapper;

    @Resource
    private Snowflake snowflake;


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
     * @return
     */
    public boolean pubDynamic(DynamicInVo dynamicVo, Long uid){
        try {
            //生成一个id
            Long id = snowflake.nextId();
            Dynamic dynamic = Dynamic.builder()
                    .id(id)
                    .uid(uid)
                    .content(dynamicVo.getContent())
                    .region(dynamicVo.getRegion())
                    .build();
            List<DynamicPic> pics = dynamicVo.getPics();
            //将动态插入表中
            Integer res = dynamicMapper.insertSelective(dynamic);
            //将上传的图片插入pic表中
            for (DynamicPic pic : pics){
                pic.setDynId(id);
                //将图片和动态的id插入关系表中
                dynamicPicMapper.insertSelective(pic);
            }
            return true;
        }catch (DuplicateKeyException e){
            return false;
        }
    }

    /**
     * 根据uid查询动态
     * @param uid
     * @return
     */
    public List<Dynamic> selectByUid(Long uid) {
        Example example = new Example(Dynamic.class);
        example.and()
                .andEqualTo("uid",uid)
                .andEqualTo("deleted",0);
        return dynamicMapper.selectByExample(example);
    }

    @Override
    public boolean deleteById(Long id) {
        int res = dynamicMapper.deleteByPrimaryKey(id);
        return 1 == res;
    }



}
