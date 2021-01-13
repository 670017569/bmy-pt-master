package com.bmy.dao.mapper.ex;


import com.bmy.dao.domain.ex.DynamicPic;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DynamicPicMapper extends Mapper<DynamicPic> {

}
