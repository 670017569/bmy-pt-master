package com.bmy.dao.mapper.ex;

import com.bmy.dao.domain.ex.DynamicPraise;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DynamicPraiseMapper extends Mapper<DynamicPraise> {

    List<DynamicPraise> selectPraiseMessage(Long uid);

}
