package com.bmy.core.vo;

import com.bmy.core.validator.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName PhonePwdLoginVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午12:38
 **/
@Data
@ApiModel("手机密码登录数据模型")
public class PhonePwdLoginVo {

    @Phone
    @ApiModelProperty(value = "phone",name = "手机号",required = true)
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "password",name = "密码",required = true)
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度为6-20位。")
    private String password;

}
