package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class PageArticles {
    private String orderBy = "publish_time";
    private String orderType = "DESC";
    private Long pageNum = 1L;
    private Long pageSize = 10L;
}
