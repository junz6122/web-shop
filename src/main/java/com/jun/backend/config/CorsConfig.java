package com.jun.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        allowedOriginPatterns("*"). // Allow any domain to make cross requests-domain
                        allowedMethods("*"). // Allow any HTTP method (POST, GET, etc.)
                        allowedHeaders("*"). // Allow any request headers
                        allowCredentials(true). // Allow cookies to be included
                        exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); // maxAge(3600) indicates that the preflight request result can be cached for 3600 seconds
            }
        };
    }
}
