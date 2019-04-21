package com.janwarlen.chroniclequeue;

import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: TestSimpleDemo
 * @author: janwarlen
 * @Date: 2019/3/1 11:58
 * @Description:
 */
public class TestSimpleDemo {

    private static final String BASE_FILE_PATH = "/usr/local/dev/tmp/proton";

    /**
     * 简单的消息队列写入与读取
     */
    @Test
    public void testFirstStep() throws InterruptedException {
        String node = "/test";
        SingleChronicleQueue chronicleQueue = ChronicleQueue.singleBuilder(BASE_FILE_PATH + node).build();
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 每个线程都需要一个单例的appender
                    ExcerptAppender excerptAppender = chronicleQueue.acquireAppender();
                    ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
                    while (!writeLock.tryLock()) {
                    }
                    System.out.println("time:" + System.currentTimeMillis());
                    // 追加器
//                    try {
                        excerptAppender.writeText("message from test node." + System.currentTimeMillis());


                    writeLock.unlock();
                }
            });
            thread.setName("test-"+i);
            thread.start();
        }
        Thread.sleep(5000);
        // 读取器
        ExcerptTailer tailer = chronicleQueue.createTailer();
        System.out.println("indexCount:"+chronicleQueue.indexCount());
        while (true) {
            String readText = tailer.readText();
            if (null == readText) {
                break;
            }
            System.out.println("index:" + tailer.index() + ";value:" + readText);
        }
    }

    @Test
    public void testWriteAndReadByMethod() {
        String node = "/test";
        SingleChronicleQueue chronicleQueue = ChronicleQueue.singleBuilder(BASE_FILE_PATH + node).build();
//        chronicleQueue.createTailer().

    }
}
