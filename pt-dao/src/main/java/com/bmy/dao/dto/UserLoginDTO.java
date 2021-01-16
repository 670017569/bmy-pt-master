package com.bmy.dao.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户登录DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("验证码")
    private String code;

}
