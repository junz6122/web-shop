package com.jun.backend;

import com.jun.backend.utils.PathUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jun.backend.mapper")
@SpringBootApplication
public class ElectronicMallApplication {

    public static void main(String[] args) {
        System.out.println("Project Path: " + PathUtils.getClassLoadRootPath());
        SpringApplication.run(ElectronicMallApplication.class, args);
    }

}
