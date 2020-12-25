package com.bmy.admin.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WxOfficialToken
 * @Description TODO
 * @Author potato
 * @Date 2020/12/24 下午3:30
 **/
@Data
public class WxOfficialToken implements Serializable {

    private String access_token;

    private String expires_in;

}
