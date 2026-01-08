package com.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "tb_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String openId;
    private String nickname;
    private String avatarUrl;
    private Date createTime;
}
