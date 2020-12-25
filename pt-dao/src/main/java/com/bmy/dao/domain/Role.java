package com.bmy.dao.domain;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "role")
public class Role implements Serializable {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long id;

    /**
     * 父节点id
     */
    @Column
    private Long parentId;

    /**
     * 角色名称
     */
    @Column
    private String name;

    /**
     * 角色描述
     */
    @Column
    private String description;

    /**
     * 角色创建时间
     */
    @Column
    private Date created;

    /**
     * 更新时间
     */
    @Column
    private Date updated;

    private List<Permission> permissions;

}
