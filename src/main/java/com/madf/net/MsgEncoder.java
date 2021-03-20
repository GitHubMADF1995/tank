package com.madf.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码器
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(msg.getMsgType().ordinal());//消息类型
        byte[] bytes = msg.parseMsgToBytes();//把消息转为字节数组
        byteBuf.writeInt(bytes.length);//消息长度
        byteBuf.writeBytes(bytes);//消息内容
    }
}
