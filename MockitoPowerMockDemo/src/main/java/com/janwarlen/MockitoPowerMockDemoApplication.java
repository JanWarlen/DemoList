package com.janwarlen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 14:57
 * @Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.janwarlen.mapper")
public class MockitoPowerMockDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockitoPowerMockDemoApplication.class, args);
    }
}
