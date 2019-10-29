package com.shensiyuan.netty.handler2;

import com.shensiyuan.netty.handler.MyByteToLongDecoder2;
import com.shensiyuan.netty.handler.MyLongToByteEncoder;
import com.shensiyuan.netty.handler.MyLongToStringDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyServerHandler());
    }
}
