package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Image;

import java.util.List;

/**
 * 图片Service接口
 */
public interface ImageService {
    /**
     * 查询图片
     *
     * @param quoteId 引用ID
     * @return 图片
     */
    public List<Image> selectImageById(Long quoteId, Long type);

    /**
     * 新增图片
     *
     * @param image 图片信息
     * @return 结果
     */
    public Long insertImage(Image image);

    /**
     * 新增多张图片
     *
     * @param urls 图片地址集合
     * @param image 图片信息集合
     * @return 结果
     */
    public AjaxResult insertImages(List<String> urls, Image image);

    /**
     * 删除图片信息
     *
     * @param id 图片主键
     * @return 结果
     */
    public Long deleteImageById(Long id);

    /**
     * 删除引用图片信息
     *
     * @param quoteId 文章ID
     * @return 结果
     */
    public Long deleteImageByArticlesId(Long quoteId,Long type);


    public Long deleteImageByArticlesIds(Long[] quoteIds,Long type);
}
