package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class ArticlesTag {
    /**
     * Id
     */
    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 标签详情
     */
    private String description;
    /**
     * 状态
     */
    private Long status;
    /**
     * 创建者Id
     */
    private Long createBy;
    /**
     * 创建者名称
     */
    private String createByName;
    /**
     * 创建时间
     */
    private String createTime;
}
