package com.founder.netty.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    /**
     * 读取数据事件（）
     * @param ctx ChannelHandlerContext 上下文对象，含有 管道pipline,通道channel,地址
     * @param msg Object
     * @throws Exception 异常
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {


        System.out.println("客户端发送的人员: id=" + msg.getId() + " name=" + msg.getName() + " age="+ msg.getAge()) ;

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
