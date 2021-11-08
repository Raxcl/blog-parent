package com.raxcl.blog.controller;

import com.raxcl.blog.service.CommentsService;
import com.raxcl.blog.vo.Result;
import com.raxcl.blog.vo.param.CommentParam;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

@RestController
@RequestMapping("comments")
public class CommentsController {
    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService){
        this.commentsService = commentsService;
    }

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){
        return commentsService.commentsByArticleId(articleId);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
