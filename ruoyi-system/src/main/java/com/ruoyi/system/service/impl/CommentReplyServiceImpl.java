package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Comment;
import com.ruoyi.system.domain.CommentReply;
import com.ruoyi.system.domain.Image;
import com.ruoyi.system.domain.Thumbs;
import com.ruoyi.system.mapper.CommentReplyMapper;
import com.ruoyi.system.service.CommentReplyService;
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
public class CommentReplyServiceImpl implements CommentReplyService {
    @Autowired
    CommentReplyMapper commentReplyMapper;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ThumbsService thumbsService;

    @Autowired
    private CommentService commentService;

    @Override
    public Long selectTotalNum(Long commentId) {
        return commentReplyMapper.selectTotalNum(commentId);
    }

    @Override
    public CommentReply selectCommentReplyById(Long id, Long userId) {
        return commentReplyMapper.selectCommentReplyById(id, userId);
    }

    @Override
    public AjaxResult selectCommentReplyList(String orderBy, String orderType, Long commentId, Long pageNum, Long pageSize) {
        if (StringUtils.isNull(commentId)) {
            return error("评论Id不存在");
        }
        if (pageNum <= 0) {
            return error("页数不能少于0");
        }
        if (orderBy == null) {
            orderBy = "reply_time";
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
        List<CommentReply> commentReplies = commentReplyMapper.selectCommentReplyList(orderBy, orderType, commentId, userId, (pageNum - 1) * pageSize, pageSize);
        AjaxResult success = success(commentReplies);
        success.put("pageNum", pageNum);
        success.put("pageSize", pageSize);
        success.put("totalNum", commentReplyMapper.selectTotalNum(commentId));
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertCommentReply(CommentReply commentReply, List<String> uploadImageList) throws Exception {
        Long commentId = commentReply.getCommentId();
        if (StringUtils.isNull(commentId)) {
            return error("评论Id不能为空");
        }
        Comment commentById = commentService.selectCommentById(commentId, getUserId());
        if (commentById != null) {
            // 更新评论信息
            Comment comment = new Comment();
            comment.setId(commentId);
            comment.setReplyCount(1L);
            commentService.updateComment(comment);
            // 插入回复
            commentReply.setCommentReplyUserId(getUserId());
            commentReplyMapper.insertCommentReply(commentReply);
            // 插入评论图片
            Image image = new Image();
            image.setQuoteId(commentReply.getId());
            image.setType(3L);
            imageService.insertImages(uploadImageList, image);
            return success(selectCommentReplyById(commentReply.getId(), getUserId()));
        }
        return error("评论不存在");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCommentReply(CommentReply commentReply) {
        return commentReplyMapper.updateCommentReply(commentReply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCommentReplyByIds(Long[] ids) {
        return commentReplyMapper.deleteCommentReplyByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteCommentReplyById(Long commentReplyId) throws Exception {
        if (StringUtils.isNull(commentReplyId)) {
            return error("回复评论id不得为空");
        }
        CommentReply commentReply = commentReplyMapper.selectCommentReplyById(commentReplyId, 0L);
        if (StringUtils.isNull(commentReply)) {
            return error("评论回复不存在");
        }
        //先获取用户信息
        if (!checkUserDataScopeUtils(commentReply.getCommentReplyUserId(),"system:article_comment:remove")) {
            return error("无权限删除此评论");
        }
        Comment comment = new Comment();
        comment.setId(commentReply.getCommentId());
        comment.setReplyCount(-1L);
        commentService.updateComment(comment);
        imageService.deleteImageByArticlesId(commentReply.getId(), 3L);
        commentReplyMapper.deleteCommentReplyById(commentReply.getId());
        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult commentReplyThumbs(Long quoteId) throws Exception {
        AjaxResult result;
        Thumbs thumbs = new Thumbs();
        thumbs.setUserId(getUserId());
        thumbs.setType(3L);
        thumbs.setQuoteId(quoteId);
        // 查询数据库中有没有数据
        CommentReply commentReply = commentReplyMapper.selectCommentReplyById(quoteId, getUserId());
        if (StringUtils.isNull(commentReply)) {
            return error("评论回复不存在");
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
        CommentReply updateCommentReply = new CommentReply();
        updateCommentReply.setId(quoteId);
        updateCommentReply.setCommentReplyThumbsUpCount(thumbUpNumber);
        commentReplyMapper.updateCommentReply(updateCommentReply);
        return result;
    }
}
