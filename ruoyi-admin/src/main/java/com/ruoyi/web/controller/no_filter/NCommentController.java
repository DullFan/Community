package com.ruoyi.web.controller.no_filter;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.CommentReplyService;
import com.ruoyi.system.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/filterN/article/comment")
@Tag(name = "评论")
public class NCommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentReplyService commentReplyService;


    @GetMapping("/selectComment")
    public AjaxResult selectComment(String orderBy, String orderType, Long commentArticlesId, Long pageNum, Long pageSize) {
        return commentService.selectCommentList(orderBy, orderType, commentArticlesId, pageNum, pageSize);
    }

    @GetMapping("/selectCommentReply")
    public AjaxResult selectCommentReply(String orderBy,String orderType,Long commentId, Long pageNum, Long pageSize) {
        return commentReplyService.selectCommentReplyList(orderBy, orderType, commentId, pageNum, pageSize);
    }
}
