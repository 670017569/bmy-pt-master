package com.bmy.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName PhonePwdLoginVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午12:38
 **/
@Data
@ApiModel("手机密码登录数据模型")
public class PhonePwdLoginVo {

    @ApiModelProperty(value = "phone",name = "手机号",required = true)
    private String phone;

    @ApiModelProperty(value = "password",name = "密码",required = true)
    private String password;

}
