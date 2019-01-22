package com.janwarlen.socketdemo;

import org.junit.Test;

/**
 * @ClassName: SocketClientTest
 * @author: janwarlen
 * @Date: 2019/1/21 17:46
 * @Description:
 */
public class SocketClientTest {

    public static void main(String[] args) {
        // bye 退出
        SocketClient socketClient = new SocketClient("127.0.0.1", 1999);
        socketClient.start();
    }
}
