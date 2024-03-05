package com.ruoyi.system.service;


import com.ruoyi.system.domain.Browse;

import java.util.List;

/**
 * 浏览文章接口
 */
public interface BrowseService {

    /**
     * 查询浏览
     */
    public Browse selectBrowseById(Long id);

    /**
     * 通过浏览id和用户id查询点赞
     */
    public Browse selectBrowseByBrowse(Browse browse);

    /**
     * 查询浏览列表
     */
    public List<Browse> selectBrowseList(Browse browse);

    /**
     * 新增浏览
     */
    public Long insertBrowse(Browse browse);

    /**
     * 修改浏览
     */
    public Long updateBrowse(Browse browse);

    /**
     * 批量删除浏览
     */
    public Long deleteBrowseByIds(Long[] ids);

    /**
     * 删除浏览信息
     */
    public Long deleteBrowseById(Long id);
}
