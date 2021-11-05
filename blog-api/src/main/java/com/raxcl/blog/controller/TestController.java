package com.raxcl.blog.controller;

import com.raxcl.blog.utils.UserThreadLocal;
import com.raxcl.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping
    public Result test(){
        System.out.println("哈哈哈哈哈哈");
        System.out.println(UserThreadLocal.get());
        return Result.success(null);
    }
}
