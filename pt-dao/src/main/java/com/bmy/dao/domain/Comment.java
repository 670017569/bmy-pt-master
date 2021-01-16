package com.bmy.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    //用户id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long toUid;

    //动态id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dynId;

    //父级评论id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pid;

    //内容
    @Column
    private String content;

    //逻辑删除
    @Column
    private Boolean deleted;

    //发表时间
    @Column(name = "publish_time")
    private Date publishTime;

    @Transient
    private WxUserInfo wxUserInfo;

    @Transient
    private WxUserInfo toWxUser;

    @Transient
    private Integer sonComment;

}
