package com.janwarlen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisLockDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisLockDemoApplication.class, args);
    }
}
