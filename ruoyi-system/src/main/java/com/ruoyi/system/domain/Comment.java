package com.ruoyi.system.domain;

import lombok.Data;

import java.util.List;

@Data
public class Comment {
    /**
     * 主键
     */
    private Long id;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论用户ID
     */
    private Long commentUserId;
    /**
     * 评论用户名称
     */
    private String commentNickName;
    /**
     * 评论用户头像
     */
    private String commentUserAvatar;
    /**
     * 评论图片
     */
    private List<Image> commentImgList;
    /**
     * 评论回复内容
     */
    private List<CommentReply> commentReplyList;
    /**
     * 当前用户是否点赞该评论
     */
    private int isThumbedByCommentCurrentUser;
    /**
     * 评论文章ID
     */
    private Long commentArticlesId;
    /**
     * 评论回复量
     */
    private Long replyCount;
    /**
     * 评论点赞数
     */
    private Long commentThumbsUpCount;
    /**
     * 评论时间
     */
    private String commentTime;
}
