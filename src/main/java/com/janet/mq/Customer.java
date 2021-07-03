package com.janet.mq;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description TODO
 * @Date 2021/6/25
 * @Author Janet
 */

@Component
public class Customer {

    @RabbitListener(queues = "materialActivityTopicQueue")
    public void receiveMessage(byte[] msg, Channel channel, Message message) throws IOException {

        MQMessageActivityModel msg1 = (MQMessageActivityModel) SerializationUtils.deserialize(msg);
        System.out.println("rabbitMQ----------消费者接收到消息-----------------------message:" + msg1);

        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false); //拒绝消息
        System.out.println("rabbitMQ----------消费者成功消费消息");

    }
}
