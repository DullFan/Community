package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NotificationPage;
import com.ruoyi.system.domain.Notification;

import java.util.List;

/**
 * 通知接口
 */
public interface NotificationService {
    /**
     * 查询通知列表
     */
    public AjaxResult selectNotificationByList(NotificationPage notificationPage);

    /**
     * 查询通知详情
     */
    public Notification selectNotificationById(Long notificationId);

    /**
     * 新增通知
     *
     * @param notification 通知
     * @return 结果
     */
    public Long insertNotification(Notification notification);

    /**
     * 修改通知
     *
     * @param notification 内容
     * @return 结果
     */
    public int updateNotification(Notification notification);

    /**
     * 删除通知信息
     *
     * @param notificationId 通知Id
     * @return 结果
     */
    public Long deleteNotificationById(Long notificationId);

    /**
     * 批量删除通知
     *
     * @param notificationIds 需要删除的Id主键集合
     * @return 结果
     */
    public int deleteNotificationByIds(Long[] notificationIds);

    /**
     * 查询所有条目
     */
    public Long selectTotalNum(NotificationPage articleTagPage);
}
