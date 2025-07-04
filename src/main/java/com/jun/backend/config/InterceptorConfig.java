package com.jun.backend.config;

import com.jun.backend.interceptor.AuthorityInterceptor;
import com.jun.backend.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    JwtInterceptor jwtInterceptor;
    @Resource
    AuthorityInterceptor authorityInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //jwt拦截器
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register","/file/**","/avatar/**","/api/good/**","/api/icon/**","/api/category/**","/api/market/**","/api/carousel/**")
                .order(0)
        ;
        //权限校验拦截器
        registry.addInterceptor(authorityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns()
                .order(1)
        ;

        WebMvcConfigurer.super.addInterceptors(registry);
    }


}
