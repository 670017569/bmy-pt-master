package com.bmy.wx.service;

import com.bmy.dao.domain.Comment;
import com.bmy.dao.domain.Follow;
import com.bmy.dao.domain.ex.DynamicPraise;
import com.bmy.dao.mapper.FollowMapper;
import com.bmy.dao.mapper.ex.DynamicPraiseMapper;
import com.bmy.dao.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private DynamicPraiseMapper dynamicPraiseMapper;

    @Resource
    private FollowMapper followMapper;

    @Override
    public List<DynamicPraise> selectPraiseMessage(Long uid) {
        return dynamicPraiseMapper.selectPraiseMessage(uid);
    }

    @Override
    public List<Comment> selectCommentMessage(Long uid) {
        return null;
    }

//    @Override
//    public List<Follow> selectFollowMessage(Long fuid) {
//        return followMapper.selectFollowMessage(fuid);
//    }
}
