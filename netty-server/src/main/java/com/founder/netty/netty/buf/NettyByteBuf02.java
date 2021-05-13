package com.founder.netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,world!北京", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()){
            byte[] array = byteBuf.array();

            String s = new String(array,CharsetUtil.UTF_8);
            System.out.println(s);

            System.out.println("ByteBuf =" + byteBuf);

            System.out.println(byteBuf.readByte());

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());

            // 可读取的字节数
            int lent = byteBuf.readableBytes();
            System.out.println("len=" + lent);


//            for (int i = 0; i < lent; i++) {
//                System.out.println((char) byteBuf.getByte(i));
//            }

            System.out.println(byteBuf.getCharSequence(4,6,CharsetUtil.UTF_8));

        }
    }
}
