package com.bmy.core.vo;

import lombok.Data;

/**
 * @ClassName PhoneLoginVo
 * @Description TODO
 * @Author potato
 * @Date 2020/12/26 下午12:36
 **/
@Data
public class PhoneSmsLoginVo {

    private String phone;

    private String code;

}
