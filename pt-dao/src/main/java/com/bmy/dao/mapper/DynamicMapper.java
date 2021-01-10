package com.bmy.dao.mapper;

import com.bmy.dao.domain.Dynamic;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

@Repository
public interface DynamicMapper extends Mapper<Dynamic> {

    @Select("select * from dynamic where uid = #{uid} and deleted = 0")
    @Results(id = "DYNAMIC_PICS_COLLECTION",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "region",property = "region"),
            @Result(column = "content",property = "content"),
            @Result(column = "praised",property = "praised"),
            @Result(column = "clicks",property = "clicks"),
            @Result(column = "comments",property = "comments"),
            @Result(column = "deleted",property = "deleted"),
            @Result(column = "publish_time",property = "publishTime"),
            @Result(column = "id",property = "pics", javaType = List.class,many = @Many(select = "com.bmy.dao.mapper.PicMapper.selectAllPicByDynId",fetchType = FetchType.EAGER))
    })
    public List<Dynamic> selectAllDynamicByUid(Long uid);

    @Select("select * from dynamic order by publish_time desc")
    @ResultMap(value = "DYNAMIC_PICS_COLLECTION")
    public List<Dynamic> selectAll();

}
