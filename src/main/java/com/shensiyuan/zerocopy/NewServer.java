package com.shensiyuan.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8899);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readCnt = 0;
            while (-1 != readCnt) {
                try {
                    readCnt = socketChannel.read(byteBuffer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                byteBuffer.rewind();
            }
            System.out.println("数据传输结束。");
        }
    }
}
