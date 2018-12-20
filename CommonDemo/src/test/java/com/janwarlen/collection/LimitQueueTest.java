package com.janwarlen.collection;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Auther: wangjian
 * @Date: 2018/12/20 15:05
 * @Description: 定长队列单元测试
 */
public class LimitQueueTest {

    @Test
    public void testAdd() {
        LimitQueue<Integer> test = new LimitQueue<>(2);
        test.add(0);
        for (int i = 1; i < 10; i++) {
            test.add(i);
            System.out.println("test:" + JSONObject.toJSONString(test));
            Assert.assertEquals(test.size(), 2);
            Assert.assertEquals((long)test.peek(), (long)(i-1));
        }
    }
}
