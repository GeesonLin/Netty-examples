package com.shensiyuan.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewClient {
    public static void main(String[] args) throws IOException {
        String fileName = "D:\\Tools\\spring-tool-suite-3.9.8.RELEASE-e4.11.0-win32-x86_64.zip";
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long position = 0;
        long fileSize = fileChannel.size();
        System.out.println("文件大小字节数：" + fileSize);

        long startTime = System.currentTimeMillis();
        while (fileSize > 0) {
            long count = fileChannel.transferTo(position, fileSize, socketChannel);
            if (count > 0)
            {
                position += count;
                fileSize-= count;
            }

            System.out.println("本次传输字节数：" + count);
        }

        System.out.println("总共传输：" + position + ", 耗时：" + (System.currentTimeMillis()-startTime));

        fileChannel.close();
        socketChannel.close();
    }
}
