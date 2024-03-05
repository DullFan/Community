package com.ruoyi.web.controller.filter;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Comment;
import com.ruoyi.system.domain.CommentReply;
import com.ruoyi.system.domain.Image;
import com.ruoyi.system.service.CommentReplyService;
import com.ruoyi.system.service.CommentService;
import com.ruoyi.system.service.ImageService;
import com.ruoyi.web.controller.common.CommonController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "文章评论管理")
@RequestMapping("/system/article/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentReplyService commentReplyService;

    /**
     * 评论文章
     */
    @PostMapping("/commentArticles")
    @PreAuthorize("@ss.hasPermi('system:article_comment:add')")
    public AjaxResult commentArticles(Comment comment, @RequestParam(required = false) List<String> uploadImageList) throws Exception {
        return commentService.insertComment(comment, uploadImageList);
    }

    @Autowired
    CommonController commonController;

    @Autowired
    ImageService imageService;

    /**
     * 删除评论
     */
    @DeleteMapping("/deleteComment")
    @PreAuthorize("@ss.hasPermi('system:article_comment:remove')")
    public AjaxResult deleteComment(Long commentId) throws Exception {
        List<Image> images = imageService.selectImageById(commentId, 2L);
        List<String> imgList = images.stream().map(Image::getImageUrl)
                .collect(Collectors.toList());
        commonController.deleteFiles(imgList);
        return commentService.deleteCommentById(commentId);
    }

    /**
     * 点赞评论
     */
    @PostMapping("/commentThumbs")
    @Operation(summary = "点赞评论")
    public AjaxResult commentThumbs(Long quoteId) throws Exception {
        return commentService.commentThumbs(quoteId);
    }

    /**
     * 回复评论
     */
    @PostMapping("/commentReply")
    @PreAuthorize("@ss.hasPermi('system:article_comment:add')")
    public AjaxResult commentReply(CommentReply commentReply, @RequestParam(required = false) List<String> uploadImageList) throws Exception {
        return commentReplyService.insertCommentReply(commentReply, uploadImageList);
    }

    /**
     * 删除评论回复
     */
    @DeleteMapping("/deleteCommentReply")
    @PreAuthorize("@ss.hasPermi('system:article_comment:remove')")
    public AjaxResult deleteCommentReply(Long commentReplyId) throws Exception {
        List<Image> images = imageService.selectImageById(commentReplyId, 3L);
        List<String> imgList = images.stream().map(Image::getImageUrl)
                .collect(Collectors.toList());
        commonController.deleteFiles(imgList);
        return commentReplyService.deleteCommentReplyById(commentReplyId);
    }

    /**
     * 点赞回复评论
     */
    @PostMapping("/commentReplyThumbs")
    @Operation(summary = "点赞回复评论")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult commentReplyThumbs(Long quoteId) throws Exception {
        return commentReplyService.commentReplyThumbs(quoteId);
    }
}
