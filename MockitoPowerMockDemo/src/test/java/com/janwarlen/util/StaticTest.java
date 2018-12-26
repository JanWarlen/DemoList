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
}
