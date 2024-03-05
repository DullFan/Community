package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    /**
     * 查询评论
     */
    Comment selectCommentById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 查询评论列表
     */
    public List<Comment> selectCommentList(String orderBy, String orderType, Long commentArticlesId, Long userId, Long pageNum, Long pageSize);

    /**
     * 新增评论
     *
     * @param comment 评论
     * @return 结果
     */
    public int insertComment(Comment comment);

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
     * @param id 评论主键
     * @return 结果
     */
    public int deleteCommentById(Long id);

    /**
     * 查询所有条目
     */
    public Long selectTotalNum(Long commentArticlesId);
}
