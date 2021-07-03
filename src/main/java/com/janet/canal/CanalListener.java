package com.janet.canal;

/**
 * @Description TODO
 * @Date 2021/6/30
 * @Author Janet
 */
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.janet.mq.MQMessageActivityModel;
import com.janet.mq.Product;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@CanalEventListener
public class CanalListener {
    private static final String EXCHANGE_NAME = "materialActivityTopicExchange";

    @Autowired
    private Product product;

    @ListenPoint(schema = "cloud", table = {"student"}) //设置监控的数据库名自己表名
    public void activityUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
//        System.err.println("数据发生变化");
//        rowData.getBeforeColumnsList().forEach((c) -> System.err.println("更改前数据: " + c.getName() + " :: " + c.getValue()));
//
//        rowData.getAfterColumnsList().forEach((c) -> System.err.println("更改后数据: " + c.getName() + " :: " + c.getValue()));

        Long activityId = null;

        if (eventType == CanalEntry.EventType.UPDATE){
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if ("ACTIVITY_ID".equals(column.getName())) {
                    if (StringUtils.isNotBlank(column.getValue())) {
                        activityId = Long.valueOf(column.getValue()); //保存活动ID
                    }
                }
                if ("CONFIRM_TYPE".equals(column.getName()) && column.getUpdated()) {
                    if (StringUtils.isNotBlank(column.getValue()) && "1".equals(column.getValue())) { //满足条件，给 rabbitMQ 发送消息
                        MQMessageActivityModel message = new MQMessageActivityModel();
                        message.setSchemaName("cloud");
                        message.setTableName("student");
                        message.setActivityId(activityId);

                        product.sendMessage(EXCHANGE_NAME,"material.confirmed.activity",message);
                        System.out.println("canal 监控表，student 表 CONFIRM_TYPE 字段变为1，发送消息。活动ID：" + activityId);

                    }

                }

            }

        }
    }

}