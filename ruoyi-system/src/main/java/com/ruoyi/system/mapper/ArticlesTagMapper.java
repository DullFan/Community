package com.ruoyi.system.mapper;


import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ArticlePage;
import com.ruoyi.system.domain.ArticleTagPage;
import com.ruoyi.system.domain.ArticlesTag;
import com.ruoyi.system.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import static com.ruoyi.common.core.domain.AjaxResult.error;

public interface ArticlesTagMapper {
    /**
     * 查询标签
     *
     * @param tagId 标签ID
     */
    public ArticlesTag selectArticlesTagById(Long tagId);

    /**
     * 查询标签列表
     */
    public List<ArticlesTag> selectArticlesTagByList(@Param("articleTagPage")ArticleTagPage articleTagPage);

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
    public Long selectTotalNum(@Param("articleTagPage") ArticleTagPage articleTagPage);
}
