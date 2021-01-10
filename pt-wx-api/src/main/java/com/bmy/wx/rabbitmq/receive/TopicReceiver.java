//package com.bmy.wx.rabbitmq.receive;
//
//import com.bmy.wx.rabbitmq.config.RabbitMQConfig;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * topic模式消费者
// * @author cm_wang
// */
//@Component
//public class TopicReceiver {
//
//    // queues是指要监听的队列的名字
//    @RabbitListener(queues = RabbitMQConfig.QUEUE_PRAISE_DYNAMIC)
//    public void receiveTopic(JSONObject obj) {
//        log.info("receiveTopic收到消息:" + obj.toString());
//    }
//}
