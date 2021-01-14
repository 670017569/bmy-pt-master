package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.domain.ex.DynamicPraise;
import com.bmy.dao.mapper.ex.DynamicPraiseMapper;
import com.bmy.dao.service.DynamicPraiseService;
import com.bmy.dao.service.UserInfoService;
import com.bmy.wx.drools.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class DynamicPraiseServiceImpl implements DynamicPraiseService {

    private static final Logger logger = LoggerFactory.getLogger(DynamicPraiseServiceImpl.class);

    @Resource
    private RuleService ruleService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private DynamicPraiseMapper dynamicPraiseMapper;

    @Resource
    private Snowflake snowflake;

    /**
     * 对动态点赞
     * @param dynId
     * @return
     */
    @Override
    public boolean praise(UserInfo userInfo, Long dynId) {
        Example example = new Example(DynamicPraise.class);
        example.and()
                .andEqualTo("uid",userInfo.getId())
                .andEqualTo("dynId",dynId);
        DynamicPraise dynamicPraise = dynamicPraiseMapper.selectOneByExample(example);
        if (null == dynamicPraise){
            Long id = snowflake.nextId();
            DynamicPraise praise = DynamicPraise.builder().id(id).dynId(dynId).uid(userInfo.getId()).build();
            userInfo.setPraised(userInfo.getPraised()+1);
            userInfoService.updateUserInfo(userInfo);
            return 1 == dynamicPraiseMapper.insertSelective(praise);
        }else if (dynamicPraise.getCanceled()){
            Example examples = new Example(DynamicPraise.class);
            examples.and()
                    .andEqualTo("id",dynamicPraise.getId());
            DynamicPraise dynamicPraise1 = DynamicPraise.builder().canceled(false).build();
            return 1 == dynamicPraiseMapper.updateByExampleSelective(dynamicPraise1,examples);
        }else {
            return false;
        }
    }

    /**
     * 取消点赞
     * @param dynId
     * @return
     */
    @Override
    public boolean delPraise(UserInfo userInfo, Long dynId) {
        Example example = new Example(DynamicPraise.class);
        example.and()
                .andEqualTo("uid",userInfo.getId())
                .andEqualTo("dynId",dynId);
        DynamicPraise praise = dynamicPraiseMapper.selectOneByExample(example);
        if (!praise.getCanceled()){
            userInfo.setPraised(userInfo.getPraised()-1);
            userInfoService.updateUserInfo(userInfo);
            DynamicPraise dynamicPraise = DynamicPraise.builder().id(praise.getId()).canceled(true).build();
            return 1 == dynamicPraiseMapper.updateByPrimaryKeySelective(dynamicPraise);
        }else {
            return false;
        }
    }

    /**
     * 设置用户是否对动态点赞
     * @param dynId
     * @return
     */
    @Override
    public boolean setIsPraised(Long uid,Long dynId){
        Example example = new Example(DynamicPraise.class);
        example.and()
                .andEqualTo("uid",uid)
                .andEqualTo("dynId",dynId)
                .andEqualTo("canceled",false);
        DynamicPraise dynamicPraise = dynamicPraiseMapper.selectOneByExample(example);
        return null != dynamicPraise;
    }



}
