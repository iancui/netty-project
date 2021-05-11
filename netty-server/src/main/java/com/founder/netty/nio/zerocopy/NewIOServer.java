package com.founder.netty.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(address);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();

            int readcount = 0 ;
            while (-1!=readcount){


                try {

                    readcount = socketChannel.read(byteBuffer);
                    // 后续不做处理

                    byteBuffer.rewind(); // 倒带 position =0 mark作废

                }catch (IOException e){
                    break;
                }


            }

        }


    }
}
