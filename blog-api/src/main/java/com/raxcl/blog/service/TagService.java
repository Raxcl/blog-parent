package com.raxcl.blog.service;

import com.raxcl.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagByArticleId(Long articleId);
    List<TagVo> hot(int limit);
}
