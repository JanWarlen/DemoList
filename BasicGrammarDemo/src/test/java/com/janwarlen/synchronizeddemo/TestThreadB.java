package com.janwarlen.synchronizeddemo;

public class TestThreadB extends Thread {

    private byte[] test;

    public TestThreadB(byte[] obj) {
        this.test = obj;
    }

    @Override
    public void run() {
        synchronized (test) {
            System.out.println("b1");
            try {
                test[0] = Byte.valueOf(Thread.currentThread().getName());
                System.out.println("b2");
                Thread.sleep(10000);
                System.out.println("b3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
