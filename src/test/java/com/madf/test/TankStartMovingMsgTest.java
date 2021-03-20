package com.madf.test;

import com.madf.net.*;
import com.madf.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

public class TankStartMovingMsgTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        Msg msg = new TankStartMovingMsg(id, 5, 10, Dir.DOWN);
        channel.pipeline().addLast(new MsgEncoder());
        //写出去
        channel.writeOutbound(msg);
        //读出来
        ByteBuf buf = channel.readOutbound();

        //消息类型
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankStartMoving, msgType);

        int length = buf.readInt();
        assertEquals(28, length);//id:高8低8,x:4,y:4,dir:4

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];

        assertEquals(id, uuid);
        assertEquals(5, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        Msg msg = new TankStartMovingMsg(id, 5, 10, Dir.DOWN);
        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStartMoving.ordinal());
        byte[] bytes = msg.parseMsgToBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        channel.writeInbound(buf.duplicate());
        TankStartMovingMsg msgRead = channel.readInbound();

        assertEquals(id, msgRead.id);
        assertEquals(5, msgRead.x);
        assertEquals(10, msgRead.y);
        assertEquals(Dir.DOWN, msgRead.dir);
    }

}
