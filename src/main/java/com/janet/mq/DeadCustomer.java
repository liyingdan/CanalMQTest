package com.janet.mq;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2021/6/30
 * @Author Janet
 */
@Component
public class DeadCustomer {

    @RabbitListener(queues = "${deadQueueName}")
    public void receiveDeadMessage(String msg, Channel channel, Message message) throws IOException {

        System.out.println("监听到死信队列中的消息------message:" + msg + "，date：" + new Date());

        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("消费者成功死信队列中的消息");

    }
}
