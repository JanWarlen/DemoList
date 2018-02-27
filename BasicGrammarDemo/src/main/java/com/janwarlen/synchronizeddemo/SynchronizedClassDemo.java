package com.janwarlen.synchronizeddemo;

public class SynchronizedClassDemo {

    public static synchronized void sayHi() {
        System.out.println("Hi!");
        try {
            System.out.println("How's going?");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Bye!");
    }

    public static synchronized void action() {
        System.out.println("shake hands.");
        try {
            System.out.println("hava a five.");
            Thread.sleep(3000);
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
