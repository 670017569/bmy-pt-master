package com.bmy.dao.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "pic")
public class Pic {

    @Id
    @Column
    private Long id;

    @Column
    private String url;

    @Column
    private String thumb;

}
