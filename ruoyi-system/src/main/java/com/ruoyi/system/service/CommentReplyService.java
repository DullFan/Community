package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.CommentReply;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentReplyService {
    /**
     * 查询所有条目
     */
    public Long selectTotalNum(Long commentId);
    /**
     * 查询评论回复
     *
     * @param id 评论回复主键
     * @return 评论回复
     */
    public CommentReply selectCommentReplyById(Long id, Long userId);

    /**
     * 查询评论回复列表
     */
    public AjaxResult selectCommentReplyList(String orderBy,String orderType,Long commentId, Long pageNum, Long pageSize);

    /**
     * 新增评论回复
     *
     * @param commentReply 评论回复
     * @return 结果
     */
    public AjaxResult insertCommentReply(CommentReply commentReply, List<String> uploadImageList) throws Exception;

    /**
     * 修改评论回复
     *
     * @param commentReply 评论回复
     * @return 结果
     */
    public int updateCommentReply(CommentReply commentReply);

    /**
     * 批量删除评论回复
     *
     * @param ids 需要删除的评论回复主键集合
     * @return 结果
     */
    public int deleteCommentReplyByIds(Long[] ids);

    /**
     * 删除评论回复信息
     *
     * @param commentReplyId 评论回复主键
     * @return 结果
     */
    public AjaxResult deleteCommentReplyById(Long commentReplyId) throws Exception;

    /**
     * 评论回复点赞
     * @param quoteId
     * @return
     */
    AjaxResult commentReplyThumbs(Long quoteId) throws Exception;
}
