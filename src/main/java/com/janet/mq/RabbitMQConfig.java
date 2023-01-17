package com.janet.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${exchangeName}")
    private String exchangeName;

    @Value("${queueName}")
    private String queueName;  //队列名称

    @Value("${bindingKey}")
    private String bindingKey;

    @Value("${deadExchangeName}")
    private String deadExchangeName; //死信交换机

    @Value("${deadQueueName}")
    private String deadQueueName; //死信队列

    @Value("${deadBindingKey}")
    private String deadBindingKey; //死信队列绑定路由键

    //声明交换机(topic类型)
    @Bean("materialActivityTopicExchange")
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    //声明队列以及绑定改队列的死信交换机
    @Bean("materialActivityTopicQueue")
    public Queue itemQueue(){
        Map<String, Object> args = new HashMap<>(2);
        // 声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", deadExchangeName);
        // 声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", deadBindingKey);
        //队列过期时间
//        args.put("x-message-ttl", 60000);//1min

        return QueueBuilder.durable(queueName).withArguments(args).build();
    }

    //绑定队列和交换机
    @Bean
    public Binding itemQueueExchange(@Qualifier("materialActivityTopicQueue") Queue queue,
                                     @Qualifier("materialActivityTopicExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(bindingKey).noargs();
    }

    //声明死信交换机（direct类型）
    @Bean("deadMaterialActivityTopicExchange")
    public Exchange deadExchange(){
        return ExchangeBuilder.directExchange(deadExchangeName).durable(true).build();
    }

    //声明死信队列
    @Bean("deadMaterialActivityTopicQueue")
    public Queue deadQueue(){
        return QueueBuilder.durable(deadQueueName).build();
    }

    //死信交换机和死信队列绑定
    @Bean
    public Binding deadQueueExchange(@Qualifier("deadMaterialActivityTopicQueue") Queue deadQueue,
                                     @Qualifier("deadMaterialActivityTopicExchange") Exchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(deadBindingKey).noargs();
    }

}