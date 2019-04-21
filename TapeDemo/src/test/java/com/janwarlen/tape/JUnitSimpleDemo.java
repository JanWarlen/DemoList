package com.janwarlen.tape;

import com.squareup.tape.QueueFile;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: JUnitSimpleDemo
 * @author: janwarlen
 * @Date: 2019/3/3 00:19
 * @Description: QueueFile 内部使用 RandomAccessFile 以避免多进程操作
 * 但是需要额外操作，防止单独进程的多线程问题
 */
public class JUnitSimpleDemo {

    private static final String BASE_FILE_PATH = "/usr/local/dev/tmp/tape";

    @Test
    public void testFirstStep() {
        String node = "test";
        File file = new File(BASE_FILE_PATH + node);

        QueueFile queueFile = null;
        try {
            queueFile = new QueueFile(file);
            queueFile.add("test".getBytes(Charset.forName("utf-8")));
            TimeUnit.SECONDS.sleep(5);
        } catch (IOException e) {
            // new QueueFile(file);
            e.printStackTrace();
        } catch (InterruptedException e) {
            // sleep
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(queueFile)) {
                try {
                    queueFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testFirstStepRead() {
        String node = "test";
        File file = new File(BASE_FILE_PATH + node);

        QueueFile queueFile = null;
        try {
            queueFile = new QueueFile(file);
            byte[] peek = queueFile.peek();
            String s = new String(peek, Charset.forName("utf-8"));
            System.out.println(s);
            TimeUnit.SECONDS.sleep(5);
        } catch (IOException e) {
            // new QueueFile(file);
            e.printStackTrace();
        } catch (InterruptedException e) {
            // sleep
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(queueFile)) {
                try {
                    queueFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
