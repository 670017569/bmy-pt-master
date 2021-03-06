package com.bmy.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName SmsRegisterVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/18 上午12:57
 **/
@Data
@Builder
@ApiModel("手机号注册DTO")
public class PhoneRegDTO {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String code;

}
