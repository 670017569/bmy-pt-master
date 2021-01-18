package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.BadRequestException;
import com.bmy.dao.domain.Follow;
import com.bmy.dao.mapper.FollowMapper;
import com.bmy.dao.service.FollowService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    private FollowMapper followMapper;

    @Resource
    private Snowflake snowflake;

    @Override
    public boolean follow(Long uid, Long fuid) {
        Example example = new Example(Follow.class);
        example.and().andEqualTo("uid",uid)
                .andEqualTo("fuid",fuid);
        Follow f = followMapper.selectOneByExample(example);
        if (null == f){
            //不存在记录  直接创建
            Follow follow = Follow.builder().id(snowflake.nextId()).uid(uid).fuid(fuid).build();
            return 1 == followMapper.insertSelective(follow);
        }else if (f.getCanceled() == 1){
            f.setCanceled(0);
            return 1 == followMapper.updateByPrimaryKey(f);
        }else {
            throw new BadRequestException(Response.ALREADY_DONE);
        }
    }

    @Override
    public boolean delFollow(Long uid, Long fuid) {
        Example example = new Example(Follow.class);
        example.and().andEqualTo("uid",uid)
                .andEqualTo("fuid",fuid);
        Follow f = followMapper.selectOneByExample(example);
        if (f.getCanceled() == 0){
            f.setCanceled(1);
            return  1 == followMapper.updateByPrimaryKey(f);
        }else {
            throw new BadRequestException(Response.ILLEGAL_PARAMS);
        }
    }
    @Override
    public boolean setIsFollowed(Long uid, Long fuid) {
        Example example = new Example(Follow.class);
        example.and().andEqualTo("uid",uid)
                .andEqualTo("fuid",fuid)
                .andEqualTo("canceled",0);
        Follow follow = followMapper.selectOneByExample(example);
        return follow != null;
    }
}
