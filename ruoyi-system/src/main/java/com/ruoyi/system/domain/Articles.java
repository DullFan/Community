package com.ruoyi.system.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class Articles implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 内容
     */
    @NotBlank(message = "文章内容不能为空")
    private String content = "";
    /**
     * 发布者ID
     */
    private Long userId;
    /**
     * 发布者名称
     */
    private String nickName;
    /**
     * 发布者名称
     */
    private String userAvatar;
    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 是否置顶 0:非置顶 1:置顶
     */
    private Long isTop;
    /**
     * 审核者ID
     */
    private Long reviewerId;
    /**
     * 审核时间
     */
    private String reviewerTime;
    /**
     * 文章状态，0是正常状态，1是审核状态，2是封禁状态
     */
    private Long status;
    /**
     * 文章标签
     */
    private String tag;
    /**
     * 文章标签
     */
    private Long tagId;
    /**
     * 浏览数
     */
    private Long browseCount;
    /**
     * 评论数
     */
    private Long commentCount;
    /**
     * 点赞数量
     */
    private Long thumbsUpCount;
    /**
     * 评论图片
     */
    private List<Image> articleImgList;
    /**
     * 当前用户是否点赞该文章
     */
    private Long isThumbedByCurrentUserArticles;
}