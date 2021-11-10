package com.raxcl.blog.admin.controller;

import com.raxcl.blog.admin.model.params.PageParam;
import com.raxcl.blog.admin.pojo.Permission;
import com.raxcl.blog.admin.service.PermissionService;
import com.raxcl.blog.admin.vo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final PermissionService permissionService;

    public AdminController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
