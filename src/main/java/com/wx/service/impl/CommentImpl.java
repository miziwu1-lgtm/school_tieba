package com.wx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.VO.CommentVO;
import com.wx.entity.Comment;
import com.wx.mapper.CommentMapper;
import com.wx.mapper.PostMapper;
import com.wx.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentImpl
        extends ServiceImpl<CommentMapper,Comment>
        implements ICommentService {
    @Override
    public List<CommentVO> getCommentList(Integer id){
        return baseMapper.getCommentsByPostId(id);
    }
    @Override
    public List<CommentVO> showMyComment(Integer id){
        return baseMapper.findMyComments(id);
    }
    @Override
    public List<CommentVO> showMyReply(Integer id){
        return baseMapper.findMyReply(id);
    }
    @Autowired
    private PostMapper postMapper; // 需要调用帖子Mapper更新数量

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addNewComment(Comment comment) {
        // 1. 补全数据
        comment.setCreateTime(new Date()); // 设置当前时间

        int insertResult = baseMapper.insert(comment);

        // 3. 如果插入成功，同步增加帖子的回复数
        int updateRows = postMapper.incrementReplyCount(comment.getPostId());

        return updateRows > 0;
    }
}

