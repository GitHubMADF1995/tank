package com.madf.test;

import com.madf.net.*;
import com.madf.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankDieMsgTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID bulletId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Msg msg = new TankDieMsg(bulletId, id);
        channel.pipeline().addLast(new MsgEncoder());
        //写出去
        channel.writeOutbound(msg);
        //读出来
        ByteBuf buf = channel.readOutbound();

        //消息类型
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankDie, msgType);

        int length = buf.readInt();
        assertEquals(32, length);//高8低8

        UUID uuid1 = new UUID(buf.readLong(), buf.readLong());
        UUID uuid2 = new UUID(buf.readLong(), buf.readLong());

        assertEquals(bulletId, uuid1);
        assertEquals(id, uuid2);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID bulletId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Msg msg = new TankDieMsg(bulletId, id);
        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankDie.ordinal());
        byte[] bytes = msg.parseMsgToBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        channel.writeInbound(buf.duplicate());
        TankDieMsg msgRead = channel.readInbound();

        assertEquals(bulletId, msgRead.bulletId);
        assertEquals(id, msgRead.id);
    }

}
