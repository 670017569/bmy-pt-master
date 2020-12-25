package com.bmy.core.validator.impl;

import com.bmy.core.validator.Phone;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName PhoneValidator
 * @Description TODO
 * @Author potato
 * @Date 2020/12/18 上午1:30
 **/
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.isEmpty(phone)){

            //获取默认提示信息
            String defaultConstraintMessageTemplate = constraintValidatorContext.getDefaultConstraintMessageTemplate();
            System.out.println("default message :" + defaultConstraintMessageTemplate);
            //禁用默认提示信息
            constraintValidatorContext.disableDefaultConstraintViolation();
            //设置提示语
            constraintValidatorContext.buildConstraintViolationWithTemplate("手机号格式错误").addConstraintViolation();
            String regex = "^1(3|4|5|7|8|9)\\d{9}$";
            return phone.matches(regex);
        }
        return true;
    }
}