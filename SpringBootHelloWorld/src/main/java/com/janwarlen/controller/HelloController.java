package com.janwarlen.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Auther: janwarlen
 * @Date: 2018/11/6 21:08
 * @Description:
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public String helloworld() throws UnknownHostException {
        return "helloworld:" + InetAddress.getLocalHost() + port;
    }
}
