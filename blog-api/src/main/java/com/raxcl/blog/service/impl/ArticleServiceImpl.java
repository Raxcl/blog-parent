package com.raxcl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.raxcl.blog.dao.dto.Archives;
import com.raxcl.blog.dao.mapper.ArticleBodyMapper;
import com.raxcl.blog.dao.mapper.ArticleMapper;
import com.raxcl.blog.dao.mapper.ArticleTagMapper;
import com.raxcl.blog.dao.pojo.Article;
import com.raxcl.blog.dao.pojo.ArticleBody;
import com.raxcl.blog.dao.pojo.ArticleTag;
import com.raxcl.blog.dao.pojo.SysUser;
import com.raxcl.blog.service.*;
import com.raxcl.blog.utils.UserThreadLocal;
import com.raxcl.blog.vo.ArticleBodyVo;
import com.raxcl.blog.vo.ArticleVo;
import com.raxcl.blog.vo.TagVo;
import com.raxcl.blog.vo.param.ArticleParam;
import com.raxcl.blog.vo.param.PageParams;
import com.raxcl.blog.vo.Result;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final TagService tagService;
    private final SysUserService sysUserService;
    private final ArticleBodyMapper articleBodyMapper;
    private final CategoryService categoryService;
    private final ThreadService threadService;
    private final ArticleTagMapper articleTagMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, TagService tagService, SysUserService sysUserService, ArticleBodyMapper articleBodyMapper, CategoryService categoryService, ThreadService threadService, ArticleTagMapper articleTagMapper) {
        this.articleMapper = articleMapper;
        this.tagService = tagService;
        this.sysUserService = sysUserService;
        this.articleBodyMapper = articleBodyMapper;
        this.categoryService = categoryService;
        this.threadService = threadService;
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1. 分页查询 article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId() != null){
            // and category_id=#{categoryId}
            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
        }
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
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTag) {
            articleVo.setTags(tagService.findTagByArticleId(article.getId()));
        }
        if (isAuthor) {
            articleVo.setAuthor(sysUserService.findUserById(article.getId()).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }


    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //查询文章之后，更新阅读数
        //采用线程池
        threadService.updateArticleViewCount(articleMapper, article);
        return articleVo;
    }


    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        //此接口要加入到登录拦截当中
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1. 发布文章 目的 构建Article对象
         * 2. 作者id 当前的登录用户
         * 3. 标签 要将标签加入到 关联列表当中
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getTitle());
        article.setTitle(articleParam.getTitle());
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        //插入之后会生成一个文章id
        this.articleMapper.insert(article);

        //tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                this.articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }
}
