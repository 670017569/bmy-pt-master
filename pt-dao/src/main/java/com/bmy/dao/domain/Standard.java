package com.bmy.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "standard")
public class Standard {

    @Id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column
    private Integer regionId;

    @Column
    private String url;

    @Column
    private String title;

    @Column
    private String subtitle;

    @Column
    private Date publishTime;

    @Column
    private Date updateTime;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Boolean deleted;

}
