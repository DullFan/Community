package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.CommentReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentReplyMapper {
    /**
     * 查询所有条目
     */
    public Long selectTotalNum(Long commentId);
    /**
     * 查询评论回复
     */
    public CommentReply selectCommentReplyById(@Param("commentId") Long commentId,@Param("userId") Long userId);

    /**
     * 查询评论回复列表
     */
    public List<CommentReply> selectCommentReplyList(
            String orderBy, String orderType, Long commentId, Long userId, Long pageNum, Long pageSize);

    /**
     * 新增评论回复
     *
     * @param commentReply 评论回复
     * @return 结果
     */
    public int insertCommentReply(CommentReply commentReply);

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
     * @param id 评论回复主键
     * @return 结果
     */
    public int deleteCommentReplyById(Long id);
}
