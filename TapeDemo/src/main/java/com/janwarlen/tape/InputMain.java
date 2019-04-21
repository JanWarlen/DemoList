package com.janwarlen.tape;

import com.squareup.tape.QueueFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Scanner;

/**
 * @ClassName: InputMain
 * @author: janwarlen
 * @Date: 2019/3/3 09:57
 * @Description:
 */
public class InputMain {
    private static final String BASE_FILE_PATH = "/usr/local/dev/tmp/tape";

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        File file = new File(BASE_FILE_PATH + "test");
        QueueFile queueFile = null;
        try {
            queueFile = new QueueFile(file);
            while (true) {
                System.out.print("value:");
                String value = read.nextLine();
                queueFile.add(value.getBytes(Charset.forName("utf-8")));
            }
        } catch (IOException e) {
            // new QueueFile(file);
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
