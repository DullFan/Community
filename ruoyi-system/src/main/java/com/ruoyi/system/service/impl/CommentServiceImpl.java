package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Articles;
import com.ruoyi.system.domain.Comment;
import com.ruoyi.system.domain.Image;
import com.ruoyi.system.domain.Thumbs;
import com.ruoyi.system.mapper.CommentMapper;
import com.ruoyi.system.service.ArticleService;
import com.ruoyi.system.service.CommentService;
import com.ruoyi.system.service.ImageService;
import com.ruoyi.system.service.ThumbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.ruoyi.common.core.domain.AjaxResult.error;
import static com.ruoyi.common.core.domain.AjaxResult.success;
import static com.ruoyi.common.utils.SecurityUtils.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArticleService articlesService;

    @Autowired
    private ThumbsService thumbsService;

    @Override
    public Long selectTotalNum(Long commentArticlesId) {
        return commentMapper.selectTotalNum(commentArticlesId);
    }

    @Override
    public Comment selectCommentById(Long id, Long userId) {
        return commentMapper.selectCommentById(id, userId);
    }

    @Override
    public AjaxResult selectCommentList(String orderBy, String orderType, Long commentArticlesId, Long pageNum, Long pageSize) {
        if (StringUtils.isNull(commentArticlesId)) {
            return error("文章Id不存在");
        }
        if (pageNum <= 0) {
            return error("页数不能少于0");
        }
        if (orderBy == null) {
            orderBy = "comment_time";
        }
        if (orderType == null) {
            orderType = "DESC";
        }
        LoginUser loginUser;
        Long userId;
        try {
            loginUser = getLoginUser();
            userId = loginUser.getUserId();
        } catch (Exception e) {
            System.out.println("当前为游客");
            userId = 0L;
        }
        List<Comment> comments = commentMapper.selectCommentList(orderBy, orderType, commentArticlesId, userId, (pageNum - 1) * pageSize, pageSize);
        AjaxResult success = success(comments);
        success.put("pageNum", pageNum);
        success.put("pageSize", pageSize);
        success.put("totalNum", commentMapper.selectTotalNum(commentArticlesId));
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertComment(Comment comment, List<String> uploadImageList) {
        Long commentArticlesId = comment.getCommentArticlesId();
        if (StringUtils.isNull(commentArticlesId)) {
            return error("文章id不得为空");
        }
        Articles articlesById = articlesService.selectArticlesById(commentArticlesId);
        if(articlesById != null){
            // 更新用户信息
            LoginUser loginUser = getLoginUser();
            Articles articles = new Articles();
            articles.setId(commentArticlesId);
            articles.setCommentCount(1L);
            articlesService.updateArticles(articles);
            comment.setCommentUserId(loginUser.getUserId());

            commentMapper.insertComment(comment);
            // 插入评论图片地址
            Image image = new Image();
            image.setQuoteId(comment.getId());
            image.setType(2L);
            imageService.insertImages(uploadImageList, image);
            return success(selectCommentById(comment.getId(), loginUser.getUserId()));
        }
        return error("文章不存在");
    }

    @Override
    public int updateComment(Comment comment) {
        return commentMapper.updateComment(comment);
    }

    @Override
    public int deleteCommentByIds(Long[] ids) {
        return commentMapper.deleteCommentByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteCommentById(Long commentId) {
        if (StringUtils.isNull(commentId)) {
            return error("评论id不得为空");
        }
        Comment comment = selectCommentById(commentId, 0L);
        if (StringUtils.isNull(comment)) {
            return error("评论不存在");
        }
        //先获取用户信息
        if (!checkUserDataScopeUtils(comment.getCommentUserId(),"system:article_comment:remove")) {
            return error("无权限删除此评论");
        }
        Articles articles = new Articles();
        articles.setId(comment.getCommentArticlesId());
        articles.setCommentCount(-1L);
        imageService.deleteImageByArticlesId(comment.getId(), 2L);
        articlesService.updateArticles(articles);
        commentMapper.deleteCommentById(commentId);
        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult commentThumbs(Long quoteId)  {
        AjaxResult result;
        Thumbs thumbs = new Thumbs();
        thumbs.setUserId(getUserId());
        thumbs.setType(2L);
        thumbs.setQuoteId(quoteId);
        // 查询数据库中有没有数据
        Comment comment = commentMapper.selectCommentById(quoteId, 0L);
        if (StringUtils.isNull(comment)) {
            return error("评论不存在");
        }
        Thumbs commThumbs = thumbsService.selectThumbsByThumb(thumbs);
        long thumbUpNumber;
        if (StringUtils.isNull(commThumbs)) {
            thumbsService.insertThumbs(thumbs);
            thumbUpNumber = 1L;
            result = success(true);
        } else {
            thumbsService.deleteThumbsById(commThumbs.getId());
            thumbUpNumber = -1L;
            result = success(false);
        }
        Comment commentUpdate = new Comment();
        commentUpdate.setId(quoteId);
        commentUpdate.setCommentThumbsUpCount(thumbUpNumber);
        commentMapper.updateComment(commentUpdate);
        return result;
    }
}
