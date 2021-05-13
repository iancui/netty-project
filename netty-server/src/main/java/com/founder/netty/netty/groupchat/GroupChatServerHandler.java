package com.founder.netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

//    public static Map<String,Channel> channels = new HashMap<>();

    // 定义一个channel组,管理所有的channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 连接建立
     * @param ctx ChannelHandlerContext
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        // 将客户加入聊天的信息推送其他在线的客户段
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[" + sdf.format(new Date()) + "][客户端]" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        // 将客户加入聊天的信息推送其他在线的客户段
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[" + sdf.format(new Date()) + "][客户端]" + channel.remoteAddress() + "离开聊天\n");

    }

    /**
     * 上线,表示channel处于一个活动的状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("[" + sdf.format(new Date()) + "]"+ctx.channel().remoteAddress() + " 上线了~");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("[" + sdf.format(new Date()) + "]"+ctx.channel().remoteAddress() + " 离线了~");
    }
    /**
     * 断开连接会被触发
     * @param ctx ChannelHandlerContext
     * @throws Exception 异常
     */


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {


        Channel channel = ctx.channel();


        channelGroup.forEach(ch -> {

            if(channel!=ch){
                ch.writeAndFlush("[" + sdf.format(new Date()) + "][客户](" +channel.remoteAddress() + "):" + msg+"\n");
            } else {
                ch.writeAndFlush("[" + sdf.format(new Date()) + "][自己]:" + msg +"\n");
            }

        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
