package com.wx.VO;

import com.wx.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVO extends Post {
    private String nickname;   // 作者昵称
    private String avatarUrl;  // 作者头像
}
