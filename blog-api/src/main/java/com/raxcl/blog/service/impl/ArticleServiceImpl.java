package com.raxcl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.raxcl.blog.dao.dto.Archives;
import com.raxcl.blog.dao.mapper.ArticleBodyMapper;
import com.raxcl.blog.dao.mapper.ArticleMapper;
import com.raxcl.blog.dao.pojo.Article;
import com.raxcl.blog.dao.pojo.ArticleBody;
import com.raxcl.blog.service.ArticleService;
import com.raxcl.blog.service.CategoryService;
import com.raxcl.blog.service.SysUserService;
import com.raxcl.blog.service.TagService;
import com.raxcl.blog.vo.ArticleBodyVo;
import com.raxcl.blog.vo.ArticleVo;
import com.raxcl.blog.vo.param.PageParams;
import com.raxcl.blog.vo.Result;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final TagService tagService;
    private final SysUserService sysUserService;
    private final ArticleBodyMapper articleBodyMapper;
    private final CategoryService categoryService;

    public ArticleServiceImpl(ArticleMapper articleMapper, TagService tagService, SysUserService sysUserService, ArticleBodyMapper articleBodyMapper, CategoryService categoryService) {
        this.articleMapper = articleMapper;
        this.tagService = tagService;
        this.sysUserService = sysUserService;
        this.articleBodyMapper = articleBodyMapper;
        this.categoryService = categoryService;
    }

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1. 分页查询 article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //是否置顶进行排序
         queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        //能直接返回吗？ 很明显不能
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return Result.success(articleVoList);
    }



    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records){
            articleVoList.add(copy(record,isTag, isAuthor,false,false));
        }
        return  articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records){
            articleVoList.add(copy(record,isTag, isAuthor, isBody, isCategory));
        }
        return  articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTag){
            articleVo.setTags(tagService.findTagByArticleId(article.getId()));
        }
        if (isAuthor){
            articleVo.setAuthor(sysUserService.findUserById(article.getId()).getNickname());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }



    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+ limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+ limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        return copy(article, true, true, true,true);
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
