package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.dao.domain.Comment;
import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.mapper.CommentMapper;
import com.bmy.dao.mapper.DynamicMapper;
import com.bmy.dao.service.DynamicCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    /**
     * 对动态发表评论
     * @param comment
     * @return
     */
    @Override
    public Comment pubComment(Comment comment) {
        //根据评论DTO中的dynId查询动态的数据
        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(comment.getDynId());
        logger.info("要评论的动态:{}",dynamic);
        //如果
        if (comment.getToUid() != 0){
            comment.setType(1);
        }
        Long id = snowflake.nextId();
        comment.setId(id);
        logger.info("插入评论：{}",comment);
        commentMapper.insertSelective(comment);
        dynamic.setComments(dynamic.getComments()+1);
        int res = dynamicMapper.updateByPrimaryKeySelective(dynamic);
        if (res == 1){
            return commentMapper.selectById(id);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean delComment(Long id) {
        Comment comment = commentMapper.selectById(id);
        comment.setDeleted(true);
        int res = commentMapper.updateByPrimaryKeySelective(comment);
        if (res == 1){
            Dynamic dynamic = dynamicMapper.selectByPrimaryKey(comment.getDynId());
            dynamic.setComments(dynamic.getComments()-1);
            dynamicMapper.updateByPrimaryKeySelective(dynamic);
            return true;
        }
        return false;
    }

    /**
     * 查询一级评论
     * @param dynId
     * @return
     */
    @Override
    public List<Comment> selectAll(Long dynId) {
        List<Comment> comments = commentMapper.selectByDynId(dynId);
        return comments;
    }

    /**
     * 查询二级评论
     * @param pid
     * @return
     */
    @Override
    public List<Comment> selectByPid(Long pid) {
        List<Comment> comments = commentMapper.selectByPid(pid);
        return comments;
    }

    /**
     * 查询所有的评论
     * @param dynId
     * @return
     */
    @Override
    public List<Comment> selectAllComment(Long dynId) {

        return commentMapper.selectAllComment(dynId);
    }


}
