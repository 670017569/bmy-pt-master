package com.bmy.dao.service;

/**
 * 关注service
 */
public interface FollowService {

    /**
     * uid关注fuid
     * @param uid
     * @param fuid
     * @return
     */
    boolean follow(Long uid,Long fuid);

    /**
     * uid取消对fuid的关注
     * @param uid
     * @param fuid
     * @return
     */
    boolean delFollow(Long uid , Long fuid);


    /**
     *  设置用户之间的关注关系
     *  true为已关注，false为未关注
     * @param uid
     * @return
     */
    boolean setIsFollowed(Long uid , Long fuid);

}
