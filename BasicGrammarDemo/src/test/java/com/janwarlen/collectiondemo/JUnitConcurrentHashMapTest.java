package com.janwarlen.collectiondemo;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: JUnitConcurrentHashMapTest
 * @author: wangjian
 * @Date: 2019/7/26 10:48
 * @Description:
 */
public class JUnitConcurrentHashMapTest {

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    @Test
    public void test() throws InterruptedException {

        System.setProperty("test", "asd123asd123");
        Thread.sleep(1000 * 60 * 60);
    }

    @Test
    public void testInitCreate() {

        ConcurrentHashMap defaultMap = new ConcurrentHashMap();
        // 指定初始map容量
        // 如果超过1 << 30的1/2，则直接使用1 << 30
        // 否则将会使用tableSizeFor，针对 initialCapacity + (initialCapacity >>> 1) + 1)
        // 因为右移一位，因此+1补充防止奇数不够
        ConcurrentHashMap initialCapacityMap = new ConcurrentHashMap(12);
        // 指定一个初始map，全部塞进新的map中
        // 此时初始容量使用 DEFAULT_CAPACITY = 16
        // 因此如果初始塞的map比较大，将会频繁触发扩容步骤
        ConcurrentHashMap tmp = new ConcurrentHashMap(initialCapacityMap);
    }


    /**
     * 这是什么魔鬼算法？
     * 可以将任意整数算出向上取的最大2的指数次值
     */
    private static final int tableSizeFor(int c) {
        // 如果此处不减1，则2的指数将会翻一倍
        int n = c - 1;

        // 换成二进制后，此处是确保第二大位能够补1
        n |= n >>> 1;
        // 此时前两位都已取保为1，则前两位不用确保补1，直接移2位，此运算能确保前四位补1
        n |= n >>> 2;
        // 因上一步运算则会保证前4位确保补1，则此时，直接移4位，将能够保证前8位补1
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Test
    public void testAdd() {
        ConcurrentHashMap tmp = new ConcurrentHashMap();
    }
}
