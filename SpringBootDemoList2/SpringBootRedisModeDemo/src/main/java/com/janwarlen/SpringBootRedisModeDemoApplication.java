package com.janwarlen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

/**
 * @ClassName: SpringBootRedisModeDemoApplication
 * @author: wangjian
 * @Date: 2019/9/11 18:13
 * @Description:
 */
@SpringBootApplication
@EnableScheduling
public class SpringBootRedisModeDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisModeDemoApplication.class, args);
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
