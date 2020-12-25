package com.bmy.dao.mapper;

import com.bmy.dao.domain.WxOfficialNews;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @InterfaceName WxOfficialNewsMapper
 * @Description TODO
 * @Author potato
 * @Date 2020/12/24 下午7:23
 **/
@Repository
public interface WxOfficialNewsMapper extends Mapper<WxOfficialNews> {

    @Update("truncate table official_news")
    int truncateTable();

}
