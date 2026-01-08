package com.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.VO.PostVO;
import com.wx.entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    @Select("<script>" +
            "SELECT p.*, u.nickname, u.avatar_url " +
            "FROM tb_post p " +
            "LEFT JOIN tb_user u ON p.user_id = u.id " +
            "<where>" +
            "  <if test='categoryId != null'> " +
            "    AND p.category_id = #{categoryId} " +
            "  </if> " +
            "  <if test='keyword != null and keyword != \"\"'> " +
            "    AND (p.title LIKE CONCAT('%', #{keyword}, '%') " +
            "         OR p.content LIKE CONCAT('%', #{keyword}, '%') " +
            "         OR u.nickname LIKE CONCAT('%', #{keyword}, '%')) " +
            "  </if> " +
            "</where> " +
            "ORDER BY p.create_time DESC" +
            "</script>")
    List<PostVO> searchPostByKeyword(@Param("keyword") String keyword,
                                     @Param("categoryId") Integer categoryId);
    @Select("SELECT p.*, u.nickname, u.avatar_url, c.name as categoryName " +
            "FROM tb_post p " +
            "LEFT JOIN tb_user u ON p.user_id = u.id " +
            "LEFT JOIN tb_category c ON p.category_id = c.id " +
            "WHERE p.id = #{id}")
    PostVO getPostDetailById(@Param("id") Integer id);

    @Select("SELECT p.*, u.nickname, u.avatar_url " +
            "FROM tb_post p " +
            "LEFT JOIN tb_user u ON p.user_id = u.id " +
            "WHERE p.user_id = #{userId} " +
            "ORDER BY p.create_time DESC")
    List<PostVO> findMyPosts(@Param("userId") Integer userId);

    @Select("SELECT p.*, u.nickname, u.avatar_url " +
            "FROM tb_post_like l " +
            "JOIN tb_post p ON l.post_id = p.id " +
            "JOIN tb_user u ON p.user_id = u.id " +
            "WHERE l.user_id = #{userId} " +
            "ORDER BY l.create_time DESC")
    List<PostVO> findMyLikedPosts(@Param("userId") Integer userId);
    // 1. 插入点赞记录
    @Insert("INSERT INTO tb_post_like (post_id, user_id, create_time) VALUES (#{postId}, #{userId}, NOW())")
    int insertLikeRecord(@Param("postId") Integer postId, @Param("userId") Integer userId);

    // 2. 帖子点赞数 +1
    @Update("UPDATE tb_post SET like_count = like_count + 1 WHERE id = #{postId}")
    void incrementLikeCount(@Param("postId") Integer postId);
    // 3. 帖子回复数 +1
    @Update("UPDATE tb_post SET reply_count = reply_count + 1 WHERE id = #{postId}")
    int incrementReplyCount(@Param("postId") Integer postId);
    @Update("update tb_post set view_count =view_count + 1 where id = #{id}")
    void incrementViewCount(Integer id);
}
