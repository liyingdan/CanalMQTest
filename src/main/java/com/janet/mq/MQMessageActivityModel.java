package com.janet.mq;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2021/6/23
 * @Author Janet
 */

public class MQMessageActivityModel implements Serializable {
    private String schemaName; //数据库名称

    private String tableName; //canal监控表的名字

    private Long activityId; //活动Id

    private String confirmOrder; //审批批次号

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getConfirmOrder() {
        return confirmOrder;
    }

    public void setConfirmOrder(String confirmOrder) {
        this.confirmOrder = confirmOrder;
    }

    public MQMessageActivityModel() {
    }

    @Override
    public String toString() {
        return "MQMessageActivityModel{" +
                "schemaName='" + schemaName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", activityId=" + activityId +
                ", confirmOrder='" + confirmOrder + '\'' +
                '}';
    }
}
