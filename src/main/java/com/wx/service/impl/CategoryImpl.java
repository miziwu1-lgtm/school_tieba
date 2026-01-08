package com.wx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.entity.Category;
import com.wx.mapper.CategoryMapper;
import com.wx.service.ICategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements ICategoryService {
}
