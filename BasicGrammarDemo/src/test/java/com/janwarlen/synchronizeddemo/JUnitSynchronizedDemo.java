package com.janwarlen.synchronizeddemo;

import org.junit.Test;

public class JUnitSynchronizedDemo {

    @Test
    public void testSynchronizedObject() {
        final SynchronizedObjectDemo synchronizedObjectDemo = new SynchronizedObjectDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedObjectDemo.sayHi();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedObjectDemo.action();
            }
        }).start();
    }

    @Test
    public void testSynchronizedClass() {
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

    @Test
    public void testSynchronizedBlock() {
        final SynchronizedBlockDemo synchronizedBlockDemo = new SynchronizedBlockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedBlockDemo.sayHi();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedBlockDemo.action();
            }
        }).start();
    }

}
