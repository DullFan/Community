package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class Thumbs {
    /**
     * 点赞表ID
     * */
    private Long id;

    /**
     * 点赞者id
     * */
    private Long userId;

    /**
     * 点赞话题id
     * */
    private Long quoteId;

    /**
     * 点赞类型. 1:文章点赞,1:文章评论点赞,3:文章评论回复点赞
     * */
    private Long type;

    /**
     * 创建时间
     */
    private String createTime;
}
