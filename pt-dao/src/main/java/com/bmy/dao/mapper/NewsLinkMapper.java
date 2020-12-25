package com.bmy.dao.mapper;

import com.bmy.dao.domain.WxOfficialNews;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @InterfaceName NewsLinkMapper
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 下午7:21
 **/
@Repository
public interface NewsLinkMapper extends Mapper<WxOfficialNews> {
}
