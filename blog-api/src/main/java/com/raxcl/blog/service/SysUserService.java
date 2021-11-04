package com.raxcl.blog.service;

import com.raxcl.blog.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long id);
}
