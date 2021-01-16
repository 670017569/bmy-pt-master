package com.bmy.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "permission")
public class Permission implements Serializable {

    /**
     * id主键
     */
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long id;

    /**
     * 父节点id
     */
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    /**
     * 权限名称
     */
    @Column
    private String name;

    /**
     * 权限请求路径
     */
    @Column
    private String url;

    /**
     * 权限描述
     */
    @Column
    private String description;

    /**
     * 创建时间
     */
    @Column
    private Date created;

    /**
     * 更新时间
     */
    @Column
    private Date updated;

}
