package com.shensiyuan.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest9 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        byteBuffer.put(0, (byte) 'a');
        byteBuffer.put(3, (byte) 'c');

        fileChannel.close();
    }
}
