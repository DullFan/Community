package com.ruoyi.system.service;


import com.ruoyi.system.domain.Thumbs;

import java.util.List;

/**
 * 点赞Service接口
 */
public interface ThumbsService
{
    /**
     * 查询点赞
     *
     * @param id 点赞主键
     * @return 点赞
     */
    public Thumbs selectThumbsById(Long id);

    /**
     * 通过话题id和用户id查询点赞
     *
     * @param thumb 点赞
     * @return 点赞
     */
    public Thumbs selectThumbsByThumb(Thumbs thumb);

    /**
     * 查询点赞列表
     *
     * @param thumbs 点赞
     * @return 点赞集合
     */
    public List<Thumbs> selectThumbsList(Thumbs thumbs);

    /**
     * 新增点赞
     *
     * @param thumbs 点赞
     * @return 结果
     */
    public Long insertThumbs(Thumbs thumbs);

    /**
     * 修改点赞
     *
     * @param thumbs 点赞
     * @return 结果
     */
    public Long updateThumbs(Thumbs thumbs);

    /**
     * 批量删除点赞
     *
     * @param ids 需要删除的点赞主键集合
     * @return 结果
     */
    public Long deleteThumbsByIds(Long[] ids);

    /**
     * 删除点赞信息
     *
     * @param id 点赞主键
     * @return 结果
     */
    public Long deleteThumbsById(Long id);
}
