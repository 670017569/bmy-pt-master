package com.bmy.dao.mapper;

import com.bmy.dao.domain.WxUserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @InterfaceName WxUserInfoMapper
 * @Description 微信用户信息实体
 * @Author potato
 * @Date 2020/12/15 下午9:10
 **/
@Repository
public interface WxUserInfoMapper extends Mapper<WxUserInfo> {

    @Override
    WxUserInfo selectByPrimaryKey(Object key);

//    @Select("select * from wx_userinfo where uid in (select fuid from follow where follow.uid = #{uid} and canceled = 0)")
    List<WxUserInfo> selectFollows(Long uid);

//    @Select("select * from wx_userinfo where uid in (select follow.uid from follow where follow.fuid = #{uid} and canceled = 0)")
    List<WxUserInfo> selectFans(Long uid);

//    @Select("select count(1) from bmy.`follow` where canceled = 0 and `uid`=#{uid} and `fuid`=#{fuid} and `uid` in (select `fuid` from bmy.`follow` where `uid`=#{fuid} and canceled = 0)")
    Boolean selectMutual(@Param("uid") Long uid,@Param("fuid") Long fuid);
}
