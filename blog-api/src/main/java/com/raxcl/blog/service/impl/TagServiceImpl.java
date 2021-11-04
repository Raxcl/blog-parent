package com.raxcl.blog.service.impl;

import com.raxcl.blog.dao.mapper.TagMapper;
import com.raxcl.blog.dao.pojo.Tag;
import com.raxcl.blog.service.TagService;
import com.raxcl.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    public TagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<TagVo> findTagByArticleId(Long articleId) {
        //mybatisplus 无法进行多表查询？
        List<Tag> tags = tagMapper.findTagByArticleId(articleId);
        return copyList(tags);
    }

    private List<TagVo> copyList(List<Tag> records) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag record : records){
            tagVoList.add(copy(record));
        }
        return  tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    @Override
    public List<TagVo> hot(int limit) {
        List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(hotsTagIds)){
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(hotsTagIds);
        return copyList(tagList);
    }
}
