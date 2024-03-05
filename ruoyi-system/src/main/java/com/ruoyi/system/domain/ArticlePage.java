package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class ArticlePage {
    /**
     * 可选排序字段: publish_time、browse_count、comment_count、thumbs_up_count
     */
    String orderBy = "publish_time";
    /**
     * ASC: 升序  DESC: 升序
     */
    String orderType = "DESC";
    /**
     * 文章标签
     */
    Long tagId;
    /**
     * 用户Id
     */
    Long userId;
    /**
     * 用户名称
     */
    String nickName;
    /**
     * 文章标题
     */
    String content;
    /**
     * 文章状态
     */
    Long status;
    /**
     * 页数
     */
    Long pageNum = 1L;
    /**
     * 一页多少条
     */
    Long pageSize = 10L;
}
