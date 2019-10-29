package com.shensiyuan.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务器接收到的数据：" + new String(content, Charset.forName("utf-8")));
        System.out.println("服务器接收到的数据长度：" + length);
        System.out.println("服务器接收到的消息数量:" + (++this.count));

        String responseMessage = UUID.randomUUID().toString();
        byte[] responseContent = responseMessage.getBytes("utf-8");
        int responseLength = responseContent.length;

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setContent(responseContent);
        personProtocol.setLength(responseLength);

        ctx.writeAndFlush(personProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
