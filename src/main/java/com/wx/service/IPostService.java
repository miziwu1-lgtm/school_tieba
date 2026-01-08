package com.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.VO.PostVO;
import com.wx.entity.Post;
import com.wx.utils.R;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPostService extends IService<Post> {
    List<PostVO> getPostList(String keyword, Integer categoryId);

    PostVO showDetail(Integer id);

    List<PostVO> showMyPost(Integer id);

    List<PostVO> showMyLikePost(Integer id);

    @Transactional(rollbackFor = Exception.class)
    R likePost(Integer postId, Integer userId);

    boolean addPost(Post post);
}
