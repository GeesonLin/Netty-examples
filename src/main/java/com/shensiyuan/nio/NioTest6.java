package com.shensiyuan.nio;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.nio.ByteBuffer;

public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        buffer.position(2);
        buffer.limit(6);

        ByteBuffer sliceBuffer = buffer.slice();

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            int b = 2 * (int) sliceBuffer.get(i);
            sliceBuffer.put(i, (byte) b);
        }

        buffer.limit(buffer.capacity());

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.get(i));
        }
    }
}
