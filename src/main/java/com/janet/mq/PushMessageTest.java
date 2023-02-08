package com.janet.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janet Li
 * @createTime 2023/1/10
 */

@RestController
public class PushMessageTest {
    @Autowired
    private Product product;


    @PostMapping("/sendMessage")
    public void sendMessage(long id) {
        MQMessageActivityModel message = new MQMessageActivityModel();
        message.setSchemaName("cloud");
        message.setTableName("student");
        message.setActivityId(id);

        product.sendMessage(message);
    }

    @PostMapping("/sendActivityModelMessage")
    public void sendActivityModelMessage(long id) {
        MQMessageActivityModel message = new MQMessageActivityModel();
        message.setSchemaName("cloud");
        message.setTableName("student");
        message.setActivityId(id);

        product.sendActivityModelMessage(message);
    }

    @PostMapping("/sendStringMessage")
    public void sendStringMessage(String msg) {
        product.sendStringMessage(msg);
    }

    /**
     * 发送有过期时间的消息
     * @param msg 发送的消息
     * @param expiration 过期时间，单位为毫秒
     */
    @PostMapping("/sendTtlMessage")
    public void sendTtlMessage(String msg, String expiration) {
        product.sendTtlMessage(msg, expiration);
    }

}
