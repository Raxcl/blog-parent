package com.raxcl.blog.controller;

import com.raxcl.blog.service.CategoryService;
import com.raxcl.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Result listCategory(){
        return categoryService.findAll();
    }

    @GetMapping("/detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }
}
