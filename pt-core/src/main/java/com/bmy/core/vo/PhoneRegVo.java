package com.bmy.core.vo;

import com.bmy.core.validator.Phone;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName SmsRegisterVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/18 上午12:57
 **/
@Data
public class PhoneRegVo {

    @Phone
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度为6-20位。")
    private String password;

    @NotEmpty(message = "验证码不能为空")
    private String code;

}
