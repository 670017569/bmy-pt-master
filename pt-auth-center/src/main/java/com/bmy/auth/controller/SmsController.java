package com.bmy.auth.controller;

import cn.hutool.core.lang.Snowflake;
import cn.jsms.api.SendSMSResult;
import com.bmy.auth.service.SmsService;
import com.bmy.auth.vo.SmsRegisterVo;
import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.core.constant.SmsAuthenticated;
import com.bmy.dao.domain.User;
import com.bmy.dao.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @ClassName SmsController
 * @Description TODO
 * @Author potato
 * @Date 2020/12/17 下午8:37
 **/
@RestController
@RequestMapping("sms")
@Api(tags = "手机短信认证接口")
public class SmsController {

    Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Resource
    private SmsService smsService;

    @Resource
    private Snowflake snowflake;

    @Resource
    private UserMapper userMapper;

    /**
     *
     * @param phone
     * @param action
     * @return
     */
    @RequestMapping(value = "send/code", method = RequestMethod.GET)
    @ApiOperation("发送短信,action字段表示验证码的功能,根据功能的不同会分开存储,目前有login register reset activity,默认值是login")
    public R<Object> send(@RequestParam("phone") String phone,
                          @RequestParam(value = "action",
                          defaultValue = SmsAuthenticated.ACTION.LOGIN) String action){
        SendSMSResult sendSMSResult = smsService.sendTemplateSMS(phone, smsService.generateCode(), action);
        logger.info("短信发送成功:{}",sendSMSResult);
        return new R<>(Response.SMS_SEND_SUCCESS,sendSMSResult);
    }

    @PostMapping("register")
    @ApiOperation("携带短信验证码发起注册")
    public R<Object> register(@RequestBody @Validated SmsRegisterVo smsRegisterVo,
                             @RequestParam(value = "action",
                             defaultValue = SmsAuthenticated.ACTION.LOGIN) String action){
        String phone = smsRegisterVo.getPhone();
        String password = smsRegisterVo.getPassword();
        String code = smsRegisterVo.getCode();

        logger.info("短信验证码注册 ==> 开始 phone:{}",phone);
        User user = null;
        boolean res = smsService.smsCheck(phone, code, action);
        if (res){
            logger.info("验证码正确 注册继续");
            Example example = new Example(User.class);
            example.and()
                    .andEqualTo("phone",phone);
            user = userMapper.selectOneByExample(example);
        }else {
            logger.info("验证码错误 注册失败 <== 结束");
            return new R<>(Response.INVALID_SMS_CODE);
        }
        if (null != user){
            logger.info("该手机号已注册 <== 结束");
            return new R<>(Response.PHONE_HAS_USED);
        }
        try {
            Long uid = snowflake.nextId();
            User newUser = User.builder()
                    .phone(phone)
                    .id(uid)
                    .password(password)
                    .status(0)
                    .build();
            int response = userMapper.insertSelective(newUser);
            if (1 == response){
                User user1 = userMapper.selectByPrimaryKey(uid);
                logger.info("手机号注册成功 <== 结束:{}",user1);
                return new R<>(Response.REGISTER_SUCCESS,user1);
            }else {
                logger.info("手机号注册失败 <== 结束");
                return new R<>(Response.REGISTER_FAILED);
            }
        }catch (Exception e){
            logger.info("手机号注册失败 <== 结束");
            return new R<>(Response.UNKNOWN_ERROR);
        }
    }

}
