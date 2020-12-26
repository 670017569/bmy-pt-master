package com.bmy.dao.domain.ex;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName RolePermission
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 上午1:34
 **/
@Data
@Table(name = "role_permission")
public class RolePermission {

    @Id
    @Column
    private Long id;

    @Column
    private Long rid;

    @Column
    private Long pid;

}
