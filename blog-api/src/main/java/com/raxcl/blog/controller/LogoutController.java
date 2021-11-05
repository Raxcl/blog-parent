package com.raxcl.blog.controller;

import com.raxcl.blog.service.LoginService;
import com.raxcl.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LogoutController {
    private final LoginService loginService;

    public LogoutController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
