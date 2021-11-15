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
        //TODO 等域名备案成功后修改为域名访问
        //通过ip访问不能走443端口
        //如果配置了自定义拦截器，这种跨域配置会失效，所以采用第二种
        //跨域配置一
//      registry.addMapping("/**").allowedOrigins("http://81.71.87.241:8080","http://81.71.87.241:80","http://81.71.87.241:8888")
        //跨域配置二
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
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
