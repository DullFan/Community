package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.ArticlePage;
import com.ruoyi.system.domain.Articles;
import com.ruoyi.system.domain.Browse;
import com.ruoyi.system.domain.PageArticles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文章接口
 */
public interface ArticleService {

    /**
     * 新增文章
     *
     * @param articles 文章
     * @return 结果
     */
    public AjaxResult
    insertArticles(Articles articles);


    public Long
    reviewArticles(Long[] ids,Long status);

    /**
     * 查询文章
     */
    public Articles selectArticlesById(Long id);

    /**
     * 查询话题列表
     */
    public AjaxResult selectArticlesByList(ArticlePage articlePage);

    /**
     * 删除文章
     *
     * @param articlesId 文章主键
     * @return
     */
    public AjaxResult deleteArticlesById(Long articlesId, HttpServletRequest request) throws Exception;

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public AjaxResult deleteArticlesByIds(Long[] ids);

    /**
     * 修改文章
     *
     * @param articles 文章
     * @return 结果
     */
    public AjaxResult updateArticles(Articles articles);

    /**
     * 查询所有条目
     */
    public Long selectTotalNum(ArticlePage articlePage);

    /**
     * 点赞文章
     *
     * @param quoteId
     * @return
     * @throws Exception
     */
    AjaxResult thumbsArticles(Long quoteId) throws Exception;

    /**
     * 浏览文章
     *
     * @param quoteId
     * @return
     */
    AjaxResult browseArticles(Long quoteId) throws Exception;
}
