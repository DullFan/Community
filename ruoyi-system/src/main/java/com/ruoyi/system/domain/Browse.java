package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Browse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    private Long id;
    /**
     * 浏览用户Id
     */
    private Long browseUserId;
    /**
     * 浏览文章Id
     */
    private Long browseArticlesId;
    /**
     * 浏览时间
     */
    private String createTime;
}