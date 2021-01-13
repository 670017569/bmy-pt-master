package com.bmy.dao.mapper;

import com.bmy.dao.domain.Dynamic;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

@Repository
public interface DynamicMapper extends Mapper<Dynamic> {




}
