package com.raxcl.blog.dao.pojo;

import lombok.Data;

@Data
public class Article {

    public static final int Article_TOP =1;

    public static final int Article_Common = 0;

    private Long id;

    /**
     *评论数量
     */
    private int commentCounts;

    /**
     * 创建时间
     */
    private Long createDate;

    /**
     * 简介
     */
    private String summary;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览数量
     */
    private  int viewCounts;

    /**
     * 是否置顶
     */
    private int weight;

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
    private Long categoryId;
}
