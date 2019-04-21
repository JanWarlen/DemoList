package com.janwarlen.chroniclequeue;

import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;

import java.util.Objects;
import java.util.Scanner;

/**
 * @ClassName: InputMain
 * @author: janwarlen
 * @Date: 2019/3/3 10:09
 * @Description:
 */
public class InputMain {
    private static final String BASE_FILE_PATH = "/usr/local/dev/tmp/proton";

    public static void main(String[] args) {
        String node = "/test";
        SingleChronicleQueue chronicleQueue = ChronicleQueue.singleBuilder(BASE_FILE_PATH + node).build();
        ExcerptAppender excerptAppender = chronicleQueue.acquireAppender();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("value:");
            String inputValue = in.nextLine();
            if (Objects.equals("exit", inputValue)) {
                break;
            }
            excerptAppender.writeText(inputValue);
//            excerptAppender.wire();
        }
    }
}
