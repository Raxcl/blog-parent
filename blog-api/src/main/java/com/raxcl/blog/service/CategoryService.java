package com.raxcl.blog.service;

import com.raxcl.blog.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
