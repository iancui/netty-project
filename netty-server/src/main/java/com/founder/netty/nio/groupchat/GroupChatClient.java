package com.founder.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;

    private Selector selector;

    private SocketChannel socketChannel;

    private String userName;

    public GroupChatClient() {

        try {
            selector = Selector.open();

            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ);

            userName = socketChannel.getLocalAddress().toString().substring(1);

            System.out.println(userName + " 准备好了");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            msg = userName + " 说：" + msg;

            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg(){

        try {
        int read = selector.select();

        if (read > 0 ){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){

                SelectionKey key = iterator.next();
                if (key.isReadable()){
                    SocketChannel channel = (SocketChannel)key.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    channel.read(buffer);

                    System.out.println(new String(buffer.array()).trim());

                }

            }
            iterator.remove();

        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        GroupChatClient chatClient = new GroupChatClient();

        new Thread(){
            public void run(){
                while (true){
                    chatClient.readMsg();
                    try {
                        Thread.currentThread().sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){

            String msg = scanner.next();

            chatClient.sendMsg(msg);


        }



    }



}
