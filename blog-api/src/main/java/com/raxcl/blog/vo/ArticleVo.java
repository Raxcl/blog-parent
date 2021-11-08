package com.raxcl.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     *评论数量
     */
    private int commentCounts;

    /**
     * 浏览数量
     */
    private  int viewCounts;

    /**
     * 是否置顶
     */
    private int weight;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 作者
     */
    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;




    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 内容id
     */
    private Long bodyId;

    /**
     * 类别id
     */
    private int categoryId;
}
