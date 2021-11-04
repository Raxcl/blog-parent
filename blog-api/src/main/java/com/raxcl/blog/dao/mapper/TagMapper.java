package com.raxcl.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.raxcl.blog.dao.pojo.Tag;
import com.raxcl.blog.vo.TagVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章标题查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagByArticleId(Long articleId);

    /**
     * 查询最热的标签，前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);

}
