package com.wx.service;

import com.wx.VO.CommentVO;
import com.wx.entity.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICommentService {
    List<CommentVO> getCommentList(Integer id);

    List<CommentVO> showMyComment(Integer id);

    List<CommentVO> showMyReply(Integer id);

    @Transactional(rollbackFor = Exception.class) // 开启事务
    boolean addNewComment(Comment comment);
}
