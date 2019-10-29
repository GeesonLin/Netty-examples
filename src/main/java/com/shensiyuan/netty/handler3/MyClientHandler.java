package com.shensiyuan.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到的消息：");
        System.out.println("长度：" + length);
        System.out.println("内容：" + new String(content, Charset.forName("utf-8")));
        System.out.println("客户端接收到的消息数量：" + (++this.count));

        ctx.writeAndFlush("from client:" + LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String messageToBeSent = "send from client";
            byte[] content = messageToBeSent.getBytes(Charset.forName("utf-8"));
            int length = content.length;

            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setContent(content);
            personProtocol.setLength(length);

            ctx.writeAndFlush(personProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
