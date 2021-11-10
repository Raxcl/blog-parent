package com.raxcl.blog.admin.service;

import com.raxcl.blog.admin.pojo.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class SecurityUserService implements UserDetailsService {
    private final AdminService adminService;

    public SecurityUserService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}",username);
        //当用户登录的时候，springSecurity就会将请求转发到此
        //根据用户名查找用户，不存在则抛出异常， 存在则将用户名，密码，授权列表 组装成springSecurity的User对象并返回
        Admin adminUser = adminService.findAdminByUserName(username);
        if (adminUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails = new User(username,adminUser.getPassword(),authorities);
        //剩下的认证 就由框架帮我们完成
        return userDetails;
    }
}
