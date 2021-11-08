package com.raxcl.blog.service;

import com.raxcl.blog.vo.Result;
import com.raxcl.blog.vo.param.CommentParam;

public interface CommentsService {
    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
