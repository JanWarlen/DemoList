package com.janwarlen.service.impl;

import com.janwarlen.mapper.TestMapper;
import com.janwarlen.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 15:08
 * @Description: 测试用service
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;
    @Override
    public String test() {
        return testMapper.test();
    }

    @Override
    public String testWithParam(Map<String, String> param) {
        return String.valueOf(param.size());
    }

    @Override
    public String testPrivateMethod(String param) {
        return privateMethod(param);
    }

    private String privateMethod(String param) {
        return param + "---";
    }
}
