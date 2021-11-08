package com.raxcl.blog.controller;

import com.raxcl.blog.service.TagService;
import com.raxcl.blog.vo.Result;
import com.raxcl.blog.vo.TagVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagsController {

    private final TagService tagService;

    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<TagVo> tagVoList = tagService.hot(limit);
        return Result.success(tagVoList);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }
}
