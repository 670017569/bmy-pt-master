package com.bmy.dao.mapper;

import com.bmy.dao.domain.Comment;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentMapper extends Mapper<Comment> {


    public List<Comment> selectByDynId(Long dynId);

    public List<Comment> selectByPid(Long pid);

    public Integer selectCountByPid(Long pid);

    @Override
    Comment selectByPrimaryKey(Object key);
}
