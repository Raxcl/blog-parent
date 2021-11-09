package com.raxcl.blog.vo.param;

import lombok.Data;

@Data
public class PageParams {
    private int page = 1;
    private int pageSize = 10;
    private Long categoryId;
    private Long tagId;
}
