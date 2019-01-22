package com.janwarlen.socketdemo;

import org.junit.Test;

/**
 *
 * @author: janwarlen
 * @Date: 2019/1/21 17:44
 * @Description:
 */
public class SocketServerTest {

    public static void main(String[] args) {
        // q 退出
        SocketServer socketServer = new SocketServer(1999);
        socketServer.start();
    }
}
