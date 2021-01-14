package com.bmy.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    //评论id
    @Id
    @Column
    private Long id;


    //用户id
    @Column
    private Long uid;

    @Column
    private Long toUid;

    //动态id
    @Column
    private Long dynId;

    //内容
    @Column
    private String content;

    //逻辑删除
    @Column
    private Boolean deleted;

    //发表时间
    @Column
    private Date publishTime;


}
