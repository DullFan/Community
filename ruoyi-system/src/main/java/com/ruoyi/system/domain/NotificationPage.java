package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class NotificationPage {
    /**
     * 通知状态
     */
    Long status;
    /**
     * 通知Id
     */
    Long notificationId;
    /**
     * 通知类型
     */
    Long notificationType;
    /**
     * 发布者Id
     */
    Long fromUserId;
    /**
     * 接收者Id
     */
    Long toUserId;
    /**
     * 通知内容
     */
    String content;
    /**
     * 文章Id
     */
    Long articleId;
    /**
     * 评论Id
     */
    Long commentId;
    /**
     * 页数
     */
    Long pageNum = 1L;
    /**
     * 给数据库使用的页数
     */
    Long newPageNum = 1L;
    /**
     * 一页多少条
     */
    Long pageSize = 10L;

    /**
     * 设置数据库使用的页数
     */
    public void sqlPageNum() {
        newPageNum = (getPageNum() - 1) * getPageSize();
    }
}
