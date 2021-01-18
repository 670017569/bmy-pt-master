package com.bmy.dao.service;

import com.bmy.dao.domain.Comment;

import java.util.List;

/**
 * 动态的评论
 */
public interface DynamicCommentService {

    //发表评论
    public Comment pubComment(Comment comment);

    //删除评论
    public boolean delComment(Long id);

    //查询一级评论
    public List<Comment> selectAll(Long dynId);

    //查询二级评论
    public List<Comment> selectByPid(Long pid);

    //查询所有评论
    public List<Comment> selectAllComment(Long dynId);

}
