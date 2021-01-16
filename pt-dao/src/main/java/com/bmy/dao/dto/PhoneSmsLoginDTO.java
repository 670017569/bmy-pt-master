package com.bmy.dao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName PhoneLoginVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午12:36
 **/
@Data
@ApiModel("手机短信登录数据模型")
public class PhoneSmsLoginDTO {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;

}
