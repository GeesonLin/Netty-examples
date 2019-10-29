package com.shensiyuan.nio;

import java.nio.ByteBuffer;

public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getClass());

        for(int i=0; i<10; i++) {
            buffer.put((byte)i);
        }

        ByteBuffer robuffer = buffer.asReadOnlyBuffer();
        System.out.println(robuffer.getClass());

    }
}
