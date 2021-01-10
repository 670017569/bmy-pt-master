//package com.bmy.wx.rabbitmq.controller;
//
//import com.bmy.core.constant.R;
//import com.bmy.core.constant.Response;
//import com.bmy.wx.rabbitmq.send.PraiseSender;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * 消息路由规则：四种模式（topic、direct、fanout、header）
// * topic：根据绑定关键字通配符规则匹配、比较灵活
// * direct：默认，根据routingKey完全匹配，好处是先匹配再发送
// * fanout：不需要指定routingkey，相当于群发,广播模式
// * header：不太常用，可以自定义匹配规则
// *
// * @author cm_wang
// */
//@RestController
//@RequestMapping("auth")
//@Api(tags = "rabbitmq测试")
//public class MessageController {
//
//    @Autowired
//    private PraiseSender topicSender;
//
//
//    /**
//     * topic 模式
//     *
//     * @return
//     */
//    @PostMapping("/sendTopic")
//    public R sendTopic(@RequestBody JSONObject obj) {
//        topicSender.sendTopic(obj);
//        return R.success(Response.SIGN_IN_SUCCESS);
//    }
//
//
//}
