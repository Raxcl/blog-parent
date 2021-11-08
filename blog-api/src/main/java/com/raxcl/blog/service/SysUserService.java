package com.raxcl.blog.service;

import com.raxcl.blog.dao.pojo.SysUser;
import com.raxcl.blog.vo.Result;
import com.raxcl.blog.vo.UserVo;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String pwd);

    Result getUserInfoByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long authorId);
}
