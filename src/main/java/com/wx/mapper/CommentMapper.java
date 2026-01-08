package com.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.VO.CommentVO;
import com.wx.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("SELECT " +
            "  c1.id, c1.post_id, c1.user_id, c1.content, c1.parent_id, c1.create_time, " +
            "  u1.nickname AS nickname, " +          /* 当前评论人的昵称 */
            "  u1.avatar_url AS avatarUrl, " +       /* 当前评论人的头像 */
            "  c2.content AS parentContent, " +      /* 父评论的内容 */
            "  u2.nickname AS parentNickname " +     /* 父评论人的昵称 */
            "FROM tb_comment c1 " +
            "LEFT JOIN tb_user u1 ON c1.user_id = u1.id " +
            "LEFT JOIN tb_comment c2 ON c1.parent_id = c2.id " + /* 自连接找父评论 */
            "LEFT JOIN tb_user u2 ON c2.user_id = u2.id " +      /* 找父评论的作者 */
            "WHERE c1.post_id = #{postId} " +
            "ORDER BY c1.create_time ASC")
    List<CommentVO> getCommentsByPostId(@Param("postId") Integer postId);

    @Select("SELECT " +
            "  c1.id, c1.content, c1.create_time, c1.post_id, c1.parent_id, " +
            "  p.title AS postTitle, " +                   /* 1. 关联出原帖子标题 */
            "  p.create_time AS postCreateTime, " +        /* 2. 帖子发布时间 */
            "  u_post.nickname AS postNickname, " +        /* 3. 贴主昵称 */
            "  u_post.avatar_url AS postAvatarUrl, " +     /* 4. 贴主头像 */
            "  c2.content AS parentContent, " +            /* 5. 如果是回复，查出对方的评论内容 */
            "  u_parent.nickname AS parentNickname, " +     /* 6. 如果是回复，查出对方的昵称 */
            "  u_parent.avatar_url AS parentAvatarUrl " +   /* 6. 如果是回复，查出对方的头像 */
            "FROM tb_comment c1 " +
            "INNER JOIN tb_post p ON c1.post_id = p.id " +  /* 必须有帖子 */
            "LEFT JOIN tb_user u_post ON p.user_id = u_post.id " + /* 找贴主 */
            "LEFT JOIN tb_comment c2 ON c1.parent_id = c2.id " +    /* 找父评论 */
            "LEFT JOIN tb_user u_parent ON c2.user_id = u_parent.id " + /* 找被回复人 */
            "WHERE c1.user_id = #{userId} " +
            "ORDER BY c1.create_time DESC")
    List<CommentVO> findMyComments(@Param("userId") Integer userId);

    @Select("SELECT " +
            "  c1.id, c1.content, c1.create_time, c1.post_id, " +
            "  u_sender.nickname AS nickname, " +            /* 1. 谁给我发的（发送者昵称） */
            "  u_sender.avatar_url AS avatarUrl, " +         /* 2. 发送者头像 */
            "  p.title AS postTitle, " +                     /* 3. 发生在哪个帖子里 */
            "  c2.content AS parentContent " +               /* 4. 如果是回复我的评论，显示我的那条原评论 */
            "FROM tb_comment c1 " +
            "INNER JOIN tb_post p ON c1.post_id = p.id " +
            "INNER JOIN tb_user u_sender ON c1.user_id = u_sender.id " +
            "LEFT JOIN tb_comment c2 ON c1.parent_id = c2.id " + /* 关联父评论 */
            "WHERE c1.user_id != #{userId} " +               /* 过滤掉我自己发的 */
            "AND ( " +
            "  (p.user_id = #{userId} AND c1.parent_id = 0) " + /* 情况1：评论了我的帖子（一级评论） */
            "  OR (c2.user_id = #{userId}) " +                 /* 情况2：回复了我的评论（楼中楼） */
            ") " +
            "ORDER BY c1.create_time DESC")
    List<CommentVO> findMyReply(@Param("userId") Integer userId);

}
