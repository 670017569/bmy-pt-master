package com.bmy.dao.domain.ex;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dynId;

    @Column
    private String url;



}
