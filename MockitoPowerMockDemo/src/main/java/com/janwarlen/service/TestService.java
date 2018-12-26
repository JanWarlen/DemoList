package com.janwarlen.service;

import java.util.Map;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 15:08
 * @Description: 测试用service
 */
public interface TestService {
    String test();

    String testWithParam(Map<String, String> param);

    String testPrivateMethod(String param);
}
