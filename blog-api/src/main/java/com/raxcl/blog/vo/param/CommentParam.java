package com.raxcl.blog.vo.param;

import lombok.Data;

@Data
public class CommentParam {
    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
