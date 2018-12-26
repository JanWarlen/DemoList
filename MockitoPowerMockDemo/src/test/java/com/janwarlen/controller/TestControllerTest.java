package com.janwarlen.controller;

import com.janwarlen.BasedTest;
import com.janwarlen.service.TestService;
import com.janwarlen.service.impl.TestServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 15:58
 * @Description:
 */
public class TestControllerTest extends BasedTest {
    /**
     * 使用注解的mock对象只会通过设置的规则,不会处理非设置条件
     */
    @Mock
    private TestService testService;

    @Autowired
    @InjectMocks
    private TestController testController;

    @Test
    public void testTestMethod() {
        Mockito.when(testService.test()).thenReturn("mockito");
        Assert.assertEquals("mockito", testController.testMethod());
    }

    /**
     * 需要设置详尽的规则，未设置的只会返回null
     */
    @Test
    public void testTestMethodWithParam() {
        Map<String,String> test = new HashMap<>();
        test.put("1","2");
        PowerMockito.when(testService.testWithParam(Mockito.any())).thenReturn("others");
        // 后配置规则覆盖之前规则，此处覆盖指的是优先级高，并不是替换
        PowerMockito.when(testService.testWithParam(test)).thenReturn("1-2");
        Map<String,String> change = new HashMap<>();
        change.put("1","2");
        Assert.assertEquals("1-2", testController.testMethodWithParam(change));
        Map<String,String> change2 = new HashMap<>();
        change2.put("1","2");
        change2.put("2","3");
        Assert.assertEquals("others", testController.testMethodWithParam(change2));
    }

    /**
     * 因此此处并未使用注解注入的mock对象，因此可以设置特殊规则
     * 其他非规则测试不会触发mock
     * 此处并未通过controller代码，严格来算，应该属于service测试
     * 此处为了对比
     * 放在了controller的测试类中
     * @throws Exception
     */
    @Test
    public void testTestMethodWithParamBySpy() throws Exception {
        TestServiceImpl spy = PowerMockito.spy(new TestServiceImpl());
        Map<String,String> test = new HashMap<>();
        test.put("1","2");
        PowerMockito.doReturn("mockito").when(spy, "testWithParam", test);
        Map<String,String> change = new HashMap<>();
        change.put("1","2");
        Assert.assertEquals("mockito", spy.testWithParam(change));
        Map<String,String> change2 = new HashMap<>();
        change2.put("1","2");
        change2.put("2","3");
        Assert.assertEquals("2", spy.testWithParam(change2));
    }
}
