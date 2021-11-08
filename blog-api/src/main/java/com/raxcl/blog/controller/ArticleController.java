package com.raxcl.blog.controller;

import com.raxcl.blog.dao.pojo.Article;
import com.raxcl.blog.service.ArticleService;
import com.raxcl.blog.vo.ArticleVo;
import com.raxcl.blog.vo.param.PageParams;
import com.raxcl.blog.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

@RestController
@RequestMapping("articles")
public class ArticleController {

    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //Result是统一结果返回(VO类)
    @PostMapping
    public Result articles(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){

        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){
        ArticleVo articleVo = articleService.findArticleById(id);
        return Result.success(articleVo);
    }
}
