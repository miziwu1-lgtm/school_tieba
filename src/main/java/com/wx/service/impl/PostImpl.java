package com.wx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.VO.PostVO;
import com.wx.entity.Post;
import com.wx.utils.R;
import com.wx.mapper.PostMapper;
import com.wx.service.IPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostImpl
        extends ServiceImpl<PostMapper, Post>
        implements IPostService{
    @Override
    public List<PostVO> getPostList(String keyword, Integer categoryId) {
        // 如果前端传的是空字符串 ""，我们需要处理成 null，这样上面的 XML <if> 才会判定为假
        if (keyword != null && keyword.trim().equals("")) {
            keyword = null;
        }
        if (categoryId != null && categoryId <= 0) {
            categoryId = null;
        }
        // 调用我们自定义的联表搜索方法
        return baseMapper.searchPostByKeyword(keyword, categoryId);
    }
    @Override
    public PostVO showDetail(Integer id){
        baseMapper.incrementViewCount(id);
        return baseMapper.getPostDetailById(id);
    }
    @Override
    public List<PostVO> showMyPost(Integer id){
        return baseMapper.findMyPosts(id);
    }
    @Override
    public List<PostVO> showMyLikePost(Integer id){
        return baseMapper.findMyLikedPosts(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R likePost(Integer postId, Integer userId){
        try {
            // 1. 尝试往 tb_post_like 表插入一条记录
            // 如果该用户已经点过赞，数据库有唯一索引，这里会直接报错，跳到 catch
            baseMapper.insertLikeRecord(postId, userId);

            // 2. 如果上面没报错，说明是第一次点赞，让帖子点赞数 +1
            baseMapper.incrementLikeCount(postId);

            return new R(true, "点赞成功", null);

        } catch (org.springframework.dao.DuplicateKeyException e) {
            // 3. 捕获到重复键异常，说明用户已经点过赞了
            return new R(false, "您已点过赞", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false, "点赞失败", null);
        }
    }
    @Override
    public boolean addPost(Post post){
        return baseMapper.insert(post) > 0;
    }
}

