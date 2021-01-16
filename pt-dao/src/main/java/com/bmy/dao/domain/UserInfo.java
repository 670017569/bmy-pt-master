package com.bmy.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userinfo")
public class UserInfo implements Serializable {

    @Id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @Column
    private String username;
    @Column
    private String intro;
    @Column
    private Long follows;
    @Column
    private Long fans;
    @Column
    private Long praised;
    @Column
    private Long dynamics;
    @Column
    private Long topics;
    @Column
    private Long brick;
    @Column
    private Long integration;
    @Column
    private Long signTimes;
    @Column(name = "sign_today")
    private Boolean signToday;
    @Column
    private Date lastSignTime;


}
