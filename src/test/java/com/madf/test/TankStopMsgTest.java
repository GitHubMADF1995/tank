package com.madf.test;

import com.madf.net.*;
import com.madf.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankStopMsgTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        Msg msg = new TankStopMsg(id, 5, 10);
        channel.pipeline().addLast(new MsgEncoder());
        //写出去
        channel.writeOutbound(msg);
        //读出来
        ByteBuf buf = channel.readOutbound();

        //消息类型
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankStop, msgType);

        int length = buf.readInt();
        assertEquals(24, length);//id:高8低8,x:4,y:4

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();

        assertEquals(id, uuid);
        assertEquals(5, x);
        assertEquals(10, y);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        Msg msg = new TankStopMsg(id, 5, 10);
        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        byte[] bytes = msg.parseMsgToBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        channel.writeInbound(buf.duplicate());
        TankStopMsg msgRead = channel.readInbound();

        assertEquals(id, msgRead.id);
        assertEquals(5, msgRead.x);
        assertEquals(10, msgRead.y);
    }

}
