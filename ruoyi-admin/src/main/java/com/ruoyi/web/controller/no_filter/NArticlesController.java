package com.ruoyi.web.controller.no_filter;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.ArticlePage;
import com.ruoyi.system.service.ArticleService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "文章查询")
@RequestMapping("/filterN/article")
public class NArticlesController {
    @Autowired
    ArticleService articleService;

    @Autowired
    ISysUserService userService;

    @Autowired
    TokenService tokenService;

    /**
     * 查询文章列表
     */
    @GetMapping("/selectArticlesByList")
    @Operation(summary = "查询文章列表")
    public AjaxResult selectArticlesByList(ArticlePage articlePage) {
        return articleService.selectArticlesByList(articlePage);
    }

    @GetMapping("/{articlesId}")
    @Operation(summary = "查询文章详情")
    public AjaxResult selectArticlesById(@PathVariable Long articlesId) {
        return AjaxResult.success(articleService.selectArticlesById(articlesId));
    }
}
