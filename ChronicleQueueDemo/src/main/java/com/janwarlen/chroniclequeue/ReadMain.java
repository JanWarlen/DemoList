package com.janwarlen.chroniclequeue;

import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ReadMain
 * @author: janwarlen
 * @Date: 2019/3/3 10:13
 * @Description:
 */
public class ReadMain {
    private static final String BASE_FILE_PATH = "/usr/local/dev/tmp/proton";

    public static void main(String[] args) {
        String node = "/test";
        SingleChronicleQueue chronicleQueue = ChronicleQueue.singleBuilder(BASE_FILE_PATH + node).build();
//        SingleChronicleQueue indexQueue = ChronicleQueue.singleBuilder(BASE_FILE_PATH + node + "index").build();
        ExcerptTailer tailer = chronicleQueue.createTailer();
//        ExcerptTailer indexQueueTailer = indexQueue.createTailer();
        while (true) {
//            String index = indexQueueTailer.readText();
//            if (Objects.isNull(index)) {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                continue;
//            }
//            boolean b = tailer.moveToIndex(Long.valueOf(index));
//            if (!b) {
//                System.out.println("index 不规范或不存在!");
//            }
            String s = tailer.readText();
            if (Objects.isNull(s)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            System.out.println("index:" + tailer.index() + ";value:" + s);
        }
    }
}
