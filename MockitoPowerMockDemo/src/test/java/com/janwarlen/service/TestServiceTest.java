package com.janwarlen.service;

import com.janwarlen.BasedTest;
import com.janwarlen.mapper.TestMapper;
import com.janwarlen.service.impl.TestServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 16:16
 * @Description:
 */
@PrepareForTest(TestServiceImpl.class)
public class TestServiceTest extends BasedTest {
    @Mock
    private TestMapper testMapper;

    @Autowired
    @InjectMocks
    private TestServiceImpl testService;

    @Test
    public void testTest() {
        Mockito.when(testMapper.test()).thenReturn("mockito");
        Assert.assertEquals("mockito", testService.test());
    }

    /**
     * 测试私有方法
     */
    @Test
    public void testPrivateMethod() throws Exception {
        TestServiceImpl spy = PowerMockito.spy(new TestServiceImpl());
        PowerMockito.when(spy, "privateMethod", "test").thenReturn("mockito");
        Assert.assertEquals("mockito", spy.testPrivateMethod("test"));
        Assert.assertEquals("test2---", spy.testPrivateMethod("test2"));
        PowerMockito.verifyPrivate(spy, Mockito.times(1))
                .invoke("privateMethod", "test");
    }
}
