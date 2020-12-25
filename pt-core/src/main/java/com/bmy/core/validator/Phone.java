package com.bmy.core.validator;

import javax.validation.Payload;

/**
 * 手机号校验注解
 */
public @interface Phone {

    /**
     * 校验不通过的message
     */
    String message() default "请输入正确的手机号";

    /**
     * 分组校验
     */
    Class<?>[] groups() default { };


    Class<? extends Payload>[] payload() default { };

}
