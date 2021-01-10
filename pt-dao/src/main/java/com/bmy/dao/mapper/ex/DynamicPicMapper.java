package com.bmy.dao.mapper.ex;


import com.bmy.dao.domain.Pic;
import com.bmy.dao.domain.ex.DynamicPic;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DynamicPicMapper extends Mapper<DynamicPic> {

    @Select("select *\n" +
            "from dynamic_pic\n" +
            "left join pic\n" +
            "on pic.id = dynamic_pic.pic_id\n" +
            "where dyn_id = #{dynId}")
    public List<Pic> selectAllPicByDynId(Long dynId);

}
