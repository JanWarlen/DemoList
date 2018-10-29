package com.janwarlen.synchronizeddemo;

public class TestThreadA extends Thread {

    private byte[] test;

    public TestThreadA(byte[] obj) {
        this.test = obj;
    }

    @Override
    public void run() {
        synchronized (test) {
            System.out.println("a1");
            try {
                Thread.sleep(2000);
                System.out.println("a2");
                test[0] = Byte.valueOf(Thread.currentThread().getName());
                System.out.println("a3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
