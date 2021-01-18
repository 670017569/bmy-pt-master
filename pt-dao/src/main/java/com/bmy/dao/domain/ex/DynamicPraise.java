package com.bmy.dao.domain.ex;

import com.bmy.dao.domain.WxUserInfo;
import com.bmy.dao.vo.WxUserVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dynamic_praise")
public class DynamicPraise {

    @Id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dynId;

    @Column(name = "praise_time")
    private Date praiseTime;

    @Column(name = "canceled")
    private Boolean canceled;

}
