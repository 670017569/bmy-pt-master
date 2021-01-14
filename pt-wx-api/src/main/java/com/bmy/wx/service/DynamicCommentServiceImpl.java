package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.dao.domain.Comment;
import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.mapper.CommentMapper;
import com.bmy.dao.mapper.DynamicMapper;
import com.bmy.dao.service.DynamicCommentService;
import com.bmy.dao.service.DynamicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DynamicCommentServiceImpl implements DynamicCommentService {

    Logger logger = LoggerFactory.getLogger(DynamicCommentService.class);

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private Snowflake snowflake;


    @Resource
    private CommentMapper commentMapper;

    @Override
    public boolean pubComment(Comment comment) {
        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(comment.getDynId());
        if (comment.getToUid().equals(dynamic.getUid())){
            comment.setToUid(null);
        }
        comment.setId(snowflake.nextId());
        logger.info("插入评论：{}",comment);
        int res = commentMapper.insertSelective(comment);
        dynamic.setComments(dynamic.getComments()+1);
        dynamicMapper.updateByPrimaryKeySelective(dynamic);
        return res == 1;
    }

    @Override
    public boolean delComment(Comment comment) {
        return false;
    }

    @Override
    public List<Comment> selectAll(Comment comment) {
        return null;
    }

}
