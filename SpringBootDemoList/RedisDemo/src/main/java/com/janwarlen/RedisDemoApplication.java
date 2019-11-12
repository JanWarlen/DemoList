package com.janwarlen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/5 15:01
 * @Description:
 */
@SpringBootApplication
@EnableScheduling
public class RedisDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }
}
