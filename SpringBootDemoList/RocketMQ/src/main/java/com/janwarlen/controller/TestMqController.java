package com.janwarlen.controller;

import com.janwarlen.model.MqMessage;
import com.janwarlen.mq.MQProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: janwarlen
 * @Date: 2018/11/25 17:30
 * @Description:
 */
@RestController
public class TestMqController {

    @Autowired
    private MQProducer mqProducer;

    @RequestMapping("/pushMessage")
    public void pushMessage(@RequestBody MqMessage message) throws InterruptedException, RemotingException, UnsupportedEncodingException, MQClientException, MQBrokerException {
        mqProducer.sendMessage(message.getTopic(), message.getTags(), message.getMessage());
    }



}
