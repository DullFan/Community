package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class ArticleTagPage {
    /**
     * 标签状态
     */
    Long status;
    /**
     * 页数
     */
    Long pageNum = 1L;
    /**
     * 页数
     */
    Long newPageNum = 1L;
    /**
     * 一页多少条
     */
    Long pageSize = 10L;
}
