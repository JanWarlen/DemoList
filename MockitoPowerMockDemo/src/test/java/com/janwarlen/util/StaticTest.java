package com.janwarlen.util;

import com.janwarlen.BasedTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 16:29
 * @Description:
 */
@PrepareForTest(Static.class)
public class StaticTest extends BasedTest {

    @Before
    public void init() {
        PowerMockito.mockStatic(Static.class);
    }

    @Test
    public void testStaticMethod() {
        Mockito.when(Static.staticMethod()).thenReturn("mockito static");
        Assert.assertEquals("mockito static", Static.staticMethod());
    }

    @Test
    public void testStaticWithParam() {
        // 该方式会导致所有的方法调用均通过Mockito
        Mockito.when(Static.staticWithParam("test")).thenReturn("mockito static");
        Mockito.when(Static.staticWithParam("static")).thenReturn("static_");
        Assert.assertEquals("static_", Static.staticWithParam("static"));
        Assert.assertEquals("mockito static", Static.staticWithParam("test"));
        Assert.assertEquals(null, Static.staticWithParam("null"));
    }

    @Test
    public void testStaticWithParamBySpy() {
        PowerMockito.spy(Static.class);
        PowerMockito.when(Static.staticWithParam("test")).thenReturn("mockito static");
        Assert.assertEquals("static_", Static.staticWithParam("static"));
        Assert.assertEquals("mockito static", Static.staticWithParam("test"));
    }

    @Test
    public void testStaticVoid() throws Exception {
        PowerMockito.spy(Static.class);
        PowerMockito.doNothing().when(Static.class, "staticVoid");
        Static.staticVoid();
    }

    @Test
    public void testStaticVoidWithParam() throws Exception {
        PowerMockito.mockStatic(Static.class);
        PowerMockito.doCallRealMethod().when(Static.class, "staticVoidWithParam", Mockito.anyString());
        PowerMockito.doNothing().when(Static.class, "staticVoidWithParam", "test");
        // 实际调用
        Static.staticVoidWithParam("1");
        // 验证调用次数
        PowerMockito.verifyStatic(Mockito.times(1));
        // 需要验证调用次数的方法
        Static.staticWithParam("1");
        // 同上
        PowerMockito.verifyStatic(Mockito.never());
        Static.staticVoidWithParam("test");
        PowerMockito.verifyStatic(Mockito.never());
        Static.staticWithParam("test");
    }
}
