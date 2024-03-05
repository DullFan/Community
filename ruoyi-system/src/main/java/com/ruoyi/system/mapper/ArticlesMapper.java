package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ArticlePage;
import com.ruoyi.system.domain.Articles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章Mapper
 */
public interface ArticlesMapper {
    /**
     * 新增话题
     *
     * @param articles 话题
     * @return
     */
    public Long insertArticles(Articles articles);


    /**
     * 查询文章
     *
     * @param id 文章主键
     * @return 文章
     */
    public Articles selectArticlesById(Long id);

    /**
     * 查询文章列表
     */
    public List<Articles> selectArticlesByList(@Param("articlePage")ArticlePage articlePage, @Param("userId")Long userId, @Param("pageNum")Long pageNum);

    /**
     * 删除文章
     *
     * @param id 文章主键
     * @return
     */
    public Long deleteArticlesById(Long id);

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Long deleteArticlesByIds(Long[] ids);

    /**
     * 修改文章
     *
     * @param articles 文章
     * @return 结果
     */
    public Long updateArticles(Articles articles);

    public Long updateArticlesStatus(@Param("ids")Long[] ids,@Param("status")Long status,@Param("userId")Long userId);

    /**
     * 查询所有条目
     */
    public Long selectTotalNum(@Param("articlePage")ArticlePage articlePage);
}
