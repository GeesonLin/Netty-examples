package com.shensiyuan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest8 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("Input.txt");
        FileOutputStream outputStream = new FileOutputStream("Output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println("read bytes:" + read);

            if (-1 == read)
                break;

            buffer.flip();
            outputChannel.write(buffer);
        }

        inputStream.close();
        outputStream.close();
    }
}
