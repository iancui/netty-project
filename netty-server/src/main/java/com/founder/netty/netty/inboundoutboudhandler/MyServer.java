package com.founder.netty.netty.inboundoutboudhandler;

import com.founder.netty.netty.codec2.MyDataInfo;
import com.founder.netty.netty.codec2.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class MyServer {

    public static void main(String[] args) throws InterruptedException {
        // 创建BoosGroup 和 WorkerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            // 绑定一个端口并且同步，生成了一个ChannelFuture对象
            ChannelFuture cf = bootstrap.bind(7000).sync();

            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
