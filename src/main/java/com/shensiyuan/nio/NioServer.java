package com.shensiyuan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {
    private  static Map<String, SocketChannel> clientMap = new HashMap();
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocketChannel.configureBlocking(false);
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                System.out.println("pre select");
                selector.select();
                System.out.println("after select");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final SocketChannel[] client = new SocketChannel[1];
                selectionKeys.forEach(selectionKey -> {
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();

                            client[0] = server.accept();
                            client[0].configureBlocking(false);
                            client[0].register(selector, SelectionKey.OP_READ);
                            String key = "[" + UUID.randomUUID() + "]";
                            clientMap.put(key, client[0]);
                        } else if (selectionKey.isReadable()) {
                            client[0] = (SocketChannel)selectionKey.channel();

                            ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                            int count = client[0].read(byteBuffer);
                            if (count > 0) {
                                byteBuffer.flip();
                                Charset charset = Charset.forName("UTF-8");
                                String receivedMsg = String.valueOf(charset.decode(byteBuffer).array());
                                System.out.println(client[0] + ":" + receivedMsg);

                                String sendKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (client[0] == entry.getValue()) {
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> entry: clientMap.entrySet()) {
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                                    writeBuffer.put((sendKey + ":" + receivedMsg).getBytes());
                                    writeBuffer.flip();
                                    value.write(writeBuffer);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
