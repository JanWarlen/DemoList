package com.janwarlen.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/19 11:25
 * @Description: md5工具
 */
public class MD5Utils {
    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    public static String Encoder(String str)  {
        //确定计算方法
        MessageDigest md5= null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            logger.error("md5 加密失败:"+e);
            return "";
        }

    }

    /**
     * 进行 MD5 编码
     *
     * @param in
     * @return md5 checksum String in lower case
     * @throws IOException when fail to read from input stream.
     */
    public static String md5(InputStream in) throws IOException {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Fail to get MD5 MessageDigest instance", e);
        }
        byte[] buffer = new byte[1024 * 16];
        int bytes;
        while ((bytes = in.read(buffer)) != -1) {
            md5.update(buffer, 0, bytes);
        }

        byte[] bs = md5.digest();
        return byteArrayToHex(bs);
    }

    /**
     * 字节数组转化为 Hex 字符串表示，输出 Hex 为小写
     *
     * @param bs
     * @param start 开始位置
     * @param end   结束位置
     * @return hex String in lower case
     */
    public static String byteArrayToHex(byte[] bs, int start, int end) {
        StringBuilder sb = new StringBuilder(bs.length << 1);
        for (int i = start; i < end; ++i) {
            byte b = bs[i];
            sb.append("0123456789abcdef".charAt(0xf & (b >> 4)));
            sb.append("0123456789abcdef".charAt(b & 0xf));
        }
        return sb.toString();
    }

    /**
     * 字节数组转化为 Hex 字符串表示，输出 Hex 为小写
     *
     * @param bs
     * @return hex String in lower case
     */
    public static String byteArrayToHex(byte[] bs) {
        return byteArrayToHex(bs, 0, bs.length);
    }

}
