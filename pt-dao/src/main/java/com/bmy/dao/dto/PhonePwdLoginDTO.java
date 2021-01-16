package com.bmy.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName PhonePwdLoginVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午12:38
 **/
@Data
@ApiModel("手机密码登录数据模型")
public class PhonePwdLoginDTO {

    @ApiModelProperty(value = "phone",name = "手机号",required = true)
    @NotNull
    private String phone;

    @NotNull
    @ApiModelProperty(value = "password",name = "密码",required = true)
    private String password;

}
