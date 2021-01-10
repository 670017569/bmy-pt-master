package com.bmy.dao.mapper;

import com.bmy.dao.domain.Pic;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *图片mapper
 */
public interface PicMapper extends Mapper<Pic> {

    @Select("select *\n" +
            "from dynamic_pic\n" +
            "left join pic\n" +
            "on pic.id = dynamic_pic.pic_id\n" +
            "where dyn_id = #{dynId}")
    public List<Pic> selectAllPicByDynId(Long dynId);


}
