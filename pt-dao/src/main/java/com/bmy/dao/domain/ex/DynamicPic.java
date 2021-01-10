package com.bmy.dao.domain.ex;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 动态图片关联实体
 */
@Data
@Builder
@Table(name = "dynamic_pic")
public class DynamicPic {

    @Id
    @Column
    private Long id;

    @Column
    private Long dynId;

    @Column
    private Long picId;


}
