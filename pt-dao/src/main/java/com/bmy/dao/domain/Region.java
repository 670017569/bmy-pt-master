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
@Table(name = "region")
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @Id
    @Column
    private Integer id;

    @Column
    private Integer pid;

    @Column
    private String name;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Integer level;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Integer hot;

}
