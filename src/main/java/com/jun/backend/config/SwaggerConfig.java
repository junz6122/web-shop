package com.jun.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("SpringBoot + Swagger")
                        .description("detail message")
                        .version("1.0")
                        .license("The Apache License")
                        .build()
                )
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jun.backend.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
