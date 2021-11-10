package com.raxcl.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    //防止前端精度损失，把id转为string
    //分布式id比较长，传到前端会有精度损失，必须转为string类型进行传输，就不会有问题
    private String id;
    private UserVo author;
    private String content;
    private List<CommentVo> childrens;
    private String createDate;
    private Integer level;
    private UserVo toUser;
}
