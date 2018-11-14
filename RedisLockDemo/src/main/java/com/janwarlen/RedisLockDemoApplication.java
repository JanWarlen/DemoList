package com.janwarlen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisLockDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisLockDemoApplication.class, args);
    }
}
