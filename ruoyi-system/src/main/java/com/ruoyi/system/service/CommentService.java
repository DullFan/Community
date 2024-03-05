package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Comment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 评论Service接口
 */
public interface CommentService {

    /**
     * 查询所有条目
     */
    public Long selectTotalNum(Long commentArticlesId);

    /**
     * 查询评论
     */
    public Comment selectCommentById(Long id, Long userId);

    /**
     * 查询评论列表
     */
    AjaxResult selectCommentList(String orderBy,String orderType,Long commentArticlesId, Long pageNum, Long pageSize);

    /**
     * 新增评论
     *
     * @param comment 评论
     * @return 结果
     */
    public AjaxResult insertComment(Comment comment, List<String> uploadImageList) throws Exception;

    /**
     * 修改评论
     *
     * @param comment 评论
     * @return 结果
     */
    public int updateComment(Comment comment);

    /**
     * 批量删除评论
     *
     * @param ids 需要删除的评论主键集合
     * @return 结果
     */
    public int deleteCommentByIds(Long[] ids);

    /**
     * 删除评论信息
     *
     * @param commentId 评论主键
     * @return 结果
     */
    public AjaxResult deleteCommentById(Long commentId) throws Exception;

    /**
     * 点赞评论
     * @param quoteId
     * @return
     */
    AjaxResult commentThumbs(Long quoteId) throws Exception;
}
