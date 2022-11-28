package com.root;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.root.mapper"}) //扫描mapper接口
public class SmartSchoolProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSchoolProjectApplication.class, args);
    }

}
