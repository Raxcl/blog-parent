package com.raxcl.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.raxcl.blog.dao.pojo.SysUser;
import com.raxcl.blog.service.LoginService;
import com.raxcl.blog.service.SysUserService;
import com.raxcl.blog.utils.JWTUtils;
import com.raxcl.blog.vo.ErrorCode;
import com.raxcl.blog.vo.Result;
import com.raxcl.blog.vo.param.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    private static final String slat = "raxcl!@#";

    private final SysUserService sysUserService;
    private final RedisTemplate<String, String> redisTemplate;

    public LoginServiceImpl(SysUserService sysUserService, RedisTemplate<String, String> redisTemplate) {
        this.sysUserService = sysUserService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Result login(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        String pwd = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account,pwd);
        if (Objects.isNull(sysUser)){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

}
