package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Notification;
import com.ruoyi.system.domain.NotificationPage;
import com.ruoyi.system.domain.NotificationUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知Mapper
 */
public interface NotificationUserMapper {
    /**
     * 查询通知
     */
    public Notification selectNotificationById(@Param("notificationUserId") Long id);

    /**
     * 新增通知
     */
    public Long insertNotificationUser(NotificationUser notificationUser);


    /**
     * 批量新增
     */
    public Long insertNotificationUsers(@Param("notificationUsers") List<NotificationUser> notificationUser);

    /**
     * 修改通知
     */
    public int updateNotificationUser(NotificationUser notificationUser);

    /**
     * 删除通知信息
     */
    public Long deleteNotificationUserById(@Param("notificationUserId") Long notificationUserId);

    /**
     * 批量删除通知
     */
    public Long deleteNotificationUserByIds(@Param("notificationUserIds") Long[] notificationUserIds);
}
