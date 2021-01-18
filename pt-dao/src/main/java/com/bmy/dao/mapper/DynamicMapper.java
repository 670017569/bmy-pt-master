package com.bmy.dao.mapper;

import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.domain.ex.DynamicPraise;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

@Repository
public interface DynamicMapper extends Mapper<Dynamic> {


    /**
     * 查询所有的动态
     * @return
     */
    @Override
    List<Dynamic> selectAll();

    /**
     * 根据uid查询该用户的所有动态
     * @param uid
     * @return
     */
    List<Dynamic> selectByUid(Long uid);

    /**
     * 根据主键查询
     * @param key
     * @return
     */
    @Override
    Dynamic selectByPrimaryKey(Object key);



}
