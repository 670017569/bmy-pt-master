package com.bmy.core.vo;

import com.bmy.core.validator.Phone;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName PhoneLoginVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午12:36
 **/
@Data
public class PhoneSmsLoginVo {

    @Phone
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    private String code;

}
