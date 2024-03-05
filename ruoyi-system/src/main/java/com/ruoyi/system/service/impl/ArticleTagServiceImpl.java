package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ArticleTagPage;
import com.ruoyi.system.domain.ArticlesTag;
import com.ruoyi.system.mapper.ArticlesTagMapper;
import com.ruoyi.system.service.ArticleTagService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ruoyi.common.utils.SecurityUtils.getLoginUser;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {
    @Autowired
    ArticlesTagMapper articlesTagMapper;

    @Override
    public ArticlesTag selectArticlesTagById(Long tagId) {
        return articlesTagMapper.selectArticlesTagById(tagId);
    }

    @Override
    public List<ArticlesTag> selectArticlesTagByList(ArticleTagPage articleTagPage) {
        return articlesTagMapper.selectArticlesTagByList(articleTagPage);
    }

    @Override
    public Long insertArticlesTag(ArticlesTag articlesTag) {
        articlesTag.setCreateBy(getLoginUser().getUserId());
        return articlesTagMapper.insertArticlesTag(articlesTag);
    }

    @Override
    public int updateArticlesTag(ArticlesTag articlesTag) {
        return articlesTagMapper.updateArticlesTag(articlesTag);
    }

    @Override
    public Long deleteArticlesTagById(Long tagId) {
        return articlesTagMapper.deleteArticlesTagById(tagId);
    }

    @Override
    public int deleteArticlesTagByIds(Long[] tagIds) {
        return articlesTagMapper.deleteArticlesTagByIds(tagIds);
    }

    @Override
    public Long selectTotalNum(ArticleTagPage articleTagPage) {
        return articlesTagMapper.selectTotalNum(articleTagPage);
    }

    @Override
    public Boolean isTagEnabled(Long tagId) {
        ArticlesTag articlesTag = articlesTagMapper.selectArticlesTagById(tagId);
        if(StringUtils.isNull(articlesTag)){
            return false;
        }
        return articlesTag.getStatus() != 2;
    }
}
