package com.bmy.dao.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Builder
@Table(name = "comment")
public class Comment {

    //评论id
    @Id
    @Column
    private Long id;

    //评论父id
    @Column
    private Long pid;

    //用户id
    @Column
    private Long uid;

    //动态id
    @Column
    private Long dynId;

    //内容
    @Column
    private String content;

    //逻辑删除
    @Column
    private Boolean deleted;

}
