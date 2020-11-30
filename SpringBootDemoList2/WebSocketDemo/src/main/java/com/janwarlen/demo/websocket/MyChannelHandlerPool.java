package com.janwarlen.demo.websocket;

import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MyChannelHandlerPool
 * @author: wangjian
 * @Date: 2020/11/23 20:59
 * @Description:
 */
public class MyChannelHandlerPool {
    public MyChannelHandlerPool() {
    }

    //可以存储userId与ChannelId的映射表
    public static ConcurrentHashMap<String, ChannelId> channelIdMap = new ConcurrentHashMap<>();

    //channelGroup通道组
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
