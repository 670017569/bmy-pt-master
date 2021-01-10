package com.bmy.dao.mapper;

import com.bmy.dao.domain.Comment;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentMapper extends Mapper<Comment> {


}
