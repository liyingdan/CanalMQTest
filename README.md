# CanalMQTest
SpringBoot 整合canal以及RabbitMQ（注解形式）

项目中监控数据库（cloud）的表（student），一旦表中的数据有变化并满足条件，则发送消息到 RabbitMQ 中的队列，消费者监听到队列中的消息，再把消息拿出来做其他的业务操作。

文档：https://blog.csdn.net/weixin_44210965/article/details/117070658
