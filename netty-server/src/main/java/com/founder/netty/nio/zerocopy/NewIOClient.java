package com.founder.netty.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    private static final long FILE_SIZE = 8 * 1024 * 1024;

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("localhost", 7001));

        String filename = "D:\\cmder_mini.zip";

        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        long startTime = System.currentTimeMillis();

        long count = fileChannel.size() / FILE_SIZE;
        long transferCount = 0;

        for (int i = 0; i <= count; i++) {

            long fileCount = FILE_SIZE;
            if (count == i) {
                fileCount = fileChannel.size() - transferCount;
            }

            transferCount += fileChannel.transferTo(i * FILE_SIZE, fileCount, socketChannel);
        }


        System.out.println("发送的总的字节数 = " + transferCount + " 耗时：" + (System.currentTimeMillis() - startTime));

    }
}
