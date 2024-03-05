package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImageMapper {
    /**
     * 查询图片
     *
     * @param quoteId 引用ID
     * @return 图片
     */
    public List<Image> selectImageById(@Param("quoteId") Long quoteId,@Param("type") Long type);

    /**
     * 新增单张图片
     *
     * @param image 图片信息
     * @return 结果
     */
    public Long insertImage(Image image);

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
    public Long deleteImageByArticlesId(@Param("quoteId") Long quoteId,@Param("type") Long type);

    /**
     * 删除引用图片集合信息
     *
     * @param quoteIds 文章ID集合
     * @return 结果
     */
    public Long deleteImageByArticlesIds(@Param("quoteIds") Long[] quoteIds,@Param("type") Long type);
}
