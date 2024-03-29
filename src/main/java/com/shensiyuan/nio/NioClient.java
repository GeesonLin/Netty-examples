package com.shensiyuan.nio;

import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {
    public static void main(String[] args) throws IOException {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

            while (true) {
                //System.out.println("pre select");
                selector.select();
                //System.out.println("after select");

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey: selectionKeys) {
                    if (selectionKey.isConnectable()) {
                        SocketChannel client = (SocketChannel)selectionKey.channel();

                        if (client.isConnectionPending()) {
                            client.finishConnect();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                            writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(()-> {
                               while (true) {
                                   try {
                                       writeBuffer.clear();
                                       InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                       BufferedReader br = new BufferedReader(inputStreamReader);
                                       String sendMsg = br.readLine();

                                       writeBuffer.put(sendMsg.getBytes());
                                       writeBuffer.flip();
                                       client.write(writeBuffer);
                                   } catch (Exception ex) {
                                       ex.printStackTrace();
                                   }
                               }
                            });
                        }
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();

                        ByteBuffer readBuffer = ByteBuffer.allocate(512);
                        int count = client.read(readBuffer);
                        if (count > 0) {
                            readBuffer.flip();
                            String receivedMsg = new String(readBuffer.array(), 0, count);
                            System.out.println("收到" + receivedMsg);
                        }
                    }
                }
                selectionKeys.clear();
            }
        } catch (ImagingOpException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
