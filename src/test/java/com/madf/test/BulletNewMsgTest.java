package com.madf.test;

import com.madf.net.*;
import com.madf.tank.Dir;
import com.madf.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BulletNewMsgTest {

    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID playerId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Msg msg = new BulletNewMsg(playerId, id, 5, 10, Dir.DOWN, Group.BAD);
        channel.pipeline().addLast(new MsgEncoder());
        //写出去
        channel.writeOutbound(msg);
        //读出来
        ByteBuf buf = channel.readOutbound();

        //消息类型
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.BulletNew, msgType);

        int length = buf.readInt();
        assertEquals(48, length);//id:高8低8,x:4,y:4,dir:4,group:4

        UUID uuid1 = new UUID(buf.readLong(), buf.readLong());
        UUID uuid2 = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        Group group = Group.values()[buf.readInt()];

        assertEquals(playerId, uuid1);
        assertEquals(id, uuid2);
        assertEquals(5, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
        assertEquals(Group.BAD, group);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID playerId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Msg msg = new BulletNewMsg(playerId, id, 5, 10, Dir.DOWN, Group.BAD);
        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.BulletNew.ordinal());
        byte[] bytes = msg.parseMsgToBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        channel.writeInbound(buf.duplicate());
        BulletNewMsg msgRead = channel.readInbound();

        assertEquals(playerId, msgRead.playerId);
        assertEquals(id, msgRead.id);
        assertEquals(5, msgRead.x);
        assertEquals(10, msgRead.y);
        assertEquals(Dir.DOWN, msgRead.dir);
        assertEquals(Group.BAD, msgRead.group);
    }

}
