package com.renxuanchen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.southwind.mapper")
public class HrApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

}
