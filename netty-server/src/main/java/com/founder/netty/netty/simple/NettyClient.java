package com.founder.netty.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        //客户端需要一个事件循环组

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {


                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            });

            System.out.println("客户端 ok");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            channelFuture.channel().closeFuture().sync();
        }finally {

            eventExecutors.shutdownGracefully();

        }

    }
}
