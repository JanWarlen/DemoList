package com.janwarlen.file;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangjian
 * @Date: 2018/12/19 17:57
 * @Description:
 */
public class ZipUtilTest {

    @Test
    public void testZipFileWithDir() {
        try (OutputStream outputstream = new FileOutputStream("/usr/local/dev/tmp/test.zip")) {
            List<String> fileNameList = new ArrayList<>();
            fileNameList.add("/usr/local/dev/tmp/test.log");
            ZipUtil.zipFile(fileNameList, outputstream, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZipFileWithoutDir() {
        try (OutputStream outputstream = new FileOutputStream("/usr/local/dev/tmp/test.zip")) {
            List<String> fileNameList = new ArrayList<>();
            fileNameList.add("/usr/local/dev/tmp/test.log");
            ZipUtil.zipFile(fileNameList, outputstream, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnZipFile() {
        ZipUtil.unZipFile("/usr/local/dev/tmp/test.zip", "/usr/local/dev/tmp/unzip");
    }

}
