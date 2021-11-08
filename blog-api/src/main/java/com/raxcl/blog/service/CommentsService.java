package com.raxcl.blog.service;

import com.raxcl.blog.vo.Result;

public interface CommentsService {
    Result commentsByArticleId(Long articleId);
}
