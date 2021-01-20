package com.bmy.dao.service;

import com.bmy.dao.domain.Comment;
import com.bmy.dao.domain.Follow;
import com.bmy.dao.domain.ex.DynamicPraise;

import java.util.List;

public interface MessageService {

    List<DynamicPraise> selectPraiseMessage(Long uid);

    List<Comment> selectCommentMessage(Long uid);

//    List<Follow> selectFollowMessage(Long fuid);
}
