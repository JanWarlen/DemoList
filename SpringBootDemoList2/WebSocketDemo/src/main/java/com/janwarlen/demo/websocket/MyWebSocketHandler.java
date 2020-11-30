package com.janwarlen.demo.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 * @ClassName: MyWebSocketHandler
 * @author: wangjian
 * @Date: 2020/11/23 20:59
 * @Description:
 */

public class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker webSocketServerHandshaker;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");
        //添加到channelGroup通道组
        MyChannelHandlerPool.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");
        //从channelGroup通道组删除
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
//        ((HttpObjectAggregator.AggregatedFullHttpRequest) o).headers().get("User-Agent")
        if (o instanceof FullHttpRequest) {
            System.out.println("hand shake");
            handHttpRequest(ctx, (FullHttpRequest) o);
        }

        //接收的消息
        System.out.println(String.format("收到客户端%s的数据：%s", ctx.channel().id(), ((TextWebSocketFrame) o).text()));

        // 单独发消息
        // sendMessage(ctx);
        // 群发消息
        sendAllMessage();
    }

    /**
     * 处理客户端向服务端发起http握手请求业务
     *
     * @param context
     * @param fullHttpRequest
     */
    private void handHttpRequest(ChannelHandlerContext context, FullHttpRequest fullHttpRequest) {
        if (!fullHttpRequest.getDecoderResult().isSuccess() || !("websocket".equals(fullHttpRequest.headers().get("Upgrade")))) {//判断是否http握手请求
            sendHttpResponse(context, fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory("ws://localhost:8000/ws", null, false);
        webSocketServerHandshaker = webSocketServerHandshakerFactory.newHandshaker(fullHttpRequest);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
        } else {
            webSocketServerHandshaker.handshake(context.channel(), fullHttpRequest);
        }
    }

    /**
     * 服务端想客户端发送响应消息
     *
     * @param context
     * @param fullHttpRequest
     * @param defaultFullHttpResponse
     */
    private void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest fullHttpRequest, DefaultFullHttpResponse defaultFullHttpResponse) {
        if (defaultFullHttpResponse.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.getStatus().toString(), CharsetUtil.UTF_8);
            defaultFullHttpResponse.content().writeBytes(buf);
            buf.release();
        }
        //服务端向客户端发送数据
        ChannelFuture future = context.channel().writeAndFlush(defaultFullHttpResponse);
        if (defaultFullHttpResponse.getStatus().code() != 200) {
            future.addListener(ChannelFutureListener.CLOSE);
        }

    }

    private void sendMessage(ChannelHandlerContext ctx) {
        String message = "消息";
        ctx.writeAndFlush(new TextWebSocketFrame(message));
    }

    private void sendAllMessage() {
        String message = "我是服务器，这是群发消息;" + System.currentTimeMillis();
        MyChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }

}
