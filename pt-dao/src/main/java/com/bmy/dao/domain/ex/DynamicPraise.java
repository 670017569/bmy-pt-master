package com.bmy.dao.domain.ex;

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
@Table(name = "dynamic_praise")
public class DynamicPraise {

    @Id
    @Column
    private Long id;

    @Column
    private Long uid;

    @Column
    private Long dynId;

    @Column(name = "praise_time")
    private Date praiseTime;

    @Column(name = "canceled")
    private Boolean canceled;


}
