package com.raxcl.blog.service;

import com.raxcl.blog.vo.ArticleVo;
import com.raxcl.blog.vo.param.PageParams;
import com.raxcl.blog.vo.Result;

public interface ArticleService {

    /**
     * 分页查询，文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

    ArticleVo findArticleById(Long id);
}
