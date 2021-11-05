package com.raxcl.blog.service;

import com.raxcl.blog.vo.Result;
import com.raxcl.blog.vo.param.LoginParam;

public interface LoginService {
    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    Result logout(String token);
}
