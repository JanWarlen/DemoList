package com.janwarlen.encrypt;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Auther: wangjian
 * @Date: 2018/12/20 10:55
 * @Description: 测试md5工具
 */
public class MD5UtilsTest {

    @Test
    public void testMd5File() {
        File file = new File("/usr/local/dev/tmp/test.log");
        MD5(file);
    }

    /**
     * 验证解压缩文件与原文件md5值
     * 验证结果，ZipUtil的压缩解压缩不会改变文件md5值
     */
    @Test
    public void testMd5File2() {
        File file = new File("/usr/local/dev/tmp/unzip/test.log");
        MD5(file);
    }

    private void MD5(File file) {
        if (!file.exists()) {
            throw new RuntimeException("文件不存在！");
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            String fileMd5 = MD5Utils.md5(fileInputStream);
            System.out.println(fileMd5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
