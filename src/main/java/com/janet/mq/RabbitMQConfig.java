package com.janet.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2021/6/25
 * @Author Janet
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "materialActivityTopicExchange";  //交换机名称

    public static final String QUEUE = "materialActivityTopicQueue";  //队列名称

    public static final String DEAD_LETTER_EXCHANGE = "deadMaterialActivityTopicExchange"; //死信交换机

    public static final String DEAD_LETTER_QUEUE = "deadMaterialActivityTopicQueue"; //死信队列

    public static final String BINDING_KEY = "material.confirmed.activity";

    public static final String DEAD_LETTER_BINDING_KEY = "material.confirmed.activity.dead"; //死信队列绑定路由键

    //声明交换机(topic类型)
    @Bean("materialActivityTopicExchange")
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //声明队列以及绑定改队列的死信交换机
    @Bean("materialActivityTopicQueue")
    public Queue itemQueue(){
        Map<String, Object> args = new HashMap<>(2);
        // 声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // 声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_BINDING_KEY);
        //过期时间
        args.put("x-message-ttl", 60000);//1min

        return QueueBuilder.durable(QUEUE).withArguments(args).build();
    }

    //绑定队列和交换机
    @Bean
    public Binding itemQueueExchange(@Qualifier("materialActivityTopicQueue") Queue queue,
                                     @Qualifier("materialActivityTopicExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BINDING_KEY).noargs();
    }

    //声明死信交换机（direct类型）
    @Bean("deadMaterialActivityTopicExchange")
    public Exchange deadExchange(){
        return ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    //声明死信队列
    @Bean("deadMaterialActivityTopicQueue")
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //死信交换机和死信队列绑定
    @Bean
    public Binding deadQueueExchange(@Qualifier("deadMaterialActivityTopicQueue") Queue deadQueue,
                                     @Qualifier("deadMaterialActivityTopicExchange") Exchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_LETTER_BINDING_KEY).noargs();
    }


}