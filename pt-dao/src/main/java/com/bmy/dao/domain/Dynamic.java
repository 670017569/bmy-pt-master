package com.bmy.dao.domain;


import com.bmy.dao.domain.ex.DynamicPic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Data
@Table(name = "dynamic")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dynamic {

    //动态id
    @Id
    @Column
    private Long id;

    //用户id
    @Column
    private Long uid;

    //定位信息
    @Column
    private Integer region;

    //内容
    @Column
    private String content;

    //点赞数
    @Column
    private Integer praised;

    //点击数
    @Column
    private Integer clicks;

    //评论数
    @Column
    private Integer comments;

    //发布时间
    @Column(name = "publish_time")
    private Timestamp publishTime;

    //是否逻辑删除 0:未删除  1：已删除
    @Column
    private Integer deleted;

    private List<DynamicPic> pics;



}
