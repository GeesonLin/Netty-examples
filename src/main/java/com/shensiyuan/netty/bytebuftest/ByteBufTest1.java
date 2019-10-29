package com.shensiyuan.netty.bytebuftest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ByteBufTest1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello world", Charset.forName("utf-8"));

        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, Charset.forName("utf-8")));
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());

            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

        }
    }
}
