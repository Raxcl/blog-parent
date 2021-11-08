package com.raxcl.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> childrens;
    private String createDate;
    private Integer level;
    private UserVo toUser;
}
