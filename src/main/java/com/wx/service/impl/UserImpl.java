package com.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.entity.User;
import com.wx.mapper.UserMapper;
import com.wx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserImpl
        extends ServiceImpl <UserMapper, User>
        implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginOrNew(String openId, String nickname, String avatarUrl){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);

        // 2. 查询数据库
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            System.out.println("用户不存在，正在注册...");
            user = new User();
            user.setOpenId(openId);
            if(nickname!=null&&!nickname.equals("")){
                user.setNickname(nickname);
            }
            else{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String timeStr = sdf.format(new Date());
                user.setNickname("微信用户" + timeStr);
            }
            if(avatarUrl!=null&&!avatarUrl.equals("")){
                user.setAvatarUrl(avatarUrl);
            }
            else
                user.setAvatarUrl("");       // 默认头像为空

            user.setCreateTime(new Date());

            // 插入数据库
            userMapper.insert(user);
            // 插入后，MyBatis-Plus 会自动把生成的 ID 回填到 user 对象中
        } else {
            // === 情况B：查到了，说明是老用户，进行【登录】 ===
            System.out.println("用户已存在，直接登录: " + user.getId());
            // 你可以在这里更新一下“最后登录时间”，如果有这个字段的话
        }

        // 4. 无论是登录还是注册，最后都返回这个 User 对象给 Controller
        return user;
    }
}

