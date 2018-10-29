package com.janwarlen.map;

import org.junit.Test;

import java.util.HashMap;

public class HashMapTest {

    @Test
    public void testPut() {
        HashMap<String, Integer> test = new HashMap<String, Integer>();
        for (int i = 0; i < 13; i++) {
            test.put(String.valueOf(i), i);
        }
        System.out.println(test);
//        test.put(String.valueOf(11), 11);
//        test.put(String.valueOf(12), 12);
//        test.get(String.valueOf(11));
    }

    /**
     * 测试指定初始容量自动扩容过程
     */
    @Test
    public void testReSize2() {
        HashMap<String, Integer> test = new HashMap<String, Integer>(11);

        test.put(String.valueOf(0), 0);
        test.put(String.valueOf(1), 1);
        for (int i = 2; i < 7; i++) {
            test.put(String.valueOf(i), i);
            System.out.println(String.valueOf(i).hashCode() + "____" + i);
        }
        System.out.println(test);
    }
}
