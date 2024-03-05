package com.ruoyi.web.controller.filter;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.ruoyi.common.core.domain.AjaxResult.success;
import static com.ruoyi.common.utils.SecurityUtils.checkUserDataScopeUtils;
import static com.ruoyi.common.utils.SecurityUtils.getUserId;

@RestController
@Tag(name = "通知管理")
@RequestMapping("/system/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationUserService notificationUserService;

    /**
     * 查询通知列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询通知列表")
    public AjaxResult selectNotificationByList(NotificationPage notificationPage) {
        return notificationService.selectNotificationByList(notificationPage);
    }

    /**
     * 查询通知列表
     */
    @GetMapping("/{notificationId}")
    @Operation(summary = "查询通知详情")
    public AjaxResult selectNotificationById(@PathVariable Long notificationId) {
        return success(notificationService.selectNotificationById(notificationId));
    }


    @PostMapping("/system")
    @Operation(summary = "添加系统通知")
    public AjaxResult addSystemNotification(@RequestBody Notification notification) {
        notification.setFromUserId(getUserId());
        notification.setNotificationType(1L);
        Long l = notificationService.insertNotification(notification);

        if (l != 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentReplyService commentReplyService;

    @PostMapping("/thumb")
    @Operation(summary = "添加点赞通知")
    public AjaxResult addThumbNotification(@RequestBody Notification notification) {
        notification.setFromUserId(getUserId());
        notification.setNotificationType(2L);
        notification.setContent(notificationThumbVerification(notification));
        Long l = notificationService.insertNotification(notification);

        if (l != 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    @PostMapping("/comment")
    @Operation(summary = "添加评论通知")
    public AjaxResult addCommentNotification(@RequestBody Notification notification) {
        notification.setFromUserId(getUserId());
        notification.setNotificationType(3L);
        notification.setContent(notificationCommentVerification(notification));
        Long l = notificationService.insertNotification(notification);
        if (l != 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    private String notificationThumbVerification(Notification notification) {
        String content;
        if (notification.getArticleId() != null) {
            Articles articles = articleService.selectArticlesById(notification.getArticleId());
            if (articles == null) {
                throw new RuntimeException("文章不存在");
            }
            content = "赞了你的文章";
        } else {
            throw new RuntimeException("文章Id不能为空");
        }

        if (notification.getCommentId() != null) {
            Comment comment = commentService.selectCommentById(notification.getCommentId(), getUserId());
            if (comment == null) {
                throw new RuntimeException("评论不存在");
            }
            content = "赞了你的评论";
        }

        if (notification.getCommentReplyId() != null) {
            CommentReply commentReply = commentReplyService.selectCommentReplyById(notification.getCommentReplyId(), getUserId());
            if (commentReply == null) {
                throw new RuntimeException("评论回复不存在");
            }
            content = "赞了你的回复";
        }
        return content;
    }

    private String notificationCommentVerification(Notification notification) {
        String content;
        if (notification.getArticleId() != null) {
            Articles articles = articleService.selectArticlesById(notification.getArticleId());
            if (articles == null) {
                throw new RuntimeException("文章不存在");
            }
        } else {
            throw new RuntimeException("文章Id不能为空");
        }

        if (notification.getCommentId() != null) {
            Comment comment = commentService.selectCommentById(notification.getCommentId(), getUserId());
            if (comment == null) {
                throw new RuntimeException("评论不存在");
            }
            content = "评论";
        } else{
            throw new RuntimeException("评论Id不能为空");
        }

        if (notification.getCommentReplyId() != null) {
            CommentReply commentReply = commentReplyService.selectCommentReplyById(notification.getCommentReplyId(), getUserId());
            if (commentReply == null) {
                throw new RuntimeException("评论回复不存在");
            }
            content = "回复";
        }
        return content;
    }

    /**
     * 修改通知
     */
    @PutMapping
    @Operation(summary = "修改通知")
    public AjaxResult editNotification(@RequestBody Notification notification) {
        if (checkUserDataScopeUtils(notification.getFromUserId(), "system:notification:edit")) {
            notificationService.updateNotification(notification);
            return success();
        }
        return AjaxResult.error("当前用户无法操作");
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/{notificationIds}")
    @Operation(summary = "批量删除文章")
    public AjaxResult deleteNotification(@PathVariable Long[] notificationIds) {
        if (checkUserDataScopeUtils(getUserId(), "system:notification:remove")) {
            notificationService.deleteNotificationByIds(notificationIds);
            return success();
        }
        return AjaxResult.error();
    }
}
