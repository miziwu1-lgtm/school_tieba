package com.wx.controller;

import com.wx.entity.Comment;
import com.wx.utils.R;
import com.wx.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @GetMapping("all/{id}")
    public R getAllComment(@PathVariable Integer id) {
        return new R(true,"查询成功",commentService.getCommentList(id));
    }
    @PostMapping("/add")
    public R addComment(@RequestBody Comment comment) {
        return new R(true ,"回复成功",commentService.addNewComment(comment));
    }
}
