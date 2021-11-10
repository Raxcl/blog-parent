package com.raxcl.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.raxcl.blog.admin.mapper.AdminMapper;
import com.raxcl.blog.admin.mapper.PermissionMapper;
import com.raxcl.blog.admin.pojo.Admin;
import com.raxcl.blog.admin.pojo.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminMapper adminMapper;
    private final PermissionMapper permissionMapper;

    public AdminService(AdminMapper adminMapper, PermissionMapper permissionMapper) {
        this.adminMapper = adminMapper;
        this.permissionMapper = permissionMapper;
    }

    public Admin findAdminByUserName(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username).last("limit 1");
        Admin adminUser = adminMapper.selectOne(queryWrapper);
        return adminUser;
    }

    public List<Permission> findPermissionsByAdminId(Long adminId){
        return permissionMapper.findPermissionsByAdminId(adminId);
    }
}
