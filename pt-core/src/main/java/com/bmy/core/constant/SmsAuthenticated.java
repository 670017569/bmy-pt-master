package com.bmy.core.constant;

import java.lang.annotation.*;

/**
 * 注解一个API方法表示需要拦截并检查其短信验证码
 * @Created by f9miao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SmsAuthenticated {

    // 短信验证码的用途(默认为登陆)
    String action() default "login";

    public static class ACTION {
        public static final String LOGIN = "login";         // 登陆
        public static final String REGISTER = "register";   // 注册
        public static final String RESET = "reset";         // 重置密码
        public static final String ACTIVITY = "activity";   // 活动
    }

}
