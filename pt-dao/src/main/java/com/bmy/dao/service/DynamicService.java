package com.bmy.dao.service;

import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.dto.DynamicDTO;

import java.util.List;

public interface DynamicService {

    /**
     * 查询所有动态
     * @return
     */
    public List<Dynamic> selectAll(Long uid);

    /**
     * 发布一条新动态
     * @param dynamicVo
     * @return
     */
    public boolean pubDynamic(DynamicDTO dynamicVo, UserInfo userInfo);

    /**
     * 根据用户id查询所有动态
     * @param uid
     * @return
     */
    public List<Dynamic> selectByUid(Long uid);

    /**
     * 根据动态主键id删除动态
     * @param id
     * @return
     */
    public boolean deleteById(Long id,UserInfo userInfo);


}
