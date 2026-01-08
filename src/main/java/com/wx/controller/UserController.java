package com.wx.controller;

import com.wx.entity.User;
import com.wx.utils.R;
import com.wx.service.ICommentService;
import com.wx.service.IPostService;
import com.wx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    IPostService postService;
    @Autowired
    ICommentService commentService;
    @PostMapping("/login")
    public R Login(@RequestBody User user){
        String openId = user.getOpenId();
        String nickname = user.getNickname();
        String AvatarUrl = user.getAvatarUrl();
        return new R(true,"登录成功",userService.loginOrNew(openId,nickname,AvatarUrl));

    }

    @GetMapping("/getDataByType")
    public R getDateByType(@RequestParam Integer id,@RequestParam String type){
        return switch (type) {
            case "post" -> new R(true, "查询成功", postService.showMyPost(id));
            case "like" -> new R(true, "查询成功", postService.showMyLikePost(id));
            case "comment" -> new R(true, "查询成功", commentService.showMyComment(id));
            case "reply" -> new R(true, "查询成功", commentService.showMyReply(id));
            default -> new R(false, "查询失败", null);
        };
    }

    @PostMapping("/updateInfo")
    public R updateInfo(@RequestBody User user){
        return new R(true ,"修改成功",userService.updateById(user));
    }
}
