package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.ArticlesMapper;
import com.ruoyi.system.mapper.ArticlesTagMapper;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.common.core.domain.AjaxResult.error;
import static com.ruoyi.common.core.domain.AjaxResult.success;
import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 文章接口
 */
@Service
public class ArticlesServiceImpl implements ArticleService {
    @Autowired
    ArticlesMapper articlesMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private BrowseService browseService;

    @Autowired
    private ThumbsService thumbsService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ImageService imageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertArticles(Articles articles) {
        if (articles.getTagId() == 0L) {
            return error("文章标签不能为空");
        }
        if (StringUtils.isBlank(articles.getContent())) {
            return error("文章内容不能为空");
        }
        if (articleTagService.isTagEnabled(articles.getTagId())) {
            // 获取用户信息
            LoginUser loginUser = getLoginUser();
            // 设置作者
            articles.setUserId(loginUser.getUserId());
            articles.setStatus(2L);
            articlesMapper.insertArticles(articles);
            // 插入评论图片地址
            Image image = new Image();
            image.setQuoteId(articles.getId());
            image.setType(1L);

            if(articles.getArticleImgList() != null){
                List<String> collect = articles.getArticleImgList().stream()
                        .map(Image::getImageUrl)
                        .collect(Collectors.toList());
                imageService.insertImages(collect, image);
            }

            // 更新用户文章数量
            SysUser newUser = new SysUser();
            newUser.setUserId(loginUser.getUserId());
            newUser.setArticlesCount(1L);
            userService.updateUser(newUser);
            return success();
        }
        return error("标签不存在或已被停用");
    }

    @Override
    public Long reviewArticles(Long[] ids,Long status) {
        for (Long id : ids) {
            Articles articles = selectArticlesById(id);
            if (StringUtils.isNull(articles)) {
                throw new RuntimeException("文章不存在");
            }
        }
        return articlesMapper.updateArticlesStatus(ids,status,getUserId());
    }

    @Override
    public Articles selectArticlesById(Long id) {
        return articlesMapper.selectArticlesById(id);
    }

    @Override
    public AjaxResult selectArticlesByList(ArticlePage articlePage) {
        LoginUser sysUser;
        Long userId;
        try {
            sysUser = getLoginUser();
            userId = sysUser.getUserId();
            if (!checkUserDataScopeUtils("system:article:review")) {
                articlePage.setStatus(1L);
            }
        } catch (Exception e) {
            userId = 0L;
            articlePage.setStatus(1L);
        }
        long pageNum = (articlePage.getPageNum() - 1) * articlePage.getPageSize();
        List<Articles> articles = articlesMapper.selectArticlesByList(
                articlePage,
                userId
                , pageNum);
        AjaxResult success = success(articles);
        success.put("totalNum", articlesMapper.selectTotalNum(articlePage));
        success.put("pageNum", pageNum);
        success.put("pageSize", articlePage.getPageSize());
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteArticlesById(Long articlesId, HttpServletRequest request) {
        // 获取用户信息
        SysUser sysUser = getLoginUser().getUser();
        Articles articles = selectArticlesById(articlesId);
        if (StringUtils.isNull(articles)) {
            return error("文章不存在");
        }
        if (checkUserDataScopeUtils(articles.getUserId(), "system:article:remove")) {
            articlesMapper.deleteArticlesById(articlesId);
            // 更新用户文章数量
            sysUser.setUserId(sysUser.getUserId());
            sysUser.setArticlesCount(-1L);
            userService.updateUser(sysUser);
            return success();
        }
        return error("用户没有操作权限");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteArticlesByIds(Long[] ids) {
        for (Long id : ids) {
            Articles articles = selectArticlesById(id);
            if (StringUtils.isNull(articles)) {
                return error("文章不存在");
            }
            if (checkUserDataScopeUtils(articles.getUserId(), "system:article:remove")) {
                // 更新用户文章数量
                SysUser sysUser = new SysUser();
                sysUser.setUserId(articles.getUserId());
                sysUser.setArticlesCount(-1L);
                userService.updateUser(sysUser);
                imageService.deleteImageByArticlesId(id, 1L);
                articlesMapper.deleteArticlesById(id);
            } else {
                return error("用户没有操作权限");
            }
        }
        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateArticles(@RequestBody Articles articles) {
        Articles articlesById = selectArticlesById(articles.getId());
        if (StringUtils.isNull(articlesById)) {
            return error("文章不存在");
        }
        // 判断是否有审查权限
        if (!hasPermi("system:article:review")) {
            articles.setStatus(null);
        }
        if (checkUserDataScopeUtils(articlesById.getUserId(), "system:article:edit")) {
            articlesMapper.updateArticles(articles);
            System.out.println(articles);
            System.out.println("----->");
            return success();
        }
        return error("用户没有操作权限");
    }

    @Override
    public Long selectTotalNum(ArticlePage articlePage) {
        return articlesMapper.selectTotalNum(articlePage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult thumbsArticles(Long quoteId) {
        AjaxResult result;
        LoginUser loginUser = getLoginUser();
        Thumbs thumbs = new Thumbs();
        thumbs.setUserId(loginUser.getUserId());
        thumbs.setType(1L);
        thumbs.setQuoteId(quoteId);
        // 查询数据库中有没有数据
        Thumbs commThumb = thumbsService.selectThumbsByThumb(thumbs);
        long thumbUpNumber;
        //如果点赞表内无此用户与话题信息，那么就新增，反之删除
        if (StringUtils.isNull(commThumb)) {
            // 获取文章
            Articles articles = selectArticlesById(quoteId);
            if (StringUtils.isNull(articles)) {
                return error("文章不存在");
            }
            thumbsService.insertThumbs(thumbs);
            thumbUpNumber = 1L;
            result = success(true);
        } else {
            thumbsService.deleteThumbsById(commThumb.getId());
            thumbUpNumber = -1L;
            result = success(false);
        }
        Articles upArticles = new Articles();
        upArticles.setId(quoteId);
        upArticles.setThumbsUpCount(thumbUpNumber);
        // 更新文章点赞数量
        updateArticles(upArticles);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult browseArticles(Long quoteId) {
        Browse browse = new Browse();
        browse.setBrowseArticlesId(quoteId);
        AjaxResult result;
        // 获取用户信息
        LoginUser loginUser = getLoginUser();
        browse.setBrowseUserId(loginUser.getUserId());
        Browse commBrowse;
        if (browse.getBrowseArticlesId() != 0) {
            commBrowse = browseService.selectBrowseByBrowse(browse);
        } else {
            return error("文章id不得为空");
        }
        Articles articles = new Articles();
        // 判断是否为空,空就说明没有当前用户没有浏览过该话题
        if (StringUtils.isNull(commBrowse)) {
            browseService.insertBrowse(browse);
            articles.setId(browse.getBrowseArticlesId());
            articles.setBrowseCount(1L);
            updateArticles(articles);
        }
        result = success();
        return result;
    }
}
