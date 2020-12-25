package com.bmy.dao.mapper;

import com.bmy.dao.domain.WxUserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @InterfaceName WxUserInfoMapper
 * @Description 微信用户信息实体
 * @Author potato
 * @Date 2020/12/15 下午9:10
 **/
@Repository
public interface WxUserInfoMapper extends Mapper<WxUserInfo> {
}
