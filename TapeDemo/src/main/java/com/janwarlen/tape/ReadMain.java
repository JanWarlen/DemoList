package com.janwarlen.tape;

import com.squareup.tape.QueueFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ReadMain
 * @author: janwarlen
 * @Date: 2019/3/3 10:01
 * @Description:
 */
public class ReadMain {
    private static final String BASE_FILE_PATH = "/usr/local/dev/tmp/tape";
    public static void main(String[] args) {
        File file = new File(BASE_FILE_PATH + "test");
        QueueFile queueFile = null;
        try {
            queueFile = new QueueFile(file);
            while (true) {
                byte[] peek = queueFile.peek();
//                queueFile.
                if (Objects.isNull(peek)) {
                    TimeUnit.MILLISECONDS.sleep(100);
                } else {
                    System.out.println(new String(peek, Charset.forName("utf-8")));
                }
            }
        } catch (IOException e) {
            // new QueueFile(file);
            e.printStackTrace();
        } catch (InterruptedException e) {
            //  TimeUnit.MILLISECONDS.sleep(100);
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
