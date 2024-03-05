package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class NotificationUser {
    /**
     * Id
     */
    Long id;
    /**
     * 通知Id
     */
    Long notificationId;

    /**
     * 通知接收者Id
     */
    Long toUserId;

    /**
     * 通知接收者名称
     */
    String toUserName;

    /**
     * 阅读状态。0未读，1已读
     */
    Long status;

    /**
     * 查看时间
     */
    Long viewTime;
}
