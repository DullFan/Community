package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Notification;
import com.ruoyi.system.domain.NotificationUser;
import com.ruoyi.system.mapper.NotificationUserMapper;
import com.ruoyi.system.service.NotificationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationUserServiceImpl implements NotificationUserService {
    @Autowired
    NotificationUserMapper notificationUserMapper;

    @Override
    public Notification selectNotificationById(Long id) {
        return notificationUserMapper.selectNotificationById(id);
    }

    @Override
    public Long insertNotificationUser(NotificationUser notificationUser) {
        return notificationUserMapper.insertNotificationUser(notificationUser);
    }

    @Override
    public Long insertNotificationUsers(List<NotificationUser> notificationUsers) {
        return notificationUserMapper.insertNotificationUsers(notificationUsers);
    }

    @Override
    public int updateNotificationUser(NotificationUser notificationUser) {
        return notificationUserMapper.updateNotificationUser(notificationUser);
    }

    @Override
    public Long deleteNotificationUserById(Long notificationUserId) {
        return notificationUserMapper.deleteNotificationUserById(notificationUserId);
    }

    @Override
    public Long deleteNotificationUserByIds(Long[] notificationUserIds) {
        return notificationUserMapper.deleteNotificationUserByIds(notificationUserIds);
    }
}
