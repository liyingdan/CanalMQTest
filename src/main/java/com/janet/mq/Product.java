package com.janet.mq;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description 生产者发送消息
 * @Date 2021/6/30
 * @Author Janet
 */

@Component
public class Product {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${exchangeName}")
    private String exchange;

    @Value("${bindingKey}")
    private String routKey;

    /**
     * 情况1：消息是传字节数组
     * rabbitMQ111监听了消息
     */
    public void sendMessage(MQMessageActivityModel msg) {
        //sendMessage，msg：MQMessageActivityModel(schemaName=cloud, tableName=student, activityId=123, confirmOrder=null)
        System.out.println("sendMessage，msg：" + msg + "，date：" + new Date());
        byte[] bytes = SerializationUtils.serialize(msg);
        rabbitTemplate.convertAndSend(exchange, routKey, bytes);

    }

    /**
     * 情况2：消息是 MQMessageActivityModel对象
     * rabbitMQ222监听了消息
     */
    public void sendActivityModelMessage(MQMessageActivityModel msg) {
        //sendActivityModelMessage，msg：MQMessageActivityModel(schemaName=cloud, tableName=student, activityId=123, confirmOrder=null)
        System.out.println("sendActivityModelMessage，msg：" + msg + "，date：" + new Date());
        rabbitTemplate.convertAndSend(exchange, routKey, msg);
    }

    /**
     * 情况3：消息是字符串
     * rabbitMQ333 监听了消息
     */
    public void sendStringMessage(String msg) {
        //sendStringMessage，msg：Janet
        System.out.println("sendStringMessage，msg：" + msg);
        rabbitTemplate.convertAndSend(exchange, routKey, msg);

    }

    /**
     * 发送有过期时间的消息
     * @param msg 发送的消息
     * @param expiration 过期时间，单位为毫秒
     */
    public void sendTtlMessage(String msg, String expiration) {
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setExpiration(expiration); //单位为毫秒
            return message;
        };
        System.out.println("sendTtlMessage，msg：" + msg + "，date：" + new Date()+ "，expiration：" + expiration);
        rabbitTemplate.convertAndSend(exchange, routKey, msg, messagePostProcessor);
    }

}
