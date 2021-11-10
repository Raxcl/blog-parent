package com.raxcl.blog.vo;

import lombok.Data;

@Data
public class CategoryVo {
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String avatar;

    private String categoryName;

    private String description;
}
