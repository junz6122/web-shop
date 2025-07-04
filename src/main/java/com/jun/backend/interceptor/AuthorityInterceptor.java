package com.jun.backend.interceptor;

import com.jun.backend.utils.TokenUtils;
import com.jun.backend.annotation.Authority;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> clazz = handlerMethod.getBeanType();
            if(method!=null && clazz!=null){
                boolean isMethodAnnotation = method.isAnnotationPresent(Authority.class);
                boolean isClassAnnotation = clazz.isAnnotationPresent(Authority.class);
                Authority authority = null;
                // Method annotations override class annotations
                if(isMethodAnnotation){
                    authority = method.getAnnotation(Authority.class);
                }else if (isClassAnnotation){
                    authority = clazz.getAnnotation(Authority.class);
                }
                if(authority==null){
                    return true;
                }
                switch (authority.value()){
                    case requireLogin:
                        return TokenUtils.validateLogin();
                    case requireAuthority:
                        return TokenUtils.validateAuthority();
                    case noRequire:
                        return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
