package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 图片ID
     */
    private Long id;

    /**
     * 图片路径
     */
    private String imageUrl;

    /**
     * 引用ID 如文章ID、评论ID、评论回复ID
     */
    private Long quoteId;

    /**
     * 引用ID 如文章ID、评论ID、评论回复ID
     */
    private Long type;
}
