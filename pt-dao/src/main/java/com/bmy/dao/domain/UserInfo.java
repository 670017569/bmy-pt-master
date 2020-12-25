package com.bmy.dao.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/25 下午2:13
 **/
@Data
@Table(name = "userinfo")
public class UserInfo implements Serializable {

    @Id
    @Column
    private Long uid;

    @Column
    private String intro;
    @Column
    private Integer follows;
    @Column
    private Integer fans;
    @Column
    private Integer praised;
    @Column
    private Integer dynamics;
    @Column
    private Integer topics;
    @Column
    private Integer brick;
    @Column
    private Integer integration;
    @Column
    private Integer signTimes;
    @Column
    private Boolean signToday;
    @Column
    private Date lastSignTime;


}
