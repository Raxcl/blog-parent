package com.raxcl.blog.config;

import com.raxcl.blog.handler.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebMVCConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        //跨域配置
        //TODO 改成服务器域名 或者ip地址
        registry.addMapping("/**").allowedOrigins("http://81.71.87.241:8080");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //登录拦截
        //拦截test接口，后续实际遇到需要拦截的接口时，在配置为真正的拦截接口
        registry.addInterceptor(loginInterceptor).addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }
    

}
