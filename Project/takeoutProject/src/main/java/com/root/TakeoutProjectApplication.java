package com.root;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan({"com.root.mapper"})//扫描mapper接口
@SpringBootApplication
@ServletComponentScan("com.root.Filter")//扫描原生的Servlet组件
public class TakeoutProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeoutProjectApplication.class, args);
    }

}
