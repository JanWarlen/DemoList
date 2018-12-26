package com.janwarlen.mq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: janwarlen
 * @Date: 2018/11/21 17:30
 * @Description:
 */
@Component
public class MQProducer {
    /**
     * 生产者的组名
     */
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    private DefaultMQProducer producer;

    @PostConstruct
    public void defaultMQProducer() {
        //生产者的组名
        this.producer = new DefaultMQProducer(producerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        try {
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String topic, String tags, String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        //创建一个消息实例，包含 topic、tag 和 消息体
        //如下：topic 为 "TopicTest"，tag 为 "push"
        Message message = new Message(topic, tags, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        for (int i = 0; i < 1; i++) {
            SendResult result = producer.send(message);
            System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
        }
    }
}
