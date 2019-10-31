package com.janwarlen.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: janwarlen
 * @Date: 2018/11/21 17:32
 * @Description:
 */
@Component
public class MQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumer.class);

    /**
     * 消费者的组名
     */
    @Value("${apache.rocketmq.consumer.PushConsumer}")
    private String consumerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.topic}")
    private String topic;
    @Value("${rocketmq.consumer.tag}")
    private String tag;

    /**
     * 消费者
     */
    DefaultMQPushConsumer consumer;

    @PostConstruct
    public void defaultMQPushConsumer() {
        //消费者的组名
        this.consumer = new DefaultMQPushConsumer(consumerGroup);

        //指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setVipChannelEnabled(false);
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe(topic, tag);

            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {

                        String tags = messageExt.getTags();
                        if (tags.contains("heart")) {
                            continue;
                        }

                        LOGGER.info("messageExt properties: " + JSONObject.toJSONString(messageExt.getProperties()));
                        //输出消息内容

                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);

//                        LOGGER.info("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + messageBody);
                        //输出消息内容
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
