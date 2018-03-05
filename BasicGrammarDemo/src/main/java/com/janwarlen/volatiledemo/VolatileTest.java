package com.janwarlen.volatiledemo;

/**
 * 当写一个volatile变量时，JMM会把所有线程本地内存的对应变量副本刷新回主存；（注意是所有共享变量，不是一个volatile变量）
 */
public class VolatileTest extends Thread {

    private static boolean flag1 = false;
    private static volatile boolean flag2 = false;

    private int i = 0;

    public void run() {
        while (!flag1) {
            System.out.println("flag1:" + flag1 + "------------------- flag2:" + flag2);
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("flag1:" + flag1 + "------------------- flag2:" + flag2);
        System.out.println("------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        VolatileTest t = new VolatileTest();
        t.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.currentThread().sleep(2000);
                    flag1 = true;
                    System.out.println("flag1 changed");
                    Thread.currentThread().sleep(1000);
                    flag2 = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }
}
