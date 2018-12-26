package com.janwarlen.controller;

import com.janwarlen.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @auther: janwarlen
 * @date: 2018/12/25 15:07
 * @Description: 测试用controller
 */
@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("testMethod")
    @ResponseBody
    public String testMethod() {
        return testService.test();
    }

    @RequestMapping("testMethodWithParam")
    @ResponseBody
    public String testMethodWithParam(Map<String, String> param) {
        return testService.testWithParam(param);
    }
}
