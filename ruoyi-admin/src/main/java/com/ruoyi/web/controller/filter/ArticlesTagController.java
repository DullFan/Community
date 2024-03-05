package com.ruoyi.web.controller.filter;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.ArticlesTag;
import com.ruoyi.system.service.ArticleTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "文章标签操作")
@RequestMapping("/system/article/tag")
public class ArticlesTagController {

    @Autowired
    ArticleTagService articleTagService;

    /**
     * 新增文章标签
     */
    @PostMapping
    @Operation(summary = "新增文章标签")
    @PreAuthorize("@ss.hasPermi('system:article_tag:add')")
    public AjaxResult postArticlesTag(@RequestBody ArticlesTag articlesTag) {
        try {
            if (articlesTag.getStatus() == 0) {
                return AjaxResult.error("请选择标签状态");
            }
            articleTagService.insertArticlesTag(articlesTag);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("请选择标签状态");
        }
    }

    /**
     * 修改文章标签
     */
    @PutMapping
    @Operation(summary = "修改文章标签")
    @PreAuthorize("@ss.hasPermi('system:article_tag:edit')")
    public AjaxResult putArticlesTag(@RequestBody ArticlesTag articlesTag) {
        articleTagService.updateArticlesTag(articlesTag);
        return AjaxResult.success();
    }

    /**
     * 批量删除文章标签
     */
    @DeleteMapping("/{articlesTagIds}")
    @Operation(summary = "批量删除文章标签")
    @PreAuthorize("@ss.hasPermi('system:article_tag:remove')")
    public AjaxResult deleteArticlesTagByIds(@PathVariable Long[] articlesTagIds) {
        articleTagService.deleteArticlesTagByIds(articlesTagIds);
        return AjaxResult.success();
    }
}
