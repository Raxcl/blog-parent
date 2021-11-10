package com.raxcl.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    //一定要记得加 否则会出现精度损失
//    @JsonSerialize(using = ToStringSerializer.class)  后续：改为String类型
    private String id;

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
    private Integer commentCounts;

    /**
     * 浏览数量
     */
    private  Integer viewCounts;

    /**
     * 是否置顶
     */
    private Integer weight;

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
    private Integer categoryId;
}
