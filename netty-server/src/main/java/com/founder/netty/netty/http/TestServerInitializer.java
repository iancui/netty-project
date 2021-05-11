package com.founder.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer  extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个netty提供的httpServerCodec（编解码器）
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        // 增加一个自定义的Handler
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());



    }
}
