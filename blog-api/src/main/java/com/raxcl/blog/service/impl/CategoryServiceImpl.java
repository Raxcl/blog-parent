package com.raxcl.blog.service.impl;

import com.raxcl.blog.dao.mapper.CategoryMapper;
import com.raxcl.blog.dao.pojo.Category;
import com.raxcl.blog.service.CategoryService;
import com.raxcl.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
