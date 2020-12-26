package com.bmy.auth.service;

import cn.hutool.core.util.RandomUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import com.bmy.auth.config.SmsProperties;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Random;

/**
 * @ClassName SmsService
 * @Description TODO
 * @Author potato
 * @Date 2020/12/17 下午6:29
 **/
@Service
public class SmsService {

    Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Resource
    private RedisTemplate<String ,Object> redisTemplate;

    @Resource
    private SmsProperties smsProperties;

    /**
     * 添加短信到缓存，有效时间5分钟
     * @param phone
     * @param code
     * @param action
     */
    public void smsStore(String phone, String code, String action){
        String key = String.format("sms%s::%s", action, phone);
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(10));
    }

    /**
     * 查询短信验证码, 没查到返回空串
     */
    public String smsQuery(String phone, String action){

        String key = String.format("sms%s::%s", action, phone);

        logger.info(action+" "+phone);

        Long n = (Long) redisTemplate.opsForValue().get(key);

        if (null != n){
           return n.toString();
        }else{
            return "";
        }
    }

    /**
     * 短信验证码校验
     * @param phone
     * @param code
     * @param action
     * @return
     */
    public boolean smsCheck(String phone, String code, String action){
        logger.info("开始从缓存中查询:{}",phone);
        String queryCode = smsQuery(phone, action);
        logger.info("从缓存中查询验证码:{}",queryCode);
        if(queryCode.equals("") || !queryCode.equals(code)){
            logger.info("未查询到验证码");
            return false;
        }
        return true;
    }

    /**
     * 发送模板短信
     */
    public SendSMSResult sendTemplateSMS(String phone, String code, String action) {
        SMSClient client = new SMSClient(smsProperties.getAppSecret(), smsProperties.getAppId());
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phone)
                .setTempId(1)
                .addTempPara("code", code)
                .build();
        try {
            SendSMSResult res = client.sendTemplateSMS(payload);
            logger.debug(res.toString());
            this.smsStore(phone, code, action); // 加入redis缓存
            return res;
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            return null;
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
            return null;
        }
    }


    /**
     * todo
     * 检查电话号码是否有效
     */
    public void isPhone(String phone){
        if (phone.length()!=11){
            throw new UnAuthorizedException(Response.INVALID_PHONE);
        }
    }

    /**
     * 生成6位随机验证码
     */
    public String generateCode(){
        int n = RandomUtil.randomInt(100000,999999);
        return Integer.toString(n);
    }

}
