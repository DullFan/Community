package com.ruoyi.system.domain;

import lombok.Data;

import java.util.List;

@Data
public class Notification {
    /**
     * 通知Id
     */
    Long notificationId;

    /**
     * 通知类型，
     */
    Long notificationType;

    /**
     * 通知内容
     */
    String content;

    /**
     * 发布者Id
     */
    Long fromUserId;

    /**
     * 通知发布者名称
     */
    String fromUserName;

    /**
     * 文章Id
     */
    Long articleId;

    /**
     * 文章标题
     */
    String articleTitle;

    /**
     * 评论Id
     */
    Long commentId;

    /**
     * 评论内容
     */
    String commentContent;

    /**
     * 评论Id
     */
    Long commentReplyId;

    /**
     * 评论内容
     */
    String commentReplyContent;

    /**
     * 创建时间
     */
    String createTime;

    /**
     * 用户接收集合
     */
    List<NotificationUser> notificationUserList;
}
