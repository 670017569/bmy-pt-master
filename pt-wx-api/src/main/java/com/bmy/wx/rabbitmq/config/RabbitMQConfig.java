package com.bmy.wx.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic模式
 *
 * @author cm_wang
 */
@Configuration
public class RabbitMQConfig {

    // 点赞异步配置
    public static final String QUEUE_PRAISE_DYNAMIC = "dynamic.praise";
    public static final String EXCHANGE_PRAISE = "ex-praise";

    @Bean
    public Queue queuePraiseDynamic(){
        return new Queue(QUEUE_PRAISE_DYNAMIC, true);
    }

    @Bean
    public TopicExchange topicExchangePraise(){
        return new TopicExchange(EXCHANGE_PRAISE);
    }
    @Bean
    public Binding bindingLikeDynamic(){
        return BindingBuilder.bind(queuePraiseDynamic()).to(topicExchangePraise()).with(QUEUE_PRAISE_DYNAMIC);
    }


}
