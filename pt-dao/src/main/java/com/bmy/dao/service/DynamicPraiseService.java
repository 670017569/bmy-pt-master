package com.bmy.dao.service;

import com.bmy.dao.domain.UserInfo;

public interface DynamicPraiseService {

    /**
     * 完成给动态点赞
     * @param dynId
     * @return
     */
    public boolean praise(UserInfo userInfo,Long dynId);

    /**
     * 取消点赞
     * @param dynId
     * @return
     */
    public boolean delPraise(UserInfo userInfo, Long dynId);

    /**
     * 设置动态与用户关系
     * @param uid
     * @param dynId
     * @return
     */
    public boolean setIsPraised(Long uid,Long dynId);


}
