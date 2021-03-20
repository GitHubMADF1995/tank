package com.madf.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 消息解码器
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //头部指定：消息类型(4位)，消息长度(4位)
        if (byteBuf.readableBytes() < 8) return;//TCP处理拆包，粘包的问题

        byteBuf.markReaderIndex();//记录读指针的位置

        MsgType msgType = MsgType.values()[byteBuf.readInt()];//消息类型
        int length = byteBuf.readInt();//消息长度
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();//重置读指针的位置
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Msg msg = null;
//        switch(msgType) {
//            case TankJoin:
//                msg = new TankJoinMsg();
//                break;
//            case TankStartMoving:
//                msg = new TankStartMovingMsg();
//                break;
//            case TankStop:
//                msg = new TankStopMsg();
//                break;
//            default:
//                break;
//        }

        //使用反射生成对象
        msg = (Msg) Class.forName("com.madf.net." + msgType + "Msg").getDeclaredConstructor().newInstance();
        msg.parseBytesToMsg(bytes);
        list.add(msg);
    }
}
