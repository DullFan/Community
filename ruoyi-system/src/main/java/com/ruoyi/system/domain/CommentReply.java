package com.ruoyi.system.domain;

import lombok.Data;

import java.util.List;

@Data
public class CommentReply {
    /**
     * 主键
     */
    private Long id;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论回复用户ID
     */
    private Long commentReplyUserId;
    /**
     * 评论回复用户名称
     */
    private String commentReplyNickName;
    /**
     * 评论回复用户头像
     */
    private String commentReplyUserAvatar;
    /**
     * 评论回复用户ID
     */
    private Long replyUserId;
    /**
     * 被回复者名称
     */
    private String replyNickName;
    /**
     * 评论ID
     */
    private Long commentId;
    /**
     * 评论点赞数
     */
    private Long commentReplyThumbsUpCount;
    /**
     * 当前用户是否点赞该评论回复
     */
    private int isThumbedByCommentReplyCurrentUser;
    /**
     * 评论图片
     */
    private List<Image> commentReplyImgList;
    /**
     * 评论时间
     */
    private String replyTime;
}

