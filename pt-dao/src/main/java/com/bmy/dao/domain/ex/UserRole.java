package com.bmy.dao.domain.ex;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName UserRole
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 上午1:33
 **/
@Data
@Builder
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long rid;

}
