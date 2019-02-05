package com.janwarlen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 15:41
 * @Description:
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.net.ssl.*","javax.crypto.*","org.xml.*", "javax.xml.*"})
public class BasedTest {

    @Test
    public void baseTest() {
        System.out.println("----------------------------------");
        System.out.println("hello mockito.");
    }
}
