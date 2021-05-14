package com.founder.netty.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
   @Override
   public void channelActive(ChannelHandlerContext ctx) throws Exception {

      System.out.println("client "+ ctx);
      StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(122).setName("哈哈哈").setAge(15).build();
      ctx.writeAndFlush(student);

   }

   @Override
   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

      ByteBuf byteBuf = (ByteBuf) msg;

      System.out.println("服务器回复的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
      System.out.println("服务器的地址：" + ctx.channel().remoteAddress());

   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      ctx.close();
   }
}
