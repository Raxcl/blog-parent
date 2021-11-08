package com.raxcl.blog.service;

import com.raxcl.blog.vo.CategoryVo;
import com.raxcl.blog.vo.Result;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();
}
