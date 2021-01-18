package com.bmy.dao.vo;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class WxUserVO {


    private Long uid;

    private String nickName;

    private String avatar_url;

}
