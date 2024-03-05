package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ArticlePage;
import com.ruoyi.system.domain.Articles;
import com.ruoyi.system.domain.Notification;
import com.ruoyi.system.domain.NotificationPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知Mapper
 */
public interface NotificationMapper {

    /**
     * 查询通知列表
     */
    public List<Notification> selectNotificationByList(@Param("notificationPage") NotificationPage notificationPage);

    /**
     * 查询通知详情
     */
    public Notification selectNotificationById(@Param("notificationId") Long notificationId);

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
    public Long deleteNotificationById(@Param("notificationId") Long notificationId);

    /**
     * 批量删除通知
     *
     * @param notificationIds 需要删除的Id主键集合
     * @return 结果
     */
    public int deleteNotificationByIds(@Param("notificationIds") Long[] notificationIds);

    /**
     * 查询所有条目
     */
    public Long selectTotalNum(@Param("notificationPage")NotificationPage articleTagPage);
}
