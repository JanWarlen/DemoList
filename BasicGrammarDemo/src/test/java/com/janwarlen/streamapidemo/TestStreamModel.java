package com.janwarlen.streamapidemo;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/24 11:21
 * @Description:
 */
public class TestStreamModel {
    private static int counter = 0;

    public static void wasCalled () {
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        TestStreamModel.counter = counter;
    }
}
