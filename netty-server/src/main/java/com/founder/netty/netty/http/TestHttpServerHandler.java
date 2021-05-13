package com.founder.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.charset.Charset;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端数据
     *
     * @param ctx ChannelHandlerContext
     * @param msg HttpObject
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {


            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                return;
            }
            System.out.println("ctx类型= " +ctx.getClass());

            ctx.channel().eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("msg 类型" + msg.getClass());
                    System.out.println("客户端的地址" + ctx.channel().remoteAddress());

                    ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", Charset.forName("GBK"));

                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

                    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

                    ctx.pipeline().writeAndFlush(response);


                }
            });


        }

    }
}
