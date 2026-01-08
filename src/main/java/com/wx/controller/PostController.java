package com.wx.controller;

import com.wx.entity.Post;
import com.wx.utils.R;
import com.wx.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("post")
public class PostController {
    @Autowired
    private IPostService postService;
    @GetMapping("/list")
    public R searchPostList(
             String keyword,
             Integer categoryId){
        return new R(true,"查询成功",postService.getPostList(keyword,categoryId));
    }
    @GetMapping("/detail/{id}")
    public R getDetail(@PathVariable Integer id) {
        return new R(true,"查询成功",postService.showDetail(id));
    }
    @PostMapping("/like")
    public R savePostLike(@RequestBody Map<String, Integer> params) {
        Integer postId = params.get("postId"); // 注意前端传参名要一致
        Integer userId = params.get("userId");

        if (postId == null || userId == null) {
            return new R(false, "参数错误", null);
        }

        // 调用 Service 处理逻辑
        return postService.likePost(postId, userId);
    }
    @PostMapping("/add")
    public R savePost(@RequestBody Post post) {
        return new R(true,"发布成功",postService.addPost(post));
    }
}
