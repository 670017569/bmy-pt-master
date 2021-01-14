package com.bmy.dao.service;

import com.bmy.dao.domain.Comment;

import java.util.List;

/**
 * 动态的评论
 */
public interface DynamicCommentService {

    //发表评论
    public boolean pubComment(Comment comment);

    //删除评论
    public boolean delComment(Comment comment);

    //查询评论
    public List<Comment> selectAll(Comment comment);

}
