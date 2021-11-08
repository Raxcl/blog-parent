package com.raxcl.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.raxcl.blog.dao.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
