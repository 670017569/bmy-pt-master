package com.bmy.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User  implements Serializable {


    /**
     * 主键id
     */
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long id;

    /**
     * 用户名
     */
    @Column
    private String username;
    /**
     * 用户状态 0为正常
     */
    @Column
    private Integer status;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 邮箱
     */
    @Column
    private String email;

    /**
     * 手机号码
     */
    @Column
    private String phone;

    /**
     * 微信openid
     */
    @Column
    private String wxOpenid;

    /**
     * 逻辑删除 0：未删除 1：已删除
     */
    @Column
    private Boolean deleted;

    private List<Role> roles;

}
