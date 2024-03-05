package com.ruoyi.web.controller.filter;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.Articles;
import com.ruoyi.system.domain.Browse;
import com.ruoyi.system.domain.Image;
import com.ruoyi.system.service.ArticleService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ImageService;
import com.ruoyi.web.controller.common.CommonController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Tag(name = "文章操作")
@RequestMapping("/system/article")
public class ArticlesController {
    @Autowired
    ArticleService articleService;

    @Autowired
    ISysUserService userService;

    @Autowired
    TokenService tokenService;

    /**
     * 发布话题
     */
    @PostMapping
    @Operation(summary = "发布文章")
    @PreAuthorize("@ss.hasPermi('system:article:add')")
    public AjaxResult postArticles(@RequestBody Articles articles) throws Exception {
        return articleService.insertArticles(articles);
    }

    /**
     * 文章审查
     */
    @PostMapping("/review/{articlesIds}")
    @Operation(summary = "审查文章")
    @PreAuthorize("@ss.hasPermi('system:article:review')")
    public AjaxResult postReview(@PathVariable Long[] articlesIds) {
        Long l = articleService.reviewArticles(articlesIds,1L);
        if (l == null) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success();
        }
    }

    /**
     * 文章审查
     */
    @PostMapping("/unReview/{articlesIds}")
    @Operation(summary = "未审查文章")
    @PreAuthorize("@ss.hasPermi('system:article:review')")
    public AjaxResult postUnReview(@PathVariable Long[] articlesIds) {
        Long l = articleService.reviewArticles(articlesIds,2L);
        if (l == null) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success();
        }
    }

    /**
     * 文章审查
     */
    @PostMapping("/ban/{articlesIds}")
    @Operation(summary = "封禁文章")
    @PreAuthorize("@ss.hasPermi('system:article:review')")
    public AjaxResult postBan(@PathVariable Long[] articlesIds) {
        Long l = articleService.reviewArticles(articlesIds,3L);
        if (l == null) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success();
        }
    }

    /**
     * 修改文章
     */
    @PutMapping
    @Operation(summary = "修改文章")
    public AjaxResult updateArticles(@RequestBody Articles articles) throws Exception {
        return articleService.updateArticles(articles);
    }

    @Autowired
    CommonController commonController;

    @Autowired
    ImageService imageService;

    /**
     * 删除文章
     */
    @DeleteMapping("/{articlesIds}")
    @Operation(summary = "批量删除文章")
    public AjaxResult deleteArticlesIds(@PathVariable Long[] articlesIds) {
        // 删除文件
        for (Long articlesId : articlesIds) {
            List<Image> images = imageService.selectImageById(articlesId, 1L);
            List<String> imgList = images.stream().map(Image::getImageUrl)
                    .collect(Collectors.toList());
            commonController.deleteFiles(imgList);
        }
        return articleService.deleteArticlesByIds(articlesIds);
    }

    /**
     * 点赞文章
     */
    @PostMapping("/thumbs")
    @Operation(summary = "点赞文章")
    public AjaxResult updateArticles(Long quoteId) throws Exception {
        return articleService.thumbsArticles(quoteId);
    }

    /**
     * 浏览话题
     */
    @PostMapping("/browse")
    @Operation(summary = "浏览话题")
    public AjaxResult browse(Long quoteId) throws Exception {
        return articleService.browseArticles(quoteId);
    }
}
