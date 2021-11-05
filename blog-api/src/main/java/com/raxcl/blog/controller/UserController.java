package com.raxcl.blog.controller;

import com.raxcl.blog.service.SysUserService;
import com.raxcl.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    private final SysUserService sysUserService;

    public UserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("currentUser")
    public Result currentUser (@RequestHeader("Authorization") String token){
        return sysUserService.getUserInfoByToken(token);
    }
}
