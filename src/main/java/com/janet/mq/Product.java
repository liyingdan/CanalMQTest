package com.janet.mq;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2021/6/30
 * @Author Janet
 */

@Component
public class Product {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routKey, MQMessageActivityModel msg){
        System.out.println("生产者发送消息"+msg);
        byte[] bytes = SerializationUtils.serialize(msg);
        rabbitTemplate.convertAndSend(exchange, routKey, bytes);

    }
}
