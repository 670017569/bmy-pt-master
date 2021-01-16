package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.domain.ex.DynamicPraise;
import com.bmy.dao.mapper.DynamicMapper;
import com.bmy.dao.mapper.UserInfoMapper;
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

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private DynamicMapper dynamicMapper;

    /**
     * 对动态点赞
     * 要更新的用户信息是动态所有者的详细信息，不是点赞操作者的信息
     * 且该操作应该放在controller层，点赞的操作完成后进行调用更新
     * 待修改！！！！！！！！！！！！！！！！！！！！！！！！！！
     * @param dynId
     * @return
     */
    @Override
    public boolean praise(UserInfo userInfo, Long dynId) {
        Example example = new Example(DynamicPraise.class);
        example.and()
                .andEqualTo("uid",userInfo.getId())
                .andEqualTo("dynId",dynId);
        //查询数据库中是否有该用户对该动态的点赞数据
        //根据点赞者的uid和被点赞的动态dyn_id在动态点赞的表中进行查询
        DynamicPraise dynamicPraise = dynamicPraiseMapper.selectOneByExample(example);
        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(dynId);
        UserInfo dynamicOwner = userInfoMapper.selectByPrimaryKey(dynamic.getUid());

        if (null == dynamicPraise){
            //如果查询结果为空，即表示从未点赞
            Long id = snowflake.nextId();
            //接下来向动态点赞表中插入该条点赞记录
            DynamicPraise praise = DynamicPraise.builder().id(id).dynId(dynId).uid(userInfo.getId()).build();
            //将动态中的点赞量+1并更新
            dynamic.setPraises(dynamic.getPraises()+1);
            dynamicMapper.updateByPrimaryKey(dynamic);
            //将动态拥有者用户详细信息表中的点赞量+1并更新
            dynamicOwner.setPraised(dynamicOwner.getPraised()+1);
            userInfoMapper.updateByPrimaryKey(dynamicOwner);
            return 1 == dynamicPraiseMapper.insertSelective(praise);
        }else if (dynamicPraise.getCanceled()){
            //如果该查询不为空，则将点赞记录的逻辑删除状态更新为false
            Example examples = new Example(DynamicPraise.class);
            examples.and()
                    .andEqualTo("id",dynamicPraise.getId());
            DynamicPraise dynamicPraise1 = DynamicPraise.builder().canceled(false).build();
            //将用户详细信息表中的点赞量+1
            userInfo.setPraised(userInfo.getPraised()+1);
            //更新用户信息表
            userInfoService.updateUserInfo(userInfo);
            return 1 == dynamicPraiseMapper.updateByExampleSelective(dynamicPraise1,examples);
        }else {
            return false;
        }
    }

    /**
     * 取消对动态的点赞
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
        //根据点赞者的uid和被点赞的动态的dyn_id查询点赞记录
        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(dynId);
        UserInfo dynamicOwner = userInfoMapper.selectByPrimaryKey(dynamic.getUid());
        //若记录存在
        if (!praise.getCanceled()){
            dynamic.setPraises(dynamic.getPraises());
            dynamicMapper.updateByPrimaryKey(dynamic);
            //将用户详细信息被点赞量-1 ,更新用户详细信息表
            dynamicOwner.setPraised(dynamicOwner.getPraised()-1);
            userInfoMapper.updateByPrimaryKey(dynamicOwner);
            //将该条点赞记录的逻辑删除桩体设置为true
            DynamicPraise dynamicPraise = DynamicPraise.builder().id(praise.getId()).canceled(true).build();
            //更新动态点赞表
            return 1 == dynamicPraiseMapper.updateByPrimaryKeySelective(dynamicPraise);
        }else {
            return false;
        }
    }

    /**
     * 在用户查询动态的列表时，
     * 需要查询操作的用户是否对该条动态点赞
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
        //根据条件进行查询，如果返回为不为空，即该用户对该动态存在点赞记录即为true
        return null != dynamicPraise;
    }



}
