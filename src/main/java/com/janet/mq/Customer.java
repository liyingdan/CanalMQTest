package com.janet.mq;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @Description 监听队列的消费者
 */
//@Component
//@RabbitListener(queues = "${queueName}")
public class Customer {

    /**
     * 情况1：消息是传字节数组
     *
     * @param msg
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitHandler
    public void receiveMessage(byte[] msg, Channel channel, Message message) throws IOException {
        MQMessageActivityModel msg1 = (MQMessageActivityModel) SerializationUtils.deserialize(msg);
        MQMessageActivityModel msg2 = (MQMessageActivityModel) SerializationUtils.deserialize(message.getBody());
        //rabbitMQ111----------消费者接收到消息，msg1:MQMessageActivityModel(schemaName=cloud, tableName=student, activityId=123, confirmOrder=null)，msg2:MQMessageActivityModel(schemaName=cloud, tableName=student, activityId=123, confirmOrder=null)
        System.out.println("rabbitMQ111----------消费者接收到消息，msg1:" + msg1 + "，msg2:" + msg2);

        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false); //拒绝消息
        System.out.println("rabbitMQ111----------消费者成功消费消息");
    }

    /**
     * 情况2：消息是 MQMessageActivityModel对象
     */
    @RabbitHandler
    public void receiveMessage(MQMessageActivityModel msg, Channel channel, Message message) throws IOException {
        MQMessageActivityModel msg1 = (MQMessageActivityModel) SerializationUtils.deserialize(message.getBody());
        //rabbitMQ222----------消费者接收到消息，msg1:MQMessageActivityModel(schemaName=cloud, tableName=student, activityId=123, confirmOrder=null)，msg:MQMessageActivityModel(schemaName=cloud, tableName=student, activityId=123, confirmOrder=null)
        System.out.println("rabbitMQ222----------消费者接收到消息，msg1:" + msg1 + "，msg:" + msg);

        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("rabbitMQ222----------消费者成功消费消息");
    }


    /**
     * 情况3：消息是字符串
     */
    @RabbitHandler
    public void receiveMessage(String msg, Channel channel, Message message) throws IOException {

        String msg1 = new String(message.getBody(), "UTF-8");
        //rabbitMQ333----------消费者接收到消息，msg1:Janet，msg:Janet
        System.out.println("rabbitMQ333----------消费者接收到消息，msg1:" + msg1 + "，msg:" + msg + "，date：" + new Date());

        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("rabbitMQ333----------消费者成功消费消息");

    }

//    @RabbitHandler
//    public void receiveMessage(String msg, Channel channel, Message message) throws IOException {
//        System.out.println("普通消费者消费者接收到消息，msg:" + msg + "，date：" + new Date());
//
//        // 手动确认消息
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        System.out.println("普通消费者成功消费消息");
//
//    }
}