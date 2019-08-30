package com.janwarlen;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: SpringBootDubboDemoApplication
 * @author: wangjian
 * @Date: 2019/8/21 15:01
 * @Description:
 */
//@DubboComponentScan(basePackages={"io.shulie.center.user.api.service","com.deppon.data.platform.demo.api.facade.service"})
@SpringBootApplication
public class SpringBootDubboDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboDemoApplication.class, args);
    }
}
