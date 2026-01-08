package com.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.entity.User;

public interface IUserService extends IService<User> {
    User loginOrNew(String openId, String nickName, String avatarUrl);
}
