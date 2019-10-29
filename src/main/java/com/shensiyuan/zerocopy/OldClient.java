package com.shensiyuan.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class OldClient {
    public static void main(String[] args) throws IOException {
        String fileName = "D:\\Tools\\spring-tool-suite-3.9.8.RELEASE-e4.11.0-win32-x86_64.zip";
        InputStream inputStream = new FileInputStream(fileName);
        Socket socket = new Socket("localhost", 8899);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCnt = 0;
        long total = 0;
        long startTime = System.currentTimeMillis();

        while ((readCnt = inputStream.read(buffer)) >= 0) {
            total += readCnt;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送总字节数:" + total + ", 耗时：" + (System.currentTimeMillis()-startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
