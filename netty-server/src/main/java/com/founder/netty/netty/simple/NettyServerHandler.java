package com.founder.netty.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件（）
     * @param ctx ChannelHandlerContext 上下文对象，含有 管道pipline,通道channel,地址
     * @param msg Object
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        System.out.println("服务器读取线程 "+ Thread.currentThread().getName());
//
//        System.out.println("server ctx = " + ctx);
//
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();
//
//
//        // 将msg转成ByteBuf
//        ByteBuf buf = (ByteBuf) msg;
//
//        System.out.println("客户端发送消息：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端的地址："+ channel.remoteAddress());

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端2。",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        });

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端3。",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        });

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(5* 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端4。",CharsetUtil.UTF_8));
                }catch (Exception e){

                    System.out.println("发生异常" + e.getMessage());
                }

            }
        },5, TimeUnit.SECONDS);

        System.out.println("go on!!");

    }

    /**
     * 数据读取完毕
     * @param ctx ChannelHandlerContext
     * @throws Exception 异常
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // 将数据写入缓存，并刷新
        // 编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端1。",CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
