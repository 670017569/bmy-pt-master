package com.bmy.dao.service;

public interface DynamicPraiseService {

    /**
     * 完成给动态点赞
     * @param uid
     * @param dynId
     * @return
     */
    public boolean praise(Long uid,Long dynId);

    /**
     * 取消点赞
     * @param uid
     * @param dynId
     * @return
     */
    public boolean delPraise(Long uid,Long dynId);

}
