package com.founder.netty.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {

        try {
            selector = Selector.open();


            listenChannel = ServerSocketChannel.open();

            listenChannel.socket().bind(new InetSocketAddress(PORT));

            listenChannel.configureBlocking(false);

            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listen() {

        try {

            while (true) {

                int count = selector.select();
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {

                            SocketChannel socketChannel = listenChannel.accept();

                            socketChannel.configureBlocking(false);

                            socketChannel.register(selector, SelectionKey.OP_READ);

                            System.out.println(socketChannel.getRemoteAddress() + " 上线了");

                        }
                        if (key.isReadable()) {
                            readData(key);
                        }
                    }

                    iterator.remove();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取客户端消息
     *
     * @param key SelectionKey
     */

    private void readData(SelectionKey key) {

        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = socketChannel.read(buffer);

            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);


                transferMsgToOthers(msg, socketChannel);
            }


        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + " 离线了");
                key.cancel();
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    /**
     * 转发消息给其他客户端
     *
     * @param msg String
     * @param selfChannel SocketChannel
     */
    private void transferMsgToOthers(String msg, SocketChannel selfChannel) throws IOException {

        for (SelectionKey key : selector.keys()) {

            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != selfChannel) {

                SocketChannel destChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                destChannel.write(buffer);
            }

        }

    }


    public static void main(String[] args) {

        GroupChatServer chatServer = new GroupChatServer();

        chatServer.listen();

    }
}
