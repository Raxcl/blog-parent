package com.raxcl.blog.service.impl;

import com.raxcl.blog.dao.mapper.SysUserMapper;
import com.raxcl.blog.dao.pojo.SysUser;
import com.raxcl.blog.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserMapper sysUserMapper;

    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (Objects.isNull(sysUser)){
            sysUser = new SysUser();
            sysUser.setNickname("raxcl学博客");
        }
        return sysUser;
    }
}
