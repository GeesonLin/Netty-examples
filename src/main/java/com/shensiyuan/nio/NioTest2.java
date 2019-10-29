package com.shensiyuan.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest2 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        System.out.println("Read file content:");
        while (byteBuffer.remaining() > 0) {
            System.out.println("character:" + (char)byteBuffer.get());
        }

        fileChannel.close();
        inputStream.close();
    }
}
