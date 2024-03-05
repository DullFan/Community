package com.ruoyi.system.service;

import com.ruoyi.system.domain.ArticlePage;
import com.ruoyi.system.domain.ArticleTagPage;
import com.ruoyi.system.domain.ArticlesTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章标签接口
 */
public interface ArticleTagService {
    /**
     * 查询标签
     *
     * @param tagId 标签ID
     */
    public ArticlesTag selectArticlesTagById(Long tagId);

    /**
     * 查询标签列表
     */
    public List<ArticlesTag> selectArticlesTagByList(ArticleTagPage articleTagPage);

    /**
     * 新增文章标签
     *
     * @param articlesTag 文章标签
     * @return 结果
     */
    public Long insertArticlesTag(ArticlesTag articlesTag);

    /**
     * 修改文章标签
     *
     * @param articlesTag 文章内容
     * @return 结果
     */
    public int updateArticlesTag(ArticlesTag articlesTag);

    /**
     * 删除文章标签信息
     *
     * @param tagId 文章标签Id
     * @return 结果
     */
    public Long deleteArticlesTagById(Long tagId);

    /**
     * 批量删除文章标签
     *
     * @param tagIds 需要删除的文章Id主键集合
     * @return 结果
     */
    public int deleteArticlesTagByIds(Long[] tagIds);

    /**
     * 查询所有条目
     */
    public Long selectTotalNum(ArticleTagPage articleTagPage);

    /**
     * 判断标签是否可用
     */
    public Boolean isTagEnabled(Long tagId);
}
