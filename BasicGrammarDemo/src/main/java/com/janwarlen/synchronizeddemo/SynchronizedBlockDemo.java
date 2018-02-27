package com.janwarlen.synchronizeddemo;

/**
 * JUnit 无法测试多线程
 */
public class SynchronizedBlockDemo {

    public void sayHi() {
        System.out.println("Hi!");
        try {
            synchronized (this) {
                System.out.println("How's going?");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Bye!");
    }

    public synchronized void action() {
        System.out.println("shake hands.");
        try {
            synchronized (this) {
                System.out.println("hava a five.");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wave the hand");
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedClassDemo.sayHi();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedClassDemo.action();
            }
        }).start();
    }

}
