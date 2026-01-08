package com.wx.VO;

import com.wx.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO extends Comment {
    private String nickname;
    private String avatarUrl;
    private String parentContent;
    private String parentNickname;
    private String parentAvatarUrl;
    private String postTitle;
    private Date postCreateTime;
    private String postNickname;
    private String postAvatarUrl;
}
