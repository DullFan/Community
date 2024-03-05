package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Notification;
import com.ruoyi.system.domain.NotificationPage;
import com.ruoyi.system.domain.NotificationUser;
import com.ruoyi.system.mapper.NotificationMapper;
import com.ruoyi.system.mapper.NotificationUserMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.NotificationService;
import com.ruoyi.system.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.SecurityUtils.checkUserDataScopeUtils;
import static com.ruoyi.common.utils.SecurityUtils.getUserId;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationUserMapper notificationUserMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public AjaxResult selectNotificationByList(NotificationPage notificationPage) {
        if (notificationPage.getToUserId() == null) {
            notificationPage.setToUserId(getUserId());
        }
        // 只能查询本人
        if (!checkUserDataScopeUtils(notificationPage.getToUserId(), "system:notification:query")) {
            return AjaxResult.error("当前用户无权限查询");
        }
        notificationPage.sqlPageNum();
        AjaxResult success = AjaxResult.success(notificationMapper.selectNotificationByList(notificationPage));
        success.put("totalNum", notificationMapper.selectTotalNum(notificationPage));
        success.put("pageNum", notificationPage.getPageNum());
        success.put("pageSize", notificationPage.getPageSize());
        return success;
    }

    @Override
    public Notification selectNotificationById(Long notificationId) {
        Notification notification = notificationMapper.selectNotificationById(notificationId);
        System.out.println(checkUserDataScopeUtils(getUserId(), "system:notification:query"));
        if (checkUserDataScopeUtils("system:notification:query")) {
            return notification;
        }
        List<NotificationUser> notificationUserList = notification.getNotificationUserList();
        NotificationUser currentUserNotification = null;

        for (NotificationUser notificationUser : notificationUserList) {
            if (Objects.equals(notificationUser.getToUserId(), getUserId())) {
                currentUserNotification = notificationUser;
                break; // 找到当前用户的信息后停止遍历
            }
        }

        if (currentUserNotification != null) {
            // 清除原始列表中的所有元素
            notificationUserList.clear();
            // 只添加当前用户的NotificationUser对象
            notificationUserList.add(currentUserNotification);
            return notification;
        } else {
            // 如果没有找到当前用户的信息，则抛出异常
            throw new RuntimeException("当前用户无法查询");
        }
    }



    @Override
    public Long insertNotification(Notification notification) {
        if (notification.getNotificationUserList().isEmpty()) {
            throw new RuntimeException("缺少接收者Id参数");
        }

        // 判断用户是否存在
        for (NotificationUser notificationUser : notification.getNotificationUserList()) {
            SysUser sysUser = userMapper.selectUserById(notificationUser.getToUserId());
            if (sysUser == null) {
                throw new RuntimeException(notificationUser.getToUserId() + "当前用户不存在");
            }
        }

        if (notification.getNotificationType() == 1L && !checkUserDataScopeUtils("system:notification:add")) {
            throw new RuntimeException("当前用户无系统权限");
        }

        if (notification.getNotificationUserList().size() != 1 && notification.getNotificationType() != 1) {
            throw new RuntimeException("接收者只能有一名");
        }

        if(StringUtils.isNull(notification.getContent())){
            throw new RuntimeException("通知内容不能为空");
        }


        notificationMapper.insertNotification(notification);
        // 去重
        List<NotificationUser> collect = notification.getNotificationUserList().stream().distinct().collect(Collectors.toList());
        for (NotificationUser notificationUser : collect) {
            notificationUser.setNotificationId(notification.getNotificationId());
        }
        notificationUserMapper.insertNotificationUsers(collect);
        Long[] array = collect.stream().map(NotificationUser::getToUserId)
                .toArray(Long[]::new);
        webSocketServer.sendToAloneClient(array,notification.getContent());
        return 1L;
    }

    @Override
    public int updateNotification(Notification notification) {
        // 先获取数据库中的数据
        Notification notification2 = notificationMapper.selectNotificationById(notification.getNotificationId());
        if (notification2 == null) {
            throw new RuntimeException("当前通知不存在");
        }
        if (notification2.getNotificationType() == 1L) {
            notification.setFromUserId(null);
            notification.setNotificationType(null);
            notification.setCommentReplyId(null);
            notification.setCommentId(null);
            notificationMapper.updateNotification(notification);
            // 判断是否为空,为空则不修改当前接收者,不为空则修改
            List<NotificationUser> notificationUsers = notification2.getNotificationUserList();
            if (!notificationUsers.isEmpty()) {
                Long[] ids = notificationUsers.stream()
                        .map(NotificationUser::getId)
                        .toArray(Long[]::new);
                notificationUserMapper.deleteNotificationUserByIds(ids);
                // 去重
                List<NotificationUser> collect = notification.getNotificationUserList().stream().distinct().collect(Collectors.toList());
                for (NotificationUser notificationUser : collect) {
                    notificationUser.setNotificationId(notification2.getNotificationId());
                }
                notificationUserMapper.insertNotificationUsers(collect);
            }
            return 1;
        }
        throw new RuntimeException("只能修改系统通知");
    }

    @Override
    public Long deleteNotificationById(Long notificationId) {
        return notificationMapper.deleteNotificationById(notificationId);
    }

    @Override
    public int deleteNotificationByIds(Long[] notificationIds) {
        // 判断是否有权限删除通知
        for (Long notificationId : notificationIds) {
            System.out.println(notificationId);
            Notification notification = notificationMapper.selectNotificationById(notificationId);
            if(notification != null){
                if (!checkUserDataScopeUtils(notification.getFromUserId(), "system:notification:query")) {
                    throw new RuntimeException(notificationId + "当前无操作权限");
                }
            } else {
                throw new RuntimeException(notificationId + "通知不存在");
            }
        }
        return notificationMapper.deleteNotificationByIds(notificationIds);
    }

    @Override
    public Long selectTotalNum(NotificationPage notificationPage) {
        return notificationMapper.selectTotalNum(notificationPage);
    }
}
