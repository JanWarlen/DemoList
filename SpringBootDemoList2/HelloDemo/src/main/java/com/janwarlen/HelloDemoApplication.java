package com.janwarlen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName: HelloDemoApplication
 * @author: wangjian
 * @Date: 2020/8/24 10:16
 * @Description:
 */
@SpringBootApplication
@EnableScheduling
public class HelloDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloDemoApplication.class, args);
    }
}
