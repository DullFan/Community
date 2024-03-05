package com.ruoyi.web.controller.no_filter;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.ArticleTagPage;
import com.ruoyi.system.domain.ArticlesTag;
import com.ruoyi.system.service.ArticleTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

@RestController
@Tag(name = "N文章标签操作")
@RequestMapping("/filterN/article/tag")
public class NArticlesTagController {
    @Autowired
    ArticleTagService articleTagService;

    /**
     * 获取文章标签列表
     */
    @GetMapping
    @Operation(summary = "获取文章标签列表")
    public AjaxResult getArticlesTagList(ArticleTagPage articleTagPage) {
        long pageNum = (articleTagPage.getPageNum() - 1) * articleTagPage.getPageSize();
        articleTagPage.setNewPageNum(pageNum);
        List<ArticlesTag> articlesTags = articleTagService.selectArticlesTagByList(articleTagPage);
        AjaxResult success = AjaxResult.success(articlesTags);
        success.put("totalNum",articleTagService.selectTotalNum(articleTagPage));
        success.put("pageNum", articleTagPage.getPageNum());
        success.put("pageSize", articleTagPage.getPageSize());
        return success;
    }

    /**    
     * 获取文章标签
     */
    @GetMapping("/{articlesTagId}")
    @Operation(summary = "获取文章标签")
    public AjaxResult selectArticlesTagById(@PathVariable Long articlesTagId) {
        return AjaxResult.success(articleTagService.selectArticlesTagById(articlesTagId));
    }
}
