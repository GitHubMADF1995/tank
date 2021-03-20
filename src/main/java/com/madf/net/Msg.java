package com.madf.net;

/**
 * 定义抽象消息类
 */
public abstract class Msg {
    public abstract byte[] parseMsgToBytes();//编码
    public abstract void parseBytesToMsg(byte[] bytes);//解码
    public abstract void handle();//收到消息的处理
    public abstract MsgType getMsgType();//获取消息的类型
}
