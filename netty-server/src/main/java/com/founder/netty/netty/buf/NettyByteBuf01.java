package com.founder.netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {

    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = buffer.writerIndex(); i < 8; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity=" + buffer.capacity());

        for (int i = 0; i < buffer.writerIndex(); i++) {
            System.out.println(buffer.readByte());
        }


    }
}
