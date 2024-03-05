package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Notification;
import com.ruoyi.system.domain.NotificationPage;
import com.ruoyi.system.domain.NotificationUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知接口
 */
public interface NotificationUserService {
    /**
     * 查询通知
     */
    public Notification selectNotificationById(@Param("notificationUserId") Long id);

    /**
     * 新增通知
     */
    public Long insertNotificationUser(NotificationUser notificationUser);

    public Long insertNotificationUsers(List<NotificationUser> notificationUsers);

    /**
     * 修改通知
     *
     */
    public int updateNotificationUser(NotificationUser notificationUser);

    /**
     * 删除通知信息
     */
    public Long deleteNotificationUserById(Long notificationUserId);

    /**
     * 批量删除通知
     */
    public Long deleteNotificationUserByIds(Long[] notificationUserIds);
}
