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

/***
 * 用户关注实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "follow")
public class Follow {

    /**
     * PK_id
     */
    @Id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 用户id
     */
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    /**
     * 被关注的用户id
     */
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long fuid;

    /**
     * 是否取消关注
     */
    @Column
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Integer canceled;

    /**
     * 关注时间
     */
    @Column
    private Date createTime;

    /**
     * 是否已读
     */
    @Column
    private Boolean isRead;

    /**
     * 发起该关注的用户信息
     */
    @Transient
    private WxUserInfo wxUserInfo;
}
